package com.android.systemui.statusbar.notification.collection.coordinator;

import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class NotificationStatsLoggerCoordinator$sam$java_util_function_Consumer$0 implements Consumer {
    private final /* synthetic */ Function1 function;

    public NotificationStatsLoggerCoordinator$sam$java_util_function_Consumer$0(Function1 function1) {
        this.function = function1;
    }

    @Override // java.util.function.Consumer
    public final /* synthetic */ void accept(Object obj) {
        this.function.mo779invoke(obj);
    }
}
