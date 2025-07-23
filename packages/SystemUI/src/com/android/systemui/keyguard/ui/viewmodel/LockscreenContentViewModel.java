package com.android.systemui.keyguard.ui.viewmodel;

import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import androidx.compose.runtime.State;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardBlueprint;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.transition.KeyguardTransitionAnimationCallback;
import com.android.systemui.keyguard.shared.transition.KeyguardTransitionAnimationCallbackDelegator;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LockscreenContentViewModel extends ExclusiveActivatable {
    public final State areNotificationsVisible$delegate;
    public final AuthController authController;
    public final State blueprintId$delegate;
    public final KeyguardClockInteractor clockInteractor;
    public final Hydrator hydrator;
    public final State isBypassEnabled$delegate;
    public final State isContentVisible$delegate;
    public final KeyguardTransitionAnimationCallback keyguardTransitionAnimationCallback;
    public final KeyguardTransitionAnimationCallbackDelegator keyguardTransitionAnimationCallbackDelegator;
    public final State notificationsPlacement$delegate;
    public final KeyguardTouchHandlingViewModel touchHandling;
    public final State unfoldTranslations$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UnfoldTranslations {
        public final float end;
        public final float start;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public UnfoldTranslations() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel.UnfoldTranslations.<init>():void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof UnfoldTranslations)) {
                return false;
            }
            UnfoldTranslations unfoldTranslations = (UnfoldTranslations) obj;
            return Float.compare(this.start, unfoldTranslations.start) == 0 && Float.compare(this.end, unfoldTranslations.end) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.end) + (Float.hashCode(this.start) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("UnfoldTranslations(start=");
            sb.append(this.start);
            sb.append(", end=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.end, ")", sb);
        }

        public UnfoldTranslations(float f, float f2) {
            this.start = f;
            this.end = f2;
        }

        public /* synthetic */ UnfoldTranslations(float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2);
        }
    }

    public LockscreenContentViewModel(KeyguardClockInteractor keyguardClockInteractor, KeyguardBlueprintInteractor keyguardBlueprintInteractor, AuthController authController, KeyguardTouchHandlingViewModel keyguardTouchHandlingViewModel, ShadeModeInteractor shadeModeInteractor, UnfoldTransitionInteractor unfoldTransitionInteractor, DeviceEntryInteractor deviceEntryInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardTransitionAnimationCallbackDelegator keyguardTransitionAnimationCallbackDelegator, KeyguardTransitionAnimationCallback keyguardTransitionAnimationCallback) {
        this.clockInteractor = keyguardClockInteractor;
        this.authController = authController;
        this.touchHandling = keyguardTouchHandlingViewModel;
        this.keyguardTransitionAnimationCallbackDelegator = keyguardTransitionAnimationCallbackDelegator;
        this.keyguardTransitionAnimationCallback = keyguardTransitionAnimationCallback;
        Hydrator hydrator = new Hydrator("LockscreenContentViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        ShadeModeInteractorImpl shadeModeInteractorImpl = (ShadeModeInteractorImpl) shadeModeInteractor;
        this.notificationsPlacement$delegate = hydrator.hydratedStateOf("notificationsPlacement", LockscreenContentViewModel$NotificationsPlacement$BelowClock.INSTANCE, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(shadeModeInteractorImpl.shadeMode, keyguardClockInteractor.clockSize, new LockscreenContentViewModel$notificationsPlacement$2(null)));
        float f = 0.0f;
        this.unfoldTranslations$delegate = hydrator.hydratedStateOf("unfoldTranslations", new UnfoldTranslations(f, f, 3, null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(unfoldTransitionInteractor.unfoldTranslationX(true), unfoldTransitionInteractor.unfoldTranslationX(false), LockscreenContentViewModel$unfoldTranslations$4.INSTANCE));
        Boolean bool = Boolean.TRUE;
        final MutableSharedFlow transitionValueFlow = keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.OCCLUDED);
        this.isContentVisible$delegate = hydrator.hydratedStateOf("isContentVisible", bool, new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 0
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 != 0) goto L3f
                        r5 = r3
                        goto L40
                    L3f:
                        r5 = 0
                    L40:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.areNotificationsVisible$delegate = hydrator.hydratedStateOf("areNotificationsVisible", Boolean.FALSE, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardClockInteractor.clockSize, shadeModeInteractorImpl.isShadeLayoutWide, new LockscreenContentViewModel$areNotificationsVisible$2(null)));
        this.isBypassEnabled$delegate = hydrator.hydratedStateOf(deviceEntryInteractor.isBypassEnabled, "isBypassEnabled");
        String id = ((KeyguardBlueprint) keyguardBlueprintInteractor.keyguardBlueprintRepository.blueprint.getValue()).getId();
        final StateFlowImpl stateFlowImpl = keyguardBlueprintInteractor.blueprint;
        this.blueprintId$delegate = hydrator.hydratedStateOf("blueprintId", id, FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L43
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.KeyguardBlueprint r5 = (com.android.systemui.keyguard.shared.model.KeyguardBlueprint) r5
                        java.lang.String r5 = r5.getId()
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L43
                        return r1
                    L43:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$onActivated$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$onActivated$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$onActivated$2 r5 = new com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
