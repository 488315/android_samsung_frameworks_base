package com.android.server.am;

import java.util.function.Consumer;

public final /* synthetic */ class PhantomProcessList$$ExternalSyntheticLambda3
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((PhantomProcessRecord) obj).killLocked("Trimming Orphan processes", true);
    }
}
