package android.util;

import android.app.ActivityManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import libcore.util.HexEncoding;

/* loaded from: classes4.dex */
public final class PackageUtils {
    private static final int HIGH_RAM_BUFFER_SIZE_BYTES = 1000000;
    private static final int LOW_RAM_BUFFER_SIZE_BYTES = 1000;

    private PackageUtils() {
    }

    public static String[] computeSignaturesSha256Digests(Signature[] signatureArr) {
        return computeSignaturesSha256Digests(signatureArr, null);
    }

    public static String[] computeSignaturesSha256Digests(Signature[] signatureArr, String str) {
        int length = signatureArr.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = computeSha256Digest(signatureArr[i].toByteArray(), str);
        }
        return strArr;
    }

    public static String computeSignaturesSha256Digest(Signature[] signatureArr) {
        if (signatureArr.length == 1) {
            return computeSha256Digest(signatureArr[0].toByteArray(), null);
        }
        return computeSignaturesSha256Digest(computeSignaturesSha256Digests(signatureArr, null));
    }

    public static String computeSignaturesSha256Digest(String[] strArr) {
        if (strArr.length == 1) {
            return strArr[0];
        }
        Arrays.sort(strArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (String str : strArr) {
            try {
                byteArrayOutputStream.write(str.getBytes());
            } catch (IOException unused) {
            }
        }
        return computeSha256Digest(byteArrayOutputStream.toByteArray(), null);
    }

    public static byte[] computeSha256DigestBytes(byte[] bArr) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public static String computeSha256Digest(byte[] bArr) {
        return computeSha256Digest(bArr, null);
    }

    public static String computeSha256Digest(byte[] bArr, String str) throws NoSuchAlgorithmException {
        byte[] bArrComputeSha256DigestBytes = computeSha256DigestBytes(bArr);
        if (bArrComputeSha256DigestBytes == null) {
            return null;
        }
        if (str == null) {
            return HexEncoding.encodeToString(bArrComputeSha256DigestBytes, true);
        }
        int length = bArrComputeSha256DigestBytes.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = HexEncoding.encodeToString(bArrComputeSha256DigestBytes[i], true);
        }
        return TextUtils.join(str, strArr);
    }

    public static byte[] createLargeFileBuffer() {
        return new byte[ActivityManager.isLowRamDeviceStatic() ? 1000 : 1000000];
    }

    public static byte[] computeSha256DigestForLargeFileAsBytes(String str, byte[] bArr) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
            messageDigest.reset();
            try {
                DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(new File(str)), messageDigest);
                do {
                    try {
                    } finally {
                    }
                } while (digestInputStream.read(bArr) != -1);
                digestInputStream.close();
                return messageDigest.digest();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (NoSuchAlgorithmException unused) {
        }
    }

    public static String computeSha256DigestForLargeFile(String str, byte[] bArr) {
        return computeSha256DigestForLargeFile(str, bArr, null);
    }

    public static String computeSha256DigestForLargeFile(String str, byte[] bArr, String str2) throws NoSuchAlgorithmException {
        byte[] bArrComputeSha256DigestForLargeFileAsBytes = computeSha256DigestForLargeFileAsBytes(str, bArr);
        if (str2 == null) {
            return HexEncoding.encodeToString(bArrComputeSha256DigestForLargeFileAsBytes, false);
        }
        int length = bArrComputeSha256DigestForLargeFileAsBytes.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = HexEncoding.encodeToString(bArrComputeSha256DigestForLargeFileAsBytes[i], true);
        }
        return TextUtils.join(str2, strArr);
    }
}
