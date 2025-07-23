package com.android.systemui.shared.plugins;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PluginPrefs {
    public final Set mPluginActions;
    public final SharedPreferences mSharedPrefs;

    public PluginPrefs(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("plugin_prefs", 0);
        this.mSharedPrefs = sharedPreferences;
        this.mPluginActions = new ArraySet(sharedPreferences.getStringSet(SystemUIAnalytics.QPNE_VID_ACTIONS, null));
    }

    public final synchronized void addAction(String str) {
        if (((ArraySet) this.mPluginActions).add(str)) {
            this.mSharedPrefs.edit().putStringSet(SystemUIAnalytics.QPNE_VID_ACTIONS, this.mPluginActions).apply();
        }
    }
}
