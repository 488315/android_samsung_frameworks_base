package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.Swipe;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.shade.ui.viewmodel.ShadeUserActionsKt;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
final class LockscreenUserActionsViewModel$hydrateActions$2$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public LockscreenUserActionsViewModel$hydrateActions$2$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
        LockscreenUserActionsViewModel$hydrateActions$2$1 lockscreenUserActionsViewModel$hydrateActions$2$1 = new LockscreenUserActionsViewModel$hydrateActions$2$1((Continuation) obj4);
        lockscreenUserActionsViewModel$hydrateActions$2$1.Z$0 = zBooleanValue;
        lockscreenUserActionsViewModel$hydrateActions$2$1.L$0 = (ShadeMode) obj2;
        lockscreenUserActionsViewModel$hydrateActions$2$1.Z$1 = zBooleanValue2;
        return lockscreenUserActionsViewModel$hydrateActions$2$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Pair[] pairArrDualShadeActions;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        ShadeMode shadeMode = (ShadeMode) this.L$0;
        boolean z2 = this.Z$1;
        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        if (z) {
            Swipe.Companion.getClass();
            listBuilderCreateListBuilder.add(Swipe.Up.to(Scenes.Gone));
        } else {
            Swipe.Companion.getClass();
            listBuilderCreateListBuilder.add(Swipe.Up.to(Overlays.Bouncer));
        }
        if (Intrinsics.areEqual(shadeMode, ShadeMode.Single.INSTANCE)) {
            pairArrDualShadeActions = ShadeUserActionsKt.singleShadeActions$default(2, !z2);
        } else if (Intrinsics.areEqual(shadeMode, ShadeMode.Split.INSTANCE)) {
            pairArrDualShadeActions = ShadeUserActionsKt.splitShadeActions();
        } else {
            if (!Intrinsics.areEqual(shadeMode, ShadeMode.Dual.INSTANCE)) {
                throw new NoWhenBranchMatchedException();
            }
            pairArrDualShadeActions = ShadeUserActionsKt.dualShadeActions();
        }
        CollectionsKt__MutableCollectionsKt.addAll(listBuilderCreateListBuilder, pairArrDualShadeActions);
        ListBuilder listBuilderBuild = listBuilderCreateListBuilder.build();
        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(listBuilderBuild, 10));
        if (iMapCapacity < 16) {
            iMapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
        ListIterator listIterator = listBuilderBuild.listIterator(0);
        while (true) {
            ListBuilder.Itr itr = (ListBuilder.Itr) listIterator;
            if (!itr.hasNext()) {
                return linkedHashMap;
            }
            Pair pair = (Pair) itr.next();
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
    }
}
