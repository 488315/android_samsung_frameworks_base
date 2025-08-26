package com.samsung.android.photoremasterservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import com.samsung.android.photoremaster.IDirector;
import com.samsung.android.photoremaster.util.LogUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
public class ClientRemasterDirector implements IDirector {
    static final String TAG = "ClientRemasterDirector";
    private boolean mInitialized;
    private volatile boolean mIsServiceDisconnected;
    private final ProgressUpdateClient mProgressUpdateClient;
    private final PhotoRemasterServiceClient mServiceClient;
    private final PhotoRemasterServiceClient mStopCmdClient;
    private final StopLockManager mStopLockManager;

    public ClientRemasterDirector() {
        PhotoRemasterServiceClient photoRemasterServiceClient = new PhotoRemasterServiceClient();
        this.mServiceClient = photoRemasterServiceClient;
        this.mStopCmdClient = new PhotoRemasterServiceClient();
        ProgressUpdateClient progressUpdateClient = new ProgressUpdateClient();
        this.mProgressUpdateClient = progressUpdateClient;
        this.mInitialized = false;
        this.mIsServiceDisconnected = false;
        this.mStopLockManager = new StopLockManager();
        progressUpdateClient.registerObserver(photoRemasterServiceClient.getProgressObserver());
        photoRemasterServiceClient.setServiceConnectionCallback(new ServiceDisconnectionCallback() { // from class: com.samsung.android.photoremasterservice.ClientRemasterDirector$$ExternalSyntheticLambda0
            @Override // com.samsung.android.photoremasterservice.ServiceDisconnectionCallback
            public final void onServiceDisconnected() {
                this.f$0.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        LogUtil.w(TAG, "Received callback onServiceDisconnected");
        this.mIsServiceDisconnected = true;
    }

    static class StopLockManager {
        private final ReentrantLock mStopLock = new ReentrantLock();

        StopLockManager() {
        }

        private void throwException() {
            LogUtil.e(ClientRemasterDirector.TAG, "New requesting during stop processing.");
            throw new RuntimeException("New requesting during stop processing.");
        }

        public void throwExceptionIfLocked() {
            if (this.mStopLock.isLocked()) {
                throwException();
            }
        }

        public void lock() {
            LogUtil.d(ClientRemasterDirector.TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
            this.mStopLock.lock();
        }

        public void tryLock() {
            LogUtil.d(ClientRemasterDirector.TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
            if (this.mStopLock.tryLock()) {
                return;
            }
            throwException();
        }

        public void unlock() {
            LogUtil.d(ClientRemasterDirector.TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
            this.mStopLock.unlock();
        }
    }

    private boolean doInit(Context context) {
        if (this.mInitialized) {
            LogUtil.w(TAG, "Double Initialization!!! init is ignored.");
            return false;
        }
        this.mServiceClient.initServiceCall();
        this.mStopCmdClient.initServiceCall();
        this.mProgressUpdateClient.init();
        this.mServiceClient.setContext(context);
        this.mStopCmdClient.setContext(context);
        this.mProgressUpdateClient.setContext(context);
        return true;
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void init(Context context) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            if (doInit(context)) {
                this.mServiceClient.callService(2, null);
                this.mInitialized = true;
                this.mIsServiceDisconnected = false;
            }
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized boolean tryInit(Context context) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            if (!doInit(context)) {
                return false;
            }
            Bundle bundleCallService = this.mServiceClient.callService(18, null);
            if (bundleCallService == null) {
                LogUtil.e(TAG, "retBundle is null.");
                return false;
            }
            this.mInitialized = bundleCallService.getBoolean(ServiceReturnKey.BOOLEAN);
            this.mIsServiceDisconnected = false;
            this.mStopLockManager.unlock();
            if (!this.mInitialized) {
                LogUtil.i(TAG, "Fail to tryInit. Unbind all.");
                this.mStopLockManager.lock();
                try {
                    unbindClients();
                    this.mStopLockManager.unlock();
                } finally {
                }
            }
            return this.mInitialized;
        } finally {
        }
    }

    private void unbindClients() {
        this.mServiceClient.unbindService();
        this.mServiceClient.deinitServiceCall();
        this.mProgressUpdateClient.unbindService();
        this.mProgressUpdateClient.deinit();
        this.mStopCmdClient.deinitServiceCall();
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void deinit() {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.lock();
        this.mIsServiceDisconnected = false;
        try {
            try {
                confirmInitialized(false);
            } catch (IllegalStateException unused) {
                if (!this.mInitialized) {
                    LogUtil.w(TAG, "Double Deinitialization!!!");
                    this.mStopLockManager.unlock();
                    return;
                }
            }
            this.mInitialized = false;
            this.mServiceClient.callService(3, null);
            unbindClients();
            this.mStopLockManager.unlock();
            LogUtil.d(TAG, "deinit() is done.");
        } catch (Throwable th) {
            this.mStopLockManager.unlock();
            throw th;
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public void stop() {
        PhotoRemasterServiceClient photoRemasterServiceClient;
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.lock();
        this.mIsServiceDisconnected = false;
        try {
            try {
                confirmInitialized(false);
                try {
                    try {
                        this.mStopCmdClient.callService(4, null);
                        photoRemasterServiceClient = this.mStopCmdClient;
                    } catch (IllegalStateException unused) {
                        LogUtil.w(TAG, "Stop is called before initialization!!!");
                        photoRemasterServiceClient = this.mStopCmdClient;
                    }
                    photoRemasterServiceClient.unbindService();
                    this.mStopLockManager.unlock();
                    deinit();
                    LogUtil.d(TAG, "stop() is done.");
                } catch (Throwable th) {
                    this.mStopCmdClient.unbindService();
                    throw th;
                }
            } catch (IllegalStateException unused2) {
                LogUtil.w(TAG, "Stop is called after deinit is done. Stop is ignored.");
                this.mStopLockManager.unlock();
            }
        } catch (Throwable th2) {
            this.mStopLockManager.unlock();
            throw th2;
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized boolean processImage(int i, List<Integer> list) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.throwExceptionIfLocked();
        confirmInitialized();
        Bundle bundle = new Bundle();
        bundle.putInt(ServiceParameterKey.INT_PROCESS_MODE, i);
        bundle.putIntegerArrayList(ServiceParameterKey.ARRAY_LIST_INT, new ArrayList<>(list));
        LogUtil.i(TAG, "processImage(" + i + ", " + list + NavigationBarInflaterView.KEY_CODE_END);
        Bundle bundleCallService = this.mServiceClient.callService(5, bundle);
        if (bundleCallService == null) {
            LogUtil.e(TAG, "retBundle is null.");
            return false;
        }
        return bundleCallService.getBoolean(ServiceReturnKey.BOOLEAN);
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void setUriParam(int i, Uri uri) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle bundle = new Bundle();
            bundle.putInt(ServiceParameterKey.INT_ID, i);
            bundle.putParcelable(ServiceParameterKey.URI_VAL, uri);
            LogUtil.i(TAG, "setUriParam:");
            LogUtil.d(TAG, "arg:" + bundle.getInt(ServiceParameterKey.INT_ID) + ", " + uri);
            this.mServiceClient.callService(8, bundle);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void setBitmapParam(int i, Bitmap bitmap) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle bundle = new Bundle();
            bundle.putInt(ServiceParameterKey.INT_ID, i);
            bundle.putParcelable(ServiceParameterKey.BITMAP_VAL, bitmap.asShared());
            LogUtil.i(TAG, "setBitmapParam:");
            LogUtil.d(TAG, "arg:" + bundle.getInt(ServiceParameterKey.INT_ID) + ", " + bitmap);
            this.mServiceClient.callService(20, bundle);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void setStringParam(int i, String str) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle bundle = new Bundle();
            bundle.putInt(ServiceParameterKey.INT_ID, i);
            bundle.putString(ServiceParameterKey.STRING_VAL, str);
            LogUtil.i(TAG, "setStringParam:");
            LogUtil.d(TAG, "arg:" + bundle.getInt(ServiceParameterKey.INT_ID) + ", " + bundle.getString(ServiceParameterKey.STRING_VAL));
            this.mServiceClient.callService(9, bundle);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void setLongParam(int i, long j) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            if (i == 1006 && j < 0) {
                throw new IndexOutOfBoundsException();
            }
            confirmInitialized();
            Bundle bundle = new Bundle();
            bundle.putInt(ServiceParameterKey.INT_ID, i);
            bundle.putLong(ServiceParameterKey.LONG_VAL, j);
            LogUtil.i(TAG, "setLongParam:");
            LogUtil.d(TAG, "arg:" + bundle.getInt(ServiceParameterKey.INT_ID) + ", " + bundle.getLong(ServiceParameterKey.LONG_VAL));
            this.mServiceClient.callService(10, bundle);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized String getStringParam(int i) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle bundle = new Bundle();
            bundle.putInt(ServiceParameterKey.INT_ID, i);
            Bundle bundleCallService = this.mServiceClient.callService(11, bundle);
            if (bundleCallService == null) {
                throwEmptyBundleException();
                return "";
            }
            LogUtil.d(TAG, "ret Value: " + bundleCallService.getString(ServiceReturnKey.STRING));
            return bundleCallService.getString(ServiceReturnKey.STRING);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized int getIntParam(int i) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle bundle = new Bundle();
            bundle.putInt(ServiceParameterKey.INT_ID, i);
            LogUtil.d(TAG, "arg:" + bundle.getInt(ServiceParameterKey.INT_ID));
            Bundle bundleCallService = this.mServiceClient.callService(12, bundle);
            if (bundleCallService == null) {
                throwEmptyBundleException();
                return -1;
            }
            LogUtil.d(TAG, "ret Value: " + bundleCallService.getInt(ServiceReturnKey.INT));
            return bundleCallService.getInt(ServiceReturnKey.INT);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized long getLongParam(int i) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle bundle = new Bundle();
            bundle.putInt(ServiceParameterKey.INT_ID, i);
            Bundle bundleCallService = this.mServiceClient.callService(13, bundle);
            if (bundleCallService == null) {
                throwEmptyBundleException();
                return -1L;
            }
            LogUtil.d(TAG, "ret Value: " + bundleCallService.getLong(ServiceReturnKey.LONG));
            return bundleCallService.getLong(ServiceReturnKey.LONG);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized Bitmap getBitmapParam(int i) {
        Bitmap bitmap;
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle bundle = new Bundle();
            bundle.putInt(ServiceParameterKey.INT_ID, i);
            Bundle bundleCallService = this.mServiceClient.callService(19, bundle);
            if (bundleCallService == null) {
                LogUtil.e(TAG, "Return bundle is empty.");
                throw new IllegalStateException();
            }
            bitmap = (Bitmap) bundleCallService.getParcelable(ServiceReturnKey.BITMAP, Bitmap.class);
            LogUtil.d(TAG, "ret Value: " + bitmap);
        } finally {
            this.mStopLockManager.unlock();
        }
        return bitmap;
    }

    private void confirmInitialized() {
        confirmInitialized(true);
    }

    private void confirmInitialized(boolean z) {
        if (!this.mInitialized) {
            if (z) {
                LogUtil.e(TAG, "Access before initialization.");
            } else {
                LogUtil.i(TAG, "Access before initialization.");
            }
            throw new IllegalStateException();
        }
        if (this.mIsServiceDisconnected) {
            LogUtil.e(TAG, "Service was disconnected. Throwing IllegalStateException.");
            throw new IllegalStateException("Service was disconnected. Throwing IllegalStateException.");
        }
    }

    private void throwEmptyBundleException() {
        LogUtil.e(TAG, "Return bundle is empty.");
        throw new IllegalStateException();
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void setProgressUpdateListener(IDirector.ProgressUpdateListener progressUpdateListener) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            this.mProgressUpdateClient.setProgressUpdateListener(progressUpdateListener);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized String getFocusRoi(String str, String str2) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle bundle = new Bundle();
            bundle.putString(ServiceParameterKey.ORIGINAL_IMAGE_FOCUS_ROI, str);
            bundle.putString(ServiceParameterKey.REMASTERED_IMAGE_FOCUS_ROI, str2);
            LogUtil.d(TAG, "original image for getFocusRoi(): " + bundle.getString(ServiceParameterKey.ORIGINAL_IMAGE_FOCUS_ROI));
            LogUtil.d(TAG, "remastered image for getFocusRoi(): " + bundle.getString(ServiceParameterKey.REMASTERED_IMAGE_FOCUS_ROI));
            Bundle bundleCallService = this.mServiceClient.callService(17, bundle);
            if (bundleCallService == null) {
                throwEmptyBundleException();
                return "";
            }
            LogUtil.d(TAG, "ret Value: " + bundleCallService.getString(ServiceReturnKey.STRING));
            return bundleCallService.getString(ServiceReturnKey.STRING);
        } finally {
            this.mStopLockManager.unlock();
        }
    }
}
