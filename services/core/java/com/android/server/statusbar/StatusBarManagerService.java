package com.android.server.statusbar;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.app.ITransientNotificationCallback;
import android.app.Notification;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.om.IOverlayManager;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.hardware.biometrics.IBiometricContextListener;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.media.INearbyMediaDevicesProvider;
import android.media.MediaRoute2Info;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.WindowInsets;
import android.widget.RemoteViews;
import com.android.internal.carlife.IStatusBarCarLife;
import com.android.internal.logging.InstanceId;
import com.android.internal.os.TransferPipe;
import com.android.internal.statusbar.IAddTileResultCallback;
import com.android.internal.statusbar.ISessionListener;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.statusbar.RegisterStatusBarResult;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.GcUtils;
import com.android.internal.view.AppearanceRegion;
import com.android.server.LocalServices;
import com.android.server.UiThread;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.notification.NotificationDelegate;
import com.android.server.pm.UserManagerInternal;
import com.android.server.policy.GlobalActionsProvider;
import com.android.server.power.ShutdownCheckPoints;
import com.android.server.power.ShutdownThread;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.edge.EdgeManagerInternal;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class StatusBarManagerService extends IStatusBarService.Stub
    implements DisplayManager.DisplayListener {
  public final ActivityManagerInternal mActivityManagerInternal;
  public final ActivityTaskManagerInternal mActivityTaskManager;
  public volatile IStatusBar mBar;
  public final Object mBarLock;
  public volatile ArrayMap mBarMap;
  public IBiometricContextListener mBiometricContextListener;
  public volatile IStatusBarCarLife mCarLifeBar;
  public final Context mContext;
  public final ArrayMap mCurrentRequestAddTilePackages;
  public int mCurrentUserId;
  public SemDesktopModeManager.DesktopModeListener mDesktopModeListener;
  public ArrayList mDisableHistoryList;
  public final ArrayList mDisableRecords;
  public final SparseArray mDisplayUiState;
  public SparseArray mDisplayUiStateDex;
  public EdgeManagerInternal mEdgeInternal;
  public GlobalActionsProvider.GlobalActionsListener mGlobalActionListener;
  public final GlobalActionsProvider mGlobalActionsProvider;
  public final Handler mHandler;
  public final ArrayMap mIcons;
  public final StatusBarManagerInternal mInternalService;
  public ConcurrentHashMap mIsSecCustomTileMap;
  public int mLastSystemKey;
  public final Object mLock;
  public NotificationDelegate mNotificationDelegate;
  public IOverlayManager mOverlayManager;
  public final PackageManagerInternal mPackageManagerInternal;
  public ArrayMap mPanelExpandStateMap;
  public ArrayMap mQsPanelExpandStateMap;
  public SemDesktopModeManager mSemDesktopModeManager;
  public final SessionMonitor mSessionMonitor;
  public ArrayList mStatusBarHistoryList;
  public boolean mSysUiSafeMode;
  public final IBinder mSysUiVisToken;
  public final TileRequestTracker mTileRequestTracker;
  public boolean mTracingEnabled;
  public IUdfpsRefreshRateRequestCallback mUdfpsRefreshRateRequestCallback;
  public final UserManagerInternal mUserManagerInternal;
  public static final long REQUEST_TIME_OUT = TimeUnit.MINUTES.toNanos(5);
  public static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
  public static boolean DEBUG_SAFEMODE =
      Integer.toString(1).equals(SystemProperties.get("debug.sysui.safemode", "0"));

  @Override // android.hardware.display.DisplayManager.DisplayListener
  public void onDisplayAdded(int i) {}

  @Override // android.hardware.display.DisplayManager.DisplayListener
  public void onDisplayChanged(int i) {}

  public final boolean resolveEnabledComponent(boolean z, int i) {
    if (i == 1) {
      return true;
    }
    if (i == 0) {
      return z;
    }
    return false;
  }

  public final boolean checkBarMapState(int i) {
    try {
      return this.mBarMap.get(Integer.valueOf(i)) != null;
    } catch (ArrayIndexOutOfBoundsException e) {
      Slog.w("StatusBarManagerService", "checkBarMapState barType =" + i, e);
      return false;
    } catch (ConcurrentModificationException e2) {
      Slog.w("StatusBarManagerService", "checkBarMapState barType =" + i, e2);
      return false;
    }
  }

  public class DisableRecord implements IBinder.DeathRecipient {
    public int barType;
    public int displayId;
    public String pkg;
    public IBinder token;
    public int userId;
    public int what1;
    public int what2;

    public DisableRecord(int i, IBinder iBinder, int i2, int i3) {
      this.userId = i;
      this.token = iBinder;
      this.barType = i2;
      this.displayId = i3;
      try {
        iBinder.linkToDeath(this, 0);
      } catch (RemoteException unused) {
      }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      Slog.i("StatusBarManagerService", "binder died for pkg=" + this.pkg);
      StatusBarManagerService.this.disableForUserToType(
          0, this.token, this.pkg, this.userId, this.barType);
      StatusBarManagerService.this.disable2ForUserToType(
          0, this.token, this.pkg, this.userId, this.barType);
      this.token.unlinkToDeath(this, 0);
    }

    public void setFlags(int i, int i2, String str) {
      if (i2 == 1) {
        this.what1 = i;
      } else if (i2 == 2) {
        this.what2 = i;
      } else {
        Slog.w(
            "StatusBarManagerService",
            "Can't set unsupported disable flag " + i2 + ": 0x" + Integer.toHexString(i));
      }
      this.pkg = str;
    }

    public int getFlags(int i) {
      if (i == 1) {
        return this.what1;
      }
      if (i == 2) {
        return this.what2;
      }
      Slog.w("StatusBarManagerService", "Can't get unsupported disable flag " + i);
      return 0;
    }

    public boolean isEmpty() {
      return this.what1 == 0 && this.what2 == 0;
    }

    public String toString() {
      return String.format(
          "barType=%d userId=%d what1=0x%08X what2=0x%08X pkg=%s token=%s",
          Integer.valueOf(this.barType),
          Integer.valueOf(this.userId),
          Integer.valueOf(this.what1),
          Integer.valueOf(this.what2),
          this.pkg,
          this.token);
    }
  }

  public StatusBarManagerService(Context context) {
    Handler handler = new Handler();
    this.mHandler = handler;
    this.mIcons = new ArrayMap();
    this.mDisableRecords = new ArrayList();
    this.mSysUiVisToken = new Binder();
    this.mLock = new Object();
    this.mLastSystemKey = -1;
    SparseArray sparseArray = new SparseArray();
    this.mDisplayUiState = sparseArray;
    this.mCurrentRequestAddTilePackages = new ArrayMap();
    this.mEdgeInternal = null;
    this.mBarLock = new Object();
    this.mBarMap = new ArrayMap();
    this.mDisplayUiStateDex = new SparseArray();
    this.mPanelExpandStateMap = new ArrayMap();
    this.mQsPanelExpandStateMap = new ArrayMap();
    this.mDesktopModeListener =
        new SemDesktopModeManager
            .DesktopModeListener() { // from class:
                                     // com.android.server.statusbar.StatusBarManagerService.1
          public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
            Slog.d(
                "StatusBarManagerService",
                "onDesktopModeStateChanged state = " + semDesktopModeState);
            if (semDesktopModeState.getState() == 20 && semDesktopModeState.getEnabled() == 1) {
              synchronized (StatusBarManagerService.this.mLock) {
                for (int size = StatusBarManagerService.this.mDisableRecords.size() - 1;
                    -1 < size;
                    size--) {
                  DisableRecord disableRecord =
                      (DisableRecord) StatusBarManagerService.this.mDisableRecords.get(size);
                  if (disableRecord.barType == 1) {
                    Slog.d(
                        "StatusBarManagerService",
                        "remove record of STATUS_BAR_DEX r = " + disableRecord);
                    StatusBarManagerService.this.mDisableRecords.remove(size);
                    disableRecord.token.unlinkToDeath(disableRecord, 0);
                  }
                }
              }
            }
          }
        };
    StatusBarManagerInternal statusBarManagerInternal =
        new StatusBarManagerInternal() { // from class:
                                         // com.android.server.statusbar.StatusBarManagerService.2
          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void onFlashlightKeyPressed(int i) {
            StatusBarManagerService.this.enforceStatusBarService();
            Iterator it = StatusBarManagerService.this.mBarMap.entrySet().iterator();
            while (it.hasNext()) {
              IStatusBar iStatusBar = (IStatusBar) ((Map.Entry) it.next()).getValue();
              if (iStatusBar != null) {
                try {
                  iStatusBar.onFlashlightKeyPressed(i);
                } catch (RemoteException unused) {
                }
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void setNotificationDelegate(NotificationDelegate notificationDelegate) {
            StatusBarManagerService.this.mNotificationDelegate = notificationDelegate;
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void showScreenPinningRequest(int i) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.showScreenPinningRequest(i);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void showAssistDisclosure() {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.showAssistDisclosure();
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void startAssist(Bundle bundle) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.startAssist(bundle);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void onCameraLaunchGestureDetected(int i) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.onCameraLaunchGestureDetected(i);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void onEmergencyActionLaunchGestureDetected() {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.onEmergencyActionLaunchGestureDetected();
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void toggleSplitScreen() {
            StatusBarManagerService.this.enforceStatusBarService();
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.toggleSplitScreen();
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void appTransitionFinished(int i) {
            StatusBarManagerService.this.enforceStatusBarService();
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.appTransitionFinished(i);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void toggleTaskbar() {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.toggleTaskbar();
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void toggleRecentApps() {
            IStatusBar iStatusBar;
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.toggleRecentApps();
                for (Map.Entry entry : StatusBarManagerService.this.mBarMap.entrySet()) {
                  if (((Integer) entry.getKey()).intValue() != 0
                      && (iStatusBar = (IStatusBar) entry.getValue()) != null) {
                    iStatusBar.hideRecentApps(false, true);
                  }
                }
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void setCurrentUser(int i) {
            StatusBarManagerService.this.mCurrentUserId = i;
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void preloadRecentApps() {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.preloadRecentApps();
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void cancelPreloadRecentApps() {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.cancelPreloadRecentApps();
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void collapsePanels() {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.animateCollapsePanels();
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void dismissKeyboardShortcutsMenu() {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.dismissKeyboardShortcutsMenu();
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
            StatusBarManagerService.this.setImeWindowStatus(i, iBinder, i2, i3, z);
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void setIcon(String str, String str2, int i, int i2, String str3) {
            StatusBarManagerService.this.setIcon(str, str2, i, i2, str3);
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void setIconVisibility(String str, boolean z) {
            StatusBarManagerService.this.setIconVisibility(str, z);
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void showChargingAnimation(int i) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.showWirelessChargingAnimation(i);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void showPictureInPictureMenu() {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.showPictureInPictureMenu();
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void setWindowState(int i, int i2, int i3) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.setWindowState(i, i2, i3);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void appTransitionPending(int i) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.appTransitionPending(i);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void appTransitionCancelled(int i) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.appTransitionCancelled(i);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void appTransitionStarting(int i, long j, long j2) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.appTransitionStarting(i, j, j2);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void setTopAppHidesStatusBar(boolean z) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.setTopAppHidesStatusBar(z);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public boolean showShutdownUi(boolean z, String str) {
            if (StatusBarManagerService.this
                    .mContext
                    .getResources()
                    .getBoolean(R.bool.config_preferKeepClearForFocus)
                && StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.showShutdownUi(z, str);
                return true;
              } catch (RemoteException unused) {
              }
            }
            return false;
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void onProposedRotationChanged(int i, boolean z) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.onProposedRotationChanged(i, z);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void onDisplayReady(int i) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.onDisplayReady(i);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void onRecentsAnimationStateChanged(boolean z) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.onRecentsAnimationStateChanged(z);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void onSystemBarAttributesChanged(
              int i,
              int i2,
              AppearanceRegion[] appearanceRegionArr,
              boolean z,
              int i3,
              int i4,
              String str,
              LetterboxDetails[] letterboxDetailsArr) {
            StatusBarManagerService.this
                .getUiState(i, 0)
                .setBarAttributes(i2, appearanceRegionArr, z, i3, i4, str, letterboxDetailsArr);
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.onSystemBarAttributesChanged(
                    i, i2, appearanceRegionArr, z, i3, i4, str, letterboxDetailsArr);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void abortTransient(int i, int i2) {
            StatusBarManagerService.this.getUiState(i, 0).clearTransient(i2);
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.abortTransient(i, i2);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void showToast(
              int i,
              String str,
              IBinder iBinder,
              CharSequence charSequence,
              IBinder iBinder2,
              int i2,
              ITransientNotificationCallback iTransientNotificationCallback,
              int i3) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.showToast(
                    i,
                    str,
                    iBinder,
                    charSequence,
                    iBinder2,
                    i2,
                    iTransientNotificationCallback,
                    i3);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void hideToast(String str, IBinder iBinder) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.hideToast(str, iBinder);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public boolean requestWindowMagnificationConnection(boolean z) {
            if (StatusBarManagerService.this.mBar == null) {
              return false;
            }
            try {
              StatusBarManagerService.this.mBar.requestWindowMagnificationConnection(z);
              return true;
            } catch (RemoteException unused) {
              return false;
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.setNavigationBarLumaSamplingEnabled(i, z);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void setUdfpsRefreshRateCallback(
              IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) {
            synchronized (StatusBarManagerService.this.mLock) {
              StatusBarManagerService.this.mUdfpsRefreshRateRequestCallback =
                  iUdfpsRefreshRateRequestCallback;
            }
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.setUdfpsRefreshRateCallback(
                    iUdfpsRefreshRateRequestCallback);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void showRearDisplayDialog(int i) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.showRearDisplayDialog(i);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void goToFullscreenFromSplit() {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.goToFullscreenFromSplit();
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void enterStageSplitFromRunningApp(boolean z) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.enterStageSplitFromRunningApp(z);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void showMediaOutputSwitcher(String str) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.showMediaOutputSwitcher(str);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void setDisableFlagsToType(int i, int i2, String str, int i3) {
            StatusBarManagerService.this.setDisableFlags(i, i2, str, i3);
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void onSystemBarAttributesChangedToType(
              int i,
              int i2,
              AppearanceRegion[] appearanceRegionArr,
              boolean z,
              int i3,
              int i4,
              String str,
              LetterboxDetails[] letterboxDetailsArr,
              int i5) {
            StatusBarManagerService.this
                .getUiState(i, i5)
                .setBarAttributes(i2, appearanceRegionArr, z, i3, i4, str, letterboxDetailsArr);
            synchronized (StatusBarManagerService.this.mBarLock) {
              if (StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i5)) != null) {
                try {
                  ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i5)))
                      .onSystemBarAttributesChanged(
                          i, i2, appearanceRegionArr, z, i3, i4, str, letterboxDetailsArr);
                } catch (RemoteException unused) {
                  Slog.i(
                      "StatusBarManagerService",
                      "RemoteException for onSystemBarAttributesChanged");
                }
              } else if (CoreRune.CARLIFE_NAVBAR
                  && i5 == 2
                  && StatusBarManagerService.this.mCarLifeBar != null) {
                try {
                  StatusBarManagerService.this.mCarLifeBar.onSystemBarAttributesChanged(
                      i, i2, appearanceRegionArr, z, i3, i4, str);
                } catch (RemoteException unused2) {
                  Slog.i(
                      "StatusBarManagerService",
                      "RemoteException for onSystemBarAttributesChanged");
                }
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void showTransientToType(int i, int i2, boolean z, int i3) {
            StatusBarManagerService.this.getUiState(i, i3).showTransient(i2);
            synchronized (StatusBarManagerService.this.mBarLock) {
              if (StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i3)) != null) {
                try {
                  ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i3)))
                      .showTransient(i, i2, z);
                } catch (RemoteException unused) {
                  Slog.i("StatusBarManagerService", "RemoteException for showTransient");
                }
              } else if (CoreRune.CARLIFE_NAVBAR
                  && i3 == 2
                  && StatusBarManagerService.this.mCarLifeBar != null) {
                try {
                  StatusBarManagerService.this.mCarLifeBar.showTransient(i, i2, z);
                } catch (RemoteException unused2) {
                  Slog.i("StatusBarManagerService", "RemoteException for showTransient");
                }
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void abortTransientToType(int i, int i2, int i3) {
            StatusBarManagerService.this.getUiState(i, i3).clearTransient(i2);
            synchronized (StatusBarManagerService.this.mBarLock) {
              if (StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i3)) != null) {
                try {
                  ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i3)))
                      .abortTransient(i, i2);
                } catch (RemoteException unused) {
                  Slog.i("StatusBarManagerService", "RemoteException for abortTransient");
                }
              } else if (CoreRune.CARLIFE_NAVBAR
                  && i3 == 2
                  && StatusBarManagerService.this.mCarLifeBar != null) {
                try {
                  StatusBarManagerService.this.mCarLifeBar.abortTransient(i, i2);
                } catch (RemoteException unused2) {
                  Slog.i("StatusBarManagerService", "RemoteException for abortTransient");
                }
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void toggleRecentAppsToType(int i) {
            IStatusBar iStatusBar;
            synchronized (StatusBarManagerService.this.mBarLock) {
              if (StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i)) != null) {
                try {
                  ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i)))
                      .toggleRecentApps();
                  for (Map.Entry entry : StatusBarManagerService.this.mBarMap.entrySet()) {
                    if (((Integer) entry.getKey()).intValue() != i
                        && (iStatusBar = (IStatusBar) entry.getValue()) != null) {
                      iStatusBar.hideRecentApps(false, true);
                    }
                  }
                } catch (RemoteException unused) {
                }
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void showRecentAppsToType(boolean z, int i) {
            IStatusBar iStatusBar;
            synchronized (StatusBarManagerService.this.mBarLock) {
              if (StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i)) != null) {
                try {
                  ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i)))
                      .showRecentApps(z);
                  for (Map.Entry entry : StatusBarManagerService.this.mBarMap.entrySet()) {
                    if (((Integer) entry.getKey()).intValue() != i
                        && (iStatusBar = (IStatusBar) entry.getValue()) != null) {
                      iStatusBar.hideRecentApps(false, true);
                    }
                  }
                } catch (RemoteException unused) {
                  Slog.i("StatusBarManagerService", "RemoteException for SHOW_RECENT_APPS");
                }
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void hideRecentAppsFromType(boolean z, boolean z2, int i) {
            synchronized (StatusBarManagerService.this.mBarLock) {
              if (StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i)) != null) {
                try {
                  ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i)))
                      .hideRecentApps(z, z2);
                } catch (RemoteException unused) {
                  Slog.i("StatusBarManagerService", "RemoteException for HIDE_RECENT_APPS");
                }
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void toggleKeyboardShortcutsMenuToType(int i, int i2) {
            synchronized (StatusBarManagerService.this.mBarLock) {
              if (StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i2)) != null) {
                try {
                  ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i2)))
                      .toggleKeyboardShortcutsMenu(i);
                } catch (RemoteException unused) {
                }
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void onFocusedDisplayChanged(int i) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                Iterator it = StatusBarManagerService.this.mBarMap.entrySet().iterator();
                while (it.hasNext()) {
                  IStatusBar iStatusBar = (IStatusBar) ((Map.Entry) it.next()).getValue();
                  if (iStatusBar != null) {
                    iStatusBar.onFocusedDisplayChanged(i);
                  }
                }
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void notifySamsungPayInfo(int i, boolean z, Rect rect) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.notifySamsungPayInfo(i, z, rect);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void notifyRequestedSystemKey(boolean z, boolean z2) {
            Slog.d(
                "StatusBarManagerService", "notifyRequestedSystemKey recent=" + z + " home=" + z2);
            if (StatusBarManagerService.this.mBar == null) {
              return;
            }
            try {
              StatusBarManagerService.this.mBar.notifyRequestedSystemKey(z, z2);
            } catch (RemoteException unused) {
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent) {
            Slog.d("StatusBarManagerService", "sendThreeFingerGestureKeyEvent");
            if (StatusBarManagerService.this.mBar == null) {
              return;
            }
            try {
              StatusBarManagerService.this.mBar.sendThreeFingerGestureKeyEvent(keyEvent);
            } catch (RemoteException unused) {
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void notifyRequestedGameToolsWin(boolean z) {
            Slog.d("StatusBarManagerService", "notifyRequestedGameToolsWin attached=" + z);
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.notifyRequestedGameToolsWin(z);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void setSysUiSafeMode(boolean z) {
            StatusBarManagerService.this.mSysUiSafeMode = z;
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void sendKeyEventToDesktopTaskbarToType(KeyEvent keyEvent, int i) {
            StatusBarManagerService.this.enforceStatusBarService();
            if (StatusBarManagerService.this.checkBarMapState(i)) {
              try {
                ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i)))
                    .sendKeyEventToDesktopTaskbar(keyEvent);
              } catch (RemoteException unused) {
              }
            }
          }

          @Override // com.android.server.statusbar.StatusBarManagerInternal
          public void startSearcleByHomeKey(boolean z, boolean z2) {
            Slog.d(
                "StatusBarManagerService", "startSearcleByHomeKey down=" + z + " longPress=" + z2);
            if (StatusBarManagerService.this.mBar == null) {
              return;
            }
            try {
              StatusBarManagerService.this.mBar.startSearcleByHomeKey(z, z2);
            } catch (RemoteException unused) {
            }
          }
        };
    this.mInternalService = statusBarManagerInternal;
    this.mGlobalActionsProvider =
        new GlobalActionsProvider() { // from class:
                                      // com.android.server.statusbar.StatusBarManagerService.3
          @Override // com.android.server.policy.GlobalActionsProvider
          public boolean isGlobalActionsDisabled() {
            return (((UiState) StatusBarManagerService.this.mDisplayUiState.get(0)).getDisabled2()
                    & 8)
                != 0;
          }

          @Override // com.android.server.policy.GlobalActionsProvider
          public void setGlobalActionsListener(
              GlobalActionsProvider.GlobalActionsListener globalActionsListener) {
            StatusBarManagerService.this.mGlobalActionListener = globalActionsListener;
            StatusBarManagerService.this.mGlobalActionListener.onGlobalActionsAvailableChanged(
                StatusBarManagerService.this.mBar != null);
          }

          @Override // com.android.server.policy.GlobalActionsProvider
          public void showGlobalActions(int i) {
            if (StatusBarManagerService.this.mBar != null) {
              try {
                StatusBarManagerService.this.mBar.showGlobalActionsMenu(i);
              } catch (RemoteException unused) {
              }
            }
          }
        };
    this.mIsSecCustomTileMap = new ConcurrentHashMap();
    this.mDisableHistoryList = new ArrayList();
    this.mStatusBarHistoryList = new ArrayList();
    this.mSysUiSafeMode = false;
    this.mContext = context;
    LocalServices.addService(StatusBarManagerInternal.class, statusBarManagerInternal);
    sparseArray.put(0, new UiState());
    this.mDisplayUiStateDex.put(0, new UiState());
    SemDesktopModeManager semDesktopModeManager =
        (SemDesktopModeManager) context.getSystemService("desktopmode");
    this.mSemDesktopModeManager = semDesktopModeManager;
    if (semDesktopModeManager != null) {
      semDesktopModeManager.registerListener(this.mDesktopModeListener);
    }
    ((DisplayManager) context.getSystemService("display")).registerDisplayListener(this, handler);
    this.mActivityTaskManager =
        (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
    this.mPackageManagerInternal =
        (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    this.mActivityManagerInternal =
        (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
    this.mTileRequestTracker = new TileRequestTracker(context);
    this.mSessionMonitor = new SessionMonitor(context);
    this.mUserManagerInternal =
        (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
  }

  public void publishGlobalActionsProvider() {
    if (LocalServices.getService(GlobalActionsProvider.class) == null) {
      LocalServices.addService(GlobalActionsProvider.class, this.mGlobalActionsProvider);
    }
  }

  public final IOverlayManager getOverlayManager() {
    if (this.mOverlayManager == null) {
      IOverlayManager asInterface =
          IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
      this.mOverlayManager = asInterface;
      if (asInterface == null) {
        Slog.w("StatusBarManager", "warning: no OVERLAY_SERVICE");
      }
    }
    return this.mOverlayManager;
  }

  @Override // android.hardware.display.DisplayManager.DisplayListener
  public void onDisplayRemoved(int i) {
    synchronized (this.mLock) {
      this.mDisplayUiState.remove(i);
    }
  }

  public final boolean isDisable2FlagSet(int i) {
    return (((UiState) this.mDisplayUiState.get(0)).getDisabled2() & i) == i;
  }

  public void expandNotificationsPanel() {
    enforceExpandStatusBar();
    if (isDisable2FlagSet(4) || this.mBar == null) {
      return;
    }
    try {
      this.mBar.animateExpandNotificationsPanel();
    } catch (RemoteException unused) {
    }
  }

  public void collapsePanels() {
    if (checkCanCollapseStatusBar("collapsePanels") && this.mBar != null) {
      try {
        this.mBar.animateCollapsePanels();
      } catch (RemoteException unused) {
      }
    }
  }

  public void togglePanel() {
    if (!checkCanCollapseStatusBar("togglePanel") || isDisable2FlagSet(4) || this.mBar == null) {
      return;
    }
    try {
      this.mBar.togglePanel();
    } catch (RemoteException unused) {
    }
  }

  public void expandSettingsPanel(String str) {
    enforceExpandStatusBar();
    if (this.mBar != null) {
      try {
        this.mBar.animateExpandSettingsPanel(str);
      } catch (RemoteException unused) {
      }
    }
  }

  public void addTile(ComponentName componentName) {
    enforceStatusBarOrShell();
    if (this.mBar != null) {
      try {
        this.mBar.addQsTile(componentName);
      } catch (RemoteException unused) {
      }
    }
  }

  public void remTile(ComponentName componentName) {
    enforceStatusBarOrShell();
    if (this.mBar != null) {
      try {
        this.mBar.remQsTile(componentName);
      } catch (RemoteException unused) {
      }
    }
  }

  public void clickTile(ComponentName componentName) {
    enforceStatusBarOrShell();
    if (this.mBar != null) {
      try {
        this.mBar.clickQsTile(componentName);
      } catch (RemoteException unused) {
      }
    }
  }

  public void handleSystemKey(KeyEvent keyEvent) {
    if (checkCanCollapseStatusBar("handleSystemKey")) {
      this.mLastSystemKey = keyEvent.getKeyCode();
      if (this.mBar != null) {
        try {
          this.mBar.handleSystemKey(keyEvent);
        } catch (RemoteException unused) {
        }
      }
    }
  }

  public int getLastSystemKey() {
    enforceStatusBar();
    return this.mLastSystemKey;
  }

  public void showPinningEnterExitToast(boolean z) {
    if (this.mBar != null) {
      try {
        this.mBar.showPinningEnterExitToast(z);
      } catch (RemoteException unused) {
      }
    }
  }

  public void showPinningEscapeToast() {
    if (this.mBar != null) {
      try {
        this.mBar.showPinningEscapeToast();
      } catch (RemoteException unused) {
      }
    }
  }

  public void showAuthenticationDialog(
      PromptInfo promptInfo,
      IBiometricSysuiReceiver iBiometricSysuiReceiver,
      int[] iArr,
      boolean z,
      boolean z2,
      int i,
      long j,
      String str,
      long j2) {
    enforceBiometricDialog();
    if (this.mBar != null) {
      try {
        this.mBar.showAuthenticationDialog(
            promptInfo, iBiometricSysuiReceiver, iArr, z, z2, i, j, str, j2);
      } catch (RemoteException unused) {
      }
    }
  }

  public void onBiometricAuthenticated(int i) {
    enforceBiometricDialog();
    if (this.mBar != null) {
      try {
        this.mBar.onBiometricAuthenticated(i);
      } catch (RemoteException unused) {
      }
    }
  }

  public void onBiometricHelp(int i, String str) {
    enforceBiometricDialog();
    if (this.mBar != null) {
      try {
        this.mBar.onBiometricHelp(i, str);
      } catch (RemoteException unused) {
      }
    }
  }

  public void onBiometricError(int i, int i2, int i3) {
    enforceBiometricDialog();
    if (this.mBar != null) {
      try {
        this.mBar.onBiometricError(i, i2, i3);
      } catch (RemoteException unused) {
      }
    }
  }

  public void hideAuthenticationDialog(long j) {
    enforceBiometricDialog();
    if (this.mBar != null) {
      try {
        this.mBar.hideAuthenticationDialog(j);
      } catch (RemoteException unused) {
      }
    }
  }

  public void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) {
    enforceStatusBarService();
    synchronized (this.mLock) {
      this.mBiometricContextListener = iBiometricContextListener;
    }
    if (this.mBar != null) {
      try {
        this.mBar.setBiometicContextListener(iBiometricContextListener);
      } catch (RemoteException unused) {
      }
    }
  }

  public void setUdfpsRefreshRateCallback(
      IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) {
    enforceStatusBarService();
    if (this.mBar != null) {
      try {
        this.mBar.setUdfpsRefreshRateCallback(iUdfpsRefreshRateRequestCallback);
      } catch (RemoteException unused) {
      }
    }
  }

  public void startTracing() {
    if (this.mBar != null) {
      try {
        this.mBar.startTracing();
        this.mTracingEnabled = true;
      } catch (RemoteException unused) {
      }
    }
  }

  public void stopTracing() {
    if (this.mBar != null) {
      try {
        this.mTracingEnabled = false;
        this.mBar.stopTracing();
      } catch (RemoteException unused) {
      }
    }
  }

  public boolean isTracing() {
    return this.mTracingEnabled;
  }

  public void disable(int i, IBinder iBinder, String str) {
    disableForUser(i, iBinder, str, this.mCurrentUserId);
  }

  public void disableForUser(int i, IBinder iBinder, String str, int i2) {
    enforceStatusBar();
    synchronized (this.mLock) {
      disableLocked(0, i2, i, iBinder, str, 1, 0);
    }
  }

  public void disable2(int i, IBinder iBinder, String str) {
    disable2ForUser(i, iBinder, str, this.mCurrentUserId);
  }

  public void disable2ForUser(int i, IBinder iBinder, String str, int i2) {
    enforceStatusBar();
    synchronized (this.mLock) {
      disableLocked(0, i2, i, iBinder, str, 2, 0);
    }
  }

  public final void disableLocked(
      int i, int i2, int i3, IBinder iBinder, String str, int i4, int i5) {
    final int gatherDisableActionsLocked;
    manageDisableListLocked(i2, i3, iBinder, str, i4, i5, i);
    if (isSupportLargeCoverScreen()) {
      gatherDisableActionsLocked = gatherDisableActionsLocked(this.mCurrentUserId, 1, i5, i);
    } else {
      gatherDisableActionsLocked = gatherDisableActionsLocked(this.mCurrentUserId, 1, i5);
    }
    int gatherDisableActionsLocked2 = gatherDisableActionsLocked(this.mCurrentUserId, 2, i5);
    UiState uiState = getUiState(i, i5);
    if (!uiState.disableEquals(gatherDisableActionsLocked, gatherDisableActionsLocked2)) {
      uiState.setDisabled(gatherDisableActionsLocked, gatherDisableActionsLocked2);
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              StatusBarManagerService.this.lambda$disableLocked$0(gatherDisableActionsLocked);
            }
          });
      synchronized (this.mBarLock) {
        if (this.mBarMap.get(Integer.valueOf(i5)) != null) {
          try {
            ((IStatusBar) this.mBarMap.get(Integer.valueOf(i5)))
                .disable(i, gatherDisableActionsLocked, gatherDisableActionsLocked2);
          } catch (RemoteException e) {
            Slog.e("StatusBarManagerService", "disable Exception = " + e);
          }
        }
      }
    }
    if (this.mEdgeInternal == null) {
      this.mEdgeInternal =
          (EdgeManagerInternal) LocalServices.getService(EdgeManagerInternal.class);
    }
    EdgeManagerInternal edgeManagerInternal = this.mEdgeInternal;
    if (edgeManagerInternal != null) {
      edgeManagerInternal.statusBarDisabled(
          gatherDisableActionsLocked, gatherDisableActionsLocked2);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$disableLocked$0(int i) {
    this.mNotificationDelegate.onSetDisabled(i);
  }

  public int[] getDisableFlags(IBinder iBinder, int i) {
    int i2;
    int i3;
    enforceStatusBar();
    synchronized (this.mLock) {
      i2 = 0;
      if (iBinder == null && i == -1) {
        i2 = gatherDisableActionsLocked(this.mCurrentUserId, 1, 0);
        i3 = 0;
      } else {
        DisableRecord disableRecord =
            (DisableRecord) findMatchingRecordLocked(iBinder, i, 0).second;
        if (disableRecord != null) {
          i2 = disableRecord.what1;
          i3 = disableRecord.what2;
        } else {
          i3 = 0;
        }
      }
    }
    return new int[] {i2, i3};
  }

  public void runGcForTest() {
    if (!Build.IS_DEBUGGABLE) {
      throw new SecurityException("runGcForTest requires a debuggable build");
    }
    GcUtils.runGcAndFinalizersSync();
    if (this.mBar != null) {
      try {
        this.mBar.runGcForTest();
      } catch (RemoteException unused) {
      }
    }
  }

  public void setIcon(String str, String str2, int i, int i2, String str3) {
    enforceStatusBar();
    synchronized (this.mIcons) {
      StatusBarIcon statusBarIcon = new StatusBarIcon(str2, UserHandle.SYSTEM, i, i2, 0, str3);
      this.mIcons.put(str, statusBarIcon);
      Iterator it = this.mBarMap.entrySet().iterator();
      while (it.hasNext()) {
        IStatusBar iStatusBar = (IStatusBar) ((Map.Entry) it.next()).getValue();
        if (iStatusBar != null) {
          try {
            iStatusBar.setIcon(str, statusBarIcon);
          } catch (RemoteException unused) {
          }
        }
      }
    }
  }

  public void setIconVisibility(String str, boolean z) {
    enforceStatusBar();
    synchronized (this.mIcons) {
      StatusBarIcon statusBarIcon = (StatusBarIcon) this.mIcons.get(str);
      if (statusBarIcon == null) {
        return;
      }
      if (statusBarIcon.visible != z) {
        statusBarIcon.visible = z;
        Iterator it = this.mBarMap.entrySet().iterator();
        while (it.hasNext()) {
          IStatusBar iStatusBar = (IStatusBar) ((Map.Entry) it.next()).getValue();
          if (iStatusBar != null) {
            try {
              iStatusBar.setIcon(str, statusBarIcon);
            } catch (RemoteException unused) {
            }
          }
        }
      }
    }
  }

  public void removeIcon(String str) {
    enforceStatusBar();
    synchronized (this.mIcons) {
      this.mIcons.remove(str);
      Iterator it = this.mBarMap.entrySet().iterator();
      while (it.hasNext()) {
        IStatusBar iStatusBar = (IStatusBar) ((Map.Entry) it.next()).getValue();
        if (iStatusBar != null) {
          try {
            iStatusBar.removeIcon(str);
          } catch (RemoteException unused) {
          }
        }
      }
    }
  }

  public void setImeWindowStatus(
      final int i, final IBinder iBinder, final int i2, final int i3, final boolean z) {
    enforceStatusBar();
    synchronized (this.mLock) {
      getUiState(i, 0).setImeWindowState(i2, i3, z, iBinder);
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              StatusBarManagerService.this.lambda$setImeWindowStatus$1(i, iBinder, i2, i3, z);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setImeWindowStatus$1(
      int i, IBinder iBinder, int i2, int i3, boolean z) {
    if (this.mBar == null) {
      return;
    }
    try {
      this.mBar.setImeWindowStatus(i, iBinder, i2, i3, z);
    } catch (RemoteException unused) {
    }
  }

  public final void setDisableFlags(int i, int i2, String str, int i3) {
    enforceStatusBarService();
    int i4 = (-134152193) & i2;
    if (i4 != 0) {
      Slog.e(
          "StatusBarManagerService",
          "Unknown disable flags: 0x" + Integer.toHexString(i4),
          new RuntimeException());
    }
    synchronized (this.mLock) {
      disableLocked(i, this.mCurrentUserId, i2, this.mSysUiVisToken, str, 1, i3);
    }
  }

  public RegisterStatusBarResult registerStatusBarAsType(IStatusBar iStatusBar, final int i) {
    ArrayMap arrayMap;
    RegisterStatusBarResult registerStatusBarResult;
    enforceStatusBarService();
    Slog.i("StatusBarManagerService", "start!! registerStatusBarAsType bar=" + iStatusBar);
    this.mBarMap.put(Integer.valueOf(i), iStatusBar);
    try {
      iStatusBar
          .asBinder()
          .linkToDeath(
              new IBinder
                  .DeathRecipient() { // from class:
                                      // com.android.server.statusbar.StatusBarManagerService.4
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                  synchronized (StatusBarManagerService.this.mBarLock) {
                    StatusBarManagerService.this.mBarMap.remove(Integer.valueOf(i));
                  }
                  StatusBarManagerService.this.notifyBarAttachChanged();
                }
              },
              0);
    } catch (RemoteException e) {
      Slog.e("StatusBarManagerService", "Unable to register Death Recipient for DEX status bar", e);
    }
    notifyBarAttachChanged();
    synchronized (this.mIcons) {
      arrayMap = new ArrayMap(this.mIcons);
    }
    synchronized (this.mLock) {
      UiState uiState = (UiState) this.mDisplayUiStateDex.get(0);
      registerStatusBarResult =
          new RegisterStatusBarResult(
              arrayMap,
              gatherDisableActionsLocked(this.mCurrentUserId, 1, 1),
              uiState.mAppearance,
              uiState.mAppearanceRegions,
              uiState.mImeWindowVis,
              uiState.mImeBackDisposition,
              uiState.mShowImeSwitcher,
              gatherDisableActionsLocked(this.mCurrentUserId, 2, 1),
              uiState.mImeToken,
              uiState.mNavbarColorManagedByIme,
              uiState.mBehavior,
              uiState.mRequestedVisibleTypes,
              uiState.mPackageName,
              uiState.mTransientBarTypes,
              uiState.mLetterboxDetails);
    }
    return registerStatusBarResult;
  }

  public void disableToType(int i, IBinder iBinder, String str, int i2) {
    disableForUserToType(i, iBinder, str, this.mCurrentUserId, i2);
  }

  public void disableForUserToType(int i, IBinder iBinder, String str, int i2, int i3) {
    enforceStatusBar();
    synchronized (this.mLock) {
      disableLocked(0, i2, i, iBinder, str, 1, i3);
    }
  }

  public void disable2ToType(int i, IBinder iBinder, String str, int i2) {
    disable2ForUserToType(i, iBinder, str, this.mCurrentUserId, i2);
  }

  public void disable2ForUserToType(int i, IBinder iBinder, String str, int i2, int i3) {
    enforceStatusBar();
    synchronized (this.mLock) {
      disableLocked(0, i2, i, iBinder, str, 2, i3);
    }
  }

  public int[] getDisableFlagsToType(IBinder iBinder, int i, int i2) {
    int i3;
    int i4;
    enforceStatusBar();
    synchronized (this.mLock) {
      i3 = 0;
      if (iBinder == null && i == -1) {
        i3 = gatherDisableActionsLocked(this.mCurrentUserId, 1, i2);
        i4 = 0;
      } else {
        DisableRecord disableRecord =
            (DisableRecord) findMatchingRecordLocked(iBinder, i, i2).second;
        if (disableRecord != null) {
          i3 = disableRecord.what1;
          i4 = disableRecord.what2;
        } else {
          i4 = 0;
        }
      }
    }
    return new int[] {i3, i4};
  }

  public void setPanelExpandStateToType(boolean z, int i) {
    Slog.d(
        "StatusBarManagerService",
        "  setPanelExpandStateToType : state = " + z + ", barType = " + i);
    this.mPanelExpandStateMap.put(Integer.valueOf(i), Boolean.valueOf(z));
    this.mQsPanelExpandStateMap.put(Integer.valueOf(i), Boolean.valueOf(z));
  }

  public boolean getPanelExpandStateToType(int i) {
    if (this.mPanelExpandStateMap.get(Integer.valueOf(i)) != null) {
      return ((Boolean) this.mPanelExpandStateMap.get(Integer.valueOf(i))).booleanValue();
    }
    return false;
  }

  public boolean getQuickSettingPanelExpandStateToType(int i) {
    if (this.mQsPanelExpandStateMap.get(Integer.valueOf(i)) != null) {
      return ((Boolean) this.mQsPanelExpandStateMap.get(Integer.valueOf(i))).booleanValue();
    }
    return false;
  }

  public void expandNotificationsPanelToType(int i) {
    enforceExpandStatusBar();
    synchronized (this.mBarLock) {
      if (this.mBarMap.get(Integer.valueOf(i)) != null) {
        try {
          ((IStatusBar) this.mBarMap.get(Integer.valueOf(i))).animateExpandNotificationsPanel();
        } catch (RemoteException unused) {
        }
      }
    }
  }

  public void collapsePanelsToType(int i) {
    if (checkCanCollapseStatusBar("collapsePanelsToType")) {
      synchronized (this.mBarLock) {
        if (this.mBarMap.get(Integer.valueOf(i)) != null) {
          try {
            ((IStatusBar) this.mBarMap.get(Integer.valueOf(i))).animateCollapsePanels();
          } catch (RemoteException unused) {
          }
        }
      }
    }
  }

  public void expandSettingsPanelToType(String str, int i) {
    enforceExpandStatusBar();
    synchronized (this.mBarLock) {
      if (this.mBarMap.get(Integer.valueOf(i)) != null) {
        try {
          ((IStatusBar) this.mBarMap.get(Integer.valueOf(i))).animateExpandSettingsPanel(str);
        } catch (RemoteException unused) {
        }
      }
    }
  }

  public void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) {
    enforceStatusBarService();
    if (this.mBar != null) {
      try {
        this.mBar.sendKeyEventToDesktopTaskbar(keyEvent);
      } catch (RemoteException unused) {
      }
    }
  }

  public void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) {
    if (this.mBar != null) {
      try {
        this.mBar.setNavigationBarShortcut(str, remoteViews, i, i2);
      } catch (RemoteException unused) {
      }
    }
  }

  public void resetScheduleAutoHide() {
    if (this.mBar != null) {
      try {
        this.mBar.resetScheduleAutoHide();
      } catch (RemoteException unused) {
      }
    }
  }

  public void setIndicatorBgColor(int i) {
    enforceStatusBar();
  }

  public final UiState getUiState(int i, int i2) {
    if (i2 == 1) {
      UiState uiState = (UiState) this.mDisplayUiStateDex.get(i);
      if (uiState != null) {
        return uiState;
      }
      UiState uiState2 = new UiState();
      this.mDisplayUiStateDex.put(i, uiState2);
      return uiState2;
    }
    UiState uiState3 = (UiState) this.mDisplayUiState.get(i);
    if (uiState3 != null) {
      return uiState3;
    }
    UiState uiState4 = new UiState();
    this.mDisplayUiState.put(i, uiState4);
    return uiState4;
  }

  public class UiState {
    public int mAppearance;
    public AppearanceRegion[] mAppearanceRegions;
    public int mBehavior;
    public int mDisabled1;
    public int mDisabled2;
    public int mImeBackDisposition;
    public IBinder mImeToken;
    public int mImeWindowVis;
    public LetterboxDetails[] mLetterboxDetails;
    public boolean mNavbarColorManagedByIme;
    public String mPackageName;
    public int mRequestedVisibleTypes;
    public boolean mShowImeSwitcher;
    public int mTransientBarTypes;

    public UiState() {
      this.mAppearance = 0;
      this.mAppearanceRegions = new AppearanceRegion[0];
      this.mNavbarColorManagedByIme = false;
      this.mRequestedVisibleTypes = WindowInsets.Type.defaultVisible();
      this.mPackageName = "none";
      this.mDisabled1 = 0;
      this.mDisabled2 = 0;
      this.mImeWindowVis = 0;
      this.mImeBackDisposition = 0;
      this.mShowImeSwitcher = false;
      this.mImeToken = null;
      this.mLetterboxDetails = new LetterboxDetails[0];
    }

    public final void setBarAttributes(
        int i,
        AppearanceRegion[] appearanceRegionArr,
        boolean z,
        int i2,
        int i3,
        String str,
        LetterboxDetails[] letterboxDetailsArr) {
      this.mAppearance = i;
      this.mAppearanceRegions = appearanceRegionArr;
      this.mNavbarColorManagedByIme = z;
      this.mBehavior = i2;
      this.mRequestedVisibleTypes = i3;
      this.mPackageName = str;
      this.mLetterboxDetails = letterboxDetailsArr;
    }

    public final void showTransient(int i) {
      this.mTransientBarTypes = i | this.mTransientBarTypes;
    }

    public final void clearTransient(int i) {
      this.mTransientBarTypes = (~i) & this.mTransientBarTypes;
    }

    public final int getDisabled1() {
      return this.mDisabled1;
    }

    public final int getDisabled2() {
      return this.mDisabled2;
    }

    public final void setDisabled(int i, int i2) {
      this.mDisabled1 = i;
      this.mDisabled2 = i2;
    }

    public final boolean disableEquals(int i, int i2) {
      return this.mDisabled1 == i && this.mDisabled2 == i2;
    }

    public final void setImeWindowState(int i, int i2, boolean z, IBinder iBinder) {
      this.mImeWindowVis = i;
      this.mImeBackDisposition = i2;
      this.mShowImeSwitcher = z;
      this.mImeToken = iBinder;
    }
  }

  public final void enforceStatusBarOrShell() {
    if (Binder.getCallingUid() == 2000) {
      return;
    }
    enforceStatusBar();
  }

  public final void enforceStatusBar() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.STATUS_BAR", "StatusBarManagerService");
    makeStatusBarHistory(0, "null");
  }

  public final void enforceExpandStatusBar() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.EXPAND_STATUS_BAR", "StatusBarManagerService");
    makeStatusBarHistory(1, "null");
  }

  public final void enforceStatusBarService() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.STATUS_BAR_SERVICE", "StatusBarManagerService");
    makeStatusBarHistory(2, "null");
  }

  public final void enforceBiometricDialog() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MANAGE_BIOMETRIC_DIALOG", "StatusBarManagerService");
  }

  public final void enforceMediaContentControl() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.MEDIA_CONTENT_CONTROL", "StatusBarManagerService");
  }

  public final void enforceControlDeviceStatePermission() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONTROL_DEVICE_STATE", "StatusBarManagerService");
  }

  public final boolean doesCallerHoldInteractAcrossUserPermission() {
    return this.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL")
            == 0
        || this.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") == 0;
  }

  public final boolean checkCanCollapseStatusBar(String str) {
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    if (CompatChanges.isChangeEnabled(173031413L, callingUid)) {
      enforceStatusBar();
      return true;
    }
    if (this.mContext.checkPermission("android.permission.STATUS_BAR", callingPid, callingUid)
        == 0) {
      return true;
    }
    enforceExpandStatusBar();
    if (this.mActivityTaskManager.canCloseSystemDialogs(callingPid, callingUid)) {
      return true;
    }
    Slog.e(
        "StatusBarManagerService",
        "Permission Denial: Method "
            + str
            + "() requires permission android.permission.STATUS_BAR, ignoring call.");
    return false;
  }

  public final boolean isSupportLargeCoverScreen() {
    return SemFloatingFeature.getInstance()
        .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
        .contains("LARGESCREEN");
  }

  public RegisterStatusBarResult registerStatusBar(IStatusBar iStatusBar) {
    ArrayMap arrayMap;
    RegisterStatusBarResult registerStatusBarResult;
    enforceStatusBarService();
    Slog.i("StatusBarManagerService", "registerStatusBar bar=" + iStatusBar);
    this.mBar = iStatusBar;
    this.mBarMap.put(0, iStatusBar);
    try {
      this.mBar
          .asBinder()
          .linkToDeath(
              new IBinder
                  .DeathRecipient() { // from class:
                                      // com.android.server.statusbar.StatusBarManagerService.5
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                  StatusBarManagerService.this.mBar = null;
                  synchronized (StatusBarManagerService.this.mBarLock) {
                    StatusBarManagerService.this.mBarMap.remove(0);
                  }
                  StatusBarManagerService.this.notifyBarAttachChanged();
                }
              },
              0);
    } catch (RemoteException e) {
      Slog.e("StatusBarManagerService", "Unable to register Death Recipient for status bar", e);
    }
    notifyBarAttachChanged();
    synchronized (this.mIcons) {
      arrayMap = new ArrayMap(this.mIcons);
    }
    synchronized (this.mLock) {
      UiState uiState = (UiState) this.mDisplayUiState.get(0);
      registerStatusBarResult =
          new RegisterStatusBarResult(
              arrayMap,
              gatherDisableActionsLocked(this.mCurrentUserId, 1, 0),
              uiState.mAppearance,
              uiState.mAppearanceRegions,
              uiState.mImeWindowVis,
              uiState.mImeBackDisposition,
              uiState.mShowImeSwitcher,
              gatherDisableActionsLocked(this.mCurrentUserId, 2, 0),
              uiState.mImeToken,
              uiState.mNavbarColorManagedByIme,
              uiState.mBehavior,
              uiState.mRequestedVisibleTypes,
              uiState.mPackageName,
              uiState.mTransientBarTypes,
              uiState.mLetterboxDetails);
    }
    return registerStatusBarResult;
  }

  public final void notifyBarAttachChanged() {
    UiThread.getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda7
              @Override // java.lang.Runnable
              public final void run() {
                StatusBarManagerService.this.lambda$notifyBarAttachChanged$2();
              }
            });
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda8
          @Override // java.lang.Runnable
          public final void run() {
            StatusBarManagerService.this.lambda$notifyBarAttachChanged$3();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notifyBarAttachChanged$2() {
    GlobalActionsProvider.GlobalActionsListener globalActionsListener = this.mGlobalActionListener;
    if (globalActionsListener == null) {
      return;
    }
    globalActionsListener.onGlobalActionsAvailableChanged(this.mBar != null);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notifyBarAttachChanged$3() {
    synchronized (this.mLock) {
      setUdfpsRefreshRateCallback(this.mUdfpsRefreshRateRequestCallback);
      setBiometicContextListener(this.mBiometricContextListener);
    }
  }

  public void registerOverlayManager(IOverlayManager iOverlayManager) {
    this.mOverlayManager = iOverlayManager;
  }

  public void onPanelRevealed(boolean z, int i) {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onPanelRevealed(z, i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void clearNotificationEffects() {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.clearEffects();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onPanelHidden() {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onPanelHidden();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void shutdown() {
    enforceStatusBarService();
    final String str = "userrequested";
    ShutdownCheckPoints.recordCheckPoint(Binder.getCallingPid(), "userrequested");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.prepareForPossibleShutdown();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
              StatusBarManagerService.lambda$shutdown$4(str);
            }
          });
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ void lambda$shutdown$4(String str) {
    ShutdownThread.shutdown(getUiContext(), str, false);
  }

  public void reboot(final boolean z) {
    enforceStatusBarService();
    final String str = z ? "safemode" : "userrequested";
    ShutdownCheckPoints.recordCheckPoint(Binder.getCallingPid(), str);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.prepareForPossibleShutdown();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              StatusBarManagerService.lambda$reboot$5(z, str);
            }
          });
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ void lambda$reboot$5(boolean z, String str) {
    if (z) {
      ShutdownThread.rebootSafeMode(getUiContext(), true);
    } else {
      ShutdownThread.reboot(getUiContext(), str, false);
    }
  }

  public void restart() {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              StatusBarManagerService.this.lambda$restart$6();
            }
          });
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$restart$6() {
    this.mActivityManagerInternal.restart();
  }

  public void onGlobalActionsShown() {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      GlobalActionsProvider.GlobalActionsListener globalActionsListener =
          this.mGlobalActionListener;
      if (globalActionsListener == null) {
        return;
      }
      globalActionsListener.onGlobalActionsShown();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onGlobalActionsHidden() {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      GlobalActionsProvider.GlobalActionsListener globalActionsListener =
          this.mGlobalActionListener;
      if (globalActionsListener == null) {
        return;
      }
      globalActionsListener.onGlobalActionsDismissed();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isFOTAAvailableForGlobalActions() {
    boolean z;
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (ShutdownThread.isFOTAAvailable(this.mContext)) {
        Slog.d("StatusBarManagerService", "FOTA update available when asking recovery system");
        z = true;
      } else {
        z = false;
      }
      return z;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationClick(String str, NotificationVisibility notificationVisibility) {
    enforceStatusBarService();
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationClick(
          callingUid, callingPid, str, notificationVisibility);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationActionClick(
      String str,
      int i,
      Notification.Action action,
      NotificationVisibility notificationVisibility,
      boolean z) {
    enforceStatusBarService();
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationActionClick(
          callingUid, callingPid, str, i, action, notificationVisibility, z);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationError(
      String str, String str2, int i, int i2, int i3, String str3, int i4) {
    enforceStatusBarService();
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationError(
          callingUid, callingPid, str, str2, i, i2, i3, str3, i4);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationClear(
      String str,
      int i,
      String str2,
      int i2,
      int i3,
      NotificationVisibility notificationVisibility) {
    enforceStatusBarService();
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationClear(
          callingUid, callingPid, str, i, str2, i2, i3, notificationVisibility);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationVisibilityChanged(
      NotificationVisibility[] notificationVisibilityArr,
      NotificationVisibility[] notificationVisibilityArr2) {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationVisibilityChanged(
          notificationVisibilityArr, notificationVisibilityArr2);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationExpansionChanged(String str, boolean z, boolean z2, int i) {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationExpansionChanged(str, z, z2, i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationDirectReplied(String str) {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationDirectReplied(str);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationSmartSuggestionsAdded(
      String str, int i, int i2, boolean z, boolean z2) {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationSmartSuggestionsAdded(str, i, i2, z, z2);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationSmartReplySent(
      String str, int i, CharSequence charSequence, int i2, boolean z) {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationSmartReplySent(str, i, charSequence, i2, z);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationSettingsViewed(String str) {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationSettingsViewed(str);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onClearAllNotifications(int i) {
    enforceStatusBarService();
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onClearAll(callingUid, callingPid, i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationBubbleChanged(String str, boolean z, int i) {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationBubbleChanged(str, z, i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onBubbleMetadataFlagChanged(String str, int i) {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onBubbleMetadataFlagChanged(str, i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void hideCurrentInputMethodForBubbles() {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      InputMethodManagerInternal.get().hideCurrentInputMethod(20);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void grantInlineReplyUriPermission(
      String str, Uri uri, UserHandle userHandle, String str2) {
    enforceStatusBarService();
    int callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.grantInlineReplyUriPermission(
          str, uri, userHandle, str2, callingUid);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void clearInlineReplyUriPermissions(String str) {
    enforceStatusBarService();
    int callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.clearInlineReplyUriPermissions(str, callingUid);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onNotificationFeedbackReceived(String str, Bundle bundle) {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mNotificationDelegate.onNotificationFeedbackReceived(str, bundle);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void onShellCommand(
      FileDescriptor fileDescriptor,
      FileDescriptor fileDescriptor2,
      FileDescriptor fileDescriptor3,
      String[] strArr,
      ShellCallback shellCallback,
      ResultReceiver resultReceiver) {
    new StatusBarShellCommand(this, this.mContext)
        .exec(
            this,
            fileDescriptor,
            fileDescriptor2,
            fileDescriptor3,
            strArr,
            shellCallback,
            resultReceiver);
  }

  public void showInattentiveSleepWarning() {
    enforceStatusBarService();
    if (this.mBar != null) {
      try {
        this.mBar.showInattentiveSleepWarning();
      } catch (RemoteException unused) {
      }
    }
  }

  public void dismissInattentiveSleepWarning(boolean z) {
    enforceStatusBarService();
    if (this.mBar != null) {
      try {
        this.mBar.dismissInattentiveSleepWarning(z);
      } catch (RemoteException unused) {
      }
    }
  }

  public void suppressAmbientDisplay(boolean z) {
    enforceStatusBarService();
    if (this.mBar != null) {
      try {
        this.mBar.suppressAmbientDisplay(z);
      } catch (RemoteException unused) {
      }
    }
  }

  public final void checkCallingUidPackage(String str, int i, int i2) {
    if (UserHandle.getAppId(i)
        == UserHandle.getAppId(this.mPackageManagerInternal.getPackageUid(str, 0L, i2))) {
      return;
    }
    throw new SecurityException("Package " + str + " does not belong to the calling uid " + i);
  }

  public final ResolveInfo isComponentValidTileService(ComponentName componentName, int i) {
    ServiceInfo serviceInfo;
    Intent intent = new Intent("android.service.quicksettings.action.QS_TILE");
    intent.setComponent(componentName);
    ResolveInfo resolveService =
        this.mPackageManagerInternal.resolveService(
            intent,
            intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
            0L,
            i,
            Process.myUid());
    int componentEnabledSetting =
        this.mPackageManagerInternal.getComponentEnabledSetting(componentName, Process.myUid(), i);
    if (resolveService == null
        || (serviceInfo = resolveService.serviceInfo) == null
        || !resolveEnabledComponent(serviceInfo.enabled, componentEnabledSetting)
        || !"android.permission.BIND_QUICK_SETTINGS_TILE"
            .equals(resolveService.serviceInfo.permission)) {
      return null;
    }
    return resolveService;
  }

  public boolean isSecCustomTile(ComponentName componentName) {
    Bundle bundle;
    Boolean bool = (Boolean) this.mIsSecCustomTileMap.get(componentName);
    if (bool != null) {
      return bool.booleanValue();
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    boolean z = false;
    try {
      ServiceInfo serviceInfo =
          this.mContext.getPackageManager().getServiceInfo(componentName, 787072);
      if (serviceInfo != null && (bundle = serviceInfo.metaData) != null) {
        if (!""
            .equals(bundle.getString("android.service.quicksettings.SEM_DEFAULT_TILE_NAME", ""))) {
          z = true;
        }
      }
    } catch (PackageManager.NameNotFoundException unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    this.mIsSecCustomTileMap.put(componentName, Boolean.valueOf(z));
    return z;
  }

  public void requestTileServiceListeningState(ComponentName componentName, int i) {
    int callingUid = Binder.getCallingUid();
    String packageName = componentName.getPackageName();
    boolean isChangeEnabled = CompatChanges.isChangeEnabled(172251878L, callingUid);
    if (isSecCustomTile(componentName)) {
      Slog.d("StatusBarManagerService", "isSecCustomTile : componentName =" + componentName);
      isChangeEnabled = false;
    }
    if (isChangeEnabled) {
      int handleIncomingUser =
          this.mActivityManagerInternal.handleIncomingUser(
              Binder.getCallingPid(),
              callingUid,
              i,
              false,
              0,
              "requestTileServiceListeningState",
              packageName);
      checkCallingUidPackage(packageName, callingUid, handleIncomingUser);
      int currentUserId = this.mActivityManagerInternal.getCurrentUserId();
      int profileParentId = this.mUserManagerInternal.getProfileParentId(handleIncomingUser);
      if (handleIncomingUser != currentUserId && profileParentId != currentUserId) {
        if (CompatChanges.isChangeEnabled(242194868L, callingUid)) {
          return;
        }
        throw new IllegalArgumentException(
            "User " + handleIncomingUser + " is not the current user.");
      }
    }
    if (this.mBar != null) {
      try {
        this.mBar.requestTileServiceListeningState(componentName);
      } catch (RemoteException e) {
        Slog.e("StatusBarManagerService", "requestTileServiceListeningState", e);
      }
    }
  }

  public void requestAddTile(
      final ComponentName componentName,
      CharSequence charSequence,
      Icon icon,
      final int i,
      final IAddTileResultCallback iAddTileResultCallback) {
    int callingUid = Binder.getCallingUid();
    final String packageName = componentName.getPackageName();
    this.mActivityManagerInternal.handleIncomingUser(
        Binder.getCallingPid(), callingUid, i, false, 0, "requestAddTile", packageName);
    checkCallingUidPackage(packageName, callingUid, i);
    if (i != this.mActivityManagerInternal.getCurrentUserId()) {
      try {
        iAddTileResultCallback.onTileRequest(1003);
        return;
      } catch (RemoteException e) {
        Slog.e("StatusBarManagerService", "requestAddTile", e);
        return;
      }
    }
    ResolveInfo isComponentValidTileService = isComponentValidTileService(componentName, i);
    if (isComponentValidTileService == null || !isComponentValidTileService.serviceInfo.exported) {
      try {
        iAddTileResultCallback.onTileRequest(1002);
        return;
      } catch (RemoteException e2) {
        Slog.e("StatusBarManagerService", "requestAddTile", e2);
        return;
      }
    }
    if (ActivityManager.RunningAppProcessInfo.procStateToImportance(
            this.mActivityManagerInternal.getUidProcessState(callingUid))
        != 100) {
      try {
        iAddTileResultCallback.onTileRequest(1004);
        return;
      } catch (RemoteException e3) {
        Slog.e("StatusBarManagerService", "requestAddTile", e3);
        return;
      }
    }
    synchronized (this.mCurrentRequestAddTilePackages) {
      Long l = (Long) this.mCurrentRequestAddTilePackages.get(packageName);
      long nanoTime = System.nanoTime();
      if (l != null && nanoTime - l.longValue() < REQUEST_TIME_OUT) {
        try {
          iAddTileResultCallback.onTileRequest(1001);
        } catch (RemoteException e4) {
          Slog.e("StatusBarManagerService", "requestAddTile", e4);
        }
        return;
      }
      if (l != null) {
        cancelRequestAddTileInternal(packageName);
      }
      this.mCurrentRequestAddTilePackages.put(packageName, Long.valueOf(nanoTime));
      if (this.mTileRequestTracker.shouldBeDenied(i, componentName)) {
        if (clearTileAddRequest(packageName)) {
          try {
            iAddTileResultCallback.onTileRequest(0);
            return;
          } catch (RemoteException e5) {
            Slog.e("StatusBarManagerService", "requestAddTile - callback", e5);
            return;
          }
        }
        return;
      }
      IAddTileResultCallback.Stub stub =
          new IAddTileResultCallback
              .Stub() { // from class: com.android.server.statusbar.StatusBarManagerService.6
            public void onTileRequest(int i2) {
              if (i2 == 3) {
                i2 = 0;
              } else if (i2 == 0) {
                StatusBarManagerService.this.mTileRequestTracker.addDenial(i, componentName);
              } else if (i2 == 2) {
                StatusBarManagerService.this.mTileRequestTracker.resetRequests(i, componentName);
              }
              if (StatusBarManagerService.this.clearTileAddRequest(packageName)) {
                try {
                  iAddTileResultCallback.onTileRequest(i2);
                } catch (RemoteException e6) {
                  Slog.e("StatusBarManagerService", "requestAddTile - callback", e6);
                }
              }
            }
          };
      CharSequence loadLabel =
          isComponentValidTileService.serviceInfo.applicationInfo.loadLabel(
              this.mContext.getPackageManager());
      if (this.mBar != null) {
        try {
          this.mBar.requestAddTile(componentName, loadLabel, charSequence, icon, stub);
          return;
        } catch (RemoteException e6) {
          Slog.e("StatusBarManagerService", "requestAddTile", e6);
        }
      }
      clearTileAddRequest(packageName);
      try {
        iAddTileResultCallback.onTileRequest(1005);
      } catch (RemoteException e7) {
        Slog.e("StatusBarManagerService", "requestAddTile", e7);
      }
    }
  }

  public void cancelRequestAddTile(String str) {
    enforceStatusBar();
    cancelRequestAddTileInternal(str);
  }

  public final void cancelRequestAddTileInternal(String str) {
    clearTileAddRequest(str);
    if (this.mBar != null) {
      try {
        this.mBar.cancelRequestAddTile(str);
      } catch (RemoteException e) {
        Slog.e("StatusBarManagerService", "requestAddTile", e);
      }
    }
  }

  public final boolean clearTileAddRequest(String str) {
    boolean z;
    synchronized (this.mCurrentRequestAddTilePackages) {
      z = this.mCurrentRequestAddTilePackages.remove(str) != null;
    }
    return z;
  }

  public void onSessionStarted(int i, InstanceId instanceId) {
    this.mSessionMonitor.onSessionStarted(i, instanceId);
  }

  public void onSessionEnded(int i, InstanceId instanceId) {
    this.mSessionMonitor.onSessionEnded(i, instanceId);
  }

  public void registerSessionListener(int i, ISessionListener iSessionListener) {
    this.mSessionMonitor.registerSessionListener(i, iSessionListener);
  }

  public void unregisterSessionListener(int i, ISessionListener iSessionListener) {
    this.mSessionMonitor.unregisterSessionListener(i, iSessionListener);
  }

  public String[] getStatusBarIcons() {
    return this.mContext.getResources().getStringArray(17236309);
  }

  public void setNavBarMode(int i) {
    enforceStatusBar();
    if (i != 0 && i != 1) {
      throw new IllegalArgumentException("Supplied navBarMode not supported: " + i);
    }
    int i2 = this.mCurrentUserId;
    int userId = UserHandle.getUserId(Binder.getCallingUid());
    if (this.mCurrentUserId != userId && !doesCallerHoldInteractAcrossUserPermission()) {
      throw new SecurityException(
          "Calling user id: "
              + userId
              + ", cannot call on behalf of current user id: "
              + this.mCurrentUserId
              + ".");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        Settings.Secure.putIntForUser(
            this.mContext.getContentResolver(), "nav_bar_kids_mode", i, i2);
        Settings.Secure.putIntForUser(
            this.mContext.getContentResolver(), "nav_bar_force_visible", i, i2);
        IOverlayManager overlayManager = getOverlayManager();
        if (overlayManager != null
            && i == 1
            && isPackageSupported(KnoxCustomManagerService.NAV_BAR_MODE_3BUTTON_OVERLAY)) {
          overlayManager.setEnabledExclusiveInCategory(
              KnoxCustomManagerService.NAV_BAR_MODE_3BUTTON_OVERLAY, i2);
        }
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public int getNavBarMode() {
    enforceStatusBar();
    int i = this.mCurrentUserId;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      int intForUser =
          Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "nav_bar_kids_mode", i);
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return intForUser;
    } catch (Settings.SettingNotFoundException unused) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return 0;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final boolean isPackageSupported(String str) {
    if (str == null) {
      return false;
    }
    try {
      return this.mContext
              .getPackageManager()
              .getPackageInfo(str, PackageManager.PackageInfoFlags.of(0L))
          != null;
    } catch (PackageManager.NameNotFoundException unused) {
      return false;
    }
  }

  public void updateMediaTapToTransferSenderDisplay(
      int i,
      MediaRoute2Info mediaRoute2Info,
      IUndoMediaTransferCallback iUndoMediaTransferCallback) {
    enforceMediaContentControl();
    if (this.mBar != null) {
      try {
        this.mBar.updateMediaTapToTransferSenderDisplay(
            i, mediaRoute2Info, iUndoMediaTransferCallback);
      } catch (RemoteException e) {
        Slog.e("StatusBarManagerService", "updateMediaTapToTransferSenderDisplay", e);
      }
    }
  }

  public void updateMediaTapToTransferReceiverDisplay(
      int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
    enforceMediaContentControl();
    if (this.mBar != null) {
      try {
        this.mBar.updateMediaTapToTransferReceiverDisplay(i, mediaRoute2Info, icon, charSequence);
      } catch (RemoteException e) {
        Slog.e("StatusBarManagerService", "updateMediaTapToTransferReceiverDisplay", e);
      }
    }
  }

  public void registerNearbyMediaDevicesProvider(
      INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
    enforceMediaContentControl();
    if (this.mBar != null) {
      try {
        this.mBar.registerNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider);
      } catch (RemoteException e) {
        Slog.e("StatusBarManagerService", "registerNearbyMediaDevicesProvider", e);
      }
    }
  }

  public void unregisterNearbyMediaDevicesProvider(
      INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
    enforceMediaContentControl();
    if (this.mBar != null) {
      try {
        this.mBar.unregisterNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider);
      } catch (RemoteException e) {
        Slog.e("StatusBarManagerService", "unregisterNearbyMediaDevicesProvider", e);
      }
    }
  }

  public void showRearDisplayDialog(int i) {
    enforceControlDeviceStatePermission();
    if (this.mBar != null) {
      try {
        this.mBar.showRearDisplayDialog(i);
      } catch (RemoteException e) {
        Slog.e("StatusBarManagerService", "showRearDisplayDialog", e);
      }
    }
  }

  public void passThroughShellCommand(String[] strArr, FileDescriptor fileDescriptor) {
    enforceStatusBarOrShell();
    if (this.mBar == null) {
      return;
    }
    try {
      TransferPipe transferPipe = new TransferPipe();
      try {
        transferPipe.setBufferPrefix("  ");
        this.mBar.passThroughShellCommand(strArr, transferPipe.getWriteFd());
        transferPipe.go(fileDescriptor);
        transferPipe.close();
      } finally {
      }
    } catch (Throwable th) {
      Slog.e("StatusBarManagerService", "Error sending command to IStatusBar", th);
    }
  }

  public void manageDisableListLocked(
      int i, int i2, IBinder iBinder, String str, int i3, int i4, int i5) {
    int i6;
    IBinder iBinder2;
    int i7;
    String str2;
    String str3;
    DisableRecord disableRecord;
    int i8;
    if (str == null || !str.contains(KnoxVpnFirewallHelper.DELIMITER)) {
      i6 = i;
      iBinder2 = iBinder;
      i7 = i4;
      str2 = str;
      str3 = null;
    } else {
      String[] split = str.split(KnoxVpnFirewallHelper.DELIMITER);
      String str4 = split[0];
      i6 = i;
      iBinder2 = iBinder;
      i7 = i4;
      str3 = split[1];
      str2 = str4;
    }
    Pair findMatchingRecordLocked = findMatchingRecordLocked(iBinder2, i6, i7);
    int intValue = ((Integer) findMatchingRecordLocked.first).intValue();
    DisableRecord disableRecord2 = (DisableRecord) findMatchingRecordLocked.second;
    if (!iBinder.isBinderAlive()) {
      if (disableRecord2 != null) {
        this.mDisableRecords.remove(intValue);
        disableRecord2.token.unlinkToDeath(disableRecord2, 0);
        return;
      }
      return;
    }
    if (disableRecord2 != null) {
      if (disableRecord2.getFlags(i3) != i2) {
        disableRecord = disableRecord2;
        i8 = intValue;
        makeDisableHistory(i, i2, iBinder, str2, str3, i3, i4, i5);
      } else {
        disableRecord = disableRecord2;
        i8 = intValue;
      }
      if (isSupportLargeCoverScreen() && i3 == 1 && (23068672 & i2) != 0) {
        if (disableRecord.displayId == i5) {
          disableRecord.setFlags(i2, i3, str2);
        } else {
          Slog.i(
              "StatusBarManagerService",
              "Ignored flags("
                  + i2
                  + "), displayId is mismatched. record.display: "
                  + disableRecord.displayId
                  + " and displayId: "
                  + i5);
        }
      } else {
        disableRecord.setFlags(i2, i3, str2);
        Slog.d("StatusBarManagerService", "update existing record: " + disableRecord);
      }
      if (disableRecord.isEmpty()) {
        this.mDisableRecords.remove(i8);
        disableRecord.token.unlinkToDeath(disableRecord, 0);
        return;
      }
      return;
    }
    DisableRecord disableRecord3 = new DisableRecord(i, iBinder, i4, i5);
    disableRecord3.setFlags(i2, i3, str2);
    this.mDisableRecords.add(disableRecord3);
    if (i2 != 0) {
      makeDisableHistory(i, i2, iBinder, str2, str3, i3, i4, i5);
    }
  }

  public final Pair findMatchingRecordLocked(IBinder iBinder, int i, int i2) {
    DisableRecord disableRecord;
    int size = this.mDisableRecords.size();
    int i3 = 0;
    while (true) {
      if (i3 >= size) {
        disableRecord = null;
        break;
      }
      disableRecord = (DisableRecord) this.mDisableRecords.get(i3);
      if (disableRecord.token == iBinder
          && disableRecord.userId == i
          && disableRecord.barType == i2) {
        break;
      }
      i3++;
    }
    return new Pair(Integer.valueOf(i3), disableRecord);
  }

  public int gatherDisableActionsLocked(int i, int i2, int i3) {
    if (isSupportLargeCoverScreen() && i2 == 1) {
      return gatherDisableActionsLocked(i, i2, i3, 0);
    }
    int size = this.mDisableRecords.size();
    int i4 = 0;
    for (int i5 = 0; i5 < size; i5++) {
      DisableRecord disableRecord = (DisableRecord) this.mDisableRecords.get(i5);
      if (disableRecord.userId == i && disableRecord.barType == i3) {
        i4 |= disableRecord.getFlags(i2);
      }
    }
    return i4;
  }

  public int gatherDisableActionsLocked(int i, int i2, int i3, int i4) {
    int size = this.mDisableRecords.size();
    int i5 = 0;
    for (int i6 = 0; i6 < size; i6++) {
      DisableRecord disableRecord = (DisableRecord) this.mDisableRecords.get(i6);
      if (disableRecord.userId == i && disableRecord.barType == i3) {
        int flags = disableRecord.getFlags(i2);
        if (!(i4 == 0 || i4 == 1) || (23068672 & flags) == 0) {
          i5 |= disableRecord.getFlags(i2);
        } else if (i4 == 0) {
          if (disableRecord.displayId == 1) {}
          i5 |= flags;
        } else {
          if (disableRecord.displayId != i4) {}
          i5 |= flags;
        }
      }
    }
    return i5;
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    ArrayList arrayList;
    if (DumpUtils.checkDumpPermission(this.mContext, "StatusBarManagerService", printWriter)) {
      boolean z = false;
      for (String str : strArr) {
        if ("--proto".equals(str)) {
          z = true;
        }
      }
      if (z) {
        if (this.mBar == null) {
          return;
        }
        try {
          TransferPipe transferPipe = new TransferPipe();
          try {
            this.mBar.dumpProto(strArr, transferPipe.getWriteFd());
            transferPipe.go(fileDescriptor);
            transferPipe.close();
            return;
          } finally {
          }
        } catch (Throwable th) {
          Slog.e("StatusBarManagerService", "Error sending command to IStatusBar", th);
          return;
        }
      }
      synchronized (this.mLock) {
        for (int i = 0; i < this.mDisplayUiState.size(); i++) {
          int keyAt = this.mDisplayUiState.keyAt(i);
          UiState uiState = (UiState) this.mDisplayUiState.get(keyAt);
          printWriter.println("  displayId=" + keyAt);
          printWriter.println("    mDisabled1=0x" + Integer.toHexString(uiState.getDisabled1()));
          printWriter.println("    mDisabled2=0x" + Integer.toHexString(uiState.getDisabled2()));
        }
        for (int i2 = 0; i2 < this.mDisplayUiStateDex.size(); i2++) {
          int keyAt2 = this.mDisplayUiStateDex.keyAt(i2);
          UiState uiState2 = (UiState) this.mDisplayUiStateDex.get(keyAt2);
          printWriter.println("  DexdisplayId=" + keyAt2);
          printWriter.println(
              "    mDexDisabled1=0x" + Integer.toHexString(uiState2.getDisabled1()));
          printWriter.println(
              "    mDexDisabled2=0x" + Integer.toHexString(uiState2.getDisabled2()));
        }
        int size = this.mDisableRecords.size();
        printWriter.println("  mDisableRecords.size=" + size);
        for (int i3 = 0; i3 < size; i3++) {
          printWriter.println("    [" + i3 + "] " + ((DisableRecord) this.mDisableRecords.get(i3)));
        }
        printWriter.println("  mCurrentUserId=" + this.mCurrentUserId);
        printWriter.println("  mIcons=");
        for (String str2 : this.mIcons.keySet()) {
          printWriter.println("    ");
          printWriter.print(str2);
          printWriter.print(" -> ");
          StatusBarIcon statusBarIcon = (StatusBarIcon) this.mIcons.get(str2);
          printWriter.print(statusBarIcon);
          if (!TextUtils.isEmpty(statusBarIcon.contentDescription)) {
            printWriter.print(" \"");
            printWriter.print(statusBarIcon.contentDescription);
            printWriter.print("\"");
          }
          printWriter.println();
        }
        synchronized (this.mCurrentRequestAddTilePackages) {
          arrayList = new ArrayList(this.mCurrentRequestAddTilePackages.keySet());
        }
        printWriter.println("  mCurrentRequestAddTilePackages=[");
        int size2 = arrayList.size();
        for (int i4 = 0; i4 < size2; i4++) {
          printWriter.println("    " + ((String) arrayList.get(i4)) + ",");
        }
        printWriter.println("  ]");
        this.mTileRequestTracker.dump(
            fileDescriptor, new IndentingPrintWriter(printWriter, "  ").increaseIndent(), strArr);
      }
      printWriter.println("  mSysUiSafeMode=" + this.mSysUiSafeMode);
      synchronized (this.mDisableHistoryList) {
        int size3 = this.mDisableHistoryList.size();
        printWriter.println("  mDisableHistoryList.size=" + size3);
        for (int i5 = 0; i5 < size3; i5++) {
          printWriter.println("    [" + i5 + "] " + ((String) this.mDisableHistoryList.get(i5)));
        }
      }
      synchronized (this.mStatusBarHistoryList) {
        int size4 = this.mStatusBarHistoryList.size();
        printWriter.println("  mStatusBarHistoryList.size=" + size4);
        for (int i6 = 0; i6 < size4; i6++) {
          printWriter.println("    [" + i6 + "] " + ((String) this.mStatusBarHistoryList.get(i6)));
        }
      }
      if (this.mIsSecCustomTileMap.isEmpty()) {
        return;
      }
      printWriter.println(" mIsSecCustomTileMap=" + this.mIsSecCustomTileMap);
    }
  }

  public final void makeDisableHistory(
      int i, int i2, IBinder iBinder, String str, String str2, int i3, int i4, int i5) {
    StringBuilder sb = new StringBuilder();
    if (i3 == 1) {
      if (i2 == 0) {
        sb.append("CLEAR");
      } else {
        sb.append((65536 & i2) != 0 ? "EXPAND " : "");
        sb.append((131072 & i2) != 0 ? "ICONS " : "");
        sb.append((262144 & i2) != 0 ? "ALERTS " : "");
        sb.append((524288 & i2) != 0 ? "TICKER " : "");
        sb.append((1048576 & i2) != 0 ? "SYSTEM_INFO " : "");
        sb.append((4194304 & i2) != 0 ? "BACK " : "");
        sb.append((2097152 & i2) != 0 ? "HOME " : "");
        sb.append((16777216 & i2) != 0 ? "RECENT " : "");
        sb.append((8388608 & i2) != 0 ? "CLOCK " : "");
        sb.append((33554432 & i2) != 0 ? "SEARCH " : "");
        sb.append((268435456 & i2) != 0 ? "EXPAND_ON_KEYGUARD " : "");
        sb.append((i2 & 536870912) != 0 ? "EXPAND_AND_TOUCH " : "");
      }
    } else {
      if (i3 != 2) {
        return;
      }
      if (i2 == 0) {
        sb.append("CLEAR2");
      } else {
        sb.append((i2 & 1) != 0 ? "QS " : "");
        sb.append((i2 & 2) != 0 ? "SYSTEM_ICONS " : "");
        sb.append((i2 & 4) != 0 ? "SHADE " : "");
        sb.append((i2 & 8) != 0 ? "GA " : "");
        sb.append((i2 & 16) != 0 ? "ROTATE " : "");
      }
    }
    this.mDisableHistoryList.add(
        makeTime()
            + " barType="
            + i4
            + " displayId="
            + i5
            + " pkg="
            + str
            + " tag="
            + str2
            + " userId="
            + i
            + " what="
            + sb.toString()
            + " token="
            + iBinder);
    while (this.mDisableHistoryList.size() > 100) {
      this.mDisableHistoryList.remove(0);
    }
  }

  public final String makeTime() {
    return FORMAT.format(new Date(System.currentTimeMillis()));
  }

  public final void makeStatusBarHistory(int i, String str) {
    String str2;
    String callers = Debug.getCallers(3);
    String str3 = "collapsePanels";
    if (i == 0) {
      if (!callers.contains("collapsePanels")) {
        return;
      } else {
        str2 = "STATUS_BAR";
      }
    } else {
      if (i != 1) {
        return;
      }
      String str4 = "expandSettingsPanel";
      if (!callers.contains("expandSettingsPanel")) {
        str4 = "togglePanel";
        if (!callers.contains("togglePanel")) {
          str4 = "expandNotificationsPanel";
          if (!callers.contains("expandNotificationsPanel")) {
            if (!callers.contains("collapsePanels")) {
              return;
            }
            str2 = "EXPAND_STATUS_BAR";
          }
        }
      }
      str3 = str4;
      str2 = "EXPAND_STATUS_BAR";
    }
    String nameForUid = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
    this.mStatusBarHistoryList.add(
        makeTime()
            + " category = "
            + str2
            + ", API = "
            + str3
            + ", pkg = "
            + nameForUid
            + ", tag = "
            + str);
    while (this.mStatusBarHistoryList.size() > 100) {
      this.mStatusBarHistoryList.remove(0);
    }
  }

  public static final Context getUiContext() {
    return ActivityThread.currentActivityThread().getSystemUiContext();
  }

  public void setBlueLightFilter(boolean z, int i) {
    enforceStatusBarService();
    Iterator it = this.mBarMap.entrySet().iterator();
    while (it.hasNext()) {
      IStatusBar iStatusBar = (IStatusBar) ((Map.Entry) it.next()).getValue();
      if (iStatusBar != null) {
        try {
          iStatusBar.setBlueLightFilter(z, i);
        } catch (RemoteException unused) {
        }
      }
    }
  }

  public boolean isSysUiSafeModeEnabled() {
    return this.mSysUiSafeMode || DEBUG_SAFEMODE;
  }

  public void shutdownByBixby() {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              StatusBarManagerService.lambda$shutdownByBixby$7();
            }
          });
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ void lambda$shutdownByBixby$7() {
    ShutdownThread.shutdown(getUiContext(), "bixbyrequest", false);
  }

  public void rebootByBixby(final boolean z) {
    enforceStatusBarService();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              StatusBarManagerService.lambda$rebootByBixby$8(z);
            }
          });
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ void lambda$rebootByBixby$8(boolean z) {
    if (z) {
      ShutdownThread.rebootSafeMode(getUiContext(), false);
    } else {
      ShutdownThread.reboot(getUiContext(), "bixbyrequest", false);
    }
  }

  public void registerStatusBarForCarLife(IStatusBarCarLife iStatusBarCarLife) {
    if (CoreRune.CARLIFE_NAVBAR) {
      enforceStatusBarService();
      Slog.i("StatusBarManagerService", "registerStatusBarForCarLife bar=" + iStatusBarCarLife);
      this.mCarLifeBar = iStatusBarCarLife;
      if (this.mCarLifeBar != null) {
        try {
          this.mCarLifeBar
              .asBinder()
              .linkToDeath(
                  new IBinder
                      .DeathRecipient() { // from class:
                                          // com.android.server.statusbar.StatusBarManagerService.7
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                      StatusBarManagerService.this.mCarLifeBar = null;
                    }
                  },
                  0);
        } catch (RemoteException e) {
          Slog.e("StatusBarManagerService", "Unable to register Death Recipient for status bar", e);
        }
      }
    }
  }
}
