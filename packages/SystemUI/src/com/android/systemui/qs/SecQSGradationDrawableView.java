package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecQSGradationDrawableView extends FrameLayout {
    public ConfigurationController.ConfigurationListener configChangedCallback;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecQSGradationDrawableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNull(context);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        ConfigurationController.ConfigurationListener configurationListener = this.configChangedCallback;
        if (configurationListener != null) {
            configurationListener.onConfigChanged(configuration);
        }
    }
}
