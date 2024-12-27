package com.samsung.android.knox.custom;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Size;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LauncherConfigurationInternal {
    public static final String ARG_GETHOMEMODE_HOMEMODE = "home_mode";
    public static final Uri CONTENT_URI = Uri.parse("content://com.sec.android.app.launcher.settings/settings");
    public static final int FEATURE_SHORTCUT_FOR_EASYMODE = 1001;
    public static final int HOME_MODE_APPS = 3;
    public static final int HOME_MODE_EASY = 1;
    public static final int HOME_MODE_HOME_ONLY = 2;
    public static final String KEY_CELLDIMENSION_COLS_INT = "cols";
    public static final String KEY_CELLDIMENSION_ROWS_INT = "rows";
    public static final String KEY_COMPONENT_COMPONENTNAME = "component";
    public static final String KEY_COORDINATION_POSITION_POINT = "coordination_position";
    public static final String KEY_COORDINATION_SIZE_POINT = "coordination_size";
    public static final String KEY_FEATURE_INT = "feature";
    public static final String KEY_HOMEMODE_STRING = "home_mode";
    public static final String KEY_INDEX_INT = "index";
    public static final String KEY_INVOCATION_RESULT_INT = "invocation_result";
    public static final String KEY_ITEMCOUNT_INT = "itemcount";
    public static final String KEY_PAGE_INT = "page";
    public static final String KEY_STATE_BOOLEAN = "state";
    public static final String KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN = "visibility";
    public static final String METHOD_ADD_SHORTCUT = "add_shortcut";
    public static final String METHOD_ADD_WIDGET = "add_widget";
    public static final String METHOD_DISABLE_APPS_BUTTON = "disable_apps_button";
    public static final String METHOD_ENABLE_APPS_BUTTON = "enable_apps_button";
    public static final String METHOD_GET_APPS_BUTTON_STATE = "get_apps_button_state";
    public static final String METHOD_GET_APPS_CELL_DIMENSION = "get_apps_cell_dimension";
    public static final String METHOD_GET_HOME_CELL_DIMENSION = "get_home_cell_dimension";
    public static final String METHOD_GET_HOME_MODE = "get_home_mode";
    public static final String METHOD_GET_HOTSEAT_ITEM = "get_hotseat_item";
    public static final String METHOD_GET_HOTSEAT_ITEM_COUNT = "get_hotseat_item_count";
    public static final String METHOD_GET_HOTSEAT_MAXITEM_COUNT = "get_hotseat_maxitem_count";
    public static final String METHOD_GET_SUPPLEMENT_SERVICE_PAGE_VISIBILITY = "get_supplement_service_page_visibility";
    public static final String METHOD_IS_SUPPORTED = "is_supported";
    public static final String METHOD_MAKE_EMPTY_POSITION = "make_empty_position";
    public static final String METHOD_REMOVE_HOTSEAT_ITEM = "remove_hotseat_item";
    public static final String METHOD_REMOVE_PAGE_FROM_HOME = "remove_page_from_home";
    public static final String METHOD_REMOVE_SHORTCUT = "remove_shortcut";
    public static final String METHOD_REMOVE_WIDGET = "remove_widget";
    public static final String METHOD_SET_HOTSEAT_ITEM = "add_hotseat_item";
    public static final String METHOD_SET_SUPPLEMENT_SERVICE_PAGE_VISIBILITY = "set_supplement_service_page_visibility";
    public static final String METHOD_SWITCH_HOME_MODE = "switch_home_mode";
    public static final int RESULT_ACCESS_DENIED = -100;
    public static final int RESULT_FAILURE = -2;
    public static final int RESULT_NOT_FOUND = -3;
    public static final int RESULT_NOT_SUPPORTED = -1;
    public static final int RESULT_PARAM_ERROR = -4;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_SUPPORTED = 0;
    public static final int SUPPLEMENT_SERVICE_PAGE_INVISIBLE = 5;
    public static final int SUPPLEMENT_SERVICE_PAGE_VISIBLE = 4;
    public static final String VALUE_GETHOMEMODE_EASYMODE = "easy_mode";
    public static final String VALUE_GETHOMEMODE_HOMEANDAPPSMODE = "home_apps_mode";
    public static final String VALUE_GETHOMEMODE_HOMEONLYMODE = "home_only_mode";
    public final Context mContext;

    public LauncherConfigurationInternal(Context context) {
        this.mContext = context;
    }

    public final int addShortcut(int i, Point point, ComponentName componentName) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PAGE_INT, i);
        bundle.putParcelable("coordination_position", point);
        bundle.putParcelable("component", componentName);
        return this.mContext.getContentResolver().call(CONTENT_URI, "add_shortcut", (String) null, bundle).getInt("invocation_result", -2);
    }

    public final int addWidget(int i, Point point, Point point2, ComponentName componentName) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PAGE_INT, i);
        bundle.putParcelable("coordination_position", point);
        bundle.putParcelable(KEY_COORDINATION_SIZE_POINT, point2);
        bundle.putParcelable("component", componentName);
        return this.mContext.getContentResolver().call(CONTENT_URI, METHOD_ADD_WIDGET, (String) null, bundle).getInt("invocation_result", -2);
    }

    public final boolean getAppsButtonVisibility() {
        Bundle call = this.mContext.getContentResolver().call(CONTENT_URI, METHOD_GET_APPS_BUTTON_STATE, (String) null, (Bundle) null);
        if (call.getInt("invocation_result", -2) == 0) {
            return call.getBoolean(KEY_STATE_BOOLEAN, false);
        }
        return false;
    }

    public final Size getAppsCellDimension() {
        Bundle call = this.mContext.getContentResolver().call(CONTENT_URI, METHOD_GET_APPS_CELL_DIMENSION, (String) null, (Bundle) null);
        return new Size(call.getInt(KEY_CELLDIMENSION_ROWS_INT, 0), call.getInt(KEY_CELLDIMENSION_COLS_INT, 0));
    }

    public final Size getHomeCellDimension() {
        Bundle call = this.mContext.getContentResolver().call(CONTENT_URI, METHOD_GET_HOME_CELL_DIMENSION, (String) null, (Bundle) null);
        return new Size(call.getInt(KEY_CELLDIMENSION_ROWS_INT, 0), call.getInt(KEY_CELLDIMENSION_COLS_INT, 0));
    }

    public final int getHomeMode() {
        String string = this.mContext.getContentResolver().call(CONTENT_URI, METHOD_GET_HOME_MODE, "home_mode", (Bundle) null).getString("home_mode");
        if (VALUE_GETHOMEMODE_EASYMODE.equals(string)) {
            return 1;
        }
        if (VALUE_GETHOMEMODE_HOMEANDAPPSMODE.equals(string)) {
            return 3;
        }
        return VALUE_GETHOMEMODE_HOMEONLYMODE.equals(string) ? 2 : -2;
    }

    public final ComponentName getHotseatItem(int i) {
        Bundle call = this.mContext.getContentResolver().call(CONTENT_URI, METHOD_GET_HOTSEAT_ITEM, (String) null, SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i, KEY_INDEX_INT));
        int i2 = call.getInt("invocation_result", -2);
        Parcelable parcelable = call.getParcelable("component");
        if (i2 == 0 && (parcelable instanceof ComponentName)) {
            return (ComponentName) parcelable;
        }
        return null;
    }

    public final int getHotseatItemCount() {
        return this.mContext.getContentResolver().call(CONTENT_URI, METHOD_GET_HOTSEAT_ITEM_COUNT, (String) null, (Bundle) null).getInt(KEY_ITEMCOUNT_INT, -2);
    }

    public final int getHotseatMaxItemCount() {
        return this.mContext.getContentResolver().call(CONTENT_URI, METHOD_GET_HOTSEAT_MAXITEM_COUNT, (String) null, (Bundle) null).getInt(KEY_ITEMCOUNT_INT, -2);
    }

    public final boolean getSupplementServicePageVisibility() {
        Bundle call = this.mContext.getContentResolver().call(CONTENT_URI, METHOD_GET_SUPPLEMENT_SERVICE_PAGE_VISIBILITY, (String) null, (Bundle) null);
        if (call.getInt("invocation_result", -2) == 0) {
            return call.getBoolean(KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN, false);
        }
        return true;
    }

    public final int isSupported(int i) {
        return this.mContext.getContentResolver().call(CONTENT_URI, METHOD_IS_SUPPORTED, (String) null, SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i, KEY_FEATURE_INT)).getInt("invocation_result", -2);
    }

    public final int makeEmptyPosition(int i, Point point, Point point2) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PAGE_INT, i);
        bundle.putParcelable("coordination_position", point);
        bundle.putParcelable(KEY_COORDINATION_SIZE_POINT, point2);
        return this.mContext.getContentResolver().call(CONTENT_URI, "make_empty_position", (String) null, bundle).getInt("invocation_result", -2);
    }

    public final int removeHotseatItem(int i) {
        return this.mContext.getContentResolver().call(CONTENT_URI, METHOD_REMOVE_HOTSEAT_ITEM, (String) null, SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i, KEY_INDEX_INT)).getInt("invocation_result", -2);
    }

    public final int removePageFromHome(int i) {
        return this.mContext.getContentResolver().call(CONTENT_URI, METHOD_REMOVE_PAGE_FROM_HOME, (String) null, SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i, KEY_PAGE_INT)).getInt("invocation_result", -2);
    }

    public final int removeShortcut(ComponentName componentName) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("component", componentName);
        return this.mContext.getContentResolver().call(CONTENT_URI, "remove_shortcut", (String) null, bundle).getInt("invocation_result", -2);
    }

    public final int removeWidget(ComponentName componentName) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("component", componentName);
        return this.mContext.getContentResolver().call(CONTENT_URI, METHOD_REMOVE_WIDGET, (String) null, bundle).getInt("invocation_result", -2);
    }

    public final int setAppsButtonVisibility(boolean z) {
        new Bundle();
        return (z ? this.mContext.getContentResolver().call(CONTENT_URI, METHOD_ENABLE_APPS_BUTTON, (String) null, (Bundle) null) : this.mContext.getContentResolver().call(CONTENT_URI, METHOD_DISABLE_APPS_BUTTON, (String) null, (Bundle) null)).getInt("invocation_result", -2);
    }

    public final int setHotseatItem(int i, ComponentName componentName) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_INDEX_INT, i);
        bundle.putParcelable("component", componentName);
        return this.mContext.getContentResolver().call(CONTENT_URI, METHOD_SET_HOTSEAT_ITEM, (String) null, bundle).getInt("invocation_result", -2);
    }

    public final int setSupplementServicePageVisibility(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN, z);
        return this.mContext.getContentResolver().call(CONTENT_URI, METHOD_SET_SUPPLEMENT_SERVICE_PAGE_VISIBILITY, (String) null, bundle).getInt("invocation_result", -2);
    }

    public final int switchHomeMode(int i) {
        Bundle bundle = new Bundle();
        if (i == 3) {
            bundle.putString("home_mode", VALUE_GETHOMEMODE_HOMEANDAPPSMODE);
        } else {
            if (i != 2) {
                return -1;
            }
            bundle.putString("home_mode", VALUE_GETHOMEMODE_HOMEONLYMODE);
        }
        return this.mContext.getContentResolver().call(CONTENT_URI, METHOD_SWITCH_HOME_MODE, (String) null, bundle).getInt("invocation_result", -2);
    }
}
