package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerDependencies;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1 implements View.OnAttachStateChangeListener {
    public final AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1$onBackInvokedCallback$1 onBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1$onBackInvokedCallback$1
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            ((AlternateBouncerDependencies) AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1.this.this$0.alternateBouncerDependencies.get()).viewModel.statusBarKeyguardViewManager.hideAlternateBouncer(false);
        }
    };
    public final /* synthetic */ AlternateBouncerViewBinder this$0;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1$onBackInvokedCallback$1] */
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
