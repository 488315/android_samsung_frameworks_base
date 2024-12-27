package com.android.server;

import android.util.Slog;

import java.nio.file.Path;
import java.util.function.Consumer;

public final /* synthetic */ class HeimdAllFsService$$ExternalSyntheticLambda8 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Slog.d("HeimdAllFS", ((Path) obj).toString());
    }
}
