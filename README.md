# argsparser

*命令行参数解析工具kotlin版本（参考golang标准库flag包开发）*

## 示例

```
import cn.fuser.argsparser.ArrayFlag
import cn.fuser.argsparser.BoolFlag
import cn.fuser.argsparser.FlagSet
import cn.fuser.argsparser.IntFlag

fun main(args: Array<String>) {
    val fs = FlagSet("测试", "测试一下")
    fs.register(IntFlag("a", default = 1, usage = "INT类型"))
    fs.register(BoolFlag("m", default = false, usage = "布尔类型"))
    fs.register(ArrayFlag("file", null, "数组"))
    fs.parse(arrayOf("-a", "2038", "-m", "-file", "/etc", "-file", "/opt", "-file", "/boot"))
    val m: Boolean? = fs.value("m", Boolean::class)
    val a = fs.value("a", Int::class)
    val files = fs.value("file", Array<String>::class)
    println("b : $m a: $a")
    files?.printArray()
}

fun <T> Array<T>.printArray() {
    forEach { println(it) }
}
```

## 支持的类型

* Int
* Boolean
* Short
* STRING
* Array\<String\>

