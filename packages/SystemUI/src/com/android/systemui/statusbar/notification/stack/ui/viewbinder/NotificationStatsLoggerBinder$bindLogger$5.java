package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.util.kotlin.Utils;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes3.dex */
final /* synthetic */ class NotificationStatsLoggerBinder$bindLogger$5 extends AdaptedFunctionReference implements Function3 {
    public NotificationStatsLoggerBinder$bindLogger$5(Object obj) {
        super(3, obj, Utils.Companion.class, "toTriple", "toTriple(Ljava/lang/Object;Lkotlin/Pair;)Lkotlin/Triple;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        Utils.Companion companion = (Utils.Companion) this.receiver;
        NotificationStatsLoggerBinder notificationStatsLoggerBinder = NotificationStatsLoggerBinder.INSTANCE;
        return companion.toTriple((Utils.Companion) bool, (Pair) obj2);
    }
}
