package com.android.systemui.statusbar.policy;

import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;

public final class HeadsUpUtil {
    public static void setNeedsHeadsUpDisappearAnimationAfterClick(View view, boolean z) {
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAnimationRemoved()) {
            return;
        }
        view.setTag(R.id.is_clicked_heads_up_tag, z ? Boolean.TRUE : null);
    }
}
