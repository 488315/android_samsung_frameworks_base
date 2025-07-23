package android.content.pm;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class Checksum implements Parcelable {
    public static final Parcelable.Creator<Checksum> CREATOR = new Parcelable.Creator<Checksum>() { // from class: android.content.pm.Checksum.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Checksum[] newArray(int i) {
            return new Checksum[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Checksum createFromParcel(Parcel parcel) {
            return new Checksum(parcel);
        }
    };
    public static final int MAX_CHECKSUM_SIZE_BYTES = 64;
    public static final int TYPE_PARTIAL_MERKLE_ROOT_1M_SHA256 = 32;
    public static final int TYPE_PARTIAL_MERKLE_ROOT_1M_SHA512 = 64;

    @Deprecated
    public static final int TYPE_WHOLE_MD5 = 2;
    public static final int TYPE_WHOLE_MERKLE_ROOT_4K_SHA256 = 1;

    @Deprecated
    public static final int TYPE_WHOLE_SHA1 = 4;

    @Deprecated
    public static final int TYPE_WHOLE_SHA256 = 8;

    @Deprecated
    public static final int TYPE_WHOLE_SHA512 = 16;
    private final int mType;
    private final byte[] mValue;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TypeMask {
    }

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static void writeToStream(DataOutputStream dataOutputStream, Checksum checksum) throws IOException {
        dataOutputStream.writeInt(checksum.getType());
        byte[] value = checksum.getValue();
        dataOutputStream.writeInt(value.length);
        dataOutputStream.write(value);
    }

    public static Checksum readFromStream(DataInputStream dataInputStream) throws IOException {
        int readInt = dataInputStream.readInt();
        byte[] bArr = new byte[dataInputStream.readInt()];
        dataInputStream.read(bArr);
        return new Checksum(readInt, bArr);
    }

    public Checksum(int i, byte[] bArr) {
        this.mType = i;
        AnnotationValidations.validate((Class<? extends Annotation>) Type.class, (Annotation) null, i);
        this.mValue = bArr;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArr);
    }

    public int getType() {
        return this.mType;
    }

    public byte[] getValue() {
        return this.mValue;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeByteArray(this.mValue);
    }

    Checksum(Parcel parcel) {
        int readInt = parcel.readInt();
        byte[] createByteArray = parcel.createByteArray();
        this.mType = readInt;
        AnnotationValidations.validate((Class<? extends Annotation>) Type.class, (Annotation) null, readInt);
        this.mValue = createByteArray;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) createByteArray);
    }
}
