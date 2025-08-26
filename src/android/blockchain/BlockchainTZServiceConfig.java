package android.blockchain;

import android.blockchain.IBlockchainClient;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class BlockchainTZServiceConfig implements Parcelable {
    private static final String QSEE_UNKNOWN_PROCESS = "unknown";
    private static final String QSEE_UNKNOWN_ROOT = "unknown";
    private static final String TBASE_UNKNOWN_PROCESS = "ffffffff000000000000000000000000";
    private static final String TBASE_UNKNOWN_ROOT = "0";
    private static final String UNKNOWN_TA_TECHNOLOGY = "unknown";
    public IBinder mClient;
    public Map<Integer, TAConfig> mTAConfigs;
    private static final boolean bQC = Build.BOARD.matches("(?i)(msm[a-z0-9]*)|(sdm[a-z0-9]*)");
    public static final Parcelable.Creator<BlockchainTZServiceConfig> CREATOR = new Parcelable.Creator<BlockchainTZServiceConfig>() { // from class: android.blockchain.BlockchainTZServiceConfig.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockchainTZServiceConfig createFromParcel(Parcel parcel) {
            return new BlockchainTZServiceConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlockchainTZServiceConfig[] newArray(int i) {
            return new BlockchainTZServiceConfig[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class TAConfig {
        public int maxRecvRespSize;
        public int maxSendCmdSize;
        public String processName;
        public String rootName;
        public String taTechnology;

        public TAConfig(int i, int i2) {
            this.taTechnology = "unknown";
            this.rootName = BlockchainTZServiceConfig.bQC ? "unknown" : "0";
            this.processName = BlockchainTZServiceConfig.bQC ? "unknown" : BlockchainTZServiceConfig.TBASE_UNKNOWN_PROCESS;
            this.maxSendCmdSize = i;
            this.maxRecvRespSize = i2;
        }

        public TAConfig(String str, String str2, String str3, int i, int i2) {
            this.taTechnology = str;
            this.rootName = str2;
            this.processName = str3;
            this.maxSendCmdSize = i;
            this.maxRecvRespSize = i2;
        }
    }

    public void addTAConfig(int i, TAConfig tAConfig) {
        this.mTAConfigs.put(Integer.valueOf(i), tAConfig);
    }

    public void removeTAConfig(int i) {
        this.mTAConfigs.remove(Integer.valueOf(i));
    }

    public TAConfig getTAConfig(int i) {
        return this.mTAConfigs.get(Integer.valueOf(i));
    }

    public BlockchainTZServiceConfig() {
        this.mClient = new IBlockchainClient.Stub(this) { // from class: android.blockchain.BlockchainTZServiceConfig.1
        };
        this.mTAConfigs = new HashMap();
    }

    private BlockchainTZServiceConfig(Parcel parcel) {
        this.mClient = new IBlockchainClient.Stub(this) { // from class: android.blockchain.BlockchainTZServiceConfig.1
        };
        this.mTAConfigs = new HashMap();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mClient);
        parcel.writeInt(this.mTAConfigs.size());
        for (Integer num : this.mTAConfigs.keySet()) {
            parcel.writeInt(num.intValue());
            parcel.writeString(this.mTAConfigs.get(num).taTechnology);
            parcel.writeString(this.mTAConfigs.get(num).rootName);
            parcel.writeString(this.mTAConfigs.get(num).processName);
            parcel.writeInt(this.mTAConfigs.get(num).maxSendCmdSize);
            parcel.writeInt(this.mTAConfigs.get(num).maxRecvRespSize);
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.mClient = parcel.readStrongBinder();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.mTAConfigs.put(Integer.valueOf(parcel.readInt()), new TAConfig(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt()));
        }
    }
}
