package android.net.metrics;

import android.annotation.SystemApi;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.android.internal.util.MessageUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public final class ValidationProbeEvent implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<ValidationProbeEvent> CREATOR = new Parcelable.Creator<ValidationProbeEvent>() { // from class: android.net.metrics.ValidationProbeEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ValidationProbeEvent createFromParcel(Parcel parcel) {
            return new ValidationProbeEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ValidationProbeEvent[] newArray(int i) {
            return new ValidationProbeEvent[i];
        }
    };
    public static final int DNS_FAILURE = 0;
    public static final int DNS_SUCCESS = 1;
    private static final int FIRST_VALIDATION = 256;
    public static final int PROBE_DNS = 0;
    public static final int PROBE_FALLBACK = 4;
    public static final int PROBE_HTTP = 1;
    public static final int PROBE_HTTPS = 2;
    public static final int PROBE_PAC = 3;
    public static final int PROBE_PRIVDNS = 5;
    private static final int REVALIDATION = 512;
    public final long durationMs;
    public final int probeType;
    public final int returnCode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ReturnCode {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int makeProbeType(int i, boolean z) {
        return (i & 255) | (z ? 256 : 512);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ValidationProbeEvent(long j, int i, int i2) {
        this.durationMs = j;
        this.probeType = i;
        this.returnCode = i2;
    }

    private ValidationProbeEvent(Parcel parcel) {
        this.durationMs = parcel.readLong();
        this.probeType = parcel.readInt();
        this.returnCode = parcel.readInt();
    }

    public static final class Builder {
        private long mDurationMs;
        private int mProbeType;
        private int mReturnCode;

        public Builder setDurationMs(long j) {
            this.mDurationMs = j;
            return this;
        }

        public Builder setProbeType(int i, boolean z) {
            this.mProbeType = ValidationProbeEvent.makeProbeType(i, z);
            return this;
        }

        public Builder setReturnCode(int i) {
            this.mReturnCode = i;
            return this;
        }

        public ValidationProbeEvent build() {
            return new ValidationProbeEvent(this.mDurationMs, this.mProbeType, this.mReturnCode);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.durationMs);
        parcel.writeInt(this.probeType);
        parcel.writeInt(this.returnCode);
    }

    public static String getProbeName(int i) {
        return Decoder.constants.get(i & 255, "PROBE_???");
    }

    private static String getValidationStage(int i) {
        return Decoder.constants.get(i & 65280, "UNKNOWN");
    }

    public String toString() {
        return String.format("ValidationProbeEvent(%s:%d %s, %dms)", getProbeName(this.probeType), Integer.valueOf(this.returnCode), getValidationStage(this.probeType), Long.valueOf(this.durationMs));
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(ValidationProbeEvent.class)) {
            ValidationProbeEvent validationProbeEvent = (ValidationProbeEvent) obj;
            if (this.durationMs == validationProbeEvent.durationMs && this.probeType == validationProbeEvent.probeType && this.returnCode == validationProbeEvent.returnCode) {
                return true;
            }
        }
        return false;
    }

    static final class Decoder {
        static final SparseArray<String> constants = MessageUtils.findMessageNames(new Class[]{ValidationProbeEvent.class}, new String[]{"PROBE_", "FIRST_", "REVALIDATION"});

        Decoder() {
        }
    }
}
