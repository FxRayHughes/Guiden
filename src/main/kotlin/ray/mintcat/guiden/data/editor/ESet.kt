package ray.mintcat.guiden.data.editor

import ray.mintcat.guiden.data.GuidenSub
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

class ESet : Editor {
    override val key: String
        get() = "="

    override val alias: MutableList<String>
        get() = mutableListOf("set", "s", "为")

    override val info: List<String>
        get() = listOf("设置数据为传参")

    override fun eval(sub: GuidenSub, value: String): GuidenSub {
        sub.value = value
        return sub
    }

    @Awake(LifeCycle.ENABLE)
    fun init() {
        Editor.load(this)
    }
}