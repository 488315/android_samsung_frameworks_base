package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioHalVolumeGroup implements Parcelable {
    public static final Parcelable.Creator<AudioHalVolumeGroup> CREATOR = new Parcelable.Creator<AudioHalVolumeGroup>() { // from class: android.media.audio.common.AudioHalVolumeGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalVolumeGroup createFromParcel(Parcel parcel) {
            AudioHalVolumeGroup audioHalVolumeGroup = new AudioHalVolumeGroup();
            audioHalVolumeGroup.readFromParcel(parcel);
            return audioHalVolumeGroup;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalVolumeGroup[] newArray(int i) {
            return new AudioHalVolumeGroup[i];
        }
    };
    public static final int INDEX_DEFERRED_TO_AUDIO_SERVICE = -1;
    public String name;
    public AudioHalVolumeCurve[] volumeCurves;
    public int minIndex = 0;
    public int maxIndex = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeInt(this.minIndex);
        parcel.writeInt(this.maxIndex);
        parcel.writeTypedArray(this.volumeCurves, i);
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
                    this.minIndex = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.maxIndex = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.volumeCurves = (AudioHalVolumeCurve[]) parcel.createTypedArray(AudioHalVolumeCurve.CREATOR);
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
        stringJoiner.add("minIndex: " + this.minIndex);
        stringJoiner.add("maxIndex: " + this.maxIndex);
        stringJoiner.add("volumeCurves: " + Arrays.toString(this.volumeCurves));
        return "AudioHalVolumeGroup" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioHalVolumeGroup)) {
            return false;
        }
        AudioHalVolumeGroup audioHalVolumeGroup = (AudioHalVolumeGroup) obj;
        return Objects.deepEquals(this.name, audioHalVolumeGroup.name) && Objects.deepEquals(Integer.valueOf(this.minIndex), Integer.valueOf(audioHalVolumeGroup.minIndex)) && Objects.deepEquals(Integer.valueOf(this.maxIndex), Integer.valueOf(audioHalVolumeGroup.maxIndex)) && Objects.deepEquals(this.volumeCurves, audioHalVolumeGroup.volumeCurves);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.name, Integer.valueOf(this.minIndex), Integer.valueOf(this.maxIndex), this.volumeCurves).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.volumeCurves);
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
