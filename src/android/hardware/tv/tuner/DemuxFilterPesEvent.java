package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterPesEvent implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterPesEvent> CREATOR = new Parcelable.Creator<DemuxFilterPesEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterPesEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterPesEvent createFromParcel(Parcel parcel) {
            DemuxFilterPesEvent demuxFilterPesEvent = new DemuxFilterPesEvent();
            demuxFilterPesEvent.readFromParcel(parcel);
            return demuxFilterPesEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterPesEvent[] newArray(int i) {
            return new DemuxFilterPesEvent[i];
        }
    };
    public int streamId = 0;
    public int dataLength = 0;
    public int mpuSequenceNumber = 0;

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
        parcel.writeInt(this.streamId);
        parcel.writeInt(this.dataLength);
        parcel.writeInt(this.mpuSequenceNumber);
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
                this.streamId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.dataLength = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.mpuSequenceNumber = parcel.readInt();
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
