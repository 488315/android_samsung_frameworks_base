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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.device, i);
        parcel.writeInt(this.flags);
        parcel.writeTypedArray(this.encodedFormats, i);
        parcel.writeInt(this.encapsulationModes);
        parcel.writeInt(this.encapsulationMetadataTypes);
        parcel.writeTypedObject(this.speakerLayout, i);
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
                this.device = (AudioDevice) parcel.readTypedObject(AudioDevice.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.flags = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.encodedFormats = (AudioFormatDescription[]) parcel.createTypedArray(AudioFormatDescription.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.encapsulationModes = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.encapsulationMetadataTypes = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.speakerLayout = (AudioChannelLayout) parcel.readTypedObject(AudioChannelLayout.CREATOR);
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
