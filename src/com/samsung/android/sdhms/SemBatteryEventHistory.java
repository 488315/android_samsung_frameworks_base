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
    public static final Parcelable.Creator<SemBatteryEventHistory> CREATOR = new Parcelable.Creator<SemBatteryEventHistory>() { // from class: com.samsung.android.sdhms.SemBatteryEventHistory.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBatteryEventHistory createFromParcel(Parcel parcel) {
            return new SemBatteryEventHistory(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBatteryEventHistory[] newArray(int i) {
            return new SemBatteryEventHistory[i];
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemBatteryEventHistory(SemBatteryEventHistory semBatteryEventHistory) {
        this.updateTime = semBatteryEventHistory.getUpdatedTimestamp();
        this.eventType = semBatteryEventHistory.getType();
        this.eventValue = semBatteryEventHistory.getValue();
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

        public Builder updateTime(long j) {
            this.updateTime = j;
            return this;
        }

        public Builder eventType(int i) {
            this.eventType = i;
            return this;
        }

        public Builder eventValue(int i) {
            this.eventValue = i;
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

    public void shiftTimestamp(long j) {
        this.updateTime += j;
    }

    protected SemBatteryEventHistory(Parcel parcel) {
        this.updateTime = parcel.readLong();
        this.eventType = parcel.readInt();
        this.eventValue = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.updateTime);
        parcel.writeInt(this.eventType);
        parcel.writeInt(this.eventValue);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemBatteryEventHistory m9431clone() {
        try {
            return (SemBatteryEventHistory) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
