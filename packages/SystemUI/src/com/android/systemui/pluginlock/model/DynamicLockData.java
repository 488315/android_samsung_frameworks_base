package com.android.systemui.pluginlock.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class DynamicLockData {

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

    public CaptureData getCaptureData() {
        if (this.mCaptureData == null) {
            this.mCaptureData = new CaptureData();
        }
        return this.mCaptureData;
    }

    public CustomShortcut getCustomShortcut() {
        if (this.mCustomShortcut == null) {
            this.mCustomShortcut = new CustomShortcut();
        }
        return this.mCustomShortcut;
    }

    public FingerPrintData getFingerPrintData() {
        if (this.mFingerPrintData == null) {
            this.mFingerPrintData = new FingerPrintData();
        }
        return this.mFingerPrintData;
    }

    public IndicationData getIndicationData() {
        if (this.mIndicationData == null) {
            this.mIndicationData = new IndicationData();
        }
        return this.mIndicationData;
    }

    public MusicData getMusicData() {
        if (this.mMusicData == null) {
            this.mMusicData = new MusicData();
        }
        return this.mMusicData;
    }

    public NonSwipeModeData getNonSwipeModeData() {
        if (this.mNonSwipeModeData == null) {
            this.mNonSwipeModeData = new NonSwipeModeData();
        }
        return this.mNonSwipeModeData;
    }

    public NotificationData getNotificationData() {
        if (this.mNotificationData == null) {
            this.mNotificationData = new NotificationData();
        }
        return this.mNotificationData;
    }

    public ServiceBoxData getServiceBoxData() {
        if (this.mServiceBoxData == null) {
            this.mServiceBoxData = new ServiceBoxData();
        }
        return this.mServiceBoxData;
    }

    public ShortcutData getShortcutData() {
        if (this.mShortcutData == null) {
            this.mShortcutData = new ShortcutData();
        }
        return this.mShortcutData;
    }

    public int getVersion() {
        return this.VERSION.intValue();
    }

    public WallpaperData getWallpaperData() {
        if (this.mWallpaperData == null) {
            this.mWallpaperData = new WallpaperData();
        }
        return this.mWallpaperData;
    }

    public boolean isDlsData() {
        return this.origin.intValue() == 0;
    }

    public boolean isLandscapeAvailable() {
        return this.landscapeAvailable.booleanValue();
    }

    public boolean isPortraitAvailable() {
        return this.portraitAvailable.booleanValue();
    }

    public boolean isStatusBarIconVisible() {
        Integer num = this.mStatusBarIconVisibility;
        return num == null || num.intValue() == -1 || this.mStatusBarIconVisibility.intValue() == 0;
    }

    public boolean isStatusBarNetworkVisible() {
        Integer num = this.mStatusBarNetworkVisibility;
        return num == null || num.intValue() == -1 || this.mStatusBarNetworkVisibility.intValue() == 0;
    }

    public void setCaptureData(CaptureData captureData) {
        if (captureData != null) {
            this.mCaptureData = captureData;
        }
    }

    public void setCustomShortcut(CustomShortcut customShortcut) {
        this.mCustomShortcut = customShortcut;
    }

    public void setFingerPrintData(FingerPrintData fingerPrintData) {
        if (fingerPrintData != null) {
            this.mFingerPrintData = fingerPrintData;
        }
    }

    public void setIndicationData(IndicationData indicationData) {
        if (indicationData != null) {
            this.mIndicationData = indicationData;
        }
    }

    public void setLandscapeAvailable(boolean z) {
        this.landscapeAvailable = Boolean.valueOf(z);
    }

    public void setNonSwipeData(NonSwipeModeData nonSwipeModeData) {
        if (nonSwipeModeData != null) {
            this.mNonSwipeModeData = nonSwipeModeData;
        }
    }

    public void setNotificationData(NotificationData notificationData) {
        if (notificationData != null) {
            this.mNotificationData = notificationData;
        }
    }

    public void setOrigin(int i) {
        this.origin = Integer.valueOf(i);
    }

    public void setPortraitAvailable(boolean z) {
        this.portraitAvailable = Boolean.valueOf(z);
    }

    public void setServiceBoxData(ServiceBoxData serviceBoxData) {
        if (serviceBoxData != null) {
            this.mServiceBoxData = serviceBoxData;
        }
    }

    public void setShortcutData(ShortcutData shortcutData) {
        if (shortcutData != null) {
            this.mShortcutData = shortcutData;
        }
    }

    public void setStatusBarIconVisibility(Integer num) {
        this.mStatusBarIconVisibility = num;
    }

    public void setStatusBarNetworkVisibility(Integer num) {
        this.mStatusBarNetworkVisibility = num;
    }

    public void setWallpaerData(WallpaperData wallpaperData) {
        if (wallpaperData != null) {
            this.mWallpaperData = wallpaperData;
        }
    }

    public String toJsonString() {
        return new Gson().toJson(this);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public DynamicLockData m2017clone() throws CloneNotSupportedException {
        return (DynamicLockData) super.clone();
    }
}
