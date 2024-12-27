package android.tracing.perfetto;

import android.tracing.perfetto.DataSourceInstance;

/* loaded from: classes4.dex */
public interface TraceFunction<DataSourceInstanceType extends DataSourceInstance, TlsStateType, IncrementalStateType> {
    void trace(TracingContext<DataSourceInstanceType, TlsStateType, IncrementalStateType> tracingContext);
}
