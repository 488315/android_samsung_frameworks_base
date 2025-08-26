package android.security.keymaster;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class KeymasterCertificateChain implements Parcelable {
    public static final Parcelable.Creator<KeymasterCertificateChain> CREATOR = new Parcelable.Creator<KeymasterCertificateChain>() { // from class: android.security.keymaster.KeymasterCertificateChain.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeymasterCertificateChain createFromParcel(Parcel parcel) {
            return new KeymasterCertificateChain(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeymasterCertificateChain[] newArray(int i) {
            return new KeymasterCertificateChain[i];
        }
    };
    private List<byte[]> mCertificates;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KeymasterCertificateChain() {
        this.mCertificates = null;
    }

    public KeymasterCertificateChain(List<byte[]> list) {
        this.mCertificates = list;
    }

    private KeymasterCertificateChain(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void shallowCopyFrom(KeymasterCertificateChain keymasterCertificateChain) {
        this.mCertificates = keymasterCertificateChain.mCertificates;
    }

    public List<byte[]> getCertificates() {
        return this.mCertificates;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        List<byte[]> list = this.mCertificates;
        if (list == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(list.size());
        Iterator<byte[]> it = this.mCertificates.iterator();
        while (it.hasNext()) {
            parcel.writeByteArray(it.next());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        this.mCertificates = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            this.mCertificates.add(parcel.createByteArray());
        }
    }
}
