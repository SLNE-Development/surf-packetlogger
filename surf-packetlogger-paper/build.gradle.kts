plugins {
    id("dev.slne.surf.api.gradle.paper-plugin")
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.packet.logger.paper.PacketLoggerPaper")
    foliaSupported(true)
}

dependencies {
    api(projects.surfPacketloggerClient)
}