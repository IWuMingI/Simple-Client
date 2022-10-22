package cn.wuming.simple.module.modules.render;

import cn.wuming.simple.Simple;
import cn.wuming.simple.module.Module;
import cn.wuming.simple.module.ModuleCategory;
import cn.wuming.simple.module.ModuleInfo;
import org.lwjgl.input.Keyboard;

/**
 * Copyright © 2015 - 2017 | CCBlueX | All rights reserved.
 * <p>
 * Simple - By CCBlueX(Marco)
 */
@ModuleInfo(moduleName = "ClickGUI", moduleDescription = "", moduleCateogry = ModuleCategory.RENDER, canEnable = false, defaultKey = Keyboard.KEY_RSHIFT)
public class ClickGUI extends Module {

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Simple.INSTANCE.clickGUI);
    }
}