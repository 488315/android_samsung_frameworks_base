package com.android.systemui.controls.util;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.io.CloseableKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EncryptDecryptWrapper {
    public final EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            AbsAdapter$1$$ExternalSyntheticOutline0.m39m("make file Exception: ", e, "EncryptDecryptWrapper");
            return false;
        }
    }

    public final boolean decryptFile(File file, File file2, String str, ControlsBackUpRestore$BNRSecurityLevel controlsBackUpRestore$BNRSecurityLevel) {
        if (setup(file) && setup(file2)) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    EncryptDecrypt encryptDecrypt = this.encryptDecrypt;
                    int value = controlsBackUpRestore$BNRSecurityLevel.getValue();
                    encryptDecrypt.getClass();
                    InputStream decryptStream = EncryptDecrypt.decryptStream(fileInputStream, str, value);
                    if (decryptStream == null) {
                        CloseableKt.closeFinally(fileInputStream, null);
                        return false;
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    try {
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int read = decryptStream.read(bArr, 0, 1024);
                                if (read == -1) {
                                    Unit unit = Unit.INSTANCE;
                                    CloseableKt.closeFinally(decryptStream, null);
                                    CloseableKt.closeFinally(fileOutputStream, null);
                                    CloseableKt.closeFinally(fileInputStream, null);
                                    return true;
                                }
                                fileOutputStream.write(bArr, 0, read);
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
