package com.android.systemui.qs.tiles.impl.custom.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class CustomTileRepositoryImpl$updateTile$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CustomTileRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileRepositoryImpl$updateTile$1(CustomTileRepositoryImpl customTileRepositoryImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = customTileRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        CustomTileRepositoryImpl customTileRepositoryImpl = this.this$0;
        int i = CustomTileRepositoryImpl.$r8$clinit;
        return customTileRepositoryImpl.updateTile(null, false, null, this);
    }
}
