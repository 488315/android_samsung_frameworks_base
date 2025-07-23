package android.hardware.power;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class CompositionData implements Parcelable {
    public static final Parcelable.Creator<CompositionData> CREATOR = new Parcelable.Creator<CompositionData>() { // from class: android.hardware.power.CompositionData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompositionData createFromParcel(Parcel parcel) {
            CompositionData compositionData = new CompositionData();
            compositionData.readFromParcel(parcel);
            return compositionData;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompositionData[] newArray(int i) {
            return new CompositionData[i];
        }
    };
    public long[] outputIds;
    public FrameProducer[] producers;
    public long[] scheduledPresentTimestampsNanos;
    public CompositionUpdate updateData;
    public long timestampNanos = 0;
    public long latchTimestampNanos = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.timestampNanos);
        parcel.writeLongArray(this.scheduledPresentTimestampsNanos);
        parcel.writeLong(this.latchTimestampNanos);
        parcel.writeTypedArray(this.producers, i);
        parcel.writeTypedObject(this.updateData, i);
        parcel.writeLongArray(this.outputIds);
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
                this.timestampNanos = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.scheduledPresentTimestampsNanos = parcel.createLongArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.latchTimestampNanos = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.producers = (FrameProducer[]) parcel.createTypedArray(FrameProducer.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.updateData = (CompositionUpdate) parcel.readTypedObject(CompositionUpdate.CREATOR);
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.outputIds = parcel.createLongArray();
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
        return describeContents(this.updateData) | describeContents(this.producers);
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
