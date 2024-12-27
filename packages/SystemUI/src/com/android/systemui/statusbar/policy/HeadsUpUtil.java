package com.android.systemui.statusbar.policy;

import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HeadsUpUtil {
    public static void setNeedsHeadsUpDisappearAnimationAfterClick(View view, boolean z) {
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAnimationRemoved()) {
            return;
        }
        view.setTag(R.id.is_clicked_heads_up_tag, z ? Boolean.TRUE : null);
    }
}
