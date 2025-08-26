package com.sec.ims.cmc;

import android.os.Parcel;
import android.os.Parcelable;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class CmcCallCmdInfo implements Parcelable {
    public static final Parcelable.Creator<CmcCallCmdInfo> CREATOR = new Parcelable.Creator<CmcCallCmdInfo>() { // from class: com.sec.ims.cmc.CmcCallCmdInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcCallCmdInfo createFromParcel(Parcel parcel) {
            return new CmcCallCmdInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcCallCmdInfo[] newArray(int i) {
            return new CmcCallCmdInfo[i];
        }
    };
    private static final String LOG_TAG = "CmcCallCmdInfo";
    private int mExternalCallSlotAtPd;
    private String mPulledDialogId;

    public class Builder {
        protected String mPulledDialogId = "";
        protected int mExternalCallSlotAtPd = -1;

        public CmcCallCmdInfo build() {
            return new CmcCallCmdInfo(this);
        }

        public Builder setExternalCallSlotAtPd(int i) {
            this.mExternalCallSlotAtPd = i;
            return this;
        }

        public Builder setPulledDialogId(String str) {
            this.mPulledDialogId = str;
            return this;
        }
    }

    public /* synthetic */ CmcCallCmdInfo(Parcel parcel, int i) {
        this(parcel);
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getExternalCallSlotAtPd() {
        return this.mExternalCallSlotAtPd;
    }

    public String getPulledDialogId() {
        return this.mPulledDialogId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CmcCallCmdInfo [mPulledDialogId=");
        sb.append(this.mPulledDialogId);
        sb.append(", mExternalCallSlotAtPd=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.mExternalCallSlotAtPd, "]", sb);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeString(this.mPulledDialogId);
        parcel.writeInt(this.mExternalCallSlotAtPd);
    }

    private CmcCallCmdInfo(Parcel parcel) {
        this.mPulledDialogId = "";
        this.mExternalCallSlotAtPd = -1;
        this.mPulledDialogId = parcel.readString();
        this.mExternalCallSlotAtPd = parcel.readInt();
    }

    public CmcCallCmdInfo(CmcCallCmdInfo cmcCallCmdInfo) {
        this.mPulledDialogId = "";
        this.mExternalCallSlotAtPd = -1;
        this.mPulledDialogId = cmcCallCmdInfo.mPulledDialogId;
        this.mExternalCallSlotAtPd = cmcCallCmdInfo.mExternalCallSlotAtPd;
    }

    public CmcCallCmdInfo(Builder builder) {
        this.mPulledDialogId = "";
        this.mExternalCallSlotAtPd = -1;
        this.mPulledDialogId = builder.mPulledDialogId;
        this.mExternalCallSlotAtPd = builder.mExternalCallSlotAtPd;
    }
}
