package cn.wuming.simple;

import cn.wuming.simple.clickgui.ClickGUI;
import cn.wuming.simple.event.EventManager;
import cn.wuming.simple.filesystem.FileManager;
import cn.wuming.simple.command.CommandManager;
import cn.wuming.simple.module.ModuleManager;
import cn.wuming.simple.notifications.NotificationManager;
import cn.wuming.simple.utils.Update;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.Display.setTitle;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@SideOnly(Side.CLIENT)
public class Simple {
    public static Simple INSTANCE;
    public static final String CLIENT_INITIALS;
    public static final String CLIENT_NAME = "Simple";
    public static final int CLIENT_VERSION = 1;
    public static final String CLIENT_VERSION_DEV = "-Dev " + CLIENT_VERSION;
    public static final String CLIENT_AUTHOR = "WuMing";
    public static final NotificationManager notificationManager = new NotificationManager();
    public final Logger logger = LogManager.getLogger("Simple");
    public final ModuleManager moduleManager = new ModuleManager();
    public final EventManager eventManager = new EventManager();
    public final CommandManager commandManager = new CommandManager();
    public final FileManager fileManager = new FileManager();
    public ClickGUI clickGUI;

    static {
        List<Character> chars = new ArrayList<>();
        for (char c : CLIENT_NAME.toCharArray())
            if (Character.toUpperCase(c) == c) chars.add(c);
        char[] c = new char[chars.size()];

        for (int i = 0; i < chars.size(); i++) {
            c[i] = chars.get(i);
        }
        CLIENT_INITIALS = new String(c);
    }

    public Simple() {
        INSTANCE = this;
    }

    public void Creat(int version) {
        System.out.println(Update.getJson());
        if (version > CLIENT_VERSION) {
            setTitle(String.format("发现新版本 b%s,请前往 %s 请下载新版", version, Update.getUrl()));
        } else if (version < CLIENT_VERSION) {
            setTitle(String.format("你使用的不是正版,可能存在风险,请前往:%s下载新版", Update.getUrl()));
        } else {
            setTitle(CLIENT_NAME + " | " + "b" + CLIENT_VERSION + " | " + "By " + CLIENT_AUTHOR + " Loading...");
        }
    }

    public void startClient() {
        logger.info(String.format("Starting %s b%d by %s", CLIENT_NAME, CLIENT_VERSION, CLIENT_AUTHOR));
        logger.info("Simple " + CLIENT_VERSION + " based on Simple by WuMing (Copyright 2022)");

        setTitle(CLIENT_NAME + " | " + "b" + CLIENT_VERSION + " | " + "By " + CLIENT_AUTHOR);

        commandManager.registerCommands();
        moduleManager.registerModules();
        clickGUI = new ClickGUI();
        eventManager.registerListener(moduleManager);

        try{
            fileManager.loadModules();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void stopClient() {
        try{
            fileManager.saveModules();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}