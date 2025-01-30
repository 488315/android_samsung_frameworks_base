package vendor.samsung.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehCdmaSmsMessage implements Parcelable {
  public static final Parcelable.Creator<SehCdmaSmsMessage> CREATOR =
      new Parcelable.Creator<
          SehCdmaSmsMessage>() { // from class:
                                 // vendor.samsung.hardware.radio.messaging.SehCdmaSmsMessage.1
        @Override // android.os.Parcelable.Creator
        public SehCdmaSmsMessage createFromParcel(Parcel _aidl_source) {
          SehCdmaSmsMessage _aidl_out = new SehCdmaSmsMessage();
          _aidl_out.readFromParcel(_aidl_source);
          return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public SehCdmaSmsMessage[] newArray(int _aidl_size) {
          return new SehCdmaSmsMessage[_aidl_size];
        }
      };
  public SehCdmaSmsAddress address;
  public byte[] bearerData;
  public SehCdmaSmsSubaddress subAddress;
  public int teleserviceId = 0;
  public boolean isServicePresent = false;
  public int serviceCategory = 0;

  @Override // android.os.Parcelable
  public final int getStability() {
    return 1;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
    int _aidl_start_pos = _aidl_parcel.dataPosition();
    _aidl_parcel.writeInt(0);
    _aidl_parcel.writeInt(this.teleserviceId);
    _aidl_parcel.writeBoolean(this.isServicePresent);
    _aidl_parcel.writeInt(this.serviceCategory);
    _aidl_parcel.writeTypedObject(this.address, _aidl_flag);
    _aidl_parcel.writeTypedObject(this.subAddress, _aidl_flag);
    _aidl_parcel.writeByteArray(this.bearerData);
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
      this.teleserviceId = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.isServicePresent = _aidl_parcel.readBoolean();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.serviceCategory = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.address = (SehCdmaSmsAddress) _aidl_parcel.readTypedObject(SehCdmaSmsAddress.CREATOR);
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.subAddress =
          (SehCdmaSmsSubaddress) _aidl_parcel.readTypedObject(SehCdmaSmsSubaddress.CREATOR);
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
      } else {
        this.bearerData = _aidl_parcel.createByteArray();
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
    _aidl_sj.add("teleserviceId: " + this.teleserviceId);
    _aidl_sj.add("isServicePresent: " + this.isServicePresent);
    _aidl_sj.add("serviceCategory: " + this.serviceCategory);
    _aidl_sj.add("address: " + Objects.toString(this.address));
    _aidl_sj.add("subAddress: " + Objects.toString(this.subAddress));
    _aidl_sj.add("bearerData: " + Arrays.toString(this.bearerData));
    return "vendor.samsung.hardware.radio.messaging.SehCdmaSmsMessage" + _aidl_sj.toString();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    int _mask = 0 | describeContents(this.address);
    return _mask | describeContents(this.subAddress);
  }

  private int describeContents(Object _v) {
    if (_v == null || !(_v instanceof Parcelable)) {
      return 0;
    }
    return ((Parcelable) _v).describeContents();
  }
}
