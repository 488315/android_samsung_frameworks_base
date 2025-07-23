package com.samsung.android.smartface;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.smartface.ISmartFaceClient;
import com.samsung.android.smartface.ISmartFaceService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
public class SmartFaceManager {
    public static final String FALSE = "false";
    public static final String FEATURE_SMART_STAY = "com.sec.android.smartface.smart_stay";
    public static final int MSG_FACEINFO = 0;
    public static final int MSG_REGISTERED = 1;
    public static final int MSG_UNREGISTERED = 2;
    private static final String NULL_VALUE = "";
    public static final int SERVICETYPE_STAY = 4;
    public static final String SMARTFACE_SERVICE = "samsung.smartfaceservice";
    public static final String SMART_SCREEN_DUMP_PREVIEW = "smart-screen-dump";
    public static final int SMART_STAY_DELAY = 2750;
    public static final String SMART_STAY_FRAMECOUNT_RESET = "smart-stay-framecount-reset";
    private static final String TAG = "SmartFaceManager";
    public static final String TRUE = "true";
    private final Condition complete;
    private final Lock lock;
    private int mCallbackData;
    private final SmartFaceClient mClient;
    private final Context mContext;
    private final EventHandler mEventHandler;
    private final Object mEventHandlerLock;
    private SmartFaceInfoListener mListener = null;
    private final Object mListenerLock = new Object();
    private EventHandler mInternalEventHandler = null;

    public interface SmartFaceInfoListener {
        void onInfo(FaceInfo faceInfo, int i);
    }

    public interface SmartFaceInfoListener2 extends SmartFaceInfoListener {
        void onRegistered(SmartFaceManager smartFaceManager, int i);

        void onUnregistered(SmartFaceManager smartFaceManager, int i);
    }

    private static final class ServiceManagerGlobal {
        private static final String SERVICE_PACKAGE_NAME = "com.samsung.android.smartface";
        private static final String SERVICE_SERVICE_NAME = "com.samsung.android.smartface.SmartFaceServiceStarter";
        private static final long SERVICE_TIMEOUT = 1000;
        private final Handler mHandler;
        private final HandlerThread mHandlerThread;
        private static final String TAG = "SmartFaceManager/" + ServiceManagerGlobal.class.getSimpleName();
        private static final ServiceManagerGlobal INSTANCE = new ServiceManagerGlobal();
        private ISmartFaceService mService = null;
        private ServiceConnection mServiceConnection = null;
        private int mConnectionCount = 0;

        private ServiceManagerGlobal() {
            HandlerThread handlerThread = new HandlerThread(TAG);
            this.mHandlerThread = handlerThread;
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper());
        }

        public static ServiceManagerGlobal get() {
            return INSTANCE;
        }

        public synchronized boolean bindToServiceSync(final Context context) {
            String str = TAG;
            Log.e(str, "bind to smart face service");
            if (this.mServiceConnection == null) {
                this.mServiceConnection = new ServiceConnection() { // from class: com.samsung.android.smartface.SmartFaceManager.ServiceManagerGlobal.1
                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        Log.e(ServiceManagerGlobal.TAG, "smart face service connected");
                        ServiceManagerGlobal.this.finishConnection(iBinder);
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(ComponentName componentName) {
                        Log.e(ServiceManagerGlobal.TAG, "smart face service disconnected");
                        ServiceManagerGlobal.this.unbindService(context);
                    }

                    @Override // android.content.ServiceConnection
                    public void onBindingDied(ComponentName componentName) {
                        Log.e(ServiceManagerGlobal.TAG, "smart face service died");
                        ServiceManagerGlobal.this.unbindService(context);
                    }
                };
                Intent intent = new Intent();
                intent.setClassName(SERVICE_PACKAGE_NAME, SERVICE_SERVICE_NAME);
                Log.i(str, "bindService started");
                context.bindServiceAsUser(intent, this.mServiceConnection, 73, getHandler(), UserHandle.CURRENT_OR_SELF);
                try {
                    wait(1000L);
                } catch (InterruptedException unused) {
                    Log.w(TAG, "interrupted while binding");
                }
            } else {
                Log.e(str, "already tried bound to smart face service");
            }
            String str2 = TAG;
            StringBuilder sb = new StringBuilder("Service is ");
            sb.append(this.mService != null ? "available" : "unavailable");
            sb.append(", connection count = ");
            sb.append(this.mConnectionCount);
            sb.append(" -> ");
            int i = this.mConnectionCount + 1;
            this.mConnectionCount = i;
            sb.append(i);
            Log.e(str2, sb.toString());
            return this.mService != null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void finishConnection(IBinder iBinder) {
            this.mService = ISmartFaceService.Stub.asInterface(iBinder);
            notifyAll();
        }

        public synchronized void unbindService(Context context) {
            String str = TAG;
            StringBuilder sb = new StringBuilder("unbind from smart face service, connection count = ");
            sb.append(this.mConnectionCount);
            sb.append(" -> ");
            int i = this.mConnectionCount - 1;
            this.mConnectionCount = i;
            sb.append(i);
            Log.e(str, sb.toString());
            if (this.mConnectionCount <= 0) {
                ServiceConnection serviceConnection = this.mServiceConnection;
                if (serviceConnection != null) {
                    try {
                        context.unbindService(serviceConnection);
                    } catch (Exception e) {
                        Log.e(TAG, "fail to unbind from smart face service", e);
                    }
                    this.mService = null;
                    this.mServiceConnection = null;
                } else {
                    Log.e(str, "already unbound from smart face service");
                }
            }
            if (this.mConnectionCount < 0) {
                Log.e(TAG, "possible mis-match in bind & unbind or service died.");
                this.mConnectionCount = 0;
            }
        }

        public synchronized Handler getHandler() {
            return this.mHandler;
        }

        public synchronized Looper getLooper() {
            return this.mHandlerThread.getLooper();
        }

        public synchronized ISmartFaceService getService() {
            return this.mService;
        }
    }

