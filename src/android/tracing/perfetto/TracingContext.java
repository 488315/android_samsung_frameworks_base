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

    TracingContext(DataSource<DataSourceInstanceType, TlsStateType, IncrementalStateType> dataSource, int i) {
        this.mDataSource = dataSource;
        this.mInstanceIndex = i;
    }

    public ProtoOutputStream newTracePacket() {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(0);
        this.mTracePackets.add(protoOutputStream);
        return protoOutputStream;
    }

    public TlsStateType getCustomTlsState() {
        TlsStateType tlsstatetype = (TlsStateType) nativeGetCustomTls(this.mDataSource.mNativeObj);
        if (tlsstatetype != null) {
            return tlsstatetype;
        }
        TlsStateType createTlsState = this.mDataSource.createTlsState(new CreateTlsStateArgs<>(this.mDataSource, this.mInstanceIndex));
        nativeSetCustomTls(this.mDataSource.mNativeObj, createTlsState);
        return createTlsState;
    }

    public IncrementalStateType getIncrementalState() {
        IncrementalStateType incrementalstatetype = (IncrementalStateType) nativeGetIncrementalState(this.mDataSource.mNativeObj);
        if (incrementalstatetype != null) {
            return incrementalstatetype;
        }
        IncrementalStateType createIncrementalState = this.mDataSource.createIncrementalState(new CreateIncrementalStateArgs<>(this.mDataSource, this.mInstanceIndex));
        nativeSetIncrementalState(this.mDataSource.mNativeObj, createIncrementalState);
        return createIncrementalState;
    }

    protected byte[][] getAndClearAllPendingTracePackets() {
        byte[][] bArr = new byte[this.mTracePackets.size()][];
        for (int i = 0; i < this.mTracePackets.size(); i++) {
            bArr[i] = this.mTracePackets.get(i).getBytes();
        }
        this.mTracePackets.clear();
        return bArr;
    }
}
