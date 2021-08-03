package ray.mintcat.guiden

import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion

object GuidenPapiHook : PlaceholderExpansion {
    override val identifier: String
        get() = "guiden"

    override fun onPlaceholderRequest(player: Player, args: String): String {
        TODO("Not yet implemented")
    }
}