package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioHalProductStrategy implements Parcelable {
    public static final Parcelable.Creator<AudioHalProductStrategy> CREATOR = new Parcelable.Creator<AudioHalProductStrategy>() { // from class: android.media.audio.common.AudioHalProductStrategy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalProductStrategy createFromParcel(Parcel parcel) {
            AudioHalProductStrategy audioHalProductStrategy = new AudioHalProductStrategy();
            audioHalProductStrategy.readFromParcel(parcel);
            return audioHalProductStrategy;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalProductStrategy[] newArray(int i) {
            return new AudioHalProductStrategy[i];
        }
    };
    public static final int VENDOR_STRATEGY_ID_START = 1000;
    public AudioHalAttributesGroup[] attributesGroups;
    public String name;
    public int id = -1;
    public int zoneId = 0;

    public @interface ZoneId {
        public static final int DEFAULT = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeTypedArray(this.attributesGroups, i);
        parcel.writeString(this.name);
        parcel.writeInt(this.zoneId);
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
                    this.attributesGroups = (AudioHalAttributesGroup[]) parcel.createTypedArray(AudioHalAttributesGroup.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.name = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.zoneId = parcel.readInt();
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
        stringJoiner.add("id: " + this.id);
        stringJoiner.add("attributesGroups: " + Arrays.toString(this.attributesGroups));
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("zoneId: " + this.zoneId);
        return "AudioHalProductStrategy" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioHalProductStrategy)) {
            return false;
        }
        AudioHalProductStrategy audioHalProductStrategy = (AudioHalProductStrategy) obj;
        return Objects.deepEquals(Integer.valueOf(this.id), Integer.valueOf(audioHalProductStrategy.id)) && Objects.deepEquals(this.attributesGroups, audioHalProductStrategy.attributesGroups) && Objects.deepEquals(this.name, audioHalProductStrategy.name) && Objects.deepEquals(Integer.valueOf(this.zoneId), Integer.valueOf(audioHalProductStrategy.zoneId));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.id), this.attributesGroups, this.name, Integer.valueOf(this.zoneId)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.attributesGroups);
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
