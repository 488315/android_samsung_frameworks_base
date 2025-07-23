package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CurrentTilesInteractorImpl$tilesUpdatedFlow$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CurrentTilesInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurrentTilesInteractorImpl$tilesUpdatedFlow$1(CurrentTilesInteractorImpl currentTilesInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = currentTilesInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CurrentTilesInteractorImpl$tilesUpdatedFlow$1 currentTilesInteractorImpl$tilesUpdatedFlow$1 = new CurrentTilesInteractorImpl$tilesUpdatedFlow$1(this.this$0, continuation);
        currentTilesInteractorImpl$tilesUpdatedFlow$1.L$0 = obj;
        return currentTilesInteractorImpl$tilesUpdatedFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CurrentTilesInteractorImpl$tilesUpdatedFlow$1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
        currentTilesInteractorImpl.hiddenTilesByKnoxInTopBottomBar.clear();
        StateFlowImpl stateFlowImpl = currentTilesInteractorImpl._userContext;
        int i = ((Context) stateFlowImpl.getValue()).getResources().getConfiguration().orientation;
        ReadonlyStateFlow readonlyStateFlow = currentTilesInteractorImpl.userContext;
        Context context = (Context) readonlyStateFlow.$$delegate_0.getValue();
        SecQSPanelResourcePicker secQSPanelResourcePicker = currentTilesInteractorImpl.resourcePicker;
        String topBarTileList = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTopBarTileList(i, context);
        StateFlow stateFlow = readonlyStateFlow.$$delegate_0;
        Context context2 = (Context) stateFlow.getValue();
        SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = secQSPanelResourcePicker.resourcePickHelper;
        String bottomBarTileList = secQSPanelResourcePickHelper.getTargetPicker().getBottomBarTileList(i, context2);
        String string = ((Context) stateFlow.getValue()).getString(R.string.sec_brightness_volume_bar_tiles_default);
        String smartViewBarTileList = secQSPanelResourcePickHelper.getTargetPicker().getSmartViewBarTileList(i, (Context) stateFlow.getValue());
        int i2 = 0;
        List split$default = StringsKt__StringsKt.split$default(topBarTileList, new String[]{","}, 0, 6);
        Resources resources = ((Context) stateFlowImpl.getValue()).getResources();
        CurrentTilesInteractorImpl.Companion companion = CurrentTilesInteractorImpl.Companion;
        List access$toTileList = CurrentTilesInteractorImpl.Companion.access$toTileList(companion, split$default, resources);
        List access$toTileList2 = CurrentTilesInteractorImpl.Companion.access$toTileList(companion, StringsKt__StringsKt.split$default(bottomBarTileList, new String[]{","}, 0, 6), ((Context) stateFlowImpl.getValue()).getResources());
        List access$toTileList3 = CurrentTilesInteractorImpl.Companion.access$toTileList(companion, StringsKt__StringsKt.split$default(string, new String[]{","}, 0, 6), ((Context) stateFlowImpl.getValue()).getResources());
        List access$toTileList4 = CurrentTilesInteractorImpl.Companion.access$toTileList(companion, StringsKt__StringsKt.split$default(smartViewBarTileList, new String[]{","}, 0, 6), ((Context) stateFlowImpl.getValue()).getResources());
        ArrayList arrayList = currentTilesInteractorImpl.hiddenTilesByKnoxInTopBottomBar;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = (ArrayList) access$toTileList;
        int size = arrayList3.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj2 = arrayList3.get(i3);
            i3++;
            if (list.contains((TileSpec) obj2)) {
                arrayList2.add(obj2);
            }
        }
        arrayList.addAll(arrayList2);
        ArrayList arrayList4 = currentTilesInteractorImpl.hiddenTilesByKnoxInTopBottomBar;
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = (ArrayList) access$toTileList2;
        int size2 = arrayList6.size();
        int i4 = 0;
        while (i4 < size2) {
            Object obj3 = arrayList6.get(i4);
            i4++;
            if (list.contains((TileSpec) obj3)) {
                arrayList5.add(obj3);
            }
        }
        arrayList4.addAll(arrayList5);
        ArrayList arrayList7 = currentTilesInteractorImpl.hiddenTilesByKnoxInTopBottomBar;
        ArrayList arrayList8 = new ArrayList();
        ArrayList arrayList9 = (ArrayList) access$toTileList3;
        int size3 = arrayList9.size();
        int i5 = 0;
        while (i5 < size3) {
            Object obj4 = arrayList9.get(i5);
            i5++;
            if (list.contains((TileSpec) obj4)) {
                arrayList8.add(obj4);
            }
        }
        arrayList7.addAll(arrayList8);
        ArrayList arrayList10 = currentTilesInteractorImpl.hiddenTilesByKnoxInTopBottomBar;
        ArrayList arrayList11 = new ArrayList();
        ArrayList arrayList12 = (ArrayList) access$toTileList4;
        int size4 = arrayList12.size();
        while (i2 < size4) {
            Object obj5 = arrayList12.get(i2);
            i2++;
            if (list.contains((TileSpec) obj5)) {
                arrayList11.add(obj5);
            }
        }
        arrayList10.addAll(arrayList11);
        Log.d("CurrentTilesInteractor", "hiddenTilesByKnoxInTopBottomBar list=" + currentTilesInteractorImpl.hiddenTilesByKnoxInTopBottomBar.clone());
        return Unit.INSTANCE;
    }
}
