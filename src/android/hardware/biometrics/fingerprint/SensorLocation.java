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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.displayId);
        parcel.writeInt(this.sensorLocationX);
        parcel.writeInt(this.sensorLocationY);
        parcel.writeInt(this.sensorRadius);
        parcel.writeString(this.display);
        parcel.writeByte(this.sensorShape);
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
                this.displayId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.sensorLocationX = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.sensorLocationY = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.sensorRadius = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.display = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.sensorShape = parcel.readByte();
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
