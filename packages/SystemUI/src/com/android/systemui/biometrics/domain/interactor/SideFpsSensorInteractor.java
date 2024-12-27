package com.android.systemui.biometrics.domain.interactor;

import android.content.Context;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepository;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepositoryImpl;
import com.android.systemui.biometrics.domain.model.SideFpsSensorLocation;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.log.SideFpsLogger;
import java.util.Optional;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__DistinctKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SideFpsSensorInteractor {
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 authenticationDuration;
    public final Context context;
    public final SideFpsSensorInteractor$special$$inlined$map$2 isAvailable;
    public final Flow isProlongedTouchRequiredForAuthentication;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 isSettingEnabled;
    public final SideFpsLogger logger;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 sensorLocation;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 sensorLocationForCurrentDisplay;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DisplayRotation.values().length];
            try {
                iArr[DisplayRotation.ROTATION_0.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DisplayRotation.ROTATION_90.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DisplayRotation.ROTATION_180.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DisplayRotation.ROTATION_270.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public SideFpsSensorInteractor(Context context, FingerprintPropertyRepository fingerprintPropertyRepository, final WindowManager windowManager, DisplayStateInteractor displayStateInteractor, Optional<Object> optional, BiometricSettingsRepository biometricSettingsRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, SideFpsLogger sideFpsLogger) {
        this.context = context;
        this.logger = sideFpsLogger;
        boolean z = context.getResources().getBoolean(R.bool.config_restToUnlockSupported);
        DisplayStateInteractorImpl displayStateInteractorImpl = (DisplayStateInteractorImpl) displayStateInteractor;
        FingerprintPropertyRepositoryImpl fingerprintPropertyRepositoryImpl = (FingerprintPropertyRepositoryImpl) fingerprintPropertyRepository;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(displayStateInteractorImpl.displayChanges, fingerprintPropertyRepositoryImpl.sensorLocations, SideFpsSensorInteractor$sensorLocationForCurrentDisplay$2.INSTANCE);
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SideFpsSensorInteractor this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SideFpsSensorInteractor sideFpsSensorInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = sideFpsSensorInteractor;
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
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L59
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Pair r5 = (kotlin.Pair) r5
                        java.lang.Object r5 = r5.component2()
                        java.util.Map r5 = (java.util.Map) r5
                        com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor r6 = r4.this$0
                        android.content.Context r6 = r6.context
                        android.view.Display r6 = r6.getDisplay()
                        if (r6 == 0) goto L49
                        java.lang.String r6 = r6.getUniqueId()
                        goto L4a
                    L49:
                        r6 = 0
                    L4a:
                        java.lang.Object r5 = r5.get(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L59
                        return r1
                    L59:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final ReadonlyStateFlow readonlyStateFlow = fingerprintPropertyRepositoryImpl.sensorType;
        Flow flow = new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2$2$1
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
                        com.android.systemui.biometrics.shared.model.FingerprintSensorType r5 = (com.android.systemui.biometrics.shared.model.FingerprintSensorType) r5
                        com.android.systemui.biometrics.shared.model.FingerprintSensorType r6 = com.android.systemui.biometrics.shared.model.FingerprintSensorType.POWER_BUTTON
                        if (r5 != r6) goto L3a
                        r5 = r3
                        goto L3b
                    L3a:
                        r5 = 0
                    L3b:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1(keyguardTransitionInteractor.finishedKeyguardState, new Function1() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$authenticationDuration$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                KeyguardState keyguardState = (KeyguardState) obj;
                return Boolean.valueOf(keyguardState == KeyguardState.OFF || keyguardState == KeyguardState.DOZING);
            }
        }));
        final Flow flow2 = new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SideFpsSensorInteractor this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SideFpsSensorInteractor sideFpsSensorInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = sideFpsSensorInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:23:0x0074 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L75
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        r6 = 0
                        com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor r2 = r4.this$0
                        if (r5 == 0) goto L54
                        android.content.Context r5 = r2.context
                        android.content.res.Resources r5 = r5.getResources()
                        if (r5 == 0) goto L6a
                        r6 = 2131427381(0x7f0b0035, float:1.8476377E38)
                        int r5 = r5.getInteger(r6)
                        long r5 = (long) r5
                        java.lang.Long r2 = new java.lang.Long
                        r2.<init>(r5)
                    L52:
                        r6 = r2
                        goto L6a
                    L54:
                        android.content.Context r5 = r2.context
                        android.content.res.Resources r5 = r5.getResources()
                        if (r5 == 0) goto L6a
                        r6 = 2131427380(0x7f0b0034, float:1.8476375E38)
                        int r5 = r5.getInteger(r6)
                        long r5 = (long) r5
                        java.lang.Long r2 = new java.lang.Long
                        r2.<init>(r5)
                        goto L52
                    L6a:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L75
                        return r1
                    L75:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4$2$1 r0 = (com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4$2$1 r0 = new com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4$2$1
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
                        java.lang.Long r5 = (java.lang.Long) r5
                        if (r5 == 0) goto L3b
                        long r5 = r5.longValue()
                        goto L3d
                    L3b:
                        r5 = 0
                    L3d:
                        java.lang.Long r2 = new java.lang.Long
                        r2.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r2, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new SideFpsSensorInteractor$authenticationDuration$4(this, null));
        this.isProlongedTouchRequiredForAuthentication = !z ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.transformLatest(((BiometricSettingsRepositoryImpl) biometricSettingsRepository).isFingerprintEnrolledAndEnabled, new SideFpsSensorInteractor$special$$inlined$flatMapLatest$1(null, optional)), new SideFpsSensorInteractor$isSettingEnabled$2(this, null)), new SideFpsSensorInteractor$isProlongedTouchRequiredForAuthentication$1(null));
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(displayStateInteractorImpl.currentRotation, flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, SideFpsSensorInteractor$sensorLocation$2.INSTANCE);
        Flow flow3 = new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$5

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ WindowManager $windowManager$inlined;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, WindowManager windowManager) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$windowManager$inlined = windowManager;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r13, kotlin.coroutines.Continuation r14) {
                    /*
                        Method dump skipped, instructions count: 362
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, windowManager), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SideFpsSensorInteractor$sensorLocation$4 sideFpsSensorInteractor$sensorLocation$4 = new Function2() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$sensorLocation$4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SideFpsSensorLocation sideFpsSensorLocation = (SideFpsSensorLocation) obj;
                SideFpsSensorLocation sideFpsSensorLocation2 = (SideFpsSensorLocation) obj2;
                return Boolean.valueOf(sideFpsSensorLocation.left == sideFpsSensorLocation2.left && sideFpsSensorLocation.top == sideFpsSensorLocation2.top && sideFpsSensorLocation.length == sideFpsSensorLocation2.length && sideFpsSensorLocation.isSensorVerticalInDefaultOrientation == sideFpsSensorLocation2.isSensorVerticalInDefaultOrientation);
            }
        };
        Function1 function1 = FlowKt__DistinctKt.defaultKeySelector;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, sideFpsSensorInteractor$sensorLocation$4);
        this.sensorLocation = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(flow3, function1, sideFpsSensorInteractor$sensorLocation$4), new SideFpsSensorInteractor$sensorLocation$5(this, null));
    }
}
