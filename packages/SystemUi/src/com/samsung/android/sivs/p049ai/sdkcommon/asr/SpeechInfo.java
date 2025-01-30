package com.samsung.android.sivs.p049ai.sdkcommon.asr;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SpeechInfo implements Parcelable {
    public static final Parcelable.Creator<SpeechInfo> CREATOR = new Parcelable.Creator<SpeechInfo>() { // from class: com.samsung.android.sivs.ai.sdkcommon.asr.SpeechInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpeechInfo createFromParcel(Parcel parcel) {
            return new SpeechInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpeechInfo[] newArray(int i) {
            return new SpeechInfo[i];
        }
    };
    private final int endTime;
    private final int speaker;
    private final int startTime;

    public SpeechInfo(int i, int i2, int i3) {
        this.speaker = i;
        this.startTime = i2;
        this.endTime = i3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public int getSpeaker() {
        return this.speaker;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public String toString() {
        return "SpeechInfo{speaker=" + this.speaker + ", startTime=" + this.startTime + ", endTime=" + this.endTime + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.speaker);
        parcel.writeInt(this.startTime);
        parcel.writeInt(this.endTime);
    }

    public SpeechInfo(Parcel parcel) {
        this.speaker = parcel.readInt();
        this.startTime = parcel.readInt();
        this.endTime = parcel.readInt();
    }
}
