package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioGain implements Parcelable {
    public static final Parcelable.Creator<AudioGain> CREATOR = new Parcelable.Creator<AudioGain>() { // from class: android.media.audio.common.AudioGain.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioGain createFromParcel(Parcel parcel) {
            AudioGain audioGain = new AudioGain();
            audioGain.readFromParcel(parcel);
            return audioGain;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioGain[] newArray(int i) {
            return new AudioGain[i];
        }
    };
    public AudioChannelLayout channelMask;
    public int mode = 0;
    public int minValue = 0;
    public int maxValue = 0;
    public int defaultValue = 0;
    public int stepValue = 0;
    public int minRampMs = 0;
    public int maxRampMs = 0;
    public boolean useForVolume = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.mode);
        parcel.writeTypedObject(this.channelMask, i);
        parcel.writeInt(this.minValue);
        parcel.writeInt(this.maxValue);
        parcel.writeInt(this.defaultValue);
        parcel.writeInt(this.stepValue);
        parcel.writeInt(this.minRampMs);
        parcel.writeInt(this.maxRampMs);
        parcel.writeBoolean(this.useForVolume);
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
                this.mode = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.channelMask = (AudioChannelLayout) parcel.readTypedObject(AudioChannelLayout.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.minValue = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.maxValue = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.defaultValue = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.stepValue = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.minRampMs = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.maxRampMs = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.useForVolume = parcel.readBoolean();
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
        stringJoiner.add("mode: " + this.mode);
        stringJoiner.add("channelMask: " + Objects.toString(this.channelMask));
        stringJoiner.add("minValue: " + this.minValue);
        stringJoiner.add("maxValue: " + this.maxValue);
        stringJoiner.add("defaultValue: " + this.defaultValue);
        stringJoiner.add("stepValue: " + this.stepValue);
        stringJoiner.add("minRampMs: " + this.minRampMs);
        stringJoiner.add("maxRampMs: " + this.maxRampMs);
        stringJoiner.add("useForVolume: " + this.useForVolume);
        return "AudioGain" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioGain)) {
            return false;
        }
        AudioGain audioGain = (AudioGain) obj;
        return Objects.deepEquals(Integer.valueOf(this.mode), Integer.valueOf(audioGain.mode)) && Objects.deepEquals(this.channelMask, audioGain.channelMask) && Objects.deepEquals(Integer.valueOf(this.minValue), Integer.valueOf(audioGain.minValue)) && Objects.deepEquals(Integer.valueOf(this.maxValue), Integer.valueOf(audioGain.maxValue)) && Objects.deepEquals(Integer.valueOf(this.defaultValue), Integer.valueOf(audioGain.defaultValue)) && Objects.deepEquals(Integer.valueOf(this.stepValue), Integer.valueOf(audioGain.stepValue)) && Objects.deepEquals(Integer.valueOf(this.minRampMs), Integer.valueOf(audioGain.minRampMs)) && Objects.deepEquals(Integer.valueOf(this.maxRampMs), Integer.valueOf(audioGain.maxRampMs)) && Objects.deepEquals(java.lang.Boolean.valueOf(this.useForVolume), java.lang.Boolean.valueOf(audioGain.useForVolume));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.mode), this.channelMask, Integer.valueOf(this.minValue), Integer.valueOf(this.maxValue), Integer.valueOf(this.defaultValue), Integer.valueOf(this.stepValue), Integer.valueOf(this.minRampMs), Integer.valueOf(this.maxRampMs), java.lang.Boolean.valueOf(this.useForVolume)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.channelMask);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
