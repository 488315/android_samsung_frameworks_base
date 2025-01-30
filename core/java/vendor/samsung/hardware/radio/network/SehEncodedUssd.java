package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehEncodedUssd implements Parcelable {
  public static final Parcelable.Creator<SehEncodedUssd> CREATOR =
      new Parcelable.Creator<
          SehEncodedUssd>() { // from class: vendor.samsung.hardware.radio.network.SehEncodedUssd.1
        @Override // android.os.Parcelable.Creator
        public SehEncodedUssd createFromParcel(Parcel _aidl_source) {
          SehEncodedUssd _aidl_out = new SehEncodedUssd();
          _aidl_out.readFromParcel(_aidl_source);
          return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public SehEncodedUssd[] newArray(int _aidl_size) {
          return new SehEncodedUssd[_aidl_size];
        }
      };
  public byte[] encodedUssd;
  public int ussdLength = 0;
  public int dcsCode = 0;

  @Override // android.os.Parcelable
  public final int getStability() {
    return 1;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
    int _aidl_start_pos = _aidl_parcel.dataPosition();
    _aidl_parcel.writeInt(0);
    _aidl_parcel.writeByteArray(this.encodedUssd);
    _aidl_parcel.writeInt(this.ussdLength);
    _aidl_parcel.writeInt(this.dcsCode);
    int _aidl_end_pos = _aidl_parcel.dataPosition();
    _aidl_parcel.setDataPosition(_aidl_start_pos);
    _aidl_parcel.writeInt(_aidl_end_pos - _aidl_start_pos);
    _aidl_parcel.setDataPosition(_aidl_end_pos);
  }

  public final void readFromParcel(Parcel _aidl_parcel) {
    int _aidl_start_pos = _aidl_parcel.dataPosition();
    int _aidl_parcelable_size = _aidl_parcel.readInt();
    try {
      if (_aidl_parcelable_size < 4) {
        throw new BadParcelableException("Parcelable too small");
      }
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.encodedUssd = _aidl_parcel.createByteArray();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.ussdLength = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
      } else {
        this.dcsCode = _aidl_parcel.readInt();
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
      }
    } catch (Throwable th) {
      if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
        throw new BadParcelableException("Overflow in the size of parcelable");
      }
      _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
      throw th;
    }
  }

  public String toString() {
    StringJoiner _aidl_sj = new StringJoiner(", ", "{", "}");
    _aidl_sj.add("encodedUssd: " + Arrays.toString(this.encodedUssd));
    _aidl_sj.add("ussdLength: " + this.ussdLength);
    _aidl_sj.add("dcsCode: " + this.dcsCode);
    return "vendor.samsung.hardware.radio.network.SehEncodedUssd" + _aidl_sj.toString();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }
}
