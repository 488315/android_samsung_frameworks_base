package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.OperatorInfo;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

public final class SehOperatorInfo {
    public OperatorInfo base = new OperatorInfo();
    public String rat = new String();
    public String lac = new String();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehOperatorInfo.class) {
            return false;
        }
        SehOperatorInfo other = (SehOperatorInfo) otherObject;
        if (HidlSupport.deepEquals(this.base, other.base)
                && HidlSupport.deepEquals(this.rat, other.rat)
                && HidlSupport.deepEquals(this.lac, other.lac)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.base)),
                Integer.valueOf(HidlSupport.deepHashCode(this.rat)),
                Integer.valueOf(HidlSupport.deepHashCode(this.lac)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .rat = " + this.rat + ", .lac = " + this.lac + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(88L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehOperatorInfo> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehOperatorInfo> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob =
                parcel.readEmbeddedBuffer(_hidl_vec_size * 88, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehOperatorInfo _hidl_vec_element = new SehOperatorInfo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 88);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(
            HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.base.readEmbeddedFromParcel(parcel, _hidl_blob, _hidl_offset + 0);
        this.rat = _hidl_blob.getString(_hidl_offset + 56);
        parcel.readEmbeddedBuffer(
                this.rat.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 56 + 0, false);
        this.lac = _hidl_blob.getString(_hidl_offset + 72);
        parcel.readEmbeddedBuffer(
                this.lac.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 72 + 0, false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(88);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(
            HwParcel parcel, ArrayList<SehOperatorInfo> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 88);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 88);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        this.base.writeEmbeddedToBlob(_hidl_blob, 0 + _hidl_offset);
        _hidl_blob.putString(56 + _hidl_offset, this.rat);
        _hidl_blob.putString(72 + _hidl_offset, this.lac);
    }
}
