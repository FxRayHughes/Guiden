package ray.mintcat.guiden

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ray.mintcat.guiden.data.GuidenAPI
import taboolib.common5.Baffle

fun Player.getGuiden() {
    when (Guiden.conf.getString("type", "UUID")) {
        "UUID" -> GuidenAPI.get(this.uniqueId.toString())
        "NAME" -> GuidenAPI.get(this.name)
    }
}

fun String.center(prefix: String, suffix: String): String? {
    val start = this.indexOfLast { it.toString() == prefix }
    val end = this.indexOfFirst { it.toString() == suffix }
    if (start == -1 || end == -1) {
        return null
    }
    return this.subSequence(start + 1, end).toString()

}

fun String.range(): String {
    if (this.center("{", "}") != null) {
        val info = this.center("{", "}")!!.split("-")
        // {10-20}
        return this.replace(
            "{${this.center("{", "}")}}",
            ((info[0].toInt())..(info[1].toInt())).random().toString()
        )
    } else {
        return this
    }
}

fun Player.info(vararg block: String) {
    block.forEach {
        toInfo(this, it)
    }
}

fun Player.error(vararg block: String) {
    block.forEach {
        toError(this, it)
    }
}

fun debug(vararg block: String) {
    val player = Bukkit.getPlayerExact("Ray_Hughes") ?: return
    block.forEach {
        toError(player, it)
    }
}

fun toInfo(sender: CommandSender, message: String) {
    sender.sendMessage("§8[§a Natur §8] §7${message.replace("&", "§")}")
    if (sender is Player && !cooldown.hasNext(sender.name)) {
        sender.playSound(sender.location, Sound.UI_BUTTON_CLICK, 1f, (1..2).random().toFloat())
    }
}

fun toError(sender: CommandSender, message: String) {
    sender.sendMessage("§8[§4 Natur §8] §7${message.replace("&", "§")}")
    if (sender is Player && !cooldown.hasNext(sender.name)) {
        sender.playSound(sender.location, Sound.ENTITY_VILLAGER_NO, 1f, (1..2).random().toFloat())
    }
}

fun toDone(sender: CommandSender, message: String) {
    sender.sendMessage("§8[§6 Natur §8] §7${message.replace("&", "§")}")
    if (sender is Player && !cooldown.hasNext(sender.name)) {
        sender.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, (1..2).random().toFloat())
    }
}

fun toConsole(message: String) {
    Bukkit.getConsoleSender().sendMessage("§8[§e Natur §8] §7${message.replace("&", "§")}")
}

val cooldown = Baffle.of(100)
