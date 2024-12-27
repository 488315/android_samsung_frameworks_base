package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehSimLockInfo {
    public int numberOfLockTypes = 0;
    public int lockType = 0;
    public int lockKey = 0;
    public int numberOfRetry = 0;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehSimLockInfo.class) {
            return false;
        }
        SehSimLockInfo other = (SehSimLockInfo) otherObject;
        if (this.numberOfLockTypes == other.numberOfLockTypes
                && this.lockType == other.lockType
                && this.lockKey == other.lockKey
                && this.numberOfRetry == other.numberOfRetry) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.numberOfLockTypes))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.lockType))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.lockKey))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.numberOfRetry))));
    }

    public final String toString() {
        return "{.numberOfLockTypes = "
                + this.numberOfLockTypes
                + ", .lockType = "
                + this.lockType
                + ", .lockKey = "
                + this.lockKey
                + ", .numberOfRetry = "
                + this.numberOfRetry
                + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(16L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehSimLockInfo> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehSimLockInfo> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob =
                parcel.readEmbeddedBuffer(_hidl_vec_size * 16, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehSimLockInfo _hidl_vec_element = new SehSimLockInfo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 16);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(
            HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.numberOfLockTypes = _hidl_blob.getInt32(0 + _hidl_offset);
        this.lockType = _hidl_blob.getInt32(4 + _hidl_offset);
        this.lockKey = _hidl_blob.getInt32(8 + _hidl_offset);
        this.numberOfRetry = _hidl_blob.getInt32(12 + _hidl_offset);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(16);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(
            HwParcel parcel, ArrayList<SehSimLockInfo> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 16);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 16);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(0 + _hidl_offset, this.numberOfLockTypes);
        _hidl_blob.putInt32(4 + _hidl_offset, this.lockType);
        _hidl_blob.putInt32(8 + _hidl_offset, this.lockKey);
        _hidl_blob.putInt32(12 + _hidl_offset, this.numberOfRetry);
    }
}
