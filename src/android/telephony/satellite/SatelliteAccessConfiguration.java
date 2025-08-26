package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class SatelliteAccessConfiguration implements Parcelable {
    public static final Parcelable.Creator<SatelliteAccessConfiguration> CREATOR = new Parcelable.Creator<SatelliteAccessConfiguration>() { // from class: android.telephony.satellite.SatelliteAccessConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteAccessConfiguration createFromParcel(Parcel parcel) {
            return new SatelliteAccessConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteAccessConfiguration[] newArray(int i) {
            return new SatelliteAccessConfiguration[i];
        }
    };
    private List<SatelliteInfo> mSatelliteInfoList;
    private List<Integer> mTagIdList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SatelliteAccessConfiguration(List<SatelliteInfo> list, List<Integer> list2) {
        this.mSatelliteInfoList = list;
        this.mTagIdList = list2;
    }

    public SatelliteAccessConfiguration(Parcel parcel) throws ClassNotFoundException, IOException {
        this.mSatelliteInfoList = parcel.createTypedArrayList(SatelliteInfo.CREATOR);
        ArrayList arrayList = new ArrayList();
        this.mTagIdList = arrayList;
        parcel.readList(arrayList, Integer.class.getClassLoader(), Integer.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mSatelliteInfoList);
        parcel.writeList(this.mTagIdList);
    }

    public List<SatelliteInfo> getSatelliteInfos() {
        return this.mSatelliteInfoList;
    }

    public List<Integer> getTagIds() {
        return this.mTagIdList;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SatelliteAccessConfiguration) {
            SatelliteAccessConfiguration satelliteAccessConfiguration = (SatelliteAccessConfiguration) obj;
            if (this.mSatelliteInfoList.equals(satelliteAccessConfiguration.mSatelliteInfoList) && Objects.equals(this.mTagIdList, satelliteAccessConfiguration.mTagIdList)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (Objects.hash(this.mSatelliteInfoList) * 31) + Objects.hashCode(this.mTagIdList);
    }

    public String toString() {
        return "SatelliteAccessConfiguration{mSatelliteInfoList=" + this.mSatelliteInfoList + ", mTagIds=" + this.mTagIdList + '}';
    }
}
