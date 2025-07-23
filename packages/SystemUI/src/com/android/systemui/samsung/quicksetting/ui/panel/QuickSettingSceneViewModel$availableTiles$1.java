package com.android.systemui.samsung.quicksetting.ui.panel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0046, code lost:
    
        if (r7 == r0) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:9:0x007b  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 1
            r3 = 2
            if (r1 == 0) goto L20
            if (r1 == r2) goto L1c
            if (r1 != r3) goto L14
            java.lang.Object r6 = r6.L$0
            java.lang.String r6 = (java.lang.String) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L6a
        L14:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L1c:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L49
        L20:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            java.lang.String r7 = (java.lang.String) r7
            java.lang.String r1 = ""
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r1)
            r4 = 0
            if (r1 == 0) goto L4c
            com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel r7 = r6.this$0
            r6.label = r2
            kotlinx.coroutines.flow.StateFlowImpl r1 = r7._qsPanelItems
            if (r1 != 0) goto L39
            goto L3a
        L39:
            r4 = r1
        L3a:
            java.lang.Object r1 = r4.getValue()
            java.util.List r1 = (java.util.List) r1
            com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor r7 = r7.gridTileInteractor
            java.lang.Object r7 = r7.loadAvailableTiles(r1, r6)
            if (r7 != r0) goto L49
            goto L66
        L49:
            java.util.List r7 = (java.util.List) r7
            goto L9e
        L4c:
            com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel r1 = r6.this$0
            r6.L$0 = r7
            r6.label = r3
            kotlinx.coroutines.flow.StateFlowImpl r2 = r1._qsPanelItems
            if (r2 != 0) goto L57
            goto L58
        L57:
            r4 = r2
        L58:
            java.lang.Object r2 = r4.getValue()
            java.util.List r2 = (java.util.List) r2
            com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor r1 = r1.gridTileInteractor
            java.lang.Object r6 = r1.loadAvailableTiles(r2, r6)
            if (r6 != r0) goto L67
        L66:
            return r0
        L67:
            r5 = r7
            r7 = r6
            r6 = r5
        L6a:
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.Iterator r7 = r7.iterator()
        L75:
            boolean r1 = r7.hasNext()
            if (r1 == 0) goto L9d
            java.lang.Object r1 = r7.next()
            r2 = r1
            com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem r2 = (com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem) r2
            com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem r2 = r2.gridTileItem
            java.lang.String r2 = com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItemKt.getQueryString(r2)
            java.util.Locale r3 = java.util.Locale.ROOT
            java.lang.String r2 = r2.toLowerCase(r3)
            java.lang.String r3 = r6.toLowerCase(r3)
            r4 = 0
            boolean r2 = kotlin.text.StringsKt__StringsKt.contains(r2, r3, r4)
            if (r2 == 0) goto L75
            r0.add(r1)
            goto L75
        L9d:
            r7 = r0
        L9e:
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.Iterator r6 = r7.iterator()
        La4:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto Lcf
            java.lang.Object r0 = r6.next()
            com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem r0 = (com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem) r0
            com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem r0 = r0.gridTileItem
            java.lang.String r0 = com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItemKt.getQueryString(r0)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "availableTiles "
            r1.<init>(r2)
            r1.append(r0)
            java.lang.String r0 = " "
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = "QuickSettingSceneViewModel"
            android.util.Log.i(r1, r0)
            goto La4
        Lcf:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$availableTiles$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
