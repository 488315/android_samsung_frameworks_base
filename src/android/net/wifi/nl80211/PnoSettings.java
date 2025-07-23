package android.net.wifi.nl80211;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class PnoSettings implements Parcelable {
    public static final Parcelable.Creator<PnoSettings> CREATOR = new Parcelable.Creator<PnoSettings>() { // from class: android.net.wifi.nl80211.PnoSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PnoSettings createFromParcel(Parcel parcel) {
            PnoSettings pnoSettings = new PnoSettings();
            pnoSettings.mIntervalMs = parcel.readLong();
            pnoSettings.mMin2gRssi = parcel.readInt();
            pnoSettings.mMin5gRssi = parcel.readInt();
            pnoSettings.mMin6gRssi = parcel.readInt();
            pnoSettings.mScanIterations = parcel.readInt();
            pnoSettings.mScanIntervalMultiplier = parcel.readInt();
            pnoSettings.mPnoNetworks = new ArrayList();
            parcel.readTypedList(pnoSettings.mPnoNetworks, PnoNetwork.CREATOR);
            return pnoSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PnoSettings[] newArray(int i) {
            return new PnoSettings[i];
        }
    };
    private long mIntervalMs;
    private int mMin2gRssi;
    private int mMin5gRssi;
    private int mMin6gRssi;
    private List<PnoNetwork> mPnoNetworks;
    private int mScanIntervalMultiplier;
    private int mScanIterations;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getIntervalMillis() {
        return this.mIntervalMs;
    }

    public void setIntervalMillis(long j) {
        this.mIntervalMs = j;
    }

    public int getMin2gRssiDbm() {
        return this.mMin2gRssi;
    }

    public void setMin2gRssiDbm(int i) {
        this.mMin2gRssi = i;
    }

    public int getMin5gRssiDbm() {
        return this.mMin5gRssi;
    }

    public void setMin5gRssiDbm(int i) {
        this.mMin5gRssi = i;
    }

    public int getMin6gRssiDbm() {
        return this.mMin6gRssi;
    }

    public void setMin6gRssiDbm(int i) {
        this.mMin6gRssi = i;
    }

    public int getScanIterations() {
        return this.mScanIterations;
    }

    public void setScanIterations(int i) {
        this.mScanIterations = i;
    }

    public int getScanIntervalMultiplier() {
        return this.mScanIntervalMultiplier;
    }

    public void setScanIntervalMultiplier(int i) {
        this.mScanIntervalMultiplier = i;
    }

    public List<PnoNetwork> getPnoNetworks() {
        return this.mPnoNetworks;
    }

    public void setPnoNetworks(List<PnoNetwork> list) {
        this.mPnoNetworks = list;
    }

    public boolean equals(Object obj) {
        PnoSettings pnoSettings;
        if (this == obj) {
            return true;
        }
        return (obj instanceof PnoSettings) && (pnoSettings = (PnoSettings) obj) != null && this.mIntervalMs == pnoSettings.mIntervalMs && this.mMin2gRssi == pnoSettings.mMin2gRssi && this.mMin5gRssi == pnoSettings.mMin5gRssi && this.mMin6gRssi == pnoSettings.mMin6gRssi && this.mScanIterations == pnoSettings.mScanIterations && this.mScanIntervalMultiplier == pnoSettings.mScanIntervalMultiplier && this.mPnoNetworks.equals(pnoSettings.mPnoNetworks);
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mIntervalMs), Integer.valueOf(this.mMin2gRssi), Integer.valueOf(this.mMin5gRssi), Integer.valueOf(this.mMin6gRssi), Integer.valueOf(this.mScanIterations), Integer.valueOf(this.mScanIntervalMultiplier), this.mPnoNetworks);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mIntervalMs);
        parcel.writeInt(this.mMin2gRssi);
        parcel.writeInt(this.mMin5gRssi);
        parcel.writeInt(this.mMin6gRssi);
        parcel.writeInt(this.mScanIterations);
        parcel.writeInt(this.mScanIntervalMultiplier);
        parcel.writeTypedList(this.mPnoNetworks);
    }
}
