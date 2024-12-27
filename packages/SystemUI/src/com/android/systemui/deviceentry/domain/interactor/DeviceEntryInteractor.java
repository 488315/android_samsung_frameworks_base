package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepository;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyguard.domain.interactor.TrustInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class DeviceEntryInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AlternateBouncerInteractor alternateBouncerInteractor;
    public final AuthenticationInteractor authenticationInteractor;
    public final DeviceEntryBiometricSettingsInteractor biometricSettingsInteractor;
    public final ReadonlyStateFlow canSwipeToEnter;
    public final ChannelFlowTransformLatest deviceEntryRestrictionReason;
    public final DeviceUnlockedInteractor deviceUnlockedInteractor;
    public final StateFlow faceEnrolledAndEnabled;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 faceOrFingerprintOrTrustEnabled;
    public final DeviceEntryFingerprintAuthInteractor fingerprintAuthInteractor;
    public final StateFlow fingerprintEnrolledAndEnabled;
    public final ReadonlyStateFlow isBypassEnabled;
    public final ReadonlyStateFlow isDeviceEntered;
    public final ReadonlyStateFlow isUnlocked;
    public final DeviceEntryRepository repository;
    public final SceneInteractor sceneInteractor;
    public final SystemPropertiesHelper systemPropertiesHelper;
    public final ReadonlyStateFlow trustAgentEnabled;
    public final TrustInteractor trustInteractor;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getREBOOT_MAINLINE_UPDATE$annotations() {
        }

        public static /* synthetic */ void getSYS_BOOT_REASON_PROP$annotations() {
        }
    }

    static {
        new Companion(null);
    }

    public DeviceEntryInteractor(CoroutineScope coroutineScope, DeviceEntryRepository deviceEntryRepository, AuthenticationInteractor authenticationInteractor, SceneInteractor sceneInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, DeviceEntryBiometricSettingsInteractor deviceEntryBiometricSettingsInteractor, TrustInteractor trustInteractor, DeviceUnlockedInteractor deviceUnlockedInteractor, SystemPropertiesHelper systemPropertiesHelper, AlternateBouncerInteractor alternateBouncerInteractor) {
        this.repository = deviceEntryRepository;
        this.authenticationInteractor = authenticationInteractor;
        this.fingerprintAuthInteractor = deviceEntryFingerprintAuthInteractor;
        this.biometricSettingsInteractor = deviceEntryBiometricSettingsInteractor;
        this.trustInteractor = trustInteractor;
        this.deviceUnlockedInteractor = deviceUnlockedInteractor;
        this.systemPropertiesHelper = systemPropertiesHelper;
        final ReadonlyStateFlow readonlyStateFlow = deviceUnlockedInteractor.deviceUnlockStatus;
        this.isUnlocked = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus r5 = (com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus) r5
                        boolean r5 = r5.isUnlocked
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.valueOf(((DeviceUnlockStatus) readonlyStateFlow.$$delegate_0.getValue()).isUnlocked));
        final ReadonlyStateFlow readonlyStateFlow2 = sceneInteractor.currentScene;
        Flow buffer$default = FlowKt.buffer$default(FlowKt.mapLatest(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L50
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.compose.animation.scene.SceneKey r6 = (com.android.compose.animation.scene.SceneKey) r6
                        com.android.compose.animation.scene.SceneKey r2 = com.android.systemui.scene.shared.model.Scenes.Gone
                        boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r2 != 0) goto L45
                        com.android.compose.animation.scene.SceneKey r2 = com.android.systemui.scene.shared.model.Scenes.Lockscreen
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r6 == 0) goto L50
                    L45:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new DeviceEntryInteractor$isDeviceEntered$2(this, null)), -1);
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(buffer$default, coroutineScope, startedEagerly, Boolean.FALSE);
        this.isDeviceEntered = stateIn;
        final Flow flow = authenticationInteractor.authenticationMethod;
        this.canSwipeToEnter = FlowKt.stateIn(FlowKt.combine(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceEntryInteractor this$0;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceEntryInteractor deviceEntryInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceEntryInteractor;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L3a
                        if (r2 == r4) goto L32
                        if (r2 != r3) goto L2a
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L6f
                    L2a:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L32:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L5d
                    L3a:
                        kotlin.ResultKt.throwOnFailure(r8)
                        com.android.systemui.authentication.shared.model.AuthenticationMethodModel r7 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r7
                        com.android.systemui.authentication.shared.model.AuthenticationMethodModel$None r8 = com.android.systemui.authentication.shared.model.AuthenticationMethodModel.None.INSTANCE
                        boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r8)
                        kotlinx.coroutines.flow.FlowCollector r8 = r6.$this_unsafeFlow
                        if (r7 == 0) goto L61
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor r6 = r6.this$0
                        com.android.systemui.deviceentry.data.repository.DeviceEntryRepository r6 = r6.repository
                        r0.L$0 = r8
                        r0.label = r4
                        com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl r6 = (com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl) r6
                        java.lang.Object r6 = r6.isLockscreenEnabled(r0)
                        if (r6 != r1) goto L5a
                        return r1
                    L5a:
                        r5 = r8
                        r8 = r6
                        r6 = r5
                    L5d:
                        r5 = r8
                        r8 = r6
                        r6 = r5
                        goto L63
                    L61:
                        java.lang.Boolean r6 = java.lang.Boolean.FALSE
                    L63:
                        r7 = 0
                        r0.L$0 = r7
                        r0.label = r3
                        java.lang.Object r6 = r8.emit(r6, r0)
                        if (r6 != r1) goto L6f
                        return r1
                    L6f:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, readonlyStateFlow, stateIn, new DeviceEntryInteractor$canSwipeToEnter$2(null)), coroutineScope, startedEagerly, null);
        FlowKt.transformLatest(FlowKt.combine(deviceEntryBiometricSettingsInteractor.isFaceAuthEnrolledAndEnabled, deviceEntryBiometricSettingsInteractor.isFingerprintAuthEnrolledAndEnabled, trustInteractor.isEnrolledAndEnabled, DeviceEntryInteractor$faceOrFingerprintOrTrustEnabled$2.INSTANCE), new DeviceEntryInteractor$special$$inlined$flatMapLatest$1(null, this, deviceEntryFaceAuthInteractor));
        this.isBypassEnabled = ((DeviceEntryRepositoryImpl) deviceEntryRepository).isBypassEnabled;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0051, code lost:
    
        if (((com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r5).isSecure == false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isAuthenticationRequired(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isAuthenticationRequired$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isAuthenticationRequired$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isAuthenticationRequired$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isAuthenticationRequired$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isAuthenticationRequired$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r5)
            goto L4d
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor r5 = r4.deviceUnlockedInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.deviceUnlockStatus
            kotlinx.coroutines.flow.StateFlow r5 = r5.$$delegate_0
            java.lang.Object r5 = r5.getValue()
            com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus r5 = (com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus) r5
            boolean r5 = r5.isUnlocked
            if (r5 != 0) goto L54
            r0.label = r3
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r4 = r4.authenticationInteractor
            java.lang.Object r5 = r4.getAuthenticationMethod(r0)
            if (r5 != r1) goto L4d
            return r1
        L4d:
            com.android.systemui.authentication.shared.model.AuthenticationMethodModel r5 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r5
            boolean r4 = r5.isSecure
            if (r4 == 0) goto L54
            goto L55
        L54:
            r3 = 0
        L55:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r3)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor.isAuthenticationRequired(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
