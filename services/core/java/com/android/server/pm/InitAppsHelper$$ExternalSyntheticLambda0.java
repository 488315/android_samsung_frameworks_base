package com.android.server.pm;

import android.system.ErrnoException;
import android.system.Os;
import android.util.Slog;

import java.nio.file.Path;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
