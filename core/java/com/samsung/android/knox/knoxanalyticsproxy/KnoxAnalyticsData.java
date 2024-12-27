package com.samsung.android.knox.knoxanalyticsproxy;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

public class KnoxAnalyticsData implements Parcelable {
    public static final Parcelable.Creator<KnoxAnalyticsData> CREATOR =
            new Parcelable.Creator<
                    KnoxAnalyticsData>() { // from class:
                                           // com.samsung.android.knox.knoxanalyticsproxy.KnoxAnalyticsData.1
                @Override // android.os.Parcelable.Creator
                public KnoxAnalyticsData createFromParcel(Parcel in) {
                    return new KnoxAnalyticsData(in);
                }

                @Override // android.os.Parcelable.Creator
                public KnoxAnalyticsData[] newArray(int size) {
                    return new KnoxAnalyticsData[size];
                }
            };
    private static final String PACKAGE_NAME_FLAG_PROPERTY_NAME = "ReservedKey_Pid_PackageNameFlag";
    private static final String USER_TYPE_FLAG_PROPERTY_NAME = "ReservedKey_UserId_UserTypeFlag";
    private String event;
    public long eventId;
    private String feature;
    private Bundle payload;
    private int schemaVersion;
    private long timestamp;

    public KnoxAnalyticsData(String feature, int schemaVersion, String event) {
        this.feature = feature;
        this.schemaVersion = schemaVersion;
        this.event = event;
        this.payload = new Bundle();
        this.timestamp = generateTimestamp();
    }

    private long generateTimestamp() {
        return (Calendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC)).getTimeInMillis()
                        / 3600000)
                * 3600000;
    }

    public void setProperty(String key, String value) {
        this.payload.putString(key, value);
    }

    public void setProperty(String key, int value) {
        this.payload.putInt(key, value);
    }

    public void setProperty(String key, float value) {
        this.payload.putFloat(key, value);
    }

    public void setProperty(String key, long value) {
        this.payload.putLong(key, value);
    }

    public void setProperty(String str, boolean z) {
        this.payload.putInt(str, z ? 1 : 0);
    }

    public void setProperty(String key, Serializable value) {
        this.payload.putSerializable(key, value);
    }

    public void setProperty(String key, JSONObject value) {
        this.payload.putString(key, value.toString());
    }

    public void setProperty(String key, Bundle value) {
        this.payload.putBundle(key, value);
    }

    public void setUserTypeProperty(int userId) {
        setProperty(USER_TYPE_FLAG_PROPERTY_NAME, userId);
    }

    public void setPackageNameProperty(int pid) {
        setProperty(PACKAGE_NAME_FLAG_PROPERTY_NAME, pid);
    }

    public String getFeature() {
        return this.feature;
    }

    public int getSchemaVersion() {
        return this.schemaVersion;
    }

    public String getEvent() {
        return this.event;
    }

    public Bundle getPayload() {
        return this.payload;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public long getEventId() {
        return this.eventId;
    }

    public KnoxAnalyticsData(Parcel in) {
        this.feature = in.readString();
        this.schemaVersion = in.readInt();
        this.event = in.readString();
        this.payload = in.readBundle();
        this.timestamp = in.readLong();
        this.eventId = in.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.feature);
        dest.writeInt(this.schemaVersion);
        dest.writeString(this.event);
        dest.writeBundle(this.payload);
        dest.writeLong(this.timestamp);
        dest.writeLong(this.eventId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "feature = "
                + this.feature
                + System.lineSeparator()
                + "schemaVersion = "
                + this.schemaVersion
                + System.lineSeparator()
                + "event = "
                + this.event
                + System.lineSeparator()
                + "payload = "
                + (this.payload != null ? this.payload.toString() : "null")
                + System.lineSeparator()
                + "timestamp = "
                + this.timestamp
                + System.lineSeparator()
                + "eventId = "
                + this.eventId
                + System.lineSeparator();
    }
}
