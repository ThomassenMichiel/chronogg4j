package chronogg;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Main {
    
    public static void main(String[] args) {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        checkIfTokenIsValid(args, propertiesLoader);
        triggerCoin(propertiesLoader);
    }
    
    private static void checkIfTokenIsValid(String[] args, PropertiesLoader propertiesLoader) {
        String jwtToken = propertiesLoader.getString("authorization");
        if(Objects.isNull(jwtToken) || jwtToken.isEmpty()) {
            if (args.length == 0 || args[0].isEmpty() || !args[0].matches("^JWT [A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$")) {
                System.out.println("Please add the JWT token after the jar command or add it in application.properties");
                System.exit(0);
            }
            propertiesLoader.addProperty("authorization",args[0]);
        }
    }
    
    private static void triggerCoin(PropertiesLoader propertiesLoader)  {
        try {
            URL url = new URL(propertiesLoader.getString("postUrl"));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("authorization",propertiesLoader.getString("authorization"));
            log(con.getResponseCode());
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void log(int code) {
        String dateTime = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) + "] ";
        switch (code) {
            case 200: dateTime += "Successfully claimed"; break;
            case 401: dateTime += "Unauthorized (JWT token invalid or expired)"; break;
            case 420: dateTime += "Already claimed"; break;
        }
        System.out.println(dateTime);
    }
}
