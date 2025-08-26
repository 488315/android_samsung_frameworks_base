package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.gestures.ScrollExtensionsKt;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* loaded from: classes2.dex */
public final class GridDragDropStateV1 extends GridDragDropStateInternal {
    public final ContentListState contentListState;
    public Object previousTargetItemKey;
    public final CoroutineScope scope;
    public final BufferedChannel scrollChannel;
    public final CommunalContentModel.Spacer spacer;
    public Integer spacerIndex;
    public final Function1 updateDragPositionForRemove;

    /* renamed from: com.android.systemui.communal.ui.compose.GridDragDropStateV1$processScrollRequests$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return GridDragDropStateV1.this.processScrollRequests(this);
        }
    }

    public GridDragDropStateV1(LazyGridState lazyGridState, ContentListState contentListState, CoroutineScope coroutineScope, Function1 function1) {
        super(lazyGridState);
        this.contentListState = contentListState;
        this.scope = coroutineScope;
        this.updateDragPositionForRemove = function1;
        this.scrollChannel = ChannelKt.Channel$default(0, null, null, 7);
        this.spacer = new CommunalContentModel.Spacer(CommunalContentSize.Responsive.m1076boximpl(1));
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003e A[PHI: r5
      0x003e: PHI (r5v2 'this' com.android.systemui.communal.ui.compose.GridDragDropStateV1) = 
      (r5v1 'this' com.android.systemui.communal.ui.compose.GridDragDropStateV1)
      (r5v3 'this' com.android.systemui.communal.ui.compose.GridDragDropStateV1)
     binds: [B:16:0x003b, B:21:0x005b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004b A[PHI: r5 r6
      0x004b: PHI (r5v3 'this' com.android.systemui.communal.ui.compose.GridDragDropStateV1) = 
      (r5v2 'this' com.android.systemui.communal.ui.compose.GridDragDropStateV1)
      (r5v5 'this' com.android.systemui.communal.ui.compose.GridDragDropStateV1)
     binds: [B:18:0x0048, B:15:0x0033] A[DONT_GENERATE, DONT_INLINE]
      0x004b: PHI (r6v4 java.lang.Object) = (r6v3 java.lang.Object), (r6v1 java.lang.Object) binds: [B:18:0x0048, B:15:0x0033] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x005b -> B:17:0x003e). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object processScrollRequests(Continuation continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object objReceive = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 != 0) {
            if (i2 == 1) {
                this = (GridDragDropStateV1) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objReceive);
                float fFloatValue = ((Number) objReceive).floatValue();
                LazyGridState lazyGridState = this.state;
                anonymousClass1.L$0 = this;
                anonymousClass1.label = 2;
                if (ScrollExtensionsKt.scrollBy(lazyGridState, fFloatValue, anonymousClass1) != coroutineSingletons) {
                    BufferedChannel bufferedChannel = this.scrollChannel;
                    anonymousClass1.L$0 = this;
                    anonymousClass1.label = 1;
                    objReceive = bufferedChannel.receive(anonymousClass1);
                    if (objReceive != coroutineSingletons) {
                        float fFloatValue2 = ((Number) objReceive).floatValue();
                        LazyGridState lazyGridState2 = this.state;
                        anonymousClass1.L$0 = this;
                        anonymousClass1.label = 2;
                        if (ScrollExtensionsKt.scrollBy(lazyGridState2, fFloatValue2, anonymousClass1) != coroutineSingletons) {
                        }
                    }
                }
                return coroutineSingletons;
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (GridDragDropStateV1) anonymousClass1.L$0;
        }
        ResultKt.throwOnFailure(objReceive);
        BufferedChannel bufferedChannel2 = this.scrollChannel;
        anonymousClass1.L$0 = this;
        anonymousClass1.label = 1;
        objReceive = bufferedChannel2.receive(anonymousClass1);
        if (objReceive != coroutineSingletons) {
        }
        return coroutineSingletons;
    }
}
