package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerDependencies;

public final class AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1 implements View.OnAttachStateChangeListener {
    public final AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1$onBackInvokedCallback$1 onBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1$onBackInvokedCallback$1
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            ((AlternateBouncerDependencies) AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1.this.this$0.alternateBouncerDependencies.get()).viewModel.statusBarKeyguardViewManager.hideAlternateBouncer(false);
        }
    };
    public final /* synthetic */ AlternateBouncerViewBinder this$0;

    public AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1(AlternateBouncerViewBinder alternateBouncerViewBinder) {
        this.this$0 = alternateBouncerViewBinder;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        OnBackInvokedDispatcher findOnBackInvokedDispatcher = view.findOnBackInvokedDispatcher();
        if (findOnBackInvokedDispatcher != null) {
            findOnBackInvokedDispatcher.registerOnBackInvokedCallback(1000000, this.onBackInvokedCallback);
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        OnBackInvokedDispatcher findOnBackInvokedDispatcher = view.findOnBackInvokedDispatcher();
        if (findOnBackInvokedDispatcher != null) {
            findOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(this.onBackInvokedCallback);
        }
    }
}
