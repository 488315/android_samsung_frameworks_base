package com.android.systemui.log.echo;

import com.android.systemui.util.settings.GlobalSettings;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class LogcatEchoTrackerDebug$clearAllOverrides$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ LogcatEchoTrackerDebug this$0;

    public LogcatEchoTrackerDebug$clearAllOverrides$1(LogcatEchoTrackerDebug logcatEchoTrackerDebug, Continuation continuation) {
        super(2, continuation);
        this.this$0 = logcatEchoTrackerDebug;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LogcatEchoTrackerDebug$clearAllOverrides$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LogcatEchoTrackerDebug$clearAllOverrides$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.bufferOverrides = MapsKt__MapsKt.emptyMap();
        this.this$0.tagOverrides = MapsKt__MapsKt.emptyMap();
        List listEchoOverrides = this.this$0.listEchoOverrides();
        LogcatEchoTrackerDebug logcatEchoTrackerDebug = this.this$0;
        GlobalSettings globalSettings = logcatEchoTrackerDebug.globalSettings;
        logcatEchoTrackerDebug.settingFormat.getClass();
        globalSettings.putString("systemui/logbuffer_echo_overrides", LogcatEchoSettingFormat.stringifyOverrides(listEchoOverrides));
        return Unit.INSTANCE;
    }
}
