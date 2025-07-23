package android.hardware.thermal;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CoolingDevice implements Parcelable {
    public static final Parcelable.Creator<CoolingDevice> CREATOR = new Parcelable.Creator<CoolingDevice>() { // from class: android.hardware.thermal.CoolingDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CoolingDevice createFromParcel(Parcel parcel) {
            CoolingDevice coolingDevice = new CoolingDevice();
            coolingDevice.readFromParcel(parcel);
            return coolingDevice;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CoolingDevice[] newArray(int i) {
            return new CoolingDevice[i];
        }
    };
    public String name;
    public int type;
    public long value = 0;
    public long powerLimitMw = 0;
    public long powerMw = 0;
    public long timeWindowMs = 0;

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
        parcel.writeInt(this.type);
        parcel.writeString(this.name);
        parcel.writeLong(this.value);
        parcel.writeLong(this.powerLimitMw);
        parcel.writeLong(this.powerMw);
        parcel.writeLong(this.timeWindowMs);
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
                    this.name = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.value = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.powerLimitMw = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.powerMw = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.timeWindowMs = parcel.readLong();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("value: " + this.value);
        stringJoiner.add("powerLimitMw: " + this.powerLimitMw);
        stringJoiner.add("powerMw: " + this.powerMw);
        stringJoiner.add("timeWindowMs: " + this.timeWindowMs);
        return "CoolingDevice" + stringJoiner.toString();
    }
}
