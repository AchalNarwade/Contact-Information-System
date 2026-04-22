package ContactInfoSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.FileInputStream;

public class DBConnection {

    public static Connection getConnection(){
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("db.properties"); //read file from project,that stores database url,username & password
            props.load(fis);

            //fetch values using keys from the file
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            //connects java with mysql
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e){
            System.out.println("Database connection failed");
            e.printStackTrace();
            return null; //indicate connection failed
        }
    }
}