    public static SmartFaceManager getSmartFaceManager(Context context) {
        return new SmartFaceManager(context);
    }

    private SmartFaceManager(Context context) {
        Object obj = new Object();
        this.mEventHandlerLock = obj;
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.complete = reentrantLock.newCondition();
        this.mContext = context;
        this.mClient = new SmartFaceClient();
        synchronized (obj) {
            Looper myLooper = Looper.myLooper();
            if (myLooper != null) {
                this.mEventHandler = new EventHandler(this, myLooper);
            } else {
                Looper mainLooper = Looper.getMainLooper();
                if (mainLooper != null) {
                    this.mEventHandler = new EventHandler(this, mainLooper);
                } else {
                    this.mEventHandler = null;
                }
            }
        }
    }

    public synchronized boolean start(int i) {
        boolean z = false;
        if (!ServiceManagerGlobal.get().bindToServiceSync(this.mContext)) {
            return false;
        }
        try {
            z = ServiceManagerGlobal.get().getService().register(this.mClient, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            Log.w(TAG, "null service, ignore", e2);
        }
        return z;
    }

    public synchronized void startAsync(int i) {
        if (ServiceManagerGlobal.get().bindToServiceSync(this.mContext)) {
            try {
                ServiceManagerGlobal.get().getService().registerAsync(this.mClient, i);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NullPointerException e2) {
                Log.w(TAG, "null service, ignore", e2);
            }
        }
    }

    public synchronized void stop() {
        try {
            if (ServiceManagerGlobal.get().getService() == null) {
                return;
            }
            try {
                try {
                    ServiceManagerGlobal.get().getService().unregister(this.mClient);
                } catch (NullPointerException e) {
                    Log.w(TAG, "null service, ignore", e);
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            synchronized (this.mEventHandlerLock) {
                EventHandler eventHandler = this.mEventHandler;
                if (eventHandler != null) {
                    eventHandler.removeCallbacksAndMessages(null);
                }
                EventHandler eventHandler2 = this.mInternalEventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.removeCallbacksAndMessages(null);
                }
            }
        } finally {
            ServiceManagerGlobal.get().unbindService(this.mContext);
        }
    }

    public synchronized void stopAsync() {
        try {
            if (ServiceManagerGlobal.get().getService() == null) {
                return;
            }
            try {
                try {
                    ServiceManagerGlobal.get().getService().unregisterAsync(this.mClient);
                } catch (NullPointerException e) {
                    Log.w(TAG, "null service, ignore", e);
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            synchronized (this.mEventHandlerLock) {
                EventHandler eventHandler = this.mEventHandler;
                if (eventHandler != null) {
                    eventHandler.removeCallbacksAndMessages(null);
                }
                EventHandler eventHandler2 = this.mInternalEventHandler;
                if (eventHandler2 != null) {
                    eventHandler2.removeCallbacksAndMessages(null);
                }
            }
        } finally {
            ServiceManagerGlobal.get().unbindService(this.mContext);
        }
    }

    public synchronized void setValue(String str, int i) {
        setValue(str, Integer.toString(i));
    }

    public synchronized void setValue(String str, String str2) {
        try {
            if (ServiceManagerGlobal.get().bindToServiceSync(this.mContext)) {
                Log.d(TAG, "Sending " + str + ":" + str2 + " to service");
                try {
                    ServiceManagerGlobal.get().getService().setValue(this.mClient, str, str2);
                } catch (RemoteException e) {
                    Log.w(TAG, "remote exception, ignore", e);
                } catch (NullPointerException e2) {
                    Log.w(TAG, "null service, ignore", e2);
                }
            }
        } finally {
            ServiceManagerGlobal.get().unbindService(this.mContext);
        }
    }

    public synchronized boolean checkForSmartStay() {
        SmartFaceInfoListener smartFaceInfoListener;
        boolean z;
        Log.e(TAG, "checkForSmartStay S");
        synchronized (this.mEventHandlerLock) {
            this.mInternalEventHandler = new EventHandler(this, ServiceManagerGlobal.get().getLooper());
        }
        synchronized (this.mListenerLock) {
            smartFaceInfoListener = this.mListener;
        }
        setListener(new SmartFaceInfoListener() { // from class: com.samsung.android.smartface.SmartFaceManager$$ExternalSyntheticLambda0
            @Override // com.samsung.android.smartface.SmartFaceManager.SmartFaceInfoListener
            public final void onInfo(FaceInfo faceInfo, int i) {
                SmartFaceManager.this.lambda$checkForSmartStay$0(faceInfo, i);
            }
        });
        this.lock.lock();
        try {
            ServiceManagerGlobal.get().bindToServiceSync(this.mContext);
            setValue(SMART_STAY_FRAMECOUNT_RESET, "");
            if (start(4)) {
                this.mCallbackData = -1;
                waitForCallback(1182);
                z = this.mCallbackData > 0;
                this.mCallbackData = -1;
                waitForCallback(1017);
                if (this.mCallbackData > 0) {
                    z = true;
                }
            }
            this.lock.unlock();
            stop();
            ServiceManagerGlobal.get().unbindService(this.mContext);
            synchronized (this.mEventHandlerLock) {
                this.mInternalEventHandler = null;
            }
            setListener(smartFaceInfoListener);
            Log.e(TAG, "checkForSmartStay X: " + z);
        } catch (Throwable th) {
            this.lock.unlock();
            stop();
            ServiceManagerGlobal.get().unbindService(this.mContext);
            throw th;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkForSmartStay$0(FaceInfo faceInfo, int i) {
        Log.e(TAG, "checkForSmartStay onInfo: " + Integer.toBinaryString(i) + ": " + faceInfo.needToStay);
        if ((i & 4) != 0) {
            this.lock.lock();
            try {
                this.mCallbackData = faceInfo.needToStay;
                this.complete.signal();
            } finally {
                this.lock.unlock();
            }
        }
    }

    public synchronized int getSupportedServices() {
        try {
            int i = 0;
            if (!ServiceManagerGlobal.get().bindToServiceSync(this.mContext)) {
                return 0;
            }
            try {
                i = ServiceManagerGlobal.get().getService().getSupportedServices();
            } catch (RemoteException e) {
                Log.w(TAG, "remote exception, ignore", e);
            } catch (NullPointerException e2) {
                Log.w(TAG, "null service, ignore", e2);
            }
            return i;
        } finally {
            ServiceManagerGlobal.get().unbindService(this.mContext);
        }
    }

    public void setListener(SmartFaceInfoListener smartFaceInfoListener) {
        synchronized (this.mListenerLock) {
            this.mListener = smartFaceInfoListener;
        }
    }

    private class SmartFaceClient extends ISmartFaceClient.Stub {
        SmartFaceClient() {
            Log.e(SmartFaceManager.TAG, "New SmartFaceClient");
        }

        @Override // com.samsung.android.smartface.ISmartFaceClient
        public void onInfo(int i, FaceInfo faceInfo, int i2) {
            synchronized (SmartFaceManager.this.mEventHandlerLock) {
                if (SmartFaceManager.this.mInternalEventHandler != null) {
                    SmartFaceManager.this.mInternalEventHandler.sendMessage(SmartFaceManager.this.mInternalEventHandler.obtainMessage(i, i2, 0, faceInfo));
                } else if (SmartFaceManager.this.mEventHandler != null) {
                    SmartFaceManager.this.mEventHandler.sendMessage(SmartFaceManager.this.mEventHandler.obtainMessage(i, i2, 0, faceInfo));
                } else {
                    Log.e(SmartFaceManager.TAG, "EventHandler is null");
                }
            }
        }
    }

    private long waitForCallback(int i) {
        long j = -1;
        try {
            j = this.complete.awaitNanos(i * 1000000);
            if (j <= 0) {
                Log.e(TAG, "No Callback!");
            }
        } catch (Exception unused) {
        }
        return j;
    }

    private class EventHandler extends Handler {
        private final SmartFaceManager mManager;

        public EventHandler(SmartFaceManager smartFaceManager, Looper looper) {
            super(looper);
            this.mManager = smartFaceManager;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            synchronized (SmartFaceManager.this.mListenerLock) {
                if (SmartFaceManager.this.mListener != null) {
                    int i = message.what;
                    if (i == 0) {
                        SmartFaceManager.this.mListener.onInfo((FaceInfo) message.obj, message.arg1);
                    } else if (i != 1) {
                        if (i == 2) {
                            if (SmartFaceManager.this.mListener instanceof SmartFaceInfoListener2) {
                                ((SmartFaceInfoListener2) SmartFaceManager.this.mListener).onUnregistered(this.mManager, message.arg1);
                            } else {
                                Log.e(SmartFaceManager.TAG, "Listener does not implements SmartFaceInfoListener2");
                            }
                        }
                    } else if (SmartFaceManager.this.mListener instanceof SmartFaceInfoListener2) {
                        ((SmartFaceInfoListener2) SmartFaceManager.this.mListener).onRegistered(this.mManager, message.arg1);
                    } else {
                        Log.e(SmartFaceManager.TAG, "Listener does not implements SmartFaceInfoListener2");
                    }
                } else {
                    Log.e(SmartFaceManager.TAG, "Listener is null");
                }
            }
        }
    }
}
