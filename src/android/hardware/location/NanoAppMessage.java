package android.hardware.location;

import android.annotation.SystemApi;
import android.chre.flags.Flags;
import android.hardware.hdmi.HdmiControlManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Logging.Session;
import java.util.Arrays;
import java.util.Objects;
import libcore.util.HexEncoding;

@SystemApi
/* loaded from: classes2.dex */
public final class NanoAppMessage implements Parcelable {
    public static final Parcelable.Creator<NanoAppMessage> CREATOR = new Parcelable.Creator<NanoAppMessage>() { // from class: android.hardware.location.NanoAppMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoAppMessage createFromParcel(Parcel parcel) {
            return new NanoAppMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoAppMessage[] newArray(int i) {
            return new NanoAppMessage[i];
        }
    };
    private static final int DEBUG_LOG_NUM_BYTES = 16;
    private boolean mIsBroadcasted;
    private boolean mIsReliable;
    private byte[] mMessageBody;
    private int mMessageSequenceNumber;
    private int mMessageType;
    private long mNanoAppId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private NanoAppMessage(long j, int i, byte[] bArr, boolean z, boolean z2, int i2) {
        this.mNanoAppId = j;
        this.mMessageType = i;
        this.mMessageBody = bArr;
        this.mIsBroadcasted = z;
        this.mIsReliable = z2;
        this.mMessageSequenceNumber = i2;
    }

    public static NanoAppMessage createMessageToNanoApp(long j, int i, byte[] bArr) {
        return new NanoAppMessage(j, i, bArr, false, false, 0);
    }

    public static NanoAppMessage createMessageFromNanoApp(long j, int i, byte[] bArr, boolean z) {
        return new NanoAppMessage(j, i, bArr, z, false, 0);
    }

    public static NanoAppMessage createMessageFromNanoApp(long j, int i, byte[] bArr, boolean z, boolean z2, int i2) {
        return new NanoAppMessage(j, i, bArr, z, z2, i2);
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

    public void setIsReliable(boolean z) {
        this.mIsReliable = z;
    }

    public void setMessageSequenceNumber(int i) {
        this.mMessageSequenceNumber = i;
    }

    private NanoAppMessage(Parcel parcel) {
        this.mNanoAppId = parcel.readLong();
        this.mIsBroadcasted = parcel.readInt() == 1;
        this.mMessageType = parcel.readInt();
        byte[] bArr = new byte[parcel.readInt()];
        this.mMessageBody = bArr;
        parcel.readByteArray(bArr);
        this.mIsReliable = parcel.readInt() == 1;
        this.mMessageSequenceNumber = parcel.readInt();
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
        StringBuilder sb = new StringBuilder("NanoAppMessage[type = ");
        sb.append(this.mMessageType);
        sb.append(", length = ");
        sb.append(this.mMessageBody.length);
        sb.append(" bytes, ");
        sb.append(this.mIsBroadcasted ? HdmiControlManager.POWER_CONTROL_MODE_BROADCAST : "unicast");
        sb.append(", nanoapp = 0x");
        sb.append(Long.toHexString(this.mNanoAppId));
        sb.append(", isReliable = ");
        sb.append(this.mIsReliable ? "true" : "false");
        sb.append(", messageSequenceNumber = ");
        sb.append(this.mMessageSequenceNumber);
        sb.append("](");
        if (length > 0) {
            sb.append("data = 0x");
        }
        int i = 0;
        while (i < Math.min(length, 16)) {
            sb.append(HexEncoding.encodeToString(this.mMessageBody[i], true));
            i++;
            if (i % 4 == 0) {
                sb.append(" ");
            }
        }
        if (length > 16) {
            sb.append(Session.TRUNCATE_STRING);
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof NanoAppMessage) {
            NanoAppMessage nanoAppMessage = (NanoAppMessage) obj;
            if (nanoAppMessage.getNanoAppId() == this.mNanoAppId && nanoAppMessage.getMessageType() == this.mMessageType && nanoAppMessage.isBroadcastMessage() == this.mIsBroadcasted && Arrays.equals(nanoAppMessage.getMessageBody(), this.mMessageBody) && ((!Flags.reliableMessage() || nanoAppMessage.isReliable() == this.mIsReliable) && (!Flags.reliableMessage() || nanoAppMessage.getMessageSequenceNumber() == this.mMessageSequenceNumber))) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        if (!Flags.fixApiCheck()) {
            return super.hashCode();
        }
        return Objects.hash(Long.valueOf(this.mNanoAppId), Integer.valueOf(this.mMessageType), Boolean.valueOf(this.mIsBroadcasted), Integer.valueOf(Arrays.hashCode(this.mMessageBody)), Boolean.valueOf(this.mIsReliable), Integer.valueOf(this.mMessageSequenceNumber));
    }
}
