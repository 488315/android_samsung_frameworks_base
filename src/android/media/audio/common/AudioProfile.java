package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioProfile implements Parcelable {
    public static final Parcelable.Creator<AudioProfile> CREATOR = new Parcelable.Creator<AudioProfile>() { // from class: android.media.audio.common.AudioProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioProfile createFromParcel(Parcel parcel) {
            AudioProfile audioProfile = new AudioProfile();
            audioProfile.readFromParcel(parcel);
            return audioProfile;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioProfile[] newArray(int i) {
            return new AudioProfile[i];
        }
    };
    public AudioChannelLayout[] channelMasks;
    public int encapsulationType = 0;
    public AudioFormatDescription format;
    public String name;
    public int[] sampleRates;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeTypedObject(this.format, i);
        parcel.writeTypedArray(this.channelMasks, i);
        parcel.writeIntArray(this.sampleRates);
        parcel.writeInt(this.encapsulationType);
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
                this.name = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.format = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.channelMasks = (AudioChannelLayout[]) parcel.createTypedArray(AudioChannelLayout.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.sampleRates = parcel.createIntArray();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.encapsulationType = parcel.readInt();
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
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("format: " + Objects.toString(this.format));
        stringJoiner.add("channelMasks: " + Arrays.toString(this.channelMasks));
        stringJoiner.add("sampleRates: " + Arrays.toString(this.sampleRates));
        stringJoiner.add("encapsulationType: " + this.encapsulationType);
        return "AudioProfile" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioProfile)) {
            return false;
        }
        AudioProfile audioProfile = (AudioProfile) obj;
        return Objects.deepEquals(this.name, audioProfile.name) && Objects.deepEquals(this.format, audioProfile.format) && Objects.deepEquals(this.channelMasks, audioProfile.channelMasks) && Objects.deepEquals(this.sampleRates, audioProfile.sampleRates) && Objects.deepEquals(Integer.valueOf(this.encapsulationType), Integer.valueOf(audioProfile.encapsulationType));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.name, this.format, this.channelMasks, this.sampleRates, Integer.valueOf(this.encapsulationType)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.channelMasks) | describeContents(this.format);
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
