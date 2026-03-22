package dev.slne.surf.packet.logger.client

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.event.PacketSendEvent
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object PacketLogger {
    private val cacheMutex = Mutex()
    private var activeCache: PacketCache? = null

    fun registerPacketEvents() {
        PacketEvents.getAPI().eventManager.registerListener(PacketListener)
    }

    fun unregisterPacketEvents() {
        PacketEvents.getAPI().eventManager.unregisterListeners(PacketListener)
    }

    suspend fun startLogging(): Boolean = cacheMutex.withLock {
        if (activeCache != null) {
            return@withLock false
        }

        activeCache = PacketCache()

        return@withLock true
    }

    suspend fun stopLogging(): Boolean = cacheMutex.withLock {
        if (activeCache == null) {
            return@withLock false
        }

        val cache = activeCache
        activeCache = null

        cache?.savePacketsToFile()

        return@withLock true
    }

    fun handlePacketReceive(event: PacketReceiveEvent) {
        activeCache?.handlePacketReceive(event)
    }

    fun handlePacketSend(event: PacketSendEvent) {
        activeCache?.handlePacketSend(event)
    }
}