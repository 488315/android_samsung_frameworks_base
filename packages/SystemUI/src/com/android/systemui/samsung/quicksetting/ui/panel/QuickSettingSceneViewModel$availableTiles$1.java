package com.android.systemui.samsung.quicksetting.ui.panel;

import android.util.Log;
import com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem;
import com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItemKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
final class QuickSettingSceneViewModel$availableTiles$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QuickSettingSceneViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QuickSettingSceneViewModel$availableTiles$1(QuickSettingSceneViewModel quickSettingSceneViewModel, Continuation continuation) {
        super(4, continuation);
        this.this$0 = quickSettingSceneViewModel;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        QuickSettingSceneViewModel$availableTiles$1 quickSettingSceneViewModel$availableTiles$1 = new QuickSettingSceneViewModel$availableTiles$1(this.this$0, (Continuation) obj4);
        quickSettingSceneViewModel$availableTiles$1.L$0 = (String) obj3;
        return quickSettingSceneViewModel$availableTiles$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0046, code lost:
    
        if (r7 == r0) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007b  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        String str;
        List list;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            String str2 = (String) this.L$0;
            if (Intrinsics.areEqual(str2, "")) {
                QuickSettingSceneViewModel quickSettingSceneViewModel = this.this$0;
                this.label = 1;
                StateFlowImpl stateFlowImpl = quickSettingSceneViewModel._qsPanelItems;
                obj = quickSettingSceneViewModel.gridTileInteractor.loadAvailableTiles((List) (stateFlowImpl != null ? stateFlowImpl : null).getValue(), this);
            } else {
                QuickSettingSceneViewModel quickSettingSceneViewModel2 = this.this$0;
                this.L$0 = str2;
                this.label = 2;
                StateFlowImpl stateFlowImpl2 = quickSettingSceneViewModel2._qsPanelItems;
                Object objLoadAvailableTiles = quickSettingSceneViewModel2.gridTileInteractor.loadAvailableTiles((List) (stateFlowImpl2 != null ? stateFlowImpl2 : null).getValue(), this);
                if (objLoadAvailableTiles != coroutineSingletons) {
                    obj = objLoadAvailableTiles;
                    str = str2;
                    ArrayList arrayList = new ArrayList();
                    while (r7.hasNext()) {
                    }
                    list = arrayList;
                }
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            ResultKt.throwOnFailure(obj);
            list = (List) obj;
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str = (String) this.L$0;
            ResultKt.throwOnFailure(obj);
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : (Iterable) obj) {
                String queryString = GridTileItemKt.getQueryString(((QSPanelItem) obj2).gridTileItem);
                Locale locale = Locale.ROOT;
                if (StringsKt__StringsKt.contains(queryString.toLowerCase(locale), str.toLowerCase(locale), false)) {
                    arrayList2.add(obj2);
                }
            }
            list = arrayList2;
        }
        List list2 = list;
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            Log.i("QuickSettingSceneViewModel", "availableTiles " + GridTileItemKt.getQueryString(((QSPanelItem) it.next()).gridTileItem) + " ");
        }
        return list2;
    }
}
