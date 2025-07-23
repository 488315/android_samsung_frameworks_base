package com.samsung.android.lock;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.FileUtils;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    public static void prepare() {
        long nanoTime = System.nanoTime();
        prepareFiles(prepareDir());
        Log.e(TAG, "prepare lsslog file : " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime) + " ms");
    }

    private static String prepareDir() {
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

    private static void prepareFiles(String str) {
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

    public static boolean saveFile(LsLogType lsLogType, Queue<String> queue) {
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
                long readLong = randomAccessFile.readLong();
                randomAccessFile.seek(readLong);
                while (!queue.isEmpty()) {
                    String poll = queue.poll();
                    if (poll != null) {
                        byte[] bytes = poll.getBytes(StandardCharsets.UTF_8);
                        if (readLong + bytes.length + 1 > getMaxSize(lsLogType)) {
                            randomAccessFile.seek(17L);
                        }
                        randomAccessFile.write(bytes);
                        randomAccessFile.write(10);
                        readLong = randomAccessFile.getFilePointer();
                    }
                }
                randomAccessFile.seek(0L);
                randomAccessFile.writeLong(readLong);
                Log.d(TAG, String.format(Locale.US, "Saving success! [FP : %d, FS : %d]", Long.valueOf(readLong), Long.valueOf(randomAccessFile.length())));
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

    private static void check(RandomAccessFile randomAccessFile, LsLogType lsLogType) throws SecurityException {
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

    public static void reset(LsLogType lsLogType) {
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

    public static void setPermission(String str) {
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
        RandomAccessFile randomAccessFile;
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
                randomAccessFile = new RandomAccessFile(filePath, "r");
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
            try {
                randomAccessFile.seek(17L);
                int i = 0;
                while (true) {
                    String readLine = randomAccessFile.readLine();
                    if (readLine == null) {
                        str2 = readLine;
                        break;
                    }
                    try {
                        printWriter.println(readLine);
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
                fileUnlock();
                printWriter.println("----------------- End " + errorCode + " state -----------------");
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
    }

    private static String getLegacyFilePath(LsLogType lsLogType) {
        return LOG_DIR + getFileName(lsLogType);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0079, code lost:
    
        if (r3 != null) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void dumpLegacy(java.io.PrintWriter r13) {
        /*
            boolean r0 = com.samsung.android.lock.LsLogFile.DEBUG
            java.lang.String r1 = "LsLogFile"
            if (r0 == 0) goto Lb
            java.lang.String r0 = "dumpLegacy start"
            android.util.Log.i(r1, r0)
        Lb:
            r0 = 0
            r2 = r0
            r3 = r2
        Le:
            com.samsung.android.lock.LsLogType[] r4 = com.samsung.android.lock.LsLogType.LIST
            int r4 = r4.length
            if (r2 >= r4) goto Lbc
            com.samsung.android.lock.LsLogType[] r4 = com.samsung.android.lock.LsLogType.LIST
            r4 = r4[r2]
            r5 = 1
            boolean r4 = r4.containsProperty(r5)
            if (r4 != 0) goto L20
            goto Laf
        L20:
            com.samsung.android.lock.LsLogType[] r4 = com.samsung.android.lock.LsLogType.LIST
            r4 = r4[r2]
            java.lang.String r6 = getLegacyFilePath(r4)
            java.lang.String r4 = r4.getErrorCode()
            java.io.File r7 = new java.io.File
            r7.<init>(r6)
            boolean r7 = r7.exists()
            if (r7 != 0) goto L39
            goto Laf
        L39:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r7 = "\n----------------- Start "
            r3.<init>(r7)
            r3.append(r4)
            java.lang.String r7 = " state -----------------"
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            r13.println(r3)
            java.util.concurrent.locks.Lock r3 = com.samsung.android.lock.LsLogFile.F_LOCK
            r3.lock()
            r3 = 0
            java.io.RandomAccessFile r8 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88 java.io.IOException -> L8f java.io.FileNotFoundException -> L92
            java.lang.String r9 = "r"
            r8.<init>(r6, r9)     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88 java.io.IOException -> L8f java.io.FileNotFoundException -> L92
            r6 = r0
        L5e:
            java.lang.String r9 = r8.readLine()     // Catch: java.lang.Throwable -> L7c
            if (r9 == 0) goto L75
            r13.println(r9)     // Catch: java.lang.Throwable -> L72
            int r6 = r6 + r5
            long r9 = (long) r6
            r11 = 3000(0xbb8, double:1.482E-320)
            int r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r9 <= 0) goto L5e
            java.lang.String r3 = "<MAX Line reached>"
            goto L76
        L72:
            r6 = move-exception
            r3 = r9
            goto L7d
        L75:
            r3 = r9
        L76:
            r8.close()     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88 java.io.IOException -> L8f java.io.FileNotFoundException -> L92
            if (r3 == 0) goto L97
            goto L94
        L7c:
            r6 = move-exception
        L7d:
            r8.close()     // Catch: java.lang.Throwable -> L81
            goto L85
        L81:
            r8 = move-exception
            r6.addSuppressed(r8)     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88 java.io.IOException -> L8f java.io.FileNotFoundException -> L92
        L85:
            throw r6     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88 java.io.IOException -> L8f java.io.FileNotFoundException -> L92
        L86:
            r0 = move-exception
            goto Lb3
        L88:
            r6 = move-exception
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L86
            java.lang.String r3 = "<Unknown Error>"
            goto L94
        L8f:
            java.lang.String r3 = "<IO Error>"
            goto L94
        L92:
            java.lang.String r3 = "<File not found>"
        L94:
            r13.println(r3)
        L97:
            fileUnlock()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r6 = "----------------- End "
            r3.<init>(r6)
            r3.append(r4)
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            r13.println(r3)
            r3 = r5
        Laf:
            int r2 = r2 + 1
            goto Le
        Lb3:
            if (r3 == 0) goto Lb8
            r13.println(r3)
        Lb8:
            fileUnlock()
            throw r0
        Lbc:
            if (r3 != 0) goto Lc3
            java.lang.String r0 = "----------------- No legacy log -----------------"
            r13.println(r0)
        Lc3:
            boolean r13 = com.samsung.android.lock.LsLogFile.DEBUG
            if (r13 == 0) goto Lcc
            java.lang.String r13 = "dumpLegacy end"
            android.util.Log.i(r1, r13)
        Lcc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.lock.LsLogFile.dumpLegacy(java.io.PrintWriter):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x008b, code lost:
    
        if (r3 != null) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void showLegacy() {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.lock.LsLogFile.showLegacy():void");
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
        RandomAccessFile randomAccessFile;
        String str2;
        String filePath = getFilePath(lsLogType);
        String errorCode = lsLogType.getErrorCode();
        Log.e(TAG, "!@----------------- Start " + errorCode + " state -----------------");
        F_LOCK.lock();
        try {
            try {
                randomAccessFile = new RandomAccessFile(filePath, "r");
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
            try {
                randomAccessFile.seek(17L);
                int i = 0;
                while (true) {
                    String readLine = randomAccessFile.readLine();
                    if (readLine == null) {
                        str2 = readLine;
                        break;
                    }
                    try {
                        Log.e(TAG, "!@" + readLine);
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
                fileUnlock();
                Log.e(TAG, "!@----------------- End " + errorCode + " state -----------------");
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
    }

    public static String[] getLog(LsLogType lsLogType, int i) {
        String readLine;
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
                        readLine = randomAccessFile.readLine();
                        if (readLine == null) {
                            break;
                        }
                        try {
                            strArr[i2] = readLine + ShaderAssembler.NEWLINE;
                            i2++;
                            if (i2 >= i) {
                                readLine = "<MAX Line reached>";
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
                    String str = readLine;
                    randomAccessFile.close();
                    if (str != null && i2 < i) {
                        strArr[i2] = str;
                    }
                    fileUnlock();
                    return strArr;
                } catch (Throwable th3) {
                    th = th3;
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
        } catch (Throwable th4) {
            if (0 != 0 && 0 < i) {
                strArr[0] = null;
            }
            fileUnlock();
            throw th4;
        }
    }

    public static void migrate(int i) {
        if (i <= 1) {
            Log.i(TAG, "migrateLsLog skipped!");
            return;
        }
        for (int i2 = 0; i2 < LsLogType.LIST.length; i2++) {
            migrateOldLogs(LsLogType.LIST[i2]);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x00da, code lost:
    
        if (r1 == null) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:63:0x010e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0109 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0104 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean migrateOldLogs(com.samsung.android.lock.LsLogType r13) {
        /*
            Method dump skipped, instructions count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.lock.LsLogFile.migrateOldLogs(com.samsung.android.lock.LsLogType):boolean");
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.lock.LsLogFile$2] */
    public static void upload(final LsLogType lsLogType) {
        Log.i(TAG, "Request upload for " + lsLogType);
        new Thread() { // from class: com.samsung.android.lock.LsLogFile.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(3000L);
                } catch (Exception e) {
                    Log.e(LsLogFile.TAG, "sleep error" + e);
                }
                if (LsLogUploader.canUpload(LsLogType.this)) {
                    String zipFile = LsLogFile.getZipFile();
                    if (TextUtils.isEmpty(zipFile)) {
                        Log.e(LsLogFile.TAG, "getZipFile() failed");
                    } else {
                        LsLogUploader.sendToDiagmon(LsLogType.this, zipFile);
                    }
                }
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 8, insn: 0x00de: MOVE (r1 I:??[OBJECT, ARRAY]) = (r8 I:??[OBJECT, ARRAY]), block:B:100:0x00de */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00cc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.util.zip.ZipOutputStream] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7, types: [java.util.zip.ZipOutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getZipFile() {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.lock.LsLogFile.getZipFile():java.lang.String");
    }
}
