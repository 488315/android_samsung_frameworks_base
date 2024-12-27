package com.android.systemui.qs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public final class QSBackupRestoreManager {
    public static Cipher mCipher;
    public static byte[] mSalt;
    public static SecretKeySpec mSecretKey;
    public static String mSecurityPassword;
    public final LinkedHashMap mQSBnRMap = new LinkedHashMap();

    public interface Callback {
        boolean isValidDB();

        String onBackup(boolean z);

        void onRestore(String str);
    }

    enum ERR_CODE {
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

    public class QSBnRReceiver extends BroadcastReceiver {
        public Thread mBackupThread;

        @Override // android.content.BroadcastReceiver
        public final void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onReceive ( action = ", action, ")", "QSBackupRestoreManager");
            if (action != null) {
                try {
                    final String stringExtra = intent.getStringExtra("SAVE_PATH");
                    final String stringExtra2 = intent.getStringExtra("SOURCE");
                    final String stringExtra3 = intent.getStringExtra("SESSION_KEY");
                    String stringExtra4 = intent.getStringExtra("EXPORT_SESSION_TIME");
                    int intExtra = intent.getIntExtra("ACTION", 0);
                    final int intExtra2 = intent.getIntExtra("SECURITY_LEVEL", 0);
                    if (action.equals("com.samsung.android.intent.action.REQUEST_BACKUP_QUICKPANEL2")) {
                        if (intExtra == 2) {
                            Thread thread = this.mBackupThread;
                            if (thread != null && thread.isAlive()) {
                                Log.d("QSBackupRestoreManager", "stop backup working thread for quickpanel");
                                this.mBackupThread.interrupt();
                                this.mBackupThread = null;
                                QSBackupRestoreManager qSBackupRestoreManager = (QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class);
                                ERR_CODE err_code = ERR_CODE.UNKNOWN_ERROR;
                                qSBackupRestoreManager.getClass();
                                QSBackupRestoreManager.sendResponse(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_QUICKPANEL2", 1, err_code, stringExtra2, stringExtra4);
                            }
                        } else {
                            Thread thread2 = new Thread(new Runnable(this) { // from class: com.android.systemui.qs.QSBackupRestoreManager.QSBnRReceiver.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    QSBackupRestoreManager qSBackupRestoreManager2 = (QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class);
                                    Context context2 = context;
                                    String str = stringExtra;
                                    String str2 = stringExtra2;
                                    int i = intExtra2;
                                    String str3 = stringExtra3;
                                    qSBackupRestoreManager2.getClass();
                                    Log.d("QSBackupRestoreManager", "start backup basePath=" + str + " source=" + str2);
                                    ERR_CODE err_code2 = ERR_CODE.SUCCESS;
                                    String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "/");
                                    try {
                                        QSBackupRestoreManager.streamCrypt(str3);
                                        int createBackupFile = qSBackupRestoreManager2.createBackupFile(i, m);
                                        Log.d("QSBackupRestoreManager", "resultCode=" + createBackupFile);
                                        QSBackupRestoreManager.sendResponse(context2, "com.samsung.android.intent.action.RESPONSE_BACKUP_QUICKPANEL2", createBackupFile, createBackupFile == 1 ? ERR_CODE.INVALID_DATA : err_code2, str2, "");
                                    } catch (Exception e) {
                                        QSBackupRestoreManager.sendResponse(context2, "com.samsung.android.intent.action.RESPONSE_BACKUP_QUICKPANEL2", 1, ERR_CODE.INVALID_DATA, str2, "");
                                        e.printStackTrace();
                                    }
                                }
                            }, "REQUEST_BACKUP_QUICKPANEL");
                            this.mBackupThread = thread2;
                            thread2.start();
                        }
                    } else if (action.equals("com.samsung.android.intent.action.REQUEST_RESTORE_QUICKPANEL2")) {
                        new Thread(new Runnable(this) { // from class: com.android.systemui.qs.QSBackupRestoreManager.QSBnRReceiver.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i;
                                QSBackupRestoreManager qSBackupRestoreManager2 = (QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class);
                                Context context2 = context;
                                String str = stringExtra;
                                String str2 = stringExtra2;
                                int i2 = intExtra2;
                                String str3 = stringExtra3;
                                qSBackupRestoreManager2.getClass();
                                Log.d("QSBackupRestoreManager", "start restore basePath=" + str + " source=" + str2);
                                ERR_CODE err_code2 = ERR_CODE.SUCCESS;
                                String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "/");
                                try {
                                    QSBackupRestoreManager.streamCrypt(str3);
                                    if (qSBackupRestoreManager2.loadRestoreFile(QSBackupRestoreManager.decrypt(i2, m))) {
                                        i = 0;
                                    } else {
                                        err_code2 = ERR_CODE.INVALID_DATA;
                                        i = 1;
                                    }
                                    QSBackupRestoreManager.sendResponse(context2, "com.samsung.android.intent.action.RESPONSE_RESTORE_QUICKPANEL2", i, err_code2, str2, null);
                                } catch (Exception e) {
                                    QSBackupRestoreManager.sendResponse(context2, "com.samsung.android.intent.action.RESPONSE_RESTORE_QUICKPANEL2", 1, ERR_CODE.INVALID_DATA, str2, null);
                                    e.printStackTrace();
                                }
                            }
                        }, "REQUEST_RESTORE_QUICKPANEL").start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v19, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.io.File decrypt(int r9, java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.QSBackupRestoreManager.decrypt(int, java.lang.String):java.io.File");
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
    /* JADX WARN: Multi-variable type inference failed */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void encrypt(java.io.File r6, java.lang.String r7, int r8) {
        /*
            Method dump skipped, instructions count: 188
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.QSBackupRestoreManager.encrypt(java.io.File, java.lang.String, int):void");
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

    public static void sendResponse(Context context, String str, int i, ERR_CODE err_code, String str2, String str3) {
        StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(i, " action=", str, " resultCode=", " errorCode=");
        m.append(err_code);
        m.append(" requiredSize=0");
        Log.d("QSBackupRestoreManager", m.toString());
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
        Log.d("QSBackupRestoreManager", "sendBroadcast. ");
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

    public final void addCallback(String str, Callback callback) {
        if (this.mQSBnRMap.keySet().contains(str)) {
            return;
        }
        this.mQSBnRMap.put(str, callback);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0176  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int createBackupFile(int r17, java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 437
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.QSBackupRestoreManager.createBackupFile(int, java.lang.String):int");
    }

    public final boolean loadRestoreFile(File file) {
        String name;
        Log.d("QSBackupRestoreManager", " filename=" + file);
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                        newPullParser.setInput(fileInputStream2, "UTF-8");
                        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                            if (eventType == 0) {
                                newPullParser.getName();
                            } else if (eventType == 2) {
                                String name2 = newPullParser.getName();
                                if (this.mQSBnRMap.containsKey(name2)) {
                                    Callback callback = (Callback) this.mQSBnRMap.get(name2);
                                    newPullParser.next();
                                    callback.onRestore(newPullParser.getName() + "::" + newPullParser.nextText());
                                }
                            } else if (eventType == 3 && (name = newPullParser.getName()) != null) {
                                Log.d("QSBackupRestoreManager", "END_TAG : " + name);
                            }
                        }
                        try {
                            fileInputStream2.close();
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return true;
                        }
                    } catch (IOException e2) {
                        e = e2;
                        fileInputStream = fileInputStream2;
                        e.printStackTrace();
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return false;
                    } catch (IllegalArgumentException e3) {
                        e = e3;
                        fileInputStream = fileInputStream2;
                        e.printStackTrace();
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return false;
                    } catch (IllegalStateException e4) {
                        e = e4;
                        fileInputStream = fileInputStream2;
                        e.printStackTrace();
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return false;
                    } catch (XmlPullParserException e5) {
                        e = e5;
                        fileInputStream = fileInputStream2;
                        e.printStackTrace();
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e7) {
                    e = e7;
                } catch (IllegalArgumentException e8) {
                    e = e8;
                } catch (IllegalStateException e9) {
                    e = e9;
                } catch (XmlPullParserException e10) {
                    e = e10;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e11) {
            e11.printStackTrace();
        }
    }

    public final void removeCallback(String str) {
        if (this.mQSBnRMap.keySet().contains(str)) {
            this.mQSBnRMap.remove(str);
        }
    }
}
