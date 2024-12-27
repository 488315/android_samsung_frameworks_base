package android.tracing.perfetto;

/* loaded from: classes4.dex */
public abstract class DataSourceInstance implements AutoCloseable {
    private final DataSource mDataSource;
    private final int mInstanceIndex;

    public DataSourceInstance(DataSource dataSource, int instanceIndex) {
        this.mDataSource = dataSource;
        this.mInstanceIndex = instanceIndex;
    }

    protected void onStart(StartCallbackArguments args) {}

    protected void onFlush(FlushCallbackArguments args) {}

    protected void onStop(StopCallbackArguments args) {}

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
