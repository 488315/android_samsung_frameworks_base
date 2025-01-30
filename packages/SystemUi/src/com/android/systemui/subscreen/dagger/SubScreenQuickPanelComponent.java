package com.android.systemui.subscreen.dagger;

import com.android.systemui.statusbar.phone.SubScreenQuickPanelHeaderController;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface SubScreenQuickPanelComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        SubScreenQuickPanelComponent create(SubScreenQuickPanelWindowView subScreenQuickPanelWindowView);
    }

    SubScreenQuickPanelHeaderController getSubScreenQuickPanelHeaderController();
}
