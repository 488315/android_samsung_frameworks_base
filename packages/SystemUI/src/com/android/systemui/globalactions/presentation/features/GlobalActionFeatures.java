package com.android.systemui.globalactions.presentation.features;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.globalactions.presentation.features.Features;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SettingsWrapper;
import com.samsung.android.globalactions.util.SystemPropertiesWrapper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GlobalActionFeatures implements Features {
    public static final String VALUE_SUB_DISPLAY_POLICY;
    public final Context mContext;
    public final LogWrapper mLogWrapper;
    public final SettingsWrapper mSettingsWrapper;
    public final SystemPropertiesWrapper mSystemPropertiesWrapper;

    static {
        VALUE_SUB_DISPLAY_POLICY = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
    }

    public GlobalActionFeatures(Context context, SettingsWrapper settingsWrapper, SystemPropertiesWrapper systemPropertiesWrapper, LogWrapper logWrapper) {
        this.mContext = context;
        this.mSettingsWrapper = settingsWrapper;
        this.mSystemPropertiesWrapper = systemPropertiesWrapper;
        this.mLogWrapper = logWrapper;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a4, code lost:
    
        if (r0.charAt(10) != '9') goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x011c, code lost:
    
        if (com.android.systemui.globalactions.presentation.features.GlobalActionFeatures.VALUE_SUB_DISPLAY_POLICY.contains("LARGESCREEN") == false) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isEnabled(java.lang.String r7) {
        /*
            Method dump skipped, instructions count: 362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.globalactions.presentation.features.GlobalActionFeatures.isEnabled(java.lang.String):boolean");
    }
}
