package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioConfigBase implements Parcelable {
    public static final Parcelable.Creator<AudioConfigBase> CREATOR = new Parcelable.Creator<AudioConfigBase>() { // from class: android.media.audio.common.AudioConfigBase.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioConfigBase createFromParcel(Parcel parcel) {
            AudioConfigBase audioConfigBase = new AudioConfigBase();
            audioConfigBase.readFromParcel(parcel);
            return audioConfigBase;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioConfigBase[] newArray(int i) {
            return new AudioConfigBase[i];
        }
    };
    public AudioChannelLayout channelMask;
    public AudioFormatDescription format;
    public int sampleRate = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sampleRate);
        parcel.writeTypedObject(this.channelMask, i);
        parcel.writeTypedObject(this.format, i);
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
                this.sampleRate = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.channelMask = (AudioChannelLayout) parcel.readTypedObject(AudioChannelLayout.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.format = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("sampleRate: " + this.sampleRate);
        stringJoiner.add("channelMask: " + Objects.toString(this.channelMask));
        stringJoiner.add("format: " + Objects.toString(this.format));
        return "AudioConfigBase" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioConfigBase)) {
            return false;
        }
        AudioConfigBase audioConfigBase = (AudioConfigBase) obj;
        return Objects.deepEquals(Integer.valueOf(this.sampleRate), Integer.valueOf(audioConfigBase.sampleRate)) && Objects.deepEquals(this.channelMask, audioConfigBase.channelMask) && Objects.deepEquals(this.format, audioConfigBase.format);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.sampleRate), this.channelMask, this.format).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.format) | describeContents(this.channelMask);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
