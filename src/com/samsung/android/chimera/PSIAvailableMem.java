package com.samsung.android.chimera;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes6.dex */
public class PSIAvailableMem implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.samsung.android.chimera.PSIAvailableMem.1
        @Override // android.os.Parcelable.Creator
        public PSIAvailableMem createFromParcel(Parcel parcel) {
            return new PSIAvailableMem(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public PSIAvailableMem[] newArray(int i) {
            return new PSIAvailableMem[i];
        }
    };
    long availMem;
    long cached;
    long checkTime;
    long running;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public PSIAvailableMem(long j, long j2, long j3, long j4) {
        this.availMem = j;
        this.running = j2;
        this.cached = j3;
        this.checkTime = j4;
    }

    private PSIAvailableMem(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.availMem = parcel.readLong();
        this.running = parcel.readLong();
        this.cached = parcel.readLong();
        this.checkTime = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.availMem);
        parcel.writeLong(this.running);
        parcel.writeLong(this.cached);
        parcel.writeLong(this.checkTime);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public long getAvailMem() {
        return this.availMem;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public long getRunning() {
        return this.running;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public long getCached() {
        return this.cached;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public long getCheckTime() {
        return this.checkTime;
    }
}
