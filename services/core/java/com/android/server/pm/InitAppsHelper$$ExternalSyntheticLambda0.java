package com.android.server.pm;

import android.system.ErrnoException;
import android.system.Os;
import android.util.Slog;

import java.nio.file.Path;
import java.util.function.Consumer;

public final /* synthetic */ class InitAppsHelper$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        try {
            Os.chmod(((Path) obj).toString(), 505);
        } catch (ErrnoException e) {
            Slog.w("PackageManager", "Failed to fix an installed app dir mode", e);
        }
    }
}
