package com.sec.ims.volte2.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.sec.ims.util.IMSLog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class QuantumSecurityInfo implements Parcelable {
    public static final Parcelable.Creator<QuantumSecurityInfo> CREATOR = new Parcelable.Creator<QuantumSecurityInfo>() { // from class: com.sec.ims.volte2.data.QuantumSecurityInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QuantumSecurityInfo createFromParcel(Parcel parcel) {
            return new QuantumSecurityInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QuantumSecurityInfo[] newArray(int i) {
            return new QuantumSecurityInfo[i];
        }
    };
    public static int QUANTUM_ENCRYPT_KEY_LENGTH = 16;
    private int mCallDirection;
    private int mCryptoMode;
    private int mEncryptStatus;
    private String mLocalPhoneNumber;
    private int mPeerProfileStatus;
    private String mQtSessionId;
    private String mRemoteTelNum;
    private String mSessionKey;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class QUANTUM_AUTH_STATUS {
        public static final int AUTH_STATUS_FAIL = 1;
        public static final int AUTH_STATUS_INVALID = -1;
        public static final int AUTH_STATUS_SUCCESS = 0;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class QUANTUM_CALL_DIRECTION {
        public static final int CALL_DIR_CALLEE = 1;
        public static final int CALL_DIR_CALLER = 0;
        public static final int CALL_DIR_INVALID = -1;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class QUANTUM_CRYPTO_MODE {
        public static final int CRYPTO_MODE_INVALID = 0;
        public static final int CRYPTO_MODE_SM4_CBC = 2;
        public static final int CRYPTO_MODE_SM4_ECB = 1;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class QUANTUM_ENCRYPT_STATUS {
        public static final int DISABLED = 4;
        public static final int ENABLED = 3;
        public static final int OBTAIN_KEY = 2;
        public static final int PEER_VERIFIED = 1;
        public static final int PROGRESS = 0;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class QUANTUM_KEY_STATUS {
        public static final int KEY_STATUS_EXCEPTION = 352;
        public static final int KEY_STATUS_FAIL_TO_GET_KEY = 353;
        public static final int KEY_STATUS_INVALID = -1;
        public static final int KEY_STATUS_SUCCESS = 351;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class QUANTUM_LOGIN_STATUS {
        public static final int LOGIN_STATUS_CHANGE_LOGIN_FAIL = 404;
        public static final int LOGIN_STATUS_GET_CHANGE_FAIL = 401;
        public static final int LOGIN_STATUS_GET_KEY_HANDLE_FAIL = 402;
        public static final int LOGIN_STATUS_INVALID = -1;
        public static final int LOGIN_STATUS_RANDOM_ENCRYPT_FAIL = 403;
        public static final int LOGIN_STATUS_SUCCESS = 0;
        public static final int LOGIN_STATUS_TOKEN_STORAGE_FAIL = 405;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class QUANTUM_PEER_PROFILE_STATUS {
        public static final int PEER_PROFILE_STATUS_EXCEPTION = 403;
        public static final int PEER_PROFILE_STATUS_INVALID = -1;
        public static final int PEER_PROFILE_STATUS_NULL = 402;
        public static final int PEER_PROFILE_STATUS_SUCCESS = 401;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class QUANTUM_VOLTE_STATUS {
        public static final int VOLTE_STATUS_ERROR = 1;
        public static final int VOLTE_STATUS_INVALID = -1;
        public static final int VOLTE_STATUS_SUCCESS = 0;
    }

    public QuantumSecurityInfo() {
        this.mEncryptStatus = 0;
        this.mPeerProfileStatus = -1;
        this.mCallDirection = -1;
        this.mCryptoMode = 0;
        this.mQtSessionId = "";
        this.mRemoteTelNum = "";
        this.mSessionKey = "";
        this.mLocalPhoneNumber = "";
    }

    public synchronized void clearAll() {
        this.mEncryptStatus = 0;
        this.mPeerProfileStatus = -1;
        this.mCallDirection = -1;
        this.mCryptoMode = 0;
        this.mQtSessionId = "";
        this.mRemoteTelNum = "";
        this.mSessionKey = "";
        this.mLocalPhoneNumber = "";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCallDirection() {
        return this.mCallDirection;
    }

    public int getCryptoMode() {
        return this.mCryptoMode;
    }

    public synchronized int getEncryptStatus() {
        return this.mEncryptStatus;
    }

    public String getLocalPhoneNumber() {
        return this.mLocalPhoneNumber;
    }

    public int getPeerProfileStatus() {
        return this.mPeerProfileStatus;
    }

    public synchronized String getQtSessionId() {
        return this.mQtSessionId;
    }

    public String getRemoteTelNum() {
        return this.mRemoteTelNum;
    }

    public String getSessionKey() {
        return this.mSessionKey;
    }

    public void setCallDirection(int i) {
        this.mCallDirection = i;
    }

    public void setCryptoMode(int i) {
        this.mCryptoMode = i;
    }

    public synchronized void setEncryptStatus(int i) {
        this.mEncryptStatus = i;
    }

    public void setLocalPhoneNumber(String str) {
        this.mLocalPhoneNumber = str;
    }

    public void setPeerProfileStatus(int i) {
        this.mPeerProfileStatus = i;
    }

    public synchronized void setQtSessionId(String str) {
        this.mQtSessionId = str;
    }

    public void setRemoteTelNum(String str) {
        this.mRemoteTelNum = str;
    }

    public void setSessionKey(String str) {
        this.mSessionKey = str;
    }

    public String toString() {
        return "QuantumSecurityInfo [mEncryptStatus=" + this.mEncryptStatus + ", mPeerProfileStatus=" + this.mPeerProfileStatus + ", mCallDirection=" + this.mCallDirection + ", mCryptoMode=" + this.mCryptoMode + ", mQtSessionId=" + IMSLog.checker(this.mQtSessionId) + ", mSessionKey=" + IMSLog.checker(this.mSessionKey) + ", mLocalPhoneNumber=" + IMSLog.checker(this.mLocalPhoneNumber) + ", mRemoteTelNum=" + IMSLog.checker(this.mRemoteTelNum) + "]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mEncryptStatus);
        parcel.writeInt(this.mPeerProfileStatus);
        parcel.writeInt(this.mCallDirection);
        parcel.writeInt(this.mCryptoMode);
        parcel.writeString(this.mQtSessionId);
        parcel.writeString(this.mRemoteTelNum);
        parcel.writeString(this.mSessionKey);
        parcel.writeString(this.mLocalPhoneNumber);
    }

    public QuantumSecurityInfo(Parcel parcel) {
        this.mEncryptStatus = 0;
        this.mPeerProfileStatus = -1;
        this.mCallDirection = -1;
        this.mCryptoMode = 0;
        this.mQtSessionId = "";
        this.mRemoteTelNum = "";
        this.mSessionKey = "";
        this.mLocalPhoneNumber = "";
        this.mEncryptStatus = parcel.readInt();
        this.mPeerProfileStatus = parcel.readInt();
        this.mCallDirection = parcel.readInt();
        this.mCryptoMode = parcel.readInt();
        this.mQtSessionId = parcel.readString();
        this.mRemoteTelNum = parcel.readString();
        this.mSessionKey = parcel.readString();
        this.mLocalPhoneNumber = parcel.readString();
    }
}
