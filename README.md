# SerialPortHelper

三年了，你知道我这三年是怎么过来的吗？

基于大量项目背景所优化的串口方案他来了

#### 项目说明
1.本程序基于不支持创建多实例的原项目 https://github.com/freyskill/SerialPortHelper 根据issue #2中源码修改的 dearchun大神 所提供的lib包制作。  

2.由于原SerialPortHelper Java程序在数据量巨大时存在anr情况，在Kotlin语言以及由谷歌带来的Jetpack架构中协程的强力支持下，根据项目需要，制作了一款多并发、高可用、扩展性强的串口库。

3.本案例分别使用java和kotlin同时编写，方便大家参考与快速集成。

#### 接入方式
1.在 project build.gradle 中添加

```groovy
repositories {
   ...
   maven { url "https://jitpack.io" }
}
```

2.在 app build.gradle 中添加

```groovy
dependencies {
    ...
    implementation 'com.github.zhushenwudi:serialport:1.5'
}
```

3.导入对应平台的 so 库

    app/src/main/jniLibs/xxx/libSerialPortLib.so

#### 示例
1.配置串口通信协议

kotlin代码如下:

    示例采用单例模式，文件位于 com.zhushenwudi.app.BalanceConfig

```kotlin
object BalanceConfig: SerialPortConfig(){
    fun getBalanceConfig(): BalanceConfig {
        mode = 0			// 是否使用原始模式(Raw Mode)方式来通讯
        path = "dev/ttyS3"		// 串口地址 [ttyS0 ~ ttyS6, ttyUSB0 ~ ttyUSB4]
        baudRate = 9600			// 波特率
        dataBits = 8			// 数据位 [7, 8]
        parity = 'n'			// 检验类型 [N(无校验) ,E(偶校验), O(奇校验)] (大小写随意)
        stopBits = 1			// 停止位 [1, 2]
        return this
    }
}
```
java代码如下:

    示例采用单例模式，文件位于 com.zhushenwudi.app.RfidConfig

```java
class RfidConfig extends SerialPortConfig {
	private getConfig() {
	    setMode(0);                  // 是否使用原始模式(Raw Mode)方式来通讯
	    setPath("dev/ttyS1");        // 串口地址 [ttyS0 ~ ttyS6, ttyUSB0 ~ ttyUSB4]
	    setBaudRate(19200);          // 波特率
	    setDataBits(8);              // 数据位 [7, 8]
	    setParity('n');              // 检验类型 [N(无校验) ,E(偶校验), O(奇校验)] (大小写随意)
	    setStopBits(1);              // 停止位 [1, 2]
	    return this;
	}
	...
}
```

2.创建串口通信类

kotlin代码如下:

    详看com.zhushenwudi.app.BalanceHelper

```kotlin
// 1. 将类实现SerialListener接口
class BalanceHelper: SerialListener

// 2. 初始化
serialPortHelper = SerialPortHelper(
    20,     // 每次接受数据的最大长度
    this,   // 回调所在的上下文
    config  // 通信配置
)

// 3. 打开串口，返回为true表示打开成功
serialPortHelper.openDevice()

// 4. 串口回调
override fun onNewData(data: ByteArray?) {
    // 数据回调位置
}

override fun onRunError(e: Exception?) {
    // 异常回调位置
}

// 5. 发送串口数据
serialPortHelper.sendHex("要发送的16进制字符串")

// 6. 判断串口实例是否创建（是否正在运行）
serialPortHelper.isRunning()
```

java代码如下:

    详看com.zhushenwudi.app.RfidHelper

```java
// 1. 将类实现SerialListener接口
public class RfidHelper implements SerialListener {}

// 2. 初始化
serialPortHelper = new SerialPortHelper(
	15, 	// 每次接受数据的最大长度
	new SerialListener() {
	    @Override
	    public void onNewData(@Nullable byte[] data) {
	        // 数据回调位置
	    }
	
	    @Override
	    public void onRunError(@Nullable Exception e) {
	        // 异常回调位置
	    }
	},
	RfidConfig.getInstance(),  // 通信配置
	false
);

// 3. 打开串口，返回为true表示打开成功
serialPortHelper.openDevice();

// 4. 发送串口数据
serialPortHelper.sendHex("要发送的16进制字符串");

// 5. 判断串口实例是否创建（是否正在运行）
serialPortHelper.isRunning();
```

3.Activity调用

kotlin代码如下:

    详看com.zhushenwudi.app.KotlinActivity

```kotlin
// 声明变量
private val balanceApi by lazy {
    BalanceHelper({
        Log.d(TAG, "balance: $it")
    }, {
        Log.e(TAG, "balance: $it")
    })
}
        
// 启动设备 + 循环发送
balanceApi.start()

// 停止发送 + 关闭设备
balanceApi.stop()
```

java代码如下:

    详看com.zhushenwudi.app.JavaActivity

```java
// 声明变量
rfidApi = new RfidHelper().setCallback(new RfidCallback() {
    @Override
    public void callback(@NonNull String result) {
        Log.d(TAG, "rfid: " + result);
    }

    @Override
    public void message(@NonNull String message) {
        Log.e(TAG, "rfid: " + message);
    }
});

// 启动设备 + 循环发送
rfidApi.start()

// 停止发送 + 关闭设备
rfidApi.stop()
```

4.Log截图

![Image text](https://raw.githubusercontent.com/zhushenwudi/serialport/master/example.png)

### 注意事项

1. 本项目无需添加任何混淆规则
2. 请在 jniLibs 目录放入对应 abi 的so文件（根据自己的设备酌情导入来减少打包的大小）

## 许可证

[MIT](https://github.com/zhushenwudi/serialport/blob/master/LICENSE)
