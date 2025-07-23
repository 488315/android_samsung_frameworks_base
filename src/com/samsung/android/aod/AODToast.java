package com.samsung.android.aod;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes6.dex */
public class AODToast implements Parcelable {
    public static final Parcelable.Creator<AODToast> CREATOR = new Parcelable.Creator<AODToast>() { // from class: com.samsung.android.aod.AODToast.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AODToast createFromParcel(Parcel parcel) {
            return new AODToast(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AODToast[] newArray(int i) {
            return new AODToast[i];
        }
    };
    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    private int mDuration;
    private long mDurationMillis;
    private int mGravity;
    private float mHorizontalMargin;
    private String mText;
    private IBinder mToken;
    private float mVerticalMargin;
    private int mX;
    private int mY;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AODToast(Builder builder) {
        this.mToken = new Binder();
        this.mText = builder.mText;
        this.mDuration = builder.mDuration;
        this.mDurationMillis = builder.mDurationMillis;
        this.mGravity = builder.mGravity;
        this.mX = builder.mX;
        this.mY = builder.mY;
        this.mHorizontalMargin = builder.mHorizontalMargin;
        this.mVerticalMargin = builder.mVerticalMargin;
    }

    private AODToast(Parcel parcel) {
        this.mToken = parcel.readStrongBinder();
        this.mText = parcel.readString();
        this.mDuration = parcel.readInt();
        this.mDurationMillis = parcel.readLong();
        this.mGravity = parcel.readInt();
        this.mX = parcel.readInt();
        this.mY = parcel.readInt();
        this.mHorizontalMargin = parcel.readFloat();
        this.mVerticalMargin = parcel.readFloat();
    }

    public IBinder getToken() {
        return this.mToken;
    }

    public String getText() {
        return this.mText;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public long getDuratioinMillis() {
        return this.mDurationMillis;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getXOffset() {
        return this.mX;
    }

    public int getYOffset() {
        return this.mY;
    }

    public float getHorizontalMargin() {
        return this.mHorizontalMargin;
    }

    public float getVerticalMargin() {
        return this.mVerticalMargin;
    }

    public String toString() {
        return "[AODToast: text:(" + this.mText + ") duration:(" + this.mDuration + ") durationMillis:(" + this.mDurationMillis + ") gravity:(" + this.mGravity + ") xOffset:(" + this.mX + ") yOffset:(" + this.mY + ") hMargin:(" + this.mHorizontalMargin + ") vMargin:(" + this.mVerticalMargin + ")]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mToken);
        parcel.writeString(this.mText);
        parcel.writeInt(this.mDuration);
        parcel.writeLong(this.mDurationMillis);
        parcel.writeInt(this.mGravity);
        parcel.writeInt(this.mX);
        parcel.writeInt(this.mY);
        parcel.writeFloat(this.mHorizontalMargin);
        parcel.writeFloat(this.mVerticalMargin);
    }

    public static class Builder {
        private int mDuration;
        private long mDurationMillis;
        private int mGravity;
        private float mHorizontalMargin;
        private final String mText;
        private float mVerticalMargin;
        private int mX;
        private int mY;

        public Builder(String str) {
            this.mText = str;
        }

        public Builder setDuration(int i) {
            this.mDuration = i;
            return this;
        }

        public Builder setDurationInMillis(long j) {
            this.mDurationMillis = j;
            return this;
        }

        public Builder setGravity(int i, int i2, int i3) {
            this.mGravity = i;
            this.mX = i2;
            this.mY = i3;
            return this;
        }

        public void setMargin(float f, float f2) {
            this.mHorizontalMargin = f;
            this.mVerticalMargin = f2;
        }

        public AODToast build() {
            return new AODToast(this);
        }
    }
}
