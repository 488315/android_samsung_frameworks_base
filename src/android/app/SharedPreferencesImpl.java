package android.app;

import android.app.SharedPreferencesImpl;
import android.compat.Compatibility;
import android.content.SharedPreferences;
import android.os.FileUtils;
import android.os.Looper;
import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStat;
import android.system.StructTimespec;
import android.util.Log;
import com.android.internal.util.ExponentiallyBucketedHistogram;
import com.android.internal.util.XmlUtils;
import dalvik.system.BlockGuard;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
final class SharedPreferencesImpl implements SharedPreferences {
    private static final long CALLBACK_ON_CLEAR_CHANGE = 119147584;
    private static final boolean DEBUG = false;
    private static final long MAX_FSYNC_DURATION_MILLIS = 256;
    private static final String TAG = "SharedPreferencesImpl";
    private final File mBackupFile;
    private long mCurrentMemoryStateGeneration;
    private long mDiskStateGeneration;
    private final File mFile;
    private boolean mLoaded;
    private final int mMode;
    private long mStatSize;
    private StructTimespec mStatTimestamp;
    private static final Object CONTENT = new Object();
    private static final ThreadPoolExecutor sLoadExecutor = new ThreadPoolExecutor(0, 1, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(), new SharedPreferencesThreadFactory());
    private final Object mLock = new Object();
    private final Object mWritingToDiskLock = new Object();
    private int mDiskWritesInFlight = 0;
    private final WeakHashMap<SharedPreferences.OnSharedPreferenceChangeListener, Object> mListeners = new WeakHashMap<>();
    private final ExponentiallyBucketedHistogram mSyncTimes = new ExponentiallyBucketedHistogram(16);
    private int mNumSync = 0;
    private Map<String, Object> mMap = null;
    private Throwable mThrowable = null;

    SharedPreferencesImpl(File file, int i) {
        this.mLoaded = false;
        this.mFile = file;
        this.mBackupFile = makeBackupFile(file);
        this.mMode = i;
        this.mLoaded = false;
        startLoadFromDisk();
    }

