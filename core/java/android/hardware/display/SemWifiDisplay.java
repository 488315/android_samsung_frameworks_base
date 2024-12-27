package android.hardware.display;

import android.os.Parcel;
import android.os.Parcelable;

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
    public static final Parcelable.Creator<SemWifiDisplay> CREATOR =
            new Parcelable.Creator<
                    SemWifiDisplay>() { // from class: android.hardware.display.SemWifiDisplay.1
                @Override // android.os.Parcelable.Creator
                public SemWifiDisplay createFromParcel(Parcel in) {
                    WifiDisplay display =
                            (WifiDisplay) in.readParcelable(WifiDisplay.class.getClassLoader());
                    return new SemWifiDisplay(display);
                }

                @Override // android.os.Parcelable.Creator
                public SemWifiDisplay[] newArray(int size) {
                    return size == 0 ? SemWifiDisplay.EMPTY_ARRAY : new SemWifiDisplay[size];
                }
            };

    public SemWifiDisplay(WifiDisplay wfd) {
        this.mWfd = wfd;
    }

    public SemWifiDisplay(Parcelable wfd) {
        if (wfd instanceof WifiDisplay) {
            this.mWfd = (WifiDisplay) wfd;
        } else {
            if (wfd instanceof SemWifiDisplay) {
                this.mWfd = ((SemWifiDisplay) wfd).mWfd;
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

    @Deprecated
    public int getIconIndex() {
        return 0;
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

    public boolean isDmrSupportedType(int type) {
        return this.mWfd.isDmrSupportedType(type);
    }

    public boolean isHighResolutionModeSupported() {
        return this.mWfd.isHighResolutionModeSupported();
    }

    public boolean isSupported(String feature) {
        return this.mWfd.isSupport(feature);
    }

    public boolean equals(Object o) {
        return (o instanceof SemWifiDisplay) && equals((SemWifiDisplay) o);
    }

    public boolean equals(SemWifiDisplay other) {
        return other != null
                && getDeviceAddress().equals(other.getDeviceAddress())
                && getDeviceName().equals(other.getDeviceName());
    }

    public int hashCode() {
        return getDeviceAddress().hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mWfd, 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int compareTo(SemWifiDisplay other) {
        return getDeviceName().compareTo(other.getDeviceName());
    }

    public int getDeviceInfo() {
        return this.mWfd.getDeviceInfo();
    }
}
