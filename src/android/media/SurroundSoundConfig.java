package android.media;

import android.media.audio.common.AudioFormatDescription;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SurroundSoundConfig implements Parcelable {
    public static final Parcelable.Creator<SurroundSoundConfig> CREATOR = new Parcelable.Creator<SurroundSoundConfig>() { // from class: android.media.SurroundSoundConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurroundSoundConfig createFromParcel(Parcel parcel) {
            SurroundSoundConfig surroundSoundConfig = new SurroundSoundConfig();
            surroundSoundConfig.readFromParcel(parcel);
            return surroundSoundConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurroundSoundConfig[] newArray(int i) {
            return new SurroundSoundConfig[i];
        }
    };
    public SurroundFormatFamily[] formatFamilies;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.formatFamilies, i);
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
                this.formatFamilies = (SurroundFormatFamily[]) parcel.createTypedArray(SurroundFormatFamily.CREATOR);
                if (iDataPosition > Integer.MAX_VALUE - i) {
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
        return describeContents(this.formatFamilies);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    public static class SurroundFormatFamily implements Parcelable {
        public static final Parcelable.Creator<SurroundFormatFamily> CREATOR = new Parcelable.Creator<SurroundFormatFamily>() { // from class: android.media.SurroundSoundConfig.SurroundFormatFamily.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SurroundFormatFamily createFromParcel(Parcel parcel) {
                SurroundFormatFamily surroundFormatFamily = new SurroundFormatFamily();
                surroundFormatFamily.readFromParcel(parcel);
                return surroundFormatFamily;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SurroundFormatFamily[] newArray(int i) {
                return new SurroundFormatFamily[i];
            }
        };
        public AudioFormatDescription primaryFormat;
        public AudioFormatDescription[] subFormats;

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.primaryFormat, i);
            parcel.writeTypedArray(this.subFormats, i);
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
                    this.primaryFormat = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.subFormats = (AudioFormatDescription[]) parcel.createTypedArray(AudioFormatDescription.CREATOR);
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
            return describeContents(this.subFormats) | describeContents(this.primaryFormat);
        }

        private int describeContents(Object obj) {
            if (obj == null) {
                return 0;
            }
            if (obj instanceof Object[]) {
                int iDescribeContents = 0;
                for (Object obj2 : (Object[]) obj) {
                    iDescribeContents |= describeContents(obj2);
                }
                return iDescribeContents;
            }
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }
}
