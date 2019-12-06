package cx.rain.mc.plugin.spigot.fantasticChineseNewYear.util;

import cx.rain.mc.plugin.spigot.fantasticChineseNewYear.FantasticChineseNewYearSpigot;
import cx.rain.mc.plugin.spigot.fantasticChineseNewYear.util.enumerate.Language;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class I18n {
    public static Language language = Language.zhcn;
    public static void loadMessages(String lang) {
        language = Language.valueOf(lang);
        internalLoad();
    }

    private static void internalLoad() {
        FantasticChineseNewYearSpigot instance = FantasticChineseNewYearSpigot.getInstance();
        Logger log = instance.getLog();
        if (!new File(instance.getDataFolder(), language.getFile()).exists()) {
            instance.saveResource(language.getFile(), false);
        }
        File messagesFile = new File(instance.getDataFolder(), language.getFile());
        instance.setMessages(YamlConfiguration.loadConfiguration(messagesFile));
        log.info(I18n.format("languages.set", I18n.format(language.getDescription())));
    }

    public static String format(String key, Object... args) {
        if (FantasticChineseNewYearSpigot.getInstance().getMessages() == null) {
            loadMessages(FantasticChineseNewYearSpigot.getInstance().getConf().getString("general.language"));
        }
        FileConfiguration messages = FantasticChineseNewYearSpigot.getInstance().getMessages();
        if (!messages.isSet(key)) {
            // Put the log message in last for prevent loop.
            File file = new File(FantasticChineseNewYearSpigot.getInstance().getDataFolder(), language.getFile());
            if (file.exists()) {
                file.delete();
            }
            FantasticChineseNewYearSpigot.getInstance().getLog().log(Level.WARNING, I18n.format("language.missing", key));
        }
        String format = messages.getString(key);
        return String.format(format, args);
    }
}