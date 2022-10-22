package cn.wuming.simple.module.modules.movement;

import cn.wuming.simple.event.EventTarget;
import cn.wuming.simple.event.events.MotionUpdateEvent;
import cn.wuming.simple.module.Module;
import cn.wuming.simple.module.ModuleInfo;
import cn.wuming.simple.module.ModuleCategory;

@ModuleInfo(moduleName = "Sprint", moduleCateogry = ModuleCategory.MOVEMENT, moduleDescription = "Forward only")
public class Sprint extends Module {

    @EventTarget
    public void onMotion(MotionUpdateEvent event) {
        if(!getState())
            return;
        mc.thePlayer.setSprinting(true);
    }
}
