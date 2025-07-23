package com.android.internal.protolog;

import com.android.internal.protolog.ProtoLogDataSource;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class PerfettoProtoLogImpl$$ExternalSyntheticLambda4 implements ProtoLogDataSource.Instance.TracingInstanceStopCallback {
    public final /* synthetic */ PerfettoProtoLogImpl f$0;

    public /* synthetic */ PerfettoProtoLogImpl$$ExternalSyntheticLambda4(PerfettoProtoLogImpl perfettoProtoLogImpl) {
        this.f$0 = perfettoProtoLogImpl;
    }

    @Override // com.android.internal.protolog.ProtoLogDataSource.Instance.TracingInstanceStopCallback
    public final void run(int i, ProtoLogDataSource.ProtoLogConfig protoLogConfig) {
        this.f$0.onTracingInstanceStop(i, protoLogConfig);
    }
}
