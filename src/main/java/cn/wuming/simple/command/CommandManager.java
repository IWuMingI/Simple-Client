package cn.wuming.simple.command;

import cn.wuming.simple.command.commands.BindCommand;
import cn.wuming.simple.command.commands.ToggleCommand;
import cn.wuming.simple.command.commands.ValueCommand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@SideOnly(Side.CLIENT)
public class CommandManager {

    private final List<Command> commands = new ArrayList<>();

    public void registerCommands() {
        registerCommand(new BindCommand());
        registerCommand(new ValueCommand());
        registerCommand(new ToggleCommand());
    }

    public void registerCommand(final Command command) {
        commands.add(command);
    }

    public void callCommand(final String s) {
        final String[] strings = s.split(" ");
        commands.stream().filter(command -> strings[0].equalsIgnoreCase("." + command.getName())).forEach(command -> command.execute(strings));
    }
}