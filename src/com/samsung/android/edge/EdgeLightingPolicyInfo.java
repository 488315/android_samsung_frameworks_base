package com.samsung.android.edge;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public final class EdgeLightingPolicyInfo implements Parcelable {
    public static final int CATEGORY_BLACK = 2;
    public static final int CATEGORY_WHITE = 1;
    public static final Parcelable.Creator<EdgeLightingPolicyInfo> CREATOR = new Parcelable.Creator<EdgeLightingPolicyInfo>() { // from class: com.samsung.android.edge.EdgeLightingPolicyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EdgeLightingPolicyInfo createFromParcel(Parcel parcel) {
            return new EdgeLightingPolicyInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EdgeLightingPolicyInfo[] newArray(int i) {
            return new EdgeLightingPolicyInfo[i];
        }
    };
    private static final int RANGE_MASK = 65535;
    public static final int RANGE_NOTIFICATION = 1;
    public static final int RANGE_PRIVATE_MASK = 65280;
    public static final int RANGE_PRIVATE_NOTIFICATION_AFTER_WAKEUP = 1024;
    public static final int RANGE_PRIVATE_NOT_HUN_BUT_NOTIFICATION = 256;
    public static final int RANGE_PRIVATE_TOAST = 512;
    public static final int RANGE_PUBLIC_ALL = 7;
    public static final int RANGE_PUBLIC_MASK = 255;
    public static final int RANGE_RESERVED_FLAG = 65536;
    public static final int RANGE_WAKE_LOCK = 4;
    public static final int RANGE_WAKE_UP = 2;
    public final int category;
    public final String packageName;
    public final int range;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public EdgeLightingPolicyInfo(String str, int i, int i2) {
        this.packageName = str;
        this.category = i;
        this.range = 65535 & i2;
    }

    public EdgeLightingPolicyInfo(Parcel parcel) {
        this.packageName = parcel.readString();
        this.category = parcel.readInt();
        this.range = parcel.readInt();
    }

    public String toString() {
        return ("EdgeLightingPolicyInfo{packageName= " + this.packageName) + ", category= " + this.category + ", range= " + this.range + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeInt(this.category);
        parcel.writeInt(this.range);
    }
}
