package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GridDragDropStateV1 extends GridDragDropStateInternal {
    public final ContentListState contentListState;
    public Object previousTargetItemKey;
    public final CoroutineScope scope;
    public final BufferedChannel scrollChannel;
    public final CommunalContentModel.Spacer spacer;
    public Integer spacerIndex;
    public final Function1 updateDragPositionForRemove;

    public GridDragDropStateV1(LazyGridState lazyGridState, ContentListState contentListState, CoroutineScope coroutineScope, Function1 function1) {
        super(lazyGridState);
        this.contentListState = contentListState;
        this.scope = coroutineScope;
        this.updateDragPositionForRemove = function1;
        this.scrollChannel = ChannelKt.Channel$default(0, null, null, 7);
        this.spacer = new CommunalContentModel.Spacer(CommunalContentSize.Responsive.m1074boximpl(1));
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x005b, code lost:
    
        if (androidx.compose.foundation.gestures.ScrollExtensionsKt.scrollBy(r2, r6, r0) == r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0048, code lost:
    
        if (r6 != r1) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005d, code lost:
    
        return r1;
     */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x005b -> B:17:0x003e). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processScrollRequests(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.communal.ui.compose.GridDragDropStateV1$processScrollRequests$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.communal.ui.compose.GridDragDropStateV1$processScrollRequests$1 r0 = (com.android.systemui.communal.ui.compose.GridDragDropStateV1$processScrollRequests$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.communal.ui.compose.GridDragDropStateV1$processScrollRequests$1 r0 = new com.android.systemui.communal.ui.compose.GridDragDropStateV1$processScrollRequests$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3b
            if (r2 == r4) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            com.android.systemui.communal.ui.compose.GridDragDropStateV1 r5 = (com.android.systemui.communal.ui.compose.GridDragDropStateV1) r5
            goto L3b
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.communal.ui.compose.GridDragDropStateV1 r5 = (com.android.systemui.communal.ui.compose.GridDragDropStateV1) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4b
        L3b:
            kotlin.ResultKt.throwOnFailure(r6)
        L3e:
            kotlinx.coroutines.channels.BufferedChannel r6 = r5.scrollChannel
            r0.L$0 = r5
            r0.label = r4
            java.lang.Object r6 = r6.receive(r0)
            if (r6 != r1) goto L4b
            goto L5d
        L4b:
            java.lang.Number r6 = (java.lang.Number) r6
            float r6 = r6.floatValue()
            androidx.compose.foundation.lazy.grid.LazyGridState r2 = r5.state
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r6 = androidx.compose.foundation.gestures.ScrollExtensionsKt.scrollBy(r2, r6, r0)
            if (r6 != r1) goto L3e
        L5d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.GridDragDropStateV1.processScrollRequests(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
