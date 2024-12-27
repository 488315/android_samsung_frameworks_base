package com.android.systemui.popup.data;

import com.android.systemui.R;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
