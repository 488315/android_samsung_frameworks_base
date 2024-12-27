package com.android.systemui.statusbar.pipeline.shared.ui.view;

import android.content.res.ColorStateList;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
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
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SingleBindableStatusBarIconView$Companion$withDefaultBinding$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Function3 $block;
    final /* synthetic */ MutableStateFlow $decorTint;
    final /* synthetic */ MutableStateFlow $iconTint;
    final /* synthetic */ Ref$BooleanRef $isCollecting;
    final /* synthetic */ SingleBindableStatusBarIconView $view;
    final /* synthetic */ MutableStateFlow $visibilityState;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ MutableStateFlow $decorTint;
        final /* synthetic */ MutableStateFlow $iconTint;
        final /* synthetic */ Ref$BooleanRef $isCollecting;
        final /* synthetic */ SingleBindableStatusBarIconView $view;
        final /* synthetic */ MutableStateFlow $visibilityState;
        int label;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02461 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $decorTint;
            final /* synthetic */ MutableStateFlow $iconTint;
            final /* synthetic */ Ref$BooleanRef $isCollecting;
            final /* synthetic */ SingleBindableStatusBarIconView $view;
            final /* synthetic */ MutableStateFlow $visibilityState;
            private /* synthetic */ Object L$0;
            int label;

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C02471 extends SuspendLambda implements Function2 {
                final /* synthetic */ SingleBindableStatusBarIconView $view;
                final /* synthetic */ MutableStateFlow $visibilityState;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02471(MutableStateFlow mutableStateFlow, SingleBindableStatusBarIconView singleBindableStatusBarIconView, Continuation continuation) {
                    super(2, continuation);
                    this.$visibilityState = mutableStateFlow;
                    this.$view = singleBindableStatusBarIconView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02471(this.$visibilityState, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02471) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        MutableStateFlow mutableStateFlow = this.$visibilityState;
                        final SingleBindableStatusBarIconView singleBindableStatusBarIconView = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView.Companion.withDefaultBinding.1.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int intValue = ((Number) obj2).intValue();
                                ModernStatusBarViewVisibilityHelper.Companion companion = ModernStatusBarViewVisibilityHelper.Companion;
                                SingleBindableStatusBarIconView singleBindableStatusBarIconView2 = SingleBindableStatusBarIconView.this;
                                ImageView imageView = singleBindableStatusBarIconView2.iconView;
                                if (imageView == null) {
                                    imageView = null;
                                }
                                StatusBarIconView statusBarIconView = singleBindableStatusBarIconView2.dotView;
                                StatusBarIconView statusBarIconView2 = statusBarIconView != null ? statusBarIconView : null;
                                companion.getClass();
                                ModernStatusBarViewVisibilityHelper.Companion.setVisibilityState(imageView, statusBarIconView2, intValue);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (((StateFlowImpl) mutableStateFlow).collect(flowCollector, this) == coroutineSingletons) {
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

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ MutableStateFlow $iconTint;
                final /* synthetic */ SingleBindableStatusBarIconView $view;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(MutableStateFlow mutableStateFlow, SingleBindableStatusBarIconView singleBindableStatusBarIconView, Continuation continuation) {
                    super(2, continuation);
                    this.$iconTint = mutableStateFlow;
                    this.$view = singleBindableStatusBarIconView;
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
                        final SingleBindableStatusBarIconView singleBindableStatusBarIconView = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView.Companion.withDefaultBinding.1.1.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int intValue = ((Number) obj2).intValue();
                                ColorStateList valueOf = ColorStateList.valueOf(intValue);
                                SingleBindableStatusBarIconView singleBindableStatusBarIconView2 = SingleBindableStatusBarIconView.this;
                                ImageView imageView = singleBindableStatusBarIconView2.iconView;
                                if (imageView == null) {
                                    imageView = null;
                                }
                                imageView.setImageTintList(valueOf);
                                StatusBarIconView statusBarIconView = singleBindableStatusBarIconView2.dotView;
                                (statusBarIconView != null ? statusBarIconView : null).setDecorColor(intValue);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (((StateFlowImpl) mutableStateFlow).collect(flowCollector, this) == coroutineSingletons) {
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

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ MutableStateFlow $decorTint;
                final /* synthetic */ SingleBindableStatusBarIconView $view;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(MutableStateFlow mutableStateFlow, SingleBindableStatusBarIconView singleBindableStatusBarIconView, Continuation continuation) {
                    super(2, continuation);
                    this.$decorTint = mutableStateFlow;
                    this.$view = singleBindableStatusBarIconView;
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
                        final SingleBindableStatusBarIconView singleBindableStatusBarIconView = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView.Companion.withDefaultBinding.1.1.1.3.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int intValue = ((Number) obj2).intValue();
                                StatusBarIconView statusBarIconView = SingleBindableStatusBarIconView.this.dotView;
                                if (statusBarIconView == null) {
                                    statusBarIconView = null;
                                }
                                statusBarIconView.setDecorColor(intValue);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (((StateFlowImpl) mutableStateFlow).collect(flowCollector, this) == coroutineSingletons) {
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
            public C02461(Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, SingleBindableStatusBarIconView singleBindableStatusBarIconView, MutableStateFlow mutableStateFlow2, MutableStateFlow mutableStateFlow3, Continuation continuation) {
                super(2, continuation);
                this.$isCollecting = ref$BooleanRef;
                this.$visibilityState = mutableStateFlow;
                this.$view = singleBindableStatusBarIconView;
                this.$iconTint = mutableStateFlow2;
                this.$decorTint = mutableStateFlow3;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C02461 c02461 = new C02461(this.$isCollecting, this.$visibilityState, this.$view, this.$iconTint, this.$decorTint, continuation);
                c02461.L$0 = obj;
                return c02461;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02461) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        BuildersKt.launch$default(coroutineScope, null, null, new C02471(this.$visibilityState, this.$view, null), 3);
                        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$iconTint, this.$view, null), 3);
                        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$decorTint, this.$view, null), 3);
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
        public AnonymousClass1(LifecycleOwner lifecycleOwner, Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, SingleBindableStatusBarIconView singleBindableStatusBarIconView, MutableStateFlow mutableStateFlow2, MutableStateFlow mutableStateFlow3, Continuation continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$isCollecting = ref$BooleanRef;
            this.$visibilityState = mutableStateFlow;
            this.$view = singleBindableStatusBarIconView;
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
                C02461 c02461 = new C02461(this.$isCollecting, this.$visibilityState, this.$view, this.$iconTint, this.$decorTint, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c02461, this) == coroutineSingletons) {
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
    public SingleBindableStatusBarIconView$Companion$withDefaultBinding$1(Function3 function3, SingleBindableStatusBarIconView singleBindableStatusBarIconView, Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, MutableStateFlow mutableStateFlow2, MutableStateFlow mutableStateFlow3, Continuation continuation) {
        super(3, continuation);
        this.$block = function3;
        this.$view = singleBindableStatusBarIconView;
        this.$isCollecting = ref$BooleanRef;
        this.$visibilityState = mutableStateFlow;
        this.$iconTint = mutableStateFlow2;
        this.$decorTint = mutableStateFlow3;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SingleBindableStatusBarIconView$Companion$withDefaultBinding$1 singleBindableStatusBarIconView$Companion$withDefaultBinding$1 = new SingleBindableStatusBarIconView$Companion$withDefaultBinding$1(this.$block, this.$view, this.$isCollecting, this.$visibilityState, this.$iconTint, this.$decorTint, (Continuation) obj3);
        singleBindableStatusBarIconView$Companion$withDefaultBinding$1.L$0 = (LifecycleOwner) obj;
        return singleBindableStatusBarIconView$Companion$withDefaultBinding$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        LifecycleOwner lifecycleOwner;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner2 = (LifecycleOwner) this.L$0;
            Function3 function3 = this.$block;
            SingleBindableStatusBarIconView singleBindableStatusBarIconView = this.$view;
            this.L$0 = lifecycleOwner2;
            this.label = 1;
            if (function3.invoke(lifecycleOwner2, singleBindableStatusBarIconView, this) == coroutineSingletons) {
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
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new AnonymousClass1(lifecycleOwner, this.$isCollecting, this.$visibilityState, this.$view, this.$iconTint, this.$decorTint, null), 3);
        return Unit.INSTANCE;
    }
}
