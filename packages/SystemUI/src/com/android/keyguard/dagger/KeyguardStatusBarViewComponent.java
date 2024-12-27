package com.android.keyguard.dagger;

import com.android.systemui.shade.ShadeViewStateProvider;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface KeyguardStatusBarViewComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        KeyguardStatusBarViewComponent build(KeyguardStatusBarView keyguardStatusBarView, ShadeViewStateProvider shadeViewStateProvider);
    }

    KeyguardStatusBarViewController getKeyguardStatusBarViewController();
}
