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
        public SplitActivityInfo createFromParcel(Parcel in) {
            return new SplitActivityInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SplitActivityInfo[] newArray(int size) {
            return new SplitActivityInfo[size];
        }
    };
    private final int mMode;
    private final String mSourceName;
    private final String mTargetName;

    public SplitActivityInfo(String sourceName, String targetName, int mode) {
        this.mSourceName = sourceName;
        this.mTargetName = targetName;
        this.mMode = mode;
    }

    protected SplitActivityInfo(Parcel in) {
        this.mSourceName = in.readString();
        this.mTargetName = in.readString();
        this.mMode = in.readInt();
    }

    boolean match(String sourceName, String targetName) {
        return this.mSourceName.equals(sourceName) && this.mTargetName.equals(targetName);
    }

    boolean matchWithWildcard(String sourceName, String targetName) {
        return this.mSourceName.equals(sourceName) && (this.mTargetName.equals("*") || this.mTargetName.equals(targetName));
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SplitActivityInfo that = (SplitActivityInfo) o;
        if (this.mMode == that.mMode && Objects.equals(this.mSourceName, that.mSourceName) && Objects.equals(this.mTargetName, that.mTargetName)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mSourceName, this.mTargetName, Integer.valueOf(this.mMode));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSourceName);
        dest.writeString(this.mTargetName);
        dest.writeInt(this.mMode);
    }

    String toShortString() {
        return String.format("{ %s -> %s }", this.mSourceName, this.mTargetName);
    }
}
