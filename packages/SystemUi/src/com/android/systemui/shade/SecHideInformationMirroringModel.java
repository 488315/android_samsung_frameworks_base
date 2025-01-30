package com.android.systemui.shade;

import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecHideInformationMirroringModel {
    public final SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);

    public final boolean shouldHideInformation() {
        return this.settingsHelper.mItemLists.get("smart_view_show_notification_on").getIntValue() == 0;
    }
}
