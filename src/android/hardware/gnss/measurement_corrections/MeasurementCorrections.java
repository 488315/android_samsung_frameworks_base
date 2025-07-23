package android.hardware.gnss.measurement_corrections;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class MeasurementCorrections implements Parcelable {
    public static final Parcelable.Creator<MeasurementCorrections> CREATOR = new Parcelable.Creator<MeasurementCorrections>() { // from class: android.hardware.gnss.measurement_corrections.MeasurementCorrections.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MeasurementCorrections createFromParcel(Parcel parcel) {
            MeasurementCorrections measurementCorrections = new MeasurementCorrections();
            measurementCorrections.readFromParcel(parcel);
            return measurementCorrections;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MeasurementCorrections[] newArray(int i) {
            return new MeasurementCorrections[i];
        }
    };
    public SingleSatCorrection[] satCorrections;
    public double latitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double longitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double altitudeMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double horizontalPositionUncertaintyMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double verticalPositionUncertaintyMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public long toaGpsNanosecondsOfWeek = 0;
    public boolean hasEnvironmentBearing = false;
    public float environmentBearingDegrees = 0.0f;
    public float environmentBearingUncertaintyDegrees = 0.0f;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeDouble(this.latitudeDegrees);
        parcel.writeDouble(this.longitudeDegrees);
        parcel.writeDouble(this.altitudeMeters);
        parcel.writeDouble(this.horizontalPositionUncertaintyMeters);
        parcel.writeDouble(this.verticalPositionUncertaintyMeters);
        parcel.writeLong(this.toaGpsNanosecondsOfWeek);
        parcel.writeTypedArray(this.satCorrections, i);
        parcel.writeBoolean(this.hasEnvironmentBearing);
        parcel.writeFloat(this.environmentBearingDegrees);
        parcel.writeFloat(this.environmentBearingUncertaintyDegrees);
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
                this.latitudeDegrees = parcel.readDouble();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.longitudeDegrees = parcel.readDouble();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.altitudeMeters = parcel.readDouble();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.horizontalPositionUncertaintyMeters = parcel.readDouble();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.verticalPositionUncertaintyMeters = parcel.readDouble();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.toaGpsNanosecondsOfWeek = parcel.readLong();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.satCorrections = (SingleSatCorrection[]) parcel.createTypedArray(SingleSatCorrection.CREATOR);
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.hasEnvironmentBearing = parcel.readBoolean();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.environmentBearingDegrees = parcel.readFloat();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.environmentBearingUncertaintyDegrees = parcel.readFloat();
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
        return describeContents(this.satCorrections);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
