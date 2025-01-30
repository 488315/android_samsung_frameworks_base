package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.widget.Toast;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClipboardToast extends Toast.Callback {
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
