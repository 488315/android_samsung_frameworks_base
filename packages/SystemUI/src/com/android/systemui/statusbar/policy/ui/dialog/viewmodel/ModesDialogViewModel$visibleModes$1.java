package com.android.systemui.statusbar.policy.ui.dialog.viewmodel;

import com.android.settingslib.notification.modes.ZenMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class ModesDialogViewModel$visibleModes$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public ModesDialogViewModel$visibleModes$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ModesDialogViewModel$visibleModes$1 modesDialogViewModel$visibleModes$1 = new ModesDialogViewModel$visibleModes$1((Continuation) obj3);
        modesDialogViewModel$visibleModes$1.L$0 = (List) obj;
        modesDialogViewModel$visibleModes$1.L$1 = (List) obj2;
        return modesDialogViewModel$visibleModes$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        List list2 = (List) this.L$1;
        List list3 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        Iterator it = list3.iterator();
        while (it.hasNext()) {
            arrayList.add(((ZenMode) it.next()).mId);
        }
        Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : list2) {
            ZenMode zenMode = (ZenMode) obj2;
            if (!set.contains(zenMode.mId)) {
                if (zenMode.mRule.isEnabled()) {
                    if (zenMode.isActive() || zenMode.mRule.isManualInvocationAllowed()) {
                    }
                } else if (zenMode.mRule.isEnabled() || zenMode.mStatus != ZenMode.Status.DISABLED_BY_OTHER) {
                }
            }
            arrayList2.add(obj2);
        }
        return arrayList2;
    }
}
