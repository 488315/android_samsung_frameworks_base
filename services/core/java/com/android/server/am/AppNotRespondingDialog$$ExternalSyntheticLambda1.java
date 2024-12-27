package com.android.server.am;

import android.content.DialogInterface;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppNotRespondingDialog$$ExternalSyntheticLambda1 implements DialogInterface.OnDismissListener {
    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        Log.i("GATE", "<GATE-M>APP_ANR:ANR dialog has been cleared</GATE-M>");
    }
}
