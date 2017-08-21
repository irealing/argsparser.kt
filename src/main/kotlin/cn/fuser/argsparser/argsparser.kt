package cn.fuser.argsparser

import kotlin.reflect.KClass

const val EMPTY_STR: String = ""

enum class FlagType constructor(val type: String) {
    UNSET("UNSET"), INT("INT"), BOOL("BOOL"), SHORT("SHORT"), STRING("STRING");

    override fun toString(): String {
        return type
    }
}

class FlagSet(private val name: String, private val usage: String) {
    private val output = System.err
    private val flags: HashMap<String, Flag<*>> = HashMap()
    var parsed: Boolean = false
        get() = field


    fun register(f: Flag<*>) {
        flags[f.name] = f
    }

    fun printDefaults() {
        output.println("$name:\n\t$usage")
        for ((k, v) in flags)
            output.println("-$k (${v.type}):\t${v.usage} (default:${v.default})")
    }

    fun parse(args: Array<String>) {
        if (args.isEmpty() && flags.isNotEmpty()) {
            printDefaults()
            return
        }
        var cursor = 0
        while (cursor < args.size) {
            var name = args[cursor]
            if (name.isEmpty() || name[0] != '-') {
                printDefaults()
                return
            }
            name = name.substring(1)
            val flag = flags[name]
            if (flag == null) {
                printDefaults()
                return
            }
            var value = ""
            if (flag.needValue && args.size > cursor + 1) {
                value = args[cursor + 1]
                cursor += 2
            } else {
                cursor += 1
            }
            val status = flag.parse(value)
            if (!status) {
                printDefaults()
                break
            }
        }
        this.parsed = true
    }

    fun value(key: String): Any? = flags[key]?.value

    fun <T : Any> value(name: String, clz: KClass<T>): T? = value(name) as? T
}