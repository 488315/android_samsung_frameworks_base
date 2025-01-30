package com.android.server.display.mode;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.DisplayManagerInternal;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Slog;
import android.view.DisplayAddress;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.LocalServices;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class RefreshRateModeManager {
  public static HashMap mControllerByToken = new HashMap();
  public Context mContext;
  public Handler mHandler;
  public Object mLock;
  public final ModeSettingsObserver mModeSettingsObserver;
  public AtomicReference mDisplayToken = new AtomicReference();
  public DisplayManagerInternal.DisplayStateListener mDisplayStateListener = new C13481();

  public RefreshRateModeManager(
      Context context,
      DisplayModeDirector displayModeDirector,
      Object obj,
      Handler handler,
      VotesStorage votesStorage) {
    this.mContext = context;
    this.mHandler = handler;
    this.mLock = obj;
    RefreshRateController.init(
        context, displayModeDirector, handler, votesStorage, new RefreshRateTokenController(obj));
    this.mModeSettingsObserver = new ModeSettingsObserver(context, handler);
    addControllerByDisplayToken(null);
  }

  public RefreshRateController getController() {
    return getController((IBinder) this.mDisplayToken.get());
  }

  public RefreshRateController getController(IBinder iBinder) {
    RefreshRateController refreshRateController;
    synchronized (mControllerByToken) {
      refreshRateController = (RefreshRateController) mControllerByToken.get(iBinder);
    }
    return refreshRateController;
  }

  public void setPrimaryDisplayToken() {
    getController()
        .setPrimaryDisplayToken(
            SurfaceControl.getDisplayToken(
                DisplayAddress.fromPhysicalDisplayId(
                    DisplayManagerGlobal.getInstance().getPrimaryPhysicalDisplayId())));
  }

  public void updateDefaultDisplayToken() {
    DisplayAddress displayAddress;
    DisplayInfo displayInfo = DisplayManagerGlobal.getInstance().getDisplayInfo(0);
    if (displayInfo == null || (displayAddress = displayInfo.address) == null) {
      return;
    }
    IBinder displayToken = SurfaceControl.getDisplayToken(displayAddress);
    addControllerByDisplayToken(displayToken);
    IBinder iBinder = (IBinder) this.mDisplayToken.getAndSet(displayToken);
    boolean z = iBinder != displayToken;
    Slog.d(
        "RefreshRateModeManager",
        "updateDefaultDisplayToken: " + this.mDisplayToken + ", isChanged: " + z);
    if (CoreRune.FW_VRR_FOR_SUB_DISPLAY && z) {
      synchronized (this.mLock) {
        getController(iBinder).updateDefaultDisplayOrOffDisplayLocked();
        getController().updateRefreshRateModeLocked(z);
        onBrightnessChangedLocked();
        getController().updateRefreshRateMaxLimitTokenLocked();
      }
    }
  }

  public void addControllerByDisplayToken(IBinder iBinder) {
    synchronized (mControllerByToken) {
      if (!mControllerByToken.containsKey(iBinder)) {
        mControllerByToken.put(iBinder, RefreshRateController.makeController(iBinder));
        Slog.d("RefreshRateModeManager", "display - " + iBinder + " registered");
      }
    }
  }

  public int getRefreshRateMode() {
    int refreshRateModeLocked;
    synchronized (this.mLock) {
      refreshRateModeLocked = getController().getRefreshRateModeLocked();
    }
    return refreshRateModeLocked;
  }

  public void onLightSensorChanged(float f) {
    if (!getController().compareAndSetAmbientLuxZone(f) || f <= -1.0f) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.display.mode.RefreshRateModeManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              RefreshRateModeManager.this.lambda$onLightSensorChanged$0();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onLightSensorChanged$0() {
    synchronized (this.mLock) {
      onBrightnessChangedLocked();
    }
  }

  /* renamed from: com.android.server.display.mode.RefreshRateModeManager$1 */
  public class C13481 implements DisplayManagerInternal.DisplayStateListener {
    public C13481() {}

    public void onFinish(int i, int i2, int i3) {
      if (i3 == 1 || i3 == 2) {
        final boolean z = i == 2;
        Boolean powerModeOnByDisplayType =
            RefreshRateModeManager.this.getController().getPowerModeOnByDisplayType(i3);
        if (powerModeOnByDisplayType == null || z != powerModeOnByDisplayType.booleanValue()) {
          Slog.d(
              "RefreshRateModeManager",
              "DisplayStateListener#onFinish, isPowerModeOn=" + z + ", displayType=" + i3);
          RefreshRateModeManager.this.getController().setPowerModeOnByDisplayType(i3, z);
          RefreshRateModeManager.this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.display.mode.RefreshRateModeManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  RefreshRateModeManager.C13481.this.lambda$onFinish$0(z);
                }
              });
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinish$0(boolean z) {
      synchronized (RefreshRateModeManager.this.mLock) {
        RefreshRateModeManager.this.getController().onDisplayStateChangeLocked(z);
      }
    }
  }

  public final void registerBrightnessAndStateListener() {
    Slog.d("RefreshRateModeManager", "registerBrightnessAndStateListener");
    DisplayManagerInternal displayManagerInternal =
        (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
    displayManagerInternal.registerDisplayBrightnessListener(
        new DisplayManagerInternal
            .DisplayBrightnessListener() { // from class:
                                           // com.android.server.display.mode.RefreshRateModeManager$$ExternalSyntheticLambda1
          public final void onChanged(float f) {
            RefreshRateModeManager.this.lambda$registerBrightnessAndStateListener$2(f);
          }
        });
    getController()
        .compareAndSetBrightnessZone(
            BrightnessSynchronizer.brightnessFloatToInt(
                displayManagerInternal.getCurrentScreenBrightness()));
    displayManagerInternal.registerDisplayStateListener(this.mDisplayStateListener);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$registerBrightnessAndStateListener$2(float f) {
    if (getController()
        .compareAndSetBrightnessZone(BrightnessSynchronizer.brightnessFloatToInt(f))) {
      return;
    }
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.display.mode.RefreshRateModeManager$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            RefreshRateModeManager.this.lambda$registerBrightnessAndStateListener$1();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$registerBrightnessAndStateListener$1() {
    synchronized (this.mLock) {
      onBrightnessChangedLocked();
    }
  }

  public final void registerWirelessChargeReceiver() {
    Slog.d("RefreshRateModeManager", "registerWirelessChargeReceiver");
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
    this.mContext.registerReceiver(
        new BroadcastReceiver() { // from class:
                                  // com.android.server.display.mode.RefreshRateModeManager.2
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
              boolean z = intent.getIntExtra("plugged", 0) == 4;
              synchronized (RefreshRateModeManager.this.mLock) {
                if (!RefreshRateModeManager.this
                    .getController()
                    .compareAndSetIsWirelessCharging(z)) {
                  RefreshRateModeManager.this.onBrightnessChangedLocked();
                }
              }
            }
          }
        },
        intentFilter);
  }

  public void observe() {
    this.mModeSettingsObserver.observe();
    if (CoreRune.FW_VRR_SEAMLESS) {
      registerBrightnessAndStateListener();
    }
    if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
      registerWirelessChargeReceiver();
    }
  }

  public final class ModeSettingsObserver extends ContentObserver {
    public final Context mContext;
    public final Uri mRefreshRateModeSetting;
    public final Uri mSubRefreshRateModeSetting;

    public ModeSettingsObserver(Context context, Handler handler) {
      super(handler);
      this.mRefreshRateModeSetting = Settings.Secure.getUriFor("refresh_rate_mode");
      this.mSubRefreshRateModeSetting = Settings.Secure.getUriFor("refresh_rate_mode_cover");
      this.mContext = context;
    }

    public void observe() {
      ContentResolver contentResolver = this.mContext.getContentResolver();
      contentResolver.registerContentObserver(this.mRefreshRateModeSetting, false, this, -1);
      if (CoreRune.FW_VRR_FOR_SUB_DISPLAY) {
        contentResolver.registerContentObserver(this.mSubRefreshRateModeSetting, false, this, -1);
      }
      synchronized (RefreshRateModeManager.this.mLock) {
        RefreshRateModeManager.this.getController().updateRefreshRateModeLocked(false);
      }
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri, int i) {
      synchronized (RefreshRateModeManager.this.mLock) {
        if (this.mRefreshRateModeSetting.equals(uri)
            || (CoreRune.FW_VRR_FOR_SUB_DISPLAY && this.mSubRefreshRateModeSetting.equals(uri))) {
          RefreshRateModeManager.this.getController().updateRefreshRateModeLocked(false);
        }
      }
    }
  }

  public void onBrightnessChangedLocked() {
    getController().onBrightnessChangedLocked(false);
  }

  public boolean logCurrentState(
      int i, DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
    boolean logCurrentStateLocked;
    if (i != 0) {
      return false;
    }
    synchronized (this.mLock) {
      logCurrentStateLocked = getController().logCurrentStateLocked(desiredDisplayModeSpecs);
    }
    return logCurrentStateLocked;
  }

  public void dumpLocked(final PrintWriter printWriter) {
    printWriter.println("  RefreshRateModeManager");
    printWriter.println("    mRefreshRateModeByDisplayToken");
    mControllerByToken.forEach(
        new BiConsumer() { // from class:
                           // com.android.server.display.mode.RefreshRateModeManager$$ExternalSyntheticLambda2
          @Override // java.util.function.BiConsumer
          public final void accept(Object obj, Object obj2) {
            RefreshRateModeManager.lambda$dumpLocked$3(
                printWriter, (IBinder) obj, (RefreshRateController) obj2);
          }
        });
    if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
      printWriter.println();
      getController().dumpHistory(printWriter);
    }
  }

  public static /* synthetic */ void lambda$dumpLocked$3(
      PrintWriter printWriter, IBinder iBinder, RefreshRateController refreshRateController) {
    printWriter.println("      DisplayToken: " + iBinder);
    refreshRateController.dumpLocked(printWriter, "        ");
  }
}
