package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxTlvFilterSettings implements Parcelable {
    public static final Parcelable.Creator<DemuxTlvFilterSettings> CREATOR = new Parcelable.Creator<DemuxTlvFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxTlvFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxTlvFilterSettings createFromParcel(Parcel parcel) {
            DemuxTlvFilterSettings demuxTlvFilterSettings = new DemuxTlvFilterSettings();
            demuxTlvFilterSettings.readFromParcel(parcel);
            return demuxTlvFilterSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxTlvFilterSettings[] newArray(int i) {
            return new DemuxTlvFilterSettings[i];
        }
    };
    public DemuxTlvFilterSettingsFilterSettings filterSettings;
    public int packetType = 0;
    public boolean isCompressedIpPacket = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.packetType);
        parcel.writeBoolean(this.isCompressedIpPacket);
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
                    this.isCompressedIpPacket = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.filterSettings = (DemuxTlvFilterSettingsFilterSettings) parcel.readTypedObject(DemuxTlvFilterSettingsFilterSettings.CREATOR);
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
