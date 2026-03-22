package dev.slne.surf.packet.logger.client.config

import kotlinx.serialization.Serializable

@Serializable
data class Packet(
    val type: String,
    val timestamp: Long
)
