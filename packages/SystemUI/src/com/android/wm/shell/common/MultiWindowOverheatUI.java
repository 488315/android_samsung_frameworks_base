package com.android.wm.shell.common;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.popup.util.PopupUIUtil;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.util.List;

/* loaded from: classes3.dex */
public class MultiWindowOverheatUI extends AlertDialog {
    public final AnonymousClass1 mDismissReceiver;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.common.MultiWindowOverheatUI$1] */
    private MultiWindowOverheatUI(Context context) {
        super(context, R.style.Theme_DeviceDefault_MultiWindowOverheatDialog);
        this.mDismissReceiver = new BroadcastReceiver() { // from class: com.android.wm.shell.common.MultiWindowOverheatUI.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                MultiWindowOverheatUI.this.dismiss();
            }
        };
        setMessage(context.getResources().getString(CoreRune.IS_TABLET_DEVICE ? R.string.multiwindow_overheat_warning_dialog_body_tablet : R.string.multiwindow_overheat_warning_dialog_body_phone));
        setButton(-1, context.getResources().getString(R.string.dnd_yes), (DialogInterface.OnClickListener) null);
        getWindow().setType(2008);
    }

    public static boolean showIfNeeded(Context context) {
        List mWDisableRequesters = MultiWindowManager.getInstance().getMWDisableRequesters();
        if (mWDisableRequesters != null && !mWDisableRequesters.contains(PopupUIUtil.ACTION_MULTI_WINDOW_ENABLE_VALID_REQUESTER)) {
            Log.w("MWOverheatDialog", "requester is not SSRM");
            return false;
        }
        Log.w("MWOverheatDialog", "show mw overheat dialog");
        new MultiWindowOverheatUI(context).show();
        return true;
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        getContext().registerReceiver(this.mDismissReceiver, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_OFF", PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), 2);
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        getContext().unregisterReceiver(this.mDismissReceiver);
    }
}
