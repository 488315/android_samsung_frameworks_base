package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationStackSizeCalculator$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ NotificationStackSizeCalculator f$0;

    public /* synthetic */ NotificationStackSizeCalculator$$ExternalSyntheticLambda2(NotificationStackSizeCalculator notificationStackSizeCalculator) {
        this.f$0 = notificationStackSizeCalculator;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        KProperty[] kPropertyArr = NotificationStackSizeCalculator.$$delegatedProperties;
        return Boolean.valueOf(NotificationStackSizeCalculator.isShowable((ExpandableView) obj, this.f$0.onLockscreen()));
    }
}
