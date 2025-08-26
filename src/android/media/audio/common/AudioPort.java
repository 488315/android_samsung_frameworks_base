package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioPort implements Parcelable {
    public static final Parcelable.Creator<AudioPort> CREATOR = new Parcelable.Creator<AudioPort>() { // from class: android.media.audio.common.AudioPort.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPort createFromParcel(Parcel parcel) {
            AudioPort audioPort = new AudioPort();
            audioPort.readFromParcel(parcel);
            return audioPort;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPort[] newArray(int i) {
            return new AudioPort[i];
        }
    };
    public AudioPortExt ext;
    public ExtraAudioDescriptor[] extraAudioDescriptors;
    public AudioIoFlags flags;
    public AudioGain[] gains;
    public int id = 0;
    public String name;
    public AudioProfile[] profiles;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeTypedArray(this.profiles, i);
        parcel.writeTypedObject(this.flags, i);
        parcel.writeTypedArray(this.extraAudioDescriptors, i);
        parcel.writeTypedArray(this.gains, i);
        parcel.writeTypedObject(this.ext, i);
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
                this.id = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.name = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.profiles = (AudioProfile[]) parcel.createTypedArray(AudioProfile.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.flags = (AudioIoFlags) parcel.readTypedObject(AudioIoFlags.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.extraAudioDescriptors = (ExtraAudioDescriptor[]) parcel.createTypedArray(ExtraAudioDescriptor.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.gains = (AudioGain[]) parcel.createTypedArray(AudioGain.CREATOR);
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.ext = (AudioPortExt) parcel.readTypedObject(AudioPortExt.CREATOR);
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
        stringJoiner.add("id: " + this.id);
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("profiles: " + Arrays.toString(this.profiles));
        stringJoiner.add("flags: " + Objects.toString(this.flags));
        stringJoiner.add("extraAudioDescriptors: " + Arrays.toString(this.extraAudioDescriptors));
        stringJoiner.add("gains: " + Arrays.toString(this.gains));
        stringJoiner.add("ext: " + Objects.toString(this.ext));
        return "AudioPort" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioPort)) {
            return false;
        }
        AudioPort audioPort = (AudioPort) obj;
        return Objects.deepEquals(Integer.valueOf(this.id), Integer.valueOf(audioPort.id)) && Objects.deepEquals(this.name, audioPort.name) && Objects.deepEquals(this.profiles, audioPort.profiles) && Objects.deepEquals(this.flags, audioPort.flags) && Objects.deepEquals(this.extraAudioDescriptors, audioPort.extraAudioDescriptors) && Objects.deepEquals(this.gains, audioPort.gains) && Objects.deepEquals(this.ext, audioPort.ext);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.id), this.name, this.profiles, this.flags, this.extraAudioDescriptors, this.gains, this.ext).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.ext) | describeContents(this.profiles) | describeContents(this.flags) | describeContents(this.extraAudioDescriptors) | describeContents(this.gains);
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
