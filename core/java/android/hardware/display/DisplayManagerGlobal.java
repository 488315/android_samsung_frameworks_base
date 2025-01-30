package android.hardware.display;

import android.app.ActivityThread;
import android.app.PropertyInvalidatedCache;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.OverlayProperties;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.media.projection.IMediaProjection;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.sec.clipboard.data.ClipboardConstants;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayAdjustments;
import android.view.DisplayInfo;
import android.view.Surface;
import com.android.server.LocalServices;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
public final class DisplayManagerGlobal {
  public static final String CACHE_KEY_DISPLAY_INFO_PROPERTY = "cache_key.display_info";
  private static final boolean DEBUG = false;
  public static final int EVENT_CONNECTIONSTATUS_CHANGED = 6;
  public static final int EVENT_DISPLAY_ADDED = 1;
  public static final int EVENT_DISPLAY_BRIGHTNESS_CHANGED = 4;
  public static final int EVENT_DISPLAY_CHANGED = 2;
  public static final int EVENT_DISPLAY_HDR_SDR_RATIO_CHANGED = 5;
  public static final int EVENT_DISPLAY_REMOVED = 3;
  public static final int EVENT_REMOTE_DISPLAY_ROTATION_CHANGED = 8;
  public static final int EVENT_REMOTE_DISPLAY_STATE_CHANGED = 7;
  public static final int EVENT_VOLUME_KEY_DOWN = 10;
  public static final int EVENT_VOLUME_KEY_UP = 11;
  public static final int EVENT_VOLUME_LEVEL_CHANGED = 9;
  public static final int EVENT_VOLUME_MUTE = 12;
  public static final int EVENT_VOLUME_UNMUTE = 13;
  public static final int EVENT_WIFIDISPLAY_PARAMETERS_CHANGED = 17;
  private static final String TAG = "DisplayManager";
  private static final boolean USE_CACHE = false;
  private static DisplayManagerGlobal sInstance;
  private DisplayManagerCallback mCallback;
  private int[] mDisplayIdCache;
  private final IDisplayManager mDm;
  private float mNativeCallbackReportedRefreshRate;
  private final OverlayProperties mOverlayProperties;
  private final ColorSpace mWideColorSpace;
  private WifiDisplayConnectionCallback mWifiDisplayConnectionCallback;
  private int mWifiDisplayScanNestCount;
  private boolean mDispatchNativeCallbacks = false;
  private final Object mLock = new Object();
  private long mRegisteredEventsMask = 0;
  private final CopyOnWriteArrayList<DisplayListenerDelegate> mDisplayListeners =
      new CopyOnWriteArrayList<>();
  private final ArrayList<DisplayVolumeListenerDelegate> mDisplayVolumeListeners =
      new ArrayList<>();
  private final ArrayList<DisplayVolumeKeyListenerDelegate> mDisplayVolumeKeyListeners =
      new ArrayList<>();
  private final ArrayList<WifiDisplayParameterListenerDelegate> mWifiDisplayParameterListeners =
      new ArrayList<>();
  private final ArrayList<DeviceListenerDelegate> mDeviceListeners = new ArrayList<>();
  private final SparseArray<DisplayInfo> mDisplayInfoCache = new SparseArray<>();
  private PropertyInvalidatedCache<Integer, DisplayInfo> mDisplayCache =
      new PropertyInvalidatedCache<Integer, DisplayInfo>(
          8,
          CACHE_KEY_DISPLAY_INFO_PROPERTY) { // from class:
                                             // android.hardware.display.DisplayManagerGlobal.1
        @Override // android.app.PropertyInvalidatedCache
        public DisplayInfo recompute(Integer id) {
          try {
            return DisplayManagerGlobal.this.mDm.getDisplayInfo(id.intValue());
          } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
          }
        }
      };

  @Retention(RetentionPolicy.SOURCE)
  public @interface DisplayEvent {}

  private static native void nSignalNativeCallbacks(float f);

  public DisplayManagerGlobal(IDisplayManager dm) {
    this.mDm = dm;
    try {
      this.mWideColorSpace =
          ColorSpace.get(ColorSpace.Named.values()[dm.getPreferredWideGamutColorSpaceId()]);
      this.mOverlayProperties = dm.getOverlaySupport();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public static DisplayManagerGlobal getInstance() {
    DisplayManagerGlobal displayManagerGlobal;
    IBinder b;
    synchronized (DisplayManagerGlobal.class) {
      if (sInstance == null && (b = ServiceManager.getService(Context.DISPLAY_SERVICE)) != null) {
        sInstance = new DisplayManagerGlobal(IDisplayManager.Stub.asInterface(b));
      }
      displayManagerGlobal = sInstance;
    }
    return displayManagerGlobal;
  }

  public DisplayInfo getDisplayInfo(int displayId) {
    DisplayInfo displayInfoLocked;
    synchronized (this.mLock) {
      displayInfoLocked = getDisplayInfoLocked(displayId);
    }
    return displayInfoLocked;
  }

  private DisplayInfo getDisplayInfoLocked(int displayId) {
    DisplayInfo info = null;
    PropertyInvalidatedCache<Integer, DisplayInfo> propertyInvalidatedCache = this.mDisplayCache;
    if (propertyInvalidatedCache != null) {
      DisplayInfo info2 = propertyInvalidatedCache.query(Integer.valueOf(displayId));
      info = info2;
    } else {
      try {
        info = this.mDm.getDisplayInfo(displayId);
      } catch (RemoteException ex) {
        ex.rethrowFromSystemServer();
      }
    }
    if (info == null) {
      return null;
    }
    if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
      ActivityThread thread = ActivityThread.currentActivityThread();
      Configuration currentConfig = thread != null ? thread.getConfiguration() : null;
      if (CompatSandbox.isDisplaySandboxingEnabled(currentConfig)) {
        Rect appBounds = currentConfig.windowConfiguration.getAppBounds();
        info.appWidth = appBounds.width();
        info.appHeight = appBounds.height();
        Rect maxBounds = currentConfig.windowConfiguration.getMaxBounds();
        info.logicalWidth = maxBounds.width();
        info.logicalHeight = maxBounds.height();
      }
    }
    ActivityThread thread2 = ActivityThread.currentActivityThread();
    if (thread2 != null && thread2.isDexCompatMode()) {
      Configuration threadConfig = thread2.getConfiguration();
      int windowingMode = threadConfig.windowConfiguration.getWindowingMode();
      if (windowingMode == 5
          && threadConfig.densityDpi != 0
          && threadConfig.screenWidthDp != 0
          && threadConfig.screenHeightDp != 0) {
        float density = threadConfig.densityDpi * 0.00625f;
        int width = (int) ((threadConfig.screenWidthDp * density) + 0.5f);
        int height = (int) ((threadConfig.screenHeightDp * density) + 0.5f);
        info.logicalWidth = width;
        info.appWidth = width;
        info.logicalHeight = height;
        info.appHeight = height;
      }
    }
    registerCallbackIfNeededLocked();
    return info;
  }

  public int[] getDisplayIds() {
    return getDisplayIds(false);
  }

  public int[] getDisplayIds(boolean includeDisabled) {
    int[] displayIds;
    try {
      synchronized (this.mLock) {
        displayIds = this.mDm.getDisplayIds(includeDisabled);
        registerCallbackIfNeededLocked();
      }
      return displayIds;
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public boolean isUidPresentOnDisplay(int uid, int displayId) {
    try {
      return this.mDm.isUidPresentOnDisplay(uid, displayId);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public Display getCompatibleDisplay(int displayId, DisplayAdjustments daj) {
    DisplayInfo displayInfo = getDisplayInfo(displayId);
    if (displayInfo == null) {
      return null;
    }
    return new Display(this, displayId, displayInfo, daj);
  }

  public Display getCompatibleDisplay(int displayId, Resources resources) {
    DisplayInfo displayInfo = getDisplayInfo(displayId);
    if (displayInfo == null) {
      return null;
    }
    return new Display(this, displayId, displayInfo, resources);
  }

  public Display getRealDisplay(int displayId) {
    return getCompatibleDisplay(displayId, DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS);
  }

  public void registerDisplayListener(
      DisplayManager.DisplayListener listener, Handler handler, long eventsMask) {
    Looper looper = getLooperForHandler(handler);
    Handler springBoard = new Handler(looper);
    registerDisplayListener(listener, new HandlerExecutor(springBoard), eventsMask);
  }

  public void registerDisplayListener(
      DisplayManager.DisplayListener listener, Executor executor, long eventsMask) {
    if (listener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    if (eventsMask == 0) {
      throw new IllegalArgumentException("The set of events to listen to must not be empty.");
    }
    synchronized (this.mLock) {
      int index = findDisplayListenerLocked(listener);
      if (index < 0) {
        this.mDisplayListeners.add(new DisplayListenerDelegate(listener, executor, eventsMask));
        registerCallbackIfNeededLocked();
      } else {
        this.mDisplayListeners.get(index).setEventsMask(eventsMask);
      }
      updateCallbackIfNeededLocked();
    }
  }

  public void unregisterDisplayListener(DisplayManager.DisplayListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    synchronized (this.mLock) {
      int index = findDisplayListenerLocked(listener);
      if (index >= 0) {
        DisplayListenerDelegate d = this.mDisplayListeners.get(index);
        d.clearEvents();
        this.mDisplayListeners.remove(index);
        updateCallbackIfNeededLocked();
      }
    }
  }

  private static Looper getLooperForHandler(Handler handler) {
    Looper looper = handler != null ? handler.getLooper() : Looper.myLooper();
    if (looper == null) {
      looper = Looper.getMainLooper();
    }
    if (looper == null) {
      throw new RuntimeException("Could not get Looper for the UI thread.");
    }
    return looper;
  }

  private int findDisplayListenerLocked(DisplayManager.DisplayListener listener) {
    int numListeners = this.mDisplayListeners.size();
    for (int i = 0; i < numListeners; i++) {
      if (this.mDisplayListeners.get(i).mListener == listener) {
        return i;
      }
    }
    return -1;
  }

  private int calculateEventsMaskLocked() {
    int mask = 0;
    int numListeners = this.mDisplayListeners.size();
    for (int i = 0; i < numListeners; i++) {
      mask = (int) (mask | this.mDisplayListeners.get(i).mEventsMask);
    }
    if (this.mDispatchNativeCallbacks) {
      return (int) (mask | 7);
    }
    return mask;
  }

  private void registerCallbackIfNeededLocked() {
    if (this.mCallback == null) {
      this.mCallback = new DisplayManagerCallback();
      updateCallbackIfNeededLocked();
    }
  }

  private void updateCallbackIfNeededLocked() {
    int mask = calculateEventsMaskLocked();
    if (mask != this.mRegisteredEventsMask) {
      try {
        this.mDm.registerCallbackWithEventMask(this.mCallback, mask);
        this.mRegisteredEventsMask = mask;
      } catch (RemoteException ex) {
        throw ex.rethrowFromSystemServer();
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void handleDisplayEvent(int displayId, int event) {
    DisplayInfo info;
    DisplayInfo display;
    synchronized (this.mLock) {
      info = getDisplayInfoLocked(displayId);
      if (event == 2
          && this.mDispatchNativeCallbacks
          && displayId == 0
          && (display = getDisplayInfoLocked(displayId)) != null
          && this.mNativeCallbackReportedRefreshRate != display.getRefreshRate()) {
        float refreshRate = display.getRefreshRate();
        this.mNativeCallbackReportedRefreshRate = refreshRate;
        nSignalNativeCallbacks(refreshRate);
      }
    }
    Iterator<DisplayListenerDelegate> it = this.mDisplayListeners.iterator();
    while (it.hasNext()) {
      DisplayListenerDelegate listener = it.next();
      listener.sendDisplayEvent(displayId, event, info);
    }
  }

  public void startWifiDisplayScan() {
    synchronized (this.mLock) {
      registerCallbackIfNeededLocked();
      try {
        this.mDm.startWifiDisplayScan();
      } catch (RemoteException ex) {
        throw ex.rethrowFromSystemServer();
      }
    }
  }

  public void startWifiDisplayScanAutoP2P() {
    synchronized (this.mLock) {
      registerCallbackIfNeededLocked();
      try {
        this.mDm.startWifiDisplayScanAutoP2P();
      } catch (RemoteException ex) {
        throw ex.rethrowFromSystemServer();
      }
    }
  }

  public void stopWifiDisplayScan() {
    synchronized (this.mLock) {
      try {
        try {
          this.mDm.stopWifiDisplayScan();
        } catch (RemoteException ex) {
          throw ex.rethrowFromSystemServer();
        }
      } catch (Throwable th) {
        throw th;
      }
    }
  }

  public void connectWifiDisplay(String deviceAddress) {
    if (deviceAddress == null) {
      throw new IllegalArgumentException("deviceAddress must not be null");
    }
    try {
      this.mDm.connectWifiDisplay(deviceAddress);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void pauseWifiDisplay() {
    try {
      this.mDm.pauseWifiDisplay();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void resumeWifiDisplay() {
    try {
      this.mDm.resumeWifiDisplay();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void disconnectWifiDisplay() {
    try {
      this.mDm.disconnectWifiDisplay();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void renameWifiDisplay(String deviceAddress, String alias) {
    if (deviceAddress == null) {
      throw new IllegalArgumentException("deviceAddress must not be null");
    }
    try {
      this.mDm.renameWifiDisplay(deviceAddress, alias);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void forgetWifiDisplay(String deviceAddress) {
    if (deviceAddress == null) {
      throw new IllegalArgumentException("deviceAddress must not be null");
    }
    try {
      this.mDm.forgetWifiDisplay(deviceAddress);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public WifiDisplayStatus getWifiDisplayStatus() {
    try {
      return this.mDm.getWifiDisplayStatus();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setUserDisabledHdrTypes(int[] userDisabledHdrTypes) {
    try {
      this.mDm.setUserDisabledHdrTypes(userDisabledHdrTypes);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setAreUserDisabledHdrTypesAllowed(boolean areUserDisabledHdrTypesAllowed) {
    try {
      this.mDm.setAreUserDisabledHdrTypesAllowed(areUserDisabledHdrTypesAllowed);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public boolean areUserDisabledHdrTypesAllowed() {
    try {
      return this.mDm.areUserDisabledHdrTypesAllowed();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public int[] getUserDisabledHdrTypes() {
    try {
      return this.mDm.getUserDisabledHdrTypes();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void overrideHdrTypes(int displayId, int[] modes) {
    try {
      this.mDm.overrideHdrTypes(displayId, modes);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void requestColorMode(int displayId, int colorMode) {
    try {
      this.mDm.requestColorMode(displayId, colorMode);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public VirtualDisplay createSpegVirtualDisplay(String packageName, int uid) {
    if (!CoreRune.SYSFW_APP_SPEG) {
      return null;
    }
    VirtualDisplayCallback callbackWrapper = new VirtualDisplayCallback(null, null);
    DisplayManagerInternal dmi =
        (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
    int displayId = dmi.createSpegVirtualDisplay(packageName, uid, callbackWrapper);
    if (displayId < 0) {
      Log.m96e(DisplayManager.TAG_SPEG, "Could not create speg display for " + packageName);
      return null;
    }
    Display display = getRealDisplay(displayId);
    if (display == null) {
      Log.wtf(
          DisplayManager.TAG_SPEG, "Could not obtain display info for created display: " + display);
      try {
        this.mDm.releaseVirtualDisplay(callbackWrapper);
        return null;
      } catch (RemoteException ex) {
        throw ex.rethrowFromSystemServer();
      }
    }
    VirtualDisplay vd = new VirtualDisplay(this, display, callbackWrapper, null);
    vd.setDisplayState(true);
    return vd;
  }

  public VirtualDisplay createVirtualDisplay(
      Context context,
      MediaProjection projection,
      VirtualDisplayConfig virtualDisplayConfig,
      VirtualDisplay.Callback callback,
      Executor executor) {
    VirtualDisplayCallback callbackWrapper = new VirtualDisplayCallback(callback, executor);
    IMediaProjection projectionToken = projection != null ? projection.getProjection() : null;
    try {
      int displayId =
          this.mDm.createVirtualDisplay(
              virtualDisplayConfig, callbackWrapper, projectionToken, context.getPackageName());
      return createVirtualDisplayWrapper(virtualDisplayConfig, callbackWrapper, displayId);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public VirtualDisplay createVirtualDisplayWrapper(
      VirtualDisplayConfig virtualDisplayConfig,
      IVirtualDisplayCallback callbackWrapper,
      int displayId) {
    if (displayId < 0) {
      Log.m96e(TAG, "Could not create virtual display: " + virtualDisplayConfig.getName());
      return null;
    }
    Display display = getRealDisplay(displayId);
    if (display == null) {
      Log.wtf(
          TAG,
          "Could not obtain display info for newly created virtual display: "
              + virtualDisplayConfig.getName());
      try {
        this.mDm.releaseVirtualDisplay(callbackWrapper);
        return null;
      } catch (RemoteException ex) {
        throw ex.rethrowFromSystemServer();
      }
    }
    return new VirtualDisplay(this, display, callbackWrapper, virtualDisplayConfig.getSurface());
  }

  public void setVirtualDisplaySurface(IVirtualDisplayCallback token, Surface surface) {
    try {
      this.mDm.setVirtualDisplaySurface(token, surface);
      setVirtualDisplayState(token, surface != null);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void resizeVirtualDisplay(
      IVirtualDisplayCallback token, int width, int height, int densityDpi) {
    try {
      this.mDm.resizeVirtualDisplay(token, width, height, densityDpi);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void releaseVirtualDisplay(IVirtualDisplayCallback token) {
    try {
      this.mDm.releaseVirtualDisplay(token);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  void setVirtualDisplayState(IVirtualDisplayCallback token, boolean isOn) {
    try {
      this.mDm.setVirtualDisplayState(token, isOn);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public Point getStableDisplaySize() {
    try {
      return this.mDm.getStableDisplaySize();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public List<BrightnessChangeEvent> getBrightnessEvents(String callingPackage) {
    try {
      ParceledListSlice<BrightnessChangeEvent> events =
          this.mDm.getBrightnessEvents(callingPackage);
      if (events == null) {
        return Collections.emptyList();
      }
      return events.getList();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public BrightnessInfo getBrightnessInfo(int displayId) {
    try {
      return this.mDm.getBrightnessInfo(displayId);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public ColorSpace getPreferredWideGamutColorSpace() {
    return this.mWideColorSpace;
  }

  public OverlayProperties getOverlaySupport() {
    return this.mOverlayProperties;
  }

  public void setBrightnessConfigurationForUser(
      BrightnessConfiguration c, int userId, String packageName) {
    try {
      this.mDm.setBrightnessConfigurationForUser(c, userId, packageName);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setBrightnessConfigurationForDisplay(
      BrightnessConfiguration c, String uniqueDisplayId, int userId, String packageName) {
    try {
      this.mDm.setBrightnessConfigurationForDisplay(c, uniqueDisplayId, userId, packageName);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public BrightnessConfiguration getBrightnessConfigurationForDisplay(
      String uniqueDisplayId, int userId) {
    try {
      return this.mDm.getBrightnessConfigurationForDisplay(uniqueDisplayId, userId);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public BrightnessConfiguration getBrightnessConfigurationForUser(int userId) {
    try {
      return this.mDm.getBrightnessConfigurationForUser(userId);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public BrightnessConfiguration getDefaultBrightnessConfiguration() {
    try {
      return this.mDm.getDefaultBrightnessConfiguration();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public boolean isMinimalPostProcessingRequested(int displayId) {
    try {
      return this.mDm.isMinimalPostProcessingRequested(displayId);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setTemporaryBrightness(int displayId, float brightness) {
    try {
      this.mDm.setTemporaryBrightness(displayId, brightness);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setBrightness(int displayId, float brightness) {
    try {
      this.mDm.setBrightness(displayId, brightness);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public DisplayDecorationSupport getDisplayDecorationSupport(int displayId) {
    try {
      return this.mDm.getDisplayDecorationSupport(displayId);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public float getBrightness(int displayId) {
    try {
      return this.mDm.getBrightness(displayId);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setTemporaryAutoBrightnessAdjustment(float adjustment) {
    try {
      this.mDm.setTemporaryAutoBrightnessAdjustment(adjustment);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public Pair<float[], float[]> getMinimumBrightnessCurve() {
    try {
      Curve curve = this.mDm.getMinimumBrightnessCurve();
      return Pair.create(curve.getX(), curve.getY());
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public List<AmbientBrightnessDayStats> getAmbientBrightnessStats() {
    try {
      ParceledListSlice<AmbientBrightnessDayStats> stats = this.mDm.getAmbientBrightnessStats();
      if (stats == null) {
        return Collections.emptyList();
      }
      return stats.getList();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setUserPreferredDisplayMode(int displayId, Display.Mode mode) {
    try {
      this.mDm.setUserPreferredDisplayMode(displayId, mode);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public Display.Mode getUserPreferredDisplayMode(int displayId) {
    try {
      return this.mDm.getUserPreferredDisplayMode(displayId);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public Display.Mode getSystemPreferredDisplayMode(int displayId) {
    try {
      return this.mDm.getSystemPreferredDisplayMode(displayId);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setHdrConversionMode(HdrConversionMode hdrConversionMode) {
    try {
      this.mDm.setHdrConversionMode(hdrConversionMode);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public HdrConversionMode getHdrConversionModeSetting() {
    try {
      return this.mDm.getHdrConversionModeSetting();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public HdrConversionMode getHdrConversionMode() {
    try {
      return this.mDm.getHdrConversionMode();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public int[] getSupportedHdrOutputTypes() {
    try {
      return this.mDm.getSupportedHdrOutputTypes();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setShouldAlwaysRespectAppRequestedMode(boolean enabled) {
    try {
      this.mDm.setShouldAlwaysRespectAppRequestedMode(enabled);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public boolean shouldAlwaysRespectAppRequestedMode() {
    try {
      return this.mDm.shouldAlwaysRespectAppRequestedMode();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setRefreshRateSwitchingType(int newValue) {
    try {
      this.mDm.setRefreshRateSwitchingType(newValue);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public int getRefreshRateSwitchingType() {
    try {
      return this.mDm.getRefreshRateSwitchingType();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void rotateVirtualDisplay(IVirtualDisplayCallback token, int rotation) {
    try {
      this.mDm.rotateVirtualDisplay(token, rotation);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void connectWifiDisplay(
      SemWifiDisplayConfig wifidisplayConfig,
      DisplayManager.SemWifiDisplayConnectionCallback callback,
      Handler handler) {
    WifiDisplayConnectionCallback wifiDisplayConnectionCallback;
    if (callback == null) {
      wifiDisplayConnectionCallback = null;
    } else {
      try {
        wifiDisplayConnectionCallback = new WifiDisplayConnectionCallback(callback, handler);
      } catch (RemoteException ex) {
        throw ex.rethrowFromSystemServer();
      }
    }
    this.mWifiDisplayConnectionCallback = wifiDisplayConnectionCallback;
    this.mDm.connectWifiDisplayWithConfig(wifidisplayConfig, wifiDisplayConnectionCallback);
  }

  public void startWifiDisplayScan(int scanChannel) {
    try {
      this.mDm.startWifiDisplayChannelScan(scanChannel);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed startWifiDisplayChannelScan ", ex);
    }
  }

  public void startWifiDisplayScan(int scanChannel, int interval) {
    try {
      this.mDm.startWifiDisplayChannelScanAndInterval(scanChannel, interval);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed startWifiDisplayChannelScan ", ex);
    }
  }

  public int getScreenSharingStatus() {
    try {
      return this.mDm.getScreenSharingStatus();
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed getScreenSharingStatus ", ex);
      return -1;
    }
  }

  public void setScreenSharingStatus(int status) {
    try {
      this.mDm.setScreenSharingStatus(status);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed setScreenSharingStatus ", ex);
    }
  }

  public void setDlnaDevice(SemDlnaDevice dlnaDevice) {
    IBinder binder = new Binder();
    try {
      this.mDm.setDlnaDevice(dlnaDevice, binder);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed setDlnaDevice ", ex);
    }
  }

  public SemDlnaDevice getDlnaDevice() {
    try {
      return this.mDm.getDlnaDevice();
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed getDlnaDevice ", ex);
      return new SemDlnaDevice();
    }
  }

  public void setDeviceVolume(int volume) {
    try {
      this.mDm.setDeviceVolume(volume);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed setDeviceVolume ", ex);
    }
  }

  public void setDeviceVolumeMuted(boolean muted) {
    try {
      this.mDm.setDeviceVolumeMuted(muted);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed setDeviceVolumeMuted ", ex);
    }
  }

  public void setVolumeKeyEvent(int event) {
    try {
      this.mDm.setVolumeKeyEvent(event);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed setVolumeKeyEvent ", ex);
    }
  }

  public int getDeviceMaxVolume() {
    try {
      return this.mDm.getDeviceMaxVolume();
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed getDeviceMaxVolume ", ex);
      return -1;
    }
  }

  public int getDeviceMinVolume() {
    try {
      return this.mDm.getDeviceMinVolume();
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed getDeviceMinVolume ", ex);
      return -1;
    }
  }

  public boolean isDeviceVolumeMuted() {
    try {
      return this.mDm.isDeviceVolumeMuted();
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed isDeviceVolumeMuted ", ex);
      return false;
    }
  }

  public boolean requestSetWifiDisplayParameters(List<SemWifiDisplayParameter> parameters) {
    try {
      return this.mDm.requestSetWifiDisplayParameters(parameters);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed requestSetWifiDisplayParameters ", ex);
      return false;
    }
  }

  public boolean requestWifiDisplayParameter(
      String parameterGroup, SemWifiDisplayParameter parameter) {
    try {
      return this.mDm.requestWifiDisplayParameter(parameterGroup, parameter);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed requestSetWifiDisplayParameters ", ex);
      return false;
    }
  }

  public void registerDisplayVolumeListener(SemDisplayVolumeListener listener, Handler handler) {
    if (this.mDisplayVolumeListeners != null) {
      Log.m94d(TAG, "registerDisplayVolumeListener");
      if (listener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      synchronized (this.mLock) {
        int index = findDisplayVolumeListnerLocked(listener);
        if (index < 0) {
          Log.m94d(TAG, "registerDisplayVolumeListener index < 0");
          this.mDisplayVolumeListeners.add(new DisplayVolumeListenerDelegate(listener, handler));
          registerCallbackIfNeededLocked();
        }
      }
    }
  }

  public void unregisterDisplayVolumeListener(SemDisplayVolumeListener listener) {
    if (this.mDisplayVolumeListeners != null) {
      Log.m94d(TAG, "unregisterDisplayVolumeListener");
      if (listener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      synchronized (this.mLock) {
        int index = findDisplayVolumeListnerLocked(listener);
        if (index >= 0) {
          Log.m94d(TAG, "unregisterDisplayVolumeListener index >= 0");
          DisplayVolumeListenerDelegate d = this.mDisplayVolumeListeners.get(index);
          d.clearEvents();
          this.mDisplayVolumeListeners.remove(index);
        }
      }
    }
  }

  private int findDisplayVolumeListnerLocked(SemDisplayVolumeListener listener) {
    ArrayList<DisplayVolumeListenerDelegate> arrayList = this.mDisplayVolumeListeners;
    if (arrayList != null) {
      int numListeners = arrayList.size();
      Log.m94d(TAG, "findDisplayVolumeListnerLocked numListeners: " + numListeners);
      for (int i = 0; i < numListeners; i++) {
        if (this.mDisplayVolumeListeners.get(i).mListener == listener) {
          return i;
        }
      }
      return -1;
    }
    return -1;
  }

  public void registerDisplayVolumeKeyListener(
      SemDisplayVolumeKeyListener listener, Handler handler) {
    if (this.mDisplayVolumeKeyListeners != null) {
      Log.m94d(TAG, "registerDisplayVolumeKeyListener");
      if (listener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      synchronized (this.mLock) {
        int index = findDisplayVolumeKeyListnerLocked(listener);
        if (index < 0) {
          Log.m94d(TAG, "registerDisplayVolumeKeyListener index < 0");
          this.mDisplayVolumeKeyListeners.add(
              new DisplayVolumeKeyListenerDelegate(listener, handler));
          registerCallbackIfNeededLocked();
        }
      }
    }
  }

  public void unregisterDisplayVolumeKeyListener(SemDisplayVolumeKeyListener listener) {
    if (this.mDisplayVolumeKeyListeners != null) {
      Log.m94d(TAG, "unregisterDisplayVolumeKeyListener");
      if (listener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      synchronized (this.mLock) {
        int index = findDisplayVolumeKeyListnerLocked(listener);
        if (index >= 0) {
          Log.m94d(TAG, "unregisterDisplayVolumeKeyListener index >= 0");
          DisplayVolumeKeyListenerDelegate d = this.mDisplayVolumeKeyListeners.get(index);
          d.clearEvents();
          this.mDisplayVolumeKeyListeners.remove(index);
        }
      }
    }
  }

  private int findDisplayVolumeKeyListnerLocked(SemDisplayVolumeKeyListener listener) {
    ArrayList<DisplayVolumeKeyListenerDelegate> arrayList = this.mDisplayVolumeKeyListeners;
    if (arrayList != null) {
      int numListeners = arrayList.size();
      Log.m94d(TAG, "findDisplayVolumeKeyListnerLocked numListeners: " + numListeners);
      for (int i = 0; i < numListeners; i++) {
        if (this.mDisplayVolumeKeyListeners.get(i).mListener == listener) {
          return i;
        }
      }
      return -1;
    }
    return -1;
  }

  public void registerWifiDisplayParameterListener(
      SemWifiDisplayParameterListener listener, Handler handler) {
    if (this.mWifiDisplayParameterListeners != null) {
      Log.m94d(TAG, "registerWifiDisplayParameterListener");
      if (listener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      synchronized (this.mLock) {
        int index = findWifiDisplayParameterListnerLocked(listener);
        if (index < 0) {
          Log.m94d(TAG, "registerWifiDisplayParameterListener index < 0");
          this.mWifiDisplayParameterListeners.add(
              new WifiDisplayParameterListenerDelegate(listener, handler));
          registerCallbackIfNeededLocked();
        }
      }
    }
  }

  public void unregisterWifiDisplayParameterListener(SemWifiDisplayParameterListener listener) {
    if (this.mWifiDisplayParameterListeners != null) {
      Log.m94d(TAG, "unregisterWifiDisplayParameterListener");
      if (listener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      synchronized (this.mLock) {
        int index = findWifiDisplayParameterListnerLocked(listener);
        if (index >= 0) {
          Log.m94d(TAG, "unregisterWifiDisplayParameterListener index >= 0");
          WifiDisplayParameterListenerDelegate d = this.mWifiDisplayParameterListeners.get(index);
          d.clearEvents();
          this.mWifiDisplayParameterListeners.remove(index);
        }
      }
    }
  }

  private int findWifiDisplayParameterListnerLocked(SemWifiDisplayParameterListener listener) {
    ArrayList<WifiDisplayParameterListenerDelegate> arrayList = this.mWifiDisplayParameterListeners;
    if (arrayList != null) {
      int numListeners = arrayList.size();
      Log.m94d(TAG, "findWifiDisplayParameterListnerLocked numListeners: " + numListeners);
      for (int i = 0; i < numListeners; i++) {
        if (this.mWifiDisplayParameterListeners.get(i).mListener == listener) {
          return i;
        }
      }
      return -1;
    }
    return -1;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void handleDisplayVolumeEvent(int event, Bundle data) {
    synchronized (this.mLock) {
      ArrayList<DisplayVolumeListenerDelegate> arrayList = this.mDisplayVolumeListeners;
      if (arrayList != null) {
        int numListeners = arrayList.size();
        for (int i = 0; i < numListeners; i++) {
          this.mDisplayVolumeListeners.get(i).sendDisplayVolumeEvent(event, data);
        }
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void handleDisplayVolumeKeyEvent(int event) {
    synchronized (this.mLock) {
      ArrayList<DisplayVolumeKeyListenerDelegate> arrayList = this.mDisplayVolumeKeyListeners;
      if (arrayList != null) {
        int numListeners = arrayList.size();
        for (int i = 0; i < numListeners; i++) {
          this.mDisplayVolumeKeyListeners.get(i).sendDisplayVolumeKeyEvent(event);
        }
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void handleWifiDisplayParameterEvent(int event, List<SemWifiDisplayParameter> parameters) {
    synchronized (this.mLock) {
      ArrayList<WifiDisplayParameterListenerDelegate> arrayList =
          this.mWifiDisplayParameterListeners;
      if (arrayList != null) {
        int numListeners = arrayList.size();
        for (int i = 0; i < numListeners; i++) {
          this.mWifiDisplayParameterListeners
              .get(i)
              .sendWifiDisplayParameterEvent(event, parameters);
        }
      }
    }
  }

  public boolean isWifiDisplayWithPinSupported(String address) {
    try {
      return this.mDm.isWifiDisplayWithPinSupported(address);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed to get dongle pin supported feature Wifi display", ex);
      return false;
    }
  }

  public void fitToActiveDisplay(boolean status) {
    try {
      this.mDm.fitToActiveDisplay(status);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed to fit/unfit to active display", ex);
    }
  }

  public boolean isFitToActiveDisplay() {
    try {
      return this.mDm.isFitToActiveDisplay();
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed to get active display's fit status", ex);
      return false;
    }
  }

  public String getPresentationOwner(int displayId) {
    try {
      return this.mDm.getPresentationOwner(displayId);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Fail to get PresentationOwner.", ex);
      return "";
    }
  }

  public void setWifiDisplayParam(String key, String value) {
    try {
      this.mDm.setWifiDisplayParam(key, value);
    } catch (RemoteException ex) {
      Log.m97e(TAG, "Failed to setWifiDisplayParam", ex);
    }
  }

  public void registerDeviceListener(SemDeviceStatusListener listener, Handler handler) {
    if (this.mDeviceListeners != null) {
      Log.m94d(TAG, "registerDeviceListener");
      if (listener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      synchronized (this.mLock) {
        int index = findDeviceListnerLocked(listener);
        if (index < 0) {
          Log.m94d(TAG, "registerDeviceListener index < 0");
          this.mDeviceListeners.add(new DeviceListenerDelegate(listener, handler));
          registerCallbackIfNeededLocked();
        }
      }
    }
  }

  public void unregisterDeviceListener(SemDeviceStatusListener listener) {
    if (this.mDeviceListeners != null) {
      if (listener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      synchronized (this.mLock) {
        int index = findDeviceListnerLocked(listener);
        if (index >= 0) {
          DeviceListenerDelegate d = this.mDeviceListeners.get(index);
          d.clearEvents();
          this.mDeviceListeners.remove(index);
        }
      }
    }
  }

  private int findDeviceListnerLocked(SemDeviceStatusListener listener) {
    ArrayList<DeviceListenerDelegate> arrayList = this.mDeviceListeners;
    if (arrayList != null) {
      int numListeners = arrayList.size();
      for (int i = 0; i < numListeners; i++) {
        if (this.mDeviceListeners.get(i).mListener == listener) {
          return i;
        }
      }
      return -1;
    }
    return -1;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void handleDeviceEvent(Bundle msg, int event) {
    synchronized (this.mLock) {
      ArrayList<DeviceListenerDelegate> arrayList = this.mDeviceListeners;
      if (arrayList != null) {
        int numListeners = arrayList.size();
        for (int i = 0; i < numListeners; i++) {
          this.mDeviceListeners.get(i).sendDeviceEvent(msg, event);
        }
      }
    }
  }

  public class WifiDisplayConnectionCallback extends IWifiDisplayConnectionCallback.Stub {
    private Handler mHandler;
    private DisplayManager.SemWifiDisplayConnectionCallback mUserCallback;

    WifiDisplayConnectionCallback(
        DisplayManager.SemWifiDisplayConnectionCallback userCallback, Handler handler) {
      this.mUserCallback = userCallback;
      this.mHandler = new Handler(handler != null ? handler.getLooper() : Looper.myLooper());
    }

    @Override // android.hardware.display.IWifiDisplayConnectionCallback
    public void onSuccess(final List<SemWifiDisplayParameter> parameters) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // android.hardware.display.DisplayManagerGlobal.WifiDisplayConnectionCallback.1
            @Override // java.lang.Runnable
            public void run() {
              if (WifiDisplayConnectionCallback.this.mUserCallback != null) {
                WifiDisplayConnectionCallback.this.mUserCallback.onSuccess(parameters);
              }
            }
          });
    }

    @Override // android.hardware.display.IWifiDisplayConnectionCallback
    public void onFailure(final int reason) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // android.hardware.display.DisplayManagerGlobal.WifiDisplayConnectionCallback.2
            @Override // java.lang.Runnable
            public void run() {
              if (WifiDisplayConnectionCallback.this.mUserCallback != null) {
                WifiDisplayConnectionCallback.this.mUserCallback.onFailure(reason);
              }
            }
          });
    }
  }

  private final class DisplayManagerCallback extends IDisplayManagerCallback.Stub {
    private DisplayManagerCallback() {}

    @Override // android.hardware.display.IDisplayManagerCallback
    public void onDisplayEvent(int displayId, int event) {
      DisplayManagerGlobal.this.handleDisplayEvent(displayId, event);
    }

    @Override // android.hardware.display.IDisplayManagerCallback
    public void onDisplayVolumeEvent(int event, Bundle data) {
      DisplayManagerGlobal.this.handleDisplayVolumeEvent(event, data);
    }

    @Override // android.hardware.display.IDisplayManagerCallback
    public void onDisplayVolumeKeyEvent(int event) {
      DisplayManagerGlobal.this.handleDisplayVolumeKeyEvent(event);
    }

    @Override // android.hardware.display.IDisplayManagerCallback
    public void onWifiDisplayParameterEvent(int event, List<SemWifiDisplayParameter> parameters) {
      DisplayManagerGlobal.this.handleWifiDisplayParameterEvent(event, parameters);
    }

    @Override // android.hardware.display.IDisplayManagerCallback
    public void onDeviceEvent(Bundle msg, int event) {
      DisplayManagerGlobal.this.handleDeviceEvent(msg, event);
    }

    @Override // android.hardware.display.IDisplayManagerCallback
    public void onAsyncBinderBufferFillEvent(int event, Bundle data) {
      Log.m94d(DisplayManagerGlobal.TAG, "onAsyncBinderBufferFillEvent");
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  static final class DisplayListenerDelegate {
    public volatile long mEventsMask;
    private final Executor mExecutor;
    public final DisplayManager.DisplayListener mListener;
    private final DisplayInfo mDisplayInfo = new DisplayInfo();
    private AtomicLong mGenerationId = new AtomicLong(1);

    DisplayListenerDelegate(
        DisplayManager.DisplayListener listener, Executor executor, long eventsMask) {
      this.mExecutor = executor;
      this.mListener = listener;
      this.mEventsMask = eventsMask;
    }

    public void sendDisplayEvent(int displayId, int event, DisplayInfo info) {
      final long generationId = this.mGenerationId.get();
      final Message msg = Message.obtain(null, event, displayId, 0, info);
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.hardware.display.DisplayManagerGlobal$DisplayListenerDelegate$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              DisplayManagerGlobal.DisplayListenerDelegate.this.lambda$sendDisplayEvent$0(
                  generationId, msg);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendDisplayEvent$0(long generationId, Message msg) {
      if (generationId == this.mGenerationId.get()) {
        handleMessage(msg);
      }
      msg.recycle();
    }

    public void clearEvents() {
      this.mGenerationId.incrementAndGet();
    }

    public void setEventsMask(long newEventsMask) {
      this.mEventsMask = newEventsMask;
    }

    private void handleMessage(Message msg) {
      DisplayInfo newInfo;
      switch (msg.what) {
        case 1:
          if ((this.mEventsMask & 1) != 0) {
            this.mListener.onDisplayAdded(msg.arg1);
            break;
          }
          break;
        case 2:
          if ((this.mEventsMask & 4) != 0
              && (newInfo = (DisplayInfo) msg.obj) != null
              && !newInfo.equals(this.mDisplayInfo)) {
            this.mDisplayInfo.copyFrom(newInfo);
            this.mListener.onDisplayChanged(msg.arg1);
            break;
          }
          break;
        case 3:
          if ((this.mEventsMask & 2) != 0) {
            this.mListener.onDisplayRemoved(msg.arg1);
            break;
          }
          break;
        case 4:
          if ((this.mEventsMask & 8) != 0) {
            this.mListener.onDisplayChanged(msg.arg1);
            break;
          }
          break;
        case 5:
          if ((this.mEventsMask & 16) != 0) {
            this.mListener.onDisplayChanged(msg.arg1);
            break;
          }
          break;
      }
    }
  }

  public static final class VirtualDisplayCallback extends IVirtualDisplayCallback.Stub {
    private final VirtualDisplay.Callback mCallback;
    private final Executor mExecutor;

    public VirtualDisplayCallback(VirtualDisplay.Callback callback, Executor executor) {
      this.mCallback = callback;
      this.mExecutor = callback != null ? (Executor) Objects.requireNonNull(executor) : null;
    }

    @Override // android.hardware.display.IVirtualDisplayCallback
    public void onPaused() {
      final VirtualDisplay.Callback callback = this.mCallback;
      if (callback != null) {
        Executor executor = this.mExecutor;
        Objects.requireNonNull(callback);
        executor.execute(
            new Runnable() { // from class:
                             // android.hardware.display.DisplayManagerGlobal$VirtualDisplayCallback$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                VirtualDisplay.Callback.this.onPaused();
              }
            });
      }
    }

    @Override // android.hardware.display.IVirtualDisplayCallback
    public void onResumed() {
      final VirtualDisplay.Callback callback = this.mCallback;
      if (callback != null) {
        Executor executor = this.mExecutor;
        Objects.requireNonNull(callback);
        executor.execute(
            new Runnable() { // from class:
                             // android.hardware.display.DisplayManagerGlobal$VirtualDisplayCallback$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                VirtualDisplay.Callback.this.onResumed();
              }
            });
      }
    }

    @Override // android.hardware.display.IVirtualDisplayCallback
    public void onStopped() {
      final VirtualDisplay.Callback callback = this.mCallback;
      if (callback != null) {
        Executor executor = this.mExecutor;
        Objects.requireNonNull(callback);
        executor.execute(
            new Runnable() { // from class:
                             // android.hardware.display.DisplayManagerGlobal$VirtualDisplayCallback$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                VirtualDisplay.Callback.this.onStopped();
              }
            });
      }
    }
  }

  public static void invalidateLocalDisplayInfoCaches() {
    PropertyInvalidatedCache.invalidateCache(CACHE_KEY_DISPLAY_INFO_PROPERTY);
  }

  public void disableLocalDisplayInfoCaches() {
    this.mDisplayCache = null;
  }

  public void registerNativeChoreographerForRefreshRateCallbacks() {
    synchronized (this.mLock) {
      this.mDispatchNativeCallbacks = true;
      registerCallbackIfNeededLocked();
      updateCallbackIfNeededLocked();
      DisplayInfo display = getDisplayInfoLocked(0);
      if (display != null) {
        float refreshRate = display.getRefreshRate();
        this.mNativeCallbackReportedRefreshRate = refreshRate;
        nSignalNativeCallbacks(refreshRate);
      }
    }
  }

  public void unregisterNativeChoreographerForRefreshRateCallbacks() {
    synchronized (this.mLock) {
      this.mDispatchNativeCallbacks = false;
      updateCallbackIfNeededLocked();
    }
  }

  public long getPrimaryPhysicalDisplayId() {
    try {
      return this.mDm.getPrimaryPhysicalDisplayId();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  private static String eventToString(int event) {
    switch (event) {
      case 1:
        return ClipboardConstants.USER_ADDED;
      case 2:
        return "CHANGED";
      case 3:
        return ClipboardConstants.USER_REMOVED;
      case 4:
        return "BRIGHTNESS_CHANGED";
      case 5:
        return "HDR_SDR_RATIO_CHANGED";
      default:
        return "UNKNOWN";
    }
  }

  private static final class DisplayVolumeListenerDelegate extends Handler {
    public final SemDisplayVolumeListener mListener;

    public DisplayVolumeListenerDelegate(SemDisplayVolumeListener listener, Handler handler) {
      super(handler != null ? handler.getLooper() : Looper.myLooper(), null, true);
      this.mListener = listener;
    }

    public void sendDisplayVolumeEvent(int event, Bundle data) {
      Message msg = Message.obtain(this, event);
      msg.setData(data);
      sendMessage(msg);
    }

    public void clearEvents() {
      removeCallbacksAndMessages(null);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      Bundle data = msg.getData();
      switch (msg.what) {
        case 9:
          int minVol = data.getInt("minVol");
          int maxVol = data.getInt("maxVol");
          int curVol = data.getInt("curVol");
          boolean isMute = data.getBoolean("isMute", false);
          Log.m94d(
              DisplayManagerGlobal.TAG,
              "handleMessage EVENT_VOLUME_LEVEL_CHANGED= curVol: " + curVol);
          this.mListener.onVolumeChanged(minVol, maxVol, curVol, isMute);
          break;
      }
    }
  }

  private static final class DisplayVolumeKeyListenerDelegate extends Handler {
    public final SemDisplayVolumeKeyListener mListener;

    public DisplayVolumeKeyListenerDelegate(SemDisplayVolumeKeyListener listener, Handler handler) {
      super(handler != null ? handler.getLooper() : Looper.myLooper(), null, true);
      this.mListener = listener;
    }

    public void sendDisplayVolumeKeyEvent(int event) {
      Message msg = Message.obtain(this, event);
      sendMessage(msg);
    }

    public void clearEvents() {
      removeCallbacksAndMessages(null);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      msg.getData();
      switch (msg.what) {
        case 10:
          Log.m94d(DisplayManagerGlobal.TAG, "onVolumeKeyDown");
          this.mListener.onVolumeKeyDown();
          break;
        case 11:
          Log.m94d(DisplayManagerGlobal.TAG, "onVolumeKeyUp");
          this.mListener.onVolumeKeyUp();
          break;
        case 12:
          Log.m94d(DisplayManagerGlobal.TAG, "onMuteKeyStateChanged [MUTE]");
          this.mListener.onMuteKeyStateChanged(true);
          break;
        case 13:
          Log.m94d(DisplayManagerGlobal.TAG, "onMuteKeyStateChanged [UNMUTE]");
          this.mListener.onMuteKeyStateChanged(false);
          break;
      }
    }
  }

  private static final class WifiDisplayParameterListenerDelegate extends Handler {
    private final SemWifiDisplayParameterListener mListener;

    public WifiDisplayParameterListenerDelegate(
        SemWifiDisplayParameterListener listener, Handler handler) {
      super(handler != null ? handler.getLooper() : Looper.myLooper(), null, true);
      this.mListener = listener;
    }

    public void sendWifiDisplayParameterEvent(int event, List<SemWifiDisplayParameter> parameters) {
      Message msg = Message.obtain(this, event);
      msg.obj = parameters;
      sendMessage(msg);
    }

    public void clearEvents() {
      removeCallbacksAndMessages(null);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 17:
          Log.m94d(DisplayManagerGlobal.TAG, "onParametersChanged");
          this.mListener.onParametersChanged((List) msg.obj);
          break;
      }
    }
  }

  private static final class DeviceListenerDelegate extends Handler {
    public final SemDeviceStatusListener mListener;

    public DeviceListenerDelegate(SemDeviceStatusListener listener, Handler handler) {
      super(handler != null ? handler.getLooper() : Looper.myLooper(), null, true);
      this.mListener = listener;
    }

    public void sendDeviceEvent(Bundle data, int event) {
      Message msg = Message.obtain(this, event);
      msg.setData(data);
      sendMessage(msg);
    }

    public void clearEvents() {
      removeCallbacksAndMessages(null);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      Bundle data = msg.getData();
      switch (msg.what) {
        case 6:
          int status = data.getInt("status", 0);
          Log.m94d(
              DisplayManagerGlobal.TAG, "handleMessage EVENT_CONNECTIONSTATUS_CHANGED = " + status);
          this.mListener.onConnectionStatusChanged(status);
          break;
        case 7:
          int status2 = data.getInt("status", 6);
          Log.m94d(
              DisplayManagerGlobal.TAG,
              "handleMessage EVENT_REMOTE_DISPLAY_STATE_CHANGED = " + status2);
          this.mListener.onScreenSharingStatusChanged(status2);
          break;
        case 8:
          int status3 = data.getInt("status", 0);
          Log.m94d(
              DisplayManagerGlobal.TAG,
              "handleMessage EVENT_REMOTE_DISPLAY_ROTATION_CHANGED = " + status3);
          this.mListener.onScreenSharingStatusChanged(status3);
          break;
      }
    }
  }

  public void setTemporaryBrightnessForSlowChange(
      int displayId, float brightness, boolean slowChange) {
    try {
      this.mDm.setTemporaryBrightnessForSlowChange(displayId, brightness, slowChange);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setBrightnessConfigurationForUser(
      BrightnessConfiguration c,
      int userId,
      String packageName,
      List<String> weights,
      List<String> timeWeights,
      List<String> continuityWeights) {
    try {
      this.mDm.setBrightnessConfigurationForUserWithStats(
          c, userId, packageName, weights, timeWeights, continuityWeights);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void resetBrightnessConfigurationForUser(int userId, String packageName) {
    try {
      this.mDm.resetBrightnessConfigurationForUser(userId, packageName);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setBackupBrightnessConfiguration(
      BrightnessConfiguration config, int userId, String packageName) {
    try {
      this.mDm.setBackupBrightnessConfiguration(config, userId, packageName);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public BrightnessConfiguration getBackupBrightnessConfiguration(int userId) {
    try {
      return this.mDm.getBackupBrightnessConfiguration(userId);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public int convertToBrightness(float nits) {
    try {
      return this.mDm.convertToBrightness(nits);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public float getAdaptiveBrightness(int displayId, float lux) {
    try {
      return this.mDm.getAdaptiveBrightness(displayId, lux);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }
}
