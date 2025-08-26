package com.android.systemui.wmshell;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.UserHandle;
import android.util.Log;
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
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.notetask.NoteTaskInitializer;
import com.android.systemui.notetask.NoteTaskInitializer$callbacks$1;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.wmshell.WMShell;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda2;
import com.android.wm.shell.desktopmode.DesktopMode;
import com.android.wm.shell.onehanded.OneHanded;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda7;
import com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0;
import com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda3;
import com.android.wm.shell.onehanded.OneHandedTransitionCallback;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.recents.RecentTasks;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda1;
import com.android.wm.shell.splitscreen.EnterSplitGestureHandler;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda7;
import com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda1;
import com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda2;
import com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda6;
import com.android.wm.shell.sysui.ShellInterface;
import com.samsung.android.systemui.multistar.MultiStarManager;
import com.samsung.android.systemui.multistar.MultiStarSystemProxyImpl;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes3.dex */
public final class WMShell implements CoreStartable, CommandQueue.Callbacks {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommandQueue mCommandQueue;
    public final CommandRegistry mCommandRegistry;
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
    public AnonymousClass14 mWakefulnessObserver;
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
        public final void onBeforeUserSwitching(int i) {
            WMShell.this.mShell.onBeforeUserSwitching(i);
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onProfilesChanged(List list) {
            WMShell.this.mShell.onUserProfilesChanged(list);
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            WMShell.this.mShell.onUserChanged(i, context);
        }
    };
    public final AnonymousClass5 mShellCommand = new Command() { // from class: com.android.systemui.wmshell.WMShell.5
        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            ArrayList arrayList = new ArrayList(list);
            arrayList.add(0, "WMShell");
            int i = WMShell.$r8$clinit;
            Log.d("com.android.systemui.wmshell.WMShell", "Command with args: " + String.join(", ", arrayList));
            if (WMShell.this.mShell.handleCommand(printWriter, (String[]) arrayList.toArray(new String[0]))) {
                return;
            }
            printWriter.println("Invalid wm shell command: " + String.join(", ", list));
        }
    };

    /* renamed from: com.android.systemui.wmshell.WMShell$10, reason: invalid class name */
    public class AnonymousClass10 {
        public AnonymousClass10() {
        }
    }

    /* renamed from: com.android.systemui.wmshell.WMShell$12, reason: invalid class name */
    public class AnonymousClass12 implements OneHandedTransitionCallback {
        public AnonymousClass12() {
        }

        @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStartFinished(Rect rect) {
            WMShell.this.mSysUiMainExecutor.execute(new WMShell$12$$ExternalSyntheticLambda0(this, 1));
        }

        @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStartTransition() {
            WMShell.this.mSysUiMainExecutor.execute(new WMShell$12$$ExternalSyntheticLambda0(this, 2));
        }

        @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStopFinished(Rect rect) {
            WMShell.this.mSysUiMainExecutor.execute(new WMShell$12$$ExternalSyntheticLambda0(this, 0));
        }
    }

    /* renamed from: com.android.systemui.wmshell.WMShell$13, reason: invalid class name */
    public class AnonymousClass13 {
        public AnonymousClass13() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.wmshell.WMShell$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.wmshell.WMShell$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.wmshell.WMShell$5] */
    public WMShell(Context context, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<DesktopMode> optional4, Optional<RecentTasks> optional5, CommandQueue commandQueue, CommandRegistry commandRegistry, ConfigurationController configurationController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, ScreenLifecycle screenLifecycle, SysUiState sysUiState, WakefulnessLifecycle wakefulnessLifecycle, UserTracker userTracker, DisplayTracker displayTracker, NoteTaskInitializer noteTaskInitializer, CommunalTransitionViewModel communalTransitionViewModel, JavaAdapter javaAdapter, Executor executor, Optional<EnterSplitGestureHandler> optional6) {
        this.mContext = context;
        this.mShell = shellInterface;
        this.mCommandQueue = commandQueue;
        this.mCommandRegistry = commandRegistry;
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.wmshell.WMShell$14, java.lang.Object] */
    public void initOneHanded(final OneHanded oneHanded) {
        final AnonymousClass12 anonymousClass12 = new AnonymousClass12();
        final OneHandedController.OneHandedImpl oneHandedImpl = (OneHandedController.OneHandedImpl) oneHanded;
        final int i = 0;
        OneHandedController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        OneHandedController.OneHandedImpl oneHandedImpl2 = oneHandedImpl;
                        ((ArrayList) OneHandedController.this.mDisplayAreaOrganizer.mTransitionCallbacks).add((WMShell.AnonymousClass12) anonymousClass12);
                        break;
                    default:
                        OneHandedController.this.mEventCallback = (WMShell.AnonymousClass13) anonymousClass12;
                        break;
                }
            }
        });
        final AnonymousClass13 anonymousClass13 = new AnonymousClass13();
        final int i2 = 1;
        OneHandedController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        OneHandedController.OneHandedImpl oneHandedImpl2 = oneHandedImpl;
                        ((ArrayList) OneHandedController.this.mDisplayAreaOrganizer.mTransitionCallbacks).add((WMShell.AnonymousClass12) anonymousClass13);
                        break;
                    default:
                        OneHandedController.this.mEventCallback = (WMShell.AnonymousClass13) anonymousClass13;
                        break;
                }
            }
        });
        ?? r0 = new WakefulnessLifecycle.Observer(this) { // from class: com.android.systemui.wmshell.WMShell.14
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                OneHandedController.this.mMainExecutor.execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda3(oneHandedImpl2, false));
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                OneHandedController.this.mMainExecutor.execute(new OneHandedController$$ExternalSyntheticLambda7(oneHandedImpl2, 8));
                OneHandedController.this.mMainExecutor.execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda3(oneHandedImpl2, true));
            }
        };
        this.mWakefulnessObserver = r0;
        this.mWakefulnessLifecycle.addObserver(r0);
        this.mScreenLifecycle.addObserver(new ScreenLifecycle.Observer(this) { // from class: com.android.systemui.wmshell.WMShell.15
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurningOff() {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                OneHandedController.this.mMainExecutor.execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0(oneHandedImpl2, 7));
            }
        });
        this.mCommandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.wmshell.WMShell.16
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onCameraLaunchGestureDetected(int i3) {
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                OneHandedController.this.mMainExecutor.execute(new OneHandedController$$ExternalSyntheticLambda7(oneHandedImpl2, 8));
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void setImeWindowStatus(int i3, int i4, int i5, boolean z) {
                WMShell.this.mDisplayTracker.getClass();
                if (i3 != 0 || (i4 & 2) == 0) {
                    return;
                }
                OneHandedController.OneHandedImpl oneHandedImpl2 = (OneHandedController.OneHandedImpl) oneHanded;
                OneHandedController.this.mMainExecutor.execute(new OneHandedController$OneHandedImpl$$ExternalSyntheticLambda0(oneHandedImpl2, 3));
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.wmshell.WMShell$7] */
    public void initPip(final Pip pip) {
        this.mCommandQueue.addCallback(new CommandQueue.Callbacks(this) { // from class: com.android.systemui.wmshell.WMShell.6
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void showPictureInPictureMenu() {
                pip.showPictureInPictureMenu();
            }
        });
        pip.registerPipTransitionCallback(new PipTransitionController.PipTransitionCallback() { // from class: com.android.systemui.wmshell.WMShell.7
            @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
            public final void onPipTransitionFinished(int i) {
                WMShell wMShell = WMShell.this;
                SysUiState flag = wMShell.mSysUiState.setFlag(17179869184L, false);
                wMShell.mDisplayTracker.getClass();
                ((SysUiStateImpl) flag).commitUpdate();
            }

            @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
            public final void onPipTransitionStarted(int i, Rect rect) {
                WMShell wMShell = WMShell.this;
                SysUiState flag = wMShell.mSysUiState.setFlag(17179869184L, true);
                wMShell.mDisplayTracker.getClass();
                ((SysUiStateImpl) flag).commitUpdate();
            }

            @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
            public final void onPipTransitionCanceled(int i) {
            }
        }, this.mSysUiMainExecutor);
        int i = 0;
        pip.addOnIsInPipStateChangedListener(new WMShell$$ExternalSyntheticLambda0(this, i));
        ((SysUiStateImpl) this.mSysUiState).addCallback(new WMShell$$ExternalSyntheticLambda1(this, pip, i));
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda2] */
    public void initRecentTasks(final RecentTasks recentTasks) {
        final Executor executor = this.mSysUiMainExecutor;
        final CommandQueue commandQueue = this.mCommandQueue;
        Objects.requireNonNull(commandQueue);
        final int i = 0;
        final ?? r2 = new Consumer() { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                Object obj2 = commandQueue;
                switch (i2) {
                    case 0:
                        CommandQueue commandQueue2 = (CommandQueue) obj2;
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        synchronized (commandQueue2.mLock) {
                            commandQueue2.mHandler.obtainMessage(3080192, zBooleanValue ? 1 : 0, 0).sendToTarget();
                        }
                        return;
                    default:
                        RecentTasksController.RecentTasksImpl recentTasksImpl = (RecentTasksController.RecentTasksImpl) ((RecentTasks) obj2);
                        RecentTasksController.this.mMainExecutor.execute(new RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda1(1, recentTasksImpl, (Color) obj));
                        return;
                }
            }
        };
        final RecentTasksController.RecentTasksImpl recentTasksImpl = (RecentTasksController.RecentTasksImpl) recentTasks;
        RecentTasksController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                RecentTasksController.RecentTasksImpl recentTasksImpl2 = recentTasksImpl;
                Executor executor2 = executor;
                WMShell$$ExternalSyntheticLambda2 wMShell$$ExternalSyntheticLambda2 = r2;
                RecentsTransitionHandler recentsTransitionHandler = RecentTasksController.this.mTransitionHandler;
                if (recentsTransitionHandler == null) {
                    return;
                }
                recentsTransitionHandler.mStateListeners.add(new RecentsTransitionStateListener(recentTasksImpl2, executor2, wMShell$$ExternalSyntheticLambda2) { // from class: com.android.wm.shell.recents.RecentTasksController.RecentTasksImpl.1
                    public final /* synthetic */ Executor val$executor;
                    public final /* synthetic */ Consumer val$listener;

                    public AnonymousClass1(RecentTasksImpl recentTasksImpl22, Executor executor22, Consumer wMShell$$ExternalSyntheticLambda22) {
                        this.val$executor = executor22;
                        this.val$listener = wMShell$$ExternalSyntheticLambda22;
                    }

                    @Override // com.android.wm.shell.recents.RecentsTransitionStateListener
                    public final void onTransitionStateChanged(final int i2) {
                        Executor executor3 = this.val$executor;
                        final Consumer consumer = this.val$listener;
                        executor3.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(Boolean.valueOf(i2 >= 3));
                            }
                        });
                    }
                });
            }
        });
        final int i2 = 1;
        this.mJavaAdapter.alwaysCollectFlow(this.mCommunalTransitionViewModel.recentsBackgroundColor, new Consumer() { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                Object obj2 = recentTasks;
                switch (i22) {
                    case 0:
                        CommandQueue commandQueue2 = (CommandQueue) obj2;
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        synchronized (commandQueue2.mLock) {
                            commandQueue2.mHandler.obtainMessage(3080192, zBooleanValue ? 1 : 0, 0).sendToTarget();
                        }
                        return;
                    default:
                        RecentTasksController.RecentTasksImpl recentTasksImpl2 = (RecentTasksController.RecentTasksImpl) ((RecentTasks) obj2);
                        RecentTasksController.this.mMainExecutor.execute(new RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda1(1, recentTasksImpl2, (Color) obj));
                        return;
                }
            }
        });
    }

    public void initSplitScreen(final SplitScreen splitScreen) {
        this.mWakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer(this) { // from class: com.android.systemui.wmshell.WMShell.8
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                SplitScreenController splitScreenController = SplitScreenController.this;
                splitScreenController.mMainExecutor.execute(new SplitScreenController$$ExternalSyntheticLambda7(splitScreenController, 2));
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                SplitScreenController splitScreenController = SplitScreenController.this;
                splitScreenController.mMainExecutor.execute(new SplitScreenController$$ExternalSyntheticLambda7(splitScreenController, 1));
            }
        });
        CommandQueue.Callbacks callbacks = new CommandQueue.Callbacks(this) { // from class: com.android.systemui.wmshell.WMShell.9
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void moveFocusedTaskToFullscreen(int i) {
                SplitScreenController splitScreenController = SplitScreenController.this;
                splitScreenController.mMainExecutor.execute(new SplitScreenController$$ExternalSyntheticLambda7(splitScreenController, 3));
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void setSplitscreenFocus(boolean z) {
                SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) splitScreen;
                SplitScreenController.this.mMainExecutor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda6(splitScreenImpl, z, 1));
            }
        };
        CommandQueue commandQueue = this.mCommandQueue;
        commandQueue.addCallback(callbacks);
        final SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) splitScreen;
        splitScreenImpl.registerSplitAnimationListener(new AnonymousClass10(), this.mSysUiMainExecutor);
        SplitScreenController.this.mMainExecutor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda1(splitScreenImpl, new WMShell$$ExternalSyntheticLambda4(this), 2));
        commandQueue.addCallback(new CommandQueue.Callbacks(this) { // from class: com.android.systemui.wmshell.WMShell.11
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void toggleSplitScreen() {
                SplitScreenController.SplitScreenImpl splitScreenImpl2 = (SplitScreenController.SplitScreenImpl) splitScreenImpl;
                SplitScreenController.this.mMainExecutor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda2(splitScreenImpl2, 1));
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
        this.mCommandRegistry.registerCommand("wmshell-passthrough", new Function0() { // from class: com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.mShellCommand;
            }
        });
        this.mPipOptional.ifPresent(new WMShell$$ExternalSyntheticLambda0(this, 3));
        this.mSplitScreenOptional.ifPresent(new WMShell$$ExternalSyntheticLambda0(this, 4));
        this.mOneHandedOptional.ifPresent(new WMShell$$ExternalSyntheticLambda0(this, 5));
        this.mDesktopModeOptional.ifPresent(new WMShell$$ExternalSyntheticLambda0(this, 6));
        this.mRecentTasksOptional.ifPresent(new WMShell$$ExternalSyntheticLambda0(this, 1));
        NoteTaskInitializer noteTaskInitializer = this.mNoteTaskInitializer;
        noteTaskInitializer.getClass();
        DebugLogger debugLogger = DebugLogger.INSTANCE;
        boolean z = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(NoteTaskInitializer.class).getSimpleName();
        if (noteTaskInitializer.isEnabled && !noteTaskInitializer.optionalBubbles.isEmpty()) {
            InputManager inputManager = noteTaskInitializer.inputManager;
            List listSingletonList = Collections.singletonList(33);
            NoteTaskInitializer$callbacks$1 noteTaskInitializer$callbacks$1 = noteTaskInitializer.callbacks;
            inputManager.registerKeyGestureEventHandler(listSingletonList, noteTaskInitializer$callbacks$1);
            noteTaskInitializer.roleManager.addOnRoleHoldersChangedListenerAsUser(noteTaskInitializer.backgroundExecutor, noteTaskInitializer$callbacks$1, UserHandle.ALL);
            UserTrackerImpl userTrackerImpl = (UserTrackerImpl) noteTaskInitializer.userTracker;
            int userId = userTrackerImpl.getUserId();
            KeyguardUpdateMonitor keyguardUpdateMonitor = noteTaskInitializer.keyguardUpdateMonitor;
            if (keyguardUpdateMonitor.mUserManager.isUserUnlocked(userId)) {
                noteTaskInitializer.controller.updateNoteTaskForCurrentUserAndManagedProfiles();
            }
            keyguardUpdateMonitor.registerCallback(noteTaskInitializer$callbacks$1);
            userTrackerImpl.addCallback(noteTaskInitializer$callbacks$1, noteTaskInitializer.backgroundExecutor);
        }
        this.mEnterSplitGestureHandlerOptional.ifPresent(new WMShell$$ExternalSyntheticLambda0(this, 2));
    }
}
