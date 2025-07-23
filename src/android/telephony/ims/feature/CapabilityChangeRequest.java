package android.telephony.ims.feature;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SystemApi
/* loaded from: classes4.dex */
public final class CapabilityChangeRequest implements Parcelable {
    public static final Parcelable.Creator<CapabilityChangeRequest> CREATOR = new Parcelable.Creator<CapabilityChangeRequest>() { // from class: android.telephony.ims.feature.CapabilityChangeRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CapabilityChangeRequest createFromParcel(Parcel parcel) {
            return new CapabilityChangeRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CapabilityChangeRequest[] newArray(int i) {
            return new CapabilityChangeRequest[i];
        }
    };
    private final Set<CapabilityPair> mCapabilitiesToDisable;
    private final Set<CapabilityPair> mCapabilitiesToEnable;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class CapabilityPair {
        private final int mCapability;
        private final int radioTech;

        public CapabilityPair(int i, int i2) {
            this.mCapability = i;
            this.radioTech = i2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CapabilityPair)) {
                return false;
            }
            CapabilityPair capabilityPair = (CapabilityPair) obj;
            return getCapability() == capabilityPair.getCapability() && getRadioTech() == capabilityPair.getRadioTech();
        }

        public int hashCode() {
            return (getCapability() * 31) + getRadioTech();
        }

        public int getCapability() {
            return this.mCapability;
        }

        public int getRadioTech() {
            return this.radioTech;
        }

        public String toString() {
            return "CapabilityPair{mCapability=" + this.mCapability + ", radioTech=" + this.radioTech + '}';
        }
    }

    public CapabilityChangeRequest() {
        this.mCapabilitiesToEnable = new ArraySet();
        this.mCapabilitiesToDisable = new ArraySet();
    }

    public void addCapabilitiesToEnableForTech(int i, int i2) {
        addAllCapabilities(this.mCapabilitiesToEnable, i, i2);
    }

    public void addCapabilitiesToDisableForTech(int i, int i2) {
        addAllCapabilities(this.mCapabilitiesToDisable, i, i2);
    }

    public List<CapabilityPair> getCapabilitiesToEnable() {
        return new ArrayList(this.mCapabilitiesToEnable);
    }

    public List<CapabilityPair> getCapabilitiesToDisable() {
        return new ArrayList(this.mCapabilitiesToDisable);
    }

    private void addAllCapabilities(Set<CapabilityPair> set, int i, int i2) {
        long highestOneBit = Long.highestOneBit(i);
        for (int i3 = 1; i3 <= highestOneBit; i3 *= 2) {
            if ((i3 & i) > 0) {
                set.add(new CapabilityPair(i3, i2));
            }
        }
    }

    protected CapabilityChangeRequest(Parcel parcel) {
        int readInt = parcel.readInt();
        this.mCapabilitiesToEnable = new ArraySet(readInt);
        for (int i = 0; i < readInt; i++) {
            this.mCapabilitiesToEnable.add(new CapabilityPair(parcel.readInt(), parcel.readInt()));
        }
        int readInt2 = parcel.readInt();
        this.mCapabilitiesToDisable = new ArraySet(readInt2);
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.mCapabilitiesToDisable.add(new CapabilityPair(parcel.readInt(), parcel.readInt()));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCapabilitiesToEnable.size());
        for (CapabilityPair capabilityPair : this.mCapabilitiesToEnable) {
            parcel.writeInt(capabilityPair.getCapability());
            parcel.writeInt(capabilityPair.getRadioTech());
        }
        parcel.writeInt(this.mCapabilitiesToDisable.size());
        for (CapabilityPair capabilityPair2 : this.mCapabilitiesToDisable) {
            parcel.writeInt(capabilityPair2.getCapability());
            parcel.writeInt(capabilityPair2.getRadioTech());
        }
    }

    public String toString() {
        return "CapabilityChangeRequest{mCapabilitiesToEnable=" + this.mCapabilitiesToEnable + ", mCapabilitiesToDisable=" + this.mCapabilitiesToDisable + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CapabilityChangeRequest)) {
            return false;
        }
        CapabilityChangeRequest capabilityChangeRequest = (CapabilityChangeRequest) obj;
        if (this.mCapabilitiesToEnable.equals(capabilityChangeRequest.mCapabilitiesToEnable)) {
            return this.mCapabilitiesToDisable.equals(capabilityChangeRequest.mCapabilitiesToDisable);
        }
        return false;
    }

    public int hashCode() {
        return (this.mCapabilitiesToEnable.hashCode() * 31) + this.mCapabilitiesToDisable.hashCode();
    }
}
