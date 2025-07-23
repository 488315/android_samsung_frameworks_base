package com.android.systemui.compose;

import android.util.Log;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import com.android.systemui.statusbar.commandline.ParseableCommand;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class EnableCommand extends ParseableCommand {
    public EnableCommand() {
        super("enable", null, 2, null);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.compose.EnableCommand$enableCompositionTracing$1] */
    @Override // com.android.systemui.statusbar.commandline.ParseableCommand
    public final void execute(PrintWriter printWriter) {
        Log.i("ComposeTracingStartable", "Enabled Composition tracing");
        printWriter.println("Enabled Composition tracing");
        ?? r1 = new Object() { // from class: com.android.systemui.compose.EnableCommand$enableCompositionTracing$1
        };
        Composer.Companion.getClass();
        ComposerKt.compositionTracer = r1;
    }
}
