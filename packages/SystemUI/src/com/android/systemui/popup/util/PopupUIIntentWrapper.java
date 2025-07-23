package com.android.systemui.popup.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
