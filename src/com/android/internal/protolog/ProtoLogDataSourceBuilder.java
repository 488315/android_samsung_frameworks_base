package com.android.internal.protolog;

import com.android.internal.protolog.ProtoLogDataSource;

/* loaded from: classes4.dex */
public interface ProtoLogDataSourceBuilder {
    ProtoLogDataSource build(ProtoLogDataSource.Instance.TracingInstanceStartCallback tracingInstanceStartCallback, Runnable runnable, ProtoLogDataSource.Instance.TracingInstanceStopCallback tracingInstanceStopCallback);
}
