package com.android.bouncer.ui;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;

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
