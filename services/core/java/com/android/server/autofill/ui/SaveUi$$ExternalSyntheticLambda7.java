package com.android.server.autofill.ui;

import android.service.autofill.InternalOnClickAction;
import android.service.autofill.SaveInfo;
import android.util.Slog;
import android.view.View;
import android.view.ViewGroup;
import com.android.server.autofill.Helper;
import com.android.server.autofill.ui.SaveUi;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SaveUi$$ExternalSyntheticLambda7 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SaveUi$$ExternalSyntheticLambda7(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                InternalOnClickAction internalOnClickAction = (InternalOnClickAction) this.f$0;
                ViewGroup viewGroup = (ViewGroup) this.f$1;
                if (Helper.sVerbose) {
                    Slog.v("SaveUi", "Applying " + internalOnClickAction + " after " + view + " was clicked");
                }
                internalOnClickAction.onClick(viewGroup);
                break;
            default:
                ((SaveUi.AnonymousClass2) this.f$0).this$0.mListener.onCancel(((SaveInfo) this.f$1).semGetNegativeSecondActionListener());
                break;
        }
    }
}
