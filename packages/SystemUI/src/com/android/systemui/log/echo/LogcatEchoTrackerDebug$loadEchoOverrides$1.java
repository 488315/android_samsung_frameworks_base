package com.android.systemui.log.echo;

import android.util.Log;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
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
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class LogcatEchoTrackerDebug$loadEchoOverrides$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ LogcatEchoTrackerDebug this$0;

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
    /* JADX WARN: Removed duplicated region for block: B:35:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0122  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws NumberFormatException {
        EmptyList<LogcatEchoOverride> emptyList;
        EchoOverrideType echoOverrideType;
        int iHashCode;
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
        List listSplit$default = StringsKt__StringsKt.split$default(string, new String[]{";"}, 2, 2);
        if (listSplit$default.size() != 2) {
            Log.e("EchoFormat", "Unrecognized echo override format: \"" + string + "\"");
            emptyList = EmptyList.INSTANCE;
        } else {
            int i = 0;
            try {
                int i2 = Integer.parseInt((String) listSplit$default.get(0));
                if (i2 == 0) {
                    String str = (String) listSplit$default.get(1);
                    ArrayList arrayList = new ArrayList();
                    List listSplit = new Regex("(?<!\\\\);").split(str);
                    while (i < listSplit.size() && listSplit.size() - i >= 3) {
                        String str2 = (String) listSplit.get(i);
                        int iHashCode2 = str2.hashCode();
                        if (iHashCode2 != 98) {
                            if (iHashCode2 != 116 || !str2.equals("t")) {
                                break;
                            }
                            echoOverrideType = EchoOverrideType.TAG;
                            String strReplace$default = StringsKt__StringsJVMKt.replace$default((String) listSplit.get(i + 1), "\\;", ";");
                            String str3 = (String) listSplit.get(i + 2);
                            iHashCode = str3.hashCode();
                            if (iHashCode != 33) {
                                if (!str3.equals("!")) {
                                    break;
                                }
                                logLevel = LogLevel.WTF;
                                i += 3;
                                arrayList.add(new LogcatEchoOverride(echoOverrideType, strReplace$default, logLevel));
                            } else if (iHashCode == 105) {
                                if (!str3.equals("i")) {
                                    break;
                                }
                                logLevel = LogLevel.INFO;
                                i += 3;
                                arrayList.add(new LogcatEchoOverride(echoOverrideType, strReplace$default, logLevel));
                            } else if (iHashCode == 100) {
                                if (!str3.equals("d")) {
                                    break;
                                }
                                logLevel = LogLevel.DEBUG;
                                i += 3;
                                arrayList.add(new LogcatEchoOverride(echoOverrideType, strReplace$default, logLevel));
                            } else if (iHashCode == 101) {
                                if (!str3.equals("e")) {
                                    break;
                                }
                                logLevel = LogLevel.ERROR;
                                i += 3;
                                arrayList.add(new LogcatEchoOverride(echoOverrideType, strReplace$default, logLevel));
                            } else if (iHashCode != 118) {
                                if (iHashCode != 119 || !str3.equals("w")) {
                                    break;
                                }
                                logLevel = LogLevel.WARNING;
                                i += 3;
                                arrayList.add(new LogcatEchoOverride(echoOverrideType, strReplace$default, logLevel));
                            } else {
                                if (!str3.equals("v")) {
                                    break;
                                }
                                logLevel = LogLevel.VERBOSE;
                                i += 3;
                                arrayList.add(new LogcatEchoOverride(echoOverrideType, strReplace$default, logLevel));
                            }
                        } else {
                            if (!str2.equals("b")) {
                                break;
                            }
                            echoOverrideType = EchoOverrideType.BUFFER;
                            String strReplace$default2 = StringsKt__StringsJVMKt.replace$default((String) listSplit.get(i + 1), "\\;", ";");
                            String str32 = (String) listSplit.get(i + 2);
                            iHashCode = str32.hashCode();
                            if (iHashCode != 33) {
                            }
                        }
                    }
                    emptyList = arrayList;
                } else {
                    ClockEventController$$ExternalSyntheticOutline0.m(i2, "Unrecognized echo override formation version: ", "EchoFormat");
                    emptyList = EmptyList.INSTANCE;
                }
            } catch (NumberFormatException unused) {
                Log.e("EchoFormat", "Unrecognized echo override formation version: " + listSplit$default.get(0));
                emptyList = EmptyList.INSTANCE;
            }
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (LogcatEchoOverride logcatEchoOverride : emptyList) {
            int i3 = WhenMappings.$EnumSwitchMapping$0[logcatEchoOverride.type.ordinal()];
            if (i3 == 1) {
                linkedHashMap = linkedHashMap2;
            } else {
                if (i3 != 2) {
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
