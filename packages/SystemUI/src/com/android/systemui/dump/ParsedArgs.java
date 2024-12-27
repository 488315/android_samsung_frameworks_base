package com.android.systemui.dump;

import java.util.List;

public final class ParsedArgs {
    public String command;
    public String dumpPriority;
    public boolean listOnly;
    public boolean matchAll;
    public final List nonFlagArgs;
    public boolean proto;
    public final String[] rawArgs;
    public int tailLength;

    public ParsedArgs(String[] strArr, List<String> list) {
        this.rawArgs = strArr;
        this.nonFlagArgs = list;
    }
}
