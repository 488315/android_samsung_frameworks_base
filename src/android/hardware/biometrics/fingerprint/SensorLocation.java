package android.hardware.biometrics.fingerprint;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SensorLocation implements Parcelable {
    public static final Parcelable.Creator<SensorLocation> CREATOR = new Parcelable.Creator<SensorLocation>() { // from class: android.hardware.biometrics.fingerprint.SensorLocation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SensorLocation createFromParcel(Parcel parcel) {
            SensorLocation sensorLocation = new SensorLocation();
            sensorLocation.readFromParcel(parcel);
            return sensorLocation;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SensorLocation[] newArray(int i) {
            return new SensorLocation[i];
        }
    };

    @Deprecated
    public int displayId = 0;
    public int sensorLocationX = 0;
    public int sensorLocationY = 0;
    public int sensorRadius = 0;
    public String display = "";
    public byte sensorShape = 1;

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
        parcel.writeInt(this.displayId);
        parcel.writeInt(this.sensorLocationX);
        parcel.writeInt(this.sensorLocationY);
        parcel.writeInt(this.sensorRadius);
        parcel.writeString(this.display);
        parcel.writeByte(this.sensorShape);
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
                this.displayId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.sensorLocationX = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.sensorLocationY = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.sensorRadius = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.display = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.sensorShape = parcel.readByte();
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
