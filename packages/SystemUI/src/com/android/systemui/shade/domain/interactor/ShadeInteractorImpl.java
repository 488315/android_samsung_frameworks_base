package com.android.systemui.shade.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2;
import com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepository;
import com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepositoryImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepositoryImpl;
import com.android.systemui.statusbar.policy.domain.interactor.DeviceProvisioningInteractor;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShadeInteractorImpl implements ShadeInteractor, BaseShadeInteractor {
    public final BaseShadeInteractor baseShadeInteractor;
    public final ReadonlyStateFlow isAnyFullyExpanded;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 isExpandToQsEnabled;
    public final ReadonlyStateFlow isQsEnabled;
    public final ReadonlyStateFlow isShadeEnabled;
    public final Flow isShadeFullyCollapsed;
    public final Flow isShadeFullyExpanded;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isShadeTouchable;
    public final ReadonlyStateFlow isUserInteracting;

    public ShadeInteractorImpl(CoroutineScope coroutineScope, DeviceProvisioningInteractor deviceProvisioningInteractor, DisableFlagsRepository disableFlagsRepository, DozeParameters dozeParameters, KeyguardRepository keyguardRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, PowerInteractor powerInteractor, UserSetupRepository userSetupRepository, UserSwitcherInteractor userSwitcherInteractor, BaseShadeInteractor baseShadeInteractor) {
        this.baseShadeInteractor = baseShadeInteractor;
        final ReadonlyStateFlow readonlyStateFlow = ((DisableFlagsRepositoryImpl) disableFlagsRepository).disableFlags;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.disableflags.data.model.DisableFlagsModel r5 = (com.android.systemui.statusbar.disableflags.data.model.DisableFlagsModel) r5
                        int r5 = r5.disable2
                        r5 = r5 & 4
                        if (r5 != 0) goto L3c
                        r5 = r3
                        goto L3d
                    L3c:
                        r5 = 0
                    L3d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(distinctUntilChanged, coroutineScope, startedEagerly, bool);
        this.isShadeEnabled = stateIn;
        this.isQsEnabled = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4b
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.disableflags.data.model.DisableFlagsModel r5 = (com.android.systemui.statusbar.disableflags.data.model.DisableFlagsModel) r5
                        int r5 = r5.disable2
                        r5 = r5 & r3
                        if (r5 != 0) goto L3b
                        r5 = r3
                        goto L3c
                    L3b:
                        r5 = 0
                    L3c:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4b
                        return r1
                    L4b:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, bool);
        final StateFlow anyExpansion = baseShadeInteractor.getAnyExpansion();
        this.isAnyFullyExpanded = FlowKt.stateIn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$3

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$3$2$1
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
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 1065353216(0x3f800000, float:1.0)
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 < 0) goto L40
                        r5 = r3
                        goto L41
                    L40:
                        r5 = 0
                    L41:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineScope, startedEagerly, bool);
        final StateFlow shadeExpansion = baseShadeInteractor.getShadeExpansion();
        this.isShadeFullyExpanded = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$4

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$4$2$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$4$2$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$4$2$1
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
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 1065353216(0x3f800000, float:1.0)
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 < 0) goto L40
                        r5 = r3
                        goto L41
                    L40:
                        r5 = 0
                    L41:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final StateFlow shadeExpansion2 = baseShadeInteractor.getShadeExpansion();
        this.isShadeFullyCollapsed = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$5

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$5$2$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$5$2$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$5$2$1
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
                        if (r5 > 0) goto L3f
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isUserInteracting = FlowKt.stateIn(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(baseShadeInteractor.isUserInteractingWithShade(), baseShadeInteractor.isUserInteractingWithQs(), new ShadeInteractorImpl$isUserInteracting$1(null))), coroutineScope, startedEagerly, bool);
        PowerInteractor$special$$inlined$map$2 powerInteractor$special$$inlined$map$2 = powerInteractor.isAsleep;
        Edge.StateToState create$default = Edge.Companion.create$default(Edge.Companion, null, KeyguardState.AOD, 1);
        String str = KeyguardTransitionInteractor.TAG;
        Flow isInTransition = keyguardTransitionInteractor.isInTransition(create$default, null);
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        final Flow flow = keyguardRepositoryImpl.dozeTransitionModel;
        this.isShadeTouchable = FlowKt.combine(powerInteractor$special$$inlined$map$2, isInTransition, new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$6

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$6$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$6$2$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$6$2$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$6$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.DozeTransitionModel r5 = (com.android.systemui.keyguard.shared.model.DozeTransitionModel) r5
                        com.android.systemui.keyguard.shared.model.DozeStateModel r5 = r5.to
                        com.android.systemui.keyguard.shared.model.DozeStateModel r6 = com.android.systemui.keyguard.shared.model.DozeStateModel.DOZE_PULSING
                        if (r5 != r6) goto L3c
                        r5 = r3
                        goto L3d
                    L3c:
                        r5 = 0
                    L3d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeInteractorImpl$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new ShadeInteractorImpl$isShadeTouchable$2(dozeParameters, null));
        ReadonlyStateFlow readonlyStateFlow2 = ((UserSetupRepositoryImpl) userSetupRepository).isUserSetUp;
        ShadeInteractorImpl$isExpandToQsEnabled$1 shadeInteractorImpl$isExpandToQsEnabled$1 = new ShadeInteractorImpl$isExpandToQsEnabled$1(userSwitcherInteractor, null);
        this.isExpandToQsEnabled = FlowKt.combine(readonlyStateFlow, stateIn, keyguardRepositoryImpl.isDozing, readonlyStateFlow2, deviceProvisioningInteractor.isDeviceProvisioned, shadeInteractorImpl$isExpandToQsEnabled$1);
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getAnyExpansion() {
        return this.baseShadeInteractor.getAnyExpansion();
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getQsExpansion() {
        return this.baseShadeInteractor.getQsExpansion();
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getShadeExpansion() {
        return this.baseShadeInteractor.getShadeExpansion();
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getShadeMode() {
        return this.baseShadeInteractor.getShadeMode();
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow isAnyExpanded() {
        return this.baseShadeInteractor.isAnyExpanded();
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isQsBypassingShade() {
        return this.baseShadeInteractor.isQsBypassingShade();
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow isQsExpanded() {
        return this.baseShadeInteractor.isQsExpanded();
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isUserInteractingWithQs() {
        return this.baseShadeInteractor.isUserInteractingWithQs();
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isUserInteractingWithShade() {
        return this.baseShadeInteractor.isUserInteractingWithShade();
    }
}
