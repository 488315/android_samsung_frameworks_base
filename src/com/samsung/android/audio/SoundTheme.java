package com.samsung.android.audio;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.text.TextUtils;
import com.android.internal.R;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes6.dex */
public final class SoundTheme {
    private static final String BRAND_SOUND_VERSION_DEFAULT = "3.1.1";
    private static final String BrandSound = "BrandSound";
    public static final String Calm = "Calm";
    public static final String Custom = "Custom";
    public static final String Default = "Default";
    public static final String EXTRA_RINGTONE_PICKED_SOUND_THEME = "android.samsung.intent.extra.ringtone.PICKED_SOUND_THEME";
    public static final String EXTRA_RINGTONE_PICKED_SOUND_THEME_URI = "android.samsung.intent.extra.ringtone.PICKED_SOUND_THEME_URI";
    public static final String EXTRA_RINGTONE_SHOW_OPEN_THEME = "android.samsung.intent.extra.ringtone.SHOW_OPEN_THEME";
    public static final String Fun = "Fun";
    public static final String Galaxy = "Galaxy";
    public static final String Open_theme = "Open_theme";
    public static final String Retro = "Retro";
    public static final String Ringtone = "Ringtone";
    private static final String SAMSUNG_BRAND_GALAXY_BELLS = "Galaxy Bells";
    private static final String SAMSUNG_BRAND_OVER_THE_HORIZON = "Over the Horizon";
    private static final String SAMSUNG_BRAND_OVER_THE_HORIZON_2022_BY_BTS = "Over the Horizon 2022 produced by SUGA of BTS";
    private static final String SAMSUNG_BRAND_OVER_THE_HORIZON_BY_BTS = "Over the Horizon by SUGA of BTS";
    private static final String SAMSUNG_BRAND_SPACELINE = "Spaceline";
    private static final String SAMSUNG_BRAND_THE_VOYAGE = "The Voyage";
    public static final String[] SOUND_THEME_MEDIA_COLUMNS = {"_id", "title", "volume_name", "bucket_display_name", "_display_name"};
    private static String brandSoundVersion = "3.1.1";

    public static String getCurrentThemeTitle(Context context, String str) {
        if (TextUtils.equals(str, Calm)) {
            return context.getString(R.string.sec_ringtone_category_calm);
        }
        if (TextUtils.equals(str, Fun)) {
            return context.getString(R.string.sec_ringtone_category_fun);
        }
        if (TextUtils.equals(str, Retro)) {
            return context.getString(R.string.sec_ringtone_category_retro);
        }
        if (TextUtils.equals(str, BrandSound)) {
            return context.getString(R.string.sec_ringtone_category_brand_sound);
        }
        return context.getString(R.string.sec_ringtone_category_galaxy);
    }

    public static boolean isSoundThemeCategory(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return TextUtils.equals(str, Calm) || TextUtils.equals(str, Fun) || TextUtils.equals(str, Galaxy) || TextUtils.equals(str, Retro) || TextUtils.equals(str, BrandSound);
    }

    public static String getTitleIncludingTheme(Context context, Cursor cursor) {
        int columnIndex = cursor.getColumnIndex("volume_name");
        int columnIndex2 = cursor.getColumnIndex("bucket_display_name");
        String string = cursor.getString(columnIndex);
        String string2 = cursor.getString(columnIndex2);
        String string3 = cursor.getString(1);
        if (TextUtils.isEmpty(brandSoundVersion) && !isSepLiteDevice(context)) {
            brandSoundVersion = "3.1.1";
        }
        if (isBrandSound(string3)) {
            string2 = BrandSound;
        }
        if (TextUtils.isEmpty(string) || !"internal".equals(string) || !isSoundThemeCategory(string2)) {
            return string3;
        }
        return getCurrentThemeTitle(context, string2) + " / " + string3;
    }

    private static boolean isBrandSound(String str) {
        if (TextUtils.isEmpty(brandSoundVersion) || TextUtils.isEmpty(str) || !"3.1.1".equals(brandSoundVersion)) {
            return false;
        }
        return str.startsWith(SAMSUNG_BRAND_OVER_THE_HORIZON) || TextUtils.equals(str, SAMSUNG_BRAND_THE_VOYAGE) || TextUtils.equals(str, SAMSUNG_BRAND_GALAXY_BELLS) || TextUtils.equals(str, SAMSUNG_BRAND_SPACELINE);
    }

    private static boolean isSepLiteDevice(Context context) {
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_SEP_CATEGORY");
        if (TextUtils.isEmpty(string)) {
            return context.getPackageManager().hasSystemFeature(PackageManager.SEM_FEATURE_SAMSUNG_EXPERIENCE_MOBILE_LITE);
        }
        return TextUtils.equals(string, "sep_lite") || TextUtils.equals(string, "sep_lite_new");
    }
}
