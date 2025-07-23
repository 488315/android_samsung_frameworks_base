package android.telephony.satellite.stub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class SatelliteModemEnableRequestAttributes implements Parcelable {
    public static final Parcelable.Creator<SatelliteModemEnableRequestAttributes> CREATOR = new Parcelable.Creator<SatelliteModemEnableRequestAttributes>() { // from class: android.telephony.satellite.stub.SatelliteModemEnableRequestAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteModemEnableRequestAttributes createFromParcel(Parcel parcel) {
            SatelliteModemEnableRequestAttributes satelliteModemEnableRequestAttributes = new SatelliteModemEnableRequestAttributes();
            satelliteModemEnableRequestAttributes.readFromParcel(parcel);
            return satelliteModemEnableRequestAttributes;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteModemEnableRequestAttributes[] newArray(int i) {
            return new SatelliteModemEnableRequestAttributes[i];
        }
    };
    public SatelliteSubscriptionInfo satelliteSubscriptionInfo;
    public boolean isEnabled = false;
    public boolean isDemoMode = false;
    public boolean isEmergencyMode = false;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.isEnabled);
        parcel.writeBoolean(this.isDemoMode);
        parcel.writeBoolean(this.isEmergencyMode);
        parcel.writeTypedObject(this.satelliteSubscriptionInfo, i);
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
                this.isEnabled = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isDemoMode = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.isEmergencyMode = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.satelliteSubscriptionInfo = (SatelliteSubscriptionInfo) parcel.readTypedObject(SatelliteSubscriptionInfo.CREATOR);
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
        return describeContents(this.satelliteSubscriptionInfo);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
