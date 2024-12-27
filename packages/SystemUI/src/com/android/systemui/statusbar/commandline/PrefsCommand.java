package com.android.systemui.statusbar.commandline;

import android.content.Context;
import com.android.systemui.Prefs;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

public final class PrefsCommand implements Command {
    public PrefsCommand(Context context) {
    }

    @Override // com.android.systemui.statusbar.commandline.Command
    public final void execute(PrintWriter printWriter, List list) {
        if (list.isEmpty()) {
            printWriter.println("usage: prefs <command> [args]");
            printWriter.println("Available commands:");
            printWriter.println("  list-prefs");
            printWriter.println("  set-pref <pref name> <value>");
            return;
        }
        if (!Intrinsics.areEqual((String) list.get(0), "list-prefs")) {
            printWriter.println("usage: prefs <command> [args]");
            printWriter.println("Available commands:");
            printWriter.println("  list-prefs");
            printWriter.println("  set-pref <pref name> <value>");
            return;
        }
        printWriter.println("Available keys:");
        for (Field field : Prefs.Key.class.getDeclaredFields()) {
            printWriter.print("  ");
            printWriter.println(field.get(Prefs.Key.class));
        }
    }
}
