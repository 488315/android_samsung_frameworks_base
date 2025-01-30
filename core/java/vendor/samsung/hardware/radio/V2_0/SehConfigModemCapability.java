package vendor.samsung.hardware.radio.V2_0;

import android.p009os.HidlSupport;
import android.p009os.HwBlob;
import android.p009os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehConfigModemCapability {
  public int supportCltcp = 0;

  public final boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (otherObject == null || otherObject.getClass() != SehConfigModemCapability.class) {
      return false;
    }
    SehConfigModemCapability other = (SehConfigModemCapability) otherObject;
    if (this.supportCltcp == other.supportCltcp) {
      return true;
    }
    return false;
  }

  public final int hashCode() {
    return Objects.hash(
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.supportCltcp))));
  }

  public final String toString() {
    return "{.supportCltcp = " + this.supportCltcp + "}";
  }

  public final void readFromParcel(HwParcel parcel) {
    HwBlob blob = parcel.readBuffer(4L);
    readEmbeddedFromParcel(parcel, blob, 0L);
  }

  public static final ArrayList<SehConfigModemCapability> readVectorFromParcel(HwParcel parcel) {
    ArrayList<SehConfigModemCapability> _hidl_vec = new ArrayList<>();
    HwBlob _hidl_blob = parcel.readBuffer(16L);
    int _hidl_vec_size = _hidl_blob.getInt32(8L);
    HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 4, _hidl_blob.handle(), 0L, true);
    _hidl_vec.clear();
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      SehConfigModemCapability _hidl_vec_element = new SehConfigModemCapability();
      _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 4);
      _hidl_vec.add(_hidl_vec_element);
    }
    return _hidl_vec;
  }

  public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
    this.supportCltcp = _hidl_blob.getInt32(0 + _hidl_offset);
  }

  public final void writeToParcel(HwParcel parcel) {
    HwBlob _hidl_blob = new HwBlob(4);
    writeEmbeddedToBlob(_hidl_blob, 0L);
    parcel.writeBuffer(_hidl_blob);
  }

  public static final void writeVectorToParcel(
      HwParcel parcel, ArrayList<SehConfigModemCapability> _hidl_vec) {
    HwBlob _hidl_blob = new HwBlob(16);
    int _hidl_vec_size = _hidl_vec.size();
    _hidl_blob.putInt32(8L, _hidl_vec_size);
    _hidl_blob.putBool(12L, false);
    HwBlob childBlob = new HwBlob(_hidl_vec_size * 4);
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 4);
    }
    _hidl_blob.putBlob(0L, childBlob);
    parcel.writeBuffer(_hidl_blob);
  }

  public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
    _hidl_blob.putInt32(0 + _hidl_offset, this.supportCltcp);
  }
}
