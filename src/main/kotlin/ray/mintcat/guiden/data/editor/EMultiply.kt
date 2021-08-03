package ray.mintcat.guiden.data.editor

import ray.mintcat.guiden.data.GuidenSub
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

class EMultiply : Editor {
    override val key: String
        get() = "*"

    override val alias: MutableList<String>
        get() = mutableListOf("mul", "m", "乘")

    override val info: List<String>
        get() = listOf("原有数据与参数相乘", "若数据或传参不为数字则转为0.0")

    override fun eval(sub: GuidenSub, value: String): GuidenSub {
        val main = sub.value.toDoubleOrNull() ?: 0.0
        val to = value.toDoubleOrNull() ?: 0.0
        sub.value = (main * to).toString()
        return sub
    }

    @Awake(LifeCycle.ENABLE)
    fun init() {
        Editor.load(this)
    }
}