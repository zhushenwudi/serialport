package top.keepempty.sph.library

import java.util.*

/**
 * 数据转换工具
 *
 * @author ：frey
 * @date：2019/4/03 16:15
 */
object DataConversion {
    /**
     * 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
     *
     * @param num
     * @return
     */
    fun isOdd(num: Int): Int {
        return num and 0x1
    }

    /**
     * 将int转成byte
     *
     * @param number
     * @return
     */
    fun intToByte(number: Int): Byte {
        return hexToByte(intToHex(number))
    }

    /**
     * 将int转成hex字符串
     *
     * @param number
     * @return
     */
    fun intToHex(number: Int): String {
        val st = Integer.toHexString(number).uppercase(Locale.getDefault())
        return String.format("%2s", st).replace(" ".toRegex(), "0")
    }

    /**
     * 字节转十进制
     *
     * @param b
     * @return
     */
    fun byteToDec(b: Byte): Int {
        val s = byteToHex(b)
        return hexToDec(s).toInt()
    }

    /**
     * 字节数组转十进制
     *
     * @param bytes
     * @return
     */
    fun bytesToDec(bytes: ByteArray?): Int {
        val s = encodeHexString(bytes)
        return hexToDec(s).toInt()
    }

    /**
     * Hex字符串转int
     *
     * @param inHex
     * @return
     */
    fun hexToInt(inHex: String): Int {
        return inHex.toInt(16)
    }

    /**
     * 字节转十六进制字符串
     *
     * @param num
     * @return
     */
    fun byteToHex(num: Byte): String {
        val hexDigits = CharArray(2)
        hexDigits[0] = Character.forDigit(num.toInt() shr 4 and 0xF, 16)
        hexDigits[1] = Character.forDigit(num.toInt() and 0xF, 16)
        return String(hexDigits).uppercase(Locale.getDefault())
    }

    /**
     * 十六进制转byte字节
     *
     * @param hexString
     * @return
     */
    fun hexToByte(hexString: String): Byte {
        val firstDigit = toDigit(hexString[0])
        val secondDigit = toDigit(hexString[1])
        return ((firstDigit shl 4) + secondDigit).toByte()
    }

    private fun toDigit(hexChar: Char): Int {
        val digit = Character.digit(hexChar, 16)
        require(digit != -1) { "Invalid Hexadecimal Character: $hexChar" }
        return digit
    }

    /**
     * 字节数组转十六进制
     *
     * @param byteArray
     * @return
     */
    fun encodeHexString(byteArray: ByteArray?): String {
        if (byteArray == null) {
            return ""
        }
        val hexStringBuffer = StringBuffer()
        for (i in byteArray.indices) {
            hexStringBuffer.append(byteToHex(byteArray[i]))
        }
        return hexStringBuffer.toString().uppercase(Locale.getDefault())
    }

    /**
     * 十六进制转字节数组
     *
     * @param hexString
     * @return
     */
    fun decodeHexString(hexString: String): ByteArray {
        require(hexString.length % 2 != 1) { "Invalid hexadecimal String supplied." }
        val bytes = ByteArray(hexString.length / 2)
        var i = 0
        while (i < hexString.length) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2))
            i += 2
        }
        return bytes
    }

    /**
     * 十进制转十六进制
     *
     * @param dec
     * @return
     */
    fun decToHex(dec: Int): String {
        var hex = Integer.toHexString(dec)
        if (hex.length == 1) {
            hex = "0$hex"
        }
        return hex.lowercase(Locale.getDefault())
    }

    /**
     * 十六进制转十进制
     *
     * @param hex
     * @return
     */
    fun hexToDec(hex: String): Long {
        return hex.toLong(16)
    }

    /**
     * 十六进制转十进制数组
     * @param hex
     * @return
     */
    fun hexStringToIntArray(hex: String): IntArray? {
        if (hex.length % 2 != 0) {
            return null
        }
        val arr = IntArray(hex.length / 2)
        for (i in 0 until hex.length / 2) {
            arr[i] = hexToInt(hex.substring(2 * i, 2 * (i + 1)))
        }
        return arr
    }

    /**
     * 字节数组转十六进制
     * @param src
     * @param offset
     * @param length
     * @return
     */
    fun bytesToHexString(src: ByteArray, offset: Int, length: Int): String? {
        val stringBuilder = StringBuilder("")
        if (src.isEmpty()) {
            return null
        }
        for (i in offset until offset + length) {
            val v: Int = src[i].toInt() and 0xFF
            val hv = Integer.toHexString(v)
            if (hv.length == 1) {
                stringBuilder.append(0)
            }
            stringBuilder.append(hv)
        }
        return stringBuilder.toString().toUpperCase()
    }

    /**
     * 十六进制转字符数组
     * @param hexString
     * @return
     */
    fun hexStringToBytes(hexString: String): ByteArray? {
        if (hexString.isEmpty()) {
            return null
        }
        val hexStr = hexString.toUpperCase()
        val length = hexStr.length / 2
        val hexChars = hexStr.toCharArray()
        val d = ByteArray(length)
        for (i in 0 until length) {
            val pos = i * 2
            d[i] = (charToByte(hexChars[pos]).toInt() shl 4 or charToByte(hexChars[pos + 1]).toInt()).toByte()
        }
        return d
    }

    /**
     * 字符转字节
     * @param char
     * @return
     */
    private fun charToByte(char: Char): Byte {
        return "0123456789ABCDEF".indexOf(char).toByte()
    }
}