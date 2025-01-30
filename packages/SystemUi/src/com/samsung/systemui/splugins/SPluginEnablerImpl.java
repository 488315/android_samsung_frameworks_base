package com.samsung.systemui.splugins;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SPluginEnablerImpl implements SPluginEnabler {
    private static final String CRASH_DISABLED_PLUGINS_PREF_FILE = "auto_disabled_splugins_prefs";
    private final SharedPreferences mAutoDisabledPrefs;
    private PackageManager mPm;

    public SPluginEnablerImpl(Context context) {
        this(context, context.getPackageManager());
    }

    @Override // com.samsung.systemui.splugins.SPluginEnabler
    public int getDisableReason(ComponentName componentName) {
        if (isEnabled(componentName)) {
            return 0;
        }
        return this.mAutoDisabledPrefs.getInt(componentName.flattenToString(), 1);
    }

    @Override // com.samsung.systemui.splugins.SPluginEnabler
    public boolean isEnabled(ComponentName componentName) {
        return this.mPm.getComponentEnabledSetting(componentName) != 2;
    }

    @Override // com.samsung.systemui.splugins.SPluginEnabler
    public void setDisabled(ComponentName componentName, int i) {
        boolean z = i == 0;
        this.mPm.setComponentEnabledSetting(componentName, z ? 1 : 2, 1);
        if (z) {
            this.mAutoDisabledPrefs.edit().remove(componentName.flattenToString()).apply();
        } else {
            this.mAutoDisabledPrefs.edit().putInt(componentName.flattenToString(), i).apply();
        }
    }

    @Override // com.samsung.systemui.splugins.SPluginEnabler
    public void setEnabled(ComponentName componentName) {
        setDisabled(componentName, 0);
    }

    public SPluginEnablerImpl(Context context, PackageManager packageManager) {
        this.mAutoDisabledPrefs = context.getSharedPreferences(CRASH_DISABLED_PLUGINS_PREF_FILE, 0);
        this.mPm = packageManager;
    }
}
