package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterRecordSettings implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterRecordSettings> CREATOR = new Parcelable.Creator<DemuxFilterRecordSettings>() { // from class: android.hardware.tv.tuner.DemuxFilterRecordSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterRecordSettings createFromParcel(Parcel parcel) {
            DemuxFilterRecordSettings demuxFilterRecordSettings = new DemuxFilterRecordSettings();
            demuxFilterRecordSettings.readFromParcel(parcel);
            return demuxFilterRecordSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterRecordSettings[] newArray(int i) {
            return new DemuxFilterRecordSettings[i];
        }
    };
    public DemuxFilterScIndexMask scIndexMask;
    public int tsIndexMask = 0;
    public int scIndexType = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.tsIndexMask);
        parcel.writeInt(this.scIndexType);
        parcel.writeTypedObject(this.scIndexMask, i);
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
                this.tsIndexMask = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.scIndexType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.scIndexMask = (DemuxFilterScIndexMask) parcel.readTypedObject(DemuxFilterScIndexMask.CREATOR);
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
        return describeContents(this.scIndexMask);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
