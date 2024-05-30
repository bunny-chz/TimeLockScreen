# TimeLockScreen
安卓定时自动锁屏TimeLockScreen，应用安卓的电源键管理权限，秒级别定时关闭手机屏幕，也可以下拉任务栏QS Tile一键锁屏。



# TimeLockScreen_V1.0.0 实现原理
锁屏需要授予系统锁屏的权限，要在Manifest配置好ServiceReceiver

定时实现原理使用安卓的重写onPause()方法，应用进入后台后，Java的 Thread.sleep()睡眠，待定设定好的时间，时间一过就锁屏。



# TimeLockScreen_V2.0.0 实现原理
为了不被系统杀死，使用无障碍模式在后台计时，在到时间点时，调用锁屏函数。需要在Manifest注册无障碍服务。



抖音演示视频：

V1.0.0
https://v.douyin.com/rbyWYnJ/

V2.0.0
https://v.douyin.com/ijj2tP3H/


新拟物化设计界面风格网址

https://github.com/sshadkany/Android_neumorphic

声明： 

软件是否可行看实际情况。

在高版本安卓系统中，每次自动锁屏后，再次打开手机需要输入锁屏密码。

效果图:

![Screenshot_2022-11-25-16-56-58-59_63e6416f95f1597b8c41497bed031a3d](https://user-images.githubusercontent.com/57706599/203940904-01af388a-7b71-42dd-9eca-bdd9dc9d418a.jpg)

