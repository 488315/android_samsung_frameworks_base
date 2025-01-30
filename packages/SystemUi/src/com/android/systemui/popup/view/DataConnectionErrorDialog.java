package com.android.systemui.popup.view;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.data.DataConnectionErrorData;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DataConnectionErrorDialog implements PopupUIAlertDialog {
    public AlertDialog mDialog;
    public final LogWrapper mLogWrapper;

    /* JADX WARN: Removed duplicated region for block: B:42:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DataConnectionErrorDialog(final Context context, LogWrapper logWrapper, Runnable runnable, Runnable runnable2, int i, final boolean z, final PendingIntent pendingIntent) {
        String str;
        String str2;
        Runnable runnable3;
        final Runnable runnable4;
        this.mLogWrapper = logWrapper;
        final DataConnectionErrorData dataConnectionErrorData = new DataConnectionErrorData(logWrapper);
        int i2 = R.string.data_connection_error_no_network_connection;
        int i3 = -1;
        final int i4 = 1;
        if ((i != 0 ? i != 1 ? (i == 2 || i == 4) ? R.string.data_connection_error_no_network_connection : (char) 65535 : R.string.data_connection_error_mobile_data_off_title : R.string.data_connection_error_flight_mode_on_title) != 65535) {
            Resources resources = context.getResources();
            if (i == 0) {
                i2 = R.string.data_connection_error_flight_mode_on_title;
            } else if (i == 1) {
                i2 = R.string.data_connection_error_mobile_data_off_title;
            } else if (i != 2 && i != 4) {
                i2 = -1;
            }
            str = resources.getString(i2);
        } else {
            str = null;
        }
        String string = DataConnectionErrorData.getBody(i) != -1 ? context.getResources().getString(DataConnectionErrorData.getBody(i)) : null;
        String string2 = DataConnectionErrorData.getPButton(i, z) != -1 ? context.getResources().getString(DataConnectionErrorData.getPButton(i, z)) : null;
        if (((i == 0 || i == 1) ? R.string.f788no : (i == 4 && z) ? R.string.later : (char) 65535) != 65535) {
            Resources resources2 = context.getResources();
            if (i == 0 || i == 1) {
                i3 = R.string.f788no;
            } else if (i == 4 && z) {
                i3 = R.string.later;
            }
            str2 = resources2.getString(i3);
        } else {
            str2 = null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2132018540);
        builder.setTitle(str);
        builder.setMessage(string);
        if (i == 0) {
            final int i5 = 0;
            runnable3 = new Runnable() { // from class: com.android.systemui.popup.data.DataConnectionErrorData$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i5) {
                        case 0:
                            Context context2 = context;
                            Intent intent = new Intent();
                            intent.setAction("com.samsung.settings.AIRPLANE_MODE");
                            intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                            context2.startActivity(intent);
                            break;
                        default:
                            ((TelephonyManager) context.getSystemService("phone")).setDataEnabled(true);
                            break;
                    }
                }
            };
        } else {
            if (i != 1) {
                runnable4 = i != 4 ? null : new Runnable() { // from class: com.android.systemui.popup.data.DataConnectionErrorData$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DataConnectionErrorData dataConnectionErrorData2 = DataConnectionErrorData.this;
                        boolean z2 = z;
                        PendingIntent pendingIntent2 = pendingIntent;
                        dataConnectionErrorData2.getClass();
                        if (!z2 || pendingIntent2 == null) {
                            return;
                        }
                        try {
                            pendingIntent2.send();
                        } catch (Exception e) {
                            dataConnectionErrorData2.mLogWrapper.m98d("DataConnectionErrorData", "showDataConnectionNotifications() : PendingIntent.send() error. " + e.getStackTrace().toString());
                        }
                    }
                };
                builder.setPositiveButton(string2, runnable4 != null ? null : new DialogInterface.OnClickListener() { // from class: com.android.systemui.popup.view.DataConnectionErrorDialog$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i6) {
                        runnable4.run();
                    }
                });
                if (str2 != null) {
                    builder.setNegativeButton(str2, (DialogInterface.OnClickListener) null);
                }
                AlertDialog create = builder.create();
                this.mDialog = create;
                create.getWindow().getAttributes().setTitle("DataConnectionErrorDialog");
                this.mDialog.getWindow().setType(2008);
                this.mDialog = this.mDialog;
            }
            runnable3 = new Runnable() { // from class: com.android.systemui.popup.data.DataConnectionErrorData$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i4) {
                        case 0:
                            Context context2 = context;
                            Intent intent = new Intent();
                            intent.setAction("com.samsung.settings.AIRPLANE_MODE");
                            intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                            context2.startActivity(intent);
                            break;
                        default:
                            ((TelephonyManager) context.getSystemService("phone")).setDataEnabled(true);
                            break;
                    }
                }
            };
        }
        runnable4 = runnable3;
        builder.setPositiveButton(string2, runnable4 != null ? null : new DialogInterface.OnClickListener() { // from class: com.android.systemui.popup.view.DataConnectionErrorDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i6) {
                runnable4.run();
            }
        });
        if (str2 != null) {
        }
        AlertDialog create2 = builder.create();
        this.mDialog = create2;
        create2.getWindow().getAttributes().setTitle("DataConnectionErrorDialog");
        this.mDialog.getWindow().setType(2008);
        this.mDialog = this.mDialog;
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public final void dismiss() {
        if (this.mDialog == null || !isShowing()) {
            return;
        }
        this.mLogWrapper.m103v("DataConnectionErrorDialog");
        this.mDialog.dismiss();
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public final boolean isShowing() {
        return this.mDialog.isShowing();
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public final void show() {
        try {
            if (this.mDialog != null) {
                dismiss();
                this.mDialog.show();
            }
        } catch (WindowManager.BadTokenException unused) {
            this.mLogWrapper.m103v("DataConnectionErrorDialog");
        }
    }
}
