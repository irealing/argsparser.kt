import cn.fuser.argsparser.FlagSet
import cn.fuser.argsparser.IntFlag

fun main(args: Array<String>) {
    val fs = FlagSet("测试", "测试一下")
    fs.register(IntFlag("a", default = 2, usage = "a的值"))
    fs.parse(args)
    println(fs.value("a"))
}