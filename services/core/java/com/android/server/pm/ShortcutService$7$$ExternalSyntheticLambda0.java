package com.android.server.pm;

import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class ShortcutService$7$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((ShortcutUser) obj).cancelAllInFlightTasks();
    }
}
