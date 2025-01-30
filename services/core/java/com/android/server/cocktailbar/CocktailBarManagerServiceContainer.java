package com.android.server.cocktailbar;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.LauncherApps;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.RemoteViews;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.LocalServices;
import com.android.server.cocktailbar.constant.Constants;
import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.policy.cocktail.CocktailPolicyManager;
import com.android.server.cocktailbar.policy.state.CocktailBarStatePolicyController;
import com.android.server.cocktailbar.policy.systemui.SystemUiVisibilityPolicyController;
import com.android.server.cocktailbar.settings.CocktailOrderManager;
import com.android.server.cocktailbar.utils.CocktailBarConfig;
import com.android.server.cocktailbar.utils.CocktailBarHistory;
import com.android.server.cocktailbar.utils.CocktailBarUtils;
import com.android.server.cocktailbar.watcher.CocktailBarUsageStateWatcher;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.cocktailbar.Cocktail;
import com.samsung.android.cocktailbar.CocktailBarStateInfo;
import com.samsung.android.cocktailbar.CocktailInfo;
import com.samsung.android.cocktailbar.ICocktailBarService;
import com.samsung.android.cocktailbar.ICocktailHost;
import com.samsung.android.knox.SemPersonaManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes.dex */
public class CocktailBarManagerServiceContainer extends ICocktailBarService.Stub
    implements CocktailBarManagerServiceListener,
        CocktailPolicyManager.OnCocktailBarPolicyListener,
        CocktailBarUsageStateWatcher.OnCocktailBarWatcherListener,
        DevicePolicyManagerInternal.OnCrossProfileWidgetProvidersChangeListener {
  public static final boolean SUPPORT_EDGE_MUM;
  public static final String TAG = CocktailBarManagerServiceContainer.class.getSimpleName();
  public final AppOpsManager mAppOpsManager;
  public Handler mCocktailBarHandler;
  public final SparseArray mCocktailBarServices;
  public final CocktailOrderManager mCocktailOrderManager;
  public final CocktailPolicyManager mCocktailPolicyManager;
  public Context mContext;
  public LauncherApps mLauncherAppsService;
  public final CocktailBarModeManager mModeManager;
  public SemPersonaManager mPersonalManager;
  public final SecurityPolicy mSecurityPolicy;
  public final CocktailBarStatePolicyController mStatePolicyController;
  public int mSystemBarAppearance;
  public final SystemUiVisibilityPolicyController mSystemUiVisibilityPolicyController;
  public WindowManagerInternal.TaskSystemBarsListener mTaskSystemBarsVisibilityListener;
  public final UserManager mUserManager;
  public CocktailBarUsageStateWatcher mWatcher;
  public Intent mIntent = null;
  public HashMap mHost = new HashMap();
  public HashMap mFilterCategory = new HashMap();
  public final SparseIntArray mLoadedUserIds = new SparseIntArray();
  public int mCurrentUserId = -10;
  public BroadcastReceiver mBroadcastReceiver =
      new BroadcastReceiver() { // from class:
                                // com.android.server.cocktailbar.CocktailBarManagerServiceContainer.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          int i = 0;
          if ("android.intent.action.CONFIGURATION_CHANGED".equals(action)) {
            synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
              int size = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
              while (i < size) {
                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
                    (CocktailBarManagerServiceImpl)
                        CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                if (cocktailBarManagerServiceImpl != null) {
                  cocktailBarManagerServiceImpl.onConfigurationChanged();
                }
                i++;
              }
            }
            return;
          }
          if ("android.intent.action.USER_STARTED".equals(action)) {
            CocktailBarManagerServiceContainer.this.onUserStarted(
                intent.getIntExtra("android.intent.extra.user_handle", -10000));
            return;
          }
          if ("android.intent.action.USER_STOPPED".equals(action)) {
            CocktailBarManagerServiceContainer.this.onUserStopped(
                intent.getIntExtra("android.intent.extra.user_handle", -10000));
            return;
          }
          if ("android.intent.action.USER_SWITCHED".equals(action)) {
            CocktailBarManagerServiceContainer.this.onUserSwitched(
                intent.getIntExtra("android.intent.extra.user_handle", -10000));
            return;
          }
          if ("android.intent.action.PACKAGE_DATA_CLEARED".equals(action)) {
            if (intent.getData() != null
                && intent.getData().toString().contains(Constants.COCKTAIL_BAR_PACKAGE_NAME)) {
              CocktailBarManagerServiceContainer.this.mCocktailBarHandler.sendEmptyMessage(3);
              return;
            }
            return;
          }
          Slog.d(CocktailBarManagerServiceContainer.TAG, "onReceive : " + action);
          if (CocktailBarManagerServiceContainer.this.mModeManager.onBroadcastReceived(intent)) {
            return;
          }
          synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
            int sendingUserId = getSendingUserId();
            if (sendingUserId == -1) {
              int size2 = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
              while (i < size2) {
                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 =
                    (CocktailBarManagerServiceImpl)
                        CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                if (cocktailBarManagerServiceImpl2 != null) {
                  cocktailBarManagerServiceImpl2.onBroadcastReceived(intent);
                }
                i++;
              }
            } else {
              CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl3 =
                  (CocktailBarManagerServiceImpl)
                      CocktailBarManagerServiceContainer.this.mCocktailBarServices.get(
                          sendingUserId);
              if (cocktailBarManagerServiceImpl3 != null) {
                cocktailBarManagerServiceImpl3.onBroadcastReceived(intent);
              }
            }
          }
        }
      };
  public final LauncherApps.Callback mLauncherAppsCallback =
      new LauncherApps
          .Callback() { // from class:
                        // com.android.server.cocktailbar.CocktailBarManagerServiceContainer.2
        @Override // android.content.pm.LauncherApps.Callback
        public void onPackagesAvailable(String[] strArr, UserHandle userHandle, boolean z) {}

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackagesUnavailable(String[] strArr, UserHandle userHandle, boolean z) {}

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageRemoved(String str, UserHandle userHandle) {
          synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
            int semGetIdentifier = userHandle.semGetIdentifier();
            if (semGetIdentifier == -1) {
              int size = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
              for (int i = 0; i < size; i++) {
                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
                    (CocktailBarManagerServiceImpl)
                        CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                if (cocktailBarManagerServiceImpl != null) {
                  cocktailBarManagerServiceImpl.onPackageRemoved(str);
                }
              }
            } else {
              CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 =
                  (CocktailBarManagerServiceImpl)
                      CocktailBarManagerServiceContainer.this.mCocktailBarServices.get(
                          semGetIdentifier);
              if (cocktailBarManagerServiceImpl2 != null) {
                cocktailBarManagerServiceImpl2.onPackageRemoved(str);
              }
            }
          }
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageAdded(String str, UserHandle userHandle) {
          synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
            int semGetIdentifier = userHandle.semGetIdentifier();
            if (semGetIdentifier == -1) {
              int size = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
              for (int i = 0; i < size; i++) {
                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
                    (CocktailBarManagerServiceImpl)
                        CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                if (cocktailBarManagerServiceImpl != null) {
                  cocktailBarManagerServiceImpl.onPackageAdded(str);
                }
              }
            } else {
              CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 =
                  (CocktailBarManagerServiceImpl)
                      CocktailBarManagerServiceContainer.this.mCocktailBarServices.get(
                          semGetIdentifier);
              if (cocktailBarManagerServiceImpl2 != null) {
                cocktailBarManagerServiceImpl2.onPackageAdded(str);
              }
            }
          }
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageChanged(String str, UserHandle userHandle) {
          synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
            int semGetIdentifier = userHandle.semGetIdentifier();
            if (semGetIdentifier == -1) {
              int size = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
              for (int i = 0; i < size; i++) {
                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
                    (CocktailBarManagerServiceImpl)
                        CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                if (cocktailBarManagerServiceImpl != null) {
                  cocktailBarManagerServiceImpl.onPackageChanged(str);
                }
              }
            } else {
              CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 =
                  (CocktailBarManagerServiceImpl)
                      CocktailBarManagerServiceContainer.this.mCocktailBarServices.get(
                          semGetIdentifier);
              if (cocktailBarManagerServiceImpl2 != null) {
                cocktailBarManagerServiceImpl2.onPackageChanged(str);
              }
            }
          }
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackagesSuspended(String[] strArr, UserHandle userHandle) {
          onPackagesSuspendChanged(strArr, userHandle, true);
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackagesUnsuspended(String[] strArr, UserHandle userHandle) {
          onPackagesSuspendChanged(strArr, userHandle, false);
        }

        public final void onPackagesSuspendChanged(
            String[] strArr, UserHandle userHandle, boolean z) {
          synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
            int semGetIdentifier = userHandle.semGetIdentifier();
            if (semGetIdentifier == -1) {
              int size = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
              for (int i = 0; i < size; i++) {
                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
                    (CocktailBarManagerServiceImpl)
                        CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                if (cocktailBarManagerServiceImpl != null) {
                  cocktailBarManagerServiceImpl.onPackagesSuspendChanged(strArr, z);
                }
              }
            } else {
              CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 =
                  (CocktailBarManagerServiceImpl)
                      CocktailBarManagerServiceContainer.this.mCocktailBarServices.get(
                          semGetIdentifier);
              if (cocktailBarManagerServiceImpl2 != null) {
                cocktailBarManagerServiceImpl2.onPackagesSuspendChanged(strArr, z);
              }
            }
          }
        }
      };

  public boolean getCocktaiBarWakeUpState() {
    return false;
  }

  public final boolean isMaintenanceMode(int i) {
    return i == 77;
  }

  public void setCocktailBarWakeUpState(boolean z) {}

  public void updateWakeupArea(int i) {}

  public void updateWakeupGesture(int i, boolean z) {}

  static {
    SUPPORT_EDGE_MUM = Build.VERSION.SEM_PLATFORM_INT >= 140500;
  }

  public CocktailBarManagerServiceContainer(Context context) {
    this.mContext = null;
    this.mContext = context;
    HandlerThread handlerThread = new HandlerThread(TAG);
    handlerThread.start();
    this.mCocktailBarHandler = new CocktailBarHandler(handlerThread.getLooper());
    this.mModeManager =
        new CocktailBarModeManager(
            this.mContext, this, this.mBroadcastReceiver, this.mCocktailBarHandler);
    this.mUserManager = (UserManager) this.mContext.getSystemService("user");
    this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService("appops");
    this.mSecurityPolicy = new SecurityPolicy();
    this.mStatePolicyController = CocktailBarStatePolicyController.getInstance(this.mContext);
    this.mSystemUiVisibilityPolicyController =
        SystemUiVisibilityPolicyController.getInstance(this.mContext);
    this.mCocktailPolicyManager = new CocktailPolicyManager(context, this);
    this.mCocktailBarServices = new SparseArray(5);
    registerOnCrossProfileProvidersChangedListener();
    this.mCocktailOrderManager = new CocktailOrderManager(this.mContext);
  }

  public void systemRunning(boolean z) {
    CocktailBarHistory.getInstance().recordServiceProcess("systemRunning");
    this.mCurrentUserId = this.mContext.getUserId();
    bootCompleted();
  }

  public final void bootCompleted() {
    CocktailBarHistory.getInstance().recordServiceProcess("bootCompleted");
    createCocktailBarManagerServiceImpl(SUPPORT_EDGE_MUM ? this.mContext.getUserId() : 0);
    registerBroadcastReceiver();
    registerTaskSystemBarsListener();
  }

  public void systemServicesReady() {
    CocktailBarHistory.getInstance().recordServiceProcess("systemServicesReady");
    if (SUPPORT_EDGE_MUM) {
      copySettingValuesForMum();
    }
    try {
      LauncherApps launcherApps = (LauncherApps) this.mContext.getSystemService("launcherapps");
      this.mLauncherAppsService = launcherApps;
      launcherApps.registerCallback(this.mLauncherAppsCallback, null);
    } catch (RuntimeException e) {
      Slog.e(TAG, "systemServicesReady : " + e.getMessage());
      CocktailBarHistory.getInstance().recordServiceProcess(e.toString());
    }
  }

  public final void startCocktailBarServiceWithCurrentUser() {
    CocktailBarHistory.getInstance().recordServiceProcess("startCocktailBarServiceWithCurrentUser");
    Intent intent = new Intent("android.intent.action.MAIN");
    this.mIntent = intent;
    intent.setClassName(Constants.COCKTAIL_BAR_PACKAGE_NAME, Constants.COCKTAIL_BAR_CLASS_NAME);
    try {
      this.mContext.startServiceAsUser(
          this.mIntent, new UserHandle(ActivityManager.getCurrentUser()));
    } catch (Exception e) {
      e.printStackTrace();
      CocktailBarHistory.getInstance().recordServiceProcess(e.toString());
    }
  }

  public final void registerBroadcastReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
    this.mContext.registerReceiverAsUser(
        this.mBroadcastReceiver, UserHandle.ALL, intentFilter, null, null);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
    intentFilter2.addDataScheme("package");
    intentFilter2.addDataSchemeSpecificPart(Constants.COCKTAIL_BAR_PACKAGE_NAME, 0);
    this.mContext.registerReceiverAsUser(
        this.mBroadcastReceiver, UserHandle.ALL, intentFilter2, null, null);
    IntentFilter intentFilter3 = new IntentFilter();
    intentFilter3.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
    intentFilter3.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
    this.mContext.registerReceiverAsUser(
        this.mBroadcastReceiver, UserHandle.ALL, intentFilter3, null, null);
    IntentFilter intentFilter4 = new IntentFilter();
    intentFilter4.addAction("android.intent.action.USER_STARTED");
    intentFilter4.addAction("android.intent.action.USER_STOPPED");
    intentFilter4.addAction("android.intent.action.USER_SWITCHED");
    this.mContext.registerReceiverAsUser(
        this.mBroadcastReceiver, UserHandle.ALL, intentFilter4, null, null);
  }

  public final void ensureGroupStateLoaded(int i) {
    if (isNotEdgeRunnableId(i)) {
      return;
    }
    synchronized (this.mCocktailBarServices) {
      int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(i);
      int length = enabledGroupProfileIds.length;
      int i2 = 0;
      for (int i3 = 0; i3 < length; i3++) {
        if (this.mLoadedUserIds.indexOfKey(enabledGroupProfileIds[i3]) >= 0) {
          enabledGroupProfileIds[i3] = -1;
        } else {
          i2++;
        }
      }
      if (i2 <= 0) {
        return;
      }
      for (int i4 : enabledGroupProfileIds) {
        if (i4 != -1) {
          createCocktailBarManagerServiceImplLocked(i4);
        }
      }
    }
  }

  public final void createCocktailBarManagerServiceImpl(int i) {
    synchronized (this.mCocktailBarServices) {
      if (((CocktailBarManagerServiceImpl) this.mCocktailBarServices.get(i)) == null) {
        createCocktailBarManagerServiceImplLocked(i);
      }
    }
  }

  public final void createCocktailBarManagerServiceImplLocked(int i) {
    if (isNotEdgeRunnableId(i)) {
      return;
    }
    CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
        new CocktailBarManagerServiceImpl(
            this.mContext,
            this.mCocktailBarHandler,
            this.mModeManager,
            this.mCocktailPolicyManager,
            i);
    HashMap hashMap = this.mHost;
    if (hashMap != null && !hashMap.isEmpty()) {
      cocktailBarManagerServiceImpl.setCocktailHostCallbacks(
          this.mHost, this.mFilterCategory, false);
    }
    this.mCocktailBarServices.append(i, cocktailBarManagerServiceImpl);
    this.mLoadedUserIds.put(i, i);
  }

  public final void registerOnCrossProfileProvidersChangedListener() {
    DevicePolicyManagerInternal devicePolicyManagerInternal =
        (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
    if (devicePolicyManagerInternal != null) {
      devicePolicyManagerInternal.addOnCrossProfileWidgetProvidersChangeListener(this);
    }
  }

  public final void checkPermission(String str) {
    if (this.mContext.checkCallingOrSelfPermission(str) == 0) {
      return;
    }
    throw new SecurityException(
        "Access denied to process: " + Binder.getCallingPid() + ", must have permission " + str);
  }

  public final CocktailBarManagerServiceImpl getImplForUser(int i) {
    CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl;
    boolean z;
    synchronized (this.mCocktailBarServices) {
      cocktailBarManagerServiceImpl =
          (CocktailBarManagerServiceImpl) this.mCocktailBarServices.get(i);
      z = false;
      if (cocktailBarManagerServiceImpl == null) {
        Slog.i(TAG, "Unable to find CocktailBarManagerService for user " + i + ", adding");
        cocktailBarManagerServiceImpl =
            new CocktailBarManagerServiceImpl(
                this.mContext,
                this.mCocktailBarHandler,
                this.mModeManager,
                this.mCocktailPolicyManager,
                i);
        this.mCocktailBarServices.append(i, cocktailBarManagerServiceImpl);
        HashMap hashMap = this.mHost;
        if (hashMap != null && !hashMap.isEmpty()) {
          cocktailBarManagerServiceImpl.setCocktailHostCallbacks(
              this.mHost, this.mFilterCategory, false);
        }
        z = true;
      }
    }
    if (z && this.mHost != null) {
      cocktailBarManagerServiceImpl.initialize();
    }
    return cocktailBarManagerServiceImpl;
  }

  public void setCocktailHostCallbacks(ICocktailHost iCocktailHost, String str, int i) {
    int callingUserId = UserHandle.getCallingUserId();
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(callingUserId)) {
      return;
    }
    checkPermission("com.samsung.android.app.cocktailbarservice.permission.ACCESS_PANEL");
    CocktailBarHistory.getInstance()
        .recordServiceProcess(
            "setCocktailHostCallbacks packageName - " + str + ", category - " + i);
    this.mHost.put(str, iCocktailHost);
    this.mFilterCategory.put(str, Integer.valueOf(i));
    ensureGroupStateLoaded(callingUserId);
    for (int i2 : this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId)) {
      getImplForUser(i2).setCocktailHostCallbacks(this.mHost, this.mFilterCategory, true);
    }
    if (CocktailBarUtils.isExistKioskContainers(this.mContext)) {
      int callingUserId2 = UserHandle.getCallingUserId();
      int i3 = this.mCurrentUserId;
      if (callingUserId2 != i3) {
        getImplForUser(i3).setCocktailHostCallbacks(this.mHost, this.mFilterCategory, true);
      }
    }
  }

  public void startListening(ICocktailHost iCocktailHost, String str, int i) {
    int callingUserId = UserHandle.getCallingUserId();
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(callingUserId)) {
      return;
    }
    checkPermission("com.samsung.android.app.cocktailbarservice.permission.ACCESS_PANEL");
    CocktailBarHistory.getInstance()
        .recordServiceProcess("startListening packageName - " + str + ", category - " + i);
    String str2 = TAG;
    StringBuilder sb = new StringBuilder();
    sb.append("startListening() ");
    sb.append(i);
    Slog.i(str2, sb.toString());
    this.mHost.put(str, iCocktailHost);
    this.mFilterCategory.put(str, Integer.valueOf(i));
    ensureGroupStateLoaded(callingUserId);
    for (int i2 : this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId)) {
      getImplForUser(i2).startListening(iCocktailHost, str, i);
    }
  }

  public void stopListening(String str) {
    int callingUserId = UserHandle.getCallingUserId();
    if (isNotEdgeRunnableId(callingUserId)) {
      return;
    }
    CocktailBarHistory.getInstance().recordServiceProcess("stopListening callingPackage - " + str);
    Slog.i(TAG, "stopListening() " + callingUserId);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    ensureGroupStateLoaded(callingUserId);
    HashMap hashMap = this.mHost;
    if (hashMap == null || !hashMap.containsKey(str)) {
      return;
    }
    this.mHost.remove(str);
    synchronized (this.mCocktailBarServices) {
      int indexOfKey = this.mCocktailBarServices.indexOfKey(callingUserId);
      if (indexOfKey >= 0) {
        ((CocktailBarManagerServiceImpl) this.mCocktailBarServices.valueAt(indexOfKey))
            .deleteHost(str);
      }
    }
    CocktailBarStatePolicyController.getInstance(this.mContext).clearCocktailWindowType(str);
  }

  public boolean requestToUpdateCocktail(int i) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return false;
    }
    CocktailBarHistory.getInstance()
        .recordCocktailBarManagerCommand("requestToUpdateCocktail id:" + i);
    return getImplForUser(UserHandle.getCallingUserId()).requestToUpdateCocktail(i);
  }

  public boolean requestToDisableCocktail(int i) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return false;
    }
    CocktailBarHistory.getInstance()
        .recordCocktailBarManagerCommand("requestToDisableCocktail id:" + i);
    return getImplForUser(UserHandle.getCallingUserId()).requestToDisableCocktail(i);
  }

  public boolean requestToUpdateCocktailByCategory(int i) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return false;
    }
    CocktailBarHistory.getInstance()
        .recordCocktailBarManagerCommand("requestToUpdateCocktailByCategory category:" + i);
    return getImplForUser(UserHandle.getCallingUserId()).requestToUpdateCocktailByCategory(i);
  }

  public boolean requestToDisableCocktailByCategory(int i) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return false;
    }
    CocktailBarHistory.getInstance()
        .recordCocktailBarManagerCommand("requestToDisableCocktailByCategory category:" + i);
    return getImplForUser(UserHandle.getCallingUserId()).requestToDisableCocktailByCategory(i);
  }

  public void updateCocktail(String str, CocktailInfo cocktailInfo, int i) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return;
    }
    if (cocktailInfo == null) {
      Slog.e(TAG, "updateCocktail : cocktailInfo is null");
      return;
    }
    CocktailBarHistory.getInstance()
        .recordCocktailBarManagerCommand("updateCocktail callingPackage:" + str + ", id:" + i);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    getImplForUser(UserHandle.getCallingUserId()).updateCocktail(str, i, cocktailInfo);
  }

  public void partiallyUpdateCocktail(String str, RemoteViews remoteViews, int i) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return;
    }
    if (remoteViews == null) {
      Slog.e(TAG, "partiallyUpdateCocktail : contentView is null");
      return;
    }
    CocktailBarHistory.getInstance()
        .recordCocktailBarManagerCommand(
            "partiallyUpdateCocktail callingPackage:" + str + ", id:" + i);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    getImplForUser(UserHandle.getCallingUserId()).partiallyUpdateCocktail(str, i, remoteViews);
  }

  public void partiallyUpdateHelpView(String str, RemoteViews remoteViews, int i) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return;
    }
    if (remoteViews == null) {
      Slog.e(TAG, "partiallyUpdateHelpView : helpView is null");
      return;
    }
    CocktailBarHistory.getInstance()
        .recordCocktailBarManagerCommand(
            "partiallyUpdateHelpView callingPackage:" + str + ", id:" + i);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    getImplForUser(UserHandle.getCallingUserId()).partiallyUpdateHelpView(str, i, remoteViews);
  }

  public void showCocktail(String str, int i) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return;
    }
    CocktailBarHistory.getInstance()
        .recordCocktailBarManagerCommand("showCocktail callingPackage:" + str + ", id:" + i);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    checkPermission("com.samsung.android.app.cocktailbarservice.permission.ACCESS_PANEL");
    getImplForUser(UserHandle.getCallingUserId()).showCocktail(str, i);
  }

  public void closeCocktail(String str, int i, int i2) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return;
    }
    CocktailBarHistory.getInstance()
        .recordCocktailBarManagerCommand(
            "closeCocktail callingPackage:" + str + ", id:" + i + ", category:" + i2);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    checkPermission("com.samsung.android.app.cocktailbarservice.permission.ACCESS_PANEL");
    getImplForUser(UserHandle.getCallingUserId()).closeCocktail(str, i, i2);
  }

  public void notifyCocktailViewDataChanged(String str, int i, int i2) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return;
    }
    CocktailBarHistory.getInstance()
        .recordCocktailBarManagerCommand(
            "notifyCocktailViewDataChanged callingPackage:" + str + ", id:" + i + ", viewId:" + i2);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    getImplForUser(UserHandle.getCallingUserId()).notifyCocktailViewDataChanged(str, i, i2);
  }

  public void setOnPullPendingIntent(String str, int i, int i2, PendingIntent pendingIntent) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return;
    }
    CocktailBarHistory.getInstance()
        .recordCocktailBarManagerCommand(
            "setOnPullPendingIntent callingPackage:" + str + ", id:" + i + ", viewId:" + i2);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    getImplForUser(UserHandle.getCallingUserId()).setOnPullPendingIntent(str, i, i2, pendingIntent);
  }

  public void setEnabledCocktailIds(int[] iArr) {
    int callingUserId = UserHandle.getCallingUserId();
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(callingUserId)) {
      return;
    }
    checkPermission("com.samsung.android.app.cocktailbarservice.permission.ACCESS_PANEL");
    ensureGroupStateLoaded(callingUserId);
    int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId);
    for (int i : enabledGroupProfileIds) {
      getImplForUser(i).setEnabledCocktailIds(iArr);
    }
    int length = iArr.length;
    ArrayList arrayList = new ArrayList();
    for (int i2 = 0; i2 < length; i2++) {
      for (int i3 = 0; i3 < enabledGroupProfileIds.length; i3++) {
        Cocktail cocktail = getImplForUser(enabledGroupProfileIds[i3]).getCocktail(iArr[i2]);
        if (cocktail != null) {
          arrayList.add(
              new CocktailOrderManager.CocktailOrderInfo(cocktail, enabledGroupProfileIds[i3], i2));
        }
      }
    }
    this.mCocktailOrderManager.setOrderedList(arrayList);
  }

  public int[] getEnabledCocktailIds() {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return new int[0];
    }
    int callingUserId = UserHandle.getCallingUserId();
    ensureGroupStateLoaded(callingUserId);
    int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId);
    ArrayList arrayList = new ArrayList();
    SparseArray sparseArray = new SparseArray();
    for (int i = 0; i < enabledGroupProfileIds.length; i++) {
      ArrayList enabledCocktailIds =
          getImplForUser(enabledGroupProfileIds[i]).getEnabledCocktailIds();
      arrayList.addAll(enabledCocktailIds);
      int size = enabledCocktailIds.size();
      for (int i2 = 0; i2 < size; i2++) {
        ComponentName componentName =
            getImplForUser(enabledGroupProfileIds[i]).getComponentName((Integer) arrayList.get(i2));
        if (componentName != null) {
          sparseArray.put(
              ((Integer) enabledCocktailIds.get(i2)).intValue(),
              componentName.getClassName() + "_" + enabledGroupProfileIds[i]);
        }
      }
    }
    return this.mCocktailOrderManager.getMatchedSortIds(arrayList, sparseArray);
  }

  public Cocktail getCocktail(int i) {
    int callingUserId = UserHandle.getCallingUserId();
    if (enforceCocktailBarService() && !isNotEdgeRunnableId(callingUserId)) {
      ensureGroupStateLoaded(callingUserId);
      for (int i2 : this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId)) {
        Cocktail cocktail = getImplForUser(i2).getCocktail(i);
        if (cocktail != null) {
          return cocktail;
        }
      }
    }
    return null;
  }

  public int[] getAllCocktailIds() {
    int callingUserId = UserHandle.getCallingUserId();
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(callingUserId)) {
      return null;
    }
    ensureGroupStateLoaded(callingUserId);
    int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId);
    ArrayList arrayList = new ArrayList();
    for (int i : enabledGroupProfileIds) {
      getImplForUser(i).getAllCocktailIds(arrayList);
    }
    int size = arrayList.size();
    int[] iArr = new int[size];
    for (int i2 = 0; i2 < size; i2++) {
      iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
    }
    return iArr;
  }

  public void disableCocktail(String str, ComponentName componentName) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return;
    }
    this.mSecurityPolicy.enforceCallFromPackage(str);
    getImplForUser(UserHandle.getCallingUserId()).disableCocktail(componentName);
  }

  public int getCocktailId(String str, ComponentName componentName) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return 0;
    }
    this.mSecurityPolicy.enforceCallFromPackage(str);
    return getImplForUser(UserHandle.getCallingUserId()).getCocktailId(componentName);
  }

  public int[] getCocktailIds(String str, ComponentName componentName) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return new int[0];
    }
    this.mSecurityPolicy.enforceCallFromPackage(str);
    return getImplForUser(UserHandle.getCallingUserId()).getCocktailIds(componentName);
  }

  public boolean isEnabledCocktail(String str, ComponentName componentName) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return false;
    }
    this.mSecurityPolicy.enforceCallFromPackage(str);
    return getImplForUser(UserHandle.getCallingUserId()).isEnabledCocktail(str, componentName);
  }

  public boolean isCocktailEnabled(String str, ComponentName componentName) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return false;
    }
    this.mSecurityPolicy.enforceCallFromPackage(str);
    return getImplForUser(UserHandle.getCallingUserId()).isCocktailEnabled(str, componentName);
  }

  public boolean isBoundCocktailPackage(String str, int i) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(i)) {
      return false;
    }
    if (Binder.getCallingUid() != 1000) {
      throw new SecurityException("Only the system process can call this");
    }
    return getImplForUser(i).isBoundCocktailPackage(str);
  }

  public void notifyKeyguardState(boolean z) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return;
    }
    getImplForUser(this.mCurrentUserId).notifyKeyguardState(z);
  }

  public void notifyCocktailVisibiltyChanged(int i, int i2) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return;
    }
    if (getUserIdFromCocktailId(i) != -10000) {
      getImplForUser(getUserIdFromCocktailId(i)).notifyCocktailVisibiltyChanged(i, i2);
      return;
    }
    Slog.w(TAG, "notifyCocktailVisibiltyChanged: invalid user id " + i);
  }

  public final int getUserIdFromCocktailId(int i) {
    int i2 = i >> 16;
    if (isValidCocktailBarManagerServiceImpl(i2)) {
      return i2;
    }
    return -10000;
  }

  public final boolean isValidCocktailBarManagerServiceImpl(int i) {
    synchronized (this.mCocktailBarServices) {
      return ((CocktailBarManagerServiceImpl) this.mCocktailBarServices.get(i)) != null;
    }
  }

  public boolean bindRemoteViewsService(
      String str,
      int i,
      Intent intent,
      IApplicationThread iApplicationThread,
      IBinder iBinder,
      IServiceConnection iServiceConnection,
      int i2) {
    if (this.mContext.checkCallingOrSelfPermission(
            "com.samsung.android.app.cocktailbarservice.permission.ACCESS_PANEL")
        != 0) {
      Slog.e(
          TAG,
          "bindRemoteViewsService : Permission Denial, pid="
              + Binder.getCallingPid()
              + ", uid="
              + Binder.getCallingUid());
      return false;
    }
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return false;
    }
    return getImplForUser(UserHandle.getCallingUserId())
        .bindRemoteViewsService(
            str, i, intent, iApplicationThread, iBinder, iServiceConnection, i2);
  }

  public void unbindRemoteViewsService(String str, int i, Intent intent) {
    if (enforceCocktailBarService()) {
      isNotEdgeRunnableId(UserHandle.getCallingUserId());
    }
  }

  public void updateCocktailBarPosition(int i) {
    this.mStatePolicyController.updatePosition(i);
  }

  public void updateCocktailBarWindowType(String str, int i) {
    this.mSecurityPolicy.enforceCallFromPackage(str);
    this.mStatePolicyController.updateWindowType(str, i);
  }

  public int getCocktailBarVisibility() {
    return this.mStatePolicyController.getCocktailBarStateInfo().visibility;
  }

  public CocktailBarStateInfo getCocktailBarStateInfo() {
    return this.mStatePolicyController.getCocktailBarStateInfo();
  }

  public int getWindowType() {
    return this.mStatePolicyController.getWindowType();
  }

  public void sendExtraDataToCocktailBar(Bundle bundle) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
      return;
    }
    getImplForUser(UserHandle.getCallingUserId()).sendExtraDataToCocktailBar(bundle);
  }

  public void removeCocktailUIService() {
    if (Binder.getCallingUid() == 1000) {
      this.mContext.stopService(this.mIntent);
    }
  }

  public void activateCocktailBar() {
    if (Binder.getCallingUid() == 1000) {
      this.mStatePolicyController.activateCocktailBar();
      return;
    }
    Slog.w(
        TAG,
        "activateCocktailBar : Calling uid is not system uid (" + Binder.getCallingUid() + ")");
  }

  public void deactivateCocktailBar() {
    if (Binder.getCallingUid() == 1000) {
      this.mStatePolicyController.deactivateCocktailBar();
      return;
    }
    Slog.w(
        TAG,
        "deactivateCocktailBar : Calling uid is not system uid (" + Binder.getCallingUid() + ")");
  }

  public void updateCocktailBarVisibility(int i) {
    this.mStatePolicyController.updateVisibility(i);
  }

  public void registerCocktailBarStateListenerCallback(
      IBinder iBinder, ComponentName componentName) {
    this.mStatePolicyController.registerCocktailBarStateListenerCallback(iBinder, componentName);
  }

  public void unregisterCocktailBarStateListenerCallback(IBinder iBinder) {
    this.mStatePolicyController.unregisterCocktailBarStateListenerCallback(iBinder);
  }

  public void registerSystemUiVisibilityListenerCallback(
      IBinder iBinder, ComponentName componentName) {
    this.mSystemUiVisibilityPolicyController.registerSystemUiVisibilityListenerCallback(
        iBinder, componentName);
  }

  public void unregisterSystemUiVisibilityListenerCallback(IBinder iBinder) {
    this.mSystemUiVisibilityPolicyController.unregisterSystemUiVisibilityListenerCallback(iBinder);
  }

  public void topAppWindowChanged(int i, boolean z, boolean z2) {
    this.mSystemUiVisibilityPolicyController.topAppWindowChanged(i, z, z2);
  }

  @Override // com.android.server.cocktailbar.CocktailBarManagerServiceListener
  public void onSetMode(int i, int i2, String str, int i3) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(i)) {
      return;
    }
    ensureGroupStateLoaded(i);
    for (int i4 : this.mSecurityPolicy.getEnabledGroupProfileIds(i)) {
      getImplForUser(i4).setMode(i2, str, i3);
    }
  }

  @Override // com.android.server.cocktailbar.CocktailBarManagerServiceListener
  public void onResetMode(int i, int i2, String str) {
    this.mModeManager.setMode(i, 1);
  }

  @Override // com.android.server.cocktailbar.CocktailBarManagerServiceListener
  public void onUnsetMode(int i, int i2, String str) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(i)) {
      return;
    }
    ensureGroupStateLoaded(i);
    for (int i3 : this.mSecurityPolicy.getEnabledGroupProfileIds(i)) {
      getImplForUser(i3).unsetMode(i2, str);
    }
  }

  @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicyManager.OnCocktailBarPolicyListener
  public void onUpdateCocktail(int i, int i2) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(i2)) {
      return;
    }
    getImplForUser(i2).updateCocktail(i);
  }

  @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicyManager.OnCocktailBarPolicyListener
  public void onRemoveCocktail(int i, int i2) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(i2)) {
      return;
    }
    getImplForUser(i2).removeCocktail(i);
  }

  @Override // com.android.server.cocktailbar.watcher.CocktailBarUsageStateWatcher.OnCocktailBarWatcherListener
  public void onChangeVisibleEdgeService(boolean z) {
    this.mCocktailBarHandler.sendMessage(Message.obtain(this.mCocktailBarHandler, 1, z ? 1 : 0, 0));
  }

  public final void handleChangeVisibleEdgeService(boolean z) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(this.mCurrentUserId)) {
      return;
    }
    getImplForUser(this.mCurrentUserId).changeVisibleEdgeService(z);
  }

  @Override // com.android.server.cocktailbar.watcher.CocktailBarUsageStateWatcher.OnCocktailBarWatcherListener
  public void onNoteResumeComponent(ComponentName componentName) {
    this.mCocktailBarHandler.sendMessage(
        Message.obtain(this.mCocktailBarHandler, 5, componentName));
  }

  public final void handleNoteResumeComponent(ComponentName componentName) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(this.mCurrentUserId)) {
      return;
    }
    getImplForUser(this.mCurrentUserId).noteResumeComponent(componentName);
  }

  @Override // com.android.server.cocktailbar.watcher.CocktailBarUsageStateWatcher.OnCocktailBarWatcherListener
  public void onNotePauseComponent(ComponentName componentName) {
    this.mCocktailBarHandler.sendMessage(
        Message.obtain(this.mCocktailBarHandler, 6, componentName));
  }

  public final void handleNotePauseComponent(ComponentName componentName) {
    if (!enforceCocktailBarService() || isNotEdgeRunnableId(this.mCurrentUserId)) {
      return;
    }
    getImplForUser(this.mCurrentUserId).notePauseComponent(componentName);
  }

  public final void handleChangedResumePackage(String str) {
    this.mCocktailPolicyManager.changeResumePackage(str, 6);
  }

  public void onCrossProfileWidgetProvidersChanged(int i, List list) {
    String str = TAG;
    Slog.d(str, "onCrossProfileWidgetProvidersChanged : userId = " + i);
    if (list != null) {
      Slog.d(str, "onCrossProfileWidgetProvidersChanged : packages = " + list.toString());
    }
  }

  public final boolean enforceCocktailBarService() {
    synchronized (this.mCocktailBarServices) {
      return this.mCocktailBarServices.size() >= 1;
    }
  }

  public final void onUserStarted(int i) {
    Slog.i(TAG, "onUserStarted : userId = " + i);
    CocktailBarHistory.getInstance().recordServiceProcess("onUserStarted : userId = " + i);
    if (isNotEdgeRunnableId(i)) {
      return;
    }
    ensureGroupStateLoaded(i);
  }

  public final void onUserStopped(int i) {
    Slog.i(TAG, "onUserStopped : userId = " + i);
    CocktailBarHistory.getInstance().recordServiceProcess("onUserStopped : userId = " + i);
    if (enforceCocktailBarService()) {
      synchronized (this.mCocktailBarServices) {
        int indexOfKey = this.mLoadedUserIds.indexOfKey(i);
        if (indexOfKey >= 0) {
          this.mLoadedUserIds.removeAt(indexOfKey);
        }
        int indexOfKey2 = this.mCocktailBarServices.indexOfKey(i);
        if (indexOfKey2 >= 0) {
          ((CocktailBarManagerServiceImpl) this.mCocktailBarServices.valueAt(indexOfKey2))
              .systemDestroy();
          this.mCocktailBarServices.removeAt(indexOfKey2);
          if (i >= 100 && this.mModeManager.getCurrentModeId() == 2) {
            if (this.mPersonalManager == null) {
              this.mPersonalManager = (SemPersonaManager) this.mContext.getSystemService("persona");
            }
            CocktailBarModeManager cocktailBarModeManager = this.mModeManager;
            cocktailBarModeManager.onSetModeForcely(
                0, cocktailBarModeManager.getCocktailBarMode(1));
          }
        }
      }
    }
  }

  public void onUserSwitched(int i, int i2) {
    Slog.i(TAG, "onUserSwitched : from userId = " + i + ", to userId " + i2);
    CocktailBarHistory.getInstance()
        .recordServiceProcess("onUserSwitched : from userId = " + i + ", to userId " + i2);
    if (enforceCocktailBarService()) {
      isNotEdgeRunnableId(i2);
    }
  }

  public void onUserSwitched(int i) {
    Slog.i(TAG, "onUserSwitched : userId = " + i);
    CocktailBarHistory.getInstance().recordServiceProcess("onUserSwitched : userId = " + i);
    if (enforceCocktailBarService() && !isNotEdgeRunnableId(i) && SUPPORT_EDGE_MUM) {
      onUserSwitchCocktailImpl(i);
      if (this.mCurrentUserId != -10) {
        Intent intent = new Intent("com.samsung.android.cocktailbar.intent.action.USER_SWITCHED");
        intent.addFlags(285212704);
        intent.putExtra("user_id", i);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(this.mCurrentUserId));
      }
      this.mCurrentUserId = i;
    }
  }

  public final void onUserSwitchCocktailImpl(int i) {
    synchronized (this.mCocktailBarServices) {
      int size = this.mCocktailBarServices.size();
      for (int i2 = 0; i2 < size; i2++) {
        CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
            (CocktailBarManagerServiceImpl) this.mCocktailBarServices.valueAt(i2);
        if (cocktailBarManagerServiceImpl != null) {
          cocktailBarManagerServiceImpl.onSwitchUser(i);
        }
      }
    }
  }

  public final class CocktailBarHandler extends Handler {
    public CocktailBarHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      Slog.i(CocktailBarManagerServiceContainer.TAG, "handleMessage: entry what = " + message.what);
      switch (message.what) {
        case 1:
          CocktailBarManagerServiceContainer.this.handleChangeVisibleEdgeService(message.arg1 == 1);
          break;
        case 2:
          CocktailBarManagerServiceContainer.this.handleChangedResumePackage((String) message.obj);
          break;
        case 3:
          CocktailBarManagerServiceContainer.this.startCocktailBarServiceWithCurrentUser();
          break;
        case 4:
          CocktailBarManagerServiceContainer.this.onUnlockUserImpl(message.arg1);
          break;
        case 5:
          CocktailBarManagerServiceContainer.this.handleNoteResumeComponent(
              (ComponentName) message.obj);
          break;
        case 6:
          CocktailBarManagerServiceContainer.this.handleNotePauseComponent(
              (ComponentName) message.obj);
          break;
      }
    }
  }

  public final class SecurityPolicy {
    public SecurityPolicy() {}

    public void enforceCallFromPackage(String str) {
      CocktailBarManagerServiceContainer.this.mAppOpsManager.checkPackage(
          Binder.getCallingUid(), str);
    }

    public int[] getEnabledGroupProfileIds(int i) {
      int groupParent = getGroupParent(i);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        List profiles =
            CocktailBarManagerServiceContainer.this.mUserManager.getProfiles(groupParent);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        int size = profiles.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
          if (((UserInfo) profiles.get(i3)).isEnabled()) {
            i2++;
          }
        }
        int[] iArr = new int[i2];
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
          UserInfo userInfo = (UserInfo) profiles.get(i5);
          if (userInfo.isEnabled()) {
            iArr[i4] = userInfo.getUserHandle().getIdentifier();
            i4++;
          }
        }
        return iArr;
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    }

    public int getProfileParent(int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        UserInfo profileParent =
            CocktailBarManagerServiceContainer.this.mUserManager.getProfileParent(i);
        if (profileParent != null) {
          return profileParent.getUserHandle().getIdentifier();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return -10;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int getGroupParent(int i) {
      int profileParent =
          CocktailBarManagerServiceContainer.this.mSecurityPolicy.getProfileParent(i);
      return profileParent != -10 ? profileParent : i;
    }
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    this.mContext.enforceCallingOrSelfPermission("android.permission.DUMP", TAG);
    synchronized (this.mCocktailBarServices) {
      PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, " ");
      printWriter.println(CocktailBarHistory.getInstance().toString());
      int size = this.mCocktailBarServices.size();
      for (int i = 0; i < size; i++) {
        printWriter.println("User: " + this.mCocktailBarServices.keyAt(i));
        indentingPrintWriter.increaseIndent();
        ((CocktailBarManagerServiceImpl) this.mCocktailBarServices.valueAt(i))
            .dump(fileDescriptor, indentingPrintWriter, strArr);
        indentingPrintWriter.decreaseIndent();
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
      printWriter.println(this.mCocktailOrderManager.dump());
      printWriter.println(this.mStatePolicyController.dump());
      printWriter.println(CocktailBarConfig.getInstance(this.mContext).dump());
      CocktailBarUsageStateWatcher cocktailBarUsageStateWatcher = this.mWatcher;
      if (cocktailBarUsageStateWatcher != null) {
        printWriter.println(cocktailBarUsageStateWatcher.dump());
      }
    }
  }

  public final void onUnlockUserImpl(int i) {
    synchronized (this.mCocktailBarServices) {
      int size = this.mCocktailBarServices.size();
      CocktailBarHistory.getInstance()
          .recordServiceProcess(
              "onUnlockUserImpl CocktailBarServices - " + size + ", userId : " + i);
      String str = TAG;
      StringBuilder sb = new StringBuilder();
      sb.append("onUnlockUser : CocktailBarServices=");
      sb.append(size);
      Slog.d(str, sb.toString());
      for (int i2 = 0; i2 < size; i2++) {
        CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
            (CocktailBarManagerServiceImpl) this.mCocktailBarServices.valueAt(i2);
        if (cocktailBarManagerServiceImpl != null) {
          cocktailBarManagerServiceImpl.onUnlockUser();
        }
      }
    }
    if (this.mWatcher == null) {
      this.mWatcher = new CocktailBarUsageStateWatcher(this.mContext, this);
    }
    bootCompleted();
    sendEdgeAppStartBr(i, 0);
    sendDelayedEdgeAppStartBr(i);
  }

  public final void sendDelayedEdgeAppStartBr(int i) {
    for (int i2 = 1; i2 <= 3; i2++) {
      makeEdgeAppStartHandler(i, i2);
    }
  }

  public final void makeEdgeAppStartHandler(final int i, final int i2) {
    new Handler()
        .postDelayed(
            new Runnable() { // from class:
                             // com.android.server.cocktailbar.CocktailBarManagerServiceContainer$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                CocktailBarManagerServiceContainer.this.lambda$makeEdgeAppStartHandler$0(i, i2);
              }
            },
            i2 * 60000);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$makeEdgeAppStartHandler$0(int i, int i2) {
    synchronized (this.mHost) {
      if (this.mHost.isEmpty()) {
        sendEdgeAppStartBr(i, i2);
      }
    }
  }

  public final void sendEdgeAppStartBr(int i, int i2) {
    CocktailBarHistory.getInstance().recordServiceProcess("sendEdgeAppStartBr " + i2);
    Intent intent = new Intent("com.samsung.android.cocktailbar.intent.action.EDGE_APP_START");
    intent.addFlags(285212704);
    this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
  }

  public void onUnlockUser(int i) {
    if (!this.mUserManager.isUserUnlockingOrUnlocked(i)) {
      Slog.w(TAG, "User " + i + " is no longer unlocked - exiting");
      return;
    }
    if (isNotEdgeRunnableId(i)) {
      return;
    }
    this.mCocktailBarHandler.sendMessage(Message.obtain(this.mCocktailBarHandler, 4, i, 0));
  }

  public final boolean isNotEdgeRunnableId(int i) {
    return SemPersonaManager.isSecureFolderId(i)
        || SemPersonaManager.isKnoxId(i)
        || SemDualAppManager.isDualAppId(i)
        || isMaintenanceMode(i);
  }

  public int getConfigVersion() {
    return CocktailBarConfig.getInstance(this.mContext).getVersion();
  }

  public int getPreferWidth() {
    return CocktailBarConfig.getInstance(this.mContext).getPreferredWidth();
  }

  public String getCategoryFilterStr() {
    return CocktailBarConfig.getInstance(this.mContext).getCategoryFilterStr();
  }

  public void setSystemBarAppearance(int i) {
    this.mSystemBarAppearance = i;
  }

  public int getSystemBarAppearance() {
    return this.mSystemBarAppearance;
  }

  public String getHideEdgeListStr() {
    HashSet packageHideEdgeServiceList =
        CocktailBarConfig.getInstance(this.mContext).getPackageHideEdgeServiceList();
    return packageHideEdgeServiceList != null ? packageHideEdgeServiceList.toString() : "";
  }

  public final void registerTaskSystemBarsListener() {
    if (this.mTaskSystemBarsVisibilityListener == null) {
      this.mTaskSystemBarsVisibilityListener =
          new WindowManagerInternal
              .TaskSystemBarsListener() { // from class:
                                          // com.android.server.cocktailbar.CocktailBarManagerServiceContainer.3
            @Override // com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener
            public void onTransientSystemBarsVisibilityChanged(int i, boolean z, boolean z2) {
              CocktailBarManagerServiceContainer.this.mSystemUiVisibilityPolicyController
                  .transientChanged(z);
            }
          };
    }
    WindowManagerInternal windowManagerInternal =
        (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
    if (windowManagerInternal != null) {
      windowManagerInternal.registerTaskSystemBarsListener(this.mTaskSystemBarsVisibilityListener);
    }
  }

  public final void copySettingValuesForMum() {
    ContentResolver contentResolver = this.mContext.getContentResolver();
    if (Settings.Global.getInt(contentResolver, "edge_setting_copyed_for_mum", 0) != 0) {
      return;
    }
    Settings.Secure.putIntForUser(
        contentResolver,
        "edge_enable",
        Settings.Global.getInt(contentResolver, "edge_enable", -1),
        0);
    Settings.Secure.putFloatForUser(
        contentResolver,
        "edge_handle_size_percent",
        Settings.Global.getFloat(contentResolver, "edge_handle_size_percent", -1.0f),
        0);
    Settings.Secure.putIntForUser(
        contentResolver,
        "edge_handle_transparency",
        Settings.Global.getInt(contentResolver, "edge_handle_transparency", -1),
        0);
    Settings.Global.putInt(contentResolver, "edge_setting_copyed_for_mum", 1);
  }
}
