package cn.wuming.simple.module.modules.movement;

import cn.wuming.simple.event.EventTarget;
import cn.wuming.simple.event.events.MotionUpdateEvent;
import cn.wuming.simple.module.Module;
import cn.wuming.simple.module.ModuleInfo;
import cn.wuming.simple.module.ModuleCategory;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@ModuleInfo(moduleName = "Speed", moduleDescription = "Simple YPort Speed", moduleCateogry = ModuleCategory.MOVEMENT)
public class Speed extends Module {

    @EventTarget
    public void onMotion(MotionUpdateEvent event) {
        if(getState() && event.getState() == MotionUpdateEvent.State.POST) {
            if(mc.thePlayer.onGround)
                mc.thePlayer.jump();
            else
                mc.thePlayer.motionY = -1;
        }
    }
}