package android.telephony.satellite;

import android.annotation.SystemApi;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes3.dex */
public final class SatelliteCapabilities implements Parcelable {
    public static final Parcelable.Creator<SatelliteCapabilities> CREATOR = new Parcelable.Creator<SatelliteCapabilities>() { // from class: android.telephony.satellite.SatelliteCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteCapabilities createFromParcel(Parcel in) {
            return new SatelliteCapabilities(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteCapabilities[] newArray(int size) {
            return new SatelliteCapabilities[size];
        }
    };
    private Map<Integer, AntennaPosition> mAntennaPositionMap;
    private boolean mIsPointingRequired;
    private int mMaxBytesPerOutgoingDatagram;
    private Set<Integer> mSupportedRadioTechnologies;

    public SatelliteCapabilities(Set<Integer> supportedRadioTechnologies, boolean isPointingRequired, int maxBytesPerOutgoingDatagram, Map<Integer, AntennaPosition> antennaPositionMap) {
        this.mSupportedRadioTechnologies = supportedRadioTechnologies == null ? new HashSet<>() : supportedRadioTechnologies;
        this.mIsPointingRequired = isPointingRequired;
        this.mMaxBytesPerOutgoingDatagram = maxBytesPerOutgoingDatagram;
        this.mAntennaPositionMap = antennaPositionMap;
    }

    private SatelliteCapabilities(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        Set<Integer> set = this.mSupportedRadioTechnologies;
        if (set != null && !set.isEmpty()) {
            out.writeInt(this.mSupportedRadioTechnologies.size());
            Iterator<Integer> it = this.mSupportedRadioTechnologies.iterator();
            while (it.hasNext()) {
                int technology = it.next().intValue();
                out.writeInt(technology);
            }
        } else {
            out.writeInt(0);
        }
        out.writeBoolean(this.mIsPointingRequired);
        out.writeInt(this.mMaxBytesPerOutgoingDatagram);
        Map<Integer, AntennaPosition> map = this.mAntennaPositionMap;
        if (map != null && !map.isEmpty()) {
            int size = this.mAntennaPositionMap.size();
            out.writeInt(size);
            for (Map.Entry<Integer, AntennaPosition> entry : this.mAntennaPositionMap.entrySet()) {
                out.writeInt(entry.getKey().intValue());
                out.writeParcelable(entry.getValue(), flags);
            }
            return;
        }
        out.writeInt(0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SupportedRadioTechnology:");
        Set<Integer> set = this.mSupportedRadioTechnologies;
        if (set != null && !set.isEmpty()) {
            Iterator<Integer> it = this.mSupportedRadioTechnologies.iterator();
            while (it.hasNext()) {
                int technology = it.next().intValue();
                sb.append(technology);
                sb.append(",");
            }
        } else {
            sb.append("none,");
        }
        sb.append("isPointingRequired:");
        sb.append(this.mIsPointingRequired);
        sb.append(",");
        sb.append("maxBytesPerOutgoingDatagram:");
        sb.append(this.mMaxBytesPerOutgoingDatagram);
        sb.append(",");
        sb.append("antennaPositionMap:");
        sb.append(this.mAntennaPositionMap);
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SatelliteCapabilities that = (SatelliteCapabilities) o;
        if (Objects.equals(this.mSupportedRadioTechnologies, that.mSupportedRadioTechnologies) && this.mIsPointingRequired == that.mIsPointingRequired && this.mMaxBytesPerOutgoingDatagram == that.mMaxBytesPerOutgoingDatagram && Objects.equals(this.mAntennaPositionMap, that.mAntennaPositionMap)) {
            return true;
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

    public Map<Integer, AntennaPosition> getAntennaPositionMap() {
        return this.mAntennaPositionMap;
    }

    private void readFromParcel(Parcel in) {
        this.mSupportedRadioTechnologies = new HashSet();
        int numSupportedRadioTechnologies = in.readInt();
        if (numSupportedRadioTechnologies > 0) {
            for (int i = 0; i < numSupportedRadioTechnologies; i++) {
                this.mSupportedRadioTechnologies.add(Integer.valueOf(in.readInt()));
            }
        }
        this.mIsPointingRequired = in.readBoolean();
        this.mMaxBytesPerOutgoingDatagram = in.readInt();
        this.mAntennaPositionMap = new HashMap();
        int antennaPositionMapSize = in.readInt();
        for (int i2 = 0; i2 < antennaPositionMapSize; i2++) {
            int key = in.readInt();
            AntennaPosition antennaPosition = (AntennaPosition) in.readParcelable(AntennaPosition.class.getClassLoader(), AntennaPosition.class);
            this.mAntennaPositionMap.put(Integer.valueOf(key), antennaPosition);
        }
    }
}
