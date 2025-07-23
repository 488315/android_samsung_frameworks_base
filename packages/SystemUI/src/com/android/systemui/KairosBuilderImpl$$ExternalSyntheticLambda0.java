package com.android.systemui;

import com.android.systemui.kairos.BuildScope;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KairosBuilderImpl$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ KairosBuilderImpl f$0;

    public /* synthetic */ KairosBuilderImpl$$ExternalSyntheticLambda0(KairosBuilderImpl kairosBuilderImpl) {
        this.f$0 = kairosBuilderImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        BuildScope buildScope = (BuildScope) obj;
        KairosBuilderImpl kairosBuilderImpl = this.f$0;
        List list = kairosBuilderImpl._startables;
        if (list == null) {
            throw new IllegalStateException("Kairos network has already been initialized");
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((KairosActivatable) it.next()).activate(buildScope);
        }
        kairosBuilderImpl._startables = null;
        return Unit.INSTANCE;
    }
}
