package android.hardware.power;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrameProducerUpdate implements Parcelable {
    public static final Parcelable.Creator<FrameProducerUpdate> CREATOR = new Parcelable.Creator<FrameProducerUpdate>() { // from class: android.hardware.power.FrameProducerUpdate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrameProducerUpdate createFromParcel(Parcel parcel) {
            FrameProducerUpdate frameProducerUpdate = new FrameProducerUpdate();
            frameProducerUpdate.readFromParcel(parcel);
            return frameProducerUpdate;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrameProducerUpdate[] newArray(int i) {
            return new FrameProducerUpdate[i];
        }
    };
    public int[] sessions;
    public long producerId = 0;
    public boolean isDead = false;

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
        parcel.writeLong(this.producerId);
        parcel.writeBoolean(this.isDead);
        parcel.writeIntArray(this.sessions);
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
                this.producerId = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isDead = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.sessions = parcel.createIntArray();
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
}
