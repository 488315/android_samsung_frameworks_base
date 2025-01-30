package com.android.systemui.user.p035ui.binder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.common.p004ui.binder.TextViewBinder;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.user.p035ui.viewmodel.StatusBarUserChipViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1", m277f = "StatusBarUserChipViewBinder.kt", m278l = {45}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class StatusBarUserChipViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ StatusBarUserSwitcherContainer $view;
    final /* synthetic */ StatusBarUserChipViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1", m277f = "StatusBarUserChipViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1 */
    final class C35591 extends SuspendLambda implements Function2 {
        final /* synthetic */ StatusBarUserSwitcherContainer $view;
        final /* synthetic */ StatusBarUserChipViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$1", m277f = "StatusBarUserChipViewBinder.kt", m278l = {47}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarUserSwitcherContainer $view;
            final /* synthetic */ StatusBarUserChipViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(StatusBarUserChipViewModel statusBarUserChipViewModel, StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$viewModel = statusBarUserChipViewModel;
                this.$view = statusBarUserSwitcherContainer;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$view, continuation);
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$2", m277f = "StatusBarUserChipViewBinder.kt", m278l = {51}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarUserSwitcherContainer $view;
            final /* synthetic */ StatusBarUserChipViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(StatusBarUserChipViewModel statusBarUserChipViewModel, StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, Continuation<? super AnonymousClass2> continuation) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$3", m277f = "StatusBarUserChipViewBinder.kt", m278l = {55}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarUserSwitcherContainer $view;
            final /* synthetic */ StatusBarUserChipViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(StatusBarUserChipViewModel statusBarUserChipViewModel, StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, Continuation<? super AnonymousClass3> continuation) {
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
        public C35591(StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, StatusBarUserChipViewModel statusBarUserChipViewModel, Continuation<? super C35591> continuation) {
            super(2, continuation);
            this.$view = statusBarUserSwitcherContainer;
            this.$viewModel = statusBarUserChipViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C35591 c35591 = new C35591(this.$view, this.$viewModel, continuation);
            c35591.L$0 = obj;
            return c35591;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C35591) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$view, null), 3);
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarUserChipViewBinder$bind$1(StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, StatusBarUserChipViewModel statusBarUserChipViewModel, Continuation<? super StatusBarUserChipViewBinder$bind$1> continuation) {
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
            C35591 c35591 = new C35591(this.$view, this.$viewModel, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c35591, this) == coroutineSingletons) {
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
