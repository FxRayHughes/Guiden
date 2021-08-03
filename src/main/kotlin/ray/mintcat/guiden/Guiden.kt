package ray.mintcat.guiden

import ray.mintcat.guiden.database.DataBase
import taboolib.common.platform.Plugin
import taboolib.module.configuration.Config
import taboolib.module.configuration.SecuredFile
import taboolib.platform.BukkitPlugin

object Guiden : Plugin() {

    val plugin by lazy(LazyThreadSafetyMode.NONE) {
        BukkitPlugin.getInstance()
    }

    @Config(migrate = true)
    lateinit var conf: SecuredFile
        private set

    val dataBase = ArrayList<DataBase>()

    val database = dataBase.firstOrNull { it.name == conf.getString("data", "YAML") }!!


}