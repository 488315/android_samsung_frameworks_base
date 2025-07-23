package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class UserTileSpecRepository$loadTilesFromSettings$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ UserTileSpecRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserTileSpecRepository$loadTilesFromSettings$1(UserTileSpecRepository userTileSpecRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = userTileSpecRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        UserTileSpecRepository userTileSpecRepository = this.this$0;
        UserTileSpecRepository.Companion companion = UserTileSpecRepository.Companion;
        return userTileSpecRepository.loadTilesFromSettings(0, this);
    }
}
