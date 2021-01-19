package rankService;

import com.bot.ranksystem_20.dao.DaoTitle;
import com.bot.ranksystem_20.model.Title;
import com.bot.ranksystem_20.model.User;
import com.bot.ranksystem_20.service.RankService;
import com.bot.ranksystem_20.service.RankServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RankServiceTest {

    @Mock
    private DaoTitle daoTitle;

    @InjectMocks
    private RankServiceImpl rankServiceImpl;


    @ParameterizedTest
    @CsvSource({
            "1,,1,1,true",
            "2,,2,2,true",
            "3,,3,3,true",
            "4,,4,4,true"})
    void getMaxAvailableRank(@AggregateWith(UserAggregator.class) User user) throws Exception {

        when(daoTitle.getTitles()).thenReturn(getTitles());
        Title maxAvailableRank = rankServiceImpl.getMaxAvailableRank(user);
        user.setTitle(maxAvailableRank);

        assertNotNull(maxAvailableRank, "Rank is null");
        assertEquals(user.getTitle().getId(), maxAvailableRank.getId(), "Ranks is not equals");
        assertEquals(user.getTitle().getPoints(), maxAvailableRank.getPoints(), "User rank not equals max available rank");

    }

    static List<Title> getTitles() {
        return List.of(
                Title.builder().id(1L).name("rank_1").points(1).build(),
                Title.builder().id(2L).name("rank_2").points(2).build(),
                Title.builder().id(3L).name("rank_3").points(3).build(),
                Title.builder().id(4L).name("rank_4").points(4).build());
    }


    static class UserAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
                throws ArgumentsAggregationException {
            return new User(
                    accessor.getLong(0),
                    accessor.get(1, Title.class),
                    accessor.getInteger(2),
                    accessor.getInteger(3),
                    accessor.getBoolean(4));
        }
    }


    @Test
    void assignTitleToUser() {

    }
}