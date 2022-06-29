package src.SQL;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
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
    public static String[][] getReviews(int wine_id) {

        try {
            Connection connection = ConnectDB.get();
            Statement statement = connection.createStatement();

            ResultSet resultSet;

            resultSet = statement.executeQuery("select w.title , cr.customer_name , cr.customer_review from CustomerReviews cr join Wines w on cr.wine_id = w.wine_id where w.wine_id = '" + wine_id + "'");

            List<String[]> result = new ArrayList<>();

            while (resultSet.next()) {
                String[] row = new String[]{resultSet.getString("title"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("customer_review")};

                result.add(row);
            }

            return result.toArray(String[][]::new);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addReview(String customerName , String review , Integer wine_id) {
        try {
            Connection connection = ConnectDB.get();
            Statement statement = connection.createStatement();

            String insert = "INSERT INTO CustomerReviews values(null ,'" + wine_id + "','" + review + "' , '" + OffsetDateTime.now().format(DateTimeFormatter.ofPattern("DD/MM/YYYY")) + "' , '" + customerName + "')";

            statement.execute(insert);

            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    }
