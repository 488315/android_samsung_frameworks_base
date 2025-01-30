package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class GnssData implements Parcelable {
  public static final Parcelable.Creator<GnssData> CREATOR =
      new Parcelable.Creator<GnssData>() { // from class: android.hardware.gnss.GnssData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssData createFromParcel(Parcel _aidl_source) {
          GnssData _aidl_out = new GnssData();
          _aidl_out.readFromParcel(_aidl_source);
          return _aidl_out;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssData[] newArray(int _aidl_size) {
          return new GnssData[_aidl_size];
        }
      };
  public GnssClock clock;
  public ElapsedRealtime elapsedRealtime;
  public GnssAgc[] gnssAgcs = new GnssAgc[0];
  public GnssMeasurement[] measurements;

  @Override // android.os.Parcelable
  public final int getStability() {
    return 1;
  }

  @Override // android.os.Parcelable
  public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
    int _aidl_start_pos = _aidl_parcel.dataPosition();
    _aidl_parcel.writeInt(0);
    _aidl_parcel.writeTypedArray(this.measurements, _aidl_flag);
    _aidl_parcel.writeTypedObject(this.clock, _aidl_flag);
    _aidl_parcel.writeTypedObject(this.elapsedRealtime, _aidl_flag);
    _aidl_parcel.writeTypedArray(this.gnssAgcs, _aidl_flag);
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
      this.measurements =
          (GnssMeasurement[]) _aidl_parcel.createTypedArray(GnssMeasurement.CREATOR);
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.clock = (GnssClock) _aidl_parcel.readTypedObject(GnssClock.CREATOR);
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        return;
      }
      this.elapsedRealtime =
          (ElapsedRealtime) _aidl_parcel.readTypedObject(ElapsedRealtime.CREATOR);
      if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
          throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
      } else {
        this.gnssAgcs = (GnssAgc[]) _aidl_parcel.createTypedArray(GnssAgc.CREATOR);
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

  @Override // android.os.Parcelable
  public int describeContents() {
    int _mask = 0 | describeContents(this.measurements);
    return _mask
        | describeContents(this.clock)
        | describeContents(this.elapsedRealtime)
        | describeContents(this.gnssAgcs);
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

  public static class GnssAgc implements Parcelable {
    public static final Parcelable.Creator<GnssAgc> CREATOR =
        new Parcelable.Creator<GnssAgc>() { // from class: android.hardware.gnss.GnssData.GnssAgc.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public GnssAgc createFromParcel(Parcel _aidl_source) {
            GnssAgc _aidl_out = new GnssAgc();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public GnssAgc[] newArray(int _aidl_size) {
            return new GnssAgc[_aidl_size];
          }
        };
    public double agcLevelDb = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public int constellation = 0;
    public long carrierFrequencyHz = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
      return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
      int _aidl_start_pos = _aidl_parcel.dataPosition();
      _aidl_parcel.writeInt(0);
      _aidl_parcel.writeDouble(this.agcLevelDb);
      _aidl_parcel.writeInt(this.constellation);
      _aidl_parcel.writeLong(this.carrierFrequencyHz);
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
        this.agcLevelDb = _aidl_parcel.readDouble();
        if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
          if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
            throw new BadParcelableException("Overflow in the size of parcelable");
          }
          _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
          return;
        }
        this.constellation = _aidl_parcel.readInt();
        if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
          if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
            throw new BadParcelableException("Overflow in the size of parcelable");
          }
          _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
        } else {
          this.carrierFrequencyHz = _aidl_parcel.readLong();
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

    @Override // android.os.Parcelable
    public int describeContents() {
      return 0;
    }
  }
}
