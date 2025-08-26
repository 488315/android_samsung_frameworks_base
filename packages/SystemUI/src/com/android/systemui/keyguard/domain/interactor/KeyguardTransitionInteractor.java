package com.android.systemui.keyguard.domain.interactor;

import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.util.kotlin.WithPrev;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.IteratorImpl;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
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

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$1$2, reason: invalid class name */
        public final class AnonymousClass2 implements FlowCollector {
            public final /* synthetic */ KeyguardTransitionInteractor this$0;

            public AnonymousClass2(KeyguardTransitionInteractor keyguardTransitionInteractor) {
                this.this$0 = keyguardTransitionInteractor;
            }

            /* JADX WARN: Code restructure failed: missing block: B:24:0x008a, code lost:
            
                if (r7.emit(r8, r0) == r1) goto L25;
             */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(TransitionStep transitionStep, Continuation continuation) {
                KeyguardTransitionInteractor$1$2$emit$1 keyguardTransitionInteractor$1$2$emit$1;
                float f;
                if (continuation instanceof KeyguardTransitionInteractor$1$2$emit$1) {
                    keyguardTransitionInteractor$1$2$emit$1 = (KeyguardTransitionInteractor$1$2$emit$1) continuation;
                    int i = keyguardTransitionInteractor$1$2$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        keyguardTransitionInteractor$1$2$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        keyguardTransitionInteractor$1$2$emit$1 = new KeyguardTransitionInteractor$1$2$emit$1(this, continuation);
                    }
                }
                Object obj = keyguardTransitionInteractor$1$2$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = keyguardTransitionInteractor$1$2$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    f = transitionStep.transitionState == TransitionState.FINISHED ? 1.0f : transitionStep.value;
                    String str = KeyguardTransitionInteractor.TAG;
                    MutableSharedFlow transitionValueFlow = this.this$0.getTransitionValueFlow(transitionStep.from);
                    Float f2 = new Float(1.0f - f);
                    keyguardTransitionInteractor$1$2$emit$1.L$0 = this;
                    keyguardTransitionInteractor$1$2$emit$1.L$1 = transitionStep;
                    keyguardTransitionInteractor$1$2$emit$1.F$0 = f;
                    keyguardTransitionInteractor$1$2$emit$1.label = 1;
                    if (transitionValueFlow.emit(f2, keyguardTransitionInteractor$1$2$emit$1) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                float f3 = keyguardTransitionInteractor$1$2$emit$1.F$0;
                transitionStep = (TransitionStep) keyguardTransitionInteractor$1$2$emit$1.L$1;
                AnonymousClass2 anonymousClass2 = (AnonymousClass2) keyguardTransitionInteractor$1$2$emit$1.L$0;
                ResultKt.throwOnFailure(obj);
                f = f3;
                this = anonymousClass2;
                KeyguardTransitionInteractor keyguardTransitionInteractor = this.this$0;
                KeyguardState keyguardState = transitionStep.to;
                String str2 = KeyguardTransitionInteractor.TAG;
                MutableSharedFlow transitionValueFlow2 = keyguardTransitionInteractor.getTransitionValueFlow(keyguardState);
                Float f4 = new Float(f);
                keyguardTransitionInteractor$1$2$emit$1.L$0 = null;
                keyguardTransitionInteractor$1$2$emit$1.L$1 = null;
                keyguardTransitionInteractor$1$2$emit$1.label = 2;
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

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
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
                                if (((TransitionStep) obj).transitionState != TransitionState.CANCELED) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
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
                        Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2$1, reason: invalid class name */
        public final class AnonymousClass1 implements FlowCollector {
            public final /* synthetic */ KeyguardTransitionInteractor this$0;

            public AnonymousClass1(KeyguardTransitionInteractor keyguardTransitionInteractor) {
                this.this$0 = keyguardTransitionInteractor;
            }

            /* JADX WARN: Code restructure failed: missing block: B:33:0x00c5, code lost:
            
                if (r10.emit(r11, r0) == r1) goto L34;
             */
            /* JADX WARN: Removed duplicated region for block: B:32:0x00bb  */
            /* JADX WARN: Removed duplicated region for block: B:37:0x00cb  */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(TransitionStep transitionStep, Continuation continuation) {
                KeyguardTransitionInteractor$2$1$emit$1 keyguardTransitionInteractor$2$1$emit$1;
                AnonymousClass1 anonymousClass1;
                TransitionStep transitionStep2;
                MutableSharedFlow mutableSharedFlow;
                if (continuation instanceof KeyguardTransitionInteractor$2$1$emit$1) {
                    keyguardTransitionInteractor$2$1$emit$1 = (KeyguardTransitionInteractor$2$1$emit$1) continuation;
                    int i = keyguardTransitionInteractor$2$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        keyguardTransitionInteractor$2$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        keyguardTransitionInteractor$2$1$emit$1 = new KeyguardTransitionInteractor$2$1$emit$1(this, continuation);
                    }
                }
                Object obj = keyguardTransitionInteractor$2$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = keyguardTransitionInteractor$2$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    Map map = this.this$0.transitionMap;
                    Edge.Companion companion = Edge.Companion;
                    KeyguardState keyguardState = transitionStep.from;
                    companion.getClass();
                    MutableSharedFlow mutableSharedFlow2 = (MutableSharedFlow) ((LinkedHashMap) map).get(new Edge.StateToState(keyguardState, transitionStep.to));
                    if (mutableSharedFlow2 != null) {
                        keyguardTransitionInteractor$2$1$emit$1.L$0 = this;
                        keyguardTransitionInteractor$2$1$emit$1.L$1 = transitionStep;
                        keyguardTransitionInteractor$2$1$emit$1.label = 1;
                        if (mutableSharedFlow2.emit(transitionStep, keyguardTransitionInteractor$2$1$emit$1) != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                } else if (i2 == 1) {
                    transitionStep = (TransitionStep) keyguardTransitionInteractor$2$1$emit$1.L$1;
                    this = (AnonymousClass1) keyguardTransitionInteractor$2$1$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                } else {
                    if (i2 != 2) {
                        if (i2 != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    transitionStep2 = (TransitionStep) keyguardTransitionInteractor$2$1$emit$1.L$1;
                    anonymousClass1 = (AnonymousClass1) keyguardTransitionInteractor$2$1$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    AnonymousClass1 anonymousClass12 = anonymousClass1;
                    transitionStep = transitionStep2;
                    this = anonymousClass12;
                    mutableSharedFlow = (MutableSharedFlow) ((LinkedHashMap) this.this$0.transitionMap).get(KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, null, transitionStep.to));
                    if (mutableSharedFlow == null) {
                        return Unit.INSTANCE;
                    }
                    keyguardTransitionInteractor$2$1$emit$1.L$0 = null;
                    keyguardTransitionInteractor$2$1$emit$1.L$1 = null;
                    keyguardTransitionInteractor$2$1$emit$1.label = 3;
                }
                MutableSharedFlow mutableSharedFlow3 = (MutableSharedFlow) ((LinkedHashMap) this.this$0.transitionMap).get(KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, transitionStep.from, null));
                if (mutableSharedFlow3 != null) {
                    keyguardTransitionInteractor$2$1$emit$1.L$0 = this;
                    keyguardTransitionInteractor$2$1$emit$1.L$1 = transitionStep;
                    keyguardTransitionInteractor$2$1$emit$1.label = 2;
                    if (mutableSharedFlow3.emit(transitionStep, keyguardTransitionInteractor$2$1$emit$1) != coroutineSingletons) {
                        TransitionStep transitionStep3 = transitionStep;
                        anonymousClass1 = this;
                        transitionStep2 = transitionStep3;
                        AnonymousClass1 anonymousClass122 = anonymousClass1;
                        transitionStep = transitionStep2;
                        this = anonymousClass122;
                        mutableSharedFlow = (MutableSharedFlow) ((LinkedHashMap) this.this$0.transitionMap).get(KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, null, transitionStep.to));
                        if (mutableSharedFlow == null) {
                        }
                    }
                } else {
                    mutableSharedFlow = (MutableSharedFlow) ((LinkedHashMap) this.this$0.transitionMap).get(KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, null, transitionStep.to));
                    if (mutableSharedFlow == null) {
                    }
                }
                return coroutineSingletons;
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
                                Object objEmit = keyguardTransitionInteractor.getTransitionValueFlow(keyguardState2).emit(new Float(0.0f), continuation);
                                return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
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

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$4$2, reason: invalid class name */
        public final class AnonymousClass2 implements FlowCollector {
            public final /* synthetic */ KeyguardTransitionInteractor this$0;

            public AnonymousClass2(KeyguardTransitionInteractor keyguardTransitionInteractor) {
                this.this$0 = keyguardTransitionInteractor;
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(TransitionStep transitionStep, Continuation continuation) {
                KeyguardTransitionInteractor$4$2$emit$1 keyguardTransitionInteractor$4$2$emit$1;
                TransitionStep transitionStep2;
                AnonymousClass2 anonymousClass2;
                Iterator it;
                if (continuation instanceof KeyguardTransitionInteractor$4$2$emit$1) {
                    keyguardTransitionInteractor$4$2$emit$1 = (KeyguardTransitionInteractor$4$2$emit$1) continuation;
                    int i = keyguardTransitionInteractor$4$2$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        keyguardTransitionInteractor$4$2$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        keyguardTransitionInteractor$4$2$emit$1 = new KeyguardTransitionInteractor$4$2$emit$1(this, continuation);
                    }
                }
                Object obj = keyguardTransitionInteractor$4$2$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = keyguardTransitionInteractor$4$2$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    AbstractList abstractList = (AbstractList) KeyguardState.$ENTRIES;
                    abstractList.getClass();
                    AbstractList.IteratorImpl iteratorImpl = abstractList.new IteratorImpl();
                    transitionStep2 = transitionStep;
                    anonymousClass2 = this;
                    it = iteratorImpl;
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    it = (Iterator) keyguardTransitionInteractor$4$2$emit$1.L$2;
                    TransitionStep transitionStep3 = (TransitionStep) keyguardTransitionInteractor$4$2$emit$1.L$1;
                    AnonymousClass2 anonymousClass22 = (AnonymousClass2) keyguardTransitionInteractor$4$2$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    transitionStep2 = transitionStep3;
                    anonymousClass2 = anonymousClass22;
                }
                while (it.hasNext()) {
                    KeyguardState keyguardState = (KeyguardState) it.next();
                    if (keyguardState != transitionStep2.to) {
                        KeyguardTransitionInteractor keyguardTransitionInteractor = anonymousClass2.this$0;
                        String str = KeyguardTransitionInteractor.TAG;
                        MutableSharedFlow transitionValueFlow = keyguardTransitionInteractor.getTransitionValueFlow(keyguardState);
                        List replayCache = transitionValueFlow.getReplayCache();
                        if (!replayCache.isEmpty() && ((Number) CollectionsKt___CollectionsKt.last(replayCache)).floatValue() != 0.0f) {
                            Float f = new Float(0.0f);
                            keyguardTransitionInteractor$4$2$emit$1.L$0 = anonymousClass2;
                            keyguardTransitionInteractor$4$2$emit$1.L$1 = transitionStep2;
                            keyguardTransitionInteractor$4$2$emit$1.L$2 = it;
                            keyguardTransitionInteractor$4$2$emit$1.label = 1;
                            if (transitionValueFlow.emit(f, keyguardTransitionInteractor$4$2$emit$1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    }
                }
                return Unit.INSTANCE;
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

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
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
                                if (((TransitionStep) obj).transitionState == TransitionState.FINISHED) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
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
                        Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransition$3, reason: invalid class name and case insensitive filesystem */
    final class C09143 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ boolean Z$0;
        int label;

        public C09143(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            C09143 c09143 = new C09143((Continuation) obj3);
            c09143.Z$0 = zBooleanValue;
            c09143.L$0 = (ObservableTransitionState) obj2;
            return c09143.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            return Boolean.valueOf(z);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransition$6, reason: invalid class name */
    final class AnonymousClass6 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass6(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass6 anonymousClass6 = new AnonymousClass6(continuation);
            anonymousClass6.L$0 = obj;
            return anonymousClass6;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass6) create((TransitionStep) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(((TransitionStep) this.L$0).transitionState.isTransitioning());
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransition$7, reason: invalid class name */
    final class AnonymousClass7 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass7(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass7 anonymousClass7 = new AnonymousClass7(continuation);
            anonymousClass7.L$0 = obj;
            return anonymousClass7;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass7) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Boolean bool = Boolean.FALSE;
                this.label = 1;
                if (flowCollector.emit(bool, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransitionWhere$4, reason: invalid class name and case insensitive filesystem */
    final class C09154 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $fromStatePredicate;
        final /* synthetic */ Function1 $toStatePredicate;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09154(Function1 function1, Function1 function12, Continuation continuation) {
            super(2, continuation);
            this.$fromStatePredicate = function1;
            this.$toStatePredicate = function12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C09154 c09154 = new C09154(this.$fromStatePredicate, this.$toStatePredicate, continuation);
            c09154.L$0 = obj;
            return c09154;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09154) create((TransitionStep) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            TransitionStep transitionStep = (TransitionStep) this.L$0;
            return Boolean.valueOf(transitionStep.transitionState != TransitionState.FINISHED && ((Boolean) this.$fromStatePredicate.mo781invoke(transitionStep.from)).booleanValue() && ((Boolean) this.$toStatePredicate.mo781invoke(transitionStep.to)).booleanValue());
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
        Flow flowPairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(sceneInteractor.transitionState);
        ReadonlyStateFlow readonlyStateFlow = sceneInteractor.transitionState;
        this.sceneTransitionPair = FlowKt.stateIn(flowPairwise, coroutineScope, startedEagerly, new WithPrev(readonlyStateFlow.$$delegate_0.getValue(), readonlyStateFlow.$$delegate_0.getValue()));
        final Flow flowPairwise2 = com.android.systemui.util.kotlin.FlowKt.pairwise(keyguardTransitionRepositoryImpl.transitions);
        this.startedStepWithPrecedingStep = FlowKt.shareIn(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        if (((TransitionStep) ((WithPrev) obj).getNewValue()).transitionState == TransitionState.STARTED) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = flowPairwise2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, 1);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(null), 6);
        final Flow flow2 = keyguardTransitionRepositoryImpl.transitions;
        this.startedKeyguardTransitionStep = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        if (((TransitionStep) obj).transitionState == TransitionState.STARTED) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, new TransitionStep(null, null, 0.0f, null, null, 31, null));
        ChannelFlowTransformLatest channelFlowTransformLatestMapLatest = FlowKt.mapLatest(keyguardTransitionRepositoryImpl.transitions, new KeyguardTransitionInteractor$currentKeyguardState$1(null));
        KeyguardState keyguardState = KeyguardState.OFF;
        this.currentKeyguardState = FlowKt.stateIn(channelFlowTransformLatestMapLatest, coroutineScope, startedEagerly, keyguardState);
        this.isInTransition = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(isInTransitionWhere(new KeyguardTransitionInteractor$$ExternalSyntheticLambda0(), new KeyguardTransitionInteractor$$ExternalSyntheticLambda0()), readonlyStateFlow, new C09143(null));
        final Flow flow3 = keyguardTransitionRepositoryImpl.transitions;
        final Flow flow4 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        if (((TransitionStep) obj).transitionState == TransitionState.FINISHED) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = flow3.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.finishedKeyguardState = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        KeyguardState keyguardState = ((TransitionStep) obj).to;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(keyguardState, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow4.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
            SharedFlowImpl sharedFlowImplMutableSharedFlow = SharedFlowKt.MutableSharedFlow(1, 2, BufferOverflow.DROP_OLDEST);
            sharedFlowImplMutableSharedFlow.tryEmit(Float.valueOf(0.0f));
            keyguardState.name();
            linkedHashMap.put(keyguardState, sharedFlowImplMutableSharedFlow);
            obj2 = sharedFlowImplMutableSharedFlow;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        Boolean boolValueOf = Boolean.valueOf(((KeyguardState) obj) == this.$state$inlined);
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, keyguardState), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
    }

    public final Flow isInTransition(Edge edge, Edge.StateToState stateToState) {
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass7(null), FlowKt.mapLatest(transition(stateToState == null ? edge : stateToState), new AnonymousClass6(null)));
        Objects.toString(edge);
        Objects.toString(stateToState);
        return FlowKt.distinctUntilChanged(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1);
    }

    public final Flow isInTransitionWhere(Function1 function1, Function1 function12) {
        final Flow flow = ((KeyguardTransitionRepositoryImpl) this.repository).transitions;
        return FlowKt.distinctUntilChanged(FlowKt.mapLatest(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isInTransitionWhere$$inlined$filter$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        if (((TransitionStep) obj).transitionState != TransitionState.CANCELED) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new C09154(function1, function12, null)));
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
        Object objMutableSharedFlow$default = linkedHashMap.get(stateToState2);
        if (objMutableSharedFlow$default == null) {
            objMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 10, BufferOverflow.DROP_OLDEST, 1);
            Objects.toString(stateToState2.from);
            Objects.toString(stateToState2.to);
            linkedHashMap.put(stateToState2, objMutableSharedFlow$default);
        }
        return (Flow) objMutableSharedFlow$default;
    }
}
