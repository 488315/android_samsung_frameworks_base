package com.android.systemui.popup.util;

import android.content.Context;
import android.widget.Toast;

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
