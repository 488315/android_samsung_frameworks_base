package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class AppCompatSizeCompatModePolicy$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((WindowState) obj).updateGlobalScale();
    }
}
