package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxAlpFilterSettings implements Parcelable {
    public static final Parcelable.Creator<DemuxAlpFilterSettings> CREATOR = new Parcelable.Creator<DemuxAlpFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxAlpFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxAlpFilterSettings createFromParcel(Parcel parcel) {
            DemuxAlpFilterSettings demuxAlpFilterSettings = new DemuxAlpFilterSettings();
            demuxAlpFilterSettings.readFromParcel(parcel);
            return demuxAlpFilterSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxAlpFilterSettings[] newArray(int i) {
            return new DemuxAlpFilterSettings[i];
        }
    };
    public DemuxAlpFilterSettingsFilterSettings filterSettings;
    public int packetType = 0;
    public byte lengthType = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.packetType);
        parcel.writeByte(this.lengthType);
        parcel.writeTypedObject(this.filterSettings, i);
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
                this.packetType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.lengthType = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.filterSettings = (DemuxAlpFilterSettingsFilterSettings) parcel.readTypedObject(DemuxAlpFilterSettingsFilterSettings.CREATOR);
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
        return describeContents(this.filterSettings);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
