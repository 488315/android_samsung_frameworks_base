package android.p009os.incremental;

import android.p009os.BadParcelableException;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes3.dex */
public class StorageHealthCheckParams implements Parcelable {
  public static final Parcelable.Creator<StorageHealthCheckParams> CREATOR =
      new Parcelable.Creator<
          StorageHealthCheckParams>() { // from class:
                                        // android.os.incremental.StorageHealthCheckParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StorageHealthCheckParams createFromParcel(Parcel _aidl_source) {
          StorageHealthCheckParams _aidl_out = new StorageHealthCheckParams();
          _aidl_out.readFromParcel(_aidl_source);
          return _aidl_out;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StorageHealthCheckParams[] newArray(int _aidl_size) {
          return new StorageHealthCheckParams[_aidl_size];
        }
      };
  public int blockedTimeoutMs = 0;
  public int unhealthyTimeoutMs = 0;
  public int unhealthyMonitoringMs = 0;

  @Override // android.p009os.Parcelable
  public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
    int _aidl_start_pos = _aidl_parcel.dataPosition();
    _aidl_parcel.writeInt(0);
    _aidl_parcel.writeInt(this.blockedTimeoutMs);
    _aidl_parcel.writeInt(this.unhealthyTimeoutMs);
    _aidl_parcel.writeInt(this.unhealthyMonitoringMs);
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
      this.blockedTimeoutMs = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.unhealthyTimeoutMs = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
      } else {
        this.unhealthyMonitoringMs = _aidl_parcel.readInt();
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
