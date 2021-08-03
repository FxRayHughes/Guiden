package ray.mintcat.guiden.data

import org.bukkit.Bukkit
import ray.mintcat.guiden.Guiden
import ray.mintcat.guiden.data.editor.Editor
import ray.mintcat.guiden.getGuiden
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.submit

object GuidenAPI {

    val editor = ArrayList<Editor>()

    val guiden = ArrayList<GuidenData>()

    fun getEditor(key: String): Editor? {
        return editor.firstOrNull { e -> e.key == key || e.alias.map { it.contains(key) }.contains(true) }
    }

    @Awake(LifeCycle.ACTIVE)
    fun loadAll() {
        Bukkit.getOnlinePlayers().forEach {
            it.getGuiden()
        }
    }

    @Awake(LifeCycle.DISABLE)
    fun save() {
        guiden.forEach {
            Guiden.database.save(it.key)
        }
    }

    @Awake(LifeCycle.ACTIVE)
    private fun saveTimer() {
        submit(period = 600) {
            save()
        }
    }

    fun get(key: String): GuidenData {
        if (guiden.firstOrNull { it.key == key } == null) {
            val read = Guiden.database.get(key)
            if (read != null) {
                guiden.add(read)
            } else {
                guiden.add(GuidenData(key))
            }
        }
        return guiden.firstOrNull { it.key == key }!!
    }

}
