package android.tracing.perfetto;


/* loaded from: classes4.dex */
public interface TraceFunction<
        DataSourceInstanceType extends DataSourceInstance, TlsStateType, IncrementalStateType> {
    void trace(
            TracingContext<DataSourceInstanceType, TlsStateType, IncrementalStateType>
                    tracingContext);
}
