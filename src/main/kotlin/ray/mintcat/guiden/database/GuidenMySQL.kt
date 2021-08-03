package ray.mintcat.guiden.database

import ray.mintcat.guiden.Guiden
import ray.mintcat.guiden.data.GuidenAPI
import ray.mintcat.guiden.data.GuidenData
import taboolib.common.platform.info
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.Table
import taboolib.module.database.getHost
import java.io.*

class GuidenMySQL : DataBase() {

    override val name: String
        get() = "MySQL"

    val host = Guiden.conf.getHost("database")

    val table = Table("guiden", host) {
        add("key") {
            type(ColumnTypeSQL.VARCHAR, 50) {
                options(ColumnOptionSQL.UNIQUE_KEY, ColumnOptionSQL.NOTNULL)
            }
        }
        add("data") {
            type(ColumnTypeSQL.TEXT, 65535)
        }
    }

    val dataSource = host.createDataSource()

    init {
        table.workspace(dataSource) {
            createTable()
        }.run()
    }

    fun hasData(key: String): Boolean {
        return table.workspace(dataSource) {
            select { where { "key" eq key } }
        }.find()
    }

    override fun get(key: String): GuidenData? {
        return getArray(key)?.deserialize<GuidenData> {}
    }

    private fun getArray(key: String): ByteArray? {
        return table.workspace(dataSource) {
            select { where { "key" eq key } }
        }.firstOrNull {
            getBytes("data")
        }
    }

    private fun GuidenData.toArray(): ByteArray {
        return this.serialize {}
    }

    override fun save(key: String) {
        val data = GuidenAPI.guiden.firstOrNull { it.key == key } ?: return
        if (hasData(key)) {
            table.workspace(dataSource) {
                update {
                    set("data", data)
                    where { "key" eq key }
                }
            }.run()
        } else {
            table.workspace(dataSource) {
                insert("key", "data") {
                    value(key, data)
                    onFinally {
                        generatedKeys.run {
                            next()
                            info("generate id ${getObject(1)}")
                        }
                    }
                }
            }.run()
        }
    }

    fun Serializable.serialize(builder: ObjectOutputStream.() -> Unit = {}): ByteArray {
        ByteArrayOutputStream().use { byteArrayOutputStream ->
            ObjectOutputStream(byteArrayOutputStream).use { objectOutputStream ->
                builder(objectOutputStream)
                objectOutputStream.writeObject(this)
                objectOutputStream.flush()
            }
            return byteArrayOutputStream.toByteArray()
        }
    }

    fun <T> ByteArray.deserialize(reader: ObjectInputStream.() -> Unit = {}): T {
        ByteArrayInputStream(this).use { byteArrayInputStream ->
            ObjectInputStream(byteArrayInputStream).use { objectInputStream ->
                reader(objectInputStream)
                return objectInputStream.readObject() as T
            }
        }
    }

}