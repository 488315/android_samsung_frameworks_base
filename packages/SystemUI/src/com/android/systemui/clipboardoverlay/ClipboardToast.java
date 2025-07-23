package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.widget.Toast;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ClipboardToast extends Toast.Callback {
    public final Context mContext;
    public Toast mCopiedToast;

    public ClipboardToast(Context context) {
        this.mContext = context;
    }

    @Override // android.widget.Toast.Callback
    public final void onToastHidden() {
        super.onToastHidden();
        this.mCopiedToast = null;
    }
}
