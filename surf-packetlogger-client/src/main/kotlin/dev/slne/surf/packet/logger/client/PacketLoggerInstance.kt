package dev.slne.surf.packet.logger.client

import dev.slne.surf.api.core.util.requiredService
import java.nio.file.Path

private val instance = requiredService<PacketLoggerInstance>()

interface PacketLoggerInstance {
    val dataPath: Path

    companion object : PacketLoggerInstance by instance {
        val INSTANCE get() = instance
    }
}