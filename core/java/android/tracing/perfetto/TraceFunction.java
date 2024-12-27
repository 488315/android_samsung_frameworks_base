package android.tracing.perfetto;


public interface TraceFunction<
        DataSourceInstanceType extends DataSourceInstance, TlsStateType, IncrementalStateType> {
    void trace(
            TracingContext<DataSourceInstanceType, TlsStateType, IncrementalStateType>
                    tracingContext);
}
