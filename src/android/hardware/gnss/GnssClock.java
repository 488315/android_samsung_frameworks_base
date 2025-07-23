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
        int dataPosition = parcel.dataPosition();
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
                this.gnssClockFlags = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.leapSecond = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.timeNs = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.timeUncertaintyNs = parcel.readDouble();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.fullBiasNs = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.biasNs = parcel.readDouble();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.biasUncertaintyNs = parcel.readDouble();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.driftNsps = parcel.readDouble();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.driftUncertaintyNsps = parcel.readDouble();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.hwClockDiscontinuityCount = parcel.readInt();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.referenceSignalTypeForIsb = (GnssSignalType) parcel.readTypedObject(GnssSignalType.CREATOR);
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
        return describeContents(this.referenceSignalTypeForIsb);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
