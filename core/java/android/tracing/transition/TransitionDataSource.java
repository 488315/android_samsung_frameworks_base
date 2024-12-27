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

    public TransitionDataSource(Runnable onStart, Runnable onFlush, Runnable onStop) {
        super(DATA_SOURCE_NAME);
        this.mOnStartStaticCallback = onStart;
        this.mOnFlushStaticCallback = onFlush;
        this.mOnStopStaticCallback = onStop;
    }

    @Override // android.tracing.perfetto.DataSource
    public DataSourceInstance createInstance(ProtoInputStream configStream, int instanceIndex) {
        return new DataSourceInstance(this, instanceIndex) { // from class: android.tracing.transition.TransitionDataSource.1
            @Override // android.tracing.perfetto.DataSourceInstance
            protected void onStart(StartCallbackArguments args) {
                TransitionDataSource.this.mOnStartStaticCallback.run();
            }

            @Override // android.tracing.perfetto.DataSourceInstance
            protected void onFlush(FlushCallbackArguments args) {
                TransitionDataSource.this.mOnFlushStaticCallback.run();
            }

            @Override // android.tracing.perfetto.DataSourceInstance
            protected void onStop(StopCallbackArguments args) {
                TransitionDataSource.this.mOnStopStaticCallback.run();
            }
        };
    }
}
