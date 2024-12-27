package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.CardState;
import android.hardware.radio.V1_0.PinState;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

public final class SehCardStatus {
    public int cardState = 0;
    public int universalPinState = 0;
    public int gsmUmtsSubscriptionAppIndex = 0;
    public int cdmaSubscriptionAppIndex = 0;
    public int imsSubscriptionAppIndex = 0;
    public ArrayList<SehAppStatus> applications = new ArrayList<>();
    public int physicalSlotId = 0;
    public String atr = new String();
    public String iccid = new String();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehCardStatus.class) {
            return false;
        }
        SehCardStatus other = (SehCardStatus) otherObject;
        if (this.cardState == other.cardState
                && this.universalPinState == other.universalPinState
                && this.gsmUmtsSubscriptionAppIndex == other.gsmUmtsSubscriptionAppIndex
                && this.cdmaSubscriptionAppIndex == other.cdmaSubscriptionAppIndex
                && this.imsSubscriptionAppIndex == other.imsSubscriptionAppIndex
                && HidlSupport.deepEquals(this.applications, other.applications)
                && this.physicalSlotId == other.physicalSlotId
                && HidlSupport.deepEquals(this.atr, other.atr)
                && HidlSupport.deepEquals(this.iccid, other.iccid)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cardState))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.universalPinState))),
                Integer.valueOf(
                        HidlSupport.deepHashCode(
                                Integer.valueOf(this.gsmUmtsSubscriptionAppIndex))),
                Integer.valueOf(
                        HidlSupport.deepHashCode(Integer.valueOf(this.cdmaSubscriptionAppIndex))),
                Integer.valueOf(
                        HidlSupport.deepHashCode(Integer.valueOf(this.imsSubscriptionAppIndex))),
                Integer.valueOf(HidlSupport.deepHashCode(this.applications)),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.physicalSlotId))),
                Integer.valueOf(HidlSupport.deepHashCode(this.atr)),
                Integer.valueOf(HidlSupport.deepHashCode(this.iccid)));
    }

    public final String toString() {
        return "{.cardState = "
                + CardState.toString(this.cardState)
                + ", .universalPinState = "
                + PinState.toString(this.universalPinState)
                + ", .gsmUmtsSubscriptionAppIndex = "
                + this.gsmUmtsSubscriptionAppIndex
                + ", .cdmaSubscriptionAppIndex = "
                + this.cdmaSubscriptionAppIndex
                + ", .imsSubscriptionAppIndex = "
                + this.imsSubscriptionAppIndex
                + ", .applications = "
                + this.applications
                + ", .physicalSlotId = "
                + this.physicalSlotId
                + ", .atr = "
                + this.atr
                + ", .iccid = "
                + this.iccid
                + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(80L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehCardStatus> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehCardStatus> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob =
                parcel.readEmbeddedBuffer(_hidl_vec_size * 80, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehCardStatus _hidl_vec_element = new SehCardStatus();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 80);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(
            HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.cardState = _hidl_blob.getInt32(_hidl_offset + 0);
        this.universalPinState = _hidl_blob.getInt32(_hidl_offset + 4);
        this.gsmUmtsSubscriptionAppIndex = _hidl_blob.getInt32(_hidl_offset + 8);
        this.cdmaSubscriptionAppIndex = _hidl_blob.getInt32(_hidl_offset + 12);
        this.imsSubscriptionAppIndex = _hidl_blob.getInt32(_hidl_offset + 16);
        int _hidl_vec_size = _hidl_blob.getInt32(_hidl_offset + 24 + 8);
        HwBlob childBlob =
                parcel.readEmbeddedBuffer(
                        _hidl_vec_size * 88, _hidl_blob.handle(), _hidl_offset + 24 + 0, true);
        this.applications.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehAppStatus _hidl_vec_element = new SehAppStatus();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 88);
            this.applications.add(_hidl_vec_element);
        }
        this.physicalSlotId = _hidl_blob.getInt32(_hidl_offset + 40);
        this.atr = _hidl_blob.getString(_hidl_offset + 48);
        parcel.readEmbeddedBuffer(
                this.atr.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 48 + 0, false);
        this.iccid = _hidl_blob.getString(_hidl_offset + 64);
        parcel.readEmbeddedBuffer(
                this.iccid.getBytes().length + 1,
                _hidl_blob.handle(),
                _hidl_offset + 64 + 0,
                false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(80);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(
            HwParcel parcel, ArrayList<SehCardStatus> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 80);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 80);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(_hidl_offset + 0, this.cardState);
        _hidl_blob.putInt32(4 + _hidl_offset, this.universalPinState);
        _hidl_blob.putInt32(_hidl_offset + 8, this.gsmUmtsSubscriptionAppIndex);
        _hidl_blob.putInt32(_hidl_offset + 12, this.cdmaSubscriptionAppIndex);
        _hidl_blob.putInt32(16 + _hidl_offset, this.imsSubscriptionAppIndex);
        int _hidl_vec_size = this.applications.size();
        _hidl_blob.putInt32(_hidl_offset + 24 + 8, _hidl_vec_size);
        _hidl_blob.putBool(_hidl_offset + 24 + 12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 88);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            this.applications.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 88);
        }
        _hidl_blob.putBlob(24 + _hidl_offset + 0, childBlob);
        _hidl_blob.putInt32(40 + _hidl_offset, this.physicalSlotId);
        _hidl_blob.putString(48 + _hidl_offset, this.atr);
        _hidl_blob.putString(64 + _hidl_offset, this.iccid);
    }
}
