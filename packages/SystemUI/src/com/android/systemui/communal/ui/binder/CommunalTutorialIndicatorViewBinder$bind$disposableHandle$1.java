package com.android.systemui.communal.ui.binder;

import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.communal.ui.viewmodel.CommunalTutorialIndicatorViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

final class CommunalTutorialIndicatorViewBinder$bind$disposableHandle$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ boolean $isPreviewMode;
    final /* synthetic */ TextView $view;
    final /* synthetic */ CommunalTutorialIndicatorViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.communal.ui.binder.CommunalTutorialIndicatorViewBinder$bind$disposableHandle$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $isPreviewMode;
        final /* synthetic */ TextView $view;
        final /* synthetic */ CommunalTutorialIndicatorViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.communal.ui.binder.CommunalTutorialIndicatorViewBinder$bind$disposableHandle$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00721 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $isPreviewMode;
            final /* synthetic */ TextView $view;
            final /* synthetic */ CommunalTutorialIndicatorViewModel $viewModel;
            int label;

            public C00721(CommunalTutorialIndicatorViewModel communalTutorialIndicatorViewModel, boolean z, TextView textView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = communalTutorialIndicatorViewModel;
                this.$isPreviewMode = z;
                this.$view = textView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00721(this.$viewModel, this.$isPreviewMode, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00721) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                ReadonlyStateFlow readonlyStateFlow;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CommunalTutorialIndicatorViewModel communalTutorialIndicatorViewModel = this.$viewModel;
                    if (this.$isPreviewMode) {
                        communalTutorialIndicatorViewModel.getClass();
                        readonlyStateFlow = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(Boolean.FALSE));
                    } else {
                        readonlyStateFlow = communalTutorialIndicatorViewModel.communalTutorialInteractor.isTutorialAvailable;
                    }
                    final TextView textView = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.communal.ui.binder.CommunalTutorialIndicatorViewBinder.bind.disposableHandle.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            textView.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
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

        /* renamed from: com.android.systemui.communal.ui.binder.CommunalTutorialIndicatorViewBinder$bind$disposableHandle$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ TextView $view;
            final /* synthetic */ CommunalTutorialIndicatorViewModel $viewModel;
            int label;

            public AnonymousClass2(CommunalTutorialIndicatorViewModel communalTutorialIndicatorViewModel, TextView textView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = communalTutorialIndicatorViewModel;
                this.$view = textView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$view, continuation);
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
                    Flow flow = this.$viewModel.alpha;
                    final TextView textView = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.communal.ui.binder.CommunalTutorialIndicatorViewBinder.bind.disposableHandle.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            textView.setAlpha(((Number) obj2).floatValue());
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

        public AnonymousClass1(CommunalTutorialIndicatorViewModel communalTutorialIndicatorViewModel, boolean z, TextView textView, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = communalTutorialIndicatorViewModel;
            this.$isPreviewMode = z;
            this.$view = textView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$isPreviewMode, this.$view, continuation);
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
            BuildersKt.launch$default(coroutineScope, null, null, new C00721(this.$viewModel, this.$isPreviewMode, this.$view, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$view, null), 3);
            return Unit.INSTANCE;
        }
    }

    public CommunalTutorialIndicatorViewBinder$bind$disposableHandle$1(CommunalTutorialIndicatorViewModel communalTutorialIndicatorViewModel, boolean z, TextView textView, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = communalTutorialIndicatorViewModel;
        this.$isPreviewMode = z;
        this.$view = textView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalTutorialIndicatorViewBinder$bind$disposableHandle$1 communalTutorialIndicatorViewBinder$bind$disposableHandle$1 = new CommunalTutorialIndicatorViewBinder$bind$disposableHandle$1(this.$viewModel, this.$isPreviewMode, this.$view, (Continuation) obj3);
        communalTutorialIndicatorViewBinder$bind$disposableHandle$1.L$0 = (LifecycleOwner) obj;
        return communalTutorialIndicatorViewBinder$bind$disposableHandle$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$isPreviewMode, this.$view, null);
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
