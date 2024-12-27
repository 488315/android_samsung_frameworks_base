package com.samsung.android.wifi;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemEasySetupWifiScanSettings implements Parcelable {
    public static final Parcelable.Creator<SemEasySetupWifiScanSettings> CREATOR = new Parcelable.Creator<SemEasySetupWifiScanSettings>() { // from class: com.samsung.android.wifi.SemEasySetupWifiScanSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemEasySetupWifiScanSettings createFromParcel(Parcel in) {
            SemEasySetupWifiScanSettings settings = new SemEasySetupWifiScanSettings();
            in.readStringList(settings.ssidPatterns);
            settings.pendingIntentForIdlePopup = (PendingIntent) in.readParcelable(PendingIntent.class.getClassLoader());
            settings.pendingIntentForSettings = (PendingIntent) in.readParcelable(PendingIntent.class.getClassLoader());
            settings.minRssi = in.readInt();
            return settings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemEasySetupWifiScanSettings[] newArray(int size) {
            return new SemEasySetupWifiScanSettings[size];
        }
    };
    public static final int DEFAULT_EASY_SETUP_RSSI_BASE = -55;
    public int minRssi;
    public PendingIntent pendingIntentForIdlePopup;
    public PendingIntent pendingIntentForSettings;
    public List<String> ssidPatterns;

    public SemEasySetupWifiScanSettings() {
        this.ssidPatterns = new ArrayList();
        this.minRssi = -55;
    }

    public SemEasySetupWifiScanSettings(SemEasySetupWifiScanSettings source) {
        this.ssidPatterns = new ArrayList(source.ssidPatterns);
        this.pendingIntentForIdlePopup = source.pendingIntentForIdlePopup;
        this.pendingIntentForSettings = source.pendingIntentForSettings;
        this.minRssi = source.minRssi;
    }

    public boolean isActiveSeparateNetworkList() {
        return (this.ssidPatterns.isEmpty() || this.pendingIntentForSettings == null) ? false : true;
    }

    public boolean isActiveDeviceDetectionInIdle() {
        return (this.ssidPatterns.isEmpty() || this.pendingIntentForIdlePopup == null) ? false : true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.ssidPatterns);
        dest.writeParcelable(this.pendingIntentForIdlePopup, flags);
        dest.writeParcelable(this.pendingIntentForSettings, flags);
        dest.writeInt(this.minRssi);
    }
}
