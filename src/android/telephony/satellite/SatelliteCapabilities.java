package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes4.dex */
public final class SatelliteCapabilities implements Parcelable {
    public static final Parcelable.Creator<SatelliteCapabilities> CREATOR = new Parcelable.Creator<SatelliteCapabilities>() { // from class: android.telephony.satellite.SatelliteCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteCapabilities createFromParcel(Parcel parcel) {
            return new SatelliteCapabilities(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteCapabilities[] newArray(int i) {
            return new SatelliteCapabilities[i];
        }
    };
    private Map<Integer, AntennaPosition> mAntennaPositionMap;
    private boolean mIsPointingRequired;
    private int mMaxBytesPerOutgoingDatagram;
    private Set<Integer> mSupportedRadioTechnologies;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SatelliteCapabilities(Set<Integer> set, boolean z, int i, Map<Integer, AntennaPosition> map) {
        this.mSupportedRadioTechnologies = set == null ? new HashSet<>() : set;
        this.mIsPointingRequired = z;
        this.mMaxBytesPerOutgoingDatagram = i;
        this.mAntennaPositionMap = map;
    }

    private SatelliteCapabilities(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Set<Integer> set = this.mSupportedRadioTechnologies;
        if (set != null && !set.isEmpty()) {
            parcel.writeInt(this.mSupportedRadioTechnologies.size());
            Iterator<Integer> it = this.mSupportedRadioTechnologies.iterator();
            while (it.hasNext()) {
                parcel.writeInt(it.next().intValue());
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBoolean(this.mIsPointingRequired);
        parcel.writeInt(this.mMaxBytesPerOutgoingDatagram);
        Map<Integer, AntennaPosition> map = this.mAntennaPositionMap;
        if (map != null && !map.isEmpty()) {
            parcel.writeInt(this.mAntennaPositionMap.size());
            for (Map.Entry<Integer, AntennaPosition> entry : this.mAntennaPositionMap.entrySet()) {
                parcel.writeInt(entry.getKey().intValue());
                parcel.writeParcelable(entry.getValue(), i);
            }
            return;
        }
        parcel.writeInt(0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SupportedRadioTechnology:");
        Set<Integer> set = this.mSupportedRadioTechnologies;
        if (set != null && !set.isEmpty()) {
            Iterator<Integer> it = this.mSupportedRadioTechnologies.iterator();
            while (it.hasNext()) {
                sb.append(it.next().intValue());
                sb.append(",");
            }
        } else {
            sb.append("none,");
        }
        sb.append("isPointingRequired:");
        sb.append(this.mIsPointingRequired);
        sb.append(",maxBytesPerOutgoingDatagram:");
        sb.append(this.mMaxBytesPerOutgoingDatagram);
        sb.append(",antennaPositionMap:");
        sb.append(this.mAntennaPositionMap);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SatelliteCapabilities satelliteCapabilities = (SatelliteCapabilities) obj;
            if (Objects.equals(this.mSupportedRadioTechnologies, satelliteCapabilities.mSupportedRadioTechnologies) && this.mIsPointingRequired == satelliteCapabilities.mIsPointingRequired && this.mMaxBytesPerOutgoingDatagram == satelliteCapabilities.mMaxBytesPerOutgoingDatagram && Objects.equals(this.mAntennaPositionMap, satelliteCapabilities.mAntennaPositionMap)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mSupportedRadioTechnologies, Boolean.valueOf(this.mIsPointingRequired), Integer.valueOf(this.mMaxBytesPerOutgoingDatagram), this.mAntennaPositionMap);
    }

    public Set<Integer> getSupportedRadioTechnologies() {
        return this.mSupportedRadioTechnologies;
    }

    public boolean isPointingRequired() {
        return this.mIsPointingRequired;
    }

    public int getMaxBytesPerOutgoingDatagram() {
        return this.mMaxBytesPerOutgoingDatagram;
    }

    public void setMaxBytesPerOutgoingDatagram(int i) {
        this.mMaxBytesPerOutgoingDatagram = i;
    }

    public Map<Integer, AntennaPosition> getAntennaPositionMap() {
        return this.mAntennaPositionMap;
    }

    private void readFromParcel(Parcel parcel) {
        this.mSupportedRadioTechnologies = new HashSet();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            for (int i = 0; i < readInt; i++) {
                this.mSupportedRadioTechnologies.add(Integer.valueOf(parcel.readInt()));
            }
        }
        this.mIsPointingRequired = parcel.readBoolean();
        this.mMaxBytesPerOutgoingDatagram = parcel.readInt();
        this.mAntennaPositionMap = new HashMap();
        int readInt2 = parcel.readInt();
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.mAntennaPositionMap.put(Integer.valueOf(parcel.readInt()), (AntennaPosition) parcel.readParcelable(AntennaPosition.class.getClassLoader(), AntennaPosition.class));
        }
    }
}
