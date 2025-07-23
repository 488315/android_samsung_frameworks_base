package android.tracing.perfetto;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class DataSourceParams {
    public static DataSourceParams DEFAULTS = new Builder().build();
    public static final int PERFETTO_DS_BUFFER_EXHAUSTED_POLICY_DROP = 0;
    public static final int PERFETTO_DS_BUFFER_EXHAUSTED_POLICY_STALL_AND_ABORT = 1;
    public final int bufferExhaustedPolicy;
    public final boolean noFlush;
    public final boolean willNotifyOnStop;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PerfettoDsBufferExhausted {
    }

    private DataSourceParams(int i, boolean z, boolean z2) {
        this.bufferExhaustedPolicy = i;
        this.willNotifyOnStop = z;
        this.noFlush = z2;
    }

    public static final class Builder {
        private int mBufferExhaustedPolicy = 0;
        private boolean mWillNotifyOnStop = true;
        private boolean mNoFlush = false;

        public Builder setBufferExhaustedPolicy(int i) {
            this.mBufferExhaustedPolicy = i;
            return this;
        }

        public Builder setWillNotifyOnStop(boolean z) {
            this.mWillNotifyOnStop = z;
            return this;
        }

        public Builder setNoFlush(boolean z) {
            this.mNoFlush = z;
            return this;
        }

        public DataSourceParams build() {
            return new DataSourceParams(this.mBufferExhaustedPolicy, this.mWillNotifyOnStop, this.mNoFlush);
        }
    }
}
