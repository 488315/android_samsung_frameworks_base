package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.Comparator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda3 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ExpandableView expandableView = (ExpandableView) obj;
        ExpandableView expandableView2 = (ExpandableView) obj2;
        boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
        return Float.compare(expandableView.getTranslationY() + expandableView.mActualHeight, expandableView2.getTranslationY() + expandableView2.mActualHeight);
    }
}
