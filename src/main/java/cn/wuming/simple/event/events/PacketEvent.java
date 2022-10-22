package cn.wuming.simple.event.events;

import cn.wuming.simple.event.CancellableEvent;
import net.minecraft.network.Packet;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
public class PacketEvent extends CancellableEvent {

    private Packet packet;

    public PacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
}