package com.android.server.storage;

import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.util.SparseArray;

import com.android.internal.os.FuseUnavailableMountException;
import com.android.internal.util.Preconditions;
import com.android.server.AppFuseMountException;
import com.android.server.StorageManagerService;

import libcore.io.IoUtils;

import java.util.concurrent.CountDownLatch;

public class AppFuseBridge implements Runnable {
    public static final String APPFUSE_MOUNT_NAME_TEMPLATE = "/mnt/appfuse/%d_%d";
    public static final String TAG = "AppFuseBridge";
    public final SparseArray mScopes = new SparseArray();
    public long mNativeLoop = native_new();

    public abstract class MountScope implements AutoCloseable {
        public final int mountId;
        public final int uid;
        public final CountDownLatch mMounted = new CountDownLatch(1);
        public boolean mMountResult = false;

        public MountScope(int i, int i2) {
            this.uid = i;
            this.mountId = i2;
        }

        public abstract ParcelFileDescriptor open();
    }

    private native int native_add_bridge(long j, int i, int i2);

    private native void native_delete(long j);

    private native void native_lock();

    private native long native_new();

    private native void native_start_loop(long j);

    private native void native_unlock();

    public final ParcelFileDescriptor addBridge(MountScope mountScope)
            throws FuseUnavailableMountException, AppFuseMountException {
        ParcelFileDescriptor adoptFd;
        native_lock();
        try {
            synchronized (this) {
                Preconditions.checkArgument(this.mScopes.indexOfKey(mountScope.mountId) < 0);
                long j = this.mNativeLoop;
                if (j == 0) {
                    throw new FuseUnavailableMountException(mountScope.mountId);
                }
                int native_add_bridge =
                        native_add_bridge(j, mountScope.mountId, mountScope.open().detachFd());
                if (native_add_bridge == -1) {
                    throw new FuseUnavailableMountException(mountScope.mountId);
                }
                adoptFd = ParcelFileDescriptor.adoptFd(native_add_bridge);
                this.mScopes.put(mountScope.mountId, mountScope);
            }
            native_unlock();
            IoUtils.closeQuietly((AutoCloseable) null);
            return adoptFd;
        } catch (Throwable th) {
            native_unlock();
            IoUtils.closeQuietly(mountScope);
            throw th;
        }
    }

    public final synchronized void onClosed(int i) {
        MountScope mountScope = (MountScope) this.mScopes.get(i);
        if (mountScope != null) {
            if (mountScope.mMounted.getCount() != 0) {
                mountScope.mMountResult = false;
                mountScope.mMounted.countDown();
            }
            IoUtils.closeQuietly(mountScope);
            this.mScopes.remove(i);
        }
    }

    public final synchronized void onMount(int i) {
        MountScope mountScope = (MountScope) this.mScopes.get(i);
        if (mountScope != null && mountScope.mMounted.getCount() != 0) {
            mountScope.mMountResult = true;
            mountScope.mMounted.countDown();
        }
    }

    public final ParcelFileDescriptor openFile(int i, int i2, int i3)
            throws FuseUnavailableMountException, InterruptedException {
        MountScope mountScope;
        synchronized (this) {
            mountScope = (MountScope) this.mScopes.get(i);
            if (mountScope == null) {
                throw new FuseUnavailableMountException(i);
            }
        }
        mountScope.mMounted.await();
        if (!mountScope.mMountResult) {
            throw new FuseUnavailableMountException(i);
        }
        try {
            int translateModePfdToPosix = FileUtils.translateModePfdToPosix(i3);
            StorageManagerService.AppFuseMountScope appFuseMountScope =
                    (StorageManagerService.AppFuseMountScope) mountScope;
            StorageManagerService.this.getClass();
            StorageManagerService.extendWatchdogTimeout("#openFile might be slow");
            try {
                return new ParcelFileDescriptor(
                        StorageManagerService.this.mVold.openAppFuseFile(
                                appFuseMountScope.uid, i, i2, translateModePfdToPosix));
            } catch (Exception e) {
                throw new AppFuseMountException("Failed to open", e);
            }
        } catch (AppFuseMountException unused) {
            throw new FuseUnavailableMountException(i);
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        native_start_loop(this.mNativeLoop);
        synchronized (this) {
            native_delete(this.mNativeLoop);
            this.mNativeLoop = 0L;
        }
    }
}
