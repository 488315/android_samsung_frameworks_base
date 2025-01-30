package vendor.samsung.hardware.radio.V2_0;

import android.p009os.HidlSupport;
import android.p009os.HwBlob;
import android.p009os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehAdnRecord {
  public ArrayList<Byte> name = new ArrayList<>();
  public int nameDcs = 0;
  public int nameLength = 0;
  public String number = new String();
  public ArrayList<Byte> gsm8bitEmail = new ArrayList<>();
  public int gsm8bitEmailLength = 0;
  public String anr = new String();
  public String anrA = new String();
  public String anrB = new String();
  public String anrC = new String();
  public ArrayList<Byte> sne = new ArrayList<>();
  public int sneLength = 0;
  public int sneDcs = 0;

  public final boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (otherObject == null || otherObject.getClass() != SehAdnRecord.class) {
      return false;
    }
    SehAdnRecord other = (SehAdnRecord) otherObject;
    if (HidlSupport.deepEquals(this.name, other.name)
        && this.nameDcs == other.nameDcs
        && this.nameLength == other.nameLength
        && HidlSupport.deepEquals(this.number, other.number)
        && HidlSupport.deepEquals(this.gsm8bitEmail, other.gsm8bitEmail)
        && this.gsm8bitEmailLength == other.gsm8bitEmailLength
        && HidlSupport.deepEquals(this.anr, other.anr)
        && HidlSupport.deepEquals(this.anrA, other.anrA)
        && HidlSupport.deepEquals(this.anrB, other.anrB)
        && HidlSupport.deepEquals(this.anrC, other.anrC)
        && HidlSupport.deepEquals(this.sne, other.sne)
        && this.sneLength == other.sneLength
        && this.sneDcs == other.sneDcs) {
      return true;
    }
    return false;
  }

  public final int hashCode() {
    return Objects.hash(
        Integer.valueOf(HidlSupport.deepHashCode(this.name)),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.nameDcs))),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.nameLength))),
        Integer.valueOf(HidlSupport.deepHashCode(this.number)),
        Integer.valueOf(HidlSupport.deepHashCode(this.gsm8bitEmail)),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.gsm8bitEmailLength))),
        Integer.valueOf(HidlSupport.deepHashCode(this.anr)),
        Integer.valueOf(HidlSupport.deepHashCode(this.anrA)),
        Integer.valueOf(HidlSupport.deepHashCode(this.anrB)),
        Integer.valueOf(HidlSupport.deepHashCode(this.anrC)),
        Integer.valueOf(HidlSupport.deepHashCode(this.sne)),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sneLength))),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sneDcs))));
  }

  public final String toString() {
    return "{.name = "
        + this.name
        + ", .nameDcs = "
        + this.nameDcs
        + ", .nameLength = "
        + this.nameLength
        + ", .number = "
        + this.number
        + ", .gsm8bitEmail = "
        + this.gsm8bitEmail
        + ", .gsm8bitEmailLength = "
        + this.gsm8bitEmailLength
        + ", .anr = "
        + this.anr
        + ", .anrA = "
        + this.anrA
        + ", .anrB = "
        + this.anrB
        + ", .anrC = "
        + this.anrC
        + ", .sne = "
        + this.sne
        + ", .sneLength = "
        + this.sneLength
        + ", .sneDcs = "
        + this.sneDcs
        + "}";
  }

  public final void readFromParcel(HwParcel parcel) {
    HwBlob blob = parcel.readBuffer(152L);
    readEmbeddedFromParcel(parcel, blob, 0L);
  }

  public static final ArrayList<SehAdnRecord> readVectorFromParcel(HwParcel parcel) {
    ArrayList<SehAdnRecord> _hidl_vec = new ArrayList<>();
    HwBlob _hidl_blob = parcel.readBuffer(16L);
    int _hidl_vec_size = _hidl_blob.getInt32(8L);
    HwBlob childBlob =
        parcel.readEmbeddedBuffer(_hidl_vec_size * 152, _hidl_blob.handle(), 0L, true);
    _hidl_vec.clear();
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      SehAdnRecord _hidl_vec_element = new SehAdnRecord();
      _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 152);
      _hidl_vec.add(_hidl_vec_element);
    }
    return _hidl_vec;
  }

  public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
    int _hidl_vec_size = _hidl_blob.getInt32(_hidl_offset + 0 + 8);
    HwBlob childBlob =
        parcel.readEmbeddedBuffer(
            _hidl_vec_size * 1, _hidl_blob.handle(), _hidl_offset + 0 + 0, true);
    this.name.clear();
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      byte _hidl_vec_element = childBlob.getInt8(_hidl_index_0 * 1);
      this.name.add(Byte.valueOf(_hidl_vec_element));
    }
    this.nameDcs = _hidl_blob.getInt32(_hidl_offset + 16);
    this.nameLength = _hidl_blob.getInt32(_hidl_offset + 20);
    this.number = _hidl_blob.getString(_hidl_offset + 24);
    parcel.readEmbeddedBuffer(
        r8.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 24 + 0, false);
    int _hidl_vec_size2 = _hidl_blob.getInt32(_hidl_offset + 40 + 8);
    HwBlob childBlob2 =
        parcel.readEmbeddedBuffer(
            _hidl_vec_size2 * 1, _hidl_blob.handle(), _hidl_offset + 40 + 0, true);
    this.gsm8bitEmail.clear();
    for (int _hidl_index_02 = 0; _hidl_index_02 < _hidl_vec_size2; _hidl_index_02++) {
      byte _hidl_vec_element2 = childBlob2.getInt8(_hidl_index_02 * 1);
      this.gsm8bitEmail.add(Byte.valueOf(_hidl_vec_element2));
    }
    this.gsm8bitEmailLength = _hidl_blob.getInt32(_hidl_offset + 56);
    this.anr = _hidl_blob.getString(_hidl_offset + 64);
    parcel.readEmbeddedBuffer(
        r8.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 64 + 0, false);
    this.anrA = _hidl_blob.getString(_hidl_offset + 80);
    parcel.readEmbeddedBuffer(
        r8.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 80 + 0, false);
    this.anrB = _hidl_blob.getString(_hidl_offset + 96);
    parcel.readEmbeddedBuffer(
        r8.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 96 + 0, false);
    this.anrC = _hidl_blob.getString(_hidl_offset + 112);
    parcel.readEmbeddedBuffer(
        r8.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 112 + 0, false);
    int _hidl_vec_size3 = _hidl_blob.getInt32(_hidl_offset + 128 + 8);
    HwBlob childBlob3 =
        parcel.readEmbeddedBuffer(
            _hidl_vec_size3 * 1, _hidl_blob.handle(), _hidl_offset + 128 + 0, true);
    this.sne.clear();
    for (int _hidl_index_03 = 0; _hidl_index_03 < _hidl_vec_size3; _hidl_index_03++) {
      byte _hidl_vec_element3 = childBlob3.getInt8(_hidl_index_03 * 1);
      this.sne.add(Byte.valueOf(_hidl_vec_element3));
    }
    this.sneLength = _hidl_blob.getInt32(_hidl_offset + 144);
    this.sneDcs = _hidl_blob.getInt32(_hidl_offset + 148);
  }

  public final void writeToParcel(HwParcel parcel) {
    HwBlob _hidl_blob = new HwBlob(152);
    writeEmbeddedToBlob(_hidl_blob, 0L);
    parcel.writeBuffer(_hidl_blob);
  }

  public static final void writeVectorToParcel(HwParcel parcel, ArrayList<SehAdnRecord> _hidl_vec) {
    HwBlob _hidl_blob = new HwBlob(16);
    int _hidl_vec_size = _hidl_vec.size();
    _hidl_blob.putInt32(8L, _hidl_vec_size);
    _hidl_blob.putBool(12L, false);
    HwBlob childBlob = new HwBlob(_hidl_vec_size * 152);
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 152);
    }
    _hidl_blob.putBlob(0L, childBlob);
    parcel.writeBuffer(_hidl_blob);
  }

  public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
    int _hidl_vec_size = this.name.size();
    _hidl_blob.putInt32(_hidl_offset + 0 + 8, _hidl_vec_size);
    _hidl_blob.putBool(_hidl_offset + 0 + 12, false);
    HwBlob childBlob = new HwBlob(_hidl_vec_size * 1);
    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
      childBlob.putInt8(_hidl_index_0 * 1, this.name.get(_hidl_index_0).byteValue());
    }
    _hidl_blob.putBlob(_hidl_offset + 0 + 0, childBlob);
    _hidl_blob.putInt32(_hidl_offset + 16, this.nameDcs);
    _hidl_blob.putInt32(_hidl_offset + 20, this.nameLength);
    _hidl_blob.putString(_hidl_offset + 24, this.number);
    int _hidl_vec_size2 = this.gsm8bitEmail.size();
    _hidl_blob.putInt32(_hidl_offset + 40 + 8, _hidl_vec_size2);
    _hidl_blob.putBool(_hidl_offset + 40 + 12, false);
    HwBlob childBlob2 = new HwBlob(_hidl_vec_size2 * 1);
    for (int _hidl_index_02 = 0; _hidl_index_02 < _hidl_vec_size2; _hidl_index_02++) {
      childBlob2.putInt8(_hidl_index_02 * 1, this.gsm8bitEmail.get(_hidl_index_02).byteValue());
    }
    _hidl_blob.putBlob(_hidl_offset + 40 + 0, childBlob2);
    _hidl_blob.putInt32(_hidl_offset + 56, this.gsm8bitEmailLength);
    _hidl_blob.putString(_hidl_offset + 64, this.anr);
    _hidl_blob.putString(_hidl_offset + 80, this.anrA);
    _hidl_blob.putString(_hidl_offset + 96, this.anrB);
    _hidl_blob.putString(_hidl_offset + 112, this.anrC);
    int _hidl_vec_size3 = this.sne.size();
    _hidl_blob.putInt32(_hidl_offset + 128 + 8, _hidl_vec_size3);
    _hidl_blob.putBool(_hidl_offset + 128 + 12, false);
    HwBlob childBlob3 = new HwBlob(_hidl_vec_size3 * 1);
    for (int _hidl_index_03 = 0; _hidl_index_03 < _hidl_vec_size3; _hidl_index_03++) {
      childBlob3.putInt8(_hidl_index_03 * 1, this.sne.get(_hidl_index_03).byteValue());
    }
    _hidl_blob.putBlob(_hidl_offset + 128 + 0, childBlob3);
    _hidl_blob.putInt32(_hidl_offset + 144, this.sneLength);
    _hidl_blob.putInt32(_hidl_offset + 148, this.sneDcs);
  }
}
