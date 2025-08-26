package com.samsung.android.lock;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.FileUtils;
import android.system.ErrnoException;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes6.dex */
public final class LsLogFile {
    private static final long BODY_OFFSET = 17;
    private static final byte EOL = 10;
    private static final int EOL_SIZE = 1;
    private static final long HEADER_LENGTH = 17;
    private static final long HEADER_OFFSET = 0;
    private static final String LOG_DIR = "/data/log/";
    private static final String LOG_EXT = ".log";
    private static final String LOG_PREFIX = "LockSettingsLog";
    private static final int LONG_SIZE = 8;
    private static final String LSS_DIR = "locksettings/";
    private static final long MAX_DEFALUT_LOG_SIZE = 102400;
    private static final long MAX_DUMP_LINE = 3000;
    private static final String TAG = "LsLogFile";
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static final Lock F_LOCK = new ReentrantLock();
    private static String mLogPath = null;

    public static void prepare() throws IOException, ErrnoException {
        long jNanoTime = System.nanoTime();
        prepareFiles(prepareDir());
        Log.e(TAG, "prepare lsslog file : " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime) + " ms");
    }

    private static String prepareDir() throws ErrnoException {
        mLogPath = "/data/log/locksettings/";
        File file = new File(mLogPath);
        if (!file.exists()) {
            if (file.mkdir()) {
                setPermission(file.getPath());
            } else {
                Log.e(TAG, "Failed to prepare dir : " + mLogPath);
                mLogPath = LOG_DIR;
            }
        }
        return mLogPath;
    }

    private static void prepareFiles(String str) throws IOException, ErrnoException {
        for (int i = 0; i < LsLogType.LIST.length; i++) {
            File file = new File(str, getFileName(LsLogType.LIST[i]));
            if (!file.exists() && !migrateOldLogs(LsLogType.LIST[i]) && fileLock()) {
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file.getPath(), "rws");
                    try {
                        checkAndReset(randomAccessFile, LsLogType.LIST[i]);
                        randomAccessFile.close();
                    } catch (Throwable th) {
                        try {
                            randomAccessFile.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Failed to create logs : " + file.getPath());
                    e.printStackTrace();
                }
                setPermission(file.getPath());
                fileUnlock();
            }
        }
    }

    private static boolean fileLock() {
        try {
            Lock lock = F_LOCK;
            if (lock.tryLock() || lock.tryLock(1L, TimeUnit.SECONDS)) {
                return true;
            }
            Log.e(TAG, "Try lock failed.");
            return false;
        } catch (InterruptedException e) {
            Log.e(TAG, "Try lock failed. exception!");
            e.printStackTrace();
            return false;
        }
    }

    private static boolean fileUnlock() {
        F_LOCK.unlock();
        return true;
    }

    public static boolean saveFile(LsLogType lsLogType, Queue<String> queue) throws IOException {
        String filePath = getFilePath(lsLogType);
        Log.d(TAG, "Saving file:" + lsLogType + "... [Queue : " + queue.size() + NavigationBarInflaterView.SIZE_MOD_END);
        if (!fileLock()) {
            return false;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rws");
            try {
                checkAndReset(randomAccessFile, lsLogType);
                randomAccessFile.seek(0L);
                long filePointer = randomAccessFile.readLong();
                randomAccessFile.seek(filePointer);
                while (!queue.isEmpty()) {
                    String strPoll = queue.poll();
                    if (strPoll != null) {
                        byte[] bytes = strPoll.getBytes(StandardCharsets.UTF_8);
                        if (filePointer + bytes.length + 1 > getMaxSize(lsLogType)) {
                            randomAccessFile.seek(17L);
                        }
                        randomAccessFile.write(bytes);
                        randomAccessFile.write(10);
                        filePointer = randomAccessFile.getFilePointer();
                    }
                }
                randomAccessFile.seek(0L);
                randomAccessFile.writeLong(filePointer);
                Log.d(TAG, String.format(Locale.US, "Saving success! [FP : %d, FS : %d]", Long.valueOf(filePointer), Long.valueOf(randomAccessFile.length())));
                randomAccessFile.close();
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to save logs : " + e.toString());
            e.printStackTrace();
        }
        fileUnlock();
        return true;
    }

    private static void checkAndReset(RandomAccessFile randomAccessFile, LsLogType lsLogType) throws IOException {
        byte[] bArr;
        try {
            check(randomAccessFile, lsLogType);
            bArr = null;
        } catch (SecurityException e) {
            byte[] bytes = LsUtil.makeLog(e.getMessage()).getBytes(StandardCharsets.UTF_8);
            Log.d(TAG, "Reset reason : " + e.getMessage());
            bArr = bytes;
        }
        if (bArr != null) {
            if (lsLogType.containsProperty(512)) {
                randomAccessFile.seek(0L);
                randomAccessFile.writeLong(17L);
                randomAccessFile.writeLong(1L);
                randomAccessFile.write(10);
                return;
            }
            if (lsLogType.containsProperty(256)) {
                randomAccessFile.seek(0L);
                randomAccessFile.writeLong(bArr.length + 18);
                randomAccessFile.writeLong(1L);
                randomAccessFile.write(10);
                randomAccessFile.write(bArr);
                randomAccessFile.write(10);
            }
        }
    }

    private static void check(RandomAccessFile randomAccessFile, LsLogType lsLogType) throws IOException, SecurityException {
        try {
            randomAccessFile.seek(0L);
            long length = randomAccessFile.length();
            if (length == 0) {
                throw new SecurityException("Start Logging");
            }
            if (length <= 17) {
                throw new SecurityException("Broken file header");
            }
            if (randomAccessFile.readLong() > getMaxSize(lsLogType)) {
                throw new SecurityException("File size exceeded");
            }
            if (randomAccessFile.readLong() < 1) {
                throw new SecurityException("Old version");
            }
            Log.d(TAG, "File Check : Passed!");
        } catch (IOException e) {
            throw new SecurityException("Unexpected error", e);
        }
    }

    public static void reset(LsLogType lsLogType) throws IOException {
        Log.d(TAG, "reset file:" + lsLogType);
        if (!fileLock()) {
            Log.w(TAG, "reset() filelock failed");
            return;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(getFilePath(lsLogType), "rws");
            try {
                randomAccessFile.seek(0L);
                randomAccessFile.writeLong(17L);
                randomAccessFile.writeLong(1L);
                randomAccessFile.write(10);
                randomAccessFile.close();
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to save logs : " + e.toString());
            e.printStackTrace();
        }
        fileUnlock();
    }

    public static void setPermission(String str) throws ErrnoException {
        Log.d(TAG, "Set permission : " + FileUtils.setPermissions(str, 511, 1000, 1007));
    }

    public static String getFilePath(LsLogType lsLogType) {
        return getLogPath() + getFileName(lsLogType);
    }

    public static String getLogPath() {
        String str = mLogPath;
        return str != null ? str : prepareDir();
    }

    public static String getFileName(LsLogType lsLogType) {
        return "LockSettingsLog_" + lsLogType.getErrorCode() + LOG_EXT;
    }

    private static long getMaxSize(LsLogType lsLogType) {
        long maxSize = lsLogType.getMaxSize();
        return maxSize > 0 ? maxSize : MAX_DEFALUT_LOG_SIZE;
    }

    public static void dump(PrintWriter printWriter) {
        if (DEBUG) {
            Log.i(TAG, "dump start");
        }
        for (int i = 0; i < LsLogType.LIST.length; i++) {
            if (LsLogType.LIST[i].containsProperty(1)) {
                dumpStringLog(printWriter, LsLogType.LIST[i]);
            }
        }
        if (DEBUG) {
            Log.i(TAG, "dump end");
        }
    }

    private static void dumpStringLog(PrintWriter printWriter, LsLogType lsLogType) {
        String str;
        String str2;
        String filePath = getFilePath(lsLogType);
        String errorCode = lsLogType.getErrorCode();
        printWriter.println("\n----------------- Start " + errorCode + " state -----------------");
        if (!fileLock()) {
            Log.w(TAG, "dumpStringLog filelock failed");
            return;
        }
        try {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
                try {
                    randomAccessFile.seek(17L);
                    int i = 0;
                    while (true) {
                        String line = randomAccessFile.readLine();
                        if (line == null) {
                            str2 = line;
                            break;
                        }
                        try {
                            printWriter.println(line);
                            i++;
                            if (i > 3000) {
                                str2 = "<MAX Line reached>";
                                break;
                            }
                        } catch (Throwable th) {
                            th = th;
                            try {
                                randomAccessFile.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    }
                    randomAccessFile.close();
                    if (str2 != null) {
                        printWriter.println(str2);
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                if (0 != 0) {
                    printWriter.println((String) null);
                }
                fileUnlock();
                throw th4;
            }
        } catch (FileNotFoundException unused) {
            str = "<File not found>";
            printWriter.println(str);
            fileUnlock();
            printWriter.println("----------------- End " + errorCode + " state -----------------");
        } catch (IOException unused2) {
            str = "<IO Error>";
            printWriter.println(str);
            fileUnlock();
            printWriter.println("----------------- End " + errorCode + " state -----------------");
        } catch (Exception e) {
            e.printStackTrace();
            str = "<Unknown Error>";
            printWriter.println(str);
            fileUnlock();
            printWriter.println("----------------- End " + errorCode + " state -----------------");
        }
        fileUnlock();
        printWriter.println("----------------- End " + errorCode + " state -----------------");
    }

    private static String getLegacyFilePath(LsLogType lsLogType) {
        return LOG_DIR + getFileName(lsLogType);
    }

    private static void dumpLegacy(PrintWriter printWriter) {
        String str;
        if (DEBUG) {
            Log.i(TAG, "dumpLegacy start");
        }
        boolean z = false;
        for (int i = 0; i < LsLogType.LIST.length; i++) {
            if (LsLogType.LIST[i].containsProperty(1)) {
                LsLogType lsLogType = LsLogType.LIST[i];
                String legacyFilePath = getLegacyFilePath(lsLogType);
                String errorCode = lsLogType.getErrorCode();
                if (new File(legacyFilePath).exists()) {
                    printWriter.println("\n----------------- Start " + errorCode + " state -----------------");
                    F_LOCK.lock();
                    String str2 = null;
                    try {
                        try {
                            RandomAccessFile randomAccessFile = new RandomAccessFile(legacyFilePath, "r");
                            int i2 = 0;
                            while (true) {
                                try {
                                    String line = randomAccessFile.readLine();
                                    if (line == null) {
                                        str = line;
                                        break;
                                    }
                                    try {
                                        printWriter.println(line);
                                        i2++;
                                        if (i2 > 3000) {
                                            str = "<MAX Line reached>";
                                            break;
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        str2 = line;
                                        try {
                                            randomAccessFile.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                }
                            }
                            randomAccessFile.close();
                        } catch (Throwable th4) {
                            if (str2 != null) {
                                printWriter.println(str2);
                            }
                            fileUnlock();
                            throw th4;
                        }
                    } catch (FileNotFoundException unused) {
                        str = "<File not found>";
                    } catch (IOException unused2) {
                        str = "<IO Error>";
                    } catch (Exception e) {
                        e.printStackTrace();
                        str = "<Unknown Error>";
                    }
                    if (str != null) {
                        printWriter.println(str);
                    }
                    fileUnlock();
                    printWriter.println("----------------- End " + errorCode + " state -----------------");
                    z = true;
                } else {
                    continue;
                }
            }
        }
        if (!z) {
            printWriter.println("----------------- No legacy log -----------------");
        }
        if (DEBUG) {
            Log.i(TAG, "dumpLegacy end");
        }
    }

    private static void showLegacy() {
        String str;
        if (DEBUG) {
            Log.i(TAG, "showLegacy start");
        }
        boolean z = false;
        for (int i = 0; i < LsLogType.LIST.length; i++) {
            if (LsLogType.LIST[i].containsProperty(1)) {
                LsLogType lsLogType = LsLogType.LIST[i];
                String legacyFilePath = getLegacyFilePath(lsLogType);
                String errorCode = lsLogType.getErrorCode();
                if (new File(legacyFilePath).exists()) {
                    Log.e(TAG, "!@----------------- Start " + errorCode + " state -----------------");
                    F_LOCK.lock();
                    String str2 = null;
                    try {
                        try {
                            RandomAccessFile randomAccessFile = new RandomAccessFile(legacyFilePath, "r");
                            int i2 = 0;
                            while (true) {
                                try {
                                    String line = randomAccessFile.readLine();
                                    if (line == null) {
                                        str = line;
                                        break;
                                    }
                                    try {
                                        Log.e(TAG, "!@" + line);
                                        i2++;
                                        if (i2 > 3000) {
                                            str = "!@<MAX Line reached>";
                                            break;
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        str2 = line;
                                        try {
                                            randomAccessFile.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                }
                            }
                            randomAccessFile.close();
                        } catch (Throwable th4) {
                            if (str2 != null) {
                                Log.e(TAG, str2);
                            }
                            fileUnlock();
                            throw th4;
                        }
                    } catch (FileNotFoundException unused) {
                        str = "!@<File not found>";
                    } catch (IOException unused2) {
                        str = "!@<IO Error>";
                    } catch (Exception e) {
                        e.printStackTrace();
                        str = "!@<Unknown Error>";
                    }
                    if (str != null) {
                        Log.e(TAG, str);
                    }
                    fileUnlock();
                    Log.e(TAG, "!@----------------- End " + errorCode + " state -----------------");
                    z = true;
                } else {
                    continue;
                }
            }
        }
        if (!z) {
            Log.e(TAG, "!@----------------- No legacy log -----------------");
        }
        if (DEBUG) {
            Log.i(TAG, "showLegacy end");
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.lock.LsLogFile$1] */
    public static void show() {
        new Thread() { // from class: com.samsung.android.lock.LsLogFile.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Log.e(LsLogFile.TAG, "!@LSS log start");
                for (int i = 0; i < LsLogType.LIST.length; i++) {
                    if (LsLogType.LIST[i].containsProperty(1)) {
                        LsLogFile.showStringLog(LsLogType.LIST[i]);
                    }
                }
                Log.e(LsLogFile.TAG, "!@LSS log end");
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showStringLog(LsLogType lsLogType) {
        String str;
        String str2;
        String filePath = getFilePath(lsLogType);
        String errorCode = lsLogType.getErrorCode();
        Log.e(TAG, "!@----------------- Start " + errorCode + " state -----------------");
        F_LOCK.lock();
        try {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
                try {
                    randomAccessFile.seek(17L);
                    int i = 0;
                    while (true) {
                        String line = randomAccessFile.readLine();
                        if (line == null) {
                            str2 = line;
                            break;
                        }
                        try {
                            Log.e(TAG, "!@" + line);
                            i++;
                            if (i > 3000) {
                                str2 = "!@<MAX Line reached>";
                                break;
                            }
                        } catch (Throwable th) {
                            th = th;
                            try {
                                randomAccessFile.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    }
                    randomAccessFile.close();
                    if (str2 != null) {
                        Log.e(TAG, str2);
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                if (0 != 0) {
                    Log.e(TAG, null);
                }
                fileUnlock();
                throw th4;
            }
        } catch (FileNotFoundException unused) {
            str = "!@<File not found>";
            Log.e(TAG, str);
            fileUnlock();
            Log.e(TAG, "!@----------------- End " + errorCode + " state -----------------");
        } catch (IOException unused2) {
            str = "!@<IO Error>";
            Log.e(TAG, str);
            fileUnlock();
            Log.e(TAG, "!@----------------- End " + errorCode + " state -----------------");
        } catch (Exception e) {
            e.printStackTrace();
            str = "!@<Unknown Error>";
            Log.e(TAG, str);
            fileUnlock();
            Log.e(TAG, "!@----------------- End " + errorCode + " state -----------------");
        }
        fileUnlock();
        Log.e(TAG, "!@----------------- End " + errorCode + " state -----------------");
    }

    public static String[] getLog(LsLogType lsLogType, int i) {
        String line;
        String filePath = getFilePath(lsLogType);
        String[] strArr = new String[i];
        F_LOCK.lock();
        int i2 = 0;
        try {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
                try {
                    randomAccessFile.seek(17L);
                    while (true) {
                        line = randomAccessFile.readLine();
                        if (line == null) {
                            break;
                        }
                        try {
                            strArr[i2] = line + ShaderAssembler.NEWLINE;
                            i2++;
                            if (i2 >= i) {
                                line = "<MAX Line reached>";
                                break;
                            }
                        } catch (Throwable th) {
                            th = th;
                            try {
                                randomAccessFile.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    }
                    String str = line;
                    randomAccessFile.close();
                    if (str != null && i2 < i) {
                        strArr[i2] = str;
                    }
                    fileUnlock();
                    return strArr;
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                if (0 != 0 && 0 < i) {
                    strArr[0] = null;
                }
                fileUnlock();
                throw th4;
            }
        } catch (FileNotFoundException unused) {
            if (0 < i) {
                strArr[0] = "<File not found>";
            }
            fileUnlock();
            return strArr;
        } catch (IOException unused2) {
            if (0 < i) {
                strArr[0] = "<IO Error>";
            }
            fileUnlock();
            return strArr;
        } catch (Exception e) {
            e.printStackTrace();
            if (0 < i) {
                strArr[0] = "<Unknown Error>";
            }
            fileUnlock();
            return strArr;
        }
    }

    public static void migrate(int i) throws Throwable {
        if (i <= 1) {
            Log.i(TAG, "migrateLsLog skipped!");
            return;
        }
        for (int i2 = 0; i2 < LsLogType.LIST.length; i2++) {
            migrateOldLogs(LsLogType.LIST[i2]);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x010e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0109 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0104 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean migrateOldLogs(LsLogType lsLogType) throws Throwable {
        FileOutputStream fileOutputStream;
        RandomAccessFile randomAccessFile;
        if (lsLogType == LsLogType.KEYERR) {
            return false;
        }
        File file = new File(getLegacyFilePath(lsLogType));
        if (!file.exists()) {
            Log.w(TAG, "migrateOldLogs(), No log : " + file);
            return false;
        }
        if (!fileLock()) {
            return false;
        }
        Log.w(TAG, "migrateOldLogs(), file : " + file);
        File file2 = new File(getFilePath(lsLogType));
        byte[] bArr = new byte[2048];
        long length = 0;
        FileInputStream fileInputStream = null;
        long j = 17;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    fileOutputStream.write(bArr, 0, 17);
                    while (true) {
                        int i = fileInputStream2.read(bArr);
                        if (i <= 0) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, i);
                        j += i;
                    }
                    fileInputStream2.close();
                    fileOutputStream.close();
                    randomAccessFile = new RandomAccessFile(getFilePath(lsLogType), "rwd");
                    try {
                        randomAccessFile.seek(0L);
                        randomAccessFile.writeLong(j);
                        randomAccessFile.writeLong(1L);
                        randomAccessFile.write(10);
                        length = randomAccessFile.length();
                        try {
                            fileInputStream2.close();
                        } catch (IOException unused) {
                        }
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused2) {
                        }
                    } catch (IOException e) {
                        e = e;
                        fileInputStream = fileInputStream2;
                        try {
                            Log.e(TAG, "migrate() - error" + e);
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException unused3) {
                                }
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException unused4) {
                                }
                            }
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            Log.d(TAG, String.format(Locale.US, "migrate success! [FP : %d, FS : %d]", Long.valueOf(j), Long.valueOf(length)));
                            setPermission(file2.getPath());
                            fileUnlock();
                            return true;
                        } catch (Throwable th) {
                            th = th;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException unused5) {
                                }
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException unused6) {
                                }
                            }
                            if (randomAccessFile == null) {
                                try {
                                    randomAccessFile.close();
                                    throw th;
                                } catch (IOException unused7) {
                                    throw th;
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        if (randomAccessFile == null) {
                        }
                    }
                } catch (IOException e2) {
                    e = e2;
                    randomAccessFile = null;
                } catch (Throwable th3) {
                    th = th3;
                    randomAccessFile = null;
                }
            } catch (IOException e3) {
                e = e3;
                fileOutputStream = null;
                randomAccessFile = null;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                randomAccessFile = null;
            }
        } catch (IOException e4) {
            e = e4;
            fileOutputStream = null;
            randomAccessFile = null;
        } catch (Throwable th5) {
            th = th5;
            fileOutputStream = null;
            randomAccessFile = null;
        }
        try {
            randomAccessFile.close();
        } catch (IOException unused8) {
        }
        Log.d(TAG, String.format(Locale.US, "migrate success! [FP : %d, FS : %d]", Long.valueOf(j), Long.valueOf(length)));
        setPermission(file2.getPath());
        fileUnlock();
        return true;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.lock.LsLogFile$2] */
    public static void upload(final LsLogType lsLogType) {
        Log.i(TAG, "Request upload for " + lsLogType);
        new Thread() { // from class: com.samsung.android.lock.LsLogFile.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws Throwable {
                try {
                    Thread.sleep(3000L);
                } catch (Exception e) {
                    Log.e(LsLogFile.TAG, "sleep error" + e);
                }
                if (LsLogUploader.canUpload(lsLogType)) {
                    String zipFile = LsLogFile.getZipFile();
                    if (TextUtils.isEmpty(zipFile)) {
                        Log.e(LsLogFile.TAG, "getZipFile() failed");
                    } else {
                        LsLogUploader.sendToDiagmon(lsLogType, zipFile);
                    }
                }
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00cc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00c7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.util.zip.ZipOutputStream] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [java.util.zip.ZipOutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getZipFile() throws Throwable {
        FileOutputStream fileOutputStream;
        ZipOutputStream zipOutputStream;
        ?? zipOutputStream2;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        fileInputStream2 = null;
        str = null;
        str = null;
        String str = null;
        if (!fileLock()) {
            return null;
        }
        File file = new File("/data/log/LockSettingsLog.zip");
        ?? Exists = file.exists();
        if (Exists != 0) {
            file.delete();
            Log.d(TAG, "delete file [/data/log/LockSettingsLog.zip] for upload");
        }
        byte[] bArr = new byte[2048];
        try {
            try {
                Exists = new FileOutputStream("/data/log/LockSettingsLog.zip");
            } catch (Throwable th) {
                th = th;
                fileInputStream2 = fileInputStream;
                fileOutputStream = Exists;
                zipOutputStream = zipOutputStream2;
            }
        } catch (IOException e) {
            e = e;
            Exists = 0;
            zipOutputStream2 = null;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
            zipOutputStream = null;
        }
        try {
            zipOutputStream2 = new ZipOutputStream(Exists);
            fileInputStream = null;
            for (int i = 0; i < LsLogType.LIST.length; i++) {
                try {
                    File file2 = new File(getFilePath(LsLogType.LIST[i]));
                    if (file2.exists()) {
                        zipOutputStream2.putNextEntry(new ZipEntry(LsLogType.LIST[i].getErrorCode() + LOG_EXT));
                        FileInputStream fileInputStream3 = new FileInputStream(file2);
                        try {
                            fileInputStream3.read(bArr, 0, 17);
                            while (true) {
                                int i2 = fileInputStream3.read(bArr);
                                if (i2 <= 0) {
                                    break;
                                }
                                zipOutputStream2.write(bArr, 0, i2);
                            }
                            fileInputStream3.close();
                            zipOutputStream2.closeEntry();
                            fileInputStream = fileInputStream3;
                        } catch (IOException e2) {
                            e = e2;
                            fileInputStream = fileInputStream3;
                            Log.e(TAG, "zipLogFile - error" + e);
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException unused) {
                                }
                            }
                            if (zipOutputStream2 != null) {
                                try {
                                    zipOutputStream2.close();
                                } catch (IOException unused2) {
                                }
                            }
                            if (Exists != 0) {
                                try {
                                    Exists.close();
                                } catch (IOException unused3) {
                                }
                            }
                            fileUnlock();
                            if (DEBUG) {
                            }
                            return str;
                        } catch (Throwable th3) {
                            th = th3;
                            fileInputStream2 = fileInputStream3;
                            fileOutputStream = Exists;
                            zipOutputStream = zipOutputStream2;
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
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
                } catch (IOException e3) {
                    e = e3;
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException unused7) {
                }
            }
            try {
                zipOutputStream2.close();
            } catch (IOException unused8) {
            }
            try {
                Exists.close();
            } catch (IOException unused9) {
            }
            str = "/data/log/LockSettingsLog.zip";
        } catch (IOException e4) {
            e = e4;
            zipOutputStream2 = null;
            Exists = Exists;
            fileInputStream = zipOutputStream2;
            Log.e(TAG, "zipLogFile - error" + e);
            if (fileInputStream != null) {
            }
            if (zipOutputStream2 != null) {
            }
            if (Exists != 0) {
            }
            fileUnlock();
            if (DEBUG) {
            }
            return str;
        } catch (Throwable th4) {
            th = th4;
            zipOutputStream = null;
            fileOutputStream = Exists;
        }
        fileUnlock();
        if (DEBUG) {
            Log.d(TAG, "zipLogFile - finish");
        }
        return str;
    }
}
