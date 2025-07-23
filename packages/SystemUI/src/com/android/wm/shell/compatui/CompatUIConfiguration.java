package com.android.wm.shell.compatui;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.DeviceConfig;
import com.android.systemui.R;
import com.android.wm.shell.common.ShellExecutor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CompatUIConfiguration implements DeviceConfig.OnPropertiesChangedListener {
    public final SharedPreferences mCompatUISharedPreferences;
    public final int mHideSizeCompatRestartButtonTolerance;
    public boolean mIsLetterboxRestartDialogAllowed;
    public final boolean mIsRestartDialogEnabled;
    public boolean mIsRestartDialogOverrideEnabled;
    public final SharedPreferences mLetterboxEduSharedPreferences;

    public CompatUIConfiguration(Context context, ShellExecutor shellExecutor) {
        this.mIsRestartDialogEnabled = context.getResources().getBoolean(R.bool.config_letterboxIsRestartDialogEnabled);
        context.getResources().getBoolean(R.bool.config_letterboxIsReachabilityEducationEnabled);
        int integer = context.getResources().getInteger(R.integer.config_letterboxRestartButtonHideTolerance);
        this.mHideSizeCompatRestartButtonTolerance = (integer < 0 || integer > 100) ? 100 : integer;
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
