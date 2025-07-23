package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterPesDataSettings implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterPesDataSettings> CREATOR = new Parcelable.Creator<DemuxFilterPesDataSettings>() { // from class: android.hardware.tv.tuner.DemuxFilterPesDataSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterPesDataSettings createFromParcel(Parcel parcel) {
            DemuxFilterPesDataSettings demuxFilterPesDataSettings = new DemuxFilterPesDataSettings();
            demuxFilterPesDataSettings.readFromParcel(parcel);
            return demuxFilterPesDataSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterPesDataSettings[] newArray(int i) {
            return new DemuxFilterPesDataSettings[i];
        }
    };
    public int streamId = 0;
    public boolean isRaw = false;

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
        parcel.writeInt(this.streamId);
        parcel.writeBoolean(this.isRaw);
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
                this.streamId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isRaw = parcel.readBoolean();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
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
