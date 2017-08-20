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

    @Test
    fun testArray() {
        val fs = FlagSet("test array", "")
        fs.register(ArrayFlag("file", null, ""))
        fs.parse(arrayOf("-file", "/", "-file", "/opt", "-file", "/var"))
        val files = fs.value("file", Array<String>::class)
        val f = files ?: arrayOf()
        assert(compareArray(f, arrayOf("/", "/opt", "/var")))
    }

    private fun <T> compareArray(a: Array<T>, b: Array<T>): Boolean {
        assert(a.size == b.size)
        for (i in 0 until a.size - 1) {
            if (a[i] == b)
                return false
        }
        return true
    }
}