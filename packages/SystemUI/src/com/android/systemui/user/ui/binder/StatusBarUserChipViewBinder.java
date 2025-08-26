package com.android.systemui.user.ui.binder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.BasicRune;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.ui.binder.TextViewBinder;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes3.dex */
public final class StatusBarUserChipViewBinder {
    public static final StatusBarUserChipViewBinder INSTANCE = new StatusBarUserChipViewBinder();

    /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ StatusBarUserSwitcherContainer $view;
        final /* synthetic */ StatusBarUserChipViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C06131 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarUserSwitcherContainer $view;
            final /* synthetic */ StatusBarUserChipViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C06141 extends SuspendLambda implements Function2 {
                final /* synthetic */ StatusBarUserSwitcherContainer $view;
                final /* synthetic */ StatusBarUserChipViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C06141(StatusBarUserChipViewModel statusBarUserChipViewModel, StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = statusBarUserChipViewModel;
                    this.$view = statusBarUserSwitcherContainer;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C06141(this.$viewModel, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C06141) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                statusBarUserSwitcherContainer.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
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
                                TextView textView = statusBarUserSwitcherContainer.text;
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
                                ImageView imageView = statusBarUserSwitcherContainer.avatar;
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
            public C06131(StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, StatusBarUserChipViewModel statusBarUserChipViewModel, Continuation continuation) {
                super(2, continuation);
                this.$view = statusBarUserSwitcherContainer;
                this.$viewModel = statusBarUserChipViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C06131 c06131 = new C06131(this.$view, this.$viewModel, continuation);
                c06131.L$0 = obj;
                return c06131;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C06131) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C06141(this.$viewModel, this.$view, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$view, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$view, null), 7);
                StatusBarUserChipViewBinder statusBarUserChipViewBinder = StatusBarUserChipViewBinder.INSTANCE;
                final StatusBarUserSwitcherContainer statusBarUserSwitcherContainer = this.$view;
                final StatusBarUserChipViewModel statusBarUserChipViewModel = this.$viewModel;
                statusBarUserChipViewBinder.getClass();
                statusBarUserSwitcherContainer.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bindButton$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        StatusBarUserChipViewModel$$ExternalSyntheticLambda0 statusBarUserChipViewModel$$ExternalSyntheticLambda0 = statusBarUserChipViewModel.onClick;
                        Expandable.Companion companion = Expandable.Companion;
                        StatusBarUserSwitcherContainer statusBarUserSwitcherContainer2 = statusBarUserSwitcherContainer;
                        companion.getClass();
                        statusBarUserChipViewModel$$ExternalSyntheticLambda0.mo781invoke(new Expandable$Companion$fromView$1(statusBarUserSwitcherContainer2));
                    }
                });
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, StatusBarUserChipViewModel statusBarUserChipViewModel, Continuation continuation) {
            super(3, continuation);
            this.$view = statusBarUserSwitcherContainer;
            this.$viewModel = statusBarUserChipViewModel;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$viewModel, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                C06131 c06131 = new C06131(this.$view, this.$viewModel, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c06131, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
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
            final class C06181 extends SuspendLambda implements Function2 {
                final /* synthetic */ Function1 $userCountCallback;
                final /* synthetic */ StatusBarUserChipViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C06181(StatusBarUserChipViewModel statusBarUserChipViewModel, Function1 function1, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = statusBarUserChipViewModel;
                    this.$userCountCallback = function1;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C06181(this.$viewModel, this.$userCountCallback, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C06181) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                int iIntValue = ((Number) obj2).intValue();
                                Function1 function12 = function1;
                                if (function12 != null) {
                                    function12.mo781invoke(new Integer(iIntValue));
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
                CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new C06181(this.$viewModel, this.$userCountCallback, null), 7);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(StatusBarUserChipViewModel statusBarUserChipViewModel, Function1 function1, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = statusBarUserChipViewModel;
            this.$userCountCallback = function1;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$userCountCallback, (Continuation) obj3);
            anonymousClass2.L$0 = (LifecycleOwner) obj;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
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

    private StatusBarUserChipViewBinder() {
    }

    public static final void bind(StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, StatusBarUserChipViewModel statusBarUserChipViewModel, KeyguardStatusBarView$$ExternalSyntheticLambda0 keyguardStatusBarView$$ExternalSyntheticLambda0) {
        RepeatWhenAttachedKt.repeatWhenAttached(statusBarUserSwitcherContainer, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(statusBarUserSwitcherContainer, statusBarUserChipViewModel, null));
        if (BasicRune.STATUS_LAYOUT_MUM_ICON) {
            RepeatWhenAttachedKt.repeatWhenAttached(statusBarUserSwitcherContainer, EmptyCoroutineContext.INSTANCE, new AnonymousClass2(statusBarUserChipViewModel, keyguardStatusBarView$$ExternalSyntheticLambda0, null));
        }
    }
}
