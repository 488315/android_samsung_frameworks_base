package android.tracing.perfetto;

import android.tracing.perfetto.DataSourceInstance;

/* loaded from: classes4.dex */
public class CreateTlsStateArgs<DataSourceInstanceType extends DataSourceInstance> {
    private final DataSource<DataSourceInstanceType, Object, Object> mDataSource;
    private final int mInstanceIndex;

    CreateTlsStateArgs(DataSource dataSource, int i) {
        this.mDataSource = dataSource;
        this.mInstanceIndex = i;
    }

    public DataSourceInstanceType getDataSourceInstanceLocked() {
        return this.mDataSource.getDataSourceInstanceLocked(this.mInstanceIndex);
    }
}
