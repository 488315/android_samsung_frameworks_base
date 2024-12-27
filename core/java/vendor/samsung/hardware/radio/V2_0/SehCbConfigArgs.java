package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

public final class SehCbConfigArgs {
    public int enabled = 0;
    public int selectedId = 0;
    public int msgIdMaxCount = 0;
    public int msgIdCount = 0;
    public String msgIDs = new String();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehCbConfigArgs.class) {
            return false;
        }
        SehCbConfigArgs other = (SehCbConfigArgs) otherObject;
        if (this.enabled == other.enabled
                && this.selectedId == other.selectedId
                && this.msgIdMaxCount == other.msgIdMaxCount
                && this.msgIdCount == other.msgIdCount
                && HidlSupport.deepEquals(this.msgIDs, other.msgIDs)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.enabled))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.selectedId))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.msgIdMaxCount))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.msgIdCount))),
                Integer.valueOf(HidlSupport.deepHashCode(this.msgIDs)));
    }

    public final String toString() {
        return "{.enabled = "
                + this.enabled
                + ", .selectedId = "
                + this.selectedId
                + ", .msgIdMaxCount = "
                + this.msgIdMaxCount
                + ", .msgIdCount = "
                + this.msgIdCount
                + ", .msgIDs = "
                + this.msgIDs
                + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(32L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehCbConfigArgs> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehCbConfigArgs> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob =
                parcel.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehCbConfigArgs _hidl_vec_element = new SehCbConfigArgs();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 32);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(
            HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.enabled = _hidl_blob.getInt32(_hidl_offset + 0);
        this.selectedId = _hidl_blob.getInt32(_hidl_offset + 4);
        this.msgIdMaxCount = _hidl_blob.getInt32(_hidl_offset + 8);
        this.msgIdCount = _hidl_blob.getInt32(_hidl_offset + 12);
        this.msgIDs = _hidl_blob.getString(_hidl_offset + 16);
        parcel.readEmbeddedBuffer(
                this.msgIDs.getBytes().length + 1,
                _hidl_blob.handle(),
                _hidl_offset + 16 + 0,
                false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(32);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(
            HwParcel parcel, ArrayList<SehCbConfigArgs> _hidl_vec) {
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
        _hidl_blob.putInt32(0 + _hidl_offset, this.enabled);
        _hidl_blob.putInt32(4 + _hidl_offset, this.selectedId);
        _hidl_blob.putInt32(8 + _hidl_offset, this.msgIdMaxCount);
        _hidl_blob.putInt32(12 + _hidl_offset, this.msgIdCount);
        _hidl_blob.putString(16 + _hidl_offset, this.msgIDs);
    }
}
