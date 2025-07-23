package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class GnssPowerStats implements Parcelable {
    public static final Parcelable.Creator<GnssPowerStats> CREATOR = new Parcelable.Creator<GnssPowerStats>() { // from class: android.hardware.gnss.GnssPowerStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssPowerStats createFromParcel(Parcel parcel) {
            GnssPowerStats gnssPowerStats = new GnssPowerStats();
            gnssPowerStats.readFromParcel(parcel);
            return gnssPowerStats;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssPowerStats[] newArray(int i) {
            return new GnssPowerStats[i];
        }
    };
    public ElapsedRealtime elapsedRealtime;
    public double[] otherModesEnergyMilliJoule;
    public double totalEnergyMilliJoule = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double singlebandTrackingModeEnergyMilliJoule = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double multibandTrackingModeEnergyMilliJoule = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double singlebandAcquisitionModeEnergyMilliJoule = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double multibandAcquisitionModeEnergyMilliJoule = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.elapsedRealtime, i);
        parcel.writeDouble(this.totalEnergyMilliJoule);
        parcel.writeDouble(this.singlebandTrackingModeEnergyMilliJoule);
        parcel.writeDouble(this.multibandTrackingModeEnergyMilliJoule);
        parcel.writeDouble(this.singlebandAcquisitionModeEnergyMilliJoule);
        parcel.writeDouble(this.multibandAcquisitionModeEnergyMilliJoule);
        parcel.writeDoubleArray(this.otherModesEnergyMilliJoule);
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
                this.elapsedRealtime = (ElapsedRealtime) parcel.readTypedObject(ElapsedRealtime.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.totalEnergyMilliJoule = parcel.readDouble();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.singlebandTrackingModeEnergyMilliJoule = parcel.readDouble();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.multibandTrackingModeEnergyMilliJoule = parcel.readDouble();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.singlebandAcquisitionModeEnergyMilliJoule = parcel.readDouble();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.multibandAcquisitionModeEnergyMilliJoule = parcel.readDouble();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.otherModesEnergyMilliJoule = parcel.createDoubleArray();
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
