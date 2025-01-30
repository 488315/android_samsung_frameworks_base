package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehEncodedUssd {
  public ArrayList<Byte> encodedUssd = new ArrayList<>();
  public int ussdLength = 0;
  public int dcsCode = 0;

  public final boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (otherObject == null || otherObject.getClass() != SehEncodedUssd.class) {
      return false;
    }
    SehEncodedUssd other = (SehEncodedUssd) otherObject;
    if (HidlSupport.deepEquals(this.encodedUssd, other.encodedUssd)
        && this.ussdLength == other.ussdLength
        && this.dcsCode == other.dcsCode) {
      return true;
    }
    return false;
  }

  public final int hashCode() {
    return Objects.hash(
        Integer.valueOf(HidlSupport.deepHashCode(this.encodedUssd)),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.ussdLength))),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.dcsCode))));
  }

  public final String toString() {
    return "{.encodedUssd = "
        + this.encodedUssd
        + ", .ussdLength = "
        + this.ussdLength
        + ", .dcsCode = "
        + this.dcsCode
        + "}";
  }

  public final void readFromParcel(HwParcel parcel) {
    HwBlob blob = parcel.readBuffer(24L);
    readEmbeddedFromParcel(parcel, blob, 0L);
  }

  public static final ArrayList<SehEncodedUssd> readVectorFromParcel(HwParcel parcel) {
    ArrayList<SehEncodedUssd> _hidl_vec = new ArrayList<>();
    HwBlob _hidl_blob = parcel.readBuffer(16L);
    int _hidl_vec_size = _hidl_blob.getInt32(8L);
    HwBlob childBlob =
        parcel.readEmbeddedBuffer(_hidl_vec_size * 24, _hidl_blob.handle(), 0L, true);
    _hidl_vec.clear();
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      SehEncodedUssd _hidl_vec_element = new SehEncodedUssd();
      _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 24);
      _hidl_vec.add(_hidl_vec_element);
    }
    return _hidl_vec;
  }

  public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
    int _hidl_vec_size = _hidl_blob.getInt32(_hidl_offset + 0 + 8);
    HwBlob childBlob =
        parcel.readEmbeddedBuffer(
            _hidl_vec_size * 1, _hidl_blob.handle(), _hidl_offset + 0 + 0, true);
    this.encodedUssd.clear();
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      byte _hidl_vec_element = childBlob.getInt8(_hidl_index_0 * 1);
      this.encodedUssd.add(Byte.valueOf(_hidl_vec_element));
    }
    this.ussdLength = _hidl_blob.getInt32(_hidl_offset + 16);
    this.dcsCode = _hidl_blob.getInt32(_hidl_offset + 20);
  }

  public final void writeToParcel(HwParcel parcel) {
    HwBlob _hidl_blob = new HwBlob(24);
    writeEmbeddedToBlob(_hidl_blob, 0L);
    parcel.writeBuffer(_hidl_blob);
  }

  public static final void writeVectorToParcel(
      HwParcel parcel, ArrayList<SehEncodedUssd> _hidl_vec) {
    HwBlob _hidl_blob = new HwBlob(16);
    int _hidl_vec_size = _hidl_vec.size();
    _hidl_blob.putInt32(8L, _hidl_vec_size);
    _hidl_blob.putBool(12L, false);
    HwBlob childBlob = new HwBlob(_hidl_vec_size * 24);
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 24);
    }
    _hidl_blob.putBlob(0L, childBlob);
    parcel.writeBuffer(_hidl_blob);
  }

  public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
    int _hidl_vec_size = this.encodedUssd.size();
    _hidl_blob.putInt32(_hidl_offset + 0 + 8, _hidl_vec_size);
    _hidl_blob.putBool(_hidl_offset + 0 + 12, false);
    HwBlob childBlob = new HwBlob(_hidl_vec_size * 1);
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      childBlob.putInt8(_hidl_index_0 * 1, this.encodedUssd.get(_hidl_index_0).byteValue());
    }
    _hidl_blob.putBlob(_hidl_offset + 0 + 0, childBlob);
    _hidl_blob.putInt32(16 + _hidl_offset, this.ussdLength);
    _hidl_blob.putInt32(20 + _hidl_offset, this.dcsCode);
  }
}
