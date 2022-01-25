package tire.modules;

public class replaceVarsNUnicode {
    public static String customReplace(String str, String PlayerName,
                                String MessageZero, String GlobalChatPrefix,
                                String LocalChatPrefix, String WorldChatPrefix) {
        str = str.replace("&", "\u00a7");
        str = str.replace("%sender%", PlayerName);
        str = str.replace("%MessagePrefix%", MessageZero);
        str = str.replace("%GlobalChatPrefix%", GlobalChatPrefix);
        str = str.replace("%LocalChatPrefix%", LocalChatPrefix);
        str = str.replace("%WorldChatPrefix%", WorldChatPrefix);
        return str;
    }
}
