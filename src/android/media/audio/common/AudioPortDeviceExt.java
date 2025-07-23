package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioPortDeviceExt implements Parcelable {
    public static final Parcelable.Creator<AudioPortDeviceExt> CREATOR = new Parcelable.Creator<AudioPortDeviceExt>() { // from class: android.media.audio.common.AudioPortDeviceExt.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortDeviceExt createFromParcel(Parcel parcel) {
            AudioPortDeviceExt audioPortDeviceExt = new AudioPortDeviceExt();
            audioPortDeviceExt.readFromParcel(parcel);
            return audioPortDeviceExt;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortDeviceExt[] newArray(int i) {
            return new AudioPortDeviceExt[i];
        }
    };
    public static final int FLAG_INDEX_DEFAULT_DEVICE = 0;
    public AudioDevice device;
    public AudioFormatDescription[] encodedFormats;
    public AudioChannelLayout speakerLayout;
    public int flags = 0;
    public int encapsulationModes = 0;
    public int encapsulationMetadataTypes = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.device, i);
        parcel.writeInt(this.flags);
        parcel.writeTypedArray(this.encodedFormats, i);
        parcel.writeInt(this.encapsulationModes);
        parcel.writeInt(this.encapsulationMetadataTypes);
        parcel.writeTypedObject(this.speakerLayout, i);
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
                this.device = (AudioDevice) parcel.readTypedObject(AudioDevice.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.flags = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.encodedFormats = (AudioFormatDescription[]) parcel.createTypedArray(AudioFormatDescription.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.encapsulationModes = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.encapsulationMetadataTypes = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.speakerLayout = (AudioChannelLayout) parcel.readTypedObject(AudioChannelLayout.CREATOR);
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
        stringJoiner.add("device: " + Objects.toString(this.device));
        stringJoiner.add("flags: " + this.flags);
        stringJoiner.add("encodedFormats: " + Arrays.toString(this.encodedFormats));
        stringJoiner.add("encapsulationModes: " + this.encapsulationModes);
        stringJoiner.add("encapsulationMetadataTypes: " + this.encapsulationMetadataTypes);
        stringJoiner.add("speakerLayout: " + Objects.toString(this.speakerLayout));
        return "AudioPortDeviceExt" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioPortDeviceExt)) {
            return false;
        }
        AudioPortDeviceExt audioPortDeviceExt = (AudioPortDeviceExt) obj;
        return Objects.deepEquals(this.device, audioPortDeviceExt.device) && Objects.deepEquals(Integer.valueOf(this.flags), Integer.valueOf(audioPortDeviceExt.flags)) && Objects.deepEquals(this.encodedFormats, audioPortDeviceExt.encodedFormats) && Objects.deepEquals(Integer.valueOf(this.encapsulationModes), Integer.valueOf(audioPortDeviceExt.encapsulationModes)) && Objects.deepEquals(Integer.valueOf(this.encapsulationMetadataTypes), Integer.valueOf(audioPortDeviceExt.encapsulationMetadataTypes)) && Objects.deepEquals(this.speakerLayout, audioPortDeviceExt.speakerLayout);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.device, Integer.valueOf(this.flags), this.encodedFormats, Integer.valueOf(this.encapsulationModes), Integer.valueOf(this.encapsulationMetadataTypes), this.speakerLayout).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.speakerLayout) | describeContents(this.device) | describeContents(this.encodedFormats);
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
