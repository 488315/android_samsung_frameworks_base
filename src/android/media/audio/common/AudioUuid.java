package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioUuid implements Parcelable {
    public static final Parcelable.Creator<AudioUuid> CREATOR = new Parcelable.Creator<AudioUuid>() { // from class: android.media.audio.common.AudioUuid.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioUuid createFromParcel(Parcel parcel) {
            AudioUuid audioUuid = new AudioUuid();
            audioUuid.readFromParcel(parcel);
            return audioUuid;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioUuid[] newArray(int i) {
            return new AudioUuid[i];
        }
    };
    public byte[] node;
    public int timeLow = 0;
    public int timeMid = 0;
    public int timeHiAndVersion = 0;
    public int clockSeq = 0;

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
        parcel.writeInt(this.timeLow);
        parcel.writeInt(this.timeMid);
        parcel.writeInt(this.timeHiAndVersion);
        parcel.writeInt(this.clockSeq);
        parcel.writeByteArray(this.node);
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
                this.timeLow = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.timeMid = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.timeHiAndVersion = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.clockSeq = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.node = parcel.createByteArray();
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
        stringJoiner.add("timeLow: " + this.timeLow);
        stringJoiner.add("timeMid: " + this.timeMid);
        stringJoiner.add("timeHiAndVersion: " + this.timeHiAndVersion);
        stringJoiner.add("clockSeq: " + this.clockSeq);
        stringJoiner.add("node: " + Arrays.toString(this.node));
        return "AudioUuid" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioUuid)) {
            return false;
        }
        AudioUuid audioUuid = (AudioUuid) obj;
        return Objects.deepEquals(Integer.valueOf(this.timeLow), Integer.valueOf(audioUuid.timeLow)) && Objects.deepEquals(Integer.valueOf(this.timeMid), Integer.valueOf(audioUuid.timeMid)) && Objects.deepEquals(Integer.valueOf(this.timeHiAndVersion), Integer.valueOf(audioUuid.timeHiAndVersion)) && Objects.deepEquals(Integer.valueOf(this.clockSeq), Integer.valueOf(audioUuid.clockSeq)) && Objects.deepEquals(this.node, audioUuid.node);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.timeLow), Integer.valueOf(this.timeMid), Integer.valueOf(this.timeHiAndVersion), Integer.valueOf(this.clockSeq), this.node).toArray());
    }
}
