package com.android.systemui.pluginlock.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DynamicLockData {

    @SerializedName("landscape_available")
    Boolean landscapeAvailable;

    @SerializedName("capture_info")
    private CaptureData mCaptureData;

    @SerializedName("custom_shortcut")
    private CustomShortcut mCustomShortcut;

    @SerializedName("finger_print_info")
    private FingerPrintData mFingerPrintData;

    @SerializedName("indication_info")
    private IndicationData mIndicationData;

    @SerializedName("music_info")
    private MusicData mMusicData;

    @SerializedName("non_swipe_info")
    private NonSwipeModeData mNonSwipeModeData;

    @SerializedName("noti_info")
    private NotificationData mNotificationData;

    @SerializedName("service_box_info")
    private ServiceBoxData mServiceBoxData;

    @SerializedName("shortcut_info")
    private ShortcutData mShortcutData;

    @SerializedName("status_bar_icon_visibility")
    private Integer mStatusBarIconVisibility;

    @SerializedName("status_bar_network_visibility")
    private Integer mStatusBarNetworkVisibility;

    @SerializedName("wallpaper_info")
    private WallpaperData mWallpaperData;

    @SerializedName("portrait_available")
    Boolean portraitAvailable;

    @SerializedName("version")
    Integer VERSION = 3;

    @SerializedName("origin")
    private Integer origin = 0;

    public DynamicLockData() {
        Boolean bool = Boolean.FALSE;
        this.portraitAvailable = bool;
        this.landscapeAvailable = bool;
        this.mStatusBarIconVisibility = -1;
        this.mStatusBarNetworkVisibility = -1;
    }

    public static DynamicLockData fromJSon(String str) {
        try {
            return (DynamicLockData) new Gson().fromJson(DynamicLockData.class, str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final Object clone() {
        return (DynamicLockData) super.clone();
    }

    public final CaptureData getCaptureData() {
        if (this.mCaptureData == null) {
            this.mCaptureData = new CaptureData();
        }
        return this.mCaptureData;
    }

    public final FingerPrintData getFingerPrintData() {
        if (this.mFingerPrintData == null) {
            this.mFingerPrintData = new FingerPrintData();
        }
        return this.mFingerPrintData;
    }

    public final IndicationData getIndicationData() {
        if (this.mIndicationData == null) {
            this.mIndicationData = new IndicationData();
        }
        return this.mIndicationData;
    }

    public final MusicData getMusicData() {
        if (this.mMusicData == null) {
            this.mMusicData = new MusicData();
        }
        return this.mMusicData;
    }

    public final NonSwipeModeData getNonSwipeModeData() {
        if (this.mNonSwipeModeData == null) {
            this.mNonSwipeModeData = new NonSwipeModeData();
        }
        return this.mNonSwipeModeData;
    }

    public final NotificationData getNotificationData() {
        if (this.mNotificationData == null) {
            this.mNotificationData = new NotificationData();
        }
        return this.mNotificationData;
    }

    public final ServiceBoxData getServiceBoxData() {
        if (this.mServiceBoxData == null) {
            this.mServiceBoxData = new ServiceBoxData();
        }
        return this.mServiceBoxData;
    }

    public final ShortcutData getShortcutData() {
        if (this.mShortcutData == null) {
            this.mShortcutData = new ShortcutData();
        }
        return this.mShortcutData;
    }

    public final WallpaperData getWallpaperData() {
        if (this.mWallpaperData == null) {
            this.mWallpaperData = new WallpaperData();
        }
        return this.mWallpaperData;
    }

    public final boolean isDlsData() {
        return this.origin.intValue() == 0;
    }

    public final boolean isLandscapeAvailable() {
        return this.landscapeAvailable.booleanValue();
    }

    public final boolean isPortraitAvailable() {
        return this.portraitAvailable.booleanValue();
    }

    public final boolean isStatusBarIconVisible() {
        Integer num = this.mStatusBarIconVisibility;
        return num == null || num.intValue() == -1 || this.mStatusBarIconVisibility.intValue() == 0;
    }

    public final boolean isStatusBarNetworkVisible() {
        Integer num = this.mStatusBarNetworkVisibility;
        return num == null || num.intValue() == -1 || this.mStatusBarNetworkVisibility.intValue() == 0;
    }
}
