package android.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.uicc.IccUtils;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public class PcoData implements Parcelable {
    public static final Parcelable.Creator<PcoData> CREATOR = new Parcelable.Creator() { // from class: android.telephony.PcoData.1
        @Override // android.os.Parcelable.Creator
        public PcoData createFromParcel(Parcel parcel) {
            return new PcoData(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public PcoData[] newArray(int i) {
            return new PcoData[i];
        }
    };
    public final String bearerProto;
    public final int cid;
    public final byte[] contents;
    public final int pcoId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PcoData(int i, String str, int i2, byte[] bArr) {
        this.cid = i;
        this.bearerProto = str;
        this.pcoId = i2;
        this.contents = bArr;
    }

    public PcoData(Parcel parcel) {
        this.cid = parcel.readInt();
        this.bearerProto = parcel.readString();
        this.pcoId = parcel.readInt();
        this.contents = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.cid);
        parcel.writeString(this.bearerProto);
        parcel.writeInt(this.pcoId);
        parcel.writeByteArray(this.contents);
    }

    public String toString() {
        return "PcoData(" + this.cid + ", " + this.bearerProto + ", " + this.pcoId + " " + IccUtils.bytesToHexString(this.contents) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PcoData pcoData = (PcoData) obj;
            if (this.cid == pcoData.cid && this.pcoId == pcoData.pcoId && Objects.equals(this.bearerProto, pcoData.bearerProto) && Arrays.equals(this.contents, pcoData.contents)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (Objects.hash(Integer.valueOf(this.cid), this.bearerProto, Integer.valueOf(this.pcoId)) * 31) + Arrays.hashCode(this.contents);
    }
}
