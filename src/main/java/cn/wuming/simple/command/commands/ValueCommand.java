package cn.wuming.simple.command.commands;

import cn.wuming.simple.command.Command;
import cn.wuming.simple.module.Module;
import cn.wuming.simple.module.ModuleManager;
import cn.wuming.simple.utils.ChatUtils;
import cn.wuming.simple.valuesystem.Value;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
public class ValueCommand extends Command {

    public ValueCommand() {
        super("val");
    }

    @Override
    public void execute(String[] strings) {
        if(strings.length > 3) {
            final Module module = ModuleManager.getModule(strings[1]);

            if(module == null) {
                ChatUtils.displayMessage("§c§lError: §r§aThe entered module not exist.");
                return;
            }

            final Value value = module.getValue(strings[2]);

            if(value == null) {
                ChatUtils.displayMessage("§c§lError: §r§aThe entered value not exist.");
                return;
            }

            if(value.getObject() instanceof Float) {
                final float newValue = Float.parseFloat(strings[3]);
                value.setObject(newValue);
                ChatUtils.displayMessage("§cThe value of §a§l" + module.getModuleName() + " §8(§a§l" + value.getName() + ") §c was set to §a§l" + newValue + "§c.");
            }
            return;
        }

        ChatUtils.displayMessage("§2Usage: §b.val <module> <valuename> <new_value>");
    }
}