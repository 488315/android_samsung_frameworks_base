package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.SmsWriteArgsStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehSimMsgArgs {
    public int index = 0;
    public int status = 0;
    public String pdu = new String();
    public String smsc = new String();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehSimMsgArgs.class) {
            return false;
        }
        SehSimMsgArgs other = (SehSimMsgArgs) otherObject;
        if (this.index == other.index
                && this.status == other.status
                && HidlSupport.deepEquals(this.pdu, other.pdu)
                && HidlSupport.deepEquals(this.smsc, other.smsc)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.index))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.status))),
                Integer.valueOf(HidlSupport.deepHashCode(this.pdu)),
                Integer.valueOf(HidlSupport.deepHashCode(this.smsc)));
    }

    public final String toString() {
        return "{.index = "
                + this.index
                + ", .status = "
                + SmsWriteArgsStatus.toString(this.status)
                + ", .pdu = "
                + this.pdu
                + ", .smsc = "
                + this.smsc
                + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(40L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehSimMsgArgs> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehSimMsgArgs> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob =
                parcel.readEmbeddedBuffer(_hidl_vec_size * 40, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehSimMsgArgs _hidl_vec_element = new SehSimMsgArgs();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 40);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(
            HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.index = _hidl_blob.getInt32(_hidl_offset + 0);
        this.status = _hidl_blob.getInt32(_hidl_offset + 4);
        this.pdu = _hidl_blob.getString(_hidl_offset + 8);
        parcel.readEmbeddedBuffer(
                this.pdu.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 8 + 0, false);
        this.smsc = _hidl_blob.getString(_hidl_offset + 24);
        parcel.readEmbeddedBuffer(
                this.smsc.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 24 + 0, false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(40);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(
            HwParcel parcel, ArrayList<SehSimMsgArgs> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 40);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 40);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(0 + _hidl_offset, this.index);
        _hidl_blob.putInt32(4 + _hidl_offset, this.status);
        _hidl_blob.putString(8 + _hidl_offset, this.pdu);
        _hidl_blob.putString(24 + _hidl_offset, this.smsc);
    }
}
