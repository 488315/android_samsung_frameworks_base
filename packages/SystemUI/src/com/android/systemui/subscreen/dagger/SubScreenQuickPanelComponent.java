package com.android.systemui.subscreen.dagger;

import com.android.systemui.statusbar.phone.SubScreenQuickPanelHeaderController;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowView;

public interface SubScreenQuickPanelComponent {

    public interface Factory {
        SubScreenQuickPanelComponent create(SubScreenQuickPanelWindowView subScreenQuickPanelWindowView);
    }

    SubScreenQuickPanelHeaderController getSubScreenQuickPanelHeaderController();
}
