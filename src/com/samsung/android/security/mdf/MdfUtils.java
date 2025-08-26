package com.samsung.android.security.mdf;

import android.provider.DocumentsContract;
import android.security.keystore2.AndroidKeyStoreSpi;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes6.dex */
public class MdfUtils {
    public static final int AUDIT_LOG_ALERT = 1;
    public static final int AUDIT_LOG_CRITICAL = 2;
    public static final int AUDIT_LOG_ERROR = 3;
    public static final int AUDIT_LOG_GROUP_APPLICATION = 5;
    public static final int AUDIT_LOG_GROUP_EVENTS = 4;
    public static final int AUDIT_LOG_GROUP_NETWORK = 3;
    public static final int AUDIT_LOG_GROUP_SECURITY = 1;
    public static final int AUDIT_LOG_GROUP_SYSTEM = 2;
    public static final int AUDIT_LOG_NOTICE = 5;
    public static final int AUDIT_LOG_WARNING = 4;
    private static final String[] BAD_COUNTRY_2LDS;
    public static final String KEYPROP_BLOCK_MODE_GCM = "GCM";
    public static final String KEYPROP_ENCRYPTION_PADDING_NONE = "NoPadding";
    public static final String KEYPROP_KEY_ALGORITHM_AES = "AES";
    public static final int KEYPROP_PURPOSE_DECRYPT = 2;
    public static final int KEYPROP_PURPOSE_ENCRYPT = 1;
    public static final String MDF_CIPHER_MODE = "AES/GCM/NoPadding";
    public static final int MDF_IV_LENGTH = 12;
    public static final int MDF_KEY_SIZE = 32;
    public static final int MDF_TAG_LENGTH = 16;

    public static native boolean isMdfApplied();

    public static native boolean isMdfDisabled();

    public static native boolean isMdfEnabled();

    public static native boolean isMdfEnforced();

    public static native boolean isMdfReady();

    public static native boolean isMdfSupported();

    public static native int updateMdfStatus();

    public static native String updateMdfVersion();

    public native int FIPS_Openssl_SelfTest();

    public native int getCCModeFlag();

    public native int getSBFlag();

    public native int setCCModeFlag(int i);

    public native int setSBFlagOff();

    public native int setSBFlagOn();

    static {
        try {
            System.loadLibrary("mdf");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Could not link the library. Error: " + e.getMessage());
        }
        String[] strArr = {"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", DocumentsContract.EXTRA_INFO, "lg", "ne", "net", "or", "org"};
        BAD_COUNTRY_2LDS = strArr;
        Arrays.sort(strArr);
    }

    public static int getPid() {
        try {
            return ((Integer) Class.forName("android.os.Process").getMethod("myPid", null).invoke(null, null)).intValue();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            return -1;
        }
    }

    public static int getUid() {
        try {
            return ((Integer) Class.forName("android.os.Process").getMethod("myUid", null).invoke(null, null)).intValue();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            return -1;
        }
    }

