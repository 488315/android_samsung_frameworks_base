package android.hardware.input;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public class IInputDeviceBatteryState implements Parcelable {
    public static final Parcelable.Creator<IInputDeviceBatteryState> CREATOR = new Parcelable.Creator<IInputDeviceBatteryState>() { // from class: android.hardware.input.IInputDeviceBatteryState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IInputDeviceBatteryState createFromParcel(Parcel parcel) {
            IInputDeviceBatteryState iInputDeviceBatteryState = new IInputDeviceBatteryState();
            iInputDeviceBatteryState.readFromParcel(parcel);
            return iInputDeviceBatteryState;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IInputDeviceBatteryState[] newArray(int i) {
            return new IInputDeviceBatteryState[i];
        }
    };
    public int deviceId = 0;
    public long updateTime = 0;
    public boolean isPresent = false;
    public int status = 0;
    public float capacity = 0.0f;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.deviceId);
        parcel.writeLong(this.updateTime);
        parcel.writeBoolean(this.isPresent);
        parcel.writeInt(this.status);
        parcel.writeFloat(this.capacity);
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
                this.deviceId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.updateTime = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.isPresent = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.status = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.capacity = parcel.readFloat();
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof IInputDeviceBatteryState)) {
            return false;
        }
        IInputDeviceBatteryState iInputDeviceBatteryState = (IInputDeviceBatteryState) obj;
        return Objects.deepEquals(Integer.valueOf(this.deviceId), Integer.valueOf(iInputDeviceBatteryState.deviceId)) && Objects.deepEquals(Long.valueOf(this.updateTime), Long.valueOf(iInputDeviceBatteryState.updateTime)) && Objects.deepEquals(Boolean.valueOf(this.isPresent), Boolean.valueOf(iInputDeviceBatteryState.isPresent)) && Objects.deepEquals(Integer.valueOf(this.status), Integer.valueOf(iInputDeviceBatteryState.status)) && Objects.deepEquals(Float.valueOf(this.capacity), Float.valueOf(iInputDeviceBatteryState.capacity));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.deviceId), Long.valueOf(this.updateTime), Boolean.valueOf(this.isPresent), Integer.valueOf(this.status), Float.valueOf(this.capacity)).toArray());
    }
}
