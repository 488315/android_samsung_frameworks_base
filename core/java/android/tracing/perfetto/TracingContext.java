package android.tracing.perfetto;

import android.tracing.perfetto.DataSourceInstance;
import android.util.proto.ProtoOutputStream;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class TracingContext<DataSourceInstanceType extends DataSourceInstance, TlsStateType, IncrementalStateType> {
    private final DataSource<DataSourceInstanceType, TlsStateType, IncrementalStateType> mDataSource;
    private final int mInstanceIndex;
    private final List<ProtoOutputStream> mTracePackets = new ArrayList();

    private static native Object nativeGetCustomTls(long j);

    private static native Object nativeGetIncrementalState(long j);

    private static native void nativeSetCustomTls(long j, Object obj);

    private static native void nativeSetIncrementalState(long j, Object obj);

    TracingContext(DataSource<DataSourceInstanceType, TlsStateType, IncrementalStateType> dataSource, int instanceIndex) {
        this.mDataSource = dataSource;
        this.mInstanceIndex = instanceIndex;
    }

    public ProtoOutputStream newTracePacket() {
        ProtoOutputStream os = new ProtoOutputStream(0);
        this.mTracePackets.add(os);
        return os;
    }

    public TlsStateType getCustomTlsState() {
        TlsStateType tlsstatetype = (TlsStateType) nativeGetCustomTls(this.mDataSource.mNativeObj);
        if (tlsstatetype == null) {
            TlsStateType createTlsState = this.mDataSource.createTlsState(new CreateTlsStateArgs<>(this.mDataSource, this.mInstanceIndex));
            nativeSetCustomTls(this.mDataSource.mNativeObj, createTlsState);
            return createTlsState;
        }
        return tlsstatetype;
    }

    public IncrementalStateType getIncrementalState() {
        IncrementalStateType incrementalstatetype = (IncrementalStateType) nativeGetIncrementalState(this.mDataSource.mNativeObj);
        if (incrementalstatetype == null) {
            IncrementalStateType createIncrementalState = this.mDataSource.createIncrementalState(new CreateIncrementalStateArgs<>(this.mDataSource, this.mInstanceIndex));
            nativeSetIncrementalState(this.mDataSource.mNativeObj, createIncrementalState);
            return createIncrementalState;
        }
        return incrementalstatetype;
    }

    protected byte[][] getAndClearAllPendingTracePackets() {
        byte[][] res = new byte[this.mTracePackets.size()][];
        for (int i = 0; i < this.mTracePackets.size(); i++) {
            ProtoOutputStream tracePacket = this.mTracePackets.get(i);
            res[i] = tracePacket.getBytes();
        }
        this.mTracePackets.clear();
        return res;
    }
}
