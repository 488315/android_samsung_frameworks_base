package com.android.systemui.statusbar.commandline;

import android.util.IndentingPrintWriter;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class ParseableCommandKt {
    public static final void indented(IndentingPrintWriter indentingPrintWriter, Function0 function0) {
        indentingPrintWriter.increaseIndent();
        function0.invoke();
        indentingPrintWriter.decreaseIndent();
    }
}
