package org.opencommunity.envel.openantiredstonelag.listener;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.opencommunity.envel.openantiredstonelag.OpenAntiRedstoneLag;
import org.opencommunity.envel.openantiredstonelag.utils.RedstoneManager;

import java.util.HashMap;

public class OpenAntiRedstoneLagCommand implements CommandExecutor {
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
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
                            list = Integer.parseInt(args[2]) - 1;
                        } catch (NumberFormatException e) {
                            sender.sendMessage(OpenAntiRedstoneLag.name + "§cPlease enter a valid number for the list.");
                            return true;
                        }
                    } else {
                        try {
                            page = Integer.parseInt(args[1]) - 1;
                        } catch (NumberFormatException e2) {
                            sender.sendMessage(OpenAntiRedstoneLag.name + "§cPlease enter a valid number for the page.");
                            return true;
                        }
                    }
                }
                if (!RedstoneManager.redstoneChunks.containsKey(RedstoneManager.lastTime - (list * 60L))) {
                    sender.sendMessage(OpenAntiRedstoneLag.name + "§cNo data is available.");
                    return true;
                } else if (RedstoneManager.redstoneChunks.get(RedstoneManager.lastTime - (list * 60L)).size() == 0) {
                    sender.sendMessage(OpenAntiRedstoneLag.name + "§cThere is no data at the moment, please wait up to 1 minute.");
                    return true;
                } else if (RedstoneManager.redstoneChunks.get(RedstoneManager.lastTime - ((long) list * OpenAntiRedstoneLag.config.getInterval())).containsKey(Bukkit.getWorld(world))) {
                    HashMap<Chunk, Integer> listMap = RedstoneManager.redstoneChunks.get(RedstoneManager.lastTime - ((long) list * OpenAntiRedstoneLag.config.getInterval())).get(Bukkit.getWorld(world));
                    int maxPage = (int) Math.ceil(((double) (listMap.size() - 1)) / (((double) OpenAntiRedstoneLag.config.getPageItems())));
                    if (listMap.size() > page * OpenAntiRedstoneLag.config.getPageItems()) {
                        sender.sendMessage("");
                        sender.sendMessage(OpenAntiRedstoneLag.name);
                        sender.sendMessage("§cRedstone-Changes/Minute §8- §6Chunk-Position");
                        sender.sendMessage("");
                        for (Chunk chunk : listMap.keySet()) {
                            if (list > (page * OpenAntiRedstoneLag.config.getPageItems()) + OpenAntiRedstoneLag.config.getPageItems()) {
                                break;
                            }
                            if (list >= page * OpenAntiRedstoneLag.config.getPageItems()) {
                                sender.sendMessage("§c" + listMap.get(chunk) + " §8- §7X§8: §6" + (chunk.getX() * 16) + " §7Z§8: §6" + (chunk.getZ() * 16));
                            }
                            list++;
                        }
                        sender.sendMessage("");
                        Component nextm = Component.text("§7Page " + (page + 1) + "/" + maxPage + " §lNext page")
                                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/rlag 2"));
                        BukkitAudiences.create(OpenAntiRedstoneLag.getPlugin()).sender(sender).sendMessage(nextm);
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
