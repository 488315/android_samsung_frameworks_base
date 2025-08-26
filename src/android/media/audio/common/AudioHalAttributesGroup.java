package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioHalAttributesGroup implements Parcelable {
    public static final Parcelable.Creator<AudioHalAttributesGroup> CREATOR = new Parcelable.Creator<AudioHalAttributesGroup>() { // from class: android.media.audio.common.AudioHalAttributesGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalAttributesGroup createFromParcel(Parcel parcel) {
            AudioHalAttributesGroup audioHalAttributesGroup = new AudioHalAttributesGroup();
            audioHalAttributesGroup.readFromParcel(parcel);
            return audioHalAttributesGroup;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalAttributesGroup[] newArray(int i) {
            return new AudioHalAttributesGroup[i];
        }
    };
    public AudioAttributes[] attributes;
    public int streamType = -2;
    public String volumeGroupName;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.streamType);
        parcel.writeString(this.volumeGroupName);
        parcel.writeTypedArray(this.attributes, i);
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
                this.streamType = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.volumeGroupName = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.attributes = (AudioAttributes[]) parcel.createTypedArray(AudioAttributes.CREATOR);
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
        stringJoiner.add("streamType: " + this.streamType);
        stringJoiner.add("volumeGroupName: " + Objects.toString(this.volumeGroupName));
        stringJoiner.add("attributes: " + Arrays.toString(this.attributes));
        return "AudioHalAttributesGroup" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioHalAttributesGroup)) {
            return false;
        }
        AudioHalAttributesGroup audioHalAttributesGroup = (AudioHalAttributesGroup) obj;
        return Objects.deepEquals(Integer.valueOf(this.streamType), Integer.valueOf(audioHalAttributesGroup.streamType)) && Objects.deepEquals(this.volumeGroupName, audioHalAttributesGroup.volumeGroupName) && Objects.deepEquals(this.attributes, audioHalAttributesGroup.attributes);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.streamType), this.volumeGroupName, this.attributes).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.attributes);
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
