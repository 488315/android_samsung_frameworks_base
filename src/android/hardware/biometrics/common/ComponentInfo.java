package android.hardware.biometrics.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ComponentInfo implements Parcelable {
    public static final Parcelable.Creator<ComponentInfo> CREATOR = new Parcelable.Creator<ComponentInfo>() { // from class: android.hardware.biometrics.common.ComponentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ComponentInfo createFromParcel(Parcel parcel) {
            ComponentInfo componentInfo = new ComponentInfo();
            componentInfo.readFromParcel(parcel);
            return componentInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ComponentInfo[] newArray(int i) {
            return new ComponentInfo[i];
        }
    };
    public String componentId;
    public String firmwareVersion;
    public String hardwareVersion;
    public String serialNumber;
    public String softwareVersion;

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
        parcel.writeString(this.componentId);
        parcel.writeString(this.hardwareVersion);
        parcel.writeString(this.firmwareVersion);
        parcel.writeString(this.serialNumber);
        parcel.writeString(this.softwareVersion);
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
                this.componentId = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.hardwareVersion = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.firmwareVersion = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.serialNumber = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.softwareVersion = parcel.readString();
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
}
