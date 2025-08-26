package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterSectionSettings implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterSectionSettings> CREATOR = new Parcelable.Creator<DemuxFilterSectionSettings>() { // from class: android.hardware.tv.tuner.DemuxFilterSectionSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSectionSettings createFromParcel(Parcel parcel) {
            DemuxFilterSectionSettings demuxFilterSectionSettings = new DemuxFilterSectionSettings();
            demuxFilterSectionSettings.readFromParcel(parcel);
            return demuxFilterSectionSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSectionSettings[] newArray(int i) {
            return new DemuxFilterSectionSettings[i];
        }
    };
    public DemuxFilterSectionSettingsCondition condition;
    public boolean isCheckCrc = false;
    public boolean isRepeat = false;
    public boolean isRaw = false;
    public int bitWidthOfLengthField = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.condition, i);
        parcel.writeBoolean(this.isCheckCrc);
        parcel.writeBoolean(this.isRepeat);
        parcel.writeBoolean(this.isRaw);
        parcel.writeInt(this.bitWidthOfLengthField);
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
                this.condition = (DemuxFilterSectionSettingsCondition) parcel.readTypedObject(DemuxFilterSectionSettingsCondition.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.isCheckCrc = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.isRepeat = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.isRaw = parcel.readBoolean();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.bitWidthOfLengthField = parcel.readInt();
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
        return describeContents(this.condition);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
