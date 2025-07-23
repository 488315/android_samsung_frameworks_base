package com.android.systemui.qs.tiles.dialog;

import android.content.Intent;
import android.view.View;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
