# Android平台上的iptv中间件
------
## 本中间件的组成
+ 中间件中浏览器使用的定制***********chromium**
+ 中间件中播放器使用的定制**ijkplayer**
------
## 编译并运行Debug版本
(```)
  ./gradlew assembleDebug
  adb install -r app/build/outputs/apk/debug/app-debug.apk
  adb shell am start -n com.iptv.browser/.MainActivity http://www.baidu.com
(```)
------
## 本中间件优势
+ 删除了大部分不用的功能，因此占用内存较小
+ 浏览器仅需要一个so，大小为27M，压缩后更小
+ 兼容了大部分当前iptv网页
+ 支持比较有用的新标准，如WebSocket、WebGL、WebAssembly等
------
## 代码结构
+ WebView.java是浏览器的接口文件
+ MediaPlayerImpl.java调用ijkplayer实现播放器
+ init.js中定义了MediaPlayer Object
+ MainActivity.java中创建了IjkVideoView和WebView,并且连接MediaPlayer和MediaPlayerImpl
------
+ 有问题可以发邮件: pistachio2012@ranpeng.fun
