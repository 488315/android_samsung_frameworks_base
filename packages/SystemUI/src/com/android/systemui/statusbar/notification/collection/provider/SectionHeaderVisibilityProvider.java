package com.android.systemui.statusbar.notification.collection.provider;

import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SectionHeaderVisibilityProvider {
    public final boolean neverShowSectionHeaders;
    public boolean sectionHeadersVisible = true;

    public SectionHeaderVisibilityProvider(Context context) {
        this.neverShowSectionHeaders = context.getResources().getBoolean(R.bool.config_notification_never_show_section_headers);
    }
}
