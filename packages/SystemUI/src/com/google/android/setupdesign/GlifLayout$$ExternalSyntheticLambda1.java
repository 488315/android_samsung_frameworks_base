package com.google.android.setupdesign;

import android.app.Activity;
import android.view.View;
import com.google.android.setupcompat.util.Logger;

/* loaded from: classes4.dex */
public final /* synthetic */ class GlifLayout$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ Activity f$0;

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Activity activity = this.f$0;
        Logger logger = GlifLayout.LOG;
        activity.onBackPressed();
    }
}
