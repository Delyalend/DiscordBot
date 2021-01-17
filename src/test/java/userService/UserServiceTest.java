package userService;

import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.model.User;
import com.bot.ranksystem_20.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {


    DaoUser daoUserTest = new DaoUserTestImpl();
    UserServiceImpl userService = new UserServiceImpl(daoUserTest);


    @Test
    @DisplayName("╯°□°）╯")
    public void getById() {
        User user = userService.getById(0L);

        assertNotNull(user, "User is null");
        assertEquals(user.getId(), 0L, "Found the wrong user");
    }

    @Test
    public void exists() {
        boolean exists = userService.exists(0L);
        assertTrue(exists, "User not found");
    }

    @Test
    public void create() {
        User user = userService.create(0L);
        assertNotNull(user, "User not created");
        assertEquals(user.getId(), 0L, "Returned the wrong user");
    }

}
