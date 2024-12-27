package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import com.android.systemui.statusbar.disableflags.DisableStateTracker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda2 implements SidelingCutoutContainerInfo, DisableStateTracker.Callback {
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda2(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        this.f$0 = keyguardStatusBarViewController;
    }

    @Override // com.android.systemui.statusbar.phone.SidelingCutoutContainerInfo
    public int getRightSideAvailableWidth(Rect rect) {
        return KeyguardStatusBarViewController.$r8$lambda$z2zgdSVI2vZwFMhzcuLHD6QN16w(this.f$0, rect);
    }
}
