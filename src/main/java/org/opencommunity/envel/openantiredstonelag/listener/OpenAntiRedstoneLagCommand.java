package org.opencommunity.envel.openantiredstonelag.listener;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.opencommunity.envel.openantiredstonelag.OpenAntiRedstoneLag;
import org.opencommunity.envel.openantiredstonelag.utils.RedstoneManager;

import java.util.HashMap;
import java.util.Iterator;

public class OpenAntiRedstoneLagCommand implements CommandExecutor {
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rlag.use")) {
            sender.sendMessage(OpenAntiRedstoneLag.name + "§cYou do not have the permission to use that.");
            return true;
        } else if (args.length > 0) {
            String world = args[0];
            if (Bukkit.getWorld(world) != null) {
                int list = 0;
                int page = 0;
                if (args.length > 1) {
                    if (args.length > 2) {
                        try {
                            list = Integer.valueOf(args[2]).intValue() - 1;
                        } catch (NumberFormatException e) {
                            sender.sendMessage(OpenAntiRedstoneLag.name + "§cPlease enter a valid number for the list.");
                            return true;
                        }
                    } else {
                        try {
                            page = Integer.valueOf(args[1]).intValue() - 1;
                        } catch (NumberFormatException e2) {
                            sender.sendMessage(OpenAntiRedstoneLag.name + "§cPlease enter a valid number for the page.");
                            return true;
                        }
                    }
                }
                if (!RedstoneManager.redstoneChunks.containsKey(Long.valueOf(RedstoneManager.lastTime - ((long) (list * 60))))) {
                    sender.sendMessage(OpenAntiRedstoneLag.name + "§cNo data is available.");
                    return true;
                } else if (RedstoneManager.redstoneChunks.get(Long.valueOf(RedstoneManager.lastTime - ((long) (list * 60)))).size() <= 0) {
                    sender.sendMessage(OpenAntiRedstoneLag.name + "§cThere is no data at the moment, please wait up to 1 minute.");
                    return true;
                } else if (RedstoneManager.redstoneChunks.get(Long.valueOf(RedstoneManager.lastTime - ((long) (list * OpenAntiRedstoneLag.config.getInterval())))).containsKey(Bukkit.getWorld(world))) {
                    HashMap<Chunk, Integer> listMap = RedstoneManager.redstoneChunks.get(Long.valueOf(RedstoneManager.lastTime - ((long) (list * OpenAntiRedstoneLag.config.getInterval())))).get(Bukkit.getWorld(world));
                    int maxPage = (int) Math.ceil(((double) (listMap.size() - 1)) / (((double) OpenAntiRedstoneLag.config.getPageItems()) * 1.0d));
                    if (listMap.size() > page * OpenAntiRedstoneLag.config.getPageItems()) {
                        sender.sendMessage("");
                        sender.sendMessage(OpenAntiRedstoneLag.name);
                        sender.sendMessage("§cRedstone-Changes/Minute §8- §6Chunk-Position");
                        sender.sendMessage("");
                        Iterator<Chunk> it = listMap.keySet().iterator();
                        while (it.hasNext()) {
                            Chunk chunk = it.next();
                            if (list > (page * OpenAntiRedstoneLag.config.getPageItems()) + OpenAntiRedstoneLag.config.getPageItems()) {
                                break;
                            }
                            if (list >= page * OpenAntiRedstoneLag.config.getPageItems()) {
                                sender.sendMessage("§c" + listMap.get(chunk) + " §8- §7X§8: §6" + (chunk.getX() * 16) + " §7Z§8: §6" + (chunk.getZ() * 16));
                            }
                            list++;
                        }
                        sender.sendMessage("");
                        TextComponent nextm = new TextComponent("§7Page " + (page + 1) + "/" + maxPage + " §lNext page");
                        nextm.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rlag 2"));
                        sender.sendMessage(nextm);
                        sender.sendMessage("");
                        return true;
                    }
                    sender.sendMessage(OpenAntiRedstoneLag.name + "§cThe list has not that much entries.");
                    return true;
                } else {
                    sender.sendMessage(OpenAntiRedstoneLag.name + "§cNo data is available.");
                    return true;
                }
            } else {
                sender.sendMessage(OpenAntiRedstoneLag.name + "§cThe given world is not available.");
                return true;
            }
        } else {
            sender.sendMessage(OpenAntiRedstoneLag.name + "§cYou need to at least enter a world. §7Please enter §c/rl <World> [Page] [List]");
            return true;
        }
    }
}
