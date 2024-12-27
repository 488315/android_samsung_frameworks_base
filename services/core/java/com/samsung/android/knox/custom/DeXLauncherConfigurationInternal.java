package com.samsung.android.knox.custom;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;

public final class DeXLauncherConfigurationInternal {
    public static final String ALPHABETIC_GRID = "alphabetical_order";
    public static final Uri CONTENT_URI =
            Uri.parse("content://com.sec.android.app.desktoplauncher.settings");
    public static final String CUSTOM_GRID = "custom_order";
    public static final String KEY_COMPONENT_COMPONENTNAME = "component";
    public static final String KEY_COORDINATION_POSITION_POINT = "coordination_position";
    public static final String KEY_HOME_URL = "shortcut_uri";
    public static final String KEY_ICON_RESOURCE = "shortcut_resource";
    public static final String KEY_INVOCATION_RESULT_INT = "invocation_result";
    public static final String KEY_OPTION_ORDER = "option_order";
    public static final String KEY_SHORTCUT_TITLE = "shortcut_title";
    public static final String METHOD_ADD_SHORTCUT = "add_shortcut";
    public static final String METHOD_ADD_URL_SHORTCUT = "add_uri_shortcut";
    public static final String METHOD_CHANGE_ORDER = "change_order";
    public static final String METHOD_GET_ORDER = "get_order";
    public static final String METHOD_MAKE_EMPTY_POSITION = "make_empty_position";
    public static final String METHOD_REMOVE_SHORTCUT = "remove_shortcut";
    public static final String METHOD_REMOVE_URL_SHORTCUT = "remove_uri_shortcut";
    public static final int RESULT_ACCESS_DENIED = -100;
    public static final int RESULT_ALREADY_EMPTY = -6;
    public static final int RESULT_FAILURE = -2;
    public static final int RESULT_NOT_FOUND = -3;
    public static final int RESULT_NOT_SUPPORTED = -1;
    public static final int RESULT_PAGE_FULL = -5;
    public static final int RESULT_PARAM_ERROR = -4;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_SUPPORTED = 0;
    public static final String TYPE_GRID = "type_order";
    public final Context mContext;

    public DeXLauncherConfigurationInternal(Context context) {
        this.mContext = context;
    }

    public final int addShortcut(Point point, ComponentName componentName) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("coordination_position", point);
        bundle.putParcelable("component", componentName);
        return this.mContext
                .getContentResolver()
                .call(CONTENT_URI, "add_shortcut", (String) null, bundle)
                .getInt("invocation_result", -2);
    }

    public final int addURLShortcut(
            Point point, String str, String str2, String str3, ComponentName componentName) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("coordination_position", point);
        bundle.putString(KEY_SHORTCUT_TITLE, str);
        bundle.putString(KEY_HOME_URL, str2);
        bundle.putString(KEY_ICON_RESOURCE, str3);
        bundle.putParcelable("component", componentName);
        return this.mContext
                .getContentResolver()
                .call(CONTENT_URI, METHOD_ADD_URL_SHORTCUT, (String) null, bundle)
                .getInt("invocation_result", -2);
    }

    public final int changeOrder(String str) {
        return this.mContext
                .getContentResolver()
                .call(
                        CONTENT_URI,
                        METHOD_CHANGE_ORDER,
                        (String) null,
                        AccountManagerService$$ExternalSyntheticOutline0.m142m(
                                KEY_OPTION_ORDER, str))
                .getInt("invocation_result", -2);
    }

    public final int getOrder() {
        return this.mContext
                .getContentResolver()
                .call(CONTENT_URI, METHOD_GET_ORDER, (String) null, new Bundle())
                .getInt("invocation_result", -2);
    }

    public final int makeEmptyPosition(Point point) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("coordination_position", point);
        return this.mContext
                .getContentResolver()
                .call(CONTENT_URI, "make_empty_position", (String) null, bundle)
                .getInt("invocation_result", -2);
    }

    public final int removeShortcut(ComponentName componentName) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("component", componentName);
        return this.mContext
                .getContentResolver()
                .call(CONTENT_URI, "remove_shortcut", (String) null, bundle)
                .getInt("invocation_result", -2);
    }

    public final int removeURLShortcut(String str, ComponentName componentName) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_HOME_URL, str);
        bundle.putParcelable("component", componentName);
        return this.mContext
                .getContentResolver()
                .call(CONTENT_URI, METHOD_REMOVE_URL_SHORTCUT, (String) null, bundle)
                .getInt("invocation_result", -2);
    }
}
