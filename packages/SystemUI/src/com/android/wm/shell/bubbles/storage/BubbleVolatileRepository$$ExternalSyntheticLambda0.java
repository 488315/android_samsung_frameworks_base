package com.android.wm.shell.bubbles.storage;

import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleVolatileRepository$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleVolatileRepository$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(Intrinsics.areEqual(((BubbleEntity) this.f$0).key, ((BubbleEntity) obj).key));
            case 1:
                return Boolean.valueOf(Intrinsics.areEqual(((BubbleEntity) this.f$0).key, ((BubbleEntity) obj).key));
            default:
                return Boolean.valueOf(!((List) this.f$0).contains(Integer.valueOf(((BubbleEntity) obj).userId)));
        }
    }
}
