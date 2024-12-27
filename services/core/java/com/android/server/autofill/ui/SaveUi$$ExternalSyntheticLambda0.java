package com.android.server.autofill.ui;

import android.view.View;

public final /* synthetic */ class SaveUi$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SaveUi$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((SaveUi) obj).mListener.onSave();
                break;
            default:
                ((SaveUi.AnonymousClass2) obj).this$0.mListener.onSave();
                break;
        }
    }
}
