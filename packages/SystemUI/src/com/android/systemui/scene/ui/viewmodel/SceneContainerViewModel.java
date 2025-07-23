package com.android.systemui.scene.ui.viewmodel;

import android.view.View;
import androidx.compose.runtime.State;
import com.android.compose.animation.scene.EdgeDetectorKt;
import com.android.compose.animation.scene.FixedSizeEdgeDetector;
import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LightRevealScrimViewModel;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.logger.SceneLogger;
import com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor;
import com.android.systemui.wallpapers.ui.viewmodel.WallpaperViewModel;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneContainerViewModel extends ExclusiveActivatable {
    public final List allContentKeys;
    public final AodBurnInViewModel burnIn;
    public final KeyguardClockViewModel clock;
    public final StateFlow currentScene;
    public final FalsingInteractor falsingInteractor;
    public final SceneContainerHapticsViewModel hapticsViewModel;
    public final Hydrator hydrator;
    public final State isVisible$delegate;
    public final LightRevealScrimViewModel lightRevealScrim;
    public final SceneLogger logger;
    public final Function1 motionEventHandlerReceiver;
    public final PowerInteractor powerInteractor;
    public final RemoteInputInteractor remoteInputInteractor;
    public final State ribbonColorSaturation$delegate;
    public final SceneInteractor sceneInteractor;
    public final State swipeSourceDetector$delegate;
    public final WallpaperViewModel wallpaperViewModel;

    public SceneContainerViewModel(SceneInteractor sceneInteractor, FalsingInteractor falsingInteractor, PowerInteractor powerInteractor, ShadeModeInteractor shadeModeInteractor, RemoteInputInteractor remoteInputInteractor, SceneLogger sceneLogger, SceneContainerHapticsViewModel.Factory factory, LightRevealScrimViewModel lightRevealScrimViewModel, WallpaperViewModel wallpaperViewModel, KeyguardInteractor keyguardInteractor, AodBurnInViewModel aodBurnInViewModel, KeyguardClockViewModel keyguardClockViewModel, View view, Function1 function1) {
        this.sceneInteractor = sceneInteractor;
        this.falsingInteractor = falsingInteractor;
        this.powerInteractor = powerInteractor;
        this.remoteInputInteractor = remoteInputInteractor;
        this.logger = sceneLogger;
        this.lightRevealScrim = lightRevealScrimViewModel;
        this.wallpaperViewModel = wallpaperViewModel;
        this.burnIn = aodBurnInViewModel;
        this.clock = keyguardClockViewModel;
        this.motionEventHandlerReceiver = function1;
        this.currentScene = sceneInteractor.currentScene;
        Hydrator hydrator = new Hydrator("SceneContainerViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        this.isVisible$delegate = hydrator.hydratedStateOf(sceneInteractor.isVisible, "isVisible");
        this.allContentKeys = sceneInteractor.allContentKeys;
        this.hapticsViewModel = factory.create(view);
        FixedSizeEdgeDetector fixedSizeEdgeDetector = EdgeDetectorKt.DefaultEdgeDetector;
        final ReadonlyStateFlow readonlyStateFlow = ((ShadeModeInteractorImpl) shadeModeInteractor).shadeMode;
        this.swipeSourceDetector$delegate = hydrator.hydratedStateOf("swipeSourceDetector", fixedSizeEdgeDetector, new Flow() { // from class: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L51
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.shade.shared.model.ShadeMode r5 = (com.android.systemui.shade.shared.model.ShadeMode) r5
                        boolean r5 = r5 instanceof com.android.systemui.shade.shared.model.ShadeMode.Dual
                        if (r5 == 0) goto L44
                        com.android.systemui.scene.ui.viewmodel.SceneContainerSwipeDetector r5 = new com.android.systemui.scene.ui.viewmodel.SceneContainerSwipeDetector
                        r6 = 40
                        float r6 = (float) r6
                        androidx.compose.ui.unit.Dp$Companion r2 = androidx.compose.ui.unit.Dp.Companion
                        r2 = 0
                        r5.<init>(r6, r2)
                        goto L46
                    L44:
                        com.android.compose.animation.scene.FixedSizeEdgeDetector r5 = com.android.compose.animation.scene.EdgeDetectorKt.DefaultEdgeDetector
                    L46:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L51
                        return r1
                    L51:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final Flow flow = keyguardInteractor.dozeAmount;
        this.ribbonColorSaturation$delegate = hydrator.hydratedStateOf("ribbonColorSaturation", Float.valueOf(1.0f), new Flow() { // from class: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        float r6 = (float) r3
                        float r6 = r6 - r5
                        java.lang.Float r5 = new java.lang.Float
                        r5.<init>(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0066, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$1 r0 = (com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$1 r0 = new com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L41
            if (r2 == r5) goto L39
            if (r2 == r4) goto L2f
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2f:
            java.lang.Object r6 = r0.L$0
            com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel r6 = (com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel) r6
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L37
            goto L69
        L37:
            r7 = move-exception
            goto L6f
        L39:
            java.lang.Object r6 = r0.L$0
            com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel r6 = (com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel) r6
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L37
            goto L5e
        L41:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.jvm.functions.Function1 r7 = r6.motionEventHandlerReceiver     // Catch: java.lang.Throwable -> L37
            com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$2 r2 = new com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$2     // Catch: java.lang.Throwable -> L37
            r2.<init>()     // Catch: java.lang.Throwable -> L37
            r7.mo779invoke(r2)     // Catch: java.lang.Throwable -> L37
            com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$3 r7 = new com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$3     // Catch: java.lang.Throwable -> L37
            r7.<init>(r6, r3)     // Catch: java.lang.Throwable -> L37
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L37
            r0.label = r5     // Catch: java.lang.Throwable -> L37
            java.lang.Object r7 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r7, r0)     // Catch: java.lang.Throwable -> L37
            if (r7 != r1) goto L5e
            goto L68
        L5e:
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L37
            r0.label = r4     // Catch: java.lang.Throwable -> L37
            kotlin.coroutines.intrinsics.CoroutineSingletons r7 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)     // Catch: java.lang.Throwable -> L37
            if (r7 != r1) goto L69
        L68:
            return r1
        L69:
            kotlin.KotlinNothingValueException r7 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L37
            r7.<init>()     // Catch: java.lang.Throwable -> L37
            throw r7     // Catch: java.lang.Throwable -> L37
        L6f:
            kotlin.jvm.functions.Function1 r6 = r6.motionEventHandlerReceiver
            r6.mo779invoke(r3)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
