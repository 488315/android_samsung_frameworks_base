package com.android.systemui.log.echo;

import com.android.systemui.log.core.LogLevel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import kotlin.NoWhenBranchMatchedException;
import kotlin.text.StringsKt__StringsJVMKt;

public final class LogcatEchoSettingFormat {

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

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
            int[] iArr2 = new int[LogLevel.values().length];
            try {
                iArr2[LogLevel.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[LogLevel.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[LogLevel.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[LogLevel.WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[LogLevel.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[LogLevel.WTF.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static String stringifyOverrides(List list) {
        String str;
        String str2;
        StringJoiner stringJoiner = new StringJoiner(";");
        stringJoiner.add("0");
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            LogcatEchoOverride logcatEchoOverride = (LogcatEchoOverride) it.next();
            int i = WhenMappings.$EnumSwitchMapping$0[logcatEchoOverride.type.ordinal()];
            if (i == 1) {
                str = "b";
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                str = "t";
            }
            stringJoiner.add(str);
            stringJoiner.add(StringsKt__StringsJVMKt.replace$default(logcatEchoOverride.name, ";", "\\;"));
            switch (WhenMappings.$EnumSwitchMapping$1[logcatEchoOverride.level.ordinal()]) {
                case 1:
                    str2 = "v";
                    break;
                case 2:
                    str2 = "d";
                    break;
                case 3:
                    str2 = "i";
                    break;
                case 4:
                    str2 = "w";
                    break;
                case 5:
                    str2 = "e";
                    break;
                case 6:
                    str2 = "!";
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            stringJoiner.add(str2);
        }
        return stringJoiner.toString();
    }
}
