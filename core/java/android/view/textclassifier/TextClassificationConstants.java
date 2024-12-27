package android.view.textclassifier;

import android.provider.DeviceConfig;

import com.android.internal.util.IndentingPrintWriter;

/* loaded from: classes4.dex */
public final class TextClassificationConstants {
    static final String GENERATE_LINKS_MAX_TEXT_LENGTH = "generate_links_max_text_length";
    private static final int GENERATE_LINKS_MAX_TEXT_LENGTH_DEFAULT = 100000;
    static final String LOCAL_TEXT_CLASSIFIER_ENABLED = "local_textclassifier_enabled";
    private static final boolean LOCAL_TEXT_CLASSIFIER_ENABLED_DEFAULT = true;
    private static final String MODEL_DARK_LAUNCH_ENABLED = "model_dark_launch_enabled";
    private static final boolean MODEL_DARK_LAUNCH_ENABLED_DEFAULT = false;
    private static final String SMART_LINKIFY_ENABLED = "smart_linkify_enabled";
    private static final boolean SMART_LINKIFY_ENABLED_DEFAULT = true;
    private static final String SMART_SELECTION_ENABLED = "smart_selection_enabled";
    private static final boolean SMART_SELECTION_ENABLED_DEFAULT = true;
    private static final String SMART_SELECTION_TRIM_DELTA = "smart_selection_trim_delta";
    private static final int SMART_SELECTION_TRIM_DELTA_DEFAULT = 120;
    private static final String SMART_SELECT_ANIMATION_ENABLED = "smart_select_animation_enabled";
    private static final boolean SMART_SELECT_ANIMATION_ENABLED_DEFAULT = true;
    private static final String SMART_TEXT_SHARE_ENABLED = "smart_text_share_enabled";
    private static final boolean SMART_TEXT_SHARE_ENABLED_DEFAULT = true;
    static final String SYSTEM_TEXT_CLASSIFIER_API_TIMEOUT_IN_SECOND =
            "system_textclassifier_api_timeout_in_second";
    private static final long SYSTEM_TEXT_CLASSIFIER_API_TIMEOUT_IN_SECOND_DEFAULT = 60;
    static final String SYSTEM_TEXT_CLASSIFIER_ENABLED = "system_textclassifier_enabled";
    private static final boolean SYSTEM_TEXT_CLASSIFIER_ENABLED_DEFAULT = true;
    static final String TEXT_CLASSIFIER_SERVICE_PACKAGE_OVERRIDE =
            "textclassifier_service_package_override";
    private static int sGenerateLinksMaxTextLength;
    private static boolean sLocalTextClassifierEnabled;
    private static volatile boolean sMemoizedValuesInitialized;
    private static boolean sModelDarkLaunchEnabled;
    private static boolean sSmartLinkifyEnabled;
    private static boolean sSmartSelectAnimationEnabled;
    private static boolean sSmartSelectionEnabled;
    private static int sSmartSelectionTrimDelta;
    private static boolean sSmartTextShareEnabled;
    private static long sSystemTextClassifierApiTimeoutInSecond;
    private static boolean sSystemTextClassifierEnabled;
    private static final String DEFAULT_TEXT_CLASSIFIER_SERVICE_PACKAGE_OVERRIDE = null;
    private static final Object sLock = new Object();

    private static void ensureMemoizedValues() {
        if (sMemoizedValuesInitialized) {
            return;
        }
        synchronized (sLock) {
            if (sMemoizedValuesInitialized) {
                return;
            }
            DeviceConfig.Properties properties =
                    DeviceConfig.getProperties("textclassifier", new String[0]);
            sLocalTextClassifierEnabled =
                    properties.getBoolean(LOCAL_TEXT_CLASSIFIER_ENABLED, true);
            sModelDarkLaunchEnabled = properties.getBoolean(MODEL_DARK_LAUNCH_ENABLED, false);
            sSmartSelectionEnabled = properties.getBoolean(SMART_SELECTION_ENABLED, true);
            sSmartTextShareEnabled = properties.getBoolean(SMART_TEXT_SHARE_ENABLED, true);
            sSmartLinkifyEnabled = properties.getBoolean(SMART_LINKIFY_ENABLED, true);
            sSmartSelectAnimationEnabled =
                    properties.getBoolean(SMART_SELECT_ANIMATION_ENABLED, true);
            sGenerateLinksMaxTextLength = properties.getInt(GENERATE_LINKS_MAX_TEXT_LENGTH, 100000);
            sSystemTextClassifierApiTimeoutInSecond =
                    properties.getLong(
                            SYSTEM_TEXT_CLASSIFIER_API_TIMEOUT_IN_SECOND,
                            SYSTEM_TEXT_CLASSIFIER_API_TIMEOUT_IN_SECOND_DEFAULT);
            sSmartSelectionTrimDelta = properties.getInt(SMART_SELECTION_TRIM_DELTA, 120);
            sMemoizedValuesInitialized = true;
        }
    }

