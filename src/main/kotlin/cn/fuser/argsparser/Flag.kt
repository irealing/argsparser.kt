package cn.fuser.argsparser

abstract class Flag<T>(val name: String, val default: T?, val usage: String = EMPTY_STR) {
    open val type: FlagType = FlagType.UNSET
    open val needValue: Boolean = true
    protected open var realValue: T? = null
        get() = field
        set(value) {
            field = value
        }
    val value: T?
        get() = realValue ?: default

    abstract fun parse(v: String): Boolean
}

class IntFlag(name: String, default: Int?, usage: String = EMPTY_STR) : Flag<Int>(name, default, usage) {
    override val type: FlagType = FlagType.INT
    override fun parse(v: String): Boolean {
        this.realValue = v.toInt()
        return true
    }
}

class BoolFlag(name: String, default: Boolean?, usage: String = EMPTY_STR) : Flag<Boolean>(name, default, usage) {
    override val type: FlagType
        get() = FlagType.BOOL
    override val needValue: Boolean = false
    override var realValue: Boolean? = false
        get() = field ?: false

    override fun parse(v: String): Boolean {
        this.realValue = true
        return true
    }
}