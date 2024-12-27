package com.android.server.net.watchlist;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.SystemClock;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Slog;
import com.android.internal.util.HexDump;
import com.android.server.net.watchlist.FileHashCache;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FileHashCache {
    public static boolean sLoggedWtf = false;
    static String sPersistFileName = "/data/system/file_hash_cache";
    public static final long sSaveDeferredDelayMillis = TimeUnit.SECONDS.toMillis(5);
    public final Map mEntries = new HashMap();
    public final Handler mHandler;
    public final FileHashCache$$ExternalSyntheticLambda0 mLoadTask;
    public final FileHashCache$$ExternalSyntheticLambda0 mSaveTask;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Entry {
        public final long mLastModified;
        public final byte[] mSha256Hash;

        public Entry(long j, byte[] bArr) {
            this.mLastModified = j;
            this.mSha256Hash = bArr;
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.net.watchlist.FileHashCache$$ExternalSyntheticLambda0] */
    public FileHashCache(Handler handler) {
        final int i = 0;
        Runnable runnable = new Runnable(this) { // from class: com.android.server.net.watchlist.FileHashCache$$ExternalSyntheticLambda0
            public final /* synthetic */ FileHashCache f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v9, types: [com.android.server.net.watchlist.FileHashCache$$ExternalSyntheticLambda2, java.util.function.Consumer] */
            @Override // java.lang.Runnable
            public final void run() {
                int i2 = i;
                final FileHashCache fileHashCache = this.f$0;
                switch (i2) {
                    case 0:
                        ((HashMap) fileHashCache.mEntries).clear();
                        SystemClock.currentTimeMicro();
                        File file = new File(FileHashCache.sPersistFileName);
                        if (!file.exists()) {
                            return;
                        }
                        BufferedReader bufferedReader = null;
                        BufferedReader bufferedReader2 = null;
                        try {
                            try {
                                BufferedReader bufferedReader3 = new BufferedReader(new FileReader(file));
                                try {
                                    Stream lines = bufferedReader3.lines();
                                    ?? r1 = new Consumer() { // from class: com.android.server.net.watchlist.FileHashCache$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            FileHashCache fileHashCache2 = FileHashCache.this;
                                            String str = (String) obj;
                                            fileHashCache2.getClass();
                                            try {
                                                StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
                                                File file2 = new File(stringTokenizer.nextToken());
                                                long parseLong = Long.parseLong(stringTokenizer.nextToken());
                                                byte[] hexStringToByteArray = HexDump.hexStringToByteArray(stringTokenizer.nextToken());
                                                ((HashMap) fileHashCache2.mEntries).put(file2, new FileHashCache.Entry(parseLong, hexStringToByteArray));
                                            } catch (RuntimeException e) {
                                                String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Invalid entry for ", str);
                                                if (FileHashCache.sLoggedWtf) {
                                                    Slog.w("FileHashCache", m, e);
                                                } else {
                                                    Slog.wtf("FileHashCache", m, e);
                                                    FileHashCache.sLoggedWtf = true;
                                                }
                                            }
                                        }
                                    };
                                    lines.forEach(r1);
                                    FileHashCache.closeQuietly(bufferedReader3);
                                    bufferedReader = r1;
                                } catch (IOException | UncheckedIOException e) {
                                    e = e;
                                    bufferedReader2 = bufferedReader3;
                                    Slog.e("FileHashCache", "Failed to read storage file", e);
                                    FileHashCache.closeQuietly(bufferedReader2);
                                    bufferedReader = bufferedReader2;
                                    return;
                                } catch (Throwable th) {
                                    th = th;
                                    bufferedReader = bufferedReader3;
                                    FileHashCache.closeQuietly(bufferedReader);
                                    throw th;
                                }
                            } catch (IOException | UncheckedIOException e2) {
                                e = e2;
                            }
                            return;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    default:
                        fileHashCache.getClass();
                        SystemClock.currentTimeMicro();
                        BufferedWriter bufferedWriter = null;
                        try {
                            try {
                                BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(FileHashCache.sPersistFileName));
                                try {
                                    for (Map.Entry entry : ((HashMap) fileHashCache.mEntries).entrySet()) {
                                        bufferedWriter2.write(entry.getKey() + "," + ((FileHashCache.Entry) entry.getValue()).mLastModified + "," + HexDump.toHexString(((FileHashCache.Entry) entry.getValue()).mSha256Hash) + "\n");
                                    }
                                    FileHashCache.closeQuietly(bufferedWriter2);
                                    return;
                                } catch (IOException e3) {
                                    e = e3;
                                    bufferedWriter = bufferedWriter2;
                                    Slog.e("FileHashCache", "Failed to save.", e);
                                    FileHashCache.closeQuietly(bufferedWriter);
                                    return;
                                } catch (Throwable th3) {
                                    th = th3;
                                    bufferedWriter = bufferedWriter2;
                                    FileHashCache.closeQuietly(bufferedWriter);
                                    throw th;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                            }
                        } catch (IOException e4) {
                            e = e4;
                        }
                }
            }
        };
        final int i2 = 1;
        this.mSaveTask = new Runnable(this) { // from class: com.android.server.net.watchlist.FileHashCache$$ExternalSyntheticLambda0
            public final /* synthetic */ FileHashCache f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v9, types: [com.android.server.net.watchlist.FileHashCache$$ExternalSyntheticLambda2, java.util.function.Consumer] */
            @Override // java.lang.Runnable
            public final void run() {
                int i22 = i2;
                final FileHashCache fileHashCache = this.f$0;
                switch (i22) {
                    case 0:
                        ((HashMap) fileHashCache.mEntries).clear();
                        SystemClock.currentTimeMicro();
                        File file = new File(FileHashCache.sPersistFileName);
                        if (!file.exists()) {
                            return;
                        }
                        BufferedReader bufferedReader = null;
                        BufferedReader bufferedReader2 = null;
                        try {
                            try {
                                BufferedReader bufferedReader3 = new BufferedReader(new FileReader(file));
                                try {
                                    Stream lines = bufferedReader3.lines();
                                    ?? r1 = new Consumer() { // from class: com.android.server.net.watchlist.FileHashCache$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            FileHashCache fileHashCache2 = FileHashCache.this;
                                            String str = (String) obj;
                                            fileHashCache2.getClass();
                                            try {
                                                StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
                                                File file2 = new File(stringTokenizer.nextToken());
                                                long parseLong = Long.parseLong(stringTokenizer.nextToken());
                                                byte[] hexStringToByteArray = HexDump.hexStringToByteArray(stringTokenizer.nextToken());
                                                ((HashMap) fileHashCache2.mEntries).put(file2, new FileHashCache.Entry(parseLong, hexStringToByteArray));
                                            } catch (RuntimeException e) {
                                                String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Invalid entry for ", str);
                                                if (FileHashCache.sLoggedWtf) {
                                                    Slog.w("FileHashCache", m, e);
                                                } else {
                                                    Slog.wtf("FileHashCache", m, e);
                                                    FileHashCache.sLoggedWtf = true;
                                                }
                                            }
                                        }
                                    };
                                    lines.forEach(r1);
                                    FileHashCache.closeQuietly(bufferedReader3);
                                    bufferedReader = r1;
                                } catch (IOException | UncheckedIOException e) {
                                    e = e;
                                    bufferedReader2 = bufferedReader3;
                                    Slog.e("FileHashCache", "Failed to read storage file", e);
                                    FileHashCache.closeQuietly(bufferedReader2);
                                    bufferedReader = bufferedReader2;
                                    return;
                                } catch (Throwable th) {
                                    th = th;
                                    bufferedReader = bufferedReader3;
                                    FileHashCache.closeQuietly(bufferedReader);
                                    throw th;
                                }
                            } catch (IOException | UncheckedIOException e2) {
                                e = e2;
                            }
                            return;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    default:
                        fileHashCache.getClass();
                        SystemClock.currentTimeMicro();
                        BufferedWriter bufferedWriter = null;
                        try {
                            try {
                                BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(FileHashCache.sPersistFileName));
                                try {
                                    for (Map.Entry entry : ((HashMap) fileHashCache.mEntries).entrySet()) {
                                        bufferedWriter2.write(entry.getKey() + "," + ((FileHashCache.Entry) entry.getValue()).mLastModified + "," + HexDump.toHexString(((FileHashCache.Entry) entry.getValue()).mSha256Hash) + "\n");
                                    }
                                    FileHashCache.closeQuietly(bufferedWriter2);
                                    return;
                                } catch (IOException e3) {
                                    e = e3;
                                    bufferedWriter = bufferedWriter2;
                                    Slog.e("FileHashCache", "Failed to save.", e);
                                    FileHashCache.closeQuietly(bufferedWriter);
                                    return;
                                } catch (Throwable th3) {
                                    th = th3;
                                    bufferedWriter = bufferedWriter2;
                                    FileHashCache.closeQuietly(bufferedWriter);
                                    throw th;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                            }
                        } catch (IOException e4) {
                            e = e4;
                        }
                }
            }
        };
        this.mHandler = handler;
        handler.post(runnable);
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public final byte[] getSha256Hash(File file) {
        FileHashCache$$ExternalSyntheticLambda0 fileHashCache$$ExternalSyntheticLambda0 = this.mSaveTask;
        byte[] sha256HashFromCache = getSha256HashFromCache(file);
        if (sha256HashFromCache != null) {
            return sha256HashFromCache;
        }
        try {
            byte[] sha256Hash = DigestUtils.getSha256Hash(file);
            ((HashMap) this.mEntries).put(file, new Entry(Os.stat(file.getAbsolutePath()).st_ctime, sha256Hash));
            Handler handler = this.mHandler;
            handler.removeCallbacks(fileHashCache$$ExternalSyntheticLambda0);
            handler.postDelayed(fileHashCache$$ExternalSyntheticLambda0, sSaveDeferredDelayMillis);
            return sha256Hash;
        } catch (ErrnoException e) {
            throw new IOException(e);
        }
    }

    public byte[] getSha256HashFromCache(File file) {
        if (!this.mHandler.getLooper().isCurrentThread()) {
            Slog.wtf("FileHashCache", "Request from invalid thread", new Exception());
            return null;
        }
        Entry entry = (Entry) ((HashMap) this.mEntries).get(file);
        if (entry == null) {
            return null;
        }
        try {
            if (entry.mLastModified == Os.stat(file.getAbsolutePath()).st_ctime) {
                return entry.mSha256Hash;
            }
        } catch (ErrnoException unused) {
        }
        ((HashMap) this.mEntries).remove(file);
        return null;
    }
}
