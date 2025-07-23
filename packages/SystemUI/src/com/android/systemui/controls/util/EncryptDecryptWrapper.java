package com.android.systemui.controls.util;

import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.crypto.CipherOutputStream;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class EncryptDecryptWrapper {
    public final EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public static boolean setup(File file) {
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x0059 A[Catch: Exception -> 0x0092, TRY_ENTER, TRY_LEAVE, TryCatch #5 {Exception -> 0x0092, blocks: (B:6:0x000f, B:13:0x0059, B:27:0x0079, B:57:0x008e, B:58:0x0091, B:8:0x0014, B:16:0x005d, B:26:0x0076, B:35:0x0087, B:36:0x008a, B:44:0x0031, B:46:0x0038, B:48:0x0044, B:49:0x004d, B:51:0x0049, B:25:0x0073, B:42:0x0080, B:43:0x0083, B:19:0x0064, B:20:0x0066, B:22:0x006d, B:24:0x0071, B:39:0x007e, B:32:0x0085, B:54:0x008c), top: B:5:0x000f, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x005d A[Catch: all -> 0x008b, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x008b, blocks: (B:8:0x0014, B:16:0x005d, B:26:0x0076, B:35:0x0087, B:36:0x008a, B:44:0x0031, B:46:0x0038, B:48:0x0044, B:49:0x004d, B:51:0x0049, B:25:0x0073, B:42:0x0080, B:43:0x0083, B:19:0x0064, B:20:0x0066, B:22:0x006d, B:24:0x0071, B:39:0x007e, B:32:0x0085), top: B:7:0x0014, outer: #5, inners: #0, #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean decryptFile(java.io.File r5, java.io.File r6, java.lang.String r7, com.android.systemui.controls.util.ControlsBackUpRestore$BNRSecurityLevel r8) {
        /*
            r4 = this;
            boolean r0 = setup(r5)
            r1 = 0
            if (r0 == 0) goto L96
            boolean r0 = setup(r6)
            if (r0 != 0) goto Lf
            goto L96
        Lf:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch: java.lang.Exception -> L92
            r0.<init>(r5)     // Catch: java.lang.Exception -> L92
            com.android.systemui.controls.util.EncryptDecrypt r4 = r4.encryptDecrypt     // Catch: java.lang.Throwable -> L8b
            int r5 = r8.getValue()     // Catch: java.lang.Throwable -> L8b
            r4.getClass()     // Catch: java.lang.Throwable -> L8b
            java.lang.String r4 = "AES/CBC/PKCS5Padding"
            javax.crypto.Cipher r4 = javax.crypto.Cipher.getInstance(r4)     // Catch: java.lang.Throwable -> L8b
            int r8 = r4.getBlockSize()     // Catch: java.lang.Throwable -> L8b
            byte[] r8 = new byte[r8]     // Catch: java.lang.Throwable -> L8b
            int r2 = r0.read(r8)     // Catch: java.lang.Throwable -> L8b
            r3 = 1
            if (r2 > 0) goto L31
            goto L42
        L31:
            javax.crypto.spec.IvParameterSpec r2 = new javax.crypto.spec.IvParameterSpec     // Catch: java.lang.Throwable -> L8b
            r2.<init>(r8)     // Catch: java.lang.Throwable -> L8b
            if (r5 != r3) goto L49
            r5 = 16
            byte[] r5 = new byte[r5]     // Catch: java.lang.Throwable -> L8b
            int r8 = r0.read(r5)     // Catch: java.lang.Throwable -> L8b
            if (r8 > 0) goto L44
        L42:
            r4 = 0
            goto L57
        L44:
            javax.crypto.spec.SecretKeySpec r5 = com.android.systemui.controls.util.EncryptDecrypt.generatePBKDF2SecretKey(r7, r5)     // Catch: java.lang.Throwable -> L8b
            goto L4d
        L49:
            javax.crypto.spec.SecretKeySpec r5 = com.android.systemui.controls.util.EncryptDecrypt.generateSHA256SecretKey(r7)     // Catch: java.lang.Throwable -> L8b
        L4d:
            r7 = 2
            r4.init(r7, r5, r2)     // Catch: java.lang.Throwable -> L8b
            javax.crypto.CipherInputStream r5 = new javax.crypto.CipherInputStream     // Catch: java.lang.Throwable -> L8b
            r5.<init>(r0, r4)     // Catch: java.lang.Throwable -> L8b
            r4 = r5
        L57:
            if (r4 != 0) goto L5d
            r0.close()     // Catch: java.lang.Exception -> L92
            return r1
        L5d:
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L8b
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L8b
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r7 = new byte[r6]     // Catch: java.lang.Throwable -> L7d
        L66:
            int r8 = r4.read(r7, r1, r6)     // Catch: java.lang.Throwable -> L7d
            r2 = -1
            if (r8 == r2) goto L71
            r5.write(r7, r1, r8)     // Catch: java.lang.Throwable -> L7d
            goto L66
        L71:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L7d
            r4.close()     // Catch: java.lang.Throwable -> L84
            r5.close()     // Catch: java.lang.Throwable -> L8b
            r0.close()     // Catch: java.lang.Exception -> L92
            return r3
        L7d:
            r6 = move-exception
            throw r6     // Catch: java.lang.Throwable -> L7f
        L7f:
            r7 = move-exception
            kotlin.io.CloseableKt.closeFinally(r4, r6)     // Catch: java.lang.Throwable -> L84
            throw r7     // Catch: java.lang.Throwable -> L84
        L84:
            r4 = move-exception
            throw r4     // Catch: java.lang.Throwable -> L86
        L86:
            r6 = move-exception
            kotlin.io.CloseableKt.closeFinally(r5, r4)     // Catch: java.lang.Throwable -> L8b
            throw r6     // Catch: java.lang.Throwable -> L8b
        L8b:
            r4 = move-exception
            throw r4     // Catch: java.lang.Throwable -> L8d
        L8d:
            r5 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r4)     // Catch: java.lang.Exception -> L92
            throw r5     // Catch: java.lang.Exception -> L92
        L92:
            r4 = move-exception
            r4.printStackTrace()
        L96:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.util.EncryptDecryptWrapper.decryptFile(java.io.File, java.io.File, java.lang.String, com.android.systemui.controls.util.ControlsBackUpRestore$BNRSecurityLevel):boolean");
    }

    public final boolean encryptFile(File file, File file2, String str, ControlsBackUpRestore$BNRSecurityLevel controlsBackUpRestore$BNRSecurityLevel) {
        if (setup(file) && setup(file2)) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    EncryptDecrypt encryptDecrypt = this.encryptDecrypt;
                    int value = controlsBackUpRestore$BNRSecurityLevel.getValue();
                    encryptDecrypt.getClass();
                    OutputStream encryptStream = EncryptDecrypt.encryptStream(fileOutputStream, str, value);
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int read = fileInputStream.read(bArr, 0, 1024);
                                if (read == -1) {
                                    Unit unit = Unit.INSTANCE;
                                    fileInputStream.close();
                                    ((CipherOutputStream) encryptStream).close();
                                    fileOutputStream.close();
                                    return true;
                                }
                                encryptStream.write(bArr, 0, read);
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
