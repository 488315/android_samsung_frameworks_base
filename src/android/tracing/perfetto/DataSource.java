package android.tracing.perfetto;

import android.tracing.perfetto.DataSourceInstance;
import android.util.proto.ProtoInputStream;

/* loaded from: classes4.dex */
public abstract class DataSource<DataSourceInstanceType extends DataSourceInstance, TlsStateType, IncrementalStateType> {
    protected final long mNativeObj;
    public final String name;

    private static native long nativeCreate(DataSource dataSource, String str);

    private static native void nativeFlushAll(long j);

    private static native long nativeGetFinalizer();

    private static native int nativeGetPerfettoDsInstanceIndex(long j);

    private static native DataSourceInstance nativeGetPerfettoInstanceLocked(long j, int i);

    private static native boolean nativePerfettoDsTraceIterateBegin(long j);

    private static native void nativePerfettoDsTraceIterateBreak(long j);

    private static native boolean nativePerfettoDsTraceIterateNext(long j);

    private static native void nativeRegisterDataSource(long j, int i, boolean z, boolean z2);

    private static native void nativeReleasePerfettoInstanceLocked(long j, int i);

    private static native void nativeWritePackets(long j, byte[][] bArr);

    public IncrementalStateType createIncrementalState(CreateIncrementalStateArgs<DataSourceInstanceType> createIncrementalStateArgs) {
        return null;
    }

    public abstract DataSourceInstanceType createInstance(ProtoInputStream protoInputStream, int i);

    public TlsStateType createTlsState(CreateTlsStateArgs<DataSourceInstanceType> createTlsStateArgs) {
        return null;
    }

    public DataSource(String str) {
        this.name = str;
        this.mNativeObj = nativeCreate(this, str);
    }

    public final void trace(TraceFunction<DataSourceInstanceType, TlsStateType, IncrementalStateType> traceFunction) {
        if (nativePerfettoDsTraceIterateBegin(this.mNativeObj)) {
            do {
                try {
                    TracingContext<DataSourceInstanceType, TlsStateType, IncrementalStateType> tracingContext = new TracingContext<>(this, nativeGetPerfettoDsInstanceIndex(this.mNativeObj));
                    traceFunction.trace(tracingContext);
                    nativeWritePackets(this.mNativeObj, tracingContext.getAndClearAllPendingTracePackets());
                } finally {
                    nativePerfettoDsTraceIterateBreak(this.mNativeObj);
                }
            } while (nativePerfettoDsTraceIterateNext(this.mNativeObj));
        }
    }

    public final void flush() {
        nativeFlushAll(this.mNativeObj);
    }

    public void register(DataSourceParams dataSourceParams) {
        nativeRegisterDataSource(this.mNativeObj, dataSourceParams.bufferExhaustedPolicy, dataSourceParams.willNotifyOnStop, dataSourceParams.noFlush);
    }

    public DataSourceInstanceType getDataSourceInstanceLocked(int i) {
        return (DataSourceInstanceType) nativeGetPerfettoInstanceLocked(this.mNativeObj, i);
    }

    protected void releaseDataSourceInstance(int i) {
        nativeReleasePerfettoInstanceLocked(this.mNativeObj, i);
    }

    private DataSourceInstanceType createInstance(byte[] bArr, int i) {
        return createInstance(new ProtoInputStream(bArr), i);
    }
}
