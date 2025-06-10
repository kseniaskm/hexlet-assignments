package exercise;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import exercise.model.User;
import exercise.component.UserProperties;

@SpringBootApplication
@RestController
public class Application {

    // Все пользователи
    private List<User> users = Data.getUsers();

    // BEGIN
    @Autowired
    private UserProperties userProperties;

    @GetMapping("/admins")
    public List<String> getAdminName() {
        List<String> adminEmailList = userProperties.getAdmins();
        ArrayList<String> adminNameList = new ArrayList<>();
        for (int i = 0; i < adminEmailList.size(); i++) {
            var adminsEmailI = adminEmailList.get(i);
            var admin = users.stream().filter(u -> Objects.equals(u.getEmail(), adminsEmailI)).findFirst();
            admin.ifPresent(user -> adminNameList.add(user.getName()));
        }
        Collections.sort(adminNameList);
        return adminNameList;
    }
    // END

    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        var user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
