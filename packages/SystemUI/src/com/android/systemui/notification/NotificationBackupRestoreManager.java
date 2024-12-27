package com.android.systemui.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
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

public final class NotificationBackupRestoreManager {
    public static Cipher mCipher;
    public static byte[] mSalt;
    public static SecretKeySpec mSecretKey;
    public static String mSecurityPassword;

    public enum ERR_CODE {
        SUCCESS(0),
        UNKNOWN_ERROR(1),
        STORAGE_FULL(2),
        INVALID_DATA(3),
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
                    if (action.equals("com.samsung.android.intent.action.REQUEST_BACKUP_NOTIFICATION")) {
                        if (intExtra == 2) {
                            Thread thread = this.mBackupThread;
                            if (thread != null && thread.isAlive()) {
                                Log.d("NotifBnRManager", "stop backup working thread for quickpanel");
                                this.mBackupThread.interrupt();
                                this.mBackupThread = null;
                                NotificationBackupRestoreManager notificationBackupRestoreManager = (NotificationBackupRestoreManager) Dependency.sDependency.getDependencyInner(NotificationBackupRestoreManager.class);
                                ERR_CODE err_code = ERR_CODE.UNKNOWN_ERROR;
                                notificationBackupRestoreManager.getClass();
                                NotificationBackupRestoreManager.sendResponse(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_NOTIFICATION", 1, err_code, stringExtra2, stringExtra4);
                            }
                        } else {
                            Thread thread2 = new Thread(new Runnable(this) { // from class: com.android.systemui.notification.NotificationBackupRestoreManager.NotificationBnRReceiver.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ((NotificationBackupRestoreManager) Dependency.sDependency.getDependencyInner(NotificationBackupRestoreManager.class)).startBackup(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_NOTIFICATION", stringExtra, stringExtra2, intExtra2, "", stringExtra3, list);
                                }
                            }, "REQUEST_BACKUP_NOTIFICATION");
                            this.mBackupThread = thread2;
                            thread2.start();
                        }
                    } else if (action.equals("com.samsung.android.intent.action.REQUEST_RESTORE_NOTIFICATION")) {
                        new Thread(new Runnable(this) { // from class: com.android.systemui.notification.NotificationBackupRestoreManager.NotificationBnRReceiver.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                ((NotificationBackupRestoreManager) Dependency.sDependency.getDependencyInner(NotificationBackupRestoreManager.class)).startRestore(context, "com.samsung.android.intent.action.RESPONSE_RESTORE_NOTIFICATION", stringExtra, stringExtra2, intExtra2, stringExtra3, list);
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int createBackupFile(java.lang.String r9, int r10, java.util.List r11) {
        /*
            Method dump skipped, instructions count: 351
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.notification.NotificationBackupRestoreManager.createBackupFile(java.lang.String, int, java.util.List):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x009f, code lost:
    
        if (r3 != null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a1, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00cf, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00cc, code lost:
    
        if (r3 != null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b6, code lost:
    
        if (r3 != null) goto L52;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.io.File decrypt(int r9, java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.notification.NotificationBackupRestoreManager.decrypt(int, java.lang.String):java.io.File");
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

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a8, code lost:
    
        if (r1 != 0) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0091, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ab, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x008f, code lost:
    
        if (r1 != 0) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void encrypt(java.io.File r6, java.lang.String r7, int r8) {
        /*
            Method dump skipped, instructions count: 188
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.notification.NotificationBackupRestoreManager.encrypt(java.io.File, java.lang.String, int):void");
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean loadRestoreFile(java.io.File r7, java.util.List r8) {
        /*
            java.lang.String r0 = "loadRestoreFile failed"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = " filename="
            r1.<init>(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "NotifBnRManager"
            android.util.Log.d(r2, r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = " filename path="
            r1.<init>(r3)
            java.lang.String r3 = r7.getPath()
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r2, r1)
            r1 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            java.lang.String r4 = r7.getPath()     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            long r4 = r7.length()     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a
            int r7 = (int) r4     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a
            byte[] r1 = new byte[r7]     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a
            r3.read(r1)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a
            r3.close()     // Catch: java.lang.Exception -> L42
            goto L61
        L42:
            r7 = move-exception
            android.util.Log.d(r2, r0, r7)
            goto L61
        L47:
            r7 = move-exception
            r1 = r3
            goto L83
        L4a:
            r7 = move-exception
            r6 = r3
            r3 = r1
            r1 = r6
            goto L53
        L4f:
            r7 = move-exception
            goto L83
        L51:
            r7 = move-exception
            r3 = r1
        L53:
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L4f
            if (r1 == 0) goto L60
            r1.close()     // Catch: java.lang.Exception -> L5c
            goto L60
        L5c:
            r7 = move-exception
            android.util.Log.d(r2, r0, r7)
        L60:
            r1 = r3
        L61:
            java.lang.String r7 = "notification"
            android.os.IBinder r7 = android.os.ServiceManager.getService(r7)
            android.app.INotificationManager r7 = android.app.INotificationManager.Stub.asInterface(r7)
            r0 = 0
            if (r8 == 0) goto L7a
            int r2 = r8.size()     // Catch: java.lang.Exception -> L78
            if (r2 <= 0) goto L7a
            r7.setRestoreBlockListForSS(r8)     // Catch: java.lang.Exception -> L78
            goto L7a
        L78:
            r7 = move-exception
            goto L7f
        L7a:
            r7.applyRestore(r1, r0)     // Catch: java.lang.Exception -> L78
            r0 = 1
            goto L82
        L7f:
            r7.printStackTrace()
        L82:
            return r0
        L83:
            if (r1 == 0) goto L8d
            r1.close()     // Catch: java.lang.Exception -> L89
            goto L8d
        L89:
            r8 = move-exception
            android.util.Log.d(r2, r0, r8)
        L8d:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.notification.NotificationBackupRestoreManager.loadRestoreFile(java.io.File, java.util.List):boolean");
    }

    public static void sendResponse(Context context, String str, int i, ERR_CODE err_code, String str2, String str3) {
        StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(i, " action=", str, " resultCode=", " errorCode=");
        m.append(err_code);
        m.append(" requiredSize=0");
        Log.d("NotifBnRManager", m.toString());
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
        MWBixbyController$$ExternalSyntheticOutline0.m("start backup basePath=", str2, " source=", str3, "NotifBnRManager");
        ERR_CODE err_code = ERR_CODE.SUCCESS;
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, "/");
        try {
            streamCrypt(str5);
            int createBackupFile = createBackupFile(m, i, list);
            Log.d("NotifBnRManager", "resultCode=" + createBackupFile);
            sendResponse(context, str, createBackupFile, createBackupFile == 1 ? ERR_CODE.INVALID_DATA : err_code, str3, str4);
        } catch (Exception e) {
            sendResponse(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_NOTIFICATION", 1, ERR_CODE.INVALID_DATA, str3, str4);
            e.printStackTrace();
        }
    }

    public void startRestore(Context context, String str, String str2, String str3, int i, String str4, List<String> list) {
        int i2;
        MWBixbyController$$ExternalSyntheticOutline0.m("start restore basePath=", str2, " source=", str3, "NotifBnRManager");
        ERR_CODE err_code = ERR_CODE.SUCCESS;
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, "/");
        try {
            streamCrypt(str4);
            if (loadRestoreFile(decrypt(i, m), list)) {
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
