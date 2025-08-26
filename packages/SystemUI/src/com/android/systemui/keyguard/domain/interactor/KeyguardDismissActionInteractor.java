package com.android.systemui.keyguard.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.DismissAction;
import com.android.systemui.keyguard.shared.model.KeyguardDone;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBuffer$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.scene.shared.model.Scenes;
import dagger.Lazy;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes2.dex */
public final class KeyguardDismissActionInteractor extends ExclusiveActivatable {
    public final StateFlow dismissAction;
    public final KeyguardDismissInteractor dismissInteractor;
    public final KeyguardDismissActionInteractor$special$$inlined$map$5 finishedTransitionToGone;
    public final SafeFlow isOnShadeWhileUnlocked;
    public final KeyguardLogger keyguardLogger;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public final KeyguardRepository repository;
    public final ReadonlyStateFlow willAnimateDismissActionOnLockscreen;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyguardDismissActionInteractor.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ KeyguardDismissActionInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(KeyguardDismissActionInteractor keyguardDismissActionInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = keyguardDismissActionInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
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
                    KeyguardDismissActionInteractor keyguardDismissActionInteractor = this.this$0;
                    KeyguardDismissActionInteractor$special$$inlined$map$5 keyguardDismissActionInteractor$special$$inlined$map$5 = keyguardDismissActionInteractor.finishedTransitionToGone;
                    final SafeFlow safeFlow = keyguardDismissActionInteractor.isOnShadeWhileUnlocked;
                    final Flow flow = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                    if (((Boolean) obj).booleanValue()) {
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
                            Object objCollect = safeFlow.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(keyguardDismissActionInteractor$special$$inlined$map$5, new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                    ((Boolean) obj).getClass();
                                    Unit unit = Unit.INSTANCE;
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
                            Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    });
                    final KeyguardDismissActionInteractor keyguardDismissActionInteractor2 = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor.onActivated.2.1.3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                            KeyguardDismissActionInteractor keyguardDismissActionInteractor3 = keyguardDismissActionInteractor2;
                            KeyguardDismissActionInteractor.access$log(keyguardDismissActionInteractor3, "finishedTransitionToGone");
                            Object objAccess$runDismissAction = KeyguardDismissActionInteractor.access$runDismissAction(keyguardDismissActionInteractor3, continuation);
                            return objAccess$runDismissAction == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$runDismissAction : Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (channelLimitedFlowMergeMerge.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C02222 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ KeyguardDismissActionInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02222(KeyguardDismissActionInteractor keyguardDismissActionInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = keyguardDismissActionInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02222(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02222) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KeyguardDismissActionInteractor keyguardDismissActionInteractor = this.this$0;
                    KeyguardDismissInteractor$special$$inlined$map$5 keyguardDismissInteractor$special$$inlined$map$5 = keyguardDismissActionInteractor.dismissInteractor.dismissKeyguardRequestWithImmediateDismissAction;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor.onActivated.2.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                            KeyguardDismissActionInteractor keyguardDismissActionInteractor2 = keyguardDismissActionInteractor;
                            KeyguardDismissActionInteractor.access$log(keyguardDismissActionInteractor2, "eventsThatRequireKeyguardDismissal");
                            Object objAccess$runDismissAction = KeyguardDismissActionInteractor.access$runDismissAction(keyguardDismissActionInteractor2, continuation);
                            return objAccess$runDismissAction == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$runDismissAction : Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (keyguardDismissInteractor$special$$inlined$map$5.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$onActivated$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ KeyguardDismissActionInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(KeyguardDismissActionInteractor keyguardDismissActionInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = keyguardDismissActionInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
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
                    final KeyguardDismissActionInteractor keyguardDismissActionInteractor = this.this$0;
                    ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) keyguardDismissActionInteractor.repository).dismissAction;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor.onActivated.2.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardDismissActionInteractor.access$log(keyguardDismissActionInteractor, "updatedDismissAction=" + ((DismissAction) obj2));
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = KeyguardDismissActionInteractor.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(KeyguardDismissActionInteractor.this, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new C02222(KeyguardDismissActionInteractor.this, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(KeyguardDismissActionInteractor.this, null), 3);
                this.label = 1;
                if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5] */
    public KeyguardDismissActionInteractor(KeyguardRepository keyguardRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardDismissInteractor keyguardDismissInteractor, CoroutineScope coroutineScope, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, KeyguardLogger keyguardLogger, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.repository = keyguardRepository;
        this.dismissInteractor = keyguardDismissInteractor;
        this.keyguardLogger = keyguardLogger;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        final ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) keyguardRepository).dismissAction;
        this.dismissAction = readonlyStateFlow;
        new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        String message = ((DismissAction) obj).getMessage();
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(message, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.willAnimateDismissActionOnLockscreen = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((DismissAction) obj).getWillAnimateOnLockscreen());
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
        SceneKey sceneKey = Scenes.Communal;
        final Flow flowIsFinishedIn = keyguardTransitionInteractor.isFinishedIn(KeyguardState.GONE);
        final Flow flow = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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
                        if (((Boolean) obj).booleanValue()) {
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
                Object objCollect = flowIsFinishedIn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.finishedTransitionToGone = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        ((Boolean) obj).getClass();
                        Unit unit = Unit.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        ComposeBouncerFlags.INSTANCE.getClass();
        this.isOnShadeWhileUnlocked = new SafeFlow(new KeyguardDismissActionInteractor$isOnShadeWhileUnlocked$3(null));
    }

    public static final void access$log(KeyguardDismissActionInteractor keyguardDismissActionInteractor, String str) {
        keyguardDismissActionInteractor.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        LogBuffer$$ExternalSyntheticLambda0 logBuffer$$ExternalSyntheticLambda0 = new LogBuffer$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = keyguardDismissActionInteractor.keyguardLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardDismissAction", logLevel, logBuffer$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        logBuffer.commit(logMessageObtain);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$runDismissAction(KeyguardDismissActionInteractor keyguardDismissActionInteractor, Continuation continuation) throws Throwable {
        KeyguardDismissActionInteractor$runDismissAction$1 keyguardDismissActionInteractor$runDismissAction$1;
        keyguardDismissActionInteractor.getClass();
        if (continuation instanceof KeyguardDismissActionInteractor$runDismissAction$1) {
            keyguardDismissActionInteractor$runDismissAction$1 = (KeyguardDismissActionInteractor$runDismissAction$1) continuation;
            int i = keyguardDismissActionInteractor$runDismissAction$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                keyguardDismissActionInteractor$runDismissAction$1.label = i - Integer.MIN_VALUE;
            } else {
                keyguardDismissActionInteractor$runDismissAction$1 = new KeyguardDismissActionInteractor$runDismissAction$1(keyguardDismissActionInteractor, continuation);
            }
        }
        Object obj = keyguardDismissActionInteractor$runDismissAction$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = keyguardDismissActionInteractor$runDismissAction$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            DismissAction dismissAction = (DismissAction) ((KeyguardRepositoryImpl) keyguardDismissActionInteractor.repository).dismissAction.$$delegate_0.getValue();
            KeyguardDone keyguardDone = KeyguardDone.IMMEDIATE;
            if (!Intrinsics.areEqual(dismissAction, DismissAction.None.INSTANCE)) {
                KeyguardDone keyguardDone2 = (KeyguardDone) dismissAction.getOnDismissAction().invoke();
                keyguardDismissActionInteractor$runDismissAction$1.L$0 = keyguardDismissActionInteractor;
                keyguardDismissActionInteractor$runDismissAction$1.label = 1;
                Object objEmit = ((KeyguardRepositoryImpl) keyguardDismissActionInteractor.dismissInteractor.keyguardRepository)._keyguardDone.emit(keyguardDone2, keyguardDismissActionInteractor$runDismissAction$1);
                if (objEmit != coroutineSingletons) {
                    objEmit = Unit.INSTANCE;
                }
                if (objEmit != coroutineSingletons) {
                    objEmit = Unit.INSTANCE;
                }
                if (objEmit == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            ((KeyguardBouncerRepositoryImpl) keyguardDismissActionInteractor.primaryBouncerInteractor.repository)._keyguardAuthenticatedBiometrics.setValue(null);
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        keyguardDismissActionInteractor = (KeyguardDismissActionInteractor) keyguardDismissActionInteractor$runDismissAction$1.L$0;
        ResultKt.throwOnFailure(obj);
        keyguardDismissActionInteractor.getClass();
        ((KeyguardRepositoryImpl) keyguardDismissActionInteractor.repository)._dismissAction.setValue(DismissAction.None.INSTANCE);
        ((KeyguardBouncerRepositoryImpl) keyguardDismissActionInteractor.primaryBouncerInteractor.repository)._keyguardAuthenticatedBiometrics.setValue(null);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
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
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
