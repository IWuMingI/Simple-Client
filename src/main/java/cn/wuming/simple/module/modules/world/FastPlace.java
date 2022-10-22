package cn.wuming.simple.module.modules.world;

import cn.wuming.simple.event.EventTarget;
import cn.wuming.simple.event.events.MotionUpdateEvent;
import cn.wuming.simple.module.Module;
import cn.wuming.simple.module.ModuleCategory;
import cn.wuming.simple.module.ModuleInfo;

import java.lang.reflect.Field;

/**
 * Copyright © 2015 - 2017 | CCBlueX | All rights reserved.
 * <p>
 * Simple - By CCBlueX(Marco)
 */
@ModuleInfo(moduleName = "FastPlace", moduleDescription = "", moduleCateogry = ModuleCategory.WORLD)
public class FastPlace extends Module {

    @EventTarget
    public void onUpdate(final MotionUpdateEvent event) {
       if(!getState())
           return;

        try{
            final Field field = mc.getClass().getField("rightClickDelayTimer");
            field.setAccessible(true);
            field.set(mc, 0);
        }catch(NoSuchFieldException e) {
            try{
                final Field field = mc.getClass().getField("field_71467_ac");
                field.setAccessible(true);
                field.set(mc, 0);
            }catch(NoSuchFieldException | IllegalAccessException e1) {
                e.printStackTrace();
            }
        }catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
