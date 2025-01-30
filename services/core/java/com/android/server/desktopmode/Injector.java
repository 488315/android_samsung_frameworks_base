package com.android.server.desktopmode;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.app.IUiModeManager;
import android.app.KeyguardManager;
import android.app.StatusBarManager;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.IDisplayManager;
import android.hardware.input.IInputManager;
import android.hardware.input.InputManager;
import android.os.Binder;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.ServiceManager;
import android.telecom.TelecomManager;
import android.util.ArrayMap;
import android.view.IWindowManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.UiModeManagerInternal;
import com.android.server.am.ActivityManagerService;
import com.android.server.input.InputManagerService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.dex.DexManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.os.SemDvfsManager;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class Injector {
  public final Context mContext;
  public final ArrayMap mDependencies = new ArrayMap();
  public final ArrayMap mProviders;

  public interface LazyDependencyCreator {
    Object createDependency();
  }

  public Injector(Context context) {
    ArrayMap arrayMap = new ArrayMap();
    this.mProviders = arrayMap;
    this.mContext = context;
    arrayMap.put(
        Context.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            Context context2;
            context2 = Injector.this.getContext();
            return context2;
          }
        });
    arrayMap.put(
        ServiceThread.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda11
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            ServiceThread serviceThread;
            serviceThread = Injector.this.getServiceThread();
            return serviceThread;
          }
        });
    arrayMap.put(
        BlockerManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda22
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            BlockerManager blockerManager;
            blockerManager = Injector.this.getBlockerManager();
            return blockerManager;
          }
        });
    arrayMap.put(
        BootInitBlocker.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda33
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            BootInitBlocker bootInitBlocker;
            bootInitBlocker = Injector.this.getBootInitBlocker();
            return bootInitBlocker;
          }
        });
    arrayMap.put(
        BleAdvertiserServiceManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda40
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            BleAdvertiserServiceManager bleAdvertiserServiceManager;
            bleAdvertiserServiceManager = Injector.this.getBleAdvertiserServiceManager();
            return bleAdvertiserServiceManager;
          }
        });
    arrayMap.put(
        CoverStateManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda41
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            CoverStateManager coverStateManager;
            coverStateManager = Injector.this.getCoverStateManager();
            return coverStateManager;
          }
        });
    arrayMap.put(
        DesktopModeService.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda42
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            DesktopModeService desktopModeService;
            desktopModeService = Injector.this.getDesktopModeService();
            return desktopModeService;
          }
        });
    arrayMap.put(
        DisplayPortStateManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda43
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            DisplayPortStateManager displayPortStateManager;
            displayPortStateManager = Injector.this.getDisplayPortStateManager();
            return displayPortStateManager;
          }
        });
    arrayMap.put(
        DockManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda44
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            DockManager dockManager;
            dockManager = Injector.this.getDockManager();
            return dockManager;
          }
        });
    arrayMap.put(
        DualModeChanger.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda45
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            DualModeChanger dualModeChanger;
            dualModeChanger = Injector.this.getDualModeChanger();
            return dualModeChanger;
          }
        });
    arrayMap.put(
        EmergencyModeBlocker.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda1
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            EmergencyModeBlocker emergencyModeBlocker;
            emergencyModeBlocker = Injector.this.getEmergencyModeBlocker();
            return emergencyModeBlocker;
          }
        });
    arrayMap.put(
        HardwareManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda2
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            HardwareManager hardwareManager;
            hardwareManager = Injector.this.getHardwareManager();
            return hardwareManager;
          }
        });
    arrayMap.put(
        IStateManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            StateManager iStateManager;
            iStateManager = Injector.this.getIStateManager();
            return iStateManager;
          }
        });
    arrayMap.put(
        MultiResolutionManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda4
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            MultiResolutionManager multiResolutionManager;
            multiResolutionManager = Injector.this.getMultiResolutionManager();
            return multiResolutionManager;
          }
        });
    arrayMap.put(
        PackageStateManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda5
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            PackageStateManager packageStateManager;
            packageStateManager = Injector.this.getPackageStateManager();
            return packageStateManager;
          }
        });
    arrayMap.put(
        SemDesktopModeStateNotifier.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda6
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            SemDesktopModeStateNotifier semDesktopModeStateNotifier;
            semDesktopModeStateNotifier = Injector.this.getSemDesktopModeStateNotifier();
            return semDesktopModeStateNotifier;
          }
        });
    arrayMap.put(
        SettingsHelper.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda7
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            SettingsHelper settingsHelper;
            settingsHelper = Injector.this.getSettingsHelper();
            return settingsHelper;
          }
        });
    arrayMap.put(
        StandaloneModeChanger.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda8
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            StandaloneModeChanger standaloneModeChanger;
            standaloneModeChanger = Injector.this.getStandaloneModeChanger();
            return standaloneModeChanger;
          }
        });
    arrayMap.put(
        TouchpadManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda9
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            TouchpadManager touchpadManager;
            touchpadManager = Injector.this.getTouchpadManager();
            return touchpadManager;
          }
        });
    arrayMap.put(
        UiManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda10
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            UiManager uiManager;
            uiManager = Injector.this.getUiManager();
            return uiManager;
          }
        });
    arrayMap.put(
        WirelessDexManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda12
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            WirelessDexManager wirelessDexManager;
            wirelessDexManager = Injector.this.getWirelessDexManager();
            return wirelessDexManager;
          }
        });
    arrayMap.put(
        McfManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda13
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            McfManager mcfManager;
            mcfManager = Injector.this.getMcfManager();
            return mcfManager;
          }
        });
    arrayMap.put(
        ActivityManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda14
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            ActivityManager activityManager;
            activityManager = Injector.this.getActivityManager();
            return activityManager;
          }
        });
    arrayMap.put(
        ActivityManagerService.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda15
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            ActivityManagerService activityManagerService;
            activityManagerService = Injector.this.getActivityManagerService();
            return activityManagerService;
          }
        });
    arrayMap.put(
        ActivityTaskManagerInternal.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda16
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            ActivityTaskManagerInternal activityTaskManagerInternal;
            activityTaskManagerInternal = Injector.this.getActivityTaskManagerInternal();
            return activityTaskManagerInternal;
          }
        });
    arrayMap.put(
        ActivityTaskManagerService.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda17
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            ActivityTaskManagerService activityTaskManagerService;
            activityTaskManagerService = Injector.this.getActivityTaskManagerService();
            return activityTaskManagerService;
          }
        });
    arrayMap.put(
        ActivityManagerInternal.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda18
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            ActivityManagerInternal activityManagerInternal;
            activityManagerInternal = Injector.this.getActivityManagerInternal();
            return activityManagerInternal;
          }
        });
    arrayMap.put(
        DisplayManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda19
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            DisplayManager displayManager;
            displayManager = Injector.this.getDisplayManager();
            return displayManager;
          }
        });
    arrayMap.put(
        IUiModeManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda20
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            IUiModeManager iUiModeManager;
            iUiModeManager = Injector.this.getIUiModeManager();
            return iUiModeManager;
          }
        });
    arrayMap.put(
        InputManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda21
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            InputManager inputManager;
            inputManager = Injector.this.getInputManager();
            return inputManager;
          }
        });
    arrayMap.put(
        InputManagerService.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda23
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            InputManagerService inputManagerService;
            inputManagerService = Injector.this.getInputManagerService();
            return inputManagerService;
          }
        });
    arrayMap.put(
        IPackageManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda24
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            IPackageManager iPackageManager;
            iPackageManager = Injector.this.getIPackageManager();
            return iPackageManager;
          }
        });
    arrayMap.put(
        IStatusBarService.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda25
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            IStatusBarService iStatusBarService;
            iStatusBarService = Injector.this.getIStatusBarService();
            return iStatusBarService;
          }
        });
    arrayMap.put(
        MultiWindowManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda26
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            MultiWindowManager multiWindowManager;
            multiWindowManager = Injector.this.getMultiWindowManager();
            return multiWindowManager;
          }
        });
    arrayMap.put(
        PowerManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda27
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            PowerManager powerManager;
            powerManager = Injector.this.getPowerManager();
            return powerManager;
          }
        });
    arrayMap.put(
        PowerManagerInternal.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda28
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            PowerManagerInternal powerManagerInternal;
            powerManagerInternal = Injector.this.getPowerManagerInternal();
            return powerManagerInternal;
          }
        });
    arrayMap.put(
        SemDesktopModeManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda29
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            SemDesktopModeManager semDesktopModeManager;
            semDesktopModeManager = Injector.this.getSemDesktopModeManager();
            return semDesktopModeManager;
          }
        });
    arrayMap.put(
        SemDvfsManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda30
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            SemDvfsManager semDvfsManager;
            semDvfsManager = Injector.this.getSemDvfsManager();
            return semDvfsManager;
          }
        });
    arrayMap.put(
        StatusBarManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda31
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            StatusBarManager statusBarManager;
            statusBarManager = Injector.this.getStatusBarManager();
            return statusBarManager;
          }
        });
    arrayMap.put(
        TelecomManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda32
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            TelecomManager telecomManager;
            telecomManager = Injector.this.getTelecomManager();
            return telecomManager;
          }
        });
    arrayMap.put(
        UiModeManagerInternal.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda34
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            UiModeManagerInternal uiModeManagerInternal;
            uiModeManagerInternal = Injector.this.getUiModeManagerInternal();
            return uiModeManagerInternal;
          }
        });
    arrayMap.put(
        WindowManagerInternal.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda35
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            WindowManagerInternal windowManagerInternal;
            windowManagerInternal = Injector.this.getWindowManagerInternal();
            return windowManagerInternal;
          }
        });
    arrayMap.put(
        WindowManagerService.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda36
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            WindowManagerService windowManagerService;
            windowManagerService = Injector.this.getWindowManagerService();
            return windowManagerService;
          }
        });
    arrayMap.put(
        KeyguardManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda37
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            KeyguardManager keyguardManager;
            keyguardManager = Injector.this.getKeyguardManager();
            return keyguardManager;
          }
        });
    arrayMap.put(
        IDisplayManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda38
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            IDisplayManager iDisplayManager;
            iDisplayManager = Injector.this.getIDisplayManager();
            return iDisplayManager;
          }
        });
    arrayMap.put(
        DexManager.class,
        new LazyDependencyCreator() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda39
          @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
          public final Object createDependency() {
            DexManager dexManager;
            dexManager = Injector.this.getDexManager();
            return dexManager;
          }
        });
  }

  public Object get(final Class cls) {
    return Utils.getOrPut(
        this.mDependencies,
        cls,
        new Supplier() { // from class:
          // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda46
          @Override // java.util.function.Supplier
          public final Object get() {
            Object lambda$get$0;
            lambda$get$0 = Injector.this.lambda$get$0(cls);
            return lambda$get$0;
          }
        });
  }

  public void addLocalService(Class cls, Object obj) {
    LocalServices.addService(cls, obj);
  }

  public int binderGetCallingPid() {
    return Binder.getCallingPid();
  }

  public int binderGetCallingUid() {
    return Binder.getCallingUid();
  }

  public long binderClearCallingIdentity() {
    return Binder.clearCallingIdentity();
  }

  public void binderRestoreCallingIdentity(long j) {
    Binder.restoreCallingIdentity(j);
  }

  /* renamed from: createDependency, reason: merged with bridge method [inline-methods] */
  public Object lambda$get$0(Class cls) {
    LazyDependencyCreator lazyDependencyCreator = (LazyDependencyCreator) this.mProviders.get(cls);
    if (lazyDependencyCreator == null) {
      throw new IllegalArgumentException(
          "Unsupported dependency " + cls + ". " + this.mProviders.size() + " providers known.");
    }
    return lazyDependencyCreator.createDependency();
  }

  public final Object getSystemService(Class cls) {
    return ((Context) get(Context.class)).getSystemService(cls);
  }

  public final Context getContext() {
    return this.mContext;
  }

  public final ServiceThread getServiceThread() {
    ServiceThread serviceThread = new ServiceThread("desktopmode", -4, false);
    serviceThread.start();
    return serviceThread;
  }

  public final BlockerManager getBlockerManager() {
    return new BlockerManager(
        (Context) get(Context.class),
        (IStateManager) get(IStateManager.class),
        (ActivityManager) get(ActivityManager.class),
        (DisplayManager) get(DisplayManager.class),
        this);
  }

  public final BootInitBlocker getBootInitBlocker() {
    return new BootInitBlocker(
        (ServiceThread) get(ServiceThread.class),
        (IStateManager) get(IStateManager.class),
        (SemDesktopModeManager) get(SemDesktopModeManager.class));
  }

  public final BleAdvertiserServiceManager getBleAdvertiserServiceManager() {
    return new BleAdvertiserServiceManager(
        (Context) get(Context.class),
        (ServiceThread) get(ServiceThread.class),
        (IStateManager) get(IStateManager.class));
  }

  public final CoverStateManager getCoverStateManager() {
    return new CoverStateManager(
        (Context) get(Context.class),
        (IStateManager) get(IStateManager.class),
        (SemDesktopModeManager) get(SemDesktopModeManager.class),
        (PowerManager) get(PowerManager.class),
        (InputManagerService) get(InputManagerService.class));
  }

  public final DesktopModeService getDesktopModeService() {
    return new DesktopModeService(
        this,
        (Context) get(Context.class),
        (ServiceThread) get(ServiceThread.class),
        (SemDesktopModeStateNotifier) get(SemDesktopModeStateNotifier.class),
        (IStateManager) get(IStateManager.class));
  }

  public final DisplayPortStateManager getDisplayPortStateManager() {
    return new DisplayPortStateManager(
        (Context) get(Context.class),
        (IStateManager) get(IStateManager.class),
        (SettingsHelper) get(SettingsHelper.class));
  }

  public final DockManager getDockManager() {
    return new DockManager(
        (Context) get(Context.class),
        (ServiceThread) get(ServiceThread.class),
        (IStateManager) get(IStateManager.class));
  }

  public final DualModeChanger getDualModeChanger() {
    return new DualModeChanger(
        (Context) get(Context.class),
        (IStateManager) get(IStateManager.class),
        (SemDesktopModeStateNotifier) get(SemDesktopModeStateNotifier.class),
        (ServiceThread) get(ServiceThread.class),
        (IStatusBarService) get(IStatusBarService.class),
        (UiManager) get(UiManager.class),
        (SettingsHelper) get(SettingsHelper.class),
        (MultiResolutionManager) get(MultiResolutionManager.class),
        (ActivityTaskManagerService) get(ActivityTaskManagerService.class),
        (ActivityTaskManagerInternal) get(ActivityTaskManagerInternal.class),
        (ActivityManagerInternal) get(ActivityManagerInternal.class),
        (WindowManagerInternal) get(WindowManagerInternal.class),
        (StatusBarManager) get(StatusBarManager.class),
        (MultiWindowManager) get(MultiWindowManager.class),
        (SemDesktopModeManager) get(SemDesktopModeManager.class),
        (CoverStateManager) get(CoverStateManager.class),
        (KeyguardManager) get(KeyguardManager.class),
        (TouchpadManager) get(TouchpadManager.class),
        (PowerManager) get(PowerManager.class),
        (PowerManagerInternal) get(PowerManagerInternal.class));
  }

  public final EmergencyModeBlocker getEmergencyModeBlocker() {
    return new EmergencyModeBlocker(
        (Context) get(Context.class),
        (IStateManager) get(IStateManager.class),
        (SemDesktopModeManager) get(SemDesktopModeManager.class),
        this);
  }

  public final HardwareManager getHardwareManager() {
    return new HardwareManager(
        (Context) get(Context.class),
        (IStateManager) get(IStateManager.class),
        (SettingsHelper) get(SettingsHelper.class),
        (InputManager) get(InputManager.class),
        (DisplayManager) get(DisplayManager.class),
        (PowerManagerInternal) get(PowerManagerInternal.class),
        (WindowManagerService) get(WindowManagerService.class),
        (IDisplayManager) get(IDisplayManager.class));
  }

  public final StateManager getIStateManager() {
    return new StateManager((ServiceThread) get(ServiceThread.class));
  }

  public final MultiResolutionManager getMultiResolutionManager() {
    return new MultiResolutionManager(
        (Context) get(Context.class),
        (IStateManager) get(IStateManager.class),
        (SettingsHelper) get(SettingsHelper.class),
        (ActivityTaskManagerInternal) get(ActivityTaskManagerInternal.class),
        (WindowManagerService) get(WindowManagerService.class),
        (WindowManagerInternal) get(WindowManagerInternal.class));
  }

  public final PackageStateManager getPackageStateManager() {
    return new PackageStateManager(
        (Context) get(Context.class),
        (IStateManager) get(IStateManager.class),
        (ServiceThread) get(ServiceThread.class),
        (IPackageManager) get(IPackageManager.class));
  }

  public final SemDesktopModeStateNotifier getSemDesktopModeStateNotifier() {
    return new SemDesktopModeStateNotifier((Context) get(Context.class), this);
  }

  public final SettingsHelper getSettingsHelper() {
    return new SettingsHelper(
        (Context) get(Context.class),
        (ServiceThread) get(ServiceThread.class),
        (IStateManager) get(IStateManager.class),
        this);
  }

  public final StandaloneModeChanger getStandaloneModeChanger() {
    return new StandaloneModeChanger(
        (Context) get(Context.class),
        (IStateManager) get(IStateManager.class),
        (SemDesktopModeStateNotifier) get(SemDesktopModeStateNotifier.class),
        (ServiceThread) get(ServiceThread.class),
        (UiManager) get(UiManager.class),
        (SettingsHelper) get(SettingsHelper.class),
        (MultiResolutionManager) get(MultiResolutionManager.class),
        (ActivityTaskManagerService) get(ActivityTaskManagerService.class),
        (ActivityTaskManagerInternal) get(ActivityTaskManagerInternal.class),
        (ActivityManagerInternal) get(ActivityManagerInternal.class),
        (WindowManagerInternal) get(WindowManagerInternal.class),
        (StatusBarManager) get(StatusBarManager.class),
        (MultiWindowManager) get(MultiWindowManager.class),
        (SemDvfsManager) get(SemDvfsManager.class),
        (SemDesktopModeManager) get(SemDesktopModeManager.class),
        (TelecomManager) get(TelecomManager.class),
        (IUiModeManager) get(IUiModeManager.class),
        (HardwareManager) get(HardwareManager.class),
        (UiModeManagerInternal) get(UiModeManagerInternal.class));
  }

  public final TouchpadManager getTouchpadManager() {
    return new TouchpadManager(
        (Context) get(Context.class),
        (IStateManager) get(IStateManager.class),
        (ServiceThread) get(ServiceThread.class),
        (SettingsHelper) get(SettingsHelper.class),
        (WindowManagerService) get(WindowManagerService.class));
  }

  public final UiManager getUiManager() {
    return new UiManager(
        (Context) get(Context.class),
        (ServiceThread) get(ServiceThread.class),
        (IStateManager) get(IStateManager.class));
  }

  public final WirelessDexManager getWirelessDexManager() {
    return new WirelessDexManager(
        (Context) get(Context.class),
        (IStateManager) get(IStateManager.class),
        (UiManager) get(UiManager.class),
        (DisplayManager) get(DisplayManager.class),
        (InputManager) get(InputManager.class),
        (ServiceThread) get(ServiceThread.class),
        (WindowManagerService) get(WindowManagerService.class));
  }

  public final McfManager getMcfManager() {
    return new McfManager(
        (Context) get(Context.class),
        (ServiceThread) get(ServiceThread.class),
        (IStateManager) get(IStateManager.class),
        (SettingsHelper) get(SettingsHelper.class),
        (BleAdvertiserServiceManager) get(BleAdvertiserServiceManager.class));
  }

  public final ActivityManager getActivityManager() {
    return (ActivityManager) getSystemService(ActivityManager.class);
  }

  public final ActivityManagerService getActivityManagerService() {
    return (ActivityManagerService) ServiceManager.getService("activity");
  }

  public final ActivityTaskManagerInternal getActivityTaskManagerInternal() {
    return (ActivityTaskManagerInternal)
        LocalServices.getService(ActivityTaskManagerInternal.class);
  }

  public final ActivityTaskManagerService getActivityTaskManagerService() {
    return ActivityTaskManager.getService();
  }

  public final ActivityManagerInternal getActivityManagerInternal() {
    return (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
  }

  public final DisplayManager getDisplayManager() {
    return (DisplayManager) getSystemService(DisplayManager.class);
  }

  public final IUiModeManager getIUiModeManager() {
    return IUiModeManager.Stub.asInterface(ServiceManager.getService("uimode"));
  }

  public final InputManager getInputManager() {
    return (InputManager) getSystemService(InputManager.class);
  }

  public final IPackageManager getIPackageManager() {
    return AppGlobals.getPackageManager();
  }

  public final IStatusBarService getIStatusBarService() {
    return IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
  }

  public final MultiWindowManager getMultiWindowManager() {
    return MultiWindowManager.getInstance();
  }

  public final PowerManager getPowerManager() {
    return (PowerManager) getSystemService(PowerManager.class);
  }

  public final PowerManagerInternal getPowerManagerInternal() {
    return (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
  }

  public final SemDesktopModeManager getSemDesktopModeManager() {
    return (SemDesktopModeManager) getSystemService(SemDesktopModeManager.class);
  }

  public final SemDvfsManager getSemDvfsManager() {
    return SemDvfsManager.createInstance(
        (Context) get(Context.class), DesktopModeService.class.getSimpleName());
  }

  public final InputManagerService getInputManagerService() {
    return IInputManager.Stub.asInterface(ServiceManager.getService("input"));
  }

  public final StatusBarManager getStatusBarManager() {
    return (StatusBarManager) getSystemService(StatusBarManager.class);
  }

  public final TelecomManager getTelecomManager() {
    return (TelecomManager) getSystemService(TelecomManager.class);
  }

  public final UiModeManagerInternal getUiModeManagerInternal() {
    return (UiModeManagerInternal) LocalServices.getService(UiModeManagerInternal.class);
  }

  public final WindowManagerInternal getWindowManagerInternal() {
    return (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
  }

  public final WindowManagerService getWindowManagerService() {
    return IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
  }

  public final KeyguardManager getKeyguardManager() {
    return (KeyguardManager) getSystemService(KeyguardManager.class);
  }

  public final IDisplayManager getIDisplayManager() {
    return IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
  }

  public final DexManager getDexManager() {
    return DexManager.getInstance();
  }
}
