package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioPortMixExt implements Parcelable {
    public static final Parcelable.Creator<AudioPortMixExt> CREATOR = new Parcelable.Creator<AudioPortMixExt>() { // from class: android.media.audio.common.AudioPortMixExt.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortMixExt createFromParcel(Parcel parcel) {
            AudioPortMixExt audioPortMixExt = new AudioPortMixExt();
            audioPortMixExt.readFromParcel(parcel);
            return audioPortMixExt;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortMixExt[] newArray(int i) {
            return new AudioPortMixExt[i];
        }
    };
    public AudioPortMixExtUseCase usecase;
    public int handle = 0;
    public int maxOpenStreamCount = 0;
    public int maxActiveStreamCount = 0;
    public int recommendedMuteDurationMs = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.handle);
        parcel.writeTypedObject(this.usecase, i);
        parcel.writeInt(this.maxOpenStreamCount);
        parcel.writeInt(this.maxActiveStreamCount);
        parcel.writeInt(this.recommendedMuteDurationMs);
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
                this.handle = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.usecase = (AudioPortMixExtUseCase) parcel.readTypedObject(AudioPortMixExtUseCase.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.maxOpenStreamCount = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.maxActiveStreamCount = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.recommendedMuteDurationMs = parcel.readInt();
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
        stringJoiner.add("handle: " + this.handle);
        stringJoiner.add("usecase: " + Objects.toString(this.usecase));
        stringJoiner.add("maxOpenStreamCount: " + this.maxOpenStreamCount);
        stringJoiner.add("maxActiveStreamCount: " + this.maxActiveStreamCount);
        stringJoiner.add("recommendedMuteDurationMs: " + this.recommendedMuteDurationMs);
        return "AudioPortMixExt" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioPortMixExt)) {
            return false;
        }
        AudioPortMixExt audioPortMixExt = (AudioPortMixExt) obj;
        return Objects.deepEquals(Integer.valueOf(this.handle), Integer.valueOf(audioPortMixExt.handle)) && Objects.deepEquals(this.usecase, audioPortMixExt.usecase) && Objects.deepEquals(Integer.valueOf(this.maxOpenStreamCount), Integer.valueOf(audioPortMixExt.maxOpenStreamCount)) && Objects.deepEquals(Integer.valueOf(this.maxActiveStreamCount), Integer.valueOf(audioPortMixExt.maxActiveStreamCount)) && Objects.deepEquals(Integer.valueOf(this.recommendedMuteDurationMs), Integer.valueOf(audioPortMixExt.recommendedMuteDurationMs));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.handle), this.usecase, Integer.valueOf(this.maxOpenStreamCount), Integer.valueOf(this.maxActiveStreamCount), Integer.valueOf(this.recommendedMuteDurationMs)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.usecase);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
