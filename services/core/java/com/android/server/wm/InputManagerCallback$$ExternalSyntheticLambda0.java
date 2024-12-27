package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class InputManagerCallback$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((DisplayContent) obj).sendNewConfiguration();
    }
}
