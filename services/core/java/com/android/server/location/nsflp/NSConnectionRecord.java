package com.android.server.location.nsflp;

import android.os.Parcel;
import android.os.Parcelable;

public final class NSConnectionRecord implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public String componentName;
    public long connectedTime;
    public int connectionCount;
    public long disconnectedTime;
    public int disconnectionCount;
    public final String logTag;
    public String packageName;
    public String prevComponentName;
    public String prevPackageName;
    public long serviceBindingDiedTime;

    /* renamed from: com.android.server.location.nsflp.NSConnectionRecord$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new NSConnectionRecord(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new NSConnectionRecord[i];
        }
    }

    public NSConnectionRecord(Parcel parcel) {
        this.componentName = null;
        this.prevComponentName = null;
        this.packageName = null;
        this.prevPackageName = null;
        this.connectionCount = 0;
        this.disconnectionCount = 0;
        this.connectedTime = 0L;
        this.disconnectedTime = 0L;
        this.serviceBindingDiedTime = 0L;
        this.logTag = parcel.readString();
        this.componentName = parcel.readString();
        this.prevComponentName = parcel.readString();
        this.packageName = parcel.readString();
        this.prevPackageName = parcel.readString();
        this.connectionCount = parcel.readInt();
        this.disconnectionCount = parcel.readInt();
        this.connectedTime = parcel.readLong();
        this.disconnectedTime = parcel.readLong();
        this.serviceBindingDiedTime = parcel.readLong();
    }

    public NSConnectionRecord(String str) {
        this.componentName = null;
        this.prevComponentName = null;
        this.packageName = null;
        this.prevPackageName = null;
        this.connectionCount = 0;
        this.disconnectionCount = 0;
        this.connectedTime = 0L;
        this.disconnectedTime = 0L;
        this.serviceBindingDiedTime = 0L;
        this.logTag = str;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("tag=");
        sb.append(this.logTag);
        sb.append("_prevComp=");
        sb.append(this.prevComponentName);
        sb.append("_comp=");
        sb.append(this.componentName);
        sb.append("_connCnt=");
        sb.append(this.connectionCount);
        sb.append("_discCnt=");
        sb.append(this.disconnectionCount);
        sb.append("_discTime=");
        sb.append(this.disconnectedTime);
        sb.append("_discDur=");
        long j = this.connectedTime;
        long j2 = this.disconnectedTime;
        sb.append(j >= j2 ? j - j2 : 0L);
        sb.append("_bindDiedTime=");
        sb.append(this.serviceBindingDiedTime);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.logTag);
        parcel.writeString(this.componentName);
        parcel.writeString(this.prevComponentName);
        parcel.writeString(this.packageName);
        parcel.writeString(this.prevPackageName);
        parcel.writeInt(this.connectionCount);
        parcel.writeInt(this.disconnectionCount);
        parcel.writeLong(this.connectedTime);
        parcel.writeLong(this.disconnectedTime);
        parcel.writeLong(this.serviceBindingDiedTime);
    }
}
