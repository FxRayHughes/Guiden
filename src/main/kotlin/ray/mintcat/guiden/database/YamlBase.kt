package ray.mintcat.guiden.database

import ray.mintcat.guiden.data.GuidenAPI
import ray.mintcat.guiden.data.GuidenData
import ray.mintcat.guiden.data.GuidenSub
import taboolib.common.platform.releaseResourceFile
import taboolib.module.configuration.createLocal
import java.nio.charset.StandardCharsets

class YamlBase : DataBase() {

    override val name: String
        get() = "YAML"

    override fun get(key: String): GuidenData {
        releaseResourceFile("data/${key}.yml").readText(StandardCharsets.UTF_8)
        val data = createLocal("data/${key}.yml")
        val sub = mutableListOf<GuidenSub>()
        data.getKeys(false).forEach { keys ->
            sub.add(GuidenSub(keys, data.getString(keys)))
        }
        return GuidenData(key, sub)
    }

    override fun save(key: String) {
        val data = GuidenAPI.guiden.firstOrNull { it.key == key } ?: return
        releaseResourceFile("data/${key}.yml").readText(StandardCharsets.UTF_8)
        val datay = createLocal("data/${key}.yml")
        datay.getKeys(false).forEach { datay.set(it, null) }
        data.subs.forEach { sub ->
            datay.set(sub.key, sub.value)
        }
    }
}