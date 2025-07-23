package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioHalCapConfiguration implements Parcelable {
    public static final Parcelable.Creator<AudioHalCapConfiguration> CREATOR = new Parcelable.Creator<AudioHalCapConfiguration>() { // from class: android.media.audio.common.AudioHalCapConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapConfiguration createFromParcel(Parcel parcel) {
            AudioHalCapConfiguration audioHalCapConfiguration = new AudioHalCapConfiguration();
            audioHalCapConfiguration.readFromParcel(parcel);
            return audioHalCapConfiguration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapConfiguration[] newArray(int i) {
            return new AudioHalCapConfiguration[i];
        }
    };
    public String name;
    public AudioHalCapParameter[] parameterSettings;
    public AudioHalCapRule rule;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeTypedObject(this.rule, i);
        parcel.writeTypedArray(this.parameterSettings, i);
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
                    this.rule = (AudioHalCapRule) parcel.readTypedObject(AudioHalCapRule.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.parameterSettings = (AudioHalCapParameter[]) parcel.createTypedArray(AudioHalCapParameter.CREATOR);
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
        stringJoiner.add("rule: " + Objects.toString(this.rule));
        stringJoiner.add("parameterSettings: " + Arrays.toString(this.parameterSettings));
        return "AudioHalCapConfiguration" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioHalCapConfiguration)) {
            return false;
        }
        AudioHalCapConfiguration audioHalCapConfiguration = (AudioHalCapConfiguration) obj;
        return Objects.deepEquals(this.name, audioHalCapConfiguration.name) && Objects.deepEquals(this.rule, audioHalCapConfiguration.rule) && Objects.deepEquals(this.parameterSettings, audioHalCapConfiguration.parameterSettings);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.name, this.rule, this.parameterSettings).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.parameterSettings) | describeContents(this.rule);
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
