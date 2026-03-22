package dev.slne.surf.packet.logger.client.config

import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class PacketConfig(
    val receivedPackets: MutableSet<Packet>,
    val sentPackets: MutableSet<Packet>
) {
    companion object {
        fun generateConfigName(): String {
            val now = OffsetDateTime.now()
            val year = now.year
            val month = now.monthValue
            val day = now.dayOfMonth
            val hour = now.hour
            val minute = now.minute
            val second = now.second

            return "packets_$year-$month-${day}_$hour-$minute-$second.json"
        }
    }
}