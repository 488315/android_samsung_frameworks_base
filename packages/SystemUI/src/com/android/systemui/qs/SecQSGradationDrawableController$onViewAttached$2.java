package com.android.systemui.qs;

import android.content.res.Configuration;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSGradationDrawableController$onViewAttached$2 implements ConfigurationController.ConfigurationListener {
    public final /* synthetic */ SecQSGradationDrawableController this$0;

    public SecQSGradationDrawableController$onViewAttached$2(SecQSGradationDrawableController secQSGradationDrawableController) {
        this.this$0 = secQSGradationDrawableController;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        SecQSGradationDrawableView secQSGradationDrawableView = this.this$0.view;
        secQSGradationDrawableView.getLayoutParams().height = secQSGradationDrawableView.getContext().getResources().getDimensionPixelSize(R.dimen.qs_gradation_height);
    }
}
