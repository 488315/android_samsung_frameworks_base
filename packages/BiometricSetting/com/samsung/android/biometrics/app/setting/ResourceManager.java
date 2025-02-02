package com.samsung.android.biometrics.app.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes.dex */
public final class ResourceManager {
    private Context mContext;
    private String mPackageName;
    private Resources mResources;

    public ResourceManager(Context context, String str) {
        this.mContext = context;
        this.mPackageName = str;
        try {
            this.mResources = context.getPackageManager().getResourcesForApplication(this.mPackageName);
        } catch (Exception e) {
            DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("ResourceManager: "), "BSS_ResourceManager");
        }
    }

    @SuppressLint({"UseCompatLoadingForDrawables"})
    public final Drawable getDrawable(String str) {
        Resources resources;
        if (TextUtils.isEmpty(str) || (resources = this.mResources) == null) {
            return null;
        }
        try {
            return resources.getDrawable(resources.getIdentifier(str, "drawable", this.mPackageName), this.mContext.getTheme());
        } catch (Exception e) {
            Log.e("BSS_ResourceManager", "ResourceManager.getDrawable : ", e);
            return null;
        }
    }

    public final CharSequence getString() {
        int identifier;
        if (TextUtils.isEmpty("sem_fingerprint_result_failed") || (identifier = this.mContext.getResources().getIdentifier("sem_fingerprint_result_failed", "string", "android")) <= 0) {
            return "";
        }
        try {
            return this.mContext.getText(identifier);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
