package android.hardware.power;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class CompositionUpdate implements Parcelable {
    public static final Parcelable.Creator<CompositionUpdate> CREATOR = new Parcelable.Creator<CompositionUpdate>() { // from class: android.hardware.power.CompositionUpdate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompositionUpdate createFromParcel(Parcel parcel) {
            CompositionUpdate compositionUpdate = new CompositionUpdate();
            compositionUpdate.readFromParcel(parcel);
            return compositionUpdate;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompositionUpdate[] newArray(int i) {
            return new CompositionUpdate[i];
        }
    };
    public long[] deadOutputIds;
    public FrameProducerUpdate[] producerUpdates;
    public long timestampNanos = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.timestampNanos);
        parcel.writeTypedArray(this.producerUpdates, i);
        parcel.writeLongArray(this.deadOutputIds);
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
                    this.producerUpdates = (FrameProducerUpdate[]) parcel.createTypedArray(FrameProducerUpdate.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.deadOutputIds = parcel.createLongArray();
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
        return describeContents(this.producerUpdates);
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
