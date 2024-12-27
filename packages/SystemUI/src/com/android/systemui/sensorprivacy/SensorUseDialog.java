package com.android.systemui.sensorprivacy;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Window;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SensorUseDialog extends SystemUIDialog {
    public SensorUseDialog(Context context, int i, DialogInterface.OnClickListener onClickListener, DialogInterface.OnDismissListener onDismissListener) {
        super(context, R.style.Theme_SystemUI_Dialog_Alert);
        Window window = getWindow();
        Intrinsics.checkNotNull(window);
        window.addFlags(524288);
        Window window2 = getWindow();
        Intrinsics.checkNotNull(window2);
        window2.addSystemFlags(524288);
        LayoutInflater.from(context);
        setTitle(i != 1 ? i != 2 ? i != Integer.MAX_VALUE ? 0 : R.string.sec_sensor_privacy_start_use_camera_and_microphone_dialog_title : R.string.sec_sensor_privacy_start_use_camera_dialog_title : R.string.sec_sensor_privacy_start_use_microphone_dialog_title);
        setMessage(Html.fromHtml(context.getString(i != 1 ? i != 2 ? i != Integer.MAX_VALUE ? 0 : R.string.sec_sensor_privacy_start_use_camera_and_microphone_dialog_content : R.string.sec_sensor_privacy_start_use_camera_dialog_content : R.string.sec_sensor_privacy_start_use_microphone_dialog_content), 0));
        setButton(-1, context.getString(R.string.qs_sensor_privacy_dialog_turn_on), onClickListener);
        setButton(-2, context.getString(R.string.qs_sensor_privacy_dialog_cancel), onClickListener);
        setOnDismissListener(onDismissListener);
        setCancelable(false);
    }
}
