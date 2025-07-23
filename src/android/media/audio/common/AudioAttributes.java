package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioAttributes implements Parcelable {
    public static final Parcelable.Creator<AudioAttributes> CREATOR = new Parcelable.Creator<AudioAttributes>() { // from class: android.media.audio.common.AudioAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioAttributes createFromParcel(Parcel parcel) {
            AudioAttributes audioAttributes = new AudioAttributes();
            audioAttributes.readFromParcel(parcel);
            return audioAttributes;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioAttributes[] newArray(int i) {
            return new AudioAttributes[i];
        }
    };
    public String[] tags;
    public int contentType = 0;
    public int usage = 0;
    public int source = 0;
    public int flags = 0;

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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.contentType);
        parcel.writeInt(this.usage);
        parcel.writeInt(this.source);
        parcel.writeInt(this.flags);
        parcel.writeStringArray(this.tags);
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
                this.contentType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.usage = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.source = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.flags = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.tags = parcel.createStringArray();
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
        stringJoiner.add("contentType: " + this.contentType);
        stringJoiner.add("usage: " + this.usage);
        stringJoiner.add("source: " + this.source);
        stringJoiner.add("flags: " + this.flags);
        stringJoiner.add("tags: " + Arrays.toString(this.tags));
        return "AudioAttributes" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioAttributes)) {
            return false;
        }
        AudioAttributes audioAttributes = (AudioAttributes) obj;
        return Objects.deepEquals(Integer.valueOf(this.contentType), Integer.valueOf(audioAttributes.contentType)) && Objects.deepEquals(Integer.valueOf(this.usage), Integer.valueOf(audioAttributes.usage)) && Objects.deepEquals(Integer.valueOf(this.source), Integer.valueOf(audioAttributes.source)) && Objects.deepEquals(Integer.valueOf(this.flags), Integer.valueOf(audioAttributes.flags)) && Objects.deepEquals(this.tags, audioAttributes.tags);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.contentType), Integer.valueOf(this.usage), Integer.valueOf(this.source), Integer.valueOf(this.flags), this.tags).toArray());
    }
}
