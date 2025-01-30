package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.C1633x38e7c866;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardTransitionInteractor {
    public final KeyguardTransitionInteractor$special$$inlined$filter$2 anyStateToAodTransition;
    public final KeyguardTransitionInteractor$special$$inlined$filter$1 anyStateToGoneTransition;
    public final C1633x38e7c866 aodToLockscreenTransition;
    public final KeyguardTransitionInteractor$special$$inlined$filter$5 canceledKeyguardTransitionStep;
    public final ChannelLimitedFlowMerge dozeAmountTransition;
    public final C1633x38e7c866 dozingToLockscreenTransition;
    public final C1633x38e7c866 dreamingToLockscreenTransition;
    public final ReadonlyStateFlow finishedKeyguardState;
    public final KeyguardTransitionInteractor$special$$inlined$filter$6 finishedKeyguardTransitionStep;
    public final KeyguardTransitionInteractor$special$$inlined$filter$3 fromDreamingTransition;
    public final C1633x38e7c866 goneToDreamingTransition;
    public final C1633x38e7c866 lockscreenToAodTransition;
    public final C1633x38e7c866 lockscreenToDreamingTransition;
    public final C1633x38e7c866 lockscreenToOccludedTransition;
    public final C1633x38e7c866 occludedToLockscreenTransition;
    public final C1633x38e7c866 offToLockscreenTransition;
    public final C1633x38e7c866 primaryBouncerToGoneTransition;
    public final KeyguardTransitionRepository repository;
    public final ReadonlyStateFlow startedKeyguardState;
    public final KeyguardTransitionInteractor$special$$inlined$filter$4 startedKeyguardTransitionStep;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3] */
    public KeyguardTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository, CoroutineScope coroutineScope) {
        this.repository = keyguardTransitionRepository;
        KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = (KeyguardTransitionRepositoryImpl) keyguardTransitionRepository;
        final Flow flow = keyguardTransitionRepositoryImpl.transitions;
        this.anyStateToGoneTransition = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1$2 */
            public final class C17142 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1$2", m277f = "KeyguardTransitionInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C17142.this.emit(null, this);
                    }
                }

                public C17142(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                if (((TransitionStep) obj).f304to == KeyguardState.GONE) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C17142(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow2 = keyguardTransitionRepositoryImpl.transitions;
        this.anyStateToAodTransition = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2$2 */
            public final class C17152 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2$2", m277f = "KeyguardTransitionInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C17152.this.emit(null, this);
                    }
                }

                public C17152(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                if (((TransitionStep) obj).f304to == KeyguardState.AOD) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C17152(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow3 = keyguardTransitionRepositoryImpl.transitions;
        this.fromDreamingTransition = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3$2 */
            public final class C17162 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3$2", m277f = "KeyguardTransitionInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C17162.this.emit(null, this);
                    }
                }

                public C17162(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                if (((TransitionStep) obj).from == KeyguardState.DREAMING) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C17162(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        KeyguardState keyguardState = KeyguardState.AOD;
        KeyguardState keyguardState2 = KeyguardState.LOCKSCREEN;
        final C1633x38e7c866 transition = keyguardTransitionRepositoryImpl.transition(keyguardState, keyguardState2);
        this.aodToLockscreenTransition = transition;
        KeyguardState keyguardState3 = KeyguardState.DREAMING;
        this.dreamingToLockscreenTransition = keyguardTransitionRepositoryImpl.transition(keyguardState3, keyguardState2);
        KeyguardState keyguardState4 = KeyguardState.GONE;
        this.goneToDreamingTransition = keyguardTransitionRepositoryImpl.transition(keyguardState4, keyguardState3);
        C1633x38e7c866 transition2 = keyguardTransitionRepositoryImpl.transition(keyguardState2, keyguardState);
        this.lockscreenToAodTransition = transition2;
        this.lockscreenToDreamingTransition = keyguardTransitionRepositoryImpl.transition(keyguardState2, keyguardState3);
        KeyguardState keyguardState5 = KeyguardState.OCCLUDED;
        this.lockscreenToOccludedTransition = keyguardTransitionRepositoryImpl.transition(keyguardState2, keyguardState5);
        this.occludedToLockscreenTransition = keyguardTransitionRepositoryImpl.transition(keyguardState5, keyguardState2);
        this.primaryBouncerToGoneTransition = keyguardTransitionRepositoryImpl.transition(KeyguardState.PRIMARY_BOUNCER, keyguardState4);
        KeyguardState keyguardState6 = KeyguardState.OFF;
        this.offToLockscreenTransition = keyguardTransitionRepositoryImpl.transition(keyguardState6, keyguardState2);
        this.dozingToLockscreenTransition = keyguardTransitionRepositoryImpl.transition(KeyguardState.DOZING, keyguardState2);
        this.dozeAmountTransition = FlowKt.merge(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1$2 */
            public final class C17202 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1$2", m277f = "KeyguardTransitionInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C17202.this.emit(null, this);
                    }
                }

                public C17202(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                TransitionStep transitionStep = (TransitionStep) obj;
                                TransitionStep copy$default = TransitionStep.copy$default(transitionStep, 1.0f - transitionStep.value, null, 27);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(copy$default, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C17202(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, transition2);
        final KeyguardTransitionInteractor$special$$inlined$filter$4 keyguardTransitionInteractor$special$$inlined$filter$4 = new KeyguardTransitionInteractor$special$$inlined$filter$4(keyguardTransitionRepositoryImpl.transitions);
        this.startedKeyguardTransitionStep = keyguardTransitionInteractor$special$$inlined$filter$4;
        this.canceledKeyguardTransitionStep = new KeyguardTransitionInteractor$special$$inlined$filter$5(keyguardTransitionRepositoryImpl.transitions);
        final KeyguardTransitionInteractor$special$$inlined$filter$6 keyguardTransitionInteractor$special$$inlined$filter$6 = new KeyguardTransitionInteractor$special$$inlined$filter$6(keyguardTransitionRepositoryImpl.transitions);
        this.finishedKeyguardTransitionStep = keyguardTransitionInteractor$special$$inlined$filter$6;
        Flow flow4 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$2$2 */
            public final class C17212 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$2$2", m277f = "KeyguardTransitionInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C17212.this.emit(null, this);
                    }
                }

                public C17212(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                KeyguardState keyguardState = ((TransitionStep) obj).f304to;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(keyguardState, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C17212(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.startedKeyguardState = FlowKt.stateIn(flow4, coroutineScope, startedEagerly, keyguardState6);
        this.finishedKeyguardState = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$3$2 */
            public final class C17222 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$3$2", m277f = "KeyguardTransitionInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C17222.this.emit(null, this);
                    }
                }

                public C17222(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                KeyguardState keyguardState = ((TransitionStep) obj).f304to;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(keyguardState, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C17222(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, keyguardState2);
    }
}
