package com.samsung.android.photoremasterservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.samsung.android.photoremaster.util.LogUtil;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes6.dex */
public class PhotoRemasterServiceClient {
    static final long RESPONSE_TIME_OUT_SECOND_DEFAULT = 180;
    static final long RESPONSE_TIME_OUT_SECOND_FOR_STOP = 19;
    static final String SERVICE_CLASS = "com.samsung.android.photoremasterservice.PhotoRemasterService";
    static final String SERVICE_PACKAGE = "com.samsung.android.photoremasterservice";
    static final String TAG = "PhotoRemasterServiceClient";
    private Context mContext;
    private HandlerThread mHandlerThread;
    private Messenger mIncomingMessenger;
    private Messenger mServiceMessenger;
    private boolean mIsBound = false;
    private boolean mDisconnectionRequested = false;
    private CompletableFuture<Bundle> mServiceReturnValue = new CompletableFuture<>();
    private final ProgressObserver mProgressObserver = new ProgressObserver();
    private volatile ServiceDisconnectionCallback mServiceDisconnectionCallback = null;
    private final ServiceConnection mConnection = new ServiceConnection() { // from class: com.samsung.android.photoremasterservice.PhotoRemasterServiceClient.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PhotoRemasterServiceClient.this.mServiceMessenger = new Messenger(iBinder);
            PhotoRemasterServiceClient.this.mServiceReturnValue.complete(null);
            LogUtil.i(PhotoRemasterServiceClient.TAG, "Service is connected(attached).");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.i(PhotoRemasterServiceClient.TAG, "Service is disconnected.");
            PhotoRemasterServiceClient.this.mServiceMessenger = null;
            if (!PhotoRemasterServiceClient.this.mDisconnectionRequested) {
                LogUtil.e(PhotoRemasterServiceClient.TAG, "Unexpected disconnection with PhotoRemaster Service! CancellationException will be thrown!");
                PhotoRemasterServiceClient.this.mServiceReturnValue.cancel(true);
            }
            if (PhotoRemasterServiceClient.this.mServiceDisconnectionCallback != null) {
                PhotoRemasterServiceClient.this.mServiceDisconnectionCallback.onServiceDisconnected();
            }
        }
    };

    public ProgressObserver getProgressObserver() {
        return this.mProgressObserver;
    }

    public synchronized void initServiceCall() {
        HandlerThread handlerThread = new HandlerThread("PhotoRemasterService Looper");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        Handler handler = new Handler(this.mHandlerThread.getLooper()) { // from class: com.samsung.android.photoremasterservice.PhotoRemasterServiceClient.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == -1) {
                    PhotoRemasterServiceClient.this.mServiceReturnValue.complete(message.getData());
                    LogUtil.e(PhotoRemasterServiceClient.TAG, "Received exception : " + message.what);
                    return;
                }
                if (i == 0) {
                    PhotoRemasterServiceClient.this.mServiceReturnValue.complete(message.getData());
                    LogUtil.d(PhotoRemasterServiceClient.TAG, "Received: " + message.what);
                    return;
                }
                LogUtil.e(PhotoRemasterServiceClient.TAG, "Wrong Message is received." + message.what);
                super.handleMessage(message);
            }
        };
        this.mServiceReturnValue = new CompletableFuture<>();
        this.mIncomingMessenger = new Messenger(handler);
    }

    public synchronized void deinitServiceCall() {
        this.mHandlerThread.quit();
    }

    public synchronized void setContext(Context context) {
        LogUtil.d(TAG, "Context is set.");
        this.mContext = context;
    }

    public synchronized void bind() {
        LogUtil.i(TAG, "Try Binding...");
        if (this.mContext == null) {
            LogUtil.e(TAG, "Context is null.");
            return;
        }
        if (this.mIsBound) {
            LogUtil.i(TAG, "Already Bound.");
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(SERVICE_PACKAGE, SERVICE_CLASS);
        this.mDisconnectionRequested = false;
        this.mContext.bindService(intent, 1, Executors.newSingleThreadExecutor(), this.mConnection);
        getServiceReturnValue();
        LogUtil.i(TAG, "Service Binding is finished.");
        this.mIsBound = true;
    }

    private static boolean isExceptionContained(Bundle bundle) {
        return (bundle == null || bundle.getSerializable("exception", Exception.class) == null) ? false : true;
    }

    private Bundle getServiceReturnValue() {
        return getServiceReturnValue(RESPONSE_TIME_OUT_SECOND_DEFAULT);
    }

    private Bundle doGetServiceReturnValue(long j) throws TimeoutException, ExecutionException, InterruptedException {
        while (true) {
            try {
                return this.mServiceReturnValue.get(j, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                if (this.mProgressObserver.wasUpdateAndClear()) {
                    LogUtil.i(TAG, "Wait again service return value because there has been update message.");
                } else {
                    LogUtil.e(TAG, "Time Out! There is no response from service.");
                    throw e;
                }
            }
        }
    }

    private Bundle getServiceReturnValue(long j) {
        try {
            Bundle doGetServiceReturnValue = doGetServiceReturnValue(j);
            this.mServiceReturnValue = new CompletableFuture<>();
            if (!isExceptionContained(doGetServiceReturnValue)) {
                return doGetServiceReturnValue;
            }
            LogUtil.e(TAG, "Exception is received from service.");
            Exception exc = (Exception) doGetServiceReturnValue.getSerializable("exception", Exception.class);
            if (exc == null) {
                throw new RuntimeException("Unknown exception form service-server.");
            }
            RuntimeException runtimeException = new RuntimeException(exc + " in PhotoRemaster Service");
            runtimeException.setStackTrace(exc.getStackTrace());
            runtimeException.printStackTrace();
            throw runtimeException;
        } catch (InterruptedException | ExecutionException e) {
            LogUtil.e(TAG, e.toString());
            e.printStackTrace();
            this.mServiceReturnValue = new CompletableFuture<>();
            return null;
        } catch (TimeoutException e2) {
            LogUtil.e(TAG, e2 + " is occurred at getServiceReturnValue()");
            e2.printStackTrace();
            this.mServiceReturnValue = new CompletableFuture<>();
            throw new RuntimeException(e2.toString());
        }
    }

    public synchronized void unbindService() {
        LogUtil.i(TAG, "Try Unbinding...");
        Context context = this.mContext;
        if (context == null) {
            LogUtil.e(TAG, "Context is null.");
            return;
        }
        if (!this.mIsBound) {
            LogUtil.w(TAG, "Already unbound!!!");
            return;
        }
        this.mDisconnectionRequested = true;
        context.unbindService(this.mConnection);
        this.mIsBound = false;
        LogUtil.i(TAG, "Service is unbound.");
    }

    public synchronized Bundle callService(final int i, final Bundle bundle) {
        LogUtil.i(TAG, "Call Service. message: " + i);
        if (!this.mIsBound) {
            bind();
            if (!this.mIsBound) {
                LogUtil.e(TAG, "Service is not bounded!");
                return null;
            }
        }
        if (this.mServiceMessenger == null) {
            LogUtil.e(TAG, "ServiceMessenger is null!");
            return null;
        }
        new Thread(new Runnable() { // from class: com.samsung.android.photoremasterservice.PhotoRemasterServiceClient.1ServiceCallRunnable
            @Override // java.lang.Runnable
            public void run() {
                try {
                    LogUtil.d(PhotoRemasterServiceClient.TAG, "Send message to service...");
                    Message obtain = Message.obtain((Handler) null, i);
                    obtain.setData(bundle);
                    obtain.replyTo = PhotoRemasterServiceClient.this.mIncomingMessenger;
                    PhotoRemasterServiceClient.this.mServiceMessenger.send(obtain);
                } catch (RemoteException e) {
                    if (PhotoRemasterServiceClient.this.mIncomingMessenger.getBinder() == null) {
                        LogUtil.e(PhotoRemasterServiceClient.TAG, "mIncomingMessenger is null!");
                    }
                    LogUtil.e(PhotoRemasterServiceClient.TAG, "Exception!!!! " + e);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }).start();
        LogUtil.d(TAG, "Service called!");
        LogUtil.d(TAG, "Start waiting the return value.");
        Bundle serviceReturnValue = i == 4 ? getServiceReturnValue(19L) : getServiceReturnValue();
        if (serviceReturnValue == null) {
            LogUtil.e(TAG, "Service return bundle is null!");
            throw new RuntimeException("Service return bundle is null!");
        }
        LogUtil.i(TAG, "Service return value: " + serviceReturnValue);
        return serviceReturnValue;
    }

    public synchronized void setServiceConnectionCallback(ServiceDisconnectionCallback serviceDisconnectionCallback) {
        this.mServiceDisconnectionCallback = serviceDisconnectionCallback;
    }
}
