lateinit 和 lazy 是 Kotlin 中的两种不同的延迟初始化技术。
kotlin 里的变量定义有两种，val 和 var，其中 val 等同 Java 中 final 修饰的变量（只读），
**val一般是常量，var一般是变量。**
*lateinit 只用于 var，而 lazy 只用于 val
*lazy 应用于单例模式(if-null-then-init-else-return)，而且当且仅当变量被第一次调用的时候，委托方法才会执行。
*lateinit 则用于只能生命周期流程中进行获取或者初始化的变量，比如 Android 的 onCreate()

1.lazy{} 只能用在val类型, lateinit 只能用在var类型 如 ：
    val name: String by lazy { "sherlbon" }
    lateinit var adapter: MyAdapter
2.lateinit不能用在可空的属性上和java的基本类型上 如：
    lateinit var age: Int  //会报错
3.lateinit可以在任何位置初始化并且可以初始化多次。而lazy在第一次被调用时就被初始化，想要被改变只能重新定义
4.lateinit 有支持（反向）域（Backing Fields）


Kotlin扩展函数允许我们在不改变已有类的情况下，为类添加新的函数
**扩展函数是静态加载的，与类或实例无关。** 
我们可以在任何地方（如一个utils文件中）声明这个函数，在我们的Activity中将它作为普通方法来使用：
 override fun onCreate(savedInstanceState: Bundle?) { 
     super<BaseActivity>.onCreate(savedInstanceState) 
     toast("This is onCreate!!") 
 }
 
 //可以直接才函数上设置默认值
 fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
  }
 第二个参数是指烤面包的持续时间。这是一个可选参数，如果没有指定，将使用Toast.LENGTH_SHORT。现在你有两种方法来调用这个函数：
 toast("Short Toast!!")
 toast("Long Toast!!", Toast.LENGTH_LONG)
 
 在Java中，我们会将一些常用的功能封装成一个个工具类，工具类其实就是对于String，Collection，
 IO 等常用类的功能的扩展。我们写的工具类方法和变量都会写成静态的。因为，这些方法我们只是想调用一下，
 不需要牵扯工具类中的任何属性和变量，所以就没有必要实例化了(new)，既然不需要实例化了，那么就用静态就行了。
package com.john.kotlinstudy
 
import android.content.Context
import android.widget.Toast
/**
 * Toast工具类
 */
object ToastUtils {
  fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
  }
}

因为默认函数却决于调用时声明的类型，而不是运行时计算出来的结果。
成员函数优先于扩展函数 


对象声明
Singleton是一个非常有用的模式，而Kotlin（Scala之后）可以很容易地声明单例：
object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {
        // ...
    }
    val allDataProviders: Collection<DataProvider>
        get() = // ...
}

这被称为对象声明，并且在object关键字后面总是有一个名称。就像变量声明一样，对象声明不是表达式，不能在赋值语句的右边使用。

要引用这个对象，我们直接使用它的名字：
DataProviderManager.registerDataProvider(...)
伴侣对象

类中的对象声明可以使用companion关键字来标记：
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}
同伴对象的成员可以简单地使用类名作为限定符来调用：

val instance = MyClass.create()
伴侣对象的名称可以省略，在这种情况下Companion将使用该名称：

class MyClass {
    companion object {
    }
}

val x = MyClass.Companion
请注意，即使伴随对象的成员看起来像其他语言中的静态成员，但在运行时仍然是实际对象的实例成员，并且可以实现接口：

interface Factory<T> {
    fun create(): T
}


class MyClass {
    companion object : Factory<MyClass> {
        override fun create(): MyClass = MyClass()
    }
}


1.// 写在类名后的constructor()是主构造方法(primary constructor)
class User constructor(name: String){
    init {    //由于primary constructor中不能初始化（不能写表达式），
              //一般初始化工作都写在init代码块中，init 是一个关键字
        logger.info("User initialized with name: ${name}")
    }
}   

// 构造方法没有参数可以省略constructor()，类没有具体实现可以省略{}
// 可以写成：
class User


2.除了primary constructor 外，其余构造方法称为secondary constructors, 
上面都User类，用secondary constructor可以改写为：
class User{
    constructor(name: String){
        logger.info("User initialized with name: ${name}")
    }
}
// 如果一个类有primary constructor，那么所有的secondary constructor都应该实现(或者说委托, delegate)primary constructor.
class User constructor(name: String){
    var name: String = ""
    constructor(name: String, age: Int): this(name){
        // 这里的name和primary constructor的name不是同一个,跟成员变量name也不是同一个
    }
    // 也可以直接赋常量值,反正目的就是初始化
    constructor(age: Int, address: String): this("dongdong"){
    }
}


3. companion object 
companion关键字仅有这个用法，companion object只能在类中使用，相当于java中的静态内部类（kotlin没有static关键字），这里摘取kotlin文档的例子： 
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}
// 使用
var instance = MyClass.create()
// 也可以使用下面的方法来调用
// 由于Factory定义后不可修改，故使用val变量来存放
val factory = MyClass.Factory
var instance = factory.create()


data class 
kotlin中有专门的数据类来代替java中的JavaBean，再也不用写JavaBean的getter和setter了

data class News(title: String, time: String)


data class News(
        var author: String,// Ryuuzt
        var content: String,// 有些人就是嘴炮而已。还有些人自甘轻贱自己，自己看不起自己的职业。
        var avatar: String,// http://pic1.zhimg.com/da8e974dc_im.jpg
        var time: Int,// 1512974696
        var reply_to: Reply_to,
        var id: Int,// 30807290
        var likes: Int// 0
)