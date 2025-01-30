package com.samsung.android.gesture;

import android.app.ActivityThread;
import android.content.Context;
import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.telecom.Logging.Session;
import android.util.Log;
import com.samsung.android.hardware.context.SemContext;
import com.samsung.android.hardware.context.SemContextEvent;
import com.samsung.android.hardware.context.SemContextListener;
import com.samsung.android.hardware.context.SemContextManager;
import com.samsung.android.hardware.context.SemContextMovement;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
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

  @Deprecated public static final int MOTION_DIRECT_CALLING = 1024;
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
  private final SemContextListener mySemContextMotionListener =
      new SemContextListener() { // from class:
                                 // com.samsung.android.gesture.SemMotionRecognitionManager.1
        @Override // com.samsung.android.hardware.context.SemContextListener
        public void onSemContextChanged(SemContextEvent event) {
          SemContext semContext = event.semContext;
          SemMotionRecognitionEvent mrevent = new SemMotionRecognitionEvent();
          boolean isEnabledPickUp = false;
          switch (semContext.getType()) {
            case 5:
              SemContextMovement semContextMovement = event.getMovementContext();
              if (semContextMovement.getAction() == 1) {
                try {
                  isEnabledPickUp =
                      SemMotionRecognitionManager.this.motionService.getPickUpMotionStatus();
                  Log.m94d(
                      SemMotionRecognitionManager.TAG,
                      "  >> check setting smart alert enabled : " + isEnabledPickUp);
                } catch (RemoteException e) {
                  Log.m97e(
                      SemMotionRecognitionManager.TAG,
                      "RemoteException in getPickUpMotionStatus: ",
                      e);
                }
                if (isEnabledPickUp) {
                  mrevent.setMotion(67);
                  Log.m94d(
                      SemMotionRecognitionManager.TAG,
                      "mySemContextMotionListener : Send Smart alert event");
                  synchronized (SemMotionRecognitionManager.this.sListenerDelegates) {
                    int size = SemMotionRecognitionManager.this.sListenerDelegates.size();
                    for (int i = 0; i < size; i++) {
                      MRListenerDelegate l =
                          (MRListenerDelegate)
                              SemMotionRecognitionManager.this.sListenerDelegates.get(i);
                      l.motionCallback(mrevent);
                    }
                  }
                  return;
                }
                return;
              }
              return;
            default:
              return;
          }
        }
      };
  private int mMovementCnt = 0;

  public SemMotionRecognitionManager(Looper mainLooper) {
    this.motionService =
        IMotionRecognitionService.Stub.asInterface(
            ServiceManager.getService(Context.SEM_MOTION_RECOGNITION_SERVICE));
    this.mMainLooper = mainLooper;
    this.mSemContextManager = new SemContextManager(mainLooper);
    this.motionService =
        IMotionRecognitionService.Stub.asInterface(
            ServiceManager.getService(Context.SEM_MOTION_RECOGNITION_SERVICE));
    Log.m94d(TAG, "motionService = " + this.motionService);
    try {
      IMotionRecognitionService iMotionRecognitionService = this.motionService;
      if (iMotionRecognitionService != null) {
        this.mHasSensorHub = iMotionRecognitionService.getSSPstatus();
      }
    } catch (RemoteException e) {
      Log.m97e(TAG, "RemoteException in getSSPstatus: ", e);
    }
  }

  public void registerListener(SemMotionEventListener listener, int motion_events) {
    registerListener(listener, motion_events, null);
  }

  public void registerListener(
      SemMotionEventListener listener, int motion_sensors, int motion_events, Handler handler) {
    if (listener == null || this.motionService == null) {
      return;
    }
    synchronized (this.sListenerDelegates) {
      int size = this.sListenerDelegates.size();
      Iterator<MRListenerDelegate> it = this.sListenerDelegates.iterator();
      while (it.hasNext()) {
        MRListenerDelegate l = it.next();
        if (l.getListener() == listener) {
          Log.m94d(
              TAG,
              "  .registerListener : fail. already registered / listener count = "
                  + this.sListenerDelegates.size()
                  + ", name :"
                  + listener);
          return;
        }
      }
      MRListenerDelegate mrListener = new MRListenerDelegate(listener, motion_events, handler);
      this.sListenerDelegates.add(mrListener);
      if (motion_events != 0) {
        try {
          this.motionService.registerCallback(mrListener, motion_sensors, motion_events);
        } catch (RemoteException e) {
          Log.m97e(TAG, "RemoteException in registerListener : ", e);
        }
      }
      Log.m100v(
          TAG,
          "  .registerListener : success. listener count = "
              + size
              + Session.SUBSESSION_SEPARATION_CHAR
              + this.sListenerDelegates.size()
              + ", motion_events="
              + motion_events
              + ", name :"
              + listener);
    }
  }

  public void registerListener(
      SemMotionEventListener listener, int motion_events, Handler handler) {
    registerListener(listener, 0, motion_events, handler);
  }

  public void unregisterListener(SemMotionEventListener listener, int motion_events) {
    if (this.motionService == null) {
      return;
    }
    synchronized (this.sListenerDelegates) {
      int size = this.sListenerDelegates.size();
      int motionevents = 0;
      int i = 0;
      while (true) {
        if (i >= size) {
          break;
        }
        MRListenerDelegate l = this.sListenerDelegates.get(i);
        if (l.getListener() != listener) {
          i++;
        } else {
          motionevents = l.getMotionEvents() & (~motion_events);
          Log.m94d(
              TAG,
              "update listener "
                  + i
                  + " = name :"
                  + listener
                  + ",  motionevents = "
                  + motionevents);
          break;
        }
      }
      unregisterListener(listener);
      if (motionevents != 0) {
        registerListener(listener, motionevents);
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:18:0x0058, code lost:

     r8.sListenerDelegates.remove(r2);
  */
  /* JADX WARN: Code restructure failed: missing block: B:21:0x0061, code lost:

     if (r3.getMotionEvents() == 0) goto L21;
  */
  /* JADX WARN: Code restructure failed: missing block: B:22:0x0063, code lost:

     r8.motionService.unregisterCallback(r3);
  */
  /* JADX WARN: Code restructure failed: missing block: B:29:0x0069, code lost:

     r4 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:30:0x006a, code lost:

     android.util.Log.m97e(com.samsung.android.gesture.SemMotionRecognitionManager.TAG, "RemoteException in unregisterListener: ", r4);
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void unregisterListener(SemMotionEventListener listener) {
    MRListenerDelegate l;
    if (this.motionService == null) {
      return;
    }
    synchronized (this.sListenerDelegates) {
      int size = this.sListenerDelegates.size();
      for (int i = 0; i < size; i++) {
        String listener_name = this.sListenerDelegates.get(i).getListener().toString();
        Log.m94d(TAG, "@ member " + i + " = " + listener_name);
      }
      int i2 = 0;
      while (true) {
        if (i2 >= size) {
          break;
        }
        l = this.sListenerDelegates.get(i2);
        if (l.getListener() == listener) {
          break;
        } else {
          i2++;
        }
      }
      Log.m98i(
          TAG,
          "  .unregisterListener : / listener count = "
              + size
              + Session.SUBSESSION_SEPARATION_CHAR
              + this.sListenerDelegates.size()
              + ", name :"
              + listener);
    }
    l.resetListener();
    Log.m98i(
        TAG,
        "  .unregisterListener : / listener count = "
            + size
            + Session.SUBSESSION_SEPARATION_CHAR
            + this.sListenerDelegates.size()
            + ", name :"
            + listener);
  }

  @Deprecated
  public void useMotionAlways(SemMotionEventListener listener, boolean bUseAlways) {}

  public void setMotionAngle(SemMotionEventListener listener, int status) {}

  public void setSmartMotionAngle(SemMotionEventListener listener, int status) {
    if (this.motionService == null) {
      return;
    }
    synchronized (this.sListenerDelegates) {
      int size = this.sListenerDelegates.size();
      for (int i = 0; i < size; i++) {
        MRListenerDelegate l = this.sListenerDelegates.get(i);
        if (l.getListener() == listener) {
          try {
            this.motionService.setMotionAngle(l, status);
          } catch (RemoteException e) {
            Log.m97e(TAG, "RemoteException in setSmartMotionAngle: ", e);
          }
          return;
        }
      }
      Log.m94d(TAG, "  .setSmartMotionAngle : listener has to be registered first");
    }
  }

  @Deprecated
  public void setMotionTiltLevel(
      int stopUp, int level1Up, int level2Up, int stopDown, int level1Down, int level2Down) {
    IMotionRecognitionService iMotionRecognitionService = this.motionService;
    if (iMotionRecognitionService == null) {
      return;
    }
    try {
      iMotionRecognitionService.setMotionTiltLevel(
          stopUp, level1Up, level2Up, stopDown, level1Down, level2Down);
    } catch (RemoteException e) {
      Log.m97e(TAG, "RemoteException in setMotionTiltLevel: ", e);
    }
    Log.m94d(TAG, "  .setMotionTiltLevel : 1");
  }

  public int resetMotionEngine() {
    IMotionRecognitionService iMotionRecognitionService = this.motionService;
    if (iMotionRecognitionService == null) {
      return -1;
    }
    try {
      return iMotionRecognitionService.resetMotionEngine();
    } catch (RemoteException e) {
      Log.m97e(TAG, "RemoteException in resetMotionEngine: ", e);
      return 0;
    }
  }

  @Deprecated
  public static boolean isValidMotionSensor(int motion_sensor) {
    return motion_sensor == 1
        || motion_sensor == 2
        || motion_sensor == 4
        || motion_sensor == 8
        || motion_sensor == 16;
  }

  public static int getMotionVersion() {
    return 1;
  }

  public boolean isAvailable(int type) {
    IMotionRecognitionService iMotionRecognitionService = this.motionService;
    if (iMotionRecognitionService == null) {
      return false;
    }
    switch (type) {
      case 1:
      case 4:
      case 1024:
      case 2097152:
      case 4194304:
      case 8388608:
      case 16777216:
      case 33554432:
        try {
          boolean ret = iMotionRecognitionService.isAvailable(type);
          break;
        } catch (RemoteException e) {
          Log.m97e(TAG, "RemoteException in getSSPstatus: ", e);
          return false;
        }
    }
    return false;
  }

  public float[] getEvToLux(float[] values) {
    float[] ret = new float[3];
    IMotionRecognitionService iMotionRecognitionService = this.motionService;
    if (iMotionRecognitionService == null) {
      return ret;
    }
    try {
      return iMotionRecognitionService.getEvToLux(values);
    } catch (RemoteException e) {
      Log.m97e(TAG, "RemoteException in getSSPstatus: ", e);
      return ret;
    }
  }

  public String getEvLuxTableInfo(String info) {
    IMotionRecognitionService iMotionRecognitionService = this.motionService;
    if (iMotionRecognitionService == null) {
      return "";
    }
    try {
      String str = iMotionRecognitionService.getEvLuxTableInfo(info);
      return str;
    } catch (RemoteException e) {
      Log.m97e(TAG, "RemoteException in getEvLuxTableInfo: ", e);
      return "";
    }
  }

  public boolean setTestSensor() {
    try {
      boolean ret = this.motionService.setTestSensor();
      return ret;
    } catch (RemoteException e) {
      Log.m96e(TAG, "RemoteException in setTestSensor");
      return false;
    }
  }

  public void startAdaptiveBrightness() {
    try {
      this.motionService.startAdaptiveBrightness();
    } catch (RemoteException e) {
      Log.m96e(TAG, "RemoteException in startAdaptiveBrightness");
    }
  }

  public void stopAdaptiveBrightness() {
    try {
      this.motionService.stopAdaptiveBrightness();
    } catch (RemoteException e) {
      Log.m96e(TAG, "RemoteException in stopAdaptiveBrightness");
    }
  }

  private class MRListenerDelegate extends IMotionRecognitionCallback.Stub {
    private final int EVENT_FROM_SERVICE = 53;
    private final Handler mHandler;
    private SemMotionEventListener mListener;
    private String mListenerPackageName;
    private int mMotionEvents;

    MRListenerDelegate(SemMotionEventListener listener, int motion_sensors, Handler handler) {
      this.mListenerPackageName = null;
      this.mListener = listener;
      Looper looper =
          handler != null ? handler.getLooper() : SemMotionRecognitionManager.this.mMainLooper;
      this.mMotionEvents = motion_sensors;
      this.mListenerPackageName = ActivityThread.currentPackageName();
      this.mHandler =
          new Handler(
              looper) { // from class:
                        // com.samsung.android.gesture.SemMotionRecognitionManager.MRListenerDelegate.1
            @Override // android.p009os.Handler
            public void handleMessage(Message msg) {
              synchronized (SemMotionRecognitionManager.this.sListenerDelegates) {
                try {
                  if (MRListenerDelegate.this.mListener != null && msg != null && msg.what == 53) {
                    SemMotionRecognitionEvent motionEvent = (SemMotionRecognitionEvent) msg.obj;
                    MRListenerDelegate.this.mListener.onMotionEvent(motionEvent);
                  }
                } catch (ClassCastException e) {
                  Log.m97e(
                      SemMotionRecognitionManager.TAG,
                      "ClassCastException in handleMessage: msg.obj = " + msg.obj,
                      e);
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

    public void setMotionEvents(int motionevents) {
      this.mMotionEvents = motionevents;
    }

    @Override // com.samsung.android.gesture.IMotionRecognitionCallback
    public void motionCallback(SemMotionRecognitionEvent motionEvent) {
      Message msg = Message.obtain();
      msg.what = 53;
      msg.obj = motionEvent;
      this.mHandler.sendMessage(msg);
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
