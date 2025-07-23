package com.android.systemui.unfold;

import android.view.Display;
import com.android.systemui.CoreStartable;
import com.android.systemui.unfold.UnfoldTransitionWallpaperController;
import com.android.systemui.unfold.progress.UnfoldTransitionProgressForwarder;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.RotationChangeProvider;
import com.android.systemui.unfold.updates.RotationChangeProvider$addCallback$1;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider$rotationListener$1;
import java.util.Optional;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UnfoldInitializationStartable implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
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
        final int i = 0;
        this.unfoldComponentOptional.ifPresent(new UnfoldInitializationStartable$sam$java_util_function_Consumer$0(new Function1() { // from class: com.android.systemui.unfold.UnfoldInitializationStartable$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i) {
                    case 0:
                        SysUIUnfoldComponent sysUIUnfoldComponent = (SysUIUnfoldComponent) obj;
                        int i2 = UnfoldInitializationStartable.$r8$clinit;
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
                        if (unfoldLatencyTracker.isFoldable) {
                            unfoldLatencyTracker.deviceStateManager.registerCallback(unfoldLatencyTracker.uiBgExecutor, unfoldLatencyTracker.foldStateListener);
                            unfoldLatencyTracker.screenLifecycle.addObserver(unfoldLatencyTracker);
                            if (unfoldLatencyTracker.transitionProgressProvider.isPresent()) {
                                ((UnfoldTransitionProgressProvider) unfoldLatencyTracker.transitionProgressProvider.get()).addCallback(unfoldLatencyTracker);
                            }
                        }
                        break;
                    case 1:
                        int i3 = UnfoldInitializationStartable.$r8$clinit;
                        FoldStateLoggingProviderImpl foldStateLoggingProviderImpl = (FoldStateLoggingProviderImpl) ((FoldStateLoggingProvider) obj);
                        DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) foldStateLoggingProviderImpl.foldStateProvider;
                        deviceFoldStateProvider.addCallback(foldStateLoggingProviderImpl);
                        deviceFoldStateProvider.start();
                        break;
                    default:
                        FoldStateLogger foldStateLogger = (FoldStateLogger) obj;
                        int i4 = UnfoldInitializationStartable.$r8$clinit;
                        ((FoldStateLoggingProviderImpl) foldStateLogger.foldStateLoggingProvider).addCallback(foldStateLogger);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i2 = 1;
        this.foldStateLoggingProviderOptional.ifPresent(new UnfoldInitializationStartable$sam$java_util_function_Consumer$0(new Function1() { // from class: com.android.systemui.unfold.UnfoldInitializationStartable$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i2) {
                    case 0:
                        SysUIUnfoldComponent sysUIUnfoldComponent = (SysUIUnfoldComponent) obj;
                        int i22 = UnfoldInitializationStartable.$r8$clinit;
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
                        if (unfoldLatencyTracker.isFoldable) {
                            unfoldLatencyTracker.deviceStateManager.registerCallback(unfoldLatencyTracker.uiBgExecutor, unfoldLatencyTracker.foldStateListener);
                            unfoldLatencyTracker.screenLifecycle.addObserver(unfoldLatencyTracker);
                            if (unfoldLatencyTracker.transitionProgressProvider.isPresent()) {
                                ((UnfoldTransitionProgressProvider) unfoldLatencyTracker.transitionProgressProvider.get()).addCallback(unfoldLatencyTracker);
                            }
                        }
                        break;
                    case 1:
                        int i3 = UnfoldInitializationStartable.$r8$clinit;
                        FoldStateLoggingProviderImpl foldStateLoggingProviderImpl = (FoldStateLoggingProviderImpl) ((FoldStateLoggingProvider) obj);
                        DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) foldStateLoggingProviderImpl.foldStateProvider;
                        deviceFoldStateProvider.addCallback(foldStateLoggingProviderImpl);
                        deviceFoldStateProvider.start();
                        break;
                    default:
                        FoldStateLogger foldStateLogger = (FoldStateLogger) obj;
                        int i4 = UnfoldInitializationStartable.$r8$clinit;
                        ((FoldStateLoggingProviderImpl) foldStateLogger.foldStateLoggingProvider).addCallback(foldStateLogger);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i3 = 2;
        this.foldStateLoggerOptional.ifPresent(new UnfoldInitializationStartable$sam$java_util_function_Consumer$0(new Function1() { // from class: com.android.systemui.unfold.UnfoldInitializationStartable$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i3) {
                    case 0:
                        SysUIUnfoldComponent sysUIUnfoldComponent = (SysUIUnfoldComponent) obj;
                        int i22 = UnfoldInitializationStartable.$r8$clinit;
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
                        if (unfoldLatencyTracker.isFoldable) {
                            unfoldLatencyTracker.deviceStateManager.registerCallback(unfoldLatencyTracker.uiBgExecutor, unfoldLatencyTracker.foldStateListener);
                            unfoldLatencyTracker.screenLifecycle.addObserver(unfoldLatencyTracker);
                            if (unfoldLatencyTracker.transitionProgressProvider.isPresent()) {
                                ((UnfoldTransitionProgressProvider) unfoldLatencyTracker.transitionProgressProvider.get()).addCallback(unfoldLatencyTracker);
                            }
                        }
                        break;
                    case 1:
                        int i32 = UnfoldInitializationStartable.$r8$clinit;
                        FoldStateLoggingProviderImpl foldStateLoggingProviderImpl = (FoldStateLoggingProviderImpl) ((FoldStateLoggingProvider) obj);
                        DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) foldStateLoggingProviderImpl.foldStateProvider;
                        deviceFoldStateProvider.addCallback(foldStateLoggingProviderImpl);
                        deviceFoldStateProvider.start();
                        break;
                    default:
                        FoldStateLogger foldStateLogger = (FoldStateLogger) obj;
                        int i4 = UnfoldInitializationStartable.$r8$clinit;
                        ((FoldStateLoggingProviderImpl) foldStateLogger.foldStateLoggingProvider).addCallback(foldStateLogger);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        this.unfoldBgTransitionProgressProviderOptional.ifPresent(new UnfoldInitializationStartable$sam$java_util_function_Consumer$0(new UnfoldInitializationStartable$$ExternalSyntheticLambda3(this, 0)));
    }
}
