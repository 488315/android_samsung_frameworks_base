package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehExtendedRegStateResult implements Parcelable {
  public static final Parcelable.Creator<SehExtendedRegStateResult> CREATOR =
      new Parcelable.Creator<SehExtendedRegStateResult>() { // from class:
        // vendor.samsung.hardware.radio.network.SehExtendedRegStateResult.1
        @Override // android.os.Parcelable.Creator
        public SehExtendedRegStateResult createFromParcel(Parcel _aidl_source) {
          SehExtendedRegStateResult _aidl_out = new SehExtendedRegStateResult();
          _aidl_out.readFromParcel(_aidl_source);
          return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public SehExtendedRegStateResult[] newArray(int _aidl_size) {
          return new SehExtendedRegStateResult[_aidl_size];
        }
      };
  public int unprocessedDataRegState;
  public int unprocessedVoiceRegState;
  public boolean isValid = false;
  public int snapshotStatus = 0;
  public int unprocessedDataRat = 0;
  public int mobileOptionalRat = 0;
  public int imsEmergencyCallBarring = 0;
  public boolean isPsOnlyReg = false;

  @Override // android.os.Parcelable
  public final int getStability() {
    return 1;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
    int _aidl_start_pos = _aidl_parcel.dataPosition();
    _aidl_parcel.writeInt(0);
    _aidl_parcel.writeBoolean(this.isValid);
    _aidl_parcel.writeInt(this.snapshotStatus);
    _aidl_parcel.writeInt(this.unprocessedDataRegState);
    _aidl_parcel.writeInt(this.unprocessedDataRat);
    _aidl_parcel.writeInt(this.mobileOptionalRat);
    _aidl_parcel.writeInt(this.imsEmergencyCallBarring);
    _aidl_parcel.writeInt(this.unprocessedVoiceRegState);
    _aidl_parcel.writeBoolean(this.isPsOnlyReg);
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
      this.isValid = _aidl_parcel.readBoolean();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.snapshotStatus = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.unprocessedDataRegState = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.unprocessedDataRat = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.mobileOptionalRat = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.imsEmergencyCallBarring = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.unprocessedVoiceRegState = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
      } else {
        this.isPsOnlyReg = _aidl_parcel.readBoolean();
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
    _aidl_sj.add("isValid: " + this.isValid);
    _aidl_sj.add("snapshotStatus: " + this.snapshotStatus);
    _aidl_sj.add(
        "unprocessedDataRegState: " + SehRegState$$.toString(this.unprocessedDataRegState));
    _aidl_sj.add("unprocessedDataRat: " + this.unprocessedDataRat);
    _aidl_sj.add("mobileOptionalRat: " + this.mobileOptionalRat);
    _aidl_sj.add("imsEmergencyCallBarring: " + this.imsEmergencyCallBarring);
    _aidl_sj.add(
        "unprocessedVoiceRegState: " + SehRegState$$.toString(this.unprocessedVoiceRegState));
    _aidl_sj.add("isPsOnlyReg: " + this.isPsOnlyReg);
    return "vendor.samsung.hardware.radio.network.SehExtendedRegStateResult" + _aidl_sj.toString();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }
}
