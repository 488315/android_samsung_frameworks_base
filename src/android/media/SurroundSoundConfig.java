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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.formatFamilies, i);
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
                this.formatFamilies = (SurroundFormatFamily[]) parcel.createTypedArray(SurroundFormatFamily.CREATOR);
                if (dataPosition > Integer.MAX_VALUE - readInt) {
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
        return describeContents(this.formatFamilies);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
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
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.primaryFormat, i);
            parcel.writeTypedArray(this.subFormats, i);
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
                    this.primaryFormat = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.subFormats = (AudioFormatDescription[]) parcel.createTypedArray(AudioFormatDescription.CREATOR);
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.subFormats) | describeContents(this.primaryFormat);
        }

        private int describeContents(Object obj) {
            if (obj == null) {
                return 0;
            }
            if (obj instanceof Object[]) {
                int i = 0;
                for (Object obj2 : (Object[]) obj) {
                    i |= describeContents(obj2);
                }
                return i;
            }
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }
}