    public static void resetMemoizedValues() {
        sMemoizedValuesInitialized = false;
    }

    public String getTextClassifierServicePackageOverride() {
        return DeviceConfig.getString(
                "textclassifier",
                TEXT_CLASSIFIER_SERVICE_PACKAGE_OVERRIDE,
                DEFAULT_TEXT_CLASSIFIER_SERVICE_PACKAGE_OVERRIDE);
    }

    public boolean isLocalTextClassifierEnabled() {
        ensureMemoizedValues();
        return sLocalTextClassifierEnabled;
    }

    public boolean isSystemTextClassifierEnabled() {
        return DeviceConfig.getBoolean("textclassifier", SYSTEM_TEXT_CLASSIFIER_ENABLED, true);
    }

    public boolean isModelDarkLaunchEnabled() {
        ensureMemoizedValues();
        return sModelDarkLaunchEnabled;
    }

    public boolean isSmartSelectionEnabled() {
        ensureMemoizedValues();
        return sSmartSelectionEnabled;
    }

    public boolean isSmartTextShareEnabled() {
        ensureMemoizedValues();
        return sSmartTextShareEnabled;
    }

    public boolean isSmartLinkifyEnabled() {
        ensureMemoizedValues();
        return sSmartLinkifyEnabled;
    }

    public boolean isSmartSelectionAnimationEnabled() {
        ensureMemoizedValues();
        return sSmartSelectAnimationEnabled;
    }

    public int getGenerateLinksMaxTextLength() {
        ensureMemoizedValues();
        return sGenerateLinksMaxTextLength;
    }

    public long getSystemTextClassifierApiTimeoutInSecond() {
        ensureMemoizedValues();
        return sSystemTextClassifierApiTimeoutInSecond;
    }

    public int getSmartSelectionTrimDelta() {
        ensureMemoizedValues();
        return sSmartSelectionTrimDelta;
    }

    void dump(IndentingPrintWriter pw) {
        pw.println("TextClassificationConstants:");
        pw.increaseIndent();
        pw.print(GENERATE_LINKS_MAX_TEXT_LENGTH, Integer.valueOf(getGenerateLinksMaxTextLength()))
                .println();
        pw.print(LOCAL_TEXT_CLASSIFIER_ENABLED, Boolean.valueOf(isLocalTextClassifierEnabled()))
                .println();
        pw.print(MODEL_DARK_LAUNCH_ENABLED, Boolean.valueOf(isModelDarkLaunchEnabled())).println();
        pw.print(SMART_LINKIFY_ENABLED, Boolean.valueOf(isSmartLinkifyEnabled())).println();
        pw.print(
                        SMART_SELECT_ANIMATION_ENABLED,
                        Boolean.valueOf(isSmartSelectionAnimationEnabled()))
                .println();
        pw.print(SMART_SELECTION_ENABLED, Boolean.valueOf(isSmartSelectionEnabled())).println();
        pw.print(SMART_TEXT_SHARE_ENABLED, Boolean.valueOf(isSmartTextShareEnabled())).println();
        pw.print(SYSTEM_TEXT_CLASSIFIER_ENABLED, Boolean.valueOf(isSystemTextClassifierEnabled()))
                .println();
        pw.print(
                        TEXT_CLASSIFIER_SERVICE_PACKAGE_OVERRIDE,
                        getTextClassifierServicePackageOverride())
                .println();
        pw.print(
                        SYSTEM_TEXT_CLASSIFIER_API_TIMEOUT_IN_SECOND,
                        Long.valueOf(getSystemTextClassifierApiTimeoutInSecond()))
                .println();
        pw.print(SMART_SELECTION_TRIM_DELTA, Integer.valueOf(getSmartSelectionTrimDelta()))
                .println();
        pw.decreaseIndent();
    }
}
