package hr.winary.webshop.jwpwinary.controller.mvc;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import hr.winary.webshop.jwpwinary.GlobalData;
import hr.winary.webshop.jwpwinary.model.*;
import hr.winary.webshop.jwpwinary.repository.PurchaseRepository;
import hr.winary.webshop.jwpwinary.repository.UserRepository;
import hr.winary.webshop.jwpwinary.service.MyUserDetailService;
import hr.winary.webshop.jwpwinary.service.PaypalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringTokenizer;

@Controller
@Slf4j
//@RequestMapping(value = "/paypal")
//@CrossOrigin(origins = "http://localhost:8080")
public class PaymentController {

    private final PaypalService paypalService;
    @Autowired
    private MyUserDetailService userService;
    @Autowired
    private PurchaseRepository purchaseRepository;

    public PaymentController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }


    @GetMapping("/payment/create")
    public RedirectView initiatePayment(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        try {
            String cancelUrl = "http://localhost:8080/payment/cancel";
            String successUrl = "http://localhost:8080/payment/success";
            Double totalPrice;

            User user = userService.findByUsername(userDetails.getUsername());
            double total = GlobalData.cart.stream().mapToDouble(Wine::getPrice).sum();

            for (Wine wine : GlobalData.cart) {
                Purchase purchase = new Purchase();
                purchase.setUser(user);
                purchase.setWine(wine);
                purchase.setPurchaseTime(LocalDateTime.now());
                purchase.setQuantity(1);  // Assuming quantity is 1 for simplicity
                purchase.setTotalPrice(wine.getPrice());
                purchase.setPaymentMethod("paypal");  // Setting payment method to Cash
                purchaseRepository.save(purchase);
            }


            GlobalData.cart.clear();
            model.addAttribute("total", total);


            Payment payment = paypalService.createPayment(
                    total,
                    "EUR",
                    "paypal",
                    "sale",
                    "Rose",
                    cancelUrl,
                    successUrl
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException | com.paypal.core.rest.PayPalRESTException e) {
            e.printStackTrace();
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerID){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerID);
            if (payment.getState().equals("approved")){
                return "paymentSuccess";
            }

        }catch (com.paypal.core.rest.PayPalRESTException e){
            log.error("Error occured: ", e);
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }

        return "paymentSuccess";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel(){
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError(){
        return "paymentError";
    }


    /*@Autowired
    private PaypalService paypalService;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/init")
    public PaymentOrder createPayment(@RequestParam("sum") BigDecimal sum) {
        return paypalService.createPayment(sum);
    }

    @PostMapping(value = "/capture")
    public CompletedOrder completePayment(@RequestParam("token") String token) {
        CompletedOrder completedOrder = paypalService.completePayment(token);
        if ("success".equals(completedOrder.getStatus())) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);

            Purchase purchase = new Purchase();
            purchase.setUser(user);
            purchase.setPurchaseTime(LocalDateTime.now());
            purchase.setQuantity(1);
            purchase.setTotalPrice(completedOrder.getTotalPrice());
            purchase.setPaymentMethod("PayPal");
            purchaseRepository.save(purchase);
        }
        return completedOrder;
    }*/

}
