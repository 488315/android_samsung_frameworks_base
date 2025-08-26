package android.media.soundtrigger;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class RecognitionConfig implements Parcelable {
    public static final Parcelable.Creator<RecognitionConfig> CREATOR = new Parcelable.Creator<RecognitionConfig>() { // from class: android.media.soundtrigger.RecognitionConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionConfig createFromParcel(Parcel parcel) {
            RecognitionConfig recognitionConfig = new RecognitionConfig();
            recognitionConfig.readFromParcel(parcel);
            return recognitionConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionConfig[] newArray(int i) {
            return new RecognitionConfig[i];
        }
    };
    public byte[] data;
    public PhraseRecognitionExtra[] phraseRecognitionExtras;
    public boolean captureRequested = false;
    public int audioCapabilities = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.captureRequested);
        parcel.writeTypedArray(this.phraseRecognitionExtras, i);
        parcel.writeInt(this.audioCapabilities);
        parcel.writeByteArray(this.data);
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
                this.captureRequested = parcel.readBoolean();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.phraseRecognitionExtras = (PhraseRecognitionExtra[]) parcel.createTypedArray(PhraseRecognitionExtra.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.audioCapabilities = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.data = parcel.createByteArray();
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
        stringJoiner.add("captureRequested: " + this.captureRequested);
        stringJoiner.add("phraseRecognitionExtras: " + Arrays.toString(this.phraseRecognitionExtras));
        stringJoiner.add("audioCapabilities: " + this.audioCapabilities);
        stringJoiner.add("data: " + Arrays.toString(this.data));
        return "RecognitionConfig" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof RecognitionConfig)) {
            return false;
        }
        RecognitionConfig recognitionConfig = (RecognitionConfig) obj;
        return Objects.deepEquals(Boolean.valueOf(this.captureRequested), Boolean.valueOf(recognitionConfig.captureRequested)) && Objects.deepEquals(this.phraseRecognitionExtras, recognitionConfig.phraseRecognitionExtras) && Objects.deepEquals(Integer.valueOf(this.audioCapabilities), Integer.valueOf(recognitionConfig.audioCapabilities)) && Objects.deepEquals(this.data, recognitionConfig.data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Boolean.valueOf(this.captureRequested), this.phraseRecognitionExtras, Integer.valueOf(this.audioCapabilities), this.data).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.phraseRecognitionExtras);
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
