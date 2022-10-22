package cn.wuming.simple.module.modules.combat;

import cn.wuming.simple.event.EventTarget;
import cn.wuming.simple.event.events.MotionUpdateEvent;
import cn.wuming.simple.valuesystem.Value;
import cn.wuming.simple.module.Module;
import cn.wuming.simple.module.ModuleCategory;
import cn.wuming.simple.module.ModuleInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@ModuleInfo(moduleName = "KillAura", moduleDescription = "You need to know this.", moduleCateogry = ModuleCategory.COMBAT, defaultKey = Keyboard.KEY_R)
public class KillAura extends Module {

    private EntityLivingBase target;

    @EventTarget
    public void onMotion(MotionUpdateEvent event) {
        if(!getState())
            return;

        switch(event.getState()) {
            case PRE:
                Object[] objects = mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityLivingBase && entity != mc.thePlayer && ((EntityLivingBase) entity).getHealth() > 0F && entity.getDistanceToEntity(mc.thePlayer) <= 4.2f).sorted(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer))).toArray();

                if(objects.length > 0)
                    target = (EntityLivingBase) objects[0];

                // Your facing etc here then
                break;
            case POST:
                if(target == null)
                    return;

                mc.thePlayer.swingItem();
                mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

                target = null;
                break;
        }
    }
}
