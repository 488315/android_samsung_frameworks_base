package com.android.systemui.shade;

import android.util.Log;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02971 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C02981 extends SuspendLambda implements Function2 {
                /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02981(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secTabletHorizontalPanelPositionHelper;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C02981 c02981 = new C02981(this.this$0, continuation);
                    c02981.L$0 = obj;
                    return c02981;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02981) create((SecQsUiDisplayModeInteractor.FoldState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    SecQsUiDisplayModeInteractor.FoldState foldState = (SecQsUiDisplayModeInteractor.FoldState) this.L$0;
                    SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper = this.this$0;
                    SecQsUiDisplayModeInteractor.FoldState foldState2 = secTabletHorizontalPanelPositionHelper.foldState;
                    if (foldState2 == foldState) {
                        return Unit.INSTANCE;
                    }
                    Log.d("SecTabletHorizontalPanelPositionHelper", "foldState: " + foldState2 + " -> " + foldState);
                    secTabletHorizontalPanelPositionHelper.foldState = foldState;
                    this.this$0.resetHorizontalPanelPosition(true);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02971(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secTabletHorizontalPanelPositionHelper;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02971(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02971) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper = this.this$0;
                    int i2 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                    StateFlow foldState = ((SecQsUiDisplayModeInteractor) secTabletHorizontalPanelPositionHelper.qsUiDisplayModeInteractor$delegate.getValue()).getFoldState();
                    C02981 c02981 = new C02981(this.this$0, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(foldState, c02981, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$2$1, reason: invalid class name and collision with other inner class name */
            final class C02991 extends SuspendLambda implements Function2 {
                /* synthetic */ int I$0;
                int label;
                final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02991(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secTabletHorizontalPanelPositionHelper;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C02991 c02991 = new C02991(this.this$0, continuation);
                    c02991.I$0 = ((Number) obj).intValue();
                    return c02991;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02991) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    int i = this.I$0;
                    SuggestionsAdapter$$ExternalSyntheticOutline0.m(this.this$0.isQsSTATE, i, "isQsStateValue : ", " -> ", "SecTabletHorizontalPanelPositionHelper");
                    SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper = this.this$0;
                    int i2 = secTabletHorizontalPanelPositionHelper.isQsSTATE;
                    if (i2 != i) {
                        if (i2 == -1 && secTabletHorizontalPanelPositionHelper.barStateIntSupplier.getAsInt() == 0) {
                            secTabletHorizontalPanelPositionHelper.isQsSTATE = i;
                        } else {
                            secTabletHorizontalPanelPositionHelper.isQsSTATE = i;
                            if (i != -1 && ((WakefulnessLifecycle) secTabletHorizontalPanelPositionHelper.wakefulnessLifecycle$delegate.getValue()).mWakefulness != 3 && !secTabletHorizontalPanelPositionHelper.transitionSwitchOn) {
                                secTabletHorizontalPanelPositionHelper.transitionSwitchOn = true;
                                secTabletHorizontalPanelPositionHelper.setHorizontalPanelTranslation(secTabletHorizontalPanelPositionHelper.transitioning ? secTabletHorizontalPanelPositionHelper.prevTransitionX : secTabletHorizontalPanelPositionHelper.posResult, false);
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secTabletHorizontalPanelPositionHelper;
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
                    SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper = this.this$0;
                    ReadonlyStateFlow readonlyStateFlow = secTabletHorizontalPanelPositionHelper.splitStateInteractor.repository.isQsState;
                    C02991 c02991 = new C02991(secTabletHorizontalPanelPositionHelper, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(readonlyStateFlow, c02991, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$3$1, reason: invalid class name and collision with other inner class name */
            final class C03001 extends SuspendLambda implements Function2 {
                /* synthetic */ boolean Z$0;
                int label;
                final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03001(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secTabletHorizontalPanelPositionHelper;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C03001 c03001 = new C03001(this.this$0, continuation);
                    c03001.Z$0 = ((Boolean) obj).booleanValue();
                    return c03001;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Boolean bool = (Boolean) obj;
                    bool.booleanValue();
                    return ((C03001) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    boolean z = this.Z$0;
                    KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("reversedValue : ", " -> ", "SecTabletHorizontalPanelPositionHelper", this.this$0.reversed, z);
                    this.this$0.reversed = z;
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secTabletHorizontalPanelPositionHelper;
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
                    SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper = this.this$0;
                    ReadonlyStateFlow readonlyStateFlow = secTabletHorizontalPanelPositionHelper.splitStateInteractor.repository.reverseState;
                    C03001 c03001 = new C03001(secTabletHorizontalPanelPositionHelper, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(readonlyStateFlow, c03001, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$4$1, reason: invalid class name and collision with other inner class name */
            final class C03011 extends SuspendLambda implements Function2 {
                /* synthetic */ boolean Z$0;
                int label;
                final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03011(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secTabletHorizontalPanelPositionHelper;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C03011 c03011 = new C03011(this.this$0, continuation);
                    c03011.Z$0 = ((Boolean) obj).booleanValue();
                    return c03011;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Boolean bool = (Boolean) obj;
                    bool.booleanValue();
                    return ((C03011) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    boolean z = this.Z$0;
                    KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("transitioningValue : ", " -> ", "SecTabletHorizontalPanelPositionHelper", this.this$0.transitioning, z);
                    this.this$0.transitioning = z;
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secTabletHorizontalPanelPositionHelper;
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
                    SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper = this.this$0;
                    ReadonlyStateFlow readonlyStateFlow = secTabletHorizontalPanelPositionHelper.splitStateInteractor.repository.transitioning;
                    C03011 c03011 = new C03011(secTabletHorizontalPanelPositionHelper, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(readonlyStateFlow, c03011, this) == coroutineSingletons) {
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
        public AnonymousClass1(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
            super(2, continuation);
            this.this$0 = secTabletHorizontalPanelPositionHelper;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new C02971(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.this$0, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
        super(3, continuation);
        this.this$0 = secTabletHorizontalPanelPositionHelper;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1 secTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1 = new SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1(this.this$0, (Continuation) obj3);
        secTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1.L$0 = (LifecycleOwner) obj;
        return secTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
