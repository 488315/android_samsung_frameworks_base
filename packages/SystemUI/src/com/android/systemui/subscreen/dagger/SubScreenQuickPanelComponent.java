package com.android.systemui.subscreen.dagger;

import com.android.systemui.statusbar.phone.SubScreenQuickPanelHeaderController;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface SubScreenQuickPanelComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        SubScreenQuickPanelComponent create(SubScreenQuickPanelWindowView subScreenQuickPanelWindowView);
    }

    SubScreenQuickPanelHeaderController getSubScreenQuickPanelHeaderController();
}
