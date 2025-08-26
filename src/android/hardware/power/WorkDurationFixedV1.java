package android.hardware.power;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class WorkDurationFixedV1 implements Parcelable {
    public static final Parcelable.Creator<WorkDurationFixedV1> CREATOR = new Parcelable.Creator<WorkDurationFixedV1>() { // from class: android.hardware.power.WorkDurationFixedV1.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WorkDurationFixedV1 createFromParcel(Parcel parcel) {
            WorkDurationFixedV1 workDurationFixedV1 = new WorkDurationFixedV1();
            workDurationFixedV1.readFromParcel(parcel);
            return workDurationFixedV1;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WorkDurationFixedV1[] newArray(int i) {
            return new WorkDurationFixedV1[i];
        }
    };
    public long durationNanos = 0;
    public long workPeriodStartTimestampNanos = 0;
    public long cpuDurationNanos = 0;
    public long gpuDurationNanos = 0;

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
        parcel.writeLong(this.durationNanos);
        parcel.writeLong(this.workPeriodStartTimestampNanos);
        parcel.writeLong(this.cpuDurationNanos);
        parcel.writeLong(this.gpuDurationNanos);
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
                this.durationNanos = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.workPeriodStartTimestampNanos = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.cpuDurationNanos = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.gpuDurationNanos = parcel.readLong();
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
}
