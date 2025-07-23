package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class GnssLocation implements Parcelable {
    public static final Parcelable.Creator<GnssLocation> CREATOR = new Parcelable.Creator<GnssLocation>() { // from class: android.hardware.gnss.GnssLocation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssLocation createFromParcel(Parcel parcel) {
            GnssLocation gnssLocation = new GnssLocation();
            gnssLocation.readFromParcel(parcel);
            return gnssLocation;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssLocation[] newArray(int i) {
            return new GnssLocation[i];
        }
    };
    public static final int HAS_ALTITUDE = 2;
    public static final int HAS_BEARING = 8;
    public static final int HAS_BEARING_ACCURACY = 128;
    public static final int HAS_HORIZONTAL_ACCURACY = 16;
    public static final int HAS_LAT_LONG = 1;
    public static final int HAS_SPEED = 4;
    public static final int HAS_SPEED_ACCURACY = 64;
    public static final int HAS_VERTICAL_ACCURACY = 32;
    public ElapsedRealtime elapsedRealtime;
    public int gnssLocationFlags = 0;
    public double latitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double longitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double altitudeMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double speedMetersPerSec = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double bearingDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double horizontalAccuracyMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double verticalAccuracyMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double speedAccuracyMetersPerSecond = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double bearingAccuracyDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public long timestampMillis = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.gnssLocationFlags);
        parcel.writeDouble(this.latitudeDegrees);
        parcel.writeDouble(this.longitudeDegrees);
        parcel.writeDouble(this.altitudeMeters);
        parcel.writeDouble(this.speedMetersPerSec);
        parcel.writeDouble(this.bearingDegrees);
        parcel.writeDouble(this.horizontalAccuracyMeters);
        parcel.writeDouble(this.verticalAccuracyMeters);
        parcel.writeDouble(this.speedAccuracyMetersPerSecond);
        parcel.writeDouble(this.bearingAccuracyDegrees);
        parcel.writeLong(this.timestampMillis);
        parcel.writeTypedObject(this.elapsedRealtime, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.gnssLocationFlags = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.latitudeDegrees = parcel.readDouble();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.longitudeDegrees = parcel.readDouble();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.altitudeMeters = parcel.readDouble();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.speedMetersPerSec = parcel.readDouble();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.bearingDegrees = parcel.readDouble();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.horizontalAccuracyMeters = parcel.readDouble();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.verticalAccuracyMeters = parcel.readDouble();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.speedAccuracyMetersPerSecond = parcel.readDouble();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.bearingAccuracyDegrees = parcel.readDouble();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.timestampMillis = parcel.readLong();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.elapsedRealtime = (ElapsedRealtime) parcel.readTypedObject(ElapsedRealtime.CREATOR);
                                                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                                            }
                                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                                        }
                                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                                    }
                                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                }
                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.elapsedRealtime);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
