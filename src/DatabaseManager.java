import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by timbauer on 12/7/15.
 */
public class DatabaseManager {
    private static DatabaseManager databaseManager = null;
    private static Connection conn = null;
    private static int game_iD = -1;


    private DatabaseManager(){
        //singleton
    }

    public static DatabaseManager getDatabaseManager(){
        if(databaseManager == null){
            try {
                databaseManager = new DatabaseManager();
                getConnection();
                createDefaultTables();
            }catch (Exception e){
                System.out.println(e);
            }
        }



        return databaseManager;
    }

    private static void getConnection() throws Exception{
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/lol_analyst_tools";
        String username = "root";
        String password = "hopeti27";
        Class.forName(driver);

        conn = DriverManager.getConnection(url, username, password);
        System.out.println("Connected");
    }


    public static void createDefaultTables()throws Exception{
        try {
            PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS teamTable(teamName VARCHAR(255)" +
                    " NOT NULL PRIMARY KEY UNIQUE , region VARCHAR(255))");
            create.executeUpdate();
        }catch (Exception e){System.out.println(e);}

        try {
            PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS gamesTable (game_Id INT NOT NULL PRIMARY KEY, " +
                    "teamName VARCHAR(255) NOT NULL, game_date DATE, patch DECIMAL)");

            create.executeUpdate();

        }catch (Exception e1){System.out.println(e1);}
    }

    public static String createNewGameTable(String teamName, int game_iD) throws Exception {
        String teamNameNoWhiteSpace = teamName.trim().replaceAll(" ", "_");
        String query1 = "CREATE TABLE IF NOT EXISTS ";
        String gameName = teamNameNoWhiteSpace + "_" + game_iD;
        String query2 = query1 + gameName + " ";
        String fullQueryText = query2 + "(event VARCHAR(255)NOT NULL , ingame_time_milliseconds INT NOT NULL, xCoord INT, yCoord INT, " +
                "fk_game_Id INT NOT NULL, FOREIGN KEY (fk_game_Id) REFERENCES gamesTable(game_Id))";

        PreparedStatement create = conn.prepareStatement(fullQueryText);
        create.executeUpdate();

        return gameName;
    }

    public static boolean checkIfTeamIsEntered(String teamName)throws Exception{

        String query = "SELECT count(*) from teamTable where teamName = ?";

        PreparedStatement queryChecker = conn.prepareStatement(query);
        queryChecker.setString(1, teamName);
        ResultSet results = queryChecker.executeQuery();
        results.next();
        int numResults = results.getInt(1);
        System.out.println(numResults);
        if(numResults > 0){
            System.out.println("Team is already in our database. :)");
            return true;
        }else return false;
    }

    public static void addNewTeam(String teamName, String region) throws Exception{
        String query = "INSERT INTO teamTable (teamName, region) VALUES (?, ?)";
        PreparedStatement insert = conn.prepareStatement(query);
        insert.setString(1, teamName);
        insert.setString(2, region);
        insert.executeUpdate();
    }

    private static int randomNumber(){
        return (int)(Math.random()*10000 + 1);
    }

    public static String addGameToGamesTable(String teamName) throws Exception{
        int numResults = 0;

        Boolean idInUse = true;

        do {
            game_iD = randomNumber();

            String toCheck = "SELECT count(*) FROM gamesTable  WHERE game_Id = ?";

            PreparedStatement queryChecker = conn.prepareStatement(toCheck);
            queryChecker.setInt(1, game_iD);
            ResultSet results = queryChecker.executeQuery();
            results.next();
            numResults = results.getInt(1);
            System.out.println(numResults);
            if(numResults == 0){
                idInUse = false;
            }
        }while(idInUse);

        String toInsert = "INSERT INTO gamesTable (game_Id, teamName) VALUES (?, ?)";
        PreparedStatement insert = conn.prepareStatement(toInsert);
        insert.setInt(1, game_iD);
        insert.setString(2, teamName);
        insert.executeUpdate();


        String gameName = createNewGameTable(teamName, game_iD);
        return gameName;
    }

    public static void addEntryToCurrentGameTable(String tableName, String event, long time,
                                                  int xCoord, int yCoord) throws Exception{
        String tableNameNoWhiteSpace = tableName.trim().replaceAll(" ", "_");
        String query1 = "INSERT INTO ";
        String toInsert = query1 + tableNameNoWhiteSpace + " (event, ingame_time_milliseconds, xCoord, yCoord, fk_game_Id) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement insert = conn.prepareStatement(toInsert);
        insert.setString(1, event);
        insert.setLong(2, time);
        insert.setInt(3, xCoord);
        insert.setInt(4, yCoord);
        insert.setInt(5, game_iD);

        insert.executeUpdate();
    }

    public static ArrayList<String> getListOfRecordedGames() throws Exception{
        ArrayList<String> listOfGames = new ArrayList<String> ();
        int gameId;
        int i = 0;

        PreparedStatement query = conn.prepareStatement("SELECT * FROM gamesTable");
        ResultSet results = query.executeQuery();

        while(results.next()){
            gameId = results.getInt(1);
            listOfGames.add((results.getString(2) + "_" + gameId));
            String temp = results.getString(2) + "_" + gameId;
            System.out.println(temp);
        }
        return listOfGames;
    }

    public static ArrayList<Object[]> getGameData(String gameTableName) throws Exception{
        ArrayList<Object[]> gameData = new ArrayList<Object[]>();

        String event;
        long time;
        int xCoord;
        int yCoord;
        int foreignKey;

        String tableNameNoWhiteSpace = gameTableName.trim().replaceAll(" ", "_");
        String toInsert = "SELECT * FROM " + tableNameNoWhiteSpace;
        PreparedStatement query = conn.prepareStatement(toInsert);
        ResultSet results = query.executeQuery();

        while (results.next()){
            Object[] currentEvent = new Object[5];
            currentEvent[0] = results.getString(1);
            currentEvent[1] = (Long)results.getLong(2);
            currentEvent[2] = (Integer)results.getInt(3);
            currentEvent[3] = (Integer)results.getInt(4);
            currentEvent[4] = (Integer)results.getInt(5);

            gameData.add(currentEvent);
        }

        return gameData;
    }
}
