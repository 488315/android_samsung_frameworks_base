package com.android.wm.shell.sysui;

import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import java.io.PrintWriter;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShellCommandHandler {
    public final TreeMap mDumpables = new TreeMap();
    public final TreeMap mCommands = new TreeMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ShellCommandActionHandler {
        boolean onShellCommand(PrintWriter printWriter, String[] strArr);

        void printShellCommandHelp(PrintWriter printWriter, String str);
    }

    public final void addCommandCallback(String str, ShellCommandActionHandler shellCommandActionHandler, Object obj) {
        this.mCommands.put(str, shellCommandActionHandler);
        if (ShellProtoLogCache.WM_SHELL_INIT_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_INIT, 568095130, 0, "Adding command class %s for %s", str, obj.getClass().getSimpleName());
        }
    }

    public final void addDumpCallback(BiConsumer biConsumer, Object obj) {
        this.mDumpables.put(obj.getClass().getSimpleName(), biConsumer);
        if (ShellProtoLogCache.WM_SHELL_INIT_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_INIT, -913222528, 0, "Adding dump callback for %s", obj.getClass().getSimpleName());
        }
    }
}
