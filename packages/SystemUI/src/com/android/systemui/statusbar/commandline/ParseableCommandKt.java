package com.android.systemui.statusbar.commandline;

import android.util.IndentingPrintWriter;
import kotlin.jvm.functions.Function0;

public abstract class ParseableCommandKt {
    public static final void indented(IndentingPrintWriter indentingPrintWriter, Function0 function0) {
        indentingPrintWriter.increaseIndent();
        function0.invoke();
        indentingPrintWriter.decreaseIndent();
    }
}
