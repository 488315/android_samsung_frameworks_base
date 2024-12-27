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
        public EdgeLightingPolicyInfo createFromParcel(Parcel source) {
            return new EdgeLightingPolicyInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EdgeLightingPolicyInfo[] newArray(int size) {
            return new EdgeLightingPolicyInfo[size];
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

    public EdgeLightingPolicyInfo(String packageName, int category, int range) {
        this.packageName = packageName;
        this.category = category;
        this.range = 65535 & range;
    }

    public EdgeLightingPolicyInfo(Parcel source) {
        this.packageName = source.readString();
        this.category = source.readInt();
        this.range = source.readInt();
    }

    public String toString() {
        String str = "EdgeLightingPolicyInfo{packageName= " + this.packageName;
        return str + ", category= " + this.category + ", range= " + this.range + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeInt(this.category);
        dest.writeInt(this.range);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
