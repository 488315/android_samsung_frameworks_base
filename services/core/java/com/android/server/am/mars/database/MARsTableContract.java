package com.android.server.am.mars.database;

import android.net.Uri;

/* loaded from: classes.dex */
public abstract class MARsTableContract {
  public static final Uri MARS_POLICY_DEFINITION =
      Uri.parse("content://com.samsung.android.sm.mars/MARs_Policy");
  public static final Uri MARS_POLICY_AUTORUN_PARAMETER_DEFINITION =
      Uri.parse("content://com.samsung.android.sm.mars/MARs_AutoRunParameter");
  public static final Uri MARS_ADJUST_TARGET_EXCLUDE_PACKAGE =
      Uri.parse("content://com.samsung.android.sm.mars/MARs_ExcludeTarget");
  public static final Uri MARS_ADJUST_TARGET_CURRENT_IMPORTANT =
      Uri.parse("content://com.samsung.android.sm.mars/MARs_IsCurrentImportant");
  public static final Uri MARS_ADJUST_RESTRICTION =
      Uri.parse("content://com.samsung.android.sm.mars/MARs_AdjustRestriction");
  public static final Uri MARS_SETTINGS_URI =
      Uri.parse("content://com.samsung.android.sm.mars/MARs_Settings");
  public static final Uri SCPM_AUTHORITY_URI = Uri.parse("content://com.samsung.android.sm.dcapi");
}
