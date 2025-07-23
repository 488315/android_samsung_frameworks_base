package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterSectionEvent implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterSectionEvent> CREATOR = new Parcelable.Creator<DemuxFilterSectionEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterSectionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSectionEvent createFromParcel(Parcel parcel) {
            DemuxFilterSectionEvent demuxFilterSectionEvent = new DemuxFilterSectionEvent();
            demuxFilterSectionEvent.readFromParcel(parcel);
            return demuxFilterSectionEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSectionEvent[] newArray(int i) {
            return new DemuxFilterSectionEvent[i];
        }
    };
    public int tableId = 0;
    public int version = 0;
    public int sectionNum = 0;
    public long dataLength = 0;

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
        parcel.writeInt(this.tableId);
        parcel.writeInt(this.version);
        parcel.writeInt(this.sectionNum);
        parcel.writeLong(this.dataLength);
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
                this.tableId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.version = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.sectionNum = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.dataLength = parcel.readLong();
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
