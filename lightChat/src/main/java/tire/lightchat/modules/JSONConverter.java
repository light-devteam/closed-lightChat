package tire.lightchat.modules;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

import tire.lightchat.main.LightChat;

public class JSONConverter {

    private LightChat plugin;
    public JSONConverter(LightChat plugin) {
        this.plugin = plugin;
    }

    public String converter(String string) {

        String format = plugin.getConfig().getString("config.formatSymbol");
        String placeholder = plugin.getConfig().getString("config.placeholder");

        String _format = format.replaceAll("(\\p{Punct})", "\\\\$1");
        String _placeholder = placeholder.replaceAll("(\\p{Punct})", "\\\\$1");

        final String regex = "(?<=^|[^\\\\])" + _format + _placeholder.split("placeholder")[0] + "((\\\"underlined\\\":(true|false))|(\\\"bold\\\":(true|false))|(\\\"strikethrough\\\":(true|false))|(\\\"italic\\\":(true|false))|(\\\"obfuscated\\\":(true|false))|#([A-Fa-f0-9]{3}){1,2})" + _placeholder.split("placeholder")[1] + "|(?<=^|[^\\\\])" + _placeholder.split("placeholder")[0] + "json[ ]+([\\s\\S]+?)[ ]+" + _placeholder.split("placeholder")[1] + "|[\\s\\S]";

        ArrayList<String> array = new ArrayList<>();
        String str = "";
        String colors[] = {"#000000", "#0000AA", "#00AA00", "#00AAAA", "#AA0000", "#AA00AA", "#FFAA00", "#AAAAAA", "#555555", "#5555FF", "#55FF55", "#55FFFF", "#FF5555", "#FF55FF", "#FFFF55", "#FFFFFF", "\"obfuscated\":true", "\"bold\":true", "\"strikethrough\":true", "\"underlined\":true", "\"italic\":true", "json {\"bold\": false, \"underlined\":false, \"strikethrough\":false, \"italic\":false, \"obfuscated\":false, \"color\":\"white\", \"text\":\"\"} "};
        String codes[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "k", "l", "m", "n", "o", "r"};
        for(int i = 0; i<codes.length; i++) {
            if(codes[i] == "r") {
                string = string.replaceAll("(?<=^|[^\\\\])" + _format + codes[i], placeholder.replace("placeholder", colors[i]));
            } else {
                string = string.replaceAll("(?<=^|[^\\\\])" + _format + codes[i], _format + placeholder.replace("placeholder", colors[i]));
            }
        }
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            if (matcher.group(0).length() == 1) {
                str = str + matcher.group(0);
            } else {
                if (str.length() != 0) {
                    array.add(str);
                }
                array.add(matcher.group(0));
                str = "";
            }
        }
        int startl = format.length() + placeholder.split("placeholder")[0].length();
        int endl = placeholder.split("placeholder")[1].length();
        array.add(str);
        for (int i = 0; i < array.size(); i++) {
            String item = array.get(i);
            if (item.startsWith(format + placeholder.split("placeholder")[0])) {
                item.replaceAll("\\s", "");
                if (item.startsWith(format + placeholder.split("placeholder")[0] + "#")) {
                    array.set(i, "{\"color\":\"" + item.substring(startl, item.length() - endl) + "\"}");
                } else {
                    array.set(i, "{" + item.substring(startl, item.length() - endl) + "}");
                }
            } else if (item.startsWith(placeholder.split("placeholder")[0] + "json ")) {
                array.set(i, item.substring(startl + 4, item.length() - endl));
            } else {
                item = item.replaceAll("\\\\(\\p{Punct})", "$1");
                item = item.replaceAll("\\\\(" + _format + "[0-9a-fklmnor]{1})", "$1");
                item = item.replaceAll("\\\\(" + _format + _placeholder.split("placeholder")[0] + "#([A-Fa-f0-9]{3}){1,2}" + _placeholder.split("placeholder")[1] + ")", "$1");
                item = item.replaceAll("\\\\(" + _placeholder.split("placeholder")[0] + "json[ ]+([\\s\\S]+?)[ ]+" + _placeholder.split("placeholder")[1] + ")", "$1");
                array.set(i, "{\"text\":\"" + item.replaceAll("(\")", "\\\\$1") + "\"}");
            }
        }
        String jsonString = array.toString();
        String[] vars = {"color", "bold", "obfuscated", "italic", "underlined", "strikethrough"};
        JSONArray arrayJ = new JSONArray(jsonString);
        for (int j = 0; j<arrayJ.length(); j++) {
            JSONObject object = arrayJ.getJSONObject(j);
            for(int k = 0; k<vars.length; k++) {
                if (j == 0) {
                    if(object.opt(vars[k]) == null) {
                        if(vars[k] != "color") { object.put(vars[k], false); }
                        else { object.put(vars[k], "#FFFFFF"); }
                    }
                } else {
                    if(object.opt(vars[k]) == null) {
                        object.put(vars[k], arrayJ.getJSONObject(j-1).opt(vars[k]));
                    }
                }
            }
        }
        int l = arrayJ.length();
        for (int j = 0; j<l; j++) {
            if(arrayJ.getJSONObject(j).optString("text") == null || (arrayJ.getJSONObject(j).optString("text")).length() == 0) {
                arrayJ.remove(j);
                j--;
                l--;
            }
        }
        return(arrayJ.toString());
    }
}
