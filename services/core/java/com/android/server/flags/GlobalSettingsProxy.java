package com.android.server.flags;

import android.content.ContentResolver;

public final class GlobalSettingsProxy {
    public final ContentResolver mContentResolver;

    public GlobalSettingsProxy(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }
}
