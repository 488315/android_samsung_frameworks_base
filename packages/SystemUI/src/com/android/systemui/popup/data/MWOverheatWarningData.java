package com.android.systemui.popup.data;

import com.android.systemui.R;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
