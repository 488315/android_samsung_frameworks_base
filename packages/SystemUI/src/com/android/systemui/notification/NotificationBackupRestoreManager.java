package com.android.systemui.notification;

import android.app.INotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.subscreen.SubRoom;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class NotificationBackupRestoreManager {
    public static Cipher mCipher;
    public static byte[] mSalt;
    public static SecretKeySpec mSecretKey;
    public static String mSecurityPassword;

    public enum ERR_CODE {
        SUCCESS(0),
        UNKNOWN_ERROR(1),
        /* JADX INFO: Fake field, exist only in values array */
        STORAGE_FULL(2),
        INVALID_DATA(3),
        /* JADX INFO: Fake field, exist only in values array */
        PARTIAL_SUCCESS(7);

        private int value;

        ERR_CODE(int i) {
            this.value = i;
        }

        public final int getValue() {
            return this.value;
        }
    }

    public class NotificationBnRReceiver extends BroadcastReceiver {
        public Thread mBackupThread;

        @Override // android.content.BroadcastReceiver
        public final void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onReceive ( action = ", action, ")", "NotifBnRManager");
            if (action != null) {
                try {
                    final String stringExtra = intent.getStringExtra("SAVE_PATH");
                    final String stringExtra2 = intent.getStringExtra("SOURCE");
                    final String stringExtra3 = intent.getStringExtra("SESSION_KEY");
                    String stringExtra4 = intent.getStringExtra("EXPORT_SESSION_TIME");
                    int intExtra = intent.getIntExtra("ACTION", 0);
                    final int intExtra2 = intent.getIntExtra("SECURITY_LEVEL", 0);
                    final List list = (List) intent.getSerializableExtra("NOTIFICATION_BLOCK_LIST");
                    if (!action.equals("com.samsung.android.intent.action.REQUEST_BACKUP_NOTIFICATION")) {
                        if (action.equals("com.samsung.android.intent.action.REQUEST_RESTORE_NOTIFICATION")) {
                            new Thread(new Runnable(this) { // from class: com.android.systemui.notification.NotificationBackupRestoreManager.NotificationBnRReceiver.2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ((NotificationBackupRestoreManager) Dependency.sDependency.getDependencyInner(NotificationBackupRestoreManager.class)).startRestore(context, "com.samsung.android.intent.action.RESPONSE_RESTORE_NOTIFICATION", stringExtra, stringExtra2, intExtra2, stringExtra3, list);
                                }
                            }, "REQUEST_RESTORE_NOTIFICATION").start();
                            return;
                        }
                        return;
                    }
                    if (intExtra != 2) {
                        Thread thread = new Thread(new Runnable(this) { // from class: com.android.systemui.notification.NotificationBackupRestoreManager.NotificationBnRReceiver.1
                            @Override // java.lang.Runnable
                            public final void run() throws Throwable {
                                ((NotificationBackupRestoreManager) Dependency.sDependency.getDependencyInner(NotificationBackupRestoreManager.class)).startBackup(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_NOTIFICATION", stringExtra, stringExtra2, intExtra2, "", stringExtra3, list);
                            }
                        }, "REQUEST_BACKUP_NOTIFICATION");
                        this.mBackupThread = thread;
                        thread.start();
                        return;
                    }
                    Thread thread2 = this.mBackupThread;
                    if (thread2 == null || !thread2.isAlive()) {
                        return;
                    }
                    Log.d("NotifBnRManager", "stop backup working thread for quickpanel");
                    this.mBackupThread.interrupt();
                    this.mBackupThread = null;
                    NotificationBackupRestoreManager notificationBackupRestoreManager = (NotificationBackupRestoreManager) Dependency.sDependency.getDependencyInner(NotificationBackupRestoreManager.class);
                    ERR_CODE err_code = ERR_CODE.UNKNOWN_ERROR;
                    notificationBackupRestoreManager.getClass();
                    NotificationBackupRestoreManager.sendResponse(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_NOTIFICATION", 1, err_code, stringExtra2, stringExtra4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public NotificationBackupRestoreManager() {
        new LinkedHashMap();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(26:0|2|(3:81|3|(1:5))|9|(1:11)|12|(1:14)|66|15|19|(3:77|21|(1:23))|26|27|(2:74|(2:69|28))|(3:83|29|30)|68|73|52|(1:54)(1:55)|71|56|(1:58)|62|64|65|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0155, code lost:
    
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0159, code lost:
    
        r9.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:54:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x014c A[Catch: IOException -> 0x0155, TRY_LEAVE, TryCatch #3 {IOException -> 0x0155, blocks: (B:56:0x0134, B:58:0x014c), top: B:71:0x0134 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x00e9 -> B:68:0x0121). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int createBackupFile(int i, String str, List list) throws Throwable {
        int i2;
        FileOutputStream fileOutputStream;
        Log.d("NotifBnRManager", "create backup file basePath=" + str);
        try {
            File file = new File(str + "notification_policy.xml");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("NotifBnRManager", "basePath=" + str);
        File file2 = new File(str);
        if (!file2.exists()) {
            Log.i("NotifBnRManager", file2.mkdir() + ", folder created last");
        }
        String str2 = file2.getPath() + "/notification_policy.xml";
        File file3 = new File(file2.getPath() + "/notification_policy.xml");
        if (file3.exists()) {
            file3.delete();
        }
        try {
            file3.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        Slog.d("NotifBnRManager", "copyBackupFile path=" + str2);
        INotificationManager iNotificationManagerAsInterface = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
        int i3 = 0;
        if (list != null) {
            try {
                if (list.size() > 0) {
                    iNotificationManagerAsInterface.setRestoreBlockListForSS(list);
                }
            } catch (Exception e3) {
                Slog.d("NotifBnRManager", "copyBackupFile Failed");
                e3.printStackTrace();
            }
        }
        byte[] backupPayload = iNotificationManagerAsInterface.getBackupPayload(0);
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(str2);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        try {
            fileOutputStream.write(backupPayload);
            fileOutputStream.close();
        } catch (Exception e6) {
            e = e6;
            fileOutputStream2 = fileOutputStream;
            Slog.d("NotifBnRManager", "copyBackupFile Exception!! fout:" + fileOutputStream2);
            e.printStackTrace();
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            if (file3.length() > 0) {
            }
            encrypt(file3, str + "/encrypt_notification_policy.xml", i);
            if (file3.exists()) {
            }
            i3 = i2;
            return i3 ^ 1;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (Exception e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
        if (file3.length() > 0) {
            Log.e("NotifBnRManager", "Backup file size error");
            i2 = 0;
        } else {
            i2 = 1;
        }
        encrypt(file3, str + "/encrypt_notification_policy.xml", i);
        if (file3.exists()) {
            file3.delete();
            Log.e("NotifBnRManager", "file delete!!!");
        }
        i3 = i2;
        return i3 ^ 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00b8 A[PHI: r2 r3 r10
      0x00b8: PHI (r2v10 ??) = (r2v8 ??), (r2v11 ??) binds: [B:59:0x00b6, B:68:0x00cf] A[DONT_GENERATE, DONT_INLINE]
      0x00b8: PHI (r3v8 ??) = (r3v6 ??), (r3v9 ??) binds: [B:59:0x00b6, B:68:0x00cf] A[DONT_GENERATE, DONT_INLINE]
      0x00b8: PHI (r10v7 java.io.InputStream) = (r10v5 java.io.InputStream), (r10v8 java.io.InputStream) binds: [B:59:0x00b6, B:68:0x00cf] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v16, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static File decrypt(int i, String str) throws Throwable {
        OutputStream outputStream;
        InputStream inputStream;
        File file = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "/decrypt_notification_policy.xml"));
        ?? fileInputStream = "/encrypt_notification_policy.xml";
        ?? file2 = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "/encrypt_notification_policy.xml"));
        InputStream inputStream2 = null;
        try {
            try {
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
            file2 = 0;
            fileInputStream = 0;
        } catch (Exception e2) {
            e = e2;
            file2 = 0;
            fileInputStream = 0;
        } catch (Throwable th2) {
            th = th2;
            file2 = 0;
            fileInputStream = 0;
        }
        if (!file2.exists()) {
            Log.e("NotifBnRManager", "decrypt: file is not found.encrypt_notification_policy.xml");
            return null;
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        if (file2.length() > 0) {
            fileInputStream = new FileInputStream((File) file2);
            try {
                InputStream inputStreamDecryptStream = decryptStream(fileInputStream, i);
                try {
                    file2 = new FileOutputStream(file);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i2 = inputStreamDecryptStream.read(bArr, 0, 1024);
                            if (i2 == -1) {
                                break;
                            }
                            file2.write(bArr, 0, i2);
                        }
                        inputStream2 = inputStreamDecryptStream;
                        outputStream = file2;
                        inputStream = fileInputStream;
                    } catch (IOException e3) {
                        inputStream2 = inputStreamDecryptStream;
                        e = e3;
                        file2 = file2;
                        fileInputStream = fileInputStream;
                        Log.d("NotifBnRManager", e.toString());
                        if (inputStream2 != null) {
                            inputStream2.close();
                        }
                        if (file2 != 0) {
                            file2.close();
                        }
                        if (fileInputStream != 0) {
                            fileInputStream.close();
                        }
                        return file;
                    } catch (Exception e4) {
                        inputStream2 = inputStreamDecryptStream;
                        e = e4;
                        file2 = file2;
                        fileInputStream = fileInputStream;
                        Log.d("NotifBnRManager", e.toString());
                        if (inputStream2 != null) {
                            inputStream2.close();
                        }
                        if (file2 != 0) {
                            file2.close();
                        }
                        if (fileInputStream != 0) {
                        }
                        return file;
                    } catch (Throwable th3) {
                        inputStream2 = inputStreamDecryptStream;
                        th = th3;
                        if (inputStream2 != null) {
                            inputStream2.close();
                        }
                        if (file2 != 0) {
                            file2.close();
                        }
                        if (fileInputStream != 0) {
                            fileInputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    inputStream2 = inputStreamDecryptStream;
                    e = e5;
                    file2 = 0;
                    fileInputStream = fileInputStream;
                } catch (Exception e6) {
                    inputStream2 = inputStreamDecryptStream;
                    e = e6;
                    file2 = 0;
                    fileInputStream = fileInputStream;
                } catch (Throwable th4) {
                    file2 = 0;
                    inputStream2 = inputStreamDecryptStream;
                    th = th4;
                }
            } catch (IOException e7) {
                e = e7;
                file2 = 0;
                fileInputStream = fileInputStream;
            } catch (Exception e8) {
                e = e8;
                file2 = 0;
                fileInputStream = fileInputStream;
            } catch (Throwable th5) {
                th = th5;
                file2 = 0;
            }
        } else {
            outputStream = null;
            inputStream = null;
        }
        if (inputStream2 != null) {
            inputStream2.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
            return file;
        }
        return file;
    }

    public static InputStream decryptStream(InputStream inputStream, int i) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] bArr = new byte[mCipher.getBlockSize()];
        inputStream.read(bArr);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        if (i == 1) {
            byte[] bArr2 = new byte[16];
            mSalt = bArr2;
            inputStream.read(bArr2);
            mSecretKey = generatePBKDF2SecretKey();
        } else {
            mSecretKey = generateSHA256SecretKey();
        }
        mCipher.init(2, mSecretKey, ivParameterSpec);
        return new CipherInputStream(inputStream, mCipher);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0091 A[PHI: r1 r7 r8
      0x0091: PHI (r1v8 ??) = (r1v6 ??), (r1v9 ??) binds: [B:50:0x008f, B:59:0x00a8] A[DONT_GENERATE, DONT_INLINE]
      0x0091: PHI (r7v6 java.io.FileInputStream) = (r7v4 java.io.FileInputStream), (r7v7 java.io.FileInputStream) binds: [B:50:0x008f, B:59:0x00a8] A[DONT_GENERATE, DONT_INLINE]
      0x0091: PHI (r8v8 ??) = (r8v6 ??), (r8v9 ??) binds: [B:50:0x008f, B:59:0x00a8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r8v0, types: [int] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v26 */
    /* JADX WARN: Type inference failed for: r8v27 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9, types: [java.io.OutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void encrypt(File file, String str, int i) throws Throwable {
        OutputStream outputStream;
        FileOutputStream fileOutputStream;
        OutputStream outputStream2;
        FileOutputStream fileOutputStream2;
        OutputStream outputStream3;
        FileOutputStream fileOutputStream3;
        ?? file2 = new File(str);
        FileInputStream fileInputStream = null;
        outputStreamEncryptStream = null;
        outputStreamEncryptStream = null;
        OutputStream outputStreamEncryptStream = null;
        FileInputStream fileInputStream2 = null;
        fileInputStream = null;
        fileInputStream = null;
        fileInputStream = null;
        try {
            try {
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                if (file.length() > 0) {
                    FileInputStream fileInputStream3 = new FileInputStream(file);
                    try {
                        FileOutputStream fileOutputStream4 = new FileOutputStream((File) file2);
                        try {
                            outputStreamEncryptStream = encryptStream(fileOutputStream4, i);
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int i2 = fileInputStream3.read(bArr, 0, 1024);
                                if (i2 == -1) {
                                    break;
                                } else {
                                    outputStreamEncryptStream.write(bArr, 0, i2);
                                }
                            }
                            fileOutputStream = fileOutputStream4;
                            outputStream = outputStreamEncryptStream;
                            fileInputStream2 = fileInputStream3;
                        } catch (IOException e) {
                            fileOutputStream3 = fileOutputStream4;
                            e = e;
                            outputStream3 = outputStreamEncryptStream;
                            fileInputStream = fileInputStream3;
                            file2 = fileOutputStream3;
                            i = outputStream3;
                            Log.d("NotifBnRManager", e.toString());
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (i != 0) {
                                i.close();
                            }
                            if (file2 != 0) {
                                file2.close();
                            }
                            return;
                        } catch (Exception e2) {
                            fileOutputStream2 = fileOutputStream4;
                            e = e2;
                            outputStream2 = outputStreamEncryptStream;
                            fileInputStream = fileInputStream3;
                            file2 = fileOutputStream2;
                            i = outputStream2;
                            Log.d("NotifBnRManager", e.toString());
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (i != 0) {
                                i.close();
                            }
                            if (file2 != 0) {
                            }
                            return;
                        } catch (Throwable th) {
                            file2 = fileOutputStream4;
                            th = th;
                            i = outputStreamEncryptStream;
                            fileInputStream = fileInputStream3;
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (i != 0) {
                                i.close();
                            }
                            if (file2 != 0) {
                                file2.close();
                            }
                            throw th;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        outputStream3 = null;
                        fileOutputStream3 = null;
                    } catch (Exception e4) {
                        e = e4;
                        outputStream2 = null;
                        fileOutputStream2 = null;
                    } catch (Throwable th2) {
                        th = th2;
                        i = 0;
                        file2 = 0;
                    }
                } else {
                    outputStream = null;
                    fileOutputStream = null;
                }
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (IOException e5) {
            e = e5;
            i = 0;
            file2 = 0;
        } catch (Exception e6) {
            e = e6;
            i = 0;
            file2 = 0;
        } catch (Throwable th4) {
            th = th4;
            i = 0;
            file2 = 0;
        }
    }

    public static OutputStream encryptStream(OutputStream outputStream, int i) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] bArr = new byte[mCipher.getBlockSize()];
        new SecureRandom().nextBytes(bArr);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        outputStream.write(bArr);
        if (i == 1) {
            byte[] bArr2 = new byte[16];
            new SecureRandom().nextBytes(bArr2);
            mSalt = bArr2;
            outputStream.write(bArr2);
            mSecretKey = generatePBKDF2SecretKey();
        } else {
            mSecretKey = generateSHA256SecretKey();
        }
        mCipher.init(1, mSecretKey, ivParameterSpec);
        return new CipherOutputStream(outputStream, mCipher);
    }

    public static SecretKeySpec generatePBKDF2SecretKey() {
        return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(mSecurityPassword.toCharArray(), mSalt, 1000, 256)).getEncoded(), "AES");
    }

    public static SecretKeySpec generateSHA256SecretKey() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(mSecurityPassword.getBytes("UTF-8"));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        return new SecretKeySpec(bArr, "AES");
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x006e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean loadRestoreFile(File file, List list) throws Throwable {
        byte[] bArr;
        FileInputStream fileInputStream;
        Log.d("NotifBnRManager", " filename=" + file);
        Log.d("NotifBnRManager", " filename path=" + file.getPath());
        FileInputStream fileInputStream2 = null;
        byte[] bArr2 = null;
        fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(file.getPath());
            } catch (Exception e) {
                e = e;
                bArr = null;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            bArr2 = new byte[(int) file.length()];
            fileInputStream.read(bArr2);
            try {
                fileInputStream.close();
            } catch (Exception e2) {
                Log.d("NotifBnRManager", "loadRestoreFile failed", e2);
            }
        } catch (Exception e3) {
            e = e3;
            bArr = bArr2;
            fileInputStream2 = fileInputStream;
            e.printStackTrace();
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (Exception e4) {
                    Log.d("NotifBnRManager", "loadRestoreFile failed", e4);
                }
            }
            bArr2 = bArr;
            INotificationManager iNotificationManagerAsInterface = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
            if (list != null) {
            }
            iNotificationManagerAsInterface.applyRestore(bArr2, 0);
            return true;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (Exception e5) {
                    Log.d("NotifBnRManager", "loadRestoreFile failed", e5);
                }
            }
            throw th;
        }
        INotificationManager iNotificationManagerAsInterface2 = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
        if (list != null) {
            try {
                if (list.size() > 0) {
                    iNotificationManagerAsInterface2.setRestoreBlockListForSS(list);
                }
            } catch (Exception e6) {
                e6.printStackTrace();
                return false;
            }
        }
        iNotificationManagerAsInterface2.applyRestore(bArr2, 0);
        return true;
    }

    public static void sendResponse(Context context, String str, int i, ERR_CODE err_code, String str2, String str3) {
        StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(i, " action=", str, " resultCode=", " errorCode=");
        sbM890m.append(err_code);
        sbM890m.append(" requiredSize=0");
        Log.d("NotifBnRManager", sbM890m.toString());
        Intent intent = new Intent();
        intent.setAction(str);
        intent.putExtra("RESULT", i);
        intent.putExtra("ERR_CODE", err_code.getValue());
        intent.putExtra("REQ_SIZE", 0);
        intent.putExtra("SOURCE", str2);
        if (str3 != null) {
            intent.putExtra("EXPORT_SESSION_TIME", str3);
        }
        context.sendBroadcast(intent, "com.wssnps.permission.COM_WSSNPS");
        Log.d("NotifBnRManager", "sendBroadcast. ");
    }

    public static void streamCrypt(String str) throws NoSuchAlgorithmException {
        mSecurityPassword = str;
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(mSecurityPassword.getBytes("UTF-8"));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        mSecretKey = new SecretKeySpec(bArr, "AES");
    }

    public void startBackup(Context context, String str, String str2, String str3, int i, String str4, String str5, List<String> list) throws Throwable {
        MediaSessions$H$$ExternalSyntheticOutline0.m("start backup basePath=", str2, " source=", str3, "NotifBnRManager");
        ERR_CODE err_code = ERR_CODE.SUCCESS;
        String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, "/");
        try {
            streamCrypt(str5);
            int iCreateBackupFile = createBackupFile(i, strM, list);
            Log.d("NotifBnRManager", "resultCode=" + iCreateBackupFile);
            if (iCreateBackupFile == 1) {
                err_code = ERR_CODE.INVALID_DATA;
            }
            sendResponse(context, str, iCreateBackupFile, err_code, str3, str4);
        } catch (Exception e) {
            sendResponse(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_NOTIFICATION", 1, ERR_CODE.INVALID_DATA, str3, str4);
            e.printStackTrace();
        }
    }

    public void startRestore(Context context, String str, String str2, String str3, int i, String str4, List<String> list) {
        int i2;
        MediaSessions$H$$ExternalSyntheticOutline0.m("start restore basePath=", str2, " source=", str3, "NotifBnRManager");
        ERR_CODE err_code = ERR_CODE.SUCCESS;
        String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, "/");
        try {
            streamCrypt(str4);
            if (loadRestoreFile(decrypt(i, strM), list)) {
                i2 = 0;
            } else {
                err_code = ERR_CODE.INVALID_DATA;
                i2 = 1;
            }
            sendResponse(context, str, i2, err_code, str3, null);
        } catch (Exception e) {
            sendResponse(context, "com.samsung.android.intent.action.RESPONSE_RESTORE_NOTIFICATION", 1, ERR_CODE.INVALID_DATA, str3, null);
            e.printStackTrace();
        }
    }
}
