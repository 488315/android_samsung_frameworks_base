package com.android.systemui.shared.plugins;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PluginPrefs {
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
