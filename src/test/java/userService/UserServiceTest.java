package userService;

import com.bot.ranksystem_20.dao.DaoUser;
import com.bot.ranksystem_20.model.Title;
import com.bot.ranksystem_20.model.User;
import com.bot.ranksystem_20.service.UserService;
import com.bot.ranksystem_20.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private DaoUser daoUser;

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @ParameterizedTest
    @CsvSource({"1,,10,15,true", "2,,20,25,false",
            "3,,30,35,true", "4,,40,45,false",
            "5,,50,55,true", "6,,60,65,false"})
    public void getById(@AggregateWith(UserAggregator.class) User user) {
        when(daoUser.getUserById(user.getId())).thenReturn(user);
        User userFromService = userServiceImpl.getById(user.getId());
        assertNotNull(userFromService, "User is null");
        assertEquals(userFromService.getId(), user.getId(), "Found the wrong user");
    }

    @ParameterizedTest
    @CsvSource({"1,,10,15,true", "2,,20,25,false",
            "3,,30,35,true", "4,,40,45,false",
            "5,,50,55,true", "6,,60,65,false"})
    public void exists(@AggregateWith(UserAggregator.class) User user) {
        when(daoUser.getUserById(user.getId())).thenReturn(user);
        boolean exists = userServiceImpl.exists(user.getId());
        assertTrue(exists, "User not found");
    }

    @ParameterizedTest
    @CsvSource({"1,,10,15,true", "2,,20,25,false",
            "3,,30,35,true", "4,,40,45,false",
            "5,,50,55,true", "6,,60,65,false"})
    public void create(@AggregateWith(UserAggregator.class) User user) {
        when(daoUser.getUserById(user.getId())).thenReturn(user);
        User userFromService = userServiceImpl.create(user.getId());
        assertNotNull(userFromService, "User not created");
        assertEquals(userFromService.getId(), user.getId(), "Returned the wrong user");
    }

    static class UserAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
                throws ArgumentsAggregationException {
            return new User(accessor.getLong(0),
                    accessor.get(1, Title.class),
                    accessor.getInteger(2),
                    accessor.getInteger(3),
                    accessor.getBoolean(4));
        }
    }

}
