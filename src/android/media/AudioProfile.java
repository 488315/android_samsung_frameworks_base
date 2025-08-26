package android.media;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class AudioProfile implements Parcelable {
    public static final int AUDIO_ENCAPSULATION_TYPE_IEC61937 = 1;
    public static final int AUDIO_ENCAPSULATION_TYPE_NONE = 0;
    public static final int AUDIO_ENCAPSULATION_TYPE_PCM = 2;
    public static final Parcelable.Creator<AudioProfile> CREATOR = new Parcelable.Creator<AudioProfile>() { // from class: android.media.AudioProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioProfile createFromParcel(Parcel parcel) {
            return new AudioProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioProfile[] newArray(int i) {
            return new AudioProfile[i];
        }
    };
    private final int[] mChannelIndexMasks;
    private final int[] mChannelMasks;
    private final int mEncapsulationType;
    private final int mFormat;
    private final int[] mSamplingRates;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EncapsulationType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public AudioProfile(int i, int[] iArr, int[] iArr2, int[] iArr3, int i2) {
        this.mFormat = i;
        this.mSamplingRates = iArr;
        this.mChannelMasks = iArr2;
        this.mChannelIndexMasks = iArr3;
        this.mEncapsulationType = i2;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int[] getChannelMasks() {
        return this.mChannelMasks;
    }

    public int[] getChannelIndexMasks() {
        return this.mChannelIndexMasks;
    }

    public int[] getSampleRates() {
        return this.mSamplingRates;
    }

    public int getEncapsulationType() {
        return this.mEncapsulationType;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mFormat), Integer.valueOf(Arrays.hashCode(this.mSamplingRates)), Integer.valueOf(Arrays.hashCode(this.mChannelMasks)), Integer.valueOf(Arrays.hashCode(this.mChannelIndexMasks)), Integer.valueOf(this.mEncapsulationType));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AudioProfile audioProfile = (AudioProfile) obj;
            if (this.mFormat == audioProfile.mFormat && hasIdenticalElements(this.mSamplingRates, audioProfile.mSamplingRates) && hasIdenticalElements(this.mChannelMasks, audioProfile.mChannelMasks) && hasIdenticalElements(this.mChannelIndexMasks, audioProfile.mChannelIndexMasks) && this.mEncapsulationType == audioProfile.mEncapsulationType) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append(AudioFormat.toLogFriendlyEncoding(this.mFormat));
        int[] iArr = this.mSamplingRates;
        if (iArr != null && iArr.length > 0) {
            sb.append(", sampling rates=");
            sb.append(Arrays.toString(this.mSamplingRates));
        }
        int[] iArr2 = this.mChannelMasks;
        if (iArr2 != null && iArr2.length > 0) {
            sb.append(", channel masks=");
            sb.append(toHexString(this.mChannelMasks));
        }
        int[] iArr3 = this.mChannelIndexMasks;
        if (iArr3 != null && iArr3.length > 0) {
            sb.append(", channel index masks=");
            sb.append(Arrays.toString(this.mChannelIndexMasks));
        }
        sb.append(", encapsulation type=" + this.mEncapsulationType);
        sb.append("}");
        return sb.toString();
    }

    private static String toHexString(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return "";
        }
        return (String) Arrays.stream(iArr).mapToObj(new IntFunction() { // from class: android.media.AudioProfile$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return String.format("0x%02X", Integer.valueOf(i));
            }
        }).collect(Collectors.joining(", "));
    }

    private static boolean hasIdenticalElements(int[] iArr, int[] iArr2) {
        int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length);
        Arrays.sort(iArrCopyOf);
        int[] iArrCopyOf2 = Arrays.copyOf(iArr2, iArr2.length);
        Arrays.sort(iArrCopyOf2);
        return Arrays.equals(iArrCopyOf, iArrCopyOf2);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mFormat);
        parcel.writeIntArray(this.mSamplingRates);
        parcel.writeIntArray(this.mChannelMasks);
        parcel.writeIntArray(this.mChannelIndexMasks);
        parcel.writeInt(this.mEncapsulationType);
    }

    private AudioProfile(Parcel parcel) {
        this.mFormat = parcel.readInt();
        this.mSamplingRates = parcel.createIntArray();
        this.mChannelMasks = parcel.createIntArray();
        this.mChannelIndexMasks = parcel.createIntArray();
        this.mEncapsulationType = parcel.readInt();
    }
}
