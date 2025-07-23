package com.samsung.android.vibrator;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class VibrationDebugInfo implements Parcelable {
    public static final int CHANGE_SEP_INDEX_DURATION = 2;
    public static final Parcelable.Creator<VibrationDebugInfo> CREATOR = new Parcelable.Creator<VibrationDebugInfo>() { // from class: com.samsung.android.vibrator.VibrationDebugInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibrationDebugInfo createFromParcel(Parcel parcel) {
            return new VibrationDebugInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibrationDebugInfo[] newArray(int i) {
            return new VibrationDebugInfo[i];
        }
    };
    public static final String FAIL = "fail";
    public static final int GET_DEVICE_INFORMATION = 0;
    public static final int RESET_INDEX = 3;
    public static final int SET_SEP_INDEX = 1;
    public static final String SUCCESS = "success";
    private int command;
    private int duration;
    private int index;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VibrationDebugInfo(Parcel parcel) {
        this.command = parcel.readInt();
        this.index = parcel.readInt();
        this.duration = parcel.readInt();
    }

    public VibrationDebugInfo(int i, int i2, int i3) {
        this.command = i;
        this.index = i2;
        this.duration = i3;
    }

    public VibrationDebugInfo(int i) {
        this.command = i;
    }

    public int getCommand() {
        return this.command;
    }

    public int getIndex() {
        return this.index;
    }

    public int getDuration() {
        return this.duration;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.command);
        parcel.writeInt(this.index);
        parcel.writeInt(this.duration);
    }
}
