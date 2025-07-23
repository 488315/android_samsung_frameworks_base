package android.telephony.euicc;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class EuiccNotification implements Parcelable {
    public static final int ALL_EVENTS = 15;
    public static final Parcelable.Creator<EuiccNotification> CREATOR = new Parcelable.Creator<EuiccNotification>() { // from class: android.telephony.euicc.EuiccNotification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EuiccNotification createFromParcel(Parcel parcel) {
            return new EuiccNotification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EuiccNotification[] newArray(int i) {
            return new EuiccNotification[i];
        }
    };
    public static final int EVENT_DELETE = 8;
    public static final int EVENT_DISABLE = 4;
    public static final int EVENT_ENABLE = 2;
    public static final int EVENT_INSTALL = 1;
    private final byte[] mData;
    private final int mEvent;
    private final int mSeq;
    private final String mTargetAddr;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Event {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public EuiccNotification(int i, String str, int i2, byte[] bArr) {
        this.mSeq = i;
        this.mTargetAddr = str;
        this.mEvent = i2;
        this.mData = bArr;
    }

    public int getSeq() {
        return this.mSeq;
    }

    public String getTargetAddr() {
        return this.mTargetAddr;
    }

    public int getEvent() {
        return this.mEvent;
    }

    public byte[] getData() {
        return this.mData;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            EuiccNotification euiccNotification = (EuiccNotification) obj;
            if (this.mSeq == euiccNotification.mSeq && Objects.equals(this.mTargetAddr, euiccNotification.mTargetAddr) && this.mEvent == euiccNotification.mEvent && Arrays.equals(this.mData, euiccNotification.mData)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.mSeq + 31) * 31) + Objects.hashCode(this.mTargetAddr)) * 31) + this.mEvent) * 31) + Arrays.hashCode(this.mData);
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder("EuiccNotification (seq=");
        sb.append(this.mSeq);
        sb.append(", targetAddr=");
        sb.append(this.mTargetAddr);
        sb.append(", event=");
        sb.append(this.mEvent);
        sb.append(", data=");
        if (this.mData == null) {
            str = PerfettoProtoLogImpl.NULL_STRING;
        } else {
            str = "byte[" + this.mData.length + NavigationBarInflaterView.SIZE_MOD_END;
        }
        sb.append(str);
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSeq);
        parcel.writeString(this.mTargetAddr);
        parcel.writeInt(this.mEvent);
        parcel.writeByteArray(this.mData);
    }

    private EuiccNotification(Parcel parcel) {
        this.mSeq = parcel.readInt();
        this.mTargetAddr = parcel.readString();
        this.mEvent = parcel.readInt();
        this.mData = parcel.createByteArray();
    }
}
