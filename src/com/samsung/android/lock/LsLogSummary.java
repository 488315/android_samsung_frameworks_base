package com.samsung.android.lock;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class LsLogSummary {
    private static final long BODY_OFFSET = 17;
    public static final byte ENROLL_TYPE = 1;
    private static final byte EOL = 10;
    private static final int EOL_SIZE = 1;
    private static final long HEADER_LENGTH = 17;
    private static final long HEADER_OFFSET = 0;
    private static final int LONG_SIZE = 8;
    private static final int MAX_ENROLL_RESULT = 10;
    private static final int MAX_VERIFY_FAILED = 60;
    private static final int MAX_VERIFY_SUCCESS = 30;
    private static final String SUMMARY_DIR = "summary/";
    private static final String TAG = "LsLogSummary";
    public static final byte VERIFY_FAIL = 3;
    public static final byte VERIFY_SUCC = 2;
    private static String mLogPath;
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static Queue<LsLogEnroll> mEnrollResults = new LinkedList();
    private static Queue<LsLogVerify> mVerifySuccess = new LinkedList();
    private static Queue<LsLogVerify> mVerifyFailed = new LinkedList();
    private static Queue<File> mFileList = new LinkedList();
    private static final Cache mCache = new Cache();
    private static final Object mFileWriteLock = new Object();
    private static long PROTECTOR_ID = 0;
    private static long SICHECKER_ID = 0;
    private static LsLogEnroll mLastEnroll = null;
    private static LsLogVerify mLastVSucc = null;
    private static LsLogVerify mLastVFail = null;
    private static LsLogEnroll mLastSIEnroll = null;
    private static LsLogVerify mLastSISucc = null;
    private static LsLogVerify mLastSIFail = null;

    public static void prefetchData() {
        long nanoTime = System.nanoTime();
        Cache cache = mCache;
        synchronized (cache) {
            if (cache.isFetched(0)) {
                return;
            }
            cache.setFetched(0);
            loadLoglist();
            Log.w(TAG, "prefetchData summary files : " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime) + " ms");
        }
    }

    public static void setCurIds(long j, long j2) {
        if (PROTECTOR_ID != j) {
            PROTECTOR_ID = j;
            Log.w(TAG, "!@ Set PROTECTOR_ID = " + j);
            mLastEnroll = null;
            mLastVSucc = null;
            mLastVFail = null;
        }
        if (SICHECKER_ID != j2) {
            SICHECKER_ID = j2;
            Log.w(TAG, "!@ Set SICHECKER_ID = " + j2);
            mLastSIEnroll = null;
            mLastSISucc = null;
            mLastSIFail = null;
        }
    }

    public static void addEnrollResult(LsLogEnroll lsLogEnroll, boolean z) {
        synchronized (mEnrollResults) {
            if (mEnrollResults.size() >= 10) {
                LsLogEnroll poll = mEnrollResults.poll();
                if (poll.mProtectorId != 0 && poll.mProtectorId == PROTECTOR_ID) {
                    mEnrollResults.add(poll);
                    poll = mEnrollResults.poll();
                }
                deleteFile(getFileName(Long.valueOf(poll.mReqTime)));
            }
            mEnrollResults.add(lsLogEnroll);
        }
        if (z) {
            saveFile(getFileName(Long.valueOf(lsLogEnroll.mReqTime)), lsLogEnroll.toBytes());
        }
    }

    public static void addVerifySuccess(LsLogVerify lsLogVerify, boolean z) {
        LsLogVerify poll;
        synchronized (mVerifySuccess) {
            if (mVerifySuccess.size() >= 30 && (poll = mVerifySuccess.poll()) != null) {
                deleteFile(getFileName(Long.valueOf(poll.mReqTime)));
            }
            mVerifySuccess.add(lsLogVerify);
        }
        if (z) {
            saveFile(getFileName(Long.valueOf(lsLogVerify.mReqTime)), lsLogVerify.toBytes());
        }
    }

    public static void addVerifyFailed(LsLogVerify lsLogVerify, boolean z) {
        LsLogVerify poll;
        synchronized (mVerifyFailed) {
            if (mVerifyFailed.size() >= 60 && (poll = mVerifyFailed.poll()) != null) {
                deleteFile(getFileName(Long.valueOf(poll.mReqTime)));
            }
            mVerifyFailed.add(lsLogVerify);
        }
        if (z) {
            saveFile(getFileName(Long.valueOf(lsLogVerify.mReqTime)), lsLogVerify.toBytes());
        }
    }

    public static String getRootDir() {
        String str = mLogPath;
        if (str != null) {
            return str;
        }
        mLogPath = LsLogFile.getLogPath() + SUMMARY_DIR;
        File file = new File(mLogPath);
        if (!file.exists()) {
            if (file.mkdir()) {
                LsLogFile.setPermission(file.getPath());
            } else {
                Log.e(TAG, "Failed to prepare dir : " + mLogPath);
                mLogPath = LsLogFile.getLogPath();
            }
        }
        return mLogPath;
    }

    private static String getFileName(Long l) {
        return TextUtils.formatSimple("%016x.log", l);
    }

    public static void loadLoglist() {
        File[] listFiles = new File(getRootDir()).listFiles();
        if (listFiles == null) {
            Log.w(TAG, "No enroll/verify list");
            return;
        }
        Arrays.sort(listFiles);
        for (File file : listFiles) {
            Log.i(TAG, "load log file : " + file);
            byte[] loadFile = loadFile(file, true);
            if (loadFile == null || loadFile.length <= 4) {
                Log.i(TAG, "skip file : " + file);
            } else {
                byte b = loadFile[0];
                if (b == 1) {
                    addEnrollResult(LsLogEnroll.fromBytes(loadFile), false);
                } else if (b == 2) {
                    addVerifySuccess(LsLogVerify.fromBytes(loadFile), false);
                } else if (b != 3) {
                    Log.w(TAG, "Data is UNKWON type " + ((int) loadFile[0]));
                } else {
                    addVerifyFailed(LsLogVerify.fromBytes(loadFile), false);
                }
            }
        }
    }

    public static void readySummary() {
        File[] listFiles = new File(getRootDir()).listFiles();
        if (listFiles == null) {
            Log.w(TAG, "readySummary : No enroll/verify list!");
            return;
        }
        Arrays.sort(listFiles);
        for (File file : listFiles) {
            mFileList.add(file);
        }
        Log.w(TAG, "readySummary : Done!");
    }

    public static String getSummaryString() {
        while (!mFileList.isEmpty()) {
            try {
                File poll = mFileList.poll();
                if (!poll.exists()) {
                    Log.i(TAG, "NOT Found file : " + poll);
                } else {
                    if (DEBUG) {
                        Log.i(TAG, "getSummary file : " + poll);
                    }
                    byte[] loadFile = loadFile(poll, true);
                    if (loadFile != null && loadFile.length > 4) {
                        byte b = loadFile[0];
                        if (b == 1) {
                            LsLogEnroll fromBytes = LsLogEnroll.fromBytes(loadFile);
                            if (fromBytes.mResponse == 0 && fromBytes.mProtectorId != 0) {
                                if (fromBytes.mProtectorId == PROTECTOR_ID) {
                                    mLastEnroll = fromBytes;
                                } else if (fromBytes.mProtectorId == SICHECKER_ID) {
                                    mLastSIEnroll = fromBytes;
                                }
                            }
                            return fromBytes.toSummary();
                        }
                        if (b != 2 && b != 3) {
                            Log.w(TAG, "file info error, data[0] = " + ((int) loadFile[0]));
                        }
                        LsLogVerify fromBytes2 = LsLogVerify.fromBytes(loadFile);
                        if (fromBytes2.mProtectorId != 0) {
                            if (fromBytes2.mResponse == 0) {
                                if (fromBytes2.mProtectorId == PROTECTOR_ID) {
                                    mLastVSucc = fromBytes2;
                                } else if (fromBytes2.mProtectorId == SICHECKER_ID) {
                                    mLastSISucc = fromBytes2;
                                }
                            } else if (fromBytes2.mProtectorId == PROTECTOR_ID) {
                                mLastVFail = fromBytes2;
                            } else if (fromBytes2.mProtectorId == SICHECKER_ID) {
                                mLastSIFail = fromBytes2;
                            }
                        }
                        return fromBytes2.toSummary();
                    }
                    Log.i(TAG, "skip file : " + poll);
                }
            } catch (Exception e) {
                Log.e(TAG, "getSummaryString failed " + e);
                return e.toString();
            }
        }
        return null;
    }

    public static void addRecentHistory() {
        readySummary();
        LsLogger.addLog(LsLogType.SUMMARY, "");
        LsLog.summary("=== Enroll/Verify history ===");
        for (String summaryString = getSummaryString(); summaryString != null; summaryString = getSummaryString()) {
            LsLogger.addLog(LsLogType.SUMMARY, summaryString);
            Log.w(TAG, "!@ " + summaryString);
        }
    }

    public static void addAutoAnalisys() {
        if (PROTECTOR_ID == 0) {
            Log.w(TAG, "addAutoAnalisys : Protector ID is null");
            return;
        }
        LsLogger.addLog(LsLogType.SUMMARY, "");
        LsLogger.addLog(LsLogType.SUMMARY, LsUtil.makeLog("=== Auto Analisys ==="));
        LsLogger.addLog(LsLogType.SUMMARY, String.format("  = Current User Protector ID : %016x", Long.valueOf(PROTECTOR_ID)));
        if (mLastEnroll != null) {
            LsLogger.addLog(LsLogType.SUMMARY, mLastEnroll.toDetailsLog(false));
        }
        if (mLastVSucc != null) {
            LsLogger.addLog(LsLogType.SUMMARY, "  = Last Unlock Success");
            LsLogger.addLog(LsLogType.SUMMARY, mLastVSucc.toDetailsLog(false));
        }
        if (mLastVFail != null) {
            LsLogger.addLog(LsLogType.SUMMARY, "  = Last Unlock Failed");
            LsLogger.addLog(LsLogType.SUMMARY, mLastVFail.toDetailsLog(false));
            LsLogEnroll lsLogEnroll = mLastEnroll;
            if (lsLogEnroll != null) {
                if (Arrays.equals(lsLogEnroll.mSalt, mLastVFail.mSalt)) {
                    LsLogger.addLog(LsLogType.SUMMARY, "  = Salt is same as enrollment! " + LsUtil.gethashStr(mLastVFail.mSalt) + ShaderAssembler.NEWLINE);
                } else {
                    LsLogger.addLog(LsLogType.SUMMARY, "  = Salt is diff! Enrolled Salt=" + LsUtil.gethashStr(mLastEnroll.mSalt) + ", Failed Salt=" + LsUtil.gethashStr(mLastVFail.mSalt) + ShaderAssembler.NEWLINE);
                }
            } else {
                LsLogVerify lsLogVerify = mLastVSucc;
                if (lsLogVerify != null) {
                    if (Arrays.equals(lsLogVerify.mSalt, mLastVFail.mSalt)) {
                        LsLogger.addLog(LsLogType.SUMMARY, "  = Salt is same as when Auth is successful! " + LsUtil.gethashStr(mLastVFail.mSalt) + ShaderAssembler.NEWLINE);
                    } else {
                        LsLogger.addLog(LsLogType.SUMMARY, "  = Salt is diff! Succeed Salt=" + LsUtil.gethashStr(mLastVSucc.mSalt) + ", Failed Salt=" + LsUtil.gethashStr(mLastVFail.mSalt) + ShaderAssembler.NEWLINE);
                    }
                }
            }
        }
        if (mLastSIEnroll != null) {
            LsLogger.addLog(LsLogType.SUMMARY, mLastSIEnroll.toDetailsLog(true));
        }
        if (mLastSISucc != null) {
            LsLogger.addLog(LsLogType.SUMMARY, "  = SI Checker Success!");
            LsLogger.addLog(LsLogType.SUMMARY, mLastSISucc.toDetailsLog(true));
        }
        if (mLastSIFail != null) {
            LsLogger.addLog(LsLogType.SUMMARY, "  = SI Checker Failed!");
            LsLogger.addLog(LsLogType.SUMMARY, mLastSIFail.toDetailsLog(true));
        }
    }

    public static byte[] loadFile(String str, boolean z) {
        return loadFile(new File(getRootDir() + str), z);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static byte[] loadFile(java.io.File r7, boolean r8) {
        /*
            com.samsung.android.lock.LsLogSummary$Cache r0 = com.samsung.android.lock.LsLogSummary.mCache
            monitor-enter(r0)
            if (r8 == 0) goto L11
            boolean r1 = r0.hasFile(r7)     // Catch: java.lang.Throwable -> L78
            if (r1 == 0) goto L11
            byte[] r7 = r0.peekFile(r7)     // Catch: java.lang.Throwable -> L78
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L78
            return r7
        L11:
            int r1 = com.samsung.android.lock.LsLogSummary.Cache.m9282$$Nest$mgetVersion(r0)     // Catch: java.lang.Throwable -> L78
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L78
            java.lang.String r0 = "LsLogSummary"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "loadfile : "
            r2.<init>(r3)
            r2.append(r7)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r0, r2)
            r0 = 0
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L69
            java.lang.String r3 = "r"
            r2.<init>(r7, r3)     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L69
            long r3 = r2.length()     // Catch: java.lang.Throwable -> L46
            int r3 = (int) r3     // Catch: java.lang.Throwable -> L46
            byte[] r4 = new byte[r3]     // Catch: java.lang.Throwable -> L46
            r5 = 0
            r2.readFully(r4, r5, r3)     // Catch: java.lang.Throwable -> L44
            r2.close()     // Catch: java.lang.Throwable -> L44
            r2.close()     // Catch: java.io.IOException -> L51 java.io.FileNotFoundException -> L6a
            goto L6a
        L44:
            r3 = move-exception
            goto L48
        L46:
            r3 = move-exception
            r4 = r0
        L48:
            r2.close()     // Catch: java.lang.Throwable -> L4c
            goto L50
        L4c:
            r2 = move-exception
            r3.addSuppressed(r2)     // Catch: java.io.IOException -> L51 java.io.FileNotFoundException -> L6a
        L50:
            throw r3     // Catch: java.io.IOException -> L51 java.io.FileNotFoundException -> L6a
        L51:
            r2 = move-exception
            goto L55
        L53:
            r2 = move-exception
            r4 = r0
        L55:
            java.lang.String r3 = "LsLogSummary"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Cannot road file "
            r5.<init>(r6)
            r5.append(r2)
            java.lang.String r2 = r5.toString()
            android.util.Log.e(r3, r2)
            goto L6a
        L69:
            r4 = r0
        L6a:
            if (r8 == 0) goto L72
            com.samsung.android.lock.LsLogSummary$Cache r8 = com.samsung.android.lock.LsLogSummary.mCache
            r8.putFileIfUnchanged(r7, r4, r1)
            goto L77
        L72:
            com.samsung.android.lock.LsLogSummary$Cache r8 = com.samsung.android.lock.LsLogSummary.mCache
            r8.putFile(r7, r0)
        L77:
            return r4
        L78:
            r7 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L78
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.lock.LsLogSummary.loadFile(java.io.File, boolean):byte[]");
    }

    public static void saveFile(String str, byte[] bArr) {
        saveFile(new File(getRootDir() + str), bArr);
    }

    private static void saveFile(File file, byte[] bArr) {
        FileOutputStream fileOutputStream;
        IOException e;
        String path;
        synchronized (mFileWriteLock) {
            AtomicFile atomicFile = new AtomicFile(file);
            FileOutputStream fileOutputStream2 = null;
            try {
                try {
                    fileOutputStream = atomicFile.startWrite();
                } catch (IOException e2) {
                    fileOutputStream = null;
                    e = e2;
                } catch (Throwable th) {
                    th = th;
                    atomicFile.failWrite(fileOutputStream2);
                    LsLogFile.setPermission(file.getPath());
                    throw th;
                }
                try {
                    fileOutputStream.write(bArr);
                    atomicFile.finishWrite(fileOutputStream);
                    atomicFile.failWrite(null);
                    path = file.getPath();
                } catch (IOException e3) {
                    e = e3;
                    Log.e(TAG, "Error writing file " + file, e);
                    atomicFile.failWrite(fileOutputStream);
                    path = file.getPath();
                    LsLogFile.setPermission(path);
                    mCache.putFile(file, bArr);
                }
                LsLogFile.setPermission(path);
                mCache.putFile(file, bArr);
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                atomicFile.failWrite(fileOutputStream2);
                LsLogFile.setPermission(file.getPath());
                throw th;
            }
        }
    }

    public static void deleteFile(String str) {
        deleteFile(new File(getRootDir() + str));
    }

    private static void deleteFile(File file) {
        synchronized (mFileWriteLock) {
            if (file.exists()) {
                new AtomicFile(file).delete();
            }
            mCache.putFile(file, null);
        }
    }

    private static class Cache {
        private final ArrayMap<CacheKey, Object> mCache;
        private final CacheKey mCacheKey;
        private int mVersion;

        private Cache() {
            this.mCache = new ArrayMap<>();
            this.mCacheKey = new CacheKey();
            this.mVersion = 0;
        }

        byte[] peekFile(File file) {
            return copyOf((byte[]) peek(1, file.toString(), -1));
        }

        boolean hasFile(File file) {
            return contains(1, file.toString(), -1);
        }

        void putFile(File file, byte[] bArr) {
            put(1, file.toString(), copyOf(bArr), -1);
        }

        void putFileIfUnchanged(File file, byte[] bArr, int i) {
            putIfUnchanged(1, file.toString(), copyOf(bArr), -1, i);
        }

        void setFetched(int i) {
            put(2, "", "true", i);
        }

        boolean isFetched(int i) {
            return contains(2, "", i);
        }

        private synchronized void remove(int i, String str, int i2) {
            this.mCache.remove(this.mCacheKey.set(i, str, i2));
            this.mVersion++;
        }

        private synchronized void put(int i, String str, Object obj, int i2) {
            this.mCache.put(new CacheKey().set(i, str, i2), obj);
            this.mVersion++;
        }

        private synchronized void putIfUnchanged(int i, String str, Object obj, int i2, int i3) {
            if (!contains(i, str, i2) && this.mVersion == i3) {
                this.mCache.put(new CacheKey().set(i, str, i2), obj);
            }
        }

        private synchronized boolean contains(int i, String str, int i2) {
            return this.mCache.containsKey(this.mCacheKey.set(i, str, i2));
        }

        private synchronized Object peek(int i, String str, int i2) {
            return this.mCache.get(this.mCacheKey.set(i, str, i2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized int getVersion() {
            return this.mVersion;
        }

        private byte[] copyOf(byte[] bArr) {
            if (bArr != null) {
                return Arrays.copyOf(bArr, bArr.length);
            }
            return null;
        }

        synchronized void purgePath(File file) {
            String file2 = file.toString();
            for (int size = this.mCache.size() - 1; size >= 0; size--) {
                CacheKey keyAt = this.mCache.keyAt(size);
                if (keyAt.type == 1 && keyAt.key.startsWith(file2)) {
                    this.mCache.removeAt(size);
                }
            }
            this.mVersion++;
        }

        synchronized void clear() {
            this.mCache.clear();
            this.mVersion++;
        }

        private static final class CacheKey {
            static final int TYPE_FETCHED = 2;
            static final int TYPE_FILE = 1;
            static final int TYPE_KEY_VALUE = 0;
            String key;
            int type;
            int userId;

            private CacheKey() {
            }

            public CacheKey set(int i, String str, int i2) {
                this.type = i;
                this.key = str;
                this.userId = i2;
                return this;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof CacheKey)) {
                    return false;
                }
                CacheKey cacheKey = (CacheKey) obj;
                return this.userId == cacheKey.userId && this.type == cacheKey.type && Objects.equals(this.key, cacheKey.key);
            }

            public int hashCode() {
                return (((Objects.hashCode(this.key) * 31) + this.userId) * 31) + this.type;
            }
        }
    }
}
