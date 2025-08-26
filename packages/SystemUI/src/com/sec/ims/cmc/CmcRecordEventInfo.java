package com.sec.ims.cmc;

import android.os.Parcel;
import android.os.Parcelable;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class CmcRecordEventInfo implements Parcelable {
    public static final Parcelable.Creator<CmcRecordEventInfo> CREATOR = new Parcelable.Creator<CmcRecordEventInfo>() { // from class: com.sec.ims.cmc.CmcRecordEventInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcRecordEventInfo createFromParcel(Parcel parcel) {
            return new CmcRecordEventInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcRecordEventInfo[] newArray(int i) {
            return new CmcRecordEventInfo[i];
        }
    };
    private static final String LOG_TAG = "CmcCallEventInfo";
    private int mCallId;
    private int mRecordEvent;

    public class Builder {
        protected int mCallId = -1;
        protected int mRecordEvent = -1;

        public CmcRecordEventInfo build() {
            return new CmcRecordEventInfo(this);
        }

        public Builder setCallId(int i) {
            this.mCallId = i;
            return this;
        }

        public Builder setRecordEvent(int i) {
            this.mRecordEvent = i;
            return this;
        }
    }

    public /* synthetic */ CmcRecordEventInfo(Parcel parcel, int i) {
        this(parcel);
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCallId() {
        return this.mCallId;
    }

    public int getRecordEvent() {
        return this.mRecordEvent;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CmcRecordEventInfo [mCallId=");
        sb.append(this.mCallId);
        sb.append(", mRecordEvent=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.mRecordEvent, "]", sb);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeInt(this.mCallId);
        parcel.writeInt(this.mRecordEvent);
    }

    private CmcRecordEventInfo(Parcel parcel) {
        this.mCallId = -1;
        this.mRecordEvent = -1;
        this.mCallId = parcel.readInt();
        this.mRecordEvent = parcel.readInt();
    }

    public CmcRecordEventInfo(CmcRecordEventInfo cmcRecordEventInfo) {
        this.mCallId = -1;
        this.mRecordEvent = -1;
        this.mCallId = cmcRecordEventInfo.mCallId;
        this.mRecordEvent = cmcRecordEventInfo.mRecordEvent;
    }

    public CmcRecordEventInfo(Builder builder) {
        this.mCallId = -1;
        this.mRecordEvent = -1;
        this.mCallId = builder.mCallId;
        this.mRecordEvent = builder.mRecordEvent;
    }
}
