package com.samsung.android.wallpaper.utils;

import android.app.WallpaperManager;
import android.content.Context;
import android.p009os.Bundle;
import android.p009os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemWallpaperProperties {
  private static final String KEY_AOD_THUMBNAIL = "aodThumbnail";
  private static final String KEY_AOD_THUMBNAIL_ERASEBG = "bgErasedAodThumbnail";
  public static final String KEY_CONTENT_ATTRIBUTES = "contentAttributes";
  public static final String KEY_CONTENT_NAME = "contentName";
  public static final String KEY_CONTENT_TYPE = "contentType";
  private static final String KEY_FIXED_ORIENTATION = "isFixedOrientation";
  private static final String KEY_HAS_CROPPED_OBJECT = "hasCroppedObject";
  private static final String KEY_HAS_OWN_CLOCK = "hasOwnClock";
  public static final String KEY_IMAGE_CATEGORY = "imageCategory";
  public static final String KEY_IMAGE_FILTER_PARAMS = "imageFilterParams";
  public static final String KEY_IS_PRELOADED = "isPreloaded";
  public static final String KEY_SERVICE_SETTINGS = "serviceSettings";
  private static final String TAG = SemWallpaperProperties.class.getSimpleName();
  private static final String VALUE_CONTENT_TYPE_LAYERED = "layered";
  private static final String VALUE_CONTENT_TYPE_WEATHER = "weather";
  private static final String VALUE_IMAGE_CATEGORY_COLORS = "Colors";
  private static final String VALUE_IMAGE_CATEGORY_GRAPHICAL = "Graphical";
  private Context mContext;
  private DlsStateShot mDlsStateShot;
  private Bundle mExtras;
  private int mOriginalWhich;
  private int mTargetWhich;
  private int mUserId;
  private WallpaperManager mWallpaperManager;

  public SemWallpaperProperties(Context context, int which, int userId) {
    if (WhichChecker.getMode(which) == 0) {
      throw new IllegalArgumentException(
          "The 'which' does not have the mode value such as FLAG_DISPLAY_PHONE");
    }
    if (!WhichChecker.isSingleType(which)) {
      throw new IllegalArgumentException(
          "The type of 'which' should be one of FLAG_LOCK or FLAG_SYSTEM");
    }
    Context applicationContext = context.getApplicationContext();
    this.mContext = applicationContext;
    this.mWallpaperManager = WallpaperManager.getInstance(applicationContext);
    this.mUserId = userId;
    this.mOriginalWhich = which;
    refresh();
  }

  public void refresh() {
    this.mDlsStateShot = new DlsStateShot(this.mContext, this.mUserId);
    this.mTargetWhich = getPairingConsideredTargetWhich(this.mOriginalWhich);
    boolean isDlsEnabled = isDlsEnabled();
    if (!isDlsEnabled) {
      this.mExtras = this.mWallpaperManager.getWallpaperExtras(this.mTargetWhich, this.mUserId);
    }
    if (this.mExtras == null) {
      this.mExtras = new Bundle();
    }
    Log.m94d(
        TAG,
        "refresh: which="
            + this.mOriginalWhich
            + ", targetWhich="
            + this.mTargetWhich
            + ", dlsEnabled="
            + isDlsEnabled
            + ", dlsState="
            + this.mDlsStateShot.getStateCode()
            + ", userId="
            + this.mUserId);
  }

  public boolean isSupportFullAod() {
    if (isSggEnabled()) {
      return false;
    }
    int wpType = getWallpaperType();
    switch (wpType) {
      case 7:
        return this.mWallpaperManager.isPreloadedLiveWallpaper(this.mTargetWhich);
      default:
        return true;
    }
  }

  @Deprecated
  public boolean isSupportAodSmartEffect() {
    return false;
  }

  public boolean isSupportAodBackgroundErasing() {
    String contentType;
    char c;
    if (isDlsEnabled() || (contentType = getContentType()) == null) {
      return false;
    }
    switch (contentType.hashCode()) {
      case -41954896:
        if (contentType.equals(VALUE_CONTENT_TYPE_LAYERED)) {
          c = 0;
          break;
        }
      default:
        c = 65535;
        break;
    }
    switch (c) {
    }
    return false;
  }

  public boolean hasProperty(String key) {
    return this.mExtras.containsKey(key);
  }

  public String getStringProperty(String key) {
    return this.mExtras.getString(key);
  }

  public int getIntProperty(String key, int defaultValue) {
    return this.mExtras.getInt(key, defaultValue);
  }

  public boolean getBooleanProperty(String key, boolean defaultValue) {
    return this.mExtras.getBoolean(key, defaultValue);
  }

  public String getContentType() {
    Bundle attributes = getContentAttributes();
    if (attributes != null) {
      String contentType = attributes.getString("contentType");
      if (!TextUtils.isEmpty(contentType)) {
        return contentType;
      }
    }
    return getStringProperty("contentType");
  }

  public String getContentName() {
    Bundle attributes = getContentAttributes();
    if (attributes != null) {
      String contentType = attributes.getString(KEY_CONTENT_NAME);
      if (!TextUtils.isEmpty(contentType)) {
        return contentType;
      }
    }
    return getStringProperty("contentType");
  }

  public String getImageFilterParams() {
    return getStringProperty(KEY_IMAGE_FILTER_PARAMS);
  }

  public String getImageCategory() {
    return getStringProperty(KEY_IMAGE_CATEGORY);
  }

  public boolean hasOwnClock() {
    Bundle attributes = getContentAttributes();
    if (attributes == null) {
      return false;
    }
    return attributes.getBoolean(KEY_HAS_OWN_CLOCK, false);
  }

  public boolean isFixedOrientationLiveWallpaper() {
    if (getWallpaperType() != 7) {
      return false;
    }
    return isFixedOrientation();
  }

  private boolean isFixedOrientation() {
    Bundle attributes = getContentAttributes();
    if (attributes != null && attributes.getBoolean(KEY_FIXED_ORIENTATION, false)) {
      return true;
    }
    return getBooleanProperty(KEY_FIXED_ORIENTATION, false);
  }

  @Deprecated
  public ParcelFileDescriptor getAodThumbnailFile(boolean applyEffect, boolean removeBg) {
    int which = this.mTargetWhich;
    if (applyEffect && !isSupportAodSmartEffect()) {
      Log.m94d(TAG, "getAodThumbnailFile: effect not supported. which=" + which);
      return null;
    }
    return getAodThumbnailFile(removeBg);
  }

  public ParcelFileDescriptor getAodThumbnailFile(boolean removeBg) {
    String thumbnailPropertyKey;
    int which = this.mTargetWhich;
    if (removeBg && !isSupportAodBackgroundErasing()) {
      Log.m94d(TAG, "getAodThumbnailFile: erasing BG not supported. which=" + which);
      return null;
    }
    if (removeBg) {
      thumbnailPropertyKey = KEY_AOD_THUMBNAIL_ERASEBG;
    } else {
      thumbnailPropertyKey = KEY_AOD_THUMBNAIL;
    }
    String filename = getStringProperty(thumbnailPropertyKey);
    if (TextUtils.isEmpty(filename)) {
      Log.m94d(
          TAG,
          "getAodThumbnailFile: field not present. which="
              + which
              + ", key="
              + thumbnailPropertyKey);
      return null;
    }
    return this.mWallpaperManager.getWallpaperAssetFile(which, this.mUserId, filename);
  }

  public boolean isStaticImageTypeWallpaper() {
    if (!WhichChecker.isSystem(this.mOriginalWhich)) {
      throw new IllegalArgumentException("Only supports FLAG_SYSTEM. which=" + this.mOriginalWhich);
    }
    int wpType = this.mWallpaperManager.semGetWallpaperType(this.mTargetWhich);
    switch (wpType) {
      case 0:
        return true;
      case 7:
        String contentType = getContentType();
        return VALUE_CONTENT_TYPE_LAYERED.equals(contentType);
      default:
        return false;
    }
  }

  private boolean hasCroppedObject() {
    return getBooleanProperty(KEY_HAS_CROPPED_OBJECT, false);
  }

  private int getWallpaperType() {
    return this.mWallpaperManager.semGetWallpaperType(this.mTargetWhich);
  }

  private Bundle getContentAttributes() {
    return this.mExtras.getBundle(KEY_CONTENT_ATTRIBUTES);
  }

  private boolean isDlsEnabled() {
    return this.mDlsStateShot.isDlsEnabled(this.mOriginalWhich);
  }

  private boolean isSggEnabled() {
    return this.mDlsStateShot.isSggEnabled(this.mOriginalWhich);
  }

  private int getPairingConsideredTargetWhich(int originalWhich) {
    if (WhichChecker.isSystem(originalWhich)) {
      return originalWhich;
    }
    if (isDlsEnabled()) {
      return originalWhich;
    }
    int mode = WhichChecker.getMode(originalWhich);
    if (this.mWallpaperManager.isSystemAndLockPaired(mode)) {
      int systemWhich = mode | 1;
      int type = this.mWallpaperManager.semGetWallpaperType(systemWhich);
      if (type == 7) {
        return systemWhich;
      }
    }
    return originalWhich;
  }
}
