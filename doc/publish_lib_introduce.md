如果你想在Android Studio中引入一个library到你的项目，你只需添加如下的一行代码到模块的build.gradle文件中。

```
dependencies {
    compile 'com.inthecheesefactory.thecheeselibrary:fb-like:0.9.3'
}
```

就是如此简单的一行代码，你就可以使用这个library了。

酷呆了。不过你可能很好奇Android Studio是从哪里得到这个library的。这篇文章将详细讲解这是怎么回事，包括如何把你的库发布出去分享给世界各地的其他开发者，这样不仅可以让世界更美好，还可以耍一次酷。

## Android studio 是从哪里得到库的？
先从这个简单的问题开始，我相信不是每个人都完全明白Android studio 是从哪里得到这些library的。莫非就是Android studio 从google搜索然后下载了一个合适的给我们？

呵呵，没那么复杂。Android Studio是从build.gradle里面定义的Maven 仓库服务器上下载library的。Apache Maven是Apache开发的一个工具，提供了用于贡献library的文件服务器。总的来说，只有两个标准的Android library文件服务器：jcenter 和  Maven Central。

## jcenter
jcenter是一个由 bintray.com维护的Maven仓库 。你可以在这里看到整个仓库的内容。

我们在项目的build.gradle 文件中如下定义仓库，就能使用jcenter了：

```
allprojects {
    repositories {
        jcenter()
    }
}

```

## Maven Central
Maven Central 则是由sonatype.org维护的Maven仓库。你可以在这里看到整个仓库。

注：不管是jcenter还是Maven Central ，两者都是Maven仓库

我们在项目的build.gradle 文件中如下定义仓库，就能使用Maven Central了：

```
allprojects {
    repositories {
        mavenCentral()
    }
}
```

注意，虽然jcenter和Maven Central 都是标准的 android library仓库，但是它们维护在完全不同的服务器上，由不同的人提供内容，两者之间毫无关系。在jcenter上有的可能 Maven Central 上没有，反之亦然。
除了两个标准的服务器之外，如果我们使用的library的作者是把该library放在自己的服务器上，我们还可以自己定义特有的Maven仓库服务器。Twitter的Fabric.io 就是这种情况，它们在https://maven.fabric.io/public上维护了一个自己的Maven仓库。如果你想使用Fabric.io的library，你必须自己如下定义仓库的url。

repositories {
    maven { url 'https://maven.fabric.io/public' }
}
然后在里面使用相同的方法获取一个library。

dependencies {
    compile 'com.crashlytics.sdk.android:crashlytics:2.2.4@aar'
}
但是将library上传到标准的服务器与自建服务器，哪种方法更好呢？当然是前者。如果将我们的library公开，其他开发者除了一行定义依赖名的代码之外不需要定义任何东西。因此这篇文章中，我们将只关注对开发者更友好的jcenter 和 Maven Central 。

实际上可以在Android Studio上使用的除了Maven 仓库之外还有另外一种仓库：Ivy 仓库 。但是根据我的经验来看，我还没看到任何人用过它，包括我，因此本文就直接忽略了。


(http://www.open-open.com/lib/view/open1435109824278.html)