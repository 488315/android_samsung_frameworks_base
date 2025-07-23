package com.android.bouncer.ui;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class HintInteractor {
    public final HintRepository repository;

    public HintInteractor(HintRepository hintRepository) {
        this.repository = hintRepository;
    }

    public final Object getHint(ContinuationImpl continuationImpl) {
        HintRepositoryImpl hintRepositoryImpl = (HintRepositoryImpl) this.repository;
        hintRepositoryImpl.getClass();
        return BuildersKt.withContext(hintRepositoryImpl.backgroundDispatcher, new HintRepositoryImpl$getHint$2(hintRepositoryImpl, null), continuationImpl);
    }
}
