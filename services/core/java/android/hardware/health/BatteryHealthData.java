package android.hardware.health;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BatteryHealthData implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public long batteryFirstUsageSeconds;
    public long batteryManufacturingDateSeconds;
    public int batteryPartStatus;
    public String batterySerialNumber;
    public long batteryStateOfHealth;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.health.BatteryHealthData$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            BatteryHealthData batteryHealthData = new BatteryHealthData();
            batteryHealthData.batteryManufacturingDateSeconds = 0L;
            batteryHealthData.batteryFirstUsageSeconds = 0L;
            batteryHealthData.batteryStateOfHealth = 0L;
            batteryHealthData.batteryPartStatus = 0;
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    batteryHealthData.batteryManufacturingDateSeconds = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        batteryHealthData.batteryFirstUsageSeconds = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            batteryHealthData.batteryStateOfHealth = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                batteryHealthData.batterySerialNumber = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    batteryHealthData.batteryPartStatus = parcel.readInt();
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
                return batteryHealthData;
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new BatteryHealthData[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.batteryManufacturingDateSeconds);
        parcel.writeLong(this.batteryFirstUsageSeconds);
        parcel.writeLong(this.batteryStateOfHealth);
        parcel.writeString(this.batterySerialNumber);
        int m = SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(parcel, this.batteryPartStatus, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
