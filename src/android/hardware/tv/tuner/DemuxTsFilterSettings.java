package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxTsFilterSettings implements Parcelable {
    public static final Parcelable.Creator<DemuxTsFilterSettings> CREATOR = new Parcelable.Creator<DemuxTsFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxTsFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxTsFilterSettings createFromParcel(Parcel parcel) {
            DemuxTsFilterSettings demuxTsFilterSettings = new DemuxTsFilterSettings();
            demuxTsFilterSettings.readFromParcel(parcel);
            return demuxTsFilterSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxTsFilterSettings[] newArray(int i) {
            return new DemuxTsFilterSettings[i];
        }
    };
    public DemuxTsFilterSettingsFilterSettings filterSettings;
    public int tpid = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.tpid);
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
                this.tpid = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.filterSettings = (DemuxTsFilterSettingsFilterSettings) parcel.readTypedObject(DemuxTsFilterSettingsFilterSettings.CREATOR);
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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
