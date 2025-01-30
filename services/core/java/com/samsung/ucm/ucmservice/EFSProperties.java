package com.samsung.ucm.ucmservice;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/* loaded from: classes2.dex */
public abstract class EFSProperties {
    public static final String[] STORAGE_TYPES = {"eSE", "SIM", "SD", "eSE1", "SIM1", "SD1", "eSE2", "SIM2", "SD2", "ETC"};
    public static final String[] SCP_TYPES = {"NONE", "SCP11a", "SCP11b", "SCPCustom"};

    public static boolean checkKeyguardProperty(KeyguardProperties keyguardProperties) {
        return true;
    }

    public static void clearAppletInfo() {
        log("deletePluginUidResult: " + deleteStoredPluginUid() + ", deleteLccmScript: " + deleteAppletDeletionLccmScript());
    }

    public static File getPluginUidFile() {
        return new File("/efs/sec_efs", "ucm_applet_pluginuid");
    }

    public static boolean isPluginUidStored() {
        return getPluginUidFile().exists();
    }

    public static boolean savePluginUid(int i) {
        log("savePluginUid");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getPluginUidFile());
            try {
                fileOutputStream.write(String.valueOf(i).getBytes(StandardCharsets.UTF_8));
                fileOutputStream.close();
                return true;
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getStoredPluginUid() {
        log("getStoredPluginUid");
        int i = -1;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(getPluginUidFile()));
            try {
                int length = (int) getPluginUidFile().length();
                byte[] bArr = new byte[length];
                log("getByteArray read: " + bufferedInputStream.read(bArr, 0, length));
                i = Integer.parseInt(new String(bArr, "UTF-8"));
                bufferedInputStream.close();
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static boolean deleteStoredPluginUid() {
        log("deleteStoredPluginUid");
        return deleteFile(getPluginUidFile());
    }

    public static File getLccmScriptFile() {
        return new File("/efs/sec_efs", "ucm_delete_applet_lccmscript");
    }

    public static boolean isAppletDeletionLccmScriptExist() {
        return getLccmScriptFile().exists();
    }

    public static boolean saveAppletDeletionLccmScript(byte[] bArr) {
        log("saveAppletDeletionLccmScript");
        if (bArr == null) {
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getLccmScriptFile());
            try {
                fileOutputStream.write(bArr);
                fileOutputStream.close();
                return true;
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] getAppletDeletionLccmScript() {
        log("getAppletDeletionLccmScript");
        if (!isAppletDeletionLccmScriptExist()) {
            return null;
        }
        int length = (int) getLccmScriptFile().length();
        byte[] bArr = new byte[length];
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(getLccmScriptFile()));
            try {
                log("getByteArray read: " + bufferedInputStream.read(bArr, 0, length));
                bufferedInputStream.close();
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bArr;
    }

    public static boolean deleteAppletDeletionLccmScript() {
        log("deleteAppletDeletionLccmScript");
        return deleteFile(getLccmScriptFile());
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
                        Log.d("EFSProperties", "read side mismatched, lengthAID : " + read2 + ", readByteSize : " + read3);
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
                        Log.d("EFSProperties", "read side mismatched, lengthCsName : " + read4 + ", readByteSize : " + read5);
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
                        Log.d("EFSProperties", "read side mismatched, lenCertAlias : " + read6 + ", readByteSize : " + read7);
                    }
                }
                int read8 = fileInputStream.read();
                if (read8 != 0) {
                    byte[] bArr4 = new byte[read8];
                    oDEProperties.CertLocation = bArr4;
                    int read9 = fileInputStream.read(bArr4, 0, read8);
                    if (read9 != read8) {
                        Log.d("EFSProperties", "read side mismatched, lenCertLocation : " + read8 + ", readByteSize : " + read9);
                    }
                }
                int read10 = fileInputStream.read();
                if (read10 != 0) {
                    byte[] bArr5 = new byte[read10];
                    oDEProperties.cofiguratorPkg = bArr5;
                    int read11 = fileInputStream.read(bArr5, 0, read10);
                    if (read11 != read10) {
                        Log.d("EFSProperties", "read side mismatched, lenConfiguratorPkgLen : " + read10 + ", readByteSize : " + read11);
                    }
                }
                int read12 = fileInputStream.read();
                if (read12 != 0) {
                    byte[] bArr6 = new byte[read12];
                    oDEProperties.cofiguratorSign = bArr6;
                    int read13 = fileInputStream.read(bArr6, 0, read12);
                    if (read13 != read12) {
                        Log.d("EFSProperties", "read side mismatched, lenConfiguratorDigestLen : " + read12 + ", readByteSize : " + read13);
                    }
                }
                byte[] bArr7 = new byte[2];
                if (fileInputStream.read(bArr7, 0, 2) < 2) {
                    throw new IOException();
                }
                int i = ((bArr7[0] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT) << 8) | 0 | (bArr7[1] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                if (i != 0) {
                    byte[] bArr8 = new byte[i];
                    oDEProperties.scpCreationParam = bArr8;
                    int read14 = fileInputStream.read(bArr8, 0, i);
                    if (read14 != i) {
                        Log.d("EFSProperties", "read side mismatched, lenScpParamLen : " + i + ", readByteSize : " + read14);
                    }
                }
                int read15 = fileInputStream.read();
                if (read15 > 0) {
                    byte[] bArr9 = new byte[read15];
                    oDEProperties.pluginSignatureHash = bArr9;
                    int read16 = fileInputStream.read(bArr9, 0, read15);
                    if (read16 != read15) {
                        Log.d("EFSProperties", "read side mismatched, lenPluginSignatureHash : " + read15 + ", readByteSize : " + read16);
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
                            Log.d("EFSProperties", "read side mismatched, lenDefaultLanguage : " + read + ", readByteSize : " + read18);
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

    /* JADX WARN: Removed duplicated region for block: B:65:0x0152 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean saveODEConfig(ODEProperties oDEProperties) {
        boolean z;
        FileOutputStream fileOutputStream;
        log("save ODE config");
        if (oDEProperties == null) {
            log("ODEProperties is null");
            return false;
        }
        File file = new File("/efs/sec_efs", "odeConfig");
        if (file.exists() && !file.delete()) {
            log("failed to delete the existing ODE config file");
            return false;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(oDEProperties.enabledUCSInODE);
                byte[] bArr = oDEProperties.AID;
                if (bArr != null && bArr.length > 0) {
                    fileOutputStream.write(bArr.length);
                    fileOutputStream.write(oDEProperties.AID);
                } else {
                    fileOutputStream.write(0);
                }
                fileOutputStream.write(oDEProperties.storageType);
                fileOutputStream.write(oDEProperties.enabledSCP);
                fileOutputStream.write(oDEProperties.enabledWrap);
                fileOutputStream.write(oDEProperties.pinMinLength);
                fileOutputStream.write(oDEProperties.pinMaxLength);
                fileOutputStream.write(oDEProperties.authMode);
                fileOutputStream.write(oDEProperties.authMaxCnt);
                fileOutputStream.write(oDEProperties.pukMinLength);
                fileOutputStream.write(oDEProperties.pukMaxLength);
                byte[] bArr2 = oDEProperties.csName;
                if (bArr2 != null && bArr2.length > 0) {
                    fileOutputStream.write(bArr2.length);
                    fileOutputStream.write(oDEProperties.csName);
                } else {
                    fileOutputStream.write(0);
                }
                fileOutputStream.write(oDEProperties.CertAdminID);
                fileOutputStream.write(oDEProperties.CertUserID);
                byte[] bArr3 = oDEProperties.CertAlias;
                if (bArr3 != null && bArr3.length > 0) {
                    fileOutputStream.write(bArr3.length);
                    fileOutputStream.write(oDEProperties.CertAlias);
                } else {
                    fileOutputStream.write(0);
                }
                byte[] bArr4 = oDEProperties.CertLocation;
                if (bArr4 != null && bArr4.length > 0) {
                    fileOutputStream.write(bArr4.length);
                    fileOutputStream.write(oDEProperties.CertLocation);
                } else {
                    fileOutputStream.write(0);
                }
                byte[] bArr5 = oDEProperties.cofiguratorPkg;
                if (bArr5 != null && bArr5.length > 0) {
                    fileOutputStream.write(bArr5.length);
                    fileOutputStream.write(oDEProperties.cofiguratorPkg);
                } else {
                    fileOutputStream.write(0);
                }
                byte[] bArr6 = oDEProperties.cofiguratorSign;
                if (bArr6 != null && bArr6.length > 0) {
                    fileOutputStream.write(bArr6.length);
                    fileOutputStream.write(oDEProperties.cofiguratorSign);
                } else {
                    fileOutputStream.write(0);
                }
                byte[] bArr7 = oDEProperties.scpCreationParam;
                z = true;
                if (bArr7 != null && bArr7.length > 0) {
                    fileOutputStream.write(new byte[]{(byte) ((bArr7.length & 65280) >> 8), (byte) (bArr7.length & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)});
                    fileOutputStream.write(oDEProperties.scpCreationParam);
                } else {
                    fileOutputStream.write(0);
                    fileOutputStream.write(0);
                }
                byte[] bArr8 = oDEProperties.pluginSignatureHash;
                if (bArr8 != null && bArr8.length > 0) {
                    fileOutputStream.write(bArr8.length);
                    fileOutputStream.write(oDEProperties.pluginSignatureHash);
                } else {
                    fileOutputStream.write(0);
                }
                fileOutputStream.write(oDEProperties.version);
                byte[] bArr9 = oDEProperties.defaultLanguage;
                if (bArr9 != null && bArr9.length > 0) {
                    fileOutputStream.write(bArr9.length);
                    fileOutputStream.write(oDEProperties.defaultLanguage);
                }
            } catch (FileNotFoundException e) {
                e = e;
                fileOutputStream2 = fileOutputStream;
                e.printStackTrace();
                file.delete();
                z = false;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                }
                return z;
            } catch (IOException e2) {
                e = e2;
                fileOutputStream2 = fileOutputStream;
                e.printStackTrace();
                file.delete();
                z = false;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                }
                return z;
            }
        } catch (FileNotFoundException e3) {
            e = e3;
        } catch (IOException e4) {
            e = e4;
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e5) {
                e5.printStackTrace();
                return false;
            }
        }
        return z;
    }

    public static boolean deleteKeyguardConfig() {
        File file = new File("/efs/sec_efs", "keyguardConfig");
        if (!file.exists()) {
            return true;
        }
        boolean delete = file.delete();
        if (delete) {
            return delete;
        }
        log("failed to delete the existing keyguard config file");
        return false;
    }

    public static boolean saveKeyguardConfig(KeyguardProperties keyguardProperties) {
        FileOutputStream fileOutputStream;
        byte[] bArr;
        int i;
        int i2;
        log("save Keyguard config");
        boolean z = false;
        if (keyguardProperties == null) {
            log("KeyguardProperties is null");
            return false;
        }
        if (!checkKeyguardProperty(keyguardProperties)) {
            log("checkKeyguardProperty false");
            return false;
        }
        File file = new File("/efs/sec_efs", "keyguardConfig");
        if (file.exists() && !file.delete()) {
            log("failed to delete the existing Keyguard ODE config file");
            return false;
        }
        FileOutputStream fileOutputStream2 = null;
        fileOutputStream2 = null;
        fileOutputStream2 = null;
        fileOutputStream2 = null;
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                e = e2;
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr2 = keyguardProperties.csName;
            int length = bArr2.length;
            fileOutputStream.write(length);
            fileOutputStream.write(bArr2, 0, length);
            bArr = keyguardProperties.AID;
            i = length;
        } catch (IOException e4) {
            e = e4;
            fileOutputStream2 = fileOutputStream;
            log("saveKeyguardConfig : IOException");
            e.printStackTrace();
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
                fileOutputStream2 = fileOutputStream2;
            }
            return z;
        } catch (Exception e5) {
            e = e5;
            fileOutputStream2 = fileOutputStream;
            log("saveKeyguardConfig : Exception");
            e.printStackTrace();
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
                fileOutputStream2 = fileOutputStream2;
            }
            return z;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
        if (bArr != null) {
            int length2 = bArr.length;
            i = length2;
            if (length2 > 0) {
                fileOutputStream.write(bArr.length);
                fileOutputStream.write(keyguardProperties.AID);
                i2 = length2;
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
    }

    public class ODEProperties {
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

        public static int getStorageTypeIndex(String str) {
            int length = EFSProperties.STORAGE_TYPES.length;
            if (str == null || str.isEmpty()) {
                return -1;
            }
            String replace = str.toLowerCase().replace("uicc", "sim");
            for (int i = 0; i < length; i++) {
                if (EFSProperties.STORAGE_TYPES[i].equalsIgnoreCase(replace)) {
                    return i;
                }
            }
            return 9;
        }

        public static int getSCPTypeIndex(String str) {
            int length = EFSProperties.SCP_TYPES.length;
            if (str == null || str.isEmpty()) {
                Log.d("EFSProperties", "SCP empty, set SCP_NONE");
                return 0;
            }
            String lowerCase = str.toLowerCase();
            for (int i = 0; i < length; i++) {
                if (EFSProperties.SCP_TYPES[i].toLowerCase().equals(lowerCase)) {
                    return i;
                }
            }
            return -1;
        }
    }

    public class KeyguardProperties {
        public byte[] AID;
        public byte[] csName;
        public int enabledSCP;
        public int pinMaxLength;
        public int pinMinLength;
        public int pukMaxLength;
        public int pukMinLength;
        public int storageType;

        public KeyguardProperties(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4, int i5, int i6) {
            this.csName = bArr;
            this.AID = bArr2;
            this.storageType = i;
            this.enabledSCP = i2;
            this.pinMinLength = i3;
            this.pinMaxLength = i4;
            this.pukMinLength = i5;
            this.pukMaxLength = i6;
        }
    }

    public static void log(String str) {
        Log.d("EFSProperties", str);
    }
}
