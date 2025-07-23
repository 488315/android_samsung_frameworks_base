package android.telephony.data;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class NetworkSlicingConfig implements Parcelable {
    public static final Parcelable.Creator<NetworkSlicingConfig> CREATOR = new Parcelable.Creator<NetworkSlicingConfig>() { // from class: android.telephony.data.NetworkSlicingConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkSlicingConfig createFromParcel(Parcel parcel) {
            return new NetworkSlicingConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkSlicingConfig[] newArray(int i) {
            return new NetworkSlicingConfig[i];
        }
    };
    private final List<NetworkSliceInfo> mSliceInfo;
    private final List<UrspRule> mUrspRules;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NetworkSlicingConfig() {
        this.mUrspRules = new ArrayList();
        this.mSliceInfo = new ArrayList();
    }

    public NetworkSlicingConfig(List<UrspRule> list, List<NetworkSliceInfo> list2) {
        this();
        this.mUrspRules.addAll(list);
        this.mSliceInfo.addAll(list2);
    }

    public NetworkSlicingConfig(Parcel parcel) {
        this.mUrspRules = parcel.createTypedArrayList(UrspRule.CREATOR);
        this.mSliceInfo = parcel.createTypedArrayList(NetworkSliceInfo.CREATOR);
    }

    public List<UrspRule> getUrspRules() {
        return this.mUrspRules;
    }

    public List<NetworkSliceInfo> getSliceInfo() {
        return this.mSliceInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mUrspRules, i);
        parcel.writeTypedList(this.mSliceInfo, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            NetworkSlicingConfig networkSlicingConfig = (NetworkSlicingConfig) obj;
            if (this.mUrspRules.size() == networkSlicingConfig.mUrspRules.size() && this.mUrspRules.containsAll(networkSlicingConfig.mUrspRules) && this.mSliceInfo.size() == networkSlicingConfig.mSliceInfo.size() && this.mSliceInfo.containsAll(networkSlicingConfig.mSliceInfo)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mUrspRules, this.mSliceInfo);
    }

    public String toString() {
        return "{.urspRules = " + this.mUrspRules + ", .sliceInfo = " + this.mSliceInfo + "}";
    }
}
