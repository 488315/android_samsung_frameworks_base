package android.media;

import android.p009os.BadParcelableException;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes2.dex */
public class AudioHwModule implements Parcelable {
  public static final Parcelable.Creator<AudioHwModule> CREATOR =
      new Parcelable.Creator<AudioHwModule>() { // from class: android.media.AudioHwModule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHwModule createFromParcel(Parcel _aidl_source) {
          AudioHwModule _aidl_out = new AudioHwModule();
          _aidl_out.readFromParcel(_aidl_source);
          return _aidl_out;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHwModule[] newArray(int _aidl_size) {
          return new AudioHwModule[_aidl_size];
        }
      };
  public int handle = 0;
  public String name;
  public android.media.audio.common.AudioPort[] ports;
  public AudioRoute[] routes;

  @Override // android.p009os.Parcelable
  public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
    int _aidl_start_pos = _aidl_parcel.dataPosition();
    _aidl_parcel.writeInt(0);
    _aidl_parcel.writeInt(this.handle);
    _aidl_parcel.writeString(this.name);
    _aidl_parcel.writeTypedArray(this.ports, _aidl_flag);
    _aidl_parcel.writeTypedArray(this.routes, _aidl_flag);
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
      this.handle = _aidl_parcel.readInt();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.name = _aidl_parcel.readString();
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.ports =
          (android.media.audio.common.AudioPort[])
              _aidl_parcel.createTypedArray(android.media.audio.common.AudioPort.CREATOR);
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
      } else {
        this.routes = (AudioRoute[]) _aidl_parcel.createTypedArray(AudioRoute.CREATOR);
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
    int _mask = 0 | describeContents(this.ports);
    return _mask | describeContents(this.routes);
  }

  private int describeContents(Object _v) {
    if (_v == null) {
      return 0;
    }
    if (_v instanceof Object[]) {
      int _mask = 0;
      for (Object o : (Object[]) _v) {
        _mask |= describeContents(o);
      }
      return _mask;
    }
    if (!(_v instanceof Parcelable)) {
      return 0;
    }
    return ((Parcelable) _v).describeContents();
  }
}
