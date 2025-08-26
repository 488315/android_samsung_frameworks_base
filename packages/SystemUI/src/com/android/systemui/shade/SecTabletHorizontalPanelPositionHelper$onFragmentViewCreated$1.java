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

/* loaded from: classes3.dex */
final class SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

    /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

        /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C04631 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

            /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C04641 extends SuspendLambda implements Function2 {
                /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C04641(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secTabletHorizontalPanelPositionHelper;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C04641 c04641 = new C04641(this.this$0, continuation);
                    c04641.L$0 = obj;
                    return c04641;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04641) create((SecQsUiDisplayModeInteractor.FoldState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    if (((SecQsUiDisplayModeInteractor) this.this$0.qsUiDisplayModeInteractor$delegate.getValue()).isTablet()) {
                        this.this$0.updateResources();
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04631(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secTabletHorizontalPanelPositionHelper;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04631(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04631) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    C04641 c04641 = new C04641(this.this$0, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(foldState, c04641, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

            /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$2$1, reason: invalid class name and collision with other inner class name */
            final class C04651 extends SuspendLambda implements Function2 {
                /* synthetic */ int I$0;
                int label;
                final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C04651(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secTabletHorizontalPanelPositionHelper;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C04651 c04651 = new C04651(this.this$0, continuation);
                    c04651.I$0 = ((Number) obj).intValue();
                    return c04651;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04651) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    C04651 c04651 = new C04651(secTabletHorizontalPanelPositionHelper, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(readonlyStateFlow, c04651, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

            /* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1$1$3$1, reason: invalid class name and collision with other inner class name */
            final class C04661 extends SuspendLambda implements Function2 {
                /* synthetic */ boolean Z$0;
                int label;
                final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C04661(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secTabletHorizontalPanelPositionHelper;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C04661 c04661 = new C04661(this.this$0, continuation);
                    c04661.Z$0 = ((Boolean) obj).booleanValue();
                    return c04661;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Boolean bool = (Boolean) obj;
                    bool.booleanValue();
                    return ((C04661) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    ReadonlyStateFlow readonlyStateFlow = secTabletHorizontalPanelPositionHelper.splitStateInteractor.repository.transitioning;
                    C04661 c04661 = new C04661(secTabletHorizontalPanelPositionHelper, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(readonlyStateFlow, c04661, this) == coroutineSingletons) {
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
            BuildersKt.launch$default(coroutineScope, null, null, new C04631(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 3);
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
