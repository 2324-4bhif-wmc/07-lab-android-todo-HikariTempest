package at.htl.todo.util.config;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    static final String TAG  = Config.class.getSimpleName();
    private static Properties properties;

    public static void load(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("application.properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            Log.i(TAG, "Error loading config", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
