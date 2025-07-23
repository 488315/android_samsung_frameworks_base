package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSGradationDrawableView extends FrameLayout {
    public SecQSGradationDrawableController$onViewAttached$2 configChangedCallback;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecQSGradationDrawableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context.getClass();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        SecQSGradationDrawableController$onViewAttached$2 secQSGradationDrawableController$onViewAttached$2 = this.configChangedCallback;
        if (secQSGradationDrawableController$onViewAttached$2 != null) {
            secQSGradationDrawableController$onViewAttached$2.onConfigChanged(configuration);
        }
    }
}
