package ray.mintcat.guiden.data.editor

import ray.mintcat.guiden.data.GuidenSub
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

class ESubtract : Editor {
    override val key: String
        get() = "-"

    override val alias: MutableList<String>
        get() = mutableListOf("sub", "s", "take", "t", "减")

    override val info: List<String>
        get() = listOf("在原有的数据上进行减少", "若数据或传参不为数字则转为0.0")

    override fun eval(sub: GuidenSub, value: String): GuidenSub {
        val main = sub.value.toDoubleOrNull() ?: 0.0
        val to = value.toDoubleOrNull() ?: 0.0
        sub.value = (main - to).toString()
        return sub
    }

    @Awake(LifeCycle.ENABLE)
    fun init() {
        Editor.load(this)
    }
}