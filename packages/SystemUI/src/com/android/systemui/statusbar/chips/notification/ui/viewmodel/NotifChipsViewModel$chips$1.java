package com.android.systemui.statusbar.chips.notification.ui.viewmodel;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.chips.notification.shared.StatusBarNotifChips;
import com.android.systemui.statusbar.notification.domain.model.TopPinnedState;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class NotifChipsViewModel$chips$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ NotifChipsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotifChipsViewModel$chips$1(NotifChipsViewModel notifChipsViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = notifChipsViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NotifChipsViewModel$chips$1 notifChipsViewModel$chips$1 = new NotifChipsViewModel$chips$1(this.this$0, (Continuation) obj3);
        notifChipsViewModel$chips$1.L$0 = (List) obj;
        notifChipsViewModel$chips$1.L$1 = (TopPinnedState) obj2;
        return notifChipsViewModel$chips$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        List list2 = list;
        NotifChipsViewModel notifChipsViewModel = this.this$0;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        if (!it.hasNext()) {
            return arrayList;
        }
        int i = NotifChipsViewModel.$r8$clinit;
        notifChipsViewModel.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = StatusBarNotifChips.$r8$clinit;
        throw new IllegalStateException("New code path not supported when android.app.ui_rich_ongoing is disabled.");
    }
}
