package com.android.systemui.communal.widgets;

import com.android.systemui.util.kotlin.WithPrev;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class CommunalAppWidgetHostStartable$start$4 extends AdaptedFunctionReference implements Function3 {
    public static final CommunalAppWidgetHostStartable$start$4 INSTANCE = new CommunalAppWidgetHostStartable$start$4();

    public CommunalAppWidgetHostStartable$start$4() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair((WithPrev) obj, (List) obj2);
    }
}
