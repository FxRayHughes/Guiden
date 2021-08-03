package ray.mintcat.guiden.data

import com.sun.org.apache.xml.internal.serializer.Serializer
import java.io.Serializable


class GuidenData(
    val key: String,
    val subs: MutableList<GuidenSub>
): Serializable {

    fun get(key: String, default: String): String {
        return subs.firstOrNull { it.key == key }?.value ?: default
    }

    fun getSub(key: String): GuidenSub? {
        return subs.firstOrNull { it.key == key }
    }

    private fun set(key: String, setter: String): Boolean {
        val sub = getSub(key) ?: return false
        sub.value = setter
        return true
    }


    fun edit(key: String, editor: String, value: String): Boolean {
        val sub = getSub(key) ?: return false
        val editors = GuidenAPI.getEditor(editor) ?: return false
        if (editors.eval(sub, value).remove) {
            subs.remove(sub)
        } else {
            editors.eval(sub, value)
        }
        return true
    }


}