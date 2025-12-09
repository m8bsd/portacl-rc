package ee.ttu.tarkvaratehnika.simpleapp;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.user.User;
import ee.ttu.tarkvaratehnika.simpleapp.data.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void testUserCreations() {
        User user = userService.createUser("", "", "", "", "");
        System.out.println(user);
        System.out.println(user.getPerson());
        System.out.println(user.getUserPreferences());
        System.out.println(user.getUserPreferences().getUser());
        System.out.println(user.getPerson().getUser());
    }
}
