package com.android.systemui.popup.data;

import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DataConnectionErrorData {
    public final LogWrapper mLogWrapper;

    public DataConnectionErrorData(LogWrapper logWrapper) {
        this.mLogWrapper = logWrapper;
    }

    public static int getBody(int i) {
        if (i == 0) {
            return DeviceType.isTablet() ? R.string.data_connection_error_flight_mode_on_body_tablet : R.string.data_connection_error_flight_mode_on_body;
        }
        if (i == 1) {
            return R.string.data_connection_error_mobile_data_off_body;
        }
        if (i == 2) {
            return R.string.data_connection_error_data_roaming_off_body;
        }
        if (i != 4) {
            return -1;
        }
        return R.string.data_connection_error_no_signal_body;
    }

    public static int getPButton(int i, boolean z) {
        if (i == 0) {
            return R.string.status_bar_settings_settings_button;
        }
        if (i == 1) {
            return R.string.popupui_dialog_turn_on_button;
        }
        if (i != 2) {
            if (i != 4) {
                return -1;
            }
            if (z) {
                return R.string.retry;
            }
        }
        return R.string.yes;
    }
}
