package com.samsung.android.security.mdf;

import android.provider.DocumentsContract;
import android.security.keystore2.AndroidKeyStoreSpi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
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
        BAD_COUNTRY_2LDS =
                new String[] {
                    "ac",
                    "co",
                    "com",
                    "ed",
                    "edu",
                    "go",
                    "gouv",
                    "gov",
                    DocumentsContract.EXTRA_INFO,
                    "lg",
                    "ne",
                    "net",
                    "or",
                    "org"
                };
        Arrays.sort(BAD_COUNTRY_2LDS);
    }

    public static int getPid() {
        try {
            Class processClass = Class.forName("android.os.Process");
            Method myPidMethod = processClass.getMethod("myPid", null);
            return ((Integer) myPidMethod.invoke(null, new Object[0])).intValue();
        } catch (ClassNotFoundException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException e) {
            return -1;
        }
    }

    public static int getUid() {
        try {
            Class processClass = Class.forName("android.os.Process");
            Method myPidMethod = processClass.getMethod("myUid", null);
            return ((Integer) myPidMethod.invoke(null, new Object[0])).intValue();
        } catch (ClassNotFoundException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException e) {
            return -1;
        }
    }

    public static String getName() {
        BufferedReader reader = null;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("/proc/" + getPid() + "/cmdline");
            reader = new BufferedReader(fileReader);
            StringBuffer fileContent = new StringBuffer();
            while (true) {
                int c = reader.read();
                if (c <= 0) {
                    break;
                }
                fileContent.append((char) c);
            }
            String str = new String(fileContent);
            try {
                reader.close();
                fileReader.close();
            } catch (IOException e) {
                System.err.println("MdfUtils::getName encountered an exception: " + e.getMessage());
            }
            return str;
        } catch (Exception e2) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e3) {
                    System.err.println(
                            "MdfUtils::getName encountered an exception: " + e3.getMessage());
                    return null;
                }
            }
            if (fileReader == null) {
                return null;
            }
            fileReader.close();
            return null;
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e4) {
                    System.err.println(
                            "MdfUtils::getName encountered an exception: " + e4.getMessage());
                    throw th;
                }
            }
            if (fileReader != null) {
                fileReader.close();
            }
            throw th;
        }
    }

    public static void logMdf(
            boolean condition,
            String logMessage,
            boolean outcome,
            int severity,
            String swComponent) {
        logMdf(condition, logMessage, null, outcome, severity, swComponent);
    }

    public static void logMdf(
            boolean condition,
            String logMessage,
            String redactedLogMessage,
            boolean outcome,
            int severity,
            String swComponent) {
        if (condition) {
            try {
                Class.forName("android.sec.enterprise.EnterpriseDeviceManager");
                Class<?> auditLog = Class.forName("android.sec.enterprise.auditlog.AuditLog");
                Class<?>[] auditParams = {
                    Integer.TYPE,
                    Integer.TYPE,
                    Boolean.TYPE,
                    Integer.TYPE,
                    String.class,
                    String.class,
                    String.class
                };
                Object[] auditValues = {
                    Integer.valueOf(severity),
                    3,
                    Boolean.valueOf(outcome),
                    Integer.valueOf(getPid()),
                    swComponent,
                    logMessage,
                    redactedLogMessage
                };
                Method logMethod = auditLog.getMethod("logPrivileged", auditParams);
                logMethod.invoke(null, auditValues);
            } catch (ClassNotFoundException
                    | IllegalAccessException
                    | NoSuchMethodException
                    | InvocationTargetException e) {
                System.err.println(
                        "MdfUtils::AuditLog encountered an exception: " + e.getMessage());
            }
        }
    }

    public static void logMdf(
            String logMessage, boolean outcome, int severity, String swComponent) {
        logMdf(logMessage, (String) null, outcome, severity, swComponent);
    }

    public static void logMdf(
            String logMessage,
            String redactedLogMessage,
            boolean outcome,
            int severity,
            String swComponent) {
        logMdf(isMdfEnforced(), logMessage, redactedLogMessage, outcome, severity, swComponent);
    }

    public static String buildHostnameLog(String host, X509Certificate certificate) {
        if (certificate == null) {
            return "Certificate not presented";
        }
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("Identifier verification failed. Presented identifier: ");
        logBuilder.append(host);
        logBuilder.append(" List of reference identifiers: ");
        X500Principal principal = certificate.getSubjectX500Principal();
        logBuilder.append(principal == null ? "" : principal.getName());
        logBuilder.append(" ");
        try {
        } catch (CertificateParsingException e) {
            logBuilder.append("list of subject alternative names is not available");
        }
        if (certificate.getSubjectAlternativeNames() == null) {
            throw new CertificateParsingException("No SANs available");
        }
        int index = 0;
        for (Object san : certificate.getSubjectAlternativeNames()) {
            logBuilder.append(index + ") ");
            logBuilder.append(((List) san).get(1));
            logBuilder.append("; ");
            index++;
        }
        return logBuilder.toString();
    }

    private static boolean acceptableCountryWildcard(String cn) {
        int cnLen = cn.length();
        if (cnLen < 7 || cnLen > 9 || cn.charAt(cnLen - 3) != '.') {
            return true;
        }
        String s = cn.substring(2, cnLen - 3);
        int x = Arrays.binarySearch(BAD_COUNTRY_2LDS, s);
        return x < 0;
    }

    public static boolean isHostnameAllowed(String hostName, String pattern) {
        return pattern.indexOf(46, 2) != pattern.length() - 1
                && acceptableCountryWildcard(pattern.substring(0, pattern.length() - 1));
    }

    public static boolean isCertificateAllowed(
            X500Principal[] allowedIssuers, X509Certificate[] certificateChain) {
        if (certificateChain == null || certificateChain.length == 0) {
            return false;
        }
        if (allowedIssuers == null || allowedIssuers.length == 0) {
            return true;
        }
        for (int i = 0; i < certificateChain.length; i++) {
            if (certificateChain[i] != null) {
                for (int j = 0; j < allowedIssuers.length; j++) {
                    if (allowedIssuers[j] != null
                            && allowedIssuers[j].equals(
                                    certificateChain[i].getIssuerX500Principal())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static byte[] encryptMdf(byte[] value, String keyAlias) {
        try {
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
            keyStore.load(null);
            SecretKey secretKey = (SecretKey) keyStore.getKey(keyAlias, null);
            if (secretKey == null) {
                Class<?> clsSpecBuilder =
                        Class.forName("android.security.keystore.KeyGenParameterSpec$Builder");
                Constructor specBuilderConstructor =
                        clsSpecBuilder.getDeclaredConstructor(String.class, Integer.TYPE);
                Object specBuilder = specBuilderConstructor.newInstance(keyAlias, 3);
                Method setKeySizeMethod = clsSpecBuilder.getMethod("setKeySize", Integer.TYPE);
                Method setEncryptionPaddingsMethod =
                        clsSpecBuilder.getMethod("setEncryptionPaddings", String[].class);
                Method setBlockModesMethod =
                        clsSpecBuilder.getMethod("setBlockModes", String[].class);
                Method buildMethod = clsSpecBuilder.getMethod("build", null);
                AlgorithmParameterSpec spec =
                        (AlgorithmParameterSpec)
                                buildMethod.invoke(
                                        setEncryptionPaddingsMethod.invoke(
                                                setKeySizeMethod.invoke(
                                                        setBlockModesMethod.invoke(
                                                                specBuilder, new String[] {"GCM"}),
                                                        256),
                                                new String[] {"NoPadding"}),
                                        new Object[0]);
                KeyGenerator keyGenerator =
                        KeyGenerator.getInstance("AES", AndroidKeyStoreSpi.NAME);
                keyGenerator.init(spec);
                secretKey = keyGenerator.generateKey();
            }
            Cipher cipher = Cipher.getInstance(MDF_CIPHER_MODE);
            cipher.init(1, secretKey);
            try {
                byte[] encryption = cipher.doFinal(value);
                byte[] output = new byte[encryption.length + 12];
                for (int i = 0; i < 12; i++) {
                    output[i] = cipher.getIV()[i];
                }
                for (int i2 = 0; i2 < encryption.length; i2++) {
                    output[i2 + 12] = encryption[i2];
                }
                return output;
            } catch (Exception e) {
                e = e;
                System.err.println(
                        "MDFUtils::Got exception during MDF encryption: " + e.getMessage());
                return null;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static byte[] decryptMdf(byte[] value, String keyAlias) {
        if (value.length <= 28) {
            System.err.println("MDFUtils::MDF decryption failed, invalid encryption length");
            return null;
        }
        try {
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
            keyStore.load(null);
            SecretKey secretKey = (SecretKey) keyStore.getKey(keyAlias, null);
            if (secretKey == null) {
                System.err.println(
                        "MDFUtils::MDF decryption failed, unable to get encryption key from"
                            + " AndroidKeystore");
                return null;
            }
            int encLength = value.length - 12;
            byte[] iv = new byte[12];
            byte[] encryption = new byte[encLength];
            for (int i = 0; i < 12; i++) {
                iv[i] = value[i];
            }
            for (int i2 = 0; i2 < encryption.length; i2++) {
                encryption[i2] = value[i2 + 12];
            }
            Cipher cipher = Cipher.getInstance(MDF_CIPHER_MODE);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(2, secretKey, gcmParameterSpec);
            byte[] output = cipher.doFinal(encryption);
            return output;
        } catch (Exception e) {
            System.err.println("MDFUtils::Got exception during MDF decryption" + e.getMessage());
            return null;
        }
    }

    public static String byteArrayToHexString(byte[] input) {
        if (input == null) {
            System.err.println("MDFUtils::Unable to convert the byte array, input is null");
            return null;
        }
        StringBuilder outputStringBuilder = new StringBuilder();
        for (byte b : input) {
            outputStringBuilder.append(String.format("%02x", Byte.valueOf(b)));
        }
        return outputStringBuilder.toString();
    }

    public static byte[] hexStringToByteArray(String input) {
        if (input == null || input.length() == 0 || input.length() % 2 != 0) {
            System.err.println("MDFUtils::Unable to convert the string, the length is invalid");
            return null;
        }
        byte[] output = new byte[input.length() / 2];
        for (int i = 0; i < input.length(); i += 2) {
            output[i / 2] =
                    (byte)
                            ((Character.digit(input.charAt(i), 16) << 4)
                                    + Character.digit(input.charAt(i + 1), 16));
        }
        return output;
    }
}
