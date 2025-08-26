package com.samsung.android.gesture;

import android.app.ActivityThread;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telecom.Logging.Session;
import android.util.Log;
import com.samsung.android.gesture.IMotionRecognitionCallback;
import com.samsung.android.gesture.IMotionRecognitionService;
import com.samsung.android.hardware.context.SemContext;
import com.samsung.android.hardware.context.SemContextEvent;
import com.samsung.android.hardware.context.SemContextListener;
import com.samsung.android.hardware.context.SemContextManager;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SemMotionRecognitionManager {
    public static final int EVENT_DOUBLE_TAP = 8;
    public static final int EVENT_ELEVATOR_DETECTOR = 16777216;
    public static final int EVENT_LOCK_EXECUTE_L = 128;
    public static final int EVENT_LOCK_EXECUTE_R = 256;
    public static final int EVENT_PANNING_GALLERY = 32;
    public static final int EVENT_PANNING_HOME = 64;
    public static final int EVENT_POCKET_FOR_BIXBY = 33554432;
    public static final int EVENT_POCKET_MODE = 8388608;
    public static final int EVENT_SHAKE = 2;
    public static final int EVENT_SMART_ALERT_SETTING = 32768;
    public static final int EVENT_SMART_SCROLL = 524288;
    public static final int EVENT_TILT = 16;
    public static final int EVENT_TILT_LEVEL_ZERO = 4096;
    public static final int EVENT_TILT_LEVEL_ZERO_LAND = 16384;
    public static final int EVENT_TILT_TO_UNLOCK = 2048;
    public static final int EVENT_VOLUME_DOWN = 512;
    public static final int MOTION_ALL = 1180677;
    public static final int MOTION_CALL_POSE = 262144;

    @Deprecated
    public static final int MOTION_DIRECT_CALLING = 1024;
    public static final int MOTION_FLAT = 8192;
    public static final int MOTION_NUM = 25;
    public static final int MOTION_OVERTURN = 1;
    public static final int MOTION_OVERTURN_LOW_POWER = 131072;
    public static final int MOTION_PALM_SWIPE = 4194304;
    public static final int MOTION_PALM_TOUCH = 2097152;
    public static final int MOTION_SCREEN_UP_STEADY = 65536;
    public static final int MOTION_SENSOR_NUM = 5;
    public static final int MOTION_SMART_ALERT = 4;
    public static final int MOTION_SMART_RELAY = 1048576;
    public static final int MOTION_USE_ACC = 1;
    public static final int MOTION_USE_ALL = 15;
    public static final int MOTION_USE_ALWAYS = 1073741824;
    public static final int MOTION_USE_FOLDING_STATE = 16;
    public static final int MOTION_USE_GYRO = 2;
    public static final int MOTION_USE_LIGHT = 8;
    public static final int MOTION_USE_PROX = 4;
    protected static final String TAG = "MotionRecognitionManager";
    private static final int mMotionVersion = 1;
    private boolean mHasSensorHub;
    private final Looper mMainLooper;
    private final SemContextManager mSemContextManager;
    private IMotionRecognitionService motionService;
    private final ArrayList<MRListenerDelegate> sListenerDelegates = new ArrayList<>();
    private final SemContextListener mySemContextMotionListener = new SemContextListener() { // from class: com.samsung.android.gesture.SemMotionRecognitionManager.1
        /* JADX WARN: Removed duplicated region for block: B:17:0x0046  */
        /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
        @Override // com.samsung.android.hardware.context.SemContextListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onSemContextChanged(SemContextEvent semContextEvent) {
            boolean pickUpMotionStatus;
            SemContext semContext = semContextEvent.semContext;
            SemMotionRecognitionEvent semMotionRecognitionEvent = new SemMotionRecognitionEvent();
            if (semContext.getType() == 5 && semContextEvent.getMovementContext().getAction() == 1) {
                try {
                    pickUpMotionStatus = SemMotionRecognitionManager.this.motionService.getPickUpMotionStatus();
                    try {
                        Log.d(SemMotionRecognitionManager.TAG, "  >> check setting smart alert enabled : " + pickUpMotionStatus);
                    } catch (RemoteException e) {
                        e = e;
                        Log.e(SemMotionRecognitionManager.TAG, "RemoteException in getPickUpMotionStatus: ", e);
                        if (pickUpMotionStatus) {
                        }
                    }
                } catch (RemoteException e2) {
                    e = e2;
                    pickUpMotionStatus = false;
                }
                if (pickUpMotionStatus) {
                    return;
                }
                semMotionRecognitionEvent.setMotion(67);
                Log.d(SemMotionRecognitionManager.TAG, "mySemContextMotionListener : Send Smart alert event");
                synchronized (SemMotionRecognitionManager.this.sListenerDelegates) {
                    int size = SemMotionRecognitionManager.this.sListenerDelegates.size();
                    for (int i = 0; i < size; i++) {
                        ((MRListenerDelegate) SemMotionRecognitionManager.this.sListenerDelegates.get(i)).motionCallback(semMotionRecognitionEvent);
                    }
                }
            }
        }
    };
    private int mMovementCnt = 0;

    public static int getMotionVersion() {
        return 1;
    }

    @Deprecated
    public static boolean isValidMotionSensor(int i) {
        return i == 1 || i == 2 || i == 4 || i == 8 || i == 16;
    }

    public void setMotionAngle(SemMotionEventListener semMotionEventListener, int i) {
    }

    @Deprecated
    public void useMotionAlways(SemMotionEventListener semMotionEventListener, boolean z) {
    }

    public SemMotionRecognitionManager(Looper looper) {
        this.motionService = IMotionRecognitionService.Stub.asInterface(ServiceManager.getService(Context.SEM_MOTION_RECOGNITION_SERVICE));
        this.mMainLooper = looper;
        this.mSemContextManager = new SemContextManager(looper);
        this.motionService = IMotionRecognitionService.Stub.asInterface(ServiceManager.getService(Context.SEM_MOTION_RECOGNITION_SERVICE));
        Log.d(TAG, "motionService = " + this.motionService);
        try {
            IMotionRecognitionService iMotionRecognitionService = this.motionService;
            if (iMotionRecognitionService != null) {
                this.mHasSensorHub = iMotionRecognitionService.getSSPstatus();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getSSPstatus: ", e);
        }
    }

    public void registerListener(SemMotionEventListener semMotionEventListener, int i) {
        registerListener(semMotionEventListener, i, null);
    }

    public void registerListener(SemMotionEventListener semMotionEventListener, int i, int i2, Handler handler) {
        if (semMotionEventListener == null || this.motionService == null) {
            return;
        }
        synchronized (this.sListenerDelegates) {
            int size = this.sListenerDelegates.size();
            Iterator<MRListenerDelegate> it = this.sListenerDelegates.iterator();
            while (it.hasNext()) {
                if (it.next().getListener() == semMotionEventListener) {
                    Log.d(TAG, "  .registerListener : fail. already registered / listener count = " + this.sListenerDelegates.size() + ", name :" + semMotionEventListener);
                    return;
                }
            }
            MRListenerDelegate mRListenerDelegate = new MRListenerDelegate(semMotionEventListener, i2, handler);
            this.sListenerDelegates.add(mRListenerDelegate);
            if (i2 != 0) {
                try {
                    this.motionService.registerCallback(mRListenerDelegate, i, i2);
                } catch (RemoteException e) {
                    Log.e(TAG, "RemoteException in registerListener : ", e);
                }
            }
            Log.v(TAG, "  .registerListener : success. listener count = " + size + Session.SUBSESSION_SEPARATION_CHAR + this.sListenerDelegates.size() + ", motion_events=" + i2 + ", name :" + semMotionEventListener);
        }
    }

    public void registerListener(SemMotionEventListener semMotionEventListener, int i, Handler handler) {
        registerListener(semMotionEventListener, 0, i, handler);
    }

    public void unregisterListener(SemMotionEventListener semMotionEventListener, int i) {
        if (this.motionService == null) {
            return;
        }
        synchronized (this.sListenerDelegates) {
            int size = this.sListenerDelegates.size();
            int motionEvents = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    break;
                }
                MRListenerDelegate mRListenerDelegate = this.sListenerDelegates.get(i2);
                if (mRListenerDelegate.getListener() == semMotionEventListener) {
                    motionEvents = mRListenerDelegate.getMotionEvents() & (~i);
                    Log.d(TAG, "update listener " + i2 + " = name :" + semMotionEventListener + ",  motionevents = " + motionEvents);
                    break;
                }
                i2++;
            }
            unregisterListener(semMotionEventListener);
            if (motionEvents != 0) {
                registerListener(semMotionEventListener, motionEvents);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0054, code lost:
    
        r8.sListenerDelegates.remove(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005d, code lost:
    
        if (r3.getMotionEvents() == 0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x005f, code lost:
    
        r8.motionService.unregisterCallback(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0065, code lost:
    
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0066, code lost:
    
        android.util.Log.e(com.samsung.android.gesture.SemMotionRecognitionManager.TAG, "RemoteException in unregisterListener: ", r2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void unregisterListener(SemMotionEventListener semMotionEventListener) {
        MRListenerDelegate mRListenerDelegate;
        if (this.motionService == null) {
            return;
        }
        synchronized (this.sListenerDelegates) {
            int size = this.sListenerDelegates.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                Log.d(TAG, "@ member " + i2 + " = " + this.sListenerDelegates.get(i2).getListener().toString());
            }
            while (true) {
                if (i >= size) {
                    break;
                }
                mRListenerDelegate = this.sListenerDelegates.get(i);
                if (mRListenerDelegate.getListener() == semMotionEventListener) {
                    break;
                } else {
                    i++;
                }
            }
            Log.i(TAG, "  .unregisterListener : / listener count = " + size + Session.SUBSESSION_SEPARATION_CHAR + this.sListenerDelegates.size() + ", name :" + semMotionEventListener);
        }
        mRListenerDelegate.resetListener();
        Log.i(TAG, "  .unregisterListener : / listener count = " + size + Session.SUBSESSION_SEPARATION_CHAR + this.sListenerDelegates.size() + ", name :" + semMotionEventListener);
    }

    public void setSmartMotionAngle(SemMotionEventListener semMotionEventListener, int i) {
        if (this.motionService == null) {
            return;
        }
        synchronized (this.sListenerDelegates) {
            int size = this.sListenerDelegates.size();
            for (int i2 = 0; i2 < size; i2++) {
                MRListenerDelegate mRListenerDelegate = this.sListenerDelegates.get(i2);
                if (mRListenerDelegate.getListener() == semMotionEventListener) {
                    try {
                        this.motionService.setMotionAngle(mRListenerDelegate, i);
                    } catch (RemoteException e) {
                        Log.e(TAG, "RemoteException in setSmartMotionAngle: ", e);
                    }
                    return;
                }
            }
            Log.d(TAG, "  .setSmartMotionAngle : listener has to be registered first");
        }
    }

    @Deprecated
    public void setMotionTiltLevel(int i, int i2, int i3, int i4, int i5, int i6) {
        IMotionRecognitionService iMotionRecognitionService = this.motionService;
        if (iMotionRecognitionService == null) {
            return;
        }
        try {
            iMotionRecognitionService.setMotionTiltLevel(i, i2, i3, i4, i5, i6);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in setMotionTiltLevel: ", e);
        }
        Log.d(TAG, "  .setMotionTiltLevel : 1");
    }

    public int resetMotionEngine() {
        IMotionRecognitionService iMotionRecognitionService = this.motionService;
        if (iMotionRecognitionService == null) {
            return -1;
        }
        try {
            return iMotionRecognitionService.resetMotionEngine();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in resetMotionEngine: ", e);
            return 0;
        }
    }

    public boolean isAvailable(int i) {
        IMotionRecognitionService iMotionRecognitionService = this.motionService;
        if (iMotionRecognitionService != null) {
            if (i != 1 && i != 4 && i != 1024 && i != 2097152 && i != 4194304 && i != 8388608 && i != 16777216 && i != 33554432) {
                return false;
            }
            try {
                return iMotionRecognitionService.isAvailable(i);
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in getSSPstatus: ", e);
            }
        }
        return false;
    }

    public float[] getEvToLux(float[] fArr) {
        float[] fArr2 = new float[3];
        IMotionRecognitionService iMotionRecognitionService = this.motionService;
        if (iMotionRecognitionService != null) {
            try {
                return iMotionRecognitionService.getEvToLux(fArr);
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in getSSPstatus: ", e);
            }
        }
        return fArr2;
    }

    public String getEvLuxTableInfo(String str) {
        IMotionRecognitionService iMotionRecognitionService = this.motionService;
        if (iMotionRecognitionService == null) {
            return "";
        }
        try {
            return iMotionRecognitionService.getEvLuxTableInfo(str);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getEvLuxTableInfo: ", e);
            return "";
        }
    }

    public boolean setTestSensor() {
        try {
            return this.motionService.setTestSensor();
        } catch (RemoteException unused) {
            Log.e(TAG, "RemoteException in setTestSensor");
            return false;
        }
    }

    public void startAdaptiveBrightness() {
        try {
            this.motionService.startAdaptiveBrightness();
        } catch (RemoteException unused) {
            Log.e(TAG, "RemoteException in startAdaptiveBrightness");
        }
    }

    public void stopAdaptiveBrightness() {
        try {
            this.motionService.stopAdaptiveBrightness();
        } catch (RemoteException unused) {
            Log.e(TAG, "RemoteException in stopAdaptiveBrightness");
        }
    }

    private class MRListenerDelegate extends IMotionRecognitionCallback.Stub {
        private final int EVENT_FROM_SERVICE = 53;
        private final Handler mHandler;
        private SemMotionEventListener mListener;
        private String mListenerPackageName;
        private int mMotionEvents;

        MRListenerDelegate(SemMotionEventListener semMotionEventListener, int i, Handler handler) {
            this.mListenerPackageName = null;
            this.mListener = semMotionEventListener;
            Looper looper = handler != null ? handler.getLooper() : SemMotionRecognitionManager.this.mMainLooper;
            this.mMotionEvents = i;
            this.mListenerPackageName = ActivityThread.currentPackageName();
            this.mHandler = new Handler(looper) { // from class: com.samsung.android.gesture.SemMotionRecognitionManager.MRListenerDelegate.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    synchronized (SemMotionRecognitionManager.this.sListenerDelegates) {
                        try {
                        } catch (ClassCastException e) {
                            Log.e(SemMotionRecognitionManager.TAG, "ClassCastException in handleMessage: msg.obj = " + message.obj, e);
                        }
                        if (MRListenerDelegate.this.mListener != null && message != null && message.what == 53) {
                            MRListenerDelegate.this.mListener.onMotionEvent((SemMotionRecognitionEvent) message.obj);
                        }
                    }
                }
            };
        }

        public SemMotionEventListener getListener() {
            return this.mListener;
        }

        public int getMotionEvents() {
            return this.mMotionEvents;
        }

        public void setMotionEvents(int i) {
            this.mMotionEvents = i;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionCallback
        public void motionCallback(SemMotionRecognitionEvent semMotionRecognitionEvent) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 53;
            messageObtain.obj = semMotionRecognitionEvent;
            this.mHandler.sendMessage(messageObtain);
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionCallback
        public String getListenerInfo() {
            return this.mListener.toString();
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionCallback
        public String getListenerPackageName() {
            return this.mListenerPackageName;
        }

        public void resetListener() {
            this.mListener = null;
        }
    }
}
