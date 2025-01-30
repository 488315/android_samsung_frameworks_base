package com.android.systemui.recents;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
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
import android.view.SurfaceControl;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.util.ScreenshotHelper;
import com.android.p038wm.shell.sysui.ShellInterface;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.p016qs.FgsManagerController;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.recents.ISystemUiProxy;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.unfold.progress.UnfoldTransitionProgressForwarder;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OverviewProxyService implements CallbackController, NavigationModeController.ModeChangedListener, Dumpable {
    static final String ACTION_QUICKSTEP = "android.intent.action.QUICKSTEP_SERVICE";
    public Region mActiveNavBarRegion;
    public boolean mBound;
    public final Lazy mCentralSurfacesOptionalLazy;
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
    public CountDownLatch mLatchForOnUserChanging;
    public final C23092 mLauncherStateChangedReceiver;
    public final Executor mMainExecutor;
    public final Lazy mNavBarControllerLazy;
    public int mNavBarMode;
    public final NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public SurfaceControl mNavigationBarSurface;
    public IOverviewProxy mOverviewProxy;
    public final ServiceConnectionC23103 mOverviewServiceConnection;
    public final OverviewProxyService$$ExternalSyntheticLambda2 mOverviewServiceDeathRcpt;
    public final Intent mQuickStepIntent;
    public final ComponentName mRecentsComponentName;
    public final C23147 mScreenLifecycleObserver;
    public final ScreenshotHelper mScreenshotHelper;
    public final SearcleManager mSearcleManager;
    public final ShellInterface mShellInterface;
    public final NotificationShadeWindowController mStatusBarWinController;
    public final StatusBarWindowCallback mStatusBarWindowCallback;
    public final boolean mSupportsRoundedCornersOnWindows;
    public final SysUiState mSysUiState;
    public final KeyguardUnlockAnimationController mSysuiUnlockAnimationController;
    public final UiEventLogger mUiEventLogger;
    public final Optional mUnfoldTransitionProgressForwarder;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final C23114 mVoiceInteractionSessionListener;
    public final C23158 mWakefulnessLifecycleObserver;
    public final float mWindowCornerRadius;
    public final OverviewProxyService$$ExternalSyntheticLambda0 mConnectionRunnable = new OverviewProxyService$$ExternalSyntheticLambda0(this, 0);
    public final List mConnectionCallbacks = new ArrayList();
    public int mCurrentBoundedUserId = -1;
    public ISystemUiProxy mSysUiProxy = new BinderC23081();
    public final OverviewProxyService$$ExternalSyntheticLambda0 mDeferredConnectionCallback = new OverviewProxyService$$ExternalSyntheticLambda0(this, 1);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.recents.OverviewProxyService$1 */
    public final class BinderC23081 extends ISystemUiProxy.Stub {
        public final OverviewProxyService$1$$ExternalSyntheticLambda4 mOnNumberOfPackagesChangedListener = new FgsManagerController.OnNumberOfPackagesChangedListener() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda4
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
        public final Handler mMainHandler = (Handler) Dependency.get(Dependency.MAIN_HANDLER);

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda4] */
        public BinderC23081() {
        }

        public final void sendEvent(int i) {
            long uptimeMillis = SystemClock.uptimeMillis();
            KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 72, 257);
            keyEvent.setDisplayId(OverviewProxyService.this.mContext.getDisplay().getDisplayId());
            InputManagerGlobal.getInstance().injectInputEvent(keyEvent, 0);
        }

        public final boolean verifyCaller(String str) {
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0950x8906c950.m92m("verifyCaller reason : ", str, " callerId : ", identifier, " mCurrentBoundedUserId : "), OverviewProxyService.this.mCurrentBoundedUserId, "OverviewProxyService");
            if (identifier == OverviewProxyService.this.mCurrentBoundedUserId) {
                return true;
            }
            Log.w("OverviewProxyService", "Launcher called sysui with invalid user: " + identifier + ", reason: " + str);
            return false;
        }

        public final void verifyCallerAndClearCallingIdentityPostMain(Runnable runnable, String str) {
            if (verifyCaller(str)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    OverviewProxyService.this.mHandler.post(runnable);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r12v0, types: [com.android.systemui.recents.OverviewProxyService$7, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v0, types: [com.android.systemui.recents.OverviewProxyService$8, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v5, types: [android.content.BroadcastReceiver, com.android.systemui.recents.OverviewProxyService$2] */
    /* JADX WARN: Type inference failed for: r9v1, types: [com.android.systemui.recents.OverviewProxyService$3] */
    public OverviewProxyService(Context context, Executor executor, CommandQueue commandQueue, ShellInterface shellInterface, Lazy lazy, Lazy lazy2, NavigationModeController navigationModeController, NotificationShadeWindowController notificationShadeWindowController, SysUiState sysUiState, UserTracker userTracker, ScreenLifecycle screenLifecycle, WakefulnessLifecycle wakefulnessLifecycle, UiEventLogger uiEventLogger, DisplayTracker displayTracker, KeyguardUnlockAnimationController keyguardUnlockAnimationController, AssistUtils assistUtils, DumpManager dumpManager, Optional<UnfoldTransitionProgressForwarder> optional, FgsManagerController fgsManagerController, SearcleManager searcleManager) {
        this.mNavBarMode = 0;
        ?? r7 = new BroadcastReceiver() { // from class: com.android.systemui.recents.OverviewProxyService.2
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
                String str = context2.getPackageManager().resolveService(new Intent(OverviewProxyService.ACTION_QUICKSTEP), 0).serviceInfo.name;
                for (String str2 : stringArrayExtra) {
                    if (str.equals(str2)) {
                        Log.i("OverviewProxyService", "Rebinding for component [" + str2 + "] change");
                        OverviewProxyService.this.updateEnabledAndBinding();
                        return;
                    }
                }
            }
        };
        this.mLauncherStateChangedReceiver = r7;
        this.mOverviewServiceConnection = new ServiceConnection() { // from class: com.android.systemui.recents.OverviewProxyService.3
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
                    Bundle bundle = new Bundle();
                    ISystemUiProxy.Stub stub = (ISystemUiProxy.Stub) OverviewProxyService.this.mSysUiProxy;
                    stub.getClass();
                    bundle.putBinder(QuickStepContract.KEY_EXTRA_SYSUI_PROXY, stub);
                    bundle.putFloat(QuickStepContract.KEY_EXTRA_WINDOW_CORNER_RADIUS, OverviewProxyService.this.mWindowCornerRadius);
                    bundle.putBoolean(QuickStepContract.KEY_EXTRA_SUPPORTS_WINDOW_CORNERS, OverviewProxyService.this.mSupportsRoundedCornersOnWindows);
                    KeyguardUnlockAnimationController keyguardUnlockAnimationController2 = OverviewProxyService.this.mSysuiUnlockAnimationController;
                    keyguardUnlockAnimationController2.getClass();
                    bundle.putBinder("unlock_animation", keyguardUnlockAnimationController2);
                    OverviewProxyService.this.mUnfoldTransitionProgressForwarder.ifPresent(new OverviewProxyService$$ExternalSyntheticLambda4(bundle, 1));
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
                    OverviewProxyService.this.dispatchNavigationBarSurface();
                    OverviewProxyService overviewProxyService5 = OverviewProxyService.this;
                    Lazy lazy3 = overviewProxyService5.mNavBarControllerLazy;
                    NavigationBar defaultNavigationBar = ((NavigationBarController) lazy3.get()).getDefaultNavigationBar();
                    NavigationBarView navigationBarView = ((NavigationBarController) lazy3.get()).getNavigationBarView(overviewProxyService5.mContext.getDisplayId());
                    ShadeViewController shadeViewController = (ShadeViewController) ((Optional) overviewProxyService5.mCentralSurfacesOptionalLazy.get()).map(new OverviewProxyService$$ExternalSyntheticLambda5()).orElse(null);
                    if (defaultNavigationBar != null) {
                        defaultNavigationBar.updateSystemUiStateFlags();
                    }
                    if (navigationBarView != null) {
                        navigationBarView.updateDisabledSystemUiStateFlags(overviewProxyService5.mSysUiState);
                    }
                    if (shadeViewController != null) {
                        ((NotificationPanelViewController) shadeViewController).updateSystemUiStateFlags();
                    }
                    NotificationShadeWindowController notificationShadeWindowController2 = overviewProxyService5.mStatusBarWinController;
                    if (notificationShadeWindowController2 != null) {
                        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController2).notifyStateChangedCallbacks();
                    }
                    if (BasicRune.NAVBAR_ENABLED) {
                        ((NavBarStoreImpl) overviewProxyService5.mNavBarStore).handleEvent(overviewProxyService5, new EventTypeFactory.EventType.OnUpdateSysUiStateFlag());
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
                    CountDownLatch countDownLatch = OverviewProxyService.this.mLatchForOnUserChanging;
                    if (countDownLatch != null) {
                        countDownLatch.countDown();
                        OverviewProxyService.this.mLatchForOnUserChanging = null;
                    }
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
        StatusBarWindowCallback statusBarWindowCallback = new StatusBarWindowCallback() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.phone.StatusBarWindowCallback
            public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.getClass();
                boolean z8 = z && !z2;
                SysUiState sysUiState2 = overviewProxyService.mSysUiState;
                sysUiState2.setFlag(64L, z8);
                sysUiState2.setFlag(512L, z && z2);
                sysUiState2.setFlag(2147483648L, z3);
                sysUiState2.setFlag(8L, z4);
                sysUiState2.setFlag(2097152L, z5);
                sysUiState2.setFlag(134217728L, z7);
                sysUiState2.commitUpdate(overviewProxyService.mContext.getDisplayId());
            }
        };
        this.mStatusBarWindowCallback = statusBarWindowCallback;
        this.mOverviewServiceDeathRcpt = new IBinder.DeathRecipient() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda2
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                if (overviewProxyService.mInputFocusTransferStarted) {
                    overviewProxyService.mHandler.post(new OverviewProxyService$$ExternalSyntheticLambda0(overviewProxyService, 2));
                }
                overviewProxyService.startConnectionToCurrentUser();
            }
        };
        C23114 c23114 = new C23114();
        this.mVoiceInteractionSessionListener = c23114;
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.recents.OverviewProxyService.5
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanging(int i, Context context2, CountDownLatch countDownLatch) {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                overviewProxyService.mConnectionBackoffAttempts = 0;
                overviewProxyService.mLatchForOnUserChanging = countDownLatch;
                overviewProxyService.internalConnectToCurrentUser("User changed");
            }
        };
        this.mUserChangedCallback = callback;
        ?? r12 = new ScreenLifecycle.Observer() { // from class: com.android.systemui.recents.OverviewProxyService.7
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOn() {
                try {
                    IOverviewProxy iOverviewProxy = OverviewProxyService.this.mOverviewProxy;
                    if (iOverviewProxy != null) {
                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onScreenTurnedOn();
                    } else {
                        Log.e("OverviewProxyService", "Failed to get overview proxy for screen turned on event.");
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to call onScreenTurnedOn()", e);
                }
            }

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

            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurningOn() {
                try {
                    IOverviewProxy iOverviewProxy = OverviewProxyService.this.mOverviewProxy;
                    if (iOverviewProxy != null) {
                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onScreenTurningOn();
                    } else {
                        Log.e("OverviewProxyService", "Failed to get overview proxy for screen turning on event.");
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to call onScreenTurningOn()", e);
                }
            }
        };
        this.mScreenLifecycleObserver = r12;
        ?? r13 = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.recents.OverviewProxyService.8
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
        this.mWakefulnessLifecycleObserver = r13;
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.e("OverviewProxyService", "Unexpected initialization for non-primary user", new Throwable());
            Log.e("OverviewProxyService", "OPS not initialized for non-primary user, just return");
            return;
        }
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mShellInterface = shellInterface;
        this.mCentralSurfacesOptionalLazy = lazy2;
        this.mHandler = new Handler();
        this.mNavBarControllerLazy = lazy;
        this.mStatusBarWinController = notificationShadeWindowController;
        this.mUserTracker = userTracker;
        this.mConnectionBackoffAttempts = 0;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getString(R.string.etws_primary_default_message_test));
        this.mRecentsComponentName = unflattenFromString;
        this.mQuickStepIntent = new Intent(ACTION_QUICKSTEP).setPackage(unflattenFromString.getPackageName());
        this.mWindowCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mSupportsRoundedCornersOnWindows = ScreenDecorationsUtils.supportsRoundedCornersOnWindows(context.getResources());
        this.mSysUiState = sysUiState;
        sysUiState.addCallback(new SysUiState.SysUiStateCallback() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda3
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
        this.mSysuiUnlockAnimationController = keyguardUnlockAnimationController;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "OverviewProxyService", this);
        this.mNavBarMode = navigationModeController.addListener(this);
        if (BasicRune.NAVBAR_GESTURE) {
            NavBarStore navBarStore = (NavBarStore) Dependency.get(NavBarStore.class);
            this.mNavBarStore = navBarStore;
            this.mNavBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(context.getDisplayId());
        }
        if (BasicRune.SEARCLE) {
            this.mSearcleManager = searcleManager;
        }
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(unflattenFromString.getPackageName(), 0);
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        context.registerReceiver(r7, intentFilter);
        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).registerCallback(statusBarWindowCallback);
        this.mScreenshotHelper = new ScreenshotHelper(context);
        commandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.recents.OverviewProxyService.6
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void enterStageSplitFromRunningApp(boolean z) {
                IOverviewProxy iOverviewProxy = OverviewProxyService.this.mOverviewProxy;
                if (iOverviewProxy != null) {
                    try {
                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).enterStageSplitFromRunningApp(z);
                    } catch (RemoteException unused) {
                        Log.w("OverviewProxyService", "Unable to enter stage split from the current running app");
                    }
                }
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onTracingStateChanged(boolean z) {
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                SysUiState sysUiState2 = overviewProxyService.mSysUiState;
                sysUiState2.setFlag(4096L, z);
                sysUiState2.commitUpdate(overviewProxyService.mContext.getDisplayId());
            }
        });
        this.mCommandQueue = commandQueue;
        ((UserTrackerImpl) userTracker).addCallback(callback, executor);
        screenLifecycle.addObserver(r12);
        wakefulnessLifecycle.addObserver(r13);
        updateEnabledAndBinding();
        assistUtils.registerVoiceInteractionSessionListener(c23114);
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

    public final void dispatchNavigationBarSurface() {
        try {
            if (this.mOverviewProxy != null) {
                SurfaceControl surfaceControl = this.mNavigationBarSurface;
                if (surfaceControl != null && !surfaceControl.isValid()) {
                    this.mNavigationBarSurface = null;
                }
                ((IOverviewProxy.Stub.Proxy) this.mOverviewProxy).onNavigationBarSurface(this.mNavigationBarSurface);
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to notify back action", e);
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
        printWriter.print("  mWindowCornerRadius=");
        printWriter.println(this.mWindowCornerRadius);
        printWriter.print("  mSupportsRoundedCornersOnWindows=");
        printWriter.println(this.mSupportsRoundedCornersOnWindows);
        printWriter.print("  mActiveNavBarRegion=");
        printWriter.println(this.mActiveNavBarRegion);
        printWriter.print("  mNavigationBarSurface=");
        printWriter.println(this.mNavigationBarSurface);
        printWriter.print("  mNavBarMode=");
        printWriter.println(this.mNavBarMode);
        this.mSysUiState.dump(printWriter, strArr);
    }

    public final void internalConnectToCurrentUser(String str) {
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
        List list = this.mConnectionCallbacks;
        for (int size = list.size() - 1; size >= 0; size--) {
            ((OverviewProxyListener) list.get(size)).onConnectionChanged(this.mOverviewProxy != null);
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
        if (!this.mIsEnabled) {
            return false;
        }
        int i = this.mNavBarMode;
        boolean z = com.android.systemui.shared.system.QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN;
        return !(i == 0);
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
        List list = this.mConnectionCallbacks;
        if (!list.contains(overviewProxyListener)) {
            list.add(overviewProxyListener);
        }
        overviewProxyListener.onConnectionChanged(this.mOverviewProxy != null);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.recents.OverviewProxyService$4 */
    public final class C23114 extends IVoiceInteractionSessionListener.Stub {
        public C23114() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onVoiceSessionWindowVisibilityChanged(boolean z) {
            OverviewProxyService.this.mContext.getMainExecutor().execute(new OverviewProxyService$1$$ExternalSyntheticLambda5(this, z, 4));
        }

        public final void onSetUiHints(Bundle bundle) {
        }

        public final void onVoiceSessionHidden() {
        }

        public final void onVoiceSessionShown() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OverviewProxyListener {
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

        default void startAssistant(Bundle bundle) {
        }

        default void onInitializedTaskbarNavigationBar() {
        }

        default void onOverviewShown() {
        }

        default void onTaskbarSPluginButtonClicked() {
        }

        default void onToggleRecentApps() {
        }

        default void onTaskbarStatusUpdated(boolean z, boolean z2) {
        }
    }
}
