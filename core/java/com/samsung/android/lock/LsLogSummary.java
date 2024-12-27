package com.samsung.android.lock;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Log;
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
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static String mLogPath = null;
    private static Queue<LsLogEnroll> mEnrollResults = new LinkedList();
    private static Queue<LsLogVerify> mVerifySuccess = new LinkedList();
    private static Queue<LsLogVerify> mVerifyFailed = new LinkedList();
    private static Queue<File> mFileList = new LinkedList();
    private static final Cache mCache = new Cache();
    private static final Object mFileWriteLock = new Object();

    public static void prefetchData() {
        long nanoTime = System.nanoTime();
        synchronized (mCache) {
            if (mCache.isFetched(0)) {
                return;
            }
            mCache.setFetched(0);
            mCache.getVersion();
            loadLoglist();
            long elapsed = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime);
            Log.e(TAG, "prefetchData summary files : " + elapsed + " ms");
        }
    }

    public static void addEnrollResult(LsLogEnroll result, boolean isSave) {
        synchronized (mEnrollResults) {
            if (mEnrollResults.size() >= 10) {
                LsLogEnroll old = mEnrollResults.poll();
                deleteFile(getFileName(Long.valueOf(old.mReqTime)));
            }
            mEnrollResults.add(result);
        }
        if (isSave) {
            saveFile(getFileName(Long.valueOf(result.mReqTime)), result.toBytes());
        }
    }

    public static void addVerifySuccess(LsLogVerify result, boolean isSave) {
        LsLogVerify old;
        synchronized (mVerifySuccess) {
            if (mVerifySuccess.size() >= 30 && (old = mVerifySuccess.poll()) != null) {
                deleteFile(getFileName(Long.valueOf(old.mReqTime)));
            }
            mVerifySuccess.add(result);
        }
        if (isSave) {
            saveFile(getFileName(Long.valueOf(result.mReqTime)), result.toBytes());
        }
    }

    public static void addVerifyFailed(LsLogVerify result, boolean isSave) {
        LsLogVerify old;
        synchronized (mVerifyFailed) {
            if (mVerifyFailed.size() >= 60 && (old = mVerifyFailed.poll()) != null) {
                deleteFile(getFileName(Long.valueOf(old.mReqTime)));
            }
            mVerifyFailed.add(result);
        }
        if (isSave) {
            saveFile(getFileName(Long.valueOf(result.mReqTime)), result.toBytes());
        }
    }

    public static String getRootDir() {
        if (mLogPath != null) {
            return mLogPath;
        }
        mLogPath = LsLogFile.getLogPath() + SUMMARY_DIR;
        File baseDir = new File(mLogPath);
        if (!baseDir.exists()) {
            if (baseDir.mkdir()) {
                LsLogFile.setPermission(baseDir.getPath());
            } else {
                Log.e(TAG, "Failed to prepare dir : " + mLogPath);
                mLogPath = LsLogFile.getLogPath();
            }
        }
        return mLogPath;
    }

    private static String getFileName(Long reqTime) {
        return TextUtils.formatSimple("%016x.log", reqTime);
    }

    public static void loadLoglist() {
        String dir = getRootDir();
        File userPath = new File(dir);
        File[] files = userPath.listFiles();
        if (files == null) {
            Log.w(TAG, "No enroll/verify list");
            return;
        }
        Arrays.sort(files);
        for (File file : files) {
            Log.i(TAG, "load log file : " + file);
            byte[] data = loadFile(file, true);
            if (data == null || data.length <= 4) {
                Log.i(TAG, "skip file : " + file);
            } else if (data[0] == 1) {
                addEnrollResult(LsLogEnroll.fromBytes(data), false);
            } else if (data[0] == 2) {
                addVerifySuccess(LsLogVerify.fromBytes(data), false);
            } else if (data[0] != 3) {
                Log.w(TAG, "Data is UNKWON type " + ((int) data[0]));
            } else {
                addVerifyFailed(LsLogVerify.fromBytes(data), false);
            }
        }
    }

    public static String[] readySummary() {
        String dir = getRootDir();
        File path = new File(dir);
        File[] files = path.listFiles();
        if (files == null) {
            Log.w(TAG, "No enroll/verify list");
            return null;
        }
        Arrays.sort(files);
        for (File file : files) {
            mFileList.add(file);
        }
        return LsLogFile.getLog(LsLogType.SUMMARY, 6);
    }

    public static String getSummaryString() {
        while (!mFileList.isEmpty()) {
            try {
                File file = mFileList.poll();
                if (!file.exists()) {
                    Log.i(TAG, "NOT Found file : " + file);
                } else {
                    if (DEBUG) {
                        Log.i(TAG, "getSummary file : " + file);
                    }
                    byte[] data = loadFile(file, true);
                    if (data != null && data.length > 4) {
                        if (data[0] == 1) {
                            return LsLogEnroll.fromBytes(data).toSummary();
                        }
                        if (data[0] == 2) {
                            return LsLogVerify.fromBytes(data).toSummary();
                        }
                        if (data[0] == 3) {
                            return LsLogVerify.fromBytes(data).toSummary();
                        }
                    }
                    Log.i(TAG, "skip file : " + file);
                }
            } catch (Exception e) {
                Log.e(TAG, "getSummaryString failed " + e);
                return e.toString();
            }
        }
        return null;
    }

    public static byte[] loadFile(String filename, boolean useCache) {
        File path = new File(getRootDir() + filename);
        return loadFile(path, useCache);
    }

    private static byte[] loadFile(File path, boolean useCache) {
        synchronized (mCache) {
            if (useCache) {
                if (mCache.hasFile(path)) {
                    return mCache.peekFile(path);
                }
            }
            int version = mCache.getVersion();
            Log.d(TAG, "loadfile : " + path);
            byte[] data = null;
            try {
                RandomAccessFile raf = new RandomAccessFile(path, "r");
                try {
                    data = new byte[(int) raf.length()];
                    raf.readFully(data, 0, data.length);
                    raf.close();
                    raf.close();
                } finally {
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e2) {
                Log.e(TAG, "Cannot road file " + e2);
            }
            if (useCache) {
                mCache.putFileIfUnchanged(path, data, version);
            } else {
                mCache.putFile(path, null);
            }
            return data;
        }
    }

    public static void saveFile(String filename, byte[] data) {
        File path = new File(getRootDir() + filename);
        saveFile(path, data);
    }

    private static void saveFile(File path, byte[] data) {
        String path2;
        synchronized (mFileWriteLock) {
            AtomicFile file = new AtomicFile(path);
            FileOutputStream out = null;
            try {
                try {
                    out = file.startWrite();
                    out.write(data);
                    file.finishWrite(out);
                    out = null;
                    file.failWrite(null);
                    path2 = path.getPath();
                } catch (IOException e) {
                    Log.e(TAG, "Error writing file " + path, e);
                    file.failWrite(out);
                    path2 = path.getPath();
                }
                LsLogFile.setPermission(path2);
                mCache.putFile(path, data);
            } catch (Throwable th) {
                file.failWrite(out);
                LsLogFile.setPermission(path.getPath());
                throw th;
            }
        }
    }

    public static void deleteFile(String filename) {
        File path = new File(getRootDir() + filename);
        deleteFile(path);
    }

    private static void deleteFile(File path) {
        synchronized (mFileWriteLock) {
            if (path.exists()) {
                new AtomicFile(path).delete();
            }
            mCache.putFile(path, null);
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

        byte[] peekFile(File path) {
            return copyOf((byte[]) peek(1, path.toString(), -1));
        }

        boolean hasFile(File path) {
            return contains(1, path.toString(), -1);
        }

        void putFile(File path, byte[] data) {
            put(1, path.toString(), copyOf(data), -1);
        }

        void putFileIfUnchanged(File path, byte[] data, int version) {
            putIfUnchanged(1, path.toString(), copyOf(data), -1, version);
        }

        void setFetched(int userId) {
            put(2, "", "true", userId);
        }

        boolean isFetched(int userId) {
            return contains(2, "", userId);
        }

        private synchronized void remove(int type, String key, int userId) {
            this.mCache.remove(this.mCacheKey.set(type, key, userId));
            this.mVersion++;
        }

        private synchronized void put(int type, String key, Object value, int userId) {
            this.mCache.put(new CacheKey().set(type, key, userId), value);
            this.mVersion++;
        }

        private synchronized void putIfUnchanged(int type, String key, Object value, int userId, int version) {
            if (!contains(type, key, userId) && this.mVersion == version) {
                this.mCache.put(new CacheKey().set(type, key, userId), value);
            }
        }

        private synchronized boolean contains(int type, String key, int userId) {
            return this.mCache.containsKey(this.mCacheKey.set(type, key, userId));
        }

        private synchronized Object peek(int type, String key, int userId) {
            return this.mCache.get(this.mCacheKey.set(type, key, userId));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized int getVersion() {
            return this.mVersion;
        }

        private byte[] copyOf(byte[] data) {
            if (data != null) {
                return Arrays.copyOf(data, data.length);
            }
            return null;
        }

        synchronized void purgePath(File path) {
            String pathStr = path.toString();
            for (int i = this.mCache.size() - 1; i >= 0; i--) {
                CacheKey entry = this.mCache.keyAt(i);
                if (entry.type == 1 && entry.key.startsWith(pathStr)) {
                    this.mCache.removeAt(i);
                }
            }
            int i2 = this.mVersion;
            this.mVersion = i2 + 1;
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

            public CacheKey set(int type, String key, int userId) {
                this.type = type;
                this.key = key;
                this.userId = userId;
                return this;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof CacheKey)) {
                    return false;
                }
                CacheKey o = (CacheKey) obj;
                return this.userId == o.userId && this.type == o.type && Objects.equals(this.key, o.key);
            }

            public int hashCode() {
                int hashCode = Objects.hashCode(this.key);
                return (((hashCode * 31) + this.userId) * 31) + this.type;
            }
        }
    }
}
