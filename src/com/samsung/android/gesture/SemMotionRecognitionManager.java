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
        /* JADX WARN: Removed duplicated region for block: B:11:0x0046  */
        /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
        @Override // com.samsung.android.hardware.context.SemContextListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onSemContextChanged(com.samsung.android.hardware.context.SemContextEvent r6) {
            /*
                r5 = this;
                java.lang.String r0 = "  >> check setting smart alert enabled : "
                com.samsung.android.hardware.context.SemContext r1 = r6.semContext
                com.samsung.android.gesture.SemMotionRecognitionEvent r2 = new com.samsung.android.gesture.SemMotionRecognitionEvent
                r2.<init>()
                int r1 = r1.getType()
                r3 = 5
                if (r1 == r3) goto L11
                goto L7d
            L11:
                com.samsung.android.hardware.context.SemContextMovement r6 = r6.getMovementContext()
                int r6 = r6.getAction()
                r1 = 1
                if (r6 != r1) goto L7d
                r6 = 0
                com.samsung.android.gesture.SemMotionRecognitionManager r1 = com.samsung.android.gesture.SemMotionRecognitionManager.this     // Catch: android.os.RemoteException -> L3b
                com.samsung.android.gesture.IMotionRecognitionService r1 = com.samsung.android.gesture.SemMotionRecognitionManager.m9120$$Nest$fgetmotionService(r1)     // Catch: android.os.RemoteException -> L3b
                boolean r1 = r1.getPickUpMotionStatus()     // Catch: android.os.RemoteException -> L3b
                java.lang.String r3 = "MotionRecognitionManager"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L39
                r4.<init>(r0)     // Catch: android.os.RemoteException -> L39
                r4.append(r1)     // Catch: android.os.RemoteException -> L39
                java.lang.String r0 = r4.toString()     // Catch: android.os.RemoteException -> L39
                android.util.Log.d(r3, r0)     // Catch: android.os.RemoteException -> L39
                goto L44
            L39:
                r0 = move-exception
                goto L3d
            L3b:
                r0 = move-exception
                r1 = r6
            L3d:
                java.lang.String r3 = "MotionRecognitionManager"
                java.lang.String r4 = "RemoteException in getPickUpMotionStatus: "
                android.util.Log.e(r3, r4, r0)
            L44:
                if (r1 == 0) goto L7d
                r0 = 67
                r2.setMotion(r0)
                java.lang.String r0 = "MotionRecognitionManager"
                java.lang.String r1 = "mySemContextMotionListener : Send Smart alert event"
                android.util.Log.d(r0, r1)
                com.samsung.android.gesture.SemMotionRecognitionManager r0 = com.samsung.android.gesture.SemMotionRecognitionManager.this
                java.util.ArrayList r0 = com.samsung.android.gesture.SemMotionRecognitionManager.m9121$$Nest$fgetsListenerDelegates(r0)
                monitor-enter(r0)
                com.samsung.android.gesture.SemMotionRecognitionManager r1 = com.samsung.android.gesture.SemMotionRecognitionManager.this     // Catch: java.lang.Throwable -> L7a
                java.util.ArrayList r1 = com.samsung.android.gesture.SemMotionRecognitionManager.m9121$$Nest$fgetsListenerDelegates(r1)     // Catch: java.lang.Throwable -> L7a
                int r1 = r1.size()     // Catch: java.lang.Throwable -> L7a
            L64:
                if (r6 >= r1) goto L78
                com.samsung.android.gesture.SemMotionRecognitionManager r3 = com.samsung.android.gesture.SemMotionRecognitionManager.this     // Catch: java.lang.Throwable -> L7a
                java.util.ArrayList r3 = com.samsung.android.gesture.SemMotionRecognitionManager.m9121$$Nest$fgetsListenerDelegates(r3)     // Catch: java.lang.Throwable -> L7a
                java.lang.Object r3 = r3.get(r6)     // Catch: java.lang.Throwable -> L7a
                com.samsung.android.gesture.SemMotionRecognitionManager$MRListenerDelegate r3 = (com.samsung.android.gesture.SemMotionRecognitionManager.MRListenerDelegate) r3     // Catch: java.lang.Throwable -> L7a
                r3.motionCallback(r2)     // Catch: java.lang.Throwable -> L7a
                int r6 = r6 + 1
                goto L64
            L78:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L7a
                goto L7d
            L7a:
                r5 = move-exception
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L7a
                throw r5
            L7d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.gesture.SemMotionRecognitionManager.AnonymousClass1.onSemContextChanged(com.samsung.android.hardware.context.SemContextEvent):void");
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
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    break;
                }
                MRListenerDelegate mRListenerDelegate = this.sListenerDelegates.get(i3);
                if (mRListenerDelegate.getListener() == semMotionEventListener) {
                    i2 = mRListenerDelegate.getMotionEvents() & (~i);
                    Log.d(TAG, "update listener " + i3 + " = name :" + semMotionEventListener + ",  motionevents = " + i2);
                    break;
                }
                i3++;
            }
            unregisterListener(semMotionEventListener);
            if (i2 != 0) {
                registerListener(semMotionEventListener, i2);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0054, code lost:
    
        r8.sListenerDelegates.remove(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x005d, code lost:
    
        if (r3.getMotionEvents() == 0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005f, code lost:
    
        r8.motionService.unregisterCallback(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0065, code lost:
    
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0066, code lost:
    
        android.util.Log.e(com.samsung.android.gesture.SemMotionRecognitionManager.TAG, "RemoteException in unregisterListener: ", r2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void unregisterListener(com.samsung.android.gesture.SemMotionEventListener r9) {
        /*
            r8 = this;
            com.samsung.android.gesture.IMotionRecognitionService r0 = r8.motionService
            if (r0 != 0) goto L6
            goto La1
        L6:
            java.util.ArrayList<com.samsung.android.gesture.SemMotionRecognitionManager$MRListenerDelegate> r0 = r8.sListenerDelegates
            monitor-enter(r0)
            java.util.ArrayList<com.samsung.android.gesture.SemMotionRecognitionManager$MRListenerDelegate> r1 = r8.sListenerDelegates     // Catch: java.lang.Throwable -> La2
            int r1 = r1.size()     // Catch: java.lang.Throwable -> La2
            r2 = 0
            r3 = r2
        L11:
            if (r3 >= r1) goto L44
            java.util.ArrayList<com.samsung.android.gesture.SemMotionRecognitionManager$MRListenerDelegate> r4 = r8.sListenerDelegates     // Catch: java.lang.Throwable -> La2
            java.lang.Object r4 = r4.get(r3)     // Catch: java.lang.Throwable -> La2
            com.samsung.android.gesture.SemMotionRecognitionManager$MRListenerDelegate r4 = (com.samsung.android.gesture.SemMotionRecognitionManager.MRListenerDelegate) r4     // Catch: java.lang.Throwable -> La2
            com.samsung.android.gesture.SemMotionEventListener r4 = r4.getListener()     // Catch: java.lang.Throwable -> La2
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> La2
            java.lang.String r5 = "MotionRecognitionManager"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La2
            r6.<init>()     // Catch: java.lang.Throwable -> La2
            java.lang.String r7 = "@ member "
            r6.append(r7)     // Catch: java.lang.Throwable -> La2
            r6.append(r3)     // Catch: java.lang.Throwable -> La2
            java.lang.String r7 = " = "
            r6.append(r7)     // Catch: java.lang.Throwable -> La2
            r6.append(r4)     // Catch: java.lang.Throwable -> La2
            java.lang.String r4 = r6.toString()     // Catch: java.lang.Throwable -> La2
            android.util.Log.d(r5, r4)     // Catch: java.lang.Throwable -> La2
            int r3 = r3 + 1
            goto L11
        L44:
            if (r2 >= r1) goto L74
            java.util.ArrayList<com.samsung.android.gesture.SemMotionRecognitionManager$MRListenerDelegate> r3 = r8.sListenerDelegates     // Catch: java.lang.Throwable -> La2
            java.lang.Object r3 = r3.get(r2)     // Catch: java.lang.Throwable -> La2
            com.samsung.android.gesture.SemMotionRecognitionManager$MRListenerDelegate r3 = (com.samsung.android.gesture.SemMotionRecognitionManager.MRListenerDelegate) r3     // Catch: java.lang.Throwable -> La2
            com.samsung.android.gesture.SemMotionEventListener r4 = r3.getListener()     // Catch: java.lang.Throwable -> La2
            if (r4 != r9) goto L71
            java.util.ArrayList<com.samsung.android.gesture.SemMotionRecognitionManager$MRListenerDelegate> r4 = r8.sListenerDelegates     // Catch: java.lang.Throwable -> La2
            r4.remove(r2)     // Catch: java.lang.Throwable -> La2
            int r2 = r3.getMotionEvents()     // Catch: android.os.RemoteException -> L65 java.lang.Throwable -> La2
            if (r2 == 0) goto L6d
            com.samsung.android.gesture.IMotionRecognitionService r2 = r8.motionService     // Catch: android.os.RemoteException -> L65 java.lang.Throwable -> La2
            r2.unregisterCallback(r3)     // Catch: android.os.RemoteException -> L65 java.lang.Throwable -> La2
            goto L6d
        L65:
            r2 = move-exception
            java.lang.String r4 = "MotionRecognitionManager"
            java.lang.String r5 = "RemoteException in unregisterListener: "
            android.util.Log.e(r4, r5, r2)     // Catch: java.lang.Throwable -> La2
        L6d:
            r3.resetListener()     // Catch: java.lang.Throwable -> La2
            goto L74
        L71:
            int r2 = r2 + 1
            goto L44
        L74:
            java.lang.String r2 = "MotionRecognitionManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La2
            r3.<init>()     // Catch: java.lang.Throwable -> La2
            java.lang.String r4 = "  .unregisterListener : / listener count = "
            r3.append(r4)     // Catch: java.lang.Throwable -> La2
            r3.append(r1)     // Catch: java.lang.Throwable -> La2
            java.lang.String r1 = "->"
            r3.append(r1)     // Catch: java.lang.Throwable -> La2
            java.util.ArrayList<com.samsung.android.gesture.SemMotionRecognitionManager$MRListenerDelegate> r8 = r8.sListenerDelegates     // Catch: java.lang.Throwable -> La2
            int r8 = r8.size()     // Catch: java.lang.Throwable -> La2
            r3.append(r8)     // Catch: java.lang.Throwable -> La2
            java.lang.String r8 = ", name :"
            r3.append(r8)     // Catch: java.lang.Throwable -> La2
            r3.append(r9)     // Catch: java.lang.Throwable -> La2
            java.lang.String r8 = r3.toString()     // Catch: java.lang.Throwable -> La2
            android.util.Log.i(r2, r8)     // Catch: java.lang.Throwable -> La2
            monitor-exit(r0)     // Catch: java.lang.Throwable -> La2
        La1:
            return
        La2:
            r8 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> La2
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.gesture.SemMotionRecognitionManager.unregisterListener(com.samsung.android.gesture.SemMotionEventListener):void");
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
                            if (MRListenerDelegate.this.mListener != null && message != null && message.what == 53) {
                                MRListenerDelegate.this.mListener.onMotionEvent((SemMotionRecognitionEvent) message.obj);
                            }
                        } catch (ClassCastException e) {
                            Log.e(SemMotionRecognitionManager.TAG, "ClassCastException in handleMessage: msg.obj = " + message.obj, e);
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
            Message obtain = Message.obtain();
            obtain.what = 53;
            obtain.obj = semMotionRecognitionEvent;
            this.mHandler.sendMessage(obtain);
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
