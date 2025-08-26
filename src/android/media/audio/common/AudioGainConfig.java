package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioGainConfig implements Parcelable {
    public static final Parcelable.Creator<AudioGainConfig> CREATOR = new Parcelable.Creator<AudioGainConfig>() { // from class: android.media.audio.common.AudioGainConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioGainConfig createFromParcel(Parcel parcel) {
            AudioGainConfig audioGainConfig = new AudioGainConfig();
            audioGainConfig.readFromParcel(parcel);
            return audioGainConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioGainConfig[] newArray(int i) {
            return new AudioGainConfig[i];
        }
    };
    public AudioChannelLayout channelMask;
    public int index = 0;
    public int mode = 0;
    public int rampDurationMs = 0;
    public int[] values;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.index);
        parcel.writeInt(this.mode);
        parcel.writeTypedObject(this.channelMask, i);
        parcel.writeIntArray(this.values);
        parcel.writeInt(this.rampDurationMs);
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
                this.index = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mode = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.channelMask = (AudioChannelLayout) parcel.readTypedObject(AudioChannelLayout.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.values = parcel.createIntArray();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.rampDurationMs = parcel.readInt();
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
        stringJoiner.add("index: " + this.index);
        stringJoiner.add("mode: " + this.mode);
        stringJoiner.add("channelMask: " + Objects.toString(this.channelMask));
        stringJoiner.add("values: " + Arrays.toString(this.values));
        stringJoiner.add("rampDurationMs: " + this.rampDurationMs);
        return "AudioGainConfig" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioGainConfig)) {
            return false;
        }
        AudioGainConfig audioGainConfig = (AudioGainConfig) obj;
        return Objects.deepEquals(Integer.valueOf(this.index), Integer.valueOf(audioGainConfig.index)) && Objects.deepEquals(Integer.valueOf(this.mode), Integer.valueOf(audioGainConfig.mode)) && Objects.deepEquals(this.channelMask, audioGainConfig.channelMask) && Objects.deepEquals(this.values, audioGainConfig.values) && Objects.deepEquals(Integer.valueOf(this.rampDurationMs), Integer.valueOf(audioGainConfig.rampDurationMs));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.index), Integer.valueOf(this.mode), this.channelMask, this.values, Integer.valueOf(this.rampDurationMs)).toArray());
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
