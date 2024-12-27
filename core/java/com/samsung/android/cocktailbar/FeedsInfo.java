package com.samsung.android.cocktailbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes5.dex */
public class FeedsInfo implements Parcelable {
    public static final Parcelable.Creator<FeedsInfo> CREATOR = new Parcelable.Creator<FeedsInfo>() { // from class: com.samsung.android.cocktailbar.FeedsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeedsInfo createFromParcel(Parcel parcel) {
            return new FeedsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeedsInfo[] newArray(int size) {
            return new FeedsInfo[size];
        }
    };
    public Bundle extras;
    public CharSequence feedsText;
    public int icon;
    public Bitmap largeIcon;
    public String packageName;

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

        public Builder(CharSequence feedsText, String packageName) {
            this.mFeedsText = feedsText;
            this.mPackageName = packageName;
        }

        public Builder setIcon(int icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setLargeIcon(Bitmap icon) {
            this.mLargeIcon = icon;
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public FeedsInfo build() {
            FeedsInfo fi = new FeedsInfo(this.mFeedsText, this.mPackageName);
            fi.icon = this.mIcon;
            fi.largeIcon = this.mLargeIcon;
            fi.extras = this.mExtras != null ? new Bundle(this.mExtras) : new Bundle();
            return fi;
        }
    }

    private FeedsInfo(CharSequence feedsText, String packageName) {
        this.extras = new Bundle();
        this.feedsText = feedsText;
        this.packageName = packageName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        TextUtils.writeToParcel(this.feedsText, out, flags);
        out.writeInt(this.icon);
        if (this.largeIcon != null) {
            out.writeInt(1);
            this.largeIcon.writeToParcel(out, 0);
        } else {
            out.writeInt(0);
        }
        out.writeString(this.packageName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
