package android.hardware.power;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class WorkDuration implements Parcelable {
    public static final Parcelable.Creator<WorkDuration> CREATOR = new Parcelable.Creator<WorkDuration>() { // from class: android.hardware.power.WorkDuration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WorkDuration createFromParcel(Parcel parcel) {
            WorkDuration workDuration = new WorkDuration();
            workDuration.readFromParcel(parcel);
            return workDuration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WorkDuration[] newArray(int i) {
            return new WorkDuration[i];
        }
    };
    public long timeStampNanos = 0;
    public long durationNanos = 0;
    public long workPeriodStartTimestampNanos = 0;
    public long cpuDurationNanos = 0;
    public long gpuDurationNanos = 0;
    public long intendedPresentTimestampNanos = 0;

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
        parcel.writeLong(this.timeStampNanos);
        parcel.writeLong(this.durationNanos);
        parcel.writeLong(this.workPeriodStartTimestampNanos);
        parcel.writeLong(this.cpuDurationNanos);
        parcel.writeLong(this.gpuDurationNanos);
        parcel.writeLong(this.intendedPresentTimestampNanos);
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
                this.timeStampNanos = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.durationNanos = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.workPeriodStartTimestampNanos = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.cpuDurationNanos = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.gpuDurationNanos = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.intendedPresentTimestampNanos = parcel.readLong();
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
