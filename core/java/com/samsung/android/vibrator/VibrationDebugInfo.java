package com.samsung.android.vibrator;

import android.os.Parcel;
import android.os.Parcelable;

public class VibrationDebugInfo implements Parcelable {
    public static final int CHANGE_SEP_INDEX_DURATION = 2;
    public static final Parcelable.Creator<VibrationDebugInfo> CREATOR =
            new Parcelable.Creator<
                    VibrationDebugInfo>() { // from class:
                                            // com.samsung.android.vibrator.VibrationDebugInfo.1
                @Override // android.os.Parcelable.Creator
                public VibrationDebugInfo createFromParcel(Parcel source) {
                    return new VibrationDebugInfo(source);
                }

                @Override // android.os.Parcelable.Creator
                public VibrationDebugInfo[] newArray(int size) {
                    return new VibrationDebugInfo[size];
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

    public VibrationDebugInfo(Parcel in) {
        this.command = in.readInt();
        this.index = in.readInt();
        this.duration = in.readInt();
    }

    public VibrationDebugInfo(int command, int index, int duration) {
        this.command = command;
        this.index = index;
        this.duration = duration;
    }

    public VibrationDebugInfo(int command) {
        this.command = command;
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.command);
        dest.writeInt(this.index);
        dest.writeInt(this.duration);
    }
}
