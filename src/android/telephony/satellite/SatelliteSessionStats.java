package android.telephony.satellite;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SatelliteSessionStats implements Parcelable {
    public static final Parcelable.Creator<SatelliteSessionStats> CREATOR = new Parcelable.Creator<SatelliteSessionStats>() { // from class: android.telephony.satellite.SatelliteSessionStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSessionStats createFromParcel(Parcel parcel) {
            return new SatelliteSessionStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSessionStats[] newArray(int i) {
            return new SatelliteSessionStats[i];
        }
    };
    private Map<Integer, SatelliteSessionStats> datagramStats;
    private int mCountOfSuccessfulUserMessages;
    private int mCountOfTimedOutUserMessagesWaitingForAck;
    private int mCountOfTimedOutUserMessagesWaitingForConnection;
    private int mCountOfUnsuccessfulUserMessages;
    private int mCountOfUserMessagesInQueueToBeSent;
    private long mLastMessageLatency;
    private long mLatencyOfSuccessfulUserMessages;
    private long mMaxLatency;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SatelliteSessionStats() {
        this.datagramStats = new HashMap();
    }

    public SatelliteSessionStats(Builder builder) {
        this.mCountOfSuccessfulUserMessages = builder.mCountOfSuccessfulUserMessages;
        this.mCountOfUnsuccessfulUserMessages = builder.mCountOfUnsuccessfulUserMessages;
        this.mCountOfTimedOutUserMessagesWaitingForConnection = builder.mCountOfTimedOutUserMessagesWaitingForConnection;
        this.mCountOfTimedOutUserMessagesWaitingForAck = builder.mCountOfTimedOutUserMessagesWaitingForAck;
        this.mCountOfUserMessagesInQueueToBeSent = builder.mCountOfUserMessagesInQueueToBeSent;
        this.mLatencyOfSuccessfulUserMessages = builder.mLatencyOfSuccessfulUserMessages;
    }

    private SatelliteSessionStats(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCountOfSuccessfulUserMessages);
        parcel.writeInt(this.mCountOfUnsuccessfulUserMessages);
        parcel.writeInt(this.mCountOfTimedOutUserMessagesWaitingForConnection);
        parcel.writeInt(this.mCountOfTimedOutUserMessagesWaitingForAck);
        parcel.writeInt(this.mCountOfUserMessagesInQueueToBeSent);
        parcel.writeLong(this.mLatencyOfSuccessfulUserMessages);
        parcel.writeLong(this.mMaxLatency);
        parcel.writeLong(this.mLastMessageLatency);
        Map<Integer, SatelliteSessionStats> map = this.datagramStats;
        if (map != null && !map.isEmpty()) {
            parcel.writeInt(this.datagramStats.size());
            for (Map.Entry<Integer, SatelliteSessionStats> entry : this.datagramStats.entrySet()) {
                parcel.writeInt(entry.getKey().intValue());
                parcel.writeParcelable(entry.getValue(), i);
            }
            return;
        }
        parcel.writeInt(0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.datagramStats != null) {
            sb.append(" ====== SatelliteSessionStatsWrapper Info =============");
            for (Map.Entry<Integer, SatelliteSessionStats> entry : this.datagramStats.entrySet()) {
                Integer key = entry.getKey();
                SatelliteSessionStats value = entry.getValue();
                sb.append(ShaderAssembler.NEWLINE);
                sb.append("Key:");
                sb.append(key);
                sb.append(", SatelliteSessionStats:[");
                value.getPrintableCounters(sb);
                sb.append(",");
                sb.append(" LatencyOfSuccessfulUserMessages:");
                sb.append(value.mLatencyOfSuccessfulUserMessages);
                sb.append(",");
                sb.append(" mMaxLatency:");
                sb.append(value.mMaxLatency);
                sb.append(",");
                sb.append(" mLastMessageLatency:");
                sb.append(value.mLastMessageLatency);
                sb.append(NavigationBarInflaterView.SIZE_MOD_END);
                sb.append(ShaderAssembler.NEWLINE);
            }
            sb.append(" ============== ================== ===============");
            sb.append(ShaderAssembler.NEWLINE);
            sb.append(ShaderAssembler.NEWLINE);
        } else {
            sb.append(ShaderAssembler.NEWLINE);
            getPrintableCounters(sb);
        }
        sb.append(ShaderAssembler.NEWLINE);
        return sb.toString();
    }

    private void getPrintableCounters(StringBuilder sb) {
        sb.append("countOfSuccessfulUserMessages:");
        sb.append(this.mCountOfSuccessfulUserMessages);
        sb.append(",");
        sb.append("countOfUnsuccessfulUserMessages:");
        sb.append(this.mCountOfUnsuccessfulUserMessages);
        sb.append(",");
        sb.append("countOfTimedOutUserMessagesWaitingForConnection:");
        sb.append(this.mCountOfTimedOutUserMessagesWaitingForConnection);
        sb.append(",");
        sb.append("countOfTimedOutUserMessagesWaitingForAck:");
        sb.append(this.mCountOfTimedOutUserMessagesWaitingForAck);
        sb.append(",");
        sb.append("countOfUserMessagesInQueueToBeSent:");
        sb.append(this.mCountOfUserMessagesInQueueToBeSent);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SatelliteSessionStats satelliteSessionStats = (SatelliteSessionStats) obj;
            if (this.mCountOfSuccessfulUserMessages == satelliteSessionStats.mCountOfSuccessfulUserMessages && this.mLatencyOfSuccessfulUserMessages == satelliteSessionStats.mLatencyOfSuccessfulUserMessages && this.mCountOfUnsuccessfulUserMessages == satelliteSessionStats.mCountOfUnsuccessfulUserMessages && this.mCountOfTimedOutUserMessagesWaitingForConnection == satelliteSessionStats.mCountOfTimedOutUserMessagesWaitingForConnection && this.mCountOfTimedOutUserMessagesWaitingForAck == satelliteSessionStats.mCountOfTimedOutUserMessagesWaitingForAck && this.mCountOfUserMessagesInQueueToBeSent == satelliteSessionStats.mCountOfUserMessagesInQueueToBeSent) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mCountOfSuccessfulUserMessages), Long.valueOf(this.mLatencyOfSuccessfulUserMessages), Integer.valueOf(this.mCountOfUnsuccessfulUserMessages), Integer.valueOf(this.mCountOfTimedOutUserMessagesWaitingForConnection), Integer.valueOf(this.mCountOfTimedOutUserMessagesWaitingForAck), Integer.valueOf(this.mCountOfUserMessagesInQueueToBeSent));
    }

    public int getCountOfSuccessfulUserMessages() {
        return this.mCountOfSuccessfulUserMessages;
    }

    public void incrementSuccessfulUserMessageCount() {
        this.mCountOfSuccessfulUserMessages++;
    }

    public int getCountOfUnsuccessfulUserMessages() {
        return this.mCountOfUnsuccessfulUserMessages;
    }

    public void incrementUnsuccessfulUserMessageCount() {
        this.mCountOfUnsuccessfulUserMessages++;
    }

    public int getCountOfTimedOutUserMessagesWaitingForConnection() {
        return this.mCountOfTimedOutUserMessagesWaitingForConnection;
    }

    public void incrementTimedOutUserMessagesWaitingForConnection() {
        this.mCountOfTimedOutUserMessagesWaitingForConnection++;
    }

    public int getCountOfTimedOutUserMessagesWaitingForAck() {
        return this.mCountOfTimedOutUserMessagesWaitingForAck;
    }

    public void incrementTimedOutUserMessagesWaitingForAck() {
        this.mCountOfTimedOutUserMessagesWaitingForAck++;
    }

    public int getCountOfUserMessagesInQueueToBeSent() {
        return this.mCountOfUserMessagesInQueueToBeSent;
    }

    public void incrementUserMessagesInQueueToBeSent() {
        this.mCountOfUserMessagesInQueueToBeSent++;
    }

    public long getLatencyOfAllSuccessfulUserMessages() {
        return this.mLatencyOfSuccessfulUserMessages;
    }

    public void updateLatencyOfAllSuccessfulUserMessages(long j) {
        this.mLatencyOfSuccessfulUserMessages += j;
    }

    public void recordSuccessfulOutgoingDatagramStats(int i, long j) {
        try {
            this.datagramStats.putIfAbsent(Integer.valueOf(i), new Builder().build());
            SatelliteSessionStats satelliteSessionStats = this.datagramStats.get(Integer.valueOf(i));
            satelliteSessionStats.incrementSuccessfulUserMessageCount();
            if (satelliteSessionStats.mMaxLatency < j) {
                satelliteSessionStats.mMaxLatency = j;
            }
            satelliteSessionStats.mLastMessageLatency = j;
            satelliteSessionStats.updateLatencyOfAllSuccessfulUserMessages(j);
        } catch (Exception e) {
            Log.e("SatelliteSessionStats", "Error while recordSuccessfulOutgoingDatagramStats: " + e.getMessage());
        }
    }

    public void resetCountOfUserMessagesInQueueToBeSent() {
        Iterator<Map.Entry<Integer, SatelliteSessionStats>> it = this.datagramStats.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().mCountOfUserMessagesInQueueToBeSent = 0;
        }
    }

    public int getCountOfSuccessfulOutgoingDatagram(int i) {
        return this.datagramStats.getOrDefault(Integer.valueOf(i), new SatelliteSessionStats()).getCountOfSuccessfulUserMessages();
    }

    public long getMaxLatency() {
        return this.mMaxLatency;
    }

    public Long getLatencyOfAllSuccessfulUserMessages(int i) {
        return Long.valueOf(this.datagramStats.getOrDefault(Integer.valueOf(i), new SatelliteSessionStats()).getLatencyOfAllSuccessfulUserMessages());
    }

    public long getLastMessageLatency() {
        return this.mLastMessageLatency;
    }

    public void addCountOfUnsuccessfulUserMessages(int i, int i2) {
        try {
            this.datagramStats.putIfAbsent(Integer.valueOf(i), new Builder().build());
            SatelliteSessionStats satelliteSessionStats = this.datagramStats.get(Integer.valueOf(i));
            satelliteSessionStats.incrementUnsuccessfulUserMessageCount();
            if (i2 == 18) {
                satelliteSessionStats.incrementTimedOutUserMessagesWaitingForConnection();
            } else if (i2 == 24) {
                satelliteSessionStats.incrementTimedOutUserMessagesWaitingForAck();
            }
        } catch (Exception e) {
            Log.e("SatelliteSessionStats", "Error while addCountOfUnsuccessfulUserMessages: " + e.getMessage());
        }
    }

    public void updateCountOfUserMessagesInQueueToBeSent(int i) {
        try {
            this.datagramStats.putIfAbsent(Integer.valueOf(i), new Builder().build());
            this.datagramStats.get(Integer.valueOf(i)).incrementUserMessagesInQueueToBeSent();
        } catch (Exception e) {
            Log.e("SatelliteSessionStats", "Error while addCountOfUserMessagesInQueueToBeSent: " + e.getMessage());
        }
    }

    public int getCountOfUnsuccessfulUserMessages(int i) {
        return this.datagramStats.get(Integer.valueOf(i)).getCountOfUnsuccessfulUserMessages();
    }

    public int getCountOfTimedOutUserMessagesWaitingForConnection(int i) {
        return this.datagramStats.get(Integer.valueOf(i)).getCountOfTimedOutUserMessagesWaitingForConnection();
    }

    public int getCountOfTimedOutUserMessagesWaitingForAck(int i) {
        return this.datagramStats.get(Integer.valueOf(i)).getCountOfTimedOutUserMessagesWaitingForAck();
    }

    public int getCountOfUserMessagesInQueueToBeSent(int i) {
        return this.datagramStats.get(Integer.valueOf(i)).getCountOfUserMessagesInQueueToBeSent();
    }

    public void clear() {
        this.datagramStats.clear();
    }

    public Map<Integer, SatelliteSessionStats> getSatelliteSessionStats() {
        return this.datagramStats;
    }

    public void setSatelliteSessionStats(Map<Integer, SatelliteSessionStats> map) {
        this.datagramStats = map;
    }

    private void readFromParcel(Parcel parcel) {
        this.mCountOfSuccessfulUserMessages = parcel.readInt();
        this.mCountOfUnsuccessfulUserMessages = parcel.readInt();
        this.mCountOfTimedOutUserMessagesWaitingForConnection = parcel.readInt();
        this.mCountOfTimedOutUserMessagesWaitingForAck = parcel.readInt();
        this.mCountOfUserMessagesInQueueToBeSent = parcel.readInt();
        this.mLatencyOfSuccessfulUserMessages = parcel.readLong();
        this.mMaxLatency = parcel.readLong();
        this.mLastMessageLatency = parcel.readLong();
        int readInt = parcel.readInt();
        this.datagramStats = new HashMap();
        for (int i = 0; i < readInt; i++) {
            this.datagramStats.put(Integer.valueOf(parcel.readInt()), (SatelliteSessionStats) parcel.readParcelable(SatelliteSessionStats.class.getClassLoader()));
        }
    }

    public static final class Builder {
        private int mCountOfSuccessfulUserMessages;
        private int mCountOfTimedOutUserMessagesWaitingForAck;
        private int mCountOfTimedOutUserMessagesWaitingForConnection;
        private int mCountOfUnsuccessfulUserMessages;
        private int mCountOfUserMessagesInQueueToBeSent;
        private long mLastMessageLatency;
        private long mLatencyOfSuccessfulUserMessages;
        private long mMaxLatency;

        public Builder setCountOfSuccessfulUserMessages(int i) {
            this.mCountOfSuccessfulUserMessages = i;
            return this;
        }

        public Builder setCountOfUnsuccessfulUserMessages(int i) {
            this.mCountOfUnsuccessfulUserMessages = i;
            return this;
        }

        public Builder setCountOfTimedOutUserMessagesWaitingForConnection(int i) {
            this.mCountOfTimedOutUserMessagesWaitingForConnection = i;
            return this;
        }

        public Builder setCountOfTimedOutUserMessagesWaitingForAck(int i) {
            this.mCountOfTimedOutUserMessagesWaitingForAck = i;
            return this;
        }

        public Builder setCountOfUserMessagesInQueueToBeSent(int i) {
            this.mCountOfUserMessagesInQueueToBeSent = i;
            return this;
        }

        public Builder setLatencyOfSuccessfulUserMessages(long j) {
            this.mLatencyOfSuccessfulUserMessages = j;
            return this;
        }

        public Builder setMaxLatency(long j) {
            this.mMaxLatency = j;
            return this;
        }

        public Builder setLastLatency(long j) {
            this.mLastMessageLatency = j;
            return this;
        }

        public SatelliteSessionStats build() {
            return new SatelliteSessionStats(this);
        }
    }
}
