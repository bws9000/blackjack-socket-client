package info.bws9000.blackjack;

import java.io.FileInputStream;
import java.util.Properties;

public final class Config {

    private static Properties properties;

    private static Properties getProperties() {
        if(properties == null) {
            try {
                properties = new Properties();
                FileInputStream in = new FileInputStream("config.properties");
                properties.load(in);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //
        }
        return properties;
    }

    public static String getRemoteSocketURI(){
        return getProperties().getProperty("REMOTE_DEV_URI");
    }

    public static String getLocalSocketURI(){
        return getProperties().getProperty("LOCAL_DEV_URI");
    }
}
