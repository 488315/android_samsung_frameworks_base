package android.hardware.display;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class SemWifiDisplay implements Parcelable {
    public static final String FEATURE_HIGH_RESOLUTION_MODE = "high_resolution_mode";
    public static final int SCREEN_SHARING_AP_CONNECTED = 3;
    public static final int SCREEN_SHARING_AP_NOT_CONNECTED = 1;
    public static final int SCREEN_SHARING_NOT_SUPPORTED = 0;
    public static final String VIEW_MODE_FULL = "full";
    public static final String VIEW_MODE_MULTI = "multi";
    public static final String VIEW_MODE_NONE = "none";
    private WifiDisplay mWfd;
    public static final SemWifiDisplay[] EMPTY_ARRAY = new SemWifiDisplay[0];
    public static final Parcelable.Creator<SemWifiDisplay> CREATOR = new Parcelable.Creator<SemWifiDisplay>() { // from class: android.hardware.display.SemWifiDisplay.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiDisplay createFromParcel(Parcel parcel) {
            return new SemWifiDisplay((WifiDisplay) parcel.readParcelable(WifiDisplay.class.getClassLoader()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiDisplay[] newArray(int i) {
            return i == 0 ? SemWifiDisplay.EMPTY_ARRAY : new SemWifiDisplay[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public int getIconIndex() {
        return 0;
    }

    public SemWifiDisplay(WifiDisplay wifiDisplay) {
        this.mWfd = wifiDisplay;
    }

    public SemWifiDisplay(Parcelable parcelable) {
        if (parcelable instanceof WifiDisplay) {
            this.mWfd = (WifiDisplay) parcelable;
        } else {
            if (parcelable instanceof SemWifiDisplay) {
                this.mWfd = ((SemWifiDisplay) parcelable).mWfd;
                return;
            }
            throw new IllegalArgumentException("parameter must be WifiDisplay type");
        }
    }

    public String getDeviceAddress() {
        return this.mWfd.getDeviceAddress();
    }

    public String getDeviceName() {
        return this.mWfd.getDeviceName();
    }

    public int getDeviceType() {
        return this.mWfd.getSamsungDeviceType();
    }

    public int getDeviceIcon() {
        return this.mWfd.getSamsungDeviceIcon();
    }

    public String getPrimaryDeviceType() {
        return this.mWfd.getPrimaryDeviceType();
    }

    public String getViewMode() {
        return this.mWfd.getViewMode();
    }

    public String getFriendlyDisplayName() {
        return this.mWfd.getFriendlyDisplayName();
    }

    public String getBluetoothMacAddress() {
        return this.mWfd.getBluetoothMacAddress();
    }

    public String getScreenSharingHashedDi() {
        return this.mWfd.getScreenSharingHashedDi();
    }

    public boolean isAvailable() {
        return this.mWfd.isAvailable();
    }

    public boolean isEmptySurface() {
        return this.mWfd.isEmptySurface();
    }

    public boolean isDmrSupported() {
        return this.mWfd.isDmrSupported();
    }

    public boolean isDmrSupportedType(int i) {
        return this.mWfd.isDmrSupportedType(i);
    }

    public boolean isHighResolutionModeSupported() {
        return this.mWfd.isHighResolutionModeSupported();
    }

    public boolean isSupported(String str) {
        return this.mWfd.isSupport(str);
    }

    public boolean equals(Object obj) {
        return (obj instanceof SemWifiDisplay) && equals((SemWifiDisplay) obj);
    }

    public boolean equals(SemWifiDisplay semWifiDisplay) {
        return semWifiDisplay != null && getDeviceAddress().equals(semWifiDisplay.getDeviceAddress()) && getDeviceName().equals(semWifiDisplay.getDeviceName());
    }

    public int hashCode() {
        return getDeviceAddress().hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mWfd, 0);
    }

    public int compareTo(SemWifiDisplay semWifiDisplay) {
        return getDeviceName().compareTo(semWifiDisplay.getDeviceName());
    }

    public int getDeviceInfo() {
        return this.mWfd.getDeviceInfo();
    }
}
