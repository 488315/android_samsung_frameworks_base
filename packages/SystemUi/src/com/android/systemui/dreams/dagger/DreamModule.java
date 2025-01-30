package com.android.systemui.dreams.dagger;

import android.R;
import android.content.res.Resources;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface DreamModule {
    static boolean providesDreamOnlyEnabledForDockUser(Resources resources) {
        return resources.getBoolean(R.bool.config_disableTransitionAnimation);
    }

    static boolean providesDreamSupported(Resources resources) {
        return resources.getBoolean(R.bool.config_disable_all_cb_messages);
    }
}
