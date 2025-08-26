package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SatellitePvt implements Parcelable {
    public static final Parcelable.Creator<SatellitePvt> CREATOR = new Parcelable.Creator<SatellitePvt>() { // from class: android.hardware.gnss.SatellitePvt.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatellitePvt createFromParcel(Parcel parcel) {
            SatellitePvt satellitePvt = new SatellitePvt();
            satellitePvt.readFromParcel(parcel);
            return satellitePvt;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatellitePvt[] newArray(int i) {
            return new SatellitePvt[i];
        }
    };
    public static final int HAS_IONO = 2;
    public static final int HAS_POSITION_VELOCITY_CLOCK_INFO = 1;
    public static final int HAS_TROPO = 4;
    public SatelliteClockInfo satClockInfo;
    public SatellitePositionEcef satPosEcef;
    public SatelliteVelocityEcef satVelEcef;
    public int flags = 0;
    public double ionoDelayMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double tropoDelayMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public long timeOfClockSeconds = 0;
    public int issueOfDataClock = 0;
    public long timeOfEphemerisSeconds = 0;
    public int issueOfDataEphemeris = 0;
    public int ephemerisSource = 3;

    public @interface SatelliteEphemerisSource {
        public static final int DEMODULATED = 0;
        public static final int OTHER = 3;
        public static final int SERVER_LONG_TERM = 2;
        public static final int SERVER_NORMAL = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.flags);
        parcel.writeTypedObject(this.satPosEcef, i);
        parcel.writeTypedObject(this.satVelEcef, i);
        parcel.writeTypedObject(this.satClockInfo, i);
        parcel.writeDouble(this.ionoDelayMeters);
        parcel.writeDouble(this.tropoDelayMeters);
        parcel.writeLong(this.timeOfClockSeconds);
        parcel.writeInt(this.issueOfDataClock);
        parcel.writeLong(this.timeOfEphemerisSeconds);
        parcel.writeInt(this.issueOfDataEphemeris);
        parcel.writeInt(this.ephemerisSource);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.flags = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.satPosEcef = (SatellitePositionEcef) parcel.readTypedObject(SatellitePositionEcef.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.satVelEcef = (SatelliteVelocityEcef) parcel.readTypedObject(SatelliteVelocityEcef.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.satClockInfo = (SatelliteClockInfo) parcel.readTypedObject(SatelliteClockInfo.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.ionoDelayMeters = parcel.readDouble();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.tropoDelayMeters = parcel.readDouble();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.timeOfClockSeconds = parcel.readLong();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.issueOfDataClock = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.timeOfEphemerisSeconds = parcel.readLong();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.issueOfDataEphemeris = parcel.readInt();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.ephemerisSource = parcel.readInt();
                                                        if (iDataPosition > Integer.MAX_VALUE - i) {
                                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                                        }
                                                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                                    }
                                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                }
                                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.satClockInfo) | describeContents(this.satPosEcef) | describeContents(this.satVelEcef);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
