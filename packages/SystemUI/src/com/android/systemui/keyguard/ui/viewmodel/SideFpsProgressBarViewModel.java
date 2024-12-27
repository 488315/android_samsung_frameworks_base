package com.android.systemui.keyguard.ui.viewmodel;

import android.content.Context;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.BiometricStatusInteractor;
import com.android.systemui.biometrics.domain.interactor.BiometricStatusInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFingerprintAuthInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class SideFpsProgressBarViewModel {
    public final StateFlowImpl _progress;
    public final StateFlowImpl _visible;
    public final CoroutineScope applicationScope;
    public final Context context;
    public final DozeServiceHost dozeServiceHost;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isFingerprintAuthRunning;
    public final Flow isProlongedTouchRequiredForAuthentication;
    public final ReadonlyStateFlow isVisible;
    public final KeyguardInteractor keyguardInteractor;
    public final CoroutineDispatcher mainDispatcher;
    public final SideFpsProgressBarViewModel$special$$inlined$filter$1 mergedFingerprintAuthenticationStatus;
    public final PowerInteractor powerInteractor;
    public final ReadonlyStateFlow progress;
    public final Flow progressBarLength;
    public final SideFpsProgressBarViewModel$special$$inlined$map$2 progressBarLocation;
    public final int progressBarThickness;
    public final SideFpsProgressBarViewModel$special$$inlined$map$3 rotation;
    public final SideFpsSensorInteractor sfpsSensorInteractor;

    public SideFpsProgressBarViewModel(Context context, BiometricStatusInteractor biometricStatusInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, SideFpsSensorInteractor sideFpsSensorInteractor, DozeServiceHost dozeServiceHost, KeyguardInteractor keyguardInteractor, DisplayStateInteractor displayStateInteractor, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, PowerInteractor powerInteractor) {
        this.context = context;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._visible = MutableStateFlow2;
        BiometricStatusInteractorImpl biometricStatusInteractorImpl = (BiometricStatusInteractorImpl) biometricStatusInteractor;
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.merge(biometricStatusInteractorImpl.fingerprintAcquiredStatus, deviceEntryFingerprintAuthInteractor.authenticationStatus));
        new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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

                /* JADX WARN: Code restructure failed: missing block: B:20:0x004d, code lost:
                
                    if (kotlin.jvm.internal.Intrinsics.areEqual(r7.authenticationReason, com.android.systemui.biometrics.shared.model.AuthenticationReason.BiometricPromptAuthentication.INSTANCE) == false) goto L23;
                 */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5a
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        r7 = r6
                        com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus r7 = (com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus) r7
                        boolean r2 = r7 instanceof com.android.systemui.keyguard.shared.model.AcquiredFingerprintAuthenticationStatus
                        if (r2 == 0) goto L4f
                        com.android.systemui.keyguard.shared.model.AcquiredFingerprintAuthenticationStatus r7 = (com.android.systemui.keyguard.shared.model.AcquiredFingerprintAuthenticationStatus) r7
                        com.android.systemui.biometrics.shared.model.AuthenticationReason r2 = r7.authenticationReason
                        com.android.systemui.biometrics.shared.model.AuthenticationReason$DeviceEntryAuthentication r4 = com.android.systemui.biometrics.shared.model.AuthenticationReason.DeviceEntryAuthentication.INSTANCE
                        boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r4)
                        if (r2 != 0) goto L4f
                        com.android.systemui.biometrics.shared.model.AuthenticationReason r7 = r7.authenticationReason
                        com.android.systemui.biometrics.shared.model.AuthenticationReason$BiometricPromptAuthentication r2 = com.android.systemui.biometrics.shared.model.AuthenticationReason.BiometricPromptAuthentication.INSTANCE
                        boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r2)
                        if (r7 == 0) goto L5a
                    L4f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        FlowKt.asStateFlow(MutableStateFlow2);
        FlowKt.asStateFlow(MutableStateFlow);
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = sideFpsSensorInteractor.sensorLocation;
        FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.domain.model.SideFpsSensorLocation r5 = (com.android.systemui.biometrics.domain.model.SideFpsSensorLocation) r5
                        int r5 = r5.length
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.progressBarThickness = (int) context.getResources().getDimension(R.dimen.sfps_progress_bar_thickness);
        ReadonlyStateFlow readonlyStateFlow = ((DisplayStateInteractorImpl) displayStateInteractor).currentRotation;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, SideFpsProgressBarViewModel$progressBarLocation$2.INSTANCE);
        new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SideFpsProgressBarViewModel this$0;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SideFpsProgressBarViewModel sideFpsProgressBarViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = sideFpsProgressBarViewModel;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        r9 = this;
                        boolean r0 = r11 instanceof com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r11
                        com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r11)
                    L18:
                        java.lang.Object r11 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r11)
                        goto La6
                    L28:
                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                        r9.<init>(r10)
                        throw r9
                    L30:
                        kotlin.ResultKt.throwOnFailure(r11)
                        kotlin.Pair r10 = (kotlin.Pair) r10
                        java.lang.Object r11 = r10.component1()
                        com.android.systemui.biometrics.shared.model.DisplayRotation r11 = (com.android.systemui.biometrics.shared.model.DisplayRotation) r11
                        java.lang.Object r10 = r10.component2()
                        com.android.systemui.biometrics.domain.model.SideFpsSensorLocation r10 = (com.android.systemui.biometrics.domain.model.SideFpsSensorLocation) r10
                        com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel r2 = r9.this$0
                        android.content.Context r4 = r2.context
                        android.content.res.Resources r4 = r4.getResources()
                        r5 = 2131170151(0x7f071367, float:1.7954652E38)
                        float r4 = r4.getDimension(r5)
                        int r4 = (int) r4
                        android.graphics.Point r5 = new android.graphics.Point
                        int r6 = r10.left
                        int r7 = r10.top
                        r5.<init>(r6, r7)
                        int r2 = r2.progressBarThickness
                        int r2 = r2 + r4
                        com.android.systemui.biometrics.shared.model.DisplayRotation r6 = com.android.systemui.biometrics.shared.model.DisplayRotation.ROTATION_0
                        if (r11 == r6) goto L68
                        com.android.systemui.biometrics.shared.model.DisplayRotation r7 = com.android.systemui.biometrics.shared.model.DisplayRotation.ROTATION_180
                        if (r11 != r7) goto L66
                        goto L68
                    L66:
                        r7 = 0
                        goto L69
                    L68:
                        r7 = r3
                    L69:
                        boolean r8 = r10.isSensorVerticalInDefaultOrientation
                        if (r8 != r7) goto L89
                        int r6 = r5.y
                        int r10 = r10.length
                        int r6 = r6 + r10
                        r5.y = r6
                        com.android.systemui.biometrics.shared.model.DisplayRotation r10 = com.android.systemui.biometrics.shared.model.DisplayRotation.ROTATION_180
                        if (r11 == r10) goto L83
                        com.android.systemui.biometrics.shared.model.DisplayRotation r10 = com.android.systemui.biometrics.shared.model.DisplayRotation.ROTATION_90
                        if (r11 != r10) goto L7d
                        goto L83
                    L7d:
                        int r10 = r5.x
                        int r10 = r10 - r2
                        r5.x = r10
                        goto L9b
                    L83:
                        int r10 = r5.x
                        int r10 = r10 + r4
                        r5.x = r10
                        goto L9b
                    L89:
                        if (r11 == r6) goto L96
                        com.android.systemui.biometrics.shared.model.DisplayRotation r10 = com.android.systemui.biometrics.shared.model.DisplayRotation.ROTATION_90
                        if (r11 != r10) goto L90
                        goto L96
                    L90:
                        int r10 = r5.y
                        int r10 = r10 - r2
                        r5.y = r10
                        goto L9b
                    L96:
                        int r10 = r5.y
                        int r10 = r10 + r4
                        r5.y = r10
                    L9b:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                        java.lang.Object r9 = r9.emit(r5, r0)
                        if (r9 != r1) goto La6
                        return r1
                    La6:
                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(deviceEntryFingerprintAuthInteractor.isRunning, biometricStatusInteractorImpl.sfpsAuthenticationReason, new SideFpsProgressBarViewModel$isFingerprintAuthRunning$1(null));
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, SideFpsProgressBarViewModel$rotation$2.INSTANCE);
        new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L64
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Pair r5 = (kotlin.Pair) r5
                        java.lang.Object r6 = r5.component1()
                        com.android.systemui.biometrics.shared.model.DisplayRotation r6 = (com.android.systemui.biometrics.shared.model.DisplayRotation) r6
                        java.lang.Object r5 = r5.component2()
                        com.android.systemui.biometrics.domain.model.SideFpsSensorLocation r5 = (com.android.systemui.biometrics.domain.model.SideFpsSensorLocation) r5
                        com.android.systemui.biometrics.shared.model.DisplayRotation r2 = com.android.systemui.biometrics.shared.model.DisplayRotation.ROTATION_0
                        if (r6 == r2) goto L4b
                        com.android.systemui.biometrics.shared.model.DisplayRotation r2 = com.android.systemui.biometrics.shared.model.DisplayRotation.ROTATION_180
                        if (r6 != r2) goto L49
                        goto L4b
                    L49:
                        r6 = 0
                        goto L4c
                    L4b:
                        r6 = r3
                    L4c:
                        boolean r5 = r5.isSensorVerticalInDefaultOrientation
                        if (r6 != r5) goto L53
                        r5 = 1132920832(0x43870000, float:270.0)
                        goto L54
                    L53:
                        r5 = 0
                    L54:
                        java.lang.Float r6 = new java.lang.Float
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L64
                        return r1
                    L64:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flow = sideFpsSensorInteractor.isProlongedTouchRequiredForAuthentication;
        Flags.FEATURE_FLAGS.getClass();
    }

    public final void setVisible(boolean z) {
        this._visible.updateState(null, Boolean.valueOf(z));
    }
}
