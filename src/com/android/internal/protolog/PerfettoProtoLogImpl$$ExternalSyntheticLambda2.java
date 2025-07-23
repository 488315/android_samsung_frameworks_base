package com.android.internal.protolog;

import com.android.internal.protolog.ProtoLogDataSource;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class PerfettoProtoLogImpl$$ExternalSyntheticLambda2 implements ProtoLogDataSource.Instance.TracingInstanceStartCallback {
    public final /* synthetic */ PerfettoProtoLogImpl f$0;

    public /* synthetic */ PerfettoProtoLogImpl$$ExternalSyntheticLambda2(PerfettoProtoLogImpl perfettoProtoLogImpl) {
        this.f$0 = perfettoProtoLogImpl;
    }

    @Override // com.android.internal.protolog.ProtoLogDataSource.Instance.TracingInstanceStartCallback
    public final void run(int i, ProtoLogDataSource.ProtoLogConfig protoLogConfig) {
        this.f$0.onTracingInstanceStart(i, protoLogConfig);
    }
}
