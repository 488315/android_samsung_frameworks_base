package com.android.wm.shell.compatui;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.DeviceConfig;
import com.android.wm.shell.common.ShellExecutor;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CompatUIConfiguration implements DeviceConfig.OnPropertiesChangedListener {
    public final SharedPreferences mCompatUISharedPreferences;
    public boolean mIsLetterboxRestartDialogAllowed;
    public final boolean mIsRestartDialogEnabled;
    public boolean mIsRestartDialogOverrideEnabled;
    public final SharedPreferences mLetterboxEduSharedPreferences;

    public CompatUIConfiguration(Context context, ShellExecutor shellExecutor) {
        this.mIsRestartDialogEnabled = context.getResources().getBoolean(R.bool.config_letterboxIsRestartDialogEnabled);
        context.getResources().getBoolean(R.bool.config_letterboxIsReachabilityEducationEnabled);
        this.mIsLetterboxRestartDialogAllowed = DeviceConfig.getBoolean("window_manager", "enable_letterbox_restart_confirmation_dialog", true);
        DeviceConfig.getBoolean("window_manager", "enable_letterbox_education_for_reachability", true);
        DeviceConfig.addOnPropertiesChangedListener("app_compat", shellExecutor, this);
        this.mCompatUISharedPreferences = context.getSharedPreferences("dont_show_restart_dialog", 0);
        this.mLetterboxEduSharedPreferences = context.getSharedPreferences("has_seen_letterbox_education", 0);
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        if (properties.getKeyset().contains("enable_letterbox_restart_confirmation_dialog")) {
            this.mIsLetterboxRestartDialogAllowed = DeviceConfig.getBoolean("window_manager", "enable_letterbox_restart_confirmation_dialog", true);
        }
        if (properties.getKeyset().contains("enable_letterbox_education_for_reachability")) {
            DeviceConfig.getBoolean("window_manager", "enable_letterbox_education_for_reachability", true);
        }
    }
}
