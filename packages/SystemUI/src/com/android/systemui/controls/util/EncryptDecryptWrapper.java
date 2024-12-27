package com.android.systemui.controls.util;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class EncryptDecryptWrapper {
    public final EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
            AbsAdapter$1$$ExternalSyntheticOutline0.m("make file Exception: ", e, "EncryptDecryptWrapper");
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0059 A[Catch: Exception -> 0x005d, TRY_ENTER, TRY_LEAVE, TryCatch #3 {Exception -> 0x005d, blocks: (B:6:0x0011, B:12:0x0059, B:26:0x007b, B:56:0x0092, B:57:0x0095, B:53:0x0090, B:8:0x0016, B:15:0x005f, B:25:0x0078, B:34:0x008c, B:35:0x008f, B:43:0x0033, B:45:0x003a, B:48:0x0045, B:49:0x004e, B:50:0x004a), top: B:5:0x0011, inners: #0, #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005f A[Catch: all -> 0x007f, TRY_ENTER, TRY_LEAVE, TryCatch #6 {all -> 0x007f, blocks: (B:8:0x0016, B:15:0x005f, B:25:0x0078, B:34:0x008c, B:35:0x008f, B:43:0x0033, B:45:0x003a, B:48:0x0045, B:49:0x004e, B:50:0x004a, B:24:0x0075, B:41:0x0086, B:42:0x0089, B:18:0x0066, B:19:0x0068, B:21:0x006f, B:23:0x0073, B:38:0x0084, B:31:0x008a), top: B:7:0x0016, outer: #3, inners: #1, #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean decryptFile(java.io.File r6, java.io.File r7, java.lang.String r8, com.android.systemui.controls.util.ControlsBackUpRestore$BNRSecurityLevel r9) {
        /*
            r5 = this;
            r0 = 1
            r1 = 0
            boolean r2 = setup(r6)
            r3 = 0
            if (r2 == 0) goto L99
            boolean r2 = setup(r7)
            if (r2 != 0) goto L11
            goto L99
        L11:
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Exception -> L5d
            r2.<init>(r6)     // Catch: java.lang.Exception -> L5d
            com.android.systemui.controls.util.EncryptDecrypt r5 = r5.encryptDecrypt     // Catch: java.lang.Throwable -> L7f
            int r6 = r9.getValue()     // Catch: java.lang.Throwable -> L7f
            r5.getClass()     // Catch: java.lang.Throwable -> L7f
            java.lang.String r5 = "AES/CBC/PKCS5Padding"
            javax.crypto.Cipher r5 = javax.crypto.Cipher.getInstance(r5)     // Catch: java.lang.Throwable -> L7f
            int r9 = r5.getBlockSize()     // Catch: java.lang.Throwable -> L7f
            byte[] r9 = new byte[r9]     // Catch: java.lang.Throwable -> L7f
            int r4 = r2.read(r9)     // Catch: java.lang.Throwable -> L7f
            if (r4 > 0) goto L33
        L31:
            r6 = r1
            goto L57
        L33:
            javax.crypto.spec.IvParameterSpec r4 = new javax.crypto.spec.IvParameterSpec     // Catch: java.lang.Throwable -> L7f
            r4.<init>(r9)     // Catch: java.lang.Throwable -> L7f
            if (r6 != r0) goto L4a
            r6 = 16
            byte[] r6 = new byte[r6]     // Catch: java.lang.Throwable -> L7f
            int r9 = r2.read(r6)     // Catch: java.lang.Throwable -> L7f
            if (r9 > 0) goto L45
            goto L31
        L45:
            javax.crypto.spec.SecretKeySpec r6 = com.android.systemui.controls.util.EncryptDecrypt.generatePBKDF2SecretKey(r8, r6)     // Catch: java.lang.Throwable -> L7f
            goto L4e
        L4a:
            javax.crypto.spec.SecretKeySpec r6 = com.android.systemui.controls.util.EncryptDecrypt.generateSHA256SecretKey(r8)     // Catch: java.lang.Throwable -> L7f
        L4e:
            r8 = 2
            r5.init(r8, r6, r4)     // Catch: java.lang.Throwable -> L7f
            javax.crypto.CipherInputStream r6 = new javax.crypto.CipherInputStream     // Catch: java.lang.Throwable -> L7f
            r6.<init>(r2, r5)     // Catch: java.lang.Throwable -> L7f
        L57:
            if (r6 != 0) goto L5f
            kotlin.io.CloseableKt.closeFinally(r2, r1)     // Catch: java.lang.Exception -> L5d
            return r3
        L5d:
            r5 = move-exception
            goto L96
        L5f:
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L7f
            r5.<init>(r7)     // Catch: java.lang.Throwable -> L7f
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r8 = new byte[r7]     // Catch: java.lang.Throwable -> L83
        L68:
            int r9 = r6.read(r8, r3, r7)     // Catch: java.lang.Throwable -> L83
            r4 = -1
            if (r9 == r4) goto L73
            r5.write(r8, r3, r9)     // Catch: java.lang.Throwable -> L83
            goto L68
        L73:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L83
            kotlin.io.CloseableKt.closeFinally(r6, r1)     // Catch: java.lang.Throwable -> L81
            kotlin.io.CloseableKt.closeFinally(r5, r1)     // Catch: java.lang.Throwable -> L7f
            kotlin.io.CloseableKt.closeFinally(r2, r1)     // Catch: java.lang.Exception -> L5d
            return r0
        L7f:
            r5 = move-exception
            goto L90
        L81:
            r6 = move-exception
            goto L8a
        L83:
            r7 = move-exception
            throw r7     // Catch: java.lang.Throwable -> L85
        L85:
            r8 = move-exception
            kotlin.io.CloseableKt.closeFinally(r6, r7)     // Catch: java.lang.Throwable -> L81
            throw r8     // Catch: java.lang.Throwable -> L81
        L8a:
            throw r6     // Catch: java.lang.Throwable -> L8b
        L8b:
            r7 = move-exception
            kotlin.io.CloseableKt.closeFinally(r5, r6)     // Catch: java.lang.Throwable -> L7f
            throw r7     // Catch: java.lang.Throwable -> L7f
        L90:
            throw r5     // Catch: java.lang.Throwable -> L91
        L91:
            r6 = move-exception
            kotlin.io.CloseableKt.closeFinally(r2, r5)     // Catch: java.lang.Exception -> L5d
            throw r6     // Catch: java.lang.Exception -> L5d
        L96:
            r5.printStackTrace()
        L99:
            return r3
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
                                    CloseableKt.closeFinally(fileInputStream, null);
                                    CloseableKt.closeFinally(encryptStream, null);
                                    CloseableKt.closeFinally(fileOutputStream, null);
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
