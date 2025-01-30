package com.samsung.android.edge;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public final class EdgeLightingPolicy implements Parcelable {
    public static final Parcelable.Creator<EdgeLightingPolicy> CREATOR = new Parcelable.Creator<EdgeLightingPolicy>() { // from class: com.samsung.android.edge.EdgeLightingPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EdgeLightingPolicy createFromParcel(Parcel source) {
            return new EdgeLightingPolicy(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EdgeLightingPolicy[] newArray(int size) {
            return new EdgeLightingPolicy[size];
        }
    };
    public static final int TYPE_EXCLUDE_BLACK_LIST = 4;
    public static final int TYPE_EXCLUDE_SYSTEM_APP = 2;
    public static final int TYPE_INCLUDE_ALL_APP = 1;
    public static final int TYPE_NOT_CONNECTED_MODE = 8;
    private ArrayList<EdgeLightingPolicyInfo> mPolicyInfoList;
    private int mType;
    private long mVersion;

    public EdgeLightingPolicy() {
        this.mType = 0;
        this.mVersion = 0L;
        this.mPolicyInfoList = new ArrayList<>();
    }

    public EdgeLightingPolicy(Parcel source) {
        this.mType = 0;
        this.mVersion = 0L;
        this.mPolicyInfoList = new ArrayList<>();
        this.mType = source.readInt();
        this.mVersion = source.readLong();
        source.readTypedList(this.mPolicyInfoList, EdgeLightingPolicyInfo.CREATOR);
    }

    public void addEdgeLightingPolicyInfo(EdgeLightingPolicyInfo info) {
        this.mPolicyInfoList.add(info);
    }

    public ArrayList<EdgeLightingPolicyInfo> getEdgeLightingPolicyInfoList() {
        return this.mPolicyInfoList;
    }

    public void setPolicyType(int type) {
        this.mType = type;
    }

    public int getPolicyType() {
        return this.mType;
    }

    public void setPolicyVersion(long version) {
        this.mVersion = version;
    }

    public long getPolicyVersion() {
        return this.mVersion;
    }

    public String toString() {
        String str = "EdgeLightingPolicy{Type = " + this.mType + ", version = " + this.mVersion + "}";
        Iterator<EdgeLightingPolicyInfo> it = this.mPolicyInfoList.iterator();
        while (it.hasNext()) {
            EdgeLightingPolicyInfo info = it.next();
            str = str + " " + info.toString();
        }
        return str;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mType);
        out.writeLong(this.mVersion);
        out.writeTypedList(this.mPolicyInfoList);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }
}
