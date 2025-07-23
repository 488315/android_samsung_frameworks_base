package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import com.android.systemui.qs.QSEditEvent;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.TileSpecKt;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class EditModeViewModel$setTiles$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<TileSpec> $currentTiles;
    final /* synthetic */ List<TileSpec> $tileSpecs;
    int label;
    final /* synthetic */ EditModeViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public EditModeViewModel$setTiles$1(EditModeViewModel editModeViewModel, List<? extends TileSpec> list, List<? extends TileSpec> list2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = editModeViewModel;
        this.$currentTiles = list;
        this.$tileSpecs = list2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditModeViewModel$setTiles$1(this.this$0, this.$currentTiles, this.$tileSpecs, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditModeViewModel$setTiles$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final EditModeViewModel editModeViewModel = this.this$0;
        final List<TileSpec> list = this.$currentTiles;
        final List<TileSpec> list2 = this.$tileSpecs;
        editModeViewModel.getClass();
        DiffUtil.calculateDiff(new DiffCallback(list, list2)).dispatchUpdatesTo(new ListUpdateCallback() { // from class: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$calculateDiffsAndEmitUiEvents$1
            @Override // androidx.recyclerview.widget.ListUpdateCallback
            public final void onInserted(int i, int i2) {
                TileSpec tileSpec = (TileSpec) CollectionsKt___CollectionsKt.getOrNull(i, list2);
                if (tileSpec != null) {
                    editModeViewModel.uiEventLogger.logWithPosition(QSEditEvent.QS_EDIT_ADD, 0, TileSpecKt.getMetricSpec(tileSpec), i);
                }
            }

            @Override // androidx.recyclerview.widget.ListUpdateCallback
            public final void onMoved(int i, int i2) {
                TileSpec tileSpec = (TileSpec) CollectionsKt___CollectionsKt.getOrNull(i, list);
                if (tileSpec != null) {
                    editModeViewModel.uiEventLogger.logWithPosition(QSEditEvent.QS_EDIT_MOVE, 0, TileSpecKt.getMetricSpec(tileSpec), i2);
                }
            }

            @Override // androidx.recyclerview.widget.ListUpdateCallback
            public final void onRemoved(int i, int i2) {
                TileSpec tileSpec = (TileSpec) CollectionsKt___CollectionsKt.getOrNull(i, list);
                if (tileSpec != null) {
                    editModeViewModel.uiEventLogger.log(QSEditEvent.QS_EDIT_REMOVE, 0, TileSpecKt.getMetricSpec(tileSpec));
                }
            }

            @Override // androidx.recyclerview.widget.ListUpdateCallback
            public final void onChanged(int i, int i2, Object obj2) {
            }
        });
        return Unit.INSTANCE;
    }
}
