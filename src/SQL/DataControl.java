package src.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DataControl {

    public static String[][] getWine() {

        try {
            Connection connection = ConnectDB.get();
            Statement statement = connection.createStatement();

            ResultSet resultSet;

            resultSet = statement.executeQuery("select wine_id, title , description , country , designation , country , variety from Wines where active = 1");
            List<String[]> result = new ArrayList<>();

            while (resultSet.next()) {
                String[] row = new String[]{resultSet.getString("wine_id"),
                        resultSet.getString("title"),
                        resultSet.getString("variety"),
                        resultSet.getString("description"),
                        resultSet.getString("designation"),
                        resultSet.getString("country")};

                result.add(row);
            }

            return result.toArray(String[][]::new);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
