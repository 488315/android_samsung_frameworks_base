package com.android.systemui.dagger;

import android.R;
import com.android.p038wm.shell.keyguard.KeyguardTransitions;
import com.android.p038wm.shell.sysui.ShellInterface;
import com.android.p038wm.shell.transition.ShellTransitions;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.BootAnimationFinishedTrigger;
import com.android.systemui.BootCompleteCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.InitController;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.doze.AODIntentService;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plank.protocol.TestProtocolProvider;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.unfold.FoldStateLogger;
import com.android.systemui.unfold.FoldStateLoggingProvider;
import com.android.systemui.unfold.FoldStateLoggingProviderImpl;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.UnfoldLatencyTracker;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.UnfoldTransitionWallpaperController;
import com.android.systemui.unfold.UnfoldTransitionWallpaperController.TransitionListener;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.RotationChangeProvider;
import com.android.systemui.unfold.updates.RotationChangeProvider$addCallback$1;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider$rotationListener$1;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface SysUIComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Builder {
        SysUIComponent build();

        Builder setBackAnimation(Optional optional);

        Builder setBubbles(Optional optional);

        Builder setDesktopMode(Optional optional);

        Builder setDisplayAreaHelper(Optional optional);

        Builder setDisplayController(Optional optional);

        Builder setEnterSplitGestureHandler(Optional optional);

        Builder setKeyguardTransitions(KeyguardTransitions keyguardTransitions);

        Builder setOneHanded(Optional optional);

        Builder setPip(Optional optional);

        Builder setRecentTasks(Optional optional);

        Builder setShell(ShellInterface shellInterface);

        Builder setSplitScreen(Optional optional);

        Builder setSplitScreenController(Optional optional);

        Builder setStartingSurface(Optional optional);

        Builder setTaskViewFactory(Optional optional);

        Builder setTransitions(ShellTransitions shellTransitions);
    }

    Dependency createDependency();

    DumpManager createDumpManager();

    ConfigurationController getConfigurationController();

    Optional getFoldStateLogger();

    Optional getFoldStateLoggingProvider();

    InitController getInitController();

    Optional getMediaMuteAwaitConnectionCli();

    Optional getNaturalRotationUnfoldProgressProvider();

    Optional getNearbyMediaDevicesManager();

    Map getPerUserStartables();

    Map getPostStartables();

    Map getPreStartables();

    Map getSafeUIStartables();

    Map getStartables();

    Optional getSysUIUnfoldComponent();

    UnfoldLatencyTracker getUnfoldLatencyTracker();

    Optional getUnfoldTransitionProgressForwarder();

    Optional getUnfoldTransitionProgressProvider();

    default void init() {
        final int i = 0;
        getSysUIUnfoldComponent().ifPresent(new Consumer() { // from class: com.android.systemui.dagger.SysUIComponent$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i) {
                    case 0:
                        UnfoldTransitionWallpaperController unfoldTransitionWallpaperController = ((SysUIUnfoldComponent) obj).getUnfoldTransitionWallpaperController();
                        unfoldTransitionWallpaperController.getClass();
                        unfoldTransitionWallpaperController.unfoldTransitionProgressProvider.addCallback(unfoldTransitionWallpaperController.new TransitionListener());
                        break;
                    case 1:
                        NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider = (NaturalRotationUnfoldProgressProvider) obj;
                        NaturalRotationUnfoldProgressProvider$rotationListener$1 naturalRotationUnfoldProgressProvider$rotationListener$1 = naturalRotationUnfoldProgressProvider.rotationListener;
                        RotationChangeProvider rotationChangeProvider = naturalRotationUnfoldProgressProvider.rotationChangeProvider;
                        rotationChangeProvider.getClass();
                        rotationChangeProvider.mainHandler.post(new RotationChangeProvider$addCallback$1(rotationChangeProvider, naturalRotationUnfoldProgressProvider$rotationListener$1));
                        naturalRotationUnfoldProgressProvider$rotationListener$1.onRotationChanged(naturalRotationUnfoldProgressProvider.context.getDisplay().getRotation());
                        break;
                    case 2:
                        FoldStateLoggingProviderImpl foldStateLoggingProviderImpl = (FoldStateLoggingProviderImpl) ((FoldStateLoggingProvider) obj);
                        DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) foldStateLoggingProviderImpl.foldStateProvider;
                        deviceFoldStateProvider.addCallback(foldStateLoggingProviderImpl);
                        deviceFoldStateProvider.start();
                        break;
                    default:
                        FoldStateLogger foldStateLogger = (FoldStateLogger) obj;
                        ((FoldStateLoggingProviderImpl) foldStateLogger.foldStateLoggingProvider).addCallback(foldStateLogger);
                        break;
                }
            }
        });
        final int i2 = 1;
        getNaturalRotationUnfoldProgressProvider().ifPresent(new Consumer() { // from class: com.android.systemui.dagger.SysUIComponent$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i2) {
                    case 0:
                        UnfoldTransitionWallpaperController unfoldTransitionWallpaperController = ((SysUIUnfoldComponent) obj).getUnfoldTransitionWallpaperController();
                        unfoldTransitionWallpaperController.getClass();
                        unfoldTransitionWallpaperController.unfoldTransitionProgressProvider.addCallback(unfoldTransitionWallpaperController.new TransitionListener());
                        break;
                    case 1:
                        NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider = (NaturalRotationUnfoldProgressProvider) obj;
                        NaturalRotationUnfoldProgressProvider$rotationListener$1 naturalRotationUnfoldProgressProvider$rotationListener$1 = naturalRotationUnfoldProgressProvider.rotationListener;
                        RotationChangeProvider rotationChangeProvider = naturalRotationUnfoldProgressProvider.rotationChangeProvider;
                        rotationChangeProvider.getClass();
                        rotationChangeProvider.mainHandler.post(new RotationChangeProvider$addCallback$1(rotationChangeProvider, naturalRotationUnfoldProgressProvider$rotationListener$1));
                        naturalRotationUnfoldProgressProvider$rotationListener$1.onRotationChanged(naturalRotationUnfoldProgressProvider.context.getDisplay().getRotation());
                        break;
                    case 2:
                        FoldStateLoggingProviderImpl foldStateLoggingProviderImpl = (FoldStateLoggingProviderImpl) ((FoldStateLoggingProvider) obj);
                        DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) foldStateLoggingProviderImpl.foldStateProvider;
                        deviceFoldStateProvider.addCallback(foldStateLoggingProviderImpl);
                        deviceFoldStateProvider.start();
                        break;
                    default:
                        FoldStateLogger foldStateLogger = (FoldStateLogger) obj;
                        ((FoldStateLoggingProviderImpl) foldStateLogger.foldStateLoggingProvider).addCallback(foldStateLogger);
                        break;
                }
            }
        });
        getMediaMuteAwaitConnectionCli();
        getNearbyMediaDevicesManager();
        UnfoldLatencyTracker unfoldLatencyTracker = getUnfoldLatencyTracker();
        if (!(unfoldLatencyTracker.context.getResources().getIntArray(R.array.networks_not_clear_data).length == 0)) {
            unfoldLatencyTracker.deviceStateManager.registerCallback(unfoldLatencyTracker.uiBgExecutor, unfoldLatencyTracker.foldStateListener);
            unfoldLatencyTracker.screenLifecycle.addObserver(unfoldLatencyTracker);
            Optional optional = unfoldLatencyTracker.transitionProgressProvider;
            if (optional.isPresent()) {
                ((UnfoldTransitionProgressProvider) optional.get()).addCallback(unfoldLatencyTracker);
            }
        }
        final int i3 = 2;
        getFoldStateLoggingProvider().ifPresent(new Consumer() { // from class: com.android.systemui.dagger.SysUIComponent$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i3) {
                    case 0:
                        UnfoldTransitionWallpaperController unfoldTransitionWallpaperController = ((SysUIUnfoldComponent) obj).getUnfoldTransitionWallpaperController();
                        unfoldTransitionWallpaperController.getClass();
                        unfoldTransitionWallpaperController.unfoldTransitionProgressProvider.addCallback(unfoldTransitionWallpaperController.new TransitionListener());
                        break;
                    case 1:
                        NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider = (NaturalRotationUnfoldProgressProvider) obj;
                        NaturalRotationUnfoldProgressProvider$rotationListener$1 naturalRotationUnfoldProgressProvider$rotationListener$1 = naturalRotationUnfoldProgressProvider.rotationListener;
                        RotationChangeProvider rotationChangeProvider = naturalRotationUnfoldProgressProvider.rotationChangeProvider;
                        rotationChangeProvider.getClass();
                        rotationChangeProvider.mainHandler.post(new RotationChangeProvider$addCallback$1(rotationChangeProvider, naturalRotationUnfoldProgressProvider$rotationListener$1));
                        naturalRotationUnfoldProgressProvider$rotationListener$1.onRotationChanged(naturalRotationUnfoldProgressProvider.context.getDisplay().getRotation());
                        break;
                    case 2:
                        FoldStateLoggingProviderImpl foldStateLoggingProviderImpl = (FoldStateLoggingProviderImpl) ((FoldStateLoggingProvider) obj);
                        DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) foldStateLoggingProviderImpl.foldStateProvider;
                        deviceFoldStateProvider.addCallback(foldStateLoggingProviderImpl);
                        deviceFoldStateProvider.start();
                        break;
                    default:
                        FoldStateLogger foldStateLogger = (FoldStateLogger) obj;
                        ((FoldStateLoggingProviderImpl) foldStateLogger.foldStateLoggingProvider).addCallback(foldStateLogger);
                        break;
                }
            }
        });
        final int i4 = 3;
        getFoldStateLogger().ifPresent(new Consumer() { // from class: com.android.systemui.dagger.SysUIComponent$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i4) {
                    case 0:
                        UnfoldTransitionWallpaperController unfoldTransitionWallpaperController = ((SysUIUnfoldComponent) obj).getUnfoldTransitionWallpaperController();
                        unfoldTransitionWallpaperController.getClass();
                        unfoldTransitionWallpaperController.unfoldTransitionProgressProvider.addCallback(unfoldTransitionWallpaperController.new TransitionListener());
                        break;
                    case 1:
                        NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider = (NaturalRotationUnfoldProgressProvider) obj;
                        NaturalRotationUnfoldProgressProvider$rotationListener$1 naturalRotationUnfoldProgressProvider$rotationListener$1 = naturalRotationUnfoldProgressProvider.rotationListener;
                        RotationChangeProvider rotationChangeProvider = naturalRotationUnfoldProgressProvider.rotationChangeProvider;
                        rotationChangeProvider.getClass();
                        rotationChangeProvider.mainHandler.post(new RotationChangeProvider$addCallback$1(rotationChangeProvider, naturalRotationUnfoldProgressProvider$rotationListener$1));
                        naturalRotationUnfoldProgressProvider$rotationListener$1.onRotationChanged(naturalRotationUnfoldProgressProvider.context.getDisplay().getRotation());
                        break;
                    case 2:
                        FoldStateLoggingProviderImpl foldStateLoggingProviderImpl = (FoldStateLoggingProviderImpl) ((FoldStateLoggingProvider) obj);
                        DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) foldStateLoggingProviderImpl.foldStateProvider;
                        deviceFoldStateProvider.addCallback(foldStateLoggingProviderImpl);
                        deviceFoldStateProvider.start();
                        break;
                    default:
                        FoldStateLogger foldStateLogger = (FoldStateLogger) obj;
                        ((FoldStateLoggingProviderImpl) foldStateLogger.foldStateLoggingProvider).addCallback(foldStateLogger);
                        break;
                }
            }
        });
        getUnfoldTransitionProgressProvider().ifPresent(new SysUIComponent$$ExternalSyntheticLambda1(this, i));
    }

    void inject(SystemUIAppComponentFactoryBase systemUIAppComponentFactoryBase);

    void inject(AODIntentService aODIntentService);

    void inject(TestProtocolProvider testProtocolProvider);

    void inject(KeyguardSecAffordanceView keyguardSecAffordanceView);

    BootAnimationFinishedCacheImpl provideBootAnimationFinishedImpl();

    BootAnimationFinishedTrigger provideBootAnimationFinishedTrigger();

    BootCompleteCacheImpl provideBootCacheImpl();
}
