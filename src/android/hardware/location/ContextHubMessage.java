package android.hardware.location;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Logging.Session;
import java.util.Arrays;
import libcore.util.HexEncoding;

@SystemApi
@Deprecated
/* loaded from: classes2.dex */
public class ContextHubMessage implements Parcelable {
    public static final Parcelable.Creator<ContextHubMessage> CREATOR = new Parcelable.Creator<ContextHubMessage>() { // from class: android.hardware.location.ContextHubMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextHubMessage createFromParcel(Parcel parcel) {
            return new ContextHubMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextHubMessage[] newArray(int i) {
            return new ContextHubMessage[i];
        }
    };
    private static final int DEBUG_LOG_NUM_BYTES = 16;
    private byte[] mData;
    private int mType;
    private int mVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getMsgType() {
        return this.mType;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public byte[] getData() {
        byte[] bArr = this.mData;
        return Arrays.copyOf(bArr, bArr.length);
    }

    public void setMsgType(int i) {
        this.mType = i;
    }

    public void setVersion(int i) {
        this.mVersion = i;
    }

    public void setMsgData(byte[] bArr) {
        this.mData = Arrays.copyOf(bArr, bArr.length);
    }

    public ContextHubMessage(int i, int i2, byte[] bArr) {
        this.mType = i;
        this.mVersion = i2;
        this.mData = Arrays.copyOf(bArr, bArr.length);
    }

    private ContextHubMessage(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mVersion = parcel.readInt();
        byte[] bArr = new byte[parcel.readInt()];
        this.mData = bArr;
        parcel.readByteArray(bArr);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mData.length);
        parcel.writeByteArray(this.mData);
    }

    public String toString() {
        int length = this.mData.length;
        String str = "ContextHubMessage[type = " + this.mType + ", length = " + this.mData.length + " bytes](";
        if (length > 0) {
            str = str + "data = 0x";
        }
        int i = 0;
        while (i < Math.min(length, 16)) {
            str = str + HexEncoding.encodeToString(this.mData[i], true);
            i++;
            if (i % 4 == 0) {
                str = str + " ";
            }
        }
        if (length > 16) {
            str = str + Session.TRUNCATE_STRING;
        }
        return str + NavigationBarInflaterView.KEY_CODE_END;
    }
}
