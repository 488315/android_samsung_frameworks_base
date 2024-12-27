package com.android.systemui.log.echo;

import com.android.systemui.log.core.LogLevel;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class LogcatEchoTrackerDebug$setEchoLevel$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LogLevel $level;
    final /* synthetic */ String $name;
    final /* synthetic */ EchoOverrideType $type;
    int label;
    final /* synthetic */ LogcatEchoTrackerDebug this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EchoOverrideType.values().length];
            try {
                iArr[EchoOverrideType.BUFFER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EchoOverrideType.TAG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LogcatEchoTrackerDebug$setEchoLevel$1(LogcatEchoTrackerDebug logcatEchoTrackerDebug, EchoOverrideType echoOverrideType, LogLevel logLevel, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = logcatEchoTrackerDebug;
        this.$type = echoOverrideType;
        this.$level = logLevel;
        this.$name = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LogcatEchoTrackerDebug$setEchoLevel$1(this.this$0, this.$type, this.$level, this.$name, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LogcatEchoTrackerDebug$setEchoLevel$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        LinkedHashMap linkedHashMap;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(this.this$0.bufferOverrides);
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(this.this$0.tagOverrides);
        int i = WhenMappings.$EnumSwitchMapping$0[this.$type.ordinal()];
        if (i == 1) {
            linkedHashMap = linkedHashMap2;
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            linkedHashMap = linkedHashMap3;
        }
        LogLevel logLevel = this.$level;
        if (logLevel != null) {
            linkedHashMap.put(this.$name, logLevel);
        } else {
            linkedHashMap.remove(this.$name);
        }
        this.this$0.bufferOverrides = linkedHashMap2;
        this.this$0.tagOverrides = linkedHashMap3;
        List listEchoOverrides = this.this$0.listEchoOverrides();
        LogcatEchoTrackerDebug logcatEchoTrackerDebug = this.this$0;
        GlobalSettings globalSettings = logcatEchoTrackerDebug.globalSettings;
        logcatEchoTrackerDebug.settingFormat.getClass();
        globalSettings.putString("systemui/logbuffer_echo_overrides", LogcatEchoSettingFormat.stringifyOverrides(listEchoOverrides));
        return Unit.INSTANCE;
    }
}
