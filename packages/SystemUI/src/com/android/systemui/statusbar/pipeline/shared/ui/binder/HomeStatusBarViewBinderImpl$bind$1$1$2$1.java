package com.android.systemui.statusbar.pipeline.shared.ui.binder;

import com.android.systemui.statusbar.core.StatusBarRootModernization;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModel;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class HomeStatusBarViewBinderImpl$bind$1$1$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ StatusBarVisibilityChangeListener $listener;
    final /* synthetic */ HomeStatusBarViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeStatusBarViewBinderImpl$bind$1$1$2$1(HomeStatusBarViewModel homeStatusBarViewModel, StatusBarVisibilityChangeListener statusBarVisibilityChangeListener, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = homeStatusBarViewModel;
        this.$listener = statusBarVisibilityChangeListener;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HomeStatusBarViewBinderImpl$bind$1$1$2$1(this.$viewModel, this.$listener, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HomeStatusBarViewBinderImpl$bind$1$1$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flow = ((HomeStatusBarViewModelImpl) this.$viewModel).transitionFromLockscreenToDreamStartedEvent;
            final StatusBarVisibilityChangeListener statusBarVisibilityChangeListener = this.$listener;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.binder.HomeStatusBarViewBinderImpl$bind$1$1$2$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    CollapsedStatusBarFragment.AnonymousClass5 anonymousClass5 = (CollapsedStatusBarFragment.AnonymousClass5) statusBarVisibilityChangeListener;
                    anonymousClass5.getClass();
                    int i2 = StatusBarRootModernization.$r8$clinit;
                    int i3 = CollapsedStatusBarFragment.$r8$clinit;
                    CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                    collapsedStatusBarFragment.getClass();
                    ((CollapsedStatusBarFragmentExt) collapsedStatusBarFragment.mSamsungExtLazy.get()).postUpdateStatusBarVisibility();
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
