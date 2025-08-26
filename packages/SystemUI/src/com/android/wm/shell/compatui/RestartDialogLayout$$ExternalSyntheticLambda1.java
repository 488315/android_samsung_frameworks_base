package com.android.wm.shell.compatui;

import android.view.View;
import android.widget.CheckBox;

/* loaded from: classes3.dex */
public final /* synthetic */ class RestartDialogLayout$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RestartDialogLayout$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                int i2 = RestartDialogLayout.$r8$clinit;
                ((CheckBox) obj).performClick();
                break;
            default:
                int i3 = RestartDialogLayout.$r8$clinit;
                ((RestartDialogWindowManager$$ExternalSyntheticLambda0) obj).run();
                break;
        }
    }
}
