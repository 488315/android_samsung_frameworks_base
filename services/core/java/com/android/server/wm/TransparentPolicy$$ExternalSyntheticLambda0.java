package com.android.server.wm;

import java.util.function.BooleanSupplier;

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
