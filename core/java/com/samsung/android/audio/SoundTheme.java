package com.samsung.android.audio;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.text.TextUtils;
import com.android.internal.C4337R;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes5.dex */
public final class SoundTheme {
  private static final String BRAND_SOUND_VERSION_DEFAULT = "3.1.1";
  private static final String BrandSound = "BrandSound";
  public static final String Calm = "Calm";
  public static final String Custom = "Custom";
  public static final String Default = "Default";
  public static final String EXTRA_RINGTONE_PICKED_SOUND_THEME =
      "android.samsung.intent.extra.ringtone.PICKED_SOUND_THEME";
  public static final String EXTRA_RINGTONE_PICKED_SOUND_THEME_URI =
      "android.samsung.intent.extra.ringtone.PICKED_SOUND_THEME_URI";
  public static final String EXTRA_RINGTONE_SHOW_OPEN_THEME =
      "android.samsung.intent.extra.ringtone.SHOW_OPEN_THEME";
  public static final String Fun = "Fun";
  public static final String Galaxy = "Galaxy";
  public static final String Open_theme = "Open_theme";
  public static final String Retro = "Retro";
  public static final String Ringtone = "Ringtone";
  private static final String SAMSUNG_BRAND_GALAXY_BELLS = "Galaxy Bells";
  private static final String SAMSUNG_BRAND_OVER_THE_HORIZON = "Over the Horizon";
  private static final String SAMSUNG_BRAND_OVER_THE_HORIZON_2022_BY_BTS =
      "Over the Horizon 2022 produced by SUGA of BTS";
  private static final String SAMSUNG_BRAND_OVER_THE_HORIZON_BY_BTS =
      "Over the Horizon by SUGA of BTS";
  private static final String SAMSUNG_BRAND_SPACELINE = "Spaceline";
  private static final String SAMSUNG_BRAND_THE_VOYAGE = "The Voyage";
  private static String brandSoundVersion = "";
  public static final String[] SOUND_THEME_MEDIA_COLUMNS = {
    "_id", "title", "volume_name", "bucket_display_name", "_display_name"
  };

  public static String getCurrentThemeTitle(Context context, String category) {
    if (TextUtils.equals(category, Calm)) {
      return context.getString(C4337R.string.sec_ringtone_category_calm);
    }
    if (TextUtils.equals(category, Fun)) {
      return context.getString(C4337R.string.sec_ringtone_category_fun);
    }
    if (TextUtils.equals(category, Retro)) {
      return context.getString(C4337R.string.sec_ringtone_category_retro);
    }
    if (TextUtils.equals(category, BrandSound)) {
      return context.getString(C4337R.string.sec_ringtone_category_brand_sound);
    }
    return context.getString(C4337R.string.sec_ringtone_category_galaxy);
  }

  public static boolean isSoundThemeCategory(String category) {
    return !TextUtils.isEmpty(category)
        && (TextUtils.equals(category, Calm)
            || TextUtils.equals(category, Fun)
            || TextUtils.equals(category, Galaxy)
            || TextUtils.equals(category, Retro)
            || TextUtils.equals(category, BrandSound));
  }

  public static String getTitleIncludingTheme(Context context, Cursor cursor) {
    int indexVolumeName = cursor.getColumnIndex("volume_name");
    int indexBucketDisplayName = cursor.getColumnIndex("bucket_display_name");
    String volumeName = cursor.getString(indexVolumeName);
    String bucketDisplayName = cursor.getString(indexBucketDisplayName);
    String titleName = cursor.getString(1);
    if (TextUtils.isEmpty(brandSoundVersion) && !isSepLiteDevice(context)) {
      brandSoundVersion = BRAND_SOUND_VERSION_DEFAULT;
    }
    if (isBrandSound(titleName)) {
      bucketDisplayName = BrandSound;
    }
    if (!TextUtils.isEmpty(volumeName) && "internal".equals(volumeName)) {
      if (isSoundThemeCategory(bucketDisplayName)) {
        String themeTitle = getCurrentThemeTitle(context, bucketDisplayName);
        return themeTitle + " / " + titleName;
      }
      return titleName;
    }
    return titleName;
  }

  private static boolean isBrandSound(String title) {
    if (!TextUtils.isEmpty(brandSoundVersion)
        && !TextUtils.isEmpty(title)
        && BRAND_SOUND_VERSION_DEFAULT.equals(brandSoundVersion)) {
      if (title.startsWith(SAMSUNG_BRAND_OVER_THE_HORIZON)
          || TextUtils.equals(title, SAMSUNG_BRAND_THE_VOYAGE)
          || TextUtils.equals(title, SAMSUNG_BRAND_GALAXY_BELLS)
          || TextUtils.equals(title, SAMSUNG_BRAND_SPACELINE)) {
        return true;
      }
      return false;
    }
    return false;
  }

  private static boolean isSepLiteDevice(Context context) {
    String sepCategory =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_SEP_CATEGORY");
    if (!TextUtils.isEmpty(sepCategory)) {
      return TextUtils.equals(sepCategory, "sep_lite")
          || TextUtils.equals(sepCategory, "sep_lite_new");
    }
    PackageManager packageManager = context.getPackageManager();
    return packageManager.hasSystemFeature(
        PackageManager.SEM_FEATURE_SAMSUNG_EXPERIENCE_MOBILE_LITE);
  }
}
