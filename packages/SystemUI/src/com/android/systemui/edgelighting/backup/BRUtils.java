package com.android.systemui.edgelighting.backup;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Slog;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import java.io.File;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class BRUtils {
    public static BRUtils sInstance;
    public final Context mContext;

    private BRUtils(Context context) {
        this.mContext = context;
    }

    public static synchronized BRUtils getInstance(Context context) {
        BRUtils bRUtils;
        synchronized (BRUtils.class) {
            try {
                if (sInstance == null) {
                    sInstance = new BRUtils(context);
                }
                bRUtils = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return bRUtils;
    }

    public final void restoreSettingValue(File file, boolean z) {
        Iterator it = new CocktailBarXmlParser(this.mContext, file).itemsList.iterator();
        boolean z2 = true;
        while (it.hasNext()) {
            CocktailBarSettingValue cocktailBarSettingValue = (CocktailBarSettingValue) it.next();
            if (!z) {
                return;
            }
            String str = cocktailBarSettingValue.mType;
            String str2 = cocktailBarSettingValue.mName;
            String str3 = cocktailBarSettingValue.mValue;
            if (str2 == null || str == null || str3 == null) {
                Slog.i("BRUtils_systemui", str2 + " " + str + " " + str3);
                return;
            }
            if (str2.equals("edge_lighting_version")) {
                if (Settings.System.getInt(this.mContext.getContentResolver(), "edge_lighting_version", 0) == 1) {
                    z2 = false;
                }
            } else if (!Feature.FEATURE_SUPPORT_EDGE_LIGHTING || !str2.equals("turn_over_lighting")) {
                if ("edge_lighting_basic_color_index".equals(str2)) {
                    EdgeLightingSettingUtils.rematchingSimilarColorChip(this.mContext.getContentResolver(), Integer.parseInt(str3));
                } else {
                    switch (str) {
                        case "FLOAT_GLOBAL":
                            Settings.Global.putFloat(this.mContext.getContentResolver(), str2, Float.parseFloat(str3));
                            break;
                        case "BOOLEAN_SHARED_PREFERENCE":
                            SharedPreferences.Editor edit = this.mContext.getSharedPreferences("cocktailbar_shared_prefs", 0).edit();
                            edit.putBoolean(str2, Boolean.parseBoolean(str3));
                            edit.apply();
                            break;
                        case "FLOAT_SYSTEM":
                            Settings.System.putFloat(this.mContext.getContentResolver(), str2, Float.parseFloat(str3));
                            break;
                        case "EDGE_LIGHTING_CUSTOM_COLOR_LIST_SETTING":
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("custom_color_list", str3);
                            this.mContext.getContentResolver().insert(EdgeLightingContentProvider.CUSTOM_COLOR_LIST_CONTENT_URI, contentValues);
                            break;
                        case "INT_SHARED_PREFERENCE":
                            SharedPreferences.Editor edit2 = this.mContext.getSharedPreferences("cocktailbar_shared_prefs", 0).edit();
                            edit2.putInt(str2, Integer.parseInt(str3));
                            edit2.apply();
                            break;
                        case "INT_GLOBAL":
                            Settings.Global.putInt(this.mContext.getContentResolver(), str2, Integer.parseInt(str3));
                            break;
                        case "EDGE_LIGHTING_SETTINGS":
                            ContentValues contentValues2 = new ContentValues();
                            contentValues2.put(str2, str3);
                            this.mContext.getContentResolver().insert(EdgeLightingContentProvider.SETTINGS_CONTENT_URI, contentValues2);
                            break;
                        case "EDGE_LIGHTING_APP_LIST_SETTING":
                            ContentValues contentValues3 = new ContentValues();
                            contentValues3.put("app_list", str3);
                            this.mContext.getContentResolver().insert(EdgeLightingContentProvider.APP_LIST_CONTENT_URI, contentValues3);
                            break;
                        case "INT_SYSTEM":
                            Settings.System.putInt(this.mContext.getContentResolver(), str2, Integer.parseInt(str3));
                            break;
                        case "EDGE_LIGHTING_CUSTOM_TEXT_FILTER":
                            ContentValues contentValues4 = new ContentValues();
                            contentValues4.put("custom_text_filter_color", str3);
                            this.mContext.getContentResolver().insert(EdgeLightingContentProvider.TEXT_FILTER_CONTENT_URI, contentValues4);
                            break;
                        case "STRING_GLOBAL":
                            Settings.Global.putString(this.mContext.getContentResolver(), str2, str3);
                            break;
                        case "STRING_SYSTEM":
                            if (!str2.equals("edge_lighting_style_type_str")) {
                                Settings.System.putString(this.mContext.getContentResolver(), str2, str3);
                                break;
                            } else if (!z2 || !str3.equals("preload/basic")) {
                                Settings.System.putString(this.mContext.getContentResolver(), str2, str3);
                                break;
                            } else {
                                Settings.System.putString(this.mContext.getContentResolver(), str2, "preload/noframe");
                                Slog.i("BRUtils_systemui", "edgelighting style will be restored from basic to noFrame");
                                break;
                            }
                            break;
                    }
                    Slog.d("BRUtils_systemui", "restore Setting  Value - " + str2 + " " + str3);
                }
            }
        }
    }
}
