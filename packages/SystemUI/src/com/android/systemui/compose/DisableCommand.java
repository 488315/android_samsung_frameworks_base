package com.android.systemui.compose;

import android.util.Log;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import com.android.systemui.statusbar.commandline.ParseableCommand;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisableCommand extends ParseableCommand {
    public DisableCommand() {
        super("disable", null, 2, null);
    }

    @Override // com.android.systemui.statusbar.commandline.ParseableCommand
    public final void execute(PrintWriter printWriter) {
        Log.i("ComposeTracingStartable", "Disabled Composition tracing");
        printWriter.println("Disabled Composition tracing");
        Composer.Companion.getClass();
        ComposerKt.compositionTracer = null;
    }
}
