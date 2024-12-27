package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextCarryingDetection extends SemContextEventContext {
    public static final int AOD_BRIGHTNESS_HYSTERESIS_LUX_COUNT = 10;
    public static final int AOD_BRIGHTNESS_HYSTERESIS_MODE = 9;
    public static final int AOD_BRIGHTNESS_SIMPLE_MODE = 11;
    public static final int AOD_MODE_END = 12;
    public static final int AOD_MODE_TAP_TO_SHOW = 7;
    public static final int AOD_OFF_TIMEOUT = 12;
    public static final int AOD_OVERTURN_DURATION = 4;
    public static final int AOD_PARTIAL_MODE = 8;
    public static final int AOD_PROXIMITY_CHECK_DURATION = 6;
    public static final int AOD_PROXIMITY_USE_DURATION = 5;
    public static final int AOD_SCENARIO_CHECK_OVERTURN = 2;
    public static final int AOD_SCENARIO_CHECK_PROXIMITY_PERIODICALLY = 4;
    public static final int AOD_SCENARIO_CHECK_TIMEOUT = 1;
    public static final int AOD_SCENARIO_CHECK_USER_CYCLE = 64;
    public static final int AOD_SCENARIO_CHECK_USER_RUNNING = 16;
    public static final int AOD_SCENARIO_CHECK_USER_VEHICLE = 32;
    public static final int AOD_SCENARIO_CHECK_USER_WALKING = 8;
    public static final int AOD_SCENARIO_PARTIAL_MODE_OFF = 1;
    public static final int AOD_SCENARIO_PARTIAL_MODE_ON = 2;
    public static final int AOD_SCENARIO_TAP_TO_SHOW_DISABLED = 2;
    public static final int AOD_SCENARIO_TAP_TO_SHOW_ENABLED = 1;
    public static final int AOD_STATUS_OFF = 2;
    public static final int AOD_STATUS_ON = 1;
    public static final int AOD_TIMEOUT_DURATION = 3;
    public static final Parcelable.Creator<SemContextCarryingDetection> CREATOR = new Parcelable.Creator<SemContextCarryingDetection>() { // from class: com.samsung.android.hardware.context.SemContextCarryingDetection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextCarryingDetection createFromParcel(Parcel in) {
            return new SemContextCarryingDetection(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextCarryingDetection[] newArray(int size) {
            return new SemContextCarryingDetection[size];
        }
    };
    public static final int REASON_OFF_CARRYING_IN = 9;
    public static final int REASON_OFF_NO_MOVE_SCREEN_DOWN_TIME_OUT = 6;
    public static final int REASON_OFF_NO_MOVE_SCREEN_UP_TIME_OUT = 7;
    public static final int REASON_OFF_RUNNING_START = 10;
    public static final int REASON_OFF_SCREEN_DOWN_START_STATE = 8;
    public static final int REASON_ON_CARRYING_OUT = 4;
    public static final int REASON_ON_DISPLAY_INIT = 1;
    public static final int REASON_ON_MOVEMENT_WITH_SCREEN_DOWN = 2;
    public static final int REASON_ON_MOVEMENT_WITH_SCREEN_UP = 3;
    public static final int REASON_ON_RUNNING_STOPPED = 5;
    private Bundle mContext;

    SemContextCarryingDetection() {
        this.mContext = new Bundle();
    }

    SemContextCarryingDetection(Parcel src) {
        readFromParcel(src);
    }

    public int getCarryingStatus() {
        return this.mContext.getInt("AODStatus");
    }

    public int getLux() {
        int lux = this.mContext.getInt("AODLux");
        return lux;
    }

    public int getCarryingReason() {
        return this.mContext.getInt("AODReason");
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
