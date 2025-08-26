package com.samsung.android.knox.datetime;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;

/* loaded from: classes4.dex */
public class NtpInfo implements Parcelable {
    public static final Parcelable.Creator<NtpInfo> CREATOR = new Parcelable.Creator<NtpInfo>() { // from class: com.samsung.android.knox.datetime.NtpInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NtpInfo createFromParcel(Parcel parcel) {
            return new NtpInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NtpInfo[] newArray(int i) {
            return new NtpInfo[i];
        }
    };
    public static final int NOT_SET_INT = 0;
    public static final long NOT_SET_LONG = 0;
    public int mMaxAttempts;
    public long mPollingInterval;
    public long mPollingIntervalShorter;
    public String mServer;
    public int mTimeErrorThreshold;
    public long mTimeout;

    public /* synthetic */ NtpInfo(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getMaxAttempts() {
        return this.mMaxAttempts;
    }

    public long getPollingInterval() {
        return this.mPollingInterval;
    }

    public long getPollingIntervalShorter() {
        return this.mPollingIntervalShorter;
    }

    public String getServer() {
        return this.mServer;
    }

    public int getTimeErrorThreshold() {
        return this.mTimeErrorThreshold;
    }

    public long getTimeout() {
        return this.mTimeout;
    }

    public void setMaxAttempts(int i) {
        this.mMaxAttempts = i;
    }

    public void setPollingInterval(long j) {
        this.mPollingInterval = j;
    }

    public void setPollingIntervalShorter(long j) {
        this.mPollingIntervalShorter = j;
    }

    public void setServer(String str) {
        this.mServer = str;
    }

    public void setTimeout(long j) {
        this.mTimeout = j;
    }

    public String toString() {
        return "server=" + this.mServer + " timeout=" + this.mTimeout + " maxAttempts=" + this.mMaxAttempts + " pollingInterval=" + this.mPollingInterval + " pollingIntervalShorter=" + this.mPollingIntervalShorter;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mServer);
        parcel.writeLong(this.mTimeout);
        parcel.writeInt(this.mMaxAttempts);
        parcel.writeLong(this.mPollingInterval);
        parcel.writeLong(this.mPollingIntervalShorter);
    }

    public NtpInfo(Context context) throws Resources.NotFoundException {
        this.mServer = null;
        this.mTimeout = 0L;
        this.mMaxAttempts = 0;
        this.mPollingInterval = 0L;
        this.mPollingIntervalShorter = 0L;
        this.mTimeErrorThreshold = 0;
        Resources resources = context.getResources();
        ContentResolver contentResolver = context.getContentResolver();
        String[] stringArray = resources.getStringArray(17236281);
        String strReplace = stringArray.length > 0 ? stringArray[0].replace("ntp://", "") : "";
        long integer = resources.getInteger(Resources.getSystem().getIdentifier("config_ntpTimeout", "integer", "android"));
        String string = Settings.Global.getString(contentResolver, "ntp_server");
        this.mTimeout = Settings.Global.getLong(contentResolver, "ntp_timeout", integer);
        this.mServer = string != null ? string : strReplace;
        this.mPollingInterval = context.getResources().getInteger(Resources.getSystem().getIdentifier("config_ntpPollingInterval", "integer", "android"));
        this.mPollingIntervalShorter = context.getResources().getInteger(Resources.getSystem().getIdentifier("config_ntpPollingIntervalShorter", "integer", "android"));
        this.mMaxAttempts = context.getResources().getInteger(Resources.getSystem().getIdentifier("config_ntpRetry", "integer", "android"));
    }

    private NtpInfo(Parcel parcel) {
        this.mServer = null;
        this.mTimeout = 0L;
        this.mMaxAttempts = 0;
        this.mPollingInterval = 0L;
        this.mPollingIntervalShorter = 0L;
        this.mTimeErrorThreshold = 0;
        this.mServer = parcel.readString();
        this.mTimeout = parcel.readLong();
        this.mMaxAttempts = parcel.readInt();
        this.mPollingInterval = parcel.readLong();
        this.mPollingIntervalShorter = parcel.readLong();
    }

    public void setTimeErrorThreshold(int i) {
    }
}
