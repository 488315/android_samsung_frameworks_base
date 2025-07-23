package com.android.wm.shell.sysui;

import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.io.PrintWriter;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShellCommandHandler {
    public final TreeMap mDumpables = new TreeMap();
    public final TreeMap mCommands = new TreeMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ShellCommandActionHandler {
        boolean onShellCommand(PrintWriter printWriter, String[] strArr);

        void printShellCommandHelp(PrintWriter printWriter, String str);
    }

    public final void addCommandCallback(String str, ShellCommandActionHandler shellCommandActionHandler, Object obj) {
        this.mCommands.put(str, shellCommandActionHandler);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_INIT_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_INIT, -7021501875761171703L, 0, str, obj.getClass().getSimpleName());
        }
    }

    public final void addDumpCallback(BiConsumer biConsumer, Object obj) {
        this.mDumpables.put(obj.getClass().getSimpleName(), biConsumer);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_INIT_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_INIT, 5564691995529851186L, 0, obj.getClass().getSimpleName());
        }
    }
}
