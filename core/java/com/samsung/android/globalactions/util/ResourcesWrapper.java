package com.samsung.android.globalactions.util;

import android.content.res.Resources;

/* loaded from: classes6.dex */
public class ResourcesWrapper {
    Resources mResources;

    public ResourcesWrapper(Resources resources) {
        this.mResources = resources;
    }

    public String getString(int resID) {
        return this.mResources.getString(resID);
    }

    public String getString(int resID, Object... formatArgs) {
        return this.mResources.getString(resID, formatArgs);
    }
}
