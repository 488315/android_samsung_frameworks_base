package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator;
import kotlinx.coroutines.channels.ChannelKt;

/* loaded from: classes2.dex */
public final class DragAndDropTargetStateV1 {
    public final BufferedChannel scrollChannel;
    public final LazyGridState state;

    /* renamed from: com.android.systemui.communal.ui.compose.DragAndDropTargetStateV1$processScrollRequests$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DragAndDropTargetStateV1.this.processScrollRequests(this);
        }
    }

    public /* synthetic */ DragAndDropTargetStateV1(LazyGridState lazyGridState, long j, ContentListState contentListState, float f, CoroutineScope coroutineScope, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyGridState, j, contentListState, f, coroutineScope);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0084, code lost:
    
        if (androidx.compose.foundation.gestures.ScrollExtensionsKt.scrollBy(r5, r8, r0) == r1) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0084 -> B:13:0x0031). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object processScrollRequests(Continuation continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        BufferedChannel.BufferedChannelIterator bufferedChannelIterator;
        BufferedChannel.BufferedChannelIterator bufferedChannelIterator2;
        DragAndDropTargetStateV1 dragAndDropTargetStateV1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object objHasNext = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objHasNext);
            BufferedChannel bufferedChannel = this.scrollChannel;
            bufferedChannel.getClass();
            bufferedChannelIterator = bufferedChannel.new BufferedChannelIterator();
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = bufferedChannelIterator;
            anonymousClass1.label = 1;
            objHasNext = bufferedChannelIterator.hasNext(anonymousClass1);
            if (objHasNext != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            bufferedChannelIterator2 = (BufferedChannel.BufferedChannelIterator) anonymousClass1.L$1;
            dragAndDropTargetStateV1 = (DragAndDropTargetStateV1) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objHasNext);
            DragAndDropTargetStateV1 dragAndDropTargetStateV12 = dragAndDropTargetStateV1;
            bufferedChannelIterator = bufferedChannelIterator2;
            this = dragAndDropTargetStateV12;
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = bufferedChannelIterator;
            anonymousClass1.label = 1;
            objHasNext = bufferedChannelIterator.hasNext(anonymousClass1);
            if (objHasNext != coroutineSingletons) {
                BufferedChannel.BufferedChannelIterator bufferedChannelIterator3 = bufferedChannelIterator;
                dragAndDropTargetStateV1 = this;
                bufferedChannelIterator2 = bufferedChannelIterator3;
                if (((Boolean) objHasNext).booleanValue()) {
                    return Unit.INSTANCE;
                }
                float fFloatValue = ((Number) bufferedChannelIterator2.next()).floatValue();
                LazyGridState lazyGridState = dragAndDropTargetStateV1.state;
                anonymousClass1.L$0 = dragAndDropTargetStateV1;
                anonymousClass1.L$1 = bufferedChannelIterator2;
                anonymousClass1.label = 2;
            }
            return coroutineSingletons;
        }
        bufferedChannelIterator2 = (BufferedChannel.BufferedChannelIterator) anonymousClass1.L$1;
        dragAndDropTargetStateV1 = (DragAndDropTargetStateV1) anonymousClass1.L$0;
        ResultKt.throwOnFailure(objHasNext);
        if (((Boolean) objHasNext).booleanValue()) {
        }
    }

    private DragAndDropTargetStateV1(LazyGridState lazyGridState, long j, ContentListState contentListState, float f, CoroutineScope coroutineScope) {
        this.state = lazyGridState;
        new CommunalContentModel.WidgetPlaceholder();
        this.scrollChannel = ChannelKt.Channel$default(0, null, null, 7);
    }
}
