package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DragAndDropTargetStateV1 {
    public final BufferedChannel scrollChannel;
    public final LazyGridState state;

    public /* synthetic */ DragAndDropTargetStateV1(LazyGridState lazyGridState, long j, ContentListState contentListState, float f, CoroutineScope coroutineScope, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyGridState, j, contentListState, f, coroutineScope);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0084, code lost:
    
        if (androidx.compose.foundation.gestures.ScrollExtensionsKt.scrollBy(r5, r8, r0) == r1) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0084 -> B:11:0x0031). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processScrollRequests(kotlin.coroutines.Continuation r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof com.android.systemui.communal.ui.compose.DragAndDropTargetStateV1$processScrollRequests$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.communal.ui.compose.DragAndDropTargetStateV1$processScrollRequests$1 r0 = (com.android.systemui.communal.ui.compose.DragAndDropTargetStateV1$processScrollRequests$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.communal.ui.compose.DragAndDropTargetStateV1$processScrollRequests$1 r0 = new com.android.systemui.communal.ui.compose.DragAndDropTargetStateV1$processScrollRequests$1
            r0.<init>(r7, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L49
            if (r2 == r4) goto L3d
            if (r2 != r3) goto L35
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r7 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r7
            java.lang.Object r2 = r0.L$0
            com.android.systemui.communal.ui.compose.DragAndDropTargetStateV1 r2 = (com.android.systemui.communal.ui.compose.DragAndDropTargetStateV1) r2
            kotlin.ResultKt.throwOnFailure(r8)
        L31:
            r6 = r2
            r2 = r7
            r7 = r6
            goto L56
        L35:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3d:
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r7 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r7
            java.lang.Object r2 = r0.L$0
            com.android.systemui.communal.ui.compose.DragAndDropTargetStateV1 r2 = (com.android.systemui.communal.ui.compose.DragAndDropTargetStateV1) r2
            kotlin.ResultKt.throwOnFailure(r8)
            goto L66
        L49:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.BufferedChannel r8 = r7.scrollChannel
            r8.getClass()
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r2 = new kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator
            r2.<init>()
        L56:
            r0.L$0 = r7
            r0.L$1 = r2
            r0.label = r4
            java.lang.Object r8 = r2.hasNext(r0)
            if (r8 != r1) goto L63
            goto L86
        L63:
            r6 = r2
            r2 = r7
            r7 = r6
        L66:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L87
            java.lang.Object r8 = r7.next()
            java.lang.Number r8 = (java.lang.Number) r8
            float r8 = r8.floatValue()
            androidx.compose.foundation.lazy.grid.LazyGridState r5 = r2.state
            r0.L$0 = r2
            r0.L$1 = r7
            r0.label = r3
            java.lang.Object r8 = androidx.compose.foundation.gestures.ScrollExtensionsKt.scrollBy(r5, r8, r0)
            if (r8 != r1) goto L31
        L86:
            return r1
        L87:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.DragAndDropTargetStateV1.processScrollRequests(kotlin.coroutines.Continuation):java.lang.Object");
    }

    private DragAndDropTargetStateV1(LazyGridState lazyGridState, long j, ContentListState contentListState, float f, CoroutineScope coroutineScope) {
        this.state = lazyGridState;
        new CommunalContentModel.WidgetPlaceholder();
        this.scrollChannel = ChannelKt.Channel$default(0, null, null, 7);
    }
}
