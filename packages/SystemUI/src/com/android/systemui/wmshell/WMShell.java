package com.android.systemui.wmshell;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Log;
import android.window.WindowContainerTransaction;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.communal.ui.viewmodel.CommunalTransitionViewModel;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.model.SysUiState;
import com.android.systemui.notetask.NoteTaskInitializer;
import com.android.systemui.notetask.NoteTaskInitializer$callbacks$1;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.wmshell.WMShell;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.desktopmode.DesktopMode;
import com.android.wm.shell.desktopmode.DesktopModeTaskRepository;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.onehanded.OneHanded;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda7;
import com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0;
import com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda3;
import com.android.wm.shell.onehanded.OneHandedTransitionCallback;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda5;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasks;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda2;
import com.android.wm.shell.splitscreen.EnterSplitGestureHandler;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda6;
import com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0;
import com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda2;
import com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda7;
import com.android.wm.shell.sysui.ShellInterface;
import com.android.wm.shell.util.KtProtoLog;
import com.samsung.android.systemui.multistar.MultiStarManager;
import com.samsung.android.systemui.multistar.MultiStarSystemProxyImpl;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WMShell implements CoreStartable, CommandQueue.Callbacks {
    public final CommandQueue mCommandQueue;
    public final CommunalTransitionViewModel mCommunalTransitionViewModel;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final Optional mDesktopModeOptional;
    public final DisplayTracker mDisplayTracker;
    public final Optional mEnterSplitGestureHandlerOptional;
    public final JavaAdapter mJavaAdapter;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final NoteTaskInitializer mNoteTaskInitializer;
    public final Optional mOneHandedOptional;
    public final Optional mPipOptional;
    public final Optional mRecentTasksOptional;
    public final ScreenLifecycle mScreenLifecycle;
    public final ShellInterface mShell;
    public final Optional mSplitScreenOptional;
    public final Executor mSysUiMainExecutor;
    public final SysUiState mSysUiState;
    public final UserTracker mUserTracker;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public AnonymousClass12 mWakefulnessObserver;
    public final AnonymousClass1 mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.wmshell.WMShell.1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            WMShell.this.mShell.onConfigurationChanged(configuration);
        }
    };
    public final AnonymousClass2 mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.wmshell.WMShell.2
        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            WMShell wMShell = WMShell.this;
            ShellInterface shellInterface = wMShell.mShell;
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) wMShell.mKeyguardStateController;
            shellInterface.onKeyguardVisibilityChanged(keyguardStateControllerImpl.mShowing, keyguardStateControllerImpl.mOccluded, ((KeyguardViewMediator) ((KeyguardUnlockAnimationController) keyguardStateControllerImpl.mUnlockAnimationControllerLazy.get()).keyguardViewMediator.get()).isAnimatingBetweenKeyguardAndSurfaceBehind());
        }
    };
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.wmshell.WMShell.3
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardDismissAnimationFinished() {
            WMShell.this.mShell.onKeyguardDismissAnimationFinished();
        }
    };
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.wmshell.WMShell.4
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onProfilesChanged(List list) {
            WMShell.this.mShell.onUserProfilesChanged(list);
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            WMShell.this.mShell.onUserChanged(i, context);
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.wmshell.WMShell$10, reason: invalid class name */
    public final class AnonymousClass10 implements OneHandedTransitionCallback {
        public AnonymousClass10() {
        }

        @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStartFinished(Rect rect) {
            WMShell.this.mSysUiMainExecutor.execute(new WMShell$10$$ExternalSyntheticLambda0(this, 0));
        }

        @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStartTransition() {
            WMShell.this.mSysUiMainExecutor.execute(new WMShell$10$$ExternalSyntheticLambda0(this, 2));
        }

        @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStopFinished(Rect rect) {
            WMShell.this.mSysUiMainExecutor.execute(new WMShell$10$$ExternalSyntheticLambda0(this, 1));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.wmshell.WMShell$11, reason: invalid class name */
    public final class AnonymousClass11 {
        public AnonymousClass11() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.wmshell.WMShell$8, reason: invalid class name */
    public final class AnonymousClass8 {
        public AnonymousClass8() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.wmshell.WMShell$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.wmshell.WMShell$2] */
    public WMShell(Context context, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<DesktopMode> optional4, Optional<RecentTasks> optional5, CommandQueue commandQueue, ConfigurationController configurationController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, ScreenLifecycle screenLifecycle, SysUiState sysUiState, WakefulnessLifecycle wakefulnessLifecycle, UserTracker userTracker, DisplayTracker displayTracker, NoteTaskInitializer noteTaskInitializer, CommunalTransitionViewModel communalTransitionViewModel, JavaAdapter javaAdapter, Executor executor, Optional<EnterSplitGestureHandler> optional6) {
        this.mContext = context;
        this.mShell = shellInterface;
        this.mCommandQueue = commandQueue;
        this.mConfigurationController = configurationController;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mScreenLifecycle = screenLifecycle;
        this.mSysUiState = sysUiState;
        this.mPipOptional = optional;
        this.mSplitScreenOptional = optional2;
        this.mOneHandedOptional = optional3;
        this.mDesktopModeOptional = optional4;
        this.mRecentTasksOptional = optional5;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mUserTracker = userTracker;
        this.mDisplayTracker = displayTracker;
        this.mNoteTaskInitializer = noteTaskInitializer;
        this.mCommunalTransitionViewModel = communalTransitionViewModel;
        this.mJavaAdapter = javaAdapter;
        this.mSysUiMainExecutor = executor;
        this.mEnterSplitGestureHandlerOptional = optional6;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Dumping with args: "), String.join(", ", strArr), "com.android.systemui.wmshell.WMShell");
        if (strArr[0].equals("dependency")) {
            strArr = (String[]) Arrays.copyOfRange(strArr, 1, strArr.length);
        }
        ShellInterface shellInterface = this.mShell;
        if (shellInterface.handleCommand(printWriter, strArr)) {
            return;
        }
        shellInterface.dump(printWriter);
    }

    public void initOneHanded(final OneHanded oneHanded) {
        final AnonymousClass10 anonymousClass10 = new AnonymousClass10();
        final OneHandedController.OneHandedImpl oneHandedImpl = (OneHandedController.OneHandedImpl) oneHanded;
        final int i = 0;
        ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        OneHandedController.OneHandedImpl oneHandedImpl2 = oneHandedImpl;
                        ((ArrayList) OneHandedController.this.mDisplayAreaOrganizer.mTransitionCallbacks).add((OneHandedTransitionCallback) anonymousClass10);
                        break;
                    default:
                        OneHandedController.this.mEventCallback = (WMShell.AnonymousClass11) anonymousClass10;
                        break;
                }
            }
        });
        final AnonymousClass11 anonymousClass11 = new AnonymousClass11();
        final int i2 = 1;
        ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        OneHandedController.OneHandedImpl oneHandedImpl2 = oneHandedImpl;
                        ((ArrayList) OneHandedController.this.mDisplayAreaOrganizer.mTransitionCallbacks).add((OneHandedTransitionCallback) anonymousClass11);
                        break;
                    default:
                        OneHandedController.this.mEventCallback = (WMShell.AnonymousClass11) anonymousClass11;
                        break;
                }
            }
        });
        this.mWakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer(this) { // from class: com.android.systemui.wmshell.WMShell.12
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda3(oneHandedImpl2, false));
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$$ExternalSyntheticLambda7(oneHandedImpl2, 8));
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda3(oneHandedImpl2, true));
            }
        });
        this.mScreenLifecycle.addObserver(new ScreenLifecycle.Observer(this) { // from class: com.android.systemui.wmshell.WMShell.13
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurningOff() {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0(oneHandedImpl2, 7));
            }
        });
        this.mCommandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.wmshell.WMShell.14
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onCameraLaunchGestureDetected(int i3) {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$$ExternalSyntheticLambda7(oneHandedImpl2, 8));
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void setImeWindowStatus(int i3, IBinder iBinder, int i4, int i5, boolean z) {
                WMShell.this.mDisplayTracker.getClass();
                if (i3 != 0 || (i4 & 2) == 0) {
                    return;
                }
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0(oneHandedImpl2, 3));
            }
        });
    }

    public void initPip(final Pip pip) {
        this.mCommandQueue.addCallback(new CommandQueue.Callbacks(this) { // from class: com.android.systemui.wmshell.WMShell.5
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void showPictureInPictureMenu() {
                PipController.PipImpl pipImpl = (PipController.PipImpl) pip;
                ((HandlerExecutor) PipController.this.mMainExecutor).execute(new PipController$$ExternalSyntheticLambda5(pipImpl, 6));
            }
        });
        this.mSysUiState.addCallback(new WMShell$$ExternalSyntheticLambda0(this, pip, 0));
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda1] */
    public void initRecentTasks(final RecentTasks recentTasks) {
        final Executor executor = this.mSysUiMainExecutor;
        final CommandQueue commandQueue = this.mCommandQueue;
        Objects.requireNonNull(commandQueue);
        final int i = 0;
        final ?? r2 = new Consumer() { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                Object obj2 = commandQueue;
                switch (i2) {
                    case 0:
                        ((CommandQueue) obj2).onRecentsAnimationStateChanged(((Boolean) obj).booleanValue());
                        break;
                    default:
                        RecentTasksController.RecentTasksImpl recentTasksImpl = (RecentTasksController.RecentTasksImpl) ((RecentTasks) obj2);
                        ((HandlerExecutor) RecentTasksController.this.mMainExecutor).execute(new RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda2(0, recentTasksImpl, (Color) obj));
                        break;
                }
            }
        };
        final RecentTasksController.RecentTasksImpl recentTasksImpl = (RecentTasksController.RecentTasksImpl) recentTasks;
        ((HandlerExecutor) RecentTasksController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RecentTasksController.RecentTasksImpl recentTasksImpl2 = RecentTasksController.RecentTasksImpl.this;
                Executor executor2 = executor;
                Consumer consumer = r2;
                RecentsTransitionHandler recentsTransitionHandler = RecentTasksController.this.mTransitionHandler;
                if (recentsTransitionHandler == null) {
                    return;
                }
                recentsTransitionHandler.mStateListeners.add(new RecentsTransitionStateListener(recentTasksImpl2, executor2, consumer) { // from class: com.android.wm.shell.recents.RecentTasksController.RecentTasksImpl.1
                    public final /* synthetic */ Executor val$executor;
                    public final /* synthetic */ Consumer val$listener;

                    public AnonymousClass1(RecentTasksImpl recentTasksImpl22, Executor executor22, Consumer consumer2) {
                        this.val$executor = executor22;
                        this.val$listener = consumer2;
                    }

                    @Override // com.android.wm.shell.recents.RecentsTransitionStateListener
                    public final void onAnimationStateChanged(final boolean z) {
                        Executor executor3 = this.val$executor;
                        final Consumer consumer2 = this.val$listener;
                        executor3.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer2.accept(Boolean.valueOf(z));
                            }
                        });
                    }
                });
            }
        });
        final int i2 = 1;
        this.mJavaAdapter.alwaysCollectFlow(this.mCommunalTransitionViewModel.recentsBackgroundColor, new Consumer() { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                Object obj2 = recentTasks;
                switch (i22) {
                    case 0:
                        ((CommandQueue) obj2).onRecentsAnimationStateChanged(((Boolean) obj).booleanValue());
                        break;
                    default:
                        RecentTasksController.RecentTasksImpl recentTasksImpl2 = (RecentTasksController.RecentTasksImpl) ((RecentTasks) obj2);
                        ((HandlerExecutor) RecentTasksController.this.mMainExecutor).execute(new RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda2(0, recentTasksImpl2, (Color) obj));
                        break;
                }
            }
        });
    }

    public void initSplitScreen(final SplitScreen splitScreen) {
        this.mWakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer(this) { // from class: com.android.systemui.wmshell.WMShell.6
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                SplitScreenController splitScreenController = SplitScreenController.this;
                ((HandlerExecutor) splitScreenController.mMainExecutor).execute(new SplitScreenController$$ExternalSyntheticLambda6(splitScreenController, 2));
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                SplitScreenController splitScreenController = SplitScreenController.this;
                ((HandlerExecutor) splitScreenController.mMainExecutor).execute(new SplitScreenController$$ExternalSyntheticLambda6(splitScreenController, 1));
            }
        });
        CommandQueue.Callbacks callbacks = new CommandQueue.Callbacks(this) { // from class: com.android.systemui.wmshell.WMShell.7
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void moveFocusedTaskToFullscreen(int i) {
                SplitScreenController splitScreenController = SplitScreenController.this;
                ((HandlerExecutor) splitScreenController.mMainExecutor).execute(new SplitScreenController$$ExternalSyntheticLambda6(splitScreenController, 3));
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void setSplitscreenFocus(boolean z) {
                SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) splitScreen;
                ((HandlerExecutor) SplitScreenController.this.mMainExecutor).execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda2(splitScreenImpl, z, 1));
            }
        };
        CommandQueue commandQueue = this.mCommandQueue;
        commandQueue.addCallback(callbacks);
        final SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) splitScreen;
        splitScreenImpl.registerSplitAnimationListener(new AnonymousClass8(), this.mSysUiMainExecutor);
        BooleanSupplier booleanSupplier = new BooleanSupplier() { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda3
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) WMShell.this.mKeyguardStateController;
                return keyguardStateControllerImpl.mOccluded && keyguardStateControllerImpl.mShowing;
            }
        };
        ((HandlerExecutor) SplitScreenController.this.mMainExecutor).execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda7(splitScreenImpl, booleanSupplier, 2));
        commandQueue.addCallback(new CommandQueue.Callbacks(this) { // from class: com.android.systemui.wmshell.WMShell.9
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void toggleSplitScreen() {
                SplitScreenController.SplitScreenImpl splitScreenImpl2 = (SplitScreenController.SplitScreenImpl) splitScreenImpl;
                ((HandlerExecutor) SplitScreenController.this.mMainExecutor).execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0(splitScreenImpl2, 1));
            }
        });
        MultiStarManager multiStarManager = (MultiStarManager) MultiStarManager.sInstance.get();
        Context context = this.mContext;
        multiStarManager.getClass();
        Log.d("MultiStarManager", "Create");
        multiStarManager.mMultiStarSystemFacade = new MultiStarSystemProxyImpl(context, splitScreenImpl);
        ((SPluginManager) Dependency.sDependency.getDependencyInner(SPluginManager.class)).addPluginListener((SPluginListener) new SPluginListener() { // from class: com.samsung.android.systemui.multistar.MultiStarManager.2
            public AnonymousClass2() {
            }

            @Override // com.samsung.systemui.splugins.SPluginListener
            public final void onPluginConnected(SPlugin sPlugin, Context context2) {
                PluginMultiStar pluginMultiStar = (PluginMultiStar) sPlugin;
                PluginMultiStar pluginMultiStar2 = MultiStarManager.mPluginMultiStar;
                Log.d("MultiStarManager", "onPluginConnected");
                MultiStarManager.mPluginMultiStar = pluginMultiStar;
                pluginMultiStar.init(MultiStarManager.this.mMultiStarSystemFacade);
            }

            @Override // com.samsung.systemui.splugins.SPluginListener
            public final void onPluginDisconnected(SPlugin sPlugin, int i) {
                PluginMultiStar pluginMultiStar = MultiStarManager.mPluginMultiStar;
                Log.d("MultiStarManager", "onPluginDisconnected");
                MultiStarManager.mPluginMultiStar = null;
            }
        }, PluginMultiStar.class, false);
    }

    @Override // com.android.systemui.CoreStartable
    public final boolean isDumpCritical() {
        return false;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mShell.onConfigurationChanged(this.mContext.getResources().getConfiguration());
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateCallback);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        final int i = 0;
        this.mPipOptional.ifPresent(new Consumer(this) { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda4
            public final /* synthetic */ WMShell f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                final WMShell wMShell = this.f$0;
                switch (i2) {
                    case 0:
                        wMShell.initPip((Pip) obj);
                        break;
                    case 1:
                        wMShell.initSplitScreen((SplitScreen) obj);
                        break;
                    case 2:
                        wMShell.initOneHanded((OneHanded) obj);
                        break;
                    case 3:
                        DesktopMode desktopMode = (DesktopMode) obj;
                        wMShell.getClass();
                        final DesktopModeTaskRepository.VisibleTasksListener visibleTasksListener = new DesktopModeTaskRepository.VisibleTasksListener() { // from class: com.android.systemui.wmshell.WMShell.15
                            @Override // com.android.wm.shell.desktopmode.DesktopModeTaskRepository.VisibleTasksListener
                            public final void onTasksVisibilityChanged(int i3, int i4) {
                                if (i3 == 0) {
                                    WMShell wMShell2 = WMShell.this;
                                    SysUiState sysUiState = wMShell2.mSysUiState;
                                    sysUiState.setFlag(67108864L, i4 > 0);
                                    wMShell2.mDisplayTracker.getClass();
                                    sysUiState.commitUpdate(0);
                                }
                            }
                        };
                        final Executor executor = wMShell.mSysUiMainExecutor;
                        final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                        ((HandlerExecutor) desktopTasksController.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addVisibleTasksListener$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                desktopTasksController2.desktopModeTaskRepository.addVisibleTasksListener(visibleTasksListener, executor);
                            }
                        });
                        wMShell.mCommandQueue.addCallback(new CommandQueue.Callbacks(wMShell, desktopMode) { // from class: com.android.systemui.wmshell.WMShell.16
                            public final /* synthetic */ DesktopMode val$desktopMode;

                            {
                                this.val$desktopMode = desktopMode;
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToDesktop(final int i3) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToDesktop$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i4 = i3;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ArrayList runningTasks = desktopTasksController3.shellTaskOrganizer.getRunningTasks(i4);
                                        ArrayList arrayList = new ArrayList();
                                        Iterator it = runningTasks.iterator();
                                        while (it.hasNext()) {
                                            Object next = it.next();
                                            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) next;
                                            if (runningTaskInfo.isFocused && (runningTaskInfo.getWindowingMode() == 1 || runningTaskInfo.getWindowingMode() == 6)) {
                                                if (runningTaskInfo.getActivityType() != 2) {
                                                    arrayList.add(next);
                                                }
                                            }
                                        }
                                        if (!arrayList.isEmpty()) {
                                            int size = arrayList.size();
                                            if (size == 1) {
                                                desktopTasksController3.moveToDesktop(((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                                return;
                                            }
                                            if (size == 2) {
                                                ActivityManager.RunningTaskInfo runningTaskInfo2 = ((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId == ((ActivityManager.RunningTaskInfo) arrayList.get(1)).parentTaskId ? (ActivityManager.RunningTaskInfo) arrayList.get(1) : (ActivityManager.RunningTaskInfo) arrayList.get(0);
                                                Intrinsics.checkNotNull(runningTaskInfo2);
                                                desktopTasksController3.moveToDesktop(runningTaskInfo2, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                            } else {
                                                KtProtoLog.Companion companion = KtProtoLog.Companion;
                                                ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                                                Object[] objArr = {Integer.valueOf(arrayList.size())};
                                                companion.getClass();
                                                KtProtoLog.Companion.w(shellProtoLogGroup, "DesktopTasksController: Cannot enter desktop, expected less than 3 focused tasks but found %d", objArr);
                                            }
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToFullscreen(final int i3) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToFullscreen$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i4 = i3;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i4);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.moveToFullscreenWithAnimation(focusedFreeformTask, focusedFreeformTask.positionInParent, desktopModeTransitionSource2);
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToStageSplit(final int i3, final boolean z) {
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToStageSplit$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i4 = i3;
                                        boolean z2 = z;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i4);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.requestSplit(focusedFreeformTask, z2);
                                        }
                                    }
                                });
                            }
                        });
                        break;
                    case 4:
                        wMShell.initRecentTasks((RecentTasks) obj);
                        break;
                    default:
                        wMShell.getClass();
                        wMShell.mSysUiState.addCallback(new WMShell$$ExternalSyntheticLambda0(wMShell, (EnterSplitGestureHandler) obj, 1));
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mSplitScreenOptional.ifPresent(new Consumer(this) { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda4
            public final /* synthetic */ WMShell f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                final WMShell wMShell = this.f$0;
                switch (i22) {
                    case 0:
                        wMShell.initPip((Pip) obj);
                        break;
                    case 1:
                        wMShell.initSplitScreen((SplitScreen) obj);
                        break;
                    case 2:
                        wMShell.initOneHanded((OneHanded) obj);
                        break;
                    case 3:
                        DesktopMode desktopMode = (DesktopMode) obj;
                        wMShell.getClass();
                        final DesktopModeTaskRepository.VisibleTasksListener visibleTasksListener = new DesktopModeTaskRepository.VisibleTasksListener() { // from class: com.android.systemui.wmshell.WMShell.15
                            @Override // com.android.wm.shell.desktopmode.DesktopModeTaskRepository.VisibleTasksListener
                            public final void onTasksVisibilityChanged(int i3, int i4) {
                                if (i3 == 0) {
                                    WMShell wMShell2 = WMShell.this;
                                    SysUiState sysUiState = wMShell2.mSysUiState;
                                    sysUiState.setFlag(67108864L, i4 > 0);
                                    wMShell2.mDisplayTracker.getClass();
                                    sysUiState.commitUpdate(0);
                                }
                            }
                        };
                        final Executor executor = wMShell.mSysUiMainExecutor;
                        final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                        ((HandlerExecutor) desktopTasksController.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addVisibleTasksListener$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                desktopTasksController2.desktopModeTaskRepository.addVisibleTasksListener(visibleTasksListener, executor);
                            }
                        });
                        wMShell.mCommandQueue.addCallback(new CommandQueue.Callbacks(wMShell, desktopMode) { // from class: com.android.systemui.wmshell.WMShell.16
                            public final /* synthetic */ DesktopMode val$desktopMode;

                            {
                                this.val$desktopMode = desktopMode;
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToDesktop(final int i3) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToDesktop$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i4 = i3;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ArrayList runningTasks = desktopTasksController3.shellTaskOrganizer.getRunningTasks(i4);
                                        ArrayList arrayList = new ArrayList();
                                        Iterator it = runningTasks.iterator();
                                        while (it.hasNext()) {
                                            Object next = it.next();
                                            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) next;
                                            if (runningTaskInfo.isFocused && (runningTaskInfo.getWindowingMode() == 1 || runningTaskInfo.getWindowingMode() == 6)) {
                                                if (runningTaskInfo.getActivityType() != 2) {
                                                    arrayList.add(next);
                                                }
                                            }
                                        }
                                        if (!arrayList.isEmpty()) {
                                            int size = arrayList.size();
                                            if (size == 1) {
                                                desktopTasksController3.moveToDesktop(((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                                return;
                                            }
                                            if (size == 2) {
                                                ActivityManager.RunningTaskInfo runningTaskInfo2 = ((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId == ((ActivityManager.RunningTaskInfo) arrayList.get(1)).parentTaskId ? (ActivityManager.RunningTaskInfo) arrayList.get(1) : (ActivityManager.RunningTaskInfo) arrayList.get(0);
                                                Intrinsics.checkNotNull(runningTaskInfo2);
                                                desktopTasksController3.moveToDesktop(runningTaskInfo2, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                            } else {
                                                KtProtoLog.Companion companion = KtProtoLog.Companion;
                                                ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                                                Object[] objArr = {Integer.valueOf(arrayList.size())};
                                                companion.getClass();
                                                KtProtoLog.Companion.w(shellProtoLogGroup, "DesktopTasksController: Cannot enter desktop, expected less than 3 focused tasks but found %d", objArr);
                                            }
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToFullscreen(final int i3) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToFullscreen$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i4 = i3;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i4);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.moveToFullscreenWithAnimation(focusedFreeformTask, focusedFreeformTask.positionInParent, desktopModeTransitionSource2);
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToStageSplit(final int i3, final boolean z) {
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToStageSplit$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i4 = i3;
                                        boolean z2 = z;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i4);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.requestSplit(focusedFreeformTask, z2);
                                        }
                                    }
                                });
                            }
                        });
                        break;
                    case 4:
                        wMShell.initRecentTasks((RecentTasks) obj);
                        break;
                    default:
                        wMShell.getClass();
                        wMShell.mSysUiState.addCallback(new WMShell$$ExternalSyntheticLambda0(wMShell, (EnterSplitGestureHandler) obj, 1));
                        break;
                }
            }
        });
        final int i3 = 2;
        this.mOneHandedOptional.ifPresent(new Consumer(this) { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda4
            public final /* synthetic */ WMShell f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i3;
                final WMShell wMShell = this.f$0;
                switch (i22) {
                    case 0:
                        wMShell.initPip((Pip) obj);
                        break;
                    case 1:
                        wMShell.initSplitScreen((SplitScreen) obj);
                        break;
                    case 2:
                        wMShell.initOneHanded((OneHanded) obj);
                        break;
                    case 3:
                        DesktopMode desktopMode = (DesktopMode) obj;
                        wMShell.getClass();
                        final DesktopModeTaskRepository.VisibleTasksListener visibleTasksListener = new DesktopModeTaskRepository.VisibleTasksListener() { // from class: com.android.systemui.wmshell.WMShell.15
                            @Override // com.android.wm.shell.desktopmode.DesktopModeTaskRepository.VisibleTasksListener
                            public final void onTasksVisibilityChanged(int i32, int i4) {
                                if (i32 == 0) {
                                    WMShell wMShell2 = WMShell.this;
                                    SysUiState sysUiState = wMShell2.mSysUiState;
                                    sysUiState.setFlag(67108864L, i4 > 0);
                                    wMShell2.mDisplayTracker.getClass();
                                    sysUiState.commitUpdate(0);
                                }
                            }
                        };
                        final Executor executor = wMShell.mSysUiMainExecutor;
                        final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                        ((HandlerExecutor) desktopTasksController.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addVisibleTasksListener$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                desktopTasksController2.desktopModeTaskRepository.addVisibleTasksListener(visibleTasksListener, executor);
                            }
                        });
                        wMShell.mCommandQueue.addCallback(new CommandQueue.Callbacks(wMShell, desktopMode) { // from class: com.android.systemui.wmshell.WMShell.16
                            public final /* synthetic */ DesktopMode val$desktopMode;

                            {
                                this.val$desktopMode = desktopMode;
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToDesktop(final int i32) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToDesktop$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i4 = i32;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ArrayList runningTasks = desktopTasksController3.shellTaskOrganizer.getRunningTasks(i4);
                                        ArrayList arrayList = new ArrayList();
                                        Iterator it = runningTasks.iterator();
                                        while (it.hasNext()) {
                                            Object next = it.next();
                                            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) next;
                                            if (runningTaskInfo.isFocused && (runningTaskInfo.getWindowingMode() == 1 || runningTaskInfo.getWindowingMode() == 6)) {
                                                if (runningTaskInfo.getActivityType() != 2) {
                                                    arrayList.add(next);
                                                }
                                            }
                                        }
                                        if (!arrayList.isEmpty()) {
                                            int size = arrayList.size();
                                            if (size == 1) {
                                                desktopTasksController3.moveToDesktop(((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                                return;
                                            }
                                            if (size == 2) {
                                                ActivityManager.RunningTaskInfo runningTaskInfo2 = ((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId == ((ActivityManager.RunningTaskInfo) arrayList.get(1)).parentTaskId ? (ActivityManager.RunningTaskInfo) arrayList.get(1) : (ActivityManager.RunningTaskInfo) arrayList.get(0);
                                                Intrinsics.checkNotNull(runningTaskInfo2);
                                                desktopTasksController3.moveToDesktop(runningTaskInfo2, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                            } else {
                                                KtProtoLog.Companion companion = KtProtoLog.Companion;
                                                ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                                                Object[] objArr = {Integer.valueOf(arrayList.size())};
                                                companion.getClass();
                                                KtProtoLog.Companion.w(shellProtoLogGroup, "DesktopTasksController: Cannot enter desktop, expected less than 3 focused tasks but found %d", objArr);
                                            }
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToFullscreen(final int i32) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToFullscreen$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i4 = i32;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i4);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.moveToFullscreenWithAnimation(focusedFreeformTask, focusedFreeformTask.positionInParent, desktopModeTransitionSource2);
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToStageSplit(final int i32, final boolean z) {
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToStageSplit$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i4 = i32;
                                        boolean z2 = z;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i4);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.requestSplit(focusedFreeformTask, z2);
                                        }
                                    }
                                });
                            }
                        });
                        break;
                    case 4:
                        wMShell.initRecentTasks((RecentTasks) obj);
                        break;
                    default:
                        wMShell.getClass();
                        wMShell.mSysUiState.addCallback(new WMShell$$ExternalSyntheticLambda0(wMShell, (EnterSplitGestureHandler) obj, 1));
                        break;
                }
            }
        });
        final int i4 = 3;
        this.mDesktopModeOptional.ifPresent(new Consumer(this) { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda4
            public final /* synthetic */ WMShell f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i4;
                final WMShell wMShell = this.f$0;
                switch (i22) {
                    case 0:
                        wMShell.initPip((Pip) obj);
                        break;
                    case 1:
                        wMShell.initSplitScreen((SplitScreen) obj);
                        break;
                    case 2:
                        wMShell.initOneHanded((OneHanded) obj);
                        break;
                    case 3:
                        DesktopMode desktopMode = (DesktopMode) obj;
                        wMShell.getClass();
                        final DesktopModeTaskRepository.VisibleTasksListener visibleTasksListener = new DesktopModeTaskRepository.VisibleTasksListener() { // from class: com.android.systemui.wmshell.WMShell.15
                            @Override // com.android.wm.shell.desktopmode.DesktopModeTaskRepository.VisibleTasksListener
                            public final void onTasksVisibilityChanged(int i32, int i42) {
                                if (i32 == 0) {
                                    WMShell wMShell2 = WMShell.this;
                                    SysUiState sysUiState = wMShell2.mSysUiState;
                                    sysUiState.setFlag(67108864L, i42 > 0);
                                    wMShell2.mDisplayTracker.getClass();
                                    sysUiState.commitUpdate(0);
                                }
                            }
                        };
                        final Executor executor = wMShell.mSysUiMainExecutor;
                        final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                        ((HandlerExecutor) desktopTasksController.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addVisibleTasksListener$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                desktopTasksController2.desktopModeTaskRepository.addVisibleTasksListener(visibleTasksListener, executor);
                            }
                        });
                        wMShell.mCommandQueue.addCallback(new CommandQueue.Callbacks(wMShell, desktopMode) { // from class: com.android.systemui.wmshell.WMShell.16
                            public final /* synthetic */ DesktopMode val$desktopMode;

                            {
                                this.val$desktopMode = desktopMode;
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToDesktop(final int i32) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToDesktop$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i42 = i32;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ArrayList runningTasks = desktopTasksController3.shellTaskOrganizer.getRunningTasks(i42);
                                        ArrayList arrayList = new ArrayList();
                                        Iterator it = runningTasks.iterator();
                                        while (it.hasNext()) {
                                            Object next = it.next();
                                            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) next;
                                            if (runningTaskInfo.isFocused && (runningTaskInfo.getWindowingMode() == 1 || runningTaskInfo.getWindowingMode() == 6)) {
                                                if (runningTaskInfo.getActivityType() != 2) {
                                                    arrayList.add(next);
                                                }
                                            }
                                        }
                                        if (!arrayList.isEmpty()) {
                                            int size = arrayList.size();
                                            if (size == 1) {
                                                desktopTasksController3.moveToDesktop(((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                                return;
                                            }
                                            if (size == 2) {
                                                ActivityManager.RunningTaskInfo runningTaskInfo2 = ((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId == ((ActivityManager.RunningTaskInfo) arrayList.get(1)).parentTaskId ? (ActivityManager.RunningTaskInfo) arrayList.get(1) : (ActivityManager.RunningTaskInfo) arrayList.get(0);
                                                Intrinsics.checkNotNull(runningTaskInfo2);
                                                desktopTasksController3.moveToDesktop(runningTaskInfo2, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                            } else {
                                                KtProtoLog.Companion companion = KtProtoLog.Companion;
                                                ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                                                Object[] objArr = {Integer.valueOf(arrayList.size())};
                                                companion.getClass();
                                                KtProtoLog.Companion.w(shellProtoLogGroup, "DesktopTasksController: Cannot enter desktop, expected less than 3 focused tasks but found %d", objArr);
                                            }
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToFullscreen(final int i32) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToFullscreen$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i42 = i32;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i42);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.moveToFullscreenWithAnimation(focusedFreeformTask, focusedFreeformTask.positionInParent, desktopModeTransitionSource2);
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToStageSplit(final int i32, final boolean z) {
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToStageSplit$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i42 = i32;
                                        boolean z2 = z;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i42);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.requestSplit(focusedFreeformTask, z2);
                                        }
                                    }
                                });
                            }
                        });
                        break;
                    case 4:
                        wMShell.initRecentTasks((RecentTasks) obj);
                        break;
                    default:
                        wMShell.getClass();
                        wMShell.mSysUiState.addCallback(new WMShell$$ExternalSyntheticLambda0(wMShell, (EnterSplitGestureHandler) obj, 1));
                        break;
                }
            }
        });
        final int i5 = 4;
        this.mRecentTasksOptional.ifPresent(new Consumer(this) { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda4
            public final /* synthetic */ WMShell f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i5;
                final WMShell wMShell = this.f$0;
                switch (i22) {
                    case 0:
                        wMShell.initPip((Pip) obj);
                        break;
                    case 1:
                        wMShell.initSplitScreen((SplitScreen) obj);
                        break;
                    case 2:
                        wMShell.initOneHanded((OneHanded) obj);
                        break;
                    case 3:
                        DesktopMode desktopMode = (DesktopMode) obj;
                        wMShell.getClass();
                        final DesktopModeTaskRepository.VisibleTasksListener visibleTasksListener = new DesktopModeTaskRepository.VisibleTasksListener() { // from class: com.android.systemui.wmshell.WMShell.15
                            @Override // com.android.wm.shell.desktopmode.DesktopModeTaskRepository.VisibleTasksListener
                            public final void onTasksVisibilityChanged(int i32, int i42) {
                                if (i32 == 0) {
                                    WMShell wMShell2 = WMShell.this;
                                    SysUiState sysUiState = wMShell2.mSysUiState;
                                    sysUiState.setFlag(67108864L, i42 > 0);
                                    wMShell2.mDisplayTracker.getClass();
                                    sysUiState.commitUpdate(0);
                                }
                            }
                        };
                        final Executor executor = wMShell.mSysUiMainExecutor;
                        final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                        ((HandlerExecutor) desktopTasksController.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addVisibleTasksListener$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                desktopTasksController2.desktopModeTaskRepository.addVisibleTasksListener(visibleTasksListener, executor);
                            }
                        });
                        wMShell.mCommandQueue.addCallback(new CommandQueue.Callbacks(wMShell, desktopMode) { // from class: com.android.systemui.wmshell.WMShell.16
                            public final /* synthetic */ DesktopMode val$desktopMode;

                            {
                                this.val$desktopMode = desktopMode;
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToDesktop(final int i32) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToDesktop$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i42 = i32;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ArrayList runningTasks = desktopTasksController3.shellTaskOrganizer.getRunningTasks(i42);
                                        ArrayList arrayList = new ArrayList();
                                        Iterator it = runningTasks.iterator();
                                        while (it.hasNext()) {
                                            Object next = it.next();
                                            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) next;
                                            if (runningTaskInfo.isFocused && (runningTaskInfo.getWindowingMode() == 1 || runningTaskInfo.getWindowingMode() == 6)) {
                                                if (runningTaskInfo.getActivityType() != 2) {
                                                    arrayList.add(next);
                                                }
                                            }
                                        }
                                        if (!arrayList.isEmpty()) {
                                            int size = arrayList.size();
                                            if (size == 1) {
                                                desktopTasksController3.moveToDesktop(((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                                return;
                                            }
                                            if (size == 2) {
                                                ActivityManager.RunningTaskInfo runningTaskInfo2 = ((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId == ((ActivityManager.RunningTaskInfo) arrayList.get(1)).parentTaskId ? (ActivityManager.RunningTaskInfo) arrayList.get(1) : (ActivityManager.RunningTaskInfo) arrayList.get(0);
                                                Intrinsics.checkNotNull(runningTaskInfo2);
                                                desktopTasksController3.moveToDesktop(runningTaskInfo2, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                            } else {
                                                KtProtoLog.Companion companion = KtProtoLog.Companion;
                                                ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                                                Object[] objArr = {Integer.valueOf(arrayList.size())};
                                                companion.getClass();
                                                KtProtoLog.Companion.w(shellProtoLogGroup, "DesktopTasksController: Cannot enter desktop, expected less than 3 focused tasks but found %d", objArr);
                                            }
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToFullscreen(final int i32) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToFullscreen$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i42 = i32;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i42);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.moveToFullscreenWithAnimation(focusedFreeformTask, focusedFreeformTask.positionInParent, desktopModeTransitionSource2);
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToStageSplit(final int i32, final boolean z) {
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToStageSplit$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i42 = i32;
                                        boolean z2 = z;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i42);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.requestSplit(focusedFreeformTask, z2);
                                        }
                                    }
                                });
                            }
                        });
                        break;
                    case 4:
                        wMShell.initRecentTasks((RecentTasks) obj);
                        break;
                    default:
                        wMShell.getClass();
                        wMShell.mSysUiState.addCallback(new WMShell$$ExternalSyntheticLambda0(wMShell, (EnterSplitGestureHandler) obj, 1));
                        break;
                }
            }
        });
        NoteTaskInitializer noteTaskInitializer = this.mNoteTaskInitializer;
        noteTaskInitializer.getClass();
        int i6 = DebugLogger.$r8$clinit;
        boolean z = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(NoteTaskInitializer.class).getSimpleName();
        if (noteTaskInitializer.isEnabled && !noteTaskInitializer.optionalBubbles.isEmpty()) {
            CommandQueue commandQueue = noteTaskInitializer.commandQueue;
            NoteTaskInitializer$callbacks$1 noteTaskInitializer$callbacks$1 = noteTaskInitializer.callbacks;
            commandQueue.addCallback((CommandQueue.Callbacks) noteTaskInitializer$callbacks$1);
            noteTaskInitializer.roleManager.addOnRoleHoldersChangedListenerAsUser(noteTaskInitializer.backgroundExecutor, noteTaskInitializer$callbacks$1, UserHandle.ALL);
            UserTrackerImpl userTrackerImpl = (UserTrackerImpl) noteTaskInitializer.userTracker;
            int userId = userTrackerImpl.getUserId();
            KeyguardUpdateMonitor keyguardUpdateMonitor = noteTaskInitializer.keyguardUpdateMonitor;
            if (keyguardUpdateMonitor.mUserIsUnlocked.get(userId)) {
                noteTaskInitializer.controller.updateNoteTaskForCurrentUserAndManagedProfiles();
            }
            keyguardUpdateMonitor.registerCallback(noteTaskInitializer$callbacks$1);
            userTrackerImpl.addCallback(noteTaskInitializer$callbacks$1, noteTaskInitializer.backgroundExecutor);
        }
        final int i7 = 5;
        this.mEnterSplitGestureHandlerOptional.ifPresent(new Consumer(this) { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda4
            public final /* synthetic */ WMShell f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i7;
                final WMShell wMShell = this.f$0;
                switch (i22) {
                    case 0:
                        wMShell.initPip((Pip) obj);
                        break;
                    case 1:
                        wMShell.initSplitScreen((SplitScreen) obj);
                        break;
                    case 2:
                        wMShell.initOneHanded((OneHanded) obj);
                        break;
                    case 3:
                        DesktopMode desktopMode = (DesktopMode) obj;
                        wMShell.getClass();
                        final DesktopModeTaskRepository.VisibleTasksListener visibleTasksListener = new DesktopModeTaskRepository.VisibleTasksListener() { // from class: com.android.systemui.wmshell.WMShell.15
                            @Override // com.android.wm.shell.desktopmode.DesktopModeTaskRepository.VisibleTasksListener
                            public final void onTasksVisibilityChanged(int i32, int i42) {
                                if (i32 == 0) {
                                    WMShell wMShell2 = WMShell.this;
                                    SysUiState sysUiState = wMShell2.mSysUiState;
                                    sysUiState.setFlag(67108864L, i42 > 0);
                                    wMShell2.mDisplayTracker.getClass();
                                    sysUiState.commitUpdate(0);
                                }
                            }
                        };
                        final Executor executor = wMShell.mSysUiMainExecutor;
                        final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                        ((HandlerExecutor) desktopTasksController.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addVisibleTasksListener$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                desktopTasksController2.desktopModeTaskRepository.addVisibleTasksListener(visibleTasksListener, executor);
                            }
                        });
                        wMShell.mCommandQueue.addCallback(new CommandQueue.Callbacks(wMShell, desktopMode) { // from class: com.android.systemui.wmshell.WMShell.16
                            public final /* synthetic */ DesktopMode val$desktopMode;

                            {
                                this.val$desktopMode = desktopMode;
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToDesktop(final int i32) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToDesktop$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i42 = i32;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ArrayList runningTasks = desktopTasksController3.shellTaskOrganizer.getRunningTasks(i42);
                                        ArrayList arrayList = new ArrayList();
                                        Iterator it = runningTasks.iterator();
                                        while (it.hasNext()) {
                                            Object next = it.next();
                                            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) next;
                                            if (runningTaskInfo.isFocused && (runningTaskInfo.getWindowingMode() == 1 || runningTaskInfo.getWindowingMode() == 6)) {
                                                if (runningTaskInfo.getActivityType() != 2) {
                                                    arrayList.add(next);
                                                }
                                            }
                                        }
                                        if (!arrayList.isEmpty()) {
                                            int size = arrayList.size();
                                            if (size == 1) {
                                                desktopTasksController3.moveToDesktop(((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                                return;
                                            }
                                            if (size == 2) {
                                                ActivityManager.RunningTaskInfo runningTaskInfo2 = ((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId == ((ActivityManager.RunningTaskInfo) arrayList.get(1)).parentTaskId ? (ActivityManager.RunningTaskInfo) arrayList.get(1) : (ActivityManager.RunningTaskInfo) arrayList.get(0);
                                                Intrinsics.checkNotNull(runningTaskInfo2);
                                                desktopTasksController3.moveToDesktop(runningTaskInfo2, new WindowContainerTransaction(), desktopModeTransitionSource2);
                                            } else {
                                                KtProtoLog.Companion companion = KtProtoLog.Companion;
                                                ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                                                Object[] objArr = {Integer.valueOf(arrayList.size())};
                                                companion.getClass();
                                                KtProtoLog.Companion.w(shellProtoLogGroup, "DesktopTasksController: Cannot enter desktop, expected less than 3 focused tasks but found %d", objArr);
                                            }
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToFullscreen(final int i32) {
                                final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToFullscreen$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i42 = i32;
                                        DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i42);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.moveToFullscreenWithAnimation(focusedFreeformTask, focusedFreeformTask.positionInParent, desktopModeTransitionSource2);
                                        }
                                    }
                                });
                            }

                            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                            public final void moveFocusedTaskToStageSplit(final int i32, final boolean z2) {
                                final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                ((HandlerExecutor) desktopTasksController2.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToStageSplit$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                                        int i42 = i32;
                                        boolean z22 = z2;
                                        ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i42);
                                        if (focusedFreeformTask != null) {
                                            desktopTasksController3.requestSplit(focusedFreeformTask, z22);
                                        }
                                    }
                                });
                            }
                        });
                        break;
                    case 4:
                        wMShell.initRecentTasks((RecentTasks) obj);
                        break;
                    default:
                        wMShell.getClass();
                        wMShell.mSysUiState.addCallback(new WMShell$$ExternalSyntheticLambda0(wMShell, (EnterSplitGestureHandler) obj, 1));
                        break;
                }
            }
        });
    }
}
