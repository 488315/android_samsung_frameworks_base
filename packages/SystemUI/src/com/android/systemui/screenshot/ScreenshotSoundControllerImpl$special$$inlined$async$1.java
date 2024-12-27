package com.android.systemui.screenshot;

import android.media.MediaPlayer;
import android.os.Trace;
import android.util.Log;
import com.android.app.tracing.TraceProxy_platformKt;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceData;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class ScreenshotSoundControllerImpl$special$$inlined$async$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ ScreenshotSoundControllerImpl this$0;

    public ScreenshotSoundControllerImpl$special$$inlined$async$1(String str, Continuation continuation, ScreenshotSoundControllerImpl screenshotSoundControllerImpl) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = screenshotSoundControllerImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScreenshotSoundControllerImpl$special$$inlined$async$1 screenshotSoundControllerImpl$special$$inlined$async$1 = new ScreenshotSoundControllerImpl$special$$inlined$async$1(this.$spanName, continuation, this.this$0);
        screenshotSoundControllerImpl$special$$inlined$async$1.L$0 = obj;
        return screenshotSoundControllerImpl$special$$inlined$async$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenshotSoundControllerImpl$special$$inlined$async$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        MediaPlayer mediaPlayer;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = this.$spanName;
        TraceData traceData = (TraceData) TraceContextElementKt.traceThreadLocal.get();
        boolean isEnabled = Trace.isEnabled();
        if (traceData == null && !isEnabled) {
            str = "<none>";
        }
        if (traceData != null) {
            traceData.beginSpan(str);
        }
        int nextInt = isEnabled ? ThreadLocalRandom.current().nextInt() : 0;
        if (isEnabled) {
            TraceProxy_platformKt.asyncTraceForTrackBegin(nextInt, "Coroutines", str);
        }
        try {
            try {
                mediaPlayer = ((ScreenshotSoundProviderImpl) this.this$0.soundProvider).getScreenshotSound();
            } catch (IllegalStateException e) {
                Log.w("ScreenshotSoundControllerImpl", "Screenshot sound initialization failed", e);
                mediaPlayer = null;
            }
            return mediaPlayer;
        } finally {
            if (isEnabled) {
                TraceProxy_platformKt.asyncTraceForTrackEnd(nextInt, "Coroutines");
            }
            if (traceData != null) {
                traceData.endSpan();
            }
        }
    }
}
