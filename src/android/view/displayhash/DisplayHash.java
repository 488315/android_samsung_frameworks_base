package android.view.displayhash;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes4.dex */
public final class DisplayHash implements Parcelable {
    public static final Parcelable.Creator<DisplayHash> CREATOR = new Parcelable.Creator<DisplayHash>() { // from class: android.view.displayhash.DisplayHash.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayHash[] newArray(int i) {
            return new DisplayHash[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayHash createFromParcel(Parcel parcel) {
            return new DisplayHash(parcel);
        }
    };
    private final Rect mBoundsInWindow;
    private final String mHashAlgorithm;
    private final byte[] mHmac;
    private final byte[] mImageHash;
    private final long mTimeMillis;

    @Override // android.os.Parcelable
    @SystemApi
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public DisplayHash(long j, Rect rect, String str, byte[] bArr, byte[] bArr2) {
        this.mTimeMillis = j;
        this.mBoundsInWindow = rect;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) rect);
        this.mHashAlgorithm = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mImageHash = bArr;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArr);
        this.mHmac = bArr2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArr2);
    }

    @SystemApi
    public long getTimeMillis() {
        return this.mTimeMillis;
    }

    @SystemApi
    public Rect getBoundsInWindow() {
        return this.mBoundsInWindow;
    }

    @SystemApi
    public String getHashAlgorithm() {
        return this.mHashAlgorithm;
    }

    @SystemApi
    public byte[] getImageHash() {
        return this.mImageHash;
    }

    @SystemApi
    public byte[] getHmac() {
        return this.mHmac;
    }

    public String toString() {
        return "DisplayHash { timeMillis = " + this.mTimeMillis + ", boundsInWindow = " + this.mBoundsInWindow + ", hashAlgorithm = " + this.mHashAlgorithm + ", imageHash = " + byteArrayToString(this.mImageHash) + ", hmac = " + byteArrayToString(this.mHmac) + " }";
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

    @Override // android.os.Parcelable
    @SystemApi
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mTimeMillis);
        parcel.writeTypedObject(this.mBoundsInWindow, i);
        parcel.writeString(this.mHashAlgorithm);
        parcel.writeByteArray(this.mImageHash);
        parcel.writeByteArray(this.mHmac);
    }

    private DisplayHash(Parcel parcel) {
        this.mTimeMillis = parcel.readLong();
        Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
        String string = parcel.readString();
        byte[] bArrCreateByteArray = parcel.createByteArray();
        byte[] bArrCreateByteArray2 = parcel.createByteArray();
        this.mBoundsInWindow = rect;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) rect);
        this.mHashAlgorithm = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mImageHash = bArrCreateByteArray;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArrCreateByteArray);
        this.mHmac = bArrCreateByteArray2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArrCreateByteArray2);
    }
}
