package com.android.settingslib.devicestate;

import android.content.ContentResolver;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AndroidSecureSettings implements SecureSettings {
    public final ContentResolver mContentResolver;

    public AndroidSecureSettings(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }
}
