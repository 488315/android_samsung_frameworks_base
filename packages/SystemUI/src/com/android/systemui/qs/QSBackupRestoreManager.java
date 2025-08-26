package com.android.systemui.qs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Xml;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes2.dex */
public class QSBackupRestoreManager {
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
                    if (!action.equals("com.samsung.android.intent.action.REQUEST_BACKUP_QUICKPANEL2")) {
                        if (action.equals("com.samsung.android.intent.action.REQUEST_RESTORE_QUICKPANEL2")) {
                            new Thread(new Runnable(this) { // from class: com.android.systemui.qs.QSBackupRestoreManager.QSBnRReceiver.2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i;
                                    QSBackupRestoreManager qSBackupRestoreManager = (QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class);
                                    Context context2 = context;
                                    String str = stringExtra;
                                    String str2 = stringExtra2;
                                    int i2 = intExtra2;
                                    String str3 = stringExtra3;
                                    qSBackupRestoreManager.getClass();
                                    Log.d("QSBackupRestoreManager", "start restore basePath=" + str + " source=" + str2);
                                    ERR_CODE err_code = ERR_CODE.SUCCESS;
                                    String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "/");
                                    try {
                                        QSBackupRestoreManager.streamCrypt(str3);
                                        if (qSBackupRestoreManager.loadRestoreFile(QSBackupRestoreManager.decrypt(i2, strM))) {
                                            i = 0;
                                        } else {
                                            err_code = ERR_CODE.INVALID_DATA;
                                            i = 1;
                                        }
                                        QSBackupRestoreManager.sendResponse(context2, "com.samsung.android.intent.action.RESPONSE_RESTORE_QUICKPANEL2", i, err_code, str2, null);
                                    } catch (Exception e) {
                                        QSBackupRestoreManager.sendResponse(context2, "com.samsung.android.intent.action.RESPONSE_RESTORE_QUICKPANEL2", 1, ERR_CODE.INVALID_DATA, str2, null);
                                        e.printStackTrace();
                                    }
                                }
                            }, "REQUEST_RESTORE_QUICKPANEL").start();
                            return;
                        }
                        return;
                    }
                    if (intExtra != 2) {
                        Thread thread = new Thread(new Runnable(this) { // from class: com.android.systemui.qs.QSBackupRestoreManager.QSBnRReceiver.1
                            @Override // java.lang.Runnable
                            public final void run() throws Throwable {
                                QSBackupRestoreManager qSBackupRestoreManager = (QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class);
                                Context context2 = context;
                                String str = stringExtra;
                                String str2 = stringExtra2;
                                int i = intExtra2;
                                String str3 = stringExtra3;
                                qSBackupRestoreManager.getClass();
                                Log.d("QSBackupRestoreManager", "start backup basePath=" + str + " source=" + str2);
                                ERR_CODE err_code = ERR_CODE.SUCCESS;
                                String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "/");
                                try {
                                    QSBackupRestoreManager.streamCrypt(str3);
                                    int iCreateBackupFile = qSBackupRestoreManager.createBackupFile(i, strM);
                                    Log.d("QSBackupRestoreManager", "resultCode=" + iCreateBackupFile);
                                    if (iCreateBackupFile == 1) {
                                        err_code = ERR_CODE.INVALID_DATA;
                                    }
                                    QSBackupRestoreManager.sendResponse(context2, "com.samsung.android.intent.action.RESPONSE_BACKUP_QUICKPANEL2", iCreateBackupFile, err_code, str2, "");
                                } catch (Exception e) {
                                    QSBackupRestoreManager.sendResponse(context2, "com.samsung.android.intent.action.RESPONSE_BACKUP_QUICKPANEL2", 1, ERR_CODE.INVALID_DATA, str2, "");
                                    e.printStackTrace();
                                }
                            }
                        }, "REQUEST_BACKUP_QUICKPANEL");
                        this.mBackupThread = thread;
                        thread.start();
                        return;
                    }
                    Thread thread2 = this.mBackupThread;
                    if (thread2 == null || !thread2.isAlive()) {
                        return;
                    }
                    Log.d("QSBackupRestoreManager", "stop backup working thread for quickpanel");
                    this.mBackupThread.interrupt();
                    this.mBackupThread = null;
                    QSBackupRestoreManager qSBackupRestoreManager = (QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class);
                    ERR_CODE err_code = ERR_CODE.UNKNOWN_ERROR;
                    qSBackupRestoreManager.getClass();
                    QSBackupRestoreManager.sendResponse(context, "com.samsung.android.intent.action.RESPONSE_BACKUP_QUICKPANEL2", 1, err_code, stringExtra2, stringExtra4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
        File file = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "/decrypt_quickpanel.xml"));
        ?? fileInputStream = "/encrypt_quickpanel.xml";
        ?? file2 = new File(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "/encrypt_quickpanel.xml"));
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
            Log.e("QSBackupRestoreManager", "decrypt: file is not found.encrypt_quickpanel.xml");
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
                        Log.d("QSBackupRestoreManager", e.toString());
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
                        Log.d("QSBackupRestoreManager", e.toString());
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
                            Log.d("QSBackupRestoreManager", e.toString());
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
                            Log.d("QSBackupRestoreManager", e.toString());
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

    public static void sendResponse(Context context, String str, int i, ERR_CODE err_code, String str2, String str3) {
        StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(i, " action=", str, " resultCode=", " errorCode=");
        sbM890m.append(err_code);
        sbM890m.append(" requiredSize=0");
        Log.d("QSBackupRestoreManager", sbM890m.toString());
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

    public static void streamCrypt(String str) throws NoSuchAlgorithmException {
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

    /* JADX WARN: Can't wrap try/catch for region: R(22:0|2|(3:80|3|(1:5))|9|(1:11)|12|(1:14)|(2:73|15)|19|(3:78|68|20)|(6:82|21|(4:24|(4:27|(4:29|(1:31)|36|86)(1:87)|37|25)|85|22)|84|38|39)|77|49|(1:51)(1:52)|70|53|54|57|(1:59)|60|61|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0196, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0197, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:51:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int createBackupFile(int i, String str) throws Throwable {
        int i2;
        Throwable th;
        FileWriter fileWriter;
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
        XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
        int i3 = 0;
        FileWriter fileWriter2 = null;
        try {
            try {
                try {
                    fileWriter = new FileWriter(file3);
                } catch (IOException e3) {
                    e = e3;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        try {
            xmlSerializerNewSerializer.setOutput(fileWriter);
            xmlSerializerNewSerializer.startDocument("UTF-8", Boolean.TRUE);
            xmlSerializerNewSerializer.startTag("", "quickpanel");
            for (Map.Entry entry : this.mQSBnRMap.entrySet()) {
                String str2 = (String) entry.getKey();
                Callback callback = (Callback) entry.getValue();
                String[] strArrSplit = callback.onBackup(callback.isValidDB()).split("::");
                int i4 = 0;
                while (i4 < strArrSplit.length) {
                    if (strArrSplit[i4].equals("TAG")) {
                        xmlSerializerNewSerializer.startTag("", str2);
                        int i5 = i4 + 1;
                        xmlSerializerNewSerializer.startTag("", strArrSplit[i5]);
                        i4 += 2;
                        String str3 = strArrSplit[i4];
                        if (str3 == null) {
                            str3 = "null";
                        }
                        xmlSerializerNewSerializer.text(str3);
                        xmlSerializerNewSerializer.endTag("", strArrSplit[i5]);
                        xmlSerializerNewSerializer.endTag("", str2);
                    }
                    i4++;
                }
            }
            xmlSerializerNewSerializer.endTag("", "quickpanel");
            xmlSerializerNewSerializer.endDocument();
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
            if (file3.length() > 0) {
            }
            encrypt(file3, str + "/encrypt_quickpanel.xml", i);
            i3 = i2;
            if (file3.exists()) {
            }
            return i3 ^ 1;
        } catch (Throwable th3) {
            th = th3;
            fileWriter2 = fileWriter;
            if (fileWriter2 == null) {
                throw th;
            }
            try {
                fileWriter2.flush();
                fileWriter2.close();
                throw th;
            } catch (IOException e6) {
                e6.printStackTrace();
                throw th;
            }
        }
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

    public final boolean loadRestoreFile(File file) throws Throwable {
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
            XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlPullParserNewPullParser.setInput(fileInputStream, "UTF-8");
            for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
                if (eventType == 0) {
                    xmlPullParserNewPullParser.getName();
                } else if (eventType == 2) {
                    String name2 = xmlPullParserNewPullParser.getName();
                    if (this.mQSBnRMap.containsKey(name2)) {
                        Callback callback = (Callback) this.mQSBnRMap.get(name2);
                        xmlPullParserNewPullParser.next();
                        callback.onRestore(xmlPullParserNewPullParser.getName() + "::" + xmlPullParserNewPullParser.nextText());
                    }
                } else if (eventType == 3 && (name = xmlPullParserNewPullParser.getName()) != null) {
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

    public final void removeCallback(String str) {
        if (this.mQSBnRMap.keySet().contains(str)) {
            this.mQSBnRMap.remove(str);
        }
    }
}
