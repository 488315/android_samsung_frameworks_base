package com.android.systemui.popup.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PopupUIIntentWrapper {
    public PopupUIIntentWrapper(Context context) {
    }

    public String getAction(Intent intent) {
        return intent.getAction();
    }

    public boolean getBooleanExtra(Intent intent, String str, boolean z) {
        return intent.getBooleanExtra(str, z);
    }

    public int getIntExtra(Intent intent, String str, int i) {
        return intent.getIntExtra(str, i);
    }

    public PendingIntent getParcelableExtra(Intent intent, String str) {
        return (PendingIntent) intent.getParcelableExtra(str);
    }

    public String getStringExtra(Intent intent, String str) {
        return intent.getStringExtra(str);
    }
}
