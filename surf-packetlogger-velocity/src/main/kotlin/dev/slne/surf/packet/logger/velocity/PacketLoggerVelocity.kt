package dev.slne.surf.packet.logger.velocity

import com.github.shynixn.mccoroutine.velocity.SuspendingPluginContainer
import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.PluginContainer
import com.velocitypowered.api.plugin.annotation.DataDirectory
import dev.slne.surf.packet.logger.client.PacketLogger
import java.nio.file.Path

class PacketLoggerVelocity @Inject constructor(
    suspendingContainer: SuspendingPluginContainer,
    val container: PluginContainer,
    @param:DataDirectory val dataPath: Path
) {
    init {
        INSTANCE = this
        suspendingContainer.initialize(this)
    }

    @Subscribe
    fun onProxyInitialize(event: ProxyInitializeEvent) {
        PacketLogger.registerPacketEvents()

        packetLoggerCommand()
    }

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent) {
        PacketLogger.unregisterPacketEvents()
    }

    companion object {
        lateinit var INSTANCE: PacketLoggerVelocity
    }
}

val plugin get() = PacketLoggerVelocity.INSTANCE