package rankService;

import com.bot.ranksystem_20.dao.DaoTitle;
import com.bot.ranksystem_20.model.Title;

import java.util.Collections;
import java.util.List;

public class DaoTitleTestImpl implements DaoTitle {
    @Override
    public List<Title> getTitles() {
        return Collections.singletonList(new Title(0L,"maxAvailableRank",0));
    }

    @Override
    public Title getTitleByTitleId(Long titleId) {
        return null;
    }

    @Override
    public Title getLastTitle() {
        return null;
    }

    @Override
    public void assignTitleToUser(Long userId, Title title) {

    }

    @Override
    public Title getNextTitleForUser(Long userId) {
        return null;
    }

    @Override
    public Title getCurrentTitleByUserId(Long userId) {
        return null;
    }
}
