package com.samsung.android.photoremasterservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.HandlerThread;
import android.p009os.IBinder;
import android.p009os.Message;
import android.p009os.Messenger;
import android.p009os.RemoteException;
import com.samsung.android.photoremaster.IDirector;
import com.samsung.android.photoremaster.util.LogUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class ProgressUpdateClient {
    private static final long BIND_TIME_OUT_SECOND = 25;
    private static final String SERVICE_CLASS = "com.samsung.android.photoremasterservice.PhotoRemasterService";
    private static final String SERVICE_PACKAGE = "com.samsung.android.photoremasterservice";
    private static final String TAG = "ProgressUpdateClient";
    private Context mContext;
    private HandlerThread mHandlerThread;
    private Messenger mIncomingMessenger;
    private IDirector.ProgressUpdateListener mProgressUpdateListener;
    private Messenger mServiceMessenger;
    private boolean mIsBound = false;
    private CompletableFuture<Bundle> mServiceReturnValue = new CompletableFuture<>();
    private final List<ProgressObserver> mProgressObservers = new ArrayList();
    private final ServiceConnection mConnection = new ServiceConnection() { // from class: com.samsung.android.photoremasterservice.ProgressUpdateClient.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            ProgressUpdateClient.this.mServiceMessenger = new Messenger(service);
            ProgressUpdateClient.this.mServiceReturnValue.complete(null);
            LogUtil.m294i(ProgressUpdateClient.TAG, "Service is connected(attached).");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            LogUtil.m294i(ProgressUpdateClient.TAG, "Service is disconnected.");
            ProgressUpdateClient.this.mServiceMessenger = null;
        }
    };

    public void registerObserver(ProgressObserver progressObserver) {
        this.mProgressObservers.add(progressObserver);
    }

    public synchronized void init() {
        HandlerThread handlerThread = new HandlerThread("PhotoRemasterService Looper");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mIncomingMessenger = new Messenger(new Handler(this.mHandlerThread.getLooper()) { // from class: com.samsung.android.photoremasterservice.ProgressUpdateClient.1
            @Override // android.p009os.Handler
            public void handleMessage(Message msg) {
                if (ProgressUpdateClient.this.mProgressUpdateListener == null) {
                    LogUtil.m291d(ProgressUpdateClient.TAG, "progressUpdate is received. But listener is unregistered. So received data is ignored.");
                }
                Bundle bundle = msg.getData();
                switch (msg.what) {
                    case -1:
                        ProgressUpdateClient.this.mServiceReturnValue.complete(msg.getData());
                        LogUtil.m292e(ProgressUpdateClient.TAG, "Received exception : " + msg.what);
                        break;
                    case 0:
                        ProgressUpdateClient.this.mServiceReturnValue.complete(msg.getData());
                        LogUtil.m291d(ProgressUpdateClient.TAG, "Received MSG_RET");
                        break;
                    case 15:
                        String metadata = bundle.getString("String");
                        ProgressUpdateClient.this.mProgressUpdateListener.onUpdateMetadata(metadata);
                        LogUtil.m291d(ProgressUpdateClient.TAG, "Received OnUpdateMetaData");
                        break;
                    case 16:
                        double percent = bundle.getDouble(ServiceReturnKey.DOUBLE);
                        int currentImageIndex = bundle.getIntArray(ServiceReturnKey.INT_ARRAY)[0];
                        int totalImageCount = bundle.getIntArray(ServiceReturnKey.INT_ARRAY)[1];
                        ProgressUpdateClient.this.mProgressUpdateListener.onUpdateProgress(percent, currentImageIndex, totalImageCount);
                        ProgressUpdateClient.this.mProgressObservers.forEach(new Consumer() { // from class: com.samsung.android.photoremasterservice.ProgressUpdateClient$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((ProgressObserver) obj).update();
                            }
                        });
                        LogUtil.m291d(ProgressUpdateClient.TAG, "Received OnUpdateProgress: " + percent + ", " + currentImageIndex + ", " + totalImageCount);
                        break;
                    default:
                        LogUtil.m292e(ProgressUpdateClient.TAG, "Wrong message is received.: " + msg.what);
                        super.handleMessage(msg);
                        break;
                }
            }
        });
    }

    public synchronized void deinit() {
        this.mHandlerThread.quit();
    }

    public synchronized void setContext(Context context) {
        LogUtil.m291d(TAG, "Context is set.");
        this.mContext = context;
    }

    private synchronized void bind() {
        LogUtil.m291d(TAG, "Try binding...");
        if (this.mContext == null) {
            LogUtil.m292e(TAG, "Context is null.");
            return;
        }
        if (this.mIsBound) {
            LogUtil.m294i(TAG, "Already bound.");
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(SERVICE_PACKAGE, SERVICE_CLASS);
        this.mContext.bindService(intent, 1, Executors.newSingleThreadExecutor(), this.mConnection);
        getServiceReturnValue();
        LogUtil.m294i(TAG, "Service binding is finished.");
        this.mIsBound = true;
    }

    private static boolean isExceptionContained(Bundle bundle) {
        return (bundle == null || bundle.getSerializable("exception", Exception.class) == null) ? false : true;
    }

    private Bundle getServiceReturnValue() {
        try {
            Bundle retValue = this.mServiceReturnValue.get(BIND_TIME_OUT_SECOND, TimeUnit.SECONDS);
            this.mServiceReturnValue = new CompletableFuture<>();
            if (isExceptionContained(retValue)) {
                LogUtil.m292e(TAG, "Exception is received from service.");
                Exception exception = (Exception) retValue.getSerializable("exception", Exception.class);
                if (exception == null) {
                    throw new RuntimeException("Unknown exception form service-server.");
                }
                RuntimeException rtException = new RuntimeException(exception + " in PhotoRemaster Service");
                rtException.setStackTrace(exception.getStackTrace());
                rtException.printStackTrace();
                throw rtException;
            }
            return retValue;
        } catch (InterruptedException | ExecutionException e) {
            LogUtil.m292e(TAG, e.toString());
            e.printStackTrace();
            this.mServiceReturnValue = new CompletableFuture<>();
            return null;
        } catch (TimeoutException e2) {
            LogUtil.m292e(TAG, e2 + " is occurred at getServiceReturnValue()");
            e2.printStackTrace();
            this.mServiceReturnValue = new CompletableFuture<>();
            throw new RuntimeException(e2.toString());
        }
    }

    public synchronized void unbindService() {
        LogUtil.m291d(TAG, "Try unbinding...");
        Context context = this.mContext;
        if (context == null) {
            LogUtil.m292e(TAG, "Context is null.");
            return;
        }
        if (!this.mIsBound) {
            LogUtil.m291d(TAG, "Already unbound!!!");
            return;
        }
        context.unbindService(this.mConnection);
        this.mIsBound = false;
        this.mProgressUpdateListener = null;
        LogUtil.m294i(TAG, "Service is unbound.");
    }

    private void throwRuntimeException(String msg) {
        LogUtil.m292e(TAG, msg);
        throw new RuntimeException(msg);
    }

    public void setProgressUpdateListener(IDirector.ProgressUpdateListener listener) {
        this.mProgressUpdateListener = listener;
        if (listener == null) {
            return;
        }
        if (!this.mIsBound) {
            bind();
            if (!this.mIsBound) {
                throwRuntimeException("Service bind fail.");
            }
        }
        if (this.mServiceMessenger == null) {
            throwRuntimeException("Unexpected disconnection, ServiceMessenger is null!");
        }
        LogUtil.m291d(TAG, "ServiceCallRunnable started!");
        new Thread(new Runnable() { // from class: com.samsung.android.photoremasterservice.ProgressUpdateClient.1ServiceCallRunnable
            @Override // java.lang.Runnable
            public void run() {
                try {
                    LogUtil.m291d(ProgressUpdateClient.TAG, "Send message to service...");
                    Message msg = Message.obtain((Handler) null, 14);
                    msg.setData(null);
                    msg.replyTo = ProgressUpdateClient.this.mIncomingMessenger;
                    ProgressUpdateClient.this.mServiceMessenger.send(msg);
                } catch (RemoteException e) {
                    LogUtil.m292e(ProgressUpdateClient.TAG, "Exception at sending message. - " + e);
                }
            }
        }).start();
        LogUtil.m291d(TAG, "Waiting the ack.");
        Bundle retBundle = getServiceReturnValue();
        if (retBundle == null) {
            LogUtil.m292e(TAG, "Service return bundle is null!");
            throw new RuntimeException("Service return bundle is null!");
        }
        LogUtil.m291d(TAG, "Service ack is received.");
    }
}
