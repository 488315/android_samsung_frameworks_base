package android.view.displayhash;

import android.annotation.CurrentTimeMillisLong;
import android.annotation.NonNull;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;

/* loaded from: classes4.dex */
public final class VerifiedDisplayHash implements Parcelable {
    public static final Parcelable.Creator<VerifiedDisplayHash> CREATOR = new Parcelable.Creator<VerifiedDisplayHash>() { // from class: android.view.displayhash.VerifiedDisplayHash.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifiedDisplayHash[] newArray(int i) {
            return new VerifiedDisplayHash[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifiedDisplayHash createFromParcel(Parcel parcel) {
            return new VerifiedDisplayHash(parcel);
        }
    };
    private final Rect mBoundsInWindow;
    private final String mHashAlgorithm;
    private final byte[] mImageHash;
    private final long mTimeMillis;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private String imageHashToString() {
        return byteArrayToString(this.mImageHash);
    }

    private String byteArrayToString(byte[] bArr) {
        if (bArr == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        int length = bArr.length - 1;
        if (length == -1) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        int i = 0;
        while (true) {
            sb.append(String.format("%02X", Integer.valueOf(bArr[i] & 255)));
            if (i == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            i++;
        }
    }

    public VerifiedDisplayHash(long j, Rect rect, String str, byte[] bArr) {
        this.mTimeMillis = j;
        AnnotationValidations.validate((Class<? extends Annotation>) CurrentTimeMillisLong.class, (Annotation) null, j);
        this.mBoundsInWindow = rect;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) rect);
        this.mHashAlgorithm = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mImageHash = bArr;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArr);
    }

    public long getTimeMillis() {
        return this.mTimeMillis;
    }

    public Rect getBoundsInWindow() {
        return this.mBoundsInWindow;
    }

    public String getHashAlgorithm() {
        return this.mHashAlgorithm;
    }

    public byte[] getImageHash() {
        return this.mImageHash;
    }

    public String toString() {
        return "VerifiedDisplayHash { timeMillis = " + this.mTimeMillis + ", boundsInWindow = " + this.mBoundsInWindow + ", hashAlgorithm = " + this.mHashAlgorithm + ", imageHash = " + imageHashToString() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mTimeMillis);
        parcel.writeTypedObject(this.mBoundsInWindow, i);
        parcel.writeString(this.mHashAlgorithm);
        parcel.writeByteArray(this.mImageHash);
    }

    VerifiedDisplayHash(Parcel parcel) {
        long j = parcel.readLong();
        Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
        String string = parcel.readString();
        byte[] bArrCreateByteArray = parcel.createByteArray();
        this.mTimeMillis = j;
        AnnotationValidations.validate((Class<? extends Annotation>) CurrentTimeMillisLong.class, (Annotation) null, j);
        this.mBoundsInWindow = rect;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) rect);
        this.mHashAlgorithm = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mImageHash = bArrCreateByteArray;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArrCreateByteArray);
    }
}
