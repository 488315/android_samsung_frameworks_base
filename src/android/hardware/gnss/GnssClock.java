package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class GnssClock implements Parcelable {
    public static final Parcelable.Creator<GnssClock> CREATOR = new Parcelable.Creator<GnssClock>() { // from class: android.hardware.gnss.GnssClock.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssClock createFromParcel(Parcel parcel) {
            GnssClock gnssClock = new GnssClock();
            gnssClock.readFromParcel(parcel);
            return gnssClock;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssClock[] newArray(int i) {
            return new GnssClock[i];
        }
    };
    public static final int HAS_BIAS = 8;
    public static final int HAS_BIAS_UNCERTAINTY = 16;
    public static final int HAS_DRIFT = 32;
    public static final int HAS_DRIFT_UNCERTAINTY = 64;
    public static final int HAS_FULL_BIAS = 4;
    public static final int HAS_LEAP_SECOND = 1;
    public static final int HAS_TIME_UNCERTAINTY = 2;
    public GnssSignalType referenceSignalTypeForIsb;
    public int gnssClockFlags = 0;
    public int leapSecond = 0;
    public long timeNs = 0;
    public double timeUncertaintyNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public long fullBiasNs = 0;
    public double biasNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double biasUncertaintyNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double driftNsps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double driftUncertaintyNsps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public int hwClockDiscontinuityCount = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.gnssClockFlags);
        parcel.writeInt(this.leapSecond);
        parcel.writeLong(this.timeNs);
        parcel.writeDouble(this.timeUncertaintyNs);
        parcel.writeLong(this.fullBiasNs);
        parcel.writeDouble(this.biasNs);
        parcel.writeDouble(this.biasUncertaintyNs);
        parcel.writeDouble(this.driftNsps);
        parcel.writeDouble(this.driftUncertaintyNsps);
        parcel.writeInt(this.hwClockDiscontinuityCount);
        parcel.writeTypedObject(this.referenceSignalTypeForIsb, i);
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
                this.gnssClockFlags = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.leapSecond = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.timeNs = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.timeUncertaintyNs = parcel.readDouble();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.fullBiasNs = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.biasNs = parcel.readDouble();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.biasUncertaintyNs = parcel.readDouble();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.driftNsps = parcel.readDouble();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.driftUncertaintyNsps = parcel.readDouble();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.hwClockDiscontinuityCount = parcel.readInt();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.referenceSignalTypeForIsb = (GnssSignalType) parcel.readTypedObject(GnssSignalType.CREATOR);
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
        return describeContents(this.referenceSignalTypeForIsb);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
