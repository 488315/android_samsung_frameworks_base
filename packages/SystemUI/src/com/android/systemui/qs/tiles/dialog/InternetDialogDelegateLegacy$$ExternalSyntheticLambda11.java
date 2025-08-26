package com.android.systemui.qs.tiles.dialog;

import android.content.Intent;
import android.view.View;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* loaded from: classes2.dex */
public final /* synthetic */ class InternetDialogDelegateLegacy$$ExternalSyntheticLambda11 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InternetDialogDelegateLegacy$$ExternalSyntheticLambda11(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                boolean z = InternetDialogDelegateLegacy.DEBUG;
                ((SystemUIDialog) obj).dismiss();
                break;
            default:
                InternetDetailsContentController internetDetailsContentController = (InternetDetailsContentController) obj;
                internetDetailsContentController.getClass();
                Intent intent = new Intent("android.settings.WIFI_SCANNING_SETTINGS");
                intent.addFlags(268435456);
                internetDetailsContentController.startActivity(intent, view);
                break;
        }
    }
}
