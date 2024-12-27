package com.android.systemui.statusbar.notification.footer.ui.viewmodel;

import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class FooterViewModel$clearAllButton$2 extends AdaptedFunctionReference implements Function3 {
    public static final FooterViewModel$clearAllButton$2 INSTANCE = new FooterViewModel$clearAllButton$2();

    public FooterViewModel$clearAllButton$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        Boolean bool2 = (Boolean) obj2;
        bool2.booleanValue();
        return new Pair(bool, bool2);
    }
}
