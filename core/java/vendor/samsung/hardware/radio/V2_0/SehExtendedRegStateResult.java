package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.RegState;
import android.p009os.HidlSupport;
import android.p009os.HwBlob;
import android.p009os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehExtendedRegStateResult {
  public boolean isValid = false;
  public int snapshotStatus = 0;
  public int unprocessedDataRegState = 0;
  public int unprocessedDataRat = 0;
  public int mobileOptionalRat = 0;
  public int imsEmergencyCallBarring = 0;
  public int unprocessedVoiceRegState = 0;
  public boolean isPsOnlyReg = false;

  public final boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (otherObject == null || otherObject.getClass() != SehExtendedRegStateResult.class) {
      return false;
    }
    SehExtendedRegStateResult other = (SehExtendedRegStateResult) otherObject;
    if (this.isValid == other.isValid
        && this.snapshotStatus == other.snapshotStatus
        && this.unprocessedDataRegState == other.unprocessedDataRegState
        && this.unprocessedDataRat == other.unprocessedDataRat
        && this.mobileOptionalRat == other.mobileOptionalRat
        && this.imsEmergencyCallBarring == other.imsEmergencyCallBarring
        && this.unprocessedVoiceRegState == other.unprocessedVoiceRegState
        && this.isPsOnlyReg == other.isPsOnlyReg) {
      return true;
    }
    return false;
  }

  public final int hashCode() {
    return Objects.hash(
        Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isValid))),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.snapshotStatus))),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.unprocessedDataRegState))),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.unprocessedDataRat))),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mobileOptionalRat))),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.imsEmergencyCallBarring))),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.unprocessedVoiceRegState))),
        Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isPsOnlyReg))));
  }

  public final String toString() {
    return "{.isValid = "
        + this.isValid
        + ", .snapshotStatus = "
        + this.snapshotStatus
        + ", .unprocessedDataRegState = "
        + RegState.toString(this.unprocessedDataRegState)
        + ", .unprocessedDataRat = "
        + this.unprocessedDataRat
        + ", .mobileOptionalRat = "
        + this.mobileOptionalRat
        + ", .imsEmergencyCallBarring = "
        + this.imsEmergencyCallBarring
        + ", .unprocessedVoiceRegState = "
        + RegState.toString(this.unprocessedVoiceRegState)
        + ", .isPsOnlyReg = "
        + this.isPsOnlyReg
        + "}";
  }

  public final void readFromParcel(HwParcel parcel) {
    HwBlob blob = parcel.readBuffer(32L);
    readEmbeddedFromParcel(parcel, blob, 0L);
  }

  public static final ArrayList<SehExtendedRegStateResult> readVectorFromParcel(HwParcel parcel) {
    ArrayList<SehExtendedRegStateResult> _hidl_vec = new ArrayList<>();
    HwBlob _hidl_blob = parcel.readBuffer(16L);
    int _hidl_vec_size = _hidl_blob.getInt32(8L);
    HwBlob childBlob =
        parcel.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
    _hidl_vec.clear();
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      SehExtendedRegStateResult _hidl_vec_element = new SehExtendedRegStateResult();
      _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 32);
      _hidl_vec.add(_hidl_vec_element);
    }
    return _hidl_vec;
  }

  public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
    this.isValid = _hidl_blob.getBool(0 + _hidl_offset);
    this.snapshotStatus = _hidl_blob.getInt32(4 + _hidl_offset);
    this.unprocessedDataRegState = _hidl_blob.getInt32(8 + _hidl_offset);
    this.unprocessedDataRat = _hidl_blob.getInt32(12 + _hidl_offset);
    this.mobileOptionalRat = _hidl_blob.getInt32(16 + _hidl_offset);
    this.imsEmergencyCallBarring = _hidl_blob.getInt32(20 + _hidl_offset);
    this.unprocessedVoiceRegState = _hidl_blob.getInt32(24 + _hidl_offset);
    this.isPsOnlyReg = _hidl_blob.getBool(28 + _hidl_offset);
  }

  public final void writeToParcel(HwParcel parcel) {
    HwBlob _hidl_blob = new HwBlob(32);
    writeEmbeddedToBlob(_hidl_blob, 0L);
    parcel.writeBuffer(_hidl_blob);
  }

  public static final void writeVectorToParcel(
      HwParcel parcel, ArrayList<SehExtendedRegStateResult> _hidl_vec) {
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
    _hidl_blob.putBool(0 + _hidl_offset, this.isValid);
    _hidl_blob.putInt32(4 + _hidl_offset, this.snapshotStatus);
    _hidl_blob.putInt32(8 + _hidl_offset, this.unprocessedDataRegState);
    _hidl_blob.putInt32(12 + _hidl_offset, this.unprocessedDataRat);
    _hidl_blob.putInt32(16 + _hidl_offset, this.mobileOptionalRat);
    _hidl_blob.putInt32(20 + _hidl_offset, this.imsEmergencyCallBarring);
    _hidl_blob.putInt32(24 + _hidl_offset, this.unprocessedVoiceRegState);
    _hidl_blob.putBool(28 + _hidl_offset, this.isPsOnlyReg);
  }
}
