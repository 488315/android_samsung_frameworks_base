package android.spay;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class PaymentTZServiceCommnInfo implements Parcelable {
    public static final Parcelable.Creator<PaymentTZServiceCommnInfo> CREATOR = new Parcelable.Creator<PaymentTZServiceCommnInfo>() { // from class: android.spay.PaymentTZServiceCommnInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PaymentTZServiceCommnInfo createFromParcel(Parcel parcel) {
            return new PaymentTZServiceCommnInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PaymentTZServiceCommnInfo[] newArray(int i) {
            return new PaymentTZServiceCommnInfo[i];
        }
    };
    public int mServiceVersion;
    public Map<Integer, IBinder> mTAs;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PaymentTZServiceCommnInfo() {
        this.mTAs = new HashMap();
    }

    private PaymentTZServiceCommnInfo(Parcel parcel) {
        this.mTAs = new HashMap();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mServiceVersion);
        parcel.writeInt(this.mTAs.size());
        for (Integer num : this.mTAs.keySet()) {
            parcel.writeInt(num.intValue());
            parcel.writeStrongBinder(this.mTAs.get(num));
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.mServiceVersion = parcel.readInt();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mTAs.put(Integer.valueOf(parcel.readInt()), parcel.readStrongBinder());
        }
    }
}
