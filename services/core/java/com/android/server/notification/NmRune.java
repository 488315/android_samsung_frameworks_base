package com.android.server.notification;

import android.os.Build;
import android.os.Debug;
import android.os.SystemProperties;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class NmRune {
    public static final boolean NM_ETC_LOG_DEBUG;
    public static final boolean NM_POLICY_VIB_PICKER_CONCEPT;
    public static final boolean NM_SUPPORT_CLEAR_COVER_NOTI_LIST;
    public static final boolean NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE;
    public static final boolean NM_SUPPORT_NOTIFICATION_INSIGNIFICANT;
    public static final boolean NM_SUPPORT_PUSH_SERVICE;
    public static final boolean NM_SUPPORT_SUB_DISPLAY_EDGE_LIGHTING;

    static {
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY");
        NM_ETC_LOG_DEBUG = Debug.semIsProductDev();
        boolean z = true;
        NM_POLICY_VIB_PICKER_CONCEPT = Build.VERSION.SEM_PLATFORM_INT >= 120100;
        NM_SUPPORT_SUB_DISPLAY_EDGE_LIGHTING =
                string.contains("COVER") && string.contains("LARGESCREEN");
        NM_SUPPORT_CLEAR_COVER_NOTI_LIST = string.contains("VIRTUAL_DISPLAY");
        NM_SUPPORT_PUSH_SERVICE =
                SemCscFeature.getInstance()
                        .getString("CscFeature_SetupWizard_ConfigStepSequenceType")
                        .contains("service_tnc");
        NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_SHOW_CONTENT_WHEN_UNLOCKED")
                        .contains("support");
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_COMMON_CONFIG_AI_VERSION")
                        < 20251
                && !SystemProperties.getBoolean(
                        "persist.sys.fflag.override.settings_enable_sec_notification_highlight",
                        false)) {
            z = false;
        }
        NM_SUPPORT_NOTIFICATION_INSIGNIFICANT = z;
    }
}