    private void startLoadFromDisk() {
        synchronized (this.mLock) {
            this.mLoaded = false;
        }
        sLoadExecutor.execute(new Runnable() { // from class: android.app.SharedPreferencesImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SharedPreferencesImpl.this.lambda$startLoadFromDisk$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ad  */
    /* renamed from: loadFromDisk, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lambda$startLoadFromDisk$0() {
        /*
            Method dump skipped, instructions count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.SharedPreferencesImpl.lambda$startLoadFromDisk$0():void");
    }

    static File makeBackupFile(File file) {
        return new File(file.getPath() + ".bak");
    }

    void startReloadIfChangedUnexpectedly() {
        synchronized (this.mLock) {
            if (hasFileChangedUnexpectedly()) {
                startLoadFromDisk();
            }
        }
    }

    private boolean hasFileChangedUnexpectedly() {
        boolean z;
        synchronized (this.mLock) {
            if (this.mDiskWritesInFlight > 0) {
                return false;
            }
            try {
                BlockGuard.getThreadPolicy().onReadFromDisk();
                StructStat stat = Os.stat(this.mFile.getPath());
                synchronized (this.mLock) {
                    z = (stat.st_mtim.equals(this.mStatTimestamp) && this.mStatSize == stat.st_size) ? false : true;
                }
                return z;
            } catch (ErrnoException unused) {
                return true;
            }
        }
    }

    @Override // android.content.SharedPreferences
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        synchronized (this.mLock) {
            this.mListeners.put(onSharedPreferenceChangeListener, CONTENT);
        }
    }

    @Override // android.content.SharedPreferences
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        synchronized (this.mLock) {
            this.mListeners.remove(onSharedPreferenceChangeListener);
        }
    }

    private void awaitLoadedLocked() {
        if (!this.mLoaded) {
            BlockGuard.getThreadPolicy().onReadFromDisk();
        }
        while (!this.mLoaded) {
            try {
                this.mLock.wait();
            } catch (InterruptedException unused) {
            }
        }
        if (this.mThrowable != null) {
            throw new IllegalStateException(this.mThrowable);
        }
    }

    @Override // android.content.SharedPreferences
    public Map<String, ?> getAll() {
        HashMap hashMap;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            hashMap = new HashMap(this.mMap);
        }
        return hashMap;
    }

    @Override // android.content.SharedPreferences
    public String getString(String str, String str2) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            String str3 = (String) this.mMap.get(str);
            if (str3 != null) {
                str2 = str3;
            }
        }
        return str2;
    }

    @Override // android.content.SharedPreferences
    public Set<String> getStringSet(String str, Set<String> set) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            Set<String> set2 = (Set) this.mMap.get(str);
            if (set2 != null) {
                set = set2;
            }
        }
        return set;
    }

    @Override // android.content.SharedPreferences
    public int getInt(String str, int i) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            Integer num = (Integer) this.mMap.get(str);
            if (num != null) {
                i = num.intValue();
            }
        }
        return i;
    }

    @Override // android.content.SharedPreferences
    public long getLong(String str, long j) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            Long l = (Long) this.mMap.get(str);
            if (l != null) {
                j = l.longValue();
            }
        }
        return j;
    }

    @Override // android.content.SharedPreferences
    public float getFloat(String str, float f) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            Float f2 = (Float) this.mMap.get(str);
            if (f2 != null) {
                f = f2.floatValue();
            }
        }
        return f;
    }

    @Override // android.content.SharedPreferences
    public boolean getBoolean(String str, boolean z) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            Boolean bool = (Boolean) this.mMap.get(str);
            if (bool != null) {
                z = bool.booleanValue();
            }
        }
        return z;
    }

    @Override // android.content.SharedPreferences
    public boolean contains(String str) {
        boolean containsKey;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            containsKey = this.mMap.containsKey(str);
        }
        return containsKey;
    }

    @Override // android.content.SharedPreferences
    public SharedPreferences.Editor edit() {
        synchronized (this.mLock) {
            awaitLoadedLocked();
        }
        return new EditorImpl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MemoryCommitResult {
        final boolean keysCleared;
        final List<String> keysModified;
        final Set<SharedPreferences.OnSharedPreferenceChangeListener> listeners;
        final Map<String, Object> mapToWriteToDisk;
        final long memoryStateGeneration;
        boolean wasWritten;
        volatile boolean writeToDiskResult;
        final CountDownLatch writtenToDiskLatch;

        private MemoryCommitResult(long j, boolean z, List<String> list, Set<SharedPreferences.OnSharedPreferenceChangeListener> set, Map<String, Object> map) {
            this.writtenToDiskLatch = new CountDownLatch(1);
            this.writeToDiskResult = false;
            this.wasWritten = false;
            this.memoryStateGeneration = j;
            this.keysCleared = z;
            this.keysModified = list;
            this.listeners = set;
            this.mapToWriteToDisk = map;
        }

        void setDiskWriteResult(boolean z, boolean z2) {
            this.wasWritten = z;
            this.writeToDiskResult = z2;
            this.writtenToDiskLatch.countDown();
        }
    }

    public final class EditorImpl implements SharedPreferences.Editor {
        private final Object mEditorLock = new Object();
        private final Map<String, Object> mModified = new HashMap();
        private boolean mClear = false;

        public EditorImpl() {
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putString(String str, String str2) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, str2);
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putStringSet(String str, Set<String> set) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, set == null ? null : new HashSet(set));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putInt(String str, int i) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, Integer.valueOf(i));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putLong(String str, long j) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, Long.valueOf(j));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putFloat(String str, float f) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, Float.valueOf(f));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putBoolean(String str, boolean z) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, Boolean.valueOf(z));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor remove(String str) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, this);
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor clear() {
            synchronized (this.mEditorLock) {
                this.mClear = true;
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public void apply() {
            final long currentTimeMillis = System.currentTimeMillis();
            final MemoryCommitResult commitToMemory = commitToMemory();
            final Runnable runnable = new Runnable(this) { // from class: android.app.SharedPreferencesImpl.EditorImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        commitToMemory.writtenToDiskLatch.await();
                    } catch (InterruptedException unused) {
                    }
                }
            };
            QueuedWork.addFinisher(runnable);
            SharedPreferencesImpl.this.enqueueDiskWrite(commitToMemory, new Runnable(this) { // from class: android.app.SharedPreferencesImpl.EditorImpl.2
                @Override // java.lang.Runnable
                public void run() {
                    runnable.run();
                    QueuedWork.removeFinisher(runnable);
                }
            });
            lambda$notifyListeners$0(commitToMemory);
        }

        /* JADX WARN: Removed duplicated region for block: B:38:0x00bd A[Catch: all -> 0x00e5, TryCatch #0 {, blocks: (B:15:0x005f, B:17:0x0063, B:19:0x0069, B:20:0x006f, B:21:0x0075, B:22:0x007f, B:24:0x0085, B:42:0x009a, B:44:0x00a0, B:46:0x00a6, B:49:0x00ad, B:38:0x00bd, B:29:0x00b1, B:36:0x00b8, B:57:0x00c2, B:59:0x00c9, B:60:0x00d5, B:61:0x00db), top: B:14:0x005f, outer: #1 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private android.app.SharedPreferencesImpl.MemoryCommitResult commitToMemory() {
            /*
                Method dump skipped, instructions count: 237
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.SharedPreferencesImpl.EditorImpl.commitToMemory():android.app.SharedPreferencesImpl$MemoryCommitResult");
        }

        @Override // android.content.SharedPreferences.Editor
        public boolean commit() {
            MemoryCommitResult commitToMemory = commitToMemory();
            SharedPreferencesImpl.this.enqueueDiskWrite(commitToMemory, null);
            try {
                commitToMemory.writtenToDiskLatch.await();
                lambda$notifyListeners$0(commitToMemory);
                return commitToMemory.writeToDiskResult;
            } catch (InterruptedException unused) {
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: notifyListeners, reason: merged with bridge method [inline-methods] */
        public void lambda$notifyListeners$0(final MemoryCommitResult memoryCommitResult) {
            if (memoryCommitResult.listeners != null) {
                if (memoryCommitResult.keysModified != null || memoryCommitResult.keysCleared) {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                        if (memoryCommitResult.keysCleared && Compatibility.isChangeEnabled(SharedPreferencesImpl.CALLBACK_ON_CLEAR_CHANGE)) {
                            for (SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener : memoryCommitResult.listeners) {
                                if (onSharedPreferenceChangeListener != null) {
                                    onSharedPreferenceChangeListener.onSharedPreferenceChanged(SharedPreferencesImpl.this, null);
                                }
                            }
                        }
                        for (int size = memoryCommitResult.keysModified.size() - 1; size >= 0; size--) {
                            String str = memoryCommitResult.keysModified.get(size);
                            for (SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener2 : memoryCommitResult.listeners) {
                                if (onSharedPreferenceChangeListener2 != null) {
                                    onSharedPreferenceChangeListener2.onSharedPreferenceChanged(SharedPreferencesImpl.this, str);
                                }
                            }
                        }
                        return;
                    }
                    ActivityThread.sMainThreadHandler.post(new Runnable() { // from class: android.app.SharedPreferencesImpl$EditorImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedPreferencesImpl.EditorImpl.this.lambda$notifyListeners$0(memoryCommitResult);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enqueueDiskWrite(final MemoryCommitResult memoryCommitResult, final Runnable runnable) {
        boolean z;
        final boolean z2 = runnable == null;
        Runnable runnable2 = new Runnable() { // from class: android.app.SharedPreferencesImpl.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (SharedPreferencesImpl.this.mWritingToDiskLock) {
                    SharedPreferencesImpl.this.writeToFile(memoryCommitResult, z2);
                }
                synchronized (SharedPreferencesImpl.this.mLock) {
                    SharedPreferencesImpl sharedPreferencesImpl = SharedPreferencesImpl.this;
                    sharedPreferencesImpl.mDiskWritesInFlight--;
                }
                Runnable runnable3 = runnable;
                if (runnable3 != null) {
                    runnable3.run();
                }
            }
        };
        if (z2) {
            synchronized (this.mLock) {
                z = this.mDiskWritesInFlight == 1;
            }
            if (z) {
                runnable2.run();
                return;
            }
        }
        QueuedWork.queue(runnable2, !z2);
    }

    private static FileOutputStream createFileOutputStream(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException unused) {
            File parentFile = file.getParentFile();
            if (!parentFile.mkdir()) {
                Log.e(TAG, "Couldn't create directory for SharedPreferences file " + file);
                return null;
            }
            FileUtils.setPermissions(parentFile.getPath(), 505, -1, -1);
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Couldn't create SharedPreferences file " + file, e);
                return null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeToFile(MemoryCommitResult memoryCommitResult, boolean z) {
        boolean z2;
        if (this.mFile.exists()) {
            if (this.mDiskStateGeneration >= memoryCommitResult.memoryStateGeneration) {
                z2 = false;
            } else if (z) {
                z2 = true;
            } else {
                synchronized (this.mLock) {
                    z2 = this.mCurrentMemoryStateGeneration == memoryCommitResult.memoryStateGeneration;
                }
            }
            if (!z2) {
                memoryCommitResult.setDiskWriteResult(false, true);
                return;
            }
            if (!this.mBackupFile.exists()) {
                if (!this.mFile.renameTo(this.mBackupFile)) {
                    Log.e(TAG, "Couldn't rename file " + this.mFile + " to backup file " + this.mBackupFile);
                    memoryCommitResult.setDiskWriteResult(false, false);
                    return;
                }
            } else {
                this.mFile.delete();
            }
        }
        try {
            FileOutputStream createFileOutputStream = createFileOutputStream(this.mFile);
            if (createFileOutputStream == null) {
                memoryCommitResult.setDiskWriteResult(false, false);
                return;
            }
            XmlUtils.writeMapXml(memoryCommitResult.mapToWriteToDisk, createFileOutputStream);
            long currentTimeMillis = System.currentTimeMillis();
            FileUtils.sync(createFileOutputStream);
            long currentTimeMillis2 = System.currentTimeMillis();
            createFileOutputStream.close();
            ContextImpl.setFilePermissionsFromMode(this.mFile.getPath(), this.mMode, 0);
            try {
                StructStat stat = Os.stat(this.mFile.getPath());
                synchronized (this.mLock) {
                    this.mStatTimestamp = stat.st_mtim;
                    this.mStatSize = stat.st_size;
                }
            } catch (ErrnoException unused) {
            }
            this.mBackupFile.delete();
            this.mDiskStateGeneration = memoryCommitResult.memoryStateGeneration;
            memoryCommitResult.setDiskWriteResult(true, true);
            long j = currentTimeMillis2 - currentTimeMillis;
            this.mSyncTimes.add((int) j);
            int i = this.mNumSync + 1;
            this.mNumSync = i;
            if (i % 1024 == 0 || j > 256) {
                this.mSyncTimes.log(TAG, "Time required to fsync " + this.mFile + ": ");
            }
        } catch (IOException e) {
            Log.w(TAG, "writeToFile: Got exception:", e);
            if (this.mFile.exists() && !this.mFile.delete()) {
                Log.e(TAG, "Couldn't clean up partially-written file " + this.mFile);
            }
            memoryCommitResult.setDiskWriteResult(false, false);
        } catch (XmlPullParserException e2) {
            Log.w(TAG, "writeToFile: Got exception:", e2);
            if (this.mFile.exists()) {
                Log.e(TAG, "Couldn't clean up partially-written file " + this.mFile);
            }
            memoryCommitResult.setDiskWriteResult(false, false);
        }
    }

    private static final class SharedPreferencesThreadFactory implements ThreadFactory {
        private SharedPreferencesThreadFactory() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread newThread = Executors.defaultThreadFactory().newThread(runnable);
            newThread.setName("SharedPreferences");
            return newThread;
        }
    }
}
