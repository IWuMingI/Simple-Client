package cn.wuming.simple.injection.mixins;

import cn.wuming.simple.event.EventManager;
import io.netty.channel.ChannelHandlerContext;
import cn.wuming.simple.event.events.PacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "channelRead0", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V"), cancellable = true)
    private void readPacket(ChannelHandlerContext p_channelRead0_1_, Packet p_channelRead0_2_, CallbackInfo callbackInfo) {
        final PacketEvent packetEvent = new PacketEvent(p_channelRead0_2_);
        EventManager.callEvent(packetEvent);

        if(packetEvent.isCancelled())
            callbackInfo.cancel();
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void sendPacket(Packet packetIn, CallbackInfo callbackInfo) {
        final PacketEvent packetEvent = new PacketEvent(packetIn);
        EventManager.callEvent(packetEvent);

        if(packetEvent.isCancelled())
            callbackInfo.cancel();
    }
}