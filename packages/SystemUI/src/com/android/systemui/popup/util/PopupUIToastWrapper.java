package com.android.systemui.popup.util;

import android.content.Context;
import android.widget.Toast;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
