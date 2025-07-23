package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterTemiEvent implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterTemiEvent> CREATOR = new Parcelable.Creator<DemuxFilterTemiEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterTemiEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterTemiEvent createFromParcel(Parcel parcel) {
            DemuxFilterTemiEvent demuxFilterTemiEvent = new DemuxFilterTemiEvent();
            demuxFilterTemiEvent.readFromParcel(parcel);
            return demuxFilterTemiEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterTemiEvent[] newArray(int i) {
            return new DemuxFilterTemiEvent[i];
        }
    };
    public byte[] descrData;
    public long pts = 0;
    public byte descrTag = 0;

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
        parcel.writeLong(this.pts);
        parcel.writeByte(this.descrTag);
        parcel.writeByteArray(this.descrData);
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
                this.pts = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.descrTag = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.descrData = parcel.createByteArray();
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
