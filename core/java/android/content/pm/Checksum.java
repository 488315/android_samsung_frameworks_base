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
        public Checksum[] newArray(int size) {
            return new Checksum[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Checksum createFromParcel(Parcel in) {
            return new Checksum(in);
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

    public static void writeToStream(DataOutputStream dos, Checksum checksum) throws IOException {
        dos.writeInt(checksum.getType());
        byte[] valueBytes = checksum.getValue();
        dos.writeInt(valueBytes.length);
        dos.write(valueBytes);
    }

    public static Checksum readFromStream(DataInputStream dis) throws IOException {
        int type = dis.readInt();
        byte[] valueBytes = new byte[dis.readInt()];
        dis.read(valueBytes);
        return new Checksum(type, valueBytes);
    }

    public Checksum(int type, byte[] value) {
        this.mType = type;
        AnnotationValidations.validate((Class<? extends Annotation>) Type.class, (Annotation) null, this.mType);
        this.mValue = value;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mValue);
    }

    public int getType() {
        return this.mType;
    }

    public byte[] getValue() {
        return this.mValue;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeByteArray(this.mValue);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Checksum(Parcel in) {
        int type = in.readInt();
        byte[] value = in.createByteArray();
        this.mType = type;
        AnnotationValidations.validate((Class<? extends Annotation>) Type.class, (Annotation) null, this.mType);
        this.mValue = value;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mValue);
    }

    @Deprecated
    private void __metadata() {
    }
}
