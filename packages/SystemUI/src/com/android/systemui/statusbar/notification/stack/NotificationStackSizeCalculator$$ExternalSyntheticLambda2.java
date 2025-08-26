package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KProperty;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationStackSizeCalculator$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ NotificationStackSizeCalculator f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        KProperty[] kPropertyArr = NotificationStackSizeCalculator.$$delegatedProperties;
        return Boolean.valueOf(NotificationStackSizeCalculator.isShowable((ExpandableView) obj, this.f$0.onLockscreen()));
    }
}
