package com.samsung.android.sdhms;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemBatteryEventHistory implements Cloneable, Parcelable {
    public static final int BATTERY_STATUS_CHARGING = 2;
    public static final int BATTERY_STATUS_DISCHARGING = 4;
    public static final int BATTERY_STATUS_FULL = 16;
    public static final int BATTERY_STATUS_NOT_CHARGING = 8;
    public static final int BATTERY_STATUS_UNKNOWN = 1;
    public static final Parcelable.Creator<SemBatteryEventHistory> CREATOR =
            new Parcelable.Creator<
                    SemBatteryEventHistory>() { // from class:
                                                // com.samsung.android.sdhms.SemBatteryEventHistory.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemBatteryEventHistory createFromParcel(Parcel in) {
                    return new SemBatteryEventHistory(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemBatteryEventHistory[] newArray(int size) {
                    return new SemBatteryEventHistory[size];
                }
            };
    public static final int PLUGGED_STATE_AC = 1;
    public static final int PLUGGED_STATE_NONE = 0;
    public static final int PLUGGED_STATE_USB = 2;
    public static final int PLUGGED_STATE_WIRELESS = 4;
    public static final int POWER_SAVE_STATE_OFF = 0;
    public static final int POWER_SAVE_STATE_ON = 1;
    public static final int POWER_STATE_OFF = 0;
    public static final int POWER_STATE_ON = 1;
    public static final int PROTECT_BATTERY_STATE_OFF = 0;
    public static final int PROTECT_BATTERY_STATE_ON = 1;
    public static final int PROTECT_BATTERY_STATE_ON_ADAPTIVE = 8;
    public static final int PROTECT_BATTERY_STATE_ON_BASIC = 4;
    public static final int PROTECT_BATTERY_STATE_ON_LTC = 2;
    public static final int PROTECT_BATTERY_STATE_ON_SLEEPING = 16;
    public static final int TYPE_BATTERY_LEVEL = 1;
    public static final int TYPE_BATTERY_PLUGGED_STATE = 2;
    public static final int TYPE_BATTERY_STATUS = 32;
    public static final int TYPE_CURRENT_BATTERY_STATE = -1;
    public static final int TYPE_POWER_SAVE_STATE = 8;
    public static final int TYPE_POWER_STATE = 4;
    public static final int TYPE_PROTECT_BATTERY_STATE = 16;
    private final int eventType;
    private final int eventValue;
    private long updateTime;

    public SemBatteryEventHistory(SemBatteryEventHistory copy) {
        this.updateTime = copy.getUpdatedTimestamp();
        this.eventType = copy.getType();
        this.eventValue = copy.getValue();
    }

    public long getUpdatedTimestamp() {
        return this.updateTime;
    }

    public int getType() {
        return this.eventType;
    }

    public int getValue() {
        return this.eventValue;
    }

    public static final class Builder {
        private int eventType;
        private int eventValue;
        private long updateTime;

        public Builder updateTime(long value) {
            this.updateTime = value;
            return this;
        }

        public Builder eventType(int value) {
            this.eventType = value;
            return this;
        }

        public Builder eventValue(int value) {
            this.eventValue = value;
            return this;
        }

        public SemBatteryEventHistory build() {
            return new SemBatteryEventHistory(this);
        }
    }

    public SemBatteryEventHistory(Builder builder) {
        this.updateTime = builder.updateTime;
        this.eventType = builder.eventType;
        this.eventValue = builder.eventValue;
    }

    public void shiftTimestamp(long time) {
        this.updateTime += time;
    }

    protected SemBatteryEventHistory(Parcel in) {
        this.updateTime = in.readLong();
        this.eventType = in.readInt();
        this.eventValue = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.updateTime);
        parcel.writeInt(this.eventType);
        parcel.writeInt(this.eventValue);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemBatteryEventHistory m9009clone() {
        try {
            return (SemBatteryEventHistory) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
