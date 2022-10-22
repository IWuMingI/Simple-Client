package cn.wuming.simple.injection.mixins;

import cn.wuming.simple.Simple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
@SideOnly(Side.CLIENT)
@Mixin(GuiScreen.class)
public class MixinGuiScreen {

    @Shadow public Minecraft mc;

    @Inject(method = "sendChatMessage(Ljava/lang/String;Z)V", at = @At("HEAD"), cancellable = true)
    private void chatMessage(String msg, boolean addToChat, CallbackInfo callbackInfo) {
        if(msg.startsWith(".")) {
            Simple.INSTANCE.commandManager.callCommand(msg);
            mc.ingameGUI.getChatGUI().addToSentMessages(msg);
            callbackInfo.cancel();
        }
    }
}