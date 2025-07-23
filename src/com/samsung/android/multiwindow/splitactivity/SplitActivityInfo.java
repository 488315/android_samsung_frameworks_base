package com.samsung.android.multiwindow.splitactivity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes6.dex */
public class SplitActivityInfo implements Parcelable {
    public static final String ANY_ACTIVITY = "*";
    public static final Parcelable.Creator<SplitActivityInfo> CREATOR = new Parcelable.Creator<SplitActivityInfo>() { // from class: com.samsung.android.multiwindow.splitactivity.SplitActivityInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SplitActivityInfo createFromParcel(Parcel parcel) {
            return new SplitActivityInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SplitActivityInfo[] newArray(int i) {
            return new SplitActivityInfo[i];
        }
    };
    private final int mMode;
    private final String mSourceName;
    private final String mTargetName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SplitActivityInfo(String str, String str2, int i) {
        this.mSourceName = str;
        this.mTargetName = str2;
        this.mMode = i;
    }

    protected SplitActivityInfo(Parcel parcel) {
        this.mSourceName = parcel.readString();
        this.mTargetName = parcel.readString();
        this.mMode = parcel.readInt();
    }

    boolean match(String str, String str2) {
        return this.mSourceName.equals(str) && this.mTargetName.equals(str2);
    }

    boolean matchWithWildcard(String str, String str2) {
        if (this.mSourceName.equals(str)) {
            return this.mTargetName.equals("*") || this.mTargetName.equals(str2);
        }
        return false;
    }

    public String getSourceName() {
        return this.mSourceName;
    }

    public String getTargetName() {
        return this.mTargetName;
    }

    public int getMode() {
        return this.mMode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SplitActivityInfo splitActivityInfo = (SplitActivityInfo) obj;
            if (this.mMode == splitActivityInfo.mMode && Objects.equals(this.mSourceName, splitActivityInfo.mSourceName) && Objects.equals(this.mTargetName, splitActivityInfo.mTargetName)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mSourceName, this.mTargetName, Integer.valueOf(this.mMode));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mSourceName);
        parcel.writeString(this.mTargetName);
        parcel.writeInt(this.mMode);
    }

    String toShortString() {
        return String.format("{ %s -> %s }", this.mSourceName, this.mTargetName);
    }
}
