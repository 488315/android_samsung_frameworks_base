package android.security;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class KeystoreArguments implements Parcelable {
    public static final Parcelable.Creator<KeystoreArguments> CREATOR = new Parcelable.Creator<KeystoreArguments>() { // from class: android.security.KeystoreArguments.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeystoreArguments createFromParcel(Parcel parcel) {
            return new KeystoreArguments(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeystoreArguments[] newArray(int i) {
            return new KeystoreArguments[i];
        }
    };
    public byte[][] args;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KeystoreArguments() {
        this.args = null;
    }

    public KeystoreArguments(byte[][] bArr) {
        this.args = bArr;
    }

    private KeystoreArguments(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte[][] bArr = this.args;
        if (bArr == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(bArr.length);
        for (byte[] bArr2 : this.args) {
            parcel.writeByteArray(bArr2);
        }
    }

    private void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        this.args = new byte[i][];
        for (int i2 = 0; i2 < i; i2++) {
            this.args[i2] = parcel.createByteArray();
        }
    }
}
