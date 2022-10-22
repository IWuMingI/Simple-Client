package cn.wuming.simple.command.commands;

import cn.wuming.simple.module.Module;
import cn.wuming.simple.module.ModuleManager;
import cn.wuming.simple.utils.ChatUtils;
import cn.wuming.simple.command.Command;

/**
 * Copyright © 2015 - 2017 | CCBlueX | All rights reserved.
 * <p>
 * Simple - By CCBlueX(Marco)
 */
public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("t");
    }

    @Override
    public void execute(String[] strings) {
        if(strings.length > 1) {
            final Module module = ModuleManager.getModule(strings[1]);

            if(module == null) {
                ChatUtils.displayMessage("§c§lError: §r§aThe entered module not exist.");
                return;
            }

            module.setState(!module.getState());
            ChatUtils.displayMessage("§cToggled module.");
            return;
        }

        ChatUtils.displayMessage("§2Usage: §b.t <module>");
    }
}
