@file:OptIn(ExperimentalSerializationApi::class)

package dev.slne.surf.packet.logger.client

import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.event.PacketSendEvent
import dev.slne.surf.packet.logger.client.config.Packet
import dev.slne.surf.packet.logger.client.config.PacketConfig
import dev.slne.surf.surfapi.core.api.util.mutableObjectListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.util.concurrent.ConcurrentHashMap
import kotlin.io.path.createFile
import kotlin.io.path.outputStream

class PacketCache {
    private val sendEvents = ConcurrentHashMap.newKeySet<PacketSendEvent>()
    private val receiveEvents = ConcurrentHashMap.newKeySet<PacketReceiveEvent>()

    fun handlePacketSend(event: PacketSendEvent) {
        sendEvents.add(event)
    }

    fun handlePacketReceive(event: PacketReceiveEvent) {
        receiveEvents.add(event)
    }

    private suspend fun calculatePackets(): PacketConfig = withContext(Dispatchers.Default) {
        val sentPackets = sendEvents.mapTo(mutableObjectListOf(1_000_000)) {
            Packet(
                type = it.packetType.name,
                timestamp = it.timestamp
            )
        }

        val receivedPackets = receiveEvents.mapTo(mutableObjectListOf(1_000_000)) {
            Packet(
                type = it.packetType.name,
                timestamp = it.timestamp
            )
        }

        return@withContext PacketConfig(
            sentPackets = sentPackets,
            receivedPackets = receivedPackets,
        )
    }

    suspend fun savePacketsToFile() {
        val config = calculatePackets()
        val file = PacketLoggerInstance.dataPath.resolve(PacketConfig.generateConfigName())

        withContext(Dispatchers.IO) {
            file.createFile()
            file.outputStream().buffered().use { writer ->
                Json.encodeToStream(config, writer)
            }
        }
    }
}

