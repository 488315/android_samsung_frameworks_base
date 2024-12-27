package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.Comparator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda3 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ExpandableView expandableView = (ExpandableView) obj;
        ExpandableView expandableView2 = (ExpandableView) obj2;
        boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
        return Float.compare(expandableView.getTranslationY() + expandableView.mActualHeight, expandableView2.getTranslationY() + expandableView2.mActualHeight);
    }
}
