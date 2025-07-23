package android.hardware.display;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public class SemWifiDisplayParameter implements Parcelable {
    public static final String GET_PARAMETER = "getparams";
    public static final String INIT_PARAMETER = "initparams";
    public static final String KEY_DMR_META_CHECK = "wfd_sec_dmr_meta_check";
    public static final String KEY_DMR_SUPPORT = "wfd_sec_dmr_support";
    public static final String KEY_DMR_SUPPORT_TYPE = "wfd_sec_dmr_support_type";
    public static final String KEY_FREQUENCY = "frequency";
    public static final String KEY_HIGH_RESOLUTION_MODE = "high_resolution_mode";
    public static final String KEY_LOW_LATENCY_MODE = "wfd_sec_low_latency_mode";
    public static final String KEY_MIRRORING_MODE = "wfd_sec_mirroring_mode";
    public static final String KEY_MIRRORING_UUID = "wfd_sec_mirroring_uuid";
    public static final String KEY_POINTER_ICON_INDEX = "wfd_sec_pointer_icon_idx";
    public static final String KEY_SAMSUNG_ACCOUNT = "samsung_account";
    public static final String KEY_SCAMBLE_SUPPORT = "scramble_support";
    public static final String KEY_SOURCE_DISPLAY_ORIENTATION = "wfd_sec_source_display_orientation";
    public static final String KEY_TIZEN_VERSION = "tizenVer";
    public static final String KEY_TV_BLE_IRK = "tv_ble_irk";
    public static final String KEY_TV_BLE_MAC = "wfd_sec_tv_ble_mac";
    public static final String KEY_TV_DEVICE_ID = "tv_device_id";
    public static final String KEY_VIEW_MODE = "wfd_sec_view_mode";
    public static final String KEY_VOIP_MODE = "wfd_sec_voip_mode";
    public static final String KEY_WIRELESS_DEX_SUPPORT = "wfd_sec_wirelessdex_support";
    public static final String SET_PARAMETER = "setparams";
    private static final String TAG = "SemWifiDisplayParameter";
    public static final String VALUE_DISABLE = "disable";
    public static final String VALUE_DMR_SUPPORT_TYPE_IMAGE = "image";
    public static final String VALUE_DMR_SUPPORT_TYPE_VIDEO = "video";
    public static final String VALUE_DMR_SUPPORT_TYPE_VIDEO_HEVC_SUPER_SLOW_MOTION = "video_hevc_super_slow_motion";
    public static final String VALUE_ENABLE = "enable";
    public static final String VALUE_MIRRORING_MODE_DEX = "dex";
    public static final String VALUE_MIRRORING_MODE_MIRRORING = "mirroring";
    public static final String VALUE_NO = "no";
    public static final String VALUE_NONE = "none";
    public static final String VALUE_OFF = "off";
    public static final String VALUE_ON = "on";
    public static final String VALUE_SOURCE_DISPLAY_ORIENTATION_LANDSCAPE = "landscape";
    public static final String VALUE_SOURCE_DISPLAY_ORIENTATION_PORTRAIT = "portrait";
    public static final String VALUE_SOURCE_DISPLAY_ORIENTATION_PRESENTATION_OFF = "presentation=off";
    public static final String VALUE_SOURCE_DISPLAY_ORIENTATION_PRESENTATION_ON = "presentation=on";
    public static final String VALUE_SUPPORT = "support";
    public static final String VALUE_VIEW_MODE_FULL = "full";
    public static final String VALUE_VIEW_MODE_MULTI = "multi";
    public static final String VALUE_YES = "yes";
    private String mKey;
    private String mValue;
    public static final SemWifiDisplayParameter[] EMPTY_ARRAY = new SemWifiDisplayParameter[0];
    public static final Parcelable.Creator<SemWifiDisplayParameter> CREATOR = new Parcelable.Creator<SemWifiDisplayParameter>() { // from class: android.hardware.display.SemWifiDisplayParameter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiDisplayParameter createFromParcel(Parcel parcel) {
            return new SemWifiDisplayParameter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiDisplayParameter[] newArray(int i) {
            return i == 0 ? SemWifiDisplayParameter.EMPTY_ARRAY : new SemWifiDisplayParameter[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemWifiDisplayParameter() {
    }

    public SemWifiDisplayParameter(String str, String str2) {
        this.mKey = str;
        this.mValue = str2;
    }

    public SemWifiDisplayParameter(String str) {
        this.mKey = str;
    }

    public SemWifiDisplayParameter(String str, int i) {
        this.mKey = str;
        this.mValue = Integer.toString(i);
    }

    public SemWifiDisplayParameter(String str, boolean z) {
        this.mKey = str;
        this.mValue = Boolean.toString(z);
    }

    public SemWifiDisplayParameter(Parcel parcel) {
        this.mKey = parcel.readString();
        this.mValue = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mKey);
        parcel.writeString(this.mValue);
    }

    public String getKey() {
        return this.mKey;
    }

    public String getValue() {
        return this.mValue;
    }

    public void setKey(String str) {
        this.mKey = str;
    }

    public void setValue(String str) {
        this.mValue = str;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SemWifiDisplayParameter) || TextUtils.isEmpty(getKey())) {
            return false;
        }
        SemWifiDisplayParameter semWifiDisplayParameter = (SemWifiDisplayParameter) obj;
        if (TextUtils.isEmpty(semWifiDisplayParameter.getKey())) {
            return false;
        }
        return semWifiDisplayParameter.getKey().equals(getKey());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String str = "";
        sb.append(TextUtils.isEmpty(this.mKey) ? "" : this.mKey);
        if (!TextUtils.isEmpty(this.mValue) && !KEY_TV_DEVICE_ID.equals(this.mKey)) {
            str = ": " + this.mValue;
        }
        sb.append(str);
        return sb.toString();
    }
}
