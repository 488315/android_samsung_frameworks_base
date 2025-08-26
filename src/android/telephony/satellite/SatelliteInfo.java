package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SystemApi
/* loaded from: classes4.dex */
public final class SatelliteInfo implements Parcelable {
    public static final Parcelable.Creator<SatelliteInfo> CREATOR = new Parcelable.Creator<SatelliteInfo>() { // from class: android.telephony.satellite.SatelliteInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteInfo createFromParcel(Parcel parcel) {
            return new SatelliteInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteInfo[] newArray(int i) {
            return new SatelliteInfo[i];
        }
    };
    private List<Integer> mBandList;
    private final List<EarfcnRange> mEarfcnRangeList;
    private UUID mId;
    private SatellitePosition mPosition;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected SatelliteInfo(Parcel parcel) throws ClassNotFoundException, IOException {
        ParcelUuid parcelUuid = (ParcelUuid) parcel.readParcelable(ParcelUuid.class.getClassLoader(), ParcelUuid.class);
        if (parcelUuid != null) {
            this.mId = parcelUuid.getUuid();
        }
        this.mPosition = (SatellitePosition) parcel.readParcelable(SatellitePosition.class.getClassLoader(), SatellitePosition.class);
        ArrayList arrayList = new ArrayList();
        this.mBandList = arrayList;
        parcel.readList(arrayList, Integer.class.getClassLoader(), Integer.class);
        this.mEarfcnRangeList = parcel.createTypedArrayList(EarfcnRange.CREATOR);
    }

    public SatelliteInfo(UUID uuid, SatellitePosition satellitePosition, List<Integer> list, List<EarfcnRange> list2) {
        this.mId = uuid;
        this.mPosition = satellitePosition;
        this.mBandList = list;
        this.mEarfcnRangeList = list2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(new ParcelUuid(this.mId), i);
        parcel.writeParcelable(this.mPosition, i);
        parcel.writeList(this.mBandList);
        parcel.writeTypedList(this.mEarfcnRangeList);
    }

    public UUID getSatelliteId() {
        return this.mId;
    }

    public SatellitePosition getSatellitePosition() {
        return this.mPosition;
    }

    public List<Integer> getBands() {
        return this.mBandList;
    }

    public List<EarfcnRange> getEarfcnRanges() {
        return this.mEarfcnRangeList;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SatelliteInfo) {
            SatelliteInfo satelliteInfo = (SatelliteInfo) obj;
            if (this.mId.equals(satelliteInfo.mId) && Objects.equals(this.mPosition, satelliteInfo.mPosition) && Objects.equals(this.mBandList, satelliteInfo.mBandList) && this.mEarfcnRangeList.equals(satelliteInfo.mEarfcnRangeList)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (Objects.hash(this.mId, this.mPosition, this.mEarfcnRangeList) * 31) + Objects.hashCode(this.mBandList);
    }

    public String toString() {
        return "SatelliteInfo{mId=" + this.mId + ", mPosition=" + this.mPosition + ", mBandList=" + this.mBandList + ", mEarfcnRangeList=" + this.mEarfcnRangeList + '}';
    }
}
