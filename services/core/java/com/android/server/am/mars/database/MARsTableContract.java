package com.android.server.am.mars.database;

import android.net.Uri;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class MARsTableContract {
    public static final Uri MARS_ADJUST_RESTRICTION;
    public static final Uri MARS_ADJUST_TARGET_CURRENT_IMPORTANT;
    public static final Uri MARS_ADJUST_TARGET_EXCLUDE_PACKAGE;
    public static final Uri MARS_POLICY_DEFINITION =
            Uri.parse("content://com.samsung.android.sm.mars/MARs_Policy");
    public static final Uri MARS_SETTINGS_URI;
    public static final Uri SCPM_AUTHORITY_URI;

    static {
        Uri.parse("content://com.samsung.android.sm.mars/MARs_AutoRunParameter");
        MARS_ADJUST_TARGET_EXCLUDE_PACKAGE =
                Uri.parse("content://com.samsung.android.sm.mars/MARs_ExcludeTarget");
        MARS_ADJUST_TARGET_CURRENT_IMPORTANT =
                Uri.parse("content://com.samsung.android.sm.mars/MARs_IsCurrentImportant");
        MARS_ADJUST_RESTRICTION =
                Uri.parse("content://com.samsung.android.sm.mars/MARs_AdjustRestriction");
        MARS_SETTINGS_URI = Uri.parse("content://com.samsung.android.sm.mars/MARs_Settings");
        SCPM_AUTHORITY_URI = Uri.parse("content://com.samsung.android.sm.dcapi");
    }
}
