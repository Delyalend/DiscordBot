package rankService;

import com.bot.ranksystem_20.dao.DaoTitle;
import com.bot.ranksystem_20.model.Title;
import com.bot.ranksystem_20.model.User;
import com.bot.ranksystem_20.service.RankService;
import com.bot.ranksystem_20.service.RankServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RankServiceTest {

    DaoTitle daoTitle = new DaoTitleTestImpl();
    RankService rankService = new RankServiceImpl(daoTitle);

    @Test
    @SneakyThrows
    void getMaxAvailableRank() {
        Title rank = new Title(0L,"maxAvailableRank",0);
        User user = new User(0L,rank,0,0,true);


        Title maxAvailableRank = rankService.getMaxAvailableRank(user);

        assertNotNull(maxAvailableRank,"Rank is null");
        assertEquals(rank.getId(), maxAvailableRank.getId(), "Ranks is not equals");
        assertEquals(user.getTitle().getPoints(),maxAvailableRank.getPoints(),"User rank not equals max available rank");

    }

    @Test
    void assignTitleToUser() {

    }
}