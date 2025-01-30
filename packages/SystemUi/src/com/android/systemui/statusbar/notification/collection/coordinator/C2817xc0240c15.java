package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$1", m277f = "KeyguardCoordinator.kt", m278l = {127, 130, 133}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$1 */
/* loaded from: classes2.dex */
final class C2817xc0240c15 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2817xc0240c15(KeyguardCoordinator keyguardCoordinator, Continuation<? super C2817xc0240c15> continuation) {
        super(3, continuation);
        this.this$0 = keyguardCoordinator;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        C2817xc0240c15 c2817xc0240c15 = new C2817xc0240c15(this.this$0, (Continuation) obj3);
        c2817xc0240c15.L$0 = (FlowCollector) obj;
        c2817xc0240c15.Z$0 = booleanValue;
        return c2817xc0240c15.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0060 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    flowCollector = (FlowCollector) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    final Flow flow2 = ((KeyguardTransitionRepositoryImpl) this.this$0.keyguardTransitionRepository).transitions;
                    flow = new Flow() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$1$invokeSuspend$$inlined$map$1

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                            @DebugMetadata(m276c = "com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$1$invokeSuspend$$inlined$map$1$2", m277f = "KeyguardCoordinator.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                            /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:22:0x0052 A[RETURN] */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                int i;
                                boolean z;
                                Boolean valueOf;
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
                                            if (transitionStep.transitionState == TransitionState.STARTED) {
                                                if (transitionStep.f304to != KeyguardState.GONE) {
                                                    z = true;
                                                    valueOf = Boolean.valueOf(!z);
                                                    anonymousClass1.label = 1;
                                                    if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                                        return coroutineSingletons;
                                                    }
                                                }
                                            }
                                            z = false;
                                            valueOf = Boolean.valueOf(!z);
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
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
                        public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    this.L$0 = null;
                    this.label = 3;
                    if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                }
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        flowCollector = (FlowCollector) this.L$0;
        if (this.Z$0) {
            Boolean bool = Boolean.FALSE;
            this.label = 1;
            if (flowCollector.emit(bool, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }
        Boolean bool2 = Boolean.TRUE;
        this.L$0 = flowCollector;
        this.label = 2;
        if (flowCollector.emit(bool2, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        final Flow flow22 = ((KeyguardTransitionRepositoryImpl) this.this$0.keyguardTransitionRepository).transitions;
        flow = new Flow() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$1$invokeSuspend$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$1$invokeSuspend$$inlined$map$1$2", m277f = "KeyguardCoordinator.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:22:0x0052 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    boolean z;
                    Boolean valueOf;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj22 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj22);
                                TransitionStep transitionStep = (TransitionStep) obj;
                                if (transitionStep.transitionState == TransitionState.STARTED) {
                                    if (transitionStep.f304to != KeyguardState.GONE) {
                                        z = true;
                                        valueOf = Boolean.valueOf(!z);
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons2) {
                                            return coroutineSingletons2;
                                        }
                                    }
                                }
                                z = false;
                                valueOf = Boolean.valueOf(!z);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons2) {
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj22);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj222 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons22 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.label = 3;
        if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
        }
        return Unit.INSTANCE;
    }
}
