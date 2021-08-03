package ray.mintcat.guiden.database

import ray.mintcat.guiden.Guiden
import ray.mintcat.guiden.data.GuidenData
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake


abstract class DataBase {

    abstract val name: String

    abstract fun get(key: String): GuidenData?

    abstract fun save(key: String)

    @Awake(LifeCycle.ACTIVE)
    fun load() {
        if (!Guiden.dataBase.contains(this)) {
            Guiden.dataBase.add(this)
        }
    }

}