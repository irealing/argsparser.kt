package cn.fuser.argsparser

import kotlin.reflect.KClass

interface Param {
    fun validate(): Boolean
}

class ArgsInjector<out T : Param>(clz: KClass<T>, name: String, describe: String) {
    private val funConstructor = clz.constructors.first()
    private val flagSet: FlagSet = FlagSet(name, describe)
    private val flags: Array<Flag<*>?> = Array(funConstructor.parameters.size) { null }
    private fun register() {
        var cursor = 0
        for (item in funConstructor.parameters) {
            var flag: Flag<*>? = null
            val name = item.name ?: continue
            when (item.type.classifier) {
                String::class ->
                    flag = StringFlag(name)
                Int::class ->
                    flag = IntFlag(name, 0)
                Boolean::class ->
                    flag = BoolFlag(name)
            }
            flag ?: continue
            flagSet.register(flag)
            flags[cursor] = flag
            cursor++
        }
    }

    fun parse(args: Array<String>): T {
        register()
        flagSet.parse(args)
        return injectValue()
    }

    private fun injectValue(): T {
        val value = Array(flags.size) { flags[it]?.value }
        return funConstructor.call(*value)
    }

    fun valdate(): T = TODO()
}