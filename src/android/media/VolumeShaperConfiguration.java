package android.media;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class VolumeShaperConfiguration implements Parcelable {
    public static final Parcelable.Creator<VolumeShaperConfiguration> CREATOR = new Parcelable.Creator<VolumeShaperConfiguration>() { // from class: android.media.VolumeShaperConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VolumeShaperConfiguration createFromParcel(Parcel parcel) {
            VolumeShaperConfiguration volumeShaperConfiguration = new VolumeShaperConfiguration();
            volumeShaperConfiguration.readFromParcel(parcel);
            return volumeShaperConfiguration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VolumeShaperConfiguration[] newArray(int i) {
            return new VolumeShaperConfiguration[i];
        }
    };
    public InterpolatorConfig interpolatorConfig;
    public int type = 0;
    public int id = 0;
    public int optionFlags = 0;
    public double durationMs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeInt(this.id);
        parcel.writeInt(this.optionFlags);
        parcel.writeDouble(this.durationMs);
        parcel.writeTypedObject(this.interpolatorConfig, i);
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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.id = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.optionFlags = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.durationMs = parcel.readDouble();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.interpolatorConfig = (InterpolatorConfig) parcel.readTypedObject(InterpolatorConfig.CREATOR);
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
        return describeContents(this.interpolatorConfig);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
