package com.android.systemui.bouncer.ui.binder;

import android.view.ViewGroup;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.ViewTreeOnBackPressedDispatcherOwner;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.platform.ComposeView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.bouncer.ui.BouncerDialogFactory;
import com.android.systemui.bouncer.ui.composable.BouncerContainerKt;
import com.android.systemui.bouncer.ui.viewmodel.BouncerContainerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.lifecycle.WindowLifecycleState;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ComposeBouncerViewBinder$bind$3 extends SuspendLambda implements Function3 {
    final /* synthetic */ BouncerContainerViewModel.Factory $bouncerContainerViewModelFactory;
    final /* synthetic */ BouncerDialogFactory $dialogFactory;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ BouncerOverlayContentViewModel.Factory $viewModelFactory;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$3$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ BouncerDialogFactory $dialogFactory;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ BouncerOverlayContentViewModel.Factory $viewModelFactory;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ViewGroup viewGroup, LifecycleOwner lifecycleOwner, BouncerOverlayContentViewModel.Factory factory, BouncerDialogFactory bouncerDialogFactory, Continuation continuation) {
            super(3, continuation);
            this.$view = viewGroup;
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$viewModelFactory = factory;
            this.$dialogFactory = bouncerDialogFactory;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new AnonymousClass2(this.$view, this.$$this$repeatWhenAttached, this.$viewModelFactory, this.$dialogFactory, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ViewGroup viewGroup = this.$view;
                    ViewTreeOnBackPressedDispatcherOwner.set(viewGroup, new OnBackPressedDispatcherOwner(this.$$this$repeatWhenAttached, viewGroup) { // from class: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder.bind.3.2.1
                        public final Lifecycle lifecycle;
                        public final OnBackPressedDispatcher onBackPressedDispatcher;

                        {
                            OnBackPressedDispatcher onBackPressedDispatcher = new OnBackPressedDispatcher(null, 1, null);
                            onBackPressedDispatcher.setOnBackInvokedDispatcher(viewGroup.getViewRootImpl().getOnBackInvokedDispatcher());
                            this.onBackPressedDispatcher = onBackPressedDispatcher;
                            this.lifecycle = r4.getLifecycle();
                        }

                        @Override // androidx.lifecycle.LifecycleOwner
                        public final Lifecycle getLifecycle() {
                            return this.lifecycle;
                        }

                        @Override // androidx.activity.OnBackPressedDispatcherOwner
                        public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
                            return this.onBackPressedDispatcher;
                        }
                    });
                    ViewGroup viewGroup2 = this.$view;
                    ComposeView composeView = new ComposeView(this.$view.getContext(), null, 0, 6, null);
                    final BouncerOverlayContentViewModel.Factory factory = this.$viewModelFactory;
                    final BouncerDialogFactory bouncerDialogFactory = this.$dialogFactory;
                    composeView.setContent(new ComposableLambdaImpl(491646943, true, new Function2() { // from class: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$3$2$2$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            Composer composer = (Composer) obj2;
                            if ((((Number) obj3).intValue() & 3) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder.bind.<anonymous>.<anonymous>.<anonymous>.<anonymous> (ComposeBouncerViewBinder.kt:146)");
                            }
                            BouncerContainerKt.BouncerContainer(BouncerOverlayContentViewModel.Factory.this, bouncerDialogFactory, composer, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }));
                    viewGroup2.addView(composeView);
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
                this.$view.removeAllViews();
                throw th;
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComposeBouncerViewBinder$bind$3(ViewGroup viewGroup, BouncerContainerViewModel.Factory factory, BouncerOverlayContentViewModel.Factory factory2, BouncerDialogFactory bouncerDialogFactory, Continuation continuation) {
        super(3, continuation);
        this.$view = viewGroup;
        this.$bouncerContainerViewModelFactory = factory;
        this.$viewModelFactory = factory2;
        this.$dialogFactory = bouncerDialogFactory;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ComposeBouncerViewBinder$bind$3 composeBouncerViewBinder$bind$3 = new ComposeBouncerViewBinder$bind$3(this.$view, this.$bouncerContainerViewModelFactory, this.$viewModelFactory, this.$dialogFactory, (Continuation) obj3);
        composeBouncerViewBinder$bind$3.L$0 = (LifecycleOwner) obj;
        return composeBouncerViewBinder$bind$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            WindowLifecycleState windowLifecycleState = WindowLifecycleState.ATTACHED;
            ViewGroup viewGroup = this.$view;
            final BouncerContainerViewModel.Factory factory = this.$bouncerContainerViewModelFactory;
            Function0 function0 = new Function0() { // from class: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$3$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return BouncerContainerViewModel.Factory.this.create();
                }
            };
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(viewGroup, lifecycleOwner, this.$viewModelFactory, this.$dialogFactory, null);
            this.label = 1;
            if (SysUiViewModelKt.viewModel(viewGroup, "ComposeBouncerViewBinder", windowLifecycleState, function0, anonymousClass2, this) == coroutineSingletons) {
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
