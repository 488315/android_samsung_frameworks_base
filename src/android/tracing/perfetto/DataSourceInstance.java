package android.tracing.perfetto;

/* loaded from: classes4.dex */
public abstract class DataSourceInstance implements AutoCloseable {
    private final DataSource mDataSource;
    private final int mInstanceIndex;

    protected void onFlush(FlushCallbackArguments flushCallbackArguments) {
    }

    protected void onStart(StartCallbackArguments startCallbackArguments) {
    }

    protected void onStop(StopCallbackArguments stopCallbackArguments) {
    }

    public DataSourceInstance(DataSource dataSource, int i) {
        this.mDataSource = dataSource;
        this.mInstanceIndex = i;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        release();
    }

    public void release() {
        this.mDataSource.releaseDataSourceInstance(this.mInstanceIndex);
    }

    public final int getInstanceIndex() {
        return this.mInstanceIndex;
    }
}
