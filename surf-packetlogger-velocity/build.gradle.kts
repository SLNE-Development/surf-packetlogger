plugins {
    id("dev.slne.surf.surfapi.gradle.velocity")
}

velocityPluginFile {
    main = "dev.slne.surf.packet.logger.velocity.PacketLoggerVelocity"
}

dependencies {
    api(projects.surfPacketloggerClient)
}