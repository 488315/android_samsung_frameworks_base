package com.android.internal.os;

import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.ProxyFileDescriptorCallback;
import android.system.ErrnoException;
import android.system.OsConstants;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.util.Preconditions;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes5.dex */
public class FuseAppLoop implements Handler.Callback {
    private static final int ARGS_POOL_SIZE = 50;
    private static final int FUSE_FSYNC = 20;
    private static final int FUSE_GETATTR = 3;
    private static final int FUSE_LOOKUP = 1;
    private static final int FUSE_MAX_WRITE = 131072;
    private static final int FUSE_OK = 0;
    private static final int FUSE_OPEN = 14;
    private static final int FUSE_READ = 15;
    private static final int FUSE_RELEASE = 18;
    private static final int FUSE_WRITE = 16;
    private static final int MIN_INODE = 2;
    public static final int ROOT_INODE = 1;
    private long mInstance;
    private final int mMountPointId;
    private final Thread mThread;
    private static final String TAG = "FuseAppLoop";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final ThreadFactory sDefaultThreadFactory = new ThreadFactory() { // from class: com.android.internal.os.FuseAppLoop.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, FuseAppLoop.TAG);
        }
    };
    private final Object mLock = new Object();
    private final SparseArray<CallbackEntry> mCallbackMap = new SparseArray<>();
    private final BytesMap mBytesMap = new BytesMap();
    private final LinkedList<Args> mArgsPool = new LinkedList<>();
    private int mNextInode = 2;

    public static class UnmountedException extends Exception {
    }

    native void native_delete(long j);

    native long native_new(int i);

    native void native_replyGetAttr(long j, long j2, long j3, long j4);

    native void native_replyLookup(long j, long j2, long j3, long j4);

    native void native_replyOpen(long j, long j2, long j3);

    native void native_replyRead(long j, long j2, int i, byte[] bArr);

    native void native_replySimple(long j, long j2, int i);

    native void native_replyWrite(long j, long j2, int i);

    native void native_start(long j);

    public FuseAppLoop(int i, ParcelFileDescriptor parcelFileDescriptor, ThreadFactory threadFactory) {
        this.mMountPointId = i;
        threadFactory = threadFactory == null ? sDefaultThreadFactory : threadFactory;
        this.mInstance = native_new(parcelFileDescriptor.detachFd());
        Thread threadNewThread = threadFactory.newThread(new Runnable() { // from class: com.android.internal.os.FuseAppLoop$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        });
        this.mThread = threadNewThread;
        threadNewThread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        native_start(this.mInstance);
        synchronized (this.mLock) {
            native_delete(this.mInstance);
            this.mInstance = 0L;
            this.mBytesMap.clear();
        }
    }

    public int registerCallback(ProxyFileDescriptorCallback proxyFileDescriptorCallback, Handler handler) throws FuseUnavailableMountException {
        int i;
        synchronized (this.mLock) {
            Objects.requireNonNull(proxyFileDescriptorCallback);
            Objects.requireNonNull(handler);
            Preconditions.checkState(this.mCallbackMap.size() < 2147483645, "Too many opened files.");
            Preconditions.checkArgument(Thread.currentThread().getId() != handler.getLooper().getThread().getId(), "Handler must be different from the current thread");
            if (this.mInstance == 0) {
                throw new FuseUnavailableMountException(this.mMountPointId);
            }
            do {
                i = this.mNextInode;
                int i2 = i + 1;
                this.mNextInode = i2;
                if (i2 < 0) {
                    this.mNextInode = 2;
                }
            } while (this.mCallbackMap.get(i) != null);
            this.mCallbackMap.put(i, new CallbackEntry(proxyFileDescriptorCallback, new Handler(handler.getLooper(), this)));
        }
        return i;
    }

    public void unregisterCallback(int i) {
        synchronized (this.mLock) {
            this.mCallbackMap.remove(i);
        }
    }

    public int getMountPointId() {
        return this.mMountPointId;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        boolean z;
        int i;
        Args args = (Args) message.obj;
        CallbackEntry callbackEntry = args.entry;
        long j = args.inode;
        long j2 = args.unique;
        int i2 = args.size;
        long j3 = args.offset;
        byte[] bArr = args.data;
        try {
            i = message.what;
        } catch (Exception e) {
            e = e;
            z = true;
        }
        try {
            if (i != 1) {
                z = true;
                if (i == 3) {
                    long jOnGetSize = callbackEntry.callback.onGetSize();
                    synchronized (this.mLock) {
                        long j4 = this.mInstance;
                        if (j4 != 0) {
                            native_replyGetAttr(j4, j2, j, jOnGetSize);
                        }
                        recycleLocked(args);
                    }
                } else if (i == 18) {
                    callbackEntry.callback.onRelease();
                    synchronized (this.mLock) {
                        long j5 = this.mInstance;
                        if (j5 != 0) {
                            native_replySimple(j5, j2, 0);
                        }
                        this.mCallbackMap.remove(checkInode(j));
                        this.mBytesMap.stopUsing(j);
                        recycleLocked(args);
                    }
                } else if (i == 20) {
                    callbackEntry.callback.onFsync();
                    synchronized (this.mLock) {
                        long j6 = this.mInstance;
                        if (j6 != 0) {
                            native_replySimple(j6, j2, 0);
                        }
                        recycleLocked(args);
                    }
                } else if (i == 15) {
                    int iOnRead = callbackEntry.callback.onRead(j3, i2, bArr);
                    synchronized (this.mLock) {
                        long j7 = this.mInstance;
                        if (j7 != 0) {
                            native_replyRead(j7, j2, iOnRead, bArr);
                        }
                        recycleLocked(args);
                    }
                } else if (i == 16) {
                    int iOnWrite = callbackEntry.callback.onWrite(j3, i2, bArr);
                    synchronized (this.mLock) {
                        long j8 = this.mInstance;
                        if (j8 != 0) {
                            native_replyWrite(j8, j2, iOnWrite);
                        }
                        recycleLocked(args);
                    }
                } else {
                    throw new IllegalArgumentException("Unknown FUSE command: " + message.what);
                }
            } else {
                z = true;
                long jOnGetSize2 = callbackEntry.callback.onGetSize();
                synchronized (this.mLock) {
                    long j9 = this.mInstance;
                    if (j9 != 0) {
                        native_replyLookup(j9, j2, j, jOnGetSize2);
                    }
                    recycleLocked(args);
                }
            }
        } catch (Exception e2) {
            e = e2;
            synchronized (this.mLock) {
                Log.e(TAG, "", e);
                replySimpleLocked(j2, getError(e));
                recycleLocked(args);
            }
            return z;
        }
        return z;
    }

    private void onCommand(int i, long j, long j2, long j3, int i2, byte[] bArr) {
        Args argsPop;
        synchronized (this.mLock) {
            try {
                if (this.mArgsPool.size() == 0) {
                    argsPop = new Args();
                } else {
                    argsPop = this.mArgsPool.pop();
                }
                argsPop.unique = j;
                argsPop.inode = j2;
                argsPop.offset = j3;
                argsPop.size = i2;
                argsPop.data = bArr;
                argsPop.entry = getCallbackEntryOrThrowLocked(j2);
            } catch (Exception e) {
                replySimpleLocked(j, getError(e));
            }
            if (!argsPop.entry.handler.sendMessage(Message.obtain(argsPop.entry.handler, i, 0, 0, argsPop))) {
                throw new ErrnoException("onCommand", OsConstants.EBADF);
            }
        }
    }

    private byte[] onOpen(long j, long j2) {
        CallbackEntry callbackEntryOrThrowLocked;
        synchronized (this.mLock) {
            try {
                callbackEntryOrThrowLocked = getCallbackEntryOrThrowLocked(j2);
                try {
                } catch (ErrnoException e) {
                    e = e;
                    replySimpleLocked(j, getError(e));
                    return null;
                }
            } catch (ErrnoException e2) {
                e = e2;
            }
            if (callbackEntryOrThrowLocked.opened) {
                throw new ErrnoException("onOpen", OsConstants.EMFILE);
            }
            long j3 = this.mInstance;
            if (j3 != 0) {
                native_replyOpen(j3, j, j2);
                callbackEntryOrThrowLocked.opened = true;
                return this.mBytesMap.startUsing(j2);
            }
            return null;
        }
    }

    private static int getError(Exception exc) {
        int i;
        return (!(exc instanceof ErrnoException) || (i = ((ErrnoException) exc).errno) == OsConstants.ENOSYS) ? -OsConstants.EBADF : -i;
    }

    private CallbackEntry getCallbackEntryOrThrowLocked(long j) throws ErrnoException {
        CallbackEntry callbackEntry = this.mCallbackMap.get(checkInode(j));
        if (callbackEntry != null) {
            return callbackEntry;
        }
        throw new ErrnoException("getCallbackEntryOrThrowLocked", OsConstants.ENOENT);
    }

    private void recycleLocked(Args args) {
        if (this.mArgsPool.size() < 50) {
            this.mArgsPool.add(args);
        }
    }

    private void replySimpleLocked(long j, int i) {
        long j2 = this.mInstance;
        if (j2 != 0) {
            native_replySimple(j2, j, i);
        }
    }

    private static int checkInode(long j) {
        Preconditions.checkArgumentInRange(j, 2L, 2147483647L, "checkInode");
        return (int) j;
    }

    private static class CallbackEntry {
        final ProxyFileDescriptorCallback callback;
        final Handler handler;
        boolean opened;

        CallbackEntry(ProxyFileDescriptorCallback proxyFileDescriptorCallback, Handler handler) {
            this.callback = (ProxyFileDescriptorCallback) Objects.requireNonNull(proxyFileDescriptorCallback);
            this.handler = (Handler) Objects.requireNonNull(handler);
        }

        long getThreadId() {
            return this.handler.getLooper().getThread().getId();
        }
    }

    private static class BytesMapEntry {
        byte[] bytes;
        int counter;

        private BytesMapEntry() {
            this.counter = 0;
            this.bytes = new byte[131072];
        }
    }

    private static class BytesMap {
        final Map<Long, BytesMapEntry> mEntries;

        private BytesMap() {
            this.mEntries = new HashMap();
        }

        byte[] startUsing(long j) {
            BytesMapEntry bytesMapEntry = this.mEntries.get(Long.valueOf(j));
            if (bytesMapEntry == null) {
                bytesMapEntry = new BytesMapEntry();
                this.mEntries.put(Long.valueOf(j), bytesMapEntry);
            }
            bytesMapEntry.counter++;
            return bytesMapEntry.bytes;
        }

        void stopUsing(long j) {
            BytesMapEntry bytesMapEntry = this.mEntries.get(Long.valueOf(j));
            Objects.requireNonNull(bytesMapEntry);
            bytesMapEntry.counter--;
            if (bytesMapEntry.counter <= 0) {
                this.mEntries.remove(Long.valueOf(j));
            }
        }

        void clear() {
            this.mEntries.clear();
        }
    }

    private static class Args {
        byte[] data;
        CallbackEntry entry;
        long inode;
        long offset;
        int size;
        long unique;

        private Args() {
        }
    }
}
