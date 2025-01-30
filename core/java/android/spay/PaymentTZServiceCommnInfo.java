package android.spay;

import android.p009os.IBinder;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class PaymentTZServiceCommnInfo implements Parcelable {
    public static final Parcelable.Creator<PaymentTZServiceCommnInfo> CREATOR = new Parcelable.Creator<PaymentTZServiceCommnInfo>() { // from class: android.spay.PaymentTZServiceCommnInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PaymentTZServiceCommnInfo createFromParcel(Parcel in) {
            return new PaymentTZServiceCommnInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PaymentTZServiceCommnInfo[] newArray(int size) {
            return new PaymentTZServiceCommnInfo[size];
        }
    };
    public int mServiceVersion;
    public Map<Integer, IBinder> mTAs;

    public PaymentTZServiceCommnInfo() {
        this.mTAs = new HashMap();
    }

    private PaymentTZServiceCommnInfo(Parcel in) {
        this.mTAs = new HashMap();
        readFromParcel(in);
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        out.writeInt(this.mServiceVersion);
        out.writeInt(this.mTAs.size());
        for (Integer s : this.mTAs.keySet()) {
            out.writeInt(s.intValue());
            out.writeStrongBinder(this.mTAs.get(s));
        }
    }

    public void readFromParcel(Parcel in) {
        this.mServiceVersion = in.readInt();
        int count = in.readInt();
        for (int i = 0; i < count; i++) {
            this.mTAs.put(Integer.valueOf(in.readInt()), in.readStrongBinder());
        }
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }
}
