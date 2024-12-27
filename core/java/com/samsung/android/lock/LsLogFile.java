package com.samsung.android.lock;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.FileUtils;
import android.text.TextUtils;
import android.util.Log;

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

    public static void prepare() {
        long nanoTime = System.nanoTime();
        prepareFiles(prepareDir());
        long elapsed = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime);
        Log.e(TAG, "prepare lsslog file : " + elapsed + " ms");
    }

    private static String prepareDir() {
        mLogPath = "/data/log/locksettings/";
        File baseDir = new File(mLogPath);
        if (!baseDir.exists()) {
            if (baseDir.mkdir()) {
                setPermission(baseDir.getPath());
            } else {
                Log.e(TAG, "Failed to prepare dir : " + mLogPath);
                mLogPath = LOG_DIR;
            }
        }
        return mLogPath;
    }

    private static void prepareFiles(String path) {
        for (int i = 0; i < LsLogType.LIST.length; i++) {
            File logFile = new File(path, getFileName(LsLogType.LIST[i]));
            if (!logFile.exists() && !migrateOldLogs(LsLogType.LIST[i]) && fileLock()) {
                try {
                    RandomAccessFile file = new RandomAccessFile(logFile.getPath(), "rws");
                    try {
                        checkAndReset(file, LsLogType.LIST[i]);
                        file.close();
                    } catch (Throwable th) {
                        try {
                            file.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Failed to create logs : " + logFile.getPath());
                    e.printStackTrace();
                }
                setPermission(logFile.getPath());
                fileUnlock();
            }
        }
    }

    private static boolean fileLock() {
        try {
            if (!F_LOCK.tryLock() && !F_LOCK.tryLock(1L, TimeUnit.SECONDS)) {
                Log.e(TAG, "Try lock failed.");
                return false;
            }
            return true;
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

    public static boolean saveFile(LsLogType type, Queue<String> logQ) {
        String filePath = getFilePath(type);
        Log.d(
                TAG,
                "Saving file:"
                        + type
                        + "... [Queue : "
                        + logQ.size()
                        + NavigationBarInflaterView.SIZE_MOD_END);
        if (!fileLock()) {
            return false;
        }
        try {
            RandomAccessFile file = new RandomAccessFile(filePath, "rws");
            try {
                checkAndReset(file, type);
                file.seek(0L);
                long filePointer = file.readLong();
                file.seek(filePointer);
                while (!logQ.isEmpty()) {
                    String log = logQ.poll();
                    if (log != null) {
                        byte[] data = log.getBytes(StandardCharsets.UTF_8);
                        if (data.length + filePointer + 1 > getMaxSize(type)) {
                            file.seek(17L);
                        }
                        file.write(data);
                        file.write(10);
                        filePointer = file.getFilePointer();
                    }
                }
                file.seek(0L);
                file.writeLong(filePointer);
                Log.d(
                        TAG,
                        String.format(
                                Locale.US,
                                "Saving success! [FP : %d, FS : %d]",
                                Long.valueOf(filePointer),
                                Long.valueOf(file.length())));
                file.close();
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to save logs : " + e.toString());
            e.printStackTrace();
        }
        fileUnlock();
        return true;
    }

    private static void checkAndReset(RandomAccessFile file, LsLogType type) throws IOException {
        byte[] resetMessageBytes = null;
        try {
            check(file, type);
        } catch (SecurityException e) {
            resetMessageBytes = LsUtil.makeLog(e.getMessage()).getBytes(StandardCharsets.UTF_8);
            Log.d(TAG, "Reset reason : " + e.getMessage());
        }
        if (resetMessageBytes != null) {
            if (type.containsProperty(512)) {
                file.seek(0L);
                file.writeLong(17L);
                file.writeLong(1L);
                file.write(10);
                return;
            }
            if (type.containsProperty(256)) {
                long endOfFile = resetMessageBytes.length + 17 + 1;
                file.seek(0L);
                file.writeLong(endOfFile);
                file.writeLong(1L);
                file.write(10);
                file.write(resetMessageBytes);
                file.write(10);
            }
        }
    }

    private static void check(RandomAccessFile file, LsLogType type) throws SecurityException {
        try {
            file.seek(0L);
            long fileLength = file.length();
            if (fileLength == 0) {
                throw new SecurityException("Start Logging");
            }
            if (fileLength <= 17) {
                throw new SecurityException("Broken file header");
            }
            if (fileLength > getMaxSize(type)) {
                throw new SecurityException("File size exceeded");
            }
            long filePointer = file.readLong();
            if (filePointer > getMaxSize(type)) {
                throw new SecurityException("File corrupted");
            }
            long fileVersion = file.readLong();
            if (fileVersion < 1) {
                throw new SecurityException("Old version");
            }
            Log.d(TAG, "File Check : Passed!");
        } catch (IOException e) {
            throw new SecurityException("Unexpected error", e);
        }
    }

    public static void reset(LsLogType type) {
        Log.d(TAG, "reset file:" + type);
        if (!fileLock()) {
            Log.w(TAG, "reset() filelock failed");
            return;
        }
        String filePath = getFilePath(type);
        try {
            RandomAccessFile file = new RandomAccessFile(filePath, "rws");
            try {
                file.seek(0L);
                file.writeLong(17L);
                file.writeLong(1L);
                file.write(10);
                file.close();
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to save logs : " + e.toString());
            e.printStackTrace();
        }
        fileUnlock();
    }

    public static void setPermission(String filePath) {
        int permResult = FileUtils.setPermissions(filePath, 511, 1000, 1007);
        Log.d(TAG, "Set permission : " + permResult);
    }

    public static String getFilePath(LsLogType type) {
        return getLogPath() + getFileName(type);
    }

    public static String getLogPath() {
        if (mLogPath != null) {
            return mLogPath;
        }
        return prepareDir();
    }

    public static String getFileName(LsLogType type) {
        return "LockSettingsLog_" + type.getErrorCode() + LOG_EXT;
    }

    private static long getMaxSize(LsLogType type) {
        long size = type.getMaxSize();
        if (size > 0) {
            return size;
        }
        return MAX_DEFALUT_LOG_SIZE;
    }

    public static void dump(PrintWriter pw) {
        if (DEBUG) {
            Log.i(TAG, "dump start");
        }
        for (int i = 0; i < LsLogType.LIST.length; i++) {
            if (LsLogType.LIST[i].containsProperty(1)) {
                dumpStringLog(pw, LsLogType.LIST[i]);
            }
        }
        if (DEBUG) {
            Log.i(TAG, "dump end");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x005b, code lost:

       if (r4 != null) goto L34;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void dumpStringLog(
            java.io.PrintWriter r10, com.samsung.android.lock.LsLogType r11) {
        /*
            java.lang.String r0 = getFilePath(r11)
            java.lang.String r1 = r11.getErrorCode()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "\n----------------- Start "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r1)
            java.lang.String r3 = " state -----------------"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r10.println(r2)
            boolean r2 = fileLock()
            if (r2 != 0) goto L32
            java.lang.String r2 = "LsLogFile"
            java.lang.String r3 = "dumpStringLog filelock failed"
            android.util.Log.w(r2, r3)
            return
        L32:
            r2 = 0
            r4 = 0
            java.io.RandomAccessFile r5 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a java.io.IOException -> L72 java.io.FileNotFoundException -> L77
            java.lang.String r6 = "r"
            r5.<init>(r0, r6)     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a java.io.IOException -> L72 java.io.FileNotFoundException -> L77
            r6 = 17
            r5.seek(r6)     // Catch: java.lang.Throwable -> L5e
        L40:
            java.lang.String r6 = r5.readLine()     // Catch: java.lang.Throwable -> L5e
            r4 = r6
            if (r6 == 0) goto L58
            r10.println(r4)     // Catch: java.lang.Throwable -> L5e
            r4 = 0
            int r2 = r2 + 1
            long r6 = (long) r2     // Catch: java.lang.Throwable -> L5e
            r8 = 3000(0xbb8, double:1.482E-320)
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 <= 0) goto L40
            java.lang.String r6 = "<MAX Line reached>"
            r4 = r6
        L58:
            r5.close()     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a java.io.IOException -> L72 java.io.FileNotFoundException -> L77
            if (r4 == 0) goto L7f
            goto L7c
        L5e:
            r6 = move-exception
            r5.close()     // Catch: java.lang.Throwable -> L63
            goto L67
        L63:
            r7 = move-exception
            r6.addSuppressed(r7)     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a java.io.IOException -> L72 java.io.FileNotFoundException -> L77
        L67:
            throw r6     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a java.io.IOException -> L72 java.io.FileNotFoundException -> L77
        L68:
            r3 = move-exception
            goto L9e
        L6a:
            r5 = move-exception
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L68
            java.lang.String r6 = "<Unknown Error>"
            r4 = r6
            goto L7c
        L72:
            r5 = move-exception
            java.lang.String r6 = "<IO Error>"
            r4 = r6
            goto L7c
        L77:
            r5 = move-exception
            java.lang.String r6 = "<File not found>"
            r4 = r6
        L7c:
            r10.println(r4)
        L7f:
            fileUnlock()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "----------------- End "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.StringBuilder r3 = r5.append(r3)
            java.lang.String r3 = r3.toString()
            r10.println(r3)
            return
        L9e:
            if (r4 == 0) goto La3
            r10.println(r4)
        La3:
            fileUnlock()
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.lock.LsLogFile.dumpStringLog(java.io.PrintWriter,"
                    + " com.samsung.android.lock.LsLogType):void");
    }

    private static String getLegacyFilePath(LsLogType type) {
        return LOG_DIR + getFileName(type);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x007e, code lost:

       if (r9 != null) goto L40;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void dumpLegacy(java.io.PrintWriter r15) {
        /*
            Method dump skipped, instructions count: 222
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.lock.LsLogFile.dumpLegacy(java.io.PrintWriter):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0091, code lost:

       if (r9 != null) goto L40;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void showLegacy() {
        /*
            Method dump skipped, instructions count: 241
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.samsung.android.lock.LsLogFile.showLegacy():void");
    }

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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0067, code lost:

       if (r5 != null) goto L30;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void showStringLog(com.samsung.android.lock.LsLogType r11) {
        /*
            java.lang.String r0 = getFilePath(r11)
            java.lang.String r1 = r11.getErrorCode()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "!@----------------- Start "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r1)
            java.lang.String r3 = " state -----------------"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r4 = "LsLogFile"
            android.util.Log.e(r4, r2)
            r2 = 0
            r5 = 0
            java.util.concurrent.locks.Lock r6 = com.samsung.android.lock.LsLogFile.F_LOCK
            r6.lock()
            java.io.RandomAccessFile r6 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76 java.io.IOException -> L7e java.io.FileNotFoundException -> L83
            java.lang.String r7 = "r"
            r6.<init>(r0, r7)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76 java.io.IOException -> L7e java.io.FileNotFoundException -> L83
            r7 = 17
            r6.seek(r7)     // Catch: java.lang.Throwable -> L6a
        L39:
            java.lang.String r7 = r6.readLine()     // Catch: java.lang.Throwable -> L6a
            r5 = r7
            if (r7 == 0) goto L64
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6a
            r7.<init>()     // Catch: java.lang.Throwable -> L6a
            java.lang.String r8 = "!@"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch: java.lang.Throwable -> L6a
            java.lang.StringBuilder r7 = r7.append(r5)     // Catch: java.lang.Throwable -> L6a
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L6a
            android.util.Log.e(r4, r7)     // Catch: java.lang.Throwable -> L6a
            r5 = 0
            int r2 = r2 + 1
            long r7 = (long) r2     // Catch: java.lang.Throwable -> L6a
            r9 = 3000(0xbb8, double:1.482E-320)
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 <= 0) goto L39
            java.lang.String r7 = "!@<MAX Line reached>"
            r5 = r7
        L64:
            r6.close()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76 java.io.IOException -> L7e java.io.FileNotFoundException -> L83
            if (r5 == 0) goto L8b
            goto L88
        L6a:
            r7 = move-exception
            r6.close()     // Catch: java.lang.Throwable -> L6f
            goto L73
        L6f:
            r8 = move-exception
            r7.addSuppressed(r8)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76 java.io.IOException -> L7e java.io.FileNotFoundException -> L83
        L73:
            throw r7     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76 java.io.IOException -> L7e java.io.FileNotFoundException -> L83
        L74:
            r3 = move-exception
            goto Laa
        L76:
            r6 = move-exception
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L74
            java.lang.String r7 = "!@<Unknown Error>"
            r5 = r7
            goto L88
        L7e:
            r6 = move-exception
            java.lang.String r7 = "!@<IO Error>"
            r5 = r7
            goto L88
        L83:
            r6 = move-exception
            java.lang.String r7 = "!@<File not found>"
            r5 = r7
        L88:
            android.util.Log.e(r4, r5)
        L8b:
            fileUnlock()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "!@----------------- End "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.StringBuilder r3 = r6.append(r3)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r4, r3)
            return
        Laa:
            if (r5 == 0) goto Laf
            android.util.Log.e(r4, r5)
        Laf:
            fileUnlock()
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.lock.LsLogFile.showStringLog(com.samsung.android.lock.LsLogType):void");
    }

    public static String[] getLog(LsLogType type, int maxLine) {
        RandomAccessFile file;
        String filename = getFilePath(type);
        type.getErrorCode();
        int lineCount = 0;
        String line = null;
        String[] ret = new String[maxLine];
        F_LOCK.lock();
        try {
            try {
                file = new RandomAccessFile(filename, "r");
            } catch (FileNotFoundException e) {
                if (0 < maxLine) {
                    ret[0] = "<File not found>";
                }
            } catch (IOException e2) {
                if (0 < maxLine) {
                    ret[0] = "<IO Error>";
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                if (0 < maxLine) {
                    ret[0] = "<Unknown Error>";
                }
            }
            try {
                file.seek(17L);
                while (true) {
                    String readLine = file.readLine();
                    String line2 = readLine;
                    if (readLine == null) {
                        break;
                    }
                    ret[lineCount] = line2 + "\n";
                    lineCount++;
                    if (lineCount >= maxLine) {
                        line2 = "<MAX Line reached>";
                        break;
                    }
                }
                file.close();
                return ret;
            } catch (Throwable th) {
                try {
                    file.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } finally {
            if (line != null && 0 < maxLine) {
                ret[0] = line;
            }
            fileUnlock();
        }
    }

    public static void migrate(int ver) {
        if (ver <= 1) {
            Log.i(TAG, "migrateLsLog skipped!");
            return;
        }
        for (int i = 0; i < LsLogType.LIST.length; i++) {
            migrateOldLogs(LsLogType.LIST[i]);
        }
    }

    private static boolean migrateOldLogs(LsLogType logType) {
        if (logType == LsLogType.KEYERR) {
            return false;
        }
        File oldFile = new File(getLegacyFilePath(logType));
        if (!oldFile.exists()) {
            Log.w(TAG, "migrateOldLogs(), No log : " + oldFile);
            return false;
        }
        if (!fileLock()) {
            return false;
        }
        Log.w(TAG, "migrateOldLogs(), file : " + oldFile);
        File newFile = new File(getFilePath(logType));
        byte[] buffer = new byte[2048];
        long filePointer = 17;
        long fileLength = 0;
        FileInputStream fin = null;
        FileOutputStream fout = null;
        RandomAccessFile raf = null;
        try {
            try {
                try {
                    fin = new FileInputStream(oldFile);
                    fout = new FileOutputStream(newFile);
                    fout.write(buffer, 0, 17);
                    while (true) {
                        int len = fin.read(buffer);
                        if (len <= 0) {
                            break;
                        }
                        fout.write(buffer, 0, len);
                        filePointer += len;
                    }
                    fin.close();
                    fout.close();
                    raf = new RandomAccessFile(getFilePath(logType), "rwd");
                    raf.seek(0L);
                    raf.writeLong(filePointer);
                    raf.writeLong(1L);
                    raf.write(10);
                    fileLength = raf.length();
                    try {
                        fin.close();
                    } catch (IOException e) {
                    }
                    try {
                        fout.close();
                    } catch (IOException e2) {
                    }
                    raf.close();
                } finally {
                }
            } catch (IOException e3) {
            }
        } catch (IOException e4) {
            Log.e(TAG, "migrate() - error" + e4);
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e5) {
                }
            }
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e6) {
                }
            }
            if (raf != null) {
                raf.close();
            }
        }
        Log.d(
                TAG,
                String.format(
                        Locale.US,
                        "migrate success! [FP : %d, FS : %d]",
                        Long.valueOf(filePointer),
                        Long.valueOf(fileLength)));
        setPermission(newFile.getPath());
        fileUnlock();
        return true;
    }

    public static void upload(final LsLogType type) {
        Log.i(TAG, "Request upload for " + type);
        new Thread() { // from class: com.samsung.android.lock.LsLogFile.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(3000L);
                } catch (Exception e) {
                    Log.e(LsLogFile.TAG, "sleep error" + e);
                }
                if (LsLogUploader.canUpload(LsLogType.this)) {
                    String file = LsLogFile.getZipFile();
                    if (TextUtils.isEmpty(file)) {
                        Log.e(LsLogFile.TAG, "getZipFile() failed");
                    } else {
                        LsLogUploader.sendToDiagmon(LsLogType.this, file);
                    }
                }
            }
        }.start();
    }

    public static String getZipFile() {
        String zipFile = "/data/log/LockSettingsLog.zip";
        if (!fileLock()) {
            return null;
        }
        File zip = new File("/data/log/LockSettingsLog.zip");
        if (zip.exists()) {
            zip.delete();
            Log.d(TAG, "delete file [/data/log/LockSettingsLog.zip] for upload");
        }
        FileInputStream fin = null;
        FileOutputStream fout = null;
        ZipOutputStream gout = null;
        byte[] buffer = new byte[2048];
        try {
            try {
                try {
                    fout = new FileOutputStream("/data/log/LockSettingsLog.zip");
                    gout = new ZipOutputStream(fout);
                    for (int i = 0; i < LsLogType.LIST.length; i++) {
                        File logFile = new File(getFilePath(LsLogType.LIST[i]));
                        if (logFile.exists()) {
                            gout.putNextEntry(
                                    new ZipEntry(LsLogType.LIST[i].getErrorCode() + LOG_EXT));
                            fin = new FileInputStream(logFile);
                            fin.read(buffer, 0, 17);
                            while (true) {
                                int len = fin.read(buffer);
                                if (len <= 0) {
                                    break;
                                }
                                gout.write(buffer, 0, len);
                            }
                            fin.close();
                            gout.closeEntry();
                        }
                    }
                    if (fin != null) {
                        try {
                            fin.close();
                        } catch (IOException e) {
                        }
                    }
                    try {
                        gout.close();
                    } catch (IOException e2) {
                    }
                    fout.close();
                } finally {
                }
            } catch (IOException e3) {
            }
        } catch (IOException e4) {
            zipFile = null;
            Log.e(TAG, "zipLogFile - error" + e4);
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e5) {
                }
            }
            if (gout != null) {
                try {
                    gout.close();
                } catch (IOException e6) {
                }
            }
            if (fout != null) {
                fout.close();
            }
        }
        fileUnlock();
        if (DEBUG) {
            Log.d(TAG, "zipLogFile - finish");
        }
        return zipFile;
    }
}
