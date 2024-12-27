package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda46
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return MultiTaskingAppCompatConfiguration.isPresetBlurWallpaperLetterboxed(
                (ActivityRecord) obj);
    }
}
