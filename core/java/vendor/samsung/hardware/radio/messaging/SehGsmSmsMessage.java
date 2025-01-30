package vendor.samsung.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehGsmSmsMessage implements Parcelable {
  public static final Parcelable.Creator<SehGsmSmsMessage> CREATOR =
      new Parcelable.Creator<
          SehGsmSmsMessage>() { // from class:
                                // vendor.samsung.hardware.radio.messaging.SehGsmSmsMessage.1
        @Override // android.os.Parcelable.Creator
        public SehGsmSmsMessage createFromParcel(Parcel _aidl_source) {
          SehGsmSmsMessage _aidl_out = new SehGsmSmsMessage();
          _aidl_out.readFromParcel(_aidl_source);
          return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public SehGsmSmsMessage[] newArray(int _aidl_size) {
          return new SehGsmSmsMessage[_aidl_size];
        }
      };
  public String pdu;
  public String smscPdu;

  @Override // android.os.Parcelable
  public final int getStability() {
    return 1;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
    int _aidl_start_pos = _aidl_parcel.dataPosition();
    _aidl_parcel.writeInt(0);
    _aidl_parcel.writeString(this.smscPdu);
    _aidl_parcel.writeString(this.pdu);
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
      this.smscPdu = _aidl_parcel.readString();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
      } else {
        this.pdu = _aidl_parcel.readString();
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
    _aidl_sj.add("smscPdu: " + Objects.toString(this.smscPdu));
    _aidl_sj.add("pdu: " + Objects.toString(this.pdu));
    return "vendor.samsung.hardware.radio.messaging.SehGsmSmsMessage" + _aidl_sj.toString();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }
}
