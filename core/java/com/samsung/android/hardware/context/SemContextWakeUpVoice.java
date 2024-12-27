package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class SemContextWakeUpVoice extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextWakeUpVoice> CREATOR =
            new Parcelable.Creator<
                    SemContextWakeUpVoice>() { // from class:
                                               // com.samsung.android.hardware.context.SemContextWakeUpVoice.1
                @Override // android.os.Parcelable.Creator
                public SemContextWakeUpVoice createFromParcel(Parcel in) {
                    return new SemContextWakeUpVoice(in);
                }

                @Override // android.os.Parcelable.Creator
                public SemContextWakeUpVoice[] newArray(int size) {
                    return new SemContextWakeUpVoice[size];
                }
            };
    public static final int DATA_AM = 1;
    public static final int DATA_DOWNLOADED = -17;
    public static final int DATA_LM = 2;
    public static final int MODE_BABY_CRYING = 2;
    public static final int MODE_HI_GALAXY = 1;
    public static final int NONE = 0;
    public static final int RECOGNIZED = 1;
    private Bundle mContext;

    SemContextWakeUpVoice() {
        this.mContext = new Bundle();
    }

    SemContextWakeUpVoice(Parcel src) {
        readFromParcel(src);
    }

    public int getMode() {
        return this.mContext.getInt("Mode");
    }

    public int getAction() {
        return this.mContext.getInt("Action");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
    }
}
