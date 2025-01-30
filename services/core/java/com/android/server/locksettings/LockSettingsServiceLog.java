package com.android.server.locksettings;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemProperties;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.text.format.Time;
import android.util.Log;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes2.dex */
public class LockSettingsServiceLog {
    public static final String SECURITY_LOG_VERSION = Build.VERSION.INCREMENTAL;
    public String BUILD_ID;
    public String EVENT_ID;
    public final Lock F_LOCK;
    public String LOG_FILE;
    public final Context mContext;
    public final Object mFileWriteLock = new Object();
    public LogFileManager[] mLogFile;
    public SaveLssLog saveLssLog;

    public LockSettingsServiceLog(Context context) {
        String str = Build.VERSION.INCREMENTAL;
        this.BUILD_ID = str;
        this.EVENT_ID = str;
        this.saveLssLog = null;
        this.LOG_FILE = null;
        this.F_LOCK = new ReentrantLock();
        this.mLogFile = new LogFileManager[5];
        this.mContext = context;
        init();
    }

    public void init() {
        this.mLogFile[0] = new LogFileManager("Enroll", 7);
        this.mLogFile[1] = new LogFileManager("Verify", 7);
        this.mLogFile[2] = new LogFileManager("KeystoreErr", 7);
        this.mLogFile[3] = new LogFileManager("Restore", 7);
        this.mLogFile[4] = new LogFileManager("Debug", 4);
    }

    public void addLog(int i, String str) {
        if (this.mLogFile[i] == null) {
            Log.e("LockSettingsLog", "mLogFile is null. type = " + i);
            return;
        }
        SaveLssLog saveLssLog = new SaveLssLog(i, str);
        this.saveLssLog = saveLssLog;
        saveLssLog.start();
    }

