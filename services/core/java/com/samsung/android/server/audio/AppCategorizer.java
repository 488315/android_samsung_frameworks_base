package com.samsung.android.server.audio;

import android.content.ContentValues;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.util.Hashtable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppCategorizer {
    public final Hashtable appList;
    public final AudioSettingsHelper mSettingsHelper;

    public AppCategorizer(AudioSettingsHelper audioSettingsHelper) {
        Hashtable hashtable = new Hashtable();
        this.appList = hashtable;
        this.mSettingsHelper = audioSettingsHelper;
        synchronized (hashtable) {
            hashtable.putAll(audioSettingsHelper.getPackageList());
        }
    }

    public final void putPackage(int i, String str) {
        if (this.appList.containsValue(str)) {
            return;
        }
        this.appList.put(Integer.valueOf(i), str);
        AudioSettingsHelper audioSettingsHelper = this.mSettingsHelper;
        audioSettingsHelper.getClass();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_uid", Integer.valueOf(i));
        contentValues.put("_package", str);
        audioSettingsHelper.set(
                contentValues,
                "selectedpkg",
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "_uid='", "'"));
    }
}
