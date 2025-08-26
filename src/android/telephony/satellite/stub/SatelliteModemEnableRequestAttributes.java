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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.isEnabled);
        parcel.writeBoolean(this.isDemoMode);
        parcel.writeBoolean(this.isEmergencyMode);
        parcel.writeTypedObject(this.satelliteSubscriptionInfo, i);
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
                this.isEnabled = parcel.readBoolean();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.isDemoMode = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.isEmergencyMode = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.satelliteSubscriptionInfo = (SatelliteSubscriptionInfo) parcel.readTypedObject(SatelliteSubscriptionInfo.CREATOR);
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
        return describeContents(this.satelliteSubscriptionInfo);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
