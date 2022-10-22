package cn.wuming.simple;

import net.minecraft.client.Minecraft;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
public class Wrapper {

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    public static Minecraft getMinecraft() {
        return minecraft;
    }
}