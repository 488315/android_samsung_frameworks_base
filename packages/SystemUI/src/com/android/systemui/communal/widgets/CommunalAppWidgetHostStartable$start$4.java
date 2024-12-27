package com.android.systemui.communal.widgets;

import com.android.systemui.util.kotlin.WithPrev;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
