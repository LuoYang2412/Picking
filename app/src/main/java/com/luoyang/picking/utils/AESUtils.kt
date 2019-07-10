package com.luoyang.picking.utils

import android.util.Base64

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.nio.charset.StandardCharsets

object AESUtils {

    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private val sKey = "smkldospdosldaaa"//key，可自行修改
    private val ivParameter = "0392039203920300"//偏移量,可自行修改

    // 加密
    @Throws(Exception::class)
    fun encrypt(sSrc: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val raw = sKey.toByteArray()
        val skeySpec = SecretKeySpec(raw, "AES")
        val iv = IvParameterSpec(ivParameter.toByteArray())// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
        val encrypted = cipher.doFinal(sSrc.toByteArray(StandardCharsets.UTF_8))
        val encode = Base64.encode(encrypted, Base64.DEFAULT)
        return String(encode)
    }

    // 解密
    fun decrypt(sSrc: String): String? {
        try {
            val raw = sKey.toByteArray(StandardCharsets.US_ASCII)
            val skeySpec = SecretKeySpec(raw, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val iv = IvParameterSpec(ivParameter.toByteArray())
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
            val encrypted1 = Base64.decode(sSrc, Base64.DEFAULT)
            val original = cipher.doFinal(encrypted1)
            return String(original, StandardCharsets.UTF_8)
        } catch (ex: Exception) {
            return null
        }

    }
}
