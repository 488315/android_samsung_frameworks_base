package android.hardware.display;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class WifiDisplay implements Parcelable {
    public static final int REMOTE_DISPLAY_PAUSED = 7;
    public static final int REMOTE_DISPLAY_RESUMED = 6;
    private String mBluetoothMacAddress;
    private final boolean mCanConnect;
    private final String mDeviceAddress;
    private final String mDeviceAlias;
    private int mDeviceInfo;
    private final String mDeviceName;
    private String mDeviceType;
    private int mFlags;
    private final boolean mIsAvailable;
    private boolean mIsEmptySurface;
    private final boolean mIsRemembered;
    private int mMode;
    private ConcurrentHashMap<String, String> mParameters;
    private int mSamsungDeviceIcon;
    private int mSamsungDeviceType;
    private String mScreenSharingHashedDi;
    private int mState = 6;
    public static final WifiDisplay[] EMPTY_ARRAY = new WifiDisplay[0];
    public static final Parcelable.Creator<WifiDisplay> CREATOR = new Parcelable.Creator<WifiDisplay>() { // from class: android.hardware.display.WifiDisplay.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiDisplay createFromParcel(Parcel parcel) {
            WifiDisplay wifiDisplay = new WifiDisplay(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt() != 0, parcel.readString());
            wifiDisplay.setBluetoothMacAddress(parcel.readString());
            wifiDisplay.setScreenSharingHashedDi(parcel.readString());
            wifiDisplay.setSamsungDeviceType(parcel.readInt());
            wifiDisplay.setSamsungDeviceIcon(parcel.readInt());
            wifiDisplay.setEmptySurface(parcel.readInt() != 0);
            wifiDisplay.setFlags(parcel.readInt());
            wifiDisplay.setMode(parcel.readInt());
            wifiDisplay.setDeviceInfo(parcel.readInt());
            int i = parcel.readInt();
            for (int i2 = 0; i2 < i; i2++) {
                wifiDisplay.addParameter(parcel.readString(), (String) parcel.readValue(String.class.getClassLoader()));
            }
            return wifiDisplay;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiDisplay[] newArray(int i) {
            return i == 0 ? WifiDisplay.EMPTY_ARRAY : new WifiDisplay[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WifiDisplay(String str, String str2, String str3, boolean z, boolean z2, boolean z3, String str4) {
        if (str == null) {
            throw new IllegalArgumentException("deviceAddress must not be null");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("deviceName must not be null");
        }
        this.mDeviceAddress = str;
        this.mDeviceName = str2;
        this.mDeviceAlias = str3;
        this.mIsAvailable = z;
        this.mCanConnect = z2;
        this.mIsRemembered = z3;
        this.mDeviceType = str4;
        this.mMode = 0;
        this.mDeviceInfo = 0;
        this.mParameters = new ConcurrentHashMap<>();
    }

    public String getDeviceAddress() {
        return this.mDeviceAddress;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public String getDeviceAlias() {
        return this.mDeviceAlias;
    }

    public boolean isAvailable() {
        return this.mIsAvailable;
    }

    public boolean canConnect() {
        return this.mCanConnect;
    }

    public boolean isRemembered() {
        return this.mIsRemembered;
    }

    public String getFriendlyDisplayName() {
        String str = this.mDeviceAlias;
        return str != null ? str : this.mDeviceName;
    }

    public String getPrimaryDeviceType() {
        return this.mDeviceType;
    }

    public int getSamsungDeviceType() {
        return this.mSamsungDeviceType;
    }

    public int getSamsungDeviceIcon() {
        return this.mSamsungDeviceIcon;
    }

    public String getBluetoothMacAddress() {
        return this.mBluetoothMacAddress;
    }

    public String getScreenSharingHashedDi() {
        return this.mScreenSharingHashedDi;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getMode() {
        return this.mMode;
    }

    public int getState() {
        return this.mState;
    }

    public String getViewMode() {
        String str = this.mParameters.get(SemWifiDisplayParameter.KEY_VIEW_MODE);
        return (str == null || str.isEmpty()) ? "none" : str;
    }

    public boolean isSupport(String str) {
        String str2 = this.mParameters.get(str);
        if (str2 == null || str2.isEmpty()) {
            return false;
        }
        return SemWifiDisplayParameter.VALUE_SUPPORT.equals(str2);
    }

    public boolean isVoipModeSupported() {
        String str = this.mParameters.get(SemWifiDisplayParameter.KEY_VOIP_MODE);
        if (str == null || str.isEmpty()) {
            return false;
        }
        return SemWifiDisplayParameter.VALUE_SUPPORT.equals(str);
    }

    public boolean isHighResolutionModeSupported() {
        String str = this.mParameters.get("high_resolution_mode");
        if (str == null || str.isEmpty()) {
            return false;
        }
        return SemWifiDisplayParameter.VALUE_SUPPORT.equals(str);
    }

    public boolean isDmrSupported() {
        String str = this.mParameters.get(SemWifiDisplayParameter.KEY_DMR_SUPPORT);
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return "enable".equals(str);
    }

    public boolean isDmrSupportedType(int i) {
        String str;
        if (!isDmrSupported()) {
            return false;
        }
        if (i == 0) {
            return Build.VERSION.SEM_PLATFORM_INT < 150100 || !((str = this.mParameters.get(SemWifiDisplayParameter.KEY_DMR_META_CHECK)) == null || str.equals("none"));
        }
        String str2 = this.mParameters.get(SemWifiDisplayParameter.KEY_DMR_SUPPORT_TYPE);
        if (TextUtils.isEmpty(str2)) {
            return false;
        }
        if (i == 1) {
            return str2.contains("image");
        }
        if (i == 4) {
            return str2.contains(SemWifiDisplayParameter.VALUE_DMR_SUPPORT_TYPE_VIDEO_HEVC_SUPER_SLOW_MOTION);
        }
        return false;
    }

    public boolean isEmptySurface() {
        return this.mIsEmptySurface;
    }

    public void setSamsungDeviceType(int i) {
        this.mSamsungDeviceType = i;
    }

    public void setSamsungDeviceIcon(int i) {
        this.mSamsungDeviceIcon = i;
    }

    public void setBluetoothMacAddress(String str) {
        this.mBluetoothMacAddress = str;
    }

    public void setScreenSharingHashedDi(String str) {
        this.mScreenSharingHashedDi = str;
    }

    public void setEmptySurface(boolean z) {
        this.mIsEmptySurface = z;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    public void setMode(int i) {
        this.mMode = i;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public void addParameter(String str, String str2) {
        this.mParameters.put(str, str2);
    }

    public ConcurrentHashMap<String, String> getParameters() {
        return this.mParameters;
    }

    public int getDeviceInfo() {
        return this.mDeviceInfo;
    }

    public void setDeviceInfo(int i) {
        this.mDeviceInfo = i;
    }

    public boolean equals(Object obj) {
        return (obj instanceof WifiDisplay) && equals((WifiDisplay) obj);
    }

    public boolean equals(WifiDisplay wifiDisplay) {
        return wifiDisplay != null && this.mDeviceAddress.equals(wifiDisplay.mDeviceAddress) && this.mDeviceName.equals(wifiDisplay.mDeviceName) && Objects.equals(this.mDeviceAlias, wifiDisplay.mDeviceAlias);
    }

    public boolean hasSameAddress(WifiDisplay wifiDisplay) {
        return wifiDisplay != null && this.mDeviceAddress.equals(wifiDisplay.mDeviceAddress);
    }

    public int hashCode() {
        return this.mDeviceAddress.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeString(this.mDeviceAddress);
        parcel.writeString(this.mDeviceName);
        parcel.writeString(this.mDeviceAlias);
        parcel.writeInt(this.mIsAvailable ? 1 : 0);
        parcel.writeInt(this.mCanConnect ? 1 : 0);
        parcel.writeInt(this.mIsRemembered ? 1 : 0);
        parcel.writeString(this.mDeviceType);
        parcel.writeString(this.mBluetoothMacAddress);
        parcel.writeString(this.mScreenSharingHashedDi);
        parcel.writeInt(this.mSamsungDeviceType);
        parcel.writeInt(this.mSamsungDeviceIcon);
        parcel.writeInt(this.mIsEmptySurface ? 1 : 0);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mMode);
        parcel.writeInt(this.mDeviceInfo);
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(this.mParameters);
        parcel.writeInt(concurrentHashMap.size());
        for (Map.Entry entry : concurrentHashMap.entrySet()) {
            parcel.writeString((String) entry.getKey());
            parcel.writeValue(entry.getValue());
        }
    }

    public String toString() {
        String str = this.mDeviceName + " (" + this.mDeviceAddress + NavigationBarInflaterView.KEY_CODE_END;
        if (this.mDeviceAlias != null) {
            str = str + ", alias " + this.mDeviceAlias;
        }
        return str + ", isAvailable " + this.mIsAvailable + ", canConnect " + this.mCanConnect + ", isRemembered " + this.mIsRemembered + ", deviceType " + this.mDeviceType + ", samsungDeviceType " + this.mSamsungDeviceType + ", samsungDeviceIcon " + this.mSamsungDeviceIcon + ", isEmptySurface " + this.mIsEmptySurface + ", flags " + this.mFlags + ", mode " + this.mMode + ", DeviceInfo " + this.mDeviceInfo + ", paramters " + this.mParameters.toString();
    }
}
