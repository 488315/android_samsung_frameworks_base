package com.sec.ims.cmc;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.sec.ims.util.IMSLog;
import java.util.Objects;

/* loaded from: classes4.dex */
public class CmcCallEventInfo implements Parcelable {
    public static final Parcelable.Creator<CmcCallEventInfo> CREATOR = new Parcelable.Creator<CmcCallEventInfo>() { // from class: com.sec.ims.cmc.CmcCallEventInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcCallEventInfo createFromParcel(Parcel parcel) {
            return new CmcCallEventInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CmcCallEventInfo[] newArray(int i) {
            return new CmcCallEventInfo[i];
        }
    };
    private static final String LOG_TAG = "CmcCallEventInfo";
    private Bundle mCallExtras;
    private int mCallId;
    private int mCallRadioTech;
    private String mCmcCallTime;
    private String mCnapName;
    private int mCnapNamePresentation;
    private String mDialogId;
    private int mDirection;
    private int mDisconnectCause;
    private int mErrorCode;
    private String mErrorMessage;
    private int mExternalCallSlotAtPd;
    private boolean mIsPulledCall;
    private int mNumberPresentation;
    private String mPeerUri;
    private String mPulledDialogId;

    public /* synthetic */ CmcCallEventInfo(Parcel parcel, int i) {
        this(parcel);
    }

