package hr.winary.webshop.jwpwinary.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserLoginService {

    @Getter
    private static class UserLoginEntry {
        private String ipAddress;
        private LocalDateTime timestamp;

        public UserLoginEntry(String ipAddress, LocalDateTime timestamp) {
            this.ipAddress = ipAddress;
            this.timestamp = timestamp;
        }

    }

    private final List<UserLoginEntry> loginEntries = new ArrayList<>();

    public void logUserLogin(String ipAddress) {
        loginEntries.add(new UserLoginEntry(ipAddress, LocalDateTime.now()));
    }

    public List<UserLoginEntry> getAllLoginEntries() {
        return new ArrayList<>(loginEntries);
    }
}
