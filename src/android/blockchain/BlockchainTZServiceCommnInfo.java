package android.blockchain;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class BlockchainTZServiceCommnInfo implements Parcelable {
    public static final Parcelable.Creator<BlockchainTZServiceCommnInfo> CREATOR = new Parcelable.Creator<BlockchainTZServiceCommnInfo>() { // from class: android.blockchain.BlockchainTZServiceCommnInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockchainTZServiceCommnInfo createFromParcel(Parcel parcel) {
            return new BlockchainTZServiceCommnInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockchainTZServiceCommnInfo[] newArray(int i) {
            return new BlockchainTZServiceCommnInfo[i];
        }
    };
    public int mServiceVersion;
    public Map<Integer, IBinder> mTAs;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BlockchainTZServiceCommnInfo() {
        this.mTAs = new HashMap();
    }

    private BlockchainTZServiceCommnInfo(Parcel parcel) {
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
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.mTAs.put(Integer.valueOf(parcel.readInt()), parcel.readStrongBinder());
        }
    }
}
