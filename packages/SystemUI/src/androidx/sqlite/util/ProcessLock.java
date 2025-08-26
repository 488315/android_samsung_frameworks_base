package androidx.sqlite.util;

import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ProcessLock {
    public static final Companion Companion = new Companion(null);
    public static final Map threadLocksMap = new HashMap();
    public FileChannel lockChannel;
    public final File lockFile;
    public final boolean processLock;
    public final Lock threadLock;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ProcessLock(String str, File file, boolean z) {
        Lock lock;
        this.processLock = z;
        this.lockFile = file != null ? new File(file, AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ".lck")) : null;
        Companion.getClass();
        Map map = threadLocksMap;
        synchronized (map) {
            try {
                HashMap map2 = (HashMap) map;
                Object reentrantLock = map2.get(str);
                if (reentrantLock == null) {
                    reentrantLock = new ReentrantLock();
                    map2.put(str, reentrantLock);
                }
                lock = (Lock) reentrantLock;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.threadLock = lock;
    }

    public final void lock(boolean z) throws IOException {
        this.threadLock.lock();
        if (z) {
            try {
                File file = this.lockFile;
                if (file == null) {
                    throw new IOException("No lock directory was provided.");
                }
                File parentFile = file.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                FileChannel channel = new FileOutputStream(this.lockFile).getChannel();
                channel.lock();
                this.lockChannel = channel;
            } catch (IOException e) {
                this.lockChannel = null;
                Log.w("SupportSQLiteLock", "Unable to grab file lock.", e);
            }
        }
    }

    public final void unlock() {
        try {
            FileChannel fileChannel = this.lockChannel;
            if (fileChannel != null) {
                fileChannel.close();
            }
        } catch (IOException unused) {
        }
        this.threadLock.unlock();
    }
}
