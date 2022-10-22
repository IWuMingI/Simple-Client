package cn.wuming.simple.command;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@SideOnly(Side.CLIENT)
public abstract class Command {

    private String name;

    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(String[] strings);
}