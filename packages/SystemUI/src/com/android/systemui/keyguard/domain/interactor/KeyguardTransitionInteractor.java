package com.android.systemui.keyguard.domain.interactor;

import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.util.kotlin.WithPrev;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardTransitionInteractor {
    public static final String TAG;
    public final ReadonlyStateFlow currentKeyguardState;
    public final ReadonlyStateFlow finishedKeyguardState;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isInTransition;
    public final PowerInteractor powerInteractor;
    public final KeyguardTransitionRepository repository;
    public final SceneInteractor sceneInteractor;
    public final ReadonlyStateFlow sceneTransitionPair;
    public final ReadonlyStateFlow startedKeyguardTransitionStep;
    public final ReadonlySharedFlow startedStepWithPrecedingStep;
    public final ReadonlyStateFlow transitionState;
    public final Flow transitions;
    public final Map transitionMap = new LinkedHashMap();
    public final Map transitionValueCache = new LinkedHashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$2, reason: invalid class name */
        public final class AnonymousClass2 implements FlowCollector {
            public final /* synthetic */ KeyguardTransitionInteractor this$0;

            public AnonymousClass2(KeyguardTransitionInteractor keyguardTransitionInteractor) {
                this.this$0 = keyguardTransitionInteractor;
            }

            /* JADX WARN: Code restructure failed: missing block: B:18:0x008a, code lost:
            
                if (r7.emit(r8, r0) != r1) goto L26;
             */
            /* JADX WARN: Code restructure failed: missing block: B:19:0x008c, code lost:
            
                return r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:24:0x006d, code lost:
            
                if (r2.emit(r6, r0) == r1) goto L25;
             */
            /* JADX WARN: Removed duplicated region for block: B:20:0x0042  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(com.android.systemui.keyguard.shared.model.TransitionStep r8, kotlin.coroutines.Continuation r9) {
                /*
                    r7 = this;
                    boolean r0 = r9 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$2$emit$1
                    if (r0 == 0) goto L13
                    r0 = r9
                    com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$2$emit$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$2$emit$1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L13
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L18
                L13:
                    com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$2$emit$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$2$emit$1
                    r0.<init>(r7, r9)
                L18:
                    java.lang.Object r9 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 2
                    r4 = 1
                    if (r2 == 0) goto L42
                    if (r2 == r4) goto L32
                    if (r2 != r3) goto L2a
                    kotlin.ResultKt.throwOnFailure(r9)
                    goto L8d
                L2a:
                    java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                    java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                    r7.<init>(r8)
                    throw r7
                L32:
                    float r7 = r0.F$0
                    java.lang.Object r8 = r0.L$1
                    com.android.systemui.keyguard.shared.model.TransitionStep r8 = (com.android.systemui.keyguard.shared.model.TransitionStep) r8
                    java.lang.Object r2 = r0.L$0
                    com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$2 r2 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor.AnonymousClass1.AnonymousClass2) r2
                    kotlin.ResultKt.throwOnFailure(r9)
                    r9 = r7
                    r7 = r2
                    goto L70
                L42:
                    kotlin.ResultKt.throwOnFailure(r9)
                    com.android.systemui.keyguard.shared.model.TransitionState r9 = r8.transitionState
                    com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.FINISHED
                    r5 = 1065353216(0x3f800000, float:1.0)
                    if (r9 != r2) goto L4f
                    r9 = r5
                    goto L51
                L4f:
                    float r9 = r8.value
                L51:
                    java.lang.String r2 = com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor.TAG
                    com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r2 = r7.this$0
                    com.android.systemui.keyguard.shared.model.KeyguardState r6 = r8.from
                    kotlinx.coroutines.flow.MutableSharedFlow r2 = r2.getTransitionValueFlow(r6)
                    float r5 = r5 - r9
                    java.lang.Float r6 = new java.lang.Float
                    r6.<init>(r5)
                    r0.L$0 = r7
                    r0.L$1 = r8
                    r0.F$0 = r9
                    r0.label = r4
                    java.lang.Object r2 = r2.emit(r6, r0)
                    if (r2 != r1) goto L70
                    goto L8c
                L70:
                    com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r7 = r7.this$0
                    com.android.systemui.keyguard.shared.model.KeyguardState r8 = r8.to
                    java.lang.String r2 = com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor.TAG
                    kotlinx.coroutines.flow.MutableSharedFlow r7 = r7.getTransitionValueFlow(r8)
                    java.lang.Float r8 = new java.lang.Float
                    r8.<init>(r9)
                    r9 = 0
                    r0.L$0 = r9
                    r0.L$1 = r9
                    r0.label = r3
                    java.lang.Object r7 = r7.emit(r8, r0)
                    if (r7 != r1) goto L8d
                L8c:
                    return r1
                L8d:
                    kotlin.Unit r7 = kotlin.Unit.INSTANCE
                    return r7
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor.AnonymousClass1.AnonymousClass2.emit(com.android.systemui.keyguard.shared.model.TransitionStep, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardTransitionInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final Flow flow = ((KeyguardTransitionRepositoryImpl) KeyguardTransitionInteractor.this.repository).transitions;
                Flow flow2 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$invokeSuspend$$inlined$filter$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$invokeSuspend$$inlined$filter$1$2$1
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
                                r6 = r5
                                com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                                com.android.systemui.keyguard.shared.model.TransitionState r6 = r6.transitionState
                                com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.CANCELED
                                if (r6 == r2) goto L46
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L46
                                return r1
                            L46:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(KeyguardTransitionInteractor.this);
                this.label = 1;
                if (flow2.collect(anonymousClass2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2$1, reason: invalid class name */
        public final class AnonymousClass1 implements FlowCollector {
            public final /* synthetic */ KeyguardTransitionInteractor this$0;

            public AnonymousClass1(KeyguardTransitionInteractor keyguardTransitionInteractor) {
                this.this$0 = keyguardTransitionInteractor;
            }

            /* JADX WARN: Code restructure failed: missing block: B:22:0x00c5, code lost:
            
                if (r10.emit(r11, r0) == r1) goto L34;
             */
            /* JADX WARN: Code restructure failed: missing block: B:35:0x0079, code lost:
            
                if (r12.emit(r11, r0) == r1) goto L34;
             */
            /* JADX WARN: Removed duplicated region for block: B:21:0x00bb  */
            /* JADX WARN: Removed duplicated region for block: B:24:0x00cb  */
            /* JADX WARN: Removed duplicated region for block: B:29:0x0092  */
            /* JADX WARN: Removed duplicated region for block: B:32:0x0050  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(com.android.systemui.keyguard.shared.model.TransitionStep r11, kotlin.coroutines.Continuation r12) {
                /*
                    Method dump skipped, instructions count: 206
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor.AnonymousClass2.AnonymousClass1.emit(com.android.systemui.keyguard.shared.model.TransitionStep, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardTransitionInteractor.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                KeyguardTransitionInteractor keyguardTransitionInteractor = KeyguardTransitionInteractor.this;
                Flow flow = ((KeyguardTransitionRepositoryImpl) keyguardTransitionInteractor.repository).transitions;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(keyguardTransitionInteractor);
                this.label = 1;
                if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardTransitionInteractor.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final KeyguardTransitionInteractor keyguardTransitionInteractor = KeyguardTransitionInteractor.this;
                ReadonlySharedFlow readonlySharedFlow = keyguardTransitionInteractor.startedStepWithPrecedingStep;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        WithPrev withPrev = (WithPrev) obj2;
                        TransitionStep transitionStep = (TransitionStep) withPrev.component1();
                        TransitionStep transitionStep2 = (TransitionStep) withPrev.component2();
                        TransitionState transitionState = transitionStep.transitionState;
                        if (transitionState == TransitionState.CANCELED) {
                            KeyguardState keyguardState = transitionStep2.to;
                            KeyguardState keyguardState2 = transitionStep.from;
                            if (keyguardState != keyguardState2) {
                                String str = KeyguardTransitionInteractor.TAG;
                                Object emit = KeyguardTransitionInteractor.this.getTransitionValueFlow(keyguardState2).emit(new Float(0.0f), continuation);
                                return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
                            }
                        }
                        if (transitionState == TransitionState.RUNNING) {
                            Log.e(KeyguardTransitionInteractor.TAG, "STARTED step (" + transitionStep2 + ") was preceded by a RUNNING step (" + transitionStep + "), which should never happen. Things could go badly here.");
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlySharedFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$2, reason: invalid class name */
        public final class AnonymousClass2 implements FlowCollector {
            public final /* synthetic */ KeyguardTransitionInteractor this$0;

            public AnonymousClass2(KeyguardTransitionInteractor keyguardTransitionInteractor) {
                this.this$0 = keyguardTransitionInteractor;
            }

            /* JADX WARN: Removed duplicated region for block: B:13:0x0055  */
            /* JADX WARN: Removed duplicated region for block: B:37:0x003d  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(com.android.systemui.keyguard.shared.model.TransitionStep r7, kotlin.coroutines.Continuation r8) {
                /*
                    r6 = this;
                    boolean r0 = r8 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$2$emit$1
                    if (r0 == 0) goto L13
                    r0 = r8
                    com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$2$emit$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$2$emit$1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L13
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L18
                L13:
                    com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$2$emit$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$2$emit$1
                    r0.<init>(r6, r8)
                L18:
                    java.lang.Object r8 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 1
                    if (r2 == 0) goto L3d
                    if (r2 != r3) goto L35
                    java.lang.Object r6 = r0.L$2
                    java.util.Iterator r6 = (java.util.Iterator) r6
                    java.lang.Object r7 = r0.L$1
                    com.android.systemui.keyguard.shared.model.TransitionStep r7 = (com.android.systemui.keyguard.shared.model.TransitionStep) r7
                    java.lang.Object r2 = r0.L$0
                    com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$2 r2 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor.AnonymousClass4.AnonymousClass2) r2
                    kotlin.ResultKt.throwOnFailure(r8)
                    r8 = r7
                    r7 = r2
                    goto L4f
                L35:
                    java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                    java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                    r6.<init>(r7)
                    throw r6
                L3d:
                    kotlin.ResultKt.throwOnFailure(r8)
                    kotlin.enums.EnumEntries r8 = com.android.systemui.keyguard.shared.model.KeyguardState.$ENTRIES
                    kotlin.collections.AbstractList r8 = (kotlin.collections.AbstractList) r8
                    r8.getClass()
                    kotlin.collections.AbstractList$IteratorImpl r2 = new kotlin.collections.AbstractList$IteratorImpl
                    r2.<init>()
                    r8 = r7
                    r7 = r6
                    r6 = r2
                L4f:
                    boolean r2 = r6.hasNext()
                    if (r2 == 0) goto L95
                    java.lang.Object r2 = r6.next()
                    com.android.systemui.keyguard.shared.model.KeyguardState r2 = (com.android.systemui.keyguard.shared.model.KeyguardState) r2
                    com.android.systemui.keyguard.shared.model.KeyguardState r4 = r8.to
                    if (r2 == r4) goto L4f
                    com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r4 = r7.this$0
                    java.lang.String r5 = com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor.TAG
                    kotlinx.coroutines.flow.MutableSharedFlow r2 = r4.getTransitionValueFlow(r2)
                    java.util.List r4 = r2.getReplayCache()
                    boolean r5 = r4.isEmpty()
                    if (r5 != 0) goto L4f
                    java.lang.Object r4 = kotlin.collections.CollectionsKt___CollectionsKt.last(r4)
                    java.lang.Number r4 = (java.lang.Number) r4
                    float r4 = r4.floatValue()
                    r5 = 0
                    int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
                    if (r4 != 0) goto L81
                    goto L4f
                L81:
                    java.lang.Float r4 = new java.lang.Float
                    r4.<init>(r5)
                    r0.L$0 = r7
                    r0.L$1 = r8
                    r0.L$2 = r6
                    r0.label = r3
                    java.lang.Object r2 = r2.emit(r4, r0)
                    if (r2 != r1) goto L4f
                    return r1
                L95:
                    kotlin.Unit r6 = kotlin.Unit.INSTANCE
                    return r6
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor.AnonymousClass4.AnonymousClass2.emit(com.android.systemui.keyguard.shared.model.TransitionStep, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardTransitionInteractor.this.new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final Flow flow = ((KeyguardTransitionRepositoryImpl) KeyguardTransitionInteractor.this.repository).transitions;
                Flow flow2 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$invokeSuspend$$inlined$filter$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$invokeSuspend$$inlined$filter$1$2$1
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
                                r6 = r5
                                com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                                com.android.systemui.keyguard.shared.model.TransitionState r6 = r6.transitionState
                                com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.FINISHED
                                if (r6 != r2) goto L46
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L46
                                return r1
                            L46:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(KeyguardTransitionInteractor.this);
                this.label = 1;
                if (flow2.collect(anonymousClass2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = Reflection.getOrCreateKotlinClass(KeyguardTransitionInteractor.class).getSimpleName();
    }

    public KeyguardTransitionInteractor(CoroutineScope coroutineScope, KeyguardTransitionRepository keyguardTransitionRepository, SceneInteractor sceneInteractor, PowerInteractor powerInteractor) {
        this.repository = keyguardTransitionRepository;
        this.sceneInteractor = sceneInteractor;
        this.powerInteractor = powerInteractor;
        KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = (KeyguardTransitionRepositoryImpl) keyguardTransitionRepository;
        Flow flow = keyguardTransitionRepositoryImpl.transitions;
        this.transitions = flow;
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.transitionState = FlowKt.stateIn(flow, coroutineScope, startedEagerly, new TransitionStep(null, null, 0.0f, null, null, 31, null));
        Flow pairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(sceneInteractor.transitionState);
        ReadonlyStateFlow readonlyStateFlow = sceneInteractor.transitionState;
        this.sceneTransitionPair = FlowKt.stateIn(pairwise, coroutineScope, startedEagerly, new WithPrev(readonlyStateFlow.$$delegate_0.getValue(), readonlyStateFlow.$$delegate_0.getValue()));
        final Flow pairwise2 = com.android.systemui.util.kotlin.FlowKt.pairwise(keyguardTransitionRepositoryImpl.transitions);
        this.startedStepWithPrecedingStep = FlowKt.shareIn(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1$2$1
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
                        r6 = r5
                        com.android.systemui.util.kotlin.WithPrev r6 = (com.android.systemui.util.kotlin.WithPrev) r6
                        java.lang.Object r6 = r6.getNewValue()
                        com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                        com.android.systemui.keyguard.shared.model.TransitionState r6 = r6.transitionState
                        com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.STARTED
                        if (r6 != r2) goto L4c
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, 1);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(null), 6);
        final Flow flow2 = keyguardTransitionRepositoryImpl.transitions;
        this.startedKeyguardTransitionStep = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2$2$1
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
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                        com.android.systemui.keyguard.shared.model.TransitionState r6 = r6.transitionState
                        com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.STARTED
                        if (r6 != r2) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, new TransitionStep(null, null, 0.0f, null, null, 31, null));
        ChannelFlowTransformLatest mapLatest = FlowKt.mapLatest(keyguardTransitionRepositoryImpl.transitions, new KeyguardTransitionInteractor$currentKeyguardState$1(null));
        KeyguardState keyguardState = KeyguardState.OFF;
        this.currentKeyguardState = FlowKt.stateIn(mapLatest, coroutineScope, startedEagerly, keyguardState);
        this.isInTransition = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(isInTransitionWhere(new KeyguardTransitionInteractor$$ExternalSyntheticLambda0(), new KeyguardTransitionInteractor$$ExternalSyntheticLambda0()), readonlyStateFlow, new KeyguardTransitionInteractor$isInTransition$3(null));
        final Flow flow3 = keyguardTransitionRepositoryImpl.transitions;
        final Flow flow4 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3$2$1
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
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                        com.android.systemui.keyguard.shared.model.TransitionState r6 = r6.transitionState
                        com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.FINISHED
                        if (r6 != r2) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.finishedKeyguardState = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1$2$1
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
                        com.android.systemui.keyguard.shared.model.TransitionStep r5 = (com.android.systemui.keyguard.shared.model.TransitionStep) r5
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = r5.to
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, keyguardState);
    }

    public final KeyguardState getCurrentState() {
        return (KeyguardState) CollectionsKt___CollectionsKt.last(this.currentKeyguardState.$$delegate_0.getReplayCache());
    }

    public final MutableSharedFlow getTransitionValueFlow(KeyguardState keyguardState) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) this.transitionValueCache;
        Object obj = linkedHashMap.get(keyguardState);
        Object obj2 = obj;
        if (obj == null) {
            SharedFlowImpl MutableSharedFlow = SharedFlowKt.MutableSharedFlow(1, 2, BufferOverflow.DROP_OLDEST);
            MutableSharedFlow.tryEmit(Float.valueOf(0.0f));
            keyguardState.name();
            linkedHashMap.put(keyguardState, MutableSharedFlow);
            obj2 = MutableSharedFlow;
        }
        return (MutableSharedFlow) obj2;
    }

    public final Flow isFinishedIn(KeyguardState keyguardState) {
        return FlowKt.distinctUntilChanged(isFinishedIn$1(keyguardState));
    }

    public final Flow isFinishedIn$1(final KeyguardState keyguardState) {
        keyguardState.checkValidState();
        final ReadonlyStateFlow readonlyStateFlow = this.finishedKeyguardState;
        return FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedIn$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedIn$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ KeyguardState $state$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedIn$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardState keyguardState) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$state$inlined = keyguardState;
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedIn$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedIn$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedIn$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedIn$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedIn$$inlined$map$1$2$1
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
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = (com.android.systemui.keyguard.shared.model.KeyguardState) r5
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = r4.$state$inlined
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedIn$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, keyguardState), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }

    public final Flow isInTransition(Edge edge, Edge.StateToState stateToState) {
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardTransitionInteractor$isInTransition$7(null), FlowKt.mapLatest(transition(stateToState == null ? edge : stateToState), new KeyguardTransitionInteractor$isInTransition$6(null)));
        Objects.toString(edge);
        Objects.toString(stateToState);
        return FlowKt.distinctUntilChanged(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1);
    }

    public final Flow isInTransitionWhere(Function1 function1, Function1 function12) {
        final Flow flow = ((KeyguardTransitionRepositoryImpl) this.repository).transitions;
        return FlowKt.distinctUntilChanged(FlowKt.mapLatest(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransitionWhere$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransitionWhere$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransitionWhere$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransitionWhere$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransitionWhere$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransitionWhere$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransitionWhere$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransitionWhere$$inlined$filter$1$2$1
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
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                        com.android.systemui.keyguard.shared.model.TransitionState r6 = r6.transitionState
                        com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.CANCELED
                        if (r6 == r2) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransitionWhere$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new KeyguardTransitionInteractor$isInTransitionWhere$4(function1, function12, null)));
    }

    public final Flow transition(Edge edge) {
        edge.getClass();
        if (edge instanceof Edge.StateToState) {
            Edge.StateToState stateToState = (Edge.StateToState) edge;
            Edge.verifyValidKeyguardStates(stateToState.from, stateToState.to);
        } else if (edge instanceof Edge.ContentToState) {
            Edge.verifyValidKeyguardStates(null, ((Edge.ContentToState) edge).to);
        } else {
            if (!(edge instanceof Edge.StateToContent)) {
                throw new NoWhenBranchMatchedException();
            }
            Edge.verifyValidKeyguardStates(((Edge.StateToContent) edge).from, null);
        }
        Edge.StateToState stateToState2 = (Edge.StateToState) edge;
        LinkedHashMap linkedHashMap = (LinkedHashMap) this.transitionMap;
        Object obj = linkedHashMap.get(stateToState2);
        if (obj == null) {
            obj = SharedFlowKt.MutableSharedFlow$default(0, 10, BufferOverflow.DROP_OLDEST, 1);
            Objects.toString(stateToState2.from);
            Objects.toString(stateToState2.to);
            linkedHashMap.put(stateToState2, obj);
        }
        return (Flow) obj;
    }
}
