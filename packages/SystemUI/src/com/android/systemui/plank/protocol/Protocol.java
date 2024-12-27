package com.android.systemui.plank.protocol;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class Protocol {

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
