package com.android.systemui.log.echo;

import android.util.Log;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogLevel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class LogcatEchoTrackerDebug$loadEchoOverrides$1 extends SuspendLambda implements Function2 {
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
    public LogcatEchoTrackerDebug$loadEchoOverrides$1(LogcatEchoTrackerDebug logcatEchoTrackerDebug, Continuation continuation) {
        super(2, continuation);
        this.this$0 = logcatEchoTrackerDebug;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LogcatEchoTrackerDebug$loadEchoOverrides$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LogcatEchoTrackerDebug$loadEchoOverrides$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        EmptyList<LogcatEchoOverride> emptyList;
        EchoOverrideType echoOverrideType;
        LogLevel logLevel;
        LinkedHashMap linkedHashMap;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String string = this.this$0.globalSettings.getString("systemui/logbuffer_echo_overrides");
        if (string == null) {
            return Unit.INSTANCE;
        }
        this.this$0.settingFormat.getClass();
        List split$default = StringsKt__StringsKt.split$default(string, new String[]{";"}, 2, 2);
        if (split$default.size() != 2) {
            Log.e("EchoFormat", "Unrecognized echo override format: \"" + string + "\"");
            emptyList = EmptyList.INSTANCE;
        } else {
            int i = 0;
            try {
                int parseInt = Integer.parseInt((String) split$default.get(0));
                if (parseInt == 0) {
                    String str = (String) split$default.get(1);
                    ArrayList arrayList = new ArrayList();
                    List split = new Regex("(?<!\\\\);").split(str);
                    while (i < split.size() && split.size() - i >= 3) {
                        String str2 = (String) split.get(i);
                        if (!Intrinsics.areEqual(str2, "b")) {
                            if (!Intrinsics.areEqual(str2, "t")) {
                                break;
                            }
                            echoOverrideType = EchoOverrideType.TAG;
                        } else {
                            echoOverrideType = EchoOverrideType.BUFFER;
                        }
                        String replace$default = StringsKt__StringsJVMKt.replace$default((String) split.get(i + 1), "\\;", ";");
                        String str3 = (String) split.get(i + 2);
                        int hashCode = str3.hashCode();
                        if (hashCode == 33) {
                            if (!str3.equals("!")) {
                                break;
                            }
                            logLevel = LogLevel.WTF;
                            i += 3;
                            arrayList.add(new LogcatEchoOverride(echoOverrideType, replace$default, logLevel));
                        } else if (hashCode == 105) {
                            if (!str3.equals("i")) {
                                break;
                            }
                            logLevel = LogLevel.INFO;
                            i += 3;
                            arrayList.add(new LogcatEchoOverride(echoOverrideType, replace$default, logLevel));
                        } else if (hashCode == 100) {
                            if (!str3.equals("d")) {
                                break;
                            }
                            logLevel = LogLevel.DEBUG;
                            i += 3;
                            arrayList.add(new LogcatEchoOverride(echoOverrideType, replace$default, logLevel));
                        } else if (hashCode == 101) {
                            if (!str3.equals("e")) {
                                break;
                            }
                            logLevel = LogLevel.ERROR;
                            i += 3;
                            arrayList.add(new LogcatEchoOverride(echoOverrideType, replace$default, logLevel));
                        } else if (hashCode != 118) {
                            if (hashCode != 119 || !str3.equals("w")) {
                                break;
                            }
                            logLevel = LogLevel.WARNING;
                            i += 3;
                            arrayList.add(new LogcatEchoOverride(echoOverrideType, replace$default, logLevel));
                        } else {
                            if (!str3.equals("v")) {
                                break;
                            }
                            logLevel = LogLevel.VERBOSE;
                            i += 3;
                            arrayList.add(new LogcatEchoOverride(echoOverrideType, replace$default, logLevel));
                        }
                    }
                    emptyList = arrayList;
                } else {
                    ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(parseInt, "Unrecognized echo override formation version: ", "EchoFormat");
                    emptyList = EmptyList.INSTANCE;
                }
            } catch (NumberFormatException unused) {
                Log.e("EchoFormat", "Unrecognized echo override formation version: " + split$default.get(0));
                emptyList = EmptyList.INSTANCE;
            }
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (LogcatEchoOverride logcatEchoOverride : emptyList) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[logcatEchoOverride.type.ordinal()];
            if (i2 == 1) {
                linkedHashMap = linkedHashMap2;
            } else {
                if (i2 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                linkedHashMap = linkedHashMap3;
            }
            linkedHashMap.put(logcatEchoOverride.name, logcatEchoOverride.level);
        }
        this.this$0.bufferOverrides = linkedHashMap2;
        this.this$0.tagOverrides = linkedHashMap3;
        return Unit.INSTANCE;
    }
}
