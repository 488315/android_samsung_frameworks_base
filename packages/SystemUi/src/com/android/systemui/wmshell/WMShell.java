package com.android.systemui.wmshell;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.desktopmode.DesktopMode;
import com.android.p038wm.shell.desktopmode.DesktopModeTaskRepository;
import com.android.p038wm.shell.onehanded.OneHanded;
import com.android.p038wm.shell.onehanded.OneHandedController;
import com.android.p038wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda0;
import com.android.p038wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0;
import com.android.p038wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda2;
import com.android.p038wm.shell.onehanded.OneHandedTransitionCallback;
import com.android.p038wm.shell.pip.Pip;
import com.android.p038wm.shell.recents.RecentTasks;
import com.android.p038wm.shell.recents.RecentTasksController;
import com.android.p038wm.shell.splitscreen.EnterSplitGestureHandler;
import com.android.p038wm.shell.splitscreen.SplitScreen;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda3;
import com.android.p038wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0;
import com.android.p038wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda3;
import com.android.p038wm.shell.sysui.ShellInterface;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.notetask.NoteTaskInitializer;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.tracing.FrameProtoTracer;
import com.android.systemui.shared.tracing.ProtoTraceable;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.tracing.ProtoTracer;
import com.android.systemui.tracing.nano.SystemUiTraceProto;
import com.android.systemui.wmshell.WMShell;
import com.samsung.android.systemui.multistar.MultiStarManager;
import com.samsung.android.systemui.multistar.MultiStarSystemProxyImpl;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShell implements CoreStartable, CommandQueue.Callbacks, ProtoTraceable {
    public final CommandQueue mCommandQueue;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final Optional mDesktopModeOptional;
    public final DisplayTracker mDisplayTracker;
    public final Optional mEnterSplitGestureHandlerOptional;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final NoteTaskInitializer mNoteTaskInitializer;
    public final Optional mOneHandedOptional;
    public final Optional mPipOptional;
    public final ProtoTracer mProtoTracer;
    public final Optional mRecentTasksOptional;
    public final ScreenLifecycle mScreenLifecycle;
    public final ShellInterface mShell;
    public final Optional mSplitScreenOptional;
    public final Executor mSysUiMainExecutor;
    public final SysUiState mSysUiState;
    public final UserTracker mUserTracker;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public C374511 mWakefulnessObserver;
    public final C37431 mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.wmshell.WMShell.1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            WMShell.this.mShell.onConfigurationChanged(configuration);
        }
    };
    public final C37492 mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.wmshell.WMShell.2
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wmshell.WMShell$10 */
    public final class C374410 {
        public C374410() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wmshell.WMShell$14 */
    public final class C374814 implements DesktopModeTaskRepository.VisibleTasksListener {
        public C374814() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wmshell.WMShell$9 */
    public final class C37569 implements OneHandedTransitionCallback {
        public C37569() {
        }

        @Override // com.android.p038wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStartFinished(Rect rect) {
            WMShell.this.mSysUiMainExecutor.execute(new WMShell$9$$ExternalSyntheticLambda0(this, 1));
        }

        @Override // com.android.p038wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStartTransition() {
            WMShell.this.mSysUiMainExecutor.execute(new WMShell$9$$ExternalSyntheticLambda0(this, 2));
        }

        @Override // com.android.p038wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStopFinished(Rect rect) {
            WMShell.this.mSysUiMainExecutor.execute(new WMShell$9$$ExternalSyntheticLambda0(this, 0));
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.wmshell.WMShell$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.wmshell.WMShell$2] */
    public WMShell(Context context, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<DesktopMode> optional4, Optional<RecentTasks> optional5, CommandQueue commandQueue, ConfigurationController configurationController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, ScreenLifecycle screenLifecycle, SysUiState sysUiState, ProtoTracer protoTracer, WakefulnessLifecycle wakefulnessLifecycle, UserTracker userTracker, DisplayTracker displayTracker, NoteTaskInitializer noteTaskInitializer, Executor executor, Optional<EnterSplitGestureHandler> optional6) {
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
        this.mProtoTracer = protoTracer;
        this.mUserTracker = userTracker;
        this.mDisplayTracker = displayTracker;
        this.mNoteTaskInitializer = noteTaskInitializer;
        this.mSysUiMainExecutor = executor;
        this.mEnterSplitGestureHandlerOptional = optional6;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ShellInterface shellInterface = this.mShell;
        if (shellInterface.handleCommand(printWriter, strArr)) {
            return;
        }
        shellInterface.dump(printWriter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.wmshell.WMShell$11, java.lang.Object] */
    public void initOneHanded(final OneHanded oneHanded) {
        final C37569 c37569 = new C37569();
        final OneHandedController.OneHandedImpl oneHandedImpl = (OneHandedController.OneHandedImpl) oneHanded;
        final int i = 1;
        ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        OneHandedController.this.mEventCallback = (WMShell.C374410) c37569;
                        break;
                    default:
                        OneHandedController.OneHandedImpl oneHandedImpl2 = oneHandedImpl;
                        ((ArrayList) OneHandedController.this.mDisplayAreaOrganizer.mTransitionCallbacks).add((OneHandedTransitionCallback) c37569);
                        break;
                }
            }
        });
        final C374410 c374410 = new C374410();
        final int i2 = 0;
        ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        OneHandedController.this.mEventCallback = (WMShell.C374410) c374410;
                        break;
                    default:
                        OneHandedController.OneHandedImpl oneHandedImpl2 = oneHandedImpl;
                        ((ArrayList) OneHandedController.this.mDisplayAreaOrganizer.mTransitionCallbacks).add((OneHandedTransitionCallback) c374410);
                        break;
                }
            }
        });
        ?? r0 = new WakefulnessLifecycle.Observer(this) { // from class: com.android.systemui.wmshell.WMShell.11
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0(oneHandedImpl2, false));
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$$ExternalSyntheticLambda0(oneHandedImpl2, 8));
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0(oneHandedImpl2, true));
            }
        };
        this.mWakefulnessObserver = r0;
        this.mWakefulnessLifecycle.addObserver(r0);
        this.mScreenLifecycle.addObserver(new ScreenLifecycle.Observer(this) { // from class: com.android.systemui.wmshell.WMShell.12
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurningOff() {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda2(oneHandedImpl2, 7));
            }
        });
        this.mCommandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.wmshell.WMShell.13
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onCameraLaunchGestureDetected(int i3) {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$$ExternalSyntheticLambda0(oneHandedImpl2, 8));
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void setImeWindowStatus(int i3, IBinder iBinder, int i4, int i5, boolean z) {
                WMShell.this.mDisplayTracker.getClass();
                if (i3 != 0 || (i4 & 2) == 0) {
                    return;
                }
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                ((HandlerExecutor) OneHandedController.this.mMainExecutor).execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda2(oneHandedImpl2, 3));
            }
        });
    }

    public void initPip(final Pip pip) {
        this.mCommandQueue.addCallback(new CommandQueue.Callbacks(this) { // from class: com.android.systemui.wmshell.WMShell.5
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void showPictureInPictureMenu() {
                pip.showPictureInPictureMenu();
            }
        });
        this.mSysUiState.addCallback(new WMShell$$ExternalSyntheticLambda0(this, pip, 0));
    }

    public void initRecentTasks(RecentTasks recentTasks) {
        CommandQueue commandQueue = this.mCommandQueue;
        Objects.requireNonNull(commandQueue);
        final WMShell$$ExternalSyntheticLambda2 wMShell$$ExternalSyntheticLambda2 = new WMShell$$ExternalSyntheticLambda2(commandQueue, 6);
        final RecentTasksController.RecentTasksImpl recentTasksImpl = (RecentTasksController.RecentTasksImpl) recentTasks;
        ShellExecutor shellExecutor = RecentTasksController.this.mMainExecutor;
        final Executor executor = this.mSysUiMainExecutor;
        ((HandlerExecutor) shellExecutor).execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RecentTasksController.RecentTasksImpl recentTasksImpl2 = RecentTasksController.RecentTasksImpl.this;
                Executor executor2 = executor;
                Consumer consumer = wMShell$$ExternalSyntheticLambda2;
                RecentsTransitionHandler recentsTransitionHandler = RecentTasksController.this.mTransitionHandler;
                if (recentsTransitionHandler == null) {
                    return;
                }
                recentsTransitionHandler.mStateListeners.add(new RecentsTransitionStateListener(recentTasksImpl2, executor2, consumer) { // from class: com.android.wm.shell.recents.RecentTasksController.RecentTasksImpl.1
                    public final /* synthetic */ Executor val$executor;
                    public final /* synthetic */ Consumer val$listener;

                    public C40861(RecentTasksImpl recentTasksImpl22, Executor executor22, Consumer consumer2) {
                        this.val$executor = executor22;
                        this.val$listener = consumer2;
                    }

                    @Override // com.android.p038wm.shell.recents.RecentsTransitionStateListener
                    public final void onAnimationStateChanged(final boolean z) {
                        final Consumer consumer2 = this.val$listener;
                        this.val$executor.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer2.accept(Boolean.valueOf(z));
                            }
                        });
                    }
                });
            }
        });
    }

    public void initSplitScreen(final SplitScreen splitScreen) {
        this.mWakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer(this) { // from class: com.android.systemui.wmshell.WMShell.6
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                SplitScreenController splitScreenController = SplitScreenController.this;
                ((HandlerExecutor) splitScreenController.mMainExecutor).execute(new SplitScreenController$$ExternalSyntheticLambda3(2, splitScreenController));
            }
        });
        CommandQueue.Callbacks callbacks = new CommandQueue.Callbacks(this) { // from class: com.android.systemui.wmshell.WMShell.7
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void goToFullscreenFromSplit() {
                SplitScreenController splitScreenController = SplitScreenController.this;
                ((HandlerExecutor) splitScreenController.mMainExecutor).execute(new SplitScreenController$$ExternalSyntheticLambda3(3, splitScreenController));
            }
        };
        CommandQueue commandQueue = this.mCommandQueue;
        commandQueue.addCallback(callbacks);
        BooleanSupplier booleanSupplier = new BooleanSupplier() { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda1
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) WMShell.this.mKeyguardStateController;
                return keyguardStateControllerImpl.mOccluded && keyguardStateControllerImpl.mShowing;
            }
        };
        SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) splitScreen;
        ((HandlerExecutor) SplitScreenController.this.mMainExecutor).execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda3(splitScreenImpl, booleanSupplier, 2));
        commandQueue.addCallback(new CommandQueue.Callbacks(this) { // from class: com.android.systemui.wmshell.WMShell.8
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void toggleSplitScreen() {
                SplitScreenController.SplitScreenImpl splitScreenImpl2 = (SplitScreenController.SplitScreenImpl) splitScreen;
                ((HandlerExecutor) SplitScreenController.this.mMainExecutor).execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0(splitScreenImpl2, 1));
            }
        });
        MultiStarManager multiStarManager = (MultiStarManager) MultiStarManager.sInstance.get();
        multiStarManager.getClass();
        Log.d("MultiStarManager", "Create");
        multiStarManager.mMultiStarSystemFacade = new MultiStarSystemProxyImpl(this.mContext, splitScreen);
        ((SPluginManager) Dependency.get(SPluginManager.class)).addPluginListener((SPluginListener) new SPluginListener() { // from class: com.samsung.android.systemui.multistar.MultiStarManager.2
            public C47812() {
            }

            @Override // com.samsung.systemui.splugins.SPluginListener
            public final void onPluginConnected(SPlugin sPlugin, Context context) {
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
    public final void start() {
        Context context = this.mContext;
        this.mShell.onConfigurationChanged(context.getResources().getConfiguration());
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateCallback);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, context.getMainExecutor());
        FrameProtoTracer frameProtoTracer = this.mProtoTracer.mProtoTracer;
        synchronized (frameProtoTracer.mLock) {
            frameProtoTracer.mTraceables.add(this);
        }
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mPipOptional.ifPresent(new WMShell$$ExternalSyntheticLambda2(this, 0));
        this.mSplitScreenOptional.ifPresent(new WMShell$$ExternalSyntheticLambda2(this, 1));
        this.mOneHandedOptional.ifPresent(new WMShell$$ExternalSyntheticLambda2(this, 2));
        this.mDesktopModeOptional.ifPresent(new WMShell$$ExternalSyntheticLambda2(this, 3));
        this.mRecentTasksOptional.ifPresent(new WMShell$$ExternalSyntheticLambda2(this, 4));
        this.mNoteTaskInitializer.getClass();
        this.mEnterSplitGestureHandlerOptional.ifPresent(new WMShell$$ExternalSyntheticLambda2(this, 5));
    }

    @Override // com.android.systemui.shared.tracing.ProtoTraceable
    public final /* bridge */ /* synthetic */ void writeToProto(SystemUiTraceProto systemUiTraceProto) {
    }
}
