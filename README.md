## 仿QQ分享用户手机中安装的Apk文件
### Preview
<img src="https://github.com/zackzhou915/AppShare/blob/master/screenshot/device-2017-12-11-211846.png" width = "225" height = "400" alt="s1" />

### 关于Uri兼容问题
对于面向 Android 7.0 的应用，Android 框架执行的 StrictMode API 政策禁止在您的应用外部公开 file:// URI。  
具体Android7.0行为变更参照如下:  
https://developer.android.google.cn/about/versions/nougat/android-7.0-changes.html  
但即使为了兼容Android7.0而在Androidmanifest.xml配置了FileProvider，对部分应用的兼容性仍然不好，如QQ...  

又由于FileUriExposedException是由于StrictMode被强制打开所引起的代码检查异常。故网上找到另一种比较暴力的方式：
```
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
    val builder = StrictMode.VmPolicy.Builder()
    StrictMode.setVmPolicy(builder.build())
    builder.detectFileUriExposure()
}
```
