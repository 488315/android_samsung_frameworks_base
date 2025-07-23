package com.android.systemui.statusbar.pipeline.shared.ui.binder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.R;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.MediaProjectionStopDialogModel;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModel;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl;
import kotlin.KotlinNothingValueException;
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
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class HomeStatusBarViewBinderImpl$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ View $clockView;
    final /* synthetic */ int $displayId;
    final /* synthetic */ StatusBarVisibilityChangeListener $listener;
    final /* synthetic */ View $notificationIconsArea;
    final /* synthetic */ View $primaryChipView;
    final /* synthetic */ Function1 $systemEventChipAnimateIn;
    final /* synthetic */ Function1 $systemEventChipAnimateOut;
    final /* synthetic */ View $systemInfoView;
    final /* synthetic */ View $view;
    final /* synthetic */ HomeStatusBarViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ HomeStatusBarViewBinderImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.binder.HomeStatusBarViewBinderImpl$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ View $clockView;
        final /* synthetic */ int $displayId;
        final /* synthetic */ StatusBarVisibilityChangeListener $listener;
        final /* synthetic */ View $notificationIconsArea;
        final /* synthetic */ View $primaryChipView;
        final /* synthetic */ Function1 $systemEventChipAnimateIn;
        final /* synthetic */ Function1 $systemEventChipAnimateOut;
        final /* synthetic */ View $systemInfoView;
        final /* synthetic */ View $view;
        final /* synthetic */ HomeStatusBarViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ HomeStatusBarViewBinderImpl this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.binder.HomeStatusBarViewBinderImpl$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $lightsOutView;
            final /* synthetic */ HomeStatusBarViewModel $viewModel;
            int label;
            final /* synthetic */ HomeStatusBarViewBinderImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(HomeStatusBarViewModel homeStatusBarViewModel, HomeStatusBarViewBinderImpl homeStatusBarViewBinderImpl, View view, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = homeStatusBarViewModel;
                this.this$0 = homeStatusBarViewBinderImpl;
                this.$lightsOutView = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.this$0, this.$lightsOutView, continuation);
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
                    Flow flow = ((HomeStatusBarViewModelImpl) this.$viewModel).areNotificationsLightsOut;
                    final HomeStatusBarViewBinderImpl homeStatusBarViewBinderImpl = this.this$0;
                    final View view = this.$lightsOutView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.binder.HomeStatusBarViewBinderImpl.bind.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            final View view2 = view;
                            HomeStatusBarViewBinderImpl.this.getClass();
                            view2.animate().cancel();
                            final float f = booleanValue ? 1.0f : 0.0f;
                            long j = booleanValue ? 750L : 250L;
                            final int i2 = booleanValue ? 0 : 8;
                            if (booleanValue) {
                                view2.setAlpha(0.0f);
                                view2.setVisibility(0);
                            }
                            view2.animate().alpha(f).setDuration(j).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.binder.HomeStatusBarViewBinderImpl$animateLightsOutView$1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    view2.setAlpha(f);
                                    view2.setVisibility(i2);
                                    view2.animate().setListener(null);
                                }
                            }).start();
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
        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.binder.HomeStatusBarViewBinderImpl$bind$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ HomeStatusBarViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(HomeStatusBarViewModel homeStatusBarViewModel, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = homeStatusBarViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, continuation);
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
                    ReadonlyStateFlow readonlyStateFlow = ((HomeStatusBarViewModelImpl) this.$viewModel).mediaProjectionStopDialogDueToCallEndedState;
                    C03711 c03711 = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.binder.HomeStatusBarViewBinderImpl.bind.1.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            MediaProjectionStopDialogModel mediaProjectionStopDialogModel = (MediaProjectionStopDialogModel) obj2;
                            if (mediaProjectionStopDialogModel instanceof MediaProjectionStopDialogModel.Shown) {
                                final MediaProjectionStopDialogModel.Shown shown = (MediaProjectionStopDialogModel.Shown) mediaProjectionStopDialogModel;
                                SystemUIDialog createDialog = shown.dialogDelegate.createDialog();
                                createDialog.setCanceledOnTouchOutside(false);
                                createDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.statusbar.chips.mediaprojection.domain.model.MediaProjectionStopDialogModel$Shown$createAndShowDialog$1
                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        MediaProjectionStopDialogModel.Shown.this.onDismissAction.invoke();
                                    }
                                });
                                createDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.statusbar.chips.mediaprojection.domain.model.MediaProjectionStopDialogModel$Shown$createAndShowDialog$2
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        MediaProjectionStopDialogModel.Shown.this.onDismissAction.invoke();
                                    }
                                });
                                createDialog.show();
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(c03711, this) == coroutineSingletons) {
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
        public AnonymousClass1(HomeStatusBarViewBinderImpl homeStatusBarViewBinderImpl, int i, StatusBarVisibilityChangeListener statusBarVisibilityChangeListener, View view, View view2, HomeStatusBarViewModel homeStatusBarViewModel, LifecycleOwner lifecycleOwner, View view3, View view4, View view5, Function1 function1, Function1 function12, Continuation continuation) {
            super(2, continuation);
            this.this$0 = homeStatusBarViewBinderImpl;
            this.$displayId = i;
            this.$listener = statusBarVisibilityChangeListener;
            this.$view = view;
            this.$primaryChipView = view2;
            this.$viewModel = homeStatusBarViewModel;
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$clockView = view3;
            this.$notificationIconsArea = view4;
            this.$systemInfoView = view5;
            this.$systemEventChipAnimateIn = function1;
            this.$systemEventChipAnimateOut = function12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$displayId, this.$listener, this.$view, this.$primaryChipView, this.$viewModel, this.$$this$repeatWhenAttached, this.$clockView, this.$notificationIconsArea, this.$systemInfoView, this.$systemEventChipAnimateIn, this.$systemEventChipAnimateOut, continuation);
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
            StatusBarVisibilityChangeListener statusBarVisibilityChangeListener = this.$listener;
            if (statusBarVisibilityChangeListener != null) {
                BuildersKt.launch$default(coroutineScope, null, null, new HomeStatusBarViewBinderImpl$bind$1$1$1$1(this.$viewModel, statusBarVisibilityChangeListener, null), 3);
            }
            StatusBarVisibilityChangeListener statusBarVisibilityChangeListener2 = this.$listener;
            if (statusBarVisibilityChangeListener2 != null) {
                BuildersKt.launch$default(coroutineScope, null, null, new HomeStatusBarViewBinderImpl$bind$1$1$2$1(this.$viewModel, statusBarVisibilityChangeListener2, null), 3);
            }
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.this$0, this.$view.requireViewById(R.id.notification_lights_out), null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeStatusBarViewBinderImpl$bind$1(HomeStatusBarViewBinderImpl homeStatusBarViewBinderImpl, int i, StatusBarVisibilityChangeListener statusBarVisibilityChangeListener, View view, View view2, HomeStatusBarViewModel homeStatusBarViewModel, View view3, View view4, View view5, Function1 function1, Function1 function12, Continuation continuation) {
        super(3, continuation);
        this.this$0 = homeStatusBarViewBinderImpl;
        this.$displayId = i;
        this.$listener = statusBarVisibilityChangeListener;
        this.$view = view;
        this.$primaryChipView = view2;
        this.$viewModel = homeStatusBarViewModel;
        this.$clockView = view3;
        this.$notificationIconsArea = view4;
        this.$systemInfoView = view5;
        this.$systemEventChipAnimateIn = function1;
        this.$systemEventChipAnimateOut = function12;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        HomeStatusBarViewBinderImpl$bind$1 homeStatusBarViewBinderImpl$bind$1 = new HomeStatusBarViewBinderImpl$bind$1(this.this$0, this.$displayId, this.$listener, this.$view, this.$primaryChipView, this.$viewModel, this.$clockView, this.$notificationIconsArea, this.$systemInfoView, this.$systemEventChipAnimateIn, this.$systemEventChipAnimateOut, (Continuation) obj3);
        homeStatusBarViewBinderImpl$bind$1.L$0 = (LifecycleOwner) obj;
        return homeStatusBarViewBinderImpl$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$displayId, this.$listener, this.$view, this.$primaryChipView, this.$viewModel, lifecycleOwner, this.$clockView, this.$notificationIconsArea, this.$systemInfoView, this.$systemEventChipAnimateIn, this.$systemEventChipAnimateOut, null);
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
