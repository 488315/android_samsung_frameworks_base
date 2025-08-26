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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.packetType);
        parcel.writeByte(this.lengthType);
        parcel.writeTypedObject(this.filterSettings, i);
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
                this.packetType = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.lengthType = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.filterSettings = (DemuxAlpFilterSettingsFilterSettings) parcel.readTypedObject(DemuxAlpFilterSettingsFilterSettings.CREATOR);
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
