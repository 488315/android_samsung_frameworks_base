package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioConfig implements Parcelable {
    public static final Parcelable.Creator<AudioConfig> CREATOR = new Parcelable.Creator<AudioConfig>() { // from class: android.media.audio.common.AudioConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioConfig createFromParcel(Parcel parcel) {
            AudioConfig audioConfig = new AudioConfig();
            audioConfig.readFromParcel(parcel);
            return audioConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioConfig[] newArray(int i) {
            return new AudioConfig[i];
        }
    };
    public AudioConfigBase base;
    public long frameCount = 0;
    public AudioOffloadInfo offloadInfo;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.base, i);
        parcel.writeTypedObject(this.offloadInfo, i);
        parcel.writeLong(this.frameCount);
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
                this.base = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.offloadInfo = (AudioOffloadInfo) parcel.readTypedObject(AudioOffloadInfo.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.frameCount = parcel.readLong();
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
        stringJoiner.add("base: " + Objects.toString(this.base));
        stringJoiner.add("offloadInfo: " + Objects.toString(this.offloadInfo));
        stringJoiner.add("frameCount: " + this.frameCount);
        return "AudioConfig" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioConfig)) {
            return false;
        }
        AudioConfig audioConfig = (AudioConfig) obj;
        return Objects.deepEquals(this.base, audioConfig.base) && Objects.deepEquals(this.offloadInfo, audioConfig.offloadInfo) && Objects.deepEquals(java.lang.Long.valueOf(this.frameCount), java.lang.Long.valueOf(audioConfig.frameCount));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.base, this.offloadInfo, java.lang.Long.valueOf(this.frameCount)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.offloadInfo) | describeContents(this.base);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
