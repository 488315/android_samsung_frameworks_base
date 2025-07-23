package com.android.systemui.popup.util;

import android.content.Context;
import android.widget.Toast;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PopupUIToastWrapper {
    private Context mContext;

    public PopupUIToastWrapper(Context context) {
        this.mContext = context;
    }

    public void makeToast(int i) {
        Context context = this.mContext;
        Toast.makeText(context, context.getResources().getString(i), 0).show();
    }

    public void makeToast(String str) {
        Toast.makeText(this.mContext, str, 0).show();
    }
}
