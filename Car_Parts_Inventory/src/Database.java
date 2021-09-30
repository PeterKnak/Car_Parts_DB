import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

public class Database {

    //Declare variables specific to the Database used. I created my database in mySQL under localhost. The username and password may also be different depending on the user.
    final static String url = "jdbc:mysql://localhost:3306/car_part_inventory";
    final static String userName = "root";
    final static String password = "lucky10";

    //Three similar functions are defined, each connect to the database and insert n entry into a different table
    public static void InsertModel(int id, String name, String year) throws Exception{
        try{
            //Establish a connection
            Connection connection = DriverManager.getConnection(url, userName, password);
            //Set up and then execute an SQL insertion
            PreparedStatement insertion = connection.prepareStatement("INSERT IGNORE INTO models (model_id, model_name, model_year) VALUES ('" + id + "', '" + name + "', '" + year + "')");
            insertion.executeUpdate();

        }   catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void InsertPart(int id, String name, String type, int model_id) throws Exception{
        try{

            //Establish a connection
            Connection connection = DriverManager.getConnection(url, userName, password);
            //Set up and then execute an SQL insertion
            PreparedStatement insertion = connection.prepareStatement("INSERT IGNORE INTO parts (id, part_name, type, model_id) VALUES ('" + id + "', '" + name + "', '" + type + "', '" + model_id + "')");
            insertion.executeUpdate();

        }   catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void InsertFeature(int id, String name) throws Exception{
        try{

            //Establish a connection
            Connection connection = DriverManager.getConnection(url, userName, password);
            //Set up and then execute an SQL insertion
            PreparedStatement insertion = connection.prepareStatement("INSERT IGNORE INTO features (id, feature_name) VALUES ('" + id + "', '" + name + "')");
            insertion.executeUpdate();

        }   catch (Exception e){
            e.printStackTrace();
        }
    }

    //The function PrintTable() is not strictly necessary but is useful to see the populated data from the database
    public static void PrintTable(String tableName) throws Exception{
        try{

            Connection connection = DriverManager.getConnection(url, userName, password);

            //Query the database for all entries in a table and store that in a result set
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from " + tableName);

            //Go through each entry in the result set and print the data from each column
            while (resultSet.next()){
                if(tableName == "models"){
                    System.out.print(resultSet.getString("model_id") + " ");
                    System.out.print(resultSet.getString("model_name") + " ");
                    System.out.println(resultSet.getString("model_year"));
                }
                if(tableName == "parts"){
                    System.out.print(resultSet.getString("id") + " ");
                    System.out.print(resultSet.getString("part_name") + " ");
                    System.out.print(resultSet.getString("type") + " ");
                    System.out.println(resultSet.getString("model_id"));
                }
                if(tableName == "features"){
                    System.out.print(resultSet.getString("id") + " ");
                    System.out.println(resultSet.getString("feature_name"));
                }
            }

            System.out.println("");

        }   catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String[] carModels = {"Alfa Romeo", "Aston Martin", "Bentley", "Ford", "Jaguar", "Jeep", "Nissan", "Skoda", "Suzuki", "Toyota"};
        String[] carParts = {"bonnet", "hinges", "springs", "bumper", "fender", "grille", "quarter panel", "radiator", "roof rack", "spoiler", "rims", "hubcap", "tyre", "door handle", "door latch",
                            "central locking", "window pane", "seat belt", "windshield", "windshield wiper", "ignition box", "gear stick", "ABS", "engine", "piston", "exhaust", "tank"};
        String[] features = {"leather seats", "sunroof", "heated seats", "dash-cam", "sat-nav", "bluetooth", "radio", "remote start"};
        String[] years = {"1969", "1976", "1981", "1985", "1990", "1999", "2002", "2005", "2010", "2013"};
        String[] types = {"small", "medium", "large"};

        for(int counter = 0; counter < carModels.length; counter++){
            InsertModel(counter+1, carModels[counter], years[counter]);
        }

        for(int counter = 0; counter < 100; counter++){
            String randomPart = carParts[ThreadLocalRandom.current().nextInt(0, carParts.length)];
            int randomModelID = ThreadLocalRandom.current().nextInt(0, 11);
            String randomType = types[ThreadLocalRandom.current().nextInt(0, types.length)];
            InsertPart(counter+1, randomPart, randomType,randomModelID);
        }

        for(int counter = 0; counter < 50; counter++){
            String randomFeature = features[ThreadLocalRandom.current().nextInt(0, features.length)];
            InsertFeature(counter+1, randomFeature);
        }

        PrintTable("models");
        PrintTable("parts");
        PrintTable("features");

    }

}
