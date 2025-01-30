package com.samsung.android.smartface;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.p009os.DeadObjectException;
import android.p009os.Handler;
import android.p009os.HandlerThread;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.UserHandle;
import android.util.Log;
import com.samsung.android.smartface.ISmartFaceClient;
import com.samsung.android.smartface.ISmartFaceService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes5.dex */
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
    private ISmartFaceService mService = null;
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

    public static SmartFaceManager getSmartFaceManager(Context context) {
        return new SmartFaceManager(context);
    }

    private synchronized boolean ensureServiceConnected() {
        ISmartFaceService iSmartFaceService = this.mService;
        if (iSmartFaceService != null) {
            try {
                iSmartFaceService.setValue(this.mClient, "empty key for ping", "empty value");
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    this.mService = null;
                }
            }
        }
        if (this.mService == null) {
            startSmartFaceService();
            waitForService();
        }
        return this.mService != null;
    }

    private void waitForService() {
        for (int count = 1; count <= 3; count++) {
            ISmartFaceService asInterface = ISmartFaceService.Stub.asInterface(ServiceManager.getService(SMARTFACE_SERVICE));
            this.mService = asInterface;
            if (asInterface != null) {
                Log.m100v(TAG, "Service connected!");
                return;
            } else {
                try {
                    Thread.sleep(300L);
                    Log.m96e(TAG, "Wait for " + (count * 300) + "ms...");
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private void startSmartFaceService() {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.samsung.android.smartface", "com.samsung.android.smartface.SmartFaceServiceStarter"));
            this.mContext.startServiceAsUser(intent, UserHandle.CURRENT_OR_SELF);
        } catch (SecurityException e) {
            Log.m96e(TAG, "Service is being installed. Ignore smart stay request.");
        }
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
            Looper looper = Looper.myLooper();
            if (looper != null) {
                this.mEventHandler = new EventHandler(this, looper);
            } else {
                Looper looper2 = Looper.getMainLooper();
                if (looper2 != null) {
                    this.mEventHandler = new EventHandler(this, looper2);
                } else {
                    this.mEventHandler = null;
                }
            }
        }
    }

    public boolean start(int serviceType) {
        if (!ensureServiceConnected()) {
            return false;
        }
        try {
            boolean ret = this.mService.register(this.mClient, serviceType);
            return ret;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void startAsync(int serviceType) {
        if (ensureServiceConnected()) {
            try {
                this.mService.registerAsync(this.mClient, serviceType);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (ensureServiceConnected()) {
            try {
                this.mService.unregister(this.mClient);
            } catch (RemoteException e) {
                e.printStackTrace();
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
        }
    }

    public void stopAsync() {
        if (ensureServiceConnected()) {
            try {
                this.mService.unregisterAsync(this.mClient);
            } catch (RemoteException e) {
                e.printStackTrace();
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
        }
    }

    public void setValue(String key, int value) {
        setValue(key, Integer.toString(value));
    }

    public void setValue(String key, String value) {
        if (ensureServiceConnected()) {
            Log.m94d(TAG, "Sending " + key + ":" + value + " to service");
            try {
                this.mService.setValue(this.mClient, key, value);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean checkForSmartStay() {
        boolean ret;
        SmartFaceInfoListener listener;
        Log.m96e(TAG, "checkForSmartStay S");
        HandlerThread waitThread = new HandlerThread("Smart Stay Wait Thread");
        waitThread.start();
        synchronized (this.mEventHandlerLock) {
            this.mInternalEventHandler = new EventHandler(this, waitThread.getLooper());
        }
        synchronized (this.mListenerLock) {
            listener = this.mListener;
        }
        setListener(new SmartFaceInfoListener() { // from class: com.samsung.android.smartface.SmartFaceManager$$ExternalSyntheticLambda0
            @Override // com.samsung.android.smartface.SmartFaceManager.SmartFaceInfoListener
            public final void onInfo(FaceInfo faceInfo, int i) {
                SmartFaceManager.this.lambda$checkForSmartStay$0(faceInfo, i);
            }
        });
        this.lock.lock();
        try {
            setValue(SMART_STAY_FRAMECOUNT_RESET, "");
            if (start(4)) {
                try {
                    this.mCallbackData = -1;
                    waitForCallback(1182);
                    ret = this.mCallbackData > 0;
                    this.mCallbackData = -1;
                    waitForCallback(1017);
                    if (this.mCallbackData > 0) {
                        ret = true;
                    }
                } catch (Throwable th) {
                    th = th;
                    this.lock.unlock();
                    throw th;
                }
            }
            this.lock.unlock();
            stop();
            synchronized (this.mEventHandlerLock) {
                waitThread.quit();
                this.mInternalEventHandler = null;
            }
            setListener(listener);
            Log.m96e(TAG, "checkForSmartStay X: " + ret);
        } catch (Throwable th2) {
            th = th2;
        }
        return ret;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkForSmartStay$0(FaceInfo data, int service_type) {
        Log.m96e(TAG, "checkForSmartStay onInfo: " + Integer.toBinaryString(service_type) + ": " + data.needToStay);
        if ((service_type & 4) != 0) {
            this.lock.lock();
            try {
                this.mCallbackData = data.needToStay;
                this.complete.signal();
            } finally {
                this.lock.unlock();
            }
        }
    }

    public int getSupportedServices() {
        if (!ensureServiceConnected()) {
            return 0;
        }
        try {
            int ret = this.mService.getSupportedServices();
            return ret;
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setListener(SmartFaceInfoListener listener) {
        synchronized (this.mListenerLock) {
            this.mListener = listener;
        }
    }

    private class SmartFaceClient extends ISmartFaceClient.Stub {
        SmartFaceClient() {
            Log.m96e(SmartFaceManager.TAG, "New SmartFaceClient");
        }

        @Override // com.samsung.android.smartface.ISmartFaceClient
        public void onInfo(int msgType, FaceInfo data, int serviceType) {
            synchronized (SmartFaceManager.this.mEventHandlerLock) {
                if (SmartFaceManager.this.mInternalEventHandler != null) {
                    Message m = SmartFaceManager.this.mInternalEventHandler.obtainMessage(msgType, serviceType, 0, data);
                    SmartFaceManager.this.mInternalEventHandler.sendMessage(m);
                } else if (SmartFaceManager.this.mEventHandler != null) {
                    Message m2 = SmartFaceManager.this.mEventHandler.obtainMessage(msgType, serviceType, 0, data);
                    SmartFaceManager.this.mEventHandler.sendMessage(m2);
                } else {
                    Log.m96e(SmartFaceManager.TAG, "EventHandler is null");
                }
            }
        }
    }

    private long waitForCallback(int waitMilli) {
        long ret = -1;
        try {
            long awaitNanos = this.complete.awaitNanos(waitMilli * 1000 * 1000);
            ret = awaitNanos;
            if (awaitNanos <= 0) {
                Log.m96e(TAG, "No Callback!");
            }
        } catch (Exception e) {
        }
        return ret;
    }

    private class EventHandler extends Handler {
        private SmartFaceManager mManager;

        public EventHandler(SmartFaceManager manager, Looper looper) {
            super(looper);
            this.mManager = manager;
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            synchronized (SmartFaceManager.this.mListenerLock) {
                if (SmartFaceManager.this.mListener != null) {
                    switch (msg.what) {
                        case 0:
                            SmartFaceManager.this.mListener.onInfo((FaceInfo) msg.obj, msg.arg1);
                            break;
                        case 1:
                            if (SmartFaceManager.this.mListener instanceof SmartFaceInfoListener2) {
                                ((SmartFaceInfoListener2) SmartFaceManager.this.mListener).onRegistered(this.mManager, msg.arg1);
                                break;
                            } else {
                                Log.m96e(SmartFaceManager.TAG, "Listener does not implements SmartFaceInfoListener2");
                                break;
                            }
                        case 2:
                            if (SmartFaceManager.this.mListener instanceof SmartFaceInfoListener2) {
                                ((SmartFaceInfoListener2) SmartFaceManager.this.mListener).onUnregistered(this.mManager, msg.arg1);
                                break;
                            } else {
                                Log.m96e(SmartFaceManager.TAG, "Listener does not implements SmartFaceInfoListener2");
                                break;
                            }
                    }
                } else {
                    Log.m96e(SmartFaceManager.TAG, "Listener is null");
                }
            }
        }
    }
}
