package com.android.systemui.p016qs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Xml;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.Dependency;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSBackupRestoreManager {
    public static Cipher mCipher;
    public static byte[] mSalt;
    public static SecretKeySpec mSecretKey;
    public static String mSecurityPassword;
    public final LinkedHashMap mQSBnRMap = new LinkedHashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        boolean isValidDB();

        String onBackup(boolean z);

        void onRestore(String str);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class QSBnRReceiver extends BroadcastReceiver {
        public Thread mBackupThread;

        @Override // android.content.BroadcastReceiver
        public final void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            AbstractC0689x6838b71d.m68m("onReceive ( action = ", action, ")", "QSBackupRestoreManager");
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
                                QSBackupRestoreManager qSBackupRestoreManager = (QSBackupRestoreManager) Dependency.get(QSBackupRestoreManager.class);
                                ERR_CODE err_code = ERR_CODE.UNKNOWN_ERROR;
                                qSBackupRestoreManager.getClass();
                                QSBackupRestoreManager.sendResponse(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_QUICKPANEL2", 1, err_code, stringExtra2, stringExtra4);
                            }
                        } else {
                            Thread thread2 = new Thread(new Runnable(this) { // from class: com.android.systemui.qs.QSBackupRestoreManager.QSBnRReceiver.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    QSBackupRestoreManager qSBackupRestoreManager2 = (QSBackupRestoreManager) Dependency.get(QSBackupRestoreManager.class);
                                    Context context2 = context;
                                    String str = stringExtra;
                                    String str2 = stringExtra2;
                                    int i = intExtra2;
                                    String str3 = stringExtra3;
                                    qSBackupRestoreManager2.getClass();
                                    Log.d("QSBackupRestoreManager", "start backup basePath=" + str + " source=" + str2);
                                    ERR_CODE err_code2 = ERR_CODE.SUCCESS;
                                    String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "/");
                                    try {
                                        QSBackupRestoreManager.streamCrypt(str3);
                                        int createBackupFile = qSBackupRestoreManager2.createBackupFile(i, m14m);
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
                                ERR_CODE err_code2;
                                QSBackupRestoreManager qSBackupRestoreManager2 = (QSBackupRestoreManager) Dependency.get(QSBackupRestoreManager.class);
                                Context context2 = context;
                                String str = stringExtra;
                                String str2 = stringExtra2;
                                int i2 = intExtra2;
                                String str3 = stringExtra3;
                                qSBackupRestoreManager2.getClass();
                                Log.d("QSBackupRestoreManager", "start restore basePath=" + str + " source=" + str2);
                                ERR_CODE err_code3 = ERR_CODE.SUCCESS;
                                String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "/");
                                try {
                                    QSBackupRestoreManager.streamCrypt(str3);
                                    if (qSBackupRestoreManager2.loadRestoreFile(QSBackupRestoreManager.decrypt(i2, m14m))) {
                                        i = 0;
                                        err_code2 = err_code3;
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
        File file2 = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "/decrypt_quickpanel.xml"));
        String str3 = "/encrypt_quickpanel.xml";
        File file3 = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "/encrypt_quickpanel.xml"));
        InputStream inputStream2 = null;
        try {
            try {
                if (!file3.exists()) {
                    Log.e("QSBackupRestoreManager", "decrypt: file is not found.encrypt_quickpanel.xml");
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
                            Log.d("QSBackupRestoreManager", e2.toString());
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                        } catch (Exception e6) {
                            e = e6;
                            Log.d("QSBackupRestoreManager", e.toString());
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
                        Log.d("QSBackupRestoreManager", e2.toString());
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (outputStream3 != null) {
                            outputStream3.close();
                        }
                    } catch (Exception e4) {
                        e = e4;
                        file = 0;
                        Log.d("QSBackupRestoreManager", e.toString());
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
                        Log.d("QSBackupRestoreManager", e2.toString());
                        if (fileInputStream != null) {
                        }
                        if (outputStream3 != null) {
                        }
                    } catch (Exception e6) {
                        e = e6;
                        Log.d("QSBackupRestoreManager", e.toString());
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

    public static void sendResponse(Context context, String str, int i, ERR_CODE err_code, String str2, String str3) {
        StringBuilder m92m = AbstractC0950x8906c950.m92m(" action=", str, " resultCode=", i, " errorCode=");
        m92m.append(err_code);
        m92m.append(" requiredSize=0");
        Log.d("QSBackupRestoreManager", m92m.toString());
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
        LinkedHashMap linkedHashMap = this.mQSBnRMap;
        if (linkedHashMap.keySet().contains(str)) {
            return;
        }
        linkedHashMap.put(str, callback);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(22:0|1|(3:2|3|(1:5))|7|(1:9)|10|(1:12)|(2:13|14)|15|(3:16|17|18)|(6:20|21|(4:24|(4:27|(4:29|(1:31)|32|33)(1:35)|34|25)|36|22)|37|38|39)|40|41|(1:43)(1:56)|44|45|46|47|(1:49)|50|51|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0195, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0196, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:43:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0174  */
    /* JADX WARN: Type inference failed for: r5v6, types: [org.xmlpull.v1.XmlSerializer] */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int createBackupFile(int i, String str) {
        Throwable th;
        FileWriter fileWriter;
        int i2;
        Log.d("QSBackupRestoreManager", "create backup file basePath=" + str);
        try {
            File file = new File(str + "quickpanel.xml");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("QSBackupRestoreManager", "basePath=" + str);
        File file2 = new File(str);
        if (!file2.exists()) {
            Log.i("QSBackupRestoreManager", file2.mkdir() + "folder created last");
        }
        File file3 = new File(file2.getPath() + "/quickpanel.xml");
        if (file3.exists()) {
            file3.delete();
        }
        try {
            file3.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        Log.i("QSBackupRestoreManager", "filePath=" + file3.getPath());
        Log.i("QSBackupRestoreManager", "generateResultXML file = " + file3);
        ?? newSerializer = Xml.newSerializer();
        int i3 = 0;
        ?? r9 = 0;
        FileWriter fileWriter2 = null;
        try {
            try {
                try {
                    fileWriter = new FileWriter(file3);
                } catch (Throwable th2) {
                    th = th2;
                    fileWriter = r9;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        try {
            newSerializer.setOutput(fileWriter);
            newSerializer.startDocument("UTF-8", Boolean.TRUE);
            newSerializer.startTag("", "quickpanel");
            for (Map.Entry entry : this.mQSBnRMap.entrySet()) {
                String str2 = (String) entry.getKey();
                Callback callback = (Callback) entry.getValue();
                String[] split = callback.onBackup(callback.isValidDB()).split("::");
                int i4 = 0;
                while (i4 < split.length) {
                    if (split[i4].equals("TAG")) {
                        newSerializer.startTag("", str2);
                        int i5 = i4 + 1;
                        newSerializer.startTag("", split[i5]);
                        i4 += 2;
                        String str3 = split[i4];
                        if (str3 == null) {
                            str3 = "null";
                        }
                        newSerializer.text(str3);
                        newSerializer.endTag("", split[i5]);
                        newSerializer.endTag("", str2);
                    }
                    i4++;
                }
            }
            newSerializer.endTag("", "quickpanel");
            newSerializer.endDocument();
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e5) {
            e = e5;
            fileWriter2 = fileWriter;
            e.printStackTrace();
            if (fileWriter2 != null) {
                fileWriter2.flush();
                fileWriter2.close();
            }
            r9 = 0;
            if (file3.length() > 0) {
            }
            encrypt(file3, str + "/encrypt_quickpanel.xml", i);
            i3 = i2;
            if (file3.exists()) {
            }
            return i3 ^ 1;
        } catch (Throwable th3) {
            th = th3;
            if (fileWriter == null) {
                throw th;
            }
            try {
                fileWriter.flush();
                fileWriter.close();
                throw th;
            } catch (IOException e6) {
                e6.printStackTrace();
                throw th;
            }
        }
        r9 = 0;
        if (file3.length() > 0) {
            Log.e("QSBackupRestoreManager", "Backup file size error");
            i2 = 0;
        } else {
            i2 = 1;
        }
        encrypt(file3, str + "/encrypt_quickpanel.xml", i);
        i3 = i2;
        if (file3.exists()) {
            file3.delete();
        }
        return i3 ^ 1;
    }

    public final boolean loadRestoreFile(File file) {
        FileInputStream fileInputStream;
        String name;
        Log.d("QSBackupRestoreManager", " filename=" + file);
        FileInputStream fileInputStream2 = null;
        try {
            try {
                try {
                    fileInputStream = new FileInputStream(file);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (IOException e) {
                e = e;
            } catch (IllegalArgumentException e2) {
                e = e2;
            } catch (IllegalStateException e3) {
                e = e3;
            } catch (XmlPullParserException e4) {
                e = e4;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
        }
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(fileInputStream, "UTF-8");
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                if (eventType == 0) {
                    newPullParser.getName();
                } else if (eventType == 2) {
                    String name2 = newPullParser.getName();
                    LinkedHashMap linkedHashMap = this.mQSBnRMap;
                    if (linkedHashMap.containsKey(name2)) {
                        Callback callback = (Callback) linkedHashMap.get(name2);
                        newPullParser.next();
                        callback.onRestore(newPullParser.getName() + "::" + newPullParser.nextText());
                    }
                } else if (eventType == 3 && (name = newPullParser.getName()) != null) {
                    Log.d("QSBackupRestoreManager", "END_TAG : " + name);
                }
            }
            try {
                fileInputStream.close();
                return true;
            } catch (IOException e6) {
                e6.printStackTrace();
                return true;
            }
        } catch (IOException e7) {
            e = e7;
            fileInputStream2 = fileInputStream;
            e.printStackTrace();
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            return false;
        } catch (IllegalArgumentException e8) {
            e = e8;
            fileInputStream2 = fileInputStream;
            e.printStackTrace();
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            return false;
        } catch (IllegalStateException e9) {
            e = e9;
            fileInputStream2 = fileInputStream;
            e.printStackTrace();
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            return false;
        } catch (XmlPullParserException e10) {
            e = e10;
            fileInputStream2 = fileInputStream;
            e.printStackTrace();
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e11) {
                    e11.printStackTrace();
                }
            }
            throw th;
        }
    }
}
