package ray.mintcat.guiden.data

import java.io.Serializable

class GuidenSub(
    val key: String,
    var value: String,
    var remove: Boolean = false
) : Serializable