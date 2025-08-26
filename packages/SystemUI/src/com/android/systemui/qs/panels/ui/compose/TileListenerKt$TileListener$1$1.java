package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.runtime.SnapshotStateKt;
import com.android.systemui.qs.panels.ui.viewmodel.TileViewModel;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes2.dex */
final class TileListenerKt$TileListener$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $listeningEnabled;
    final /* synthetic */ List<TileViewModel> $tiles;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileListenerKt$TileListener$1$1(List<TileViewModel> list, Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.$tiles = list;
        this.$listeningEnabled = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TileListenerKt$TileListener$1$1(this.$tiles, this.$listeningEnabled, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileListenerKt$TileListener$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004e A[LOOP:0: B:18:0x0048->B:20:0x004e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0071 A[LOOP:1: B:27:0x006b->B:29:0x0071, LOOP_END] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object obj2;
        Throwable th;
        Iterator<T> it;
        Iterator<T> it2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            obj2 = this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                it2 = this.$tiles.iterator();
                while (it2.hasNext()) {
                    ((TileViewModel) it2.next()).tile.setListening(obj2, false);
                }
                return Unit.INSTANCE;
            } catch (Throwable th2) {
                th = th2;
                it = this.$tiles.iterator();
                while (it.hasNext()) {
                    ((TileViewModel) it.next()).tile.setListening(obj2, false);
                }
                throw th;
            }
        }
        ResultKt.throwOnFailure(obj);
        final Object obj3 = new Object();
        try {
            final Function0 function0 = this.$listeningEnabled;
            SafeFlow safeFlowSnapshotFlow = SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.TileListenerKt$TileListener$1$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Boolean bool = (Boolean) function0.invoke();
                    bool.booleanValue();
                    return bool;
                }
            });
            final List<TileViewModel> list = this.$tiles;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.panels.ui.compose.TileListenerKt$TileListener$1$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj4, Continuation continuation) {
                    boolean zBooleanValue = ((Boolean) obj4).booleanValue();
                    for (TileViewModel tileViewModel : list) {
                        Object obj5 = obj3;
                        if (zBooleanValue) {
                            tileViewModel.tile.setListening(obj5, true);
                        } else {
                            tileViewModel.tile.setListening(obj5, false);
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            this.L$0 = obj3;
            this.label = 1;
            if (safeFlowSnapshotFlow.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj2 = obj3;
            it2 = this.$tiles.iterator();
            while (it2.hasNext()) {
            }
            return Unit.INSTANCE;
        } catch (Throwable th3) {
            obj2 = obj3;
            th = th3;
            it = this.$tiles.iterator();
            while (it.hasNext()) {
            }
            throw th;
        }
    }
}
