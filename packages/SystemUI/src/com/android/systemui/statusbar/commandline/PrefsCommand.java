package com.android.systemui.statusbar.commandline;

import android.content.Context;
import com.android.systemui.Prefs;
import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class PrefsCommand implements Command {
    public PrefsCommand(Context context) {
    }

    @Override // com.android.systemui.statusbar.commandline.Command
    public final void execute(PrintWriter printWriter, List list) {
        if (list.isEmpty()) {
            AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "usage: prefs <command> [args]", "Available commands:", "  list-prefs", "  set-pref <pref name> <value>");
            return;
        }
        if (!Intrinsics.areEqual((String) list.get(0), "list-prefs")) {
            AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "usage: prefs <command> [args]", "Available commands:", "  list-prefs", "  set-pref <pref name> <value>");
            return;
        }
        printWriter.println("Available keys:");
        ArrayIterator arrayIterator = new ArrayIterator(Prefs.Key.class.getDeclaredFields());
        while (arrayIterator.hasNext()) {
            Field field = (Field) arrayIterator.next();
            printWriter.print("  ");
            printWriter.println(field.get(Prefs.Key.class));
        }
    }
}
