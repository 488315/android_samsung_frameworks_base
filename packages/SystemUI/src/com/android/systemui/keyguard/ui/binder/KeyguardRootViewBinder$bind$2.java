package com.android.systemui.keyguard.ui.binder;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.StateToValue;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardRootViewBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ Map<Integer, View> $childViews;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ KeyguardRootViewModel $viewModel;
    final /* synthetic */ ViewStateAccessor $viewState;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Map<Integer, View> $childViews;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ KeyguardRootViewModel $viewModel;
        final /* synthetic */ ViewStateAccessor $viewState;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C01851 extends SuspendLambda implements Function2 {
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01851(KeyguardRootViewModel keyguardRootViewModel, ViewGroup viewGroup, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardRootViewModel;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01851(this.$viewModel, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01851) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final Rect rect = new Rect();
                    Flow flow = this.$viewModel.topClippingBounds;
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Integer num = (Integer) obj2;
                            if (num == null) {
                                viewGroup.setClipBounds(null);
                            } else {
                                Rect rect2 = rect;
                                ViewGroup viewGroup2 = viewGroup;
                                rect2.top = num.intValue();
                                rect2.left = viewGroup2.getLeft();
                                rect2.right = viewGroup2.getRight();
                                rect2.bottom = viewGroup2.getBottom();
                                viewGroup.setClipBounds(rect);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ Map<Integer, View> $childViews;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            final /* synthetic */ ViewStateAccessor $viewState;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(KeyguardRootViewModel keyguardRootViewModel, ViewStateAccessor viewStateAccessor, ViewGroup viewGroup, Map<Integer, View> map, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardRootViewModel;
                this.$viewState = viewStateAccessor;
                this.$view = viewGroup;
                this.$childViews = map;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$viewState, this.$view, this.$childViews, continuation);
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
                    Flow alpha = this.$viewModel.alpha(this.$viewState);
                    final ViewGroup viewGroup = this.$view;
                    final Map<Integer, View> map = this.$childViews;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            float floatValue = ((Number) obj2).floatValue();
                            viewGroup.setAlpha(floatValue);
                            View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.burnInLayerId, map);
                            if (view != null) {
                                view.setAlpha(floatValue);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (alpha.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(KeyguardRootViewModel keyguardRootViewModel, ViewGroup viewGroup, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardRootViewModel;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$view, continuation);
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
                    Flow flow = this.$viewModel.scaleFromZoomOut;
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            float floatValue = ((Number) obj2).floatValue();
                            viewGroup.setScaleX(floatValue);
                            viewGroup.setScaleY(floatValue);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ Map<Integer, View> $childViews;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(KeyguardRootViewModel keyguardRootViewModel, Map<Integer, View> map, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardRootViewModel;
                this.$childViews = map;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$childViews, continuation);
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
                    Flow flow = this.$viewModel.translationY;
                    final Map<Integer, View> map = this.$childViews;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            float floatValue = ((Number) obj2).floatValue();
                            View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.burnInLayerId, map);
                            if (view != null) {
                                view.setTranslationY(floatValue);
                            }
                            View view2 = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.largeClockId, map);
                            if (view2 != null) {
                                view2.setTranslationY(floatValue);
                            }
                            View view3 = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.aodPromotedNotificationId, map);
                            if (view3 != null) {
                                view3.setTranslationY(floatValue);
                            }
                            View view4 = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.aodNotificationIconContainerId, map);
                            if (view4 != null) {
                                view4.setTranslationY(floatValue);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$2$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ Map<Integer, View> $childViews;
            final /* synthetic */ KeyguardRootViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(KeyguardRootViewModel keyguardRootViewModel, Map<Integer, View> map, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardRootViewModel;
                this.$childViews = map;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$viewModel, this.$childViews, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.translationX;
                    final Map<Integer, View> map = this.$childViews;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.2.1.5.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardState keyguardState;
                            StateToValue stateToValue = (StateToValue) obj2;
                            Float f = stateToValue.value;
                            if (f == null) {
                                return Unit.INSTANCE;
                            }
                            float floatValue = f.floatValue();
                            KeyguardState keyguardState2 = KeyguardState.AOD;
                            KeyguardState keyguardState3 = stateToValue.from;
                            if (keyguardState3 == keyguardState2 || (keyguardState = stateToValue.to) == keyguardState2) {
                                View view = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.burnInLayerId, map);
                                if (view != null) {
                                    view.setTranslationX(floatValue);
                                }
                                View view2 = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.aodPromotedNotificationId, map);
                                if (view2 != null) {
                                    view2.setTranslationX(floatValue);
                                }
                                View view3 = (View) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(KeyguardRootViewBinder.aodNotificationIconContainerId, map);
                                if (view3 != null) {
                                    view3.setTranslationX(floatValue);
                                }
                            } else {
                                KeyguardState keyguardState4 = KeyguardState.GLANCEABLE_HUB;
                                if (keyguardState3 == keyguardState4 || keyguardState == keyguardState4) {
                                    for (Map.Entry entry : map.entrySet()) {
                                        int intValue = ((Number) entry.getKey()).intValue();
                                        View view4 = (View) entry.getValue();
                                        if (intValue != KeyguardRootViewBinder.indicationArea && intValue != KeyguardRootViewBinder.startButton && intValue != KeyguardRootViewBinder.endButton && intValue != KeyguardRootViewBinder.deviceEntryIcon) {
                                            view4.setTranslationX(floatValue);
                                        }
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(KeyguardRootViewModel keyguardRootViewModel, ViewGroup viewGroup, ViewStateAccessor viewStateAccessor, Map<Integer, View> map, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = keyguardRootViewModel;
            this.$view = viewGroup;
            this.$viewState = viewStateAccessor;
            this.$childViews = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, this.$viewState, this.$childViews, continuation);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01851(this.$viewModel, this.$view, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$viewState, this.$view, this.$childViews, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$view, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$childViews, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$childViews, null), 6);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRootViewBinder$bind$2(KeyguardRootViewModel keyguardRootViewModel, ViewGroup viewGroup, ViewStateAccessor viewStateAccessor, Map<Integer, View> map, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = keyguardRootViewModel;
        this.$view = viewGroup;
        this.$viewState = viewStateAccessor;
        this.$childViews = map;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardRootViewBinder$bind$2 keyguardRootViewBinder$bind$2 = new KeyguardRootViewBinder$bind$2(this.$viewModel, this.$view, this.$viewState, this.$childViews, (Continuation) obj3);
        keyguardRootViewBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return keyguardRootViewBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, this.$viewState, this.$childViews, null);
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
