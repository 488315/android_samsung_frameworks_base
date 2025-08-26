package com.android.systemui.compose;

import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.commandline.CommandRegistry;

/* loaded from: classes2.dex */
public final class ComposeTracingStartable implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommandRegistry commandRegistry;

    public ComposeTracingStartable(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Log.i("ComposeTracingStartable", "Set up Compose tracing command");
        this.commandRegistry.registerCommand("composition-tracing", new ComposeTracingStartable$$ExternalSyntheticLambda0());
    }
}
