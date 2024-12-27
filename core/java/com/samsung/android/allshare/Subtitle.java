package com.samsung.android.allshare;

import android.net.Uri;

public abstract class Subtitle {
    public abstract String getType();

    public abstract Uri getUri();

    protected Subtitle() {}
}
