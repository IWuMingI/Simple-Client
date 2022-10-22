package cn.wuming.simple.injection.mixins;

import cn.wuming.simple.event.EventManager;
import cn.wuming.simple.event.events.MotionUpdateEvent;
import cn.wuming.simple.module.modules.world.FastPlace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@Mixin({EntityPlayerSP.class})
public class MixinEntityPlayerSP {

    @Inject(at = @At("HEAD"), method = "onUpdate")
    public void onUpdate(CallbackInfo callbackInfo) {
        if(new FastPlace().getState()) {
            if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null) {
                if (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                    //rightClickDelayTimer = 0;
                }
            }
            System.out.println("FastPlace Running!");
        }
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"))
    private void preMotion(CallbackInfo callbackInfo) {
        EventManager.callEvent(new MotionUpdateEvent(MotionUpdateEvent.State.PRE));
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"))
    private void postMotion(CallbackInfo callbackInfo) {
        EventManager.callEvent(new MotionUpdateEvent(MotionUpdateEvent.State.POST));
    }
}