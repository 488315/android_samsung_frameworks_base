package com.android.systemui.controls.util;

import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class EncryptDecryptWrapper {
    public final EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public static boolean setup(File file) throws IOException {
        try {
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                if (parentFile.exists()) {
                    parentFile = null;
                }
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
            }
            if (file.exists()) {
                return true;
            }
            file.createNewFile();
            return true;
        } catch (Exception e) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("make file Exception: ", e, "EncryptDecryptWrapper");
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0059 A[Catch: Exception -> 0x0092, TRY_ENTER, TRY_LEAVE, TryCatch #5 {Exception -> 0x0092, blocks: (B:7:0x000f, B:21:0x0059, B:32:0x0079, B:47:0x008e, B:48:0x0091, B:8:0x0014, B:23:0x005d, B:31:0x0076, B:42:0x0087, B:43:0x008a, B:11:0x0031, B:13:0x0038, B:16:0x0044, B:18:0x004d, B:17:0x0049, B:30:0x0073, B:37:0x0080, B:38:0x0083, B:25:0x0064, B:26:0x0066, B:28:0x006d, B:29:0x0071, B:35:0x007e, B:40:0x0085, B:45:0x008c), top: B:59:0x000f, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005d A[Catch: all -> 0x008b, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x008b, blocks: (B:8:0x0014, B:23:0x005d, B:31:0x0076, B:42:0x0087, B:43:0x008a, B:11:0x0031, B:13:0x0038, B:16:0x0044, B:18:0x004d, B:17:0x0049, B:30:0x0073, B:37:0x0080, B:38:0x0083, B:25:0x0064, B:26:0x0066, B:28:0x006d, B:29:0x0071, B:35:0x007e, B:40:0x0085), top: B:55:0x0014, outer: #5, inners: #0, #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean decryptFile(File file, File file2, String str, ControlsBackUpRestore$BNRSecurityLevel controlsBackUpRestore$BNRSecurityLevel) throws IOException {
        SecretKeySpec secretKeySpecGenerateSHA256SecretKey;
        CipherInputStream cipherInputStream;
        if (setup(file) && setup(file2)) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    EncryptDecrypt encryptDecrypt = this.encryptDecrypt;
                    int value = controlsBackUpRestore$BNRSecurityLevel.getValue();
                    encryptDecrypt.getClass();
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    byte[] bArr = new byte[cipher.getBlockSize()];
                    if (fileInputStream.read(bArr) > 0) {
                        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
                        if (value == 1) {
                            byte[] bArr2 = new byte[16];
                            if (fileInputStream.read(bArr2) > 0) {
                                secretKeySpecGenerateSHA256SecretKey = EncryptDecrypt.generatePBKDF2SecretKey(str, bArr2);
                            }
                        } else {
                            secretKeySpecGenerateSHA256SecretKey = EncryptDecrypt.generateSHA256SecretKey(str);
                        }
                        cipher.init(2, secretKeySpecGenerateSHA256SecretKey, ivParameterSpec);
                        cipherInputStream = new CipherInputStream(fileInputStream, cipher);
                        if (cipherInputStream != null) {
                            fileInputStream.close();
                            return false;
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        try {
                            try {
                                byte[] bArr3 = new byte[1024];
                                while (true) {
                                    int i = cipherInputStream.read(bArr3, 0, 1024);
                                    if (i == -1) {
                                        Unit unit = Unit.INSTANCE;
                                        cipherInputStream.close();
                                        fileOutputStream.close();
                                        fileInputStream.close();
                                        return true;
                                    }
                                    fileOutputStream.write(bArr3, 0, i);
                                }
                            } finally {
                            }
                        } finally {
                        }
                    }
                    cipherInputStream = null;
                    if (cipherInputStream != null) {
                    }
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public final boolean encryptFile(File file, File file2, String str, ControlsBackUpRestore$BNRSecurityLevel controlsBackUpRestore$BNRSecurityLevel) throws IOException {
        if (setup(file) && setup(file2)) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    EncryptDecrypt encryptDecrypt = this.encryptDecrypt;
                    int value = controlsBackUpRestore$BNRSecurityLevel.getValue();
                    encryptDecrypt.getClass();
                    OutputStream outputStreamEncryptStream = EncryptDecrypt.encryptStream(fileOutputStream, str, value);
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int i = fileInputStream.read(bArr, 0, 1024);
                                if (i == -1) {
                                    Unit unit = Unit.INSTANCE;
                                    fileInputStream.close();
                                    ((CipherOutputStream) outputStreamEncryptStream).close();
                                    fileOutputStream.close();
                                    return true;
                                }
                                outputStreamEncryptStream.write(bArr, 0, i);
                            }
                        } finally {
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
