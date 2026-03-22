package dev.slne.surf.packet.logger.paper

import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.packet.logger.client.PacketLogger
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun packetLoggerCommand() = commandAPICommand("packetlogger") {
    withPermission("surf.packetlogger.command")

    subcommand("start") {
        withPermission("surf.packetlogger.command")

        anyExecutor { sender, _ ->
            plugin.launch {
                if (PacketLogger.startLogging()) {
                    sender.sendText {
                        appendSuccessPrefix()

                        success("The packetlogger has been started!")
                    }
                } else {
                    sender.sendText {
                        appendErrorPrefix()

                        error("There is already a packetlogger started!")
                    }
                }
            }
        }
    }

    subcommand("stop") {
        withPermission("surf.packetlogger.command")

        anyExecutor { sender, _ ->
            plugin.launch {
                if (PacketLogger.stopLogging()) {
                    sender.sendText {
                        appendSuccessPrefix()

                        success("The packetlogger has been stopped!")
                    }
                } else {
                    sender.sendText {
                        appendErrorPrefix()

                        error("There is no packetlogger started!")
                    }
                }
            }
        }
    }
}