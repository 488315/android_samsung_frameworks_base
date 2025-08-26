package com.android.systemui.unfold;

import com.android.systemui.unfold.progress.UnfoldTransitionProgressForwarder;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class UnfoldInitializationStartable$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ UnfoldInitializationStartable$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Object obj2 = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                ((UnfoldInitializationStartable) obj2).unfoldTransitionProgressForwarder.ifPresent(new UnfoldInitializationStartable$sam$java_util_function_Consumer$0(new UnfoldInitializationStartable$$ExternalSyntheticLambda3((UnfoldTransitionProgressProvider) obj, 1)));
                break;
            default:
                int i = UnfoldInitializationStartable.$r8$clinit;
                ((UnfoldTransitionProgressProvider) obj2).addCallback((UnfoldTransitionProgressForwarder) obj);
                break;
        }
        return Unit.INSTANCE;
    }
}
