package com.android.systemui.qs.panels.ui.compose;

import android.content.ClipData;
import androidx.compose.foundation.draganddrop.DragAndDropSourceScope;
import androidx.compose.foundation.draganddrop.LegacyDragAndDropSourceNode;
import androidx.compose.foundation.gestures.DragGestureDetectorKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.State;
import androidx.compose.ui.draganddrop.DragAndDropNode;
import androidx.compose.ui.draganddrop.DragAndDropTransferData;
import androidx.compose.ui.unit.IntSizeKt;
import com.android.systemui.qs.panels.shared.model.SizedTile;
import com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DragAndDropStateKt$dragAndDropTileSource$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<DragAndDropState> $dragState$delegate;
    final /* synthetic */ DragType $dragType;
    final /* synthetic */ Function0 $onDragStart;
    final /* synthetic */ SizedTile $sizedTile;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public DragAndDropStateKt$dragAndDropTileSource$1$1(SizedTile sizedTile, DragType dragType, Function0 function0, State<? extends DragAndDropState> state, Continuation continuation) {
        super(2, continuation);
        this.$sizedTile = sizedTile;
        this.$dragType = dragType;
        this.$onDragStart = function0;
        this.$dragState$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DragAndDropStateKt$dragAndDropTileSource$1$1 dragAndDropStateKt$dragAndDropTileSource$1$1 = new DragAndDropStateKt$dragAndDropTileSource$1$1(this.$sizedTile, this.$dragType, this.$onDragStart, this.$dragState$delegate, continuation);
        dragAndDropStateKt$dragAndDropTileSource$1$1.L$0 = obj;
        return dragAndDropStateKt$dragAndDropTileSource$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DragAndDropStateKt$dragAndDropTileSource$1$1) create((DragAndDropSourceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$dragAndDropTileSource$1$1$$ExternalSyntheticLambda0] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object detectDragGesturesAfterLongPress;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final DragAndDropSourceScope dragAndDropSourceScope = (DragAndDropSourceScope) this.L$0;
            final SizedTile sizedTile = this.$sizedTile;
            final DragType dragType = this.$dragType;
            final Function0 function0 = this.$onDragStart;
            final State<DragAndDropState> state = this.$dragState$delegate;
            ?? r3 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$dragAndDropTileSource$1$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    EditTileListState editTileListState = (EditTileListState) ((DragAndDropState) state.getValue());
                    SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) editTileListState.draggedCell$delegate;
                    SizedTile sizedTile2 = SizedTile.this;
                    snapshotMutableStateImpl.setValue(sizedTile2);
                    ((SnapshotMutableStateImpl) editTileListState.dragType$delegate).setValue(dragType);
                    function0.invoke();
                    DragAndDropTransferData dragAndDropTransferData = new DragAndDropTransferData(new ClipData("tilespec", new String[]{"qstile/tilespec"}, new ClipData.Item(((EditTileViewModel) sizedTile2.getTile()).tileSpec.getSpec())), null, 0, 6, null);
                    LegacyDragAndDropSourceNode.AnonymousClass1.C00021 c00021 = (LegacyDragAndDropSourceNode.AnonymousClass1.C00021) dragAndDropSourceScope;
                    ((DragAndDropNode) c00021.$dragAndDropModifierNode).m356drag12SF9DM(dragAndDropTransferData, IntSizeKt.m864toSizeozmzZPI(c00021.$$delegate_0.mo50getSizeYbymL2g()), c00021.this$0.drawDragDecoration);
                    return Unit.INSTANCE;
                }
            };
            DragAndDropStateKt$dragAndDropTileSource$1$1$$ExternalSyntheticLambda1 dragAndDropStateKt$dragAndDropTileSource$1$1$$ExternalSyntheticLambda1 = new DragAndDropStateKt$dragAndDropTileSource$1$1$$ExternalSyntheticLambda1();
            this.label = 1;
            detectDragGesturesAfterLongPress = DragGestureDetectorKt.detectDragGesturesAfterLongPress(dragAndDropSourceScope, r3, new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGesturesAfterLongPress$3
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            }, new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGesturesAfterLongPress$4
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            }, dragAndDropStateKt$dragAndDropTileSource$1$1$$ExternalSyntheticLambda1, this);
            if (detectDragGesturesAfterLongPress == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
