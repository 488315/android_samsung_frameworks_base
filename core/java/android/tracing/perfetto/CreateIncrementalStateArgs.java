package android.tracing.perfetto;


public class CreateIncrementalStateArgs<DataSourceInstanceType extends DataSourceInstance> {
    private final DataSource<DataSourceInstanceType, Object, Object> mDataSource;
    private final int mInstanceIndex;

    CreateIncrementalStateArgs(DataSource dataSource, int instanceIndex) {
        this.mDataSource = dataSource;
        this.mInstanceIndex = instanceIndex;
    }

    public DataSourceInstanceType getDataSourceInstanceLocked() {
        return this.mDataSource.getDataSourceInstanceLocked(this.mInstanceIndex);
    }
}
