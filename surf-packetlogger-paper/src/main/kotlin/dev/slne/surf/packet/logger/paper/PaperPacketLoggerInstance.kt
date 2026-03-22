package dev.slne.surf.packet.logger.paper

import com.google.auto.service.AutoService
import dev.slne.surf.packet.logger.client.PacketLoggerInstance
import java.nio.file.Path

@AutoService(PacketLoggerInstance::class)
class PaperPacketLoggerInstance : PacketLoggerInstance {
    override val dataPath: Path get() = plugin.dataPath
}