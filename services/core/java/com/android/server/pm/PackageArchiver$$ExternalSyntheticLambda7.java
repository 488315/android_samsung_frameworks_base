package com.android.server.pm;

import android.util.secutil.Slog;

import java.util.function.BiConsumer;

public final /* synthetic */ class PackageArchiver$$ExternalSyntheticLambda7 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        String str = (String) obj;
        Integer num = (Integer) obj2;
        switch (this.$r8$classId) {
            case 0:
                Slog.d("PackageArchiverService", "pkg-version: " + str + ", " + num);
                break;
            default:
                Slog.d("PackageArchiverService", "pkg-version: " + str + ", " + num);
                break;
        }
    }
}
