package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioPlaybackRate implements Parcelable {
    public static final Parcelable.Creator<AudioPlaybackRate> CREATOR = new Parcelable.Creator<AudioPlaybackRate>() { // from class: android.media.audio.common.AudioPlaybackRate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPlaybackRate createFromParcel(Parcel parcel) {
            AudioPlaybackRate audioPlaybackRate = new AudioPlaybackRate();
            audioPlaybackRate.readFromParcel(parcel);
            return audioPlaybackRate;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPlaybackRate[] newArray(int i) {
            return new AudioPlaybackRate[i];
        }
    };
    public float speed = 0.0f;
    public float pitch = 0.0f;
    public int timestretchMode = 0;
    public int fallbackMode = 0;

    public @interface TimestretchFallbackMode {
        public static final int FAIL = 2;
        public static final int MUTE = 1;
        public static final int SYS_RESERVED_CUT_REPEAT = -1;
        public static final int SYS_RESERVED_DEFAULT = 0;
    }

    public @interface TimestretchMode {
        public static final int DEFAULT = 0;
        public static final int VOICE = 1;
    }

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
        parcel.writeFloat(this.speed);
        parcel.writeFloat(this.pitch);
        parcel.writeInt(this.timestretchMode);
        parcel.writeInt(this.fallbackMode);
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
                this.speed = parcel.readFloat();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.pitch = parcel.readFloat();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.timestretchMode = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.fallbackMode = parcel.readInt();
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
        stringJoiner.add("speed: " + this.speed);
        stringJoiner.add("pitch: " + this.pitch);
        stringJoiner.add("timestretchMode: " + this.timestretchMode);
        stringJoiner.add("fallbackMode: " + this.fallbackMode);
        return "AudioPlaybackRate" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioPlaybackRate)) {
            return false;
        }
        AudioPlaybackRate audioPlaybackRate = (AudioPlaybackRate) obj;
        return Objects.deepEquals(java.lang.Float.valueOf(this.speed), java.lang.Float.valueOf(audioPlaybackRate.speed)) && Objects.deepEquals(java.lang.Float.valueOf(this.pitch), java.lang.Float.valueOf(audioPlaybackRate.pitch)) && Objects.deepEquals(Integer.valueOf(this.timestretchMode), Integer.valueOf(audioPlaybackRate.timestretchMode)) && Objects.deepEquals(Integer.valueOf(this.fallbackMode), Integer.valueOf(audioPlaybackRate.fallbackMode));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(java.lang.Float.valueOf(this.speed), java.lang.Float.valueOf(this.pitch), Integer.valueOf(this.timestretchMode), Integer.valueOf(this.fallbackMode)).toArray());
    }
}
