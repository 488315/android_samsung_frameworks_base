package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HeadsUpNotificationInteractor {
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 canShowHeadsUp;
    public final HeadsUpNotificationInteractor$special$$inlined$map$1 hasPinnedRows;
    public final HeadsUpRepository headsUpRepository;
    public final Lazy isHeadsUpOrAnimatingAway$delegate;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 statusBarHeadsUpState;
    public final HeadsUpNotificationInteractor$special$$inlined$map$2 statusBarHeadsUpStatus;
    public final StateFlowImpl topHeadsUpRow;
    public final Flow topHeadsUpRowIfPinned;
    public final ChannelFlowTransformLatest topPinnedState;

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2] */
    public HeadsUpNotificationInteractor(HeadsUpRepository headsUpRepository, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, NotificationsKeyguardInteractor notificationsKeyguardInteractor, ShadeInteractor shadeInteractor) {
        this.headsUpRepository = headsUpRepository;
        HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) headsUpRepository;
        StateFlowImpl stateFlowImpl = headsUpManagerImpl.mTopHeadsUpRow;
        this.topHeadsUpRow = stateFlowImpl;
        this.topHeadsUpRowIfPinned = FlowKt.distinctUntilChanged(FlowKt.transformLatest(stateFlowImpl, new HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$1(null)));
        final int i = 0;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i2 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i3 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    case 2:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i4 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    default:
                        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
                        int i5 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils4.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                }
            }
        });
        final int i2 = 1;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i3 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    case 2:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i4 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    default:
                        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
                        int i5 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils4.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                }
            }
        });
        final int i3 = 2;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i32 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    case 2:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i4 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    default:
                        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
                        int i5 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils4.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                }
            }
        });
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(headsUpManagerImpl.mHeadsUpNotificationRows, new HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$2(null));
        this.topPinnedState = transformLatest;
        this.hasPinnedRows = new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L55
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.notification.domain.model.TopPinnedState r5 = (com.android.systemui.statusbar.notification.domain.model.TopPinnedState) r5
                        boolean r6 = r5 instanceof com.android.systemui.statusbar.notification.domain.model.TopPinnedState.Pinned
                        if (r6 == 0) goto L41
                        com.android.systemui.statusbar.notification.domain.model.TopPinnedState$Pinned r5 = (com.android.systemui.statusbar.notification.domain.model.TopPinnedState.Pinned) r5
                        com.android.systemui.statusbar.notification.headsup.PinnedStatus r5 = r5.status
                        boolean r5 = r5.isPinned()
                        goto L46
                    L41:
                        boolean r5 = r5 instanceof com.android.systemui.statusbar.notification.domain.model.TopPinnedState.NothingPinned
                        if (r5 == 0) goto L58
                        r5 = 0
                    L46:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L55
                        return r1
                    L55:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    L58:
                        kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                        r4.<init>()
                        throw r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final int i4 = 3;
        this.isHeadsUpOrAnimatingAway$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i32 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    case 2:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i42 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
                    default:
                        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
                        int i5 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils4.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                }
            }
        });
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 combine = FlowKt.combine(deviceEntryFaceAuthInteractor.isBypassEnabled(), ((ShadeInteractorImpl) shadeInteractor).isShadeFullyCollapsed, keyguardTransitionInteractor.currentKeyguardState, notificationsKeyguardInteractor.areNotificationsFullyHidden, new HeadsUpNotificationInteractor$canShowHeadsUp$1(null));
        this.canShowHeadsUp = combine;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(transformLatest, combine, new HeadsUpNotificationInteractor$statusBarHeadsUpState$1(null));
        this.statusBarHeadsUpState = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.statusBarHeadsUpStatus = new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.notification.domain.model.TopPinnedState r5 = (com.android.systemui.statusbar.notification.domain.model.TopPinnedState) r5
                        boolean r6 = r5 instanceof com.android.systemui.statusbar.notification.domain.model.TopPinnedState.Pinned
                        if (r6 == 0) goto L3d
                        com.android.systemui.statusbar.notification.domain.model.TopPinnedState$Pinned r5 = (com.android.systemui.statusbar.notification.domain.model.TopPinnedState.Pinned) r5
                        com.android.systemui.statusbar.notification.headsup.PinnedStatus r5 = r5.status
                        goto L43
                    L3d:
                        boolean r5 = r5 instanceof com.android.systemui.statusbar.notification.domain.model.TopPinnedState.NothingPinned
                        if (r5 == 0) goto L51
                        com.android.systemui.statusbar.notification.headsup.PinnedStatus r5 = com.android.systemui.statusbar.notification.headsup.PinnedStatus.NotPinned
                    L43:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4e
                        return r1
                    L4e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    L51:
                        kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                        r4.<init>()
                        throw r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
