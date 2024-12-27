package com.android.systemui.recents;

import android.R;
import android.app.Flags;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.graphics.Region;
import android.hardware.input.InputManagerGlobal;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.view.KeyEvent;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ScreenshotHelper;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardWmStateRefactor;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.ui.view.InWindowLauncherUnlockAnimationManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.popup.SamsungScreenPinningRequest;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.recents.ISystemUiProxy;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.unfold.progress.UnfoldTransitionProgressForwarder;
import com.android.wm.shell.shared.DesktopModeStatus;
import com.android.wm.shell.sysui.ShellInterface;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.inject.Provider;

public final class OverviewProxyService implements CallbackController, NavigationModeController.ModeChangedListener, Dumpable {
    static final String ACTION_QUICKSTEP = "android.intent.action.QUICKSTEP_SERVICE";
    public Region mActiveNavBarRegion;
    public boolean mBound;
    public final CommandQueue mCommandQueue;
    public int mConnectionBackoffAttempts;
    public final Context mContext;
    public final DisplayTracker mDisplayTracker;
    public final FgsManagerController mFgsManagerController;
    public final Handler mHandler;
    public long mInputFocusTransferStartMillis;
    public float mInputFocusTransferStartY;
    public boolean mInputFocusTransferStarted;
    public boolean mIsEnabled;
    public final boolean mIsNonPrimaryUser;
    public final AnonymousClass3 mLauncherStateChangedReceiver;
    public final Executor mMainExecutor;
    public final Handler mMainHandler;
    public final Lazy mNavBarControllerLazy;
    public int mNavBarMode;
    public final NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public IOverviewProxy mOverviewProxy;
    public final AnonymousClass4 mOverviewServiceConnection;
    public final OverviewProxyService$$ExternalSyntheticLambda3 mOverviewServiceDeathRcpt;
    public final Intent mQuickStepIntent;
    public final ComponentName mRecentsComponentName;
    public final SamsungScreenPinningRequest mSamsungScreenPinningRequest;
    public final AnonymousClass8 mScreenLifecycleObserver;
    public final ScreenshotHelper mScreenshotHelper;
    public final SearcleManager mSearcleManager;
    public final Lazy mShadeViewControllerLazy;
    public final ShellInterface mShellInterface;
    public final NotificationShadeWindowController mStatusBarWinController;
    public final StatusBarWindowCallback mStatusBarWindowCallback;
    public final SysUiState mSysUiState;
    public final KeyguardUnlockAnimationController mSysuiUnlockAnimationController;
    public final UiEventLogger mUiEventLogger;
    public final Optional mUnfoldTransitionProgressForwarder;
    public final UserTracker.Callback mUserChangedCallback;
    public final AnonymousClass2 mUserEventReceiver;
    public final UserTracker mUserTracker;
    public final AnonymousClass5 mVoiceInteractionSessionListener;
    public final AnonymousClass9 mWakefulnessLifecycleObserver;
    public final OverviewProxyService$$ExternalSyntheticLambda0 mConnectionRunnable = new OverviewProxyService$$ExternalSyntheticLambda0(this, 0);
    public final List mConnectionCallbacks = new ArrayList();
    public int mCurrentBoundedUserId = -1;
    public ISystemUiProxy mSysUiProxy = new AnonymousClass1();
    public final OverviewProxyService$$ExternalSyntheticLambda0 mDeferredConnectionCallback = new OverviewProxyService$$ExternalSyntheticLambda0(this, 1);

    /* renamed from: com.android.systemui.recents.OverviewProxyService$1, reason: invalid class name */
    public final class AnonymousClass1 extends ISystemUiProxy.Stub {
        public final OverviewProxyService$1$$ExternalSyntheticLambda2 mOnNumberOfPackagesChangedListener = new FgsManagerController.OnNumberOfPackagesChangedListener() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda2
            @Override // com.android.systemui.qs.FgsManagerController.OnNumberOfPackagesChangedListener
            public final void onNumberOfPackagesChanged(int i) {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.getClass();
                try {
                    IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
                    if (iOverviewProxy != null) {
                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onNumberOfVisibleFgsChanged(i);
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to call onNumberOfVisibleFgsChanged().", e);
                }
            }
        };

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda2] */
        public AnonymousClass1() {
        }