    private static boolean areBundlesEqual(Bundle bundle, Bundle bundle2) {
        if (bundle == null || bundle2 == null) {
            return bundle == bundle2;
        }
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            if (str != null && !Objects.equals(bundle.get(str), bundle2.get(str))) {
                return false;
            }
        }
        return true;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CmcCallEventInfo)) {
            return false;
        }
        CmcCallEventInfo cmcCallEventInfo = (CmcCallEventInfo) obj;
        if (this.mCallId != cmcCallEventInfo.mCallId) {
            return false;
        }
        String str = this.mPeerUri;
        if (str == null) {
            if (cmcCallEventInfo.mPeerUri != null) {
                return false;
            }
        } else if (!str.equals(cmcCallEventInfo.mPeerUri)) {
            return false;
        }
        if (this.mDirection != cmcCallEventInfo.mDirection || this.mCallRadioTech != cmcCallEventInfo.mCallRadioTech || this.mIsPulledCall != cmcCallEventInfo.mIsPulledCall || this.mNumberPresentation != cmcCallEventInfo.mNumberPresentation) {
            return false;
        }
        String str2 = this.mCnapName;
        if (str2 == null) {
            if (cmcCallEventInfo.mCnapName != null) {
                return false;
            }
        } else if (!str2.equals(cmcCallEventInfo.mCnapName)) {
            return false;
        }
        if (this.mCnapNamePresentation != cmcCallEventInfo.mCnapNamePresentation || this.mExternalCallSlotAtPd != cmcCallEventInfo.mExternalCallSlotAtPd || this.mErrorCode != cmcCallEventInfo.mErrorCode) {
            return false;
        }
        String str3 = this.mErrorMessage;
        if (str3 == null) {
            if (cmcCallEventInfo.mErrorMessage != null) {
                return false;
            }
        } else if (!str3.equals(cmcCallEventInfo.mErrorMessage)) {
            return false;
        }
        if (this.mDisconnectCause != cmcCallEventInfo.mDisconnectCause) {
            return false;
        }
        String str4 = this.mCmcCallTime;
        if (str4 == null) {
            if (cmcCallEventInfo.mCmcCallTime != null) {
                return false;
            }
        } else if (!str4.equals(cmcCallEventInfo.mCmcCallTime)) {
            return false;
        }
        return areBundlesEqual(this.mCallExtras, cmcCallEventInfo.mCallExtras);
    }

    public boolean getCallExtraBool(String str, boolean z) {
        Bundle bundle = this.mCallExtras;
        return bundle == null ? z : bundle.getBoolean(str, z);
    }

    public int getCallExtraInt(String str, int i) {
        Bundle bundle = this.mCallExtras;
        return bundle == null ? i : bundle.getInt(str, i);
    }

    public Bundle getCallExtraOem() {
        Bundle bundle = this.mCallExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getBundle(CmcConstants.EXTRA_OEM_EXTRAS);
    }

    public String getCallExtraString(String str, String str2) {
        Bundle bundle = this.mCallExtras;
        return bundle == null ? str2 : bundle.getString(str, str2);
    }

    public Bundle getCallExtras() {
        return this.mCallExtras;
    }

    public int getCallId() {
        return this.mCallId;
    }

    public int getCallRadioTech() {
        return this.mCallRadioTech;
    }

    public String getCmcCallTime() {
        return this.mCmcCallTime;
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

    public int getDirection() {
        return this.mDirection;
    }

    public int getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public int getExternalCallSlotAtPd() {
        return this.mExternalCallSlotAtPd;
    }

    public boolean getIsPulledCall() {
        return this.mIsPulledCall;
    }

    public int getNumberPresentation() {
        return this.mNumberPresentation;
    }

    public String getPeerUri() {
        return this.mPeerUri;
    }

    public String getPulledDialogId() {
        return this.mPulledDialogId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CmcCallEventInfo [mCallId=");
        sb.append(this.mCallId);
        sb.append(", mPeerUri=");
        sb.append(IMSLog.checker(this.mPeerUri));
        sb.append(", mDirection=");
        int i = this.mDirection;
        sb.append(i == 0 ? "MO" : i == 1 ? "MT" : "UNKNOWN");
        sb.append(", mCallRadioTech=");
        sb.append(this.mCallRadioTech);
        sb.append(", mIsPulledCall=");
        sb.append(this.mIsPulledCall);
        sb.append(", mNumberPresentation=");
        sb.append(this.mNumberPresentation);
        sb.append(", mCnapName=");
        sb.append(IMSLog.checker(this.mCnapName));
        sb.append(", mCnapNamePresentation=");
        sb.append(this.mCnapNamePresentation);
        sb.append(", mExternalCallSlotAtPd=");
        sb.append(this.mExternalCallSlotAtPd);
        sb.append(", mPulledDialogId=");
        sb.append(IMSLog.checker(this.mPulledDialogId));
        sb.append(", mDialogId=");
        sb.append(IMSLog.checker(this.mDialogId));
        sb.append(", mErrorCode=");
        sb.append(this.mErrorCode);
        sb.append(", mErrorMessage=");
        sb.append(this.mErrorMessage);
        sb.append(", mDisconnectCause=");
        sb.append(this.mDisconnectCause);
        sb.append(", mCmcCallTime=");
        sb.append(this.mCmcCallTime);
        sb.append(", mCallExtras=");
        sb.append(this.mCallExtras);
        sb.append("]");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeInt(this.mCallId);
        parcel.writeString(this.mPeerUri);
        parcel.writeInt(this.mDirection);
        parcel.writeInt(this.mCallRadioTech);
        parcel.writeInt(this.mIsPulledCall ? 1 : 0);
        parcel.writeInt(this.mNumberPresentation);
        parcel.writeString(this.mCnapName);
        parcel.writeInt(this.mCnapNamePresentation);
        parcel.writeInt(this.mExternalCallSlotAtPd);
        parcel.writeString(this.mPulledDialogId);
        parcel.writeString(this.mDialogId);
        parcel.writeInt(this.mErrorCode);
        parcel.writeString(this.mErrorMessage);
        parcel.writeInt(this.mDisconnectCause);
        parcel.writeString(this.mCmcCallTime);
        parcel.writeBundle(this.mCallExtras);
    }

    private CmcCallEventInfo(Parcel parcel) {
        this.mCallId = -1;
        this.mPeerUri = "";
        this.mDirection = -1;
        this.mCallRadioTech = 0;
        this.mIsPulledCall = false;
        this.mNumberPresentation = 1;
        this.mCnapName = "";
        this.mCnapNamePresentation = 1;
        this.mExternalCallSlotAtPd = -1;
        this.mPulledDialogId = "";
        this.mDialogId = "";
        this.mErrorCode = 0;
        this.mErrorMessage = "";
        this.mDisconnectCause = -1;
        this.mCmcCallTime = "";
        this.mCallId = parcel.readInt();
        this.mPeerUri = parcel.readString();
        this.mDirection = parcel.readInt();
        this.mCallRadioTech = parcel.readInt();
        this.mIsPulledCall = parcel.readInt() == 1;
        this.mNumberPresentation = parcel.readInt();
        this.mCnapName = parcel.readString();
        this.mCnapNamePresentation = parcel.readInt();
        this.mExternalCallSlotAtPd = parcel.readInt();
        this.mPulledDialogId = parcel.readString();
        this.mDialogId = parcel.readString();
        this.mErrorCode = parcel.readInt();
        this.mErrorMessage = parcel.readString();
        this.mDisconnectCause = parcel.readInt();
        this.mCmcCallTime = parcel.readString();
        this.mCallExtras = parcel.readBundle();
    }

    public class Builder {
        protected int mCallId = -1;
        protected String mPeerUri = "";
        protected int mDirection = -1;
        protected int mCallRadioTech = 0;
        protected boolean mIsPulledCall = false;
        protected int mNumberPresentation = 1;
        protected String mCnapName = "";
        protected int mCnapNamePresentation = 1;
        protected int mExternalCallSlotAtPd = -1;
        private String mPulledDialogId = "";
        private String mDialogId = "";
        protected int mErrorCode = 0;
        protected String mErrorMessage = "";
        protected int mDisconnectCause = -1;
        protected String mCmcCallTime = "";
        protected Bundle mCallExtras = new Bundle();

        public Builder addCallExtraOem(String str, int i) {
            Bundle bundle = this.mCallExtras;
            if (bundle != null) {
                Bundle bundle2 = bundle.getBundle(CmcConstants.EXTRA_OEM_EXTRAS);
                if (bundle2 == null) {
                    bundle2 = new Bundle();
                }
                bundle2.putInt(str, i);
                this.mCallExtras.remove(CmcConstants.EXTRA_OEM_EXTRAS);
                this.mCallExtras.putBundle(CmcConstants.EXTRA_OEM_EXTRAS, bundle2);
            }
            return this;
        }

        public CmcCallEventInfo build() {
            return new CmcCallEventInfo(this);
        }

        public Builder setCallExtraBool(String str, boolean z) {
            Bundle bundle = this.mCallExtras;
            if (bundle != null) {
                bundle.putBoolean(str, z);
            }
            return this;
        }

        public Builder setCallExtraInt(String str, int i) {
            Bundle bundle = this.mCallExtras;
            if (bundle != null) {
                bundle.putInt(str, i);
            }
            return this;
        }

        public Builder setCallExtraString(String str, String str2) {
            Bundle bundle = this.mCallExtras;
            if (bundle != null) {
                bundle.putString(str, str2);
            }
            return this;
        }

        public Builder setCallExtras(Bundle bundle) {
            this.mCallExtras = bundle;
            return this;
        }

        public Builder setCallId(int i) {
            this.mCallId = i;
            return this;
        }

        public Builder setCallRadioTech(int i) {
            this.mCallRadioTech = i;
            return this;
        }

        public Builder setCmcCallTime(String str) {
            this.mCmcCallTime = str;
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

        public Builder setDirection(int i) {
            this.mDirection = i;
            return this;
        }

        public Builder setDisconnectCause(int i) {
            this.mDisconnectCause = i;
            return this;
        }

        public Builder setErrorCode(int i) {
            this.mErrorCode = i;
            return this;
        }

        public Builder setErrorMessage(String str) {
            this.mErrorMessage = str;
            return this;
        }

        public Builder setExternalCallSlotAtPd(int i) {
            this.mExternalCallSlotAtPd = i;
            return this;
        }

        public Builder setIsPulledCall(boolean z) {
            this.mIsPulledCall = z;
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

        public Builder setPulledDialogId(String str) {
            this.mPulledDialogId = str;
            return this;
        }

        public Builder addCallExtraOem(String str, String str2) {
            Bundle bundle = this.mCallExtras;
            if (bundle != null) {
                Bundle bundle2 = bundle.getBundle(CmcConstants.EXTRA_OEM_EXTRAS);
                if (bundle2 == null) {
                    bundle2 = new Bundle();
                }
                bundle2.putString(str, str2);
                this.mCallExtras.remove(CmcConstants.EXTRA_OEM_EXTRAS);
                this.mCallExtras.putBundle(CmcConstants.EXTRA_OEM_EXTRAS, bundle2);
            }
            return this;
        }
    }

    public CmcCallEventInfo() {
        this.mCallId = -1;
        this.mPeerUri = "";
        this.mDirection = -1;
        this.mCallRadioTech = 0;
        this.mIsPulledCall = false;
        this.mNumberPresentation = 1;
        this.mCnapName = "";
        this.mCnapNamePresentation = 1;
        this.mExternalCallSlotAtPd = -1;
        this.mPulledDialogId = "";
        this.mDialogId = "";
        this.mErrorCode = 0;
        this.mErrorMessage = "";
        this.mDisconnectCause = -1;
        this.mCmcCallTime = "";
        this.mCallExtras = new Bundle();
    }

    public CmcCallEventInfo(CmcCallEventInfo cmcCallEventInfo) {
        this.mCallId = -1;
        this.mPeerUri = "";
        this.mDirection = -1;
        this.mCallRadioTech = 0;
        this.mIsPulledCall = false;
        this.mNumberPresentation = 1;
        this.mCnapName = "";
        this.mCnapNamePresentation = 1;
        this.mExternalCallSlotAtPd = -1;
        this.mPulledDialogId = "";
        this.mDialogId = "";
        this.mErrorCode = 0;
        this.mErrorMessage = "";
        this.mDisconnectCause = -1;
        this.mCmcCallTime = "";
        this.mCallId = cmcCallEventInfo.mCallId;
        this.mPeerUri = cmcCallEventInfo.mPeerUri;
        this.mDirection = cmcCallEventInfo.mDirection;
        this.mCallRadioTech = cmcCallEventInfo.mCallRadioTech;
        this.mIsPulledCall = cmcCallEventInfo.mIsPulledCall;
        this.mNumberPresentation = cmcCallEventInfo.mNumberPresentation;
        this.mCnapName = cmcCallEventInfo.mCnapName;
        this.mCnapNamePresentation = cmcCallEventInfo.mCnapNamePresentation;
        this.mExternalCallSlotAtPd = cmcCallEventInfo.mExternalCallSlotAtPd;
        this.mPulledDialogId = cmcCallEventInfo.mPulledDialogId;
        this.mDialogId = cmcCallEventInfo.mDialogId;
        this.mErrorCode = cmcCallEventInfo.mErrorCode;
        this.mErrorMessage = cmcCallEventInfo.mErrorMessage;
        this.mDisconnectCause = cmcCallEventInfo.mDisconnectCause;
        this.mCmcCallTime = cmcCallEventInfo.mCmcCallTime;
        this.mCallExtras = cmcCallEventInfo.mCallExtras;
    }

    public CmcCallEventInfo(Builder builder) {
        this.mCallId = -1;
        this.mPeerUri = "";
        this.mDirection = -1;
        this.mCallRadioTech = 0;
        this.mIsPulledCall = false;
        this.mNumberPresentation = 1;
        this.mCnapName = "";
        this.mCnapNamePresentation = 1;
        this.mExternalCallSlotAtPd = -1;
        this.mPulledDialogId = "";
        this.mDialogId = "";
        this.mErrorCode = 0;
        this.mErrorMessage = "";
        this.mDisconnectCause = -1;
        this.mCmcCallTime = "";
        this.mCallId = builder.mCallId;
        this.mPeerUri = builder.mPeerUri;
        this.mDirection = builder.mDirection;
        this.mCallRadioTech = builder.mCallRadioTech;
        this.mIsPulledCall = builder.mIsPulledCall;
        this.mNumberPresentation = builder.mNumberPresentation;
        this.mCnapName = builder.mCnapName;
        this.mCnapNamePresentation = builder.mCnapNamePresentation;
        this.mExternalCallSlotAtPd = builder.mExternalCallSlotAtPd;
        this.mPulledDialogId = builder.mPulledDialogId;
        this.mDialogId = builder.mDialogId;
        this.mErrorCode = builder.mErrorCode;
        this.mErrorMessage = builder.mErrorMessage;
        this.mDisconnectCause = builder.mDisconnectCause;
        this.mCmcCallTime = builder.mCmcCallTime;
        this.mCallExtras = builder.mCallExtras;
    }
}
