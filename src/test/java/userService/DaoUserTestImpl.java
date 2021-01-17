package userService;

import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.model.Title;
import com.bot.ranksystem_20.model.User;

import java.util.List;

public class DaoUserTest implements DaoUser {
    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User getUserById(long userId) {
        return new User(0L,new Title(),0,0,true);
    }

    @Override
    public void createUser(Long userId) {

    }

    @Override
    public void addPointsToUser(Long userId, int points) {

    }

    @Override
    public void setValueIsNewTitle(Long userId, boolean value) {

    }

    @Override
    public void minusPointsOne(Long userId, int points) {

    }
}
