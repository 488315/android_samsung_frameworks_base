package android.tracing.perfetto;

import android.tracing.perfetto.DataSourceInstance;

/* loaded from: classes4.dex */
public class CreateIncrementalStateArgs<DataSourceInstanceType extends DataSourceInstance> {
    private final DataSource<DataSourceInstanceType, Object, Object> mDataSource;
    private final int mInstanceIndex;

    CreateIncrementalStateArgs(DataSource dataSource, int i) {
        this.mDataSource = dataSource;
        this.mInstanceIndex = i;
    }

    public DataSourceInstanceType getDataSourceInstanceLocked() {
        return (DataSourceInstanceType) this.mDataSource.getDataSourceInstanceLocked(this.mInstanceIndex);
    }
}
