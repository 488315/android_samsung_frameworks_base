package com.android.systemui.statusbar.pipeline.shared.ui.view;

import androidx.compose.ui.platform.ComposeView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewVisibilityHelper;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Function4 $block;
    final /* synthetic */ MutableStateFlow $decorTint;
    final /* synthetic */ MutableStateFlow $iconTint;
    final /* synthetic */ Ref$BooleanRef $isCollecting;
    final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
    final /* synthetic */ MutableStateFlow $visibilityState;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ MutableStateFlow $decorTint;
        final /* synthetic */ MutableStateFlow $iconTint;
        final /* synthetic */ Ref$BooleanRef $isCollecting;
        final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
        final /* synthetic */ MutableStateFlow $visibilityState;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C03731 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $decorTint;
            final /* synthetic */ MutableStateFlow $iconTint;
            final /* synthetic */ Ref$BooleanRef $isCollecting;
            final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
            final /* synthetic */ MutableStateFlow $visibilityState;
            private /* synthetic */ Object L$0;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C03741 extends SuspendLambda implements Function2 {
                final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
                final /* synthetic */ MutableStateFlow $visibilityState;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03741(MutableStateFlow mutableStateFlow, SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, Continuation continuation) {
                    super(2, continuation);
                    this.$visibilityState = mutableStateFlow;
                    this.$view = singleBindableStatusBarComposeIconView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C03741(this.$visibilityState, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C03741) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        MutableStateFlow mutableStateFlow = this.$visibilityState;
                        final SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView.Companion.withDefaultBinding.1.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int intValue = ((Number) obj2).intValue();
                                ModernStatusBarViewVisibilityHelper.Companion companion = ModernStatusBarViewVisibilityHelper.Companion;
                                SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView2 = SingleBindableStatusBarComposeIconView.this;
                                ComposeView composeView = singleBindableStatusBarComposeIconView2.composeView;
                                if (composeView == null) {
                                    composeView = null;
                                }
                                StatusBarIconView statusBarIconView = singleBindableStatusBarComposeIconView2.dotView;
                                StatusBarIconView statusBarIconView2 = statusBarIconView != null ? statusBarIconView : null;
                                companion.getClass();
                                ModernStatusBarViewVisibilityHelper.Companion.setVisibilityState(composeView, statusBarIconView2, intValue);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (mutableStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ MutableStateFlow $iconTint;
                final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(MutableStateFlow mutableStateFlow, SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, Continuation continuation) {
                    super(2, continuation);
                    this.$iconTint = mutableStateFlow;
                    this.$view = singleBindableStatusBarComposeIconView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.$iconTint, this.$view, continuation);
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
                        MutableStateFlow mutableStateFlow = this.$iconTint;
                        final SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView.Companion.withDefaultBinding.1.1.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int intValue = ((Number) obj2).intValue();
                                StatusBarIconView statusBarIconView = SingleBindableStatusBarComposeIconView.this.dotView;
                                if (statusBarIconView == null) {
                                    statusBarIconView = null;
                                }
                                statusBarIconView.setDecorColor(intValue);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (mutableStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1$1$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ MutableStateFlow $decorTint;
                final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(MutableStateFlow mutableStateFlow, SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, Continuation continuation) {
                    super(2, continuation);
                    this.$decorTint = mutableStateFlow;
                    this.$view = singleBindableStatusBarComposeIconView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(this.$decorTint, this.$view, continuation);
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
                        MutableStateFlow mutableStateFlow = this.$decorTint;
                        final SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView.Companion.withDefaultBinding.1.1.1.3.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int intValue = ((Number) obj2).intValue();
                                StatusBarIconView statusBarIconView = SingleBindableStatusBarComposeIconView.this.dotView;
                                if (statusBarIconView == null) {
                                    statusBarIconView = null;
                                }
                                statusBarIconView.setDecorColor(intValue);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (mutableStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03731(Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, MutableStateFlow mutableStateFlow2, MutableStateFlow mutableStateFlow3, Continuation continuation) {
                super(2, continuation);
                this.$isCollecting = ref$BooleanRef;
                this.$visibilityState = mutableStateFlow;
                this.$view = singleBindableStatusBarComposeIconView;
                this.$iconTint = mutableStateFlow2;
                this.$decorTint = mutableStateFlow3;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03731 c03731 = new C03731(this.$isCollecting, this.$visibilityState, this.$view, this.$iconTint, this.$decorTint, continuation);
                c03731.L$0 = obj;
                return c03731;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03731) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03741(this.$visibilityState, this.$view, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$iconTint, this.$view, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$decorTint, this.$view, null), 7);
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
                } catch (Throwable th) {
                    this.$isCollecting.element = false;
                    throw th;
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(LifecycleOwner lifecycleOwner, Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, MutableStateFlow mutableStateFlow2, MutableStateFlow mutableStateFlow3, Continuation continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$isCollecting = ref$BooleanRef;
            this.$visibilityState = mutableStateFlow;
            this.$view = singleBindableStatusBarComposeIconView;
            this.$iconTint = mutableStateFlow2;
            this.$decorTint = mutableStateFlow3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$$this$repeatWhenAttached, this.$isCollecting, this.$visibilityState, this.$view, this.$iconTint, this.$decorTint, continuation);
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
                LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                Lifecycle.State state = Lifecycle.State.STARTED;
                C03731 c03731 = new C03731(this.$isCollecting, this.$visibilityState, this.$view, this.$iconTint, this.$decorTint, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c03731, this) == coroutineSingletons) {
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
    public SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1(Function4 function4, SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, MutableStateFlow mutableStateFlow, Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow2, MutableStateFlow mutableStateFlow3, Continuation continuation) {
        super(3, continuation);
        this.$block = function4;
        this.$view = singleBindableStatusBarComposeIconView;
        this.$iconTint = mutableStateFlow;
        this.$isCollecting = ref$BooleanRef;
        this.$visibilityState = mutableStateFlow2;
        this.$decorTint = mutableStateFlow3;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1 singleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1 = new SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1(this.$block, this.$view, this.$iconTint, this.$isCollecting, this.$visibilityState, this.$decorTint, (Continuation) obj3);
        singleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1.L$0 = (LifecycleOwner) obj;
        return singleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        LifecycleOwner lifecycleOwner;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner2 = (LifecycleOwner) this.L$0;
            Function4 function4 = this.$block;
            SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView = this.$view;
            MutableStateFlow mutableStateFlow = this.$iconTint;
            this.L$0 = lifecycleOwner2;
            this.label = 1;
            if (function4.invoke(lifecycleOwner2, singleBindableStatusBarComposeIconView, mutableStateFlow, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            lifecycleOwner = lifecycleOwner2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            LifecycleOwner lifecycleOwner3 = (LifecycleOwner) this.L$0;
            ResultKt.throwOnFailure(obj);
            lifecycleOwner = lifecycleOwner3;
        }
        CoroutineTracingKt.launchTraced$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new AnonymousClass1(lifecycleOwner, this.$isCollecting, this.$visibilityState, this.$view, this.$iconTint, this.$decorTint, null), 7);
        return Unit.INSTANCE;
    }
}
