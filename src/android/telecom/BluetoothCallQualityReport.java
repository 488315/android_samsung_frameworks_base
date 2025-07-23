package android.telecom;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class BluetoothCallQualityReport implements Parcelable {
    public static final Parcelable.Creator<BluetoothCallQualityReport> CREATOR = new Parcelable.Creator<BluetoothCallQualityReport>() { // from class: android.telecom.BluetoothCallQualityReport.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothCallQualityReport createFromParcel(Parcel parcel) {
            return new BluetoothCallQualityReport(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothCallQualityReport[] newArray(int i) {
            return new BluetoothCallQualityReport[i];
        }
    };
    public static final String EVENT_BLUETOOTH_CALL_QUALITY_REPORT = "android.telecom.event.BLUETOOTH_CALL_QUALITY_REPORT";
    public static final String EXTRA_BLUETOOTH_CALL_QUALITY_REPORT = "android.telecom.extra.BLUETOOTH_CALL_QUALITY_REPORT";
    private final boolean mChoppyVoice;
    private final int mNegativeAcknowledgementCount;
    private final int mPacketsNotReceivedCount;
    private final int mRetransmittedPacketsCount;
    private final int mRssiDbm;
    private final long mSentTimestampMillis;
    private final int mSnrDb;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getSentTimestampMillis() {
        return this.mSentTimestampMillis;
    }

    public boolean isChoppyVoice() {
        return this.mChoppyVoice;
    }

    public int getRssiDbm() {
        return this.mRssiDbm;
    }

    public int getSnrDb() {
        return this.mSnrDb;
    }

    public int getRetransmittedPacketsCount() {
        return this.mRetransmittedPacketsCount;
    }

    public int getPacketsNotReceivedCount() {
        return this.mPacketsNotReceivedCount;
    }

    public int getNegativeAcknowledgementCount() {
        return this.mNegativeAcknowledgementCount;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mSentTimestampMillis);
        parcel.writeBoolean(this.mChoppyVoice);
        parcel.writeInt(this.mRssiDbm);
        parcel.writeInt(this.mSnrDb);
        parcel.writeInt(this.mRetransmittedPacketsCount);
        parcel.writeInt(this.mPacketsNotReceivedCount);
        parcel.writeInt(this.mNegativeAcknowledgementCount);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            BluetoothCallQualityReport bluetoothCallQualityReport = (BluetoothCallQualityReport) obj;
            if (this.mSentTimestampMillis == bluetoothCallQualityReport.mSentTimestampMillis && this.mChoppyVoice == bluetoothCallQualityReport.mChoppyVoice && this.mRssiDbm == bluetoothCallQualityReport.mRssiDbm && this.mSnrDb == bluetoothCallQualityReport.mSnrDb && this.mRetransmittedPacketsCount == bluetoothCallQualityReport.mRetransmittedPacketsCount && this.mPacketsNotReceivedCount == bluetoothCallQualityReport.mPacketsNotReceivedCount && this.mNegativeAcknowledgementCount == bluetoothCallQualityReport.mNegativeAcknowledgementCount) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mSentTimestampMillis), Boolean.valueOf(this.mChoppyVoice), Integer.valueOf(this.mRssiDbm), Integer.valueOf(this.mSnrDb), Integer.valueOf(this.mRetransmittedPacketsCount), Integer.valueOf(this.mPacketsNotReceivedCount), Integer.valueOf(this.mNegativeAcknowledgementCount));
    }

    public static final class Builder {
        private boolean mChoppyVoice;
        private int mNegativeAcknowledgementCount;
        private int mPacketsNotReceivedCount;
        private int mRetransmittedPacketsCount;
        private int mRssiDbm;
        private long mSentTimestampMillis;
        private int mSnrDb;

        public Builder setSentTimestampMillis(long j) {
            this.mSentTimestampMillis = j;
            return this;
        }

        public Builder setChoppyVoice(boolean z) {
            this.mChoppyVoice = z;
            return this;
        }

        public Builder setRssiDbm(int i) {
            this.mRssiDbm = i;
            return this;
        }

        public Builder setSnrDb(int i) {
            this.mSnrDb = i;
            return this;
        }

        public Builder setRetransmittedPacketsCount(int i) {
            this.mRetransmittedPacketsCount = i;
            return this;
        }

        public Builder setPacketsNotReceivedCount(int i) {
            this.mPacketsNotReceivedCount = i;
            return this;
        }

        public Builder setNegativeAcknowledgementCount(int i) {
            this.mNegativeAcknowledgementCount = i;
            return this;
        }

        public BluetoothCallQualityReport build() {
            return new BluetoothCallQualityReport(this);
        }
    }

    private BluetoothCallQualityReport(Parcel parcel) {
        this.mSentTimestampMillis = parcel.readLong();
        this.mChoppyVoice = parcel.readBoolean();
        this.mRssiDbm = parcel.readInt();
        this.mSnrDb = parcel.readInt();
        this.mRetransmittedPacketsCount = parcel.readInt();
        this.mPacketsNotReceivedCount = parcel.readInt();
        this.mNegativeAcknowledgementCount = parcel.readInt();
    }

    private BluetoothCallQualityReport(Builder builder) {
        this.mSentTimestampMillis = builder.mSentTimestampMillis;
        this.mChoppyVoice = builder.mChoppyVoice;
        this.mRssiDbm = builder.mRssiDbm;
        this.mSnrDb = builder.mSnrDb;
        this.mRetransmittedPacketsCount = builder.mRetransmittedPacketsCount;
        this.mPacketsNotReceivedCount = builder.mPacketsNotReceivedCount;
        this.mNegativeAcknowledgementCount = builder.mNegativeAcknowledgementCount;
    }
}
