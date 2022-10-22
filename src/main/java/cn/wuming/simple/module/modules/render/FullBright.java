package cn.wuming.simple.module.modules.render;

import cn.wuming.simple.event.EventTarget;
import cn.wuming.simple.event.events.Render2DEvent;
import cn.wuming.simple.module.Module;
import cn.wuming.simple.module.ModuleCategory;
import cn.wuming.simple.module.ModuleInfo;

/**
 * @author WuMing
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMing | All rights reserved.
 * @project SimpleClient
 */
@ModuleInfo(moduleName = "FullBright", moduleDescription = "FullBright", moduleCateogry = ModuleCategory.RENDER)
public class FullBright extends Module {

    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 300;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
        super.onDisable();
    }
}
