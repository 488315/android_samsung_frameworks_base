package com.samsung.android.cocktailbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class FeedsInfo implements Parcelable {
    public static final Parcelable.Creator<FeedsInfo> CREATOR = new Parcelable.Creator<FeedsInfo>() { // from class: com.samsung.android.cocktailbar.FeedsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeedsInfo createFromParcel(Parcel parcel) {
            return new FeedsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeedsInfo[] newArray(int i) {
            return new FeedsInfo[i];
        }
    };
    public Bundle extras;
    public CharSequence feedsText;
    public int icon;
    public Bitmap largeIcon;
    public String packageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FeedsInfo(Parcel parcel) {
        this.extras = new Bundle();
        this.feedsText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.icon = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.largeIcon = Bitmap.CREATOR.createFromParcel(parcel);
        }
        this.packageName = parcel.readString();
    }

    public static final class Builder {
        private Bundle mExtras;
        private CharSequence mFeedsText;
        private int mIcon;
        private Bitmap mLargeIcon;
        private String mPackageName;

        public Builder(CharSequence charSequence, String str) {
            this.mFeedsText = charSequence;
            this.mPackageName = str;
        }

        public Builder setIcon(int i) {
            this.mIcon = i;
            return this;
        }

        public Builder setLargeIcon(Bitmap bitmap) {
            this.mLargeIcon = bitmap;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public FeedsInfo build() {
            FeedsInfo feedsInfo = new FeedsInfo(this.mFeedsText, this.mPackageName);
            feedsInfo.icon = this.mIcon;
            feedsInfo.largeIcon = this.mLargeIcon;
            feedsInfo.extras = this.mExtras != null ? new Bundle(this.mExtras) : new Bundle();
            return feedsInfo;
        }
    }

    private FeedsInfo(CharSequence charSequence, String str) {
        this.extras = new Bundle();
        this.feedsText = charSequence;
        this.packageName = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        TextUtils.writeToParcel(this.feedsText, parcel, i);
        parcel.writeInt(this.icon);
        if (this.largeIcon != null) {
            parcel.writeInt(1);
            this.largeIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.packageName);
    }
}
