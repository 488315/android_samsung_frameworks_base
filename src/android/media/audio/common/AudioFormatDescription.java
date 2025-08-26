package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioFormatDescription implements Parcelable {
    public static final Parcelable.Creator<AudioFormatDescription> CREATOR = new Parcelable.Creator<AudioFormatDescription>() { // from class: android.media.audio.common.AudioFormatDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioFormatDescription createFromParcel(Parcel parcel) {
            AudioFormatDescription audioFormatDescription = new AudioFormatDescription();
            audioFormatDescription.readFromParcel(parcel);
            return audioFormatDescription;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioFormatDescription[] newArray(int i) {
            return new AudioFormatDescription[i];
        }
    };
    public String encoding;
    public byte type = 0;
    public byte pcm = 0;

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
        parcel.writeByte(this.type);
        parcel.writeByte(this.pcm);
        parcel.writeString(this.encoding);
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
                this.type = parcel.readByte();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.pcm = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.encoding = parcel.readString();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("type: " + ((int) this.type));
        stringJoiner.add("pcm: " + ((int) this.pcm));
        stringJoiner.add("encoding: " + Objects.toString(this.encoding));
        return "AudioFormatDescription" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioFormatDescription)) {
            return false;
        }
        AudioFormatDescription audioFormatDescription = (AudioFormatDescription) obj;
        return Objects.deepEquals(java.lang.Byte.valueOf(this.type), java.lang.Byte.valueOf(audioFormatDescription.type)) && Objects.deepEquals(java.lang.Byte.valueOf(this.pcm), java.lang.Byte.valueOf(audioFormatDescription.pcm)) && Objects.deepEquals(this.encoding, audioFormatDescription.encoding);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(java.lang.Byte.valueOf(this.type), java.lang.Byte.valueOf(this.pcm), this.encoding).toArray());
    }
}
