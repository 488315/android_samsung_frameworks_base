package android.tracing.perfetto;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DataSourceParams {
    public static DataSourceParams DEFAULTS = new Builder().build();
    public static final int PERFETTO_DS_BUFFER_EXHAUSTED_POLICY_DROP = 0;
    public static final int PERFETTO_DS_BUFFER_EXHAUSTED_POLICY_STALL_AND_ABORT = 1;
    public final int bufferExhaustedPolicy;
    public final boolean noFlush;
    public final boolean willNotifyOnStop;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PerfettoDsBufferExhausted {}

    private DataSourceParams(int bufferExhaustedPolicy, boolean willNotifyOnStop, boolean noFlush) {
        this.bufferExhaustedPolicy = bufferExhaustedPolicy;
        this.willNotifyOnStop = willNotifyOnStop;
        this.noFlush = noFlush;
    }

    public static final class Builder {
        private int mBufferExhaustedPolicy = 0;
        private boolean mWillNotifyOnStop = true;
        private boolean mNoFlush = false;

        public Builder setBufferExhaustedPolicy(int value) {
            this.mBufferExhaustedPolicy = value;
            return this;
        }

        public Builder setWillNotifyOnStop(boolean value) {
            this.mWillNotifyOnStop = value;
            return this;
        }

        public Builder setNoFlush(boolean value) {
            this.mNoFlush = value;
            return this;
        }

        public DataSourceParams build() {
            return new DataSourceParams(
                    this.mBufferExhaustedPolicy, this.mWillNotifyOnStop, this.mNoFlush);
        }
    }
}
