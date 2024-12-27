package android.hardware.location;

import android.annotation.SystemApi;
import android.chre.flags.Flags;
import android.hardware.hdmi.HdmiControlManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Logging.Session;

import libcore.util.HexEncoding;

import java.util.Arrays;

@SystemApi
public final class NanoAppMessage implements Parcelable {
    public static final Parcelable.Creator<NanoAppMessage> CREATOR =
            new Parcelable.Creator<
                    NanoAppMessage>() { // from class: android.hardware.location.NanoAppMessage.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NanoAppMessage createFromParcel(Parcel in) {
                    return new NanoAppMessage(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NanoAppMessage[] newArray(int size) {
                    return new NanoAppMessage[size];
                }
            };
    private static final int DEBUG_LOG_NUM_BYTES = 16;
    private boolean mIsBroadcasted;
    private boolean mIsReliable;
    private byte[] mMessageBody;
    private int mMessageSequenceNumber;
    private int mMessageType;
    private long mNanoAppId;

    private NanoAppMessage(
            long nanoAppId,
            int messageType,
            byte[] messageBody,
            boolean broadcasted,
            boolean isReliable,
            int messageSequenceNumber) {
        this.mNanoAppId = nanoAppId;
        this.mMessageType = messageType;
        this.mMessageBody = messageBody;
        this.mIsBroadcasted = broadcasted;
        this.mIsReliable = isReliable;
        this.mMessageSequenceNumber = messageSequenceNumber;
    }

    public static NanoAppMessage createMessageToNanoApp(
            long targetNanoAppId, int messageType, byte[] messageBody) {
        return new NanoAppMessage(targetNanoAppId, messageType, messageBody, false, false, 0);
    }

    public static NanoAppMessage createMessageFromNanoApp(
            long sourceNanoAppId, int messageType, byte[] messageBody, boolean broadcasted) {
        return new NanoAppMessage(sourceNanoAppId, messageType, messageBody, broadcasted, false, 0);
    }

    public static NanoAppMessage createMessageFromNanoApp(
            long sourceNanoAppId,
            int messageType,
            byte[] messageBody,
            boolean broadcasted,
            boolean isReliable,
            int messageSequenceNumber) {
        return new NanoAppMessage(
                sourceNanoAppId,
                messageType,
                messageBody,
                broadcasted,
                isReliable,
                messageSequenceNumber);
    }

    public long getNanoAppId() {
        return this.mNanoAppId;
    }

    public int getMessageType() {
        return this.mMessageType;
    }

    public byte[] getMessageBody() {
        return this.mMessageBody;
    }

    public boolean isBroadcastMessage() {
        return this.mIsBroadcasted;
    }

    public boolean isReliable() {
        return this.mIsReliable;
    }

    public int getMessageSequenceNumber() {
        return this.mMessageSequenceNumber;
    }

    public void setIsReliable(boolean isReliable) {
        this.mIsReliable = isReliable;
    }

    public void setMessageSequenceNumber(int messageSequenceNumber) {
        this.mMessageSequenceNumber = messageSequenceNumber;
    }

    private NanoAppMessage(Parcel in) {
        this.mNanoAppId = in.readLong();
        this.mIsBroadcasted = in.readInt() == 1;
        this.mMessageType = in.readInt();
        int msgSize = in.readInt();
        this.mMessageBody = new byte[msgSize];
        in.readByteArray(this.mMessageBody);
        this.mIsReliable = in.readInt() == 1;
        this.mMessageSequenceNumber = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mNanoAppId);
        parcel.writeInt(this.mIsBroadcasted ? 1 : 0);
        parcel.writeInt(this.mMessageType);
        parcel.writeInt(this.mMessageBody.length);
        parcel.writeByteArray(this.mMessageBody);
        parcel.writeInt(this.mIsReliable ? 1 : 0);
        parcel.writeInt(this.mMessageSequenceNumber);
    }

    public String toString() {
        int length = this.mMessageBody.length;
        String ret =
                "NanoAppMessage[type = "
                        + this.mMessageType
                        + ", length = "
                        + this.mMessageBody.length
                        + " bytes, "
                        + (this.mIsBroadcasted
                                ? HdmiControlManager.POWER_CONTROL_MODE_BROADCAST
                                : "unicast")
                        + ", nanoapp = 0x"
                        + Long.toHexString(this.mNanoAppId)
                        + ", isReliable = "
                        + (this.mIsReliable ? "true" : "false")
                        + ", messageSequenceNumber = "
                        + this.mMessageSequenceNumber
                        + "](";
        if (length > 0) {
            ret = ret + "data = 0x";
        }
        for (int i = 0; i < Math.min(length, 16); i++) {
            ret = ret + HexEncoding.encodeToString(this.mMessageBody[i], true);
            if ((i + 1) % 4 == 0) {
                ret = ret + " ";
            }
        }
        if (length > 16) {
            ret = ret + Session.TRUNCATE_STRING;
        }
        return ret + NavigationBarInflaterView.KEY_CODE_END;
    }

    public boolean equals(Object object) {
        boolean z = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof NanoAppMessage)) {
            return false;
        }
        NanoAppMessage other = (NanoAppMessage) object;
        if (other.getNanoAppId() != this.mNanoAppId
                || other.getMessageType() != this.mMessageType
                || other.isBroadcastMessage() != this.mIsBroadcasted
                || !Arrays.equals(other.getMessageBody(), this.mMessageBody)
                || ((Flags.reliableMessage() && other.isReliable() != this.mIsReliable)
                        || (Flags.reliableMessage()
                                && other.getMessageSequenceNumber()
                                        != this.mMessageSequenceNumber))) {
            z = false;
        }
        boolean isEqual = z;
        return isEqual;
    }
}
