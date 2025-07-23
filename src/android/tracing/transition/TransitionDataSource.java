package android.tracing.transition;

import android.tracing.perfetto.DataSource;
import android.tracing.perfetto.DataSourceInstance;
import android.tracing.perfetto.FlushCallbackArguments;
import android.tracing.perfetto.StartCallbackArguments;
import android.tracing.perfetto.StopCallbackArguments;
import android.util.proto.ProtoInputStream;

/* loaded from: classes4.dex */
public class TransitionDataSource extends DataSource<DataSourceInstance, Void, Void> {
    public static String DATA_SOURCE_NAME = "com.android.wm.shell.transition";
    private final Runnable mOnFlushStaticCallback;
    private final Runnable mOnStartStaticCallback;
    private final Runnable mOnStopStaticCallback;

    public TransitionDataSource(Runnable runnable, Runnable runnable2, Runnable runnable3) {
        super(DATA_SOURCE_NAME);
        this.mOnStartStaticCallback = runnable;
        this.mOnFlushStaticCallback = runnable2;
        this.mOnStopStaticCallback = runnable3;
    }

    @Override // android.tracing.perfetto.DataSource
    public DataSourceInstance createInstance(ProtoInputStream protoInputStream, int i) {
        return new DataSourceInstance(this, i) { // from class: android.tracing.transition.TransitionDataSource.1
            @Override // android.tracing.perfetto.DataSourceInstance
            protected void onStart(StartCallbackArguments startCallbackArguments) {
                TransitionDataSource.this.mOnStartStaticCallback.run();
            }

            @Override // android.tracing.perfetto.DataSourceInstance
            protected void onFlush(FlushCallbackArguments flushCallbackArguments) {
                TransitionDataSource.this.mOnFlushStaticCallback.run();
            }

            @Override // android.tracing.perfetto.DataSourceInstance
            protected void onStop(StopCallbackArguments stopCallbackArguments) {
                TransitionDataSource.this.mOnStopStaticCallback.run();
            }
        };
    }
}
