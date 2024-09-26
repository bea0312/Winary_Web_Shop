package hr.winary.webshop.jwpwinary.service;

import com.paypal.api.payments.*;
import com.paypal.api.payments.Payer;
import com.paypal.base.rest.APIContext;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import hr.winary.webshop.jwpwinary.model.CompletedOrder;
import hr.winary.webshop.jwpwinary.model.PaymentOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

//@Slf4j
@Service
@RequiredArgsConstructor
public class PaypalService {

    private final APIContext apiContext;

    public Payment createPayment(Double total, String currency, String methode, String intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException, com.paypal.base.rest.PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        com.paypal.api.payments.Payer payer = new com.paypal.api.payments.Payer();
        payer.setPaymentMethod(methode);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException, com.paypal.base.rest.PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }




    /*@Autowired
    private PayPalHttpClient payPalHttpClient;

    public PaymentOrder createPayment(BigDecimal fee) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        AmountWithBreakdown amountBreakdown = new AmountWithBreakdown().currencyCode("USD").value(fee.toString());
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().amountWithBreakdown(amountBreakdown);
        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));
        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl("https://localhost:8080/capture")
                .cancelUrl("https://localhost:8080/cancel");
        orderRequest.applicationContext(applicationContext);
        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);

        try {
            HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
            Order order = orderHttpResponse.result();

            String redirectUrl = order.links().stream()
                    .filter(link -> "approve".equals(link.rel()))
                    .findFirst()
                    .orElseThrow(NoSuchElementException::new)
                    .href();

            return new PaymentOrder("success", order.id(), redirectUrl);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new PaymentOrder("Error");
        }
    }

    public CompletedOrder completePayment(String token) {
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            if (httpResponse.result().status() != null) {
                Integer totalPrice = Integer.valueOf(httpResponse.result().purchaseUnits().get(0).payments().captures().get(0).amount().value());
                return new CompletedOrder("success", token, totalPrice);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new CompletedOrder("error");
    }*/
    //--------------------------------------






}
