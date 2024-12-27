package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehSendSmsResult {
    public int messageRef = 0;
    public String ackPDU = new String();
    public int errorCode = 0;
    public int errorClass = 0;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehSendSmsResult.class) {
            return false;
        }
        SehSendSmsResult other = (SehSendSmsResult) otherObject;
        if (this.messageRef == other.messageRef && HidlSupport.deepEquals(this.ackPDU, other.ackPDU) && this.errorCode == other.errorCode && this.errorClass == other.errorClass) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.messageRef))), Integer.valueOf(HidlSupport.deepHashCode(this.ackPDU)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.errorCode))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.errorClass))));
    }

    public final String toString() {
        return "{.messageRef = " + this.messageRef + ", .ackPDU = " + this.ackPDU + ", .errorCode = " + this.errorCode + ", .errorClass = " + this.errorClass + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(32L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehSendSmsResult> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehSendSmsResult> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehSendSmsResult _hidl_vec_element = new SehSendSmsResult();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 32);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.messageRef = _hidl_blob.getInt32(_hidl_offset + 0);
        this.ackPDU = _hidl_blob.getString(_hidl_offset + 8);
        parcel.readEmbeddedBuffer(this.ackPDU.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 8 + 0, false);
        this.errorCode = _hidl_blob.getInt32(_hidl_offset + 24);
        this.errorClass = _hidl_blob.getInt32(_hidl_offset + 28);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(32);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<SehSendSmsResult> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 32);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 32);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(0 + _hidl_offset, this.messageRef);
        _hidl_blob.putString(8 + _hidl_offset, this.ackPDU);
        _hidl_blob.putInt32(24 + _hidl_offset, this.errorCode);
        _hidl_blob.putInt32(28 + _hidl_offset, this.errorClass);
    }
}
