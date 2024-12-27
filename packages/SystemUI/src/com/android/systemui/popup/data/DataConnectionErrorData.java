package com.android.systemui.popup.data;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.util.DeviceType;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Arrays;

public class DataConnectionErrorData {
    private static final int INVALID_MODE = -1;
    private static final String TAG = "DataConnectionErrorData";
    private LogWrapper mLogWrapper;

    public DataConnectionErrorData(LogWrapper logWrapper) {
        this.mLogWrapper = logWrapper;
    }

    public static /* synthetic */ void lambda$getPButtonClickListener$0(Context context) {
        Intent intent = new Intent();
        intent.setAction(PopupUIUtil.ACTION_AIRPLANE_MODE_SETTINGS);
        intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        context.startActivity(intent);
    }

    public static /* synthetic */ void lambda$getPButtonClickListener$1(Context context) {
        ((TelephonyManager) context.getSystemService("phone")).setDataEnabled(true);
    }

    public /* synthetic */ void lambda$getPButtonClickListener$2(boolean z, PendingIntent pendingIntent) {
        if (!z || pendingIntent == null) {
            return;
        }
        try {
            pendingIntent.send();
        } catch (Exception e) {
            this.mLogWrapper.d(TAG, "showDataConnectionNotifications() : PendingIntent.send() error. " + Arrays.toString(e.getStackTrace()));
        }
    }

    public int getBody(int i) {
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

    public int getNButton(int i, boolean z) {
        if (i == 0 || i == 1) {
            return R.string.no;
        }
        if (i == 4 && z) {
            return R.string.later;
        }
        return -1;
    }

    public int getPButton(int i, boolean z) {
        if (i == 0) {
            return R.string.status_bar_settings_settings_button;
        }
        if (i == 1) {
            return R.string.popupui_dialog_turn_on_button;
        }
        if (i == 2) {
            return R.string.yes;
        }
        if (i != 4) {
            return -1;
        }
        return z ? R.string.retry : R.string.yes;
    }

    public Runnable getPButtonClickListener(final Context context, int i, final boolean z, final PendingIntent pendingIntent) {
        if (i == 0) {
            final int i2 = 0;
            return new Runnable() { // from class: com.android.systemui.popup.data.DataConnectionErrorData$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    int i3 = i2;
                    Context context2 = context;
                    switch (i3) {
                        case 0:
                            DataConnectionErrorData.lambda$getPButtonClickListener$0(context2);
                            break;
                        default:
                            DataConnectionErrorData.lambda$getPButtonClickListener$1(context2);
                            break;
                    }
                }
            };
        }
        if (i == 1) {
            final int i3 = 1;
            return new Runnable() { // from class: com.android.systemui.popup.data.DataConnectionErrorData$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    int i32 = i3;
                    Context context2 = context;
                    switch (i32) {
                        case 0:
                            DataConnectionErrorData.lambda$getPButtonClickListener$0(context2);
                            break;
                        default:
                            DataConnectionErrorData.lambda$getPButtonClickListener$1(context2);
                            break;
                    }
                }
            };
        }
        if (i != 4) {
            return null;
        }
        return new Runnable() { // from class: com.android.systemui.popup.data.DataConnectionErrorData$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DataConnectionErrorData.this.lambda$getPButtonClickListener$2(z, pendingIntent);
            }
        };
    }

    public int getTitle(int i) {
        if (i == 0) {
            return R.string.data_connection_error_flight_mode_on_title;
        }
        if (i == 1) {
            return R.string.data_connection_error_mobile_data_off_title;
        }
        if (i == 2 || i == 4) {
            return R.string.data_connection_error_no_network_connection;
        }
        return -1;
    }
}
