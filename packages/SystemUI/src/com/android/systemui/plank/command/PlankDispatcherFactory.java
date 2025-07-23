package com.android.systemui.plank.command;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.util.Map;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PlankDispatcherFactory {
    public Map dependencies;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DispatcherType {
        public static final /* synthetic */ DispatcherType[] $VALUES;
        public static final DispatcherType global_action;
        public static final DispatcherType navigation_bar;
        public static final DispatcherType none;

        static {
            DispatcherType dispatcherType = new DispatcherType(SignalSeverity.NONE, 0);
            none = dispatcherType;
            DispatcherType dispatcherType2 = new DispatcherType("global_action", 1);
            global_action = dispatcherType2;
            DispatcherType dispatcherType3 = new DispatcherType("volume_panel", 2);
            DispatcherType dispatcherType4 = new DispatcherType("navigation_bar", 3);
            navigation_bar = dispatcherType4;
            DispatcherType[] dispatcherTypeArr = {dispatcherType, dispatcherType2, dispatcherType3, dispatcherType4};
            $VALUES = dispatcherTypeArr;
            EnumEntriesKt.enumEntries(dispatcherTypeArr);
        }

        private DispatcherType(String str, int i) {
        }

        public static DispatcherType valueOf(String str) {
            return (DispatcherType) Enum.valueOf(DispatcherType.class, str);
        }

        public static DispatcherType[] values() {
            return (DispatcherType[]) $VALUES.clone();
        }
    }
}
