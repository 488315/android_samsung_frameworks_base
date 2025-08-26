package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterSectionSettingsConditionTableInfo implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterSectionSettingsConditionTableInfo> CREATOR = new Parcelable.Creator<DemuxFilterSectionSettingsConditionTableInfo>() { // from class: android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSectionSettingsConditionTableInfo createFromParcel(Parcel parcel) {
            DemuxFilterSectionSettingsConditionTableInfo demuxFilterSectionSettingsConditionTableInfo = new DemuxFilterSectionSettingsConditionTableInfo();
            demuxFilterSectionSettingsConditionTableInfo.readFromParcel(parcel);
            return demuxFilterSectionSettingsConditionTableInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSectionSettingsConditionTableInfo[] newArray(int i) {
            return new DemuxFilterSectionSettingsConditionTableInfo[i];
        }
    };
    public int tableId = 0;
    public int version = 0;

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
        parcel.writeInt(this.tableId);
        parcel.writeInt(this.version);
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
                this.tableId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.version = parcel.readInt();
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
}
