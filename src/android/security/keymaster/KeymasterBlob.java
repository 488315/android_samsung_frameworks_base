package android.security.keymaster;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class KeymasterBlob implements Parcelable {
    public static final Parcelable.Creator<KeymasterBlob> CREATOR = new Parcelable.Creator<KeymasterBlob>() { // from class: android.security.keymaster.KeymasterBlob.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeymasterBlob createFromParcel(Parcel parcel) {
            return new KeymasterBlob(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeymasterBlob[] newArray(int i) {
            return new KeymasterBlob[i];
        }
    };
    public byte[] blob;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KeymasterBlob(byte[] bArr) {
        this.blob = bArr;
    }

    protected KeymasterBlob(Parcel parcel) {
        this.blob = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(this.blob);
    }
}
