package dev.slne.surf.packet.logger.client

import com.github.retrooper.packetevents.event.PacketListenerAbstract
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.event.PacketSendEvent

object PacketListener : PacketListenerAbstract() {
    override fun onPacketSend(event: PacketSendEvent) {
        PacketLogger.handlePacketSend(event)
    }

    override fun onPacketReceive(event: PacketReceiveEvent) {
        PacketLogger.handlePacketReceive(event)
    }
}