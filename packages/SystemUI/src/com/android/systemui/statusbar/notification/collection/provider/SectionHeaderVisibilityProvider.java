package com.android.systemui.statusbar.notification.collection.provider;

import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SectionHeaderVisibilityProvider {
    public final boolean neverShowSectionHeaders;
    public boolean sectionHeadersVisible = true;

    public SectionHeaderVisibilityProvider(Context context) {
        this.neverShowSectionHeaders = context.getResources().getBoolean(R.bool.config_notification_never_show_section_headers);
    }
}
