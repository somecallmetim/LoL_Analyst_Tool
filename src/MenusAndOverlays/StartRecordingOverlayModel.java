package MenusAndOverlays;

import java.sql.Date;

/**
 * Created by timbauer on 2/24/16.
 */
public class StartRecordingOverlayModel extends  TransparentOverlyBaseClassModel{
    String team, teamRegion;
    int numOfGameInCurrentSeries;
    Date gameDate;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeamRegion() {
        return teamRegion;
    }

    public void setTeamRegion(String teamRegion) {
        this.teamRegion = teamRegion;
    }

    public int getNumOfGameInCurrentSeries() {
        return numOfGameInCurrentSeries;
    }

    public void setNumOfGameInCurrentSeries(int numOfGameInCurrentSeries) {
        this.numOfGameInCurrentSeries = numOfGameInCurrentSeries;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }
}
