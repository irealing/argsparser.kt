package cn.fuser.argsparser

import org.junit.Test

class FlagSetTest {
    @Test
    fun testBoolean() {
        val fs = FlagSet("test boolean", "")
        fs.register(BoolFlag("m", false, "match"))
        fs.parse(arrayOf("-m"))
        val m = fs.value("m", Boolean::class)
        assert(m ?: false)
    }

    @Test
    fun testInt() {
        val fs = FlagSet("test int", "")
        fs.register(IntFlag("n", 0, "number"))
        fs.parse(arrayOf("-n", "1024"))
        val n = fs.value("n", Int::class) ?: 0
        assert(n == 1024)
    }
}