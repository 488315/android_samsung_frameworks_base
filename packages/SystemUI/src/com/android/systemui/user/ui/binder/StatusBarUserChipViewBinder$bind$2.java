package com.android.systemui.user.ui.binder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

final class StatusBarUserChipViewBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ Function1 $userCountCallback;
    final /* synthetic */ StatusBarUserChipViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $userCountCallback;
        final /* synthetic */ StatusBarUserChipViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C02661 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function1 $userCountCallback;
            final /* synthetic */ StatusBarUserChipViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02661(StatusBarUserChipViewModel statusBarUserChipViewModel, Function1 function1, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = statusBarUserChipViewModel;
                this.$userCountCallback = function1;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02661(this.$viewModel, this.$userCountCallback, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02661) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.userCount;
                    final Function1 function1 = this.$userCountCallback;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder.bind.2.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int intValue = ((Number) obj2).intValue();
                            Function1 function12 = Function1.this;
                            if (function12 != null) {
                                function12.invoke(new Integer(intValue));
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (channelFlowTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(StatusBarUserChipViewModel statusBarUserChipViewModel, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = statusBarUserChipViewModel;
            this.$userCountCallback = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$userCountCallback, continuation);
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
            BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new C02661(this.$viewModel, this.$userCountCallback, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarUserChipViewBinder$bind$2(StatusBarUserChipViewModel statusBarUserChipViewModel, Function1 function1, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = statusBarUserChipViewModel;
        this.$userCountCallback = function1;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StatusBarUserChipViewBinder$bind$2 statusBarUserChipViewBinder$bind$2 = new StatusBarUserChipViewBinder$bind$2(this.$viewModel, this.$userCountCallback, (Continuation) obj3);
        statusBarUserChipViewBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return statusBarUserChipViewBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$userCountCallback, null);
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
