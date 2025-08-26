package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.widget.Toast;

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
