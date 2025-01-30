package com.samsung.android.aod;

import android.content.Context;
import android.p009os.Binder;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.util.Log;
import android.view.Display;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes5.dex */
public class AODManager {
  public static final int AOD_MANAGER_VERSION = 4;
  public static final String AOD_PACKAGE_NAME = "com.samsung.android.app.aodservice";
  public static final int INTERVAL_100 = 0;
  public static final int INTERVAL_1000 = 3;
  public static final int INTERVAL_200 = 1;
  public static final int INTERVAL_500 = 2;
  public static final int INTERVAL_DEBUG = 999;
  public static final int ROTATE_0 = 0;
  public static final int ROTATE_180 = 2;
  public static final int ROTATE_270 = 3;
  public static final int ROTATE_90 = 1;
  private static final String TAG = "AODManager";
  public static final int TYPE_ACTIVE_ANALOG_IMAGE = 2;
  public static final int TYPE_ACTIVE_DIGITAL_IMAGE = 3;
  public static final int TYPE_ACTIVE_ICON_IMAGE = 1;
  private static AODManager sInstance;
  private AODDozeCallbackDelegate mAODDozeCallbackDelegate;
  Context mContext;
  private IAODManager mService;
  private final Object mAODCallbackLock = new Object();
  private CopyOnWriteArrayList<AODCallbackDelegate> mAODCallbackDelegates =
      new CopyOnWriteArrayList<>();

  public interface AODChangeListener {
    void readyToScreenTurningOn();
  }

  public interface AODDozeCallback {
    void onAODToastRequested(AODToast aODToast);

    void onDozeAcquired();

    void onDozeReleased();
  }

  public static AODManager getInstance(Context context) {
    AODManager aODManager = sInstance;
    if (aODManager != null) {
      return aODManager;
    }
    AODManager aODManager2 = new AODManager(context);
    sInstance = aODManager2;
    return aODManager2;
  }

  public AODManager(Context context) {
    this.mContext = context;
  }

  private IAODManager getService() {
    if (this.mService == null) {
      IBinder b = ServiceManager.getService("AODManagerService");
      this.mService = IAODManager.Stub.asInterface(b);
    }
    if (this.mService == null) {
      Log.wtf(TAG, "getService fail!");
    }
    return this.mService;
  }

  public boolean isAODState() {
    if (getService() == null) {
      return false;
    }
    try {
      return this.mService.isAODState();
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
      return false;
    }
  }

