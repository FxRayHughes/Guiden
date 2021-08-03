package ray.mintcat.guiden.data.editor

import ray.mintcat.guiden.data.GuidenSub
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

class ERemove : Editor {
    override val key: String
        get() = "r"

    override val alias: MutableList<String>
        get() = mutableListOf("remove", "re", "<-", "删")

    override val info: List<String>
        get() = listOf("删除这个数据")

    override fun eval(sub: GuidenSub, value: String): GuidenSub {
        sub.remove = true
        return sub
    }

    @Awake(LifeCycle.ENABLE)
    fun init() {
        Editor.load(this)
    }
}