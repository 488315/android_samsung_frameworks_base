package com.android.systemui.shared.plugins;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginPrefs {
    public final Set mPluginActions;
    public final SharedPreferences mSharedPrefs;

    public PluginPrefs(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("plugin_prefs", 0);
        this.mSharedPrefs = sharedPreferences;
        this.mPluginActions = new ArraySet(sharedPreferences.getStringSet("actions", null));
    }

    public final synchronized void addAction(String str) {
        if (((ArraySet) this.mPluginActions).add(str)) {
            this.mSharedPrefs.edit().putStringSet("actions", this.mPluginActions).apply();
        }
    }
}
