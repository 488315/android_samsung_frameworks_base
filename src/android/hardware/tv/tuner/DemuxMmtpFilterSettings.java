package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxMmtpFilterSettings implements Parcelable {
    public static final Parcelable.Creator<DemuxMmtpFilterSettings> CREATOR = new Parcelable.Creator<DemuxMmtpFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxMmtpFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxMmtpFilterSettings createFromParcel(Parcel parcel) {
            DemuxMmtpFilterSettings demuxMmtpFilterSettings = new DemuxMmtpFilterSettings();
            demuxMmtpFilterSettings.readFromParcel(parcel);
            return demuxMmtpFilterSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxMmtpFilterSettings[] newArray(int i) {
            return new DemuxMmtpFilterSettings[i];
        }
    };
    public DemuxMmtpFilterSettingsFilterSettings filterSettings;
    public int mmtpPid = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.mmtpPid);
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
                this.mmtpPid = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.filterSettings = (DemuxMmtpFilterSettingsFilterSettings) parcel.readTypedObject(DemuxMmtpFilterSettingsFilterSettings.CREATOR);
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
