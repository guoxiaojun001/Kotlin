lateinit 和 lazy 是 Kotlin 中的两种不同的延迟初始化技术。

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
我们可以在任何地方（如一个utils文件中）声明这个函数，在我们的Activity中将它作为普通方法来使用：
 override fun onCreate(savedInstanceState: Bundle?) { 
     super<BaseActivity>.onCreate(savedInstanceState) 
     toast("This is onCreate!!") 
 }


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