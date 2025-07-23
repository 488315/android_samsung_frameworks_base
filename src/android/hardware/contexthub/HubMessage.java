package android.hardware.contexthub;

import android.annotation.SystemApi;
import android.chre.flags.Flags;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Logging.Session;
import java.util.Arrays;
import java.util.Objects;
import libcore.util.HexEncoding;

@SystemApi
/* loaded from: classes2.dex */
public final class HubMessage implements Parcelable {
    public static final Parcelable.Creator<HubMessage> CREATOR = new Parcelable.Creator<HubMessage>() { // from class: android.hardware.contexthub.HubMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HubMessage createFromParcel(Parcel parcel) {
            return new HubMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HubMessage[] newArray(int i) {
            return new HubMessage[i];
        }
    };
    private static final int DEBUG_LOG_NUM_BYTES = 16;
    private final byte[] mMessageBody;
    private int mMessageSequenceNumber;
    private final int mMessageType;
    private final boolean mResponseRequired;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private HubMessage(int i, byte[] bArr, boolean z) {
        Objects.requireNonNull(bArr, "messageBody cannot be null");
        this.mMessageType = i;
        this.mMessageBody = bArr;
        this.mResponseRequired = z;
    }

    public int getMessageType() {
        return this.mMessageType;
    }

    public byte[] getMessageBody() {
        return this.mMessageBody;
    }

    public boolean isResponseRequired() {
        return this.mResponseRequired;
    }

    public void setMessageSequenceNumber(int i) {
        this.mMessageSequenceNumber = i;
    }

    public int getMessageSequenceNumber() {
        return this.mMessageSequenceNumber;
    }

    private HubMessage(Parcel parcel) {
        this.mMessageType = parcel.readInt();
        byte[] bArr = new byte[parcel.readInt()];
        this.mMessageBody = bArr;
        parcel.readByteArray(bArr);
        this.mResponseRequired = parcel.readInt() == 1;
        this.mMessageSequenceNumber = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMessageType);
        parcel.writeInt(this.mMessageBody.length);
        parcel.writeByteArray(this.mMessageBody);
        parcel.writeInt(this.mResponseRequired ? 1 : 0);
        parcel.writeInt(this.mMessageSequenceNumber);
    }

    public String toString() {
        int length = this.mMessageBody.length;
        StringBuilder sb = new StringBuilder("HubMessage[type = ");
        sb.append(this.mMessageType);
        sb.append(", length = ");
        sb.append(this.mMessageBody.length);
        sb.append(", messageSequenceNumber = ");
        sb.append(this.mMessageSequenceNumber);
        sb.append(", responseRequired = ");
        sb.append(this.mResponseRequired);
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
        if (obj instanceof HubMessage) {
            HubMessage hubMessage = (HubMessage) obj;
            if (hubMessage.getMessageType() == this.mMessageType && Arrays.equals(hubMessage.getMessageBody(), this.mMessageBody) && hubMessage.isResponseRequired() == this.mResponseRequired && hubMessage.getMessageSequenceNumber() == this.mMessageSequenceNumber) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        if (!Flags.fixApiCheck()) {
            return super.hashCode();
        }
        return Objects.hash(Integer.valueOf(this.mMessageType), Integer.valueOf(Arrays.hashCode(this.mMessageBody)), Boolean.valueOf(this.mResponseRequired), Integer.valueOf(this.mMessageSequenceNumber));
    }

    public static final class Builder {
        private byte[] mMessageBody;
        private int mMessageType;
        private boolean mResponseRequired = false;

        public Builder(int i, byte[] bArr) {
            this.mMessageType = i;
            this.mMessageBody = bArr;
        }

        public Builder setResponseRequired(boolean z) {
            this.mResponseRequired = z;
            return this;
        }

        public HubMessage build() {
            return new HubMessage(this.mMessageType, this.mMessageBody, this.mResponseRequired);
        }
    }
}
