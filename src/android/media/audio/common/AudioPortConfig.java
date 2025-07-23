package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioPortConfig implements Parcelable {
    public static final Parcelable.Creator<AudioPortConfig> CREATOR = new Parcelable.Creator<AudioPortConfig>() { // from class: android.media.audio.common.AudioPortConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortConfig createFromParcel(Parcel parcel) {
            AudioPortConfig audioPortConfig = new AudioPortConfig();
            audioPortConfig.readFromParcel(parcel);
            return audioPortConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortConfig[] newArray(int i) {
            return new AudioPortConfig[i];
        }
    };
    public AudioChannelLayout channelMask;
    public AudioPortExt ext;
    public AudioIoFlags flags;
    public AudioFormatDescription format;
    public AudioGainConfig gain;
    public int id = 0;
    public int portId = 0;
    public Int sampleRate;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeInt(this.portId);
        parcel.writeTypedObject(this.sampleRate, i);
        parcel.writeTypedObject(this.channelMask, i);
        parcel.writeTypedObject(this.format, i);
        parcel.writeTypedObject(this.gain, i);
        parcel.writeTypedObject(this.flags, i);
        parcel.writeTypedObject(this.ext, i);
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
                this.id = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.portId = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.sampleRate = (Int) parcel.readTypedObject(Int.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.channelMask = (AudioChannelLayout) parcel.readTypedObject(AudioChannelLayout.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.format = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.gain = (AudioGainConfig) parcel.readTypedObject(AudioGainConfig.CREATOR);
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.flags = (AudioIoFlags) parcel.readTypedObject(AudioIoFlags.CREATOR);
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.ext = (AudioPortExt) parcel.readTypedObject(AudioPortExt.CREATOR);
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
        stringJoiner.add("id: " + this.id);
        stringJoiner.add("portId: " + this.portId);
        stringJoiner.add("sampleRate: " + Objects.toString(this.sampleRate));
        stringJoiner.add("channelMask: " + Objects.toString(this.channelMask));
        stringJoiner.add("format: " + Objects.toString(this.format));
        stringJoiner.add("gain: " + Objects.toString(this.gain));
        stringJoiner.add("flags: " + Objects.toString(this.flags));
        stringJoiner.add("ext: " + Objects.toString(this.ext));
        return "AudioPortConfig" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioPortConfig)) {
            return false;
        }
        AudioPortConfig audioPortConfig = (AudioPortConfig) obj;
        return Objects.deepEquals(Integer.valueOf(this.id), Integer.valueOf(audioPortConfig.id)) && Objects.deepEquals(Integer.valueOf(this.portId), Integer.valueOf(audioPortConfig.portId)) && Objects.deepEquals(this.sampleRate, audioPortConfig.sampleRate) && Objects.deepEquals(this.channelMask, audioPortConfig.channelMask) && Objects.deepEquals(this.format, audioPortConfig.format) && Objects.deepEquals(this.gain, audioPortConfig.gain) && Objects.deepEquals(this.flags, audioPortConfig.flags) && Objects.deepEquals(this.ext, audioPortConfig.ext);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.id), Integer.valueOf(this.portId), this.sampleRate, this.channelMask, this.format, this.gain, this.flags, this.ext).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.ext) | describeContents(this.sampleRate) | describeContents(this.channelMask) | describeContents(this.format) | describeContents(this.gain) | describeContents(this.flags);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
