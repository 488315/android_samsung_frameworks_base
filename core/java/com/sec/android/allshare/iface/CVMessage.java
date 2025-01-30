package com.sec.android.allshare.iface;

import android.p009os.Bundle;
import android.p009os.Messenger;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes6.dex */
public class CVMessage implements Parcelable {
    public static final Parcelable.Creator<CVMessage> CREATOR = new Parcelable.Creator<CVMessage>() { // from class: com.sec.android.allshare.iface.CVMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CVMessage createFromParcel(Parcel src) {
            return new CVMessage(src);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CVMessage[] newArray(int size) {
            return new CVMessage[size];
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

    public CVMessage() {
        this(1, "", null);
    }

    public CVMessage(int type) {
        this(type, "", null);
    }

    public CVMessage(int type, String action) {
        this(type, action, null);
    }

    public CVMessage(int type, String action, Bundle bundle) {
        this.mVersion = 1L;
        this.mType = type;
        this.mMessageID = 0L;
        this.mActionID = action;
        this.mBundle = bundle;
        this.mReplyMessenger = null;
    }

    public void setActionID(String action_id) {
        this.mActionID = action_id;
    }

    public void setEventID(String event_id) {
        this.mActionID = event_id;
    }

    public void setMsgType(int type) {
        this.mType = type;
    }

    public void setMsgID(long id) {
        this.mMessageID = id;
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

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dst, int flags) {
        dst.writeLong(this.mVersion);
        dst.writeInt(this.mType);
        dst.writeLong(this.mMessageID);
        dst.writeString(this.mActionID);
        dst.writeBundle(this.mBundle);
        dst.writeParcelable(this.mReplyMessenger, 0);
    }

    public void readFromParcel(Parcel src) {
        this.mVersion = src.readLong();
        this.mType = src.readInt();
        this.mMessageID = src.readLong();
        this.mActionID = src.readString();
        this.mBundle = src.readBundle(CVMessage.class.getClassLoader());
        this.mReplyMessenger = (Messenger) src.readParcelable(null);
    }

    private CVMessage(Parcel src) {
        this.mVersion = 1L;
        readFromParcel(src);
    }
}
