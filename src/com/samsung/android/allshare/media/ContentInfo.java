package com.samsung.android.allshare.media;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class ContentInfo implements Parcelable {
    public static final Parcelable.Creator<ContentInfo> CREATOR = new Parcelable.Creator<ContentInfo>() { // from class: com.samsung.android.allshare.media.ContentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentInfo createFromParcel(Parcel parcel) {
            return new ContentInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentInfo[] newArray(int i) {
            return new ContentInfo[i];
        }
    };
    private long mStartingPosition;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ContentInfo() {
    }

    private ContentInfo(Builder builder) {
        this.mStartingPosition = builder.mStartingPosition;
    }

    public long getStartingPosition() {
        return this.mStartingPosition;
    }

    public static class Builder {
        private long mStartingPosition = 0;

        public Builder setStartingPosition(long j) {
            this.mStartingPosition = j;
            return this;
        }

        public ContentInfo build() {
            if (this.mStartingPosition < 0) {
                return null;
            }
            return new ContentInfo(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mStartingPosition);
    }

    private void readFromParcel(Parcel parcel) {
        this.mStartingPosition = parcel.readLong();
    }

    private ContentInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
