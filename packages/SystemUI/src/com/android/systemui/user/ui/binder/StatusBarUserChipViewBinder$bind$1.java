package com.android.systemui.user.ui.binder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.ui.binder.TextViewBinder;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel$$ExternalSyntheticLambda0;
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
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class StatusBarUserChipViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ StatusBarUserSwitcherContainer $view;
    final /* synthetic */ StatusBarUserChipViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ StatusBarUserSwitcherContainer $view;
        final /* synthetic */ StatusBarUserChipViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C03961 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarUserSwitcherContainer $view;
            final /* synthetic */ StatusBarUserChipViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03961(StatusBarUserChipViewModel statusBarUserChipViewModel, StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = statusBarUserChipViewModel;
                this.$view = statusBarUserSwitcherContainer;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03961(this.$viewModel, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03961) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarUserSwitcherContainer $view;
            final /* synthetic */ StatusBarUserChipViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarUserSwitcherContainer $view;
            final /* synthetic */ StatusBarUserChipViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03961(this.$viewModel, this.$view, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$view, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$view, null), 7);
            StatusBarUserChipViewBinder statusBarUserChipViewBinder = StatusBarUserChipViewBinder.INSTANCE;
            final StatusBarUserSwitcherContainer statusBarUserSwitcherContainer = this.$view;
            final StatusBarUserChipViewModel statusBarUserChipViewModel = this.$viewModel;
            statusBarUserChipViewBinder.getClass();
            statusBarUserSwitcherContainer.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bindButton$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StatusBarUserChipViewModel$$ExternalSyntheticLambda0 statusBarUserChipViewModel$$ExternalSyntheticLambda0 = StatusBarUserChipViewModel.this.onClick;
                    Expandable.Companion companion = Expandable.Companion;
                    StatusBarUserSwitcherContainer statusBarUserSwitcherContainer2 = statusBarUserSwitcherContainer;
                    companion.getClass();
                    statusBarUserChipViewModel$$ExternalSyntheticLambda0.mo779invoke(new Expandable$Companion$fromView$1(statusBarUserSwitcherContainer2));
                }
            });
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
