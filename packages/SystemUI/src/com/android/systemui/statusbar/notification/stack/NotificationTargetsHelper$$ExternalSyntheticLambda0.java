package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationTargetsHelper$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ExpandableView expandableView = (ExpandableView) obj;
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(expandableView.getVisibility() == 0);
            default:
                return Boolean.valueOf(expandableView.getVisibility() == 0);
        }
    }
}
