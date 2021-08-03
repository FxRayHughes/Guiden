package ray.mintcat.guiden.data.editor

import ray.mintcat.guiden.data.GuidenAPI
import ray.mintcat.guiden.data.GuidenSub

interface Editor {

    //动作识别
    val key: String

    //别名
    val alias: MutableList<String>

    //介绍
    val info: List<String>

    //Add, subtract, multiply and divide

    //运行
    //sub为对象 value是内容
    //add-> sub,1 -> +1
    fun eval(sub: GuidenSub, value: String): GuidenSub

    companion object {
        fun load(editor: Editor) {
            if (GuidenAPI.editor.firstOrNull { it.key == editor.key } != null) {
                GuidenAPI.editor.remove(GuidenAPI.editor.firstOrNull { it.key == editor.key })
            }
            GuidenAPI.editor.add(editor)
        }
    }

}