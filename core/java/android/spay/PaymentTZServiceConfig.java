package android.spay;

import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class PaymentTZServiceConfig implements Parcelable {
    private static final String QSEE_UNKNOWN_PROCESS = "unknown";
    private static final String QSEE_UNKNOWN_ROOT = "unknown";
    private static final String TBASE_UNKNOWN_PROCESS = "ffffffff000000000000000000000000";
    private static final String TBASE_UNKNOWN_ROOT = "0";
    private static final String UNKNOWN_TA_TECHNOLOGY = "unknown";
    public IBinder mClient;
    public Map<Integer, TAConfig> mTAConfigs;
    private static final boolean bQC = Build.BOARD.matches("(?i)(msm[a-z0-9]*)|(sdm[a-z0-9]*)");
    public static final Parcelable.Creator<PaymentTZServiceConfig> CREATOR =
            new Parcelable.Creator<
                    PaymentTZServiceConfig>() { // from class: android.spay.PaymentTZServiceConfig.2
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PaymentTZServiceConfig createFromParcel(Parcel in) {
                    return new PaymentTZServiceConfig(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PaymentTZServiceConfig[] newArray(int size) {
                    return new PaymentTZServiceConfig[size];
                }
            };

    public static class TAConfig {
        public int maxRecvRespSize;
        public int maxSendCmdSize;
        public String processName;
        public String rootName;
        public String taTechnology;

        public TAConfig(int sendsize, int recvsize) {
            this.taTechnology = "unknown";
            this.rootName = PaymentTZServiceConfig.bQC ? "unknown" : "0";
            this.processName =
                    PaymentTZServiceConfig.bQC
                            ? "unknown"
                            : PaymentTZServiceConfig.TBASE_UNKNOWN_PROCESS;
            this.maxSendCmdSize = sendsize;
            this.maxRecvRespSize = recvsize;
        }

        public TAConfig(
                String _taTechnology, String root, String process, int sendsize, int recvsize) {
            this.taTechnology = _taTechnology;
            this.rootName = root;
            this.processName = process;
            this.maxSendCmdSize = sendsize;
            this.maxRecvRespSize = recvsize;
        }
    }

    public void addTAConfig(int taId, TAConfig config) {
        this.mTAConfigs.put(Integer.valueOf(taId), config);
    }

    public void removeTAConfig(int taId) {
        this.mTAConfigs.remove(Integer.valueOf(taId));
    }

    public TAConfig getTAConfig(int taId) {
        return this.mTAConfigs.get(Integer.valueOf(taId));
    }

    public PaymentTZServiceConfig() {
        this.mClient =
                new IPaymentClient.Stub() { // from class: android.spay.PaymentTZServiceConfig.1
                };
        this.mTAConfigs = new HashMap();
    }

    private PaymentTZServiceConfig(Parcel in) {
        this.mClient =
                new IPaymentClient.Stub() { // from class: android.spay.PaymentTZServiceConfig.1
                };
        this.mTAConfigs = new HashMap();
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        out.writeStrongBinder(this.mClient);
        out.writeInt(this.mTAConfigs.size());
        for (Integer s : this.mTAConfigs.keySet()) {
            out.writeInt(s.intValue());
            out.writeString(this.mTAConfigs.get(s).taTechnology);
            out.writeString(this.mTAConfigs.get(s).rootName);
            out.writeString(this.mTAConfigs.get(s).processName);
            out.writeInt(this.mTAConfigs.get(s).maxSendCmdSize);
            out.writeInt(this.mTAConfigs.get(s).maxRecvRespSize);
        }
    }

    public void readFromParcel(Parcel in) {
        this.mClient = in.readStrongBinder();
        int count = in.readInt();
        for (int i = 0; i < count; i++) {
            this.mTAConfigs.put(
                    Integer.valueOf(in.readInt()),
                    new TAConfig(
                            in.readString(),
                            in.readString(),
                            in.readString(),
                            in.readInt(),
                            in.readInt()));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
