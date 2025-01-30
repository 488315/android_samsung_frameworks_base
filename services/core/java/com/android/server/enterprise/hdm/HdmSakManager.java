package com.android.server.enterprise.hdm;

import android.content.Context;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.security.securekeyblob.SecureKeyGenParameterSpec;
import com.samsung.security.securekeyblob.SecureKeyGenerator;
import com.samsung.security.securekeyblob.SecureKeyResult;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;

/* loaded from: classes2.dex */
public abstract class HdmSakManager {
    public static final String TAG = "HDM - " + HdmSakManager.class.getSimpleName();

    public static boolean isSupported(Context context) {
        try {
            boolean isSupportSecureKeyService = new SecureKeyGenerator().isSupportSecureKeyService(context);
            Log.i(TAG, "isSupportSecureKeyService: " + isSupportSecureKeyService);
            return false;
        } catch (Exception e) {
            Log.i(TAG, "isSupported failed: " + e);
            return false;
        }
    }

    public static byte[] generateHdmKey() {
        try {
            SecureKeyResult generateKeyPair = new SecureKeyGenerator().generateKeyPair(genKeySpec());
            byte[] serviceKey = generateKeyPair.getServiceKey();
            X509Certificate[] certificates = generateKeyPair.getCertificates();
            Log.i(TAG, "serviceId: " + new String(generateKeyPair.getServiceID()) + ", certLen: " + certificates.length + ", keyLen: " + serviceKey.length);
            return constructTLV(certificates, serviceKey);
        } catch (Exception e) {
            Log.e(TAG, "generateHdmKey failed: " + e, e);
            return null;
        }
    }

    public static SecureKeyGenParameterSpec genKeySpec() {
        return new SecureKeyGenParameterSpec.Builder("tz_hdm".getBytes(), "EC").build();
    }

    public static byte[] getHashedImei(Context context) {
        try {
            if (isWifiOnly()) {
                return getMacHash(context);
            }
            return getImeiHash(context);
        } catch (Exception e) {
            Log.e(TAG, "getHashedImei failed: " + e);
            return null;
        }
    }

    public static byte[] getHashedUniqueNumber() {
        try {
            String uniqueNumber = getUniqueNumber();
            return hash(uniqueNumber != null ? uniqueNumber.getBytes(StandardCharsets.UTF_8) : null, null);
        } catch (Exception e) {
            Log.e(TAG, "getHashedUniqueNumber failed: " + e);
            return null;
        }
    }

    public static byte[] constructTLV(X509Certificate[] x509CertificateArr, byte[] bArr) {
        if (x509CertificateArr.length != 3) {
            throw new Exception("Invalid key length: " + x509CertificateArr.length);
        }
        return makeTLV(makeTLV(makeTLV("SAK:".getBytes(StandardCharsets.UTF_8), (byte) 17, x509CertificateArr[1].getEncoded()), (byte) 18, x509CertificateArr[0].getEncoded()), (byte) 19, bArr);
    }

    public static byte[] makeTLV(byte[] bArr, byte b, byte[] bArr2) {
        int length = bArr2.length;
        String str = TAG;
        Log.i(str, "makeTLV: tag: " + Integer.toHexString(b) + ", len: " + length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bArr != null) {
            try {
                byteArrayOutputStream.write(bArr);
            } catch (Throwable th) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        byteArrayOutputStream.write(b);
        byteArrayOutputStream.write((byte) length);
        byteArrayOutputStream.write((byte) (length >>> 8));
        byteArrayOutputStream.write(bArr2, 0, length);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Log.i(str, "makeTLV tlvLen: " + byteArray.length);
        byteArrayOutputStream.close();
        return byteArray;
    }

    public static byte[] getMacHash(Context context) {
        String factoryMacAddress = ((SemWifiManager) context.getSystemService("sem_wifi")).getFactoryMacAddress();
        return hash(factoryMacAddress != null ? factoryMacAddress.getBytes(StandardCharsets.UTF_8) : null, null);
    }

    public static byte[] getImeiHash(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        String primaryImei = telephonyManager.getPrimaryImei();
        String secondaryImei = telephonyManager.getSecondaryImei();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("activeModem count: ");
        sb.append(telephonyManager.getActiveModemCount());
        sb.append(", Primary exist ? ");
        sb.append(primaryImei != null);
        sb.append(", Secondary exist ?  ");
        sb.append(secondaryImei != null);
        Log.i(str, sb.toString());
        return hash(primaryImei != null ? primaryImei.getBytes(StandardCharsets.UTF_8) : null, secondaryImei != null ? secondaryImei.getBytes(StandardCharsets.UTF_8) : null);
    }

    public static byte[] hash(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            Log.e(TAG, "input1 null is not allowed");
            return null;
        }
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(bArr, 0, bArr.length);
        if (bArr2 != null) {
            messageDigest.update(bArr2, 0, bArr2.length);
        }
        return messageDigest.digest();
    }

    public static String getUniqueNumber() {
        String read;
        if (isExistFile("/sys/class/sec/ufs/un")) {
            read = read("/sys/class/sec/ufs/un", "UFS_UN_R");
        } else if (isExistFile("/sys/class/scsi_host/host0/unique_number")) {
            read = read("/sys/class/scsi_host/host0/unique_number", "UFS_UN");
        } else if (isExistFile("/sys/class/sec/mmc/un")) {
            read = read("/sys/class/sec/mmc/un", "EMMC_UN_R");
        } else {
            read = isExistFile("/sys/block/mmcblk0/device/unique_number") ? read("/sys/block/mmcblk0/device/unique_number", "EMMC_UN") : null;
        }
        if (read != null) {
            return read.toUpperCase();
        }
        Log.e(TAG, "can't find SN ID");
        return null;
    }

    public static boolean isExistFile(String str) {
        return new File(str).exists();
    }

    public static String read(String str, String str2) {
        Log.i(TAG, "SN target: " + str2);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str, StandardCharsets.UTF_8));
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    return null;
                }
                String trim = readLine.trim();
                bufferedReader.close();
                return trim;
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "read Exception " + e);
            return null;
        }
    }

    public static boolean isWifiOnly() {
        return "wifi-only".equalsIgnoreCase(SystemProperties.get("ro.carrier", "").trim()) || "yes".equalsIgnoreCase(SystemProperties.get("ro.radio.noril", "").trim());
    }
}
