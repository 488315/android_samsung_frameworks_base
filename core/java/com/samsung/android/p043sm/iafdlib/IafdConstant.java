package com.samsung.android.p043sm.iafdlib;

import android.net.Uri;

/* loaded from: classes5.dex */
public class IafdConstant {
    public static final int CAN_NOT_FOUND = 0;
    public static final String DC_API_AUTHORITY = "com.samsung.android.sm.dcapi";
    public static final int FLAG_UPDATE_FAIL = -1;
    public static final Uri IAFD_STUB_EX_CHECK_UPDATE_API = Uri.parse("content://com.samsung.android.sm.dcapi").buildUpon().appendPath("iafdCheckUpdate").build();
    public static final String KEY_APP_NAME = "app_name";
    public static final String KEY_ERROR_COMPONENT = "component";
    public static final String KEY_ERROR_STACK = "error_stack";
    public static final String KEY_ERROR_TYPE = "type";
    public static final String KEY_PACKAGE_NAME = "pkgName";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_VERSION_CODE = "versionCode";
    public static final String KEY_VERSION_NAME = "version_name";
    public static final String METHOD_CHECK_UPDATE = "checkUpdate";
    public static final String METHOD_REPORT_TO_SERVER = "reportToServer";
    public static final int NO_UPDATE = 1;
    public static final int UPDATE_AVAILABLE = 2;
}
