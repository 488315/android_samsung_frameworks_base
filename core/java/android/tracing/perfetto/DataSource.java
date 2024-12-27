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

    public abstract DataSourceInstanceType createInstance(ProtoInputStream protoInputStream, int i);

    public DataSource(String name) {
        this.name = name;
        this.mNativeObj = nativeCreate(this, name);
    }

    public final void trace(TraceFunction<DataSourceInstanceType, TlsStateType, IncrementalStateType> fun) {
        boolean startedIterator = nativePerfettoDsTraceIterateBegin(this.mNativeObj);
        if (!startedIterator) {
            return;
        }
        do {
            try {
                int instanceIndex = nativeGetPerfettoDsInstanceIndex(this.mNativeObj);
                TracingContext<DataSourceInstanceType, TlsStateType, IncrementalStateType> ctx = new TracingContext<>(this, instanceIndex);
                fun.trace(ctx);
                nativeWritePackets(this.mNativeObj, ctx.getAndClearAllPendingTracePackets());
            } finally {
                nativePerfettoDsTraceIterateBreak(this.mNativeObj);
            }
        } while (nativePerfettoDsTraceIterateNext(this.mNativeObj));
    }

    public final void flush() {
        nativeFlushAll(this.mNativeObj);
    }

    public TlsStateType createTlsState(CreateTlsStateArgs<DataSourceInstanceType> args) {
        return null;
    }

    public IncrementalStateType createIncrementalState(CreateIncrementalStateArgs<DataSourceInstanceType> args) {
        return null;
    }

    public void register(DataSourceParams params) {
        nativeRegisterDataSource(this.mNativeObj, params.bufferExhaustedPolicy, params.willNotifyOnStop, params.noFlush);
    }

    public DataSourceInstanceType getDataSourceInstanceLocked(int i) {
        return (DataSourceInstanceType) nativeGetPerfettoInstanceLocked(this.mNativeObj, i);
    }

    protected void releaseDataSourceInstance(int instanceIndex) {
        nativeReleasePerfettoInstanceLocked(this.mNativeObj, instanceIndex);
    }

    private DataSourceInstanceType createInstance(byte[] rawConfig, int instanceIndex) {
        ProtoInputStream inputStream = new ProtoInputStream(rawConfig);
        return createInstance(inputStream, instanceIndex);
    }
}
