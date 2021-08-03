package ray.mintcat.guiden

import org.bukkit.entity.Player
import ray.mintcat.guiden.data.GuidenAPI
import taboolib.common.platform.command

object GuidenCommand {

    fun init() {
        command("guiden", permission = "*") {
            literal("get") {
                //name
                dynamic {
                    suggestion<Player> { _, _ ->
                        GuidenAPI.guiden.map { it.key }
                    }
                    //key
                    dynamic {
                        suggestion<Player> { _, context ->
                            val data = GuidenAPI.get(context.argument(-1)!!)
                            data.subs.map { it.key }
                        }
                        execute<Player> { sender, context, argument ->
                            //xxx get name key
                            //-3  -2   -1   0
                            val data = GuidenAPI.get(context.argument(-1)!!)
                            val value = data.getSub(context.argument(0)!!)
                            sender.info("仓库 §f${context.argument(-1)} §7的 &f${context.argument(0)} &7值为 &f${value}")
                        }
                    }
                }
            }
            literal("set") {
                dynamic {
                    suggestion<Player> { _, _ ->
                        GuidenAPI.guiden.map { it.key }
                    }
                    dynamic {
                        suggestion<Player> { _, context ->
                            val data = GuidenAPI.get(context.argument(-1)!!)
                            data.subs.map { it.key }
                        }
                        dynamic {
                            suggestion<Player> { _, context ->
                                val data = GuidenAPI.editor
                                val list = mutableListOf<String>()
                                data.forEach {
                                    list.addAll(it.alias)
                                    list.add(it.key)
                                }
                                list
                            }
                        }
                        dynamic {
                            suggestion<Player> { _, context ->
                                listOf("请输入传入参数")
                            }
                            execute<Player> { sender, context, argument ->
                                //xxx set name key action value
                                //        -3    -2      -1   0
                                val data = GuidenAPI.get(context.argument(-3)!!)
                                data.edit(context.argument(-2)!!,context.argument(-1)!!,context.argument(0)!!)
                            }
                        }

                    }
                }
            }
        }
    }

}