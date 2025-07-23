package com.sec.android.allshare.iface;

import android.os.Bundle;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class CVMessage implements Parcelable {
    public static final Parcelable.Creator<CVMessage> CREATOR = new Parcelable.Creator<CVMessage>() { // from class: com.sec.android.allshare.iface.CVMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CVMessage createFromParcel(Parcel parcel) {
            return new CVMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CVMessage[] newArray(int i) {
            return new CVMessage[i];
        }
    };
    public static final int CVM_TYPE_EVENT = 4;
    public static final int CVM_TYPE_REQUEST = 2;
    public static final int CVM_TYPE_RESPONSE = 3;
    public static final int CVM_TYPE_UNDEF = 1;
    public static final String EVT_MSG_KEY = "EVT_MSG_KEY";
    public static final String RES_MSG_KEY = "RES_MSG_KEY";
    private String mActionID;
    private Bundle mBundle;
    private long mMessageID;
    private Messenger mReplyMessenger;
    private int mType;
    private long mVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CVMessage() {
        this(1, "", null);
    }

    public CVMessage(int i) {
        this(i, "", null);
    }

    public CVMessage(int i, String str) {
        this(i, str, null);
    }

    public CVMessage(int i, String str, Bundle bundle) {
        this.mVersion = 1L;
        this.mType = i;
        this.mMessageID = 0L;
        this.mActionID = str;
        this.mBundle = bundle;
        this.mReplyMessenger = null;
    }

    public void setActionID(String str) {
        this.mActionID = str;
    }

    public void setEventID(String str) {
        this.mActionID = str;
    }

    public void setMsgType(int i) {
        this.mType = i;
    }

    public void setMsgID(long j) {
        this.mMessageID = j;
    }

    public void setBundle(Bundle bundle) {
        this.mBundle = bundle;
    }

    public void setMessenger(Messenger messenger) {
        this.mReplyMessenger = messenger;
    }

    public final long getVersion() {
        return this.mVersion;
    }

    public final String getActionID() {
        return this.mActionID;
    }

    public final String getEventID() {
        return this.mActionID;
    }

    public final int getMsgType() {
        return this.mType;
    }

    public final long getMsgID() {
        return this.mMessageID;
    }

    public final Messenger getMessenger() {
        return this.mReplyMessenger;
    }

    public final Bundle getBundle() {
        if (this.mBundle == null) {
            this.mBundle = new Bundle();
        }
        this.mBundle.setClassLoader(getClass().getClassLoader());
        return this.mBundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mVersion);
        parcel.writeInt(this.mType);
        parcel.writeLong(this.mMessageID);
        parcel.writeString(this.mActionID);
        parcel.writeBundle(this.mBundle);
        parcel.writeParcelable(this.mReplyMessenger, 0);
    }

    public void readFromParcel(Parcel parcel) {
        this.mVersion = parcel.readLong();
        this.mType = parcel.readInt();
        this.mMessageID = parcel.readLong();
        this.mActionID = parcel.readString();
        this.mBundle = parcel.readBundle(CVMessage.class.getClassLoader());
        this.mReplyMessenger = (Messenger) parcel.readParcelable(null);
    }

    private CVMessage(Parcel parcel) {
        this.mVersion = 1L;
        readFromParcel(parcel);
    }
}
