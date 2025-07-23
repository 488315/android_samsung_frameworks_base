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
        public SemEasySetupWifiScanSettings createFromParcel(Parcel parcel) {
            SemEasySetupWifiScanSettings semEasySetupWifiScanSettings = new SemEasySetupWifiScanSettings();
            parcel.readStringList(semEasySetupWifiScanSettings.ssidPatterns);
            semEasySetupWifiScanSettings.pendingIntentForIdlePopup = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
            semEasySetupWifiScanSettings.pendingIntentForSettings = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
            semEasySetupWifiScanSettings.minRssi = parcel.readInt();
            return semEasySetupWifiScanSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemEasySetupWifiScanSettings[] newArray(int i) {
            return new SemEasySetupWifiScanSettings[i];
        }
    };
    public static final int DEFAULT_EASY_SETUP_RSSI_BASE = -55;
    public int minRssi;
    public PendingIntent pendingIntentForIdlePopup;
    public PendingIntent pendingIntentForSettings;
    public List<String> ssidPatterns;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemEasySetupWifiScanSettings() {
        this.ssidPatterns = new ArrayList();
        this.minRssi = -55;
    }

    public SemEasySetupWifiScanSettings(SemEasySetupWifiScanSettings semEasySetupWifiScanSettings) {
        this.ssidPatterns = new ArrayList(semEasySetupWifiScanSettings.ssidPatterns);
        this.pendingIntentForIdlePopup = semEasySetupWifiScanSettings.pendingIntentForIdlePopup;
        this.pendingIntentForSettings = semEasySetupWifiScanSettings.pendingIntentForSettings;
        this.minRssi = semEasySetupWifiScanSettings.minRssi;
    }

    public boolean isActiveSeparateNetworkList() {
        return (this.ssidPatterns.isEmpty() || this.pendingIntentForSettings == null) ? false : true;
    }

    public boolean isActiveDeviceDetectionInIdle() {
        return (this.ssidPatterns.isEmpty() || this.pendingIntentForIdlePopup == null) ? false : true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(this.ssidPatterns);
        parcel.writeParcelable(this.pendingIntentForIdlePopup, i);
        parcel.writeParcelable(this.pendingIntentForSettings, i);
        parcel.writeInt(this.minRssi);
    }
}
