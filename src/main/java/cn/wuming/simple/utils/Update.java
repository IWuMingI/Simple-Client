package cn.wuming.simple.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @author WuMing
 */
public class Update {
    public static String readString(String link) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = ((HttpURLConnection) urlConnection);
            }
            if (connection != null) {
                IOUtils.readLines(connection.getInputStream(), StandardCharsets.UTF_8).forEach(line -> stringBuffer.append(line).append("\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public static String getUrl() {
        String jsonUrl = "";
        for (JsonElement versions : new Gson().fromJson(readString(WuMing.URL), JsonObject.class).get(WuMing.PROJECT_NAME).getAsJsonArray()) {
            if (versions.getAsJsonObject().get("id").getAsString().equals(WuMing.MINECRAFT_VERSION)) {
                jsonUrl = versions.getAsJsonObject().get("url").getAsString();
            }
        }
        return jsonUrl;
    }

    public static int getVersion() {
        int jsonVersion = 1;
        for (JsonElement versions : new Gson().fromJson(readString(WuMing.URL), JsonObject.class).get(WuMing.PROJECT_NAME).getAsJsonArray()) {
            if (versions.getAsJsonObject().get("id").getAsString().equals(WuMing.MINECRAFT_VERSION)) {
                jsonVersion = versions.getAsJsonObject().get("version").getAsInt();
            }
        }
        return jsonVersion;
    }

    public static String getJson() {
        String jsonUrl = "";
        int version = 1;
        for (JsonElement element : new Gson().fromJson(readString(WuMing.URL), JsonObject.class).get(WuMing.PROJECT_NAME).getAsJsonArray()) {
            if (element.getAsJsonObject().get("id").getAsString().equals(WuMing.MINECRAFT_VERSION)) {
                jsonUrl = element.getAsJsonObject().get("url").getAsString();
                version = element.getAsJsonObject().get("version").getAsInt();
            }
        }
        return jsonUrl + " | " + version;
    }
}
