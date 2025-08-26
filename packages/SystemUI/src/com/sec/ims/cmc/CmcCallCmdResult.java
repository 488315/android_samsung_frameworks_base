package com.sec.ims.cmc;

import android.os.Parcel;
import android.os.Parcelable;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class CmcCallCmdResult implements Parcelable {
    public static final Parcelable.Creator<CmcCallCmdResult> CREATOR = new Parcelable.Creator<CmcCallCmdResult>() { // from class: com.sec.ims.cmc.CmcCallCmdResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcCallCmdResult createFromParcel(Parcel parcel) {
            return new CmcCallCmdResult(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcCallCmdResult[] newArray(int i) {
            return new CmcCallCmdResult[i];
        }
    };
    public static final int IMS_NOT_READY = 3;
    public static final int INVALID_INPUT = 1;
    private static final String LOG_TAG = "CmcCallCmdResult";
    public static final int NOT_REGISTERED = 5;
    public static final int NOT_SUPPORTED = 4;
    public static final int OPERTION_NOT_ALLOWED = 2;
    public static final int SUCCESS = 0;
    private int mCallId;
    private int mCmdResult;

    public class Builder {
        protected int mCallId;
        protected int mCmdResult;

        public CmcCallCmdResult build() {
            return new CmcCallCmdResult(this);
        }

        public Builder setCallId(int i) {
            this.mCallId = i;
            return this;
        }

        public Builder setCmdResult(int i) {
            this.mCmdResult = i;
            return this;
        }
    }

    public /* synthetic */ CmcCallCmdResult(Parcel parcel, int i) {
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

    public int getCmdResult() {
        return this.mCmdResult;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CmcCallCmdResult [mCallId=");
        sb.append(this.mCallId);
        sb.append(", mCmdResult=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.mCmdResult, "]", sb);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeInt(this.mCallId);
        parcel.writeInt(this.mCmdResult);
    }

    private CmcCallCmdResult(Parcel parcel) {
        this.mCallId = -1;
        this.mCallId = parcel.readInt();
        this.mCmdResult = parcel.readInt();
    }

    public CmcCallCmdResult(CmcCallCmdResult cmcCallCmdResult) {
        this.mCallId = -1;
        this.mCallId = cmcCallCmdResult.mCallId;
        this.mCmdResult = cmcCallCmdResult.mCmdResult;
    }

    public CmcCallCmdResult(Builder builder) {
        this.mCallId = -1;
        this.mCallId = builder.mCallId;
        this.mCmdResult = builder.mCmdResult;
    }
}
