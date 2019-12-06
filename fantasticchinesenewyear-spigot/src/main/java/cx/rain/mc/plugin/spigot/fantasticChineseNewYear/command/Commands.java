package cx.rain.mc.plugin.spigot.fantasticChineseNewYear.command;

import cx.rain.mc.plugin.spigot.fantasticChineseNewYear.command.completer.CompleterFantasticChineseNewYear;
import cx.rain.mc.plugin.spigot.fantasticChineseNewYear.command.completer.CompleterRedPacket;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

public class Commands {
    protected static PluginCommand redpacket;
    protected static PluginCommand redpacketExclusive;
    protected static PluginCommand redpacketNormal;
    protected static PluginCommand redpacketPassword;
    protected static PluginCommand redpacketRandom;
    protected static PluginCommand fantasticChineseNewYear;

    public Commands() {
        redpacket = Bukkit.getPluginCommand("redpacket");
        redpacket.setExecutor(new CommandRedPacket());
        redpacket.setTabCompleter(new CompleterRedPacket());

        redpacketExclusive = Bukkit.getPluginCommand("zhuanshuhongbao");
        redpacketExclusive.setExecutor(new CommandRedPacketExclusive());
        redpacketNormal = Bukkit.getPluginCommand("putonghongbao");
        redpacketNormal.setExecutor(new CommandRedPacketNormal());
        redpacketPassword = Bukkit.getPluginCommand("koulinghongbao");
        redpacketPassword.setExecutor(new CommandRedPacketPassword());
        redpacketRandom = Bukkit.getPluginCommand("pinshouqihongbao");
        redpacketRandom.setExecutor(new CommandRedPacketRandom());

        fantasticChineseNewYear = Bukkit.getPluginCommand("fantasticchinesenewyear");
        fantasticChineseNewYear.setExecutor(new CommandFantasticChineseNewYear());
        fantasticChineseNewYear.setTabCompleter(new CompleterFantasticChineseNewYear());
    }
}