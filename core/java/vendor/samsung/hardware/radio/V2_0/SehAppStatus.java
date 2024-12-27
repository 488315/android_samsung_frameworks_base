package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.AppStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

public final class SehAppStatus {
    public AppStatus base = new AppStatus();
    public int pin1NumRetries = 0;
    public int puk1NumRetries = 0;
    public int pin2NumRetries = 0;
    public int puk2NumRetries = 0;
    public int persoUnblockRetries = 0;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehAppStatus.class) {
            return false;
        }
        SehAppStatus other = (SehAppStatus) otherObject;
        if (HidlSupport.deepEquals(this.base, other.base)
                && this.pin1NumRetries == other.pin1NumRetries
                && this.puk1NumRetries == other.puk1NumRetries
                && this.pin2NumRetries == other.pin2NumRetries
                && this.puk2NumRetries == other.puk2NumRetries
                && this.persoUnblockRetries == other.persoUnblockRetries) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.base)),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pin1NumRetries))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.puk1NumRetries))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pin2NumRetries))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.puk2NumRetries))),
                Integer.valueOf(
                        HidlSupport.deepHashCode(Integer.valueOf(this.persoUnblockRetries))));
    }

    public final String toString() {
        return "{.base = "
                + this.base
                + ", .pin1NumRetries = "
                + this.pin1NumRetries
                + ", .puk1NumRetries = "
                + this.puk1NumRetries
                + ", .pin2NumRetries = "
                + this.pin2NumRetries
                + ", .puk2NumRetries = "
                + this.puk2NumRetries
                + ", .persoUnblockRetries = "
                + this.persoUnblockRetries
                + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(88L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehAppStatus> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehAppStatus> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob =
                parcel.readEmbeddedBuffer(_hidl_vec_size * 88, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehAppStatus _hidl_vec_element = new SehAppStatus();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 88);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(
            HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.base.readEmbeddedFromParcel(parcel, _hidl_blob, 0 + _hidl_offset);
        this.pin1NumRetries = _hidl_blob.getInt32(64 + _hidl_offset);
        this.puk1NumRetries = _hidl_blob.getInt32(68 + _hidl_offset);
        this.pin2NumRetries = _hidl_blob.getInt32(72 + _hidl_offset);
        this.puk2NumRetries = _hidl_blob.getInt32(76 + _hidl_offset);
        this.persoUnblockRetries = _hidl_blob.getInt32(80 + _hidl_offset);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(88);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(
            HwParcel parcel, ArrayList<SehAppStatus> _hidl_vec) {
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
        _hidl_blob.putInt32(64 + _hidl_offset, this.pin1NumRetries);
        _hidl_blob.putInt32(68 + _hidl_offset, this.puk1NumRetries);
        _hidl_blob.putInt32(72 + _hidl_offset, this.pin2NumRetries);
        _hidl_blob.putInt32(76 + _hidl_offset, this.puk2NumRetries);
        _hidl_blob.putInt32(80 + _hidl_offset, this.persoUnblockRetries);
    }
}
