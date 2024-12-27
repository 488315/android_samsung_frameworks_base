package com.android.server.am;

import android.content.DialogInterface;
import android.util.Log;

public final /* synthetic */ class AppNotRespondingDialog$$ExternalSyntheticLambda1
        implements DialogInterface.OnDismissListener {
    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        Log.i("GATE", "<GATE-M>APP_ANR:ANR dialog has been cleared</GATE-M>");
    }
}
