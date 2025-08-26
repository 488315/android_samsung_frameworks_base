package com.sec.ims.cmc;

import android.os.Parcel;
import android.os.Parcelable;
import com.sec.ims.util.IMSLog;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class CmcDialog implements Parcelable {
    public static final Parcelable.Creator<CmcDialog> CREATOR = new Parcelable.Creator<CmcDialog>() { // from class: com.sec.ims.cmc.CmcDialog.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcDialog createFromParcel(Parcel parcel) {
            return new CmcDialog(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcDialog[] newArray(int i) {
            return new CmcDialog[i];
        }
    };
    private int mCallSlot;
    private int mCallType;
    private String mCnapName;
    private int mCnapNamePresentation;
    private String mDialogId;
    private boolean mIsHeld;
    private boolean mIsPullable;
    private int mNumberPresentation;
    private String mPeerUri;
    private int mState;

    public class Builder {
        private String mDialogId = "";
        private String mPeerUri = "";
        private boolean mIsPullable = true;
        private int mState = 2;
        private int mCallType = 1;
        private boolean mIsHeld = false;
        private String mCnapName = "";
        private int mCnapNamePresentation = 1;
        private int mNumberPresentation = 1;
        private int mCallSlot = 0;

        public CmcDialog build() {
            return new CmcDialog(this);
        }

        public Builder setCallSlot(int i) {
            this.mCallSlot = i;
            return this;
        }

        public Builder setCallType(int i) {
            this.mCallType = i;
            return this;
        }

        public Builder setCnapName(String str) {
            this.mCnapName = str;
            return this;
        }

        public Builder setCnapNamePresentation(int i) {
            this.mCnapNamePresentation = i;
            return this;
        }

        public Builder setDialogId(String str) {
            this.mDialogId = str;
            return this;
        }

        public Builder setIsHeld(boolean z) {
            this.mIsHeld = z;
            return this;
        }

        public Builder setIsPullable(boolean z) {
            this.mIsPullable = z;
            return this;
        }

        public Builder setNumberPresentation(int i) {
            this.mNumberPresentation = i;
            return this;
        }

        public Builder setPeerUri(String str) {
            this.mPeerUri = str;
            return this;
        }

        public Builder setState(int i) {
            this.mState = i;
            return this;
        }
    }

    public /* synthetic */ CmcDialog(Parcel parcel, int i) {
        this(parcel);
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCallSlot() {
        return this.mCallSlot;
    }

    public int getCallType() {
        return this.mCallType;
    }

    public String getCnapName() {
        return this.mCnapName;
    }

    public int getCnapNamePresentation() {
        return this.mCnapNamePresentation;
    }

    public String getDialogId() {
        return this.mDialogId;
    }

    public boolean getIsHeld() {
        return this.mIsHeld;
    }

    public boolean getIsPullable() {
        return this.mIsPullable;
    }

    public int getNumberPresentation() {
        return this.mNumberPresentation;
    }

    public String getPeerUri() {
        return this.mPeerUri;
    }

    public int getState() {
        return this.mState;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CmcDialog [mDialogId=");
        sb.append(IMSLog.checker(this.mDialogId));
        sb.append(", mPeerUri=");
        sb.append(IMSLog.checker(this.mPeerUri));
        sb.append(", mIsPullable=");
        sb.append(this.mIsPullable);
        sb.append(", mState=");
        sb.append(this.mState);
        sb.append(", mCallType=");
        sb.append(this.mCallType);
        sb.append(", mIsHeld=");
        sb.append(this.mIsHeld);
        sb.append(", mCnapName=");
        sb.append(IMSLog.checker(this.mCnapName));
        sb.append(", mCnapNamePresentation=");
        sb.append(this.mCnapNamePresentation);
        sb.append(", mNumberPresentation=");
        sb.append(this.mNumberPresentation);
        sb.append(", mCallSlot=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.mCallSlot, "]", sb);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeString(this.mDialogId);
        parcel.writeString(this.mPeerUri);
        parcel.writeInt(this.mIsPullable ? 1 : 0);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mCallType);
        parcel.writeInt(this.mIsHeld ? 1 : 0);
        parcel.writeString(this.mCnapName);
        parcel.writeInt(this.mCnapNamePresentation);
        parcel.writeInt(this.mNumberPresentation);
        parcel.writeInt(this.mCallSlot);
    }

    private CmcDialog(Parcel parcel) {
        this.mDialogId = "";
        this.mPeerUri = "";
        this.mIsPullable = true;
        this.mState = 2;
        this.mCallType = 1;
        this.mIsHeld = false;
        this.mCnapName = "";
        this.mCnapNamePresentation = 1;
        this.mNumberPresentation = 1;
        this.mCallSlot = 0;
        this.mDialogId = parcel.readString();
        this.mPeerUri = parcel.readString();
        this.mIsPullable = parcel.readInt() == 1;
        this.mState = parcel.readInt();
        this.mCallType = parcel.readInt();
        this.mIsHeld = parcel.readInt() == 1;
        this.mCnapName = parcel.readString();
        this.mCnapNamePresentation = parcel.readInt();
        this.mNumberPresentation = parcel.readInt();
        this.mCallSlot = parcel.readInt();
    }

    public CmcDialog(CmcDialog cmcDialog) {
        this.mDialogId = "";
        this.mPeerUri = "";
        this.mIsPullable = true;
        this.mState = 2;
        this.mCallType = 1;
        this.mIsHeld = false;
        this.mCnapName = "";
        this.mCnapNamePresentation = 1;
        this.mNumberPresentation = 1;
        this.mCallSlot = 0;
        this.mDialogId = cmcDialog.mDialogId;
        this.mPeerUri = cmcDialog.mPeerUri;
        this.mIsPullable = cmcDialog.mIsPullable;
        this.mState = cmcDialog.mState;
        this.mCallType = cmcDialog.mCallType;
        this.mIsHeld = cmcDialog.mIsHeld;
        this.mCnapName = cmcDialog.mCnapName;
        this.mCnapNamePresentation = cmcDialog.mCnapNamePresentation;
        this.mNumberPresentation = cmcDialog.mNumberPresentation;
        this.mCallSlot = cmcDialog.mCallSlot;
    }

    public CmcDialog(Builder builder) {
        this.mDialogId = "";
        this.mPeerUri = "";
        this.mIsPullable = true;
        this.mState = 2;
        this.mCallType = 1;
        this.mIsHeld = false;
        this.mCnapName = "";
        this.mCnapNamePresentation = 1;
        this.mNumberPresentation = 1;
        this.mCallSlot = 0;
        this.mDialogId = builder.mDialogId;
        this.mPeerUri = builder.mPeerUri;
        this.mIsPullable = builder.mIsPullable;
        this.mState = builder.mState;
        this.mCallType = builder.mCallType;
        this.mIsHeld = builder.mIsHeld;
        this.mCnapName = builder.mCnapName;
        this.mCnapNamePresentation = builder.mCnapNamePresentation;
        this.mNumberPresentation = builder.mNumberPresentation;
        this.mCallSlot = builder.mCallSlot;
    }
}
