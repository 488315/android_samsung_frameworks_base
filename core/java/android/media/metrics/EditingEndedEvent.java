package android.media.metrics;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class EditingEndedEvent extends Event implements Parcelable {
    public static final Parcelable.Creator<EditingEndedEvent> CREATOR = new Parcelable.Creator<EditingEndedEvent>() { // from class: android.media.metrics.EditingEndedEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EditingEndedEvent[] newArray(int size) {
            return new EditingEndedEvent[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EditingEndedEvent createFromParcel(Parcel in) {
            return new EditingEndedEvent(in);
        }
    };
    public static final int ERROR_CODE_AUDIO_PROCESSING_FAILED = 18;
    public static final int ERROR_CODE_DECODER_INIT_FAILED = 11;
    public static final int ERROR_CODE_DECODING_FAILED = 12;
    public static final int ERROR_CODE_DECODING_FORMAT_UNSUPPORTED = 13;
    public static final int ERROR_CODE_ENCODER_INIT_FAILED = 14;
    public static final int ERROR_CODE_ENCODING_FAILED = 15;
    public static final int ERROR_CODE_ENCODING_FORMAT_UNSUPPORTED = 16;
    public static final int ERROR_CODE_FAILED_RUNTIME_CHECK = 2;
    public static final int ERROR_CODE_IO_BAD_HTTP_STATUS = 6;
    public static final int ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED = 9;
    public static final int ERROR_CODE_IO_FILE_NOT_FOUND = 7;
    public static final int ERROR_CODE_IO_NETWORK_CONNECTION_FAILED = 4;
    public static final int ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT = 5;
    public static final int ERROR_CODE_IO_NO_PERMISSION = 8;
    public static final int ERROR_CODE_IO_READ_POSITION_OUT_OF_RANGE = 10;
    public static final int ERROR_CODE_IO_UNSPECIFIED = 3;
    public static final int ERROR_CODE_MUXING_FAILED = 19;
    public static final int ERROR_CODE_NONE = 1;
    public static final int ERROR_CODE_VIDEO_FRAME_PROCESSING_FAILED = 17;
    public static final int FINAL_STATE_CANCELED = 2;
    public static final int FINAL_STATE_ERROR = 3;
    public static final int FINAL_STATE_SUCCEEDED = 1;
    public static final long OPERATION_TYPE_AUDIO_EDIT = 8;
    public static final long OPERATION_TYPE_AUDIO_TRANSCODE = 2;
    public static final long OPERATION_TYPE_AUDIO_TRANSMUX = 32;
    public static final long OPERATION_TYPE_PAUSED = 64;
    public static final long OPERATION_TYPE_RESUMED = 128;
    public static final long OPERATION_TYPE_VIDEO_EDIT = 4;
    public static final long OPERATION_TYPE_VIDEO_TRANSCODE = 1;
    public static final long OPERATION_TYPE_VIDEO_TRANSMUX = 16;
    public static final int PROGRESS_PERCENT_UNKNOWN = -1;
    public static final int TIME_SINCE_CREATED_UNKNOWN = -1;
    private final int mErrorCode;
    private final String mExporterName;
    private final float mFinalProgressPercent;
    private final int mFinalState;
    private final ArrayList<MediaItemInfo> mInputMediaItemInfos;
    private final String mMuxerName;
    private final long mOperationTypes;
    private final MediaItemInfo mOutputMediaItemInfo;
    private final long mTimeSinceCreatedMillis;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FinalState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OperationType {
    }

    private EditingEndedEvent(int finalState, float finalProgressPercent, int errorCode, long timeSinceCreatedMillis, String exporterName, String muxerName, ArrayList<MediaItemInfo> inputMediaItemInfos, MediaItemInfo outputMediaItemInfo, long operationTypes, Bundle extras) {
        this.mFinalState = finalState;
        this.mFinalProgressPercent = finalProgressPercent;
        this.mErrorCode = errorCode;
        this.mTimeSinceCreatedMillis = timeSinceCreatedMillis;
        this.mExporterName = exporterName;
        this.mMuxerName = muxerName;
        this.mInputMediaItemInfos = inputMediaItemInfos;
        this.mOutputMediaItemInfo = outputMediaItemInfo;
        this.mOperationTypes = operationTypes;
        this.mMetricsBundle = extras.deepCopy();
    }

    public int getFinalState() {
        return this.mFinalState;
    }

    public float getFinalProgressPercent() {
        return this.mFinalProgressPercent;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    @Override // android.media.metrics.Event
    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    public String getExporterName() {
        return this.mExporterName;
    }

    public String getMuxerName() {
        return this.mMuxerName;
    }

    public List<MediaItemInfo> getInputMediaItemInfos() {
        return new ArrayList(this.mInputMediaItemInfos);
    }

    public MediaItemInfo getOutputMediaItemInfo() {
        return this.mOutputMediaItemInfo;
    }

    public long getOperationTypes() {
        return this.mOperationTypes;
    }

    @Override // android.media.metrics.Event
    public Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    public String toString() {
        return "EditingEndedEvent { finalState = " + this.mFinalState + ", finalProgressPercent = " + this.mFinalProgressPercent + ", errorCode = " + this.mErrorCode + ", timeSinceCreatedMillis = " + this.mTimeSinceCreatedMillis + ", exporterName = " + this.mExporterName + ", muxerName = " + this.mMuxerName + ", inputMediaItemInfos = " + this.mInputMediaItemInfos + ", outputMediaItemInfo = " + this.mOutputMediaItemInfo + ", operationTypes = " + this.mOperationTypes + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EditingEndedEvent that = (EditingEndedEvent) o;
        if (this.mFinalState == that.mFinalState && this.mFinalProgressPercent == that.mFinalProgressPercent && this.mErrorCode == that.mErrorCode && Objects.equals(this.mInputMediaItemInfos, that.mInputMediaItemInfos) && Objects.equals(this.mOutputMediaItemInfo, that.mOutputMediaItemInfo) && this.mOperationTypes == that.mOperationTypes && this.mTimeSinceCreatedMillis == that.mTimeSinceCreatedMillis && Objects.equals(this.mExporterName, that.mExporterName) && Objects.equals(this.mMuxerName, that.mMuxerName)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mFinalState), Float.valueOf(this.mFinalProgressPercent), Integer.valueOf(this.mErrorCode), this.mInputMediaItemInfos, this.mOutputMediaItemInfo, Long.valueOf(this.mOperationTypes), Long.valueOf(this.mTimeSinceCreatedMillis), this.mExporterName, this.mMuxerName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mFinalState);
        dest.writeFloat(this.mFinalProgressPercent);
        dest.writeInt(this.mErrorCode);
        dest.writeLong(this.mTimeSinceCreatedMillis);
        dest.writeString(this.mExporterName);
        dest.writeString(this.mMuxerName);
        dest.writeTypedList(this.mInputMediaItemInfos);
        dest.writeTypedObject(this.mOutputMediaItemInfo, 0);
        dest.writeLong(this.mOperationTypes);
        dest.writeBundle(this.mMetricsBundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private EditingEndedEvent(Parcel in) {
        this.mFinalState = in.readInt();
        this.mFinalProgressPercent = in.readFloat();
        this.mErrorCode = in.readInt();
        this.mTimeSinceCreatedMillis = in.readLong();
        this.mExporterName = in.readString();
        this.mMuxerName = in.readString();
        this.mInputMediaItemInfos = new ArrayList<>();
        in.readTypedList(this.mInputMediaItemInfos, MediaItemInfo.CREATOR);
        this.mOutputMediaItemInfo = (MediaItemInfo) in.readTypedObject(MediaItemInfo.CREATOR);
        this.mOperationTypes = in.readLong();
        this.mMetricsBundle = in.readBundle();
    }

    public static final class Builder {
        private String mExporterName;
        private final int mFinalState;
        private String mMuxerName;
        private long mOperationTypes;
        private MediaItemInfo mOutputMediaItemInfo;
        private float mFinalProgressPercent = -1.0f;
        private int mErrorCode = 1;
        private long mTimeSinceCreatedMillis = -1;
        private final ArrayList<MediaItemInfo> mInputMediaItemInfos = new ArrayList<>();
        private Bundle mMetricsBundle = new Bundle();

        public Builder(int finalState) {
            this.mFinalState = finalState;
        }

        public Builder setFinalProgressPercent(float finalProgressPercent) {
            this.mFinalProgressPercent = finalProgressPercent;
            return this;
        }

        public Builder setTimeSinceCreatedMillis(long timeSinceCreatedMillis) {
            this.mTimeSinceCreatedMillis = timeSinceCreatedMillis;
            return this;
        }

        public Builder setExporterName(String exporterName) {
            this.mExporterName = (String) Objects.requireNonNull(exporterName);
            return this;
        }

        public Builder setMuxerName(String muxerName) {
            this.mMuxerName = (String) Objects.requireNonNull(muxerName);
            return this;
        }

        public Builder setErrorCode(int value) {
            this.mErrorCode = value;
            return this;
        }

        public Builder addInputMediaItemInfo(MediaItemInfo mediaItemInfo) {
            this.mInputMediaItemInfos.add((MediaItemInfo) Objects.requireNonNull(mediaItemInfo));
            return this;
        }

        public Builder setOutputMediaItemInfo(MediaItemInfo mediaItemInfo) {
            this.mOutputMediaItemInfo = (MediaItemInfo) Objects.requireNonNull(mediaItemInfo);
            return this;
        }

        public Builder addOperationType(long operationType) {
            this.mOperationTypes |= operationType;
            return this;
        }

        public Builder setMetricsBundle(Bundle metricsBundle) {
            this.mMetricsBundle = (Bundle) Objects.requireNonNull(metricsBundle);
            return this;
        }

        public EditingEndedEvent build() {
            return new EditingEndedEvent(this.mFinalState, this.mFinalProgressPercent, this.mErrorCode, this.mTimeSinceCreatedMillis, this.mExporterName, this.mMuxerName, this.mInputMediaItemInfos, this.mOutputMediaItemInfo, this.mOperationTypes, this.mMetricsBundle);
        }
    }
}
