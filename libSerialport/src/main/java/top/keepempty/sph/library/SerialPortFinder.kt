package top.keepempty.sph.library

import java.io.File
import java.io.FileReader
import java.io.LineNumberReader
import java.util.*

class SerialPortFinder {
    private var mDrivers: Vector<Driver>? = null

    inner class Driver(private val root: String) {
        private var mDevices: Vector<File>? = null

        fun getDevices(): Vector<File> {
            if (mDevices == null) {
                mDevices = Vector()
                val dev = File("/dev")
                val files = dev.listFiles()
                files?.run {
                    for (i in files.indices) {
                        val path = files[i].absolutePath
                        if (path.startsWith(root) && (path.startsWith("/dev/ttyS") || path.startsWith("/dev/ttyUSB"))) {
                            mDevices?.add(files[i])
                        }
                    }
                }
            }
            mDevices!!.sort()
            return mDevices!!
        }
    }

    private fun getDrivers(): Vector<Driver> {
        if (mDrivers == null) {
            mDrivers = Vector<Driver>()
            val r = LineNumberReader(FileReader("/proc/tty/drivers"))
            var l: String
            try {
                do {
                    l = r.readLine()
                    if (l == null) {
                        break
                    }
                    val w = l.split(Regex(" +")).toTypedArray()
                    if (w.size >= 5 && w[w.size - 1] == "serial") {
                        mDrivers?.add(Driver(w[w.size - 4]))
                    }
                } while (true)
                r.close()
            } catch (ignore: Exception) { }
        }
        return mDrivers!!
    }

    fun getAllDevicesPath(): Array<String> {
        val devices = Vector<String>()
        // Parse each driver
        val it: Iterator<Driver>
        val drivers = getDrivers()
        try {
            it = drivers.iterator()
            while (it.hasNext()) {
                val driver = it.next()
                val itdev = driver.getDevices().iterator()
                while (itdev.hasNext()) {
                    val device = itdev.next().absolutePath
                    devices.add(device)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return devices.toTypedArray()
    }
}