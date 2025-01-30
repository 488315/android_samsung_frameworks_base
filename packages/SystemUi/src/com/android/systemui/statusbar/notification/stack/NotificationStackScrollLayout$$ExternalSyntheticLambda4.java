package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.Comparator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda4 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ExpandableView expandableView = (ExpandableView) obj;
        ExpandableView expandableView2 = (ExpandableView) obj2;
        int i = NotificationStackScrollLayout.$r8$clinit;
        return Float.compare(expandableView.getTranslationY() + expandableView.mActualHeight, expandableView2.getTranslationY() + expandableView2.mActualHeight);
    }
}
