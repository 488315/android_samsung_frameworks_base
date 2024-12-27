package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class UserTileSpecRepository$loadTilesFromSettingsAndParse$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ UserTileSpecRepository this$0;

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
