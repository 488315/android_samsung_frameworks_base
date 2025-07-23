package com.android.settingslib.devicestate;

import android.content.ContentResolver;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AndroidSecureSettings implements SecureSettings {
    public final ContentResolver mContentResolver;

    public AndroidSecureSettings(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }
}
