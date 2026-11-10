import JSEncrypt from "jsencrypt"
import CryptoJS from "crypto-js"
import { RSA_PUBLIC_KEY } from "./rsa-key"
/**
 * 生成 AES-128 Key（Base64）
 */
function generateAesKey() {
  return CryptoJS.enc.Base64.stringify(
    CryptoJS.lib.WordArray.random(16)
  )
}

/**
 * AES 加密（ECB + PKCS7）
 */
function encryptAES(plainText, base64Key) {
  const key = CryptoJS.enc.Base64.parse(base64Key)
  const encrypted = CryptoJS.AES.encrypt(plainText, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  })
  return encrypted.toString()
    .replace(/\+/g, '-')
    .replace(/\//g, '_')
    .replace(/=+$/, '')
}

/**
 * AES 解密
 */
function decryptAES(cipherText, base64Key) {
  const key = CryptoJS.enc.Base64.parse(base64Key)
  const decrypted = CryptoJS.AES.decrypt(cipherText, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  })
  return decrypted.toString(CryptoJS.enc.Utf8)
}

/**
 * RSA 加密 AES Key
 */
function encryptRSA(data) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(RSA_PUBLIC_KEY)
  const result = encryptor.encrypt(data)
  if (!result) {
    throw new Error("RSA encrypt failed")
  }
  return result
}

function base64UrlEncode(str) {
  return str
    .replace(/\+/g, '-')
    .replace(/\//g, '_')
    .replace(/=+$/, '')
}

export default {
  generateAesKey,
  encryptAES,
  decryptAES,
  encryptRSA,
  base64UrlEncode
}