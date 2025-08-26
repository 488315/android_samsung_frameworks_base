package com.samsung.android.lock;

import android.system.ErrnoException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
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
        long jNanoTime = System.nanoTime();
        Cache cache = mCache;
        synchronized (cache) {
            if (cache.isFetched(0)) {
                return;
            }
            cache.setFetched(0);
            loadLoglist();
            Log.w(TAG, "prefetchData summary files : " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime) + " ms");
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
                LsLogEnroll lsLogEnrollPoll = mEnrollResults.poll();
                if (lsLogEnrollPoll.mProtectorId != 0 && lsLogEnrollPoll.mProtectorId == PROTECTOR_ID) {
                    mEnrollResults.add(lsLogEnrollPoll);
                    lsLogEnrollPoll = mEnrollResults.poll();
                }
                deleteFile(getFileName(Long.valueOf(lsLogEnrollPoll.mReqTime)));
            }
            mEnrollResults.add(lsLogEnroll);
        }
        if (z) {
            saveFile(getFileName(Long.valueOf(lsLogEnroll.mReqTime)), lsLogEnroll.toBytes());
        }
    }

    public static void addVerifySuccess(LsLogVerify lsLogVerify, boolean z) {
        LsLogVerify lsLogVerifyPoll;
        synchronized (mVerifySuccess) {
            if (mVerifySuccess.size() >= 30 && (lsLogVerifyPoll = mVerifySuccess.poll()) != null) {
                deleteFile(getFileName(Long.valueOf(lsLogVerifyPoll.mReqTime)));
            }
            mVerifySuccess.add(lsLogVerify);
        }
        if (z) {
            saveFile(getFileName(Long.valueOf(lsLogVerify.mReqTime)), lsLogVerify.toBytes());
        }
    }

    public static void addVerifyFailed(LsLogVerify lsLogVerify, boolean z) {
        LsLogVerify lsLogVerifyPoll;
        synchronized (mVerifyFailed) {
            if (mVerifyFailed.size() >= 60 && (lsLogVerifyPoll = mVerifyFailed.poll()) != null) {
                deleteFile(getFileName(Long.valueOf(lsLogVerifyPoll.mReqTime)));
            }
            mVerifyFailed.add(lsLogVerify);
        }
        if (z) {
            saveFile(getFileName(Long.valueOf(lsLogVerify.mReqTime)), lsLogVerify.toBytes());
        }
    }

    public static String getRootDir() throws ErrnoException {
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

    public static void loadLoglist() throws Throwable {
        File[] fileArrListFiles = new File(getRootDir()).listFiles();
        if (fileArrListFiles == null) {
            Log.w(TAG, "No enroll/verify list");
            return;
        }
        Arrays.sort(fileArrListFiles);
        for (File file : fileArrListFiles) {
            Log.i(TAG, "load log file : " + file);
            byte[] bArrLoadFile = loadFile(file, true);
            if (bArrLoadFile == null || bArrLoadFile.length <= 4) {
                Log.i(TAG, "skip file : " + file);
            } else {
                byte b = bArrLoadFile[0];
                if (b == 1) {
                    addEnrollResult(LsLogEnroll.fromBytes(bArrLoadFile), false);
                } else if (b == 2) {
                    addVerifySuccess(LsLogVerify.fromBytes(bArrLoadFile), false);
                } else if (b != 3) {
                    Log.w(TAG, "Data is UNKWON type " + ((int) bArrLoadFile[0]));
                } else {
                    addVerifyFailed(LsLogVerify.fromBytes(bArrLoadFile), false);
                }
            }
        }
    }

    public static void readySummary() {
        File[] fileArrListFiles = new File(getRootDir()).listFiles();
        if (fileArrListFiles == null) {
            Log.w(TAG, "readySummary : No enroll/verify list!");
            return;
        }
        Arrays.sort(fileArrListFiles);
        for (File file : fileArrListFiles) {
            mFileList.add(file);
        }
        Log.w(TAG, "readySummary : Done!");
    }

    public static String getSummaryString() throws Throwable {
        while (!mFileList.isEmpty()) {
            try {
                File filePoll = mFileList.poll();
                if (!filePoll.exists()) {
                    Log.i(TAG, "NOT Found file : " + filePoll);
                } else {
                    if (DEBUG) {
                        Log.i(TAG, "getSummary file : " + filePoll);
                    }
                    byte[] bArrLoadFile = loadFile(filePoll, true);
                    if (bArrLoadFile == null || bArrLoadFile.length <= 4) {
                        Log.i(TAG, "skip file : " + filePoll);
                    } else {
                        byte b = bArrLoadFile[0];
                        if (b == 1) {
                            LsLogEnroll lsLogEnrollFromBytes = LsLogEnroll.fromBytes(bArrLoadFile);
                            if (lsLogEnrollFromBytes.mResponse == 0 && lsLogEnrollFromBytes.mProtectorId != 0) {
                                if (lsLogEnrollFromBytes.mProtectorId == PROTECTOR_ID) {
                                    mLastEnroll = lsLogEnrollFromBytes;
                                } else if (lsLogEnrollFromBytes.mProtectorId == SICHECKER_ID) {
                                    mLastSIEnroll = lsLogEnrollFromBytes;
                                }
                            }
                            return lsLogEnrollFromBytes.toSummary();
                        }
                        if (b != 2 && b != 3) {
                            Log.w(TAG, "file info error, data[0] = " + ((int) bArrLoadFile[0]));
                        }
                        LsLogVerify lsLogVerifyFromBytes = LsLogVerify.fromBytes(bArrLoadFile);
                        if (lsLogVerifyFromBytes.mProtectorId != 0) {
                            if (lsLogVerifyFromBytes.mResponse == 0) {
                                if (lsLogVerifyFromBytes.mProtectorId == PROTECTOR_ID) {
                                    mLastVSucc = lsLogVerifyFromBytes;
                                } else if (lsLogVerifyFromBytes.mProtectorId == SICHECKER_ID) {
                                    mLastSISucc = lsLogVerifyFromBytes;
                                }
                            } else if (lsLogVerifyFromBytes.mProtectorId == PROTECTOR_ID) {
                                mLastVFail = lsLogVerifyFromBytes;
                            } else if (lsLogVerifyFromBytes.mProtectorId == SICHECKER_ID) {
                                mLastSIFail = lsLogVerifyFromBytes;
                            }
                        }
                        return lsLogVerifyFromBytes.toSummary();
                    }
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

    /* JADX WARN: Removed duplicated region for block: B:35:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static byte[] loadFile(File file, boolean z) throws Throwable {
        byte[] bArr;
        Cache cache = mCache;
        synchronized (cache) {
            if (z) {
                if (cache.hasFile(file)) {
                    return cache.peekFile(file);
                }
            }
            int version = cache.getVersion();
            Log.d(TAG, "loadfile : " + file);
            try {
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                    try {
                        int length = (int) randomAccessFile.length();
                        bArr = new byte[length];
                        try {
                            randomAccessFile.readFully(bArr, 0, length);
                            randomAccessFile.close();
                            randomAccessFile.close();
                        } catch (Throwable th) {
                            th = th;
                            try {
                                randomAccessFile.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        bArr = null;
                    }
                } catch (FileNotFoundException unused) {
                    bArr = null;
                } catch (IOException e) {
                    e = e;
                    bArr = null;
                    Log.e(TAG, "Cannot road file " + e);
                }
            } catch (FileNotFoundException unused2) {
            } catch (IOException e2) {
                e = e2;
                Log.e(TAG, "Cannot road file " + e);
                if (!z) {
                }
                return bArr;
            }
            if (!z) {
                mCache.putFileIfUnchanged(file, bArr, version);
            } else {
                mCache.putFile(file, null);
            }
            return bArr;
        }
    }

    public static void saveFile(String str, byte[] bArr) {
        saveFile(new File(getRootDir() + str), bArr);
    }

    private static void saveFile(File file, byte[] bArr) {
        FileOutputStream fileOutputStreamStartWrite;
        IOException e;
        String path;
        synchronized (mFileWriteLock) {
            AtomicFile atomicFile = new AtomicFile(file);
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    fileOutputStreamStartWrite = atomicFile.startWrite();
                    try {
                        fileOutputStreamStartWrite.write(bArr);
                        atomicFile.finishWrite(fileOutputStreamStartWrite);
                        atomicFile.failWrite(null);
                        path = file.getPath();
                    } catch (IOException e2) {
                        e = e2;
                        Log.e(TAG, "Error writing file " + file, e);
                        atomicFile.failWrite(fileOutputStreamStartWrite);
                        path = file.getPath();
                        LsLogFile.setPermission(path);
                        mCache.putFile(file, bArr);
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStreamStartWrite;
                    atomicFile.failWrite(fileOutputStream);
                    LsLogFile.setPermission(file.getPath());
                    throw th;
                }
            } catch (IOException e3) {
                fileOutputStreamStartWrite = null;
                e = e3;
            } catch (Throwable th2) {
                th = th2;
                atomicFile.failWrite(fileOutputStream);
                LsLogFile.setPermission(file.getPath());
                throw th;
            }
            LsLogFile.setPermission(path);
            mCache.putFile(file, bArr);
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
            String string = file.toString();
            for (int size = this.mCache.size() - 1; size >= 0; size--) {
                CacheKey cacheKeyKeyAt = this.mCache.keyAt(size);
                if (cacheKeyKeyAt.type == 1 && cacheKeyKeyAt.key.startsWith(string)) {
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
