package android.telephony.data;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class UrspRule implements Parcelable {
    public static final Parcelable.Creator<UrspRule> CREATOR = new Parcelable.Creator<UrspRule>() { // from class: android.telephony.data.UrspRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UrspRule createFromParcel(Parcel parcel) {
            return new UrspRule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UrspRule[] newArray(int i) {
            return new UrspRule[i];
        }
    };
    public static final int MAX_URSP_PRECEDENCE = 255;
    public static final int MIN_URSP_PRECEDENCE = 0;
    private final int mPrecedence;
    private final List<RouteSelectionDescriptor> mRouteSelectionDescriptor;
    private final List<TrafficDescriptor> mTrafficDescriptors;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UrspRule(int i, List<TrafficDescriptor> list, List<RouteSelectionDescriptor> list2) {
        this.mPrecedence = i;
        ArrayList arrayList = new ArrayList();
        this.mTrafficDescriptors = arrayList;
        arrayList.addAll(list);
        ArrayList arrayList2 = new ArrayList();
        this.mRouteSelectionDescriptor = arrayList2;
        arrayList2.addAll(list2);
    }

    private UrspRule(Parcel parcel) {
        this.mPrecedence = parcel.readInt();
        this.mTrafficDescriptors = parcel.createTypedArrayList(TrafficDescriptor.CREATOR);
        this.mRouteSelectionDescriptor = parcel.createTypedArrayList(RouteSelectionDescriptor.CREATOR);
    }

    public int getPrecedence() {
        return this.mPrecedence;
    }

    public List<TrafficDescriptor> getTrafficDescriptors() {
        return this.mTrafficDescriptors;
    }

    public List<RouteSelectionDescriptor> getRouteSelectionDescriptor() {
        return this.mRouteSelectionDescriptor;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPrecedence);
        parcel.writeTypedList(this.mTrafficDescriptors, i);
        parcel.writeTypedList(this.mRouteSelectionDescriptor, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            UrspRule urspRule = (UrspRule) obj;
            if (this.mPrecedence == urspRule.mPrecedence && this.mTrafficDescriptors.size() == urspRule.mTrafficDescriptors.size() && this.mTrafficDescriptors.containsAll(urspRule.mTrafficDescriptors) && this.mRouteSelectionDescriptor.size() == urspRule.mRouteSelectionDescriptor.size() && this.mRouteSelectionDescriptor.containsAll(urspRule.mRouteSelectionDescriptor)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPrecedence), this.mTrafficDescriptors, this.mRouteSelectionDescriptor);
    }

    public String toString() {
        return "{.precedence = " + this.mPrecedence + ", .trafficDescriptors = " + this.mTrafficDescriptors + ", .routeSelectionDescriptor = " + this.mRouteSelectionDescriptor + "}";
    }
}
