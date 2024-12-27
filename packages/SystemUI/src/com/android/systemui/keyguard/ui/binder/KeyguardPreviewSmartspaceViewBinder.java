package com.android.systemui.keyguard.ui.binder;

import android.content.Context;
import android.view.View;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardPreviewSmartspaceViewBinder {
    public static final KeyguardPreviewSmartspaceViewBinder INSTANCE = new KeyguardPreviewSmartspaceViewBinder();

    private KeyguardPreviewSmartspaceViewBinder() {
    }

    public static final void bind(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, boolean z, Context context, View view) {
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new KeyguardPreviewSmartspaceViewBinder$bind$1(keyguardPreviewSmartspaceViewModel, z, context, view, null));
    }
}
