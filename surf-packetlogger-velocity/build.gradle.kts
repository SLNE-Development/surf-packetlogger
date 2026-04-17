plugins {
    id("dev.slne.surf.api.gradle.velocity")
}

velocityPluginFile {
    main = "dev.slne.surf.packet.logger.velocity.PacketLoggerVelocity"
}

dependencies {
    api(projects.surfPacketloggerClient)
}