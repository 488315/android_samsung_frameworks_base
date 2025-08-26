package android.hardware.camera2.impl;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class CaptureResultExtras implements Parcelable {
    public static final Parcelable.Creator<CaptureResultExtras> CREATOR = new Parcelable.Creator<CaptureResultExtras>() { // from class: android.hardware.camera2.impl.CaptureResultExtras.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CaptureResultExtras createFromParcel(Parcel parcel) {
            return new CaptureResultExtras(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CaptureResultExtras[] newArray(int i) {
            return new CaptureResultExtras[i];
        }
    };
    private int afTriggerId;
    private String errorPhysicalCameraId;
    private int errorStreamId;
    private long frameNumber;
    private boolean hasReadoutTimestamp;
    private long lastCompletedRegularFrameNumber;
    private long lastCompletedReprocessFrameNumber;
    private long lastCompletedZslFrameNumber;
    private int partialResultCount;
    private int precaptureTriggerId;
    private long readoutTimestamp;
    private int requestId;
    private int subsequenceId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CaptureResultExtras(Parcel parcel) {
        readFromParcel(parcel);
    }

    public CaptureResultExtras(int i, int i2, int i3, int i4, long j, int i5, int i6, String str, long j2, long j3, long j4, boolean z, long j5) {
        this.requestId = i;
        this.subsequenceId = i2;
        this.afTriggerId = i3;
        this.precaptureTriggerId = i4;
        this.frameNumber = j;
        this.partialResultCount = i5;
        this.errorStreamId = i6;
        this.errorPhysicalCameraId = str;
        this.lastCompletedRegularFrameNumber = j2;
        this.lastCompletedReprocessFrameNumber = j3;
        this.lastCompletedZslFrameNumber = j4;
        this.hasReadoutTimestamp = z;
        this.readoutTimestamp = j5;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.requestId);
        parcel.writeInt(this.subsequenceId);
        parcel.writeInt(this.afTriggerId);
        parcel.writeInt(this.precaptureTriggerId);
        parcel.writeLong(this.frameNumber);
        parcel.writeInt(this.partialResultCount);
        parcel.writeInt(this.errorStreamId);
        String str = this.errorPhysicalCameraId;
        if (str != null && !str.isEmpty()) {
            parcel.writeBoolean(true);
            parcel.writeString(this.errorPhysicalCameraId);
        } else {
            parcel.writeBoolean(false);
        }
        parcel.writeLong(this.lastCompletedRegularFrameNumber);
        parcel.writeLong(this.lastCompletedReprocessFrameNumber);
        parcel.writeLong(this.lastCompletedZslFrameNumber);
        parcel.writeBoolean(this.hasReadoutTimestamp);
        if (this.hasReadoutTimestamp) {
            parcel.writeLong(this.readoutTimestamp);
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.requestId = parcel.readInt();
        this.subsequenceId = parcel.readInt();
        this.afTriggerId = parcel.readInt();
        this.precaptureTriggerId = parcel.readInt();
        this.frameNumber = parcel.readLong();
        this.partialResultCount = parcel.readInt();
        this.errorStreamId = parcel.readInt();
        if (parcel.readBoolean()) {
            this.errorPhysicalCameraId = parcel.readString();
        }
        this.lastCompletedRegularFrameNumber = parcel.readLong();
        this.lastCompletedReprocessFrameNumber = parcel.readLong();
        this.lastCompletedZslFrameNumber = parcel.readLong();
        boolean z = parcel.readBoolean();
        this.hasReadoutTimestamp = z;
        if (z) {
            this.readoutTimestamp = parcel.readLong();
        }
    }

    public String getErrorPhysicalCameraId() {
        return this.errorPhysicalCameraId;
    }

    public int getRequestId() {
        return this.requestId;
    }

    public int getSubsequenceId() {
        return this.subsequenceId;
    }

    public int getAfTriggerId() {
        return this.afTriggerId;
    }

    public int getPrecaptureTriggerId() {
        return this.precaptureTriggerId;
    }

    public long getFrameNumber() {
        return this.frameNumber;
    }

    public int getPartialResultCount() {
        return this.partialResultCount;
    }

    public int getErrorStreamId() {
        return this.errorStreamId;
    }

    public long getLastCompletedRegularFrameNumber() {
        return this.lastCompletedRegularFrameNumber;
    }

    public long getLastCompletedReprocessFrameNumber() {
        return this.lastCompletedReprocessFrameNumber;
    }

    public long getLastCompletedZslFrameNumber() {
        return this.lastCompletedZslFrameNumber;
    }

    public boolean hasReadoutTimestamp() {
        return this.hasReadoutTimestamp;
    }

    public long getReadoutTimestamp() {
        return this.readoutTimestamp;
    }
}
