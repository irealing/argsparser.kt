package cn.fuser.argsparser

abstract class Flag<T>(val name: String, val default: T?, val usage: String, require: Boolean) {
    open val type: FlagType = FlagType.UNSET
    open val needValue: Boolean = true
    open val require = require
    protected open var realValue: T? = null
        get() = field
        set(value) {
            field = value
        }
    val value: T?
        get() = if (realValue != null) realValue else default

    abstract fun parse(v: String): Boolean
    open fun validate(): Boolean = !require || value != null
}

class IntFlag(name: String, default: Int?, usage: String = EMPTY_STR, require: Boolean = false) : Flag<Int>(name, default, usage, require) {
    override val type: FlagType = FlagType.INT
    override fun parse(v: String): Boolean {
        this.realValue = v.toIntOrNull()
        return true
    }
}

class BoolFlag(name: String, default: Boolean = false, usage: String = EMPTY_STR, require: Boolean = false) : Flag<Boolean>(name, default, usage, require) {
    override val type: FlagType
        get() = FlagType.BOOL
    override val needValue: Boolean = false

    override fun parse(v: String): Boolean {
        this.realValue = true
        return true
    }
}

class ShortFlag(name: String, default: Short? = null, usage: String = EMPTY_STR, require: Boolean = false) : Flag<Short>(name, default, usage, require) {
    override val type: FlagType
        get() = FlagType.SHORT

    override fun parse(v: String): Boolean {
        this.realValue = v.toShortOrNull()
        return true
    }
}

class StringFlag(name: String, default: String? = null, usage: String = EMPTY_STR, require: Boolean = false) : Flag<String>(name, default, usage, require) {
    override val type: FlagType
        get() = FlagType.STRING

    override fun parse(v: String): Boolean {
        this.realValue = v
        return true
    }
}

class ArrayFlag(name: String, default: Array<String>?, usage: String = EMPTY_STR, require: Boolean = false) : Flag<Array<String>>(name, default, usage, require) {
    override val type: FlagType = FlagType.STRING
    override var realValue: Array<String>? = null
        get() = container.toTypedArray()
    private val container = mutableListOf<String>()
    override fun parse(v: String): Boolean {
        container.add(v)
        return true
    }
}