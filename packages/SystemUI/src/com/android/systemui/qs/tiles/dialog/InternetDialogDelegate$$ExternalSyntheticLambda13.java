package com.android.systemui.qs.tiles.dialog;

import android.content.Intent;
import android.view.View;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class InternetDialogDelegate$$ExternalSyntheticLambda13 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InternetDialogDelegate$$ExternalSyntheticLambda13(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((SystemUIDialog) obj).dismiss();
                break;
            default:
                InternetDialogController internetDialogController = (InternetDialogController) obj;
                internetDialogController.getClass();
                Intent intent = new Intent("android.settings.WIFI_SCANNING_SETTINGS");
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                internetDialogController.startActivity(intent, view);
                break;
        }
    }
}
