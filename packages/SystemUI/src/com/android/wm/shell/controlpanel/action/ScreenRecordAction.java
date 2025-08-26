package com.android.wm.shell.controlpanel.action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.widget.Toast;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;

/* loaded from: classes3.dex */
public class ScreenRecordAction extends MenuActionType {
    public final Context mContext;

    private ScreenRecordAction(Context context) {
        this.mContext = context;
    }

    public static ScreenRecordAction createAction(FlexPanelActivity flexPanelActivity) {
        return new ScreenRecordAction(flexPanelActivity);
    }

    @Override // com.android.wm.shell.controlpanel.action.MenuActionType
    public final void doControlAction(String str, FlexPanelActivity flexPanelActivity) {
        Context context = this.mContext;
        String str2 = ControlPanelUtils.TALKBACK_SERVICE;
        int i = context.getContentResolver().call(Uri.parse("content://com.samsung.android.app.screenrecorder.provider/status"), "getScreenRecorderStatus()", (String) null, (Bundle) null).getInt("screen_recorder_status");
        if (i == -1) {
            return;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "takeScreenRecord: Record screen status", "ControlPanelUtils");
        if (i != 0) {
            Toast.makeText(context, R.string.flex_panel_screen_recording_already_running, 0).show();
            return;
        }
        Intent intent = new Intent();
        intent.setClassName("com.samsung.android.app.smartcapture", "com.samsung.android.app.smartcapture.screenrecorder.ScreenRecorderService");
        intent.setAction("com.samsung.android.app.screenrecorder.ACTION_START");
        context.startServiceAsUser(intent, UserHandle.CURRENT);
    }
}
