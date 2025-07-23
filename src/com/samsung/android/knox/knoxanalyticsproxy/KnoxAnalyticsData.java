package com.samsung.android.knox.knoxanalyticsproxy;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class KnoxAnalyticsData implements Parcelable {
    public static final Parcelable.Creator<KnoxAnalyticsData> CREATOR = new Parcelable.Creator<KnoxAnalyticsData>() { // from class: com.samsung.android.knox.knoxanalyticsproxy.KnoxAnalyticsData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnoxAnalyticsData createFromParcel(Parcel parcel) {
            return new KnoxAnalyticsData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnoxAnalyticsData[] newArray(int i) {
            return new KnoxAnalyticsData[i];
        }
    };
    private static final String PACKAGE_NAME_FLAG_PROPERTY_NAME = "ReservedKey_Pid_PackageNameFlag";
    private static final String USER_TYPE_FLAG_PROPERTY_NAME = "ReservedKey_UserId_UserTypeFlag";
    public int count;
    private String event;
    public long eventId;
    private String feature;
    private Bundle payload;
    private int schemaVersion;
    private long timestamp;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KnoxAnalyticsData(String str, int i, String str2) {
        this.feature = str;
        this.schemaVersion = i;
        this.event = str2;
        this.payload = new Bundle();
        this.timestamp = generateTimestamp();
        this.count = 1;
    }

    private long generateTimestamp() {
        return (Calendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC)).getTimeInMillis() / 3600000) * 3600000;
    }

    public void setProperty(String str, String str2) {
        this.payload.putString(str, str2);
    }

    public void setProperty(String str, int i) {
        this.payload.putInt(str, i);
    }

    public void setProperty(String str, float f) {
        this.payload.putFloat(str, f);
    }

    public void setProperty(String str, long j) {
        this.payload.putLong(str, j);
    }

    public void setProperty(String str, boolean z) {
        this.payload.putInt(str, z ? 1 : 0);
    }

    public void setProperty(String str, Serializable serializable) {
        this.payload.putSerializable(str, serializable);
    }

    public void setProperty(String str, JSONObject jSONObject) {
        this.payload.putString(str, jSONObject.toString());
    }

    public void setProperty(String str, Bundle bundle) {
        this.payload.putBundle(str, bundle);
    }

    public void setUserTypeProperty(int i) {
        setProperty(USER_TYPE_FLAG_PROPERTY_NAME, i);
    }

    public void setPackageNameProperty(int i) {
        setProperty(PACKAGE_NAME_FLAG_PROPERTY_NAME, i);
    }

    public void incrementCount() {
        this.count++;
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

    public int getCount() {
        return this.count;
    }

    public KnoxAnalyticsData(Parcel parcel) {
        this.feature = parcel.readString();
        this.schemaVersion = parcel.readInt();
        this.event = parcel.readString();
        this.payload = parcel.readBundle();
        this.timestamp = parcel.readLong();
        this.eventId = parcel.readLong();
        this.count = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.feature);
        parcel.writeInt(this.schemaVersion);
        parcel.writeString(this.event);
        parcel.writeBundle(this.payload);
        parcel.writeLong(this.timestamp);
        parcel.writeLong(this.eventId);
        parcel.writeInt(this.count);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("feature = ");
        sb.append(this.feature);
        sb.append(System.lineSeparator());
        sb.append("schemaVersion = ");
        sb.append(this.schemaVersion);
        sb.append(System.lineSeparator());
        sb.append("event = ");
        sb.append(this.event);
        sb.append(System.lineSeparator());
        sb.append("payload = ");
        Bundle bundle = this.payload;
        sb.append(bundle != null ? bundle.toString() : PerfettoProtoLogImpl.NULL_STRING);
        sb.append(System.lineSeparator());
        sb.append("timestamp = ");
        sb.append(this.timestamp);
        sb.append(System.lineSeparator());
        sb.append("eventId = ");
        sb.append(this.eventId);
        sb.append(System.lineSeparator());
        sb.append("count = ");
        sb.append(this.count);
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