    public void uploadLogFile(final int i) {
        if (this.mLogFile[i] == null) {
            Log.e("LockSettingsLog", "mLogFile is null. type = " + i);
            return;
        }
        new Thread() { // from class: com.android.server.locksettings.LockSettingsServiceLog.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(2000L);
                } catch (Exception e) {
                    Log.e("LockSettingsLog", "sleep error" + e);
                }
                LockSettingsServiceLog.this.mLogFile[i].prepareUpload();
                LockSettingsServiceLog lockSettingsServiceLog = LockSettingsServiceLog.this;
                lockSettingsServiceLog.LOG_FILE = lockSettingsServiceLog.zipLogFile(i);
                LockSettingsServiceLog.this.mLogFile[i].deleteUploadFile();
                LockSettingsServiceLog.this.sendToDiagmon(i);
            }
        }.start();
    }

    public static String gethashStr(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr);
            return Arrays.toString(Arrays.copyOf(messageDigest.digest(), 5));
        } catch (NoSuchAlgorithmException e) {
            Log.d("LockSettingsLog", "gethashStr() failed. " + e);
            return null;
        }
    }

    public static boolean isShipBuild() {
        return "true".equals(SystemProperties.get("ro.product_ship", "false"));
    }

    public static boolean isDevBuild() {
        String str = Build.TYPE;
        return "eng".equals(str) || "userdebug".equals(str);
    }

    public final String getCurTime() {
        Time time = new Time();
        time.setToNow();
        return time.format("%Y%m%d_%H%M%S");
    }

    public final String makeLogTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return String.format("%02d-%02d %02d:%02d:%02d.%03d ", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 8, insn: 0x00e1: MOVE (r3 I:??[OBJECT, ARRAY]) = (r8 I:??[OBJECT, ARRAY]), block:B:101:0x00e1 */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00cd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.util.zip.ZipOutputStream] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7, types: [java.util.zip.ZipOutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String zipLogFile(int i) {
        FileOutputStream fileOutputStream;
        ZipOutputStream zipOutputStream;
        ?? r5;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        String uploadFileName;
        String str = "/data/log/LockSettingsLog_" + getCurTime() + ".zip";
        synchronized (this.mFileWriteLock) {
            byte[] bArr = new byte[IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES];
            FileInputStream fileInputStream3 = null;
            try {
                try {
                    fileOutputStream = new FileOutputStream(str);
                    try {
                        r5 = new ZipOutputStream(fileOutputStream);
                        fileInputStream2 = null;
                        for (int i2 = 0; i2 < 5; i2++) {
                            if (i == i2) {
                                try {
                                    uploadFileName = this.mLogFile[i2].getUploadFileName();
                                } catch (IOException e) {
                                    e = e;
                                    Log.e("LockSettingsLog", "zipLogFile - error" + e);
                                    if (fileInputStream2 != null) {
                                        try {
                                            fileInputStream2.close();
                                        } catch (IOException unused) {
                                        }
                                    }
                                    if (r5 != 0) {
                                        try {
                                            r5.close();
                                        } catch (IOException unused2) {
                                        }
                                    }
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (IOException unused3) {
                                        }
                                    }
                                    str = null;
                                    Log.d("LockSettingsLog", "zipLogFile - finish");
                                    return str;
                                }
                            } else {
                                uploadFileName = this.mLogFile[i2].getLastFileName();
                            }
                            if (uploadFileName != null && new File(uploadFileName).exists()) {
                                r5.putNextEntry(new ZipEntry(this.mLogFile[i2].getErrorCode() + ".log"));
                                FileInputStream fileInputStream4 = new FileInputStream(uploadFileName);
                                while (true) {
                                    try {
                                        int read = fileInputStream4.read(bArr);
                                        if (read <= 0) {
                                            break;
                                        }
                                        r5.write(bArr, 0, read);
                                    } catch (IOException e2) {
                                        e = e2;
                                        fileInputStream2 = fileInputStream4;
                                        Log.e("LockSettingsLog", "zipLogFile - error" + e);
                                        if (fileInputStream2 != null) {
                                        }
                                        if (r5 != 0) {
                                        }
                                        if (fileOutputStream != null) {
                                        }
                                        str = null;
                                        Log.d("LockSettingsLog", "zipLogFile - finish");
                                        return str;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileInputStream3 = fileInputStream4;
                                        zipOutputStream = r5;
                                        if (fileInputStream3 != null) {
                                            try {
                                                fileInputStream3.close();
                                            } catch (IOException unused4) {
                                            }
                                        }
                                        if (zipOutputStream != null) {
                                            try {
                                                zipOutputStream.close();
                                            } catch (IOException unused5) {
                                            }
                                        }
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                                throw th;
                                            } catch (IOException unused6) {
                                                throw th;
                                            }
                                        }
                                        throw th;
                                    }
                                }
                                fileInputStream4.close();
                                r5.closeEntry();
                                fileInputStream2 = fileInputStream4;
                            }
                        }
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (IOException unused7) {
                            }
                        }
                        try {
                            r5.close();
                        } catch (IOException unused8) {
                        }
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused9) {
                        }
                    } catch (IOException e3) {
                        e = e3;
                        r5 = 0;
                        fileInputStream2 = r5;
                        Log.e("LockSettingsLog", "zipLogFile - error" + e);
                        if (fileInputStream2 != null) {
                        }
                        if (r5 != 0) {
                        }
                        if (fileOutputStream != null) {
                        }
                        str = null;
                        Log.d("LockSettingsLog", "zipLogFile - finish");
                        return str;
                    } catch (Throwable th2) {
                        th = th2;
                        zipOutputStream = null;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileInputStream3 = fileInputStream;
                }
            } catch (IOException e4) {
                e = e4;
                fileOutputStream = null;
                r5 = 0;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                zipOutputStream = null;
            }
            Log.d("LockSettingsLog", "zipLogFile - finish");
        }
        return str;
    }

    public final void sendToDiagmon(int i) {
        if (Settings.System.getInt(this.mContext.getContentResolver(), "samsung_errorlog_agree", 0) != 1) {
            Log.i("LockSettingsLog", "sendToDiagmon failed. errorlog_agree is not true!!");
            return;
        }
        if (this.LOG_FILE == null) {
            Log.i("LockSettingsLog", "sendToDiagmon failed. filename is null!!");
            return;
        }
        if (!this.mLogFile[i].hasLogType(1)) {
            Log.i("LockSettingsLog", "sendToDiagmon failed. Cannot upload this log : " + this.mLogFile[i].getErrorCode());
            return;
        }
        if (!isShipBuild() && !isDevBuild()) {
            Log.i("LockSettingsLog", "sendToDiagmon failed. Can upload only ship or dev!");
            return;
        }
        Log.i("LockSettingsLog", "send broadcast intent to diagmon : " + this.LOG_FILE);
        Intent intent = new Intent("com.sec.android.diagmonagent.intent.REPORT_ERROR_V2");
        Bundle bundle = new Bundle();
        intent.addFlags(32);
        try {
            bundle.putBundle("DiagMon", new Bundle());
            bundle.getBundle("DiagMon").putBundle("CFailLogUpload", new Bundle());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putString("ServiceID", "sp4xkeu9ef");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putBundle("Ext", new Bundle());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("ClientV", this.BUILD_ID);
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("UiMode", "0");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("ResultCode", this.mLogFile[i].getErrorCode());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("WifiOnlyFeature", "1");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("EventID", this.EVENT_ID);
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("Description", this.mLogFile[i].getErrorCode());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putBundle("IntentOnly", new Bundle());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("IntentOnly").putString("IntentOnlyMode", "1");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("IntentOnly").putString("Agree", "D");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("IntentOnly").putString("LogPath", this.LOG_FILE);
            intent.putExtra("uploadMO", bundle);
            intent.setFlags(32);
            intent.setPackage("com.sec.android.diagmonagent");
            this.mContext.sendBroadcast(intent);
        } catch (Exception e) {
            Log.e("LockSettingsLog", "Exception while sending a bug report.", e);
        }
        this.LOG_FILE = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x008a, code lost:
    
        if (r4 != null) goto L46;
     */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dump(PrintWriter printWriter) {
        Log.i("LockSettingsLog", "dump start");
        for (int i = 0; i < 5; i++) {
            if (this.mLogFile[i].hasLogType(2)) {
                String errorCode = this.mLogFile[i].getErrorCode();
                String str = "\n----------------- Start " + errorCode + " state -----------------";
                printWriter.println(str);
                String lastFileName = this.mLogFile[i].getLastFileName();
                if (lastFileName == null) {
                    printWriter.println("<No " + errorCode + " file>");
                } else {
                    this.F_LOCK.lock();
                    try {
                        try {
                            RandomAccessFile randomAccessFile = new RandomAccessFile(lastFileName, "r");
                            int i2 = 0;
                            while (true) {
                                try {
                                    str = randomAccessFile.readLine();
                                    if (str == null) {
                                        break;
                                    }
                                    printWriter.println(str);
                                    i2++;
                                    if (i2 > 2000) {
                                        str = "<MAX Line reached>";
                                        break;
                                    }
                                    str = null;
                                } catch (Throwable th) {
                                    String str2 = str;
                                    try {
                                        randomAccessFile.close();
                                    } catch (Throwable th2) {
                                        try {
                                            th.addSuppressed(th2);
                                        } catch (FileNotFoundException unused) {
                                            str = "<File not found>";
                                            printWriter.println(str);
                                            Log.i("LockSettingsLog", str);
                                            this.F_LOCK.unlock();
                                            printWriter.println("----------------- End " + errorCode + " state -----------------");
                                        } catch (IOException unused2) {
                                            str = "<IO Error>";
                                            printWriter.println(str);
                                            Log.i("LockSettingsLog", str);
                                            this.F_LOCK.unlock();
                                            printWriter.println("----------------- End " + errorCode + " state -----------------");
                                        } catch (Exception e) {
                                            e = e;
                                            e.printStackTrace();
                                            str = "<Unknown Error>";
                                            printWriter.println(str);
                                            Log.i("LockSettingsLog", str);
                                            this.F_LOCK.unlock();
                                            printWriter.println("----------------- End " + errorCode + " state -----------------");
                                        } catch (Throwable th3) {
                                            th = th3;
                                            str = str2;
                                            if (str != null) {
                                                printWriter.println(str);
                                                Log.i("LockSettingsLog", str);
                                            }
                                            this.F_LOCK.unlock();
                                            throw th;
                                        }
                                    }
                                    throw th;
                                }
                            }
                            randomAccessFile.close();
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    } catch (FileNotFoundException unused3) {
                    } catch (IOException unused4) {
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
            }
        }
        Log.i("LockSettingsLog", "dump end");
    }

    public void writeLog() {
        new Thread() { // from class: com.android.server.locksettings.LockSettingsServiceLog.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (Exception e) {
                    Log.e("LockSettingsLog", "sleep error" + e);
                }
                LockSettingsServiceLog.this.showDump();
            }
        }.start();
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0080, code lost:
    
        if (r4 != null) goto L43;
     */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showDump() {
        Log.e("LockSettingsLog", "!@LSS log start");
        String str = null;
        for (int i = 0; i < 5; i++) {
            if (this.mLogFile[i].hasLogType(4)) {
                String errorCode = this.mLogFile[i].getErrorCode();
                Log.e("LockSettingsLog", "!@----------------- Start " + errorCode + " state -----------------");
                String lastFileName = this.mLogFile[i].getLastFileName();
                this.F_LOCK.lock();
                try {
                    try {
                        RandomAccessFile randomAccessFile = new RandomAccessFile(lastFileName, "r");
                        int i2 = 0;
                        while (true) {
                            try {
                                str = randomAccessFile.readLine();
                                if (str == null) {
                                    break;
                                }
                                Log.e("LockSettingsLog", "!@" + str);
                                i2++;
                                if (i2 > 2000) {
                                    str = "!@<MAX Line reached>";
                                    break;
                                }
                                str = null;
                            } catch (Throwable th) {
                                String str2 = str;
                                try {
                                    randomAccessFile.close();
                                } catch (Throwable th2) {
                                    try {
                                        th.addSuppressed(th2);
                                    } catch (FileNotFoundException unused) {
                                        str = "!@<File not found>";
                                        Log.e("LockSettingsLog", str);
                                        this.F_LOCK.unlock();
                                        Log.e("LockSettingsLog", "!@----------------- End " + errorCode + " state -----------------");
                                    } catch (IOException unused2) {
                                        str = "!@<IO Error>";
                                        Log.e("LockSettingsLog", str);
                                        this.F_LOCK.unlock();
                                        Log.e("LockSettingsLog", "!@----------------- End " + errorCode + " state -----------------");
                                    } catch (Exception e) {
                                        e = e;
                                        e.printStackTrace();
                                        str = "!@<Unknown Error>";
                                        Log.e("LockSettingsLog", str);
                                        this.F_LOCK.unlock();
                                        Log.e("LockSettingsLog", "!@----------------- End " + errorCode + " state -----------------");
                                    } catch (Throwable th3) {
                                        th = th3;
                                        str = str2;
                                        if (str != null) {
                                            Log.e("LockSettingsLog", str);
                                        }
                                        this.F_LOCK.unlock();
                                        throw th;
                                    }
                                }
                                throw th;
                            }
                        }
                        randomAccessFile.close();
                    } catch (Throwable th4) {
                        th = th4;
                    }
                } catch (FileNotFoundException unused3) {
                } catch (IOException unused4) {
                } catch (Exception e2) {
                    e = e2;
                }
            }
        }
        Log.e("LockSettingsLog", "!@LSS log end");
    }

    public void migrateLssLog() {
        try {
            final String errorCode = this.mLogFile[4].getErrorCode();
            File file = new File("/data/log/");
            if (!file.exists()) {
                Log.e("LockSettingsLog", "No log folder");
                return;
            }
            File[] listFiles = file.listFiles(new FilenameFilter() { // from class: com.android.server.locksettings.LockSettingsServiceLog.3
                @Override // java.io.FilenameFilter
                public boolean accept(File file2, String str) {
                    if (str.startsWith("LockSettingsLog")) {
                        return str.endsWith(".zip") || str.contains(errorCode);
                    }
                    return false;
                }
            });
            if (listFiles == null) {
                Log.e("LockSettingsLog", "No log files");
                return;
            }
            for (File file2 : listFiles) {
                if (file2.exists()) {
                    file2.delete();
                    Log.i("LockSettingsLog", "file : " + file2 + " deleted!");
                }
            }
        } catch (Exception e) {
            Log.e("LockSettingsLog", "delete file error = " + e);
        }
    }

    public class SaveLssLog extends Thread {
        public String mContents;
        public boolean mIsSaveLssLogDone = false;
        public int mType;

        public SaveLssLog(int i, String str) {
            this.mType = i;
            this.mContents = str;
            if (LockSettingsServiceLog.this.mLogFile[i].hasLogType(2)) {
                Log.i("LockSettingsLog", str);
            }
        }

        public final void writeLockSettingsLog() {
            try {
                if (!LockSettingsServiceLog.this.F_LOCK.tryLock() && !LockSettingsServiceLog.this.F_LOCK.tryLock(1L, TimeUnit.SECONDS)) {
                    Log.d("LockSettingsLog", "maybe dump is in progress!! Cannot written log");
                    return;
                }
                synchronized (LockSettingsServiceLog.this.mFileWriteLock) {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(LockSettingsServiceLog.this.mLogFile[this.mType].getLastFileName(), true));
                        bufferedWriter.append((CharSequence) LockSettingsServiceLog.this.makeLogTime());
                        bufferedWriter.append((CharSequence) (this.mContents + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE));
                        bufferedWriter.close();
                    } catch (IOException e) {
                        Log.e("LockSettingsLog", "makefile error" + e);
                    }
                }
                LockSettingsServiceLog.this.F_LOCK.unlock();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            writeLockSettingsLog();
            this.mIsSaveLssLogDone = true;
        }
    }

    public class LogFileManager {
        public String mErrorCode;
        public int mLogType;
        public String mLastFileName = null;
        public String mUploadFileName = null;

        public LogFileManager(String str, int i) {
            this.mErrorCode = str;
            this.mLogType = i;
        }

        public String getErrorCode() {
            return this.mErrorCode;
        }

        public boolean hasLogType(int i) {
            return (this.mLogType & i) != 0;
        }

        public String getLastFileName() {
            if (this.mLastFileName == null) {
                this.mLastFileName = "/data/log/LockSettingsLog_" + this.mErrorCode + ".log";
            }
            return this.mLastFileName;
        }

        public String getUploadFileName() {
            return this.mUploadFileName;
        }

        public void prepareUpload() {
            String str = this.mLastFileName;
            if (str != null) {
                this.mUploadFileName = str;
                this.mLastFileName = null;
            }
        }

        public void deleteUploadFile() {
            if (this.mUploadFileName == null) {
                return;
            }
            try {
                File file = new File(this.mUploadFileName);
                if (file.exists() && file.length() > 102400) {
                    Log.i("LockSettingsLog", "delete uploaded Filename = " + this.mUploadFileName + ", FileSize = " + file.length() + " byte");
                    file.delete();
                }
            } catch (Exception e) {
                Log.e("LockSettingsLog", "delete uploaded Filename = " + this.mUploadFileName + ", error = " + e);
            }
            this.mUploadFileName = null;
        }
    }
}
