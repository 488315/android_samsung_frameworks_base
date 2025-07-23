package com.android.systemui.screenshot.appclips;

import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ListPopupWindow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppClipsActivity$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AppClipsActivity$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                AppClipsActivity.$r8$lambda$stcjMVf1JwQ0vVO3T941Qunv0VY((AppClipsActivity) obj, view);
                break;
            default:
                PackageManager.ApplicationInfoFlags applicationInfoFlags = AppClipsActivity.APPLICATION_INFO_FLAGS;
                ((ListPopupWindow) obj).show();
                break;
        }
    }
}
