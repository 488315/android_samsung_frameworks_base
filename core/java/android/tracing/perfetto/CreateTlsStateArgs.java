package android.tracing.perfetto;


public class CreateTlsStateArgs<DataSourceInstanceType extends DataSourceInstance> {
    private final DataSource<DataSourceInstanceType, Object, Object> mDataSource;
    private final int mInstanceIndex;

    CreateTlsStateArgs(DataSource dataSource, int instanceIndex) {
        this.mDataSource = dataSource;
        this.mInstanceIndex = instanceIndex;
    }

    public DataSourceInstanceType getDataSourceInstanceLocked() {
        return this.mDataSource.getDataSourceInstanceLocked(this.mInstanceIndex);
    }
}
