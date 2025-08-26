package com.android.settingslib.devicestate;

import android.content.ContentResolver;

/* loaded from: classes.dex */
public class AndroidSecureSettings implements SecureSettings {
    public final ContentResolver mContentResolver;

    public AndroidSecureSettings(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }
}
