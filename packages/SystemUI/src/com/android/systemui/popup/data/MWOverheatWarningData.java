package com.android.systemui.popup.data;

import com.android.systemui.R;
import com.android.systemui.util.DeviceType;

public class MWOverheatWarningData {
    public int getBody() {
        return DeviceType.isTablet() ? R.string.multiwindow_overheat_warning_dialog_body_tablet : R.string.multiwindow_overheat_warning_dialog_body_phone;
    }

    public int getPButton() {
        return R.string.yes;
    }

    public int getTitle() {
        return R.string.multiwindow_overheat_warning_dialog_title;
    }
}
