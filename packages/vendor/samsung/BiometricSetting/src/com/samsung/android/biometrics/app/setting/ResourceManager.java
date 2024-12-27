package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class ResourceManager {
    public final Context mContext;
    public final String mPackageName;
    public final Resources mResources;

    public ResourceManager(Context context, String str) {
        this.mContext = context;
        this.mPackageName = str;
        try {
            this.mResources = context.getPackageManager().getResourcesForApplication(str);
        } catch (Exception e) {
            FocusableWindow$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("ResourceManager: "), "BSS_ResourceManager");
        }
    }

    public final Drawable getDrawable(String str) {
        Resources resources;
        if (!TextUtils.isEmpty(str) && (resources = this.mResources) != null) {
            try {
                return resources.getDrawable(
                        resources.getIdentifier(str, "drawable", this.mPackageName),
                        this.mContext.getTheme());
            } catch (Exception e) {
                Log.e("BSS_ResourceManager", "ResourceManager.getDrawable : ", e);
            }
        }
        return null;
    }
}
