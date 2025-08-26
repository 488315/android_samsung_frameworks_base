package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository;
import com.android.systemui.statusbar.notification.domain.model.TopPinnedState;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.PinnedStatus;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
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
        final ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(headsUpManagerImpl.mHeadsUpNotificationRows, new HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$2(null));
        this.topPinnedState = channelFlowTransformLatestTransformLatest;
        this.hasPinnedRows = new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    boolean zIsPinned;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        TopPinnedState topPinnedState = (TopPinnedState) obj;
                        if (topPinnedState instanceof TopPinnedState.Pinned) {
                            zIsPinned = ((TopPinnedState.Pinned) topPinnedState).status.isPinned();
                        } else {
                            if (!(topPinnedState instanceof TopPinnedState.NothingPinned)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            zIsPinned = false;
                        }
                        Boolean boolValueOf = Boolean.valueOf(zIsPinned);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelFlowTransformLatestTransformLatest.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine = FlowKt.combine(deviceEntryFaceAuthInteractor.isBypassEnabled(), ((ShadeInteractorImpl) shadeInteractor).isShadeFullyCollapsed, keyguardTransitionInteractor.currentKeyguardState, notificationsKeyguardInteractor.areNotificationsFullyHidden, new HeadsUpNotificationInteractor$canShowHeadsUp$1(null));
        this.canShowHeadsUp = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(channelFlowTransformLatestTransformLatest, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine, new HeadsUpNotificationInteractor$statusBarHeadsUpState$1(null));
        this.statusBarHeadsUpState = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.statusBarHeadsUpStatus = new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    PinnedStatus pinnedStatus;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        TopPinnedState topPinnedState = (TopPinnedState) obj;
                        if (topPinnedState instanceof TopPinnedState.Pinned) {
                            pinnedStatus = ((TopPinnedState.Pinned) topPinnedState).status;
                        } else {
                            if (!(topPinnedState instanceof TopPinnedState.NothingPinned)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            pinnedStatus = PinnedStatus.NotPinned;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(pinnedStatus, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
