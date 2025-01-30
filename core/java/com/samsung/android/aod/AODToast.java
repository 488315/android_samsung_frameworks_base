package com.samsung.android.aod;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public class AODToast implements Parcelable {
  public static final Parcelable.Creator<AODToast> CREATOR =
      new Parcelable.Creator<AODToast>() { // from class: com.samsung.android.aod.AODToast.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AODToast createFromParcel(Parcel in) {
          return new AODToast(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AODToast[] newArray(int size) {
          return new AODToast[size];
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

  /* renamed from: mX */
  private int f2956mX;

  /* renamed from: mY */
  private int f2957mY;

  @Retention(RetentionPolicy.SOURCE)
  public @interface Duration {}

  private AODToast(Builder builder) {
    this.mToken = new Binder();
    this.mText = builder.mText;
    this.mDuration = builder.mDuration;
    this.mDurationMillis = builder.mDurationMillis;
    this.mGravity = builder.mGravity;
    this.f2956mX = builder.f2958mX;
    this.f2957mY = builder.f2959mY;
    this.mHorizontalMargin = builder.mHorizontalMargin;
    this.mVerticalMargin = builder.mVerticalMargin;
  }

  private AODToast(Parcel in) {
    this.mToken = in.readStrongBinder();
    this.mText = in.readString();
    this.mDuration = in.readInt();
    this.mDurationMillis = in.readLong();
    this.mGravity = in.readInt();
    this.f2956mX = in.readInt();
    this.f2957mY = in.readInt();
    this.mHorizontalMargin = in.readFloat();
    this.mVerticalMargin = in.readFloat();
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
    return this.f2956mX;
  }

  public int getYOffset() {
    return this.f2957mY;
  }

  public float getHorizontalMargin() {
    return this.mHorizontalMargin;
  }

  public float getVerticalMargin() {
    return this.mVerticalMargin;
  }

  public String toString() {
    return "[AODToast: text:("
        + this.mText
        + ") duration:("
        + this.mDuration
        + ") durationMillis:("
        + this.mDurationMillis
        + ") gravity:("
        + this.mGravity
        + ") xOffset:("
        + this.f2956mX
        + ") yOffset:("
        + this.f2957mY
        + ") hMargin:("
        + this.mHorizontalMargin
        + ") vMargin:("
        + this.mVerticalMargin
        + ")]";
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeStrongBinder(this.mToken);
    dest.writeString(this.mText);
    dest.writeInt(this.mDuration);
    dest.writeLong(this.mDurationMillis);
    dest.writeInt(this.mGravity);
    dest.writeInt(this.f2956mX);
    dest.writeInt(this.f2957mY);
    dest.writeFloat(this.mHorizontalMargin);
    dest.writeFloat(this.mVerticalMargin);
  }

  public static class Builder {
    private int mDuration;
    private long mDurationMillis;
    private int mGravity;
    private float mHorizontalMargin;
    private final String mText;
    private float mVerticalMargin;

    /* renamed from: mX */
    private int f2958mX;

    /* renamed from: mY */
    private int f2959mY;

    public Builder(String text) {
      this.mText = text;
    }

    public Builder setDuration(int duration) {
      this.mDuration = duration;
      return this;
    }

    public Builder setDurationInMillis(long duration) {
      this.mDurationMillis = duration;
      return this;
    }

    public Builder setGravity(int gravity, int xOffset, int yOffset) {
      this.mGravity = gravity;
      this.f2958mX = xOffset;
      this.f2959mY = yOffset;
      return this;
    }

    public void setMargin(float horizontalMargin, float verticalMargin) {
      this.mHorizontalMargin = horizontalMargin;
      this.mVerticalMargin = verticalMargin;
    }

    public AODToast build() {
      return new AODToast(this);
    }
  }
}
