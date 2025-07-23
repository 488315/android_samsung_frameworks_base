package android.telephony.data;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class RouteSelectionDescriptor implements Parcelable {
    public static final Parcelable.Creator<RouteSelectionDescriptor> CREATOR = new Parcelable.Creator<RouteSelectionDescriptor>() { // from class: android.telephony.data.RouteSelectionDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RouteSelectionDescriptor createFromParcel(Parcel parcel) {
            return new RouteSelectionDescriptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RouteSelectionDescriptor[] newArray(int i) {
            return new RouteSelectionDescriptor[i];
        }
    };
    public static final int MAX_ROUTE_PRECEDENCE = 255;
    public static final int MAX_ROUTE_SSC_MODE = 3;
    public static final int MIN_ROUTE_PRECEDENCE = 0;
    public static final int MIN_ROUTE_SSC_MODE = 1;
    public static final int ROUTE_SSC_MODE_1 = 1;
    public static final int ROUTE_SSC_MODE_2 = 2;
    public static final int ROUTE_SSC_MODE_3 = 3;
    public static final int SESSION_TYPE_IPV4 = 0;
    public static final int SESSION_TYPE_IPV4V6 = 2;
    public static final int SESSION_TYPE_IPV6 = 1;
    private final List<String> mDnn;
    private final int mPrecedence;
    private final int mSessionType;
    private final List<NetworkSliceInfo> mSliceInfo;
    private final int mSscMode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RouteSessionType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RouteSscMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RouteSelectionDescriptor(int i, int i2, int i3, List<NetworkSliceInfo> list, List<String> list2) {
        this.mPrecedence = i;
        this.mSessionType = i2;
        this.mSscMode = i3;
        ArrayList arrayList = new ArrayList();
        this.mSliceInfo = arrayList;
        arrayList.addAll(list);
        ArrayList arrayList2 = new ArrayList();
        this.mDnn = arrayList2;
        arrayList2.addAll(list2);
    }

    private RouteSelectionDescriptor(Parcel parcel) {
        this.mPrecedence = parcel.readInt();
        this.mSessionType = parcel.readInt();
        this.mSscMode = parcel.readInt();
        this.mSliceInfo = parcel.createTypedArrayList(NetworkSliceInfo.CREATOR);
        ArrayList arrayList = new ArrayList();
        this.mDnn = arrayList;
        parcel.readStringList(arrayList);
    }

    public int getPrecedence() {
        return this.mPrecedence;
    }

    public int getSessionType() {
        return this.mSessionType;
    }

    public int getSscMode() {
        return this.mSscMode;
    }

    public List<NetworkSliceInfo> getSliceInfo() {
        return this.mSliceInfo;
    }

    public List<String> getDataNetworkName() {
        return this.mDnn;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPrecedence);
        parcel.writeInt(this.mSessionType);
        parcel.writeInt(this.mSscMode);
        parcel.writeTypedList(this.mSliceInfo, i);
        parcel.writeStringList(this.mDnn);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            RouteSelectionDescriptor routeSelectionDescriptor = (RouteSelectionDescriptor) obj;
            if (this.mPrecedence == routeSelectionDescriptor.mPrecedence && this.mSessionType == routeSelectionDescriptor.mSessionType && this.mSscMode == routeSelectionDescriptor.mSscMode && this.mSliceInfo.size() == routeSelectionDescriptor.mSliceInfo.size() && this.mSliceInfo.containsAll(routeSelectionDescriptor.mSliceInfo) && this.mDnn.size() == routeSelectionDescriptor.mDnn.size() && this.mDnn.containsAll(routeSelectionDescriptor.mDnn)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPrecedence), Integer.valueOf(this.mSessionType), Integer.valueOf(this.mSscMode), this.mSliceInfo, this.mDnn);
    }

    public String toString() {
        return "{.precedence = " + this.mPrecedence + ", .sessionType = " + this.mSessionType + ", .sscMode = " + this.mSscMode + ", .sliceInfo = " + this.mSliceInfo + ", .dnn = " + this.mDnn + "}";
    }
}
