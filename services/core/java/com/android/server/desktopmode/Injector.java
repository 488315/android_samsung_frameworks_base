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
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.ServiceManager;
import android.telecom.TelecomManager;
import android.util.ArrayMap;
import android.view.IWindowManager;

import com.android.internal.statusbar.IStatusBarService;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.UiModeManagerService;
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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Injector {
    public final Context mContext;
    public final ArrayMap mDependencies = new ArrayMap();
    public final ArrayMap mProviders;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface LazyDependencyCreator {
        Object createDependency();
    }

    public Injector(Context context) {
        ArrayMap arrayMap = new ArrayMap();
        this.mProviders = arrayMap;
        this.mContext = context;
        final int i = 0;
        arrayMap.put(
                Context.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i2 = 2;
        arrayMap.put(
                ServiceThread.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i2) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i3 = 14;
        arrayMap.put(
                BlockerManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i3) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i4 = 26;
        arrayMap.put(
                BootInitBlocker.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i4) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i5 = 4;
        arrayMap.put(
                BleAdvertiserServiceManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i5) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i6 = 5;
        arrayMap.put(
                CoverStateManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i6) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i7 = 6;
        arrayMap.put(
                DesktopModeService.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i7) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i8 = 7;
        arrayMap.put(
                DisplayPortStateManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i8) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i9 = 8;
        arrayMap.put(
                DockManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i9) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i10 = 9;
        arrayMap.put(
                DualModeChanger.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i10) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i11 = 11;
        arrayMap.put(
                EmergencyModeBlocker.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i11) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i12 = 22;
        arrayMap.put(
                HardwareManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i12) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i13 = 0;
        arrayMap.put(
                IStateManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i13) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i14 = 10;
        arrayMap.put(
                MultiResolutionManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i14) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        arrayMap.put(
                PackageStateManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i11) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i15 = 12;
        arrayMap.put(
                SemDesktopModeStateNotifier.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i15) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i16 = 13;
        arrayMap.put(
                SettingsHelper.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i16) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i17 = 14;
        arrayMap.put(
                StandaloneModeChanger.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i17) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i18 = 15;
        arrayMap.put(
                TouchpadManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i18) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i19 = 1;
        arrayMap.put(
                UiManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i19) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i20 = 3;
        arrayMap.put(
                WirelessDexManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i20) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i21 = 4;
        arrayMap.put(
                McfManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i21) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i22 = 5;
        arrayMap.put(
                ActivityManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i22) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i23 = 6;
        arrayMap.put(
                ActivityManagerService.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i23) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i24 = 7;
        arrayMap.put(
                ActivityTaskManagerInternal.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i24) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i25 = 8;
        arrayMap.put(
                ActivityTaskManagerService.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i25) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i26 = 9;
        arrayMap.put(
                ActivityManagerInternal.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i26) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i27 = 10;
        arrayMap.put(
                DisplayManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i27) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i28 = 12;
        arrayMap.put(
                IUiModeManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i28) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i29 = 13;
        arrayMap.put(
                InputManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i29) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i30 = 15;
        arrayMap.put(
                InputManagerService.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i30) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i31 = 16;
        arrayMap.put(
                IPackageManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i31) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i32 = 17;
        arrayMap.put(
                IStatusBarService.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i32) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i33 = 18;
        arrayMap.put(
                MultiWindowManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i33) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i34 = 19;
        arrayMap.put(
                PowerManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i34) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i35 = 20;
        arrayMap.put(
                PowerManagerInternal.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i35) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i36 = 21;
        arrayMap.put(
                SemDesktopModeManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i36) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i37 = 23;
        arrayMap.put(
                SemDvfsManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i37) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i38 = 24;
        arrayMap.put(
                StatusBarManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i38) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i39 = 25;
        arrayMap.put(
                TelecomManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i39) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i40 = 27;
        arrayMap.put(
                UiModeManagerService.LocalService.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i40) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i41 = 28;
        arrayMap.put(
                WindowManagerInternal.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i41) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i42 = 29;
        arrayMap.put(
                WindowManagerService.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda0
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i42) {
                            case 0:
                                return this.f$0.mContext;
                            case 1:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new UiManager(
                                        (Context) injector.get(Context.class),
                                        (ServiceThread) injector.get(ServiceThread.class),
                                        (IStateManager) injector.get(IStateManager.class));
                            case 2:
                                this.f$0.getClass();
                                ServiceThread serviceThread =
                                        new ServiceThread(-4, "desktopmode", false);
                                serviceThread.start();
                                return serviceThread;
                            case 3:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return new WirelessDexManager(
                                        (Context) injector2.get(Context.class),
                                        (IStateManager) injector2.get(IStateManager.class),
                                        (UiManager) injector2.get(UiManager.class),
                                        (DisplayManager) injector2.get(DisplayManager.class),
                                        (InputManager) injector2.get(InputManager.class),
                                        (ServiceThread) injector2.get(ServiceThread.class),
                                        (WindowManagerService)
                                                injector2.get(WindowManagerService.class));
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new McfManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class),
                                        (SettingsHelper) injector3.get(SettingsHelper.class),
                                        (BleAdvertiserServiceManager)
                                                injector3.get(BleAdvertiserServiceManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return (ActivityManager)
                                        injector4.getSystemService(ActivityManager.class);
                            case 6:
                                this.f$0.getClass();
                                return (ActivityManagerService)
                                        ServiceManager.getService("activity");
                            case 7:
                                this.f$0.getClass();
                                return (ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class);
                            case 8:
                                this.f$0.getClass();
                                return ActivityTaskManager.getService();
                            case 9:
                                this.f$0.getClass();
                                return (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                            case 10:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return (DisplayManager)
                                        injector5.getSystemService(DisplayManager.class);
                            case 11:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new EmergencyModeBlocker(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector6.get(SemDesktopModeManager.class),
                                        injector6);
                            case 12:
                                this.f$0.getClass();
                                return IUiModeManager.Stub.asInterface(
                                        ServiceManager.getService("uimode"));
                            case 13:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return (InputManager)
                                        injector7.getSystemService(InputManager.class);
                            case 14:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                return new BlockerManager(
                                        (Context) injector8.get(Context.class),
                                        (IStateManager) injector8.get(IStateManager.class),
                                        (ActivityManager) injector8.get(ActivityManager.class),
                                        (DisplayManager) injector8.get(DisplayManager.class),
                                        injector8);
                            case 15:
                                this.f$0.getClass();
                                return IInputManager.Stub.asInterface(
                                        ServiceManager.getService("input"));
                            case 16:
                                this.f$0.getClass();
                                return AppGlobals.getPackageManager();
                            case 17:
                                this.f$0.getClass();
                                return IStatusBarService.Stub.asInterface(
                                        ServiceManager.getService("statusbar"));
                            case 18:
                                this.f$0.getClass();
                                return MultiWindowManager.getInstance();
                            case 19:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                return (PowerManager)
                                        injector9.getSystemService(PowerManager.class);
                            case 20:
                                this.f$0.getClass();
                                return (PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class);
                            case 21:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return (SemDesktopModeManager)
                                        injector10.getSystemService(SemDesktopModeManager.class);
                            case 22:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new HardwareManager(
                                        (Context) injector11.get(Context.class),
                                        (IStateManager) injector11.get(IStateManager.class),
                                        (SettingsHelper) injector11.get(SettingsHelper.class),
                                        (InputManager) injector11.get(InputManager.class),
                                        (DisplayManager) injector11.get(DisplayManager.class),
                                        (PowerManagerInternal)
                                                injector11.get(PowerManagerInternal.class),
                                        (WindowManagerService)
                                                injector11.get(WindowManagerService.class),
                                        (IDisplayManager) injector11.get(IDisplayManager.class));
                            case 23:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return SemDvfsManager.createInstance(
                                        (Context) injector12.get(Context.class),
                                        "DesktopModeService");
                            case 24:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                return (StatusBarManager)
                                        injector13.getSystemService(StatusBarManager.class);
                            case 25:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return (TelecomManager)
                                        injector14.getSystemService(TelecomManager.class);
                            case 26:
                                Injector injector15 = this.f$0;
                                injector15.getClass();
                                return new BootInitBlocker(
                                        (ServiceThread) injector15.get(ServiceThread.class),
                                        (IStateManager) injector15.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector15.get(SemDesktopModeManager.class));
                            case 27:
                                this.f$0.getClass();
                                return (UiModeManagerService.LocalService)
                                        LocalServices.getService(
                                                UiModeManagerService.LocalService.class);
                            case 28:
                                this.f$0.getClass();
                                return (WindowManagerInternal)
                                        LocalServices.getService(WindowManagerInternal.class);
                            default:
                                this.f$0.getClass();
                                return IWindowManager.Stub.asInterface(
                                        ServiceManager.getService("window"));
                        }
                    }
                });
        final int i43 = 1;
        arrayMap.put(
                KeyguardManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i43) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i44 = 2;
        arrayMap.put(
                IDisplayManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i44) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
        final int i45 = 3;
        arrayMap.put(
                DexManager.class,
                new LazyDependencyCreator(
                        this) { // from class:
                                // com.android.server.desktopmode.Injector$$ExternalSyntheticLambda3
                    public final /* synthetic */ Injector f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.desktopmode.Injector.LazyDependencyCreator
                    public final Object createDependency() {
                        switch (i45) {
                            case 0:
                                Injector injector = this.f$0;
                                injector.getClass();
                                return new StateManager(
                                        (ServiceThread) injector.get(ServiceThread.class));
                            case 1:
                                Injector injector2 = this.f$0;
                                injector2.getClass();
                                return (KeyguardManager)
                                        injector2.getSystemService(KeyguardManager.class);
                            case 2:
                                this.f$0.getClass();
                                return IDisplayManager.Stub.asInterface(
                                        ServiceManager.getService("display"));
                            case 3:
                                this.f$0.getClass();
                                return DexManager.getInstance();
                            case 4:
                                Injector injector3 = this.f$0;
                                injector3.getClass();
                                return new BleAdvertiserServiceManager(
                                        (Context) injector3.get(Context.class),
                                        (ServiceThread) injector3.get(ServiceThread.class),
                                        (IStateManager) injector3.get(IStateManager.class));
                            case 5:
                                Injector injector4 = this.f$0;
                                injector4.getClass();
                                return new CoverStateManager(
                                        (Context) injector4.get(Context.class),
                                        (IStateManager) injector4.get(IStateManager.class),
                                        (SemDesktopModeManager)
                                                injector4.get(SemDesktopModeManager.class),
                                        (PowerManager) injector4.get(PowerManager.class),
                                        (InputManagerService)
                                                injector4.get(InputManagerService.class));
                            case 6:
                                Injector injector5 = this.f$0;
                                injector5.getClass();
                                return new DesktopModeService(
                                        injector5,
                                        (Context) injector5.get(Context.class),
                                        (ServiceThread) injector5.get(ServiceThread.class),
                                        (SemDesktopModeStateNotifier)
                                                injector5.get(SemDesktopModeStateNotifier.class),
                                        (IStateManager) injector5.get(IStateManager.class));
                            case 7:
                                Injector injector6 = this.f$0;
                                injector6.getClass();
                                return new DisplayPortStateManager(
                                        (Context) injector6.get(Context.class),
                                        (IStateManager) injector6.get(IStateManager.class),
                                        (SettingsHelper) injector6.get(SettingsHelper.class));
                            case 8:
                                Injector injector7 = this.f$0;
                                injector7.getClass();
                                return new DockManager(
                                        (Context) injector7.get(Context.class),
                                        (ServiceThread) injector7.get(ServiceThread.class),
                                        (IStateManager) injector7.get(IStateManager.class));
                            case 9:
                                Injector injector8 = this.f$0;
                                injector8.getClass();
                                Context context2 = (Context) injector8.get(Context.class);
                                IStateManager iStateManager =
                                        (IStateManager) injector8.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier =
                                        (SemDesktopModeStateNotifier)
                                                injector8.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread =
                                        (ServiceThread) injector8.get(ServiceThread.class);
                                IStatusBarService iStatusBarService =
                                        (IStatusBarService) injector8.get(IStatusBarService.class);
                                UiManager uiManager = (UiManager) injector8.get(UiManager.class);
                                SettingsHelper settingsHelper =
                                        (SettingsHelper) injector8.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager =
                                        (MultiResolutionManager)
                                                injector8.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                injector8.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal =
                                        (ActivityTaskManagerInternal)
                                                injector8.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal =
                                        (ActivityManagerInternal)
                                                injector8.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal =
                                        (WindowManagerInternal)
                                                injector8.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager =
                                        (MultiWindowManager)
                                                injector8.get(MultiWindowManager.class);
                                return new DualModeChanger(
                                        context2,
                                        iStateManager,
                                        semDesktopModeStateNotifier,
                                        serviceThread,
                                        iStatusBarService,
                                        uiManager,
                                        settingsHelper,
                                        multiResolutionManager,
                                        activityTaskManagerService,
                                        activityTaskManagerInternal,
                                        activityManagerInternal,
                                        windowManagerInternal,
                                        multiWindowManager,
                                        (CoverStateManager) injector8.get(CoverStateManager.class),
                                        (KeyguardManager) injector8.get(KeyguardManager.class),
                                        (TouchpadManager) injector8.get(TouchpadManager.class),
                                        (PowerManager) injector8.get(PowerManager.class),
                                        (PowerManagerInternal)
                                                injector8.get(PowerManagerInternal.class));
                            case 10:
                                Injector injector9 = this.f$0;
                                injector9.getClass();
                                Context context3 = (Context) injector9.get(Context.class);
                                IStateManager iStateManager2 =
                                        (IStateManager) injector9.get(IStateManager.class);
                                SettingsHelper settingsHelper2 =
                                        (SettingsHelper) injector9.get(SettingsHelper.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal2 =
                                        (ActivityTaskManagerInternal)
                                                injector9.get(ActivityTaskManagerInternal.class);
                                return new MultiResolutionManager(
                                        context3,
                                        iStateManager2,
                                        settingsHelper2,
                                        activityTaskManagerInternal2,
                                        (WindowManagerInternal)
                                                injector9.get(WindowManagerInternal.class));
                            case 11:
                                Injector injector10 = this.f$0;
                                injector10.getClass();
                                return new PackageStateManager(
                                        (Context) injector10.get(Context.class),
                                        (IStateManager) injector10.get(IStateManager.class),
                                        (ServiceThread) injector10.get(ServiceThread.class),
                                        (IPackageManager) injector10.get(IPackageManager.class));
                            case 12:
                                Injector injector11 = this.f$0;
                                injector11.getClass();
                                return new SemDesktopModeStateNotifier(
                                        (Context) injector11.get(Context.class), injector11);
                            case 13:
                                Injector injector12 = this.f$0;
                                injector12.getClass();
                                return new SettingsHelper(
                                        (Context) injector12.get(Context.class),
                                        (ServiceThread) injector12.get(ServiceThread.class),
                                        (IStateManager) injector12.get(IStateManager.class),
                                        injector12);
                            case 14:
                                Injector injector13 = this.f$0;
                                injector13.getClass();
                                Context context4 = (Context) injector13.get(Context.class);
                                IStateManager iStateManager3 =
                                        (IStateManager) injector13.get(IStateManager.class);
                                SemDesktopModeStateNotifier semDesktopModeStateNotifier2 =
                                        (SemDesktopModeStateNotifier)
                                                injector13.get(SemDesktopModeStateNotifier.class);
                                ServiceThread serviceThread2 =
                                        (ServiceThread) injector13.get(ServiceThread.class);
                                UiManager uiManager2 = (UiManager) injector13.get(UiManager.class);
                                SettingsHelper settingsHelper3 =
                                        (SettingsHelper) injector13.get(SettingsHelper.class);
                                MultiResolutionManager multiResolutionManager2 =
                                        (MultiResolutionManager)
                                                injector13.get(MultiResolutionManager.class);
                                ActivityTaskManagerService activityTaskManagerService2 =
                                        (ActivityTaskManagerService)
                                                injector13.get(ActivityTaskManagerService.class);
                                ActivityTaskManagerInternal activityTaskManagerInternal3 =
                                        (ActivityTaskManagerInternal)
                                                injector13.get(ActivityTaskManagerInternal.class);
                                ActivityManagerInternal activityManagerInternal2 =
                                        (ActivityManagerInternal)
                                                injector13.get(ActivityManagerInternal.class);
                                WindowManagerInternal windowManagerInternal2 =
                                        (WindowManagerInternal)
                                                injector13.get(WindowManagerInternal.class);
                                MultiWindowManager multiWindowManager2 =
                                        (MultiWindowManager)
                                                injector13.get(MultiWindowManager.class);
                                SemDvfsManager semDvfsManager =
                                        (SemDvfsManager) injector13.get(SemDvfsManager.class);
                                TelecomManager telecomManager =
                                        (TelecomManager) injector13.get(TelecomManager.class);
                                IUiModeManager iUiModeManager =
                                        (IUiModeManager) injector13.get(IUiModeManager.class);
                                return new StandaloneModeChanger(
                                        context4,
                                        iStateManager3,
                                        semDesktopModeStateNotifier2,
                                        serviceThread2,
                                        uiManager2,
                                        settingsHelper3,
                                        multiResolutionManager2,
                                        activityTaskManagerService2,
                                        activityTaskManagerInternal3,
                                        activityManagerInternal2,
                                        windowManagerInternal2,
                                        multiWindowManager2,
                                        semDvfsManager,
                                        telecomManager,
                                        iUiModeManager,
                                        (UiModeManagerService.LocalService)
                                                injector13.get(
                                                        UiModeManagerService.LocalService.class));
                            default:
                                Injector injector14 = this.f$0;
                                injector14.getClass();
                                return new TouchpadManager(
                                        (Context) injector14.get(Context.class),
                                        (IStateManager) injector14.get(IStateManager.class),
                                        (ServiceThread) injector14.get(ServiceThread.class),
                                        (SettingsHelper) injector14.get(SettingsHelper.class),
                                        (WindowManagerService)
                                                injector14.get(WindowManagerService.class));
                        }
                    }
                });
    }

    public Object createDependency(Class cls) {
        LazyDependencyCreator lazyDependencyCreator =
                (LazyDependencyCreator) this.mProviders.get(cls);
        if (lazyDependencyCreator != null) {
            return lazyDependencyCreator.createDependency();
        }
        throw new IllegalArgumentException(
                "Unsupported dependency "
                        + cls
                        + ". "
                        + this.mProviders.size()
                        + " providers known.");
    }

    public final Object get(Class cls) {
        ArrayMap arrayMap = this.mDependencies;
        Object obj = arrayMap.get(cls);
        if (obj != null) {
            return obj;
        }
        Object createDependency = createDependency(cls);
        arrayMap.put(cls, createDependency);
        return createDependency;
    }

    public final Object getSystemService(Class cls) {
        return ((Context) get(Context.class)).getSystemService(cls);
    }
}
