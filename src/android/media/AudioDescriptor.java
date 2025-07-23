package android.media;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public class AudioDescriptor implements Parcelable {
    public static final Parcelable.Creator<AudioDescriptor> CREATOR = new Parcelable.Creator<AudioDescriptor>() { // from class: android.media.AudioDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioDescriptor createFromParcel(Parcel parcel) {
            return new AudioDescriptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioDescriptor[] newArray(int i) {
            return new AudioDescriptor[i];
        }
    };
    public static final int STANDARD_EDID = 1;
    public static final int STANDARD_NONE = 0;
    public static final int STANDARD_SADB = 2;
    public static final int STANDARD_VSADB = 3;
    private final byte[] mDescriptor;
    private final int mEncapsulationType;
    private final int mStandard;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioDescriptorStandard {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public AudioDescriptor(int i, int i2, byte[] bArr) {
        this.mStandard = i;
        this.mEncapsulationType = i2;
        this.mDescriptor = bArr;
    }

    public int getStandard() {
        return this.mStandard;
    }

    public byte[] getDescriptor() {
        return this.mDescriptor;
    }

    public int getEncapsulationType() {
        return this.mEncapsulationType;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mStandard), Integer.valueOf(this.mEncapsulationType), Integer.valueOf(Arrays.hashCode(this.mDescriptor)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AudioDescriptor audioDescriptor = (AudioDescriptor) obj;
            if (this.mStandard == audioDescriptor.mStandard && this.mEncapsulationType == audioDescriptor.mEncapsulationType && Arrays.equals(this.mDescriptor, audioDescriptor.mDescriptor)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("standard=" + this.mStandard);
        sb.append(", encapsulation type=" + this.mEncapsulationType);
        byte[] bArr = this.mDescriptor;
        if (bArr != null && bArr.length > 0) {
            sb.append(", descriptor=");
            sb.append(Arrays.toString(this.mDescriptor));
        }
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStandard);
        parcel.writeInt(this.mEncapsulationType);
        parcel.writeByteArray(this.mDescriptor);
    }

    private AudioDescriptor(Parcel parcel) {
        this.mStandard = parcel.readInt();
        this.mEncapsulationType = parcel.readInt();
        this.mDescriptor = parcel.createByteArray();
    }
}
