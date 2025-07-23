package com.android.systemui.util.animation.data.repository;

import android.content.ContentResolver;
import android.os.Handler;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AnimationStatusRepositoryImpl implements AnimationStatusRepository {
    public static final int $stable = 8;
    private final CoroutineDispatcher backgroundDispatcher;
    private final Handler backgroundHandler;
    private final ContentResolver resolver;

    public AnimationStatusRepositoryImpl(ContentResolver contentResolver, Handler handler, CoroutineDispatcher coroutineDispatcher) {
        this.resolver = contentResolver;
        this.backgroundHandler = handler;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.util.animation.data.repository.AnimationStatusRepository
    public Flow areAnimationsEnabled() {
        return FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new AnimationStatusRepositoryImpl$areAnimationsEnabled$1(this, null)), this.backgroundDispatcher);
    }
}
