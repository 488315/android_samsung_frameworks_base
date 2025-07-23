package android.hardware.radio.ims;

import android.hardware.radio.AccessNetwork$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class ImsRegistration implements Parcelable {
    public static final Parcelable.Creator<ImsRegistration> CREATOR = new Parcelable.Creator<ImsRegistration>() { // from class: android.hardware.radio.ims.ImsRegistration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsRegistration createFromParcel(Parcel parcel) {
            ImsRegistration imsRegistration = new ImsRegistration();
            imsRegistration.readFromParcel(parcel);
            return imsRegistration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsRegistration[] newArray(int i) {
            return new ImsRegistration[i];
        }
    };
    public static final int IMS_MMTEL_CAPABILITY_NONE = 0;
    public static final int IMS_MMTEL_CAPABILITY_SMS = 4;
    public static final int IMS_MMTEL_CAPABILITY_VIDEO = 2;
    public static final int IMS_MMTEL_CAPABILITY_VOICE = 1;
    public static final int IMS_RCS_CAPABILITIES = 8;
    public int regState = 0;
    public int accessNetworkType = 0;
    public int suggestedAction = 0;
    public int capabilities = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.regState);
        parcel.writeInt(this.accessNetworkType);
        parcel.writeInt(this.suggestedAction);
        parcel.writeInt(this.capabilities);
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
                this.regState = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.accessNetworkType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.suggestedAction = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.capabilities = parcel.readInt();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("regState: " + ImsRegistrationState$$.toString(this.regState));
        stringJoiner.add("accessNetworkType: " + AccessNetwork$$.toString(this.accessNetworkType));
        stringJoiner.add("suggestedAction: " + SuggestedAction$$.toString(this.suggestedAction));
        stringJoiner.add("capabilities: " + this.capabilities);
        return "ImsRegistration" + stringJoiner.toString();
    }
}
