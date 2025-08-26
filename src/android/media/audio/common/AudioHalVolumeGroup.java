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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeInt(this.minIndex);
        parcel.writeInt(this.maxIndex);
        parcel.writeTypedArray(this.volumeCurves, i);
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
                this.name = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.minIndex = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.maxIndex = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.volumeCurves = (AudioHalVolumeCurve[]) parcel.createTypedArray(AudioHalVolumeCurve.CREATOR);
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
