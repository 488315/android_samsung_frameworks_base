package com.android.systemui.user.ui.binder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.ui.binder.TextViewBinder;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
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
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

final class StatusBarUserChipViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ StatusBarUserSwitcherContainer $view;
    final /* synthetic */ StatusBarUserChipViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ StatusBarUserSwitcherContainer $view;
        final /* synthetic */ StatusBarUserChipViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02621 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarUserSwitcherContainer $view;
            final /* synthetic */ StatusBarUserChipViewModel $viewModel;
            int label;

            public C02621(StatusBarUserChipViewModel statusBarUserChipViewModel, StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = statusBarUserChipViewModel;
                this.$view = statusBarUserSwitcherContainer;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02621(this.$viewModel, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02621) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.isChipVisible;
                    final StatusBarUserSwitcherContainer statusBarUserSwitcherContainer = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            StatusBarUserSwitcherContainer.this.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
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

        /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarUserSwitcherContainer $view;
            final /* synthetic */ StatusBarUserChipViewModel $viewModel;
            int label;

            public AnonymousClass2(StatusBarUserChipViewModel statusBarUserChipViewModel, StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = statusBarUserChipViewModel;
                this.$view = statusBarUserSwitcherContainer;
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
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.userName;
                    final StatusBarUserSwitcherContainer statusBarUserSwitcherContainer = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder.bind.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Text text = (Text) obj2;
                            TextViewBinder textViewBinder = TextViewBinder.INSTANCE;
                            TextView textView = StatusBarUserSwitcherContainer.this.text;
                            if (textView == null) {
                                textView = null;
                            }
                            textViewBinder.getClass();
                            TextViewBinder.bind(textView, text);
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

        /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarUserSwitcherContainer $view;
            final /* synthetic */ StatusBarUserChipViewModel $viewModel;
            int label;

            public AnonymousClass3(StatusBarUserChipViewModel statusBarUserChipViewModel, StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = statusBarUserChipViewModel;
                this.$view = statusBarUserSwitcherContainer;
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
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.userAvatar;
                    final StatusBarUserSwitcherContainer statusBarUserSwitcherContainer = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder.bind.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Drawable drawable = (Drawable) obj2;
                            ImageView imageView = StatusBarUserSwitcherContainer.this.avatar;
                            if (imageView == null) {
                                imageView = null;
                            }
                            imageView.setImageDrawable(drawable);
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

        public AnonymousClass1(StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, StatusBarUserChipViewModel statusBarUserChipViewModel, Continuation continuation) {
            super(2, continuation);
            this.$view = statusBarUserSwitcherContainer;
            this.$viewModel = statusBarUserChipViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$viewModel, continuation);
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
            BuildersKt.launch$default(coroutineScope, null, null, new C02621(this.$viewModel, this.$view, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$view, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$view, null), 3);
            StatusBarUserChipViewBinder statusBarUserChipViewBinder = StatusBarUserChipViewBinder.INSTANCE;
            final StatusBarUserSwitcherContainer statusBarUserSwitcherContainer = this.$view;
            final StatusBarUserChipViewModel statusBarUserChipViewModel = this.$viewModel;
            statusBarUserChipViewBinder.getClass();
            statusBarUserSwitcherContainer.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bindButton$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Function1 function1 = StatusBarUserChipViewModel.this.onClick;
                    Expandable.Companion companion = Expandable.Companion;
                    StatusBarUserSwitcherContainer statusBarUserSwitcherContainer2 = statusBarUserSwitcherContainer;
                    companion.getClass();
                    function1.invoke(new Expandable$Companion$fromView$1(statusBarUserSwitcherContainer2));
                }
            });
            return Unit.INSTANCE;
        }
    }

    public StatusBarUserChipViewBinder$bind$1(StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, StatusBarUserChipViewModel statusBarUserChipViewModel, Continuation continuation) {
        super(3, continuation);
        this.$view = statusBarUserSwitcherContainer;
        this.$viewModel = statusBarUserChipViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StatusBarUserChipViewBinder$bind$1 statusBarUserChipViewBinder$bind$1 = new StatusBarUserChipViewBinder$bind$1(this.$view, this.$viewModel, (Continuation) obj3);
        statusBarUserChipViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return statusBarUserChipViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$viewModel, null);
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
