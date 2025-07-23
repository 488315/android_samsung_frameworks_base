package com.google.android.material.appbar.model.view;

import android.view.View;
import com.google.android.material.appbar.model.AppBarModel;
import com.google.android.material.appbar.model.ButtonModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class SuggestAppBarView$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ SuggestAppBarView f$1;

    public /* synthetic */ SuggestAppBarView$$ExternalSyntheticLambda0(Object obj, SuggestAppBarView suggestAppBarView, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = suggestAppBarView;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                SuggestAppBarView.generateButton$lambda$9$lambda$8((ButtonModel) this.f$0, this.f$1, view);
                break;
            default:
                SuggestAppBarView.setCloseClickListener$lambda$4$lambda$3((AppBarModel.OnClickListener) this.f$0, this.f$1, view);
                break;
        }
    }
}
