package android.apex;

import android.p009os.BadParcelableException;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes.dex */
public class ApexSessionParams implements Parcelable {
  public static final Parcelable.Creator<ApexSessionParams> CREATOR =
      new Parcelable.Creator<ApexSessionParams>() { // from class: android.apex.ApexSessionParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApexSessionParams createFromParcel(Parcel _aidl_source) {
          ApexSessionParams _aidl_out = new ApexSessionParams();
          _aidl_out.readFromParcel(_aidl_source);
          return _aidl_out;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApexSessionParams[] newArray(int _aidl_size) {
          return new ApexSessionParams[_aidl_size];
        }
      };
  public int sessionId = 0;
  public int[] childSessionIds = new int[0];
  public boolean hasRollbackEnabled = false;
  public boolean isRollback = false;
  public int rollbackId = 0;

  @Override // android.p009os.Parcelable
  public final void writeToParcel(Parcel parcel, int i) {
    int dataPosition = parcel.dataPosition();
    parcel.writeInt(0);
    parcel.writeInt(this.sessionId);
    parcel.writeIntArray(this.childSessionIds);
    parcel.writeInt(this.hasRollbackEnabled ? 1 : 0);
    parcel.writeInt(this.isRollback ? 1 : 0);
    parcel.writeInt(this.rollbackId);
    int dataPosition2 = parcel.dataPosition();
    parcel.setDataPosition(dataPosition);
    parcel.writeInt(dataPosition2 - dataPosition);
    parcel.setDataPosition(dataPosition2);
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
      this.sessionId = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.childSessionIds = _aidl_parcel.createIntArray();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      boolean z = true;
      this.hasRollbackEnabled = _aidl_parcel.readInt() != 0;
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      if (_aidl_parcel.readInt() == 0) {
        z = false;
      }
      this.isRollback = z;
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
      } else {
        this.rollbackId = _aidl_parcel.readInt();
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

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }
}