    public static String getName() throws Throwable {
        FileReader fileReader;
        Throwable th;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader("/proc/" + getPid() + "/cmdline");
            try {
                bufferedReader = new BufferedReader(fileReader);
            } catch (Exception unused) {
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
            }
        } catch (Exception unused2) {
            bufferedReader = null;
            fileReader = null;
        } catch (Throwable th3) {
            fileReader = null;
            th = th3;
            bufferedReader = null;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                int i = bufferedReader.read();
                if (i > 0) {
                    stringBuffer.append((char) i);
                } else {
                    String str = new String(stringBuffer);
                    try {
                        bufferedReader.close();
                        fileReader.close();
                        return str;
                    } catch (IOException e) {
                        System.err.println("MdfUtils::getName encountered an exception: " + e.getMessage());
                        return str;
                    }
                }
            }
        } catch (Exception unused3) {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    System.err.println("MdfUtils::getName encountered an exception: " + e2.getMessage());
                    return null;
                }
            }
            if (fileReader != null) {
                fileReader.close();
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e3) {
                    System.err.println("MdfUtils::getName encountered an exception: " + e3.getMessage());
                    throw th;
                }
            }
            if (fileReader != null) {
                fileReader.close();
            }
            throw th;
        }
    }

    public static void logMdf(boolean z, String str, boolean z2, int i, String str2) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        logMdf(z, str, null, z2, i, str2);
    }

    public static void logMdf(boolean z, String str, String str2, boolean z2, int i, String str3) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (z) {
            try {
                Class.forName("android.sec.enterprise.EnterpriseDeviceManager");
                Class.forName("android.sec.enterprise.auditlog.AuditLog").getMethod("logPrivileged", Integer.TYPE, Integer.TYPE, Boolean.TYPE, Integer.TYPE, String.class, String.class, String.class).invoke(null, Integer.valueOf(i), 3, Boolean.valueOf(z2), Integer.valueOf(getPid()), str3, str, str2);
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.err.println("MdfUtils::AuditLog encountered an exception: " + e.getMessage());
            }
        }
    }

    public static void logMdf(String str, boolean z, int i, String str2) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        logMdf(str, (String) null, z, i, str2);
    }

    public static void logMdf(String str, String str2, boolean z, int i, String str3) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        logMdf(isMdfEnforced(), str, str2, z, i, str3);
    }

    public static String buildHostnameLog(String str, X509Certificate x509Certificate) throws CertificateParsingException {
        if (x509Certificate == null) {
            return "Certificate not presented";
        }
        StringBuilder sb = new StringBuilder("Identifier verification failed. Presented identifier: ");
        sb.append(str);
        sb.append(" List of reference identifiers: ");
        X500Principal subjectX500Principal = x509Certificate.getSubjectX500Principal();
        sb.append(subjectX500Principal == null ? "" : subjectX500Principal.getName());
        sb.append(" ");
        try {
        } catch (CertificateParsingException unused) {
            sb.append("list of subject alternative names is not available");
        }
        if (x509Certificate.getSubjectAlternativeNames() == null) {
            throw new CertificateParsingException("No SANs available");
        }
        int i = 0;
        for (List<?> list : x509Certificate.getSubjectAlternativeNames()) {
            sb.append(i + ") ");
            sb.append(list.get(1));
            sb.append("; ");
            i++;
        }
        return sb.toString();
    }

    private static boolean acceptableCountryWildcard(String str) {
        int length = str.length();
        if (length >= 7 && length <= 9) {
            int i = length - 3;
            if (str.charAt(i) == '.') {
                return Arrays.binarySearch(BAD_COUNTRY_2LDS, str.substring(2, i)) < 0;
            }
        }
        return true;
    }

    public static boolean isHostnameAllowed(String str, String str2) {
        return str2.indexOf(46, 2) != str2.length() - 1 && acceptableCountryWildcard(str2.substring(0, str2.length() - 1));
    }

    public static boolean isCertificateAllowed(X500Principal[] x500PrincipalArr, X509Certificate[] x509CertificateArr) {
        if (x509CertificateArr == null || x509CertificateArr.length == 0) {
            return false;
        }
        if (x500PrincipalArr == null || x500PrincipalArr.length == 0) {
            return true;
        }
        for (int i = 0; i < x509CertificateArr.length; i++) {
            if (x509CertificateArr[i] != null) {
                for (X500Principal x500Principal : x500PrincipalArr) {
                    if (x500Principal != null && x500Principal.equals(x509CertificateArr[i].getIssuerX500Principal())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static byte[] encryptMdf(byte[] bArr, String str) throws IllegalAccessException, NoSuchMethodException, IllegalBlockSizeException, IOException, KeyStoreException, CertificateException, IllegalArgumentException, InvocationTargetException, InvalidAlgorithmParameterException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InstantiationException, ClassNotFoundException, SecurityException, InvalidKeyException, NoSuchProviderException {
        try {
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
            keyStore.load(null);
            SecretKey secretKeyGenerateKey = (SecretKey) keyStore.getKey(str, null);
            if (secretKeyGenerateKey == null) {
                Class<?> cls = Class.forName("android.security.keystore.KeyGenParameterSpec$Builder");
                AlgorithmParameterSpec algorithmParameterSpec = (AlgorithmParameterSpec) cls.getMethod("build", null).invoke(cls.getMethod("setEncryptionPaddings", String[].class).invoke(cls.getMethod("setKeySize", Integer.TYPE).invoke(cls.getMethod("setBlockModes", String[].class).invoke(cls.getDeclaredConstructor(String.class, Integer.TYPE).newInstance(str, 3), new String[]{"GCM"}), 256), new String[]{"NoPadding"}), null);
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", AndroidKeyStoreSpi.NAME);
                keyGenerator.init(algorithmParameterSpec);
                secretKeyGenerateKey = keyGenerator.generateKey();
            }
            Cipher cipher = Cipher.getInstance(MDF_CIPHER_MODE);
            cipher.init(1, secretKeyGenerateKey);
            byte[] bArrDoFinal = cipher.doFinal(bArr);
            byte[] bArr2 = new byte[bArrDoFinal.length + 12];
            for (int i = 0; i < 12; i++) {
                bArr2[i] = cipher.getIV()[i];
            }
            for (int i2 = 0; i2 < bArrDoFinal.length; i2++) {
                bArr2[i2 + 12] = bArrDoFinal[i2];
            }
            return bArr2;
        } catch (Exception e) {
            System.err.println("MDFUtils::Got exception during MDF encryption: " + e.getMessage());
            return null;
        }
    }

    public static byte[] decryptMdf(byte[] bArr, String str) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, KeyStoreException, CertificateException, InvalidAlgorithmParameterException {
        if (bArr.length <= 28) {
            System.err.println("MDFUtils::MDF decryption failed, invalid encryption length");
            return null;
        }
        try {
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
            keyStore.load(null);
            SecretKey secretKey = (SecretKey) keyStore.getKey(str, null);
            if (secretKey == null) {
                System.err.println("MDFUtils::MDF decryption failed, unable to get encryption key from AndroidKeystore");
                return null;
            }
            int length = bArr.length - 12;
            byte[] bArr2 = new byte[12];
            byte[] bArr3 = new byte[length];
            for (int i = 0; i < 12; i++) {
                bArr2[i] = bArr[i];
            }
            for (int i2 = 0; i2 < length; i2++) {
                bArr3[i2] = bArr[i2 + 12];
            }
            Cipher cipher = Cipher.getInstance(MDF_CIPHER_MODE);
            cipher.init(2, secretKey, new GCMParameterSpec(128, bArr2));
            return cipher.doFinal(bArr3);
        } catch (Exception e) {
            System.err.println("MDFUtils::Got exception during MDF decryption" + e.getMessage());
            return null;
        }
    }

    public static String byteArrayToHexString(byte[] bArr) {
        if (bArr == null) {
            System.err.println("MDFUtils::Unable to convert the byte array, input is null");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String str) {
        if (str == null || str.length() == 0 || str.length() % 2 != 0) {
            System.err.println("MDFUtils::Unable to convert the string, the length is invalid");
            return null;
        }
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }
}
