package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class UserTileSpecRepository$loadTilesFromSettingsAndParse$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ UserTileSpecRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserTileSpecRepository$loadTilesFromSettingsAndParse$1(UserTileSpecRepository userTileSpecRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = userTileSpecRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        UserTileSpecRepository userTileSpecRepository = this.this$0;
        UserTileSpecRepository.Companion companion = UserTileSpecRepository.Companion;
        return userTileSpecRepository.loadTilesFromSettingsAndParse(0, this);
    }
}
