package com.android.systemui.deviceentry.domain.interactor;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.BiometricUnlockMode;
import com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import com.android.systemui.util.kotlin.Utils;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceEntrySourceInteractor extends FlowDumperImpl {
    public final SharedFlowImpl _attemptEnterDeviceFromDeviceEntryIcon;
    public final SharedFlowImpl attemptEnterDeviceFromDeviceEntryIcon;
    public final Flow deviceEntryFromBiometricSource;

    public DeviceEntrySourceInteractor(AuthenticationInteractor authenticationInteractor, final AuthController authController, AlternateBouncerInteractor alternateBouncerInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, final DozeScrimController dozeScrimController, KeyguardBypassInteractor keyguardBypassInteractor, final KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardInteractor keyguardInteractor, SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor, SceneInteractor sceneInteractor, DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        final ReadonlyStateFlow readonlyStateFlow = sceneInteractor.transitionState;
        Flow dumpWhileCollecting = dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L56
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.compose.animation.scene.ObservableTransitionState r6 = (com.android.compose.animation.scene.ObservableTransitionState) r6
                        com.android.compose.animation.scene.OverlayKey r7 = com.android.systemui.scene.shared.model.Overlays.Bouncer
                        r2 = 0
                        boolean r4 = com.android.compose.animation.scene.ObservableTransitionState.isIdle$default(r6, r2, r7, r3)
                        if (r4 != 0) goto L46
                        boolean r6 = r6.isTransitioning(r2, r7)
                        if (r6 == 0) goto L44
                        goto L46
                    L44:
                        r6 = 0
                        goto L47
                    L46:
                        r6 = r3
                    L47:
                        java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L56
                        return r1
                    L56:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), "isShowingBouncerOverlay");
        final Flow authenticationStatus = deviceEntryFaceAuthInteractor.getAuthenticationStatus();
        Flow dumpWhileCollecting2 = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L57
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.deviceentry.shared.model.FaceAuthenticationStatus r5 = (com.android.systemui.deviceentry.shared.model.FaceAuthenticationStatus) r5
                        boolean r6 = r5 instanceof com.android.systemui.deviceentry.shared.model.SuccessFaceAuthenticationStatus
                        if (r6 == 0) goto L3b
                        com.android.systemui.deviceentry.shared.model.SuccessFaceAuthenticationStatus r5 = (com.android.systemui.deviceentry.shared.model.SuccessFaceAuthenticationStatus) r5
                        goto L3c
                    L3b:
                        r5 = 0
                    L3c:
                        if (r5 == 0) goto L47
                        android.hardware.face.FaceManager$AuthenticationResult r5 = r5.successResult
                        if (r5 == 0) goto L47
                        boolean r5 = r5.isStrongBiometric()
                        goto L48
                    L47:
                        r5 = 0
                    L48:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L57
                        return r1
                    L57:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, "unlockedWithStrongFaceUnlock");
        final Flow flow = deviceEntryFingerprintAuthInteractor.authenticationStatus;
        Flow dumpWhileCollecting3 = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3$2$1
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
                        com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus r5 = (com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus) r5
                        boolean r6 = r5 instanceof com.android.systemui.keyguard.shared.model.SuccessFingerprintAuthenticationStatus
                        if (r6 == 0) goto L3b
                        com.android.systemui.keyguard.shared.model.SuccessFingerprintAuthenticationStatus r5 = (com.android.systemui.keyguard.shared.model.SuccessFingerprintAuthenticationStatus) r5
                        goto L3c
                    L3b:
                        r5 = 0
                    L3c:
                        if (r5 == 0) goto L41
                        boolean r5 = r5.isStrongBiometric
                        goto L42
                    L41:
                        r5 = 0
                    L42:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L51
                        return r1
                    L51:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, "unlockedWithStrongFingerprintUnlock");
        final Flow[] flowArr = {alternateBouncerInteractor.isVisible, keyguardBypassInteractor.isBypassAvailable, dumpWhileCollecting2, sceneContainerOcclusionInteractor.isOccludingActivityShown, sceneInteractor.currentScene, dumpWhileCollecting};
        final Flow flow2 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                final /* synthetic */ AuthController $authController$inlined;
                final /* synthetic */ KeyguardUpdateMonitor $keyguardUpdateMonitor$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, KeyguardUpdateMonitor keyguardUpdateMonitor, AuthController authController) {
                    super(3, continuation);
                    this.$keyguardUpdateMonitor$inlined = keyguardUpdateMonitor;
                    this.$authController$inlined = authController;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.$keyguardUpdateMonitor$inlined, this.$authController$inlined);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:20:0x0072, code lost:
                
                    if (r5 != false) goto L22;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:21:0x0074, code lost:
                
                    r3 = 3;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:40:0x009d, code lost:
                
                    if (r5 != false) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:19:0x0070  */
                /* JADX WARN: Removed duplicated region for block: B:24:0x00ad A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:28:0x007c  */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r15) {
                    /*
                        r14 = this;
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r14.label
                        r2 = 1
                        if (r1 == 0) goto L16
                        if (r1 != r2) goto Le
                        kotlin.ResultKt.throwOnFailure(r15)
                        goto Lae
                    Le:
                        java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
                        java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
                        r14.<init>(r15)
                        throw r14
                    L16:
                        kotlin.ResultKt.throwOnFailure(r15)
                        java.lang.Object r15 = r14.L$0
                        kotlinx.coroutines.flow.FlowCollector r15 = (kotlinx.coroutines.flow.FlowCollector) r15
                        java.lang.Object r1 = r14.L$1
                        java.lang.Object[] r1 = (java.lang.Object[]) r1
                        r3 = 0
                        r4 = r1[r3]
                        r5 = r1[r2]
                        r6 = 2
                        r7 = r1[r6]
                        r8 = 3
                        r9 = r1[r8]
                        r10 = 4
                        r11 = r1[r10]
                        r12 = 5
                        r1 = r1[r12]
                        java.lang.Boolean r1 = (java.lang.Boolean) r1
                        boolean r1 = r1.booleanValue()
                        com.android.compose.animation.scene.SceneKey r11 = (com.android.compose.animation.scene.SceneKey) r11
                        java.lang.Boolean r9 = (java.lang.Boolean) r9
                        boolean r9 = r9.booleanValue()
                        java.lang.Boolean r7 = (java.lang.Boolean) r7
                        boolean r7 = r7.booleanValue()
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        java.lang.Boolean r4 = (java.lang.Boolean) r4
                        boolean r4 = r4.booleanValue()
                        com.android.keyguard.KeyguardUpdateMonitor r13 = r14.$keyguardUpdateMonitor$inlined
                        boolean r7 = r13.isUnlockingWithBiometricAllowed(r7)
                        if (r5 != 0) goto L69
                        com.android.systemui.biometrics.AuthController r5 = r14.$authController$inlined
                        com.android.systemui.biometrics.UdfpsController r5 = r5.mUdfpsController
                        if (r5 != 0) goto L62
                        r5 = r3
                        goto L64
                    L62:
                        boolean r5 = r5.mOnFingerDown
                    L64:
                        if (r5 == 0) goto L67
                        goto L69
                    L67:
                        r5 = r3
                        goto L6a
                    L69:
                        r5 = r2
                    L6a:
                        com.android.keyguard.KeyguardUpdateMonitor r13 = r14.$keyguardUpdateMonitor$inlined
                        boolean r13 = r13.mDeviceInteractive
                        if (r13 != 0) goto L7c
                        if (r7 != 0) goto L76
                        if (r5 == 0) goto La0
                    L74:
                        r3 = r8
                        goto La0
                    L76:
                        if (r5 == 0) goto L7a
                        r3 = r6
                        goto La0
                    L7a:
                        r3 = r10
                        goto La0
                    L7c:
                        if (r7 == 0) goto L8a
                        com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.scene.shared.model.Scenes.Dream
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r11, r6)
                        if (r6 == 0) goto L8a
                        if (r5 == 0) goto L7a
                        r3 = 6
                        goto La0
                    L8a:
                        if (r7 == 0) goto L90
                        if (r9 == 0) goto L90
                    L8e:
                        r3 = r12
                        goto La0
                    L90:
                        if (r1 != 0) goto L94
                        if (r4 == 0) goto L98
                    L94:
                        if (r7 == 0) goto L98
                        r3 = 7
                        goto La0
                    L98:
                        if (r7 == 0) goto L9d
                        if (r5 == 0) goto L9d
                        goto L8e
                    L9d:
                        if (r5 == 0) goto La0
                        goto L74
                    La0:
                        java.lang.Integer r1 = new java.lang.Integer
                        r1.<init>(r3)
                        r14.label = r2
                        java.lang.Object r14 = r15.emit(r1, r14)
                        if (r14 != r0) goto Lae
                        return r0
                    Lae:
                        kotlin.Unit r14 = kotlin.Unit.INSTANCE
                        return r14
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$1.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, keyguardUpdateMonitor, authController), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
        final Flow dumpWhileCollecting4 = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceEntrySourceInteractor this$0;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceEntrySourceInteractor deviceEntrySourceInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceEntrySourceInteractor;
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor r6 = r4.this$0
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode r5 = com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor.access$biometricModeIntToObject(r6, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, "faceWakeAndUnlockMode");
        final Flow[] flowArr2 = {alternateBouncerInteractor.isVisible, authenticationInteractor.authenticationMethod, sceneInteractor.currentScene, sceneInteractor.currentOverlays, dumpWhileCollecting3, dumpWhileCollecting};
        final Flow flow3 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$2$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                final /* synthetic */ DozeScrimController $dozeScrimController$inlined;
                final /* synthetic */ KeyguardUpdateMonitor $keyguardUpdateMonitor$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeScrimController dozeScrimController) {
                    super(3, continuation);
                    this.$keyguardUpdateMonitor$inlined = keyguardUpdateMonitor;
                    this.$dozeScrimController$inlined = dozeScrimController;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.$keyguardUpdateMonitor$inlined, this.$dozeScrimController$inlined);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:35:0x0089, code lost:
                
                    if (r4 == false) goto L18;
                 */
                /* JADX WARN: Removed duplicated region for block: B:18:0x0099 A[RETURN] */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r14) {
                    /*
                        r13 = this;
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r13.label
                        r2 = 1
                        if (r1 == 0) goto L16
                        if (r1 != r2) goto Le
                        kotlin.ResultKt.throwOnFailure(r14)
                        goto L9a
                    Le:
                        java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
                        java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
                        r13.<init>(r14)
                        throw r13
                    L16:
                        kotlin.ResultKt.throwOnFailure(r14)
                        java.lang.Object r14 = r13.L$0
                        kotlinx.coroutines.flow.FlowCollector r14 = (kotlinx.coroutines.flow.FlowCollector) r14
                        java.lang.Object r1 = r13.L$1
                        java.lang.Object[] r1 = (java.lang.Object[]) r1
                        r3 = 0
                        r4 = r1[r3]
                        r5 = r1[r2]
                        r6 = 2
                        r7 = r1[r6]
                        r8 = 3
                        r9 = r1[r8]
                        r10 = 4
                        r10 = r1[r10]
                        r11 = 5
                        r1 = r1[r11]
                        java.lang.Boolean r1 = (java.lang.Boolean) r1
                        boolean r1 = r1.booleanValue()
                        java.lang.Boolean r10 = (java.lang.Boolean) r10
                        boolean r10 = r10.booleanValue()
                        java.util.Set r9 = (java.util.Set) r9
                        com.android.compose.animation.scene.SceneKey r7 = (com.android.compose.animation.scene.SceneKey) r7
                        com.android.systemui.authentication.shared.model.AuthenticationMethodModel r5 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r5
                        java.lang.Boolean r4 = (java.lang.Boolean) r4
                        boolean r4 = r4.booleanValue()
                        com.android.keyguard.KeyguardUpdateMonitor r12 = r13.$keyguardUpdateMonitor$inlined
                        boolean r10 = r12.isUnlockingWithBiometricAllowed(r10)
                        com.android.keyguard.KeyguardUpdateMonitor r12 = r13.$keyguardUpdateMonitor$inlined
                        boolean r12 = r12.mDeviceInteractive
                        if (r12 != 0) goto L6b
                        com.android.systemui.statusbar.phone.DozeScrimController r1 = r13.$dozeScrimController$inlined
                        com.android.systemui.statusbar.phone.DozeServiceHost$1 r1 = r1.mPulseCallback
                        if (r1 == 0) goto L60
                        if (r10 == 0) goto L60
                        r3 = r6
                        goto L8c
                    L60:
                        if (r10 != 0) goto L69
                        boolean r1 = r5.isSecure
                        if (r1 != 0) goto L67
                        goto L69
                    L67:
                        r3 = r8
                        goto L8c
                    L69:
                        r3 = r2
                        goto L8c
                    L6b:
                        if (r10 == 0) goto L77
                        com.android.compose.animation.scene.SceneKey r5 = com.android.systemui.scene.shared.model.Scenes.Dream
                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r5)
                        if (r5 == 0) goto L77
                        r3 = 6
                        goto L8c
                    L77:
                        if (r1 == 0) goto L7d
                        if (r10 == 0) goto L7d
                        r3 = 7
                        goto L8c
                    L7d:
                        if (r10 == 0) goto L81
                        r3 = r11
                        goto L8c
                    L81:
                        com.android.compose.animation.scene.OverlayKey r1 = com.android.systemui.scene.shared.model.Overlays.Bouncer
                        boolean r1 = r9.contains(r1)
                        if (r1 != 0) goto L8c
                        if (r4 != 0) goto L8c
                        goto L67
                    L8c:
                        java.lang.Integer r1 = new java.lang.Integer
                        r1.<init>(r3)
                        r13.label = r2
                        java.lang.Object r13 = r14.emit(r1, r13)
                        if (r13 != r0) goto L9a
                        return r0
                    L9a:
                        kotlin.Unit r13 = kotlin.Unit.INSTANCE
                        return r13
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$2.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr3 = flowArr2;
                Object combineInternal = CombineKt.combineInternal(flowArr3, new Function0() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$2.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr3.length];
                    }
                }, new AnonymousClass3(null, keyguardUpdateMonitor, dozeScrimController), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
        final Flow dumpWhileCollecting5 = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceEntrySourceInteractor this$0;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceEntrySourceInteractor deviceEntrySourceInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceEntrySourceInteractor;
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor r6 = r4.this$0
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode r5 = com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor.access$biometricModeIntToObject(r6, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, "fingerprintWakeAndUnlockMode");
        final Flow flow4 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode r6 = (com.android.systemui.keyguard.shared.model.BiometricUnlockMode) r6
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode$Companion r2 = com.android.systemui.keyguard.shared.model.BiometricUnlockMode.Companion
                        r2.getClass()
                        java.util.Set r2 = com.android.systemui.keyguard.shared.model.BiometricUnlockMode.dismissesKeyguardModes
                        boolean r6 = r2.contains(r6)
                        if (r6 == 0) goto L4d
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flow5 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6$2$1
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
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode r5 = (com.android.systemui.keyguard.shared.model.BiometricUnlockMode) r5
                        com.android.systemui.keyguard.shared.model.BiometricUnlockModel r6 = new com.android.systemui.keyguard.shared.model.BiometricUnlockModel
                        com.android.systemui.keyguard.shared.model.BiometricUnlockSource r2 = com.android.systemui.keyguard.shared.model.BiometricUnlockSource.FINGERPRINT_SENSOR
                        r6.<init>(r5, r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow6 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode r6 = (com.android.systemui.keyguard.shared.model.BiometricUnlockMode) r6
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode$Companion r2 = com.android.systemui.keyguard.shared.model.BiometricUnlockMode.Companion
                        r2.getClass()
                        java.util.Set r2 = com.android.systemui.keyguard.shared.model.BiometricUnlockMode.dismissesKeyguardModes
                        boolean r6 = r2.contains(r6)
                        if (r6 == 0) goto L4d
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        dumpWhileCollecting(FlowKt.merge(flow5, new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7$2$1
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
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode r5 = (com.android.systemui.keyguard.shared.model.BiometricUnlockMode) r5
                        com.android.systemui.keyguard.shared.model.BiometricUnlockModel r6 = new com.android.systemui.keyguard.shared.model.BiometricUnlockModel
                        com.android.systemui.keyguard.shared.model.BiometricUnlockSource r2 = com.android.systemui.keyguard.shared.model.BiometricUnlockSource.FACE_SENSOR
                        r6.<init>(r5, r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), "biometricUnlockState");
        final Flow flow7 = deviceEntryFingerprintAuthInteractor.authenticationStatus;
        Flow dumpWhileCollecting6 = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        boolean r6 = r5 instanceof com.android.systemui.keyguard.shared.model.SuccessFingerprintAuthenticationStatus
                        if (r6 == 0) goto L41
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, "deviceEntryFingerprintAuthSuccessEvents");
        Utils.Companion companion = Utils.Companion;
        final Flow authenticationStatus2 = deviceEntryFaceAuthInteractor.getAuthenticationStatus();
        Flow flow8 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        boolean r6 = r5 instanceof com.android.systemui.deviceentry.shared.model.SuccessFaceAuthenticationStatus
                        if (r6 == 0) goto L41
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        DeviceEntrySourceInteractor$deviceEntryFaceAuthWakeAndUnlockEvents$3 deviceEntrySourceInteractor$deviceEntryFaceAuthWakeAndUnlockEvents$3 = DeviceEntrySourceInteractor$deviceEntryFaceAuthWakeAndUnlockEvents$3.INSTANCE;
        dumpWhileCollecting(FlowKt.merge(dumpWhileCollecting6, dumpWhileCollecting(companion.sampleFilter(flow8, FlowKt.combine(sceneContainerOcclusionInteractor.isOccludingActivityShown, keyguardBypassInteractor.isBypassAvailable, keyguardBypassInteractor.canBypass, deviceEntrySourceInteractor$deviceEntryFaceAuthWakeAndUnlockEvents$3), new DeviceEntrySourceInteractor$$ExternalSyntheticLambda0()), "deviceEntryFaceAuthSuccessEvents")), "deviceEntryBiometricAuthSuccessEvents");
        final ReadonlyStateFlow readonlyStateFlow2 = keyguardInteractor.biometricUnlockState;
        final Flow flow9 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3$2$1
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
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.BiometricUnlockModel r6 = (com.android.systemui.keyguard.shared.model.BiometricUnlockModel) r6
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode$Companion r2 = com.android.systemui.keyguard.shared.model.BiometricUnlockMode.Companion
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode r6 = r6.mode
                        r2.getClass()
                        java.util.Set r2 = com.android.systemui.keyguard.shared.model.BiometricUnlockMode.dismissesKeyguardModes
                        boolean r6 = r2.contains(r6)
                        if (r6 == 0) goto L4f
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.deviceEntryFromBiometricSource = dumpWhileCollecting(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.BiometricUnlockModel r5 = (com.android.systemui.keyguard.shared.model.BiometricUnlockModel) r5
                        com.android.systemui.keyguard.shared.model.BiometricUnlockSource r5 = r5.source
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), "deviceEntryFromBiometricSource");
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._attemptEnterDeviceFromDeviceEntryIcon = MutableSharedFlow$default;
        this.attemptEnterDeviceFromDeviceEntryIcon = MutableSharedFlow$default;
    }

    public static final BiometricUnlockMode access$biometricModeIntToObject(DeviceEntrySourceInteractor deviceEntrySourceInteractor, int i) {
        deviceEntrySourceInteractor.getClass();
        switch (i) {
            case 0:
                return BiometricUnlockMode.NONE;
            case 1:
                return BiometricUnlockMode.WAKE_AND_UNLOCK;
            case 2:
                return BiometricUnlockMode.WAKE_AND_UNLOCK_PULSING;
            case 3:
                return BiometricUnlockMode.SHOW_BOUNCER;
            case 4:
                return BiometricUnlockMode.ONLY_WAKE;
            case 5:
                return BiometricUnlockMode.UNLOCK_COLLAPSING;
            case 6:
                return BiometricUnlockMode.WAKE_AND_UNLOCK_FROM_DREAM;
            case 7:
                return BiometricUnlockMode.DISMISS_BOUNCER;
            default:
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid BiometricUnlockModel value: "));
        }
    }

    private static /* synthetic */ void getBiometricUnlockStateOnKeyguardDismissed$annotations() {
    }
}