        public final void sendEvent(int i) {
            long uptimeMillis = SystemClock.uptimeMillis();
            KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 72, 257);
            keyEvent.setDisplayId(OverviewProxyService.this.mContext.getDisplay().getDisplayId());
            InputManagerGlobal.getInstance().injectInputEvent(keyEvent, 0);
        }

        public final void verifyCallerAndClearCallingIdentity(String str, Supplier supplier) {
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            if (identifier == OverviewProxyService.this.mCurrentBoundedUserId) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    supplier.get();
                    return;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            Log.w("OverviewProxyService", "Launcher called sysui with invalid user: " + identifier + ", reason: " + str);
        }

        public final void verifyCallerAndClearCallingIdentityPostMain(final Runnable runnable, String str) {
            verifyCallerAndClearCallingIdentity(str, new Supplier() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda30
                @Override // java.util.function.Supplier
                public final Object get() {
                    OverviewProxyService.AnonymousClass1 anonymousClass1 = OverviewProxyService.AnonymousClass1.this;
                    return Boolean.valueOf(OverviewProxyService.this.mHandler.post(runnable));
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [android.content.BroadcastReceiver, com.android.systemui.recents.OverviewProxyService$3] */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.recents.OverviewProxyService$4] */
    /* JADX WARN: Type inference failed for: r12v0, types: [com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda3] */
    public OverviewProxyService(Context context, Executor executor, CommandQueue commandQueue, ShellInterface shellInterface, Lazy lazy, Lazy lazy2, ScreenPinningRequest screenPinningRequest, NavigationModeController navigationModeController, NotificationShadeWindowController notificationShadeWindowController, final SysUiState sysUiState, Provider provider, UserTracker userTracker, WakefulnessLifecycle wakefulnessLifecycle, UiEventLogger uiEventLogger, DisplayTracker displayTracker, KeyguardUnlockAnimationController keyguardUnlockAnimationController, InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager, AssistUtils assistUtils, DumpManager dumpManager, Optional<UnfoldTransitionProgressForwarder> optional, BroadcastDispatcher broadcastDispatcher, FgsManagerController fgsManagerController, Handler handler, SearcleManager searcleManager, SamsungScreenPinningRequest samsungScreenPinningRequest, ScreenLifecycle screenLifecycle) {
        this.mNavBarMode = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.recents.OverviewProxyService.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (Objects.equals(intent.getAction(), "android.intent.action.USER_UNLOCKED") && Flags.keyguardPrivateNotifications()) {
                    OverviewProxyService overviewProxyService = OverviewProxyService.this;
                    if (overviewProxyService.mOverviewProxy == null) {
                        overviewProxyService.startConnectionToCurrentUser();
                    }
                }
            }
        };
        ?? r10 = new BroadcastReceiver() { // from class: com.android.systemui.recents.OverviewProxyService.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (Objects.equals(intent.getAction(), "android.intent.action.PACKAGE_ADDED")) {
                    OverviewProxyService.this.updateEnabledAndBinding();
                    return;
                }
                String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                if (stringArrayExtra == null) {
                    return;
                }
                ResolveInfo resolveService = context2.getPackageManager().resolveService(new Intent(OverviewProxyService.ACTION_QUICKSTEP), 0);
                if (resolveService == null) {
                    return;
                }
                String str = resolveService.serviceInfo.name;
                for (String str2 : stringArrayExtra) {
                    if (str.equals(str2)) {
                        Log.i("OverviewProxyService", "Rebinding for component [" + str2 + "] change");
                        OverviewProxyService.this.updateEnabledAndBinding();
                        return;
                    }
                }
            }
        };
        this.mLauncherStateChangedReceiver = r10;
        this.mOverviewServiceConnection = new ServiceConnection() { // from class: com.android.systemui.recents.OverviewProxyService.4
            @Override // android.content.ServiceConnection
            public final void onBindingDied(ComponentName componentName) {
                Log.w("OverviewProxyService", "Binding died of '" + componentName + "', try reconnecting");
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.mCurrentBoundedUserId = -1;
                overviewProxyService.retryConnectionWithBackoff();
            }

            @Override // android.content.ServiceConnection
            public final void onNullBinding(ComponentName componentName) {
                Log.w("OverviewProxyService", "Null binding of '" + componentName + "', try reconnecting");
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.mCurrentBoundedUserId = -1;
                overviewProxyService.retryConnectionWithBackoff();
            }

            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Region region;
                Log.d("OverviewProxyService", "Overview proxy service connected");
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.mConnectionBackoffAttempts = 0;
                overviewProxyService.mHandler.removeCallbacks(overviewProxyService.mDeferredConnectionCallback);
                try {
                    iBinder.linkToDeath(OverviewProxyService.this.mOverviewServiceDeathRcpt, 0);
                    OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                    overviewProxyService2.mCurrentBoundedUserId = ((UserTrackerImpl) overviewProxyService2.mUserTracker).getUserId();
                    OverviewProxyService overviewProxyService3 = OverviewProxyService.this;
                    int i = IOverviewProxy.Stub.$r8$clinit;
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.shared.recents.IOverviewProxy");
                    overviewProxyService3.mOverviewProxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IOverviewProxy)) ? new IOverviewProxy.Stub.Proxy(iBinder) : (IOverviewProxy) queryLocalInterface;
                    final Bundle bundle = new Bundle();
                    ISystemUiProxy.Stub stub = (ISystemUiProxy.Stub) OverviewProxyService.this.mSysUiProxy;
                    stub.getClass();
                    bundle.putBinder(QuickStepContract.KEY_EXTRA_SYSUI_PROXY, stub);
                    KeyguardUnlockAnimationController keyguardUnlockAnimationController2 = OverviewProxyService.this.mSysuiUnlockAnimationController;
                    keyguardUnlockAnimationController2.getClass();
                    bundle.putBinder("unlock_animation", keyguardUnlockAnimationController2);
                    OverviewProxyService.this.mUnfoldTransitionProgressForwarder.ifPresent(new Consumer() { // from class: com.android.systemui.recents.OverviewProxyService$4$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            Bundle bundle2 = bundle;
                            UnfoldTransitionProgressForwarder unfoldTransitionProgressForwarder = (UnfoldTransitionProgressForwarder) obj;
                            unfoldTransitionProgressForwarder.getClass();
                            bundle2.putBinder("extra_unfold_animation", unfoldTransitionProgressForwarder);
                        }
                    });
                    OverviewProxyService.this.mShellInterface.createExternalInterfaces(bundle);
                    try {
                        Log.d("OverviewProxyService", "OverviewProxyService connected, initializing overview proxy");
                        ((IOverviewProxy.Stub.Proxy) OverviewProxyService.this.mOverviewProxy).onInitialize(bundle);
                    } catch (RemoteException e) {
                        OverviewProxyService.this.mCurrentBoundedUserId = -1;
                        Log.e("OverviewProxyService", "Failed to call onInitialize()", e);
                    }
                    OverviewProxyService overviewProxyService4 = OverviewProxyService.this;
                    IOverviewProxy iOverviewProxy = overviewProxyService4.mOverviewProxy;
                    if (iOverviewProxy != null && (region = overviewProxyService4.mActiveNavBarRegion) != null) {
                        try {
                            ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onActiveNavBarRegionChanges(region);
                        } catch (RemoteException e2) {
                            Log.e("OverviewProxyService", "Failed to call onActiveNavBarRegionChanges()", e2);
                        }
                    }
                    OverviewProxyService overviewProxyService5 = OverviewProxyService.this;
                    Lazy lazy3 = overviewProxyService5.mNavBarControllerLazy;
                    NavigationBar defaultNavigationBar = ((NavigationBarControllerImpl) ((NavigationBarController) lazy3.get())).getDefaultNavigationBar();
                    NavigationBarView navigationBarView = ((NavigationBarControllerImpl) ((NavigationBarController) lazy3.get())).getNavigationBarView(overviewProxyService5.mContext.getDisplayId());
                    if (defaultNavigationBar != null) {
                        defaultNavigationBar.updateSystemUiStateFlags();
                    }
                    if (navigationBarView != null) {
                        navigationBarView.updateDisabledSystemUiStateFlags(overviewProxyService5.mSysUiState);
                    }
                    ((ShadeViewController) overviewProxyService5.mShadeViewControllerLazy.get()).updateSystemUiStateFlags();
                    NotificationShadeWindowController notificationShadeWindowController2 = overviewProxyService5.mStatusBarWinController;
                    if (notificationShadeWindowController2 != null) {
                        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController2).notifyStateChangedCallbacks();
                    }
                    OverviewProxyService overviewProxyService6 = OverviewProxyService.this;
                    long j = overviewProxyService6.mSysUiState.mFlags;
                    try {
                        IOverviewProxy iOverviewProxy2 = overviewProxyService6.mOverviewProxy;
                        if (iOverviewProxy2 != null) {
                            ((IOverviewProxy.Stub.Proxy) iOverviewProxy2).onSystemUiStateChanged(j);
                        }
                    } catch (RemoteException e3) {
                        Log.e("OverviewProxyService", "Failed to notify sysui state change", e3);
                    }
                    OverviewProxyService.this.notifyConnectionChanged();
                } catch (RemoteException e4) {
                    Log.e("OverviewProxyService", "Lost connection to launcher service", e4);
                    OverviewProxyService.this.disconnectFromLauncherService("Lost connection to launcher service");
                    OverviewProxyService.this.retryConnectionWithBackoff();
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                Log.w("OverviewProxyService", "Service disconnected");
                OverviewProxyService.this.mCurrentBoundedUserId = -1;
            }
        };
        StatusBarWindowCallback statusBarWindowCallback = new StatusBarWindowCallback() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda2
            @Override // com.android.systemui.statusbar.phone.StatusBarWindowCallback
            public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.getClass();
                boolean z8 = false;
                boolean z9 = z && !z2;
                SysUiState sysUiState2 = overviewProxyService.mSysUiState;
                sysUiState2.setFlag(64L, z9);
                if (z && z2) {
                    z8 = true;
                }
                sysUiState2.setFlag(512L, z8);
                sysUiState2.setFlag(2147483648L, z3);
                sysUiState2.setFlag(8L, z4);
                sysUiState2.setFlag(2097152L, z5);
                sysUiState2.setFlag(134217728L, z7);
                sysUiState2.commitUpdate(overviewProxyService.mContext.getDisplayId());
            }
        };
        this.mStatusBarWindowCallback = statusBarWindowCallback;
        this.mOverviewServiceDeathRcpt = new IBinder.DeathRecipient() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda3
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                if (overviewProxyService.mInputFocusTransferStarted) {
                    overviewProxyService.mHandler.post(new OverviewProxyService$$ExternalSyntheticLambda0(overviewProxyService, 2));
                }
                overviewProxyService.startConnectionToCurrentUser();
            }
        };
        AnonymousClass5 anonymousClass5 = new AnonymousClass5();
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.recents.OverviewProxyService.6
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.mConnectionBackoffAttempts = 0;
                overviewProxyService.internalConnectToCurrentUser("User changed");
            }
        };
        this.mUserChangedCallback = callback;
        ScreenLifecycle.Observer observer = new ScreenLifecycle.Observer() { // from class: com.android.systemui.recents.OverviewProxyService.8
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurningOff() {
                try {
                    IOverviewProxy iOverviewProxy = OverviewProxyService.this.mOverviewProxy;
                    if (iOverviewProxy != null) {
                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onScreenTurningOff();
                    } else {
                        Log.e("OverviewProxyService", "Failed to get overview proxy for screen turning off event.");
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to call onScreenTurningOff()", e);
                }
            }
        };
        WakefulnessLifecycle.Observer observer2 = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.recents.OverviewProxyService.9
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                SysUiState sysUiState2 = overviewProxyService.mSysUiState;
                sysUiState2.setFlag(268435456L, false);
                sysUiState2.setFlag(536870912L, false);
                sysUiState2.commitUpdate(overviewProxyService.mContext.getDisplayId());
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                SysUiState sysUiState2 = overviewProxyService.mSysUiState;
                sysUiState2.setFlag(268435456L, true);
                sysUiState2.setFlag(536870912L, false);
                sysUiState2.commitUpdate(overviewProxyService.mContext.getDisplayId());
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                SysUiState sysUiState2 = overviewProxyService.mSysUiState;
                sysUiState2.setFlag(268435456L, false);
                sysUiState2.setFlag(536870912L, true);
                sysUiState2.commitUpdate(overviewProxyService.mContext.getDisplayId());
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                SysUiState sysUiState2 = overviewProxyService.mSysUiState;
                sysUiState2.setFlag(268435456L, true);
                sysUiState2.setFlag(536870912L, true);
                sysUiState2.commitUpdate(overviewProxyService.mContext.getDisplayId());
            }
        };
        boolean z = !Process.myUserHandle().equals(UserHandle.SYSTEM);
        this.mIsNonPrimaryUser = z;
        if (z) {
            Log.wtf("OverviewProxyService", "Unexpected initialization for non-primary user", new Throwable());
            Log.e("OverviewProxyService", "OPS not initialized for non-primary user, just return");
            return;
        }
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mShellInterface = shellInterface;
        this.mShadeViewControllerLazy = lazy2;
        this.mHandler = new Handler();
        this.mNavBarControllerLazy = lazy;
        this.mStatusBarWinController = notificationShadeWindowController;
        this.mUserTracker = userTracker;
        this.mConnectionBackoffAttempts = 0;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getString(R.string.ext_media_status_mounted));
        this.mRecentsComponentName = unflattenFromString;
        this.mQuickStepIntent = new Intent(ACTION_QUICKSTEP).setPackage(unflattenFromString.getPackageName());
        this.mSysUiState = sysUiState;
        sysUiState.addCallback(new SysUiState.SysUiStateCallback() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda4
            @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
            public final void onSystemUiStateChanged(long j) {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.getClass();
                try {
                    IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
                    if (iOverviewProxy != null) {
                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onSystemUiStateChanged(j);
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to notify sysui state change", e);
                }
            }
        });
        this.mUiEventLogger = uiEventLogger;
        this.mDisplayTracker = displayTracker;
        this.mUnfoldTransitionProgressForwarder = optional;
        this.mFgsManagerController = fgsManagerController;
        this.mMainHandler = handler;
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        com.android.systemui.Flags.keyguardWmStateRefactor();
        this.mSysuiUnlockAnimationController = keyguardUnlockAnimationController;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "OverviewProxyService", this);
        if (BasicRune.SEARCLE) {
            this.mSearcleManager = searcleManager;
        }
        this.mNavBarMode = navigationModeController.addListener(this);
        if (BasicRune.NAVBAR_GESTURE) {
            NavBarStore navBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
            this.mNavBarStore = navBarStore;
            this.mNavBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(context.getDisplayId());
        }
        this.mSamsungScreenPinningRequest = samsungScreenPinningRequest;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(unflattenFromString.getPackageName(), 0);
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        context.registerReceiver(r10, intentFilter);
        if (Flags.keyguardPrivateNotifications()) {
            broadcastDispatcher.registerReceiver(broadcastReceiver, new IntentFilter("android.intent.action.USER_UNLOCKED"), null, UserHandle.ALL);
        }
        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).registerCallback(statusBarWindowCallback);
        this.mScreenshotHelper = new ScreenshotHelper(context);
        commandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.recents.OverviewProxyService.7
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void moveFocusedTaskToStageSplit(int i, boolean z2) {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                if (overviewProxyService.mOverviewProxy != null) {
                    try {
                        if (!DesktopModeStatus.canEnterDesktopMode(overviewProxyService.mContext) || (sysUiState.mFlags & 67108864) == 0) {
                            ((IOverviewProxy.Stub.Proxy) overviewProxyService.mOverviewProxy).enterStageSplitFromRunningApp(z2);
                        }
                    } catch (RemoteException unused) {
                        Log.w("OverviewProxyService", "Unable to enter stage split from the current running app");
                    }
                }
            }
        });
        this.mCommandQueue = commandQueue;
        ((UserTrackerImpl) userTracker).addCallback(callback, executor);
        screenLifecycle.addObserver(observer);
        wakefulnessLifecycle.addObserver(observer2);
        updateEnabledAndBinding();
        assistUtils.registerVoiceInteractionSessionListener(anonymousClass5);
    }

    public final void disconnectFromLauncherService(String str) {
        Log.d("OverviewProxyService", "disconnectFromLauncherService bound?: " + this.mBound + " currentProxy: " + this.mOverviewProxy + " disconnectReason: " + str, new Throwable());
        if (this.mBound) {
            this.mContext.unbindService(this.mOverviewServiceConnection);
            this.mBound = false;
        }
        IOverviewProxy iOverviewProxy = this.mOverviewProxy;
        if (iOverviewProxy != null) {
            iOverviewProxy.asBinder().unlinkToDeath(this.mOverviewServiceDeathRcpt, 0);
            this.mOverviewProxy = null;
            notifyConnectionChanged();
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("OverviewProxyService state:");
        printWriter.print("  isConnected=");
        printWriter.println(this.mOverviewProxy != null);
        printWriter.print("  mIsEnabled=");
        printWriter.println(this.mIsEnabled);
        printWriter.print("  mRecentsComponentName=");
        printWriter.println(this.mRecentsComponentName);
        printWriter.print("  mQuickStepIntent=");
        printWriter.println(this.mQuickStepIntent);
        printWriter.print("  mBound=");
        printWriter.println(this.mBound);
        printWriter.print("  mCurrentBoundedUserId=");
        printWriter.println(this.mCurrentBoundedUserId);
        printWriter.print("  mConnectionBackoffAttempts=");
        printWriter.println(this.mConnectionBackoffAttempts);
        printWriter.print("  mInputFocusTransferStarted=");
        printWriter.println(this.mInputFocusTransferStarted);
        printWriter.print("  mInputFocusTransferStartY=");
        printWriter.println(this.mInputFocusTransferStartY);
        printWriter.print("  mInputFocusTransferStartMillis=");
        printWriter.println(this.mInputFocusTransferStartMillis);
        printWriter.print("  mActiveNavBarRegion=");
        printWriter.println(this.mActiveNavBarRegion);
        printWriter.print("  mNavBarMode=");
        printWriter.println(this.mNavBarMode);
        this.mSysUiState.dump(printWriter, strArr);
    }

    public final void internalConnectToCurrentUser(String str) {
        if (this.mIsNonPrimaryUser) {
            Log.w("OverviewProxyService", "Skipping connection to overview service due to non-primary user caller");
            return;
        }
        disconnectFromLauncherService(str);
        if (this.mIsEnabled) {
            Handler handler = this.mHandler;
            handler.removeCallbacks(this.mConnectionRunnable);
            try {
                this.mBound = this.mContext.bindServiceAsUser(this.mQuickStepIntent, this.mOverviewServiceConnection, 33554433, UserHandle.of(((UserTrackerImpl) this.mUserTracker).getUserId()));
            } catch (SecurityException e) {
                Log.e("OverviewProxyService", "Unable to bind because of security error", e);
            }
            if (this.mBound) {
                handler.postDelayed(this.mDeferredConnectionCallback, 5000L);
            } else {
                retryConnectionWithBackoff();
            }
        }
    }

    public final void notifyConnectionChanged() {
        for (int size = this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
            ((OverviewProxyListener) this.mConnectionCallbacks.get(size)).onConnectionChanged(this.mOverviewProxy != null);
        }
    }

    public final void onNavButtonsDarkIntensityChanged(float f) {
        try {
            IOverviewProxy iOverviewProxy = this.mOverviewProxy;
            if (iOverviewProxy != null) {
                ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onNavButtonsDarkIntensityChanged(f);
            } else {
                Log.e("OverviewProxyService", "Failed to get overview proxy to update nav buttons dark intensity");
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to call onNavButtonsDarkIntensityChanged()", e);
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ((ArrayList) this.mConnectionCallbacks).remove((OverviewProxyListener) obj);
    }

    public final void retryConnectionWithBackoff() {
        Handler handler = this.mHandler;
        OverviewProxyService$$ExternalSyntheticLambda0 overviewProxyService$$ExternalSyntheticLambda0 = this.mConnectionRunnable;
        if (handler.hasCallbacks(overviewProxyService$$ExternalSyntheticLambda0)) {
            return;
        }
        long min = (long) Math.min(Math.scalb(1000.0f, this.mConnectionBackoffAttempts), 600000.0f);
        handler.postDelayed(overviewProxyService$$ExternalSyntheticLambda0, min);
        this.mConnectionBackoffAttempts++;
        Log.w("OverviewProxyService", "Failed to connect on attempt " + this.mConnectionBackoffAttempts + " will try again in " + min + "ms");
    }

    public final boolean shouldShowSwipeUpUI() {
        if (this.mIsEnabled) {
            int i = this.mNavBarMode;
            boolean z = com.android.systemui.shared.system.QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN;
            if (i != 0) {
                return true;
            }
        }
        return false;
    }

    public void shutdownForTest() {
        this.mContext.unregisterReceiver(this.mLauncherStateChangedReceiver);
        this.mIsEnabled = false;
        this.mHandler.removeCallbacks(this.mConnectionRunnable);
        disconnectFromLauncherService("Shutdown for test");
    }

    public final void startConnectionToCurrentUser() {
        Handler handler = this.mHandler;
        if (handler.getLooper() != Looper.myLooper()) {
            handler.post(this.mConnectionRunnable);
        } else {
            internalConnectToCurrentUser("startConnectionToCurrentUser");
        }
    }

    public final void updateEnabledAndBinding() {
        this.mIsEnabled = this.mContext.getPackageManager().resolveServiceAsUser(this.mQuickStepIntent, QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING, ((UserTrackerImpl) this.mUserTracker).getUserId()) != null;
        startConnectionToCurrentUser();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(OverviewProxyListener overviewProxyListener) {
        if (!this.mConnectionCallbacks.contains(overviewProxyListener)) {
            this.mConnectionCallbacks.add(overviewProxyListener);
        }
        overviewProxyListener.onConnectionChanged(this.mOverviewProxy != null);
    }

    /* renamed from: com.android.systemui.recents.OverviewProxyService$5, reason: invalid class name */
    public final class AnonymousClass5 extends IVoiceInteractionSessionListener.Stub {
        public AnonymousClass5() {
        }

        public final void onVoiceSessionWindowVisibilityChanged(boolean z) {
            OverviewProxyService.this.mContext.getMainExecutor().execute(new OverviewProxyService$5$$ExternalSyntheticLambda0(this, z));
        }

        public final void onVoiceSessionHidden() {
        }

        public final void onVoiceSessionShown() {
        }

        public final void onSetUiHints(Bundle bundle) {
        }
    }

    public interface OverviewProxyListener {
        default void onInitializedTaskbarNavigationBar() {
        }

        default void onOverviewShown() {
        }

        default void onTaskbarSPluginButtonClicked() {
        }

        default void onToggleRecentApps() {
        }

        default void onAssistantGestureCompletion(float f) {
        }

        default void onAssistantProgress(float f) {
        }

        default void onConnectionChanged(boolean z) {
        }

        default void onHomeRotationEnabled(boolean z) {
        }

        default void onPrioritizedRotation(int i) {
        }

        default void onTaskbarAutohideSuspend(boolean z) {
        }

        default void setAssistantOverridesRequested(int[] iArr) {
        }

        default void startAssistant(Bundle bundle) {
        }

        default void onTaskbarStatusUpdated$1(boolean z, boolean z2) {
        }

        default void animateNavBarLongPress(boolean z, boolean z2, long j) {
        }

        default void setOverrideHomeButtonLongPress(float f, boolean z, long j) {
        }
    }
}
