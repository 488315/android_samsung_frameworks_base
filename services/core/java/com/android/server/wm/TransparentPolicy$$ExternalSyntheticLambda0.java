package com.android.server.wm;

import java.util.function.BooleanSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TransparentPolicy$$ExternalSyntheticLambda0
        implements BooleanSupplier {
    public final /* synthetic */ AppCompatConfiguration f$0;

    public /* synthetic */ TransparentPolicy$$ExternalSyntheticLambda0(
            AppCompatConfiguration appCompatConfiguration) {
        this.f$0 = appCompatConfiguration;
    }

    @Override // java.util.function.BooleanSupplier
    public final boolean getAsBoolean() {
        return this.f$0.isTranslucentLetterboxingEnabled();
    }
}
