package com.android.systemui.qs;

import android.content.res.Configuration;
import com.android.systemui.R;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.ViewController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class SecQSGradationDrawableController extends ViewController {
    public StandaloneCoroutine job;
    public final SecPanelExpansionStateInteractor panelExpansionStateInteractor;
    public final CoroutineScope scope;
    public final SecQsUiDisplayModeInteractor uiDisplayModeInteractor;
    public final SecQSGradationDrawableView view;

    /* renamed from: com.android.systemui.qs.SecQSGradationDrawableController$onViewAttached$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SecQSGradationDrawableController.this.new AnonymousClass1(continuation);
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
                SecQSGradationDrawableController secQSGradationDrawableController = SecQSGradationDrawableController.this;
                SecPanelExpansionStateInteractor secPanelExpansionStateInteractor = secQSGradationDrawableController.panelExpansionStateInteractor;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(FlowKt.combine(secPanelExpansionStateInteractor.shadeFraction, secPanelExpansionStateInteractor.lockscreenShadeFraction, secPanelExpansionStateInteractor.statusBarState, new SecQSGradationDrawableController$onViewAttached$1$1$1(null))), secQSGradationDrawableController.uiDisplayModeInteractor.getUiDisplayMode(), new SecQSGradationDrawableController$onViewAttached$1$1$2(null));
                SecQSGradationDrawableController$onViewAttached$1$1$3 secQSGradationDrawableController$onViewAttached$1$1$3 = new SecQSGradationDrawableController$onViewAttached$1$1$3(secQSGradationDrawableController, null);
                this.label = 1;
                if (FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, secQSGradationDrawableController$onViewAttached$1$1$3, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.qs.SecQSGradationDrawableController$onViewAttached$2, reason: invalid class name */
    public final class AnonymousClass2 implements ConfigurationController.ConfigurationListener {
        public AnonymousClass2() {
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            SecQSGradationDrawableView secQSGradationDrawableView = SecQSGradationDrawableController.this.view;
            secQSGradationDrawableView.getLayoutParams().height = secQSGradationDrawableView.getContext().getResources().getDimensionPixelSize(R.dimen.qs_gradation_height);
        }
    }

    public SecQSGradationDrawableController(SecQSGradationDrawableView secQSGradationDrawableView, CoroutineScope coroutineScope, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        super(secQSGradationDrawableView);
        this.view = secQSGradationDrawableView;
        this.scope = coroutineScope;
        this.panelExpansionStateInteractor = secPanelExpansionStateInteractor;
        this.uiDisplayModeInteractor = secQsUiDisplayModeInteractor;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.job = BuildersKt.launch$default(this.scope, null, null, new AnonymousClass1(null), 3);
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        SecQSGradationDrawableView secQSGradationDrawableView = this.view;
        secQSGradationDrawableView.configChangedCallback = anonymousClass2;
        secQSGradationDrawableView.getLayoutParams().height = secQSGradationDrawableView.getContext().getResources().getDimensionPixelSize(R.dimen.qs_gradation_height);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        StandaloneCoroutine standaloneCoroutine = this.job;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
    }
}
