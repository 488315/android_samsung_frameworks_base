package com.android.systemui.plank.protocol;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class Protocol {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Command {
        public static final /* synthetic */ Command[] $VALUES;
        public static final Command check_api;
        public static final Command none;
        public static final Command start_monitor;
        public static final Command stop_monitor;

        static {
            Command command = new Command(SignalSeverity.NONE, 0);
            none = command;
            Command command2 = new Command("start_monitor", 1);
            start_monitor = command2;
            Command command3 = new Command("stop_monitor", 2);
            stop_monitor = command3;
            Command command4 = new Command("check_api", 3);
            check_api = command4;
            Command[] commandArr = {command, command2, command3, command4};
            $VALUES = commandArr;
            EnumEntriesKt.enumEntries(commandArr);
        }

        private Command(String str, int i) {
        }

        public static Command valueOf(String str) {
            return (Command) Enum.valueOf(Command.class, str);
        }

        public static Command[] values() {
            return (Command[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }
}
