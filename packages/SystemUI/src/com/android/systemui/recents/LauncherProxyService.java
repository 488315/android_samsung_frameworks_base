package com.android.systemui.recents;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.view.KeyEvent;
import com.android.app.displaylib.PerDisplayRepository;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ScreenshotHelper;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.TaskbarIndicatorController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardWmStateRefactor;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.ui.view.InWindowLauncherUnlockAnimationManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.popup.SamsungScreenPinningRequest;
import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.shared.recents.ILauncherProxy;
import com.android.systemui.shared.recents.ISystemUiProxy;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.unfold.progress.UnfoldTransitionProgressForwarder;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.sysui.ShellInterface;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public class LauncherProxyService implements CallbackController, NavigationModeController.ModeChangedListener, Dumpable {
    static final String ACTION_QUICKSTEP = "android.intent.action.QUICKSTEP_SERVICE";
    public Region mActiveNavBarRegion;
    public final BackAnimationController.BackAnimationImpl mBackAnimation;
    public boolean mBound;
    public final CommandQueue mCommandQueue;
    public int mConnectionBackoffAttempts;
    public final Context mContext;
    public final SysUiState mDefaultDisplaySysUIState;
    public final DisplayRepository mDisplayRepository;
    public final DisplayTracker mDisplayTracker;
    public final FgsManagerController mFgsManagerController;
    public final Handler mHandler;
    public long mInputFocusTransferStartMillis;
    public float mInputFocusTransferStartY;
    public boolean mInputFocusTransferStarted;
    public boolean mIsEnabled;
    public final boolean mIsSystemOrVisibleBgUser;
    public ILauncherProxy mLauncherProxy;
    public final AnonymousClass4 mLauncherServiceConnection;
    public final LauncherProxyService$$ExternalSyntheticLambda4 mLauncherServiceDeathRcpt;
    public final AnonymousClass3 mLauncherStateChangedReceiver;
    public final Executor mMainExecutor;
    public final Handler mMainHandler;
    public final Lazy mNavBarControllerLazy;
    public int mNavBarMode;
    public final NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public final PerDisplayRepository mPerDisplaySysUiStateRepository;
    public final QSCMainViewController mQsCustomizerContoller;
    public final Intent mQuickStepIntent;
    public final ComponentName mRecentsComponentName;
    public final SamsungScreenPinningRequest mSamsungScreenPinningRequest;
    public final AnonymousClass10 mScreenLifecycleObserver;
    public final ScreenshotHelper mScreenshotHelper;
    public final SearcleManager mSearcleManager;
    public final Lazy mShadeViewControllerLazy;
    public final ShellInterface mShellInterface;
    public final NotificationShadeWindowController mStatusBarWinController;
    public final StatusBarWindowCallback mStatusBarWindowCallback;
    public final AnonymousClass7 mSysUiStateCallback;
    public final KeyguardUnlockAnimationController mSysuiUnlockAnimationController;
    public final TaskbarIndicatorController mTaskbarIndicatorController;
    public final UiEventLogger mUiEventLogger;
    public final Optional mUnfoldTransitionProgressForwarder;
    public final UserTracker.Callback mUserChangedCallback;
    public final AnonymousClass2 mUserEventReceiver;
    public final UserTracker mUserTracker;
    public final AnonymousClass5 mVoiceInteractionSessionListener;
    public final AnonymousClass11 mWakefulnessLifecycleObserver;
    public final LauncherProxyService$$ExternalSyntheticLambda0 mConnectionRunnable = new LauncherProxyService$$ExternalSyntheticLambda0(this, 0);
    public final List mConnectionCallbacks = new ArrayList();
    public boolean mIsPrevServiceCleanedUp = true;
    public int mCurrentBoundedUserId = -1;
    public ISystemUiProxy mSysUiProxy = new AnonymousClass1();
    public final LauncherProxyService$$ExternalSyntheticLambda0 mDeferredConnectionCallback = new LauncherProxyService$$ExternalSyntheticLambda0(this, 1);
    public final LauncherProxyService$$ExternalSyntheticLambda0 mDeferredBindAfterTimedOutCleanup = new LauncherProxyService$$ExternalSyntheticLambda0(this, 2);

    /* renamed from: com.android.systemui.recents.LauncherProxyService$1, reason: invalid class name */
    public class AnonymousClass1 extends ISystemUiProxy.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final LauncherProxyService$1$$ExternalSyntheticLambda27 mOnNumberOfPackagesChangedListener = new FgsManagerController.OnNumberOfPackagesChangedListener() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda27
            @Override // com.android.systemui.qs.FgsManagerController.OnNumberOfPackagesChangedListener
            public final void onNumberOfPackagesChanged(int i) {
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                launcherProxyService.getClass();
                try {
                    ILauncherProxy iLauncherProxy = launcherProxyService.mLauncherProxy;
                    if (iLauncherProxy != null) {
                        ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onNumberOfVisibleFgsChanged(i);
                    }
                } catch (RemoteException e) {
                    Log.e("LauncherProxyService", "Failed to call onNumberOfVisibleFgsChanged().", e);
                }
            }
        };

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda27] */
        public AnonymousClass1() {
        }

        public final void verifyCallerAndClearCallingIdentity(String str, Supplier supplier) {
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            if (identifier == LauncherProxyService.this.mCurrentBoundedUserId) {
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    supplier.get();
                    return;
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
            Log.w("LauncherProxyService", "Launcher called sysui with invalid user: " + identifier + ", reason: " + str);
        }

        public final void verifyCallerAndClearCallingIdentityPostMain(final Runnable runnable, String str) {
            verifyCallerAndClearCallingIdentity(str, new Supplier() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda36
                @Override // java.util.function.Supplier
                public final Object get() {
                    LauncherProxyService.AnonymousClass1 anonymousClass1 = this.f$0;
                    return Boolean.valueOf(LauncherProxyService.this.mHandler.post(runnable));
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1, types: [android.content.BroadcastReceiver, com.android.systemui.recents.LauncherProxyService$3] */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.recents.LauncherProxyService$4] */
    /* JADX WARN: Type inference failed for: r12v0, types: [com.android.systemui.recents.LauncherProxyService$$ExternalSyntheticLambda4] */
    /* JADX WARN: Type inference failed for: r14v0, types: [com.android.systemui.model.SysUiState$SysUiStateCallback, com.android.systemui.recents.LauncherProxyService$7] */
    /* JADX WARN: Type inference failed for: r15v0, types: [com.android.systemui.recents.LauncherProxyService$10, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v3, types: [com.android.systemui.recents.LauncherProxyService$11] */
    /* JADX WARN: Type inference failed for: r9v4, types: [android.content.BroadcastReceiver, com.android.systemui.recents.LauncherProxyService$2] */
    public LauncherProxyService(Context context, Executor executor, CommandQueue commandQueue, ShellInterface shellInterface, Lazy lazy, Lazy lazy2, ScreenPinningRequest screenPinningRequest, NavigationModeController navigationModeController, NotificationShadeWindowController notificationShadeWindowController, PerDisplayRepository perDisplayRepository, Provider provider, Provider provider2, UserTracker userTracker, UserManager userManager, WakefulnessLifecycle wakefulnessLifecycle, UiEventLogger uiEventLogger, DisplayTracker displayTracker, KeyguardUnlockAnimationController keyguardUnlockAnimationController, InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager, AssistUtils assistUtils, DumpManager dumpManager, Optional<UnfoldTransitionProgressForwarder> optional, BroadcastDispatcher broadcastDispatcher, Optional<BackAnimationController.BackAnimationImpl> optional2, ProcessWrapper processWrapper, DisplayRepository displayRepository, FgsManagerController fgsManagerController, Handler handler, SearcleManager searcleManager, SamsungScreenPinningRequest samsungScreenPinningRequest, ScreenLifecycle screenLifecycle, QSCMainViewController qSCMainViewController, TaskbarIndicatorController taskbarIndicatorController) {
        IVoiceInteractionSessionListener iVoiceInteractionSessionListener;
        Object obj;
        this.mNavBarMode = 0;
        ?? r9 = new BroadcastReceiver() { // from class: com.android.systemui.recents.LauncherProxyService.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (Objects.equals(intent.getAction(), "android.intent.action.USER_UNLOCKED")) {
                    LauncherProxyService launcherProxyService = LauncherProxyService.this;
                    if (launcherProxyService.mLauncherProxy == null) {
                        launcherProxyService.startConnectionToCurrentUser();
                    }
                }
            }
        };
        this.mUserEventReceiver = r9;
        ?? r10 = new BroadcastReceiver() { // from class: com.android.systemui.recents.LauncherProxyService.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (Objects.equals(intent.getAction(), "android.intent.action.PACKAGE_ADDED")) {
                    LauncherProxyService.this.updateEnabledAndBinding();
                    return;
                }
                String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                if (stringArrayExtra == null) {
                    return;
                }
                ResolveInfo resolveInfoResolveService = context2.getPackageManager().resolveService(new Intent(LauncherProxyService.ACTION_QUICKSTEP), 0);
                if (resolveInfoResolveService == null) {
                    return;
                }
                String str = resolveInfoResolveService.serviceInfo.name;
                for (String str2 : stringArrayExtra) {
                    if (str.equals(str2)) {
                        Log.i("LauncherProxyService", "Rebinding for component [" + str2 + "] change");
                        LauncherProxyService.this.updateEnabledAndBinding();
                        return;
                    }
                }
            }
        };
        this.mLauncherStateChangedReceiver = r10;
        this.mLauncherServiceConnection = new ServiceConnection() { // from class: com.android.systemui.recents.LauncherProxyService.4
            @Override // android.content.ServiceConnection
            public final void onBindingDied(ComponentName componentName) {
                Log.w("LauncherProxyService", "Binding died of '" + componentName + "', try reconnecting");
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                launcherProxyService.mCurrentBoundedUserId = -1;
                launcherProxyService.retryConnectionWithBackoff();
            }

            @Override // android.content.ServiceConnection
            public final void onNullBinding(ComponentName componentName) {
                Log.w("LauncherProxyService", "Null binding of '" + componentName + "', try reconnecting");
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                launcherProxyService.mCurrentBoundedUserId = -1;
                launcherProxyService.retryConnectionWithBackoff();
            }

            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
                Region region;
                Log.d("LauncherProxyService", "Launcher proxy service connected");
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                launcherProxyService.mConnectionBackoffAttempts = 0;
                launcherProxyService.mHandler.removeCallbacks(launcherProxyService.mDeferredConnectionCallback);
                try {
                    iBinder.linkToDeath(LauncherProxyService.this.mLauncherServiceDeathRcpt, 0);
                    LauncherProxyService launcherProxyService2 = LauncherProxyService.this;
                    launcherProxyService2.mCurrentBoundedUserId = ((UserTrackerImpl) launcherProxyService2.mUserTracker).getUserId();
                    LauncherProxyService launcherProxyService3 = LauncherProxyService.this;
                    int i = ILauncherProxy.Stub.$r8$clinit;
                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.shared.recents.ILauncherProxy");
                    launcherProxyService3.mLauncherProxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ILauncherProxy)) ? new ILauncherProxy.Stub.Proxy(iBinder) : (ILauncherProxy) iInterfaceQueryLocalInterface;
                    Bundle bundle = new Bundle();
                    QuickStepContract.addInterface(LauncherProxyService.this.mSysUiProxy, bundle);
                    QuickStepContract.addInterface(LauncherProxyService.this.mSysuiUnlockAnimationController, bundle);
                    QuickStepContract.addInterface((IInterface) LauncherProxyService.this.mUnfoldTransitionProgressForwarder.orElse(null), bundle);
                    LauncherProxyService.this.mShellInterface.createExternalInterfaces(bundle);
                    QuickStepContract.addInterface(LauncherProxyService.this.mTaskbarIndicatorController, bundle);
                    try {
                        Log.d("LauncherProxyService", "LauncherProxyService connected, initializing launcher proxy");
                        ((ILauncherProxy.Stub.Proxy) LauncherProxyService.this.mLauncherProxy).onInitialize(bundle);
                    } catch (RemoteException e) {
                        LauncherProxyService.this.mCurrentBoundedUserId = -1;
                        Log.e("LauncherProxyService", "Failed to call onInitialize()", e);
                    }
                    LauncherProxyService launcherProxyService4 = LauncherProxyService.this;
                    ILauncherProxy iLauncherProxy = launcherProxyService4.mLauncherProxy;
                    if (iLauncherProxy != null && (region = launcherProxyService4.mActiveNavBarRegion) != null) {
                        try {
                            ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onActiveNavBarRegionChanges(region);
                        } catch (RemoteException e2) {
                            Log.e("LauncherProxyService", "Failed to call onActiveNavBarRegionChanges()", e2);
                        }
                    }
                    LauncherProxyService launcherProxyService5 = LauncherProxyService.this;
                    launcherProxyService5.getClass();
                    if (ShadeWindowGoesAround.isEnabled()) {
                        Iterator it = ((Set) ((DisplayRepositoryImpl) launcherProxyService5.mDisplayRepository).displayRepositoryFromLib.getDisplayIds().getValue()).iterator();
                        while (it.hasNext()) {
                            launcherProxyService5.updateSysUIStateForNavbarWithDisplayId(((Integer) it.next()).intValue());
                        }
                    } else {
                        launcherProxyService5.updateSysUIStateForNavbarWithDisplayId(0);
                    }
                    ((ShadeViewController) launcherProxyService5.mShadeViewControllerLazy.get()).updateSystemUiStateFlags();
                    NotificationShadeWindowController notificationShadeWindowController2 = launcherProxyService5.mStatusBarWinController;
                    if (notificationShadeWindowController2 != null) {
                        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController2).notifyStateChangedCallbacks();
                    }
                    if (ShadeWindowGoesAround.isEnabled()) {
                        LauncherProxyService.this.notifySysUiStateFlagsForAllDisplays();
                    } else {
                        LauncherProxyService launcherProxyService6 = LauncherProxyService.this;
                        launcherProxyService6.notifySystemUiStateFlags(0, launcherProxyService6.mDefaultDisplaySysUIState.getFlags());
                    }
                    LauncherProxyService.this.notifyConnectionChanged();
                } catch (RemoteException e3) {
                    Log.e("LauncherProxyService", "Lost connection to launcher service", e3);
                    LauncherProxyService.this.disconnectFromLauncherService("Lost connection to launcher service");
                    LauncherProxyService.this.retryConnectionWithBackoff();
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                Log.w("LauncherProxyService", "Service disconnected");
                LauncherProxyService.this.mCurrentBoundedUserId = -1;
            }
        };
        StatusBarWindowCallback statusBarWindowCallback = new StatusBarWindowCallback() { // from class: com.android.systemui.recents.LauncherProxyService$$ExternalSyntheticLambda3
            @Override // com.android.systemui.statusbar.phone.StatusBarWindowCallback
            public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
                LauncherProxyService launcherProxyService = this.f$0;
                boolean z9 = false;
                SysUiState flag = launcherProxyService.mDefaultDisplaySysUIState.setFlag(64L, z && !z2);
                if (z && z2) {
                    z9 = true;
                }
                SysUiState flag2 = flag.setFlag(512L, z9).setFlag(2147483648L, z3).setFlag(8L, z4).setFlag(2097152L, z5).setFlag(134217728L, z7).setFlag(34359738368L, z8);
                launcherProxyService.mContext.getDisplayId();
                ((SysUiStateImpl) flag2).commitUpdate();
            }
        };
        this.mStatusBarWindowCallback = statusBarWindowCallback;
        this.mLauncherServiceDeathRcpt = new IBinder.DeathRecipient() { // from class: com.android.systemui.recents.LauncherProxyService$$ExternalSyntheticLambda4
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                LauncherProxyService launcherProxyService = this.f$0;
                if (launcherProxyService.mInputFocusTransferStarted) {
                    launcherProxyService.mHandler.post(new LauncherProxyService$$ExternalSyntheticLambda0(launcherProxyService, 3));
                }
                launcherProxyService.mIsPrevServiceCleanedUp = true;
                launcherProxyService.startConnectionToCurrentUser();
            }
        };
        IVoiceInteractionSessionListener anonymousClass5 = new AnonymousClass5();
        this.mVoiceInteractionSessionListener = anonymousClass5;
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.recents.LauncherProxyService.6
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                launcherProxyService.mConnectionBackoffAttempts = 0;
                launcherProxyService.internalConnectToCurrentUser("User changed");
            }
        };
        this.mUserChangedCallback = callback;
        ?? r14 = new SysUiState.SysUiStateCallback() { // from class: com.android.systemui.recents.LauncherProxyService.7
            @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
            public final void onSystemUiStateChanged(int i, long j) {
                LauncherProxyService.this.notifySystemUiStateFlags(i, j);
            }
        };
        this.mSysUiStateCallback = r14;
        ?? r15 = new ScreenLifecycle.Observer() { // from class: com.android.systemui.recents.LauncherProxyService.10
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurningOff() {
                try {
                    ILauncherProxy iLauncherProxy = LauncherProxyService.this.mLauncherProxy;
                    if (iLauncherProxy != null) {
                        ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onScreenTurningOff();
                    } else {
                        Log.e("LauncherProxyService", "Failed to get overview proxy for screen turning off event.");
                    }
                } catch (RemoteException e) {
                    Log.e("LauncherProxyService", "Failed to call onScreenTurningOff()", e);
                }
            }
        };
        this.mScreenLifecycleObserver = r15;
        ?? r7 = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.recents.LauncherProxyService.11
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                SysUiState flag = launcherProxyService.mDefaultDisplaySysUIState.setFlag(268435456L, false).setFlag(536870912L, false);
                launcherProxyService.mContext.getDisplayId();
                ((SysUiStateImpl) flag).commitUpdate();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                SysUiState flag = launcherProxyService.mDefaultDisplaySysUIState.setFlag(268435456L, true).setFlag(536870912L, false);
                launcherProxyService.mContext.getDisplayId();
                ((SysUiStateImpl) flag).commitUpdate();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                SysUiState flag = launcherProxyService.mDefaultDisplaySysUIState.setFlag(268435456L, false).setFlag(536870912L, true);
                launcherProxyService.mContext.getDisplayId();
                ((SysUiStateImpl) flag).commitUpdate();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                SysUiState flag = launcherProxyService.mDefaultDisplaySysUIState.setFlag(268435456L, true).setFlag(536870912L, true);
                launcherProxyService.mContext.getDisplayId();
                ((SysUiStateImpl) flag).commitUpdate();
            }
        };
        this.mWakefulnessLifecycleObserver = r7;
        processWrapper.getClass();
        boolean zIsSystemUser = ProcessWrapper.isSystemUser();
        boolean z = userManager.isVisibleBackgroundUsersSupported() && !userManager.isUserForeground();
        if (zIsSystemUser || !z) {
            iVoiceInteractionSessionListener = anonymousClass5;
        } else {
            iVoiceInteractionSessionListener = anonymousClass5;
            Log.d("LauncherProxyService", "Initialization for visibleBackgroundUser");
        }
        boolean z2 = zIsSystemUser || z;
        this.mIsSystemOrVisibleBgUser = z2;
        if (z2) {
            obj = r7;
        } else {
            obj = r7;
            Log.wtf("LauncherProxyService", "Unexpected initialization for non-system foreground user", new Throwable());
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
        ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(context.getString(R.string.face_acquired_tilt_too_extreme));
        this.mRecentsComponentName = componentNameUnflattenFromString;
        this.mQuickStepIntent = new Intent(ACTION_QUICKSTEP).setPackage(componentNameUnflattenFromString.getPackageName());
        this.mPerDisplaySysUiStateRepository = perDisplayRepository;
        this.mDisplayRepository = displayRepository;
        SysUiState sysUiState = (SysUiState) perDisplayRepository.get(0);
        this.mDefaultDisplaySysUIState = sysUiState;
        ((SysUiStateImpl) sysUiState).addCallback(r14);
        this.mUiEventLogger = uiEventLogger;
        this.mDisplayTracker = displayTracker;
        this.mUnfoldTransitionProgressForwarder = optional;
        this.mBackAnimation = optional2.orElse(null);
        this.mFgsManagerController = fgsManagerController;
        this.mMainHandler = handler;
        this.mQsCustomizerContoller = qSCMainViewController;
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        this.mSysuiUnlockAnimationController = keyguardUnlockAnimationController;
        String simpleName = getClass().getSimpleName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, simpleName, this);
        if (BasicRune.SEARCLE) {
            this.mSearcleManager = searcleManager;
        }
        if (BasicRune.NAVBAR_GESTURE) {
            NavBarStore navBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
            this.mNavBarStore = navBarStore;
            this.mNavBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(context.getDisplayId());
        }
        this.mSamsungScreenPinningRequest = samsungScreenPinningRequest;
        this.mTaskbarIndicatorController = taskbarIndicatorController;
        this.mNavBarMode = navigationModeController.addListener(this);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(componentNameUnflattenFromString.getPackageName(), 0);
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        context.registerReceiver(r10, intentFilter);
        broadcastDispatcher.registerReceiver(r9, new IntentFilter("android.intent.action.USER_UNLOCKED"), null, UserHandle.ALL);
        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).registerCallback(statusBarWindowCallback);
        this.mScreenshotHelper = new ScreenshotHelper(context);
        commandQueue.addCallback(new CommandQueue.Callbacks(this) { // from class: com.android.systemui.recents.LauncherProxyService.8
        });
        this.mCommandQueue = commandQueue;
        ((UserTrackerImpl) userTracker).addCallback(callback, executor);
        screenLifecycle.addObserver(r15);
        wakefulnessLifecycle.addObserver(obj);
        updateEnabledAndBinding();
        assistUtils.registerVoiceInteractionSessionListener(iVoiceInteractionSessionListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.recents.LauncherProxyService$9] */
    public final void disconnectFromLauncherService(String str) {
        Log.d("LauncherProxyService", "disconnectFromLauncherService bound?: " + this.mBound + " currentProxy: " + this.mLauncherProxy + " disconnectReason: " + str, new Throwable());
        if (this.mBound) {
            this.mContext.unbindService(this.mLauncherServiceConnection);
            this.mBound = false;
            ILauncherProxy iLauncherProxy = this.mLauncherProxy;
            if (iLauncherProxy != null) {
                try {
                    ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onUnbind(new IRemoteCallback.Stub() { // from class: com.android.systemui.recents.LauncherProxyService.9
                        public final void sendResult(Bundle bundle) {
                            LauncherProxyService launcherProxyService = LauncherProxyService.this;
                            launcherProxyService.mIsPrevServiceCleanedUp = true;
                            if (launcherProxyService.mHandler.hasCallbacks(launcherProxyService.mDeferredBindAfterTimedOutCleanup)) {
                                LauncherProxyService launcherProxyService2 = LauncherProxyService.this;
                                launcherProxyService2.mHandler.removeCallbacks(launcherProxyService2.mDeferredBindAfterTimedOutCleanup);
                                LauncherProxyService.this.maybeBindService();
                            }
                        }
                    });
                } catch (RemoteException unused) {
                    Log.w("LauncherProxyService", "disconnectFromLauncherService failed to notify Launcher");
                    this.mIsPrevServiceCleanedUp = true;
                }
            }
        }
        ILauncherProxy iLauncherProxy2 = this.mLauncherProxy;
        if (iLauncherProxy2 != null) {
            iLauncherProxy2.asBinder().unlinkToDeath(this.mLauncherServiceDeathRcpt, 0);
            this.mLauncherProxy = null;
            notifyConnectionChanged();
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("LauncherProxyService state:");
        printWriter.print("  isConnected=");
        printWriter.println(this.mLauncherProxy != null);
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
        printWriter.print("  mIsPrevServiceCleanedUp=");
        printWriter.println(this.mIsPrevServiceCleanedUp);
        ((SysUiStateImpl) this.mDefaultDisplaySysUIState).dump(printWriter, strArr);
    }

    public final void internalConnectToCurrentUser(String str) {
        if (!this.mIsSystemOrVisibleBgUser) {
            Log.w("LauncherProxyService", "Skipping connection to launcher service due to non-system foreground user caller");
            return;
        }
        disconnectFromLauncherService(str);
        if (this.mIsEnabled) {
            this.mHandler.removeCallbacks(this.mConnectionRunnable);
            maybeBindService();
        }
    }

    public final void maybeBindService() {
        boolean z = this.mIsPrevServiceCleanedUp;
        Handler handler = this.mHandler;
        LauncherProxyService$$ExternalSyntheticLambda0 launcherProxyService$$ExternalSyntheticLambda0 = this.mDeferredConnectionCallback;
        if (!z) {
            Log.w("LauncherProxyService", "Skipping connection to TouchInteractionService until previous instance is cleaned up.");
            if (handler.hasCallbacks(launcherProxyService$$ExternalSyntheticLambda0)) {
                return;
            }
            handler.postDelayed(this.mDeferredBindAfterTimedOutCleanup, 1000L);
            return;
        }
        UserHandle userHandleOf = UserHandle.of(((UserTrackerImpl) this.mUserTracker).getUserId());
        if (UserManager.isHeadlessSystemUserMode() && userHandleOf.isSystem()) {
            Log.w("LauncherProxyService", "Skipping connection to TouchInteractionService for the System user in HSUM mode.");
            return;
        }
        try {
            this.mBound = this.mContext.bindServiceAsUser(this.mQuickStepIntent, this.mLauncherServiceConnection, 33554433, userHandleOf);
        } catch (SecurityException e) {
            Log.e("LauncherProxyService", "Unable to bind because of security error", e);
        }
        if (!this.mBound) {
            retryConnectionWithBackoff();
        } else {
            this.mIsPrevServiceCleanedUp = false;
            handler.postDelayed(launcherProxyService$$ExternalSyntheticLambda0, 5000L);
        }
    }

    public final void notifyConnectionChanged() {
        for (int size = ((ArrayList) this.mConnectionCallbacks).size() - 1; size >= 0; size--) {
            ((LauncherProxyListener) ((ArrayList) this.mConnectionCallbacks).get(size)).onConnectionChanged(this.mLauncherProxy != null);
        }
    }

    public void notifySysUiStateFlagsForAllDisplays() {
        Iterator it = ((Set) ((DisplayRepositoryImpl) this.mDisplayRepository).displayRepositoryFromLib.getDisplayIds().getValue()).iterator();
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            SysUiState sysUiState = (SysUiState) this.mPerDisplaySysUiStateRepository.get(iIntValue);
            if (sysUiState != null) {
                notifySystemUiStateFlags(iIntValue, sysUiState.getFlags());
            }
        }
    }

    public final void notifySystemUiStateFlags(int i, long j) {
        try {
            ILauncherProxy iLauncherProxy = this.mLauncherProxy;
            if (iLauncherProxy != null) {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onSystemUiStateChanged(i, j);
            }
        } catch (RemoteException e) {
            Log.e("LauncherProxyService", "Failed to notify sysui state change", e);
        }
    }

    public final void notifyThreeFingerGestureEvent(KeyEvent keyEvent) {
        try {
            ILauncherProxy iLauncherProxy = this.mLauncherProxy;
            if (iLauncherProxy != null) {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onThreeFingerGestureEvent(keyEvent);
            }
        } catch (RemoteException e) {
            Log.e("LauncherProxyService", "Failed to notify three finger gesture event", e);
        }
    }

    public final void onNavButtonsDarkIntensityChanged(float f) {
        try {
            ILauncherProxy iLauncherProxy = this.mLauncherProxy;
            if (iLauncherProxy != null) {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onNavButtonsDarkIntensityChanged(f);
            } else {
                Log.e("LauncherProxyService", "Failed to get launcher proxy to update nav buttons dark intensity");
            }
        } catch (RemoteException e) {
            Log.e("LauncherProxyService", "Failed to call onNavButtonsDarkIntensityChanged()", e);
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }

    public final void onSystemBarAttributesChanged(int i, int i2) {
        try {
            ILauncherProxy iLauncherProxy = this.mLauncherProxy;
            if (iLauncherProxy != null) {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onSystemBarAttributesChanged(i, i2);
            } else {
                Log.e("LauncherProxyService", "Failed to get launcher proxy for system bar attr change.");
            }
        } catch (RemoteException e) {
            Log.e("LauncherProxyService", "Failed to call onSystemBarAttributesChanged()", e);
        }
    }

    public final void retryConnectionWithBackoff() {
        LauncherProxyService$$ExternalSyntheticLambda0 launcherProxyService$$ExternalSyntheticLambda0 = this.mConnectionRunnable;
        Handler handler = this.mHandler;
        if (handler.hasCallbacks(launcherProxyService$$ExternalSyntheticLambda0)) {
            return;
        }
        long jMin = (long) Math.min(Math.scalb(1000.0f, this.mConnectionBackoffAttempts), 600000.0f);
        handler.postDelayed(launcherProxyService$$ExternalSyntheticLambda0, jMin);
        this.mConnectionBackoffAttempts++;
        Log.w("LauncherProxyService", "Failed to connect on attempt " + this.mConnectionBackoffAttempts + " will try again in " + jMin + "ms");
    }

    public final boolean shouldShowSwipeUpUI() {
        if (!this.mIsEnabled) {
            return false;
        }
        int i = this.mNavBarMode;
        boolean z = QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN;
        return i != 0;
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
        this.mIsEnabled = this.mContext.getPackageManager().resolveServiceAsUser(this.mQuickStepIntent, 1048576, ((UserTrackerImpl) this.mUserTracker).getUserId()) != null;
        startConnectionToCurrentUser();
    }

    public final void updateSysUIStateForNavbarWithDisplayId(int i) {
        Lazy lazy = this.mNavBarControllerLazy;
        NavigationBar navigationBar = ((NavigationBarControllerImpl) ((NavigationBarController) lazy.get())).getNavigationBar(i);
        NavigationBarView navigationBarView = ((NavigationBarControllerImpl) ((NavigationBarController) lazy.get())).getNavigationBarView(i);
        SysUiState sysUiState = (SysUiState) this.mPerDisplaySysUiStateRepository.get(i);
        if (sysUiState == null) {
            return;
        }
        if (navigationBar != null) {
            navigationBar.updateSystemUiStateFlags();
        }
        if (navigationBarView != null) {
            navigationBarView.updateDisabledSystemUiStateFlags(sysUiState);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(LauncherProxyListener launcherProxyListener) {
        if (!((ArrayList) this.mConnectionCallbacks).contains(launcherProxyListener)) {
            ((ArrayList) this.mConnectionCallbacks).add(launcherProxyListener);
        }
        launcherProxyListener.onConnectionChanged(this.mLauncherProxy != null);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(LauncherProxyListener launcherProxyListener) {
        this.mConnectionCallbacks.remove(launcherProxyListener);
    }

    /* renamed from: com.android.systemui.recents.LauncherProxyService$5, reason: invalid class name */
    public class AnonymousClass5 extends IVoiceInteractionSessionListener.Stub {
        public AnonymousClass5() {
        }

        public final void onVoiceSessionWindowVisibilityChanged(boolean z) {
            LauncherProxyService.this.mContext.getMainExecutor().execute(new LauncherProxyService$5$$ExternalSyntheticLambda0(this, z));
        }

        public final void onSetUiHints(Bundle bundle) {
        }

        public final void onVoiceSessionHidden() {
        }

        public final void onVoiceSessionShown() {
        }
    }

    public interface LauncherProxyListener {
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

        default void onInitializedTaskbarNavigationBar() {
        }

        default void onOverviewShown() {
        }

        default void onTaskbarSPluginButtonClicked() {
        }

        default void onToggleRecentApps() {
        }

        default void onTaskbarAutohideSuspendForDisplay(int i, boolean z) {
        }

        default void onTaskbarStatusUpdated$1(boolean z, boolean z2) {
        }

        default void updateContextualEduStats(boolean z, GestureType gestureType) {
        }

        default void animateNavBarLongPress(boolean z, boolean z2, long j) {
        }

        default void setOverrideHomeButtonLongPress(float f, boolean z, long j) {
        }
    }
}
