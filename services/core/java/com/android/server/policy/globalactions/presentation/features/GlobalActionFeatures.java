package com.android.server.policy.globalactions.presentation.features;

import android.R;
import android.content.Context;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.globalactions.presentation.features.Features;
import com.samsung.android.globalactions.util.SettingsWrapper;

/* loaded from: classes3.dex */
public class GlobalActionFeatures implements Features {
    public Context mContext;
    public final SettingsWrapper mSettingsWrapper;

    public final boolean isDesktopModeSupported() {
        return true;
    }

    public final boolean isEffectSupported() {
        return true;
    }

    public GlobalActionFeatures(Context context, SettingsWrapper settingsWrapper) {
        this.mContext = context;
        this.mSettingsWrapper = settingsWrapper;
    }

    public final boolean isNaviBarSupported() {
        return this.mContext.getResources().getBoolean(R.bool.config_predictShowStartingSurface);
    }

    public final boolean isDataModeSupported() {
        return SemCscFeature.getInstance().getBoolean("CscFeature_Framework_SupportDataModeSwitchGlobalAction");
    }

    public final boolean isForceRestartMessageSupported() {
        return SemCscFeature.getInstance().getBoolean("CscFeature_Framework_SupportForceRestartGlobalAction");
    }

    public final boolean isRBMSupported() {
        String string = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigYuva");
        return string != null && string.contains("powerplanning") && string.contains("reserve");
    }

    public boolean isEnabled(String str) {
        if (str.equals("SF_EFFECT")) {
            return isEffectSupported();
        }
        if (str.equals("NAV_BAR")) {
            return isNaviBarSupported();
        }
        if (str.equals("DESKTOP_MODE")) {
            return isDesktopModeSupported();
        }
        if (str.equals("DATA_MODE")) {
            return isDataModeSupported();
        }
        if (str.equals("LOCKDOWN_MODE")) {
            return true;
        }
        if (str.equals("FORCE_RESTART_MESSAGE")) {
            return isForceRestartMessageSupported();
        }
        if (str.equals("RESERVE_BATTERY_MODE")) {
            return isRBMSupported();
        }
        return false;
    }
}
