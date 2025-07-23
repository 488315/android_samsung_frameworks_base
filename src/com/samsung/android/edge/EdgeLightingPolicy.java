package com.samsung.android.edge;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public final class EdgeLightingPolicy implements Parcelable {
    public static final Parcelable.Creator<EdgeLightingPolicy> CREATOR = new Parcelable.Creator<EdgeLightingPolicy>() { // from class: com.samsung.android.edge.EdgeLightingPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EdgeLightingPolicy createFromParcel(Parcel parcel) {
            return new EdgeLightingPolicy(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EdgeLightingPolicy[] newArray(int i) {
            return new EdgeLightingPolicy[i];
        }
    };
    public static final int TYPE_EXCLUDE_BLACK_LIST = 4;
    public static final int TYPE_EXCLUDE_SYSTEM_APP = 2;
    public static final int TYPE_INCLUDE_ALL_APP = 1;
    public static final int TYPE_NOT_CONNECTED_MODE = 8;
    private ArrayList<EdgeLightingPolicyInfo> mPolicyInfoList;
    private int mType;
    private long mVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public EdgeLightingPolicy() {
        this.mType = 0;
        this.mVersion = 0L;
        this.mPolicyInfoList = new ArrayList<>();
    }

    public EdgeLightingPolicy(Parcel parcel) {
        this.mType = 0;
        this.mVersion = 0L;
        this.mPolicyInfoList = new ArrayList<>();
        this.mType = parcel.readInt();
        this.mVersion = parcel.readLong();
        parcel.readTypedList(this.mPolicyInfoList, EdgeLightingPolicyInfo.CREATOR);
    }

    public void addEdgeLightingPolicyInfo(EdgeLightingPolicyInfo edgeLightingPolicyInfo) {
        this.mPolicyInfoList.add(edgeLightingPolicyInfo);
    }

    public ArrayList<EdgeLightingPolicyInfo> getEdgeLightingPolicyInfoList() {
        return this.mPolicyInfoList;
    }

    public void setPolicyType(int i) {
        this.mType = i;
    }

    public int getPolicyType() {
        return this.mType;
    }

    public void setPolicyVersion(long j) {
        this.mVersion = j;
    }

    public long getPolicyVersion() {
        return this.mVersion;
    }

    public String toString() {
        String str = "EdgeLightingPolicy{Type = " + this.mType + ", version = " + this.mVersion + "}";
        Iterator<EdgeLightingPolicyInfo> it = this.mPolicyInfoList.iterator();
        while (it.hasNext()) {
            str = str + " " + it.next().toString();
        }
        return str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeLong(this.mVersion);
        parcel.writeTypedList(this.mPolicyInfoList);
    }
}
