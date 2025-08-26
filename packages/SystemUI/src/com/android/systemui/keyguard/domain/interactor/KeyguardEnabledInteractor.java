package com.android.systemui.keyguard.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.kotlin.FlowKt;
import dagger.Lazy;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class KeyguardEnabledInteractor {
    public final CoroutineDispatcher backgroundDispatcher;
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final ReadonlyStateFlow isKeyguardEnabled;
    public final LockPatternUtils lockPatternUtils;
    public final KeyguardRepository repository;
    public final SelectedUserInteractor selectedUserInteractor;
    public final KeyguardEnabledInteractor$special$$inlined$map$1 showKeyguardWhenReenabled;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ InternalKeyguardTransitionInteractor $internalTransitionInteractor;
        final /* synthetic */ Lazy $keyguardDismissTransitionInteractor;
        int label;

        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$4, reason: invalid class name */
        final /* synthetic */ class AnonymousClass4 extends AdaptedFunctionReference implements Function3 {
            public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

            public AnonymousClass4() {
                super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                Boolean bool2 = (Boolean) obj2;
                bool2.booleanValue();
                return new Pair(bool, bool2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, Lazy lazy, Continuation continuation) {
            super(2, continuation);
            this.$internalTransitionInteractor = internalKeyguardTransitionInteractor;
            this.$keyguardDismissTransitionInteractor = lazy;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardEnabledInteractor.this.new AnonymousClass1(this.$internalTransitionInteractor, this.$keyguardDismissTransitionInteractor, continuation);
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
                final ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) KeyguardEnabledInteractor.this.repository).isKeyguardEnabled;
                Flow flowSample = FlowKt.sample(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1

                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                if (!((Boolean) obj).booleanValue()) {
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
                        Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }, ((BiometricSettingsRepositoryImpl) KeyguardEnabledInteractor.this.biometricSettingsRepository).isCurrentUserInLockdown, AnonymousClass4.INSTANCE);
                final InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor = this.$internalTransitionInteractor;
                final Lazy lazy = this.$keyguardDismissTransitionInteractor;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor.1.5
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) ((Pair) obj2).component2()).booleanValue();
                        if (internalKeyguardTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to != KeyguardState.GONE && !zBooleanValue) {
                            KeyguardDismissTransitionInteractor.startDismissKeyguardTransition$default((KeyguardDismissTransitionInteractor) lazy.get(), "keyguard disabled");
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowSample.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$isKeyguardEnabledAndNotSuppressed$1, reason: invalid class name and case insensitive filesystem */
    final class C09041 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C09041(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyguardEnabledInteractor.this.isKeyguardEnabledAndNotSuppressed(this);
        }
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1] */
    public KeyguardEnabledInteractor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, KeyguardRepository keyguardRepository, BiometricSettingsRepository biometricSettingsRepository, SelectedUserInteractor selectedUserInteractor, LockPatternUtils lockPatternUtils, Lazy lazy, final InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.repository = keyguardRepository;
        this.biometricSettingsRepository = biometricSettingsRepository;
        this.selectedUserInteractor = selectedUserInteractor;
        this.lockPatternUtils = lockPatternUtils;
        ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) keyguardRepository).isKeyguardEnabled;
        this.isKeyguardEnabled = readonlyStateFlow;
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(readonlyStateFlow, new KeyguardEnabledInteractor$showKeyguardWhenReenabled$1(null));
        final Flow flowSample = FlowKt.sample(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        if (!((Boolean) obj).booleanValue()) {
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
                Object objCollect = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, ((BiometricSettingsRepositoryImpl) biometricSettingsRepository).isCurrentUserInLockdown, KeyguardEnabledInteractor$showKeyguardWhenReenabled$5.INSTANCE);
        this.showKeyguardWhenReenabled = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ InternalKeyguardTransitionInteractor $internalTransitionInteractor$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$internalTransitionInteractor$inlined = internalKeyguardTransitionInteractor;
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
                        Boolean boolValueOf = Boolean.valueOf((this.$internalTransitionInteractor$inlined.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to == KeyguardState.GONE || ((Boolean) ((Pair) obj).component2()).booleanValue()) ? false : true);
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
                Object objCollect = flowSample.collect(new AnonymousClass2(flowCollector, internalKeyguardTransitionInteractor), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(internalKeyguardTransitionInteractor, lazy, null), 7);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isKeyguardEnabledAndNotSuppressed(ContinuationImpl continuationImpl) throws Throwable {
        C09041 c09041;
        if (continuationImpl instanceof C09041) {
            c09041 = (C09041) continuationImpl;
            int i = c09041.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09041.label = i - Integer.MIN_VALUE;
            } else {
                c09041 = new C09041(continuationImpl);
            }
        }
        Object objWithContext = c09041.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09041.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            if (((Boolean) this.isKeyguardEnabled.$$delegate_0.getValue()).booleanValue()) {
                c09041.label = 1;
                objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new KeyguardEnabledInteractor$isKeyguardSuppressed$2(this, this.selectedUserInteractor.getSelectedUserId(), null), c09041);
                if (objWithContext == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Boolean.valueOf(z);
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(objWithContext);
        boolean z = ((Boolean) objWithContext).booleanValue() ? false : true;
        return Boolean.valueOf(z);
    }
}
