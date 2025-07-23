package android.hardware.power;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class LatchedFrameData implements Parcelable {
    public static final Parcelable.Creator<LatchedFrameData> CREATOR = new Parcelable.Creator<LatchedFrameData>() { // from class: android.hardware.power.LatchedFrameData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatchedFrameData createFromParcel(Parcel parcel) {
            LatchedFrameData latchedFrameData = new LatchedFrameData();
            latchedFrameData.readFromParcel(parcel);
            return latchedFrameData;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatchedFrameData[] newArray(int i) {
            return new LatchedFrameData[i];
        }
    };
    public ParcelFileDescriptor gpuAcquireFence;
    public long frameStartTimestampNanos = 0;
    public long intendedPresentTimestampNanos = 0;
    public long bufferSubmissionTimestampNanos = 0;
    public long gpuSignalTimestampNanos = 0;
    public boolean usedGpu = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.frameStartTimestampNanos);
        parcel.writeLong(this.intendedPresentTimestampNanos);
        parcel.writeLong(this.bufferSubmissionTimestampNanos);
        parcel.writeLong(this.gpuSignalTimestampNanos);
        parcel.writeBoolean(this.usedGpu);
        parcel.writeTypedObject(this.gpuAcquireFence, i);
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
                this.frameStartTimestampNanos = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.intendedPresentTimestampNanos = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.bufferSubmissionTimestampNanos = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.gpuSignalTimestampNanos = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.usedGpu = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.gpuAcquireFence = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.gpuAcquireFence);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
