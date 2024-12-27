package com.samsung.ucm.ucmservice;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class EFSProperties {
    public static final String[] STORAGE_TYPES = {
        "eSE", "SIM", "SD", "eSE1", "SIM1", "SD1", "eSE2", "SIM2", "SD2", "ETC"
    };
    public static final String[] SCP_TYPES = {"NONE", "SCP11a", "SCP11b", "SCPCustom"};

    public final class KeyguardProperties {
        public byte[] AID;
        public byte[] csName;
        public int enabledSCP;
        public int pinMaxLength;
        public int pinMinLength;
        public int pukMaxLength;
        public int pukMinLength;
        public int storageType;
    }

    public final class ODEProperties {
        public int enabledUCSInODE = 0;
        public byte[] AID = null;
        public int storageType = 0;
        public int enabledSCP = 0;
        public int enabledWrap = 0;
        public int pinMinLength = 0;
        public int pinMaxLength = 0;
        public int authMode = 0;
        public int authMaxCnt = 0;
        public int pukMinLength = 0;
        public int pukMaxLength = 0;
        public byte[] csName = null;
        public int CertAdminID = 0;
        public int CertUserID = 0;
        public byte[] CertAlias = null;
        public byte[] CertLocation = null;
        public byte[] cofiguratorPkg = null;
        public byte[] cofiguratorSign = null;
        public byte[] scpCreationParam = null;
        public byte[] pluginSignatureHash = null;
        public int version = 1;
        public byte[] defaultLanguage = null;

        public static int getSCPTypeIndex(String str) {
            if (str == null || str.isEmpty()) {
                Log.d("EFSProperties", "SCP empty, set SCP_NONE");
                return 0;
            }
            String lowerCase = str.toLowerCase();
            for (int i = 0; i < 4; i++) {
                if (EFSProperties.SCP_TYPES[i].toLowerCase().equals(lowerCase)) {
                    return i;
                }
            }
            return -1;
        }

        public static int getStorageTypeIndex(String str) {
            if (str == null || str.isEmpty()) {
                return -1;
            }
            String replace = str.toLowerCase().replace("uicc", "sim");
            for (int i = 0; i < 10; i++) {
                if (EFSProperties.STORAGE_TYPES[i].equalsIgnoreCase(replace)) {
                    return i;
                }
            }
            return 9;
        }
    }

    public static void clearAppletInfo() {
        log("deleteAppletDeletionLccmScript");
        boolean deleteFile = deleteFile(new File("/efs/sec_efs", "ucm_delete_applet_lccmscript"));
        log("deletePluginPackageNameFile");
        boolean deleteFile2 = deleteFile(new File("/efs/sec_efs", "ucm_applet_pluginpackagename"));
        log("deletePluginSigHash");
        boolean deleteFile3 =
                deleteFile(new File("/efs/sec_efs", "ucm_applet_plugin_hash_of_signature"));
        log("deleteLccmScript: " + deleteFile);
        log("deletePluginPackageNameFileResult: " + deleteFile2);
        log("deletePluginSigHashResult: " + deleteFile3);
    }

    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.delete()) {
            return true;
        }
        log("failed to delete the existing file");
        return false;
    }

    public static boolean deleteODEConfig() {
        File file = new File("/efs/sec_efs", "odeConfig");
        if (!file.exists()) {
            return true;
        }
        boolean delete = file.delete();
        if (delete) {
            return delete;
        }
        log("failed to delete the existing ODE config file");
        return false;
    }

    public static ODEProperties loadODEConfig() {
        int read;
        log("load ODE config");
        File file = new File("/efs/sec_efs", "odeConfig");
        if (!file.exists()) {
            log("ODE config file does not exist");
            return new ODEProperties();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                ODEProperties oDEProperties = new ODEProperties();
                oDEProperties.enabledUCSInODE = fileInputStream.read();
                int read2 = fileInputStream.read();
                if (read2 != 0) {
                    byte[] bArr = new byte[read2];
                    oDEProperties.AID = bArr;
                    int read3 = fileInputStream.read(bArr, 0, read2);
                    if (read3 != read2) {
                        Log.d(
                                "EFSProperties",
                                "read side mismatched, lengthAID : "
                                        + read2
                                        + ", readByteSize : "
                                        + read3);
                    }
                }
                oDEProperties.storageType = fileInputStream.read();
                oDEProperties.enabledSCP = fileInputStream.read();
                oDEProperties.enabledWrap = fileInputStream.read();
                oDEProperties.pinMinLength = fileInputStream.read();
                oDEProperties.pinMaxLength = fileInputStream.read();
                oDEProperties.authMode = fileInputStream.read();
                oDEProperties.authMaxCnt = fileInputStream.read();
                oDEProperties.pukMinLength = fileInputStream.read();
                oDEProperties.pukMaxLength = fileInputStream.read();
                int read4 = fileInputStream.read();
                if (read4 != 0) {
                    byte[] bArr2 = new byte[read4];
                    oDEProperties.csName = bArr2;
                    int read5 = fileInputStream.read(bArr2, 0, read4);
                    if (read5 != read4) {
                        Log.d(
                                "EFSProperties",
                                "read side mismatched, lengthCsName : "
                                        + read4
                                        + ", readByteSize : "
                                        + read5);
                    }
                }
                oDEProperties.CertAdminID = fileInputStream.read();
                oDEProperties.CertUserID = fileInputStream.read();
                int read6 = fileInputStream.read();
                if (read6 != 0) {
                    byte[] bArr3 = new byte[read6];
                    oDEProperties.CertAlias = bArr3;
                    int read7 = fileInputStream.read(bArr3, 0, read6);
                    if (read7 != read6) {
                        Log.d(
                                "EFSProperties",
                                "read side mismatched, lenCertAlias : "
                                        + read6
                                        + ", readByteSize : "
                                        + read7);
                    }
                }
                int read8 = fileInputStream.read();
                if (read8 != 0) {
                    byte[] bArr4 = new byte[read8];
                    oDEProperties.CertLocation = bArr4;
                    int read9 = fileInputStream.read(bArr4, 0, read8);
                    if (read9 != read8) {
                        Log.d(
                                "EFSProperties",
                                "read side mismatched, lenCertLocation : "
                                        + read8
                                        + ", readByteSize : "
                                        + read9);
                    }
                }
                int read10 = fileInputStream.read();
                if (read10 != 0) {
                    byte[] bArr5 = new byte[read10];
                    oDEProperties.cofiguratorPkg = bArr5;
                    int read11 = fileInputStream.read(bArr5, 0, read10);
                    if (read11 != read10) {
                        Log.d(
                                "EFSProperties",
                                "read side mismatched, lenConfiguratorPkgLen : "
                                        + read10
                                        + ", readByteSize : "
                                        + read11);
                    }
                }
                int read12 = fileInputStream.read();
                if (read12 != 0) {
                    byte[] bArr6 = new byte[read12];
                    oDEProperties.cofiguratorSign = bArr6;
                    int read13 = fileInputStream.read(bArr6, 0, read12);
                    if (read13 != read12) {
                        Log.d(
                                "EFSProperties",
                                "read side mismatched, lenConfiguratorDigestLen : "
                                        + read12
                                        + ", readByteSize : "
                                        + read13);
                    }
                }
                byte[] bArr7 = new byte[2];
                if (fileInputStream.read(bArr7, 0, 2) < 2) {
                    throw new IOException();
                }
                int i =
                        ((bArr7[0] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
                                        << 8)
                                | (bArr7[1]
                                        & IDnsResolverUnsolicitedEventListener
                                                .DNS_HEALTH_RESULT_TIMEOUT);
                if (i != 0) {
                    byte[] bArr8 = new byte[i];
                    oDEProperties.scpCreationParam = bArr8;
                    int read14 = fileInputStream.read(bArr8, 0, i);
                    if (read14 != i) {
                        Log.d(
                                "EFSProperties",
                                "read side mismatched, lenScpParamLen : "
                                        + i
                                        + ", readByteSize : "
                                        + read14);
                    }
                }
                int read15 = fileInputStream.read();
                if (read15 > 0) {
                    byte[] bArr9 = new byte[read15];
                    oDEProperties.pluginSignatureHash = bArr9;
                    int read16 = fileInputStream.read(bArr9, 0, read15);
                    if (read16 != read15) {
                        Log.d(
                                "EFSProperties",
                                "read side mismatched, lenPluginSignatureHash : "
                                        + read15
                                        + ", readByteSize : "
                                        + read16);
                    }
                }
                int read17 = fileInputStream.read();
                if (read17 == -1) {
                    oDEProperties.version = 1;
                } else {
                    oDEProperties.version = read17;
                    if (read17 > 1 && (read = fileInputStream.read()) > 0) {
                        byte[] bArr10 = new byte[read];
                        oDEProperties.defaultLanguage = bArr10;
                        int read18 = fileInputStream.read(bArr10, 0, read);
                        if (read18 != read) {
                            Log.d(
                                    "EFSProperties",
                                    "read side mismatched, lenDefaultLanguage : "
                                            + read
                                            + ", readByteSize : "
                                            + read18);
                        }
                    }
                }
                fileInputStream.close();
                return oDEProperties;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ODEProperties();
        }
    }

    public static void log(String str) {
        Log.d("EFSProperties", str);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x0070 -> B:23:0x0097). Please report as a decompilation issue!!! */
    public static boolean saveKeyguardConfig(KeyguardProperties keyguardProperties) {
        FileOutputStream fileOutputStream;
        byte[] bArr;
        int i;
        int i2;
        log("save Keyguard config");
        File file = new File("/efs/sec_efs", "keyguardConfig");
        boolean z = false;
        if (file.exists() && !file.delete()) {
            log("failed to delete the existing Keyguard ODE config file");
            return false;
        }
        FileOutputStream fileOutputStream2 = null;
        FileOutputStream fileOutputStream3 = null;
        FileOutputStream fileOutputStream4 = null;
        fileOutputStream2 = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
            fileOutputStream2 = fileOutputStream2;
        }
        try {
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr2 = keyguardProperties.csName;
                    int length = bArr2.length;
                    fileOutputStream.write(length);
                    fileOutputStream.write(bArr2, 0, length);
                    bArr = keyguardProperties.AID;
                    i = length;
                } catch (IOException e2) {
                    e = e2;
                    fileOutputStream3 = fileOutputStream;
                    log("saveKeyguardConfig : IOException");
                    e.printStackTrace();
                    fileOutputStream2 = fileOutputStream3;
                    if (fileOutputStream3 != null) {
                        fileOutputStream3.close();
                        fileOutputStream2 = fileOutputStream3;
                    }
                    return z;
                } catch (Exception e3) {
                    e = e3;
                    fileOutputStream4 = fileOutputStream;
                    log("saveKeyguardConfig : Exception");
                    e.printStackTrace();
                    fileOutputStream2 = fileOutputStream4;
                    if (fileOutputStream4 != null) {
                        fileOutputStream4.close();
                        fileOutputStream2 = fileOutputStream4;
                    }
                    return z;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e5) {
                e = e5;
            } catch (Exception e6) {
                e = e6;
            }
            if (bArr != null) {
                int length2 = bArr.length;
                i = length2;
                if (length2 > 0) {
                    int length3 = bArr.length;
                    fileOutputStream.write(length3);
                    fileOutputStream.write(bArr);
                    i2 = length3;
                    fileOutputStream.write(keyguardProperties.storageType);
                    fileOutputStream.write(keyguardProperties.enabledSCP);
                    fileOutputStream.write(keyguardProperties.pinMinLength);
                    fileOutputStream.write(keyguardProperties.pinMaxLength);
                    fileOutputStream.write(keyguardProperties.pukMinLength);
                    fileOutputStream.write(keyguardProperties.pukMaxLength);
                    fileOutputStream.close();
                    z = true;
                    fileOutputStream2 = i2;
                    return z;
                }
            }
            fileOutputStream.write(0);
            i2 = i;
            fileOutputStream.write(keyguardProperties.storageType);
            fileOutputStream.write(keyguardProperties.enabledSCP);
            fileOutputStream.write(keyguardProperties.pinMinLength);
            fileOutputStream.write(keyguardProperties.pinMaxLength);
            fileOutputStream.write(keyguardProperties.pukMinLength);
            fileOutputStream.write(keyguardProperties.pukMaxLength);
            fileOutputStream.close();
            z = true;
            fileOutputStream2 = i2;
            return z;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x014f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean saveODEConfig(com.samsung.ucm.ucmservice.EFSProperties.ODEProperties r7) {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.ucm.ucmservice.EFSProperties.saveODEConfig(com.samsung.ucm.ucmservice.EFSProperties$ODEProperties):boolean");
    }
}
