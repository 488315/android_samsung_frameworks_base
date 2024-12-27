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
    private final PhotoRemasterServiceClient mServiceClient = new PhotoRemasterServiceClient();
    private final PhotoRemasterServiceClient mStopCmdClient = new PhotoRemasterServiceClient();
    private final ProgressUpdateClient mProgressUpdateClient = new ProgressUpdateClient();
    private boolean mInitialized = false;
    private final StopLockManager mStopLockManager = new StopLockManager();

    public ClientRemasterDirector() {
        this.mProgressUpdateClient.registerObserver(this.mServiceClient.getProgressObserver());
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
            if (!this.mStopLock.tryLock()) {
                throwException();
            }
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
            Bundle retBundle = this.mServiceClient.callService(18, null);
            if (retBundle == null) {
                LogUtil.e(TAG, "retBundle is null.");
                return false;
            }
            this.mInitialized = retBundle.getBoolean(ServiceReturnKey.BOOLEAN);
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
        try {
            try {
                confirmInitialized(false);
            } catch (IllegalStateException e) {
                if (!this.mInitialized) {
                    LogUtil.w(TAG, "Double Deinitialization!!!");
                    this.mStopLockManager.unlock();
                    return;
                }
            } catch (Throwable th) {
                th = th;
                this.mStopLockManager.unlock();
                throw th;
            }
            this.mInitialized = false;
            this.mServiceClient.callService(3, null);
            unbindClients();
            this.mStopLockManager.unlock();
            LogUtil.d(TAG, "deinit() is done.");
        } catch (Throwable th2) {
            th = th2;
            this.mStopLockManager.unlock();
            throw th;
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public void stop() {
        PhotoRemasterServiceClient photoRemasterServiceClient;
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.lock();
        try {
            try {
                confirmInitialized(false);
                try {
                    try {
                        this.mStopCmdClient.callService(4, null);
                        photoRemasterServiceClient = this.mStopCmdClient;
                    } catch (Throwable th) {
                        this.mStopCmdClient.unbindService();
                        throw th;
                    }
                } catch (IllegalStateException e) {
                    LogUtil.w(TAG, "Stop is called before initialization!!!");
                    photoRemasterServiceClient = this.mStopCmdClient;
                }
                photoRemasterServiceClient.unbindService();
                this.mStopLockManager.unlock();
                deinit();
                LogUtil.d(TAG, "stop() is done.");
            } catch (IllegalStateException e2) {
                LogUtil.w(TAG, "Stop is called after deinit is done. Stop is ignored.");
                this.mStopLockManager.unlock();
            }
        } catch (Throwable th2) {
            this.mStopLockManager.unlock();
            throw th2;
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized boolean processImage(int processMode, List<Integer> enhanceModes) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.throwExceptionIfLocked();
        confirmInitialized();
        Bundle argBundle = new Bundle();
        argBundle.putInt(ServiceParameterKey.INT_PROCESS_MODE, processMode);
        ArrayList<Integer> arrayList = new ArrayList<>(enhanceModes);
        argBundle.putIntegerArrayList(ServiceParameterKey.ARRAY_LIST_INT, arrayList);
        LogUtil.i(TAG, "processImage(" + processMode + ", " + enhanceModes + NavigationBarInflaterView.KEY_CODE_END);
        Bundle retBundle = this.mServiceClient.callService(5, argBundle);
        if (retBundle == null) {
            LogUtil.e(TAG, "retBundle is null.");
            return false;
        }
        return retBundle.getBoolean(ServiceReturnKey.BOOLEAN);
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void setUriParam(int id, Uri value) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle argBundle = new Bundle();
            argBundle.putInt(ServiceParameterKey.INT_ID, id);
            argBundle.putParcelable(ServiceParameterKey.URI_VAL, value);
            LogUtil.i(TAG, "setUriParam:");
            LogUtil.d(TAG, "arg:" + argBundle.getInt(ServiceParameterKey.INT_ID) + ", " + value);
            this.mServiceClient.callService(8, argBundle);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void setBitmapParam(int id, Bitmap value) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle argBundle = new Bundle();
            argBundle.putInt(ServiceParameterKey.INT_ID, id);
            argBundle.putParcelable(ServiceParameterKey.BITMAP_VAL, value.asShared());
            LogUtil.i(TAG, "setBitmapParam:");
            LogUtil.d(TAG, "arg:" + argBundle.getInt(ServiceParameterKey.INT_ID) + ", " + value);
            this.mServiceClient.callService(20, argBundle);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void setStringParam(int id, String value) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle argBundle = new Bundle();
            argBundle.putInt(ServiceParameterKey.INT_ID, id);
            argBundle.putString(ServiceParameterKey.STRING_VAL, value);
            LogUtil.i(TAG, "setStringParam:");
            LogUtil.d(TAG, "arg:" + argBundle.getInt(ServiceParameterKey.INT_ID) + ", " + argBundle.getString(ServiceParameterKey.STRING_VAL));
            this.mServiceClient.callService(9, argBundle);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void setLongParam(int id, long value) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        if (id == 1006 && value < 0) {
            try {
                throw new IndexOutOfBoundsException();
            } catch (Throwable th) {
                th = th;
            }
        } else {
            try {
                confirmInitialized();
                Bundle argBundle = new Bundle();
                argBundle.putInt(ServiceParameterKey.INT_ID, id);
                argBundle.putLong(ServiceParameterKey.LONG_VAL, value);
                LogUtil.i(TAG, "setLongParam:");
                LogUtil.d(TAG, "arg:" + argBundle.getInt(ServiceParameterKey.INT_ID) + ", " + argBundle.getLong(ServiceParameterKey.LONG_VAL));
                this.mServiceClient.callService(10, argBundle);
                this.mStopLockManager.unlock();
            } catch (Throwable th2) {
                th = th2;
            }
        }
        this.mStopLockManager.unlock();
        throw th;
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized String getStringParam(int id) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle argBundle = new Bundle();
            argBundle.putInt(ServiceParameterKey.INT_ID, id);
            Bundle retBundle = this.mServiceClient.callService(11, argBundle);
            if (retBundle == null) {
                throwEmptyBundleException();
                return "";
            }
            LogUtil.d(TAG, "ret Value: " + retBundle.getString(ServiceReturnKey.STRING));
            return retBundle.getString(ServiceReturnKey.STRING);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized int getIntParam(int id) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle argBundle = new Bundle();
            argBundle.putInt(ServiceParameterKey.INT_ID, id);
            LogUtil.d(TAG, "arg:" + argBundle.getInt(ServiceParameterKey.INT_ID));
            Bundle retBundle = this.mServiceClient.callService(12, argBundle);
            if (retBundle == null) {
                throwEmptyBundleException();
                return -1;
            }
            LogUtil.d(TAG, "ret Value: " + retBundle.getInt(ServiceReturnKey.INT));
            return retBundle.getInt(ServiceReturnKey.INT);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized long getLongParam(int id) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle argBundle = new Bundle();
            argBundle.putInt(ServiceParameterKey.INT_ID, id);
            Bundle retBundle = this.mServiceClient.callService(13, argBundle);
            if (retBundle == null) {
                throwEmptyBundleException();
                return -1L;
            }
            LogUtil.d(TAG, "ret Value: " + retBundle.getLong(ServiceReturnKey.LONG));
            return retBundle.getLong(ServiceReturnKey.LONG);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized Bitmap getBitmapParam(int id) {
        Bitmap returnBitmap;
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle argBundle = new Bundle();
            argBundle.putInt(ServiceParameterKey.INT_ID, id);
            Bundle retBundle = this.mServiceClient.callService(19, argBundle);
            if (retBundle == null) {
                LogUtil.e(TAG, "Return bundle is empty.");
                throw new IllegalStateException();
            }
            try {
                returnBitmap = (Bitmap) retBundle.getParcelable(ServiceReturnKey.BITMAP, Bitmap.class);
                LogUtil.d(TAG, "ret Value: " + returnBitmap);
                this.mStopLockManager.unlock();
            } catch (Throwable th) {
                th = th;
                this.mStopLockManager.unlock();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        return returnBitmap;
    }

    private void confirmInitialized() {
        confirmInitialized(true);
    }

    private void confirmInitialized(boolean isLeavingLog) {
        if (!this.mInitialized) {
            if (isLeavingLog) {
                LogUtil.e(TAG, "Access before initialization.");
            } else {
                LogUtil.i(TAG, "Access before initialization.");
            }
            throw new IllegalStateException();
        }
    }

    private void throwEmptyBundleException() {
        LogUtil.e(TAG, "Return bundle is empty.");
        throw new IllegalStateException();
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized void setProgressUpdateListener(IDirector.ProgressUpdateListener listener) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            this.mProgressUpdateClient.setProgressUpdateListener(listener);
        } finally {
            this.mStopLockManager.unlock();
        }
    }

    @Override // com.samsung.android.photoremaster.IDirector
    public synchronized String getFocusRoi(String originalImage, String remasteredImage) {
        LogUtil.d(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        this.mStopLockManager.tryLock();
        try {
            confirmInitialized();
            Bundle argBundle = new Bundle();
            argBundle.putString(ServiceParameterKey.ORIGINAL_IMAGE_FOCUS_ROI, originalImage);
            argBundle.putString(ServiceParameterKey.REMASTERED_IMAGE_FOCUS_ROI, remasteredImage);
            LogUtil.d(TAG, "original image for getFocusRoi(): " + argBundle.getString(ServiceParameterKey.ORIGINAL_IMAGE_FOCUS_ROI));
            LogUtil.d(TAG, "remastered image for getFocusRoi(): " + argBundle.getString(ServiceParameterKey.REMASTERED_IMAGE_FOCUS_ROI));
            Bundle retBundle = this.mServiceClient.callService(17, argBundle);
            if (retBundle == null) {
                throwEmptyBundleException();
                return "";
            }
            LogUtil.d(TAG, "ret Value: " + retBundle.getString(ServiceReturnKey.STRING));
            return retBundle.getString(ServiceReturnKey.STRING);
        } finally {
            this.mStopLockManager.unlock();
        }
    }
}
