package com.android.systemui.unfold;

import android.R;
import android.view.Display;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.unfold.UnfoldTransitionWallpaperController;
import com.android.systemui.unfold.progress.UnfoldTransitionProgressForwarder;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.RotationChangeProvider;
import com.android.systemui.unfold.updates.RotationChangeProvider$addCallback$1;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider$rotationListener$1;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UnfoldInitializationStartable implements CoreStartable {
    public final Optional foldStateLoggerOptional;
    public final Optional foldStateLoggingProviderOptional;
    public final Optional unfoldBgTransitionProgressProviderOptional;
    public final Optional unfoldComponentOptional;
    public final Optional unfoldTransitionProgressForwarder;

    public UnfoldInitializationStartable(Optional<SysUIUnfoldComponent> optional, Optional<FoldStateLoggingProvider> optional2, Optional<FoldStateLogger> optional3, Optional<UnfoldTransitionProgressProvider> optional4, Optional<UnfoldTransitionProgressProvider> optional5, Optional<UnfoldTransitionProgressForwarder> optional6) {
        this.unfoldComponentOptional = optional;
        this.foldStateLoggingProviderOptional = optional2;
        this.foldStateLoggerOptional = optional3;
        this.unfoldBgTransitionProgressProviderOptional = optional4;
        this.unfoldTransitionProgressForwarder = optional6;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.unfoldComponentOptional.ifPresent(new Consumer() { // from class: com.android.systemui.unfold.UnfoldInitializationStartable$start$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SysUIUnfoldComponent sysUIUnfoldComponent = (SysUIUnfoldComponent) obj;
                UnfoldTransitionWallpaperController unfoldTransitionWallpaperController = sysUIUnfoldComponent.getUnfoldTransitionWallpaperController();
                unfoldTransitionWallpaperController.getClass();
                unfoldTransitionWallpaperController.unfoldTransitionProgressProvider.addCallback(new UnfoldTransitionWallpaperController.TransitionListener(unfoldTransitionWallpaperController));
                sysUIUnfoldComponent.getUnfoldHapticsPlayer();
                NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider = sysUIUnfoldComponent.getNaturalRotationUnfoldProgressProvider();
                NaturalRotationUnfoldProgressProvider$rotationListener$1 naturalRotationUnfoldProgressProvider$rotationListener$1 = naturalRotationUnfoldProgressProvider.rotationListener;
                RotationChangeProvider rotationChangeProvider = naturalRotationUnfoldProgressProvider.rotationChangeProvider;
                rotationChangeProvider.getClass();
                rotationChangeProvider.bgHandler.post(new RotationChangeProvider$addCallback$1(rotationChangeProvider, naturalRotationUnfoldProgressProvider$rotationListener$1));
                Display display = naturalRotationUnfoldProgressProvider.context.getDisplay();
                if (display != null) {
                    naturalRotationUnfoldProgressProvider$rotationListener$1.onRotationChanged(display.getRotation());
                }
                UnfoldLatencyTracker unfoldLatencyTracker = sysUIUnfoldComponent.getUnfoldLatencyTracker();
                if (!(unfoldLatencyTracker.context.getResources().getIntArray(R.array.preloaded_freeform_multi_window_drawables).length == 0)) {
                    unfoldLatencyTracker.deviceStateManager.registerCallback(unfoldLatencyTracker.uiBgExecutor, unfoldLatencyTracker.foldStateListener);
                    unfoldLatencyTracker.screenLifecycle.addObserver(unfoldLatencyTracker);
                    if (unfoldLatencyTracker.transitionProgressProvider.isPresent()) {
                        ((UnfoldTransitionProgressProvider) unfoldLatencyTracker.transitionProgressProvider.get()).addCallback(unfoldLatencyTracker);
                    }
                }
            }
        });
        this.foldStateLoggingProviderOptional.ifPresent(new Consumer() { // from class: com.android.systemui.unfold.UnfoldInitializationStartable$start$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FoldStateLoggingProviderImpl foldStateLoggingProviderImpl = (FoldStateLoggingProviderImpl) ((FoldStateLoggingProvider) obj);
                DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) foldStateLoggingProviderImpl.foldStateProvider;
                deviceFoldStateProvider.addCallback(foldStateLoggingProviderImpl);
                deviceFoldStateProvider.start();
            }
        });
        this.foldStateLoggerOptional.ifPresent(new Consumer() { // from class: com.android.systemui.unfold.UnfoldInitializationStartable$start$3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FoldStateLogger foldStateLogger = (FoldStateLogger) obj;
                ((FoldStateLoggingProviderImpl) foldStateLogger.foldStateLoggingProvider).addCallback(foldStateLogger);
            }
        });
        Flags.FEATURE_FLAGS.getClass();
        this.unfoldBgTransitionProgressProviderOptional.ifPresent(new Consumer() { // from class: com.android.systemui.unfold.UnfoldInitializationStartable$start$4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final UnfoldTransitionProgressProvider unfoldTransitionProgressProvider = (UnfoldTransitionProgressProvider) obj;
                UnfoldInitializationStartable.this.unfoldTransitionProgressForwarder.ifPresent(new Consumer() { // from class: com.android.systemui.unfold.UnfoldInitializationStartable$start$4.1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        UnfoldTransitionProgressProvider.this.addCallback((UnfoldTransitionProgressForwarder) obj2);
                    }
                });
            }
        });
    }
}
