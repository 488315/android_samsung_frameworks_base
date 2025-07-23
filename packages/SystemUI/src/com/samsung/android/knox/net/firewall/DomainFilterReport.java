package com.samsung.android.knox.net.firewall;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DomainFilterReport implements Parcelable {
    public static final Parcelable.Creator<DomainFilterReport> CREATOR = new Parcelable.Creator<DomainFilterReport>() { // from class: com.samsung.android.knox.net.firewall.DomainFilterReport.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DomainFilterReport createFromParcel(Parcel parcel) {
            return new DomainFilterReport(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DomainFilterReport[] newArray(int i) {
            return new DomainFilterReport[i];
        }
    };
    public String mDomainUrl;
    public String mPackageName;
    public long mTimeStamp;

    public /* synthetic */ DomainFilterReport(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getDomainUrl() {
        return this.mDomainUrl;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mTimeStamp);
        parcel.writeString(this.mDomainUrl);
    }

    public DomainFilterReport(String str, long j, String str2) {
        this.mPackageName = str;
        this.mTimeStamp = j;
        this.mDomainUrl = str2;
    }

    private DomainFilterReport() {
    }

    private DomainFilterReport(Parcel parcel) {
        this();
        this.mPackageName = parcel.readString();
        this.mTimeStamp = parcel.readLong();
        this.mDomainUrl = parcel.readString();
    }
}
