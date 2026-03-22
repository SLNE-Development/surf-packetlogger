package dev.slne.surf.packet.logger.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.packet.logger.client.PacketLogger
import org.bukkit.plugin.java.JavaPlugin

class PacketLoggerPaper : SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {
        PacketLogger.registerPacketEvents()

        packetLoggerCommand()
    }

    override suspend fun onDisableAsync() {
        PacketLogger.unregisterPacketEvents()
    }
}

val plugin get() = JavaPlugin.getPlugin(PacketLoggerPaper::class.java)