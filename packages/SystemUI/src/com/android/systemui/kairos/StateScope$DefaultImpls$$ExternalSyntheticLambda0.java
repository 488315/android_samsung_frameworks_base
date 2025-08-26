package com.android.systemui.kairos;

import com.android.systemui.kairos.util.Maybe;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class StateScope$DefaultImpls$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StateScope$DefaultImpls$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, final Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                final Function2 function2 = (Function2) this.f$0;
                return TransactionalKt.transactionally(new Function1() { // from class: com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj3) {
                        return function2.invoke((TransactionScope) obj3, obj2);
                    }
                });
            case 1:
                return (EventsInit) this.f$0;
            case 2:
                Map.Entry entry = (Map.Entry) obj2;
                return StateKt.map((State) ((MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10) this.f$0).invoke((KairosScope) obj, entry), new StateScope$DefaultImpls$$ExternalSyntheticLambda0(entry, 3));
            case 3:
                Map.Entry entry2 = (Map.Entry) this.f$0;
                if (!((Boolean) obj2).booleanValue()) {
                    Maybe.Companion.getClass();
                    return Maybe.Companion.absent;
                }
                Maybe.Companion companion = Maybe.Companion;
                Object value = entry2.getValue();
                companion.getClass();
                return Maybe.Present.m2590boximpl(value);
            default:
                return (State) ((StateScope$DefaultImpls$$ExternalSyntheticLambda0) this.f$0).invoke((KairosScope) obj, (Map.Entry) obj2);
        }
    }
}
