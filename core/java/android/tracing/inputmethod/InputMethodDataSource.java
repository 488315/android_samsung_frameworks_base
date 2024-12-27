package android.tracing.inputmethod;

import android.tracing.perfetto.DataSource;
import android.tracing.perfetto.DataSourceInstance;
import android.tracing.perfetto.StartCallbackArguments;
import android.tracing.perfetto.StopCallbackArguments;
import android.util.proto.ProtoInputStream;

/* loaded from: classes4.dex */
public final class InputMethodDataSource extends DataSource<DataSourceInstance, Void, Void> {
    public static final String DATA_SOURCE_NAME = "android.inputmethod";
    private final Runnable mOnStartCallback;
    private final Runnable mOnStopCallback;

    public InputMethodDataSource(Runnable onStart, Runnable onStop) {
        super(DATA_SOURCE_NAME);
        this.mOnStartCallback = onStart;
        this.mOnStopCallback = onStop;
    }

    @Override // android.tracing.perfetto.DataSource
    public DataSourceInstance createInstance(ProtoInputStream configStream, int instanceIndex) {
        return new DataSourceInstance(
                this,
                instanceIndex) { // from class: android.tracing.inputmethod.InputMethodDataSource.1
            @Override // android.tracing.perfetto.DataSourceInstance
            protected void onStart(StartCallbackArguments args) {
                InputMethodDataSource.this.mOnStartCallback.run();
            }

            @Override // android.tracing.perfetto.DataSourceInstance
            protected void onStop(StopCallbackArguments args) {
                InputMethodDataSource.this.mOnStopCallback.run();
            }
        };
    }
}
