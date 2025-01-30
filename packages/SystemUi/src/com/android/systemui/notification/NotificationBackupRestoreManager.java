package com.android.systemui.notification;

import android.app.INotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.subscreen.SubRoom;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationBackupRestoreManager {
    public static Cipher mCipher;
    public static byte[] mSalt;
    public static SecretKeySpec mSecretKey;
    public static String mSecurityPassword;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class NotificationBnRReceiver extends BroadcastReceiver {
        public Thread mBackupThread;

        @Override // android.content.BroadcastReceiver
        public final void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            AbstractC0689x6838b71d.m68m("onReceive ( action = ", action, ")", "NotifBnRManager");
            if (action != null) {
                try {
                    final String stringExtra = intent.getStringExtra("SAVE_PATH");
                    final String stringExtra2 = intent.getStringExtra("SOURCE");
                    final String stringExtra3 = intent.getStringExtra("SESSION_KEY");
                    String stringExtra4 = intent.getStringExtra("EXPORT_SESSION_TIME");
                    int intExtra = intent.getIntExtra("ACTION", 0);
                    final int intExtra2 = intent.getIntExtra("SECURITY_LEVEL", 0);
                    final List list = (List) intent.getSerializableExtra("NOTIFICATION_BLOCK_LIST");
                    if (action.equals("com.samsung.android.intent.action.REQUEST_BACKUP_NOTIFICATION")) {
                        if (intExtra == 2) {
                            Thread thread = this.mBackupThread;
                            if (thread != null && thread.isAlive()) {
                                Log.d("NotifBnRManager", "stop backup working thread for quickpanel");
                                this.mBackupThread.interrupt();
                                this.mBackupThread = null;
                                NotificationBackupRestoreManager notificationBackupRestoreManager = (NotificationBackupRestoreManager) Dependency.get(NotificationBackupRestoreManager.class);
                                ERR_CODE err_code = ERR_CODE.UNKNOWN_ERROR;
                                notificationBackupRestoreManager.getClass();
                                NotificationBackupRestoreManager.sendResponse(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_NOTIFICATION", 1, err_code, stringExtra2, stringExtra4);
                            }
                        } else {
                            Thread thread2 = new Thread(new Runnable(this) { // from class: com.android.systemui.notification.NotificationBackupRestoreManager.NotificationBnRReceiver.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ((NotificationBackupRestoreManager) Dependency.get(NotificationBackupRestoreManager.class)).startBackup(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_NOTIFICATION", stringExtra, stringExtra2, intExtra2, "", stringExtra3, list);
                                }
                            }, "REQUEST_BACKUP_NOTIFICATION");
                            this.mBackupThread = thread2;
                            thread2.start();
                        }
                    } else if (action.equals("com.samsung.android.intent.action.REQUEST_RESTORE_NOTIFICATION")) {
                        new Thread(new Runnable(this) { // from class: com.android.systemui.notification.NotificationBackupRestoreManager.NotificationBnRReceiver.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                ((NotificationBackupRestoreManager) Dependency.get(NotificationBackupRestoreManager.class)).startRestore(context, "com.samsung.android.intent.action.RESPONSE_RESTORE_NOTIFICATION", stringExtra, stringExtra2, intExtra2, stringExtra3, list);
                            }
                        }, "REQUEST_RESTORE_NOTIFICATION").start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public NotificationBackupRestoreManager() {
        new LinkedHashMap();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(25:0|1|(3:2|3|(1:5))|7|(1:9)|10|(1:12)|13|14|15|(3:66|67|(1:69))|17|18|(2:19|(2:20|21))|(3:23|24|25)|26|27|(1:29)(1:41)|30|31|(1:33)|35|36|37|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0155, code lost:
    
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0156, code lost:
    
        r9.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x014b A[Catch: IOException -> 0x0155, TRY_LEAVE, TryCatch #2 {IOException -> 0x0155, blocks: (B:31:0x0133, B:33:0x014b), top: B:30:0x0133 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0132  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int createBackupFile(int i, String str, List list) {
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
        INotificationManager asInterface = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
        int i3 = 0;
        if (list != null) {
            try {
                if (list.size() > 0) {
                    asInterface.setRestoreBlockListForSS(list);
                }
            } catch (Exception e3) {
                Slog.d("NotifBnRManager", "copyBackupFile Failed");
                e3.printStackTrace();
            }
        }
        byte[] backupPayload = asInterface.getBackupPayload(0);
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(str2);
                } catch (Exception e4) {
                    e = e4;
                }
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
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
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
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

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0085, code lost:
    
        if (r3 != 0) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00c1, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00be, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00bc, code lost:
    
        if (r3 != 0) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00a3, code lost:
    
        if (r3 != 0) goto L64;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00d1  */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static File decrypt(int i, String str) {
        Throwable th;
        ?? r2;
        ?? r3;
        FileOutputStream fileOutputStream;
        ?? r32;
        Exception e;
        InputStream inputStream;
        IOException e2;
        ?? r9;
        String str2;
        File file;
        File file2 = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "/decrypt_notification_policy.xml"));
        String str3 = "/encrypt_notification_policy.xml";
        File file3 = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "/encrypt_notification_policy.xml"));
        InputStream inputStream2 = null;
        try {
            try {
                if (!file3.exists()) {
                    Log.e("NotifBnRManager", "decrypt: file is not found.encrypt_notification_policy.xml");
                    return null;
                }
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                if (file3.length() > 0) {
                    r32 = new FileInputStream(file3);
                    try {
                        inputStream = decryptStream(r32, i);
                    } catch (IOException e3) {
                        fileOutputStream = null;
                        e2 = e3;
                        inputStream = null;
                    } catch (Exception e4) {
                        fileOutputStream = null;
                        e = e4;
                        inputStream = null;
                    } catch (Throwable th2) {
                        th = th2;
                        r2 = 0;
                        r3 = r32;
                        if (inputStream2 != null) {
                            inputStream2.close();
                        }
                        if (r2 != 0) {
                            r2.close();
                        }
                        if (r3 != 0) {
                            r3.close();
                        }
                        throw th;
                    }
                    try {
                        fileOutputStream = new FileOutputStream(file2);
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int read = inputStream.read(bArr, 0, 1024);
                                if (read == -1) {
                                    break;
                                }
                                fileOutputStream.write(bArr, 0, read);
                            }
                            inputStream2 = inputStream;
                            r32 = r32;
                        } catch (IOException e5) {
                            e2 = e5;
                            Log.d("NotifBnRManager", e2.toString());
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                        } catch (Exception e6) {
                            e = e6;
                            Log.d("NotifBnRManager", e.toString());
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                        }
                    } catch (IOException e7) {
                        fileOutputStream = null;
                        e2 = e7;
                    } catch (Exception e8) {
                        fileOutputStream = null;
                        e = e8;
                    } catch (Throwable th3) {
                        th = th3;
                        file = null;
                        str2 = r32;
                        r9 = inputStream;
                        inputStream2 = r9;
                        r2 = file;
                        r3 = str2;
                        if (inputStream2 != null) {
                        }
                        if (r2 != 0) {
                        }
                        if (r3 != 0) {
                        }
                        throw th;
                    }
                } else {
                    fileOutputStream = null;
                    r32 = 0;
                }
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Throwable th4) {
                th = th4;
                file = file3;
                str2 = str3;
                r9 = i;
            }
        } catch (IOException e9) {
            fileOutputStream = null;
            r32 = 0;
            e2 = e9;
            inputStream = null;
        } catch (Exception e10) {
            fileOutputStream = null;
            r32 = 0;
            e = e10;
            inputStream = null;
        } catch (Throwable th5) {
            th = th5;
            r2 = 0;
            r3 = 0;
        }
    }

    public static InputStream decryptStream(InputStream inputStream, int i) {
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

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0091, code lost:
    
        if (r7 == 0) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0093, code lost:
    
        r7.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0096, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0079, code lost:
    
        if (r7 == 0) goto L68;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00aa  */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r7v10, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v20, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void encrypt(File file, String str, int i) {
        Throwable th;
        FileInputStream fileInputStream;
        Exception e;
        IOException e2;
        OutputStream outputStream;
        OutputStream outputStream2;
        File file2 = new File(str);
        OutputStream outputStream3 = null;
        outputStream3 = null;
        outputStream3 = null;
        outputStream3 = null;
        FileInputStream fileInputStream2 = null;
        outputStream3 = null;
        outputStream3 = null;
        outputStream3 = null;
        try {
            try {
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                if (file.length() > 0) {
                    fileInputStream = new FileInputStream((File) file);
                    try {
                        file = new FileOutputStream(file2);
                    } catch (IOException e3) {
                        e2 = e3;
                        file = 0;
                        Log.d("NotifBnRManager", e2.toString());
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (outputStream3 != null) {
                            outputStream3.close();
                        }
                    } catch (Exception e4) {
                        e = e4;
                        file = 0;
                        Log.d("NotifBnRManager", e.toString());
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (outputStream3 != null) {
                            outputStream3.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        file = 0;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (outputStream3 != null) {
                            outputStream3.close();
                        }
                        if (file != 0) {
                            file.close();
                        }
                        throw th;
                    }
                    try {
                        outputStream3 = encryptStream(file, i);
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = fileInputStream.read(bArr, 0, 1024);
                            if (read == -1) {
                                break;
                            } else {
                                outputStream3.write(bArr, 0, read);
                            }
                        }
                        outputStream2 = file;
                        outputStream = outputStream3;
                        fileInputStream2 = fileInputStream;
                    } catch (IOException e5) {
                        e2 = e5;
                        Log.d("NotifBnRManager", e2.toString());
                        if (fileInputStream != null) {
                        }
                        if (outputStream3 != null) {
                        }
                    } catch (Exception e6) {
                        e = e6;
                        Log.d("NotifBnRManager", e.toString());
                        if (fileInputStream != null) {
                        }
                        if (outputStream3 != null) {
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        if (fileInputStream != null) {
                        }
                        if (outputStream3 != null) {
                        }
                        if (file != 0) {
                        }
                        throw th;
                    }
                } else {
                    outputStream = null;
                    outputStream2 = null;
                }
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (outputStream2 != null) {
                    outputStream2.close();
                }
            } catch (Throwable th4) {
                th = th4;
                outputStream3 = outputStream3;
                file = file;
            }
        } catch (IOException e7) {
            e2 = e7;
            fileInputStream = null;
        } catch (Exception e8) {
            e = e8;
            fileInputStream = null;
        } catch (Throwable th5) {
            th = th5;
            fileInputStream = null;
        }
    }

    public static OutputStream encryptStream(OutputStream outputStream, int i) {
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

    public static SecretKeySpec generateSHA256SecretKey() {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(mSecurityPassword.getBytes("UTF-8"));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        return new SecretKeySpec(bArr, "AES");
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean loadRestoreFile(File file, List list) {
        FileInputStream fileInputStream;
        byte[] bArr;
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
            fileInputStream = fileInputStream2;
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
            INotificationManager asInterface = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
            if (list != null) {
            }
            asInterface.applyRestore(bArr2, 0);
            return true;
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e5) {
                    Log.d("NotifBnRManager", "loadRestoreFile failed", e5);
                }
            }
            throw th;
        }
        INotificationManager asInterface2 = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
        if (list != null) {
            try {
                if (list.size() > 0) {
                    asInterface2.setRestoreBlockListForSS(list);
                }
            } catch (Exception e6) {
                e6.printStackTrace();
                return false;
            }
        }
        asInterface2.applyRestore(bArr2, 0);
        return true;
    }

    public static void sendResponse(Context context, String str, int i, ERR_CODE err_code, String str2, String str3) {
        StringBuilder m92m = AbstractC0950x8906c950.m92m(" action=", str, " resultCode=", i, " errorCode=");
        m92m.append(err_code);
        m92m.append(" requiredSize=0");
        Log.d("NotifBnRManager", m92m.toString());
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

    public static void streamCrypt(String str) {
        mSecurityPassword = str;
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(mSecurityPassword.getBytes("UTF-8"));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        mSecretKey = new SecretKeySpec(bArr, "AES");
    }

    public void startBackup(Context context, String str, String str2, String str3, int i, String str4, String str5, List<String> list) {
        CustomizationProvider$$ExternalSyntheticOutline0.m135m("start backup basePath=", str2, " source=", str3, "NotifBnRManager");
        ERR_CODE err_code = ERR_CODE.SUCCESS;
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, "/");
        try {
            streamCrypt(str5);
            int createBackupFile = createBackupFile(i, m14m, list);
            Log.d("NotifBnRManager", "resultCode=" + createBackupFile);
            sendResponse(context, str, createBackupFile, createBackupFile == 1 ? ERR_CODE.INVALID_DATA : err_code, str3, str4);
        } catch (Exception e) {
            sendResponse(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_NOTIFICATION", 1, ERR_CODE.INVALID_DATA, str3, str4);
            e.printStackTrace();
        }
    }

    public void startRestore(Context context, String str, String str2, String str3, int i, String str4, List<String> list) {
        int i2;
        CustomizationProvider$$ExternalSyntheticOutline0.m135m("start restore basePath=", str2, " source=", str3, "NotifBnRManager");
        ERR_CODE err_code = ERR_CODE.SUCCESS;
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, "/");
        try {
            streamCrypt(str4);
            if (loadRestoreFile(decrypt(i, m14m), list)) {
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
