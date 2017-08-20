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
        get() = if (realValue != null) realValue else default

    abstract fun parse(v: String): Boolean
}

class IntFlag(name: String, default: Int?, usage: String = EMPTY_STR) : Flag<Int>(name, default, usage) {
    override val type: FlagType = FlagType.INT
    override fun parse(v: String): Boolean {
        this.realValue = v.toIntOrNull()
        return true
    }
}

class BoolFlag(name: String, default: Boolean = false, usage: String = EMPTY_STR) : Flag<Boolean>(name, default, usage) {
    override val type: FlagType
        get() = FlagType.BOOL
    override val needValue: Boolean = false

    override fun parse(v: String): Boolean {
        this.realValue = true
        return true
    }
}

class ShortFlag(name: String, default: Short? = null, usage: String = EMPTY_STR) : Flag<Short>(name, default, usage) {
    override val type: FlagType
        get() = FlagType.SHORT

    override fun parse(v: String): Boolean {
        this.realValue = v.toShortOrNull()
        return true
    }
}

class StringFlag(name: String, default: String? = null, usage: String = EMPTY_STR) : Flag<String>(name, default, usage) {
    override fun parse(v: String): Boolean {
        this.realValue = v
        return true
    }
}