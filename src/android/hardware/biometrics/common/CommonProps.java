package android.hardware.biometrics.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class CommonProps implements Parcelable {
    public static final Parcelable.Creator<CommonProps> CREATOR = new Parcelable.Creator<CommonProps>() { // from class: android.hardware.biometrics.common.CommonProps.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommonProps createFromParcel(Parcel parcel) {
            CommonProps commonProps = new CommonProps();
            commonProps.readFromParcel(parcel);
            return commonProps;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommonProps[] newArray(int i) {
            return new CommonProps[i];
        }
    };
    public ComponentInfo[] componentInfo;
    public int sensorId = 0;
    public byte sensorStrength = 0;
    public int maxEnrollmentsPerUser = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sensorId);
        parcel.writeByte(this.sensorStrength);
        parcel.writeInt(this.maxEnrollmentsPerUser);
        parcel.writeTypedArray(this.componentInfo, i);
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
                this.sensorId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.sensorStrength = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.maxEnrollmentsPerUser = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.componentInfo = (ComponentInfo[]) parcel.createTypedArray(ComponentInfo.CREATOR);
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
        return describeContents(this.componentInfo);
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
