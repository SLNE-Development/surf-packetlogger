@file:OptIn(ExperimentalSerializationApi::class)

package dev.slne.surf.packet.logger.client

import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.event.ProtocolPacketEvent
import com.github.retrooper.packetevents.netty.buffer.ByteBufHelper
import dev.slne.surf.packet.logger.client.config.Packet
import dev.slne.surf.packet.logger.client.config.PacketConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.util.concurrent.ConcurrentHashMap
import kotlin.io.path.createFile
import kotlin.io.path.outputStream

class PacketCache {
    private val sendEvents = ConcurrentHashMap.newKeySet<Packet>(1_000_000)
    private val receiveEvents = ConcurrentHashMap.newKeySet<Packet>(1_000_000)

    private fun calculatePacketSize(event: ProtocolPacketEvent): Int {
        return ByteBufHelper.readableBytes(event.byteBuf);
    }

    fun handlePacketSend(event: PacketSendEvent) {
        sendEvents.add(
            Packet(
                type = event.packetType.name,
                timestamp = event.timestamp,
                size = calculatePacketSize(event)
            )
        )
    }

    fun handlePacketReceive(event: PacketReceiveEvent) {
        receiveEvents.add(
            Packet(
                type = event.packetType.name,
                timestamp = event.timestamp,
                size = calculatePacketSize(event)
            )
        )
    }

    suspend fun savePacketsToFile() {
        val config = PacketConfig(
            receivedPackets = receiveEvents,
            sentPackets = sendEvents
        )
        
        val file = PacketLoggerInstance.dataPath.resolve(PacketConfig.generateConfigName())

        withContext(Dispatchers.IO) {
            file.createFile()
            file.outputStream().buffered().use { writer ->
                Json.encodeToStream(config, writer)
            }
        }
    }
}

