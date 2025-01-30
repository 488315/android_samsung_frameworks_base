package com.samsung.android.ims.cmc;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemCmcRecordingInfo implements Parcelable {
  public static final Parcelable.Creator<SemCmcRecordingInfo> CREATOR =
      new Parcelable.Creator<
          SemCmcRecordingInfo>() { // from class: com.samsung.android.ims.cmc.SemCmcRecordingInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCmcRecordingInfo createFromParcel(Parcel in) {
          return new SemCmcRecordingInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCmcRecordingInfo[] newArray(int size) {
          return new SemCmcRecordingInfo[size];
        }
      };
  int mAudioChannels;
  int mAudioEncoder;
  int mAudioEncodingBitRate;
  int mAudioSamplingRate;
  int mAudioSource;
  String mAuthor;
  int mDurationInterval;
  long mFileSizeInterval;
  int mMaxDuration;
  long mMaxFileSize;
  int mOutputFormat;
  String mOutputPath;

  public int getAudioSource() {
    return this.mAudioSource;
  }

  public void setAudioSource(int mAudioSource) {
    this.mAudioSource = mAudioSource;
  }

  public int getOutputFormat() {
    return this.mOutputFormat;
  }

  public void setOutputFormat(int mOutputFormat) {
    this.mOutputFormat = mOutputFormat;
  }

  public long getMaxFileSize() {
    return this.mMaxFileSize;
  }

  public void setMaxFileSize(long mMaxFileSize) {
    this.mMaxFileSize = mMaxFileSize;
  }

  public int getMaxDuration() {
    return this.mMaxDuration;
  }

  public void setMaxDuration(int mMaxDuration) {
    this.mMaxDuration = mMaxDuration;
  }

  public String getOutputPath() {
    return this.mOutputPath;
  }

  public void setOutputPath(String mOutputPath) {
    this.mOutputPath = mOutputPath;
  }

  public int getAudioEncodingBitRate() {
    return this.mAudioEncodingBitRate;
  }

  public void setAudioEncodingBitRate(int mAudioEncodingBitRate) {
    this.mAudioEncodingBitRate = mAudioEncodingBitRate;
  }

  public int getAudioChannels() {
    return this.mAudioChannels;
  }

  public void setAudioChannels(int mAudioChannels) {
    this.mAudioChannels = mAudioChannels;
  }

  public int getAudioSamplingRate() {
    return this.mAudioSamplingRate;
  }

  public void setAudioSamplingRate(int mAudioSamplingRate) {
    this.mAudioSamplingRate = mAudioSamplingRate;
  }

  public int getAudioEncoder() {
    return this.mAudioEncoder;
  }

  public void setAudioEncoder(int mAudioEncoder) {
    this.mAudioEncoder = mAudioEncoder;
  }

  public int getDurationInterval() {
    return this.mDurationInterval;
  }

  public void setDurationInterval(int mDurationInterval) {
    this.mDurationInterval = mDurationInterval;
  }

  public long getFileSizeInterval() {
    return this.mFileSizeInterval;
  }

  public void setFileSizeInterval(long mFileSizeInterval) {
    this.mFileSizeInterval = mFileSizeInterval;
  }

  public String getAuthor() {
    return this.mAuthor;
  }

  public void setAuthor(String mAuthor) {
    this.mAuthor = mAuthor;
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public SemCmcRecordingInfo() {}

  public SemCmcRecordingInfo(Parcel in) {
    readFromParcel(in);
  }

  private void readFromParcel(Parcel in) {
    this.mAudioSource = in.readInt();
    this.mOutputFormat = in.readInt();
    this.mMaxFileSize = in.readLong();
    this.mMaxDuration = in.readInt();
    this.mOutputPath = in.readString();
    this.mAudioEncodingBitRate = in.readInt();
    this.mAudioChannels = in.readInt();
    this.mAudioSamplingRate = in.readInt();
    this.mAudioEncoder = in.readInt();
    this.mDurationInterval = in.readInt();
    this.mFileSizeInterval = in.readLong();
    this.mAuthor = in.readString();
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mAudioSource);
    dest.writeInt(this.mOutputFormat);
    dest.writeLong(this.mMaxFileSize);
    dest.writeInt(this.mMaxDuration);
    dest.writeString(this.mOutputPath);
    dest.writeInt(this.mAudioEncodingBitRate);
    dest.writeInt(this.mAudioChannels);
    dest.writeInt(this.mAudioSamplingRate);
    dest.writeInt(this.mAudioEncoder);
    dest.writeInt(this.mDurationInterval);
    dest.writeLong(this.mFileSizeInterval);
    dest.writeString(this.mAuthor);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("SemCmcRecordingInfo = {");
    sb.append("mAudioSource : " + this.mAudioSource);
    sb.append(", mOutputFormat : " + this.mOutputFormat);
    sb.append(", mMaxFileSize : " + this.mMaxFileSize);
    sb.append(", mMaxDuration : " + this.mMaxDuration);
    sb.append(", mOutputPath : " + this.mOutputPath);
    sb.append(", mAudioEncodingBitRate : " + this.mAudioEncodingBitRate);
    sb.append(", mAudioChannels : " + this.mAudioChannels);
    sb.append(", mAudioSamplingRate : " + this.mAudioSamplingRate);
    sb.append(", mAudioEncoder : " + this.mAudioEncoder);
    sb.append(", mDurationInterval : " + this.mDurationInterval);
    sb.append(", mFileSizeInterval : " + this.mFileSizeInterval);
    sb.append(", mAuthor : " + this.mAuthor);
    sb.append("}");
    return sb.toString();
  }
}
