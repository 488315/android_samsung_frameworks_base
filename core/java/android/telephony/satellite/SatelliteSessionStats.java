package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public class SatelliteSessionStats implements Parcelable {
    public static final Parcelable.Creator<SatelliteSessionStats> CREATOR = new Parcelable.Creator<SatelliteSessionStats>() { // from class: android.telephony.satellite.SatelliteSessionStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSessionStats createFromParcel(Parcel in) {
            return new SatelliteSessionStats(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSessionStats[] newArray(int size) {
            return new SatelliteSessionStats[size];
        }
    };
    private int mCountOfSuccessfulUserMessages;
    private int mCountOfTimedOutUserMessagesWaitingForAck;
    private int mCountOfTimedOutUserMessagesWaitingForConnection;
    private int mCountOfUnsuccessfulUserMessages;
    private int mCountOfUserMessagesInQueueToBeSent;

    public SatelliteSessionStats(Builder builder) {
        this.mCountOfSuccessfulUserMessages = builder.mCountOfSuccessfulUserMessages;
        this.mCountOfUnsuccessfulUserMessages = builder.mCountOfUnsuccessfulUserMessages;
        this.mCountOfTimedOutUserMessagesWaitingForConnection = builder.mCountOfTimedOutUserMessagesWaitingForConnection;
        this.mCountOfTimedOutUserMessagesWaitingForAck = builder.mCountOfTimedOutUserMessagesWaitingForAck;
        this.mCountOfUserMessagesInQueueToBeSent = builder.mCountOfUserMessagesInQueueToBeSent;
    }

    private SatelliteSessionStats(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mCountOfSuccessfulUserMessages);
        out.writeInt(this.mCountOfUnsuccessfulUserMessages);
        out.writeInt(this.mCountOfTimedOutUserMessagesWaitingForConnection);
        out.writeInt(this.mCountOfTimedOutUserMessagesWaitingForAck);
        out.writeInt(this.mCountOfUserMessagesInQueueToBeSent);
    }

    public String toString() {
        return "countOfSuccessfulUserMessages:" + this.mCountOfSuccessfulUserMessages + ",countOfUnsuccessfulUserMessages:" + this.mCountOfUnsuccessfulUserMessages + ",countOfTimedOutUserMessagesWaitingForConnection:" + this.mCountOfTimedOutUserMessagesWaitingForConnection + ",countOfTimedOutUserMessagesWaitingForAck:" + this.mCountOfTimedOutUserMessagesWaitingForAck + ",countOfUserMessagesInQueueToBeSent:" + this.mCountOfUserMessagesInQueueToBeSent;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SatelliteSessionStats that = (SatelliteSessionStats) o;
        if (this.mCountOfSuccessfulUserMessages == that.mCountOfSuccessfulUserMessages && this.mCountOfUnsuccessfulUserMessages == that.mCountOfUnsuccessfulUserMessages && this.mCountOfTimedOutUserMessagesWaitingForConnection == that.mCountOfTimedOutUserMessagesWaitingForConnection && this.mCountOfTimedOutUserMessagesWaitingForAck == that.mCountOfTimedOutUserMessagesWaitingForAck && this.mCountOfUserMessagesInQueueToBeSent == that.mCountOfUserMessagesInQueueToBeSent) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mCountOfSuccessfulUserMessages), Integer.valueOf(this.mCountOfUnsuccessfulUserMessages), Integer.valueOf(this.mCountOfTimedOutUserMessagesWaitingForConnection), Integer.valueOf(this.mCountOfTimedOutUserMessagesWaitingForAck), Integer.valueOf(this.mCountOfUserMessagesInQueueToBeSent));
    }

    public int getCountOfSuccessfulUserMessages() {
        return this.mCountOfSuccessfulUserMessages;
    }

    public int getCountOfUnsuccessfulUserMessages() {
        return this.mCountOfUnsuccessfulUserMessages;
    }

    public int getCountOfTimedOutUserMessagesWaitingForConnection() {
        return this.mCountOfTimedOutUserMessagesWaitingForConnection;
    }

    public int getCountOfTimedOutUserMessagesWaitingForAck() {
        return this.mCountOfTimedOutUserMessagesWaitingForAck;
    }

    public int getCountOfUserMessagesInQueueToBeSent() {
        return this.mCountOfUserMessagesInQueueToBeSent;
    }

    private void readFromParcel(Parcel in) {
        this.mCountOfSuccessfulUserMessages = in.readInt();
        this.mCountOfUnsuccessfulUserMessages = in.readInt();
        this.mCountOfTimedOutUserMessagesWaitingForConnection = in.readInt();
        this.mCountOfTimedOutUserMessagesWaitingForAck = in.readInt();
        this.mCountOfUserMessagesInQueueToBeSent = in.readInt();
    }

    public static final class Builder {
        private int mCountOfSuccessfulUserMessages;
        private int mCountOfTimedOutUserMessagesWaitingForAck;
        private int mCountOfTimedOutUserMessagesWaitingForConnection;
        private int mCountOfUnsuccessfulUserMessages;
        private int mCountOfUserMessagesInQueueToBeSent;

        public Builder setCountOfSuccessfulUserMessages(int count) {
            this.mCountOfSuccessfulUserMessages = count;
            return this;
        }

        public Builder setCountOfUnsuccessfulUserMessages(int count) {
            this.mCountOfUnsuccessfulUserMessages = count;
            return this;
        }

        public Builder setCountOfTimedOutUserMessagesWaitingForConnection(int count) {
            this.mCountOfTimedOutUserMessagesWaitingForConnection = count;
            return this;
        }

        public Builder setCountOfTimedOutUserMessagesWaitingForAck(int count) {
            this.mCountOfTimedOutUserMessagesWaitingForAck = count;
            return this;
        }

        public Builder setCountOfUserMessagesInQueueToBeSent(int count) {
            this.mCountOfUserMessagesInQueueToBeSent = count;
            return this;
        }

        public SatelliteSessionStats build() {
            return new SatelliteSessionStats(this);
        }
    }
}
