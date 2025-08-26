package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.MutableState;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState;
import com.android.systemui.qs.panels.ui.compose.selection.TileState;
import com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class EditTileKt$rememberTileState$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $canShowRemovalBadge;
    final /* synthetic */ MutableSelectionState $selectionState;
    final /* synthetic */ EditTileViewModel $tile;
    final /* synthetic */ MutableState<TileState> $tileState;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditTileKt$rememberTileState$1$1(MutableState<TileState> mutableState, MutableSelectionState mutableSelectionState, EditTileViewModel editTileViewModel, boolean z, Continuation continuation) {
        super(2, continuation);
        this.$tileState = mutableState;
        this.$selectionState = mutableSelectionState;
        this.$tile = editTileViewModel;
        this.$canShowRemovalBadge = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditTileKt$rememberTileState$1$1(this.$tileState, this.$selectionState, this.$tile, this.$canShowRemovalBadge, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditTileKt$rememberTileState$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        MutableState<TileState> mutableState;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MutableState<TileState> mutableState2 = this.$tileState;
            MutableSelectionState mutableSelectionState = this.$selectionState;
            TileSpec tileSpec = this.$tile.tileSpec;
            TileState tileState = (TileState) mutableState2.getValue();
            boolean z = this.$canShowRemovalBadge;
            this.L$0 = mutableState2;
            this.label = 1;
            Object objTileStateFor = mutableSelectionState.tileStateFor(tileSpec, tileState, z, this);
            if (objTileStateFor == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = objTileStateFor;
            mutableState = mutableState2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            mutableState = (MutableState) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        mutableState.setValue(obj);
        return Unit.INSTANCE;
    }
}
