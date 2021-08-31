package top.keepempty.sph.library

import kotlinx.coroutines.*

class SphThreads(
    serialPort: SerialPortJNI,
    processingData: SphDataProcess?
) {
    private var scope: CoroutineScope? = null

    init {
        scope = CoroutineScope(Dispatchers.IO)
        scope?.launch {
            while (isActive) {
                // 读取数据
                val bytes = processingData?.getMaxSize()?.let { serialPort.readPort(it) }
                if (bytes != null && bytes.isNotEmpty()) {
                    processingData.processingRecData(bytes, bytes.size)
                }
            }
        }
    }

    fun stop() {
        scope?.cancel()
        scope = null
    }
}