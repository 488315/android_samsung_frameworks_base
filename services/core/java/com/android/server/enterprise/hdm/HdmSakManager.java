package com.android.server.enterprise.hdm;

import android.content.Context;
import android.security.KeyStore2;
import android.security.keystore.ArrayUtils;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;

import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.security.securekeyblob.SecureKeyGenParameterSpec;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class HdmSakManager {
    public static byte[] constructTLV(X509Certificate[] x509CertificateArr, byte[] bArr) {
        if (x509CertificateArr.length == 3) {
            return makeTLV(
                    (byte) 19,
                    makeTLV(
                            (byte) 18,
                            makeTLV(
                                    (byte) 17,
                                    "SAK:".getBytes(StandardCharsets.UTF_8),
                                    x509CertificateArr[1].getEncoded()),
                            x509CertificateArr[0].getEncoded()),
                    bArr);
        }
        throw new Exception("Invalid key length: " + x509CertificateArr.length);
    }

    public static SecureKeyGenParameterSpec genKeySpec() {
        byte[] bytes = "tz_hdm".getBytes();
        Set emptySet = Collections.emptySet();
        if (bytes == null) {
            throw new NullPointerException("serviceName == null");
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(random.nextLong());
        sb.append(random.nextLong());
        sb.append(random.nextLong());
        sb.append(random.nextLong());
        return new SecureKeyGenParameterSpec(
                bytes,
                256,
                ArrayUtils.cloneIfNotEmpty(new String[] {"SHA-256"}),
                emptySet,
                null,
                null,
                null,
                sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] getImeiHash(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        String primaryImei = telephonyManager.getPrimaryImei();
        String secondaryImei = telephonyManager.getSecondaryImei();
        StringBuilder sb = new StringBuilder("activeModem count: ");
        sb.append(telephonyManager.getActiveModemCount());
        sb.append(", Primary exist ? ");
        sb.append(primaryImei != null);
        sb.append(", Secondary exist ?  ");
        FlashNotificationsController$$ExternalSyntheticOutline0.m(
                "HDM - HdmSakManager", sb, secondaryImei != null);
        return hash(
                primaryImei != null ? primaryImei.getBytes(StandardCharsets.UTF_8) : null,
                secondaryImei != null ? secondaryImei.getBytes(StandardCharsets.UTF_8) : null);
    }

    public static String getUniqueNumber() {
        String read =
                BatteryService$$ExternalSyntheticOutline0.m45m(Constants.UFS_UN_R)
                        ? read(Constants.UFS_UN_R, "UFS_UN_R")
                        : BatteryService$$ExternalSyntheticOutline0.m45m(Constants.UFS_UN)
                                ? read(Constants.UFS_UN, "UFS_UN")
                                : BatteryService$$ExternalSyntheticOutline0.m45m(
                                                Constants.EMMC_UN_R)
                                        ? read(Constants.EMMC_UN_R, "EMMC_UN_R")
                                        : BatteryService$$ExternalSyntheticOutline0.m45m(
                                                        Constants.EMMC_UN)
                                                ? read(Constants.EMMC_UN, "EMMC_UN")
                                                : null;
        if (read != null) {
            return read.toUpperCase();
        }
        Log.e("HDM - HdmSakManager", "can't find SN ID");
        return null;
    }

    public static byte[] hash(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            Log.e("HDM - HdmSakManager", "input1 null is not allowed");
            return null;
        }
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(bArr, 0, bArr.length);
        if (bArr2 != null) {
            messageDigest.update(bArr2, 0, bArr2.length);
        }
        return messageDigest.digest();
    }

    public static boolean isSupported(Context context) {
        boolean z = false;
        try {
            if (Integer.parseInt("34") < 35) {
                return false;
            }
            KeyStore2.getInstance();
            z = context.getPackageManager().hasSystemFeature("samsung.software.secure_key_service");
            Log.i("HDM - HdmSakManager", "isSupportSecureKeyService: " + z);
            return z;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "isSupported failed: ", "HDM - HdmSakManager");
            return z;
        }
    }

    public static byte[] makeTLV(byte b, byte[] bArr, byte[] bArr2) {
        int length = bArr2.length;
        Log.i(
                "HDM - HdmSakManager",
                "makeTLV: tag: " + Integer.toHexString(b) + ", len: " + length);
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
        Log.i("HDM - HdmSakManager", "makeTLV tlvLen: " + byteArray.length);
        byteArrayOutputStream.close();
        return byteArray;
    }

    public static String read(String str, String str2) {
        Log.i("HDM - HdmSakManager", "SN target: ".concat(str2));
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(str, StandardCharsets.UTF_8));
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
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "read Exception ", "HDM - HdmSakManager");
            return null;
        }
    }
}