  public void updateAODTspRect(int width, int height, int x, int y) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.updateAODTspRect(width, height, x, y, AOD_PACKAGE_NAME);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
    }
  }

  public void updateAODTspRect(int width, int height, int x, int y, String packageName) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.updateAODTspRect(width, height, x, y, packageName);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
    }
  }

  public boolean isSViewCoverBrightnessHigh() {
    if (getService() == null) {
      return true;
    }
    try {
      return this.mService.isSViewCoverBrightnessHigh();
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
      return true;
    }
  }

  public void addLogText(List<String> logs) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.addLogText(logs);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
    }
  }

  public void writeAODCommand(String location, String cmd, String arg1, String arg2, String arg3) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.writeAODCommand(location, cmd, arg1, arg2, arg3);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
    }
  }

  public int setLiveClockInfo(
      int type,
      long en,
      long interval,
      long hour,
      long min,
      long second,
      long ms,
      long pos_x,
      long pos_y) {
    if (getService() == null) {
      return -1;
    }
    try {
      return this.mService.setLiveClockInfo(
          type, en, interval, hour, min, second, ms, pos_x, pos_y);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
      return -1;
    }
  }

  public void setLiveClockNeedle(byte[] img_buf) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.setLiveClockNeedle(img_buf);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
    }
  }

  public String getActiveImageInfo() {
    if (getService() == null) {
      return null;
    }
    try {
      return this.mService.getActiveImageInfo();
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
      return null;
    }
  }

  public int setLiveClockImage(int nodeType, int clockType, byte[] img_buf, String imageInfo) {
    if (getService() == null) {
      return -1;
    }
    try {
      return this.mService.setLiveClockImage(nodeType, clockType, img_buf, imageInfo);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
      return -1;
    }
  }

  public int setLiveClockCommand(int nodeType, int cmd, int dataSize, int[] dataArray) {
    if (getService() == null) {
      return -1;
    }
    try {
      return this.mService.setLiveClockCommand(nodeType, cmd, dataSize, dataArray);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
      return -1;
    }
  }

  public String getAodActiveArea(boolean isSubDisplay) {
    if (getService() == null) {
      return SemInputDeviceManager.RESULT_STR_NG;
    }
    try {
      return this.mService.getAodActiveArea(isSubDisplay);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
      return SemInputDeviceManager.RESULT_STR_NG;
    }
  }

  public void readyToScreenTurningOn() {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.readyToScreenTurningOn();
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
    }
  }

  public void registerAODListener(AODListener listener) {
    if (getService() == null) {
      return;
    }
    if (listener == null) {
      Log.m102w(TAG, "registerAODListener : listener is null");
      return;
    }
    synchronized (this.mAODCallbackLock) {
      AODCallbackDelegate delegate = null;
      Iterator<AODCallbackDelegate> i = this.mAODCallbackDelegates.iterator();
      while (true) {
        if (!i.hasNext()) {
          break;
        }
        AODCallbackDelegate temp = i.next();
        if (temp.getListener() != null && temp.getListener().equals(listener)) {
          delegate = temp;
          break;
        }
      }
      if (delegate == null) {
        AODCallbackDelegate delegate2 = new AODCallbackDelegate(listener);
        this.mAODCallbackDelegates.add(delegate2);
        try {
          this.mService.registerAODListener(delegate2);
        } catch (RemoteException e) {
          Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
        return;
      }
      Log.m102w(TAG, "registerAODListener : listener already registered");
    }
  }

  public void unregisterAODListener(AODListener listener) {
    if (getService() == null) {
      return;
    }
    if (listener == null) {
      Log.m102w(TAG, "unregisterAODListener : listener is null");
      return;
    }
    synchronized (this.mAODCallbackLock) {
      AODCallbackDelegate delegate = null;
      Iterator<AODCallbackDelegate> i = this.mAODCallbackDelegates.iterator();
      while (true) {
        if (!i.hasNext()) {
          break;
        }
        AODCallbackDelegate temp = i.next();
        if (temp.getListener() != null && temp.getListener().equals(listener)) {
          delegate = temp;
          break;
        }
      }
      if (delegate == null) {
        Log.m102w(TAG, "unregisterAODListener : cannot find the listener");
        return;
      }
      try {
        this.mService.unregisterAODListener(delegate);
        this.mAODCallbackDelegates.remove(delegate);
      } catch (RemoteException e) {
        Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  class AODCallbackDelegate extends IAODCallback.Stub {
    private Handler mHandler;
    private AODListener mListener;

    public AODCallbackDelegate(AODListener listener) {
      this.mListener = listener;
      this.mHandler = new Handler(AODManager.this.mContext.getMainLooper());
    }

    @Override // com.samsung.android.aod.IAODCallback
    public void onScreenTurningOn() {
      this.mHandler.postAtFrontOfQueue(
          new Runnable() { // from class:
                           // com.samsung.android.aod.AODManager$AODCallbackDelegate$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              AODManager.AODCallbackDelegate.this.lambda$onScreenTurningOn$0();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onScreenTurningOn$0() {
      AODListener aODListener = this.mListener;
      if (aODListener != null) {
        aODListener.onScreenTurningOn();
      }
    }

    AODListener getListener() {
      return this.mListener;
    }
  }

  public void registerAODDozeCallback(AODDozeCallback callback) {
    if (getService() == null) {
      return;
    }
    if (callback == null) {
      Log.m102w(TAG, "registerAODDozeCallback: callback is null");
      return;
    }
    if (this.mAODDozeCallbackDelegate != null) {
      Log.m102w(TAG, "registerAODDozeCallback: listener already registered");
      return;
    }
    AODDozeCallbackDelegate aODDozeCallbackDelegate = new AODDozeCallbackDelegate(callback);
    this.mAODDozeCallbackDelegate = aODDozeCallbackDelegate;
    try {
      this.mService.registerAODDozeCallback(aODDozeCallbackDelegate);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
    }
  }

  public void unregisterAODDozeCallback(AODDozeCallback callback) {
    if (getService() == null) {
      return;
    }
    if (callback == null) {
      Log.m102w(TAG, "unregisterAODDozeCallback: callback is null");
      return;
    }
    AODDozeCallbackDelegate aODDozeCallbackDelegate = this.mAODDozeCallbackDelegate;
    if (aODDozeCallbackDelegate == null) {
      Log.m102w(TAG, "unregisterAODDozeCallback: not registered yet");
      return;
    }
    try {
      this.mService.unregisterAODDozeCallback(aODDozeCallbackDelegate);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
    }
    this.mAODDozeCallbackDelegate = null;
  }

  public void requestAODToast(AODToast toast) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.requestAODToast(this.mContext.getPackageName(), toast);
    } catch (RemoteException e) {
      Log.m102w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
    }
  }

  public final class AODDozeLock {
    private boolean mHeld;
    private final String mPackageName;
    private String mTag;
    private final IBinder mToken = new Binder();

    private AODDozeLock(String tag, String packageName) {
      this.mTag = tag;
      this.mPackageName = packageName;
    }

    public void acquire() {
      synchronized (this.mToken) {
        Display display = AODManager.this.mContext.getDisplay();
        if (display == null) {
          Log.m94d(AODManager.TAG, "acquireDoze: display is null");
          return;
        }
        int state = display.getState();
        switch (display.getState()) {
          case 1:
          case 3:
          case 4:
            try {
              AODManager.this.mService.acquireDoze(this.mToken, this.mTag, this.mPackageName);
            } catch (RemoteException e) {
              Log.m102w(
                  AODManager.TAG, "AODDozeLock RuntimeException?\n" + Log.getStackTraceString(e));
            }
            this.mHeld = true;
            return;
          case 2:
          default:
            Log.m94d(AODManager.TAG, "acquireDoze: skip due to state = " + state);
            return;
        }
      }
    }

    public void release() {
      synchronized (this.mToken) {
        Display display = AODManager.this.mContext.getDisplay();
        if (display == null) {
          Log.m94d(AODManager.TAG, "release: display is null");
          return;
        }
        int state = display.getState();
        switch (display.getState()) {
          case 3:
          case 4:
            if (this.mHeld) {
              try {
                AODManager.this.mService.releaseDoze(this.mToken);
              } catch (RemoteException e) {
                Log.m102w(
                    AODManager.TAG, "AODDozeLock RuntimeException?\n" + Log.getStackTraceString(e));
              }
              this.mHeld = false;
            }
            return;
          default:
            Log.m94d(AODManager.TAG, "releaseDoze: skip due to state = " + state);
            return;
        }
      }
    }

    public boolean isHeld() {
      boolean z;
      synchronized (this.mToken) {
        z = this.mHeld;
      }
      return z;
    }

    public AODDozeLock newAODDozeLock(String tag) {
      AODManager aODManager = AODManager.this;
      return aODManager.new AODDozeLock(tag, aODManager.mContext.getOpPackageName());
    }
  }

  private class AODDozeCallbackDelegate extends IAODDozeCallback.Stub {
    private WeakReference<AODDozeCallback> mCallback;
    private Handler mHandler;

    AODDozeCallbackDelegate(AODDozeCallback callback) {
      this.mHandler = new Handler(AODManager.this.mContext.getMainLooper());
      this.mCallback = new WeakReference<>(callback);
    }

    @Override // com.samsung.android.aod.IAODDozeCallback
    public void onDozeAcquired() throws RemoteException {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.samsung.android.aod.AODManager.AODDozeCallbackDelegate.1
            @Override // java.lang.Runnable
            public void run() {
              AODDozeCallback callback =
                  (AODDozeCallback) AODDozeCallbackDelegate.this.mCallback.get();
              if (callback != null) {
                callback.onDozeAcquired();
              }
            }
          });
    }

    @Override // com.samsung.android.aod.IAODDozeCallback
    public void onDozeReleased() throws RemoteException {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.samsung.android.aod.AODManager.AODDozeCallbackDelegate.2
            @Override // java.lang.Runnable
            public void run() {
              AODDozeCallback callback =
                  (AODDozeCallback) AODDozeCallbackDelegate.this.mCallback.get();
              if (callback != null) {
                callback.onDozeReleased();
              }
            }
          });
    }

    @Override // com.samsung.android.aod.IAODDozeCallback
    public void onAODToastRequested(final AODToast toast) throws RemoteException {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.samsung.android.aod.AODManager.AODDozeCallbackDelegate.3
            @Override // java.lang.Runnable
            public void run() {
              AODDozeCallback callback =
                  (AODDozeCallback) AODDozeCallbackDelegate.this.mCallback.get();
              if (callback != null) {
                callback.onAODToastRequested(toast);
              }
            }
          });
    }
  }
}
