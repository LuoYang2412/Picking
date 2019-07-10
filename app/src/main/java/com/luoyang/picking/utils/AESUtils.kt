package com.luoyang.picking.utils

import android.util.Base64
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtils {
    //密钥长度
    private val KEY_LENGTH = 16
    // 默认填充位数
    private val DEFAULT_VALUE = "0"
    //加密的秘钥,最安全的方式是随机字符串
    private val key = "12Duoi7687F"

    @Throws(Exception::class)
    fun encrypt(/*String key, */src: String): String {
        var src = src
        // 对源数据进行Base64编码
        src = Base64.encodeToString(src.toByteArray(), Base64.DEFAULT)
        // 补全KEY为16位
        val rawKey = toMakeKey(key, KEY_LENGTH, DEFAULT_VALUE).toByteArray()
        // 获取加密后的字节数组
        var result = getBytes(rawKey, src.toByteArray(StandardCharsets.UTF_8), Cipher.ENCRYPT_MODE)
        // 对加密后的字节数组进行Base64编码
        result = Base64.encode(result, Base64.DEFAULT)
        // 返回字符串
        return String(result, Charset.defaultCharset())
    }

    @Throws(Exception::class)
    fun decrypt(/*String key, */encrypted: String): String {
        // 补全KEY为16位
        val rawKey = toMakeKey(key, KEY_LENGTH, DEFAULT_VALUE).toByteArray()
        // 获取加密后的二进制字节数组
        var enc = encrypted.toByteArray(Charset.defaultCharset())
        // 对二进制数组进行Base64解码
        enc = Base64.decode(enc, Base64.DEFAULT)
        // 获取解密后的二进制字节数组
        var result = getBytes(rawKey, enc, Cipher.DECRYPT_MODE)
        // 对解密后的二进制数组进行Base64解码
        result = Base64.decode(result, Base64.DEFAULT)
        // 返回字符串
        return String(result, StandardCharsets.UTF_8)
    }

    /**
     * 密钥key ,默认补的数字，补全16位数，以保证安全补全至少16位长度,android和ios对接通过
     *
     * @param key    密钥key
     * @param length 密钥应有的长度
     * @param text   默认补的文本
     * @return 密钥
     */
    private fun toMakeKey(key: String, length: Int, text: String): String {
        var key = key
        // 获取密钥长度
        val strLen = key.length
        // 判断长度是否小于应有的长度
        if (strLen < length) {
            // 补全位数
            val builder = StringBuilder()
            // 将key添加至builder中
            builder.append(key)
            // 遍历添加默认文本
            for (i in 0 until length - strLen) {
                builder.append(text)
            }
            // 赋值
            key = builder.toString()
        }
        return key
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        InvalidAlgorithmParameterException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    private fun getBytes(key: ByteArray, src: ByteArray, mode: Int): ByteArray {
        // 密钥规格
        val secretKeySpec = SecretKeySpec(key, "AES")
        // 密钥实例
        val cipher = Cipher.getInstance("AES")
        // 初始化密钥模式
        cipher.init(mode, secretKeySpec, IvParameterSpec(ByteArray(cipher.blockSize)))
        // 加密数据
        return cipher.doFinal(src)
    }
}
