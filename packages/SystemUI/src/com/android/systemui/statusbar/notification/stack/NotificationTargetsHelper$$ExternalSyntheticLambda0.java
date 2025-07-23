package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationTargetsHelper$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ExpandableView expandableView = (ExpandableView) obj;
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(expandableView.getVisibility() == 0);
            default:
                return Boolean.valueOf(expandableView.getVisibility() == 0);
        }
    }
}
