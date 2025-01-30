package com.android.systemui.statusbar.policy;

import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HeadsUpUtil {
    public static void setNeedsHeadsUpDisappearAnimationAfterClick(View view, boolean z) {
        if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isAnimationRemoved()) {
            return;
        }
        view.setTag(R.id.is_clicked_heads_up_tag, z ? Boolean.TRUE : null);
    }
}
