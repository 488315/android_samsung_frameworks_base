package com.android.systemui.communal.widgets;

import com.android.systemui.util.kotlin.WithPrev;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class CommunalAppWidgetHostStartable$onStartInForegroundUser$5 extends AdaptedFunctionReference implements Function3 {
    public static final CommunalAppWidgetHostStartable$onStartInForegroundUser$5 INSTANCE = new CommunalAppWidgetHostStartable$onStartInForegroundUser$5();

    public CommunalAppWidgetHostStartable$onStartInForegroundUser$5() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int i = CommunalAppWidgetHostStartable.$r8$clinit;
        return new Pair((WithPrev) obj, (List) obj2);
    }
}
