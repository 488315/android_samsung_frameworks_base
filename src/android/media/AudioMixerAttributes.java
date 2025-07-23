package android.media;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AudioMixerAttributes implements Parcelable {
    public static final Parcelable.Creator<AudioMixerAttributes> CREATOR = new Parcelable.Creator<AudioMixerAttributes>() { // from class: android.media.AudioMixerAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMixerAttributes createFromParcel(Parcel parcel) {
            return new AudioMixerAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMixerAttributes[] newArray(int i) {
            return new AudioMixerAttributes[i];
        }
    };
    public static final int MIXER_BEHAVIOR_BIT_PERFECT = 1;
    public static final int MIXER_BEHAVIOR_DEFAULT = 0;
    private final AudioFormat mFormat;
    private final int mMixerBehavior;

    @Retention(RetentionPolicy.SOURCE)
    public @interface MixerBehavior {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AudioMixerAttributes(AudioFormat audioFormat, int i) {
        this.mFormat = audioFormat;
        this.mMixerBehavior = i;
    }

    public AudioFormat getFormat() {
        return this.mFormat;
    }

    public int getMixerBehavior() {
        return this.mMixerBehavior;
    }

    public static final class Builder {
        private final AudioFormat mFormat;
        private int mMixerBehavior = 0;

        public Builder(AudioFormat audioFormat) {
            Objects.requireNonNull(audioFormat);
            this.mFormat = audioFormat;
        }

        public AudioMixerAttributes build() {
            return new AudioMixerAttributes(this.mFormat, this.mMixerBehavior);
        }

        public Builder setMixerBehavior(int i) {
            if (i == 0 || i == 1) {
                this.mMixerBehavior = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid mixer behavior " + i);
        }
    }

    public int hashCode() {
        return Objects.hash(this.mFormat, Integer.valueOf(this.mMixerBehavior));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AudioMixerAttributes audioMixerAttributes = (AudioMixerAttributes) obj;
            if (this.mFormat.equals(audioMixerAttributes.mFormat) && this.mMixerBehavior == audioMixerAttributes.mMixerBehavior) {
                return true;
            }
        }
        return false;
    }

    private String mixerBehaviorToString(int i) {
        if (i == 0) {
            return "default";
        }
        if (i == 1) {
            return "bit-perfect";
        }
        return "unknown";
    }

    public String toString() {
        return new String("AudioMixerAttributes: format:" + this.mFormat.toString() + " mixer behavior:" + mixerBehaviorToString(this.mMixerBehavior));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mFormat, i);
        parcel.writeInt(this.mMixerBehavior);
    }

    private AudioMixerAttributes(Parcel parcel) {
        this.mFormat = (AudioFormat) parcel.readParcelable(AudioFormat.class.getClassLoader(), AudioFormat.class);
        this.mMixerBehavior = parcel.readInt();
    }
}
