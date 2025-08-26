package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.qs.SecQSGradationDrawableController;

/* loaded from: classes2.dex */
public final class SecQSGradationDrawableView extends FrameLayout {
    public SecQSGradationDrawableController.AnonymousClass2 configChangedCallback;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecQSGradationDrawableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context.getClass();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        SecQSGradationDrawableController.AnonymousClass2 anonymousClass2 = this.configChangedCallback;
        if (anonymousClass2 != null) {
            anonymousClass2.onConfigChanged(configuration);
        }
    }
}
