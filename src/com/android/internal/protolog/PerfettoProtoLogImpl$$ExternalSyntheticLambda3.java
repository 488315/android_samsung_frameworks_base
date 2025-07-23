package com.android.internal.protolog;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class PerfettoProtoLogImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ PerfettoProtoLogImpl f$0;

    public /* synthetic */ PerfettoProtoLogImpl$$ExternalSyntheticLambda3(PerfettoProtoLogImpl perfettoProtoLogImpl) {
        this.f$0 = perfettoProtoLogImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.onTracingFlush();
    }
}
