package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.Call;
import android.hardware.radio.V1_2.AudioQuality;
import android.p009os.HidlSupport;
import android.p009os.HwBlob;
import android.p009os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehCall {
  public Call base = new Call();
  public int audioQuality = 0;
  public ArrayList<SehCallDetails> callDetails = new ArrayList<>();

  public final boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (otherObject == null || otherObject.getClass() != SehCall.class) {
      return false;
    }
    SehCall other = (SehCall) otherObject;
    if (HidlSupport.deepEquals(this.base, other.base)
        && this.audioQuality == other.audioQuality
        && HidlSupport.deepEquals(this.callDetails, other.callDetails)) {
      return true;
    }
    return false;
  }

  public final int hashCode() {
    return Objects.hash(
        Integer.valueOf(HidlSupport.deepHashCode(this.base)),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.audioQuality))),
        Integer.valueOf(HidlSupport.deepHashCode(this.callDetails)));
  }

  public final String toString() {
    return "{.base = "
        + this.base
        + ", .audioQuality = "
        + AudioQuality.toString(this.audioQuality)
        + ", .callDetails = "
        + this.callDetails
        + "}";
  }

  public final void readFromParcel(HwParcel parcel) {
    HwBlob blob = parcel.readBuffer(112L);
    readEmbeddedFromParcel(parcel, blob, 0L);
  }

  public static final ArrayList<SehCall> readVectorFromParcel(HwParcel parcel) {
    ArrayList<SehCall> _hidl_vec = new ArrayList<>();
    HwBlob _hidl_blob = parcel.readBuffer(16L);
    int _hidl_vec_size = _hidl_blob.getInt32(8L);
    HwBlob childBlob =
        parcel.readEmbeddedBuffer(_hidl_vec_size * 112, _hidl_blob.handle(), 0L, true);
    _hidl_vec.clear();
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      SehCall _hidl_vec_element = new SehCall();
      _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 112);
      _hidl_vec.add(_hidl_vec_element);
    }
    return _hidl_vec;
  }

  public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
    this.base.readEmbeddedFromParcel(parcel, _hidl_blob, _hidl_offset + 0);
    this.audioQuality = _hidl_blob.getInt32(_hidl_offset + 88);
    int _hidl_vec_size = _hidl_blob.getInt32(_hidl_offset + 96 + 8);
    HwBlob childBlob =
        parcel.readEmbeddedBuffer(
            _hidl_vec_size * 24, _hidl_blob.handle(), _hidl_offset + 96 + 0, true);
    this.callDetails.clear();
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      SehCallDetails _hidl_vec_element = new SehCallDetails();
      _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 24);
      this.callDetails.add(_hidl_vec_element);
    }
  }

  public final void writeToParcel(HwParcel parcel) {
    HwBlob _hidl_blob = new HwBlob(112);
    writeEmbeddedToBlob(_hidl_blob, 0L);
    parcel.writeBuffer(_hidl_blob);
  }

  public static final void writeVectorToParcel(HwParcel parcel, ArrayList<SehCall> _hidl_vec) {
    HwBlob _hidl_blob = new HwBlob(16);
    int _hidl_vec_size = _hidl_vec.size();
    _hidl_blob.putInt32(8L, _hidl_vec_size);
    _hidl_blob.putBool(12L, false);
    HwBlob childBlob = new HwBlob(_hidl_vec_size * 112);
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 112);
    }
    _hidl_blob.putBlob(0L, childBlob);
    parcel.writeBuffer(_hidl_blob);
  }

  public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
    this.base.writeEmbeddedToBlob(_hidl_blob, _hidl_offset + 0);
    _hidl_blob.putInt32(88 + _hidl_offset, this.audioQuality);
    int _hidl_vec_size = this.callDetails.size();
    _hidl_blob.putInt32(_hidl_offset + 96 + 8, _hidl_vec_size);
    _hidl_blob.putBool(_hidl_offset + 96 + 12, false);
    HwBlob childBlob = new HwBlob(_hidl_vec_size * 24);
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      this.callDetails.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 24);
    }
    _hidl_blob.putBlob(96 + _hidl_offset + 0, childBlob);
  }
}
