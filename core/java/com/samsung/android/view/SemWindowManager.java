package com.samsung.android.view;

import android.app.PendingIntent$$ExternalSyntheticLambda1;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.devicestate.DeviceStateManagerGlobal;
import android.hardware.devicestate.DeviceStateRequest;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class SemWindowManager {
  public static final int ACTION_BLOCK_KEY_EVENT = 4;
  public static final int ACTION_NOT_SET = 0;
  public static final int ACTION_SEND_BROADCAST = 2;
  public static final int ACTION_START_ACTIVITY = 1;
  public static final int ACTION_START_SERVICE = 3;
  public static final int APPLICATION_UI_LAST_ID = 2003;
  public static final int APP_CONTINUITY_MODE_APPLIED = 1;
  public static final int APP_CONTINUITY_MODE_NOT_APPLIED = 2;
  public static final int APP_CONTINUITY_MODE_RESTRICTED = 0;
  public static final int DISPATCHING = 0;
  public static final int FORCED_HIDE_CUTOUT_DEFAULT = -1;
  public static final int FORCED_HIDE_CUTOUT_OFF = 0;
  public static final int FORCED_HIDE_CUTOUT_ON = 1;
  public static final int FORCED_HIDE_CUTOUT_ON_WM_ONLY = 2;
  public static final int ID_APPLICATION_UI = 2000;
  public static final int ID_APPLICATION_UI_CAMERA = 2001;
  public static final int ID_APPLICATION_UI_TV_MODE = 2002;
  public static final int ID_DEFAULT = 1000;
  public static final int ID_GENERAL_APPLICATION = 2003;
  public static final int ID_KNOX_LEGACY = 50;
  public static final int ID_KNOX_MDM = 10;
  public static final int ID_KNOX_V2 = 30;
  public static final int ID_OLD_GOODLOCK_ROUTINE_PLUS = 900;
  public static final int ID_SETTING_UI = 1100;
  public static final int ID_SETTING_UI_B2B_DELTA = 951;
  public static final int ID_SETTING_UI_B2B_DELTA_OLD = 1102;
  public static final int ID_SETTING_UI_HIGH = 950;
  public static final int ID_SETTING_UI_MOUSE_BUTTON = 1107;
  public static final int ID_SETTING_UI_OLD_SOS_MESSAGE = 1105;
  public static final int ID_SETTING_UI_ONE_HAND_MODE = 1106;
  public static final int ID_SETTING_UI_SIDE_KEY = 1104;
  public static final int ID_SETTING_UI_XCOVER_TOP = 1103;
  public static final int KEY_CUSTOMIZATION_LAST_ID = 2003;
  public static final int KEY_PRESS_DOUBLE = 8;
  public static final int KEY_PRESS_DOWN = 1;
  public static final int KEY_PRESS_LONG = 4;
  public static final int KEY_PRESS_QUADRUPLE = 32;
  public static final int KEY_PRESS_QUINTUPLE = 64;
  public static final int KEY_PRESS_SINGLE = 3;
  public static final int KEY_PRESS_TRIPLE = 16;
  public static final int KEY_PRESS_UP = 2;

  @Deprecated public static final int MAX_ASPECT_RATIO_CUTOUT_OFF = 4;
  public static final int MAX_ASPECT_RATIO_FIXED_OFF = 3;
  public static final int MAX_ASPECT_RATIO_FIXED_ON = 2;
  public static final int MAX_ASPECT_RATIO_OFF = 0;
  public static final int MAX_ASPECT_RATIO_ON = 1;
  public static final int NO_DISPATCHING = -1;
  public static final int SETTING_UI_LAST_ID = 1107;
  public static final int SUPPORTS_DISPLAY_CUTOUT = 2;
  public static final int SUPPORTS_FLEX_MODE = 16;
  public static final int SUPPORTS_FLEX_PANEL_DISABLED = 2;
  public static final int SUPPORTS_FLEX_PANEL_ENABLED = 1;
  public static final int SUPPORTS_FLEX_PANEL_HOME_ACTIVITY = 32;
  public static final int SUPPORTS_FLEX_PANEL_RUNNABLE = 8;
  public static final int SUPPORTS_FLEX_PANEL_UNCHANGEABLE = 4;
  public static final int SUPPORTS_FLEX_PANEL_UNRESIZABLE_ACTIVITY = 64;
  public static final int SUPPORTS_MAX_ASPECT_RATIO = 1;
  private static final String TAG = "SemWindowManager";
  private static SemWindowManager sInstance;
  private final IWindowManager mWindowManager =
      IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
  private final WindowManagerGlobal mGlobal = WindowManagerGlobal.getInstance();
  private final DeviceStateManagerGlobal mDeviceStateManagerGlobal =
      DeviceStateManagerGlobal.getInstance();

  @Deprecated
  public interface FoldStateListener {
    @Deprecated
    void onFoldStateChanged(boolean z);

    @Deprecated
    void onTableModeChanged(boolean z);
  }

  private SemWindowManager() {}

  public static synchronized SemWindowManager getInstance() {
    SemWindowManager semWindowManager;
    synchronized (SemWindowManager.class) {
      if (sInstance == null) {
        sInstance = new SemWindowManager();
      }
      semWindowManager = sInstance;
    }
    return semWindowManager;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public static <BASE, T extends BASE> T castOrNull(BASE base, Class<T> cls) {
    if (cls.isInstance(base)) {
      return base;
    }
    return null;
  }

  public boolean requestSystemKeyEvent(int keyCode, ComponentName componentName, boolean request) {
    try {
      return this.mWindowManager.requestSystemKeyEvent(keyCode, componentName, request);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to request system keyevent, ", e);
      return false;
    }
  }

  public boolean isSystemKeyEventRequested(int keyCode, ComponentName componentName) {
    try {
      return this.mWindowManager.isSystemKeyEventRequested(keyCode, componentName);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to is system keyevent, ", e);
      return false;
    }
  }

  public void requestMetaKeyEvent(ComponentName componentName, boolean request) {
    try {
      this.mWindowManager.requestMetaKeyEvent(componentName, request);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to request meta keyevent, ", e);
    }
  }

  @Deprecated
  public void setStartingWindowContentView(ComponentName componentName, int resId) {}

  public void getInitialDisplaySize(Point size) {
    try {
      this.mWindowManager.getInitialDisplaySize(0, size);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getInitialDisplaySize", e);
    }
  }

  public int getInitialDensity() {
    try {
      return this.mWindowManager.getInitialDisplayDensity(0);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getInitialDisplayDensity", e);
      return -1;
    }
  }

  public void getUserDisplaySize(Point size) {
    try {
      this.mWindowManager.getUserDisplaySize(size);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getUserDisplaySize.", e);
    }
  }

  public int getUserDensity() {
    try {
      return this.mWindowManager.getUserDisplayDensity();
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getUserDisplayDensity", e);
      return -1;
    }
  }

  private void setForcedDisplaySizeDensityInner(
      int width, int height, int density, boolean saveToSettings, int forceHideCutout) {
    int userId = UserHandle.myUserId();
    Log.m94d(TAG, "setForcedDisplaySizeDensityInner userId=" + userId);
    if (!validateForcedDisplaySizeDensityValues(width, height, density)) {
      return;
    }
    try {
      MultiResolutionChangeRequestInfo info =
          new MultiResolutionChangeRequestInfo.Builder(0)
              .setWidth(width)
              .setHeight(height)
              .setDensity(density)
              .setSaveToSettings(saveToSettings)
              .setForcedHideCutout(forceHideCutout)
              .build();
      this.mWindowManager.setForcedDisplaySizeDensityWithInfo(info);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to setForcedDisplaySizeDensity", e);
    }
  }

  private boolean validateForcedDisplaySizeDensityValues(int width, int height, int density) {
    if (width == height) {
      Log.m102w(TAG, "validateForcedDisplaySizeDensityValues: width/height must be different");
      return false;
    }
    if (width <= 200 && height <= 200) {
      Log.m102w(TAG, "validateForcedDisplaySizeDensityValues: width/height must be > 200");
      return false;
    }
    if (density < 72) {
      Log.m102w(TAG, "validateForcedDisplaySizeDensityValues: density must be >= 72");
      return false;
    }
    return true;
  }

  public void setForcedDisplaySizeDensity(int width, int height, int density) {
    setForcedDisplaySizeDensityInner(width, height, density, false, -1);
  }

  public void setForcedDisplaySizeDensity(
      int width, int height, int density, boolean saveToSettings) {
    setForcedDisplaySizeDensityInner(width, height, density, saveToSettings, -1);
  }

  public void setForcedDisplaySizeDensity(int i, int i2, int i3, boolean z, boolean z2) {
    setForcedDisplaySizeDensityInner(i, i2, i3, z, z2 ? 1 : 0);
  }

  public void clearForcedDisplaySizeDensity() {
    int userId = UserHandle.myUserId();
    Log.m94d(TAG, "clearForcedDisplaySizeDensity userId=" + userId);
    try {
      this.mWindowManager.clearForcedDisplaySizeDensity(0);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to clearForcedDisplaySizeDensity", e);
    }
  }

  public void registerFoldStateListener(FoldStateListener listener, Handler handler) {
    if (listener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    this.mDeviceStateManagerGlobal.registerFoldStateListener(listener, handler);
  }

  public void unregisterFoldStateListener(FoldStateListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    this.mDeviceStateManagerGlobal.unregisterFoldStateListener(listener);
  }

  @Deprecated
  public boolean isFolded() {
    try {
      return this.mWindowManager.isFolded();
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to isFolded", e);
      return false;
    }
  }

  @Deprecated
  public boolean isTableMode() {
    try {
      return this.mWindowManager.isTableMode();
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to isTableMode", e);
      return false;
    }
  }

  public void setForcedDefaultDisplayDevice(int displayDeviceType) {
    if (displayDeviceType < 0 || displayDeviceType > 7) {
      Log.m96e(TAG, "displayDeviceType is wrong");
      return;
    }
    final int callingPid = Binder.getCallingPid();
    DeviceStateRequest deviceStateRequest = null;
    if (displayDeviceType == 0) {
      Log.m94d(TAG, "setForcedDefaultDisplayDevice main, callingPid=" + callingPid);
      this.mDeviceStateManagerGlobal.cancelStateRequest();
    } else if (displayDeviceType == 5) {
      deviceStateRequest = DeviceStateRequest.newBuilder(0).build();
    } else if (displayDeviceType == 6) {
      deviceStateRequest = DeviceStateRequest.newBuilder(5).build();
    } else if (displayDeviceType == 4) {
      deviceStateRequest = DeviceStateRequest.newBuilder(4).setFlags(4).build();
    } else if (displayDeviceType == 7) {
      deviceStateRequest = DeviceStateRequest.newBuilder(1).setFlags(8).build();
    }
    if (deviceStateRequest != null) {
      Log.m94d(
          TAG, "setForcedDefaultDisplayDevice " + displayDeviceType + ", callingPid=" + callingPid);
      this.mDeviceStateManagerGlobal.requestState(
          deviceStateRequest,
          new PendingIntent$$ExternalSyntheticLambda1(),
          new DeviceStateRequest
              .Callback() { // from class: com.samsung.android.view.SemWindowManager.1
            @Override // android.hardware.devicestate.DeviceStateRequest.Callback
            public void onRequestCanceled(DeviceStateRequest request) {
              Log.m94d(
                  SemWindowManager.TAG,
                  "onRequestCanceled,  pid=" + callingPid + " Callers=" + Debug.getCallers(5));
            }
          });
    }
  }

  public int getFullScreenAppsSupportMode() {
    try {
      return this.mWindowManager.getFullScreenAppsSupportMode();
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getFullScreenAppsSupportMode", e);
      return 0;
    }
  }

  public int getMaxAspectRatioPolicyByComponent(ComponentName componentName, int uid) {
    try {
      return this.mWindowManager.getMaxAspectRatioPolicyByComponent(componentName, uid);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getMaxAspectRatioPolicyByComponent", e);
      return 0;
    }
  }

  public int getMaxAspectRatioPolicy(String packageName, int uid) {
    try {
      return this.mWindowManager.getMaxAspectRatioPolicy(packageName, uid);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getMaxAspectRatioPolicy", e);
      return 0;
    }
  }

  public void setMaxAspectRatioPolicy(
      String packageName, int uid, boolean enable, int restartTaskId) {
    try {
      this.mWindowManager.setMaxAspectRatioPolicy(packageName, uid, enable, restartTaskId);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to setMaxAspectRatioPolicy", e);
    }
  }

  public int getAppContinuityMode(String packageName, ActivityInfo aInfo, int userId) {
    try {
      return this.mWindowManager.getAppContinuityMode(userId, packageName, aInfo);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getAppContinuityMode", e);
      return 0;
    }
  }

  public void setAppContinuityMode(String packageName, int userId, boolean applied) {
    try {
      this.mWindowManager.setAppContinuityMode(userId, packageName, applied);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to setAppContinuityMode", e);
    }
  }

  public static class VisibleWindowInfo implements Parcelable {
    public static final Parcelable.Creator<VisibleWindowInfo> CREATOR =
        new Parcelable.Creator<
            VisibleWindowInfo>() { // from class:
                                   // com.samsung.android.view.SemWindowManager.VisibleWindowInfo.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public VisibleWindowInfo createFromParcel(Parcel source) {
            return new VisibleWindowInfo(source);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public VisibleWindowInfo[] newArray(int size) {
            return new VisibleWindowInfo[size];
          }
        };
    public boolean focused;
    public boolean lastFocused;
    public String name;
    public String packageName;
    public int type;

    public VisibleWindowInfo() {}

    @Override // android.os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(this.packageName);
      parcel.writeString(this.name);
      parcel.writeInt(this.type);
      parcel.writeInt(this.focused ? 1 : 0);
      parcel.writeInt(this.lastFocused ? 1 : 0);
    }

    public void readFromParcel(Parcel source) {
      this.packageName = source.readString();
      this.name = source.readString();
      this.type = source.readInt();
      this.focused = source.readInt() != 0;
      this.lastFocused = source.readInt() != 0;
    }

    private VisibleWindowInfo(Parcel source) {
      readFromParcel(source);
    }
  }

  @Deprecated
  public List<VisibleWindowInfo> getVisibleWindowInfo() {
    return new ArrayList();
  }

  public List<VisibleWindowInfo> getVisibleWindowInfoList() {
    try {
      return this.mWindowManager.getVisibleWindowInfoList();
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getVisibleWindowInfoList", e);
      return null;
    }
  }

  public Bitmap screenshot(
      int displayId,
      int targetWindowType,
      boolean containsTargetSystemWindow,
      Rect sourceCrop,
      int width,
      int height,
      boolean useIdentityTransform) {
    return screenshot(
        displayId,
        targetWindowType,
        containsTargetSystemWindow,
        sourceCrop,
        width,
        height,
        useIdentityTransform,
        0);
  }

  public Bitmap screenshot(
      int displayId,
      int targetWindowType,
      boolean containsTargetSystemWindow,
      Rect sourceCrop,
      int width,
      int height,
      boolean useIdentityTransform,
      int rotation) {
    return screenshot(
        displayId,
        targetWindowType,
        containsTargetSystemWindow,
        sourceCrop,
        width,
        height,
        useIdentityTransform,
        rotation,
        false);
  }

  public Bitmap screenshot(
      int displayId,
      int targetWindowType,
      boolean containsTargetSystemWindow,
      Rect sourceCrop,
      int width,
      int height,
      boolean useIdentityTransform,
      int rotation,
      boolean ignorePolicy) {
    try {
      ScreenshotResult result =
          this.mWindowManager.takeScreenshotToTargetWindow(
              displayId,
              targetWindowType,
              containsTargetSystemWindow,
              sourceCrop,
              width,
              height,
              useIdentityTransform,
              ignorePolicy);
      if (result != null) {
        return result.getCapturedBitmap();
      }
      return null;
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to screenshot", e);
      return null;
    }
  }

  public int getSupportsFlexPanel(int userId, String packageName) {
    try {
      return this.mWindowManager.getSupportsFlexPanel(userId, packageName);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getSupportsFlexPanel", e);
      return 2;
    }
  }

  public void setSupportsFlexPanel(int userId, String packageName, boolean isSupportsFlexPanel) {
    try {
      this.mWindowManager.setSupportsFlexPanel(userId, packageName, isSupportsFlexPanel);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to setSupportsFlexPanel", e);
    }
  }

  public static final class KeyCustomizationInfo implements Parcelable {
    public static final Parcelable.Creator<KeyCustomizationInfo> CREATOR =
        new Parcelable.Creator<
            KeyCustomizationInfo>() { // from class:
                                      // com.samsung.android.view.SemWindowManager.KeyCustomizationInfo.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public KeyCustomizationInfo createFromParcel(Parcel source) {
            return new KeyCustomizationInfo(source);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public KeyCustomizationInfo[] newArray(int size) {
            return new KeyCustomizationInfo[size];
          }
        };
    public int action;
    public int dispatching;

    /* renamed from: id */
    public int f3073id;
    public Intent intent;
    public int keyCode;
    public long longPressTimeout;
    public long multiPressTimeout;
    public String ownerPackage;
    public int press;
    public int userId;

    public KeyCustomizationInfo() {
      this.press = -1;
      this.f3073id = 1000;
      this.keyCode = 0;
      this.action = -1;
      this.intent = null;
      this.dispatching = 0;
      this.userId = -2;
    }

    public KeyCustomizationInfo(int press, int id, int keyCode, int action) {
      this(press, id, keyCode, action, null);
    }

    public KeyCustomizationInfo(int press, int id, int keyCode, int action, Intent intent) {
      this(press, id, keyCode, action, intent, -1);
    }

    public KeyCustomizationInfo(
        int press, int id, int keyCode, int action, Intent intent, int dispatching) {
      this(press, id, keyCode, action, intent, dispatching, -2, null);
    }

    public KeyCustomizationInfo(
        int press, int id, int keyCode, int action, Intent intent, int dispatching, int userId) {
      this(press, id, keyCode, action, intent, dispatching, userId, null);
    }

    private KeyCustomizationInfo(Builder builder) {
      this(
          builder.press,
          builder.f3074id,
          builder.keyCode,
          builder.action,
          builder.intent,
          builder.dispatching,
          builder.userId,
          builder.ownerPackage);
    }

    private KeyCustomizationInfo(
        int press,
        int id,
        int keyCode,
        int action,
        Intent intent,
        int dispatching,
        int userId,
        String ownerPackage) {
      this.press = -1;
      this.f3073id = 1000;
      this.keyCode = 0;
      this.action = -1;
      this.intent = null;
      this.dispatching = 0;
      this.userId = -2;
      this.press = press;
      this.f3073id = id;
      this.keyCode = keyCode;
      this.action = action;
      this.intent = intent;
      this.dispatching = dispatching;
      this.userId = userId;
      this.ownerPackage = ownerPackage;
    }

    public void setLongPressTimeoutMs(long longPressTimeout) {
      this.longPressTimeout = longPressTimeout;
    }

    public void setMultiPressTimeoutMs(long multiPressTimeout) {
      this.multiPressTimeout = multiPressTimeout;
    }

    public int getId() {
      return this.f3073id;
    }

    public int getPress() {
      return this.press;
    }

    public int getKeyCode() {
      return this.keyCode;
    }

    public int getAction() {
      return this.action;
    }

    public Intent getIntent() {
      return this.intent;
    }

    public int getDispatching() {
      return this.dispatching;
    }

    public int getUserId() {
      return this.userId;
    }

    public String getOwnerPackage() {
      return this.ownerPackage;
    }

    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("press=").append(this.press);
      stringBuilder.append(", id=").append(this.f3073id);
      stringBuilder.append(", keyCode=").append(this.keyCode);
      stringBuilder.append(", action=").append(this.action);
      if (this.intent != null) {
        stringBuilder.append(", intent=").append(this.intent.toString());
      }
      if (this.dispatching != 0) {
        stringBuilder.append(", dispatching=").append(this.dispatching);
      }
      if (this.userId != -2) {
        stringBuilder.append(", userId=").append(this.userId);
      }
      if (!TextUtils.isEmpty(this.ownerPackage)) {
        stringBuilder.append(", ownerPackage=").append(this.ownerPackage);
      }
      return stringBuilder.toString();
    }

    public static class Builder {
      private int action;

      /* renamed from: id */
      private int f3074id;
      private Intent intent;
      private int keyCode;
      private String ownerPackage;
      private int press;
      private int dispatching = 0;
      private int userId = -2;

      public Builder(int press, int keyCode, int action, Intent intent, String ownerPackage) {
        this.press = -1;
        this.f3074id = -1;
        this.keyCode = 0;
        this.action = -1;
        this.intent = null;
        this.ownerPackage = null;
        if (action != 4 && intent == null) {
          throw new IllegalArgumentException(
              "Intent is null. When the action is not ACTION_BLOCK_KEY_EVENT, you have to add"
                  + " intent parameter.");
        }
        this.press = press;
        this.keyCode = keyCode;
        this.action = action;
        this.intent = intent;
        this.ownerPackage = ownerPackage;
        if (!TextUtils.isEmpty(ownerPackage)) {
          this.f3074id = 2003;
        }
      }

      public KeyCustomizationInfo build() {
        return new KeyCustomizationInfo(this);
      }

      public Builder setDispatching(int dispatching) {
        this.dispatching = dispatching;
        return this;
      }

      public Builder setUserId(int userId) {
        this.userId = userId;
        return this;
      }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.press);
      dest.writeInt(this.f3073id);
      dest.writeInt(this.keyCode);
      dest.writeInt(this.action);
      dest.writeTypedObject(this.intent, flags);
      dest.writeInt(this.dispatching);
      dest.writeInt(this.userId);
      dest.writeLong(this.longPressTimeout);
      dest.writeLong(this.multiPressTimeout);
      dest.writeString(this.ownerPackage);
    }

    public void readFromParcel(Parcel source) {
      this.press = source.readInt();
      this.f3073id = source.readInt();
      this.keyCode = source.readInt();
      this.action = source.readInt();
      this.intent = (Intent) source.readTypedObject(Intent.CREATOR);
      this.dispatching = source.readInt();
      this.userId = source.readInt();
      this.longPressTimeout = source.readLong();
      this.multiPressTimeout = source.readLong();
      this.ownerPackage = source.readString();
    }

    private KeyCustomizationInfo(Parcel source) {
      this.press = -1;
      this.f3073id = 1000;
      this.keyCode = 0;
      this.action = -1;
      this.intent = null;
      this.dispatching = 0;
      this.userId = -2;
      readFromParcel(source);
    }
  }

  public void putKeyCustomizationInfo(KeyCustomizationInfo keyCustomizationInfo)
      throws IllegalArgumentException {
    try {
      this.mWindowManager.putKeyCustomizationInfo(keyCustomizationInfo);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to putKeyCustomizationInfo", e);
    }
  }

  public KeyCustomizationInfo getKeyCustomizationInfo(String ownerPackage, int press, int keyCode) {
    try {
      return this.mWindowManager.getKeyCustomizationInfoByPackage(ownerPackage, press, keyCode);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getKeyCustomizationInfo", e);
      return null;
    }
  }

  public KeyCustomizationInfo getLastKeyCustomizationInfo(int press, int keyCode) {
    try {
      return this.mWindowManager.getLastKeyCustomizationInfo(press, keyCode);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getKeyCustomizationInfo", e);
      return null;
    }
  }

  public void removeKeyCustomizationInfo(String ownerPackage, int press, int keyCode) {
    try {
      this.mWindowManager.removeKeyCustomizationInfoByPackage(ownerPackage, press, keyCode);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to removeKeyCustomizationInfo", e);
    }
  }
}
