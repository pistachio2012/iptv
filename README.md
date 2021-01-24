# Android平台上的iptv中间件
---
## 中间件的组成
+ 中间件中浏览器使用的定制***********chromium**
+ 中间件中播放器使用的定制**ijkplayer**
---
## 编译Debug版本
`./gradlew assembleDebug`
---
## 代码结构
+ WebView.java是浏览器的接口文件
+ MediaPlayerImpl.java调用ijkplayer实现播放器
+ index.html是默认打开的首页
+ init.js中定义了MediaPlayer Object
+ MainActivity.java中创建了IjkVideoView和WebView,并且连接MediaPlayer和MediaPlayerImpl
---
+ 有问题可以发邮件: pistachio2012@126.com
