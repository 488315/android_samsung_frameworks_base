package com.samsung.android.wallpaper.utils;

import android.app.SemWallpaperResourcesInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemWallpaperProperties {
    private static final String KEY_AOD_THUMBNAIL = "aodThumbnail";
    private static final String KEY_AOD_THUMBNAIL_ERASEBG = "bgErasedAodThumbnail";
    public static final String KEY_CONTENT_ATTRIBUTES = "contentAttributes";
    public static final String KEY_CONTENT_TYPE = "contentType";
    private static final String KEY_FIXED_ORIENTATION = "isFixedOrientation";
    private static final String KEY_HAS_CROPPED_OBJECT = "hasCroppedObject";
    private static final String KEY_HAS_OWN_CLOCK = "hasOwnClock";
    private static final String KEY_HIDE_WHEN_ROAMING = "hideWhenRoaming";
    public static final String KEY_IMAGE_CATEGORY = "imageCategory";
    public static final String KEY_IMAGE_FILTER_PARAMS = "imageFilterParams";
    public static final String KEY_IS_PRELOADED = "isPreloaded";
    public static final String KEY_LEGIBILITY_COLORS_ROTATION_0 = "rotation0";
    public static final String KEY_LEGIBILITY_COLORS_ROTATION_270 = "rotation270";
    public static final String KEY_LEGIBILITY_COLORS_ROTATION_90 = "rotation90";
    public static final String KEY_LOCK_LEGIBILITY_COLORS = "lockLegibilityColors";
    public static final String KEY_SERVICE_SETTINGS = "serviceSettings";
    public static final String KEY_SYSTEM_LEGIBILITY_COLORS = "systemLegibilityColors";
    private static final String TAG = "SemWallpaperProperties";
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

    @Deprecated
    public boolean isSupportAodSmartEffect() {
        return false;
    }

    public SemWallpaperProperties(Context context, int i, int i2) {
        if (WhichChecker.getMode(i) == 0) {
            throw new IllegalArgumentException("The 'which' does not have the mode value such as FLAG_DISPLAY_PHONE");
        }
        if (!WhichChecker.isSingleType(i)) {
            throw new IllegalArgumentException("The type of 'which' should be one of FLAG_LOCK or FLAG_SYSTEM");
        }
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mWallpaperManager = WallpaperManager.getInstance(applicationContext);
        this.mUserId = i2;
        this.mOriginalWhich = i;
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
        Log.d(TAG, "refresh: which=" + this.mOriginalWhich + ", targetWhich=" + this.mTargetWhich + ", dlsEnabled=" + isDlsEnabled + ", dlsState=" + this.mDlsStateShot.getStateCode() + ", userId=" + this.mUserId);
    }

    public boolean isSupportFullAod() {
        if (isSggEnabled()) {
            return false;
        }
        if (getWallpaperType() != 7) {
            return true;
        }
        return this.mWallpaperManager.isStockLiveWallpaper(this.mTargetWhich);
    }

    public boolean isSupportAodBackgroundErasing() {
        String contentType;
        if (isDlsEnabled() || (contentType = getContentType()) == null) {
            return false;
        }
        contentType.hashCode();
        if (contentType.equals(VALUE_CONTENT_TYPE_LAYERED)) {
            return hasCroppedObject();
        }
        return false;
    }

    public boolean hasProperty(String str) {
        return this.mExtras.containsKey(str);
    }

    public String getStringProperty(String str) {
        return this.mExtras.getString(str);
    }

    public int getIntProperty(String str, int i) {
        return this.mExtras.getInt(str, i);
    }

    public boolean getBooleanProperty(String str, boolean z) {
        return this.mExtras.getBoolean(str, z);
    }

    public String getContentType() {
        return getStringProperty("contentType");
    }

    public String getImageFilterParams() {
        return getStringProperty(KEY_IMAGE_FILTER_PARAMS);
    }

    public String getImageCategory() {
        return getStringProperty(KEY_IMAGE_CATEGORY);
    }

    public boolean hasOwnClock() {
        Bundle contentAttributes = getContentAttributes();
        if (contentAttributes == null) {
            return false;
        }
        return contentAttributes.getBoolean(KEY_HAS_OWN_CLOCK, false);
    }

    public boolean shouldHideWhenRoaming() {
        Bundle contentAttributes = getContentAttributes();
        if (contentAttributes == null) {
            return false;
        }
        return contentAttributes.getBoolean(KEY_HIDE_WHEN_ROAMING, false);
    }

    public boolean isFixedOrientationLiveWallpaper() {
        if (getWallpaperType() != 7) {
            return false;
        }
        return isFixedOrientation();
    }

    public boolean isFixedOrientation() {
        Bundle contentAttributes = getContentAttributes();
        if (contentAttributes == null || !contentAttributes.getBoolean(KEY_FIXED_ORIENTATION, false)) {
            return getBooleanProperty(KEY_FIXED_ORIENTATION, false);
        }
        return true;
    }

    @Deprecated
    public ParcelFileDescriptor getAodThumbnailFile(boolean z, boolean z2) {
        int i = this.mTargetWhich;
        if (z && !isSupportAodSmartEffect()) {
            Log.d(TAG, "getAodThumbnailFile: effect not supported. which=" + i);
            return null;
        }
        return getAodThumbnailFile(z2);
    }

    public ParcelFileDescriptor getAodThumbnailFile(boolean z) {
        String str;
        int i = this.mTargetWhich;
        if (z && !isSupportAodBackgroundErasing()) {
            Log.d(TAG, "getAodThumbnailFile: erasing BG not supported. which=" + i);
            return null;
        }
        if (z) {
            str = KEY_AOD_THUMBNAIL_ERASEBG;
        } else {
            str = KEY_AOD_THUMBNAIL;
        }
        String stringProperty = getStringProperty(str);
        if (TextUtils.isEmpty(stringProperty)) {
            Log.d(TAG, "getAodThumbnailFile: field not present. which=" + i + ", key=" + str);
            return null;
        }
        return this.mWallpaperManager.getWallpaperAssetFile(i, this.mUserId, stringProperty);
    }

    public boolean isStaticImageTypeWallpaper() {
        if (!WhichChecker.isSystem(this.mOriginalWhich)) {
            throw new IllegalArgumentException("Only supports FLAG_SYSTEM. which=" + this.mOriginalWhich);
        }
        int semGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(this.mTargetWhich);
        if (semGetWallpaperType == 0) {
            return true;
        }
        if (semGetWallpaperType != 7) {
            return false;
        }
        return VALUE_CONTENT_TYPE_LAYERED.equals(getContentType());
    }

    public boolean isDefaultLiveWallpaper() {
        if (this.mWallpaperManager.semGetWallpaperType(this.mTargetWhich) != 7) {
            return false;
        }
        SemWallpaperResourcesInfo semWallpaperResourcesInfo = new SemWallpaperResourcesInfo(this.mContext);
        if (semWallpaperResourcesInfo.getDefaultWallpaperType(this.mTargetWhich, WallpaperManager.getDeviceColor(this.mContext)) != 7) {
            return false;
        }
        ComponentName defaultLiveWallpaperComponentName = semWallpaperResourcesInfo.getDefaultLiveWallpaperComponentName(this.mTargetWhich);
        if (defaultLiveWallpaperComponentName == null) {
            Log.e(TAG, "isDefaultLiveWallpaper : factory default component is null");
            return false;
        }
        ComponentName semGetWallpaperComponent = this.mWallpaperManager.semGetWallpaperComponent(this.mTargetWhich, this.mUserId);
        if (semGetWallpaperComponent == null) {
            return false;
        }
        return defaultLiveWallpaperComponentName.equals(semGetWallpaperComponent);
    }

    private boolean hasCroppedObject() {
        return getBooleanProperty(KEY_HAS_CROPPED_OBJECT, false);
    }

    private int getWallpaperType() {
        return this.mWallpaperManager.semGetWallpaperType(this.mTargetWhich);
    }

    public Bundle getContentAttributes() {
        return this.mExtras.getBundle(KEY_CONTENT_ATTRIBUTES);
    }

    private boolean isDlsEnabled() {
        return this.mDlsStateShot.isDlsEnabled(this.mOriginalWhich);
    }

    private boolean isSggEnabled() {
        return this.mDlsStateShot.isSggEnabled(this.mOriginalWhich);
    }

    private int getPairingConsideredTargetWhich(int i) {
        if (!WhichChecker.isSystem(i) && !isDlsEnabled()) {
            int mode = WhichChecker.getMode(i);
            if (this.mWallpaperManager.isSystemAndLockPaired(mode)) {
                int i2 = mode | 1;
                if (this.mWallpaperManager.semGetWallpaperType(i2) == 7) {
                    return i2;
                }
            }
        }
        return i;
    }
}
