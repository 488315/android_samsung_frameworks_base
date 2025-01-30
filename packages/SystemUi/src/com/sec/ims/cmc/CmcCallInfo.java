package com.sec.ims.cmc;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CmcCallInfo implements Parcelable {
    public static final int CALL_STATE_IDLE = 0;
    public static final int CALL_STATE_INCALL = 3;
    public static final int CALL_STATE_INCOMING = 1;
    public static final int CALL_STATE_OUTGOING = 2;
    public static final int CALL_STATE_PDCALL = 4;
    public static final int CMC_TYPE_NONE = 0;
    public static final int CMC_TYPE_PRIMARY = 1;
    public static final int CMC_TYPE_SECONDARY = 2;
    public static final Parcelable.Creator<CmcCallInfo> CREATOR = new Parcelable.Creator<CmcCallInfo>() { // from class: com.sec.ims.cmc.CmcCallInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcCallInfo createFromParcel(Parcel parcel) {
            return new CmcCallInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcCallInfo[] newArray(int i) {
            return new CmcCallInfo[i];
        }
    };
    private static final String LOG_TAG = "CmcCallInfo";
    private int mCmcCallState;
    private int mCmcType;
    private int mLineSlotId;
    private String mPdDeviceId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Builder {
        protected int mCmcCallState;
        protected int mCmcType;
        protected int mLineSlotId;
        protected String mPdDeviceId;

        public CmcCallInfo build() {
            return new CmcCallInfo(this);
        }

        public Builder setCallState(int i) {
            this.mCmcCallState = i;
            return this;
        }

        public Builder setCmcType(int i) {
            this.mCmcType = i;
            return this;
        }

        public Builder setLineSlotId(int i) {
            this.mLineSlotId = i;
            return this;
        }

        public Builder setPdDeviceId(String str) {
            this.mPdDeviceId = str;
            return this;
        }
    }

    public /* synthetic */ CmcCallInfo(Parcel parcel, int i) {
        this(parcel);
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCmcCallState() {
        return this.mCmcCallState;
    }

    public int getCmcType() {
        return this.mCmcType;
    }

    public int getLineSlotId() {
        return this.mLineSlotId;
    }

    public String getPdDeviceId() {
        return this.mPdDeviceId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CmcCallInfo(");
        sb.append(this.mLineSlotId);
        sb.append(") [mCmcType=");
        sb.append(this.mCmcType);
        sb.append(", mCmcCallState=");
        sb.append(this.mCmcCallState);
        sb.append(", mPdDeviceId=");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.mPdDeviceId, "]");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLineSlotId);
        parcel.writeInt(this.mCmcType);
        parcel.writeInt(this.mCmcCallState);
        parcel.writeString(this.mPdDeviceId);
    }

    private CmcCallInfo(Parcel parcel) {
        this.mLineSlotId = 0;
        this.mCmcType = 0;
        this.mCmcCallState = 0;
        this.mPdDeviceId = "";
        this.mLineSlotId = parcel.readInt();
        this.mCmcType = parcel.readInt();
        this.mCmcCallState = parcel.readInt();
        this.mPdDeviceId = parcel.readString();
    }

    public CmcCallInfo(CmcCallInfo cmcCallInfo) {
        this.mLineSlotId = 0;
        this.mCmcType = 0;
        this.mCmcCallState = 0;
        this.mPdDeviceId = "";
        this.mLineSlotId = cmcCallInfo.mLineSlotId;
        this.mCmcType = cmcCallInfo.mCmcType;
        this.mCmcCallState = cmcCallInfo.mCmcCallState;
        this.mPdDeviceId = cmcCallInfo.mPdDeviceId;
    }

    public CmcCallInfo(Builder builder) {
        this.mLineSlotId = 0;
        this.mCmcType = 0;
        this.mCmcCallState = 0;
        this.mPdDeviceId = "";
        this.mLineSlotId = builder.mLineSlotId;
        this.mCmcType = builder.mCmcType;
        this.mCmcCallState = builder.mCmcCallState;
        this.mPdDeviceId = builder.mPdDeviceId;
    }
}
