import cn.fuser.argsparser.BoolFlag
import cn.fuser.argsparser.FlagSet
import cn.fuser.argsparser.IntFlag

fun main(args: Array<String>) {
    val fs = FlagSet("测试", "测试一下")
    fs.register(IntFlag("a", default = 2, usage = "a的值"))
    fs.register(BoolFlag("m", default = false))
    fs.parse(arrayOf("-a", "2038", "-m"))
    val m: Boolean? = fs.value("m", Boolean::class)
    val a = fs.value("a", Int::class)
    println("b : $m a: $a")
}