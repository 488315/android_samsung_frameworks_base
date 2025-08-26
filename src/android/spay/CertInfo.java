package android.spay;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class CertInfo implements Parcelable {
    public static final Parcelable.Creator<CertInfo> CREATOR = new Parcelable.Creator<CertInfo>() { // from class: android.spay.CertInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CertInfo createFromParcel(Parcel parcel) {
            return new CertInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CertInfo[] newArray(int i) {
            return new CertInfo[i];
        }
    };
    private static final String TAG = "CertInfo";
    public Map<String, byte[]> mCerts;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CertInfo() {
        this.mCerts = new HashMap();
    }

    private CertInfo(Parcel parcel) {
        this.mCerts = new HashMap();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Log.d(TAG, "Writing Certificates = " + this.mCerts.size());
        parcel.writeInt(this.mCerts.size());
        for (String str : this.mCerts.keySet()) {
            Log.d(TAG, "Certificate = " + str);
            parcel.writeString(str);
            byte[] bArr = this.mCerts.get(str);
            Log.d(TAG, "certdata = " + Arrays.toString(bArr));
            if (bArr == null || bArr.length == 0) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(bArr.length);
                parcel.writeByteArray(bArr);
            }
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        Log.d(TAG, "Reading Certificates = " + i);
        for (int i2 = 0; i2 < i; i2++) {
            String string = parcel.readString();
            Log.d(TAG, "Reading Certificate = " + string);
            int i3 = parcel.readInt();
            Log.d(TAG, "Reading Certificate Len = " + i3);
            if (i3 == 0) {
                this.mCerts.put(string, null);
            } else {
                byte[] bArr = new byte[i3];
                parcel.readByteArray(bArr);
                this.mCerts.put(string, bArr);
                Log.d(TAG, "certdata = " + Arrays.toString(bArr));
            }
        }
    }
}
