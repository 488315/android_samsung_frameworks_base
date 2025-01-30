package com.android.systemui.statusbar.notification.collection.provider;

import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SectionHeaderVisibilityProvider {
    public final boolean neverShowSectionHeaders;
    public boolean sectionHeadersVisible = true;

    public SectionHeaderVisibilityProvider(Context context) {
        this.neverShowSectionHeaders = context.getResources().getBoolean(R.bool.config_notification_never_show_section_headers);
    }
}
