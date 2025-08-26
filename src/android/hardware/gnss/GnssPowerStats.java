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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.elapsedRealtime, i);
        parcel.writeDouble(this.totalEnergyMilliJoule);
        parcel.writeDouble(this.singlebandTrackingModeEnergyMilliJoule);
        parcel.writeDouble(this.multibandTrackingModeEnergyMilliJoule);
        parcel.writeDouble(this.singlebandAcquisitionModeEnergyMilliJoule);
        parcel.writeDouble(this.multibandAcquisitionModeEnergyMilliJoule);
        parcel.writeDoubleArray(this.otherModesEnergyMilliJoule);
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
                this.elapsedRealtime = (ElapsedRealtime) parcel.readTypedObject(ElapsedRealtime.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.totalEnergyMilliJoule = parcel.readDouble();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.singlebandTrackingModeEnergyMilliJoule = parcel.readDouble();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.multibandTrackingModeEnergyMilliJoule = parcel.readDouble();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.singlebandAcquisitionModeEnergyMilliJoule = parcel.readDouble();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.multibandAcquisitionModeEnergyMilliJoule = parcel.readDouble();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.otherModesEnergyMilliJoule = parcel.createDoubleArray();
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
        return describeContents(this.elapsedRealtime);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
