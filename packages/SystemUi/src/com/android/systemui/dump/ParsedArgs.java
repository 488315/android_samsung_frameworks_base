package com.android.systemui.dump;

import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ParsedArgs {
    public String command;
    public String dumpPriority;
    public boolean listOnly;
    public final List nonFlagArgs;
    public boolean proto;
    public final String[] rawArgs;
    public int tailLength;

    public ParsedArgs(String[] strArr, List<String> list) {
        this.rawArgs = strArr;
        this.nonFlagArgs = list;
    }
}
