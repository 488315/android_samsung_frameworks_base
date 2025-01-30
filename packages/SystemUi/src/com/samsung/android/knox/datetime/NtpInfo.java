package com.samsung.android.knox.datetime;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NtpInfo implements Parcelable {
    public static final Parcelable.Creator<NtpInfo> CREATOR = new Parcelable.Creator<NtpInfo>() { // from class: com.samsung.android.knox.datetime.NtpInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final NtpInfo[] newArray(int i) {
            return new NtpInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final NtpInfo createFromParcel(Parcel parcel) {
            return new NtpInfo(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final NtpInfo[] newArray(int i) {
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
    public final int describeContents() {
        return 0;
    }

    public final int getMaxAttempts() {
        return this.mMaxAttempts;
    }

    public final long getPollingInterval() {
        return this.mPollingInterval;
    }

    public final long getPollingIntervalShorter() {
        return this.mPollingIntervalShorter;
    }

    public final String getServer() {
        return this.mServer;
    }

    public final int getTimeErrorThreshold() {
        return this.mTimeErrorThreshold;
    }

    public final long getTimeout() {
        return this.mTimeout;
    }

    public final void setMaxAttempts(int i) {
        this.mMaxAttempts = i;
    }

    public final void setPollingInterval(long j) {
        this.mPollingInterval = j;
    }

    public final void setPollingIntervalShorter(long j) {
        this.mPollingIntervalShorter = j;
    }

    public final void setServer(String str) {
        this.mServer = str;
    }

    public final void setTimeout(long j) {
        this.mTimeout = j;
    }

    public final String toString() {
        return "server=" + this.mServer + " timeout=" + this.mTimeout + " maxAttempts=" + this.mMaxAttempts + " pollingInterval=" + this.mPollingInterval + " pollingIntervalShorter=" + this.mPollingIntervalShorter;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mServer);
        parcel.writeLong(this.mTimeout);
        parcel.writeInt(this.mMaxAttempts);
        parcel.writeLong(this.mPollingInterval);
        parcel.writeLong(this.mPollingIntervalShorter);
    }

    public NtpInfo(Context context) {
        this.mServer = null;
        this.mTimeout = 0L;
        this.mMaxAttempts = 0;
        this.mPollingInterval = 0L;
        this.mPollingIntervalShorter = 0L;
        this.mTimeErrorThreshold = 0;
        Resources resources = context.getResources();
        ContentResolver contentResolver = context.getContentResolver();
        String[] stringArray = resources.getStringArray(17236262);
        String replace = stringArray.length > 0 ? stringArray[0].replace("ntp://", "") : "";
        long integer = resources.getInteger(Resources.getSystem().getIdentifier("config_ntpTimeout", "integer", "android"));
        String string = Settings.Global.getString(contentResolver, "ntp_server");
        this.mTimeout = Settings.Global.getLong(contentResolver, "ntp_timeout", integer);
        this.mServer = string != null ? string : replace;
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

    public final void setTimeErrorThreshold(int i) {
    }
}
