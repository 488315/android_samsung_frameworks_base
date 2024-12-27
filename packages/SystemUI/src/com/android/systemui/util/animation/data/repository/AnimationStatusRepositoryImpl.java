package com.android.systemui.util.animation.data.repository;

import android.content.ContentResolver;
import android.os.Handler;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        AnimationStatusRepositoryImpl$areAnimationsEnabled$1 animationStatusRepositoryImpl$areAnimationsEnabled$1 = new AnimationStatusRepositoryImpl$areAnimationsEnabled$1(this, null);
        conflatedCallbackFlow.getClass();
        return FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(animationStatusRepositoryImpl$areAnimationsEnabled$1), this.backgroundDispatcher);
    }
}
