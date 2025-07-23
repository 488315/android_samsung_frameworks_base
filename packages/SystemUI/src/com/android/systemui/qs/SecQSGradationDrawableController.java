package com.android.systemui.qs;

import com.android.systemui.R;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.ViewController;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSGradationDrawableController extends ViewController {
    public StandaloneCoroutine job;
    public final SecPanelExpansionStateInteractor panelExpansionStateInteractor;
    public final CoroutineScope scope;
    public final SecQsUiDisplayModeInteractor uiDisplayModeInteractor;
    public final SecQSGradationDrawableView view;

    public SecQSGradationDrawableController(SecQSGradationDrawableView secQSGradationDrawableView, CoroutineScope coroutineScope, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        super(secQSGradationDrawableView);
        this.view = secQSGradationDrawableView;
        this.scope = coroutineScope;
        this.panelExpansionStateInteractor = secPanelExpansionStateInteractor;
        this.uiDisplayModeInteractor = secQsUiDisplayModeInteractor;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.job = BuildersKt.launch$default(this.scope, null, null, new SecQSGradationDrawableController$onViewAttached$1(this, null), 3);
        SecQSGradationDrawableController$onViewAttached$2 secQSGradationDrawableController$onViewAttached$2 = new SecQSGradationDrawableController$onViewAttached$2(this);
        SecQSGradationDrawableView secQSGradationDrawableView = this.view;
        secQSGradationDrawableView.configChangedCallback = secQSGradationDrawableController$onViewAttached$2;
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
