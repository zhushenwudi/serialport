package top.keepempty.sph.library

import androidx.annotation.Keep

@Keep
interface SerialListener {
    /**
     * Called when new incoming data is available.
     */
    fun onNewData(data: ByteArray?)

    /**
     * Called when [run] aborts due to an error.
     */
    fun onRunError(e: Exception?)
}