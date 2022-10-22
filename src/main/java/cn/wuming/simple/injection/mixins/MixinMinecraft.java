package cn.wuming.simple.injection.mixins;

import cn.wuming.simple.Simple;
import cn.wuming.simple.event.EventManager;
import cn.wuming.simple.event.events.KeyEvent;
import cn.wuming.simple.module.modules.world.FastPlace;
import cn.wuming.simple.utils.Update;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
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
@Mixin(Minecraft.class)
@SideOnly(Side.CLIENT)
public class MixinMinecraft {

    @Shadow public GuiScreen currentScreen;
    @Shadow private int rightClickDelayTimer;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initMinecraft(CallbackInfo callbackInfo) {
        new Simple();
    }

    @Inject(at = @At(value = "INVOKE",target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", shift = At.Shift.AFTER), method = "createDisplay", remap = false)
    public void createDisplay(CallbackInfo callbackInfo) {
        Simple.INSTANCE.Creat(Update.getVersion());
    }

    @Inject(method = "startGame", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;ingameGUI:Lnet/minecraft/client/gui/GuiIngame;", shift = At.Shift.AFTER))
    private void startClient(CallbackInfo callbackInfo) {
        Simple.INSTANCE.startClient();
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    private void stopClient(CallbackInfo callbackInfo) {
        Simple.INSTANCE.stopClient();
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V", shift = At.Shift.AFTER))
    private void keyPress(CallbackInfo callbackInfo) {
        if(Keyboard.getEventKeyState() && currentScreen == null)
            EventManager.callEvent(new KeyEvent(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey()));
    }
}