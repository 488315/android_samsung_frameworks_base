package com.android.systemui.bouncer.ui.viewmodel;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$1;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$2;
import com.android.systemui.bouncer.shared.model.BouncerMessageStrings;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.kotlin.Quad;
import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes.dex */
final class BouncerMessageViewModel$listenForBouncerEvents$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForBouncerEvents$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerMessageViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerMessageViewModel;
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
                final BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                BouncerInteractor$special$$inlined$map$2 bouncerInteractor$special$$inlined$map$2 = bouncerMessageViewModel.bouncerInteractor.onLockoutStarted;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel.listenForBouncerEvents.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Unit unitAccess$startLockoutCountdown = BouncerMessageViewModel.access$startLockoutCountdown(bouncerMessageViewModel);
                        return unitAccess$startLockoutCountdown == CoroutineSingletons.COROUTINE_SUSPENDED ? unitAccess$startLockoutCountdown : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (bouncerInteractor$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForBouncerEvents$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerMessageViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerMessageViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
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
                final BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                BouncerInteractor$special$$inlined$filter$1 bouncerInteractor$special$$inlined$filter$1 = bouncerMessageViewModel.bouncerInteractor.onPrimaryBouncerLockoutStarted;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel.listenForBouncerEvents.2.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Boolean) obj2).getClass();
                        Unit unitAccess$startLockoutCountdown = BouncerMessageViewModel.access$startLockoutCountdown(bouncerMessageViewModel);
                        return unitAccess$startLockoutCountdown == CoroutineSingletons.COROUTINE_SUSPENDED ? unitAccess$startLockoutCountdown : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (bouncerInteractor$special$$inlined$filter$1.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForBouncerEvents$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerMessageViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerMessageViewModel;
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
                BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                this.label = 1;
                if (BouncerMessageViewModel.access$startLockoutCountdown(bouncerMessageViewModel) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForBouncerEvents$2$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerMessageViewModel this$0;

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForBouncerEvents$2$4$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ BouncerMessageViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerMessageViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((Quad) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:22:0x007e, code lost:
            
                if (r12.emit(r1, r11) == r0) goto L29;
             */
            /* JADX WARN: Code restructure failed: missing block: B:28:0x009a, code lost:
            
                if (r12.emit(r1, r11) != r0) goto L30;
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) {
                MessageViewModel messageViewModel;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Quad quad = (Quad) this.L$0;
                    messageViewModel = (MessageViewModel) quad.component2();
                    AuthenticationMethodModel authenticationMethodModel = (AuthenticationMethodModel) quad.component3();
                    boolean zBooleanValue = ((Boolean) quad.component4()).booleanValue();
                    BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                    StateFlowImpl stateFlowImpl = bouncerMessageViewModel.message;
                    BouncerMessageStrings.INSTANCE.getClass();
                    MessageViewModel message = bouncerMessageViewModel.toMessage(BouncerMessageStrings.incorrectSecurityInput(authenticationMethodModel, zBooleanValue));
                    this.L$0 = messageViewModel;
                    this.label = 1;
                    stateFlowImpl.updateState(null, message);
                    if (Unit.INSTANCE != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                if (i != 1) {
                    if (i != 2) {
                        if (i == 3) {
                            ResultKt.throwOnFailure(obj);
                            SharedFlowImpl sharedFlowImpl = this.this$0.resetToDefault;
                            Boolean bool = Boolean.FALSE;
                            this.label = 4;
                        } else if (i != 4) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                messageViewModel = (MessageViewModel) this.L$0;
                ResultKt.throwOnFailure(obj);
                if ((messageViewModel != null ? messageViewModel.text : null) != null) {
                    SharedFlowImpl sharedFlowImpl2 = this.this$0.resetToDefault;
                    Boolean bool2 = Boolean.TRUE;
                    this.L$0 = null;
                    this.label = 2;
                } else {
                    this.L$0 = null;
                    this.label = 3;
                    if (DelayKt.delay(DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY, this) != coroutineSingletons) {
                        SharedFlowImpl sharedFlowImpl3 = this.this$0.resetToDefault;
                        Boolean bool3 = Boolean.FALSE;
                        this.label = 4;
                    }
                }
                return coroutineSingletons;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerMessageViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.this$0, continuation);
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
                Utils.Companion companion = Utils.Companion;
                BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                Flow flowSample = companion.sample(bouncerMessageViewModel.bouncerInteractor.onIncorrectBouncerInput, bouncerMessageViewModel.lockoutMessage, bouncerMessageViewModel.authenticationInteractor.authenticationMethod, bouncerMessageViewModel.deviceEntryBiometricsAllowedInteractor.isFingerprintCurrentlyAllowedOnBouncer);
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
                this.label = 1;
                if (FlowKt.collectLatest(flowSample, anonymousClass1, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$listenForBouncerEvents$2(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerMessageViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BouncerMessageViewModel$listenForBouncerEvents$2 bouncerMessageViewModel$listenForBouncerEvents$2 = new BouncerMessageViewModel$listenForBouncerEvents$2(this.this$0, continuation);
        bouncerMessageViewModel$listenForBouncerEvents$2.L$0 = obj;
        return bouncerMessageViewModel$listenForBouncerEvents$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerMessageViewModel$listenForBouncerEvents$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 7);
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.this$0, null), 7);
    }
}
